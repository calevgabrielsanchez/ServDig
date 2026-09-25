package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.clasificacion.ConsultaReporteException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.DictamenDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.EjercicioDictamen;
import mx.gob.imss.ctirss.delta.model.clasificacion.ReporteAnalisis;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;

@Remote
public interface DictamenServiceBusinessRemote {
	
	List<EjercicioDictamen> getPeriodosDictamen();
	List<DictamenDTO> buscarDictamentes(Long idDelegacion, Long idSubdelegacion, Long idPeriodo);
	DatosSalidaPaginador<DictamenDTO> consultarDictamentesPaginado(DatosEntradaPaginador<DictamenDTO> datosEntrada);
	List<ReporteAnalisis> consultarReporteAnalisisDictamen(DictamenDTO filtros, Integer rol) throws ConsultaReporteException;
	Clasificacion getClasificacionDictamen(Long idPatronDictamen, String regPatronal);
	String getEjercicioFiscal(Long idEjercicio);
}
