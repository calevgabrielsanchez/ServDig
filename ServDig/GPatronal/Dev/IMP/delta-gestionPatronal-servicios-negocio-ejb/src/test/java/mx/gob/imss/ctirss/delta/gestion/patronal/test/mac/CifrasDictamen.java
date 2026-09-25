package mx.gob.imss.ctirss.delta.gestion.patronal.test.mac;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.model.clasificacion.ElementoConcentrado;
import mx.gob.imss.ctirss.delta.model.clasificacion.SubdelegacionesConcentrado;

public class CifrasDictamen {

	private static final Logger log;

	static {
		log = LoggerFactory.getLogger(CifrasDictamen.class);
	}

	@Test
	public void generaReporteCifrasDictamen() {

		List<ElementoConcentrado> elemConcent = null;
		String reporteDtm = "C:\\Users\\jonathan.sanchez\\Documents\\Reportes MAC II\\2021-2.dsv";
		String periodo = "2021";
		FileOutputStream fos = null;

		try {
			elemConcent = getRegistrosConcentrado(reporteDtm);
			System.out.println(":: Elementos encontrados en el reporte: " + elemConcent.size());
			int cont = 1;			
			
			if(elemConcent != null && elemConcent.size() > 0){
				System.out.println(":: Obteniendo concentrado");
				ArrayList<SumarizadoConcentrado> list = getSumarizado(elemConcent);
				System.out.println("::: Imprimiendo sumarizado obtenido " + list.size() + " registros");
				
				//creacion del archivo de excel
				byte[] res = obtieneReporteExcel(list, periodo);
				System.out.println("::: Escribiendo archivo");
				fos = new FileOutputStream("C:\\\\Users\\\\jonathan.sanchez\\\\Documents\\\\Reportes MAC II\\ConcentradoDictamen-2021.xls");
				fos.write(res);
				
			}else {
				System.out.println("::: No se encontraron registros");	
			}			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			fos = null;
			Pool.cerrarConexion();
		}

		System.out.println("Termine");

	}

	private byte[] obtieneReporteExcel(ArrayList<SumarizadoConcentrado> listaResultados, String periodo) throws Exception {
		System.out.println("::: Obteniendo EXCEL");
		ByteArrayOutputStream a = null;
		HSSFWorkbook bookE = crearConcentradoPorSubdelegacion(listaResultados, periodo);
		if(bookE !=null){
			a = new ByteArrayOutputStream(bookE.getBytes().length);
//			this.log.debug("ByteArrayOutputStream [ " +a+"]");
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
	private HSSFWorkbook crearConcentradoPorSubdelegacion(ArrayList<SumarizadoConcentrado> reportes, String periodo) throws Exception {

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
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
//		final int COLUMNA_PSE = 11;
				
        int totalRegistros = 0;
        int totalRatificados = 0;
        int totalRectificados = 0;
        int totalBajas = 0;
        int totalImproc = 0;
        int totalPendientes = 0;
        int totalArp = 0;
        int totalPsp = 0;
        int totalRpc = 0;
//        int totalPse = 0;
		
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
    	cell.setCellValue("REPORTE CONSOLIDADO MAC II DICTAMEN - IMSS DIGITAL");
//    	sheet.addMergedRegion(CellRangeAddress.valueOf("A1:L1"));
    	sheet.addMergedRegion(CellRangeAddress.valueOf("A1:K1"));
    	cell.setCellStyle(cellStyle);

    	//sub titulo reporte
        row = sheet.createRow(filaCont++);//Se crea una fila dentro de la hoja
        row.setHeight((short)400);
        
        cell = row.createCell(COLUMNA_DELEGACION);
    	cell.setCellValue("PERIODO: "+periodo+"    -  FECHA DE CREACION " + df.format(new Date()));
    	sheet.addMergedRegion(CellRangeAddress.valueOf("A2:K2"));
//    	sheet.addMergedRegion(CellRangeAddress.valueOf("A2:L2"));
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

//    	cell = row.createCell(COLUMNA_PSE);
//    	cell.setCellValue("PSE");
//    	cell.setCellStyle(cellStyle);

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
//                row.createCell(COLUMNA_PSE).setCellValue(repVO.getPse());
                
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
//                cell = row.createCell(COLUMNA_PSE);
//            	cell.setCellValue(repVO.getPse());
//            	cell.setCellStyle(cellStyle);

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
//                totalPse += repVO.getPse();   
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
//                row.createCell(COLUMNA_PSE).setCellValue("");
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
//        row.createCell(COLUMNA_PSE).setCellValue("");

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
//        cell = row.createCell(COLUMNA_PSE);
//    	cell.setCellValue(totalPse);
//    	cell.setCellStyle(cellStyle);
        
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
//		sheet.setColumnWidth(COLUMNA_PSE, 6000);
        		        
        return wb;
	}
	
	
	
	private ArrayList<SumarizadoConcentrado> getSumarizado(List<ElementoConcentrado> elemConcent) throws Exception {
		
		ArrayList<SumarizadoConcentrado> sumListaNac = new ArrayList<SumarizadoConcentrado>();
		
		log.debug("******* Registros de concentrado encontrados: "+ elemConcent.size());								
		
		ArrayList<ElementoConcentrado> repDel = new ArrayList<ElementoConcentrado>();
		ElementoConcentrado repVO = null;
		ArrayList<SumarizadoConcentrado> sumListaSubDel = new ArrayList<SumarizadoConcentrado>();
		
		Pool.initConexion();
		
		for(int x = 1; x < 41; x++){  //recorre todas las delegaciones
			
//			log.debug("------- Obteniendo reporte de la delegacion " + x);
			
			if(x==9 || x==35 || x==36 || x==37 || x==38 || x==99)
				continue;				
			
			//log.debug("Buscando Subdelegaciones");
			ArrayList<SubdelegacionesConcentrado> subDelegacionesVO = Pool.obtieneSubdelegaciones(x);

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
	        sumListaSubDel = obtieneSumarizadoSubDelegacion(subDelegacionesVO, repDel);

	        //agrega a la lista nacional de sumarizados por subdelegacion
			for (Iterator<SumarizadoConcentrado> iterator = sumListaSubDel.iterator(); iterator.hasNext();) 
				sumListaNac.add((SumarizadoConcentrado) iterator.next());			        
			
		} //for delegaciones
		Pool.cerrarConexion();
		return sumListaNac;
	}
	
    private ArrayList<SumarizadoConcentrado> obtieneSumarizadoSubDelegacion(ArrayList<SubdelegacionesConcentrado> subDelegacionesVO,
			ArrayList<ElementoConcentrado> repDel)
			throws Exception {

		ArrayList<SumarizadoConcentrado> sum = new ArrayList<SumarizadoConcentrado>();
		SumarizadoConcentrado sumVO = null;
		SubdelegacionesConcentrado subDelVO = null;
		String tipoReg = null;
		
		int totalRegistros = 0;
		int ratificado = 0;
		int rectificado = 0;
		int improcedente = 0;
		int pendientes = 0;
		int baja = 0;
		int arp = 0;
		int psp = 0;
		int rpc = 0;
//		int pse = 0;

		int granTotal = 0;
		int totalRatificado = 0;
		int totalRectificado = 0;
		int totalImprocedente = 0;
		int totalBaja = 0;
		int totalPendientes = 0;
		int totalArp = 0;
		int totalPsp = 0;
		int totalRpc = 0;
//		int totalPse = 0;
		
		int idDel = 0;
		String del = "";
			
		for (int y = 0; y < subDelegacionesVO.size(); y++) {

			totalRegistros = 0;
			ratificado = 0;
			rectificado = 0;
			improcedente = 0;
			baja = 0;
			pendientes = 0;
			arp = 0;
			psp = 0;
			rpc = 0;
//			pse = 0;

			subDelVO =  subDelegacionesVO.get(y);
					
			if (subDelVO.getSubDel() != null && subDelVO.getSubDel().trim().length() > 0) {

				idDel = subDelVO.getIdDel();
				del = subDelVO.getDel();
				sumVO = new SumarizadoConcentrado();
				ElementoConcentrado repVO = null;
				
				for (int f = 0; f < repDel.size(); f++) {
					
					repVO = repDel.get(f);

					if(subDelVO.getIdSubDel().intValue() == repVO.getIdSubDel().intValue()){
						totalRegistros += 1;
						
						if(repVO.getEstatus() == 5)
							ratificado += 1;
						else if(repVO.getEstatus() == 6)
							rectificado += 1;
						else if(repVO.getEstatus() == 0 || repVO.getEstatus() == 1 || repVO.getEstatus() == 2 || repVO.getEstatus() == 3 || repVO.getEstatus() == 4
								|| repVO.getEstatus() == 7 || repVO.getEstatus() == 8 || repVO.getEstatus() == 10 || repVO.getEstatus() == 11
								|| repVO.getEstatus() == 12)
							pendientes += 1;
						else if(repVO.getEstatus() == 19)
							improcedente += 1;
						else if(repVO.getEstatus() == 20)
							baja += 1;						
						
						tipoReg = getTipoRegistro(repVO.getInd_marca_Clase(), repVO.getInd_serv_personal());
						
						if(tipoReg.equals("ARP"))
							arp += 1;
						else if(tipoReg.equals("PSP"))
							psp += 1;
						else  if(tipoReg.equals("RPC"))
							rpc += 1;
//						else  if(tipoReg.equals("PSE"))
//							pse +=1;
						
					} //if
					
				} //for
				
				sumVO.setTotalRegistros(totalRegistros);
				sumVO.setRatificado(ratificado);
				sumVO.setRectificado(rectificado);
				sumVO.setImprocedente(improcedente);
				sumVO.setBaja(baja);
				sumVO.setPendientes(pendientes);
				sumVO.setArp(arp);
				sumVO.setPsp(psp);
				sumVO.setRpc(rpc);
//				sumVO.setPse(pse);
				sumVO.setIdDel(subDelVO.getIdDel());
				sumVO.setDel(subDelVO.getDel());
				sumVO.setIdSubDel(subDelVO.getIdSubDel());
				sumVO.setSubDel(subDelVO.getSubDel());				
				
				sum.add(sumVO);
				
				//suma de totales 					
				granTotal = granTotal + totalRegistros;
				totalRatificado = totalRatificado + ratificado;
				totalRectificado = totalRectificado + rectificado;
				totalImprocedente = totalImprocedente + improcedente;
				totalBaja = totalBaja + baja;
				totalPendientes = totalPendientes + pendientes;
				totalArp = totalArp +  arp;
				totalPsp = totalPsp + psp;
				totalRpc = totalRpc + rpc;
//				totalPse = totalPse + pse;				

			} //if
			
		} //for			
			

		// se agregan los totales
		sumVO = new SumarizadoConcentrado();
		
		sumVO.setTotalRegistros(granTotal);
		sumVO.setRatificado(totalRatificado);
		sumVO.setRectificado(totalRectificado);
		sumVO.setImprocedente(totalImprocedente);
		sumVO.setPendientes(totalPendientes);
		sumVO.setBaja(totalBaja);
		sumVO.setArp(totalArp);
		sumVO.setPsp(totalPsp);
		sumVO.setRpc(totalRpc);
//		sumVO.setPse(totalPse);
		sumVO.setIdDel(idDel);
		sumVO.setDel("TOTAL " + del);
		
		sum.add(sumVO);
		

		return sum;
	}
		
	private String getTipoRegistro(String marcaClase, String servPersonal) {
		
		if( (marcaClase == null || Integer.parseInt(marcaClase) == 0) && (servPersonal == null || Integer.parseInt(servPersonal) == 0) ){
			return "ARP";
		}else if( (servPersonal!=null && Integer.parseInt(servPersonal) == 1) && (marcaClase == null || Integer.parseInt(marcaClase) == 0) ){
			return "PSP";			
		}else if( (servPersonal!=null && Integer.parseInt(servPersonal) == 1) &&  (marcaClase!=null && Integer.parseInt(marcaClase) == 1) ){
			return "RPC";	
		}else if( (servPersonal!=null && Integer.parseInt(servPersonal) == 0) &&  (marcaClase!=null && Integer.parseInt(marcaClase) == 1) ){
			return "RPC";	
			
		//por el momento las prestadoras de servicios seran tomadas como PSP
		}else if( (servPersonal!=null && Integer.parseInt(servPersonal) == 2) &&  (marcaClase!=null && Integer.parseInt(marcaClase) == 0) ){
			return "PSP";				
		}
//		else if( (servPersonal!=null && Integer.parseInt(servPersonal) == 2) &&  (marcaClase!=null && Integer.parseInt(marcaClase) == 0) ){
//			return "PSE";	
//		}

		return "";

	}       

	private List<ElementoConcentrado> getRegistrosConcentrado(String rutaArchivo) {
		List<ElementoConcentrado> elemL = new ArrayList<ElementoConcentrado>();
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			// Apertura del fichero y creacion de BufferedReader para poder
			// hacer una lectura comoda (disponer del metodo readLine()).
			archivo = new File(rutaArchivo);
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			// Lectura del fichero
			String linea = null;
			int cont = 1;
			while ((linea = br.readLine()) != null) {
//				System.out.println("---- Linea "+cont+" " + linea);
				cont += 1;
				ElementoConcentrado elemC = new ElementoConcentrado();
				String[] elemArr = linea.split("\\|");
				
				elemC.setIdDel(new Integer(elemArr[2]));
				elemC.setIdSubDel(new Integer(elemArr[4]));
				if(elemArr[23] == null || elemArr[23].equals("")) {
					elemC.setEstatus(new Integer("0"));
				}else {
					elemC.setEstatus(new Integer(elemArr[23]));
				}
				if(elemArr[24] == null || elemArr[24].equals("")) {
					elemC.setInd_serv_personal("0");
				}else {
					elemC.setInd_serv_personal(elemArr[24]);
				}
				if(elemArr[25] == null || elemArr[25].equals("")) {
					elemC.setInd_marca_Clase("0");
				}else {
					elemC.setInd_marca_Clase(elemArr[25]);
				}
				
				elemL.add(elemC);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			// En el finally cerramos el fichero, para asegurarnos
			// que se cierra tanto si todo va bien como si salta
			// una excepcion.
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return elemL;
	}

	//@Test
	public void pruebaConexion() {		
		ArrayList<SubdelegacionesConcentrado> lista = Pool.obtieneSubdelegaciones(2);
//		System.out.println("::: Se encontraron " + lista.size() + " subdelegaciones");
		for (Iterator<SubdelegacionesConcentrado> iterator = lista.iterator(); iterator.hasNext();) {
			SubdelegacionesConcentrado sub = iterator.next();
//			System.out.println(sub.toString());
		}
		Pool.cerrarConexion();
	}

}
