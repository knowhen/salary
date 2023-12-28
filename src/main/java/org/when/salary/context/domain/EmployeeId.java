package org.when.salary.context.domain;

import org.when.salary.core.domain.Identity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Embeddable
public class EmployeeId implements Identity<String>, Serializable {
    @Column(name = "id")
    private String value;

    private static Random sequence;

    static {
        sequence = new Random();
    }

    public EmployeeId() {
    }

    public EmployeeId(String value) {
        this.value = value;
    }

    public static EmployeeId of(String value) {
        return new EmployeeId(value);
    }

    public static Identity<String> next() {
        return new EmployeeId(String.format("%s%s%03d", prefix(), date(), sequence()));
    }

    private static String prefix() {
        return "EMP";
    }

    private static String date() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    private static int sequence() {
        return sequence.nextInt(999);
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeId)) return false;
        EmployeeId that = (EmployeeId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
