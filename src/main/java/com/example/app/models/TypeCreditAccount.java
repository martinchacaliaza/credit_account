package com.example.app.models;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection ="tipoCuenta")
public class TypeCreditAccount {

	
	@NotEmpty
	private String idTipo;
	@NotEmpty
	private String descripcion;
	
}
