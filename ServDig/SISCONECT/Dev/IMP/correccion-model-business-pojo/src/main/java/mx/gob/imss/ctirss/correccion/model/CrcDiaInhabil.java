package mx.gob.imss.ctirss.correccion.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@Entity
@Table(name="CRC_DIAINHABIL")
public class CrcDiaInhabil extends AbstractModel {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ID_DIAINHABIL")
	private Long idDiaInhabil;
	
	@Column(name="FEC_FECHAINHABIL")
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@Column(name="DESCRIPCION")
	private String descripcion;

	public Long getIdDiaInhabil() {
		return idDiaInhabil;
	}

	public void setIdDiaInhabil(Long idDiaInhabil) {
		this.idDiaInhabil = idDiaInhabil;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
