package com.progavanzada;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "student")
@Getter
@Setter
@Builder
@ToString
public class Student {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    Long id;

    String name;
}
