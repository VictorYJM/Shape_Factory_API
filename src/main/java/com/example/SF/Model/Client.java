package com.example.SF.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "client", schema = "sf")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "client_id", nullable = false)
    @JsonProperty("client_id")
    private UUID clientId;

    @Column(name = "client_name", nullable = false)
    @JsonProperty("client_name")
    private String clientName;

    @Column(name = "client_email", nullable = false)
    @JsonProperty("client_email")
    private String clientEmail;

    @Column(name = "client_birthday", nullable = false)
    @JsonProperty("client_birthday")
    private LocalDate clientBirthday;

    @Column(name = "client_gender", nullable = false)
    @JsonProperty("client_gender")
    private Character clientGender;

    @Column(name = "client_weight", nullable = false)
    @JsonProperty("client_weight")
    private Double clientWeight;

    @Column(name = "client_password", nullable = false)
    @JsonProperty("client_password")
    private String clientPassword;

    @Column(name = "client_active", nullable = false)
    @JsonProperty("client_active")
    private Boolean clientActive;
}
