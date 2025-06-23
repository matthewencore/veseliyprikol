package vpsite.veseliyprikol.models.client.enums;


public enum TypeSubscription {
    SUBSCRIPTION_PROF("1С:ИТС уровня ПРОФ"),
    SUBSCRIPTION_OTRASL("1С:ИТС уровня ПРОФ");

    private String ch;

    TypeSubscription(String ch) {
        this.ch = ch;
    }


    public String getCh() {
        return ch;
    }

    public TypeSubscription setCh(String ch) {
        this.ch = ch;
        return this;
    }
}
