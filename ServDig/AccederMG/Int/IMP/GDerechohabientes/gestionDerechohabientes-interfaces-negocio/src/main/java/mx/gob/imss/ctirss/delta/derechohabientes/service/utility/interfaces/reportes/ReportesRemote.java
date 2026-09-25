/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.reportes;

import javax.ejb.Remote;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.ReporteRegistro;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;

/**
 * @author ghdolores
 * 
 */
@Remote
public interface ReportesRemote {

	Object getReporteSav011(Integer idAsegurado);

	DatosSalidaPaginador<ReporteRegistro> getReporteRegistroConyugeConcubinario(String fechaInicio, String fechaFin, Long cveDelegacion, Long cveSubdelegacion) throws Exception;

}
