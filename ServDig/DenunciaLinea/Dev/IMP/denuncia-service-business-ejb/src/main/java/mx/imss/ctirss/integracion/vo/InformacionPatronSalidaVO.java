/**
 * Proyecto: IMSS - SATIC
 *
 * Archivo: InformacionPatronSalidaVO.java
 *
 * Creado: 18/08/2008
 *
 * Derechos Reservados de Copia (c) - TCS / Instituto Mexicano del Seguro Social - 2008
 */

package mx.imss.ctirss.integracion.vo;

/**
 * Proyecto: Sistema de Afiliacion de Trabajadores de la Industria de la Construccion. <BR>
 * Objetivo: TODO Documentar!!!<BR>
 * Fecha de creacion: Aug 18, 2008 <BR>
 * Copyright (c) IMSS
 * <BR>
 *
 * @author TCS
 * @version 1.0
 */

public class InformacionPatronSalidaVO {

    private String rfc;
    private String curp;
    private String razonSocial;
    private String codigoPostal;
    private String correo;
    private String domicilio;
    private String localidad;
    private int cveDelegacion;
    private int cveSubDelegacion;
    private String cveMunicipio;
    private int cveTipoMov;
    private int exito;
    private int codigo;
    private String descripcion;
    /**
     * Metodo que regresa el valor relacionado al atributo codigo.
     * @return El valor relacionado al atributo codigo.
     */
    public int getCodigo() {
        return codigo;
    }
    /**
     * Metodo que establece el valor del atributo codigo.
     * @param codigo Valor a establecer en el atributo codigo.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    /**
     * Metodo que regresa el valor relacionado al atributo codigoPostal.
     * @return El valor relacionado al atributo codigoPostal.
     */
    public String getCodigoPostal() {
        return codigoPostal;
    }
    /**
     * Metodo que establece el valor del atributo codigoPostal.
     * @param codigoPostal Valor a establecer en el atributo codigoPostal.
     */
    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
    /**
     * Metodo que regresa el valor relacionado al atributo correo.
     * @return El valor relacionado al atributo correo.
     */
    public String getCorreo() {
        return correo;
    }
    /**
     * Metodo que establece el valor del atributo correo.
     * @param correo Valor a establecer en el atributo correo.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    /**
     * Metodo que regresa el valor relacionado al atributo curp.
     * @return El valor relacionado al atributo curp.
     */
    public String getCURP() {
        return curp;
    }
    /**
     * Metodo que establece el valor del atributo curp.
     * @param curp Valor a establecer en el atributo curp.
     */
    public void setCURP(String curp) {
        this.curp = curp;
    }
    /**
     * Metodo que regresa el valor relacionado al atributo cveDelegacion.
     * @return El valor relacionado al atributo cveDelegacion.
     */
    public int getCveDelegacion() {
        return cveDelegacion;
    }
    /**
     * Metodo que establece el valor del atributo cveDelegacion.
     * @param cveDelegacion Valor a establecer en el atributo cveDelegacion.
     */
    public void setCveDelegacion(int cveDelegacion) {
        this.cveDelegacion = cveDelegacion;
    }
    /**
     * Metodo que regresa el valor relacionado al atributo cveMunicipio.
     * @return El valor relacionado al atributo cveMunicipio.
     */
    public String getCveMunicipio() {
        return cveMunicipio;
    }
    /**
     * Metodo que establece el valor del atributo cveMunicipio.
     * @param cveMunicipio Valor a establecer en el atributo cveMunicipio.
     */
    public void setCveMunicipio(String cveMunicipio) {
        this.cveMunicipio = cveMunicipio;
    }
    /**
     * Metodo que regresa el valor relacionado al atributo cveSubDelegacion.
     * @return El valor relacionado al atributo cveSubDelegacion.
     */
    public int getCveSubDelegacion() {
        return cveSubDelegacion;
    }
    /**
     * Metodo que establece el valor del atributo cveSubDelegacion.
     * @param cveSubDelegacion Valor a establecer en el atributo cveSubDelegacion.
     */
    public void setCveSubDelegacion(int cveSubDelegacion) {
        this.cveSubDelegacion = cveSubDelegacion;
    }
    /**
     * Metodo que regresa el valor relacionado al atributo cveTipoMov.
     * @return El valor relacionado al atributo cveTipoMov.
     */
    public int getCveTipoMov() {
        return cveTipoMov;
    }
    /**
     * Metodo que establece el valor del atributo cveTipoMov.
     * @param cveTipoMov Valor a establecer en el atributo cveTipoMov.
     */
    public void setCveTipoMov(int cveTipoMov) {
        this.cveTipoMov = cveTipoMov;
    }
    /**
     * Metodo que regresa el valor relacionado al atributo descripcion.
     * @return El valor relacionado al atributo descripcion.
     */
    public String getDescripcion() {
        return descripcion;
    }
    /**
     * Metodo que establece el valor del atributo descripcion.
     * @param descripcion Valor a establecer en el atributo descripcion.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    /**
     * Metodo que regresa el valor relacionado al atributo domicilio.
     * @return El valor relacionado al atributo domicilio.
     */
    public String getDomicilio() {
        return domicilio;
    }
    /**
     * Metodo que establece el valor del atributo domicilio.
     * @param domicilio Valor a establecer en el atributo domicilio.
     */
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }
    /**
     * Metodo que regresa el valor relacionado al atributo exito.
     * @return El valor relacionado al atributo exito.
     */
    public int getExito() {
        return exito;
    }
    /**
     * Metodo que establece el valor del atributo exito.
     * @param exito Valor a establecer en el atributo exito.
     */
    public void setExito(int exito) {
        this.exito = exito;
    }
    /**
     * Metodo que regresa el valor relacionado al atributo localidad.
     * @return El valor relacionado al atributo localidad.
     */
    public String getLocalidad() {
        return localidad;
    }
    /**
     * Metodo que establece el valor del atributo localidad.
     * @param localidad Valor a establecer en el atributo localidad.
     */
    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }
    /**
     * Metodo que regresa el valor relacionado al atributo razonSocial.
     * @return El valor relacionado al atributo razonSocial.
     */
    public String getRazonSocial() {
        return razonSocial;
    }
    /**
     * Metodo que establece el valor del atributo razonSocial.
     * @param razonSocial Valor a establecer en el atributo razonSocial.
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
    /**
     * Metodo que regresa el valor relacionado al atributo rfc.
     * @return El valor relacionado al atributo rfc.
     */
    public String getRFC() {
        return rfc;
    }
    /**
     * Metodo que establece el valor del atributo rfc.
     * @param rfc Valor a establecer en el atributo rfc.
     */
    public void setRFC(String rfc) {
        this.rfc = rfc;
    }
}