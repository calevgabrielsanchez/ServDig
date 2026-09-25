package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the SPT_DET_IMPRES_DOCTOS_PENSION database table.
 * 
 */
@Entity
@Table(name="SPT_DET_IMPRES_DOCTOS_PENSION")
@NamedQuery(name="SptDetImpresDoctosPension.findAll", query="SELECT s FROM SptDetImpresDoctosPension s")
public class SptDetImpresDoctosPension implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTDETIMPRESDOCTOSPENSION", sequenceName = "SEQ_SPTDETIMPRESDOCTOSPENSION")
	@GeneratedValue(generator = "SEQ_SPTDETIMPRESDOCTOSPENSION")
	@Column(name="CVE_ID_DET_IMPRES_DOCTOS_PENSI")
	private long cveIdDetImpresDoctosPensi;

	@Column(name="CVE_CUENTA_USUARIO")
	private String cveCuentaUsuario;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REIMPRESION")
	private Date fecReimpresion;

	//bi-directional many-to-one association to DitDoctoResultanteTramite
	@ManyToOne
	@JoinColumn(name="CVE_ID_DOC_RESULTANTE")
	private DitDoctoResultanteTramite ditDoctoResultanteTramite;

	public SptDetImpresDoctosPension() {
	}

	public long getCveIdDetImpresDoctosPensi() {
		return this.cveIdDetImpresDoctosPensi;
	}

	public void setCveIdDetImpresDoctosPensi(long cveIdDetImpresDoctosPensi) {
		this.cveIdDetImpresDoctosPensi = cveIdDetImpresDoctosPensi;
	}

	public String getCveCuentaUsuario() {
		return this.cveCuentaUsuario;
	}

	public void setCveCuentaUsuario(String cveCuentaUsuario) {
		this.cveCuentaUsuario = cveCuentaUsuario;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public Date getFecReimpresion() {
		return this.fecReimpresion;
	}

	public void setFecReimpresion(Date fecReimpresion) {
		this.fecReimpresion = fecReimpresion;
	}

	public DitDoctoResultanteTramite getDitDoctoResultanteTramite() {
		return this.ditDoctoResultanteTramite;
	}

	public void setDitDoctoResultanteTramite(DitDoctoResultanteTramite ditDoctoResultanteTramite) {
		this.ditDoctoResultanteTramite = ditDoctoResultanteTramite;
	}

}