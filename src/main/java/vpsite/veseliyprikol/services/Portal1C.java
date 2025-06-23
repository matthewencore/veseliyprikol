package vpsite.veseliyprikol.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vpsite.veseliyprikol.models.partner1c.SettingsPartner;
import vpsite.veseliyprikol.repository.Settings1CPortalRepository;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class Portal1C {
    private final Settings1CPortalRepository settings1CPortalRepository;

    public SettingsPartner findOrCreateSettings(){
        return settings1CPortalRepository.findById(1L).orElseGet(() -> {
            SettingsPartner settingsPartner = new SettingsPartner(1L,"","");
            settings1CPortalRepository.save(settingsPartner);
            log.info("Был создан пустой объект для настройки 1С портала.");

            return settingsPartner;
        });
    }

    public Map<String,String> getDataSettings(){
        // Ищем или получаем текущую.
        SettingsPartner settings = findOrCreateSettings();
        return Map.of("login",settings.getLogin(),"password",settings.getPassword());
    }

    void createObjectSettings(){
        settings1CPortalRepository.save(new SettingsPartner(1L,"",""));
        log.info("Объект для настройки 1С портала был создан, пожалуйста заполните его.");
    }
}
