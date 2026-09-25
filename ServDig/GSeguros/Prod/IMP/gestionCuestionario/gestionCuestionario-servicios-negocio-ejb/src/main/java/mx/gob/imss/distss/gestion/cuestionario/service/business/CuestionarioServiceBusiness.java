package mx.gob.imss.distss.gestion.cuestionario.service.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.persistence.DicCuestionario;
import mx.gob.imss.distss.gestion.cuestionario.excepcion.CuestionarioNoExisteException;
import mx.gob.imss.distss.gestion.cuestionario.modelo.Cuestionario;
import mx.gob.imss.distss.gestion.cuestionario.service.entity.CuestionarioServiceEntityLocal;
import mx.gob.imss.distss.gestion.cuestionario.service.interfaces.CuestionarioServiceBusinessRemote;
import mx.gob.imss.distss.gestion.cuestionario.service.utility.CuestionarioServiceUtilityLocal;

@Stateless(mappedName = "cuestionarioServiceBusiness")
public class CuestionarioServiceBusiness implements
		CuestionarioServiceBusinessRemote {

	@EJB
	private CuestionarioServiceEntityLocal cuestionarioServiceEntity;
	@EJB
	private CuestionarioServiceUtilityLocal cuestionarioServiceUtility;	
	
	public Cuestionario obtenerCuestionario(int idCuestionario)
			throws CuestionarioNoExisteException {
		
		DicCuestionario entity = this.cuestionarioServiceEntity
				.obtenerCuestionario(idCuestionario);
		
		Cuestionario cuestionario = null;
		
		if (entity != null) {
			cuestionario = this.cuestionarioServiceUtility.transformar(entity);
		} else {
			throw new CuestionarioNoExisteException(
					"El cuestionario para el tipo " + idCuestionario
							+ " no existe");
		}
				
		return cuestionario;
	}
}