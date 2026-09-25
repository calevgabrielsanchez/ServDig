package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.DomicilioParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.asegurado.Asegurado;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;
import mx.gob.imss.ctirss.delta.persistence.DgDomicilioGeografico;

import org.apache.log4j.Logger;

/**
 * @author Juan Manuel Marquez
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 11/04/2012
 */
@Stateless(name = "domicilioGeograficoDao", mappedName = "domicilioGeograficoDao")
public class DomicilioGeograficoDao implements DomicilioGeograficoDaoLocal {
	
	
	private static final Logger logger = Logger.getLogger(DomicilioGeograficoDao.class);

	@PersistenceContext (unitName="deltaPersistenceUnit")
	private EntityManager em;

	

	

	@Override
	public Domicilio getDomicilioGrupoFamiliar(Asegurado asegurado) throws DerechohabientesBusinessException,Exception {		
		DgDomicilioGeografico miDomGeografico = new DgDomicilioGeografico();
		try {
			miDomGeografico = (DgDomicilioGeografico) em.createNamedQuery("DitGrupoFamiliar.busqDomicilioGrupoFamiliar")
			.setParameter("idAsignacionNSS", asegurado.getAsignacionNSS().getIdAsignacionNSS());
		}catch(NoResultException e){
			miDomGeografico = null;
		}catch (Exception e) {
			logger.error("getDomicilioGrupoFamiliar", e);
			throw e;
		}
		
		return DomicilioParser.persisToModel(miDomGeografico);
	}





	@Override
	public Domicilio ubicarDomicilioGeografico(TipoDomicilio tipoDomicilio) {
		// TODO Auto-generated method stub
		return null;
	}

}
