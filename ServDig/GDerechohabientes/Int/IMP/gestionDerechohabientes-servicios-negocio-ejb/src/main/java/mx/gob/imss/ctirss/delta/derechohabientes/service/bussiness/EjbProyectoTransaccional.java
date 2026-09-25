/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.EjbDelegacionDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.EjbEstatusMovimientoDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.EjbEstatusSolicitudDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.EjbProyectoTransaccionalLocal;
import mx.gob.imss.ctirss.delta.model.derechohabientes.AltaTransaccional;


@Stateless(name = "ejbProyectoTransaccional", mappedName = "ejbProyectoTransaccional")
public class EjbProyectoTransaccional implements EjbProyectoTransaccionalLocal {

	@EJB
	private EjbDelegacionDaoLocal ejbDelegacion;

	@EJB
	private EjbEstatusMovimientoDaoLocal ejbMovimiento;

	@EJB
	private EjbEstatusSolicitudDaoLocal ejbSolicitud;

	@Override
	public void alta(AltaTransaccional datos) {
		ejbSolicitud.altaEstadoSolicitud(datos.getEstadoSolicitud());

		ejbDelegacion.altaDelegacion(datos.getDelegacion());

		// VERIFICA SI GENERA UNA EXCEPCION AL RECIBIR LA DELEGACION 900
		if (datos.getDelegacion().getId()== 900L) {
			// GENERA EXCEPCION Y SE EJECUTA UN ROLLBACK EN TODA LA TRANSACCION
			ejbMovimiento.altaEstatusMovimiento();
		}
	}

}
