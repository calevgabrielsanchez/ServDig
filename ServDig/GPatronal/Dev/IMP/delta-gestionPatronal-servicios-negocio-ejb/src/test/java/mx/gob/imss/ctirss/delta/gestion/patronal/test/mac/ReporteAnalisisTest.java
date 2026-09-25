package mx.gob.imss.ctirss.delta.gestion.patronal.test.mac;

import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.ReportesAnalisisBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.EjbLocator;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosReportes;
import mx.gob.imss.ctirss.delta.model.clasificacion.ReporteAnalisis;

public class ReporteAnalisisTest {
	
	
	private static final Logger log;

	static {
		log = LoggerFactory.getLogger(ReporteAnalisisTest.class);
	}	

	@Test
	public void consultarReporteAnalisisAlmacenes(){
		try {
			log.info("::: Obteniendo EJB ");
			ReportesAnalisisBusinessRemote ejb = EjbLocator.getReportesAnalisisBusinessRemote();
			log.info("::: Consultando informacion");
			
			FiltrosReportes filtrosReportes = new FiltrosReportes();
			int rol = 10; //Jefe Depto
			
			
			Date periodoI = new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-01");
			Date periodoF = new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-31");
			
			log.info("periodoI: " + periodoI);
			log.info("periodoF: " + periodoF);

			filtrosReportes.setTipoRegistro(null);
//			filtrosReportes.setTipoMovimiento("1");
			filtrosReportes.setCveIdGrupoAnalisisCe("1");
			filtrosReportes.setPeriodoInicio(periodoI);
			filtrosReportes.setPeriodoFin(periodoF);
			filtrosReportes.setStrPeriodoInicio("01/01/2025");
			filtrosReportes.setStrPeriodoFin("31/01/2025");
			filtrosReportes.setDelegacion("6");
			
			
			List<ReporteAnalisis> listaRetVal = ejb.consultarReporteAnalisisAlmacenes(filtrosReportes, rol);
			System.out.println("Regrese: " + listaRetVal.size());
//			for (Iterator<ReporteAnalisis> iterator = listaRetVal.iterator(); iterator.hasNext();) {
//				ReporteAnalisis reporteAnalisis = iterator.next();
//				System.out.println(reporteAnalisis);
//			}

			
			log.info("::: Comenzando con EXCEL");
			
			
			int cveIdGrupoAnalisisCe = 1;
			
//			if(listaRetVal!=null && listaRetVal.size() > 0){
//				try {
//					byte[] res = 
//							ejb.crearReporteAnalisisAlmacenes(listaRetVal, cveIdGrupoAnalisisCe);
//						
//					log.info("::: Escribiendo EXCEL");
//					try (FileOutputStream fos = new FileOutputStream("C:\\temp\\archivo.xls")) {
//					      fos.write(res);
//					      //fos.close // no need, try-with-resources auto close
//					  }
//
//				}catch(Exception e) {
//					log.error("Error en el metodo init  previo : " + e);
//					e.printStackTrace();
//				}
//			}else{
//				log.debug("**************LA busqueda no obtuvo registros.");
//			}
//			
			
			
			log.info("::: Termine");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 public Date parseDate(String date) {
	     try {
	         return new SimpleDateFormat("yyyy-MM-dd").parse(date);
	     } catch (ParseException e) {
	         return null;
	     }
	  }
	
}
