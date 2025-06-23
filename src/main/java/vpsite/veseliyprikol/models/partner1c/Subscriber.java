package vpsite.veseliyprikol.models.partner1c;

import jakarta.persistence.*;
import lombok.*;
import vpsite.veseliyprikol.models.client.Client;
import vpsite.veseliyprikol.models.partner1c.embeddable.Organizations;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subscriber {
    @Id
    @GeneratedValue
    private Long id;

    private String code;
    private String name;

    private String login;
    private String password;

    @ElementCollection
    @CollectionTable(name = "subjects", joinColumns = @JoinColumn(name = "subscriber_id"))
    private List<String> subjects;

    @ElementCollection
    @CollectionTable(name = "regnums", joinColumns = @JoinColumn(name = "subscriber_id"))
    private List<Long> reg_nums;

    @ElementCollection
    @CollectionTable(name = "organizations", joinColumns = @JoinColumn(name = "subscriber_id"))
    private List<Organizations> organizations;

    // Программы клиента
    @OneToMany(mappedBy = "subscriber", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ClientProgram> program;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;


    @OneToMany(mappedBy = "subscriber", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Subscription> subscriptions = new ArrayList<>();

}
