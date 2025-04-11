package exception;

public class ReservationException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReservationException(String message) {
        super(message);
    }

    public ReservationException(String message, Throwable cause) {
        super(message, cause);
    }
}
