package vpsite.veseliyprikol.models.client.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vpsite.veseliyprikol.api.dadata_service.json.SuggestionResponse;
import vpsite.veseliyprikol.models.client.FederalTaxService;

@Mapper(componentModel = "spring")
public interface SuggestionToFNS  {
    @Mapping(target = "inn", source = "data.inn")
    @Mapping(target = "ogrn", source = "data.ogrn")
    @Mapping(target = "kpp", source = "data.kpp")
    @Mapping(target = "ogrn_date_unix", source = "data.ogrn_date")
    @Mapping(target = "type", source = "data.type")
    @Mapping(target = "okved", source = "data.okved")
    @Mapping(target = "tax_office", source = "data.address.data.tax_office")
    @Mapping(target = "nameOrganization", source = "data.name.full_with_opf")
    @Mapping(target = "short_name_organization", source = "data.name.short_with_opf")
    @Mapping(target = "name_unrestricted_value", source = "unrestricted_value")

    FederalTaxService toEntity(SuggestionResponse.Suggestion response);
}