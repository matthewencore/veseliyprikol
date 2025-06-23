package vpsite.veseliyprikol.api.one_c_portal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import vpsite.veseliyprikol.api.dadata_service.DadataConnector;
import vpsite.veseliyprikol.api.dadata_service.json.SuggestionResponse;
import vpsite.veseliyprikol.api.one_c_portal.exeption.EmptyDataAuthPortal1C;
import vpsite.veseliyprikol.api.one_c_portal.exeption.IncorrectDataAuthPortal1C;
import vpsite.veseliyprikol.api.one_c_portal.json.ClientInformation;
import vpsite.veseliyprikol.api.one_c_portal.json.ReportUeid;
import vpsite.veseliyprikol.api.one_c_portal.json.ServiceInfo;

import vpsite.veseliyprikol.models.client.Client;
import vpsite.veseliyprikol.models.partner1c.Subscriber;
import vpsite.veseliyprikol.services.ClientService;
import vpsite.veseliyprikol.services.Portal1C;
import vpsite.veseliyprikol.services.SubscriberService;
import vpsite.veseliyprikol.utils.UtilsJSON;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import static java.time.temporal.ChronoUnit.SECONDS;
import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class Connector1CPortal {
    private final Portal1C portal1C;
    private final SubscriberService subscriberService;
    private final ClientService clientService;
    private final DadataConnector dadataConnector;


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


    // ДОБАВЛЕНИЕ ПРОГРАММЫ ПО ЛОГИНУ
    public HttpResponse<String> getAllProgramLogin(String login) throws URISyntaxException, IOException, InterruptedException {
        String requestBody = String.format("{\"login\": \"%s\" }",login);
        String url = "https://partner-api.1c.ru/api/client-program-access/search/login";

        return connect1CPortal(requestBody, url, Duration.of(10, SECONDS));
    }

    // /rest/public/option/billing-report
    // ПОЛУЧЕНИЕ GUID ДЛЯ ПОЛУЧЕНИЯ ДАЛЬНЕЙШЕЙ ИНФОРМАЦИИ
    public HttpResponse<String> getGuid(String code, TypeService1CPortal type) throws URISyntaxException, IOException, InterruptedException {
        String url = "https://partner-api.1c.ru/api/rest/public/option/billing-report";
        String requestBody = String.format("""
                {
                  "type": "%s",
                  "subscriberCodeList": [
                    "%s"
                  ]
                }
                """, type.getCh(),code);

        return connect1CPortal(requestBody,url, Duration.of(15, SECONDS));
    }


    // /rest/public/subscription/checkItsBySubscriberCode
    /* ПОЛУЧИТЬ ИНФОРМАЦИЮ ОБ ИТС ПРОФ СОПРОВОЖДЕНИИ ПО НОМЕРУ АБОНЕНТА */
    public HttpResponse<String> getSubscriptionProf(String code) throws URISyntaxException, IOException, InterruptedException {
        String url = "https://partner-api.1c.ru/api/rest/public/subscription/checkItsBySubscriberCode";
        String requestBody = String.format("{\n" +
                "  \"subscriberCodeList\": [\n" +
                "    \"%s\"\n" +
                "  ]\n" +
                "}",code);

        return connect1CPortal(requestBody,url, Duration.of(15, SECONDS));
    }

    /* ПОЛУЧИТЬ ИНФОРМАЦИЮ ОБ ОТРАСЛЕВОМ СОПРОВОЖДЕНИИ ПО НОМЕРУ АБОНЕНТА */
    public HttpResponse<String> getSubscriptionOtrasl(String code) throws URISyntaxException, IOException, InterruptedException {
        String url = "https://partner-api.1c.ru/api/rest/public/industry/checkIndustryBySubscriberCode";
        String requestBody = String.format("{\n" +
                "  \"subscriberCodeList\": [\n" +
                "    \"%s\"\n" +
                "  ]\n" +
                "}",code);

        return connect1CPortal(requestBody,url, Duration.of(15, SECONDS));
    }


    /* БЛОК ЗАПРОСОВ GET */

    // Методы для получения сервисов

    // /rest/public/option/billing-report
    // ПОЛУЧЕНИЕ ИНФОРМАЦИИ ПО СЕРВИСУ ЧЕРЕЗ GUID
    public HttpResponse<String> getGuidEvent(String guid) throws URISyntaxException, IOException, InterruptedException {
        String url = "https://partner-api.1c.ru/api/rest/public/option/billing-report";
        return connect1CPortalGet(url,Duration.of(10, SECONDS), guid);
    }

    /* БЛОК ВСПОМОГАЮЩИХ МЕТОДОВ ДЛЯ РАБОТЫ С POST И GET ЗАПРОСАМИ */


    /* ОПАСНАЯ ФУНКЦИЯ */
    public void createAuto(Integer size, Integer page) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
        UtilsJSON utilsJSON = new UtilsJSON();

        // Создаёт всех проф клиентов
        ClientInformation clientInformation = utilsJSON.deserialize(getAllClient(size,page).body(), ClientInformation.class);

        // Создали клиентов автоматически.
        Map<String, AtomicInteger> mapResponse = subscriberService.createList(clientInformation);
        log.info("Информация об групповом создании клиентов ПРОФ и облачного уровня " +
                "\nДобавлено: {}" +
                "\nПропущено: {}",mapResponse.get("add"), mapResponse.get("skip"));

        // Теперь получим ИНН и свяжем с уже существующим клиентом либо создадим новый.
        for (ClientInformation.Subscribers subscribers : clientInformation.getSubscribers()){
            if (subscribers.getCode().matches("FR-MOST__-\\d*")){
                continue; // Откидываю все облачные потому что они не годятся, нет api которое будет их читать
            }

          //asyncGetInfoService(subscribers.getCode(), TypeService1CPortal.CLOUD_BACKUP));

        }

    }

    @Async
    public CompletableFuture<ServiceInfo> asyncGetInfoService(String code, TypeService1CPortal type) throws URISyntaxException, IOException, InterruptedException {
        log.info("Метод запущен в потоке: {}", Thread.currentThread().getName());

        UtilsJSON utilsJSON = new UtilsJSON();

        // Получаем заветный guid запроса
        ReportUeid reportUeid = utilsJSON.deserialize(getGuid(code, type).body(), ReportUeid.class);

        // Получаем сервисы человека, ИНН и договор ИТС
        ServiceInfo serviceInfo = utilsJSON.deserialize(getGuidEvent(reportUeid.getReportUeid()).body(), ServiceInfo.class);

        int limit = 10; // лимит попыток
        int msWait = 1200; // Поддельная задержка
        int count = 0; // счетчик

        while (serviceInfo.getState().equals("PROCESSING") || limit == 0) {
            count += 1;
            log.info("Попытка получить данные #{}", count);
            limit -= 1;
            serviceInfo = utilsJSON.deserialize(getGuidEvent(reportUeid.getReportUeid()).body(), ServiceInfo.class);
            Thread.sleep(msWait);

            // Добавления задержки на 0.5 с
            msWait += 500;
        }

        log.info("{}",serviceInfo.getState());

        return CompletableFuture.completedFuture(serviceInfo);
    }

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
