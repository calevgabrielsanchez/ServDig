package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the SPT_DET_PREVALIDA_SINDO_ENV database table.
 * 
 */
@Entity
@Table(name="SPT_DET_PREVALIDA_SINDO_ENV")
@NamedQuery(name="SptDetPrevalidaSindoEnv.findAll", query="SELECT s FROM SptDetPrevalidaSindoEnv s")
public class SptDetPrevalidaSindoEnv implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTDETPREVALIDASINDOENV", sequenceName = "SEQ_SPTDETPREVALIDASINDOENV")
	@GeneratedValue(generator = "SEQ_SPTDETPREVALIDASINDOENV")
	@Column(name="CVE_ID_DET_PREVALIDA_SINDO_ENV")
	private long cveIdDetPrevalidaSindoEnv;

	@Column(name="CVE_PRE_SOL_ENTRADA")
	private String cvePreSolEntrada;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_ENVIO")
	private Date fecEnvio;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="ID_LOGIN_SINDO")
	private String idLoginSindo;

	@Column(name="ID_LOGIN_SISTRAP")
	private String idLoginSistrap;

	//bi-directional many-to-one association to SptEnvComunicEntidade
	@ManyToOne
	@JoinColumn(name="CVE_ID_ENV_COMUNIC_ENTIDADES")
	private SptEnvComunicEntidade sptEnvComunicEntidade;

	public SptDetPrevalidaSindoEnv() {
	}

	public long getCveIdDetPrevalidaSindoEnv() {
		return this.cveIdDetPrevalidaSindoEnv;
	}

	public void setCveIdDetPrevalidaSindoEnv(long cveIdDetPrevalidaSindoEnv) {
		this.cveIdDetPrevalidaSindoEnv = cveIdDetPrevalidaSindoEnv;
	}

	public String getCvePreSolEntrada() {
		return this.cvePreSolEntrada;
	}

	public void setCvePreSolEntrada(String cvePreSolEntrada) {
		this.cvePreSolEntrada = cvePreSolEntrada;
	}

	public Date getFecEnvio() {
		return this.fecEnvio;
	}

	public void setFecEnvio(Date fecEnvio) {
		this.fecEnvio = fecEnvio;
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

	public String getIdLoginSindo() {
		return this.idLoginSindo;
	}

	public void setIdLoginSindo(String idLoginSindo) {
		this.idLoginSindo = idLoginSindo;
	}

	public String getIdLoginSistrap() {
		return this.idLoginSistrap;
	}

	public void setIdLoginSistrap(String idLoginSistrap) {
		this.idLoginSistrap = idLoginSistrap;
	}

	public SptEnvComunicEntidade getSptEnvComunicEntidade() {
		return this.sptEnvComunicEntidade;
	}

	public void setSptEnvComunicEntidade(SptEnvComunicEntidade sptEnvComunicEntidade) {
		this.sptEnvComunicEntidade = sptEnvComunicEntidade;
	}

}