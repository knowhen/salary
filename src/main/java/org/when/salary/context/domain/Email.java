package org.when.salary.context.domain;

import org.when.salary.context.domain.exception.InvalidEmailException;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Email {
    private static final String DOMAIN_STRING = "@";
    @Column(name = "email")
    private String value;

    public Email() {
    }

    public Email(String value) {
        validate(value);
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static void validate(String address) {
        if (null == address || address.isEmpty()) {
            throw new InvalidEmailException(address, "Expected non-null address");
        }
        int sepIndex = address.indexOf(DOMAIN_STRING);
        // The domain separator may be the first char, but must not be the last.
        if (sepIndex < 0) {
            throw new InvalidEmailException(address, "Missing domain prefix: " + DOMAIN_STRING);
        } else if (sepIndex >= (address.length() - 1)) {
            throw new InvalidEmailException(address, "Missing domain");
        } else if (sepIndex != address.lastIndexOf(DOMAIN_STRING)) {
            throw new InvalidEmailException(address, "Multiple domain prefixes: " + DOMAIN_STRING);
        }
    }
}
