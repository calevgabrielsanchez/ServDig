package mx.gob.imss.distss.gestion.cuestionario.service.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.persistence.DicCuestionario;
import mx.gob.imss.distss.gestion.cuestionario.modelo.Cuestionario;

@Local
public interface CuestionarioServiceUtilityLocal {

	Cuestionario transformar(DicCuestionario entity);

	DicCuestionario transformar(Cuestionario model);

}
