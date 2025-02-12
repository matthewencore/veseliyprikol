package vpsite.veseliyprikol.api.dadata_service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vpsite.veseliyprikol.api.dadata_service.exceptions.*;
import vpsite.veseliyprikol.models.dadata.DadataModels;
import vpsite.veseliyprikol.repository.DaDataRepository;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import lombok.extern.slf4j.Slf4j;
import vpsite.veseliyprikol.services.DaData;

@Data
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class DadataConnector {
    @Autowired
    DaDataRepository dadata;
    @Autowired
    DaData service;

    // Проверка таблицы
    DadataModels checkToken(){
            DadataModels connector = dadata.findById(1L)
                    .orElseThrow(() -> new DaDataTableEmpty("Объекта настройки нет"));

            if (connector.getToken().isBlank()){
                throw new DaDataTokenEmpty("Токен для соединения пуст, пожалуйста введите его.");
            }

            return connector;
    }

    public HttpResponse<String> deserialize_client_fns(String query) throws URISyntaxException, IOException, InterruptedException {
        // Проверка запроса
        if (query.isBlank() || query.matches("[^a-zA-Z0-9\\s]")){
            log.error("Запрос не должен содержать только спец. символы. ");
            throw new DaDataBadRequest("Запрос не должен содержать только спец. символы.");
        }


        DadataModels dadataTable = checkToken();

        // HTTP запрос
        HttpClient http = HttpClient.newBuilder().build();

        // Для ФНС подсказок
        String url = "http://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/party";
        String body = String.format("{\"query\": \"%s\"}",query);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .timeout(Duration.of(30, ChronoUnit.SECONDS))
                .header("Content-Type", "application/json")
                .header("Authorization", String.format("Token %s",dadataTable.getToken()))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = http.send(request,HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 403) {
            throw new DaDataForbiden("Не верный токен, пожалуйста измените его в настройках."); // вызов ошибки
        }

        if (response.statusCode() != 200){
            log.error("API | Возникла ошибка с кодом {}", response.statusCode());
            throw new RuntimeException(
                    String.format("Возникли ошибки при отправке запроса: " +
                            "\nCode: %s" +
                            "\n--------" +
                            "\nReason: %s",response.statusCode(),response.body()));
        }



        log.info("Десереализация прошла успешно");
        return response;

    }

    // Подтянуть информацию по ФНС
    public HttpResponse<String> fns_info(String code_fns) throws URISyntaxException, IOException, InterruptedException {
        // Проверка токена
        DadataModels dadataTable = checkToken();
        String url = "http://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/fns_unit";

        HttpClient http = HttpClient.newBuilder().build();
        String body = String.format("{\"query\": \"%s\"}",code_fns);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .timeout(Duration.of(30, ChronoUnit.SECONDS))
                .header("Content-Type", "application/json")
                .header("Authorization", String.format("Token %s",dadataTable.getToken()))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = http.send(request,HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200){
            log.error("Возникла ошибка с кодом {}", response.statusCode());
            throw new DaDataException(
                    String.format("Возникли ошибки при отправке запроса: " +
                            "\nCode: %s" +
                            "\n--------" +
                            "\nReason: %s",response.statusCode(),response.body()));
        }

        log.info("Десереализация {} ФНС успешна",code_fns);
        return response;
    }

    //Автозаполнение окведов
    public HttpResponse<String> okved_info(String code_okved) throws URISyntaxException, IOException, InterruptedException {
        DadataModels dadataTable = checkToken();
        HttpClient http = HttpClient.newBuilder().build();

        String url = "http://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/okved2";
        String body = String.format("{\"query\": \"%s\"}",code_okved);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .timeout(Duration.of(30, ChronoUnit.SECONDS))
                .header("Content-Type", "application/json")
                .header("Authorization", String.format("Token %s",dadataTable.getToken()))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = http.send(request,HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200){
            log.error("Возникла ошибка с кодом {}", response.statusCode());
            throw new DaDataException(
                    String.format("Возникли ошибки при отправке запроса: " +
                            "\nCode: %s" +
                            "\n--------" +
                            "\nReason: %s",response.statusCode(),response.body()));
        }
        log.info("Десереализация {} ОКВЭД успешна",code_okved);
        return response;
    }
}
