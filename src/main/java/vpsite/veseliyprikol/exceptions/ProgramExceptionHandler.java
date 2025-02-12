package vpsite.veseliyprikol.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vpsite.veseliyprikol.api.dadata_service.json.JSON_Response;
import vpsite.veseliyprikol.api.one_c_portal.exeption.EmptyDataAuthPortal1C;
import vpsite.veseliyprikol.api.one_c_portal.exeption.IncorrectDataAuthPortal1C;

@RestControllerAdvice
public class ProgramExceptionHandler {

    @ExceptionHandler(EmptyDataAuthPortal1C.class)
    ResponseEntity<JSON_Response> handlerNoAuthData(){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST).body(new JSON_Response(HttpStatus.BAD_REQUEST.toString(),"Некорректные данные для авторизации."));
    }

    @ExceptionHandler(IncorrectDataAuthPortal1C.class)
    ResponseEntity<JSON_Response> handlerIncorrectAuthData(){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED).body(new JSON_Response(HttpStatus.UNAUTHORIZED.toString(),"Данные для авторизации не правильные, проверьте их и попробуйте снова."));
    }
}
