package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SPT_DICTAMEN_LAUDO database table.
 * 
 */
@Entity
@Table(name="SPT_DICTAMEN_LAUDO")
@NamedQuery(name="SptDictamenLaudo.findAll", query="SELECT s FROM SptDictamenLaudo s")
public class SptDictamenLaudo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SPT_DICTAMEN_LAUDO_IDDICTAMENLAUDO_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SPT_DICTAMEN_LAUDO_IDDICTAMENLAUDO_GENERATOR")
	@Column(name="ID_DICTAMEN_LAUDO")
	private long idDictamenLaudo;

	@Column(name="CVE_NUMERO_EXPEDIENTE")
	private String cveNumeroExpediente;

	@Column(name="CVE_REG_PATRONAL")
	private String cveRegPatronal;

	private BigDecimal cveriesgotrabajo;

	@Column(name="DES_INCAPACIDAD")
	private String desIncapacidad;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_DEFUNCION")
	private Date fecDefuncion;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_ELABORA_DICTAMEN")
	private Date fecElaboraDictamen;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO_PENSION")
	private Date fecInicioPension;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_PADECIMIENTO")
	private Date fecPadecimiento;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_VENCIMIENTO")
	private Date fecVencimiento;

	@Column(name="IND_ESTADO_INVALIDEZ")
	private String indEstadoInvalidez;

	@Column(name="IND_POR_INV_MAYOR_75")
	private String indPorInvMayor75;

	@Column(name="POR_VALUACION")
	private BigDecimal porValuacion;

	//bi-directional many-to-one association to SpcCaracter
	@ManyToOne
	@JoinColumn(name="ID_CARACTER")
	private SpcCaracter spcCaracter;

	//bi-directional many-to-one association to SpcTipoFormato
	@ManyToOne
	@JoinColumn(name="ID_TIPO_FORMATO")
	private SpcTipoFormato spcTipoFormato;

	public SptDictamenLaudo() {
	}

	public long getIdDictamenLaudo() {
		return this.idDictamenLaudo;
	}

	public void setIdDictamenLaudo(long idDictamenLaudo) {
		this.idDictamenLaudo = idDictamenLaudo;
	}

	public String getCveNumeroExpediente() {
		return this.cveNumeroExpediente;
	}

	public void setCveNumeroExpediente(String cveNumeroExpediente) {
		this.cveNumeroExpediente = cveNumeroExpediente;
	}

	public String getCveRegPatronal() {
		return this.cveRegPatronal;
	}

	public void setCveRegPatronal(String cveRegPatronal) {
		this.cveRegPatronal = cveRegPatronal;
	}

	public BigDecimal getCveriesgotrabajo() {
		return this.cveriesgotrabajo;
	}

	public void setCveriesgotrabajo(BigDecimal cveriesgotrabajo) {
		this.cveriesgotrabajo = cveriesgotrabajo;
	}

	public String getDesIncapacidad() {
		return this.desIncapacidad;
	}

	public void setDesIncapacidad(String desIncapacidad) {
		this.desIncapacidad = desIncapacidad;
	}

	public Date getFecDefuncion() {
		return this.fecDefuncion;
	}

	public void setFecDefuncion(Date fecDefuncion) {
		this.fecDefuncion = fecDefuncion;
	}

	public Date getFecElaboraDictamen() {
		return this.fecElaboraDictamen;
	}

	public void setFecElaboraDictamen(Date fecElaboraDictamen) {
		this.fecElaboraDictamen = fecElaboraDictamen;
	}

	public Date getFecInicioPension() {
		return this.fecInicioPension;
	}

	public void setFecInicioPension(Date fecInicioPension) {
		this.fecInicioPension = fecInicioPension;
	}

	public Date getFecPadecimiento() {
		return this.fecPadecimiento;
	}

	public void setFecPadecimiento(Date fecPadecimiento) {
		this.fecPadecimiento = fecPadecimiento;
	}

	public Date getFecVencimiento() {
		return this.fecVencimiento;
	}

	public void setFecVencimiento(Date fecVencimiento) {
		this.fecVencimiento = fecVencimiento;
	}

	public String getIndEstadoInvalidez() {
		return this.indEstadoInvalidez;
	}

	public void setIndEstadoInvalidez(String indEstadoInvalidez) {
		this.indEstadoInvalidez = indEstadoInvalidez;
	}

	public String getIndPorInvMayor75() {
		return this.indPorInvMayor75;
	}

	public void setIndPorInvMayor75(String indPorInvMayor75) {
		this.indPorInvMayor75 = indPorInvMayor75;
	}

	public BigDecimal getPorValuacion() {
		return this.porValuacion;
	}

	public void setPorValuacion(BigDecimal porValuacion) {
		this.porValuacion = porValuacion;
	}

	public SpcCaracter getSpcCaracter() {
		return this.spcCaracter;
	}

	public void setSpcCaracter(SpcCaracter spcCaracter) {
		this.spcCaracter = spcCaracter;
	}

	public SpcTipoFormato getSpcTipoFormato() {
		return this.spcTipoFormato;
	}

	public void setSpcTipoFormato(SpcTipoFormato spcTipoFormato) {
		this.spcTipoFormato = spcTipoFormato;
	}

}