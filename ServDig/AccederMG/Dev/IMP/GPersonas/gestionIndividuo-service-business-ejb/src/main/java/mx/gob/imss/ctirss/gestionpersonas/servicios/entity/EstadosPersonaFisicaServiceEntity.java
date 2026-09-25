package mx.gob.imss.ctirss.gestionpersonas.servicios.entity;

import java.util.Date;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.persistence.DitHistEstadoPersona;
import mx.gob.imss.ctirss.delta.persistence.DitHistEstadoPersonaPK;

/**
 * 181012
 * @author ICCSRG
 *
 */
@Stateless(name="estadosPersonaFisicaServiceEntity", mappedName="estadosPersonaFisicaServiceEntity")
public class EstadosPersonaFisicaServiceEntity extends AbstractServiceEntity implements EstadosPersonaFisicaServiceEntityLocal {

	/**
	 * Metodo encargado de realizar el registro de los estados asociados a una persona fisica en BDU
	 * @param ditHistEstadoPersona
	 * @return
	 */
	@Override
	public void registrar(DitHistEstadoPersona ditHistEstadoPersona) {

		Date fechaAlta = new Date();

		ditHistEstadoPersona.setFecRegistroActualizado(fechaAlta);
		ditHistEstadoPersona.setFecRegistroAlta(fechaAlta);
		
		DitHistEstadoPersonaPK idEstado = new DitHistEstadoPersonaPK();
		idEstado.setCveEstadoPersona(ditHistEstadoPersona.getDicEstadoPersona().getCveEstadoPersona());
		idEstado.setCveIdPersona(ditHistEstadoPersona.getDitPersona().getCveIdPersona());
		
		ditHistEstadoPersona.setId(idEstado);
		
		em.persist(ditHistEstadoPersona);
			
	}

	/**
	 * Metodo encargado de realizar la actualizacion de los estados asociados a una persona fisica en BDU
	 * @param ditHistEstadoPersona
	 * @return
	 */
	@Override
	public void actualizar(DitHistEstadoPersona ditHistEstadoPersona) {
		// TODO Auto-generated method stub
	}

}
