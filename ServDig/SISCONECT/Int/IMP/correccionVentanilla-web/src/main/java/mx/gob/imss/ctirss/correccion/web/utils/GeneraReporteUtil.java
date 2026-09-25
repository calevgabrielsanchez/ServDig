package mx.gob.imss.ctirss.correccion.web.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import mx.gob.imss.ctirss.correccion.presentacion.service.interfaces.GeneraReporteService;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class GeneraReporteUtil implements GeneraReporteService {
	
	private static GeneraReporteUtil INSTANCE = null;
	
	private GeneraReporteUtil(){}
	
	private synchronized static void createInstance(){
		if(INSTANCE == null){
			INSTANCE = new GeneraReporteUtil();
		}
	}
	
	public static GeneraReporteUtil getInstance(){
		if(INSTANCE == null) createInstance();
		return INSTANCE;
	}

	@SuppressWarnings("rawtypes")
	public byte[] generaReporte(String rutaArchivoJasper, Map parametros, List<Object> list) {
		byte[] reporte = null;
		try {
			InputStream in = new FileInputStream(rutaArchivoJasper);
			reporte = JasperRunManager.runReportToPdf(in, parametros, new JRBeanCollectionDataSource(list, false));
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return reporte;
	}

	@SuppressWarnings("rawtypes")
	public byte[] generaReporte(String rutaArchivoJasper, Map parametros, Collection<Object> datasource) {
		byte[] reporte = null;
		try {
			InputStream in = new FileInputStream(rutaArchivoJasper);
			reporte = JasperRunManager.runReportToPdf(in, parametros, new JRBeanCollectionDataSource(datasource, false));
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return reporte;
	}

	@SuppressWarnings("rawtypes")
	public byte[] generaReporte(String rutaArchivoJasper, Map parametros) {
		byte[] reporte = null;
		try {
			InputStream in = new FileInputStream(rutaArchivoJasper);
			reporte = JasperRunManager.runReportToPdf(in, parametros, new JREmptyDataSource());
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return reporte;	}
}