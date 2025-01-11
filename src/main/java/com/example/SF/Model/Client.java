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
    private UUID client_id;

    @Column(name = "client_name", nullable = false)
    @JsonProperty("client_name")
    private String client_name;

    @Column(name = "client_email", nullable = false)
    @JsonProperty("client_email")
    private String client_email;

    @Column(name = "client_birthday", nullable = false)
    @JsonProperty("client_birthday")
    private LocalDate client_birthday;

    @Column(name = "client_gender", nullable = false)
    @JsonProperty("client_gender")
    private Character client_gender;

    @Column(name = "client_weight", nullable = false)
    @JsonProperty("client_weight")
    private Double client_weight;

    @Column(name = "client_password", nullable = false)
    @JsonProperty("client_password")
    private String client_password;

    @Column(name = "client_active", nullable = false)
    @JsonProperty("client_active")
    private Boolean client_active;
}
