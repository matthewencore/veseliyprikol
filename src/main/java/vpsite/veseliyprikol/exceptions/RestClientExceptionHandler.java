package vpsite.veseliyprikol.exceptions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vpsite.veseliyprikol.api.dadata_service.exceptions.DaDataBadRequest;
import vpsite.veseliyprikol.api.dadata_service.exceptions.DaDataForbiden;
import vpsite.veseliyprikol.api.dadata_service.exceptions.DaDataTableEmpty;
import vpsite.veseliyprikol.api.dadata_service.exceptions.DaDataTokenEmpty;
import vpsite.veseliyprikol.api.dadata_service.json.JSON_Response;
import vpsite.veseliyprikol.services.DaData;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class RestClientExceptionHandler {

    private final DaData service;

    @ExceptionHandler(DaDataBadRequest.class)
    public ResponseEntity<JSON_Response> handleBadRequest(){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new JSON_Response(HttpStatus.BAD_REQUEST
                        .toString(),"Запрос не должен содержать только спец. символы."));
    }

    @ExceptionHandler(DaDataForbiden.class)
    public ResponseEntity<JSON_Response> handleForbidden(){
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new JSON_Response(HttpStatus.FORBIDDEN
                        .toString(),"Нет доступа, скорее всего токен для 'dadata' не валидный."));
    }

    @ExceptionHandler(DaDataTableEmpty.class)
    public ResponseEntity<JSON_Response> handleBadRequestTable(){
        // Создание объекта для ввода токена
        log.error("Объект для настройки токена - пуст, создаём объект...");
        service.create_token_settings();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new JSON_Response(HttpStatus.BAD_REQUEST
                        .toString(),"Настройки для дадаты пусты, " +
                        "попробуйте еще раз, заполните их и попробуйте еще раз."));
    }

    @ExceptionHandler(DaDataTokenEmpty.class)
    public ResponseEntity<JSON_Response> handleTokenEmpty(){
        log.error("Объект для настройки токена - пуст");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new JSON_Response(HttpStatus.BAD_REQUEST
                        .toString(),"Настройки для дадаты пусты, " +
                        "попробуйте еще раз, заполните их и попробуйте еще раз."));
    }

}
