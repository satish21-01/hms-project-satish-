package com.hms.hospital_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String specialization;

    private String phone;

    // Many doctors belong to one hospital
    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    @JsonIgnore
    private Hospital hospital;

    // Many doctors belong to one department
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    @JsonIgnore
    private Department department;

    // ===== Constructors =====
    public Doctor() {}

    public Doctor(String name, String specialization, String phone,
                  Hospital hospital, Department department) {
        this.name = name;
        this.specialization = specialization;
        this.phone = phone;
        this.hospital = hospital;
        this.department = department;
    }

    // ===== Getters & Setters =====
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getSpecialization() { return specialization; }
    public String getPhone() { return phone; }
    public Hospital getHospital() { return hospital; }
    public Department getDepartment() { return department; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setHospital(Hospital hospital) { this.hospital = hospital; }
    public void setDepartment(Department department) { this.department = department; }
}
