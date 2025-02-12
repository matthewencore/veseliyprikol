package vpsite.veseliyprikol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vpsite.veseliyprikol.models.dadata.DadataModels;

public interface DaDataRepository extends JpaRepository<DadataModels, Long> {
}
