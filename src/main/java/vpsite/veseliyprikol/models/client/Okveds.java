package vpsite.veseliyprikol.models.client;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Okveds {
    @Id
    @GeneratedValue
    private Long id;

    private String code;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "okved_detail", joinColumns = @JoinColumn(name = "okved_id"))
    private List<OkvedDetail> name;

    // связь с клиентом
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

}
