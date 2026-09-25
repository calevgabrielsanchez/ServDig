package mx.gob.imss.ctirss.correccion.catalogos.model;


import javax.persistence.*;
import mx.gob.imss.ctirss.correccion.base.model.AbstractCrtNroFolio;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;




/**
 * The persistent class for the CRT_NROFOLIO database table.
 * 
 */
@Entity
@Table(name="CRT_NROFOLIO")
@OnSearchLlavePrimaria(atributos="cvePkFolio")
public class CrtNroFolio extends AbstractCrtNroFolio {
	private static final long serialVersionUID = 1L;

	@Transient
	private String numFolio;

	public String getNumFolio() {
		return numFolio;
	}

	public void setNumFolio(String numFolio) {
		this.numFolio = numFolio;
	}
	
	
}