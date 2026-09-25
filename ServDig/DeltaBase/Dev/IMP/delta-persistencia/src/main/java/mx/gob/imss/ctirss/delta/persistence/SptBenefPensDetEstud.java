package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the SPT_BENEF_PENS_DET_ESTUD database table.
 * 
 */
@Entity
@Table(name="SPT_BENEF_PENS_DET_ESTUD")
@NamedQuery(name="SptBenefPensDetEstud.findAll", query="SELECT s FROM SptBenefPensDetEstud s")
public class SptBenefPensDetEstud implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id	
	@SequenceGenerator(name = "SEQ_SPTBENEFPENSDETESTUD", sequenceName = "SEQ_SPTBENEFPENSDETESTUD")
	@GeneratedValue(generator = "SEQ_SPTBENEFPENSDETESTUD")
	@Column(name="CVE_ID_BENEF_PENS_DET_ESTUD")
	private long cveIdBenefPensDetEstud;

	@Column(name="CVE_PLANTEL_EDUCATIVO")
	private String cvePlantelEducativo;

	@Column(name="CVE_SEP")
	private String cveSep;

	@Column(name="DES_LUGAR_EXPEDICION")
	private String desLugarExpedicion;

	@Column(name="DES_PLANTEL_EDUCATIVO")
	private String desPlantelEducativo;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_EXPEDICION")
	private Date fecExpedicion;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_FIN_CICLO_ESCOLAR")
	private Date fecFinCicloEscolar;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO_CICLO_ESCOLAR")
	private Date fecInicioCicloEscolar;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to SptBeneficiarioPension
	@ManyToOne
	@JoinColumn(name="CVE_ID_BENEFICIARIO_PENSION")
	private SptBeneficiarioPension sptBeneficiarioPension;
	
	
	
	
	//bi-directional many-to-one association to SpcCalendarioEscolar
//    @ManyToOne
//	@JoinColumn(name="ID_CALENDARIO_ESCOLAR")
//	private SpcCalendarioEscolar spcCalendarioEscolar;
	//FIXME no despliega esta relacion
	//bi-directional many-to-one association to SpcNivelEstudio
//    @ManyToOne
//	@JoinColumn(name="ID_NIVEL_ESTUDIOS")
//	private SpcNivelEstudio spcNivelEstudio;
	//FIXME no despliega esta relacion

	//bi-directional many-to-one association to SpcNumeroPeriodoEscolar
    @ManyToOne
	@JoinColumn(name="ID_NUM_PERIODO_ESCOLAR")
	private SpcNumeroPeriodoEscolar spcNumeroPeriodoEscolar;

	//bi-directional many-to-one association to SpcOrfandad
    @ManyToOne
	@JoinColumn(name="ID_ORFANDAD")
	private SpcOrfandad spcOrfandad;

	//bi-directional many-to-one association to SpcPeriodoEscolar
//    @ManyToOne
//	@JoinColumn(name="ID_PERIODO_ESCOLAR")
//	private SpcPeriodoEscolar spcPeriodoEscolar;
	//FIXME no despliega esta relacion

	//bi-directional many-to-one association to SpcReformaLey
    @ManyToOne
	@JoinColumn(name="ID_REFORMA_LEY")
	private SpcReformaLey spcReformaLey;
    
	@ManyToOne
	@JoinColumn(name="CVE_ID_BENEFICIARIO_SOLICITUD")
	private SptBeneficiarioSolicitud sptBeneficiarioSolicitud;
    

	public SptBenefPensDetEstud() {
	}

	public long getCveIdBenefPensDetEstud() {
		return this.cveIdBenefPensDetEstud;
	}

	public void setCveIdBenefPensDetEstud(long cveIdBenefPensDetEstud) {
		this.cveIdBenefPensDetEstud = cveIdBenefPensDetEstud;
	}

	public String getCvePlantelEducativo() {
		return this.cvePlantelEducativo;
	}

	public void setCvePlantelEducativo(String cvePlantelEducativo) {
		this.cvePlantelEducativo = cvePlantelEducativo;
	}

	public String getCveSep() {
		return this.cveSep;
	}

	public void setCveSep(String cveSep) {
		this.cveSep = cveSep;
	}

	public String getDesLugarExpedicion() {
		return this.desLugarExpedicion;
	}

	public void setDesLugarExpedicion(String desLugarExpedicion) {
		this.desLugarExpedicion = desLugarExpedicion;
	}

	public String getDesPlantelEducativo() {
		return this.desPlantelEducativo;
	}

	public void setDesPlantelEducativo(String desPlantelEducativo) {
		this.desPlantelEducativo = desPlantelEducativo;
	}

	public Date getFecExpedicion() {
		return this.fecExpedicion;
	}

	public void setFecExpedicion(Date fecExpedicion) {
		this.fecExpedicion = fecExpedicion;
	}

	public Date getFecFinCicloEscolar() {
		return this.fecFinCicloEscolar;
	}

	public void setFecFinCicloEscolar(Date fecFinCicloEscolar) {
		this.fecFinCicloEscolar = fecFinCicloEscolar;
	}

	public Date getFecInicioCicloEscolar() {
		return this.fecInicioCicloEscolar;
	}

	public void setFecInicioCicloEscolar(Date fecInicioCicloEscolar) {
		this.fecInicioCicloEscolar = fecInicioCicloEscolar;
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

	public SptBeneficiarioPension getSptBeneficiarioPension() {
		return this.sptBeneficiarioPension;
	}

	public void setSptBeneficiarioPension(SptBeneficiarioPension sptBeneficiarioPension) {
		this.sptBeneficiarioPension = sptBeneficiarioPension;
	}
//
//	public SpcCalendarioEscolar getSpcCalendarioEscolar() {
//		return spcCalendarioEscolar;
//	}
//
//	public void setSpcCalendarioEscolar(SpcCalendarioEscolar spcCalendarioEscolar) {
//		this.spcCalendarioEscolar = spcCalendarioEscolar;
//	}

//	public SpcNivelEstudio getSpcNivelEstudio() {
//		return spcNivelEstudio;
//	}
//
//	public void setSpcNivelEstudio(SpcNivelEstudio spcNivelEstudio) {
//		this.spcNivelEstudio = spcNivelEstudio;
//	}

	public SpcNumeroPeriodoEscolar getSpcNumeroPeriodoEscolar() {
		return spcNumeroPeriodoEscolar;
	}

	public void setSpcNumeroPeriodoEscolar(
			SpcNumeroPeriodoEscolar spcNumeroPeriodoEscolar) {
		this.spcNumeroPeriodoEscolar = spcNumeroPeriodoEscolar;
	}

	public SpcOrfandad getSpcOrfandad() {
		return spcOrfandad;
	}

	public void setSpcOrfandad(SpcOrfandad spcOrfandad) {
		this.spcOrfandad = spcOrfandad;
	}

//	public SpcPeriodoEscolar getSpcPeriodoEscolar() {
//		return spcPeriodoEscolar;
//	}
//
//	public void setSpcPeriodoEscolar(SpcPeriodoEscolar spcPeriodoEscolar) {
//		this.spcPeriodoEscolar = spcPeriodoEscolar;
//	}

	public SpcReformaLey getSpcReformaLey() {
		return spcReformaLey;
	}

	public void setSpcReformaLey(SpcReformaLey spcReformaLey) {
		this.spcReformaLey = spcReformaLey;
	}

	public SptBeneficiarioSolicitud getSptBeneficiarioSolicitud() {
		return sptBeneficiarioSolicitud;
	}

	public void setSptBeneficiarioSolicitud(
			SptBeneficiarioSolicitud sptBeneficiarioSolicitud) {
		this.sptBeneficiarioSolicitud = sptBeneficiarioSolicitud;
	}
	
	

}