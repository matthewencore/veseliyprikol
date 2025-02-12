package vpsite.veseliyprikol.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vpsite.veseliyprikol.models.client.ClientDoesNotExist;

@Slf4j
@ControllerAdvice
public class ClientExceptionHandler {

    @ExceptionHandler(ClientDoesNotExist.class)
    public String handlerDoesNotExistClient(){
        log.error("Клиент не был найден");
        return "client/exception/does_not_exist_404";
    }
}

