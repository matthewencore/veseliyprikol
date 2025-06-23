package vpsite.veseliyprikol.models.client;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "federal_tax_service")
public class FederalTaxService {
    @Id
    @GeneratedValue
    private Long id;

    // Ликвидация предприятия
    private LocalDate liquidation_date;
    private LocalDate isLiquidation;

    private String type;

    private String name_unrestricted_value; // Межрайонная инспекция ФНС России № 1 по Запорожской области
    private String address_tax_office; // Запорожская область, м.р-н Мелитопольский, г.п. Мелитопольское, г. Мелитополь, ул. Беляева, д. 57А
    private String name_tax; // Межрайонная инспекция ФНС России № 1 по Запорожской области

    // Имя организации
    private String nameOrganization; // Общество с ограниченной ответственностью "Шапочка"
    private String short_name_organization; // ООО "Шапочка"

    // Регистрационные данные
    private String tax_office; // 9001

    private String inn;
    private String kpp;

    private String ogrn;
    private String ogrn_date_unix;

    private String okpo;
    private String okato;
    private String oktmo;
    private String okfs;

    private String okved;


    // Является ли юридическим лицом
    private boolean ul;
    private LocalDate dateRegOrg;

    // Связь побочная
    @OneToOne(mappedBy = "federalTaxService")
    private Client clientModels;
}
