package vpsite.veseliyprikol.api.one_c_portal.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubscribeOtr {

    private String status;
    private int code;
    private String description;
    private String subscriberCode;

    private List<ProgramInfoList> programInfoList;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProgramInfoList {
        private String uin;
        private String name;
        private String beginDate;
        private String endDate;

        private List<IndustrySubscriptionInfoList> industrySubscriptionInfoList = new ArrayList<>();
    }


    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class IndustrySubscriptionInfoList {
        private String nomenclatureName;
        private int serialNumber;
        private IndustrySubscriptionTypeInfo industrySubscriptionTypeInfo;

    }


    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class IndustrySubscriptionTypeInfo {
       private String description;
       private String uin;
    }
}
