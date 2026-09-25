package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

@Entity
@Table(name = "DIC_MOTIVO_DESACUERDO")
@OnSearchLlavePrimaria(atributos={"cveIdMotivoDescacuerdo"})
@ComponentComboCampoDescripcion(atributo="desMotivoDesacuerdo")
public class DicMotivoDesacuerdo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CVE_ID_MOTIVO_DESACUERDO")
	private long cveIdMotivoDescacuerdo;
	
	@Column(name = "DES_MOTIVO_DESACUERDO")
	private String desMotivoDesacuerdo;
		
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	public DicMotivoDesacuerdo() {
		super();
	}

	public DicMotivoDesacuerdo(long cveIdMotivoDescacuerdo) {
		this();
		this.cveIdMotivoDescacuerdo = cveIdMotivoDescacuerdo;
	}
	
	public DicMotivoDesacuerdo(long cveIdMotivoDescacuerdo, String desMotivoDesacuerdo) {		
		this(cveIdMotivoDescacuerdo);
		this.desMotivoDesacuerdo = desMotivoDesacuerdo;
	}

	public long getCveIdMotivoDescacuerdo() {
		return cveIdMotivoDescacuerdo;
	}

	public void setCveIdMotivoDescacuerdo(long cveIdMotivoDescacuerdo) {
		this.cveIdMotivoDescacuerdo = cveIdMotivoDescacuerdo;
	}

	public String getDesMotivoDesacuerdo() {
		return desMotivoDesacuerdo;
	}

	public void setDesMotivoDesacuerdo(String desMotivoDesacuerdo) {
		this.desMotivoDesacuerdo = desMotivoDesacuerdo;
	}

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}
}