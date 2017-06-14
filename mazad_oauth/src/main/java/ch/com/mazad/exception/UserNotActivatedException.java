package ch.com.mazad.exception;

import org.springframework.security.core.AuthenticationException;


public class UserNotActivatedException extends AuthenticationException
{

    public UserNotActivatedException(String message)
    {
        super(message);
    }

}
