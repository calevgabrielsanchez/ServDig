package mx.gob.imss.ctirss.correccion.base.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import mx.gob.imss.ctirss.correccion.catalogos.model.SacEntidadfed;
import mx.gob.imss.ctirss.correccion.catalogos.model.SacSubdelegacion;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CgcCatFlujo;

@MappedSuperclass
public class AbstractSacMunicipio extends AbstractModel{
	
	@Id
	@SequenceGenerator(name="CVE_MUNICIPIO_GENERATOR", sequenceName="SAS_CVE_PK_MUNICIPIO")
	@GeneratedValue(generator="CVE_MUNICIPIO_GENERATOR")
	@Column(name="CVE_PK")
	private Integer cvePK;

	@Column(name="CVE_CODIGO")
	private String codigo;
	
	@Column(name="NOM_NOMBRE")
	private String nombre;
	
	
	@Column(name="CVE_FK_ZONA")
	private Integer fkZona;

    @ManyToOne
	@JoinColumn(name="CVE_FK_SUBDELEGACION")
	private SacSubdelegacion sacSubdelegacion;

    @ManyToOne
	@JoinColumn(name="CVE_FK_ENTIDADFED")
	private SacEntidadfed sacEntidadFederativa;

	
	public SacEntidadfed getSacEntidadFederativa() {
		return sacEntidadFederativa;
	}

	public void setSacEntidadFederativa(SacEntidadfed sacEntidadFederativa) {
		this.sacEntidadFederativa = sacEntidadFederativa;
	}

	public SacSubdelegacion getSacSubdelegacion() {
		return sacSubdelegacion;
	}

	public void setSacSubdelegacion(SacSubdelegacion sacSubdelegacion) {
		this.sacSubdelegacion = sacSubdelegacion;
	}

	public Integer getCvePK() {
		return cvePK;
	}

	public void setCvePK(Integer cvePK) {
		this.cvePK = cvePK;
	}


	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getFkZona() {
		return fkZona;
	}

	public void setFkZona(Integer fkZona) {
		this.fkZona = fkZona;
	}

	

}
