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
@Table(name = "DIC_CAUSA_DESACUERDO")
@OnSearchLlavePrimaria(atributos={"cveIdCausaDescacuerdo"})
@ComponentComboCampoDescripcion(atributo="desCausaDesacuerdo")
public class DicCausaDesacuerdo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CVE_ID_CAUSA_DESACUERDO")
	private long cveIdCausaDescacuerdo;
	
	@Column(name = "DES_CAUSA_DESACUERDO")
	private String desCausaDesacuerdo;
	
	@ManyToOne
	@JoinColumn(name = "CVE_ID_MATERIA_DESACUERDO")
	private DicMateriaDesacuerdo dicMateriaDesacuerdo;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
	
	public DicCausaDesacuerdo() {
		super();
	}
	
	public DicCausaDesacuerdo(long cveIdCausaDescacuerdo) {
		this();
		this.cveIdCausaDescacuerdo = cveIdCausaDescacuerdo;
	}

	public DicCausaDesacuerdo(long cveIdCausaDescacuerdo, String desCausaDesacuerdo) {
		this(cveIdCausaDescacuerdo);
		this.desCausaDesacuerdo = desCausaDesacuerdo;
	}

	public long getCveIdCausaDescacuerdo() {
		return cveIdCausaDescacuerdo;
	}

	public void setCveIdCausaDescacuerdo(long cveIdCausaDescacuerdo) {
		this.cveIdCausaDescacuerdo = cveIdCausaDescacuerdo;
	}

	public String getDesCausaDesacuerdo() {
		return desCausaDesacuerdo;
	}

	public void setDesCausaDesacuerdo(String desCausaDesacuerdo) {
		this.desCausaDesacuerdo = desCausaDesacuerdo;
	}

	public DicMateriaDesacuerdo getDicMateriaDesacuerdo() {
		return dicMateriaDesacuerdo;
	}

	public void setDicMateriaDesacuerdo(DicMateriaDesacuerdo dicMateriaDesacuerdo) {
		this.dicMateriaDesacuerdo = dicMateriaDesacuerdo;
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