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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="DIT_DOCTO_REQ_TRAM_ORIGEN_SOL")
public class DitDoctoReqTramOrigenSol implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2619669480632638459L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_DOCTO_REQ_TRAM_ORG_SOL")
	private long cveIdDoctoReqTramOrgSol;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTROS_BAJA")
	private Date fecRegistrosBaja;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;
	
	
	@Column(name="IND_DOCTO_REQ_CAPTURA")
	private Integer indDoctoReqCaptura;
	
	@Column(name="IND_DOCTO_OPCIONAL")
	private Integer indDoctoOpcional; 

	@Column(name="IND_DOCTO_REQ_DIGITALIZACION")
	private Integer indDoctoReqDigitalizacion; 
	
	@Column(name="REF_DETALLE_DOCTO_REQUERIDO")
	private String refDetalleDoctoRequerido; 
	
	
	
	//bi-directional many-to-one association to DicModulo
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_TRAMITE" , insertable=false ,updatable=false)
	private DicTipoTramite dicTipoTramite;
	
	
  //bi-directional many-to-one association to DicModulo
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_DOCTO_PROB_POR_TIPO" , insertable=false ,updatable=false)
	private DitDocumentoPorTipo ditDocumentoPorTipo;

    
 // bi-directional many-to-one association to DicOrigenSolicitud
 	@ManyToOne(fetch=FetchType.LAZY)
 	@JoinColumn(name = "CVE_ID_ORIGEN_SOLICITUD" , insertable=false ,updatable=false)
 	private DicOrigenSolicitud dicOrigenSolicitud;

 	
	@Column(name="IND_ORDEN_LISTA")
	private Long indOrdenLista; 
	
	
	@Column(name="CVE_ID_TIPO_PERSONA")
	private Long cveIdTipoPersona; 

	//agrega columna para distincion de area solicitante
	@Column(name="CVE_TIPO_AREA")
	private Long cveTipoArea; 
	
	public long getCveIdDoctoReqTramOrgSol() {
		return cveIdDoctoReqTramOrgSol;
	}


	public void setCveIdDoctoReqTramOrgSol(long cveIdDoctoReqTramOrgSol) {
		this.cveIdDoctoReqTramOrgSol = cveIdDoctoReqTramOrgSol;
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
	 * @return the fecRegistrosBaja
	 */
	public Date getFecRegistrosBaja() {
		return fecRegistrosBaja;
	}


	/**
	 * @param fecRegistrosBaja the fecRegistrosBaja to set
	 */
	public void setFecRegistrosBaja(Date fecRegistrosBaja) {
		this.fecRegistrosBaja = fecRegistrosBaja;
	}


	/**
	 * @return the fecRegistroActualizado
	 */
	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}


	/**
	 * @param fecRegistroActualizado the fecRegistroActualizado to set
	 */
	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}


	/**
	 * @return the dicTipoTramite
	 */
	public DicTipoTramite getDicTipoTramite() {
		return dicTipoTramite;
	}


	/**
	 * @param dicTipoTramite the dicTipoTramite to set
	 */
	public void setDicTipoTramite(DicTipoTramite dicTipoTramite) {
		this.dicTipoTramite = dicTipoTramite;
	}


	public DitDocumentoPorTipo getDitDocumentoPorTipo() {
		return ditDocumentoPorTipo;
	}


	public void setDitDocumentoPorTipo(DitDocumentoPorTipo ditDocumentoPorTipo) {
		this.ditDocumentoPorTipo = ditDocumentoPorTipo;
	}


	public Integer getIndDoctoReqCaptura() {
		return indDoctoReqCaptura;
	}


	public void setIndDoctoReqCaptura(Integer indDoctoReqCaptura) {
		this.indDoctoReqCaptura = indDoctoReqCaptura;
	}


	public Integer getIndDoctoOpcional() {
		return indDoctoOpcional;
	}


	public void setIndDoctoOpcional(Integer indDoctoOpcional) {
		this.indDoctoOpcional = indDoctoOpcional;
	}



/**
	 * @return the indDoctoReqDigitalizacion
	 */
	public Integer getIndDoctoReqDigitalizacion() {
		return indDoctoReqDigitalizacion;
	}

	/**
	 * @param indDoctoReqDigitalizacion the indDoctoReqDigitalizacion to set
	 */
	public void setIndDoctoReqDigitalizacion(Integer indDoctoReqDigitalizacion) {
		this.indDoctoReqDigitalizacion = indDoctoReqDigitalizacion;
	}


	public DicOrigenSolicitud getDicOrigenSolicitud() {
		return dicOrigenSolicitud;
	}

	public void setDicOrigenSolicitud(DicOrigenSolicitud dicOrigenSolicitud) {
		this.dicOrigenSolicitud = dicOrigenSolicitud;
	}


	public String getRefDetalleDoctoRequerido() {
		return refDetalleDoctoRequerido;
	}


	public void setRefDetalleDoctoRequerido(String refDetalleDoctoRequerido) {
		this.refDetalleDoctoRequerido = refDetalleDoctoRequerido;
	}


	public Long getIndOrdenLista() {
		return indOrdenLista;
	}


	public void setIndOrdenLista(Long indOrdenLista) {
		this.indOrdenLista = indOrdenLista;
	}


	public Long getCveIdTipoPersona() {
		return cveIdTipoPersona;
	}


	public void setCveIdTipoPersona(Long cveIdTipoPersona) {
		this.cveIdTipoPersona = cveIdTipoPersona;
	}
	
	public Long getCveTipoArea() {
		return cveTipoArea;
	}

	public void setCveTipoArea(Long cveTipoArea) {
		this.cveTipoArea = cveTipoArea;
	}
	
	
}
