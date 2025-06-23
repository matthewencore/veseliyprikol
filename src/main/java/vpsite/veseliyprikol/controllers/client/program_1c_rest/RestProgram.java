package vpsite.veseliyprikol.controllers.client.program_1c_rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vpsite.veseliyprikol.api.dadata_service.DadataConnector;
import vpsite.veseliyprikol.api.dadata_service.json.JSON_Response;
import vpsite.veseliyprikol.api.dadata_service.json.SuggestionResponse;
import vpsite.veseliyprikol.api.one_c_portal.Connector1CPortal;
import vpsite.veseliyprikol.api.one_c_portal.TypeService1CPortal;
import vpsite.veseliyprikol.api.one_c_portal.json.*;
import vpsite.veseliyprikol.services.ClientService;
import vpsite.veseliyprikol.services.ProgramService;
import vpsite.veseliyprikol.services.SubscriberService;
import vpsite.veseliyprikol.utils.UtilsJSON;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController
@RequestMapping(path = "/api/")
@RequiredArgsConstructor
public class RestProgram {

    private final Connector1CPortal connector1CPortal;
    private final SubscriberService subscriberService;
    private final ProgramService programService;
    private final DadataConnector dadataConnector;
    private final ClientService client;

    UtilsJSON utilsJSON = new UtilsJSON();

    /*ЗАТЯГИВАЕТ ВСЕХ КЛИЕНТОВ С УРОВНЕМ ПРОФ И ОБЛАЧНЫХ ЗАОДНО*/

    @GetMapping(path = "/program/get-all-client")
    ResponseEntity<ResultAPI1C> getAllClient() throws URISyntaxException, IOException, InterruptedException {

            Map<String, AtomicInteger> result = subscriberService.createList(
                utilsJSON.deserialize(connector1CPortal
                .getAllClient(300,0).body(), ClientInformation.class));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResultAPI1C(
                        HttpStatus.OK.toString(),
                        result.get("add").toString(),
                        result.get("skip").toString(),
                        "Операция по автоматическому добавлению клиентов уровня ПРОФ и облака завершена успешно."));
    }

    /* ПОЛУЧЕНИЕ СПИСКА ПРОГРАММ И ПРИСВОЕНИЕ К КЛИЕНТУ, ОТНОСИТСЯ ТОЖЕ К АВТОМАТИЧЕСКИМ */

    @GetMapping(path = "/program/get-all-prog")
    ResponseEntity<JSON_Response> getProgramInfo() throws URISyntaxException, IOException, InterruptedException {
         List<ProgramAccessDto> list =
                 utilsJSON.deserializeList(connector1CPortal.getAllProgram(802498852L)
                 .body(), new TypeReference<>(){});

                // не доделано
                programService.createProgram(list);


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new JSON_Response(
                        HttpStatus.OK.toString(),
                        "Запрос прошел без ошибок."));
    }

    // Подтянуть программы по логину клиента

    @GetMapping(path = "/program/create-progr/{login}")
    ResponseEntity<JSON_Response> getProgramLogin(@PathVariable String login) throws URISyntaxException, IOException, InterruptedException {

        // Для дебага
        System.out.println(connector1CPortal.getAllProgramLogin(login).body());

        List<ProgramAccessDto> programAccessDtos =
                utilsJSON.deserializeList(connector1CPortal.getAllProgramLogin(login).body(), new TypeReference<>(){});


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new JSON_Response(
                        HttpStatus.OK.toString(),
                        "Запрос прошел без ошибок."));
    }

    /* ПОДТЯГИВАЕТ СЕРВИСЫ 1С И ДОПОЛНИТЕЛЬНУЮ С НЕЙ ИНФОРМАЦИЮ
    [SPARK_RISKS, DOCUMENT_RECOGNITION, ESS, NOMENCLATURE, COUNTERAGENT, LINK, CLOUD_BACKUP, SIGN, REPORTING, MAG1C]*/

    @GetMapping(path = "/program/get-service")
    ResponseEntity<JSON_Response> getServiceInfo() throws URISyntaxException, IOException, InterruptedException, ExecutionException {
        ServiceInfo serviceInfo = connector1CPortal.asyncGetInfoService("CL-7024368", TypeService1CPortal.REPORTING).get();

        System.out.println(
                serviceInfo.getReportUeid() +
                        "\n" + serviceInfo.getState() +
                        "\n" + serviceInfo.getReport().getEntries().get(0).getTariffs().get(0).getName() +
                        "\n" + serviceInfo.getReport().getEntries().get(0).getTariffs().get(0).getUserOrganizationName() +
                        "\n" + serviceInfo.getReport().getEntries().get(0).getTariffs().get(0).getUserOrganizationInn()
        );

        // Получаем с API dadata информацию о клиенте.
        HttpResponse<String> dadataResponse = dadataConnector.deserialize_client_fns(serviceInfo.getReport().getEntries().get(0).getTariffs().get(0).getUserOrganizationInn());

        // Десериализируем строку в объект
        ObjectMapper objectMapper = new ObjectMapper();
        SuggestionResponse response = objectMapper.readValue(dadataResponse.body(), SuggestionResponse.class);
        client.create_with_api(response);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new JSON_Response(
                        HttpStatus.OK.toString(),
                        "Запрос прошел без ошибок."));
    }


    // Получить договор ИТС сопровождения по номеру абонента
    @GetMapping(path = "/program/get-subs-prof")
    ResponseEntity<JSON_Response> getSubscribeProfInfo() throws URISyntaxException, IOException, InterruptedException {

        List<SubscribeProf> prof = utilsJSON.deserializeList(connector1CPortal.getSubscriptionProf("CL-7024368").body(), new TypeReference<>() {});

        System.out.println(prof.get(0).getItsContractInfo().get(0).getDescription());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new JSON_Response(
                        HttpStatus.OK.toString(),
                        "Запрос прошел без ошибок."));
    }


    // Получить договор КП Отраслевое по номеру абонента
    @GetMapping(path = "/program/get-subs-otr")
    ResponseEntity<JSON_Response> getSubscribeOtrInfo() throws URISyntaxException, IOException, InterruptedException {

        List<SubscribeOtr> prof = utilsJSON.deserializeList(connector1CPortal.getSubscriptionOtrasl("CL-5799068").body(), new TypeReference<>() {});

        System.out.println(prof.get(0).getProgramInfoList().get(0).getName());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new JSON_Response(
                        HttpStatus.OK.toString(),
                        "Запрос прошел без ошибок."));
    }


    // ЗАПОЛНЕНИЕ ПРОГРАММ, КЛИЕНТА И ЕГО ДОГОВОРОВ
    @GetMapping(path = "/program/all-create")
    ResponseEntity<JSON_Response> getAllData() throws URISyntaxException, IOException, InterruptedException, ExecutionException {

        connector1CPortal.createAuto(300,0);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new JSON_Response(
                        HttpStatus.OK.toString(),
                        "Запрос прошел без ошибок."));
    }


    // НАЙТИ ЛК ЛОКАЛЬНЫЙ 1С

    @GetMapping(path = "/program/getSubs/{id}")
    ResponseEntity<ResponseSubscribe> getSubcribeLk(@PathVariable Long id) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
        String login = subscriberService.findLoginById(id);
        log.info(login);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseSubscribe(login));
    }


    @PostMapping(path = "/program/create-subscribe")
    ResponseEntity<JSON_Response> createSubscribe(@RequestBody DataSubscriber subscriber){

        log.info(subscriber.getLogin());

        subscriberService.createSubscriber(
                subscriber.getId(),
                subscriber.getLogin(),
                subscriber.getPassword());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new JSON_Response(HttpStatus.OK.name(),""));
    }



}
