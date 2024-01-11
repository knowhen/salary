package org.when.salary.context.domain.employee.salaried;

import java.time.LocalDate;
import java.util.Optional;

public class AttendanceStatusService {

    private final LeaveRepository leaveRepository;
    private final ClockInRecordRepository clockInRecordRepository;

    public AttendanceStatusService(LeaveRepository leaveRepository, ClockInRecordRepository clockInRecordRepository) {
        this.leaveRepository = leaveRepository;
        this.clockInRecordRepository = clockInRecordRepository;
    }

    public AttendanceStatus getAttendanceStatus(String employeeId, LocalDate date, WorkHour workHour) {
        if (leaveRepository.isAbsent(employeeId, date)) {
            return AttendanceStatus.ABSENT;
        }
        Optional<ClockInRecord> optionalRecord = clockInRecordRepository.findByIdAndDate(employeeId, date);
        if (optionalRecord.isPresent()) {
            return optionalRecord.get().getStatus(workHour);
        }
        return AttendanceStatus.ABSENT;
    }
}
