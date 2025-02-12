package vpsite.veseliyprikol.api.one_c_portal.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProgramAccessDto {
    private Long reg_number;
    private boolean hasAccess;

    private ProgramDTO program;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static public class ProgramDTO {
        private String uin;
        private String name;
        private String nick;
        private String configurationName;
        private String developerName;

        private boolean groupProgram;
        private boolean isBase;

    }
}
