package org.when.salary.context.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DateRangeTest {
    @Test
    public void should_be_last_day_given_correct_year_and_month() {
        DateRange period = new DateRange(2019, 2);

        assertThat(period.getStartDate()).isEqualTo(LocalDate.of(2019, 2, 1));
        assertThat(period.getEndDate()).isEqualTo(LocalDate.of(2019, 2, 28));
    }

    @Test
    public void should_throw_exception_given_invalid_month_number() {
        assertThatThrownBy(() -> new DateRange(2019, 13)).hasMessage("Invalid month value: " + 13);
    }
}