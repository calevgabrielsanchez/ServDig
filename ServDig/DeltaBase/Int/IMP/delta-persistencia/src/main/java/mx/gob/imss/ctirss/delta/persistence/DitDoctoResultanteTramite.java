package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "DIT_DOCTO_RESULTANTE_TRAMITE")
public class DitDoctoResultanteTramite implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_DITDOCTORESULTANTETRAMITE", sequenceName = "SEQ_DITDOCTORESULTANTETRAMITE")
    @GeneratedValue(generator = "SEQ_DITDOCTORESULTANTETRAMITE")
	@Column(name = "CVE_ID_DOC_RESULTANTE")
	private Long cveIdDocResultante;
	
	@ManyToOne
	@JoinColumn(name = "CVE_ID_DOCTO_PROB_POR_TIPO")
	private DitDocumentoPorTipo ditDocumentoPorTipo;
	
	@ManyToOne
	@JoinColumn(name = "CVE_ID_TRAMITE")
	private DitTramite ditTramite;
	
	@Lob()
	@Column(name = "REF_DOCUMENTO_RESULTANTE")
	private byte[] refDocumentoResultante;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ALTA")    
	private Date fecRegistroAlta;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_BAJA")    
	private Date fecRegistroBaja;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO") 
	private Date fecRegistroActualizado;

	public Long getCveIdDocResultante() {
		return cveIdDocResultante;
	}

	public void setCveIdDocResultante(Long cveIdDocResultante) {
		this.cveIdDocResultante = cveIdDocResultante;
	}

	public DitDocumentoPorTipo getDitDocumentoPorTipo() {
		return ditDocumentoPorTipo;
	}

	public void setDitDocumentoPorTipo(DitDocumentoPorTipo ditDocumentoPorTipo) {
		this.ditDocumentoPorTipo = ditDocumentoPorTipo;
	}

	public DitTramite getDitTramite() {
		return ditTramite;
	}

	public void setDitTramite(DitTramite ditTramite) {
		this.ditTramite = ditTramite;
	}

	public byte[] getRefDocumentoResultante() {
		return refDocumentoResultante;
	}

	public void setRefDocumentoResultante(byte[] refDocumentoResultante) {
		this.refDocumentoResultante = refDocumentoResultante != null ? refDocumentoResultante.clone() : null;
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

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}
	
	
}
