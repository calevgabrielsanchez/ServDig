package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

@Entity
@Table(name = "DIC_MATERIA_DESACUERDO")
@OnSearchLlavePrimaria(atributos={"cveIdMateriaDesacuerdo"})
@ComponentComboCampoDescripcion(atributo="desMateriaDesacuerdo")
public class DicMateriaDesacuerdo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CVE_ID_MATERIA_DESACUERDO")
	private long cveIdMateriaDesacuerdo;
	
	@Column(name = "DES_MATERIA_DESACUERDO")
	private String desMateriaDesacuerdo;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	public DicMateriaDesacuerdo() {
		super();
	}
	
	public DicMateriaDesacuerdo(long cveIdMateriaDesacuerdo) {
		this();
		this.cveIdMateriaDesacuerdo = cveIdMateriaDesacuerdo;
	}
	
	public DicMateriaDesacuerdo(long cveIdMateriaDesacuerdo, String desMateriaDesacuerdo) {
		this(cveIdMateriaDesacuerdo);
		this.desMateriaDesacuerdo = desMateriaDesacuerdo;
	}
	
	public long getCveIdMateriaDesacuerdo() {
		return cveIdMateriaDesacuerdo;
	}

	public void setCveIdMateriaDesacuerdo(long cveIdMateriaDesacuerdo) {
		this.cveIdMateriaDesacuerdo = cveIdMateriaDesacuerdo;
	}

	public String getDesMateriaDesacuerdo() {
		return desMateriaDesacuerdo;
	}

	public void setDesMateriaDesacuerdo(String desMateriaDesacuerdo) {
		this.desMateriaDesacuerdo = desMateriaDesacuerdo;
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