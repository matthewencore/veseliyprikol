package vpsite.veseliyprikol.models.partner1c;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import vpsite.veseliyprikol.models.client.Client;
import vpsite.veseliyprikol.models.client.enums.TypeSubscription;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
public class Subscription {
    @Id
    @GeneratedValue
    private Long id;

    // Тип сопровождения
    @Enumerated(EnumType.ORDINAL)
    private TypeSubscription subscription;
    private int publicSubscriptionTypeNumber;

    private LocalDateTime buyDate;

    private LocalDate startDate;
    private LocalDate endDate;

    // Значение где стоит данные о том наш это договор или нет.
    private boolean isOurSubscription;

    // К какому клиенту будет связь
    @ManyToOne
    @JoinColumn(name = "sub_id")
    private Subscriber subscriber;

}
