package mx.gob.imss.ctirss.delta.derechohabientes.service.entity;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.ProrrogaParserServiceLocal;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteProrroga;
import mx.gob.imss.ctirss.delta.persistence.DitProrroga;

@Stateless(name = "prorrogaEntity", mappedName = "prorrogaEntity")
public class ProrrogaDerechohabienteEntity extends AbstractServiceEntity implements ProrrogaDerechohabienteEntityLocal{
	
	@EJB ProrrogaParserServiceLocal parser;

	@Override
	public void insert(TramiteProrroga tramiteProrroga) {
		try{
			DitProrroga entity = parser.modelToPersist(tramiteProrroga);
			em.persist(entity);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}