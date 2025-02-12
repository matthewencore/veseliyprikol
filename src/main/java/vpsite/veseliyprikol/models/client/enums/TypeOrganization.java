package vpsite.veseliyprikol.models.client.enums;

public enum TypeOrganization {
    TYPE_INDIVIDUAL("ИП"),
    TYPE_ORGANIZATION("ООО");

    TypeOrganization(String choiceStr) {
        this.choiceStr = choiceStr;
    }

    final String choiceStr;

    public String getChoiceStr() {
        return choiceStr;
    }
    
}
