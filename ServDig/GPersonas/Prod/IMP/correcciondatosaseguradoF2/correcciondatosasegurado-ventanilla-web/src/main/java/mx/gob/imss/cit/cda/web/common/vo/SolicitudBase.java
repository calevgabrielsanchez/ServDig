package mx.gob.imss.cit.cda.web.common.vo;

import java.util.List;

import mx.gob.imss.cit.cda.web.app.responsable.model.InformacionRENAPO;
import mx.gob.imss.cit.cda.web.app.responsable.model.SubDelegacion;
import mx.gob.imss.cit.cda.web.bandeja.vo.TareaTramite;
import mx.gob.imss.cit.cda.web.support.model.BaseModel;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
*
* @author mon
*/
@JsonIgnoreProperties(ignoreUnknown = true)
public class SolicitudBase  extends BaseModel {

    /**
     * Modelo Base de Solicitud para vista CDA Ventanilla
     */
    private static final long serialVersionUID = 6241459601983146391L;

    private String idSolicitud;
    private String folio;
    private String idTarea;
    private String idTramite;
    private List<TareaTramite> tareasTramites;
    private SubDelegacion subDelegacion;
    private InformacionRENAPO informacionRENAPO;
    private String fechaInicio;
    private String curpResponsable;
    
    /**
     * @return the idSolicitud
     */
    public String getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * @param idSolicitud
     *            the idSolicitud to set
     */
    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }
    
    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }
    
    /**
     * @return the idTarea
     */
    public String getIdTarea() {
        return idTarea;
    }

    /**
     * @param idTarea
     *            the idTarea to set
     */
    public void setIdTarea(String idTarea) {
        this.idTarea = idTarea;
    }

    /**
     * @return the idTramite
     */
    public String getIdTramite() {
        return idTramite;
    }

    /**
     * @param idTramite
     *            the idTramite to set
     */
    public void setIdTramite(String idTramite) {
        this.idTramite = idTramite;
    }
    
    public List<TareaTramite> getTareasTramites() {
        return tareasTramites;
    }

    public void setTareasTramites(List<TareaTramite> tareasTramites) {
        this.tareasTramites = tareasTramites;
    }
    
    public SubDelegacion getSubDelegacion() {
        return subDelegacion;
    }

    public void setSubDelegacion(SubDelegacion subDelegacion) {
        this.subDelegacion = subDelegacion;
    }
    
    public InformacionRENAPO getInformacionRENAPO() {
        return informacionRENAPO;
    }

    public void setInformacionRENAPO(InformacionRENAPO informacionRENAPO) {
        this.informacionRENAPO = informacionRENAPO;
    }
    
    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    public String getCurpResponsable() {
        return curpResponsable;
    }

    public void setCurpResponsable(String curpResponsable) {
        this.curpResponsable = curpResponsable;
    }

}
