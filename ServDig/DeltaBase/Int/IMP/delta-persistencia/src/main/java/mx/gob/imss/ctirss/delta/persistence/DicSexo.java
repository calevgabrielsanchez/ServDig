package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_SEXO database table.
 * 
 */
@Entity
@Table(name="DIC_SEXO")
@OnSearchLlavePrimaria(atributos="cveIdSexo")
@ComponentComboCampoDescripcion(atributo="desSexo")
public class DicSexo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_SEXO", nullable=false, precision=22)
	private Long cveIdSexo;

	@Column(name="DES_SEXO", length=50)
	private String desSexo;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitCertificadoNacimiento
	@OneToMany(mappedBy="dicSexo")
	private List<DitCertificadoNacimiento> ditCertificadoNacimientos;

	//bi-directional many-to-one association to DitCorreccionDatoDerechohab
	@OneToMany(mappedBy="dicSexo", fetch = FetchType.LAZY)
	private List<DitCorreccionDatoDerechohab> ditCorreccionDatoDerechohabs;

	//bi-directional many-to-one association to DitPersona
	@OneToMany(mappedBy="dicSexo", fetch = FetchType.LAZY)
	private List<DitPersona> ditPersonas;

    public DicSexo() {
    }

	public Long getCveIdSexo() {
		return this.cveIdSexo;
	}

	public void setCveIdSexo(Long cveIdSexo) {
		this.cveIdSexo = cveIdSexo;
	}

	public String getDesSexo() {
		return this.desSexo;
	}

	public void setDesSexo(String desSexo) {
		this.desSexo = desSexo;
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

	public List<DitCertificadoNacimiento> getDitCertificadoNacimientos() {
		return this.ditCertificadoNacimientos;
	}

	public void setDitCertificadoNacimientos(List<DitCertificadoNacimiento> ditCertificadoNacimientos) {
		this.ditCertificadoNacimientos = ditCertificadoNacimientos;
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