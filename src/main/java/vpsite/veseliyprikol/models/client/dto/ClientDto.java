package vpsite.veseliyprikol.models.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link vpsite.veseliyprikol.models.client.Client}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientDto implements Serializable {
    Long id;
    boolean isArchiveBlock;
    String reasonArchiveBlock;
    String number;
    @Email
    String email;
    String slug;
    String userBy;
    LocalDate dateOfCreated;
    String lastSeen;
    Long federalTaxServiceId;
    LocalDate federalTaxServiceLiquidation_date;
    LocalDate federalTaxServiceIsLiquidation;
    String federalTaxServiceType;
    String federalTaxServiceName_unrestricted_value;
    String federalTaxServiceAddress_tax_office;
    String federalTaxServiceName_tax;
    String federalTaxServiceNameOrganization;
    String federalTaxServiceShort_name_organization;
    String federalTaxServiceTax_office;
    String federalTaxServiceInn;
    String federalTaxServiceKpp;
    String federalTaxServiceOgrn;
    String federalTaxServiceOgrn_date_unix;
    String federalTaxServiceOkpo;
    String federalTaxServiceOkato;
    String federalTaxServiceOktmo;
    String federalTaxServiceOkfs;
    String federalTaxServiceOkved;
    boolean federalTaxServiceIsUL;
    LocalDate federalTaxServiceDateRegOrg;
    Long placeClientId;
    String placeClientUnrestricted_value;
    String placeClientValue;
    String placeClientPostal_code;
    String placeClientCountry;
    String placeClientRegion_fias_id;
    String placeClientRegion_kladr_id;
    String placeClientRegion_with_type;
    String placeClientRegion_type;
    String placeClientRegion_type_full;
    String placeClientRegion;
    String placeClientCity_fias_id;
    String placeClientCity_kladr_id;
    String placeClientCity_with_type;
    String placeClientCity_type;
    String placeClientCity_type_full;
    String placeClientCity;
    String placeClientStreet_with_type;
    String placeClientStreet_type;
    String placeClientStreet_type_full;
    String placeClientStreet;
    String placeClientFlat_type;
    String placeClientFlat_type_full;
    String placeClientFlat;
    String placeClientTimezone;
    Long leadersClientId;
    String leadersClientName;
    String leadersClientSurname;
    String leadersClientPatronymic;
    String leadersClientFullname;
    String leadersClientPost;
    LocalDate leadersClientDateOfduty;
    LocalDate leadersClientBirthOfDay;
    String leadersClientNumber;
    String leadersClientEmail;
    List<OkvedsDto> okveds;

    /**
     * DTO for {@link vpsite.veseliyprikol.models.client.Okveds}
     */
    @Value
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OkvedsDto implements Serializable {
        Long id;
        String code;
    }
}