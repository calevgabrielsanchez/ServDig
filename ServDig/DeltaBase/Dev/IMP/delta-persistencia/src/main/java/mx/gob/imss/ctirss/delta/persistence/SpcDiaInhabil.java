package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SPC_DIA_INHABIL")
public class SpcDiaInhabil implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Temporal(TemporalType.DATE)
	@Column(name="ID_FECHA")
	private Date idFecha;
	
	@Column(name="DES_FECHA_INHABIL")
	private String desFechaInhabil;
	
	public SpcDiaInhabil(){}

	public Date getIdFecha() {
		return idFecha;
	}

	public void setIdFecha(Date idFecha) {
		this.idFecha = idFecha;
	}

	public String getDesFechaInhabil() {
		return desFechaInhabil;
	}

	public void setDesFechaInhabil(String desFechaInhabil) {
		this.desFechaInhabil = desFechaInhabil;
	}
}
