package es.unex.shadoopMongo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class Datos {

	@NotEmpty
	private String coleccion;
	
	@NotNull
	private Integer annoDiaIni;
	
	@NotNull
	private Integer annoDiaFin;
	
	@NotEmpty
	private String datoAParsear;

	@NotNull
	private Integer totalDatosDias;
	
	private Datos() {
		
	}
	
	public Datos(String coleccion, Integer annoDiaIni , Integer annoDiaFin ,  String datoAParsear, Integer totalDatosDias) {
		this.coleccion = coleccion;
		this.annoDiaIni = annoDiaIni;
		this.annoDiaFin = annoDiaFin;		
		this.datoAParsear = datoAParsear;
		this.totalDatosDias = totalDatosDias;
	}

	public String getColeccion() {
		return coleccion;
	}

	public Integer getAnnoDiaIni() {
		return annoDiaIni;
	}
	
	public Integer getAnnoDiaFin() {
		return annoDiaFin;
	}


	public String getDatoAParsear() {
		return datoAParsear;
	}
	
	public Integer getTotalDatosDias() {
		return totalDatosDias;
	}	

}
