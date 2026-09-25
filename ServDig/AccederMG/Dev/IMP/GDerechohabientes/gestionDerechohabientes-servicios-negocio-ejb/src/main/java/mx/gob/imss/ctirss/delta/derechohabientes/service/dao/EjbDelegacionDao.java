/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;


/**
 * 
 * @author jponte
 */
@Stateless(name = "ejbDelegacionDao", mappedName = "ejbDelegacionDao")
public class EjbDelegacionDao implements EjbDelegacionDaoLocal {

	@PersistenceContext()
	private EntityManager em;

	@Override
	public void altaDelegacion(final Delegacion delegacionDatos) {
//		System.out.println("**********************EjbDelegacionDao. Inicio");
		
		/*SmcDelegacion delegacion = new SmcDelegacion();
		delegacion.setAnioIniOper(String.valueOf(delegacionDatos
				.getAnioInicoOperacion()));
		delegacion.setClaveDelegacion(delegacionDatos.getCveDelegacion());
		//delegacion.setCveIdDelegacion( delegacionDatos.getIdDelegacion().longValue() );
		delegacion.setDesDeleg(delegacionDatos.getDescDelegacion());
		//delegacion.setTipDelegacion(BigInteger.valueOf(500L));

		System.out.println("Alta de datos");
		em.persist(delegacion);
*/		
//		System.out
//				.println("**********************EjbDelegacionDao. Final \n\n");
	}
}
