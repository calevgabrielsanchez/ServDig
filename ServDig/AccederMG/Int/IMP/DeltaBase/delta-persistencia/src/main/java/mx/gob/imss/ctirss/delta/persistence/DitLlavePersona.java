package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DIT_LLAVE_PERSONA database table.
 * 
 */
@Entity
@Table(name="DIT_LLAVE_PERSONA")
public class DitLlavePersona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String curp;

	@Column(name="CVE_ENT")
	private String cveEnt;

	@Column(name="CVE_ID_ESTADO_CIVIL")
	private BigDecimal cveIdEstadoCivil;

	@Column(name="CVE_ID_PAIS")
	private BigDecimal cveIdPais;

	@Column(name="CVE_ID_PERSONA")
	private BigDecimal cveIdPersona;

	@Column(name="CVE_ID_SEXO")
	private BigDecimal cveIdSexo;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_DEFUNCION")
	private Date fecDefuncion;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_NACIMIENTO")
	private Date fecNacimiento;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_PER_AUTORIZADA")
	private BigDecimal indPerAutorizada;

	@Column(name="NOM_NOMBRE")
	private String nomNombre;

	@Column(name="NOM_PRIMER_APELLIDO")
	private String nomPrimerApellido;

	@Column(name="NOM_SEGUNDO_APELLIDO")
	private String nomSegundoApellido;

	@Column(name="NUM_ANIO_NAC_REG")
	private BigDecimal numAnioNacReg;

	@Column(name="NUM_MES_NAC_REG")
	private BigDecimal numMesNacReg;

	private String observaciones;

	private String rfc;

	public DitLlavePersona() {
	}

	public String getCurp() {
		return this.curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getCveEnt() {
		return this.cveEnt;
	}

	public void setCveEnt(String cveEnt) {
		this.cveEnt = cveEnt;
	}

	public BigDecimal getCveIdEstadoCivil() {
		return this.cveIdEstadoCivil;
	}

	public void setCveIdEstadoCivil(BigDecimal cveIdEstadoCivil) {
		this.cveIdEstadoCivil = cveIdEstadoCivil;
	}

	public BigDecimal getCveIdPais() {
		return this.cveIdPais;
	}

	public void setCveIdPais(BigDecimal cveIdPais) {
		this.cveIdPais = cveIdPais;
	}

	public BigDecimal getCveIdPersona() {
		return this.cveIdPersona;
	}

	public void setCveIdPersona(BigDecimal cveIdPersona) {
		this.cveIdPersona = cveIdPersona;
	}

	public BigDecimal getCveIdSexo() {
		return this.cveIdSexo;
	}

	public void setCveIdSexo(BigDecimal cveIdSexo) {
		this.cveIdSexo = cveIdSexo;
	}

	public Date getFecDefuncion() {
		return this.fecDefuncion;
	}

	public void setFecDefuncion(Date fecDefuncion) {
		this.fecDefuncion = fecDefuncion;
	}

	public Date getFecNacimiento() {
		return this.fecNacimiento;
	}

	public void setFecNacimiento(Date fecNacimiento) {
		this.fecNacimiento = fecNacimiento;
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

	public BigDecimal getIndPerAutorizada() {
		return this.indPerAutorizada;
	}

	public void setIndPerAutorizada(BigDecimal indPerAutorizada) {
		this.indPerAutorizada = indPerAutorizada;
	}

	public String getNomNombre() {
		return this.nomNombre;
	}

	public void setNomNombre(String nomNombre) {
		this.nomNombre = nomNombre;
	}

	public String getNomPrimerApellido() {
		return this.nomPrimerApellido;
	}

	public void setNomPrimerApellido(String nomPrimerApellido) {
		this.nomPrimerApellido = nomPrimerApellido;
	}

	public String getNomSegundoApellido() {
		return this.nomSegundoApellido;
	}

	public void setNomSegundoApellido(String nomSegundoApellido) {
		this.nomSegundoApellido = nomSegundoApellido;
	}

	public BigDecimal getNumAnioNacReg() {
		return this.numAnioNacReg;
	}

	public void setNumAnioNacReg(BigDecimal numAnioNacReg) {
		this.numAnioNacReg = numAnioNacReg;
	}

	public BigDecimal getNumMesNacReg() {
		return this.numMesNacReg;
	}

	public void setNumMesNacReg(BigDecimal numMesNacReg) {
		this.numMesNacReg = numMesNacReg;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getRfc() {
		return this.rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

}