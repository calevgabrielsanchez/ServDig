/**
 * 
 */
package mx.gob.imss.digital.modelo.solicitud;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "firmaElectronica", namespace = "http://mx.gob.imss.digital.modelo.solicitud")
@XmlRootElement(name = "firmaElectronica", namespace = "http://mx.gob.imss.digital.modelo.solicitud")
public class FirmaElectronica implements Serializable {
    
    private static final long serialVersionUID = -3095384283761030218L;

    private BigDecimal idSolicitud;
    private String sPKCS7;
    private String cadenaOriginal;

    private String recibo;

    // DATOS DEL DUEÑO DEL CERTIFICADO
    private String rfc;
    private String registroPatronal;
    private String curp;
    private String nombreCompleto;
    private String serialCertificado;
    private Date iniciaVigenciaCertificado;
    private Date finVigenciaCertificado;
    private String strIniciaVigenciaCertificado;
    private String strFinVigenciaCertificado;
    private String tipoCertificado;
    private Date fechaElectronica;
    private String fechaElectronicaFormateada;
    private String urlAcuseFirma;

    private String reciboNotarial;
    private String secuenciaNotaria;

    private String msgRespuesta;
    private String codRespuesta;
    
    // True si se desea firmar un archivo, false si se desea firmar una cadena
    private boolean firmarArchivo;
    
    // Nombre del archivo que se firma
    private String fileNameToSign;

    public BigDecimal getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(BigDecimal idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getsPKCS7() {
        return sPKCS7;
    }

    public void setsPKCS7(String sPKCS7) {
        this.sPKCS7 = sPKCS7;
    }

    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    public void setCadenaOriginal(String cadenaOriginal) {
        this.cadenaOriginal = cadenaOriginal;
    }

    public String getRecibo() {
        return recibo;
    }

    public void setRecibo(String recibo) {
        this.recibo = recibo;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRegistroPatronal() {
        return registroPatronal;
    }

    public void setRegistroPatronal(String registroPatronal) {
        this.registroPatronal = registroPatronal;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getSerialCertificado() {
        return serialCertificado;
    }

    public void setSerialCertificado(String serialCertificado) {
        this.serialCertificado = serialCertificado;
    }

    public Date getIniciaVigenciaCertificado() {
        return iniciaVigenciaCertificado;
    }

    public void setIniciaVigenciaCertificado(Date iniciaVigenciaCertificado) {
        this.iniciaVigenciaCertificado = iniciaVigenciaCertificado;
    }

    public Date getFinVigenciaCertificado() {
        return finVigenciaCertificado;
    }

    public void setFinVigenciaCertificado(Date finVigenciaCertificado) {
        this.finVigenciaCertificado = finVigenciaCertificado;
    }

    public String getTipoCertificado() {
        return tipoCertificado;
    }

    public void setTipoCertificado(String tipoCertificado) {
        this.tipoCertificado = tipoCertificado;
    }

    public String getReciboNotarial() {
        return reciboNotarial;
    }

    public void setReciboNotarial(String reciboNotarial) {
        this.reciboNotarial = reciboNotarial;
    }

    public String getSecuenciaNotaria() {
        return secuenciaNotaria;
    }

    public void setSecuenciaNotaria(String secuenciaNotaria) {
        this.secuenciaNotaria = secuenciaNotaria;
    }

    public String getMsgRespuesta() {
        return msgRespuesta;
    }

    public void setMsgRespuesta(String msgRespuesta) {
        this.msgRespuesta = msgRespuesta;
    }

    public String getCodRespuesta() {
        return codRespuesta;
    }

    public void setCodRespuesta(String codRespuesta) {
        this.codRespuesta = codRespuesta;
    }
    
    public boolean isFirmarArchivo() {
        return firmarArchivo;
    }

    public void setFirmarArchivo(boolean firmarArchivo) {
        this.firmarArchivo = firmarArchivo;
    }
    
    public String getFileNameToSign() {
        return fileNameToSign;
    }

    public void setFileNameToSign(String fileNameToSign) {
        this.fileNameToSign = fileNameToSign;
    }
    
    public Date getFechaElectronica() {
        return fechaElectronica;
    }

    public void setFechaElectronica(Date fechaElectronica) {
        this.fechaElectronica = fechaElectronica;
    }

    public String getFechaElectronicaFormateada() {
        return fechaElectronicaFormateada;
    }

    public void setFechaElectronicaFormateada(String fechaElectronicaFormateada) {
        this.fechaElectronicaFormateada = fechaElectronicaFormateada;
    }

    public String getUrlAcuseFirma() {
        return urlAcuseFirma;
    }

    public void setUrlAcuseFirma(String urlAcuseFirma) {
        this.urlAcuseFirma = urlAcuseFirma;
    }

    public String getStrIniciaVigenciaCertificado() {
        return strIniciaVigenciaCertificado;
    }

    public void setStrIniciaVigenciaCertificado(String strIniciaVigenciaCertificado) {
        this.strIniciaVigenciaCertificado = strIniciaVigenciaCertificado;
    }

    public String getStrFinVigenciaCertificado() {
        return strFinVigenciaCertificado;
    }

    public void setStrFinVigenciaCertificado(String strFinVigenciaCertificado) {
        this.strFinVigenciaCertificado = strFinVigenciaCertificado;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(
                "********LOS DATOS DEL OBJETO FIRMA ELECTRONICA SON ******************")
                .append("\n");
        sb.append("****idSolicitud:[").append(this.idSolicitud).append("],");
        sb.append("****cadenaOriginal:[").append(this.cadenaOriginal)
                .append("],");
        sb.append("recibo:[").append(this.recibo).append("],");
        sb.append("reciboNotarial:[").append(this.reciboNotarial).append("],");
        sb.append("sPKCS7:[").append(this.sPKCS7).append("],");
        return sb.toString();
    }

}
