package vpsite.veseliyprikol.controllers.client.program_1c_rest;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vpsite.veseliyprikol.api.dadata_service.json.JSON_Response;
import vpsite.veseliyprikol.api.one_c_portal.Connector1CPortal;
import vpsite.veseliyprikol.api.one_c_portal.json.ClientInformation;
import vpsite.veseliyprikol.api.one_c_portal.json.ProgramAccessDto;
import vpsite.veseliyprikol.api.one_c_portal.json.ResultAPI1C;
import vpsite.veseliyprikol.services.ProgramService;
import vpsite.veseliyprikol.services.SubscriberService;
import vpsite.veseliyprikol.utils.UtilsJSON;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping(path = "/api/")
public class RestProgram {

    @Autowired
    Connector1CPortal connector1CPortal;

    @Autowired
    SubscriberService subscriberService;

    @Autowired
    ProgramService programService;

    UtilsJSON utilsJSON = new UtilsJSON();

    /*ЗАТЯГИВАЕТ ВСЕХ КЛИЕНТОВ С УРОВНЕВ ПРОФ И ОБЛАЧНЫХ ЗАОДНО*/

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

    /* ПОДТЯГИВАЕТ СЕРВИСЫ 1С И ДОПОЛНИТЕЛЬНУЮ С НЕЙ ИНФОРМАЦИЮ
    [SPARK_RISKS, DOCUMENT_RECOGNITION, ESS, NOMENCLATURE, COUNTERAGENT, LINK, CLOUD_BACKUP, SIGN, REPORTING, MAG1C]*/

    @GetMapping(path = "/program/get-service")
    ResponseEntity<JSON_Response> getServiceInfo() throws URISyntaxException, IOException, InterruptedException {

        // не доделано
        System.out.println(connector1CPortal.getInfoService("eae3b513-b6c7-41da-93be-9bb850bcc6ae").body());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new JSON_Response(
                        HttpStatus.OK.toString(),
                        "Запрос прошел без ошибок."));
    }


}
