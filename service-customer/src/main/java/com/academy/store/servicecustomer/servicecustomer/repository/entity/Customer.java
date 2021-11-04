package com.academy.store.servicecustomer.servicecustomer.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(name = "tbl_customers")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Document number is empty")
    @Size(min = 8, max = 8, message = "Document number size must be 8")
    @Column(name = "number_id", unique = true, length = 8, nullable = false)
    private String numberID;

    @NotEmpty(message = "The firstname is empty")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotEmpty(message = "The lastname is empty")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotEmpty(message = "The email is empty")
    @Email(message = "The email address is wrong")
    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "photo_url")
    private String photoUrl;

    @NotNull(message = "The region is empty")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Region region;

    private String state;
}
