/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.reportes;

import java.util.Date;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.derechohabientes.reportes.SpRespuestaCommon;
import mx.gob.imss.ctirss.delta.model.derechohabientes.reportes.SpRespuestaDescargaReporte;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.ReporteRegistro;
import mx.gob.imss.ctirss.delta.model.util.ReporteEnum;

/**
 * @author ghdolores
 * 
 */
@Remote
public interface ReportesRemote {

	Object getReporteSav011(Integer idAsegurado);

	DatosSalidaPaginador<ReporteRegistro> getReporteRegistroUnionCivil(String fechaInicio, String fechaFin,
			Long cveDelegacion, Long cveSubdelegacion) throws Exception;

	String generaReporte(String curp, Long cveIdDelegacionUser, Integer userNacional,
			Long cveIdDelegacion, Long cveIdSubdelegacion, Date fechaInicial, Date fechaFinal, ReporteEnum reporte);

	String obtieneEstatusReporte(String folio, ReporteEnum reporte);

	SpRespuestaDescargaReporte descargaReporte(String folio, ReporteEnum reporte) throws Exception;

	SpRespuestaCommon eliminaReporteDescargado(String folio, String curpUsuario, ReporteEnum reporte);

	void estatusDepuracionReportes(Date parFecha);

}
