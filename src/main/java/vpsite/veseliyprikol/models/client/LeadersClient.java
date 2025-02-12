package vpsite.veseliyprikol.models.client;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "leaders")
public class LeadersClient {
    @Id
    @GeneratedValue
    private Long id;

    private String name; // И
    private String surname; // Ф
    private String patronymic; // О

    private String fullname; // ФИО

    private String post; // Должность
    private LocalDate dateOfduty; // День заступления на должность

    private LocalDate birthOfDay; // День рождения

    private String number;
    @Email
    private String email;

    // Связь
    @OneToOne(mappedBy = "leadersClient")
    Client client;

}
