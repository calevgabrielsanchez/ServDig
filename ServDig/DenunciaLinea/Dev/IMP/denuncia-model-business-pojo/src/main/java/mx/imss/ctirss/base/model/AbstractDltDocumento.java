package mx.imss.ctirss.base.model;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.model.DltDenuncia;
import mx.imss.ctirss.model.DltPersona;

import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the DLT_DOCUMENTO database table.
 * 
 */
@MappedSuperclass
@JsonIgnoreProperties(ignoreUnknown = true)
public class AbstractDltDocumento extends AbstractModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DLT_DOCUMENTO_CVEDOCUMENTO_GENERATOR", sequenceName="SEQ_CVE_DOCUMENTO")
	@GeneratedValue( generator="DLT_DOCUMENTO_CVEDOCUMENTO_GENERATOR")
	@Column(name="CVE_DOCUMENTO")
	private Long cveDocumento;
	
	
	@Column(name="CVE_PERSONA")
	private Long cvePersona;

	@Column(name="DES_EXTENSION")
	private String desExtension;

	@Column(name="DES_MEDIDA_TAMANO")
	private String desMedidaTamano;

	@Column(name="DES_NOMBRE")
	private String desNombre;

	@Column(name="DES_PATH")
	private String desPath;

	@Column(name="DES_TAMANO")
	private BigDecimal desTamano;

	@Column(name="DES_TIPO_CONTENIDO")
	private String desTipoContenido;

	@Column(name="NUM_SUBFOLIODENUNCIA")
	private String numSubfoliodenuncia;

	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="CVE_PERSONA",referencedColumnName="CVE_PERSONA",nullable = false, insertable = false, updatable = false)
	private DltPersona dltPersona;
	
	
	public Long getCveDocumento() {
		return this.cveDocumento;
	}

	public void setCveDocumento(Long cveDocumento) {
		this.cveDocumento = cveDocumento;
	}


	public String getDesExtension() {
		return this.desExtension;
	}

	public void setDesExtension(String desExtension) {
		this.desExtension = desExtension;
	}

	public String getDesMedidaTamano() {
		return this.desMedidaTamano;
	}

	public void setDesMedidaTamano(String desMedidaTamano) {
		this.desMedidaTamano = desMedidaTamano;
	}

	public String getDesNombre() {
		return this.desNombre;
	}

	public void setDesNombre(String desNombre) {
		this.desNombre = desNombre;
	}

	public String getDesPath() {
		return this.desPath;
	}

	public void setDesPath(String desPath) {
		this.desPath = desPath;
	}

	public BigDecimal getDesTamano() {
		return this.desTamano;
	}

	public void setDesTamano(BigDecimal desTamano) {
		this.desTamano = desTamano;
	}

	public String getDesTipoContenido() {
		return this.desTipoContenido;
	}

	public void setDesTipoContenido(String desTipoContenido) {
		this.desTipoContenido = desTipoContenido;
	}

	public String getNumSubfoliodenuncia() {
		return this.numSubfoliodenuncia;
	}

	public void setNumSubfoliodenuncia(String numSubfoliodenuncia) {
		this.numSubfoliodenuncia = numSubfoliodenuncia;
	}

	public Long getCvePersona() {
		return cvePersona;
	}

	public void setCvePersona(Long cvePersona) {
		this.cvePersona = cvePersona;
	}

	public DltPersona getDltPersona() {
		return dltPersona;
	}

	public void setDltPersona(DltPersona dltPersona) {
		this.dltPersona = dltPersona;
	}

}