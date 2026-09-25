package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * The persistent class for the DIT_BITACORA database table.
 * 
 */
@Entity
@Table(name="DIT_BITACORA_SEG_TRAMITE")
public class DitBitacoraSegTramite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_DITBITACORASEGTRAMITE", sequenceName="SEQ_DITBITACORASEGTRAMITE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_DITBITACORASEGTRAMITE")
	@Column(name="CVE_BITACORA_SEG_TRAMITE", nullable=false, precision=22)
	private long cveIdSeguimientoTramite;

	@Column(name="REF_OBSERVACIONES", length=255)
	private String refObservaciones;

	//bi-directional many-to-one association to DitSolicitud
   // @ManyToOne
	//@Column(name="CVE_ID_TRAMITE")
	@Transient
	private DitTramite ditTramite;
	
    //bi-directional many-to-one association to DicEstadoSolicitud
	//@ManyToOne(fetch=FetchType.LAZY)
	//@JoinColumn(name="CVE_ID_ESTADO_TRAMITE")
	@Transient
	private DicEstadoTramite dicEstadoTramite;
 
	@Column(name="CVE_CUENTA_USUARIO", length=20)
	private String cuentaUsuario;
	
	@Column(name="REF_IP_TRAMITE", length=50)
	private String ipTramite;
	
//	@Column(name="REF_DATOS_TRAMITE_XML_HIST", length=50)
//	private String refDatosTramiteXml;
	

	//bi-directional many-to-one association to DitPersona
	/*
	@ManyToOne(fetch=FetchType.LAZY)
	@Column(name="CVE_ID_PERSONA_USUARIO")
	*/
	@Transient
	private DitPersona ditPersona;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;


	/**
	 * @return the refObservaciones
	 */
	public String getRefObservaciones() {
		return refObservaciones;
	}

	/**
	 * @param refObservaciones the refObservaciones to set
	 */
	public void setRefObservaciones(String refObservaciones) {
		this.refObservaciones = refObservaciones;
	}

	/**
	 * @return the cuentaUsuario
	 */
	public String getCuentaUsuario() {
		return cuentaUsuario;
	}

	/**
	 * @param cuentaUsuario the cuentaUsuario to set
	 */
	public void setCuentaUsuario(String cuentaUsuario) {
		this.cuentaUsuario = cuentaUsuario;
	}

	/**
	 * @return the ditPersona
	 */
	public DitPersona getDitPersona() {
		return ditPersona;
	}

	/**
	 * @param ditPersona the ditPersona to set
	 */
	public void setDitPersona(DitPersona ditPersona) {
		this.ditPersona = ditPersona;
	}

	/**
	 * @return the fecRegistroAlta
	 */
	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	/**
	 * @param fecRegistroAlta the fecRegistroAlta to set
	 */
	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	/**
	 * @return the ditTramite
	 */
	public DitTramite getDitTramite() {
		return ditTramite;
	}

	/**
	 * @param ditTramite the ditTramite to set
	 */
	public void setDitTramite(DitTramite ditTramite) {
		this.ditTramite = ditTramite;
	}

	/**
	 * @return the dicEstadoTramite
	 */
	public DicEstadoTramite getDicEstadoTramite() {
		return dicEstadoTramite;
	}

	/**
	 * @param dicEstadoTramite the dicEstadoTramite to set
	 */
	public void setDicEstadoTramite(DicEstadoTramite dicEstadoTramite) {
		this.dicEstadoTramite = dicEstadoTramite;
	}

	/**
	 * @return the cveIdSeguimientoTramite
	 */
	public long getCveIdSeguimientoTramite() {
		return cveIdSeguimientoTramite;
	}

	/**
	 * @param cveIdSeguimientoTramite the cveIdSeguimientoTramite to set
	 */
	public void setCveIdSeguimientoTramite(long cveIdSeguimientoTramite) {
		this.cveIdSeguimientoTramite = cveIdSeguimientoTramite;
	}

	/**
	 * @return the ipTramite
	 */
	public String getIpTramite() {
		return ipTramite;
	}

	/**
	 * @param ipTramite the ipTramite to set
	 */
	public void setIpTramite(String ipTramite) {
		this.ipTramite = ipTramite;
	}

//	/**
//	 * @return the refDatosTramiteXml
//	 */
//	public String getRefDatosTramiteXml() {
//		return refDatosTramiteXml;
//	}
//
//	/**
//	 * @param refDatosTramiteXml the refDatosTramiteXml to set
//	 */
//	public void setRefDatosTramiteXml(String refDatosTramiteXml) {
//		this.refDatosTramiteXml = refDatosTramiteXml;
//	}

}