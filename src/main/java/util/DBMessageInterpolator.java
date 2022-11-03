package util;

import jakarta.validation.MessageInterpolator;

import java.util.Locale;

public class DBMessageInterpolator implements MessageInterpolator {


    @Override
    public String interpolate(String messageTemplate, Context context) {
        return interpolate(messageTemplate, context, new Locale("pl"));
    }

    @Override
    public String interpolate(String messageTemplate, Context context, Locale locale) {
        if (!(messageTemplate.contains("{") && messageTemplate.contains("}"))) {
            return messageTemplate;
        }
        return "Message from data base";
    }

}

