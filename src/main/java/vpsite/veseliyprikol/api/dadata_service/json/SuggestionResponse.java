package vpsite.veseliyprikol.api.dadata_service.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SuggestionResponse {
    @JsonProperty("suggestions")
    private List<Suggestion> suggestions;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    public static class Suggestion {
        @JsonProperty("value")
        private String value; // Наименование организации

        @JsonProperty("unrestricted_value")
        private String unrestricted_value; // Не обработанное наименование организации

        private Data data; // Следующий уровень вложенности


        @JsonIgnoreProperties(ignoreUnknown = true)
        @Getter
        public static class Data {
            private String citizenship; // Гражданство

            private String source;
            private String qc;
            private String hid;
            private String type;

            private String inn;
            private String kpp;
            private String ogrn;
            private String okpo;
            private String okato;
            private String oktmo;
            private String okogu;
            private String okfs;
            private String okved;

            private String ogrn_date;
            private String okved_type;
            private String employee_count;

            private String phones;
            private String emails;

            // Ссылки на объекты

            private Name name;
            private FIO fio; // ФИО
            private Management management; // ФИО
            private State state;
            private Address address;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        @Getter
        public static class Address {
            private String value; // Полностью адрес
            private DataAddress data;
        }


        @JsonIgnoreProperties(ignoreUnknown = true)
        @Getter
        public static class DataAddress {
            private String postal_code;
            private String country;
            private String country_iso_code;
            private String federal_district;
            private String region_fias_id;
            private String region_kladr_id;
            private String region_iso_code;
            private String region_with_type;
            private String region_type;
            private String region_type_full;
            private String region;
            private String city_with_type;
            private String city_type;
            private String city_type_full;
            private String city;

            private String tax_office;
            private String tax_office_legal;
            private String timezone;

        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        @Getter
        public static class Name {
            private String full_with_opf;
            private String short_with_opf;
            private String latin;
            private String full;

        }

        // Руководитель ИП
        @JsonIgnoreProperties(ignoreUnknown = true)
        @Getter
        public static class FIO {
            private String surname;
            private String name;
            private String patronymic;
            private String gender;
            private String source;
            private String qc;

        }

        // Директор для Юр лица
        @JsonIgnoreProperties(ignoreUnknown = true)
        @Getter
        public static class Management {
            private String name;
            private String post;
            private String start_date;
            private String disqualified;

        }


        @JsonIgnoreProperties(ignoreUnknown = true)
        @Getter
        public static class State {
            private String status;
            private String code;
            private String actuality_date;
            private String registration_date;
            private String liquidation_date;

        }

    }

}
