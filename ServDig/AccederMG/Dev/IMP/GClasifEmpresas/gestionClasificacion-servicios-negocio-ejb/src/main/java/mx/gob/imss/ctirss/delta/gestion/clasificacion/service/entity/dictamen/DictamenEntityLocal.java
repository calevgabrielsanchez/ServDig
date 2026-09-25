package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.dictamen;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.DictamenDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.EjercicioDictamen;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;

@Local
public interface DictamenEntityLocal {
	
	List<EjercicioDictamen> getPeriodosDictamen();
	List<DictamenDTO> buscarDictamentes(Long idDelegacion, Long idSubdelegacion, Long idPeriodo);
	List<DictamenDTO> buscarDictamentes(DictamenDTO filtros);
	DatosSalidaPaginador<DictamenDTO> consultarDictamentesPaginado(DatosEntradaPaginador<DictamenDTO> datosEntrada);
	Clasificacion getClasificacionDictamen(Long idPatronDictamen, String regPatronal);
}
