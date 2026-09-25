package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.actividad.economica;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Personal;

@Local
public interface PersonalServiceBusinessLocal {
	
	DatosSalidaPaginador<Personal> paginarPersonal( DatosEntradaPaginador<Personal> datatablein);
	
	Personal agregarPersonal(Personal instance) throws Exception;
	
	void eliminarPersonal(Personal personal) throws Exception;
	
	Personal getPersonal(Personal personal);
	
	Personal modificarPersonal(Personal instance) throws Exception;

}
