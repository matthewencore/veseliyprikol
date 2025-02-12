package vpsite.veseliyprikol.models.partner1c;

import jakarta.persistence.*;
import lombok.*;
import vpsite.veseliyprikol.models.partner1c.embeddable.Organizations;

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

    @ElementCollection
    @CollectionTable(name = "subjects", joinColumns = @JoinColumn(name = "subscriber_id"))
    private List<String> subjects;

    @ElementCollection
    @CollectionTable(name = "regnums", joinColumns = @JoinColumn(name = "subscriber_id"))
    private List<Long> reg_nums;

    @ElementCollection
    @CollectionTable(name = "organizations", joinColumns = @JoinColumn(name = "subscriber_id"))
    private List<Organizations> organizations;



}
