package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the SPT_DET_ENVIO_PROCESAR_SAL database table.
 * 
 */
@Entity
@Table(name="SPT_DET_ENVIO_PROCESAR_SAL")
@NamedQuery(name="SptDetEnvioProcesarSal.findAll", query="SELECT s FROM SptDetEnvioProcesarSal s")
public class SptDetEnvioProcesarSal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTDETENVIOPROCESARSAL", sequenceName = "SEQ_SPTDETENVIOPROCESARSAL")
	@GeneratedValue(generator = "SEQ_SPTDETENVIOPROCESARSAL")
	@Column(name="CVE_ID_DET_ENVIO_PROCESAR_SAL")
	private long cveIdDetEnvioProcesarSal;

	@Column(name="CVE_AFORE")
	private String cveAfore;

	@Column(name="DESC_MENSAJE_PROCESAR")
	private String descMensajeProcesar;

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
	@Column(name="FEC_VIGENCIA_MARCACION")
	private Date fecVigenciaMarcacion;

	@Column(name="ID_DIAGNOSTICO_CTAINDIVIDUAL")
	private String idDiagnosticoCtaindividual;

	//bi-directional many-to-one association to SptRespComunicEntidade
	@ManyToOne
	@JoinColumn(name="CVE_ID_RESP_COMUNIC_ENTIDADES")
	private SptRespComunicEntidade sptRespComunicEntidade;

	public SptDetEnvioProcesarSal() {
	}

	public long getCveIdDetEnvioProcesarSal() {
		return this.cveIdDetEnvioProcesarSal;
	}

	public void setCveIdDetEnvioProcesarSal(long cveIdDetEnvioProcesarSal) {
		this.cveIdDetEnvioProcesarSal = cveIdDetEnvioProcesarSal;
	}

	public String getCveAfore() {
		return this.cveAfore;
	}

	public void setCveAfore(String cveAfore) {
		this.cveAfore = cveAfore;
	}

	public String getDescMensajeProcesar() {
		return this.descMensajeProcesar;
	}

	public void setDescMensajeProcesar(String descMensajeProcesar) {
		this.descMensajeProcesar = descMensajeProcesar;
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

	public Date getFecVigenciaMarcacion() {
		return this.fecVigenciaMarcacion;
	}

	public void setFecVigenciaMarcacion(Date fecVigenciaMarcacion) {
		this.fecVigenciaMarcacion = fecVigenciaMarcacion;
	}

	public String getIdDiagnosticoCtaindividual() {
		return this.idDiagnosticoCtaindividual;
	}

	public void setIdDiagnosticoCtaindividual(String idDiagnosticoCtaindividual) {
		this.idDiagnosticoCtaindividual = idDiagnosticoCtaindividual;
	}

	public SptRespComunicEntidade getSptRespComunicEntidade() {
		return this.sptRespComunicEntidade;
	}

	public void setSptRespComunicEntidade(SptRespComunicEntidade sptRespComunicEntidade) {
		this.sptRespComunicEntidade = sptRespComunicEntidade;
	}

}