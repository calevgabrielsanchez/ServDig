package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.EjbProyectoTransaccionalLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.RegistraSolicitudFachada;
import mx.gob.imss.ctirss.delta.model.derechohabientes.AltaTransaccional;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;

/**
 * @author Ivan Cervantes
 * 
 * */

// EJB de la capa de PROCESO//

@Stateless(name = "registraSolicitudImp", mappedName = "registraSolicitudImp")
public class RegistraSolicitudImp implements RegistraSolicitudFachada {

	@EJB
	// EJB de la capa de SERVICIO//
	private EjbProyectoTransaccionalLocal ejbProyectoTransaccional;

	@Override
	public String registra() {

		Delegacion delegacion = new Delegacion();
		/*delegacion.setAnioInicoOperacion(2011L);
		delegacion.setCveDelegacion("600");
		delegacion.setDescDelegacion("Delegacion 600 de prueba");
		delegacion.setIdDelegacion(600L);*/

		EstadoSolicitud estatusSolicitud = new EstadoSolicitud();
		estatusSolicitud.setActivo(true);
		estatusSolicitud
				.setDescripcion("Estatus solicitud de prueba 600");
		estatusSolicitud.setIdEstadoSolicitud(600);

		/*
		 * Division division = new Division(); division.setActivo(true);
		 * division.setDescDivision("Division de prueba 600");
		 * division.setIdDivision(600L);
		 */

		AltaTransaccional altaTransaccional = new AltaTransaccional();
		altaTransaccional.setDelegacion(delegacion);
		altaTransaccional.setEstadoSolicitud(estatusSolicitud);
		// altaTransaccional.setDivision(division);
		// Mandamos a llamar al EJB de servicio//

		ejbProyectoTransaccional.alta(altaTransaccional);
		return "EXITO";
	}

}
