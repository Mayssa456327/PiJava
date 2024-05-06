package org.example.models;

import org.example.utils.myDataBase;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.sql.*;

public class SMSsender {

    public SMSsender() {
        Connection connection = myDataBase.getInstance().getConnection();
    }

    // twilio.com/console
    public static final String ACCOUNT_SID = "ACcf71deeb2721ffce0c88eeea53c5fe88";
    public static final String AUTH_TOKEN = "98d4355f4daf5e9ee1c8f4f121bea4ae";

    public static void main(String[] args) {

    }

    public static void sendSMS(String clientPhoneNumber, String s) {

        String accountSid = "ACcf71deeb2721ffce0c88eeea53c5fe88";
        String authToken = "98d4355f4daf5e9ee1c8f4f121bea4ae";

        try {
            Twilio.init(accountSid, authToken);
            Message message = Message.creator(
                    new PhoneNumber("+216" + clientPhoneNumber),
                    new PhoneNumber("+19519340166"),
                    "Votre compte est vérifié"
            ).create();

            System.out.println("SID du message : " + message.getSid());
        } catch (Exception ex) {
            System.out.println("Erreur : " + ex.getMessage());
        }
    }
}
