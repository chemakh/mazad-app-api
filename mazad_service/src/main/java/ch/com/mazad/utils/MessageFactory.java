package ch.com.mazad.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by Chemakh on 17/01/2017.
 */

@Component
public class MessageFactory {


    private final static Logger logger = LoggerFactory.getLogger(MessageFactory.class);

    private static List<Locale> LOCALES = Arrays.asList(new Locale("fr"),
            new Locale("en"),
            new Locale("it"),
            new Locale("de"));

    private static MessageSource messageSource;
    private static HttpServletRequest request;

    @Inject
    public MessageFactory(MessageSource messageSource, HttpServletRequest request) {
        MessageFactory.messageSource = messageSource;
        MessageFactory.request = request;
    }

    public static String getMessage(String s, Object[] objects, String lang) {

        try {
            return messageSource.getMessage(s, getObjectsLabel(objects), lang != null ? new Locale(lang) : getLocalFromHeader());
        } catch (NoSuchMessageException ex) {
            //logger.error(ex.getMessage());
            try {
                return messageSource.getMessage(s, objects, getLocalFromHeader());
            } catch (NoSuchMessageException e) {

                //logger.error(e.getMessage());
                return messageSource.getMessage("mazad.exception.thrown", null, lang != null ? new Locale(lang) : getLocalFromHeader());
            }
        }
    }

    public static String getMessage(String s, Object[] objects) {
        try {
            return messageSource.getMessage(s, getObjectsLabel(objects), getLocalFromHeader());
        } catch (NoSuchMessageException ex) {

            // logger.error(ex.getMessage());
            return messageSource.getMessage("mazad.exception.thrown", null, getLocalFromHeader());
        }

    }

    public static String getObjectLabel(Object object) {
        try {
            return messageSource.getMessage("mazad.label." + String.valueOf(object).toLowerCase(), null, getLocalFromHeader());
        } catch (NoSuchMessageException ex) {

            // logger.error(ex.getMessage());
            return String.valueOf(object);
        }
    }

    private static String[] getObjectsLabel(Object[] objects) {

        if (objects != null) {
            return Arrays.stream(objects).map(MessageFactory::getObjectLabel).toArray(String[]::new);

        } else
            return null;

    }

    public static Locale getLocalFromHeader() {

        try {
            if (request == null || StringUtils.isBlank(request.getHeader("Accept-Language"))) {
                return Locale.ENGLISH;
            }
        } catch (IllegalStateException e) {
            return Locale.ENGLISH;
        }

        List<Locale.LanguageRange> list = Locale.LanguageRange.parse(request.getHeader("Accept-Language"));
        Locale locale = Locale.lookup(list, LOCALES);
        if (locale == null)
            return Locale.ENGLISH;
        return locale;
    }
}
