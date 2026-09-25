package mx.gob.imss.ctirss.correccion.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;


@MappedSuperclass
public class AbstractCrtMenuPatron extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CVE_PK")
	private Integer cvePk;
	
	@Column(name = "CVE_FK_MENU")
	private Integer cveFkMenu;

	@Column(name = "CVE_ID_MENU")
	private Integer cveIdMenu;	

	
	
	@Column(name = "DES_DESCRIPCION")
	private String desDescripcion;
	
	
	@Column(name = "DES_VINCULO")
	private String desVinculo;
	
		

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
	
	
	
	@Column(name = "FEC_SOLICITUDCORR")
	private String fecSolicitudCorr;
	
	
	@Column(name = "FEC_SOLICITUDPRORR")
	private String fecSolicitudProrr;
	
	
	
	@Column(name = "NUM_ORDEN")
	private Integer numOrden;
	
	
	
	@Column(name = "ELABORACION")
	private String elaboracion;
	
	
	
	@Column(name = "FEC_LIMITE_PRESENTACORR")
	private String fecLimitePresentaCorr;



	public Integer getCveFkMenu() {
		return cveFkMenu;
	}



	public void setCveFkMenu(Integer cveFkMenu) {
		this.cveFkMenu = cveFkMenu;
	}



	public Integer getCveIdMenu() {
		return cveIdMenu;
	}



	public void setCveIdMenu(Integer cveIdMenu) {
		this.cveIdMenu = cveIdMenu;
	}



	public Integer getCvePk() {
		return cvePk;
	}



	public void setCvePk(Integer cvePk) {
		this.cvePk = cvePk;
	}



	public String getDesDescripcion() {
		return desDescripcion;
	}



	public void setDesDescripcion(String desDescripcion) {
		this.desDescripcion = desDescripcion;
	}



	public String getDesVinculo() {
		return desVinculo;
	}



	public void setDesVinculo(String desVinculo) {
		this.desVinculo = desVinculo;
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



	public String getFecSolicitudCorr() {
		return fecSolicitudCorr;
	}



	public void setFecSolicitudCorr(String fecSolicitudCorr) {
		this.fecSolicitudCorr = fecSolicitudCorr;
	}



	public String getFecSolicitudProrr() {
		return fecSolicitudProrr;
	}



	public void setFecSolicitudProrr(String fecSolicitudProrr) {
		this.fecSolicitudProrr = fecSolicitudProrr;
	}



	public Integer getNumOrden() {
		return numOrden;
	}



	public void setNumOrden(Integer numOrden) {
		this.numOrden = numOrden;
	}



	public String getElaboracion() {
		return elaboracion;
	}



	public void setElaboracion(String elaboracion) {
		this.elaboracion = elaboracion;
	}



	public String getFecLimitePresentaCorr() {
		return fecLimitePresentaCorr;
	}



	public void setFecLimitePresentaCorr(String fecLimitePresentaCorr) {
		this.fecLimitePresentaCorr = fecLimitePresentaCorr;
	}
	
	
	
	
	
	
	
	
}
