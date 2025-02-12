package vpsite.veseliyprikol.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import vpsite.veseliyprikol.api.one_c_portal.json.ClientInformation;
import vpsite.veseliyprikol.models.partner1c.Subscriber;
import vpsite.veseliyprikol.models.partner1c.embeddable.Organizations;
import vpsite.veseliyprikol.repository.SubscribersRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class SubscriberService {
    @Autowired
    SubscribersRepository subscribersRepository;

    public Map<String,AtomicInteger> createList(ClientInformation client){
    log.info("\nФункция автоматического заполнения всех клиентов уровня ПРОФ " +
            "и облака включена, имейте ввиду функция затратная по времени, " +
            "а так же используйте ее с опасением дублирования, желательно " +
            "использовать на пустую базу.");

    AtomicInteger counterSkip = new AtomicInteger(0);
    AtomicInteger counterAdd = new AtomicInteger(0);

        for (ClientInformation.Subscribers item : client.getSubscribers()){

            // Проверяем на наличие дублей
            if (checkAvailable(item.getCode()).getCode() != null) {
                log.warn("Клиент с абонентским номером {} уже есть в вашей базе.",item.getCode());
                counterSkip.addAndGet(1);
                continue;
            }

            // Создаём новый объект субскрайбера
            Subscriber subscriber= new Subscriber();

            subscriber.setCode(validateString(item.getCode(),"Код клиента"));
            subscriber.setName(validateString(item.getName(),"Наименование клиента"));

            subscriber.setSubjects(new ArrayList<>(item.getSubjects()));
            subscriber.setReg_nums(new ArrayList<>(Optional.ofNullable(item.getRegNumbers())
                    .orElseGet(ArrayList::new))
                    .stream()
                    .filter(Objects::nonNull)
                    .toList());

            subscriber.setOrganizations(Optional.ofNullable(item.getOrganizations())
                    .orElseGet(ArrayList::new)
                    .stream()
                    .map(organizations -> new Organizations(
                            organizations.getName(),
                            organizations.getInn(),
                            organizations.getKpp()))
                    .distinct()
                    .toList());

            log.info("Создаю {} {} т.к его в базе не обнаружено.",subscriber.getName(), subscriber.getCode());
            subscribersRepository.save(subscriber);
            counterAdd.addAndGet(1);
        }
        log.info("Операция по добавлению окончена. Информация снизу " +
                "\nДобавлено: {}" +
                "\nПропущено: {}" +
                "\n----------------",counterAdd,counterSkip);

        // Формирования ответа
        HashMap<String,AtomicInteger> result = new HashMap<>();
            result.put("add",counterAdd);
            result.put("skip",counterSkip);

        return result;
    }

    public String validateString(String element, String field){
        if (StringUtils.hasText(element)){
            return element;

        } else {
            log.warn("В процессе операции, поле [{}] оказалось пустым. " +
                    "По умолчанию поставлено строка без символов.",field);
            return "";
        }
    }

    public Subscriber checkAvailable(String code){
        return subscribersRepository.findByCode(code).orElseGet(Subscriber::new);
    }
}
