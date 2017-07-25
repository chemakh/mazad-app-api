package ch.com.mazad.exception;

import org.springframework.security.core.AuthenticationException;


public class UserNotActivatedException extends AuthenticationException
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3531137619423327853L;

	public UserNotActivatedException(String message)
    {
        super(message);
    }

}
