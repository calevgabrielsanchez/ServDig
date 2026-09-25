package mx.imss.ctirss.base.model;


import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.model.DltDenuncia;
import mx.imss.ctirss.model.DltFormapago;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the DLT_INFOTRABAJO database table.
 * 
 */
@MappedSuperclass
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractDltInfotrabajo extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DLT_INFOTRABAJO_CVEINFOTRABAJO_GENERATOR", sequenceName="SEQ_CVE_INFOTRABAJO")
	@GeneratedValue(generator="DLT_INFOTRABAJO_CVEINFOTRABAJO_GENERATOR")
	@Column(name="CVE_INFOTRABAJO")
	private Long cveInfotrabajo;
	
	@Column(name="CVE_FOLIODENUNCIA")
	private Long cveFoliodenuncia;
	
	
	

	@Column(name="DES_BASE_COMISION_OTROS")
	private String desBaseComisionOtros;

	@Column(name="DES_HORARIOLABORES")
	private String desHorariolabores;

	@Column(name="DES_LABORESDESEMP")
	private String desLaboresdesemp;

	@Column(name="DES_NOMJEFEINMEDIATO")
	private String desNomjefeinmediato;

	
	@Column(name="IND_CONTRATO")
	private Long indContrato;
	
	@Column(name="DES_NUMCONTRATO")
	private String desNumcontrato;

	@Column(name="DES_OBSERVACIONES")
	private String desObservaciones;
	
	@Column(name="DES_BASE_OTORGAMIENTO")
	private String desBaseOtorgamiento;
	
    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHAREG")
	private Date fecFechareg;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHARIESGOTRAB")
	private Date fecFechariesgotrab;

	@Column(name="IMP_AGUINALDO")
	private BigDecimal impAguinaldo;

	@Column(name="IMP_COMISION_OTROS")
	private BigDecimal impComisionOtros;

	@Column(name="IMP_SALARIOPERCIBIDO")
	private BigDecimal impSalariopercibido;

	@Column(name="IMP_VACACIONES")
	private BigDecimal impVacaciones;

	@Column(name="NUM_DIASVACACIONES")
	private BigDecimal numDiasvacaciones;
	
	@Column(name="NUM_DIASAGUINALDO")
	private BigDecimal numDiasAguinaldo;
	
	@Column(name="ID_DOMICILIO")
	private Long idDomicilio;
	
	
    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHAINICIO")
	private Date fecFechaInicio;
    
    
    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHAFIN")
	private Date fecFechaFin;

	//bi-directional many-to-one association to DltFormapago
	@OneToMany(mappedBy="dltInfotrabajo",fetch=FetchType.EAGER)	
	private Set<DltFormapago> dltFormapagos;

	//bi-directional many-to-one association to DltDenuncia
  //  @ManyToOne
//	@JoinColumn(name="CVE_FOLIODENUNCIA")
	//private DltDenuncia dltDenuncia;
	//bi-directional many-to-one association to DltDenuncia
    
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="CVE_FOLIODENUNCIA",referencedColumnName="CVE_FOLIODENUNCIA",nullable = false, insertable = false, updatable = false)
	private DltDenuncia dltDenuncia;


	
	
	
	public Long getIdDomicilio() {
		return idDomicilio;
	}

	public void setIdDomicilio(Long idDomicilio) {
		this.idDomicilio = idDomicilio;
	}

	public BigDecimal getNumDiasAguinaldo() {
		return numDiasAguinaldo;
	}

	public void setNumDiasAguinaldo(BigDecimal numDiasAguinaldo) {
		this.numDiasAguinaldo = numDiasAguinaldo;
	}

	public Long getCveInfotrabajo() {
		return this.cveInfotrabajo;
	}

	public void setCveInfotrabajo(Long cveInfotrabajo) {
		this.cveInfotrabajo = cveInfotrabajo;
	}
	
	
	public Long getCveFoliodenuncia() {
		return cveFoliodenuncia;
	}

	public void setCveFoliodenuncia(Long cveFoliodenuncia) {
		this.cveFoliodenuncia = cveFoliodenuncia;
	}

	public String getDesBaseComisionOtros() {
		return this.desBaseComisionOtros==null?"":this.desBaseComisionOtros.trim();
	}

	public void setDesBaseComisionOtros(String desBaseComisionOtros) {
		this.desBaseComisionOtros = desBaseComisionOtros;
	}

	public String getDesHorariolabores() {
		return this.desHorariolabores==null?"":this.desHorariolabores.trim();
	}

	public void setDesHorariolabores(String desHorariolabores) {
		this.desHorariolabores = desHorariolabores;
	}

	public String getDesLaboresdesemp() {
		return this.desLaboresdesemp==null?"":this.desLaboresdesemp.trim();
	}

	public void setDesLaboresdesemp(String desLaboresdesemp) {
		this.desLaboresdesemp = desLaboresdesemp;
	}

	public String getDesNomjefeinmediato() {
		return this.desNomjefeinmediato==null?"":this.desNomjefeinmediato.trim();
	}

	public void setDesNomjefeinmediato(String desNomjefeinmediato) {
		this.desNomjefeinmediato = desNomjefeinmediato;
	}

	public String getDesNumcontrato() {
		return this.desNumcontrato==null?"":this.desNumcontrato;
	}

	public void setDesNumcontrato(String desNumcontrato) {
		this.desNumcontrato = desNumcontrato;
	}

	public String getDesObservaciones() {
		return this.desObservaciones==null?"":this.desObservaciones.trim();
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

	public String getDesBaseOtorgamiento() {
		return desBaseOtorgamiento==null?"":this.desBaseOtorgamiento.trim();
	}

	public void setDesBaseOtorgamiento(String desBaseOtorgamiento) {
		this.desBaseOtorgamiento = desBaseOtorgamiento;
	}

	public Date getFecFechariesgotrab() {
		return this.fecFechariesgotrab;
	}

	public void setFecFechariesgotrab(Date fecFechariesgotrab) {
		this.fecFechariesgotrab = fecFechariesgotrab;
	}

	public BigDecimal getImpAguinaldo() {
		return this.impAguinaldo;
	}

	public void setImpAguinaldo(BigDecimal impAguinaldo) {
		this.impAguinaldo = impAguinaldo;
	}

	public BigDecimal getImpComisionOtros() {
		return this.impComisionOtros;
	}

	public void setImpComisionOtros(BigDecimal impComisionOtros) {
		this.impComisionOtros = impComisionOtros;
	}

	public BigDecimal getImpSalariopercibido() {
		return this.impSalariopercibido;
	}

	public void setImpSalariopercibido(BigDecimal impSalariopercibido) {
		this.impSalariopercibido = impSalariopercibido;
	}

	public BigDecimal getImpVacaciones() {
		return this.impVacaciones;
	}

	public Date getFecFechaInicio() {
		return fecFechaInicio;
	}

	public void setFecFechaInicio(Date fecFechaInicio) {
		this.fecFechaInicio = fecFechaInicio;
	}

	public Date getFecFechaFin() {
		return fecFechaFin;
	}

	public void setFecFechaFin(Date fecFechaFin) {
		this.fecFechaFin = fecFechaFin;
	}

	public void setImpVacaciones(BigDecimal impVacaciones) {
		this.impVacaciones = impVacaciones;
	}

	public BigDecimal getNumDiasvacaciones() {
		return this.numDiasvacaciones;
	}

	public void setNumDiasvacaciones(BigDecimal numDiasvacaciones) {
		this.numDiasvacaciones = numDiasvacaciones;
	}

	@JsonIgnore
	public Set<DltFormapago> getDltFormapagos() {
		return this.dltFormapagos;
	}

	public void setDltFormapagos(Set<DltFormapago> dltFormapagos) {
		this.dltFormapagos = dltFormapagos;
	}
	
	public DltDenuncia getDltDenuncia() {
		return this.dltDenuncia;
	}

	public void setDltDenuncia(DltDenuncia dltDenuncia) {
		this.dltDenuncia = dltDenuncia;
	}

	public Long getIndContrato() {
		return indContrato;
	}

	public void setIndContrato(Long indContrato) {
		this.indContrato = indContrato;
	}
	
	
}