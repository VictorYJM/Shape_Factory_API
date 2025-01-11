package com.example.SF.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "adm", schema = "sf")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Adm {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "adm_id", nullable = false)
    @JsonProperty("adm_id")
    private UUID adm_id;

    @Column(name = "adm_email", nullable = false)
    @JsonProperty("adm_email")
    private String adm_email;

    @Column(name = "adm_password", nullable = false)
    @JsonProperty("adm_password")
    private String adm_password;

    @Column(name = "adm_salary", nullable = false)
    @JsonProperty("adm_salary")
    private Double adm_salary;

    @Column(name = "adm_active", nullable = false)
    @JsonProperty("adm_active")
    private Boolean adm_active;
}
