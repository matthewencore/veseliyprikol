package vpsite.veseliyprikol.models.client;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "places")
public class PlaceClient {
    @Id
    @GeneratedValue
    private Long id;

    private String unrestricted_value; // "117312, г Москва, Академический р-н, ул Вавилова, д 19"
    private String value;

    private String postal_code;
    private String country;

    private String region_fias_id; // 7bac30d2-0e95-499e-a34a-7351a8f13833
    private String region_kladr_id; // 9000000000000
    private String region_with_type; // Запорожская обл
    private String region_type; // обл
    private String region_type_full; // область
    private String region; // Запорожская

    private String city_fias_id; // 9d010012-26e0-4e04-b4f5-092c97a26d81
    private String city_kladr_id; // 9000000200000
    private String city_with_type; // г Мелитополь
    private String city_type; // г
    private String city_type_full; // город
    private String city; // Мелитополь

    private String street_with_type; // "пр-кт 50-летия Победы"
    private String street_type; // "пр-кт"
    private String street_type_full; // "проспект"
    private String street; // "50-летия Победы"

    private String flat_type; // кв
    private String flat_type_full; // квартира
    private String flat; // 30

    private String timezone; // UTC+3

    // Связь
    @OneToOne(mappedBy = "placeClient")
    private Client client;

}
