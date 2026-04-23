package com.hms.hospital_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // Many departments belong to one hospital
    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    @JsonIgnore

    private Hospital hospital;

    // ===== Constructors =====

    public Department() {
    }

    public Department(String name, Hospital hospital) {
        this.name = name;
        this.hospital = hospital;
    }

    // ===== Getters & Setters =====

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }
    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private List<Doctor> doctors;
}
