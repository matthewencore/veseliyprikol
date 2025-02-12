package vpsite.veseliyprikol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vpsite.veseliyprikol.models.client.Client;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
    List<Client> findByFederalTaxService_InnOrFederalTaxService_Ogrn(String federalTaxService_inn, String ogrn);
    Optional<Client> findBySlug(String slug);
    Optional<Client> findByFederalTaxService_Inn(String inn);
}
