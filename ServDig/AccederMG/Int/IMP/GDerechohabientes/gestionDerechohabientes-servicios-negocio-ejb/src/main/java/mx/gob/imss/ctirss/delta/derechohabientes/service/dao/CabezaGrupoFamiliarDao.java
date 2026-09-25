package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.CabezaGrupoFamiliarParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;

import org.apache.log4j.Logger;

@Stateless(name = "cabezaGrupoFamiliarDao", mappedName = "cabezaGrupoFamiliarDao")
public class CabezaGrupoFamiliarDao implements CabezaGrupoFamiliarDaoLocal {
	
	private static final Logger logger = Logger.getLogger(CabezaGrupoFamiliarDao.class);

	@PersistenceContext(unitName = "deltaPersistenceUnit")
	EntityManager em;
	
	@Override
	public CabezaGrupoFamiliar getCabezaGrupoFamiliar(Long idAsignacionNss) throws DerechohabientesBusinessException,Exception {
		
		CabezaGrupoFamiliar cabeza = null;
		/*
		try {
			DitCabezaGrupoFamiliar ditCabeza = em.find(DitCabezaGrupoFamiliar.class, idAsignacionNss);
			cabeza = CabezaGrupoFamiliarParser.persisToModel(ditCabeza);
		}catch (NoResultException e){
			cabeza = null;
		}catch (Exception e) {
			logger.error("Error - GetCabezaGrupoFamiliar",e);
			throw e;
		}
		*/
		
		return cabeza;
	}

	@Override
	public void updateCabezaGrupoFamiliar(CabezaGrupoFamiliar cabezaGrupoFamiliar) throws DerechohabientesBusinessException,Exception {
		/*
		try {
			DitCabezaGrupoFamiliar ditCabeza = CabezaGrupoFamiliarParser.modelToPersist(cabezaGrupoFamiliar);
			em.merge(ditCabeza);
		} catch (Exception e) {
			logger.error("updateCabezaGrupoFamiliar",e);
			throw e;		
		}
		*/
		
	}

}
