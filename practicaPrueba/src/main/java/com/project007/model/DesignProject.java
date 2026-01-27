package com.project007.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "design_project")
@PrimaryKeyJoinColumn(name = "id")
@NoArgsConstructor
public class DesignProject extends Project {
    // Campos específicos de DesignProject podrían ir aquí
}
