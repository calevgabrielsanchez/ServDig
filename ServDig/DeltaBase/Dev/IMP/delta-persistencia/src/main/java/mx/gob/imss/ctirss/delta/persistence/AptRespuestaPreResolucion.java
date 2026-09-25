package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.persistence.SptRespComunicEntidade;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the APT_RESPUESTA_PRE_RESOLUCION database table.
 * 
 */
@Entity
@Table(name="APT_RESPUESTA_PRE_RESOLUCION")
public class AptRespuestaPreResolucion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_RESPUESTA_PRE_RESOLUCION")
	private long cveRespuestaPreResolucion;

	@Column(name="CVE_CURP")
	private String cveCurp;

	@Column(name="CVE_ERROR")
	private BigDecimal cveError;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_PRE_RESOLUCION")
	private Date fecPreResolucion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="ID_FOLIO")
	private BigDecimal idFolio;

	@Column(name="ID_NSS")
	private String idNss;

	@Column(name="ID_NUM_ENVIO")
	private BigDecimal idNumEnvio;

	@Column(name="ID_SOLICITUD")
	private String idSolicitud;

	@Column(name="IMP_APORTACION_CTA_IND")
	private BigDecimal impAportacionCtaInd;

	@Column(name="IMP_APORTACION_GOB_FED")
	private BigDecimal impAportacionGobFed;

	@Column(name="IMP_CUANTIA_BAS_DERECHOS_PROP")
	private BigDecimal impCuantiaBasDerechosProp;

	@Column(name="IMP_MONTO_CONST_CBDP")
	private BigDecimal impMontoConstCbdp;

	@Column(name="IMP_MONTO_CONST_RENTA_VITALICI")
	private BigDecimal impMontoConstRentaVitalici;

	@Column(name="IMP_MONTO_CONT_PMG")
	private BigDecimal impMontoContPmg;

	@Column(name="IMP_MONTO_RENTA_RET_CON_PUBA")
	private BigDecimal impMontoRentaRetConPuba;

	@Column(name="IMP_MONTO_RENTA_RET_SIN_PUBA")
	private BigDecimal impMontoRentaRetSinPuba;

	@Column(name="IMP_PMG")
	private BigDecimal impPmg;

	@Column(name="IMP_PORC_APORT_GOB_FED")
	private BigDecimal impPorcAportGobFed;

	@Column(name="IMP_PORC_SALDO_CTA_IND")
	private BigDecimal impPorcSaldoCtaInd;

	@Column(name="IMP_PORC_SUMA_ASEG")
	private BigDecimal impPorcSumaAseg;

	@Column(name="IMP_PUBA")
	private BigDecimal impPuba;

	@Column(name="IMP_SUMA_ASEGURADA")
	private BigDecimal impSumaAsegurada;

	@Column(name="IND_ARTICULO_141")
	private String indArticulo141;

	@Column(name="IND_EXEDENTE_RECURSOS")
	private BigDecimal indExedenteRecursos;

	//bi-directional many-to-one association to AptEnvioPreResolucionEnv
    @ManyToOne
	@JoinColumn(name="CVE_ENVIO_PRE_RESOLUCION")
	private AptEnvioPreResolucionEnv aptEnvioPreResolucionEnv;

	//bi-directional many-to-one association to SptRespComunicEntidade
    @ManyToOne
	@JoinColumn(name="CVE_ID_RESP_COMUNIC_ENTIDADES")
	private SptRespComunicEntidade sptRespComunicEntidade;

    public AptRespuestaPreResolucion() {
    }

	public long getCveRespuestaPreResolucion() {
		return this.cveRespuestaPreResolucion;
	}

	public void setCveRespuestaPreResolucion(long cveRespuestaPreResolucion) {
		this.cveRespuestaPreResolucion = cveRespuestaPreResolucion;
	}

	public String getCveCurp() {
		return this.cveCurp;
	}

	public void setCveCurp(String cveCurp) {
		this.cveCurp = cveCurp;
	}

	public BigDecimal getCveError() {
		return this.cveError;
	}

	public void setCveError(BigDecimal cveError) {
		this.cveError = cveError;
	}

	public Date getFecPreResolucion() {
		return this.fecPreResolucion;
	}

	public void setFecPreResolucion(Date fecPreResolucion) {
		this.fecPreResolucion = fecPreResolucion;
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

	public BigDecimal getIdFolio() {
		return this.idFolio;
	}

	public void setIdFolio(BigDecimal idFolio) {
		this.idFolio = idFolio;
	}

	public String getIdNss() {
		return this.idNss;
	}

	public void setIdNss(String idNss) {
		this.idNss = idNss;
	}

	public BigDecimal getIdNumEnvio() {
		return this.idNumEnvio;
	}

	public void setIdNumEnvio(BigDecimal idNumEnvio) {
		this.idNumEnvio = idNumEnvio;
	}

	public String getIdSolicitud() {
		return this.idSolicitud;
	}

	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public BigDecimal getImpAportacionCtaInd() {
		return this.impAportacionCtaInd;
	}

	public void setImpAportacionCtaInd(BigDecimal impAportacionCtaInd) {
		this.impAportacionCtaInd = impAportacionCtaInd;
	}

	public BigDecimal getImpAportacionGobFed() {
		return this.impAportacionGobFed;
	}

	public void setImpAportacionGobFed(BigDecimal impAportacionGobFed) {
		this.impAportacionGobFed = impAportacionGobFed;
	}

	public BigDecimal getImpCuantiaBasDerechosProp() {
		return this.impCuantiaBasDerechosProp;
	}

	public void setImpCuantiaBasDerechosProp(BigDecimal impCuantiaBasDerechosProp) {
		this.impCuantiaBasDerechosProp = impCuantiaBasDerechosProp;
	}

	public BigDecimal getImpMontoConstCbdp() {
		return this.impMontoConstCbdp;
	}

	public void setImpMontoConstCbdp(BigDecimal impMontoConstCbdp) {
		this.impMontoConstCbdp = impMontoConstCbdp;
	}

	public BigDecimal getImpMontoConstRentaVitalici() {
		return this.impMontoConstRentaVitalici;
	}

	public void setImpMontoConstRentaVitalici(BigDecimal impMontoConstRentaVitalici) {
		this.impMontoConstRentaVitalici = impMontoConstRentaVitalici;
	}

	public BigDecimal getImpMontoContPmg() {
		return this.impMontoContPmg;
	}

	public void setImpMontoContPmg(BigDecimal impMontoContPmg) {
		this.impMontoContPmg = impMontoContPmg;
	}

	public BigDecimal getImpMontoRentaRetConPuba() {
		return this.impMontoRentaRetConPuba;
	}

	public void setImpMontoRentaRetConPuba(BigDecimal impMontoRentaRetConPuba) {
		this.impMontoRentaRetConPuba = impMontoRentaRetConPuba;
	}

	public BigDecimal getImpMontoRentaRetSinPuba() {
		return this.impMontoRentaRetSinPuba;
	}

	public void setImpMontoRentaRetSinPuba(BigDecimal impMontoRentaRetSinPuba) {
		this.impMontoRentaRetSinPuba = impMontoRentaRetSinPuba;
	}

	public BigDecimal getImpPmg() {
		return this.impPmg;
	}

	public void setImpPmg(BigDecimal impPmg) {
		this.impPmg = impPmg;
	}

	public BigDecimal getImpPorcAportGobFed() {
		return this.impPorcAportGobFed;
	}

	public void setImpPorcAportGobFed(BigDecimal impPorcAportGobFed) {
		this.impPorcAportGobFed = impPorcAportGobFed;
	}

	public BigDecimal getImpPorcSaldoCtaInd() {
		return this.impPorcSaldoCtaInd;
	}

	public void setImpPorcSaldoCtaInd(BigDecimal impPorcSaldoCtaInd) {
		this.impPorcSaldoCtaInd = impPorcSaldoCtaInd;
	}

	public BigDecimal getImpPorcSumaAseg() {
		return this.impPorcSumaAseg;
	}

	public void setImpPorcSumaAseg(BigDecimal impPorcSumaAseg) {
		this.impPorcSumaAseg = impPorcSumaAseg;
	}

	public BigDecimal getImpPuba() {
		return this.impPuba;
	}

	public void setImpPuba(BigDecimal impPuba) {
		this.impPuba = impPuba;
	}

	public BigDecimal getImpSumaAsegurada() {
		return this.impSumaAsegurada;
	}

	public void setImpSumaAsegurada(BigDecimal impSumaAsegurada) {
		this.impSumaAsegurada = impSumaAsegurada;
	}

	public String getIndArticulo141() {
		return this.indArticulo141;
	}

	public void setIndArticulo141(String indArticulo141) {
		this.indArticulo141 = indArticulo141;
	}

	public BigDecimal getIndExedenteRecursos() {
		return this.indExedenteRecursos;
	}

	public void setIndExedenteRecursos(BigDecimal indExedenteRecursos) {
		this.indExedenteRecursos = indExedenteRecursos;
	}

	public AptEnvioPreResolucionEnv getAptEnvioPreResolucionEnv() {
		return this.aptEnvioPreResolucionEnv;
	}

	public void setAptEnvioPreResolucionEnv(AptEnvioPreResolucionEnv aptEnvioPreResolucionEnv) {
		this.aptEnvioPreResolucionEnv = aptEnvioPreResolucionEnv;
	}
	
	public SptRespComunicEntidade getSptRespComunicEntidade() {
		return this.sptRespComunicEntidade;
	}

	public void setSptRespComunicEntidade(SptRespComunicEntidade sptRespComunicEntidade) {
		this.sptRespComunicEntidade = sptRespComunicEntidade;
	}
	
}