package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_ESTADO_CIVIL database table.
 * 
 */
@Entity
@Table(name="DIC_ESTADO_CIVIL")
@OnSearchLlavePrimaria        (atributos={"cveIdEstadoCivil"})
@ComponentComboCampoDescripcion	(atributo="desEstadoCivil")
public class DicEstadoCivil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_ESTADO_CIVIL", nullable=false, precision=22)
	private Long cveIdEstadoCivil;

	@Column(name="DES_ESTADO_CIVIL", length=50)
	private String desEstadoCivil;

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
	@OneToMany(mappedBy="dicEstadoCivil", fetch = FetchType.LAZY)
	private List<DitCorreccionDatoDerechohab> ditCorreccionDatoDerechohabs;

	//bi-directional many-to-one association to DitPersona
	@OneToMany(mappedBy="dicEstadoCivil", fetch = FetchType.LAZY)
	private List<DitPersona> ditPersonas;

    public DicEstadoCivil() {
    }

	public Long getCveIdEstadoCivil() {
		return this.cveIdEstadoCivil;
	}

	public void setCveIdEstadoCivil(Long cveIdEstadoCivil) {
		this.cveIdEstadoCivil = cveIdEstadoCivil;
	}

	public String getDesEstadoCivil() {
		return this.desEstadoCivil;
	}

	public void setDesEstadoCivil(String desEstadoCivil) {
		this.desEstadoCivil = desEstadoCivil;
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

	public List<DitCorreccionDatoDerechohab> getDitCorreccionDatoDerechohabs() {
		return this.ditCorreccionDatoDerechohabs;
	}

	public void setDitCorreccionDatoDerechohabs(List<DitCorreccionDatoDerechohab> ditCorreccionDatoDerechohabs) {
		this.ditCorreccionDatoDerechohabs = ditCorreccionDatoDerechohabs;
	}
	
	public List<DitPersona> getDitPersonas() {
		return this.ditPersonas;
	}

	public void setDitPersonas(List<DitPersona> ditPersonas) {
		this.ditPersonas = ditPersonas;
	}
	
}