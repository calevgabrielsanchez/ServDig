package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;


@Stateless(name = "ejbEstatusSolicitudDao", mappedName = "ejbEstatusSolicitudDao")
public class EjbEstatusSolicitudDao implements EjbEstatusSolicitudDaoLocal {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void altaEstadoSolicitud(
			final EstadoSolicitud datosEstadoSolicitud) {
//		System.out.println();
//		System.out.println("**********************EjbEstatusSolicitud. Inicio");
//		System.out.println(" datosEstatusSolicitud.getIdEstatusSolicitud(): "
//				+ datosEstadoSolicitud.getIdEstadoSolicitud());
//		System.out.println("datosEstatusSolicitud.getDescEstatusSolicitud(): "
//				+ datosEstadoSolicitud.getDescripcion());

		/*SmcEstatusSolicitud estatus = new SmcEstatusSolicitud();
		estatus.setCveIdEstatusSolicitud( datosEstatusSolicitud
				.getIdEstatusSolicitud() );
		estatus.setDesEstatusSolicitud(datosEstatusSolicitud
				.getDescEstatusSolicitud());
		estatus.setIndActivo(new BigDecimal(1));

//		System.out.println("Alta de datos");
		em.persist(estatus);
*/
//		System.out
//				.println("**********************EjbEstatusSolicitud. Final \n\n");
	}
}
