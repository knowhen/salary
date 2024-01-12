package org.when.salary.context.domain.employee.salaried;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.when.salary.fixture.AttendanceFixture;
import org.when.salary.payroll.domain.salaried.*;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class AttendanceStatusServiceTest {
    private WorkHour workHour;
    private AttendanceStatusService service;
    private LeaveRepository leaveRepository;
    private ClockInRecordRepository clockInRecordRepository;

    @BeforeEach
    void setUp() {
        workHour = AttendanceFixture.getWordHour();
        leaveRepository = mock(LeaveRepository.class);
        clockInRecordRepository = mock(ClockInRecordRepository.class);
        service = new AttendanceStatusService(leaveRepository, clockInRecordRepository);
    }

    @Test
    void test_employee_is_absent() {
        String employeeId = "emp001";
        when(leaveRepository.isAbsent(employeeId, LocalDate.now())).thenReturn(true);
        when(clockInRecordRepository.findByIdAndDate(employeeId, LocalDate.now())).thenReturn(Optional.empty());

        AttendanceStatus attendanceStatus = service.getAttendanceStatus(employeeId, LocalDate.now(), workHour);

        verify(leaveRepository).isAbsent(employeeId, LocalDate.now());
        assertThat(attendanceStatus.isAbsent()).isTrue();
    }

    @Test
    void test_employee_is_normal_present() {
        String employeeId = "emp001";
        when(leaveRepository.isAbsent(employeeId, LocalDate.now())).thenReturn(false);
        ClockInRecord normalClockInRecord = AttendanceFixture.getNormalClockInRecord();
        when(clockInRecordRepository.findByIdAndDate(employeeId, LocalDate.now())).thenReturn(Optional.of(normalClockInRecord));

        AttendanceStatus attendanceStatus = service.getAttendanceStatus(employeeId, LocalDate.now(), workHour);

        verify(leaveRepository).isAbsent(employeeId, LocalDate.now());
        verify(clockInRecordRepository).findByIdAndDate(employeeId, LocalDate.now());
        assertThat(attendanceStatus.isNormal()).isTrue();
    }
}