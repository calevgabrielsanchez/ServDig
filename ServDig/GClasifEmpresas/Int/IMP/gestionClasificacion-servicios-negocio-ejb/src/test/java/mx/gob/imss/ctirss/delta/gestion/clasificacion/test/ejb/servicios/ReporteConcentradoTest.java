package mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.servicios;

import java.util.ArrayList;
import java.util.Iterator;

import mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.utils.EJBLocator;
import mx.gob.imss.ctirss.delta.model.clasificacion.CodigoRolClasificacion;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosConcentrado;
import mx.gob.imss.ctirss.delta.model.clasificacion.SumarizadoConcentrado;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReporteConcentradoTest {
	
	private static final Logger LOG;

	static {
		LOG = LoggerFactory.getLogger(ReporteConcentradoTest.class);
	}
	
	

	@Test
	public void obtieneReporteConcentrado(){
		try {
			
			FiltrosConcentrado filtrosConcentrado = new FiltrosConcentrado();
			filtrosConcentrado.setStrPeriodoInicio("01/01/2015");
			filtrosConcentrado.setStrPeriodoFin("31/03/2015");
			filtrosConcentrado.setCveIdGrupoAnalisisCe("2");
			
			final ArrayList<SumarizadoConcentrado> sumConc = EJBLocator.getConcentradoServiceBusiness()
					.obtieneElementosReporteExcel(filtrosConcentrado,CodigoRolClasificacion.NORMATIVO_CENTRAL.getCodigo().intValue(), "jsm");			
			
			System.out.println("Encontre " + sumConc.size() + " registros");
			for (Iterator<SumarizadoConcentrado> iterator = sumConc.iterator(); iterator.hasNext();) {
				SumarizadoConcentrado sumarizadoConcentrado = iterator.next();
				sumarizadoConcentrado.toString();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
}
