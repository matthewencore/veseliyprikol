package vpsite.veseliyprikol.models.partner1c;

import jakarta.persistence.*;
import vpsite.veseliyprikol.models.client.Client;

import java.util.List;

@Entity
public class ClientProgram {
    @Id
    @GeneratedValue
    private Long id;

    private Long reg_number;

    @ManyToOne()
    @JoinColumn(name = "program_id")
    private Program program;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

}
