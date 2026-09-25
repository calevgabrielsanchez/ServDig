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
@Table(name="DIT_BITACORA_SEG_SOLICITUD")
public class DitBitacoraSegSolicitud implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIT_BITACORA_SEG_SOLICITUD_CVEIDSOLICITUDSEGIMIENTO_GENERATOR", sequenceName="SEQ_BITACORASEGSOLICITUD", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIT_BITACORA_SEG_SOLICITUD_CVEIDSOLICITUDSEGIMIENTO_GENERATOR")
	@Column(name="CVE_ID_SOLICITUD_SEGUIMIENTO", nullable=false, precision=22)
	private long cveIdSeguimiento;

	@Column(name="REF_OBSERVACIONES", length=255)
	private String refObservaciones;

	//bi-directional many-to-one association to DitSolicitud
   // @ManyToOne
	//@Column(name="CVE_ID_SOLICITUD")
	@Transient
	private DitSolicitud ditSolicitud;
	
    //bi-directional many-to-one association to DicEstadoSolicitud
	//@ManyToOne(fetch=FetchType.LAZY)
	//@JoinColumn(name="CVE_ID_ESTADO_SOLICITUD")
	@Transient
	private DicEstadoSolicitud dicEstadoSolicitud;
 
	@Column(name="CVE_CUENTA_USUARIO", length=20)
	private String cuentaUsuario;
	
	@Column(name="REF_IP_SOLICITUD", length=50)
	private String ipSolicitud;
	
	//bi-directional many-to-one association to DitPersona
	//@ManyToOne(fetch=FetchType.LAZY)
	//@Column(name="CVE_ID_PERSONA_USUARIO")
	@Transient
	private DitPersona ditPersona;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	 
	/**
	 * @return the cveIdSeguimiento
	 */
	public long getCveIdSeguimiento() {
		return cveIdSeguimiento;
	}

	/**
	 * @param cveIdSeguimiento the cveIdSeguimiento to set
	 */
	public void setCveIdSeguimiento(long cveIdSeguimiento) {
		this.cveIdSeguimiento = cveIdSeguimiento;
	}

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
	 * @return the ditSolicitud
	 */
	public DitSolicitud getDitSolicitud() {
		return ditSolicitud;
	}

	/**
	 * @param ditSolicitud the ditSolicitud to set
	 */
	public void setDitSolicitud(DitSolicitud ditSolicitud) {
		this.ditSolicitud = ditSolicitud;
	}

	/**
	 * @return the dicEstadoSolicitud
	 */
	public DicEstadoSolicitud getDicEstadoSolicitud() {
		return dicEstadoSolicitud;
	}

	/**
	 * @param dicEstadoSolicitud the dicEstadoSolicitud to set
	 */
	public void setDicEstadoSolicitud(DicEstadoSolicitud dicEstadoSolicitud) {
		this.dicEstadoSolicitud = dicEstadoSolicitud;
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
	 * @return the ipSolicitud
	 */
	public String getIpSolicitud() {
		return ipSolicitud;
	}

	/**
	 * @param ipSolicitud the ipSolicitud to set
	 */
	public void setIpSolicitud(String ipSolicitud) {
		this.ipSolicitud = ipSolicitud;
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

}