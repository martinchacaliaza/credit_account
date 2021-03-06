package com.example.app.dto;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "TipoCliente")
public class dtoTypeClient {

	
	@NotEmpty
	private String idTipo;
	@NotEmpty
	private String descripcion;
}
