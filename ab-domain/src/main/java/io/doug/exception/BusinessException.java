package io.doug.exception;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 8220508217616175588L;

    private String message;


    /**
     * Create an TiiBaseException based on defaults
     *
     * message:=This constructor uses the thrown exceptions message for its
     * value explicitDismissal:=false notificationType:=NotificationType.I
     *
     * @param throwable
     *            : thrown exception object
     */
    public BusinessException(Throwable throwable) {
        super(throwable);
        this.message = throwable.getMessage();
    }

    /**
     * Create an TiiBaseException based on a custom message and passes the
     * flow of control through the thrown exceptions constructor
     *
     * explicitDismissal:=false notificationType:=NotificationType.I
     *
     * @param message
     *            : message for the TiiBaseException
     * @param throwable
     *            : thrown exception object value
     */
    public BusinessException(String message, Throwable throwable) {
        super(message, throwable);
        this.message = message;
    }

    /**
     * Creates an TiiBaseException and specifies the following values as
     * defaults.
     *
     * explicitDismissal:=false notificationType:=NotificationType.I
     *
     * @param message
     *            : message for the TiiBaseException
     */
    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * Creates an TiiBaseException and specifies the following values as
     * defaults.
     *
     * notificationType:=NotificationType.I
     *
     * @param message
     *            : message for the TiiBaseException
     * @param explicitDismissal
     *            : True requires the user to explicitly dismiss the
     *            notification, false otherwise.
     */
    public BusinessException(String message, boolean explicitDismissal) {
        super(message);
        this.message = message;
    }


    public BusinessException(Exception e, String s) {
        super(e);
        this.message = s;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
