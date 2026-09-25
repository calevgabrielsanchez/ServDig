/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.hibernate.Session;
import org.springframework.core.io.ClassPathResource;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

/**
 * 
 * @author I
 */
@Stateless(name="manejadorReportes", mappedName = "manejadorReportes")
public class ManejadorReportes implements ManejadorReportesLocal {


	@PersistenceContext(unitName="deltaPersistenceUnit")
	private EntityManager em;
	
	//@Resource(name = "pathIMG")
	private String pathIMG = "reportes/";
	//@Resource(name = "pathReportes")
	private String pathReportes = "reportes/";

	@Override
	public ByteArrayOutputStream ejecutaHolaMundo(String cveSolicitud) {

		Map parametros = new HashMap();
		parametros.put("hola", cveSolicitud);

		return ejecutaReporte(parametros, "hola.jrxml");
	}

	// Ejecuta el reporte Jasper y regresa un ByteArrayOutputStream
	@SuppressWarnings("deprecation")
	private ByteArrayOutputStream ejecutaReporte(Map parametros, String reporte) {

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			
			Session session = (Session)em.getDelegate();
			Connection conn = session.connection();
			
			JasperReport report = JasperCompileManager.compileReport(new ClassPathResource("reportes/"+reporte).getInputStream());
			JasperPrint print = JasperFillManager.fillReport(report,
					parametros, conn);

			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					byteArrayOutputStream);
			
			
			exporter.exportReport();

			
			return byteArrayOutputStream;

		} catch (Exception e) {// Agregar las excepciones personalizadas
			e.getMessage();
		}

		return null;
	}

	@Override
	public ByteArrayOutputStream ejecutaReporte(Map parametros,List<? extends Serializable> lista, String reporte) {
		// TODO Auto-generated method stub
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		try {
			JRBeanCollectionDataSource dataSource;
			dataSource = new JRBeanCollectionDataSource(lista);
			
			JasperReport report = JasperCompileManager.compileReport(new ClassPathResource("reportes/"+reporte).getInputStream());
			
			JasperPrint print = JasperFillManager.fillReport(report,parametros,dataSource);

			
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					byteArrayOutputStream);
			
			
			exporter.exportReport();

			
			return byteArrayOutputStream;
		} catch (Exception e) {// Agregar las excepciones personalizadas
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public ByteArrayOutputStream ejecutaReportePlantillas(Map parametros,List<? extends Serializable> lista, List<String> reportes) {
		// TODO Auto-generated method stub
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		List<JasperPrint> arreglo = new ArrayList<JasperPrint>(); 
		
		try {
			//System.out.println("************* contexto "+ClassLoader.getSystemResource("/reportes/img/imss.jpg"));
			//URL l=ClassLoader.getSystemResource("/reportes/img/imss.jpg");
			JRBeanCollectionDataSource dataSource;
			JasperPrint print = new JasperPrint();
			
			if(parametros.get("LOGO") == null) {
				ClassPathResource c = new ClassPathResource("reportes/img/imss.jpg"); 
				parametros.put("LOGO", c.getPath());
			}
			for (String reporte : reportes) {
				JasperReport report = (JasperReport) JRLoader.loadObject(new ClassPathResource("reportes/"+reporte).getInputStream());
				dataSource = new JRBeanCollectionDataSource(lista);
				print = JasperFillManager.fillReport(report,parametros,dataSource);
				arreglo.add(print);
			}
			
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,arreglo);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
	 		exporter.exportReport(); //Exportar al archivo PD

			
			return byteArrayOutputStream;
		} catch (Exception e) {// Agregar las excepciones personalizadas
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public JasperPrint imprimeReporte(Map parametros,
			List<? extends Serializable> lista, String reporte) {

		JasperPrint print =  null;
		JRBeanCollectionDataSource dataSource;
		dataSource = new JRBeanCollectionDataSource(lista);
	
		try{
		JasperReport report 
         = JasperCompileManager.compileReport(new ClassPathResource("reportes/"+reporte).getInputStream());
		
		print = JasperFillManager.fillReport(report, parametros, dataSource);
                
		}catch(Exception e){
			e.printStackTrace();
		}
	
		return print;
	}

	@Override
	public ByteArrayOutputStream ejecutaReporteCompilado(Map parametros,List<? extends Serializable> lista, String reporte) {
		// TODO Auto-generated method stub
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		try {
			JRBeanCollectionDataSource dataSource;
			dataSource = new JRBeanCollectionDataSource(lista);
			
			
			JasperReport report = (JasperReport) JRLoader.loadObject(new ClassPathResource("reportes/"+reporte).getInputStream());
			@SuppressWarnings("unchecked")
			JasperPrint print = JasperFillManager.fillReport(report,parametros,dataSource);

			
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					byteArrayOutputStream);
			
			
			exporter.exportReport();

			
			return byteArrayOutputStream;
		} catch (Exception e) {// Agregar las excepciones personalizadas
			System.out.println("Error al genear el reporte");
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public JasperPrint getReporteCompilado(Map parametros,List<? extends Serializable> lista, String reporte) {
		// TODO Auto-generated method stub
		
		try {
			JRBeanCollectionDataSource dataSource;
			dataSource = new JRBeanCollectionDataSource(lista);
			
			JasperReport report = (JasperReport) JRLoader.loadObject(new ClassPathResource("reportes/"+reporte).getInputStream());
			@SuppressWarnings("unchecked")
			JasperPrint print = JasperFillManager.fillReport(report,parametros,dataSource);

			return print;
		} catch (Exception e) {// Agregar las excepciones personalizadas
			System.out.println("Error al genear el reporte");
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public ByteArrayOutputStream mergeReporteCompilado(List<JasperPrint> jasperPrints) {
		// TODO Auto-generated method stub
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		try {
		
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrints);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					byteArrayOutputStream);
			
			exporter.exportReport();
			
			return byteArrayOutputStream;
		} catch (Exception e) {// Agregar las excepciones personalizadas
			System.out.println("Error al genear el reporte");
			e.printStackTrace();
		}
		
		return null;
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public  ByteArrayOutputStream ejecutaReporteSubreporte(Map parametros,List<? extends Serializable> lista, 
			String reporte,Map<String, String> plantillas) {
		
		JasperReport subreport=null;
		Iterator it = plantillas.entrySet().iterator();
		while (it.hasNext()) {
			
			Map.Entry<String,Object> e = (Map.Entry<String,Object>)it.next();
			//Key va a ser la plantilla, value va a ser el parametro
			try {
				subreport = (JasperReport) JRLoader.loadObject(new ClassPathResource("reportes/"+e.getKey()).getInputStream());
				parametros.put(e.getValue().toString(), subreport);
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
		}
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		try {
			JRBeanCollectionDataSource dataSource;
			dataSource = new JRBeanCollectionDataSource(lista);
			
			if(parametros.get("LOGO") == null) {
				ClassPathResource c = new ClassPathResource("reportes/img/imss.jpg"); 
				parametros.put("LOGO", c.getPath());
			}
			JasperReport report = (JasperReport) JRLoader.loadObject(new ClassPathResource("reportes/"+reporte).getInputStream());
			JasperPrint print = JasperFillManager.fillReport(report, parametros, dataSource);

			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					byteArrayOutputStream);
			
			exporter.exportReport();
			
			return byteArrayOutputStream;
		} catch (Exception e) {// Agregar las excepciones personalizadas
			e.printStackTrace();
		}
		
		return null;
		
	}
		

	  
		@SuppressWarnings("unchecked")
		@Override
	    public ByteArrayOutputStream  concatPDF(List<ByteArrayOutputStream>  byteArrayOutputStream, boolean paginate ) {
	    	
	        Document document = new Document();
	        OutputStream outputStream = new ByteArrayOutputStream();
	        try {
	            List<ByteArrayOutputStream> pdfs = byteArrayOutputStream;
	            List<PdfReader> readers = new ArrayList<PdfReader>();
	            int totalPages = 0;
	            Iterator<ByteArrayOutputStream> iteratorPDFs = pdfs.iterator();
	            
	            while (iteratorPDFs.hasNext()) {
	            	ByteArrayOutputStream pdf = iteratorPDFs.next();
	                PdfReader pdfReader = new PdfReader(pdf.toByteArray());
	                readers.add(pdfReader);
	                totalPages += pdfReader.getNumberOfPages();
	            }
	            
	            
	            
	            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
	            
	            
	 
	            document.open();
	            PdfContentByte cb = writer.getDirectContent();
	            PdfImportedPage page;
	            int currentPageNumber = 0;
	            int pageOfCurrentReaderPDF = 0;
	            Iterator<PdfReader> iteratorPDFReader = readers.iterator();
	 
	            while (iteratorPDFReader.hasNext()) {
	                PdfReader pdfReader = iteratorPDFReader.next();
	 
	                while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
	 
	                    Rectangle rectangle = pdfReader.getPageSizeWithRotation(1);
	                    document.setPageSize(rectangle);
	                    document.newPage();
	 
	                    pageOfCurrentReaderPDF++;
	                    currentPageNumber++;
	                    page = writer.getImportedPage(pdfReader,
	                            pageOfCurrentReaderPDF);
	                    switch (rectangle.getRotation()) {
	                    case 0:
	                        cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
	                        break;
	                    case 90:
	                        cb.addTemplate(page, 0, -1f, 1f, 0, 0, pdfReader
	                                .getPageSizeWithRotation(1).getHeight());
	                        break;
	                    case 180:
	                        cb.addTemplate(page, -1f, 0, 0, -1f, 0, 0);
	                        break;
	                    case 270:
	                        cb.addTemplate(page, 0, 1.0F, -1.0F, 0, pdfReader
	                                .getPageSizeWithRotation(1).getWidth(), 0);
	                        break;
	                    default:
	                        break;
	                    }
	                    if (paginate) {
	                        cb.beginText();
	                        cb.getPdfDocument().getPageSize();
	                        cb.endText();
	                    }
	                }
	                pageOfCurrentReaderPDF = 0;
	            }
	            document.close();
	          
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (document.isOpen())
	                document.close();
	        }
	        return (ByteArrayOutputStream)outputStream;
	    }
		
		
		
		
	


}
