package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.persistence.AptDiagnosticosCnsf;
import mx.gob.imss.ctirss.delta.persistence.SptTramitePension;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the APT_OFERTAS_CNSF database table.
 * 
 */
@Entity
@Table(name="APT_OFERTAS_CNSF")
@NamedQuery(name="AptOfertasCnsf.findAll", query="SELECT s FROM AptOfertasCnsf s")
public class AptOfertasCnsf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_APTOFERTASCNSF", sequenceName = "SEQ_APTOFERTASCNSF")
	@GeneratedValue(generator = "SEQ_APTOFERTASCNSF")
	@Column(name="CVE_OFERTAS_CNSF")
	private long cveOfertasCnsf;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_CADUCIDAD_BD")
	private Date fecCaducidadBd;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_CADUCIDAD_OFERTA")
	private Date fecCaducidadOferta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_OFERTA")
	private Date fecOferta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="ID_CODIGO_TABLA_ASGURADORA")
	private String idCodigoTablaAsguradora;

	@Column(name="ID_FOLIO_SAOR")
	private BigDecimal idFolioSaor;

	@Column(name="ID_LOGIN")
	private String idLogin;

	@Column(name="ID_OFERTA")
	private String idOferta;

	@Column(name="ID_SOLICITUD")
	private String idSolicitud;

	@Column(name="IMP_AGUINALDO")
	private BigDecimal impAguinaldo;

	@Column(name="IMP_AGUINALDO_RP_SS")
	private BigDecimal impAguinaldoRpSs;

	@Column(name="IMP_MONTO_CONST_RENTA_VITALICI")
	private BigDecimal impMontoConstRentaVitalici;

	@Column(name="IMP_MONTO_CONST_SEG_RET_PROG")
	private BigDecimal impMontoConstSegRetProg;

	@Column(name="IMP_MONTO_CONST_SEGURO_SOBREVI")
	private BigDecimal impMontoConstSeguroSobrevi;

	@Column(name="IMP_PAGOS_VENCIDOS_TOTALES")
	private BigDecimal impPagosVencidosTotales;

	@Column(name="IMP_PENSION_MAXIMA")
	private BigDecimal impPensionMaxima;

	@Column(name="IMP_PENSION_MAXIMA_RP_SS")
	private BigDecimal impPensionMaximaRpSs;

	@Column(name="IMP_PUBA")
	private BigDecimal impPuba;

	@Column(name="IMP_RENTA")
	private BigDecimal impRenta;

	@Column(name="IMP_RENTA_ADICIONAL")
	private BigDecimal impRentaAdicional;

	@Column(name="IMP_RENTA_ADICIONAL_RP_SS")
	private BigDecimal impRentaAdicionalRpSs;

	@Column(name="IMP_RENTA_RP")
	private BigDecimal impRentaRp;

	@Column(name="IMP_RENTA_RP_SS")
	private BigDecimal impRentaRpSs;

	@Column(name="IND_INSUFICIENCIA")
	private String indInsuficiencia;

	@Column(name="IND_OFERTA_SELECCIONADA")
	private BigDecimal indOfertaSeleccionada;

	@Column(name="IND_REBASA_MC_CMG")
	private String indRebasaMcCmg;

	@Column(name="NUM_ERROR")
	private BigDecimal numError;

	@Column(name="PORC_TASA_SUBASTA")
	private BigDecimal porcTasaSubasta;

	//bi-directional many-to-one association to AptDiagnosticosCnsf
    @ManyToOne
	@JoinColumns({
		@JoinColumn(name="ID_DIAGNOSTICO", referencedColumnName="ID_DIAGNOSTICO"),
		@JoinColumn(name="ID_PROCESO_OCUPADO", referencedColumnName="ID_PROCESO_OCUPADO")
		})
	private AptDiagnosticosCnsf aptDiagnosticosCnsf;

	//bi-directional many-to-one association to SpcAseguradora
    @ManyToOne
	@JoinColumn(name="ID_ASEGURADORA")
	private SpcAseguradora spcAseguradora;

	//bi-directional many-to-one association to SptTramitePension
    @ManyToOne
	@JoinColumn(name="CVE_ID_TRAMITE_PENSION")
	private SptTramitePension sptTramitePension;

    public AptOfertasCnsf() {
    }

	public long getCveOfertasCnsf() {
		return this.cveOfertasCnsf;
	}

	public void setCveOfertasCnsf(long cveOfertasCnsf) {
		this.cveOfertasCnsf = cveOfertasCnsf;
	}

	public Date getFecCaducidadBd() {
		return this.fecCaducidadBd;
	}

	public void setFecCaducidadBd(Date fecCaducidadBd) {
		this.fecCaducidadBd = fecCaducidadBd;
	}

	public Date getFecCaducidadOferta() {
		return this.fecCaducidadOferta;
	}

	public void setFecCaducidadOferta(Date fecCaducidadOferta) {
		this.fecCaducidadOferta = fecCaducidadOferta;
	}

	public Date getFecOferta() {
		return this.fecOferta;
	}

	public void setFecOferta(Date fecOferta) {
		this.fecOferta = fecOferta;
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

	public String getIdCodigoTablaAsguradora() {
		return this.idCodigoTablaAsguradora;
	}

	public void setIdCodigoTablaAsguradora(String idCodigoTablaAsguradora) {
		this.idCodigoTablaAsguradora = idCodigoTablaAsguradora;
	}

	public BigDecimal getIdFolioSaor() {
		return this.idFolioSaor;
	}

	public void setIdFolioSaor(BigDecimal idFolioSaor) {
		this.idFolioSaor = idFolioSaor;
	}

	public String getIdLogin() {
		return this.idLogin;
	}

	public void setIdLogin(String idLogin) {
		this.idLogin = idLogin;
	}

	public String getIdOferta() {
		return this.idOferta;
	}

	public void setIdOferta(String idOferta) {
		this.idOferta = idOferta;
	}

	public String getIdSolicitud() {
		return this.idSolicitud;
	}

	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public BigDecimal getImpAguinaldo() {
		return this.impAguinaldo;
	}

	public void setImpAguinaldo(BigDecimal impAguinaldo) {
		this.impAguinaldo = impAguinaldo;
	}

	public BigDecimal getImpAguinaldoRpSs() {
		return this.impAguinaldoRpSs;
	}

	public void setImpAguinaldoRpSs(BigDecimal impAguinaldoRpSs) {
		this.impAguinaldoRpSs = impAguinaldoRpSs;
	}

	public BigDecimal getImpMontoConstRentaVitalici() {
		return this.impMontoConstRentaVitalici;
	}

	public void setImpMontoConstRentaVitalici(BigDecimal impMontoConstRentaVitalici) {
		this.impMontoConstRentaVitalici = impMontoConstRentaVitalici;
	}

	public BigDecimal getImpMontoConstSegRetProg() {
		return this.impMontoConstSegRetProg;
	}

	public void setImpMontoConstSegRetProg(BigDecimal impMontoConstSegRetProg) {
		this.impMontoConstSegRetProg = impMontoConstSegRetProg;
	}

	public BigDecimal getImpMontoConstSeguroSobrevi() {
		return this.impMontoConstSeguroSobrevi;
	}

	public void setImpMontoConstSeguroSobrevi(BigDecimal impMontoConstSeguroSobrevi) {
		this.impMontoConstSeguroSobrevi = impMontoConstSeguroSobrevi;
	}

	public BigDecimal getImpPagosVencidosTotales() {
		return this.impPagosVencidosTotales;
	}

	public void setImpPagosVencidosTotales(BigDecimal impPagosVencidosTotales) {
		this.impPagosVencidosTotales = impPagosVencidosTotales;
	}

	public BigDecimal getImpPensionMaxima() {
		return this.impPensionMaxima;
	}

	public void setImpPensionMaxima(BigDecimal impPensionMaxima) {
		this.impPensionMaxima = impPensionMaxima;
	}

	public BigDecimal getImpPensionMaximaRpSs() {
		return this.impPensionMaximaRpSs;
	}

	public void setImpPensionMaximaRpSs(BigDecimal impPensionMaximaRpSs) {
		this.impPensionMaximaRpSs = impPensionMaximaRpSs;
	}

	public BigDecimal getImpPuba() {
		return this.impPuba;
	}

	public void setImpPuba(BigDecimal impPuba) {
		this.impPuba = impPuba;
	}

	public BigDecimal getImpRenta() {
		return this.impRenta;
	}

	public void setImpRenta(BigDecimal impRenta) {
		this.impRenta = impRenta;
	}

	public BigDecimal getImpRentaAdicional() {
		return this.impRentaAdicional;
	}

	public void setImpRentaAdicional(BigDecimal impRentaAdicional) {
		this.impRentaAdicional = impRentaAdicional;
	}

	public BigDecimal getImpRentaAdicionalRpSs() {
		return this.impRentaAdicionalRpSs;
	}

	public void setImpRentaAdicionalRpSs(BigDecimal impRentaAdicionalRpSs) {
		this.impRentaAdicionalRpSs = impRentaAdicionalRpSs;
	}

	public BigDecimal getImpRentaRp() {
		return this.impRentaRp;
	}

	public void setImpRentaRp(BigDecimal impRentaRp) {
		this.impRentaRp = impRentaRp;
	}

	public BigDecimal getImpRentaRpSs() {
		return this.impRentaRpSs;
	}

	public void setImpRentaRpSs(BigDecimal impRentaRpSs) {
		this.impRentaRpSs = impRentaRpSs;
	}

	public String getIndInsuficiencia() {
		return this.indInsuficiencia;
	}

	public void setIndInsuficiencia(String indInsuficiencia) {
		this.indInsuficiencia = indInsuficiencia;
	}

	public BigDecimal getIndOfertaSeleccionada() {
		return this.indOfertaSeleccionada;
	}

	public void setIndOfertaSeleccionada(BigDecimal indOfertaSeleccionada) {
		this.indOfertaSeleccionada = indOfertaSeleccionada;
	}

	public String getIndRebasaMcCmg() {
		return this.indRebasaMcCmg;
	}

	public void setIndRebasaMcCmg(String indRebasaMcCmg) {
		this.indRebasaMcCmg = indRebasaMcCmg;
	}

	public BigDecimal getNumError() {
		return this.numError;
	}

	public void setNumError(BigDecimal numError) {
		this.numError = numError;
	}

	public BigDecimal getPorcTasaSubasta() {
		return this.porcTasaSubasta;
	}

	public void setPorcTasaSubasta(BigDecimal porcTasaSubasta) {
		this.porcTasaSubasta = porcTasaSubasta;
	}

	public AptDiagnosticosCnsf getAptDiagnosticosCnsf() {
		return this.aptDiagnosticosCnsf;
	}

	public void setAptDiagnosticosCnsf(AptDiagnosticosCnsf aptDiagnosticosCnsf) {
		this.aptDiagnosticosCnsf = aptDiagnosticosCnsf;
	}
	
	public SpcAseguradora getSpcAseguradora() {
		return this.spcAseguradora;
	}

	public void setSpcAseguradora(SpcAseguradora spcAseguradora) {
		this.spcAseguradora = spcAseguradora;
	}
	
	public SptTramitePension getSptTramitePension() {
		return this.sptTramitePension;
	}

	public void setSptTramitePension(SptTramitePension sptTramitePension) {
		this.sptTramitePension = sptTramitePension;
	}
	
}