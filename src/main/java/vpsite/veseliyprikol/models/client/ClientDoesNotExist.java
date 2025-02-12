package vpsite.veseliyprikol.models.client;

public class ClientDoesNotExist extends RuntimeException {
    public ClientDoesNotExist(String message) {
        super(message);
    }
}
