package vpsite.veseliyprikol.models.partner1c;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Program {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String nick;
    private String configurationName;
    private String developerName;

    private boolean isBase;

    @OneToMany(mappedBy = "program", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<ClientProgram> clientPrograms = new ArrayList<>();

}
