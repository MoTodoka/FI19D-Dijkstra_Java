package de.epshl.e211.tisch1;

public class MalformedGraphException extends Exception {

    private final String reason;

    public MalformedGraphException(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
