package vpsite.veseliyprikol.api.dadata_service.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FederalTaxServiceResponse {
    List<SuggestionFNS> suggestions;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    public static class SuggestionFNS{
        private String value;
        private DataFNS data;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    public static class DataFNS{
        private String address;
    }
}
