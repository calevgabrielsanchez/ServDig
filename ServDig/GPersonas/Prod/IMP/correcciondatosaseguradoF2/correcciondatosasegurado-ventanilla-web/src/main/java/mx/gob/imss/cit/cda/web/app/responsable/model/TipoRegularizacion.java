/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.responsable.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;
import mx.gob.imss.ctirss.delta.model.enums.TipoRegularizacionSolicitudCDAEnum;

/**
 * 
 * @author antonio
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipoRegularizacion extends BaseModel {

    private static final long serialVersionUID = 1L;
    private boolean correccionCURP;
    private boolean homonimia;
    private boolean correccionDatosBasicos;
    private boolean desvinculacion;
    private boolean duplicidad;
    private boolean invacionCuentaIndividual;
    private boolean cuentaIlogica;
    private Long tipoRegularizacionId;

    private void setTipoRegularizacionId(boolean isTrue,
            Long tipoRegularizacionId) {
        if (isTrue) {
            this.tipoRegularizacionId = tipoRegularizacionId;
        }
    }

    public Long getTipoRegularizacionId() {
        return tipoRegularizacionId;
    }

    /**
     * @return the correccionCURP
     */
    public boolean isCorreccionCURP() {
        return correccionCURP;
    }

    /**
     * @param correccionCURP
     *            the correccionCURP to set
     */
    public void setCorreccionCURP(boolean correccionCURP) {
        this.correccionCURP = correccionCURP;
        setTipoRegularizacionId(correccionCURP,
                TipoRegularizacionSolicitudCDAEnum.CORRECCION_CURP.getId());
    }

    /**
     * @return the homonimia
     */
    public boolean isHomonimia() {
        return homonimia;
    }

    /**
     * @param homonimia
     *            the homonimia to set
     */
    public void setHomonimia(boolean homonimia) {
        this.homonimia = homonimia;
        setTipoRegularizacionId(homonimia,
                TipoRegularizacionSolicitudCDAEnum.HOMONIMIA.getId());
    }

    /**
     * @return the correccionDatosBasicos
     */
    public boolean isCorreccionDatosBasicos() {
        return correccionDatosBasicos;
    }

    /**
     * @param correccionDatosBasicos
     *            the correccionDatosBasicos to set
     */
    public void setCorreccionDatosBasicos(boolean correccionDatosBasicos) {
        this.correccionDatosBasicos = correccionDatosBasicos;
        setTipoRegularizacionId(correccionDatosBasicos,
                TipoRegularizacionSolicitudCDAEnum.CORRECION_DATOS_BASICOS
                        .getId());
    }

    /**
     * @return the desvinculacion
     */
    public boolean isDesvinculacion() {
        return desvinculacion;
    }

    /**
     * @param desvinculacion
     *            the desvinculacion to set
     */
    public void setDesvinculacion(boolean desvinculacion) {
        this.desvinculacion = desvinculacion;
        setTipoRegularizacionId(desvinculacion,
                TipoRegularizacionSolicitudCDAEnum.DESVINCULACION.getId());
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
        setTipoRegularizacionId(duplicidad,
                TipoRegularizacionSolicitudCDAEnum.DUPLICIDAD.getId());
    }

    /**
     * @return the invacionCuentaIndividual
     */
    public boolean isInvacionCuentaIndividual() {
        return invacionCuentaIndividual;
    }

    /**
     * @param invacionCuentaIndividual
     *            the invacionCuentaIndividual to set
     */
    public void setInvacionCuentaIndividual(boolean invacionCuentaIndividual) {
        this.invacionCuentaIndividual = invacionCuentaIndividual;
        setTipoRegularizacionId(invacionCuentaIndividual,
                TipoRegularizacionSolicitudCDAEnum.INVASION.getId());
    }

    public boolean isCuentaIlogica() {
        return cuentaIlogica;
    }

    public void setCuentaIlogica(boolean cuentaIlogica) {
        this.cuentaIlogica = cuentaIlogica;
        setTipoRegularizacionId(cuentaIlogica,
                TipoRegularizacionSolicitudCDAEnum.CUENTA_ILOGICA.getId());
    }
}
