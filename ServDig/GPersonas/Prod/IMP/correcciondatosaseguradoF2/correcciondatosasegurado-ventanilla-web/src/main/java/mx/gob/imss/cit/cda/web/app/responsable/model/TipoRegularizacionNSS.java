/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.responsable.model;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;
import mx.gob.imss.ctirss.delta.model.enums.TipoRegularizacionNSSEnum;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 
 * @author antonio
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipoRegularizacionNSS extends BaseModel {

    private static final long serialVersionUID = 1L;
    private boolean nombre;
    private boolean datosEstadisticos;
    private boolean duplicidad;
    private boolean noExisteCanase;
    private boolean homonimio;
    private boolean otroAsegurado;
    private boolean regularizarCuentaIndividual;
    private List<Long> idRegularizacionNSS;

    /**
     * @return the nombre
     */
    public boolean isNombre() {
        return nombre;
    }

    /**
     * @param nombre
     *            the nombre to set
     */
    public void setNombre(boolean nombre) {
        this.nombre = nombre;
        setIdRegularizacionNSS(nombre,
                TipoRegularizacionNSSEnum.CORRECCION_NOMBRE.getId());
    }

    /**
     * @return the datosEstadisticos
     */
    public boolean isDatosEstadisticos() {
        return datosEstadisticos;
    }

    /**
     * @param datosEstadisticos
     *            the datosEstadisticos to set
     */
    public void setDatosEstadisticos(boolean datosEstadisticos) {
        this.datosEstadisticos = datosEstadisticos;
        setIdRegularizacionNSS(datosEstadisticos,
                TipoRegularizacionNSSEnum.CORRECCION_DATOS_ESTADISTICOS.getId());
    }

    /**
     * @return the duplicidad
     */
    public boolean isDuplicidad() {
        return duplicidad;
    }

    /**
     * @param duplicidad
     *            the duplicidad to set
     */
    public void setDuplicidad(boolean duplicidad) {
        this.duplicidad = duplicidad;
        setIdRegularizacionNSS(duplicidad,
                TipoRegularizacionNSSEnum.CANCELADO_POR_DUPLICIDAD.getId());
    }

    /**
     * @return the noExisteCanase
     */
    public boolean isNoExisteCanase() {
        return noExisteCanase;
    }

    /**
     * @param noExisteCanase
     *            the noExisteCanase to set
     */
    public void setNoExisteCanase(boolean noExisteCanase) {
        this.noExisteCanase = noExisteCanase;
        setIdRegularizacionNSS(noExisteCanase,
                TipoRegularizacionNSSEnum.NO_EXISTE_EN_CANASE.getId());
    }

    /**
     * @return the homonimio
     */
    public boolean isHomonimio() {
        return homonimio;
    }

    /**
     * @param homonimio
     *            the homonimio to set
     */
    public void setHomonimio(boolean homonimio) {
        this.homonimio = homonimio;
        setIdRegularizacionNSS(homonimio,
                TipoRegularizacionNSSEnum.CORRESPONA_UN_HOMONIMO.getId());
    }

    /**
     * @return the otroAsegurado
     */
    public boolean isOtroAsegurado() {
        return otroAsegurado;
    }

    /**
     * @param otroAsegurado
     *            the otroAsegurado to set
     */
    public void setOtroAsegurado(boolean otroAsegurado) {
        this.otroAsegurado = otroAsegurado;
        setIdRegularizacionNSS(otroAsegurado,
                TipoRegularizacionNSSEnum.CORRESPONA_OTRO_ASEGURADO.getId());
    }

    /**
     * @return the regularizarCuentaIndividual
     */
    public boolean isRegularizarCuentaIndividual() {
        return regularizarCuentaIndividual;
    }

    /**
     * @param regularizarCuentaIndividual
     *            the regularizarCuentaIndividual to set
     */
    public void setRegularizarCuentaIndividual(
            boolean regularizarCuentaIndividual) {
        this.regularizarCuentaIndividual = regularizarCuentaIndividual;
        setIdRegularizacionNSS(regularizarCuentaIndividual,
                TipoRegularizacionNSSEnum.REGULARIZAR_CUENTA_INDIVIDUAL.getId());
    }

    private void setIdRegularizacionNSS(boolean isTrue,
            Long idTipoRegularizacionNSS) {
        if (isTrue) {
            getIdRegularizacionNSS().add(idTipoRegularizacionNSS);
        }
    }

    public List<Long> getIdRegularizacionNSS() {
        if (idRegularizacionNSS == null) {
            idRegularizacionNSS = new ArrayList<Long>();
        }
        return idRegularizacionNSS;
    }
}
