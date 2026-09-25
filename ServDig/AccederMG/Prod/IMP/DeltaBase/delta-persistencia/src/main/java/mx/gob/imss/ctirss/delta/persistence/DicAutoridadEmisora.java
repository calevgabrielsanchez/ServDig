package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_AUTORIDAD_EMISORA database table.
 * 
 */
@Entity
@Table(name="DIC_AUTORIDAD_EMISORA")
@OnSearchLlavePrimaria(atributos={"cveIdAutoridadEmisora"})
@ComponentComboCampoDescripcion(atributo="desAutoridad")
public class DicAutoridadEmisora implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_AUTORIDAD_EMISORA", nullable=false, precision=22)
	private long cveIdAutoridadEmisora;

	@Column(name="DES_AUTORIDAD", length=50)
	private String desAutoridad;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitCorreccionDatoDerechohab
	@OneToMany(mappedBy="dicAutoridadEmisora")
	private List<DitActaUnionCivil> ditActaUnionCivil;

	//bi-directional many-to-one association to DitPersona
	@OneToMany(mappedBy="dicAutoridadEmisora")
	private List<DitActaTerminoUnionCivil> ditActaTerminoUnionCivil;

    public DicAutoridadEmisora() {
    }

	public long getCveIdAutoridadEmisora() {
		return cveIdAutoridadEmisora;
	}

	public void setCveIdAutoridadEmisora(long cveIdAutoridadEmisora) {
		this.cveIdAutoridadEmisora = cveIdAutoridadEmisora;
	}

	public String getDesAutoridad() {
		return desAutoridad;
	}

	public void setDesAutoridad(String desAutoridad) {
		this.desAutoridad = desAutoridad;
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

	public List<DitActaUnionCivil> getDitActuaUnionCivil() {
		return ditActaUnionCivil;
	}

	public void setDitActuaUnionCivil(List<DitActaUnionCivil> ditActuaUnionCivil) {
		this.ditActaUnionCivil = ditActuaUnionCivil;
	}

	public List<DitActaTerminoUnionCivil> getDitActaTerminoUnionCivil() {
		return ditActaTerminoUnionCivil;
	}

	public void setDitActaTerminoUnionCivil(List<DitActaTerminoUnionCivil> ditActaTerminoUnionCivil) {
		this.ditActaTerminoUnionCivil = ditActaTerminoUnionCivil;
	}
	
}