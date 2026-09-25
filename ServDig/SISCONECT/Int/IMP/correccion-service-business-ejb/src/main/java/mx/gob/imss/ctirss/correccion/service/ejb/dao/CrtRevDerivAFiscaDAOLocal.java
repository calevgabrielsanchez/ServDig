package mx.gob.imss.ctirss.correccion.service.ejb.dao;

import mx.gob.imss.ctirss.correccion.model.CrtRevDerivAFisca;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.GenericDAO;

public interface CrtRevDerivAFiscaDAOLocal extends GenericDAO<CrtRevDerivAFisca, Long> {
	
	public CrtRevDerivAFisca buscaPorSolicitudCorr(Integer cveSolicitudCorr);

}
