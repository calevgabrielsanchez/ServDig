package mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.base.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@MappedSuperclass
public class AbstractCrtCobranzaPagos extends AbstractModel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NSS")
	private String nss;
	
	
	@Column(name="RP")
	private String rp; 
	
	@Column(name="MOD")
	private String mod; 
	
	@Column(name="DV")
	private String dv; 
	
	@Column(name="FOLIOSUA")
	private String folioSua;
	
	@Column(name="TIPO_DOCUMENTO")
	private String tipoDocumento;
	
	@Column(name="PERIODO")
	private BigDecimal periodo;
	
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FECHA_PAGO")
	private Date fecFechapago; 
	
	@Column(name="IMP_CUOTA_FIJA")
	private BigDecimal impCuotaFija;
	
	@Column(name="IMP_EXEDENTE3SMGDF")
	private BigDecimal impExedente3smGDF;
	
	@Column(name="IMP_PRESTADINERO")
	private BigDecimal impPrestaDinero;
	
	@Column(name="IMP_GASTOSMEDPENS")
	private BigDecimal impGastoMedPens;
	
	@Column(name="IMP_RIESGOSTRABAJO")
	private BigDecimal impRiesgosTrabajo;
	
	
	@Column(name="IMP_INVALIDEZVIDA")
	private BigDecimal impValidezVida;
	
	
	@Column(name="IMP_GUARDERIASPRESOC")
	private BigDecimal impGuarderiasPresoc;
		
	
	@Column(name="IMP_SUBTOTALCOP")
	private BigDecimal impSubtotalCOP;

	
	@Column(name="IMP_ACTUALIZACOP")
	private BigDecimal impActualizaCOP;
	
	
	@Column(name="IMP_RECARGOSCOP")
	private BigDecimal impRecargosCOP;
	
	
	@Column(name="IMP_TOTALCOP")
	private BigDecimal impTotalCOP;
	
	@Column(name="IMP_RETIRORCV")
	private BigDecimal impRetiroRCV;
	
	
	@Column(name="IMP_CESANTIAEDAD")
	private BigDecimal impCesAntiedad;
	
	
	@Column(name="IMP_SUBTOTALRCV")
	private BigDecimal impSubtotalRCV;
	
	@Column(name="IMP_ACTUALIZARCV")
	private BigDecimal impActualizaRCV;
	
	
	@Column(name="IMP_RECARGOSRCV")
	private BigDecimal impRegarcosRCV;
	
	
	@Column(name="IMP_TOTALRCV")
	private BigDecimal impTotalRCV;
	
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_CARGA")
	private Date fecFechaCarga;

	
	@Column(name="TIPO_DOC_COP")
	private BigDecimal tipoDocCOP;
	
	@Column(name="TIPO_DOC_RCV")
	private BigDecimal tipoDocRCV;

	
	
	
	public BigDecimal getTipoDocCOP() {
		return tipoDocCOP;
	}


	public void setTipoDocCOP(BigDecimal tipoDocCOP) {
		this.tipoDocCOP = tipoDocCOP;
	}


	public BigDecimal getTipoDocRCV() {
		return tipoDocRCV;
	}


	public void setTipoDocRCV(BigDecimal tipoDocRCV) {
		this.tipoDocRCV = tipoDocRCV;
	}


	public String getNss() {
		return nss;
	}


	public void setNss(String nss) {
		this.nss = nss;
	}


	public String getRp() {
		return rp;
	}


	public void setRp(String rp) {
		this.rp = rp;
	}


	public String getMod() {
		return mod;
	}


	public void setMod(String mod) {
		this.mod = mod;
	}


	public String getDv() {
		return dv;
	}


	public void setDv(String dv) {
		this.dv = dv;
	}


	public String getFolioSua() {
		return folioSua;
	}


	public void setFolioSua(String folioSua) {
		this.folioSua = folioSua;
	}


	public String getTipoDocumento() {
		return tipoDocumento;
	}


	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}


	public BigDecimal getPeriodo() {
		return periodo;
	}


	public void setPeriodo(BigDecimal periodo) {
		this.periodo = periodo;
	}


	public Date getFecFechapago() {
		return fecFechapago;
	}


	public void setFecFechapago(Date fecFechapago) {
		this.fecFechapago = fecFechapago;
	}


	public BigDecimal getImpCuotaFija() {
		return impCuotaFija;
	}


	public void setImpCuotaFija(BigDecimal impCuotaFija) {
		this.impCuotaFija = impCuotaFija;
	}


	public BigDecimal getImpExedente3smGDF() {
		return impExedente3smGDF;
	}


	public void setImpExedente3smGDF(BigDecimal impExedente3smGDF) {
		this.impExedente3smGDF = impExedente3smGDF;
	}


	public BigDecimal getImpPrestaDinero() {
		return impPrestaDinero;
	}


	public void setImpPrestaDinero(BigDecimal impPrestaDinero) {
		this.impPrestaDinero = impPrestaDinero;
	}


	public BigDecimal getImpGastoMedPens() {
		return impGastoMedPens;
	}


	public void setImpGastoMedPens(BigDecimal impGastoMedPens) {
		this.impGastoMedPens = impGastoMedPens;
	}


	public BigDecimal getImpRiesgosTrabajo() {
		return impRiesgosTrabajo;
	}


	public void setImpRiesgosTrabajo(BigDecimal impRiesgosTrabajo) {
		this.impRiesgosTrabajo = impRiesgosTrabajo;
	}


	public BigDecimal getImpValidezVida() {
		return impValidezVida;
	}


	public void setImpValidezVida(BigDecimal impValidezVida) {
		this.impValidezVida = impValidezVida;
	}


	public BigDecimal getImpGuarderiasPresoc() {
		return impGuarderiasPresoc;
	}


	public void setImpGuarderiasPresoc(BigDecimal impGuarderiasPresoc) {
		this.impGuarderiasPresoc = impGuarderiasPresoc;
	}


	public BigDecimal getImpSubtotalCOP() {
		return impSubtotalCOP;
	}


	public void setImpSubtotalCOP(BigDecimal impSubtotalCOP) {
		this.impSubtotalCOP = impSubtotalCOP;
	}


	public BigDecimal getImpActualizaCOP() {
		return impActualizaCOP;
	}


	public void setImpActualizaCOP(BigDecimal impActualizaCOP) {
		this.impActualizaCOP = impActualizaCOP;
	}


	public BigDecimal getImpRecargosCOP() {
		return impRecargosCOP;
	}


	public void setImpRecargosCOP(BigDecimal impRecargosCOP) {
		this.impRecargosCOP = impRecargosCOP;
	}


	public BigDecimal getImpTotalCOP() {
		return impTotalCOP;
	}


	public void setImpTotalCOP(BigDecimal impTotalCOP) {
		this.impTotalCOP = impTotalCOP;
	}


	public BigDecimal getImpRetiroRCV() {
		return impRetiroRCV;
	}


	public void setImpRetiroRCV(BigDecimal impRetiroRCV) {
		this.impRetiroRCV = impRetiroRCV;
	}


	public BigDecimal getImpCesAntiedad() {
		return impCesAntiedad;
	}


	public void setImpCesAntiedad(BigDecimal impCesAntiedad) {
		this.impCesAntiedad = impCesAntiedad;
	}


	public BigDecimal getImpSubtotalRCV() {
		return impSubtotalRCV;
	}


	public void setImpSubtotalRCV(BigDecimal impSubtotalRCV) {
		this.impSubtotalRCV = impSubtotalRCV;
	}


	public BigDecimal getImpActualizaRCV() {
		return impActualizaRCV;
	}


	public void setImpActualizaRCV(BigDecimal impActualizaRCV) {
		this.impActualizaRCV = impActualizaRCV;
	}


	public BigDecimal getImpRegarcosRCV() {
		return impRegarcosRCV;
	}


	public void setImpRegarcosRCV(BigDecimal impRegarcosRCV) {
		this.impRegarcosRCV = impRegarcosRCV;
	}


	public BigDecimal getImpTotalRCV() {
		return impTotalRCV;
	}


	public void setImpTotalRCV(BigDecimal impTotalRCV) {
		this.impTotalRCV = impTotalRCV;
	}


	public Date getFecFechaCarga() {
		return fecFechaCarga;
	}


	public void setFecFechaCarga(Date fecFechaCarga) {
		this.fecFechaCarga = fecFechaCarga;
	} 
	
	

	
	
}
