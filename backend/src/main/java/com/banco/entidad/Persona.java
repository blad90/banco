package com.banco.entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "persona")
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona {
}
