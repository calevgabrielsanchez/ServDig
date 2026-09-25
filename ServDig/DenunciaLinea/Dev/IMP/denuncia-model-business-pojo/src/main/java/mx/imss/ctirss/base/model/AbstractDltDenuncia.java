package mx.imss.ctirss.base.model;


import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.FetchMode;

import mx.imss.ctirss.catalogos.model.DlcStatus;
import mx.imss.ctirss.catalogos.model.DlcSubdelegacion;
import mx.imss.ctirss.catalogos.model.DlcTipodenunciante;
import mx.imss.ctirss.catalogos.model.DlcUsuarioFuncionario;
import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.model.DltDatospatron;
import mx.imss.ctirss.model.DltInfotrabajo;
import mx.imss.ctirss.model.DltMotivodenuncia;
import mx.imss.ctirss.model.DltPersona;
import mx.imss.ctirss.model.DltUsuarioden;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * The persistent class for the DLT_DENUNCIA database table.
 * 
 */
@MappedSuperclass
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractDltDenuncia extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DLT_DENUNCIA_CVEFOLIODENUNCIA_GENERATOR", sequenceName="SEQ_CVE_FOLIODENUNCIA")
	@GeneratedValue(generator="DLT_DENUNCIA_CVEFOLIODENUNCIA_GENERATOR")
	@Column(name="CVE_FOLIODENUNCIA", unique=true)
	private Long cveFoliodenuncia;

	@Column(name="CVE_ID_USUARIO_FUNCIONARIO", insertable=false, updatable=false)
	private BigDecimal cveIdUsuarioFuncionario;

	@Column(name="CVE_ORIGENDENUNCIA")
	private BigDecimal cveOrigendenuncia;

	@Column(name="CVE_SUBDELEG_RATIFICA", insertable=false, updatable=false)
	private BigDecimal cveSubdelegRatifica;

	@Column(name="CVE_TIPODENUNCIANTE", insertable=false, updatable=false)
	private Long cveTipodenunciante;

	@Column(name="CVE_USUARIODEN", insertable=false, updatable=false)
	private Long cveUsuarioden;

	@Column(name="DES_ACLARACION")
	private String desAclaracion;

	@Column(name="DES_OBSERVACIONES")
	private String desObservaciones;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHAREG")
	private Date fecFechareg;
    
    @Temporal( TemporalType.DATE)
   	@Column(name="FEC_FECHAENV")
   	private Date fecFechaenv;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO")
	private Date fecRegistro;

	@Column(name="ID_STATUS", insertable=false, updatable=false)
	private Long idStatus;

	@Column(name="NUM_FOLIODENUNCIA")
	private String numFoliodenuncia;


	//bi-directional many-to-one association to DlcStatus
    @ManyToOne
	@JoinColumn(name="ID_STATUS")
	private DlcStatus dlcStatus;

	//bi-directional many-to-one association to DlcSubdelegacion
    @ManyToOne
	@JoinColumn(name="CVE_SUBDELEG_RATIFICA")
	private DlcSubdelegacion dlcSubdelegacion;

	//bi-directional many-to-one association to DlcTipodenunciante
    @ManyToOne
	@JoinColumn(name="CVE_TIPODENUNCIANTE")
	private DlcTipodenunciante dlcTipodenunciante;

	//bi-directional many-to-one association to DlcUsuarioFuncionario
    @ManyToOne
	@JoinColumn(name="CVE_ID_USUARIO_FUNCIONARIO")
	private DlcUsuarioFuncionario dlcUsuarioFuncionario;

	//bi-directional many-to-one association to DltUsuarioden
    @ManyToOne
	@JoinColumn(name="CVE_USUARIODEN")    
	private DltUsuarioden dltUsuarioden;

	//bi-directional many-to-one association to DltInfotrabajo
	@OneToMany(mappedBy="cveFoliodenuncia")
	private Set<DltInfotrabajo> dltInfotrabajos;

	//bi-directional many-to-one association to DltMotivodenuncia
	@OneToMany ( mappedBy="dltDenuncia", fetch=FetchType.LAZY)
	private Set<DltMotivodenuncia> dltMotivodenuncias;

	//bi-directional many-to-one association to DltPersona
	@OneToMany( mappedBy="cveFoliodenuncia", fetch=FetchType.LAZY)
	private Set<DltPersona> dltPersonas;
	
	//bi-directional many-to-one association to DltDatospatron
	@OneToMany(mappedBy="cveFoliodenuncia",fetch=FetchType.LAZY)
	private Set<DltDatospatron> dltDatospatrons;

    public AbstractDltDenuncia() {
    }

	public Long getCveFoliodenuncia() {
		return this.cveFoliodenuncia;
	}

	public void setCveFoliodenuncia(Long cveFoliodenuncia) {
		this.cveFoliodenuncia = cveFoliodenuncia;
	}


	public BigDecimal getCveOrigendenuncia() {
		return this.cveOrigendenuncia;
	}

	public void setCveOrigendenuncia(BigDecimal cveOrigendenuncia) {
		this.cveOrigendenuncia = cveOrigendenuncia;
	}

	public BigDecimal getCveSubdelegRatifica() {
		return this.cveSubdelegRatifica;
	}

	public void setCveSubdelegRatifica(BigDecimal cveSubdelegRatifica) {
		this.cveSubdelegRatifica = cveSubdelegRatifica;
	}

	public Long getCveTipodenunciante() {
		return this.cveTipodenunciante;
	}

	public void setCveTipodenunciante(Long cveTipodenunciante) {
		this.cveTipodenunciante = cveTipodenunciante;
	}


	public Long getCveUsuarioden() {
		return this.cveUsuarioden;
	}

	public void setCveUsuarioden(Long cveUsuarioden) {
		this.cveUsuarioden = cveUsuarioden;
	}

	public String getDesAclaracion() {
		return this.desAclaracion;
	}

	public void setDesAclaracion(String desAclaracion) {
		this.desAclaracion = desAclaracion;
	}

	public String getDesObservaciones() {
		return this.desObservaciones;
	}

	public void setDesObservaciones(String desObservaciones) {
		this.desObservaciones = desObservaciones;
	}

	public Date getFecFechareg() {
		return this.fecFechareg;
	}

	public void setFecFechareg(Date fecFechareg) {
		this.fecFechareg = fecFechareg;
	}

	public Date getFecRegistro() {
		return this.fecRegistro;
	}

	public void setFecRegistro(Date fecRegistro) {
		this.fecRegistro = fecRegistro;
	}

	public Long getIdStatus() {
		return this.idStatus;
	}

//	public BigDecimal getCveSeqSubfolio() {
//		return cveSeqSubfolio;
//	}
//
//	public void setCveSeqSubfolio(BigDecimal cveSeqSubfolio) {
//		this.cveSeqSubfolio = cveSeqSubfolio;
//	}

	public void setIdStatus(Long idStatus) {
		this.idStatus = idStatus;
	}

	public String getNumFoliodenuncia() {
		return this.numFoliodenuncia;
	}

	public void setNumFoliodenuncia(String numFoliodenuncia) {
		this.numFoliodenuncia = numFoliodenuncia;
	}

	public Set<DltDatospatron> getDltDatospatrons() {
		return this.dltDatospatrons;
	}

	public void setDltDatospatrons(Set<DltDatospatron> dltDatospatrons) {
		this.dltDatospatrons = dltDatospatrons;
	}
	
	public DlcStatus getDlcStatus() {
		return this.dlcStatus;
	}

	public void setDlcStatus(DlcStatus dlcStatus) {
		this.dlcStatus = dlcStatus;
	}
	
	public DlcSubdelegacion getDlcSubdelegacion() {
		return this.dlcSubdelegacion;
	}

	public void setDlcSubdelegacion(DlcSubdelegacion dlcSubdelegacion) {
		this.dlcSubdelegacion = dlcSubdelegacion;
	}
	
	public DlcTipodenunciante getDlcTipodenunciante() {
		return this.dlcTipodenunciante;
	}

	public void setDlcTipodenunciante(DlcTipodenunciante dlcTipodenunciante) {
		this.dlcTipodenunciante = dlcTipodenunciante;
	}
	
	public DlcUsuarioFuncionario getDlcUsuarioFuncionario() {
		return dlcUsuarioFuncionario;
	}

	public void setDlcUsuarioFuncionario(DlcUsuarioFuncionario dlcUsuarioFuncionario) {
		this.dlcUsuarioFuncionario = dlcUsuarioFuncionario;
	}

	public BigDecimal getCveIdUsuarioFuncionario() {
		return cveIdUsuarioFuncionario;
	}

	public void setCveIdUsuarioFuncionario(BigDecimal cveIdUsuarioFuncionario) {
		this.cveIdUsuarioFuncionario = cveIdUsuarioFuncionario;
	}

	public DltUsuarioden getDltUsuarioden() {
		return this.dltUsuarioden;
	}

	public void setDltUsuarioden(DltUsuarioden dltUsuarioden) {
		this.dltUsuarioden = dltUsuarioden;
	}
	
	public Set<DltInfotrabajo> getDltInfotrabajos() {
		return this.dltInfotrabajos;
	}

	public void setDltInfotrabajos(Set<DltInfotrabajo> dltInfotrabajos) {
		this.dltInfotrabajos = dltInfotrabajos;
	}
	
	public Set<DltMotivodenuncia> getDltMotivodenuncias() {
		return this.dltMotivodenuncias;
	}

	public void setDltMotivodenuncias(Set<DltMotivodenuncia> dltMotivodenuncias) {
		this.dltMotivodenuncias = dltMotivodenuncias;
	}
	

	public Set<DltPersona> getDltPersonas() {
		return dltPersonas;
	}

	public void setDltPersonas(Set<DltPersona> dltPersonas) {
		this.dltPersonas = dltPersonas;
	}

	public Date getFecFechaenv() {
		return fecFechaenv;
	}

	public void setFecFechaenv(Date fecFechaenv) {
		this.fecFechaenv = fecFechaenv;
	}

	
}