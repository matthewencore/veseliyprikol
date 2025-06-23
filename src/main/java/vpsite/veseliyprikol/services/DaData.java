package vpsite.veseliyprikol.services;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vpsite.veseliyprikol.models.dadata.DadataModels;
import vpsite.veseliyprikol.repository.DaDataRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class DaData {
    private final DaDataRepository daDataRepository;

    private void find_token_settings(){
    }

    // Простое создание токена
    public void create_token_settings(){
        daDataRepository.save(new DadataModels(1L,""));
        log.info("Объект для настройки был создан успешно, заполните его и попробуйте еще раз.");
    }


}
