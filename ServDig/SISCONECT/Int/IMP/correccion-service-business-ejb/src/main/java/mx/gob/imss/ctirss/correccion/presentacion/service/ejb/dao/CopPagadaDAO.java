package mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao;

import mx.gob.imss.ctirss.correccion.model.CrtCoppagada;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.GenericDAO;

public interface CopPagadaDAO extends GenericDAO<CrtCoppagada, Long> {
	
	public Long isFolioCorreccionConCopPagada(Integer solicitudCorreccion);
	
	
	public Object[] sumaCOPbyClaveAnexoSolCorr(Integer claveAnexoSolCorr);
	
	
	public Object[] sumaRCVbyClaveAnexoSolCorr(Integer claveAnexoSolCorr);
	
}
