package org.when.salary.context.domain.employee.salaried;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.when.salary.fixture.AttendanceFixture;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ClockInRecordTest {
    private WorkHour workHour;

    @BeforeEach
    void setUp() {
        workHour = AttendanceFixture.getWordHour();
    }

    @Test
    void normal_status_test() {

        ClockInRecord clockInRecord = AttendanceFixture.getNormalClockInRecord();

        AttendanceStatus attendanceStatus = clockInRecord.getStatus(workHour);

        assertThat(attendanceStatus.isNormal()).isTrue();
    }

    @Test
    void late_status_test() {

        ClockInRecord clockInRecord = AttendanceFixture.getClockInRecord(LocalDate.now().atTime(LocalTime.of(9, 30)), LocalDate.now().atTime(LocalTime.of(18, 0)));

        AttendanceStatus attendanceStatus = clockInRecord.getStatus(workHour);

        assertThat(attendanceStatus.isLate()).isTrue();
    }


}