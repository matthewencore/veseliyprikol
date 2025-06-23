package vpsite.veseliyprikol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vpsite.veseliyprikol.models.partner1c.Subscriber;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscribersRepository  extends JpaRepository<Subscriber, Long> {
    Optional<Subscriber> findByCode(String code);
    Optional<Subscriber> findByClient_Id(Long id);

}
