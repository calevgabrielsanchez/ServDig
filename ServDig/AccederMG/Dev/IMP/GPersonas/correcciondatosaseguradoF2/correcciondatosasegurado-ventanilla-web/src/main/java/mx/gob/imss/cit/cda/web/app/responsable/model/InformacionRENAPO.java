/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.responsable.model;


/**
 *
 * @author antonio
 */
public class InformacionRENAPO extends InformacionPersona {

    /**
     * 
     */
    private static final long serialVersionUID = -4113397121910803683L;
    private String folio;
    private String lugarNacimiento;
    private String nacionalidad;
    private String datosDocumentoProbatorio;
    private String fechaNacimiento;
    private String telefonoFijo;
    private String telefonoMovil;
    private String correoElectronico;
    private String origen;
    private String idOrigen;
    private boolean errorSINDO;

    /**
     * @return the lugarNacimiento
     */
    public String getLugarNacimiento() {
        return lugarNacimiento;
    }

    /**
     * @param lugarNacimiento
     *            the lugarNacimiento to set
     */
    public void setLugarNacimiento(String lugarNacimiento) {
        this.lugarNacimiento = lugarNacimiento;
    }

    /**
     * @return the nacionalidad
     */
    public String getNacionalidad() {
        return nacionalidad;
    }

    /**
     * @param nacionalidad
     *            the nacionalidad to set
     */
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    /**
     * @return the datosDocumentoProbatorio
     */
    public String getDatosDocumentoProbatorio() {
        return datosDocumentoProbatorio;
    }

    /**
     * @param datosDocumentoProbatorio
     *            the datosDocumentoProbatorio to set
     */
    public void setDatosDocumentoProbatorio(String datosDocumentoProbatorio) {
        this.datosDocumentoProbatorio = datosDocumentoProbatorio;
    }

    
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    
    public boolean isErrorSINDO() {
        return errorSINDO;
    }

    public void setErrorSINDO(boolean errorSINDO) {
        this.errorSINDO = errorSINDO;
    }

    public String getIdOrigen() {
        return idOrigen;
    }

    public void setIdOrigen(String idOrigen) {
        this.idOrigen = idOrigen;
    }

    @Override
    public String toString() {
        return super.toString() +  "InformacionRENAPO [folio=" + folio + ", lugarNacimiento="
                + lugarNacimiento + ", nacionalidad=" + nacionalidad
                + ", datosDocumentoProbatorio=" + datosDocumentoProbatorio
                + ", fechaNacimiento=" + fechaNacimiento + ", telefonoFijo="
                + telefonoFijo + ", telefonoMovil=" + telefonoMovil
                + ", correoElectronico=" + correoElectronico + ", origen="
                + origen + ", idOrigen=" + idOrigen + ", errorSINDO="
                + errorSINDO + "]";
    }
    
}
