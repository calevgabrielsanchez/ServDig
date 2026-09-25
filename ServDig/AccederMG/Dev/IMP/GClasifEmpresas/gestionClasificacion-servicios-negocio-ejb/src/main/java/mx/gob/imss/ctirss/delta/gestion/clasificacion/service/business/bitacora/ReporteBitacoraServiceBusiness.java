/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: ReporteBitacoraServiceBusiness.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.bitacora
 *  @Fecha: 10/10/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.bitacora;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.clasificacion.ExportarReporteException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.bitacora.BitacoraServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.bitacora.ReporteBitacoraServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Constantes;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Utiles;
import mx.gob.imss.ctirss.delta.model.clasificacion.CodigoRolClasificacion;
import mx.gob.imss.ctirss.delta.model.clasificacion.ElementoBitacora;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisModel;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosBitacoras;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

@Stateless(name="reporteBitacoraServiceBusiness", mappedName = "reporteBitacoraServiceBusiness")
public class ReporteBitacoraServiceBusiness  extends AbstractServiceBusiness 
		implements ReporteBitacoraServiceBusinessRemote {

	@EJB
	private BitacoraServiceEntityLocal bitacoraService;
	
	@Override
	public List<ElementoBitacora> obtieneElementosReporteExcel(FiltrosBitacoras filtrosBitacoras, int iRol)
			throws ExportarReporteException {
		List<ElementoBitacora> response = null;
		
		//si el usuario no es de nivel central se verifica el campo delegacion
		if( !(iRol == CodigoRolClasificacion.NORMATIVO_CENTRAL.getCodigo().intValue()) ){		
			if(filtrosBitacoras.getDelegacion() == null || filtrosBitacoras.getDelegacion().equals("-1") || filtrosBitacoras.getDelegacion().equals("")){
				log.debug("************** La consulta no puede realizarce, no se ha recibido el campo de la delegacion, filtro.getDelegacion(): " + filtrosBitacoras.getDelegacion());
				throw new ExportarReporteException("La consulta no se puede realizar, no se ha identificado la delegación del usuario", 1780);
			}
		}
		
		// Valida que el periodo no sea mayor al limite
		log.debug("3 ..... Validacion del rango de fechas permitido ");
		String msj = Utiles.validaPeriodo(filtrosBitacoras.getStrPeriodoInicio(), filtrosBitacoras.getStrPeriodoFin(), Constantes.LIMITE_MESES_REPORTE);

		if ( !msj.equals("true") )
			throw new ExportarReporteException(msj, 1);				
		
		try{
			response = bitacoraService.buscaRegistros(filtrosBitacoras);
			if(response != null){
				log.debug("Registros de bitacora encontrados:"+ response.size());
			}
			
		}catch(Exception re){
			re.printStackTrace();
			throw new ExportarReporteException("No se ha podido recuperar la informaci\u00F3n para construir el reporte", 1720);
		}	
		log.debug("Termina obtieneReporteExcel");			
		return response;
	}
	

	@Override
	public byte[] obtieneReporteExcel(List<ElementoBitacora> listaResultados, 
			FiltrosBitacoras filtrosBitacoras) throws Exception {
		ByteArrayOutputStream a = null;
		HSSFWorkbook bookE = crearReporte(listaResultados, filtrosBitacoras);
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
	private HSSFWorkbook crearReporte(List<ElementoBitacora> listaResultados, 
			FiltrosBitacoras filtrosBitacoras) throws Exception {
		final int COLUMNA_USUARIO = 0;
		final int COLUMNA_ACCION_STATUS = 1;
		final int COLUMNA_COMENTARIO = 2;
		final int COLUMNA_FECHA = 3;
		final int COLUMNA_NRP = 4;

		HSSFRow row;//variable de filas excel
        HSSFWorkbook wb = new HSSFWorkbook();//Se crea el libro Excel
        HSSFSheet sheet = wb.createSheet("Reporte MAC");//Se crea una nueva hoja dentro del libro
        HSSFCellStyle cellStyle;//Celda de estilo
        HSSFCell cell;//celda simple
        int filaCont = 4;//contador de fila
        cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor((short)2);
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        Font font = wb.createFont();//crea la fuente de texto
        font.setFontName("Courier New");//modificacion de la fuente de texto
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);//modificacion de la fuente de texto
        font.setColor(HSSFColor.WHITE.index);
        cellStyle.setFont(font);//modificacion  de la fuente en celda

        row = sheet.createRow(filaCont++);//Se crea una fila dentro de la hoja

    	cell = row.createCell(COLUMNA_USUARIO);
    	cell.setCellValue(Constantes.TEXTO_BITACORA_USUARIO);
    	cell.setCellStyle(cellStyle);

    	cell = row.createCell(COLUMNA_ACCION_STATUS);
    	cell.setCellValue(Constantes.TEXTO_BITACORA_ACCION);
    	cell.setCellStyle(cellStyle);
    	
    	cell = row.createCell(COLUMNA_COMENTARIO);
    	cell.setCellValue(Constantes.TEXTO_BITACORA_COMENTARIO);
    	cell.setCellStyle(cellStyle);

    	cell = row.createCell(COLUMNA_FECHA);
    	cell.setCellValue(Constantes.TEXTO_BITACORA_FECHA);
    	cell.setCellStyle(cellStyle);
        
    	cell = row.createCell(COLUMNA_NRP);
    	cell.setCellValue(Constantes.TEXTO_BITACORA_NRP);
    	cell.setCellStyle(cellStyle);

		for(ElementoBitacora vo : listaResultados) {
            row = sheet.createRow(filaCont++);//Se crea una fila dentro de la hoja
            row.createCell(COLUMNA_USUARIO).setCellValue(vo.getUsuario());
            row.createCell(COLUMNA_ACCION_STATUS).setCellValue(vo.getAcccionRealizada());
            if(vo.getComentario()!=null && vo.getComentario().trim().length()>0)
            	row.createCell(COLUMNA_COMENTARIO).setCellValue(vo.getComentario());
            else
            	row.createCell(COLUMNA_COMENTARIO).setCellValue("");
            row.createCell(COLUMNA_FECHA).setCellValue(vo.getFecha());
        	row.createCell(COLUMNA_NRP).setCellValue(vo.getRegistroPatronal());
		}
		
		String imageFile = "";
		if(filtrosBitacoras.getEsInscripcionInicial()){
			imageFile = Constantes.PATH_IMAGENES + Constantes.BITACORA_IMAGEN_HEADER_INSCRIPCION;
		}else{
			imageFile = Constantes.PATH_IMAGENES + Constantes.BITACORA_IMAGEN_HEADER_MODIFICACION; 
		}
		
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		HSSFClientAnchor anchor = new HSSFClientAnchor(1, 0, 761, 99, (short)0, 0, (short)4, 3);
		patriarch.createPicture(anchor, loadPicture(imageFile, wb ));
		
		//Ajusta ancho de columnas
		sheet.autoSizeColumn(COLUMNA_USUARIO);
		sheet.setColumnWidth(COLUMNA_ACCION_STATUS, 8000);
		sheet.setColumnWidth(COLUMNA_COMENTARIO, 8000);
		sheet.autoSizeColumn(COLUMNA_FECHA);
		sheet.autoSizeColumn(COLUMNA_NRP);

        return wb;
	}

	/**
	 * Metodo que permite insertar imagenes a el excel
	 * @author Jonathan Sanchez Montiel
	 * @param path
	 * @param wb
	 * @return
	 * @throws IOException
	 */
	private int loadPicture(String filePath, HSSFWorkbook wb ) throws IOException {

		int pictureIndex;
		ByteArrayOutputStream bos = null;
		InputStream file =null;
		try {
			//log.info("contextClassLoader ... " + Thread.currentThread().getContextClassLoader().toString());
			log.info("FILE PATH ... " + filePath);
			file = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
		     // read in the image file
		    bos = new ByteArrayOutputStream( );
		    int c;
		   // copy the image bytes into the ByteArrayOutputStream
		    while ( (c = file.read()) != -1)
		        bos.write( c );
		   // add the image bytes to the workbook
		    pictureIndex = wb.addPicture(bos.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG );
		}
		finally {
		    if (file != null)
		    	file.close();
		    if (bos != null)
		        bos.close();
		}
		return pictureIndex;
	}
	
	public void guardaBitacora(EstatusAnalisisModel estatusAnalisisModel) throws Exception{		
		bitacoraService.guardaBitacora(estatusAnalisisModel);
	}


	@Override
	public Clasificacion consultaClasificacionActualPorHistorico(Long idAnalisis)throws Exception{
		return bitacoraService.consultaClasificacionActualPorHistorico(idAnalisis);
	}
}