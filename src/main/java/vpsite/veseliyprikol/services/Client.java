package vpsite.veseliyprikol.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import vpsite.veseliyprikol.api.dadata_service.DadataConnector;
import vpsite.veseliyprikol.api.dadata_service.json.FederalTaxServiceResponse;
import vpsite.veseliyprikol.api.dadata_service.json.JSON_Response;
import vpsite.veseliyprikol.api.dadata_service.json.SuggestionResponse;
import vpsite.veseliyprikol.models.client.*;
import vpsite.veseliyprikol.repository.ClientRepository;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Autowired
    ClientRepository client_rep;
    @Autowired
    DadataConnector dadataConnector;
    @Autowired
    Okved okved;

    public ResponseEntity<JSON_Response> create_with_api(SuggestionResponse response) throws URISyntaxException, IOException, InterruptedException {
        if (response.getSuggestions() == null || response.getSuggestions().isEmpty()){
            log.error("Клиент не был найден по текущему запросу, проверьте пожалуйста входные данные.");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new JSON_Response(HttpStatus.NOT_FOUND
                    .toString(),"По такому запросу клиент не был найден, проверьте входные данные."));
        }

        // Инстанцированные объект
        vpsite.veseliyprikol.models.client.Client client = new vpsite.veseliyprikol.models.client.Client();

        FederalTaxService fns_info = new FederalTaxService();
        PlaceClient place = new PlaceClient();
        LeadersClient leader = new LeadersClient();

        client.setArchiveBlock(false); // Архивный блок по умолчанию
        client.setDateOfCreated(LocalDate.now()); // Дата создания
        client.setUserBy("matthewencore"); // Кто создал автоматически

        // Обработка только одного объекта по этому я и поставил break
        for (SuggestionResponse.Suggestion cl: response.getSuggestions()) {

            // Проверка на уникальность, имеются ли еще такие клиенты
            if (!check_unique(cl.getData().getInn(), cl.getData().getOgrn())){
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new JSON_Response(HttpStatus.BAD_REQUEST
                        .toString(),String.format("[%s] уже есть в базе",cl.getValue())));
            }

            // Блок информации по федеральной налоговой службе
            fns_info.setInn(validateString(cl.getData().getInn(),"ИНН"));
            fns_info.setOgrn(validateString(cl.getData().getOgrn(),"ОГРН"));
            fns_info.setNameOrganization(validateString(cl.getValue(),"Наименование организации"));
            fns_info.setOkato(validateString(cl.getData().getOkato(),"ОКАТО"));
            fns_info.setOkfs(validateString(cl.getData().getOkfs(),"ОКФС"));
            fns_info.setOktmo(validateString(cl.getData().getOktmo(),"ОКТМО"));
            fns_info.setOkved(validateString(cl.getData().getOkved(),"ОКВЕД"));

            fns_info.setType(validateString(cl.getData().getType(),"Правовая форма"));
            fns_info.setTax_office(validateString(cl.getData().getAddress().getData().getTax_office(),"ФНС"));

            if (cl.getData().getType().equalsIgnoreCase("INDIVIDUAL")) {
                leader.setName(validateString(cl.getData().getFio().getName(), "Имя лидера"));
                leader.setSurname(validateString(cl.getData().getFio().getSurname(), "Фамилия лидера"));
                leader.setPatronymic(validateString(cl.getData().getFio().getPatronymic(), "Отчество лидера"));
                leader.setPost("Руководитель");
            } else {
                leader.setFullname(validateString(cl.getData().getManagement().getName(),"Полное имя лидера"));
                leader.setPost(StringUtils.capitalize(validateString(cl.getData().getManagement().getPost(), "Должность руководителя")));
            }


            // Блок для КПП
            if (cl.getData().getType().equalsIgnoreCase("INDIVIDUAL")){
                fns_info.setKpp("");
                log.info("По умолчанию КПП игнорируется для индивидуальных предпринимателей.");
            } else {
                fns_info.setKpp(validateString(cl.getData().getKpp(),"КПП"));
            }

            // Блок местоположения
            place.setPostal_code(validateString(cl.getData().getAddress().getData().getPostal_code(),"Почтовый индекс"));
            place.setCountry(validateString(cl.getData().getAddress().getData().getCountry(),"Страна"));
            place.setRegion_fias_id(validateString(cl.getData().getAddress().getData().getRegion_fias_id(),"ФИАС ИД"));
            place.setRegion_kladr_id(validateString(cl.getData().getAddress().getData().getRegion_kladr_id(),"КЛАДР"));
            place.setRegion_with_type(validateString(cl.getData().getAddress().getData().getRegion_with_type(),"Регион с типом"));
            place.setRegion(validateString(cl.getData().getAddress().getData().getRegion(),"Регион"));
            place.setRegion_type(validateString(cl.getData().getAddress().getData().getRegion_type(),"Тип региона (сокращено)"));
            place.setRegion_type_full(validateString(cl.getData().getAddress().getData().getRegion_type_full(),"Регион (полностью)"));
            place.setCity(validateString(cl.getData().getAddress().getData().getCity(),"Город"));
            place.setCity_with_type(validateString(cl.getData().getAddress().getData().getCity_with_type(),"Город с типом"));

            break;
        }
        // Добавляем в клиента всю информацию которую надыбали с нашего цикла
        client.setLeadersClient(leader);
        client.setFederalTaxService(fns_info);
        client.setPlaceClient(place);


        // Заполнение КВЭДов
        Okveds okveds = okved.createOkvedWithAPI(fns_info.getOkved());
        okveds.setClient(client);
        System.out.println(client);
        client.setOkveds(List.of(okveds));



        // Заполнение лидера ИП, он же директор, но мне это слово больше понравилось
        if (fns_info.getType().equalsIgnoreCase("INDIVIDUAL")) {
            if (!StringUtils.hasText(leader.getPatronymic())) {
                log.info("У клиента {} нет отчества если это не ошибка, обращать внимания не нужно."
                        , validateString(client.getFederalTaxService().getNameOrganization(),"Наименование клиента"));
                leader.setFullname(String.format("%s %s", leader.getSurname(), leader.getName()));
            } else if (StringUtils.hasText(leader.getName()) && StringUtils.hasText(leader.getSurname()) && StringUtils.hasText(leader.getPatronymic())) {
                leader.setFullname(String.format("%s %s %s", leader.getSurname(), leader.getName(), leader.getPatronymic()));
            }
        }

        // Заполнение налоговой
        if (StringUtils.hasText(fns_info.getTax_office())) {
            // Получаем с API dadata информацию о клиенте.
            HttpResponse<String> dadataResponse = dadataConnector.fns_info(fns_info.getTax_office());

            // Десериализируем строку в объект
            ObjectMapper objectMapper = new ObjectMapper();
            FederalTaxServiceResponse federalTaxServiceResponse = objectMapper.readValue(dadataResponse.body(), FederalTaxServiceResponse.class);

            // По такой же схеме, 1 объект здесь.
            for (FederalTaxServiceResponse.SuggestionFNS fns: federalTaxServiceResponse.getSuggestions()){
                fns_info.setAddress_tax_office(validateString(fns.getData().getAddress(),"Адрес налоговой"));
                fns_info.setName_tax(validateString(fns.getValue(),"Наименование налоговой"));
                break;
            }
        }

        // Создания клиента
        client_rep.save(client);

        // Логирование
        log.info("\"{}\" был успешно создан пользователем {}",
                client.getFederalTaxService().getNameOrganization(),
                client.getUserBy());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new JSON_Response(HttpStatus.OK
                .toString(),String.format("[%s] был успешно создан.",client.getFederalTaxService().getNameOrganization())));
    }



    public boolean check_unique(String inn, String ogrn){
        List<vpsite.veseliyprikol.models.client.Client> client = client_rep.findByFederalTaxService_InnOrFederalTaxService_Ogrn(inn, ogrn);
        if (client.isEmpty()) {
            log.info("Клиент с таким ИНН: {} и ОГРН: {} не были найдены, переходим дальше...",inn,ogrn);
            return true;
        }
        log.error("Возникла ошибка. Клиент с таким ИНН: {} и ОГРН: {} уже есть в базе данных.",inn,ogrn);
        return false;
    }

    // Найти по слагу человека
    public vpsite.veseliyprikol.models.client.Client get_client_by_slug(String slug){
        return client_rep.findBySlug(slug)
                .orElseThrow(() -> new ClientDoesNotExist("По такому слагу клиента не нашлось."));

    }

    public String validateString(String element, String field){
        if (element != null && StringUtils.hasText(element)){
            return element;

        } else {
            log.warn("В процессе операции, поле [{}] оказалось пустым. " +
                    "По умолчанию поставлено строка без символов.",field);
            return "";
        }
    }


}
