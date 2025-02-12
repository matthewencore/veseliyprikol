package vpsite.veseliyprikol.models.partner1c;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettingsPartner {
    @Id
    private Long id;

    private String login;
    private String password;
}
