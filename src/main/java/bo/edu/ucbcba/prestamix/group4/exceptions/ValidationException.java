package bo.edu.ucbcba.prestamix.group4.exceptions;

public class ValidationException extends RuntimeException
{
    public ValidationException(String message)
    {
        super("Validation error: " + message);
    }
}
