package mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.actividad.economica;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Personal;

@Remote
public interface PersonalServiceBusinessRemote {
	
	Personal agregarPersonal(Personal instance) throws Exception;
	
	void eliminarPersonal(Personal personal) throws Exception;
	
	Personal getPersonal(Personal personal);
	
	Personal modificarPersonal(Personal instance) throws Exception;
	
	DatosSalidaPaginador<Personal> paginarPersonal( DatosEntradaPaginador<Personal> datatablein);

}
