package com.autobots.automanager.entitades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.Data;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

@SuppressWarnings("serial")
@Data
@Entity
public class Credencial extends RepresentationModel<Credencial> implements Serializable  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String nomeUsuario;

	@Column(nullable = false)
	private String senha;

	@Column(nullable = false)
	private Boolean inativo = false;
}