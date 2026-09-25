package mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao;

import mx.gob.imss.ctirss.correccion.model.CrtInvitacion;
import mx.gob.imss.ctirss.correccion.model.CrtPresentacorr;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.GenericDAO;

public interface PresentacionCorreccionDAO extends GenericDAO<CrtPresentacorr, Integer> {
	
	public Boolean isFolioCorreccionPresentado(Integer solicitudCorrecionID);
	public CrtInvitacion buscarInvitacionDeSolicitudCorreccion(Integer solicitudCorreccionID);
	public CrtPresentacorr getByClaveSolCorr(Integer claveSolCorr);
	
}
