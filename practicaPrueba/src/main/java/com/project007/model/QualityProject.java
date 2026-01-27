package com.project007.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quality_project")
@PrimaryKeyJoinColumn(name = "id")
@NoArgsConstructor
public class QualityProject extends Project {
    // Campos específicos de QualityProject podrían ir aquí
}
