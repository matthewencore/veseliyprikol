package vpsite.veseliyprikol.api.one_c_portal.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubscribeProf {
    private String status;
    private String code;

    private String description;
    private String subscriberCode;

    private List<ItsContractInfo> itsContractInfo = new ArrayList<>();


    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ItsContractInfo {
        private String description;
        private String startDate;
        private String endDate;

        private ItsContractType itsContractType;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ItsContractType {
       private String uin;
       private String name;
       private String nameForUser;
       private Integer publicSubscriptionTypeNumber;
    }
}
