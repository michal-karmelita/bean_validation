package payloads;

import jakarta.validation.Payload;

public class NotConfirmedEmailHandler implements Payload {

    public void handleSendingEmail() {
        System.out.println("sending email");
    }

}
