package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness.reportes;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.asegurado.DitAseguradoDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.ManejadorReportesLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.reportes.ReportesRemote;
import mx.gob.imss.ctirss.delta.model.derechohabientes.ReporteSav011;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.RegistroDerechohabientesDaoLocal;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.ReporteRegistro;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;

@Stateless(name = "reportes", mappedName = "reportes")
public class Reportes implements ReportesRemote {

	@EJB
	private DitAseguradoDaoLocal aseguradoDao;

	@EJB
	private ManejadorReportesLocal manejadorReportes;

	@EJB
	private RegistroDerechohabientesDaoLocal registroDerechohabienteDaoLocal;	

	@Override
	public Object getReporteSav011(Integer idAsegurado) {

		ReporteSav011 dato = aseguradoDao.getAsegurado(1);
		List<ReporteSav011> lista = new ArrayList<ReporteSav011>();
		lista.add(dato);

		Map<String, Object> parametros = new HashMap<String, Object>();
		String reporte = "SAV011.jrxml";

		ByteArrayOutputStream repo=manejadorReportes.ejecutaReporte(parametros, lista, reporte);

		return repo.toByteArray();
	}

	@Override
	public DatosSalidaPaginador<ReporteRegistro> getReporteRegistroConyugeConcubinario(String fechaInicio, String fechaFin, Long cveDelegacion, Long cveSubdelegacion) throws Exception {
		DatosSalidaPaginador<ReporteRegistro> salidaPaginador=new DatosSalidaPaginador<ReporteRegistro>();
		List<ReporteRegistro> registros = null;

		registros = registroDerechohabienteDaoLocal.findRegistroConyugeConcubinario(fechaInicio, fechaFin, cveDelegacion, cveSubdelegacion);
		salidaPaginador.setAaData(registros);
		salidaPaginador.setiTotalRecords(1);
		salidaPaginador.setiTotalDisplayRecords(1);
		return salidaPaginador;

	}	

}
