package mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao;

import java.util.List;

import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.model.ErrorValidation;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.GenericDAO;

public interface AnexoSolicitudCorreccionDAO 
			extends GenericDAO<CrtAnexosolcorrpat, Integer> {
	
	public List<ErrorValidation> isFolioCorrecionConAnexoPatronal(Integer solicitudCorreccion);
	public List<Object> getByClaveSolicitudCorrEjercicio(Integer claveSolCorr, Long ejercicio);
	public CrtAnexosolcorrpat getByClaveSolicitudCorrRegistroPatronal(Integer claveSolCorr,String registroPatronal);
	public String getRegistroPatronalByAnexoSolCorrPat(Integer anexoSolCorrPatt);
	public List<Long> getEjerciciosByCveSolCorr(Integer cveSolCorrPat);
	
}
