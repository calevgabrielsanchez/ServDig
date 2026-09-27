package mx.gob.imss.ctirss.delta.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the BDTUT_ULTIMO_TRABAJO database table.
 * 
 */
@Entity
@Table(name="OPERBDTU.BDTUT_ULTIMO_TRABAJO")
public class BdtutUltimoTrabajo {
	
	@Id
	@SequenceGenerator(
		    name = "BDTUT_ULTIMO_TRABAJO_GENERATOR",
		    sequenceName = "OPERBDTU.SEQ_BDTUTULTIMOTRABAJO",
		    schema = "OPERBDTU",
		    allocationSize = 1
		)
		@GeneratedValue(
		    strategy = GenerationType.SEQUENCE,
		    generator = "BDTUT_ULTIMO_TRABAJO_GENERATOR"
		)
		@Column(
		    name = "ID_ULTIMO_TRABAJO",
		    nullable = false,
		    precision = 22
		)
		private long idUltimoTrabajo;
	
	@Column(name="CVE_NSS", length=11)
	private String cveNss;

	@Column(name="REF_REGISTRO_PATRONAL")
	private String refRegistroPatronal;

	@Column(name="CVE_ENT_INEGI")
	private String cveEntInegi;

	@Column(name="CVE_MUN_INEGI")
	private String cveMunInegi;

	@Column(name="CVE_ID_MUNICIPIO_IMSS", precision=22, scale=0)
	private Long cveIdMunicipioImss;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FEC_CONSULTA")
	private Date fecConsulta;

	@Column(name="CVE_MUNICIPIO_IMSS", length=5)
	private String cveMunicipioImss;

	@Column(name="NUM_ANIO_ULTIMO_TRABAJO", precision=4, scale=0)
	private Integer numAnioUltimoTrabajo;

	@Column(name="NUM_MES_ULTIMO_TRABAJO", precision=2, scale=0)
	private Integer numMesUltimoTrabajo;

	@Column(name="CVE_MODALIDAD", precision=2, scale=0)
	private Integer cveModalidad;

	@Column(name="NUM_SALARIO_ULTIMO_TRABAJO", precision=15, scale=2)
	private BigDecimal numSalarioUltimoTrabajo;

	@Column(name="NUM_SEMANAS_RO_ULT_5ANIOS", precision=3, scale=0)
	private Integer numSemanasRoUlt5anios;

	@Column(name="IND_PENSION", precision=1, scale=0)
	private Integer indPension;

	@Column(name="IND_TRABAJADOR_IMSS", precision=1, scale=0)
	private Integer indTrabajadorImss;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="STP_ALTA")
	private Date stpAlta;

	@Column(name="CVE_USUARIO_ALTA", length=50)
	private String cveUsuarioAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="STP_MODIFICA")
	private Date stpModifica;

	@Column(name="CVE_USUARIO_MODIFICA", length=50)
	private String cveUsuarioModifica;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="STP_BAJA")
	private Date stpBaja;

	@Column(name="CVE_USUARIO_BAJA", length=50)
	private String cveUsuarioBaja;

	@Column(name="CVE_CURP", length=18)
	private String cveCurp;

	@Column(name="CVE_RFC_ASEGURADO", length=13)
	private String cveRfcAsegurado;

	@Column(name="NOM_ASEGURADO", length=100)
	private String nomAsegurado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_BAJA_ULTIMO_TRABAJO")
	private Date fecBajaUltimoTrabajo;

	public void setCveNss(String value) { this.cveNss = value; }
	public void setRefRegistroPatronal(String value) { this.refRegistroPatronal = value; }
	public void setCveEntInegi(String value) { this.cveEntInegi = value; }
	public void setCveMunInegi(String value) { this.cveMunInegi = value; }
	public void setCveIdMunicipioImss(Long value) { this.cveIdMunicipioImss = value; }
	public void setFecConsulta(Date value) { this.fecConsulta = value; }
	public void setCveMunicipioImss(String value) { this.cveMunicipioImss = value; }
	public void setNumAnioUltimoTrabajo(Integer value) { this.numAnioUltimoTrabajo = value; }
	public void setNumMesUltimoTrabajo(Integer value) { this.numMesUltimoTrabajo = value; }
	public void setCveModalidad(Integer value) { this.cveModalidad = value; }
	public void setNumSalarioUltimoTrabajo(BigDecimal value) { this.numSalarioUltimoTrabajo = value; }
	public void setNumSemanasRoUlt5anios(Integer value) { this.numSemanasRoUlt5anios = value; }
	public void setIndPension(Integer value) { this.indPension = value; }
	public void setIndTrabajadorImss(Integer value) { this.indTrabajadorImss = value; }
	public void setStpAlta(Date value) { this.stpAlta = value; }
	public void setCveUsuarioAlta(String value) { this.cveUsuarioAlta = value; }
	public void setStpModifica(Date value) { this.stpModifica = value; }
	public void setCveUsuarioModifica(String value) { this.cveUsuarioModifica = value; }
	public void setStpBaja(Date value) { this.stpBaja = value; }
	public void setCveUsuarioBaja(String value) { this.cveUsuarioBaja = value; }
	public void setCveCurp(String value) { this.cveCurp = value; }
	public void setCveRfcAsegurado(String value) { this.cveRfcAsegurado = value; }
	public void setNomAsegurado(String value) { this.nomAsegurado = value; }
	public void setFecBajaUltimoTrabajo(Date value) { this.fecBajaUltimoTrabajo = value; }

}
