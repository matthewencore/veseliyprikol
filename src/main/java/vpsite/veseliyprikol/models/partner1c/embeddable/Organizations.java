package vpsite.veseliyprikol.models.partner1c.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Organizations {
    private String name;
    private String inn;
    private String kpp;
}
