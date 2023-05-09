public class InvalidOptionException extends Exception {
    public InvalidOptionException () {
        super();
    }

    public InvalidOptionException (String message) {
        super(message);
        System.err.println("Sorry, " + message + ".  Try again.");
    }
}
