package ch.com.mazad.utils;

import org.apache.commons.lang.RandomStringUtils;


public final class TokenUtil
{

    private static final int DEF_COUNT_REFERENCE = 20;

    private static final int DEF_COUNT_MIN = 4;

    private TokenUtil()
    {
    }


    public static String generateReference()
    {
        return RandomStringUtils.randomAlphanumeric(DEF_COUNT_REFERENCE);
    }

    public static String generateCode()
    {
        return RandomStringUtils.randomNumeric(DEF_COUNT_MIN);
    }

}
