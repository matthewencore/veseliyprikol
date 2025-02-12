package vpsite.veseliyprikol.api.one_c_portal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vpsite.veseliyprikol.api.one_c_portal.exeption.EmptyDataAuthPortal1C;
import vpsite.veseliyprikol.api.one_c_portal.exeption.IncorrectDataAuthPortal1C;
import vpsite.veseliyprikol.services.Portal1C;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Map;

import static java.time.temporal.ChronoUnit.SECONDS;

@Slf4j
@Service
public class Connector1CPortal {

    @Autowired
    Portal1C portal1C;
    final HttpClient httpClient = HttpClient.newBuilder().build();

    // POST запросы
    public HttpResponse<String> connect1CPortal(String requestBody, String url, Duration timeout) throws URISyntaxException, IOException, InterruptedException {
        String data = dataToBase64();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .timeout(timeout)
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic " + data)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        //Ответ
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // ПЕРЕПИШИ ТУТ
        if (response.statusCode() == 401){
            log.error("Данные для авторизации не корректны, сервер вернул 401");
            throw new IncorrectDataAuthPortal1C("Данные для авторизации не правильные, проверьте их и попробуйте снова.");
        }

        // Возврат значения
        return response;
    }

    // GET запросы
    public HttpResponse<String> connect1CPortalGet(String url, Duration timeout, String guid) throws URISyntaxException, IOException, InterruptedException {
        String data = dataToBase64();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url + "/" + guid))
                .timeout(timeout)
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic " + data)
                .GET()
                .build();

        //Ответ
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // ПЕРЕПИШИ ТУТ
        if (response.statusCode() == 401){
            log.error("GET | Данные для авторизации не корректны, сервер вернул 401");
            throw new IncorrectDataAuthPortal1C("GET | Данные для авторизации не правильные, проверьте их и попробуйте снова.");
        }

        // Возврат значения
        return response;
    }


    /* БЛОК ЗАПРОСОВ POST* */

    // /rest/public/subscriber
    // ОЧЕНЬ! И ОЧЕНЬ! ТРЕБОВАТЕЛЬНАЯ ФУНКЦИЯ! ЗАПОЛНЯЕТ ВСЕХ ПРОФ КЛИЕНТОВ ИСПОЛЬЗОВАНИЕ ТОЛЬКО КОГДА БАЗА ПУСТАЯ ЛИБО НУЖНО ДОЗАПОЛНИТЬ КЛИЕНТОВ
    public HttpResponse<String> getAllClient(Integer size, Integer page) throws URISyntaxException, IOException, InterruptedException {
        String requestBody = String.format("{ \"page\": %s, \"size\": %s }",page,size);
        String url = "https://partner-api.1c.ru/api/rest/public/subscriber";

        return connect1CPortal(requestBody,url, Duration.of(10, SECONDS));
    }

    // /client-program-access/search/reg-number
    // ДОБАВЛЕНИЕ ПРОГРАММЫ ПО РЕГИСТРАЦИОННОМУ НОМЕРУ
    public HttpResponse<String> getAllProgram(Long reg_num) throws URISyntaxException, IOException, InterruptedException {
        String requestBody = String.format("{\"regNumber\": %d }",reg_num);
        String url = "https://partner-api.1c.ru/api/client-program-access/search/reg-number";

        return connect1CPortal(requestBody, url, Duration.of(10, SECONDS));
    }

    /* БЛОК ЗАПРОСОВ GET */

    // Методы для получения сервисов

    public HttpResponse<String> getInfoService(String guid) throws URISyntaxException, IOException, InterruptedException {
        String url = "https://partner-api.1c.ru/api/rest/public/option/billing-report";

        return connect1CPortalGet(url,Duration.of(15, SECONDS),guid);
    }

    /* БЛОК ВСПОМОГАЮЩИХ МЕТОДОВ ДЛЯ РАБОТЫ С POST И GET ЗАПРОСАМИ */

    // Конвертация к BASE64 для авторизации
    public String dataToBase64(){
        Map<String,String> dataMap = getDataPortal1C();

        String login = dataMap.get("login");
        String password = dataMap.get("password");

        String auth = login + ":" + password;
        return Base64.getEncoder().encodeToString(auth.getBytes());
    }

    // Получить логин и пароль
    public Map<String,String> getDataPortal1C(){
        // Найти или создать объект настроек
        Map<String,String> data = portal1C.getDataSettings();

        if (data.get("login").isBlank() || data.get("password").isBlank()){
            log.info("Данные для авторизации пусты, пожалуйста заполните их");
            throw new EmptyDataAuthPortal1C("Логин или пароль пусты, пожалуйста заполните их и попробуйте еще раз.");
        }

        return data;

    }
}
