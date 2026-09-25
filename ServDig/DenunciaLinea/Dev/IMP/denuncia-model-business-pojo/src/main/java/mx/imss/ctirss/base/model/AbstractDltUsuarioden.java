package mx.imss.ctirss.base.model;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;

import mx.imss.ctirss.framework.annotations.IgnoreAtributosEnCriteria;
import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.model.DltDenuncia;


import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the DLT_USUARIODEN database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractDltUsuarioden extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DLT_CVEUSUARIODEN_GENERATOR", sequenceName="SEQ_CVE_USUARIODEN"  )
	@GeneratedValue(generator="DLT_CVEUSUARIODEN_GENERATOR")
	@Column(name="CVE_USUARIODEN")
	private Long cveUsuarioden;

	@Column(name="DES_EMAIL")
	private String desEmail;

	@Column(name="DES_PASSWORD")
	private String desPassword;

	@Column(name="DES_USER")
	private String desUser;
	
	@Column(name="DES_CURP")
	private String desCURP;
	
	@Column(name="CVE_PREGUNTA")
	private String cvePregunta;

	@Column(name="DES_RESPUESTA")
	private String desRespuesta;
	
    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHAREG")
	private Date fecFechareg;

	//bi-directional many-to-one association to DltDenuncia
	@OneToMany(mappedBy="dltUsuarioden" , fetch=FetchType.LAZY)
	@Transient
	@JsonIgnore
	private Set<DltDenuncia> dltDenuncias;

	
	

	public String getDesCURP() {
		return desCURP;
	}

	public void setDesCURP(String desCURP) {
		this.desCURP = desCURP;
	}

	
	public String getCvePregunta() {
		return cvePregunta;
	}

	public void setCvePregunta(String cvePregunta) {
		this.cvePregunta = cvePregunta;
	}

	public String getDesRespuesta() {
		return desRespuesta;
	}

	public void setDesRespuesta(String desRespuesta) {
		this.desRespuesta = desRespuesta;
	}

	public Long getCveUsuarioden() {
		return this.cveUsuarioden;
	}

	public void setCveUsuarioden(Long cveUsuarioden) {
		this.cveUsuarioden = cveUsuarioden;
	}

	public String getDesEmail() {
		return this.desEmail;
	}

	public void setDesEmail(String desEmail) {
		this.desEmail = desEmail;
	}

	public String getDesPassword() {
		return this.desPassword;
	}

	public void setDesPassword(String desPassword) {
		this.desPassword = desPassword;
	}

	public String getDesUser() {
		return this.desUser;
	}

	public void setDesUser(String desUser) {
		this.desUser = desUser;
	}

	public Date getFecFechareg() {
		return this.fecFechareg;
	}

	public void setFecFechareg(Date fecFechareg) {
		this.fecFechareg = fecFechareg;
	}

	public Set<DltDenuncia> getDltDenuncias() {
		return this.dltDenuncias;
	}

	public void setDltDenuncias(Set<DltDenuncia> dltDenuncias) {
		this.dltDenuncias = dltDenuncias;
	}
	
}