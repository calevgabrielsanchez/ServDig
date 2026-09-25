package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(name = "prorrogaServiciosMedicosDao", mappedName = "prorrogaServiciosMedicosDao")
public class  ProrrogaServiciosMedicosDao implements ProrrogaServiciosMedicosDaoLocal{
	@PersistenceContext (unitName="deltaPersistenceUnit")
	private EntityManager em;
	
	/* TODO REVISAR IMPLEMENTACION POR CAMBIO EN MODELO*/
	public Integer saveProrroga(){
		return null;
	}
	
}
