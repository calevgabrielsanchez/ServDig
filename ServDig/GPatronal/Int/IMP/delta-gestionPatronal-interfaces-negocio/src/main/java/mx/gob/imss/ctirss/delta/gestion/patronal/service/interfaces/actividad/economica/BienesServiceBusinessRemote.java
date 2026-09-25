package mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.actividad.economica;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Bien;

@Remote
public interface BienesServiceBusinessRemote {
	
	Bien agregarBien(Bien bien) throws Exception;
	
	void eliminarBien(Bien bien) throws Exception;
	
	Bien getBien(Bien bien);
	
	Bien modificarBien(Bien bien) throws Exception;
	
	DatosSalidaPaginador<Bien> paginarBien( DatosEntradaPaginador<Bien> datatablein);


}
