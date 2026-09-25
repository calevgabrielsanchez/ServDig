/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Jonathan Sanchez Montiel
 *  @Proyecto: delta
 *  @Archivo: ReporteConcentradoServiceBusiness.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.concentrado
 *  @Fecha: 20/10/2014
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.concentrado;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.clasificacion.ExportarReporteException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.concentrado.ConcentradoServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.concentrado.ReporteConcentradoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.concentrado.ConcentradoServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Constantes;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Utiles;
import mx.gob.imss.ctirss.delta.model.clasificacion.CodigoRolClasificacion;
import mx.gob.imss.ctirss.delta.model.clasificacion.ElementoConcentrado;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosConcentrado;
import mx.gob.imss.ctirss.delta.model.clasificacion.SubdelegacionesConcentrado;
import mx.gob.imss.ctirss.delta.model.clasificacion.SumarizadoConcentrado;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;

@Stateless(name="reporteConcentradoServiceBusiness", mappedName = "reporteConcentradoServiceBusiness")
public class ReporteConcentradoServiceBusiness  extends AbstractServiceBusiness 
		implements ReporteConcentradoServiceBusinessRemote {

	@EJB
	private ConcentradoServiceEntityLocal concentradoService;
	
	@EJB
	private ConcentradoServiceUtilityLocal concentradoServiceUtility;
	
	@Override
	public ArrayList<SumarizadoConcentrado> obtieneElementosReporteExcel(FiltrosConcentrado filtrosConcentrado, int idRol, String usuario)
			throws ExportarReporteException, Exception {
		
		List<ElementoConcentrado> elemConcent = null;
		ArrayList<SumarizadoConcentrado> sumListaNac = new ArrayList<SumarizadoConcentrado>();

		try{
			
			//validacion para usuario permitido
			
			log.debug("1 ..... Validacion de usuario permitido para la generacion del reporte "+ new Date());
			if (idRol != CodigoRolClasificacion.NORMATIVO_CENTRAL.getCodigo().intValue()) {
				throw new ExportarReporteException("El usuario "+usuario+" no tiene los permisos para generar el reporte concentrado", 1);
			}
			
			//validacion de horario para la generacion del reporte
	
			log.debug("2 ..... Validacion de horario permitido para la generacion del reporte ");
			String msj = Utiles.validaHorarioReportesNormativo();
			
			if( !msj.equals("true") )	{
				log.debug("************** " + msj);
				throw new ExportarReporteException(msj, 1);
			}
	
			// Valida que el periodo no sea mayor al limite
			log.debug("3 ..... Validacion del rango de fechas permitido ");
			msj = Utiles.validaPeriodo(filtrosConcentrado.getStrPeriodoInicio(), filtrosConcentrado.getStrPeriodoFin(), Constantes.LIMITE_MESES_CONCENTRADO);
			//msj = Utiles.validaLimiteDias(filtrosConcentrado.getStrPeriodoInicio(), filtrosConcentrado.getStrPeriodoFin(), Constantes.LIMITE_DIAS_CONCENTRADO); 
	
			if ( !msj.equals("true") )
				throw new ExportarReporteException(msj, 1);				
			
			log.debug("4 ..... Inicio de consulta la BD ");
			elemConcent = concentradoService.obtieneConcentrado(filtrosConcentrado);
			log.debug("5 ..... Termine de consultar la BD: " + new Date());
			
			if(elemConcent != null && elemConcent.size() > 0){
				log.debug("******* Registros de concentrado encontrados: "+ elemConcent.size());								
				
				ArrayList<ElementoConcentrado> repDel = new ArrayList<ElementoConcentrado>();
				ElementoConcentrado repVO = null;
				ArrayList<SumarizadoConcentrado> sumListaSubDel = new ArrayList<SumarizadoConcentrado>();
				
				for(int x = 1; x < 41; x++){  //recorre todas las delegaciones
					
					log.debug("------- Obteniendo reporte de la delegacion " + x);
					
					if(x==9 || x==35 || x==36 || x==37 || x==38 || x==99)
						continue;				
					
					//log.debug("Buscando Subdelegaciones");
					ArrayList<SubdelegacionesConcentrado> subDelegacionesVO = concentradoService.obtieneSubdelegaciones(x);

					//busca todos los registros de la delegacion
					repDel = new ArrayList<ElementoConcentrado>();
			        for (int y = 0; y < elemConcent.size(); y++) { 		        	
		    			repVO = elemConcent.get(y);
		    			if(repVO.getIdDel() == x){
		    				repDel.add(repVO);	
		    			}
			        }
			        
			        //busca todos los registros por subdelegacion
					sumListaSubDel = new ArrayList<SumarizadoConcentrado>();
			        sumListaSubDel = concentradoServiceUtility.obtieneSumarizadoSubDelegacion(subDelegacionesVO, repDel);

			        //agrega a la lista nacional de sumarizados por subdelegacion
					for (Iterator<SumarizadoConcentrado> iterator = sumListaSubDel.iterator(); iterator.hasNext();) 
						sumListaNac.add((SumarizadoConcentrado) iterator.next());			        
					
				} //for delegaciones
				
			}else
				throw new ExportarReporteException("No se encontraron registros", 1);			
			
			
		}catch(ExportarReporteException re){
			re.printStackTrace();
			throw re;
		}catch(Exception re){
			re.printStackTrace();
			throw re;
		}
		
		log.debug("Termina obtieneElementosReporteExcel Concentrado");	
		
		return sumListaNac;
	}
	

	@Override
	public byte[] obtieneReporteExcel(ArrayList<SumarizadoConcentrado> listaResultados, String fechIni, String fechFin) throws Exception {
		
		ByteArrayOutputStream a = null;
		HSSFWorkbook bookE = crearConcentradoPorSubdelegacion(listaResultados, fechIni, fechFin);
		if(bookE !=null){
			a = new ByteArrayOutputStream(bookE.getBytes().length);
			this.log.debug("ByteArrayOutputStream [ " +a+"]");
			bookE.write(a);
		}
	
		return a.toByteArray();		
	}
	
	
	/**
	 * Genera el Archivo Excel con el reporte
	 * @param listaRetVal
	 * @return
	 * @throws Exception
	 */
	private HSSFWorkbook crearConcentradoPorSubdelegacion(ArrayList<SumarizadoConcentrado> reportes, String fechIni, String fechFin) throws Exception {

		DateFormat df = Constantes.FORMATO_FECHA_DD_MM_YYYY;
		
		final int COLUMNA_DELEGACION = 0;
		final int COLUMNA_SUBDELEGACION = 1;
		final int COLUMNA_TOTAL_REGISTROS = 2;
		final int COLUMNA_RATIFICADO = 3;		
		final int COLUMNA_RECTIFICADO = 4;
		final int COLUMNA_IMPROCEDENTE = 5;
		final int COLUMNA_BAJA = 6;
		final int COLUMNA_PENDIENTES = 7;
		final int COLUMNA_ARP = 8;
		final int COLUMNA_PSP = 9;
		final int COLUMNA_RPC = 10;
				
        int totalRegistros = 0;
        int totalRatificados = 0;
        int totalRectificados = 0;
        int totalBajas = 0;
        int totalImproc = 0;
        int totalPendientes = 0;
        int totalArp = 0;
        int totalPsp = 0;
        int totalRpc = 0;
		
		HSSFRow row;//variable de filas excel
        HSSFWorkbook wb = new HSSFWorkbook();//Se crea el libro Excel
        HSSFSheet sheet = null;

        System.out.println("Creando reporte de Excel");

        sheet = wb.createSheet("Reporte");//Se crea una nueva hoja dentro del libro
        HSSFCellStyle cellStyle;//Celda de estilo
        HSSFCell cell;//celda simple
        Font font = null;
        int filaCont = 0;//contador de fila
        
        //estilo para titulo del reporte
        cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        font = wb.createFont();//crea la fuente de texto
        font.setFontName("Courier New");//modificacion de la fuente de texto
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);//modificacion de la fuente de texto
        font.setColor(HSSFColor.WHITE.index);
        font.setFontHeightInPoints((short)13);
        cellStyle.setFont(font);//modificacion  de la fuente en celda 
        cellStyle.setAlignment((short)2);
        cellStyle.setVerticalAlignment((short)1);

        row = sheet.createRow(filaCont++);//Se crea una fila dentro de la hoja
        row.setHeight((short)800);
        
        cell = row.createCell(COLUMNA_DELEGACION);
    	cell.setCellValue("REPORTE CONSOLIDADO MAC II - IMSS DIGITAL");
    	sheet.addMergedRegion(CellRangeAddress.valueOf("A1:K1"));
    	cell.setCellStyle(cellStyle);

    	//sub titulo reporte
        row = sheet.createRow(filaCont++);//Se crea una fila dentro de la hoja
        row.setHeight((short)400);
        
        cell = row.createCell(COLUMNA_DELEGACION);
    	cell.setCellValue("DEL "+fechIni+" AL "+fechFin+"    -  FECHA DE CREACION " + df.format(new Date()));
    	sheet.addMergedRegion(CellRangeAddress.valueOf("A2:K2"));
    	cell.setCellStyle(cellStyle);

        //estilo para encabezados
        cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor((short)2);
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        font = wb.createFont();//crea la fuente de texto
        font.setFontName("Courier New");//modificacion de la fuente de texto
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);//modificacion de la fuente de texto
        font.setColor(HSSFColor.WHITE.index);
        font.setFontHeightInPoints((short)12);
        cellStyle.setFont(font);//modificacion  de la fuente en celda  
        cellStyle.setAlignment((short)2);
        cellStyle.setVerticalAlignment((short)1);
        
        
        
        
        row = sheet.createRow(filaCont++);//Se crea una fila dentro de la hoja
        row.setHeight((short)500);
        
        cell = row.createCell(COLUMNA_DELEGACION);
    	cell.setCellValue("DELEGACION");
    	cell.setCellStyle(cellStyle);

        cell = row.createCell(COLUMNA_SUBDELEGACION);
    	cell.setCellValue("SUBDELEGACION");
    	cell.setCellStyle(cellStyle);

    	cell = row.createCell(COLUMNA_TOTAL_REGISTROS);
    	cell.setCellValue("TOTAL DE REGISTROS");
    	cell.setCellStyle(cellStyle);

    	cell = row.createCell(COLUMNA_RATIFICADO);
    	cell.setCellValue("RATIFICADO");
    	cell.setCellStyle(cellStyle);

    	cell = row.createCell(COLUMNA_RECTIFICADO);
    	cell.setCellValue("RECTIFICADO");
    	cell.setCellStyle(cellStyle);

    	cell = row.createCell(COLUMNA_IMPROCEDENTE);
    	cell.setCellValue("IMPROCEDENTE");
    	cell.setCellStyle(cellStyle);

    	cell = row.createCell(COLUMNA_BAJA);
    	cell.setCellValue("BAJA");
    	cell.setCellStyle(cellStyle);

    	cell = row.createCell(COLUMNA_PENDIENTES);
    	cell.setCellValue("PENDIENTES");
    	cell.setCellStyle(cellStyle);

    	cell = row.createCell(COLUMNA_ARP);
    	cell.setCellValue("ARP");
    	cell.setCellStyle(cellStyle);

    	cell = row.createCell(COLUMNA_PSP);
    	cell.setCellValue("PSP");
    	cell.setCellStyle(cellStyle);

    	cell = row.createCell(COLUMNA_RPC);
    	cell.setCellValue("RPC");
    	cell.setCellStyle(cellStyle);

    	SumarizadoConcentrado repVO = null;
    	
    	//estilo para totales por delegacion
        cellStyle = wb.createCellStyle();
        font = wb.createFont();//crea la fuente de texto
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);//modificacion de la fuente de texto
        cellStyle.setFont(font);//modificacion  de la fuente en celda    	    	
 	
        for (int y = 0; y < reportes.size(); y++) {
        	repVO = (SumarizadoConcentrado)reportes.get(y);
        	
            row = sheet.createRow(filaCont++);//Se crea una fila dentro de la hoja
            
            //si los resultados son de la subdelegacion
            if( !(repVO.getSubDel() == null || repVO.getSubDel().trim().length() == 0) ){
            	
            	row.createCell(COLUMNA_DELEGACION).setCellValue(repVO.getDel());
                if(repVO.getSubDel() != null)
                	row.createCell(COLUMNA_SUBDELEGACION).setCellValue(repVO.getSubDel());
                else
                	row.createCell(COLUMNA_SUBDELEGACION).setCellValue("");
                
                row.createCell(COLUMNA_TOTAL_REGISTROS).setCellValue(repVO.getTotalRegistros());
                row.createCell(COLUMNA_RATIFICADO).setCellValue(repVO.getRatificado());
                row.createCell(COLUMNA_RECTIFICADO).setCellValue(repVO.getRectificado());
                row.createCell(COLUMNA_IMPROCEDENTE).setCellValue(repVO.getImprocedente());
                row.createCell(COLUMNA_BAJA).setCellValue(repVO.getBaja());
                row.createCell(COLUMNA_PENDIENTES).setCellValue(repVO.getPendientes());
                row.createCell(COLUMNA_ARP).setCellValue(repVO.getArp());
                row.createCell(COLUMNA_PSP).setCellValue(repVO.getPsp());
                row.createCell(COLUMNA_RPC).setCellValue(repVO.getRpc());
                
            }else{ //si es el total por delegacion
            	
                cell = row.createCell(COLUMNA_DELEGACION);
            	cell.setCellValue(repVO.getDel());
            	cell.setCellStyle(cellStyle);
               	row.createCell(COLUMNA_SUBDELEGACION).setCellValue("");
                cell = row.createCell(COLUMNA_TOTAL_REGISTROS);
            	cell.setCellValue(repVO.getTotalRegistros());
            	cell.setCellStyle(cellStyle);
                cell = row.createCell(COLUMNA_RATIFICADO);
            	cell.setCellValue(repVO.getRatificado());
            	cell.setCellStyle(cellStyle);
                cell = row.createCell(COLUMNA_RECTIFICADO);
            	cell.setCellValue(repVO.getRectificado());
            	cell.setCellStyle(cellStyle);
                cell = row.createCell(COLUMNA_IMPROCEDENTE);
            	cell.setCellValue(repVO.getImprocedente());
            	cell.setCellStyle(cellStyle);
                cell = row.createCell(COLUMNA_BAJA);
            	cell.setCellValue(repVO.getBaja());
            	cell.setCellStyle(cellStyle);
                cell = row.createCell(COLUMNA_PENDIENTES);
            	cell.setCellValue(repVO.getPendientes());
            	cell.setCellStyle(cellStyle);
                cell = row.createCell(COLUMNA_ARP);
            	cell.setCellValue(repVO.getArp());
            	cell.setCellStyle(cellStyle);
                cell = row.createCell(COLUMNA_PSP);
            	cell.setCellValue(repVO.getPsp());
            	cell.setCellStyle(cellStyle);
                cell = row.createCell(COLUMNA_RPC);
            	cell.setCellValue(repVO.getRpc());
            	cell.setCellStyle(cellStyle);

            	//suma totales por delegacion para obteber el total nacional
                totalRegistros += repVO.getTotalRegistros();
                totalRatificados += repVO.getRatificado();
                totalRectificados += repVO.getRectificado();
                totalImproc += repVO.getImprocedente();
                totalBajas += repVO.getBaja();
                totalPendientes += repVO.getPendientes();
                totalArp += repVO.getArp();
                totalPsp += repVO.getPsp();
                totalRpc += repVO.getRpc();            	
            }

            if(repVO.getSubDel() == null || repVO.getSubDel().trim().length() == 0){
                row = sheet.createRow(filaCont++);//Se crea una fila vacia
                row.createCell(COLUMNA_DELEGACION).setCellValue("");
                row.createCell(COLUMNA_SUBDELEGACION).setCellValue("");
                row.createCell(COLUMNA_TOTAL_REGISTROS).setCellValue("");
                row.createCell(COLUMNA_RATIFICADO).setCellValue("");
                row.createCell(COLUMNA_RECTIFICADO).setCellValue("");
                row.createCell(COLUMNA_IMPROCEDENTE).setCellValue("");
                row.createCell(COLUMNA_BAJA).setCellValue("");
                row.createCell(COLUMNA_PENDIENTES).setCellValue("");
                row.createCell(COLUMNA_ARP).setCellValue("");
                row.createCell(COLUMNA_PSP).setCellValue("");
                row.createCell(COLUMNA_RPC).setCellValue("");
            }
            
        } //for
        
        row = sheet.createRow(filaCont++);//Se crea una fila vacia
        row.createCell(COLUMNA_DELEGACION).setCellValue("");
        row.createCell(COLUMNA_SUBDELEGACION).setCellValue("");
        row.createCell(COLUMNA_TOTAL_REGISTROS).setCellValue("");
        row.createCell(COLUMNA_RATIFICADO).setCellValue("");
        row.createCell(COLUMNA_RECTIFICADO).setCellValue("");
        row.createCell(COLUMNA_IMPROCEDENTE).setCellValue("");
        row.createCell(COLUMNA_BAJA).setCellValue("");
        row.createCell(COLUMNA_PENDIENTES).setCellValue("");
        row.createCell(COLUMNA_ARP).setCellValue("");
        row.createCell(COLUMNA_PSP).setCellValue("");
        row.createCell(COLUMNA_RPC).setCellValue("");

    	//estilo para totales nacional
        cellStyle = wb.createCellStyle();
        font = wb.createFont();//crea la fuente de texto
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);//modificacion de la fuente de texto
        font.setFontHeightInPoints((short)12);
        cellStyle.setFont(font);//modificacion  de la fuente en celda    	    	

        //Se crea fila para totales nacionales        
        row = sheet.createRow(filaCont++);
        
        cell = row.createCell(COLUMNA_DELEGACION);
    	cell.setCellValue("TOTAL");
    	cell.setCellStyle(cellStyle);
        row.createCell(COLUMNA_SUBDELEGACION).setCellValue("");
        cell = row.createCell(COLUMNA_TOTAL_REGISTROS);
    	cell.setCellValue(totalRegistros);
    	cell.setCellStyle(cellStyle);
        cell = row.createCell(COLUMNA_RATIFICADO);
    	cell.setCellValue(totalRatificados);
    	cell.setCellStyle(cellStyle);
        cell = row.createCell(COLUMNA_RECTIFICADO);
    	cell.setCellValue(totalRectificados);
    	cell.setCellStyle(cellStyle);
        cell = row.createCell(COLUMNA_IMPROCEDENTE);
    	cell.setCellValue(totalImproc);
    	cell.setCellStyle(cellStyle);
        cell = row.createCell(COLUMNA_BAJA);
    	cell.setCellValue(totalBajas);
    	cell.setCellStyle(cellStyle);
        cell = row.createCell(COLUMNA_PENDIENTES);
    	cell.setCellValue(totalPendientes);
    	cell.setCellStyle(cellStyle);
        cell = row.createCell(COLUMNA_ARP);
    	cell.setCellValue(totalArp);
    	cell.setCellStyle(cellStyle);
        cell = row.createCell(COLUMNA_PSP);
    	cell.setCellValue(totalPsp);
    	cell.setCellStyle(cellStyle);
        cell = row.createCell(COLUMNA_RPC);
    	cell.setCellValue(totalRpc);
    	cell.setCellStyle(cellStyle);
        
		//Ajusta ancho de columnas
		sheet.setColumnWidth(COLUMNA_DELEGACION, 9000);
		sheet.setColumnWidth(COLUMNA_SUBDELEGACION, 6000);
		sheet.setColumnWidth(COLUMNA_TOTAL_REGISTROS, 7000);
		sheet.setColumnWidth(COLUMNA_RATIFICADO, 6000);
		sheet.setColumnWidth(COLUMNA_RECTIFICADO, 6000);
		sheet.setColumnWidth(COLUMNA_IMPROCEDENTE, 6000);
		sheet.setColumnWidth(COLUMNA_BAJA, 6000);
		sheet.setColumnWidth(COLUMNA_PENDIENTES, 6000);
		sheet.setColumnWidth(COLUMNA_ARP, 6000);
		sheet.setColumnWidth(COLUMNA_PSP, 6000);
		sheet.setColumnWidth(COLUMNA_RPC, 6000);
        		        
        return wb;
	}

}