package vpsite.veseliyprikol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vpsite.veseliyprikol.models.partner1c.SettingsPartner;

@Repository
public interface Settings1CPortalRepository  extends JpaRepository<SettingsPartner, Long> {
}
