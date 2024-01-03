package org.when.salary.context.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Embeddable
public class Salary {
    private static final int SCALE = 2;
    public static final Salary ZERO = new Salary(BigDecimal.ZERO, Currency.RMB);
    @Column(name = "salary")
    private BigDecimal value;
    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    public Salary() {
    }

    public Salary(BigDecimal value, Currency currency) {
        this.value = value.setScale(SCALE, RoundingMode.HALF_UP);
        this.currency = currency;
    }

    public static Salary of(double value) {
        return new Salary(new BigDecimal(value), Currency.RMB);
    }

    public static Salary of(String value, Currency currency) {
        return new Salary(new BigDecimal(value), currency);
    }

    public static Salary of(double value, Currency currency) {
        return new Salary(new BigDecimal(value), currency);
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Salary add(Salary salary) {
        return new Salary(value.add(salary.value).setScale(SCALE), currency);
    }

    public Salary subtract(Salary salary) {
        return new Salary(value.subtract(salary.value).setScale(SCALE), currency);
    }

    public Salary multiply(double factor) {
        return new Salary(value.multiply(BigDecimal.valueOf(factor)).setScale(SCALE, RoundingMode.HALF_UP), currency);
    }

    public Salary divide(int workDaysEachMonth) {
        return new Salary(value.divide(BigDecimal.valueOf(workDaysEachMonth), SCALE, RoundingMode.HALF_UP), currency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Salary salary = (Salary) o;
        return currency == salary.currency && Objects.equals(value, salary.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, value);
    }

    @Override
    public String toString() {
        return currency + " " + value;
    }
}
