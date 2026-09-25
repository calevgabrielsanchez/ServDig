package mx.gob.imss.ctirss.correccion.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
/**
 * Model para la tabla CRT_REVDERIVAFISCA

 * @since 07/08/2012
 */

@Entity
@Table(name="CRT_REVDERIVAFISCA")
public class CrtRevDerivAFisca extends AbstractModel{

	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CVE_REVDERIVAFIS", sequenceName="SEQ_CVE_REVDERIVAFIS")
	@GeneratedValue(generator="CVE_REVDERIVAFIS")
	@Column(name="CVE_REVDERIVAFIS")
	private Long cveRevDerivAFis;
	
	@Column(name="CVE_SOLICITUDCORR")
	private Integer cveSolicitudCorr;	

	@Column(name="NUM_FOLIO_OFICIO")
	private String numFolioOficio;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHADERIVA")
	private Date fechaDeriva;
    
	@Column(name="NOM_USER_DERIVA")
	private String nomUsuarioDeriva;	
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHASOLREACTIVA")
	private Date fechaSolReactiva;

	@Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHAENVIOSOL")
	private Date fechaEnvioSol;

	@Column(name="NUM_OFICIOENVIO")
	private String numOficioEnvio;	

	@Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHAREACTIVA")
	private Date fechaReactiva;

	@Column(name="NUM_OFICIOREACTIVA")
	private String numOficioReactiva;	

	@Column(name="NOM_USER_REACTIVA")
	private String nomUserReactiva;	
	
	@Column(name="TX_OBSERVACIONES")
	private String txObservaciones;	
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_FECHAREG")
	private Date fecFechaReg;
	
	@Column(name="CVE_USUARIO")
	private String cveUsuario;

	public Long getCveRevDerivAFis() {
		return cveRevDerivAFis;
	}

	public void setCveRevDerivAFis(Long cveRevDerivAFis) {
		this.cveRevDerivAFis = cveRevDerivAFis;
	}

	public Integer getCveSolicitudCorr() {
		return cveSolicitudCorr;
	}

	public void setCveSolicitudCorr(Integer cveSolicitudCorr) {
		this.cveSolicitudCorr = cveSolicitudCorr;
	}

	public String getNumFolioOficio() {
		return numFolioOficio;
	}

	public void setNumFolioOficio(String numFolioOficio) {
		this.numFolioOficio = numFolioOficio;
	}

	public Date getFechaDeriva() {
		return fechaDeriva;
	}

	public void setFechaDeriva(Date fechaDeriva) {
		this.fechaDeriva = fechaDeriva;
	}

	public String getNomUsuarioDeriva() {
		return nomUsuarioDeriva;
	}

	public void setNomUsuarioDeriva(String nomUsuarioDeriva) {
		this.nomUsuarioDeriva = nomUsuarioDeriva;
	}

	public Date getFechaSolReactiva() {
		return fechaSolReactiva;
	}

	public void setFechaSolReactiva(Date fechaSolReactiva) {
		this.fechaSolReactiva = fechaSolReactiva;
	}

	public Date getFechaEnvioSol() {
		return fechaEnvioSol;
	}

	public void setFechaEnvioSol(Date fechaEnvioSol) {
		this.fechaEnvioSol = fechaEnvioSol;
	}

	public String getNumOficioEnvio() {
		return numOficioEnvio;
	}

	public void setNumOficioEnvio(String numOficioEnvio) {
		this.numOficioEnvio = numOficioEnvio;
	}

	public Date getFechaReactiva() {
		return fechaReactiva;
	}

	public void setFechaReactiva(Date fechaReactiva) {
		this.fechaReactiva = fechaReactiva;
	}

	public String getNumOficioReactiva() {
		return numOficioReactiva;
	}

	public void setNumOficioReactiva(String numOficioReactiva) {
		this.numOficioReactiva = numOficioReactiva;
	}

	public String getNomUserReactiva() {
		return nomUserReactiva;
	}

	public void setNomUserReactiva(String nomUserReactiva) {
		this.nomUserReactiva = nomUserReactiva;
	}

	public String getTxObservaciones() {
		return txObservaciones;
	}

	public void setTxObservaciones(String txObservaciones) {
		this.txObservaciones = txObservaciones;
	}

	public Date getFecFechaReg() {
		return fecFechaReg;
	}

	public void setFecFechaReg(Date fecFechaReg) {
		this.fecFechaReg = fecFechaReg;
	}

	public String getCveUsuario() {
		return cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
