package ch.com.mazad.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by Chemakh on 06/12/2016.
 */
public class UserDeletedException extends AuthenticationException
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5314458450039770786L;

	public UserDeletedException(String message)
    {
        super(message);
    }

}
