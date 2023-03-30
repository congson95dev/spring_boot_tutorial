package com.example.tutorial.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Calendar;

// @NoArgsConstructor used to automatic create an empty contstructor
// @AllArgsConstructor used to automatic create an full params constructor
// @Data used to automatic create get, set, hash, equals functions
// @Entity used so spring boot know this is an entity and add it to db
// @Table used to define name of the table
// @Id used to define the field will be primary key of the table
// @GeneratedValue(strategy = GenerationType.AUTO) used to set the field to auto increment

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="tblProduct")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @Column(nullable = false, unique = true, length = 255)
    private String productName;
    private int productYear;
    private Double productPrice;
    private String productUrl;

    // calculated field (transient)
    @Transient
    private int age;
    public int getAge() {
        return Calendar.getInstance().get(Calendar.YEAR) - productYear;
    }
}
