package vpsite.veseliyprikol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vpsite.veseliyprikol.models.partner1c.Program;

import java.util.Optional;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    Optional<Program> findByNick(String nick);
}
