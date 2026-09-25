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
@Table(name="DIT_CORRECCION_DATOS_ASEG")
public class DitCorreccionDatosAsegurado implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "SEQ_DITCORRECCIONDATOSASEG", sequenceName = "SEQ_DITCORRECCIONDATOSASEG")
    @GeneratedValue(generator = "SEQ_DITCORRECCIONDATOSASEG")
	@Column(name="CVE_ID_CORRECCION_DATOS_ASEG")
	private Long cveIdCorreccionDatosAsegurado;
	
	@Column(name="IND_DEFUNCION")
	private int indDefuncion;

	/**FK**/	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_TRAMITE")
	private DitTramite tramite;
	
	@Column(name="DES_OBSERVACION", length=4000)
	private String descObservacion;
	
		
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;
	
	@Column(name="REF_CURP", length=20)
	private String refCurp;
	
	@Column(name="IND_TIPO_SOLICITUD_INFO")
	private Integer indTipoSolicitudInfo;
	
	@Column(name="IND_REPLEGAR_BENEFICIARIO")
	private Integer indReplegarBeneficiario;
	

	public Long getCveIdCorreccionDatosAsegurado() {
		return cveIdCorreccionDatosAsegurado;
	}

	public void setCveIdCorreccionDatosAsegurado(Long cveIdCorreccionDatosAsegurado) {
		this.cveIdCorreccionDatosAsegurado = cveIdCorreccionDatosAsegurado;
	}

	public int getIndDefuncion() {
		return indDefuncion;
	}

	public void setIndDefuncion(int indDefuncion) {
		this.indDefuncion = indDefuncion;
	}

	public String getDescObservacion() {
		return descObservacion;
	}

	public void setDescObservacion(String descObservacion) {
		this.descObservacion = descObservacion;
	}

	public DitTramite getTramite() {
		return tramite;
	}

	public void setTramite(DitTramite tramite) {
		this.tramite = tramite;
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

	public String getRefCurp() {
		return refCurp;
	}

	public void setRefCurp(String refCurp) {
		this.refCurp = refCurp;
	}
	
	
	public int getIndReplegarBeneficiario() {
		return indReplegarBeneficiario;
	}

	public void setIndReplegarBeneficiario(int indReplegarBeneficiario) {
		this.indReplegarBeneficiario = indReplegarBeneficiario;
	}
	
	public int getindTipoSolicitudInfo() {
		return indTipoSolicitudInfo;
	}

	public void setindTipoSolicitudInfo(int indTipoSolicitudInfo) {
		this.indTipoSolicitudInfo = indTipoSolicitudInfo;
	}
}
