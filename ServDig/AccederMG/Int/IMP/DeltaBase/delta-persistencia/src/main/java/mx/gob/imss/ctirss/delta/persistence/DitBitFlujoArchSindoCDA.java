package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="DIT_BIT_FLUJO_ARCH_SINDOCDA")
public class DitBitFlujoArchSindoCDA implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5840581100888869859L;
	
	@Id
	@Column(name="REF_FOLIO_SOLICITUD", nullable=false, length=50)
	private String refFolioSolicitud;
	
	@Column(name="IND_CIZ1")
	private Boolean indCiz1;
	
	@Column(name="IND_CIZ2")
	private Boolean indCiz2;
	
	@Column(name="IND_CIZ3")
	private Boolean indCiz3;
	
	@Column(name="IND_CA")
	private Boolean indCA;
	
	@Column(name="IND_HC")
	private Boolean indHC;
	
	@Column(name = "IND_VALIDA")
	private String indValida;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta = new Date();
	
	@Column(name="REF_ESTADO_TRAMITE", nullable=true, length=5)
	private String refEstadoTramite;
	
	@Column(name="REF_ESTADO_SOLICITUD", nullable=true, length=5)
	private String refEstadoSolicitud;
	
	@Column(name="REF_OBSERVACION", nullable=true, length=2056)
	private String refObservacion;

	public String getRefFolioSolicitud() {
		return refFolioSolicitud;
	}

	public void setRefFolioSolicitud(String refFolioSolicitud) {
		this.refFolioSolicitud = refFolioSolicitud;
	}

	public Boolean getIndCiz1() {
		return indCiz1;
	}

	public void setIndCiz1(Boolean indCiz1) {
		this.indCiz1 = indCiz1;
	}

	public Boolean getIndCiz2() {
		return indCiz2;
	}

	public void setIndCiz2(Boolean indCiz2) {
		this.indCiz2 = indCiz2;
	}

	public Boolean getIndCiz3() {
		return indCiz3;
	}

	public void setIndCiz3(Boolean indCiz3) {
		this.indCiz3 = indCiz3;
	}

	public Boolean getIndCA() {
		return indCA;
	}

	public void setIndCA(Boolean indCA) {
		this.indCA = indCA;
	}

	public Boolean getIndHC() {
		return indHC;
	}

	public void setIndHC(Boolean indHC) {
		this.indHC = indHC;
	}

	public String getIndValida() {
		return indValida;
	}

	public void setIndValida(String indValida) {
		this.indValida = indValida;
	}

	public Date getFecRegistroBaja() {
		return null == this.fecRegistroBaja ? null : (Date) this.fecRegistroBaja.clone();
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = (null == fecRegistroBaja ? null : (Date) fecRegistroBaja.clone());
	}

	public Date getFecRegistroAlta() {
		return null == this.fecRegistroAlta ? new Date() : (Date) this.fecRegistroAlta.clone();
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = (null == fecRegistroAlta ? null : (Date) fecRegistroAlta.clone());
	}

	public String getRefEstadoTramite() {
		return refEstadoTramite;
	}

	public void setRefEstadoTramite(String refEstadoTramite) {
		this.refEstadoTramite = refEstadoTramite;
	}

	public String getRefEstadoSolicitud() {
		return refEstadoSolicitud;
	}

	public void setRefEstadoSolicitud(String refEstadoSolicitud) {
		this.refEstadoSolicitud = refEstadoSolicitud;
	}

	public String getRefObservacion() {
		return refObservacion;
	}

	public void setRefObservacion(String refObservacion) {
		this.refObservacion = refObservacion;
	}
	
	
}
