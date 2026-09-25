package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "DIT_DOCTO_CDA_NSS_BEN_REP")
public class DitDoctoCdaNssBenRep implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQDITDOCTOCDANSSBENREP", sequenceName = "SEQDITDOCTOCDANSSBENREP")
    @GeneratedValue(generator = "SEQDITDOCTOCDANSSBENREP")
	@Column(name = "CVE_ID_DOCTO_CDA_ASE_BEN_REP")
	private Long cveIdDoctoCdaAseBenRep;

	@Column(name = "CVE_ID_DOCUMENTO_PROBATORIO")
	private Long cveIdDocumentoProbatorio;

	@Column(name = "CVE_ID_TRAMITE")
	private Long cveIdTramite;

	@Column(name = "CVE_ID_CORRECCION_DATOS_ASEG")
	private Long cveIDCorreccionDatosAseg;

	@Column(name = "CVE_ID_DETALLE_NSS_CDA")
	private Long cveIdDetalleNssCda;
	
	@Column(name = "CVE_ID_ORIGEN_DOCTO")
	private Long cveIdOrigenDocto;
		
	@Column(name = "IND_INFORMACION_ADICIONAL")
	private Long indInformacionAdicional;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	
	
	public Long getIndInformacionAdicional() {
		return indInformacionAdicional;
	}

	public void setIndInformacionAdicional(Long indInformacionAdicional) {
		this.indInformacionAdicional = indInformacionAdicional;
	}
	
	public Long getCveIdDoctoCdaAseBenRep() {
		return cveIdDoctoCdaAseBenRep;
	}

	public void setCveIdDoctoCdaAseBenRep(Long cveIdDoctoCdaAseBenRep) {
		this.cveIdDoctoCdaAseBenRep = cveIdDoctoCdaAseBenRep;
	}

	public Long getCveIdDocumentoProbatorio() {
		return cveIdDocumentoProbatorio;
	}

	public void setCveIdDocumentoProbatorio(Long cveIdDocumentoProbatorio) {
		this.cveIdDocumentoProbatorio = cveIdDocumentoProbatorio;
	}

	public Long getCveIdTramite() {
		return cveIdTramite;
	}

	public void setCveIdTramite(Long cveIdTramite) {
		this.cveIdTramite = cveIdTramite;
	}

	public Long getCveIDCorreccionDatosAseg() {
		return cveIDCorreccionDatosAseg;
	}

	public void setCveIDCorreccionDatosAseg(Long cveIDCorreccionDatosAseg) {
		this.cveIDCorreccionDatosAseg = cveIDCorreccionDatosAseg;
	}

	public Long getCveIdDetalleNssCda() {
		return cveIdDetalleNssCda;
	}

	public void setCveIdDetalleNssCda(Long cveIdDetalleNssCda) {
		this.cveIdDetalleNssCda = cveIdDetalleNssCda;
	}

	public Long getCveIdOrigenDocto() {
		return cveIdOrigenDocto;
	}

	public void setCveIdOrigenDocto(Long cveIdOrigenDocto) {
		this.cveIdOrigenDocto = cveIdOrigenDocto;
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
