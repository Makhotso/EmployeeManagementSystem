package com.codeWithProject.employee.controller;

import com.codeWithProject.employee.entity.Attendance;
import com.codeWithProject.employee.entity.Employee;
import com.codeWithProject.employee.repository.AttendanceRepository;
import com.codeWithProject.employee.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceRepository attendanceRepo;
    private final EmployeeRepository employeeRepo;

    public AttendanceController(AttendanceRepository attendanceRepo, EmployeeRepository employeeRepo) {
        this.attendanceRepo = attendanceRepo;
        this.employeeRepo = employeeRepo;
    }

    @PostMapping("/{employeeId}")
    public Attendance markAttendance(@PathVariable Long employeeId, @RequestParam boolean present) {
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Attendance attendance = new Attendance(LocalDate.now(), present, employee);
        return attendanceRepo.save(attendance);
    }

    @GetMapping("/{employeeId}")
    public List<Attendance> getAttendance(@PathVariable Long employeeId) {
        return attendanceRepo.findByEmployeeId(employeeId);
    }

    @GetMapping("/all")
    public List<Attendance> getAllAttendance() {
        return attendanceRepo.findAll();
    }
}
