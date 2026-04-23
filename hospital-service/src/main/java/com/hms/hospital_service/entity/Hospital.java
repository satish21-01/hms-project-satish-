package com.hms.hospital_service.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "hospitals")
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address;

    private String phone;

    // ===== Constructors =====

    public Hospital() {
    }

    public Hospital(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    // ===== Getters & Setters =====

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Department> departments;

    @OneToMany(mappedBy = "hospital")
    @JsonIgnore
    private List<Doctor> doctors;

}
