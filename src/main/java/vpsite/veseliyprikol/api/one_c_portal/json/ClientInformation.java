package vpsite.veseliyprikol.api.one_c_portal.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientInformation {
    List<Subscribers> subscribers;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Subscribers {
        private String code;
        private String name;

        private List<String> subjects;
        private List<Long> regNumbers;

        private List<Organizations> organizations;

    }
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Organizations {
        private String name;
        private String inn;
        private String kpp;
    }
}
