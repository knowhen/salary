package org.when.salary.context.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Money {
    private static final int SCALE = 2;
    public static final Money ZERO = new Money(BigDecimal.ZERO, Currency.RMB);
    private final BigDecimal value;
    private final Currency currency;

    public Money(BigDecimal value, Currency currency) {
        this.value = value.setScale(SCALE);
        this.currency = currency;
    }

    public static Money of(String value, Currency currency) {
        return new Money(new BigDecimal(value), currency);
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Money add(Money money) {
        return new Money(value.add(money.value).setScale(SCALE), currency);
    }

    public Money subtract(Money money) {
        return new Money(value.subtract(money.value).setScale(SCALE), currency);
    }

    public Money multiply(double factor) {
        return new Money(value.multiply(BigDecimal.valueOf(factor)).setScale(SCALE, RoundingMode.HALF_UP), currency);
    }

    public Money divide(int workDaysEachMonth) {
        return new Money(value.divide(BigDecimal.valueOf(workDaysEachMonth), SCALE, RoundingMode.HALF_UP), currency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Money money = (Money) o;
        return currency == money.currency && Objects.equals(value, money.value);
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
