package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.Solicitud;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitud;

/**
 * @author Juan Manuel Marquez
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 11/04/2012
 */
@Stateless(name = "registrarDerechohabienteDao", mappedName = "registrarDerehohabienteDao")
public class PistasAuditoriaDao implements PistasAuditoriaDaoLocal {

	@PersistenceContext (unitName="deltaPersistenceUnit")
	private EntityManager em;
	
	@Override
	public Solicitud busquedaSolicitud(Long folio) {

		DitSolicitud ditSolEncontrada;
		
		Solicitud resultado = new Solicitud();

		return resultado;
	}

}
