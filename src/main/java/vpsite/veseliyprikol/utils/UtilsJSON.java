package vpsite.veseliyprikol.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UtilsJSON {
    ObjectMapper objectMapper = new ObjectMapper();

    public <T> T deserialize(String str, Class<T> tClass){

        try {
            return objectMapper.readValue(str, tClass);

        } catch (Exception e) {
            log.error("Возникла ошибка при десериализации, пожалуйста обратите внимание что в качестве класса " +
                    "десериализатора, должен быть класс с идентичной структурой как в JSON");
            throw new RuntimeException("Возникла ошибка при десереализации \n {}", e);
        }
    }

    public <T> T deserializeList(String str, TypeReference<T> tTypeReference){

        try {
            return objectMapper.readValue(str, tTypeReference);

        } catch (Exception e) {
            log.error("Возникла ошибка при List десериализации, пожалуйста обратите внимание что в качестве класса " +
                    "десериализатора, должен быть класс с идентичной структурой как в JSON");
            throw new RuntimeException("Возникла ошибка при List десереализации \n {}", e);
        }
    }


}
