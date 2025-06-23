package vpsite.veseliyprikol.models.client.mapper;

import org.mapstruct.*;
import vpsite.veseliyprikol.models.client.Client;
import vpsite.veseliyprikol.models.client.dto.ClientDto;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClientMapper {
    @Mapping(source = "leadersClientEmail", target = "leadersClient.email")
    @Mapping(source = "leadersClientNumber", target = "leadersClient.number")
    @Mapping(source = "leadersClientBirthOfDay", target = "leadersClient.birthOfDay")
    @Mapping(source = "leadersClientDateOfduty", target = "leadersClient.dateOfduty")
    @Mapping(source = "leadersClientPost", target = "leadersClient.post")
    @Mapping(source = "leadersClientFullname", target = "leadersClient.fullname")
    @Mapping(source = "leadersClientPatronymic", target = "leadersClient.patronymic")
    @Mapping(source = "leadersClientSurname", target = "leadersClient.surname")
    @Mapping(source = "leadersClientName", target = "leadersClient.name")
    @Mapping(source = "leadersClientId", target = "leadersClient.id")
    @Mapping(source = "placeClientTimezone", target = "placeClient.timezone")
    @Mapping(source = "placeClientFlat", target = "placeClient.flat")
    @Mapping(source = "placeClientFlat_type_full", target = "placeClient.flat_type_full")
    @Mapping(source = "placeClientFlat_type", target = "placeClient.flat_type")
    @Mapping(source = "placeClientStreet", target = "placeClient.street")
    @Mapping(source = "placeClientStreet_type_full", target = "placeClient.street_type_full")
    @Mapping(source = "placeClientStreet_type", target = "placeClient.street_type")
    @Mapping(source = "placeClientStreet_with_type", target = "placeClient.street_with_type")
    @Mapping(source = "placeClientCity", target = "placeClient.city")
    @Mapping(source = "placeClientCity_type_full", target = "placeClient.city_type_full")
    @Mapping(source = "placeClientCity_type", target = "placeClient.city_type")
    @Mapping(source = "placeClientCity_with_type", target = "placeClient.city_with_type")
    @Mapping(source = "placeClientCity_kladr_id", target = "placeClient.city_kladr_id")
    @Mapping(source = "placeClientCity_fias_id", target = "placeClient.city_fias_id")
    @Mapping(source = "placeClientRegion", target = "placeClient.region")
    @Mapping(source = "placeClientRegion_type_full", target = "placeClient.region_type_full")
    @Mapping(source = "placeClientRegion_type", target = "placeClient.region_type")
    @Mapping(source = "placeClientRegion_with_type", target = "placeClient.region_with_type")
    @Mapping(source = "placeClientRegion_kladr_id", target = "placeClient.region_kladr_id")
    @Mapping(source = "placeClientRegion_fias_id", target = "placeClient.region_fias_id")
    @Mapping(source = "placeClientCountry", target = "placeClient.country")
    @Mapping(source = "placeClientPostal_code", target = "placeClient.postal_code")
    @Mapping(source = "placeClientValue", target = "placeClient.value")
    @Mapping(source = "placeClientUnrestricted_value", target = "placeClient.unrestricted_value")
    @Mapping(source = "placeClientId", target = "placeClient.id")
    @Mapping(source = "federalTaxServiceDateRegOrg", target = "federalTaxService.dateRegOrg")
    @Mapping(source = "federalTaxServiceIsUL", target = "federalTaxService.ul")
    @Mapping(source = "federalTaxServiceOkved", target = "federalTaxService.okved")
    @Mapping(source = "federalTaxServiceOkfs", target = "federalTaxService.okfs")
    @Mapping(source = "federalTaxServiceOktmo", target = "federalTaxService.oktmo")
    @Mapping(source = "federalTaxServiceOkato", target = "federalTaxService.okato")
    @Mapping(source = "federalTaxServiceOkpo", target = "federalTaxService.okpo")
    @Mapping(source = "federalTaxServiceOgrn_date_unix", target = "federalTaxService.ogrn_date_unix")
    @Mapping(source = "federalTaxServiceOgrn", target = "federalTaxService.ogrn")
    @Mapping(source = "federalTaxServiceKpp", target = "federalTaxService.kpp")
    @Mapping(source = "federalTaxServiceInn", target = "federalTaxService.inn")
    @Mapping(source = "federalTaxServiceTax_office", target = "federalTaxService.tax_office")
    @Mapping(source = "federalTaxServiceShort_name_organization", target = "federalTaxService.short_name_organization")
    @Mapping(source = "federalTaxServiceNameOrganization", target = "federalTaxService.nameOrganization")
    @Mapping(source = "federalTaxServiceName_tax", target = "federalTaxService.name_tax")
    @Mapping(source = "federalTaxServiceAddress_tax_office", target = "federalTaxService.address_tax_office")
    @Mapping(source = "federalTaxServiceName_unrestricted_value", target = "federalTaxService.name_unrestricted_value")
    @Mapping(source = "federalTaxServiceType", target = "federalTaxService.type")
    @Mapping(source = "federalTaxServiceIsLiquidation", target = "federalTaxService.isLiquidation")
    @Mapping(source = "federalTaxServiceLiquidation_date", target = "federalTaxService.liquidation_date")
    @Mapping(source = "federalTaxServiceId", target = "federalTaxService.id")
    Client toEntity(ClientDto clientDto);

    @AfterMapping
    default void linkOkveds(@MappingTarget Client client) {
        client.getOkveds().forEach(okved -> okved.setClient(client));
    }

    @InheritInverseConfiguration(name = "toEntity")
    ClientDto toDto(Client client);

    @InheritInverseConfiguration(name = "toEntity")
    List<ClientDto> toDtoList(List<Client> clients);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Client partialUpdate(ClientDto clientDto, @MappingTarget Client client);
}