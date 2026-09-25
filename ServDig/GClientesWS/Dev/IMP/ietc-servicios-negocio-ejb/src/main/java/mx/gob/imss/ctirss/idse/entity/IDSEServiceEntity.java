package mx.gob.imss.ctirss.idse.entity;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import mx.gob.imss.ctirss.idse.model.RegistroPatronal;
import mx.gob.imss.ctirss.idse.model.RequerimientoIDSEBean;
import mx.gob.imss.ctirss.idse.persistencia.Patrones;
import mx.gob.imss.ctirss.idse.persistencia.PatronesIdse;
import mx.gob.imss.ctirss.idse.utility.IDSEUtilityLocal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(mappedName="idseEntity", name="idseEntity")
public class IDSEServiceEntity extends IDSEAbstractEntity implements IDSEServiceEntityLocal {
	
	private static final Logger log = LoggerFactory
		.getLogger(IDSEServiceEntity.class);
	
	@EJB
	IDSEUtilityLocal idseUtility;
	
	@Override
	public Patrones consultarRegistroPatronal(String nrp) {
		String hqlQuery = "select patron from Patrones patron where patron.regPatron=:nrp";
		Query query = this.em.createQuery(hqlQuery);
		query.setParameter("nrp", nrp);
		try{
			Patrones patron = (Patrones)query.getSingleResult();
			return patron;
		}catch(NoResultException nre){
			return null;
		}
	}

	@Override
	public void insertarNuevoRegistroPatronal(RegistroPatronal registroPatronal) {
		//TODO: Obtener nrp sin el digito verificador (llega completo el NRP)
		String nrp = registroPatronal.getNrp().substring(0, (registroPatronal.getNrp().length())-1);
		Patrones patronActual = consultarRegistroPatronal(nrp);
		if(patronActual==null){
			log.debug("No existia el rp y se insertará {}", nrp);
			Patrones patron = idseUtility.convertirModelToEntityPatrones(registroPatronal);	
			PatronesIdse patronIdse = idseUtility.convertirModelToEntityPatronesIdse(registroPatronal);
			log.debug("Insertando patron");
			this.em.persist(patron);
			log.debug("Insertando patron idse");
			this.em.persist(patronIdse);
		}else{
			log.debug("Ya existe el RP, no se inserta {}", nrp);
		}
	}

	@Override
	public void insertarNuevoRegistroPatronal(RequerimientoIDSEBean parametros) {
		Patrones patronActual = consultarRegistroPatronal(parametros.getRegPatron());
		if(patronActual==null){
			log.debug("No existia el rp y se insertará");
			Patrones patron = idseUtility.convertirModelToEntityPatrones(parametros);	
			PatronesIdse patronIdse = idseUtility.convertirModelToEntityPatronesIdse(parametros);
			log.debug("Insertando patron");
			this.em.persist(patron);
			log.debug("Insertando patron idse");
			this.em.persist(patronIdse);
		}
	}
}
