package vpsite.veseliyprikol.controllers.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vpsite.veseliyprikol.api.dadata_service.DadataConnector;
import vpsite.veseliyprikol.api.dadata_service.json.JSON_Response;
import vpsite.veseliyprikol.api.dadata_service.json.SuggestionResponse;
import vpsite.veseliyprikol.services.Client;
import vpsite.veseliyprikol.services.DaData;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;

@Slf4j
@RestController()
@RequestMapping(path = "/api/")
@AllArgsConstructor
@NoArgsConstructor
public class RestClient {
    @Autowired
    DadataConnector dadataConnector;

    @Autowired
    Client client;

    @Autowired
    DaData service;

    @PostMapping("/create-client-with-api/{q}")
    ResponseEntity<JSON_Response> createClient(@PathVariable String q) throws URISyntaxException, IOException, InterruptedException {
            log.info("Вызов метода был произведен с параметром [{}] count: {}", q, q.length());

            // Получаем с API dadata информацию о клиенте.
            HttpResponse<String> dadataResponse = dadataConnector.deserialize_client_fns(q);

            // Десериализируем строку в объект
            ObjectMapper objectMapper = new ObjectMapper();
            SuggestionResponse response = objectMapper.readValue(dadataResponse.body(), SuggestionResponse.class);

            response.getSuggestions().forEach(suggestion -> {
                log.info("Информация об объекте (см. ниже) " +
                        "\n" +
                        "\nНаименование орг: {}" +
                        "\nИНН: {}" +
                        "\nОГРН: {}" +
                        "\nАдрес: {}" +
                        "\nДиректор: {}" +
                        "\n",

                        suggestion.getValue(),
                        suggestion.getData().getInn(),
                        suggestion.getData().getOgrn(),
                        suggestion.getData().getAddress().getValue(),
                        suggestion.getData().getName().getFull());
            });

            // Создаём объект Client
            return client.create_with_api(response);
    }
}

        /*
        response.getSuggestions().forEach((el) -> {
            System.out.println(el.getValue());
            System.out.println(el.getData().getInn());
            System.out.println(el.getData().getOkved());

            System.out.println(el.getData().getAddress().getValue());
            System.out.println(el.getData().getAddress().getData().getCity());
        });
        */
        /*
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new JSON_Response(HttpStatus.OK.toString(),"Успешно! Данные были успешно получены на сервер."));

         */