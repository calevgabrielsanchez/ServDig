package mx.gob.imss.ctirss.delta.cobranza.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="comprobante")
public class ComprobanteDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EmisorDTO emisor;
    protected ReceptorDTO receptor;
    protected ConceptosDTO conceptos;
    protected ImpuestosDTO impuestos;
    protected ComplementoDTO complemento;
    protected AddendasDTO addenda;
    private String version;
    private String serie;
    private String folio;
    private Date fecha;
    private String sello;
    private String formaDePago;
    private String noCertificado;
    private String certificado;
    private String condicionesDePago;
    private BigDecimal subTotal;
    private BigDecimal descuento;
    private String motivoDescuento;
    private String tipoCambio;
    private String moneda;
    private BigDecimal total;
    private String tipoDeComprobante;
    private String metodoDePago;
    private String lugarExpedicion;
    private String numCtaPago;
    private String folioFiscalOrig;
    private String serieFolioFiscalOrig;
    private Date fechaFolioFiscalOrig;
    private BigDecimal montoFolioFiscalOrig;
    private CfdiRelacionadosDTO cfdiRelacionados;

    public AddendasDTO getAddenda() {
        return addenda;
    }

    public void setAddenda(AddendasDTO addenda) {
        this.addenda = addenda;
    }

    public EmisorDTO getEmisor() {
        return emisor;
    }

    public void setEmisor(EmisorDTO emisor) {
        this.emisor = emisor;
    }

    public ReceptorDTO getReceptor() {
        return receptor;
    }

    public void setReceptor(ReceptorDTO receptor) {
        this.receptor = receptor;
    }

    public ConceptosDTO getConceptos() {
        return conceptos;
    }

    public void setConceptos(ConceptosDTO conceptos) {
        this.conceptos = conceptos;
    }

    public ImpuestosDTO getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(ImpuestosDTO impuestos) {
        this.impuestos = impuestos;
    }

    public ComplementoDTO getComplemento() {
        return complemento;
    }

    public void setComplemento(ComplementoDTO complemento) {
        this.complemento = complemento;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getSello() {
        return sello;
    }

    public void setSello(String sello) {
        this.sello = sello;
    }

    public String getFormaDePago() {
        return formaDePago;
    }

    public void setFormaDePago(String formaDePago) {
        this.formaDePago = formaDePago;
    }

    public String getNoCertificado() {
        return noCertificado;
    }

    public void setNoCertificado(String noCertificado) {
        this.noCertificado = noCertificado;
    }

    public String getCertificado() {
        return certificado;
    }

    public void setCertificado(String certificado) {
        this.certificado = certificado;
    }

    public String getCondicionesDePago() {
        return condicionesDePago;
    }

    public void setCondicionesDePago(String condicionesDePago) {
        this.condicionesDePago = condicionesDePago;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public String getMotivoDescuento() {
        return motivoDescuento;
    }

    public void setMotivoDescuento(String motivoDescuento) {
        this.motivoDescuento = motivoDescuento;
    }

    public String getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(String tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getTipoDeComprobante() {
        return tipoDeComprobante;
    }

    public void setTipoDeComprobante(String tipoDeComprobante) {
        this.tipoDeComprobante = tipoDeComprobante;
    }

    public String getMetodoDePago() {
        return metodoDePago;
    }

    public void setMetodoDePago(String metodoDePago) {
        this.metodoDePago = metodoDePago;
    }

    public String getLugarExpedicion() {
        return lugarExpedicion;
    }

    public void setLugarExpedicion(String lugarExpedicion) {
        this.lugarExpedicion = lugarExpedicion;
    }

    public String getNumCtaPago() {
        return numCtaPago;
    }

    public void setNumCtaPago(String numCtaPago) {
        this.numCtaPago = numCtaPago;
    }

    public String getFolioFiscalOrig() {
        return folioFiscalOrig;
    }

    public void setFolioFiscalOrig(String folioFiscalOrig) {
        this.folioFiscalOrig = folioFiscalOrig;
    }

    public String getSerieFolioFiscalOrig() {
        return serieFolioFiscalOrig;
    }

    public void setSerieFolioFiscalOrig(String serieFolioFiscalOrig) {
        this.serieFolioFiscalOrig = serieFolioFiscalOrig;
    }

    public Date getFechaFolioFiscalOrig() {
        return fechaFolioFiscalOrig;
    }

    public void setFechaFolioFiscalOrig(Date fechaFolioFiscalOrig) {
        this.fechaFolioFiscalOrig = fechaFolioFiscalOrig;
    }

    public BigDecimal getMontoFolioFiscalOrig() {
        return montoFolioFiscalOrig;
    }

    public void setMontoFolioFiscalOrig(BigDecimal montoFolioFiscalOrig) {
        this.montoFolioFiscalOrig = montoFolioFiscalOrig;
    }

	public CfdiRelacionadosDTO getCfdiRelacionados() {
		return cfdiRelacionados;
	}

	public void setCfdiRelacionados(CfdiRelacionadosDTO cfdiRelacionados) {
		this.cfdiRelacionados = cfdiRelacionados;
	}
	
}
