package mx.imss.ctirss.model;

import java.io.Serializable;
import javax.persistence.*;

import mx.imss.ctirss.base.model.AbstractDltDenuncia;
import mx.imss.ctirss.catalogos.model.DlcMotivodenuncia;
import mx.imss.ctirss.framework.base.model.AbstractModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;


/**
 * The persistent class for the DLT_DENUNCIA database table.
 * 
 */
@Entity
@Table(name="DLT_DENUNCIA")
public class DltDenuncia extends AbstractDltDenuncia implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Transient
	private DltPersona persona;
	
	@Transient
	private DltDatospatron patron;
	
	@Transient
	private ArrayList motivosDenuncia;;
	
	public DltPersona getPersona() {
		return persona;
	}

	public void setPersona(DltPersona persona) {
		this.persona = persona;
	}

	public DltDatospatron getPatron() {
		return patron;
	}

	public void setPatron(DltDatospatron patron) {
		this.patron = patron;
	}

	public ArrayList getMotivosDenuncia() {
		return motivosDenuncia;
	}

	public void setMotivosDenuncia(ArrayList motivosDenuncia) {
		this.motivosDenuncia = motivosDenuncia;
	}
	
	

}