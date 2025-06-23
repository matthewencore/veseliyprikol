package vpsite.veseliyprikol.api.one_c_portal;

public enum TypeService1CPortal {
    SPARK_RISKS("SPARK_RISKS"),
    DOCUMENT_RECOGNITION("DOCUMENT_RECOGNITION"),
    ESS("ESS"),
    NOMENCLATURE("NOMENCLATURE"),
    COUNTERAGENT("COUNTERAGENT"),
    LINK("LINK"),
    CLOUD_BACKUP("CLOUD_BACKUP"),
    SIGN("SIGN"),
    REPORTING("REPORTING"),
    MAG1C("MAG1C");

    private String ch;

    TypeService1CPortal(String ch) {
        this.ch = ch;
    }

    public String getCh() {
        return ch;
    }

    public TypeService1CPortal setCh(String ch) {
        this.ch = ch;
        return this;
    }
}
