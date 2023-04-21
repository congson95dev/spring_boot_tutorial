package com.example.tutorial.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.util.Calendar;

// @NoArgsConstructor used to automatic create an empty contstructor
// @AllArgsConstructor used to automatic create an full params constructor
// @Data used to automatic create get, set, hash, equals functions
// @Id used to define the field will be primary key of the table

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document("product")
public class Product {
    // in this mongo db, it don't using int or long for id as sql, it using string
    @Id
    private String id;
    @Field(name="name")
    @Indexed(unique = true)
    private String productName;
    @Field(name="year")
    private int productYear;
    @Field(name="price")
    private Double productPrice;
    @Field(name="url")
    private String productUrl;

    // calculated field (transient)
    @Transient
    private int age;
    public int getAge() {
        return Calendar.getInstance().get(Calendar.YEAR) - productYear;
    }
}
