package ch.com.mazad.exception;


public class MazadException extends Exception
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6624200413467509795L;

	private String message;

    private Throwable cause;


    public MazadException()
    {
    }


    public MazadException(Throwable cause)
    {
        super(cause);
        this.cause = cause;
    }

    public MazadException(String message)
    {
        super(message);
        this.message = message;
    }

    public MazadException(String message, Throwable cause)
    {
        super(message, cause);
        this.message = message;
        this.cause = cause;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    @Override
    public Throwable getCause()
    {
        return cause;
    }

    public void setCause(Throwable cause)
    {
        this.cause = cause;
    }
}
