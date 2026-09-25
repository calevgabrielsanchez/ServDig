package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import mx.gob.imss.ctirss.delta.persistence.DitCambioMasivoClinica;

@Stateless(name = "cambioMasivoClinicaDao", mappedName = "cambioMasivoClinicaDao")


public class CambioMasivoClinicaDao implements CambioMasivoClinicaDaoLocal {
	
	@PersistenceContext()
	private EntityManager em;
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.derechohabientes.service.dao.CambioMasivoClinicaDaoLocal#save(mx.gob.imss.ctirss.delta.persistence.DitCambioMasivoClinica)
	 */
	@Override
	public  DitCambioMasivoClinica save(DitCambioMasivoClinica cambioMasivoClinica){

		
		em.persist(cambioMasivoClinica);
		return cambioMasivoClinica;
	}
	
}
