package ru.kazakova.snacky.model.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @Column(name="brand", nullable=false)
    private String brand;
    @Column(name="category", nullable=false)
    private String category;
    @Column(name="name", nullable=false)
    private String name;
    @Column(name="flavour")
    private String flavour;
    @Column(name="calories_per_100_gram", nullable=false)
    private Double caloriesPer100Gram;
    @Column(name="weight_gram", nullable=false)
    private Double weight;
}
