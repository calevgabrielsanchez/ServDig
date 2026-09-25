package mx.gob.imss.ctirss.correccion.catalogos.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;


/**
 * The persistent class for the CRT_TRABAJADORES database table.
 * @param <CrtEjertrabajador>
 * 
 */
@MappedSuperclass
public class AbstractCrcTrabajadores extends AbstractModel{
	private static final long serialVersionUID = 1L;

	private Long cveTrabajador;
	private Long cveSolicitudCorr;
	private String nuNss;
	private String txRfc;
	private String nombreAsegurado;
	private String apPaternoAsegurado;
	private String apMaternoAsegurado;
	private Date fecFechareg;
	private String cveUsuario;
	

	@Id
	@SequenceGenerator(name="SEQ_CVE_TRABAJADOR_GENERATOR", sequenceName="CRS_CVE_TRABAJADOR")
	@GeneratedValue(generator="SEQ_CVE_TRABAJADOR_GENERATOR")
	@Column(name = "CVE_TRABAJADOR", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getCveTrabajador() {
		return this.cveTrabajador;
	}

	public void setCveTrabajador(Long cveTrabajador) {
		this.cveTrabajador = cveTrabajador;
	}

	@Column(name = "CVE_SOLICITUDCORR")
	public Long getCveSolicitudCorr() {
		return cveSolicitudCorr;
	}

	public void setCveSolicitudCorr(Long cveSolicitudCorr) {
		this.cveSolicitudCorr = cveSolicitudCorr;
	}

	@Column(name = "NU_NSS", nullable = false, length = 11)
	public String getNuNss() {
		return this.nuNss;
	}

	public void setNuNss(String nuNss) {
		this.nuNss = nuNss;
	}

	@Column(name = "TX_RFC", length = 13)
	public String getTxRfc() {
		return this.txRfc;
	}

	public void setTxRfc(String txRfc) {
		this.txRfc = txRfc;
	}

	@Column(name = "NOMBRE_ASEGURADO", length = 30)
	public String getNombreAsegurado() {
		return this.nombreAsegurado;
	}

	public void setNombreAsegurado(String nombreAsegurado) {
		this.nombreAsegurado = nombreAsegurado==null ?nombreAsegurado:nombreAsegurado.toUpperCase();
	}

	@Column(name = "AP_PATERNO_ASEGURADO", length = 30)
	public String getApPaternoAsegurado() {
		return this.apPaternoAsegurado;
	}

	public void setApPaternoAsegurado(String apPaternoAsegurado) {
		this.apPaternoAsegurado =apPaternoAsegurado==null?apPaternoAsegurado: apPaternoAsegurado.toUpperCase();
	}

	@Column(name = "AP_MATERNO_ASEGURADO", length = 30)
	public String getApMaternoAsegurado() {
		return this.apMaternoAsegurado;
	}

	public void setApMaternoAsegurado(String apMaternoAsegurado) {
		this.apMaternoAsegurado = apMaternoAsegurado==null ? apMaternoAsegurado:apMaternoAsegurado.toUpperCase();
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_FECHAREG", length = 7)
	public Date getFecFechareg() {
		return this.fecFechareg;
	}

	public void setFecFechareg(Date fecFechareg) {
		this.fecFechareg = fecFechareg;
	}

	@Column(name = "CVE_USUARIO", length = 20)
	public String getCveUsuario() {
		return this.cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	public AbstractCrcTrabajadores(Long cveTrabajador,
			Long cveSolicitudCorr, String nuNss, String txRfc,
			String nombreAsegurado, String apPaternoAsegurado,
			String apMaternoAsegurado) {
		super();
		this.cveTrabajador = cveTrabajador;
		this.cveSolicitudCorr = cveSolicitudCorr;
		this.nuNss = nuNss;
		this.txRfc = txRfc;
		this.nombreAsegurado = nombreAsegurado;
		this.apPaternoAsegurado = apPaternoAsegurado;
		this.apMaternoAsegurado = apMaternoAsegurado;
	}

	public AbstractCrcTrabajadores() {
		super();
	}
	
	

}