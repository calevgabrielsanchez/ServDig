package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_CALIDAD_PARENTESCO database table.
 * 
 */
@Entity
@Table(name="DIC_CALIDAD_PARENTESCO")
@OnSearchLlavePrimaria        (atributos={"cveIdCalidadParentesco"})
@ComponentComboCampoDescripcion	(atributo="desParentesco")
public class DicCalidadParentesco implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_CALIDAD_PARENTESCO", nullable=false, precision=22)
	private long cveIdCalidadParentesco;

	@Column(name="CALIDAD_MAXIMA", nullable=false, precision=22)
	private long calidadMaxima;

	@Column(name="CALIDAD_MINIMA", nullable=false, precision=22)
	private long calidadMinima;

	@Column(name="DES_PARENTESCO", nullable=false, length=255)
	private String desParentesco;

	@Column(name="DES_TIPIFICACION_FEMENINA", length=255)
	private String desTipificacionFemenina;

	@Column(name="DES_TIPIFICACION_MASCULINA", length=255)
	private String desTipificacionMasculina;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitGrupoFamiliar
	@OneToMany(mappedBy="dicCalidadParentesco", fetch = FetchType.LAZY)
	private List<DitGrupoFamiliar> ditGrupoFamiliars;

	//bi-directional many-to-one association to DitRegistroDerechohabiente
	@OneToMany(mappedBy="dicCalidadParentesco", fetch = FetchType.LAZY)
	private List<DitRegistroDerechohabiente> ditRegistroDerechohabientes;

    public DicCalidadParentesco() {
    }

	public long getCveIdCalidadParentesco() {
		return this.cveIdCalidadParentesco;
	}

	public void setCveIdCalidadParentesco(long cveIdCalidadParentesco) {
		this.cveIdCalidadParentesco = cveIdCalidadParentesco;
	}

	public long getCalidadMaxima() {
		return this.calidadMaxima;
	}

	public void setCalidadMaxima(long calidadMaxima) {
		this.calidadMaxima = calidadMaxima;
	}

	public long getCalidadMinima() {
		return this.calidadMinima;
	}

	public void setCalidadMinima(long calidadMinima) {
		this.calidadMinima = calidadMinima;
	}

	public String getDesParentesco() {
		return this.desParentesco;
	}

	public void setDesParentesco(String desParentesco) {
		this.desParentesco = desParentesco;
	}

	public String getDesTipificacionFemenina() {
		return this.desTipificacionFemenina;
	}

	public void setDesTipificacionFemenina(String desTipificacionFemenina) {
		this.desTipificacionFemenina = desTipificacionFemenina;
	}

	public String getDesTipificacionMasculina() {
		return this.desTipificacionMasculina;
	}

	public void setDesTipificacionMasculina(String desTipificacionMasculina) {
		this.desTipificacionMasculina = desTipificacionMasculina;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public List<DitGrupoFamiliar> getDitGrupoFamiliars() {
		return this.ditGrupoFamiliars;
	}

	public void setDitGrupoFamiliars(List<DitGrupoFamiliar> ditGrupoFamiliars) {
		this.ditGrupoFamiliars = ditGrupoFamiliars;
	}
	
	public List<DitRegistroDerechohabiente> getDitRegistroDerechohabientes() {
		return this.ditRegistroDerechohabientes;
	}

	public void setDitRegistroDerechohabientes(List<DitRegistroDerechohabiente> ditRegistroDerechohabientes) {
		this.ditRegistroDerechohabientes = ditRegistroDerechohabientes;
	}
}