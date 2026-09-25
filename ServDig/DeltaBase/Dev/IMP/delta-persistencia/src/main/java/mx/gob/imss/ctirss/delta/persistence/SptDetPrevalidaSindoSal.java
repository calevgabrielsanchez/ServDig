package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the SPT_DET_PREVALIDA_SINDO_SAL database table.
 * 
 */
@Entity
@Table(name="SPT_DET_PREVALIDA_SINDO_SAL")
@NamedQuery(name="SptDetPrevalidaSindoSal.findAll", query="SELECT s FROM SptDetPrevalidaSindoSal s")
public class SptDetPrevalidaSindoSal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTDETPREVALIDASINDOSAL", sequenceName = "SEQ_SPTDETPREVALIDASINDOSAL")
	@GeneratedValue(generator = "SEQ_SPTDETPREVALIDASINDOSAL")
	@Column(name="CVE_ID_DET_PREVALIDA_SINDO_SAL")
	private long cveIdDetPrevalidaSindoSal;

	@Column(name="COD_ERROR_SALIDA")
	private String codErrorSalida;

	@Column(name="COD_RESP_SALIDA")
	private String codRespSalida;

	@Column(name="CVE_REG_PAT_SALIDA")
	private String cveRegPatSalida;

	@Column(name="DES_COD_ERROR_SALIDA")
	private String desCodErrorSalida;

	@Column(name="DES_RESPUESTA_SALIDA")
	private String desRespuestaSalida;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to SptRespComunicEntidade
	@ManyToOne
	@JoinColumn(name="CVE_ID_RESP_COMUNIC_ENTIDADES")
	private SptRespComunicEntidade sptRespComunicEntidade;

	public SptDetPrevalidaSindoSal() {
	}

	public long getCveIdDetPrevalidaSindoSal() {
		return this.cveIdDetPrevalidaSindoSal;
	}

	public void setCveIdDetPrevalidaSindoSal(long cveIdDetPrevalidaSindoSal) {
		this.cveIdDetPrevalidaSindoSal = cveIdDetPrevalidaSindoSal;
	}

	public String getCodErrorSalida() {
		return this.codErrorSalida;
	}

	public void setCodErrorSalida(String codErrorSalida) {
		this.codErrorSalida = codErrorSalida;
	}

	public String getCodRespSalida() {
		return this.codRespSalida;
	}

	public void setCodRespSalida(String codRespSalida) {
		this.codRespSalida = codRespSalida;
	}

	public String getCveRegPatSalida() {
		return this.cveRegPatSalida;
	}

	public void setCveRegPatSalida(String cveRegPatSalida) {
		this.cveRegPatSalida = cveRegPatSalida;
	}

	public String getDesCodErrorSalida() {
		return this.desCodErrorSalida;
	}

	public void setDesCodErrorSalida(String desCodErrorSalida) {
		this.desCodErrorSalida = desCodErrorSalida;
	}

	public String getDesRespuestaSalida() {
		return this.desRespuestaSalida;
	}

	public void setDesRespuestaSalida(String desRespuestaSalida) {
		this.desRespuestaSalida = desRespuestaSalida;
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

	public SptRespComunicEntidade getSptRespComunicEntidade() {
		return this.sptRespComunicEntidade;
	}

	public void setSptRespComunicEntidade(SptRespComunicEntidade sptRespComunicEntidade) {
		this.sptRespComunicEntidade = sptRespComunicEntidade;
	}

}