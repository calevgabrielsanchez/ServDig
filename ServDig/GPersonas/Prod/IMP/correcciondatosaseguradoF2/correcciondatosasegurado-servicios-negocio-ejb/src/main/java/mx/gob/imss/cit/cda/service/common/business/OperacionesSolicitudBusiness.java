package mx.gob.imss.cit.cda.service.common.business;

import javax.ejb.EJB;

import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoCDARemote;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperacionesSolicitudBusiness {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    @EJB(name = "solicitudBusiness", mappedName = "solicitudBusiness")
    private SolicitudBusinessRemote solicitudBusiness;
    
    @EJB(name = "flujoTrabajoBusiness", mappedName = "flujoTrabajoBusiness")
    private FlujoTrabajoRemote flujoTrabajoRemote;

    @EJB(name = "flujoTrabajoCDABusiness", mappedName = "flujoTrabajoCDABusiness")
    private FlujoTrabajoCDARemote flujoTrabajoCDARemote;


    public FlujoTrabajoCDARemote getFlujoTrabajoCDARemote() {
		return flujoTrabajoCDARemote;
	}

    public FlujoTrabajoRemote getFlujoTrabajoRemote() {
        return flujoTrabajoRemote;
    }

    public SolicitudBusinessRemote getSolicitudBusiness() {
        return solicitudBusiness;
    }

    public Logger getLog() {
        return log;
    }

}
