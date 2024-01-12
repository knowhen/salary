package org.when.salary.payroll.domain.exception;

public class InvalidEmailException extends RuntimeException {
    private final String address;

    public InvalidEmailException(String address, String message) {
        super(message);
        this.address = address;
    }

//    public InvalidEmailException(String address, Throwable cause){
//        super(cause);
//        this.address = address;
//    }
//
//    public InvalidEmailException(String address, String message, Throwable cause){
//        super(message, cause);
//        this.address = address;
//    }

    /**
     * @return the address that is invalid
     */
    public String getAddress() {
        return address;
    }

    @Override
    public String getMessage() {
        return "Invalid address '" + getAddress() + "': " + super.getMessage();
    }
}
