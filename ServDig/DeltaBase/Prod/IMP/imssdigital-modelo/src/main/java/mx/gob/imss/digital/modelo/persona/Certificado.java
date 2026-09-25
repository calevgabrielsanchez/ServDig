/**
 * 
 */
package mx.gob.imss.digital.modelo.persona;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Modelo para representar uncertificado digital
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "certificado", namespace = "http://mx.gob.imss.digital.modelo.persona")
@XmlRootElement(name = "certificado", namespace = "http://mx.gob.imss.digital.modelo.persona")
public class Certificado implements Serializable {

    /**
     * Serial version ID
     */
    private static final long serialVersionUID = 1L;
    /**
     * 
     */
    protected String claveSerial;
    /**
     * Nombre completo de la persona o enidad asociada al cerifcado
     */
    protected String nombreCompleto;
    /**
     * Estatus de la fiel
     */
    protected Integer estatusFiel;
    /**
     * correo Electronico
     */
    protected String correoElectronico;
    /**
     * curp que aparece en la fiel
     */
    protected String curpFiel;
    /**
     * RFC del usuario
     */
    protected String nombreUsuario;//RFC
    /**
     * RFC del asociado
     */
    protected String rfcAsociado;
    /**
     * fecha de inicio de vigencia del certificado
     */
    protected Date fechaValidaInicio;
    /**
     * Fecha final de vigencia del certificado
     */
    protected Date fechaValidaFin;
    /**
     * telefono de la persona del certificado
     */
    protected String telefono;
    /**
     * Identificador de su rol
     */
    protected Integer idRol;
    /**
     * @return the claveSerial
     */
    public String getClaveSerial() {
        return claveSerial;
    }
    /**
     * @param claveSerial the claveSerial to set
     */
    public void setClaveSerial(String claveSerial) {
        this.claveSerial = claveSerial;
    }
    /**
     * @return the nombreCompleto
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    /**
     * @param nombreCompleto the nombreCompleto to set
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    /**
     * @return the estatusFiel
     */
    public Integer getEstatusFiel() {
        return estatusFiel;
    }
    /**
     * @param estatusFiel the estatusFiel to set
     */
    public void setEstatusFiel(Integer estatusFiel) {
        this.estatusFiel = estatusFiel;
    }
    /**
     * @return the correoElectronico
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }
    /**
     * @param correoElectronico the correoElectronico to set
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
    /**
     * @return the curpFiel
     */
    public String getCurpFiel() {
        return curpFiel;
    }
    /**
     * @param curpFiel the curpFiel to set
     */
    public void setCurpFiel(String curpFiel) {
        this.curpFiel = curpFiel;
    }
    /**
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    /**
     * @param nombreUsuario the nombreUsuario to set
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    /**
     * @return the rfcAsociado
     */
    public String getRfcAsociado() {
        return rfcAsociado;
    }
    /**
     * @param rfcAsociado the rfcAsociado to set
     */
    public void setRfcAsociado(String rfcAsociado) {
        this.rfcAsociado = rfcAsociado;
    }
    /**
     * @return the fechaValidaInicio
     */
    public Date getFechaValidaInicio() {
        return fechaValidaInicio;
    }
    /**
     * @param fechaValidaInicio the fechaValidaInicio to set
     */
    public void setFechaValidaInicio(Date fechaValidaInicio) {
        this.fechaValidaInicio = fechaValidaInicio;
    }
    /**
     * @return the fechaValidaFin
     */
    public Date getFechaValidaFin() {
        return fechaValidaFin;
    }
    /**
     * @param fechaValidaFin the fechaValidaFin to set
     */
    public void setFechaValidaFin(Date fechaValidaFin) {
        this.fechaValidaFin = fechaValidaFin;
    }
    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }
    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    /**
     * @return the idRol
     */
    public Integer getIdRol() {
        return idRol;
    }
    /**
     * @param idRol the idRol to set
     */
    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }
    
    
}
