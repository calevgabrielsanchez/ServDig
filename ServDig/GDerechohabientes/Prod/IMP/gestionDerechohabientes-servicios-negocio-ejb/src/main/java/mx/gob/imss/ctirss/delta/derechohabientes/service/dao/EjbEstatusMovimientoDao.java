/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless(name = "ejbEstatusMovimientoDao", mappedName = "ejbEstatusMovimientoDao")
public class EjbEstatusMovimientoDao implements EjbEstatusMovimientoDaoLocal {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void altaEstatusMovimiento() {
//		System.out
//				.println("**********************EjbEstatusMovimiento. Inicio");

//		if (true) {
//			throw new EJBException();
//		}

//		System.out.println("Alta de datos (NO DEBERA IMPRIMIRSE ESTA LINEA");
//		System.out
//				.println("**********************EjbDelegacionDao. Final \n\n");
	}
}
