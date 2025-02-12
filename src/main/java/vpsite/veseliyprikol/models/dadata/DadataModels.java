package vpsite.veseliyprikol.models.dadata;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@Table(name = "settings_dadata_table")
public class DadataModels {
    @Id
    private Long id;
    private String token;

    public DadataModels(Long id,String token) {
        this.token = token;
        this.id = id;
    }

    public DadataModels() {

    }
}
