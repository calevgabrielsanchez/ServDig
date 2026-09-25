/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: ReporteAnalisisBusiness.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.analisis
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.analisis;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import mx.gob.imss.ctirss.cliente.clasificacion.ClienteClasificacionMACII;
import mx.gob.imss.ctirss.cliente.clasificacion.dto.InfoConsultaMacII;
import mx.gob.imss.ctirss.cliente.clasificacion.dto.RequestMacII;
import mx.gob.imss.ctirss.cliente.clasificacion.dto.ResponseMacII;
import mx.gob.imss.ctirss.delta.exception.clasificacion.ConsultaReporteException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.analisis.ReportesAnalisisEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.domicilio.DelegacionServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.domicilio.SubDelegacionServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.ReportesAnalisisBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Constantes;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.util.FiltroSolicitudesReporte;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Utiles;
import mx.gob.imss.ctirss.delta.model.clasificacion.CodigoRolClasificacion;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosReportes;
import mx.gob.imss.ctirss.delta.model.clasificacion.GrupoAnalisisCeEnum;
import mx.gob.imss.ctirss.delta.model.clasificacion.ReporteAnalisis;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;



@Stateless(name = "reporteAnalisisBusiness", mappedName = "reporteAnalisisBusiness")
public class ReporteAnalisisBusiness extends AbstractServiceBusiness implements
ReportesAnalisisBusinessRemote {

	@EJB
	private ReportesAnalisisEntityLocal reportesAnalisis;
	
	@EJB
	private DelegacionServiceEntityLocal delegacionEntity;	
	
	@EJB
	private SubDelegacionServiceEntityLocal subdelegacionEntity;
		
	@Override
	public List<ReporteAnalisis> consultarReporteAnalisis(
			FiltrosReportes filtro, int iRol) throws ConsultaReporteException {
		
		List<ReporteAnalisis> listaRetVal = new ArrayList<ReporteAnalisis>();
		
		log.debug("INICIO PARA CONSULTAR EL REPORTE DE ANALISIS EFECTUADOS");
				
		//si el usuario no es de nivel central se verifica el campo delegacion
		if( !(iRol == CodigoRolClasificacion.NORMATIVO_CENTRAL.getCodigo().intValue()) ){		

			if(filtro.getDelegacion() == null || filtro.getDelegacion().equals("-1") || filtro.getDelegacion().equals("")){
				log.debug("************** La consulta no puede realizarce, no se ha recibido el campo de la delegacion, filtro.getDelegacion(): " + filtro.getDelegacion());
				throw new ConsultaReporteException("La consulta no se puede realizar, no se ha identificado la delegación del usuario", 1780);
			}

		}else{ //Si el usuario es normativo central y el reporte es nacional se valida el horario permitido para este reporte
			
			log.debug("El usuario es normativo de nivel central");
			
			if(filtro.getDelegacion() == null || filtro.getDelegacion().equals("-1") || filtro.getDelegacion().equals("")){
				
				log.debug("La consulta es nacional, se valida el horario permitido para el reporte");
				
				String msj = Utiles.validaHorarioReportesNormativo();
				
				if(!msj.equals("true"))	{
					log.debug("************** " + msj);
					throw new ConsultaReporteException(msj, 1780);
				}
			}			
		}
		
		// Valida que el periodo no sea mayor al limite
		log.debug("Validacion del rango de fechas permitido ");
		String msj = Utiles.validaPeriodo(filtro.getStrPeriodoInicio(), filtro.getStrPeriodoFin(), Constantes.LIMITE_MESES_REPORTE);

		if ( !msj.equals("true") )
			throw new ConsultaReporteException(msj, 1);				
		
		try {

			listaRetVal = reportesAnalisis.consultarReportesAnalisis(filtro);

			//log.debug("BUSINESS - La lista es:: " + listaRetVal);
		} catch (Exception e) {
			log.error("ERROR- " + e.getMessage());
			throw new ConsultaReporteException("Error al consultar el reporte.", 1780);
		}
		
		log.debug("TERMINO DE CONSULTAR REPORTE DE ANALISIS");
		
		return listaRetVal;
	}
	
	@Override
	public List<ReporteAnalisis> consultarReporteAnalisisAlmacenes(FiltrosReportes filtro, int iRol) throws ConsultaReporteException {
		// TODO Auto-generated method stub
		
		log.debug("INICIO PARA CONSULTAR EL REPORTE DE ANALISIS EFECTUADOS");
		
		//si el usuario no es de nivel central se verifica el campo delegacion
		if( !(iRol == CodigoRolClasificacion.NORMATIVO_CENTRAL.getCodigo().intValue()) ){		

			if(filtro.getDelegacion() == null || filtro.getDelegacion().equals("-1") || filtro.getDelegacion().equals("")){
				log.debug("************** La consulta no puede realizarce, no se ha recibido el campo de la delegacion, filtro.getDelegacion(): " + filtro.getDelegacion());
				throw new ConsultaReporteException("La consulta no se puede realizar, no se ha identificado la delegación del usuario", 1780);
			}

		}else{ //Si el usuario es normativo central y el reporte es nacional se valida el horario permitido para este reporte
			
			log.debug("El usuario es normativo de nivel central");
			
			if(filtro.getDelegacion() == null || filtro.getDelegacion().equals("-1") || filtro.getDelegacion().equals("")){
				
				log.debug("La consulta es nacional, se valida el horario permitido para el reporte");
				
				String msj = Utiles.validaHorarioReportesNormativo();
				
				if(!msj.equals("true"))	{
					log.debug("************** " + msj);
					throw new ConsultaReporteException(msj, 1780);
				}
			}			
		}
		
		// Valida que el periodo no sea mayor al limite
		log.debug("Validacion del rango de fechas permitido ");
		String msj = Utiles.validaPeriodo(filtro.getStrPeriodoInicio(), filtro.getStrPeriodoFin(), Constantes.LIMITE_MESES_REPORTE);

		if ( !msj.equals("true") )
			throw new ConsultaReporteException(msj, 1);
		
		//Inicia consulta
		List<ReporteAnalisis> lista=new ArrayList<ReporteAnalisis>();
		ReporteAnalisis reporte=null;		
		
		ClienteClasificacionMACII servicio=new ClienteClasificacionMACII();
		RequestMacII parametros=new RequestMacII();		
		String clasePropuesta = "";
		
		if(filtro.getClasePropuesta()!=null && !filtro.getClasePropuesta().equals("-1") && !filtro.getClasePropuesta().isEmpty()){
			switch(Integer.parseInt(filtro.getClasePropuesta())){
				case 1:
					clasePropuesta="I";
					break;				
				case 2:
					clasePropuesta="II";
					break;
				case 3:
					clasePropuesta="III";
					break;
				case 4:
					clasePropuesta="IV";
					break;
				case 5:
					clasePropuesta="V";
					break;
				default:
					clasePropuesta="";
					break;
			}
		}
		parametros.setClaseRectificada(clasePropuesta);
		try {
			System.out.println("Buscando delegacion "+filtro.getDelegacion()+" Subdelegacion "+filtro.getSubDelegacion());
			Subdelegacion subdelegacion = new Subdelegacion();
			mx.gob.imss.ctirss.delta.model.domicilio.Delegacion delegacion=new mx.gob.imss.ctirss.delta.model.domicilio.Delegacion();
			
			if(filtro.getDelegacion()!=null && !filtro.getDelegacion().equals("") && !filtro.getDelegacion().equals("-1")){
				System.out.println("Buscando delegacion "+filtro.getDelegacion());
				delegacion.setId(Long.parseLong(filtro.getDelegacion()));		
				parametros.setCveIdDelegacion(rellenaCeros(delegacionEntity.consultaPorId(delegacion).getClave(), 2));
			} else {
				parametros.setCveIdDelegacion("");
			}
			
			if(filtro.getSubDelegacion()!=null && !filtro.getSubDelegacion().equals("") && !filtro.getSubDelegacion().equals("-1")){
				System.out.println("Buscando subdelegacion "+filtro.getSubDelegacion());
				subdelegacion.setId(Long.parseLong(filtro.getSubDelegacion()));
				parametros.setCveIdSubdelegacion(rellenaCeros(subdelegacionEntity.consultaPorId(subdelegacion).getClave(), 2));	
			} else {
				parametros.setCveIdSubdelegacion("");
			}
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}	
		
		if(filtro.getEstatus()!=null && !filtro.getEstatus().equals("")){
			parametros.setCveIdEstatus(rellenaCeros(filtro.getEstatus(),2));
		}else{
			parametros.setCveIdEstatus("");
		}
		
		
		
		if(filtro.getCveIdGrupoAnalisisCe().equals("1")){
			parametros.setTipoMovimiento("I");
		}else if(filtro.getCveIdGrupoAnalisisCe().equals("2")){
			parametros.setTipoMovimiento("M");
		}
		String tipoPersona = "";
		if(filtro.getTipoPersona()!=null && filtro.getTipoPersona().equals("1")){
			tipoPersona="1";
		}else if(filtro.getTipoPersona()!=null && filtro.getTipoPersona().equals("2")){
			tipoPersona="2";
		}
		parametros.setCveIdTipoPersona(tipoPersona);
		
		
		if(filtro.getTipoMovimiento()!=null && filtro.getTipoMovimiento().length()<=5 && !filtro.getTipoMovimiento().isEmpty()){
			parametros.setCveIdTipoTramite(rellenaCeros(filtro.getTipoMovimiento(),5));	
		}else{
			parametros.setCveIdTipoTramite("");
		}
		System.out.println("Tipo tramite  "+parametros.getCveIdTipoTramite());
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat second = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Date fecInicio=formatter.parse(filtro.getStrPeriodoInicio());
			Date fecFinal=formatter.parse(filtro.getStrPeriodoFin());
			parametros.setFecIniRegistro(second.format(fecInicio));
			parametros.setFecFinRegistro(second.format(fecFinal));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		parametros.setRegistroPatronal(filtro.getRegistroPatronal());
		
		
		if(filtro.getTipoRegistro()!=null && !filtro.getTipoRegistro().equals("") && !filtro.getTipoRegistro().equals("-1")){
			switch(Integer.parseInt(filtro.getTipoRegistro())){
			case 0:
				parametros.setTipoRegistro("ARP");
				break;
			case 1:
				parametros.setTipoRegistro("RPC");
				break;
			case 2:
				parametros.setTipoRegistro("PSP");
				break;
			}
		} else {
			parametros.setTipoRegistro("");
		}
		
		try {
			System.out.println("parametros.getClaseRectificada() "+parametros.getClaseRectificada());
			System.out.println("parametros.getCveIdDelegacion() "+parametros.getCveIdDelegacion());
			System.out.println("parametros.getCveIdEstatus() "+parametros.getCveIdEstatus());
			System.out.println("parametros.getCveIdSubdelegacion() "+parametros.getCveIdSubdelegacion());
			System.out.println("parametros.getCveIdTipoPersona() "+parametros.getCveIdTipoPersona());
			System.out.println("parametros.getCveIdTipoTramite() "+parametros.getCveIdTipoTramite());
			System.out.println("parametros.getFecFinRegistro() "+parametros.getFecFinRegistro());
			System.out.println("parametros.getFecIniRegistro() "+parametros.getFecIniRegistro());
			System.out.println("parametros.getRegistroPatronal() "+parametros.getRegistroPatronal());
			System.out.println("parametros.getTipoMovimiento() "+parametros.getTipoMovimiento());
			System.out.println("parametros.getTipoRegistro() "+parametros.getTipoRegistro());
			
//			ResponseMacII respuesta=invocarServicioClasificacionMACIITest();
			ResponseMacII respuesta=servicio.invocarServicioClasificacionMACII(parametros);
			System.out.println("Respuesta recibia a "+respuesta);
			
			if(respuesta==null){
				return lista;
			}
			FiltroSolicitudesReporte filtroReporte = new FiltroSolicitudesReporte(respuesta.getInfoConsultaMacII());
			
//			List<InfoConsultaMacII> registros=respuesta.getInfoConsultaMacII();
			List<InfoConsultaMacII> registros=filtroReporte.filtrarSolcitudes();
			for(InfoConsultaMacII reg:registros){
				reporte=new ReporteAnalisis();				
				reporte.setRegPatron(reg.getRegistroPatronal());		
				reporte.setRazonSocial(reg.getNombreRazonSocial());
				reporte.setDelegDesc(String.valueOf(reg.getCveIdDelegacion()));
				reporte.setSdelegDesc(String.valueOf(reg.getCveIdSubdelegacion()));
				reporte.setCveMunicio(reg.getCveIdMunicipio());
				reporte.setFecRegistro(reg.getFecRegistro());
				reporte.setFecRevision(reg.getFecRevision());				
				reporte.setFecMovimiento(reg.getFecMovimiento());			
				reporte.setTipoPersona(reg.getDesTipoPersona());				
				reporte.setCveIdTipoRegistro(String.valueOf(reg.getDesTipoRegistro()));
				reporte.setEstatus(reg.getDesEstatus());
				reporte.setClaseD(reg.getClaseDeclarada());
				reporte.setFraccionD(String.valueOf(reg.getFraccionDeclarada()));
				reporte.setPrimaD(reg.getPrimaDeclarada());
				reporte.setClaseR(reg.getClaseRectificada());
				reporte.setFraccionR(reg.getFraccionRectificada());
				reporte.setPrimaR(reg.getPrimaRectificada());
				reporte.setFolioResolucion(reg.getFolioResolucion());
				reporte.setCveCiz(String.valueOf(reg.getCveIdCiz()));
				
				if(reg.getTipoMovimiento().equals("M")){
					reporte.setTipoMovimiento("MODIFICADA");	
				}else if(reg.getTipoMovimiento().equals("I")){
					reporte.setTipoMovimiento("INSCRIPCION");	
				}
				
				reporte.setDesTipoTramite(reg.getDesTipoTramite());				
				lista.add(reporte);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return lista;
	}

	
	


	@Override
	public byte[] crearReporteAnalisis(
			List<ReporteAnalisis> listaRetVal, int grupoAnalisis) throws Exception {

		ByteArrayOutputStream a = null;
		
		HSSFWorkbook bookE = crearReporte(listaRetVal, grupoAnalisis);
		
		if(bookE !=null){
			a = new ByteArrayOutputStream(bookE.getBytes().length);
			this.log.debug("ByteArrayOutputStream [ " +a+"]");
			bookE.write(a);
		}
		
		return a.toByteArray();		
	}
	
	
	@Override
	public byte[] crearReporteAnalisisAlmacenes(
			List<ReporteAnalisis> listaRetVal, int grupoAnalisis) throws Exception {
		System.out.println("Generando reporte datamart");
		ByteArrayOutputStream a = null;
		
		HSSFWorkbook bookE = crearReporteAlmacenes(listaRetVal, grupoAnalisis);

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
	private HSSFWorkbook crearReporte(List<ReporteAnalisis> listaRetVal, int grupoAnalisis) throws Exception {
		int incremento = 0;
		final int COLUMNA_NRP = 0;
		final int COLUMNA_NRP_PADRE = 1;
		final int COLUMNA_RAZON_SOCIAL = 1 + incremento;
		final int COLUMNA_DELEGACION = 2 + incremento;
		final int COLUMNA_SUBDELEGACION = 3 + incremento;
		final int COLUMNA_TIPO_MODIFICACION = 4 + incremento;
		final int COLUMNA_FECHA_REGISTRO = 5 + incremento;
		final int COLUMNA_FECHA_REVISION = 6 + incremento;
		final int COLUMNA_FECHA_AUTORIZACION = 7 + incremento;
		final int COLUMNA_TIPO_PERSONA = 8 + incremento;
		final int COLUMNA_TIPO_REGISTRO = 9 + incremento;
		final int COLUMNA_ESTATUS = 10 + incremento;
		final int COLUMNA_CLASE_DECLARADA = 11 + incremento;
		final int COLUMNA_FRACCION_DECLARADA = 12 + incremento;
		final int COLUMNA_PRIMA_DECLARADA = 13 + incremento;
		final int COLUMNA_CLASE_RECTIFICADA = 14 + incremento;
		final int COLUMNA_FRACCION_RECTIFICADA = 15 + incremento;
		final int COLUMNA_PRIMA_RECTIFICADA = 16 + incremento;
		final int COLUMNA_FOLIO_RESOLUCION = 17 + incremento; 
		final int COLUMNA_DES_COMENTARIO = 17 + incremento;   					
		final int COLUMNA_AUTORIZACION = 18 + incremento;
		final int COLUMNA_MOD_CLEM = 19 + incremento;
		
		DateFormat df = Constantes.FORMATO_FECHA_DD_MM_YYYY;

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

        cell = row.createCell(COLUMNA_NRP);
    	cell.setCellValue(Constantes.TEXTO_ENCABEZADO_NRP);
    	cell.setCellStyle(cellStyle);
    	
    	if(incremento == 1){
    		cell = row.createCell(COLUMNA_NRP_PADRE);
        	cell.setCellValue(Constantes.TEXTO_ENCABEZADO_NRP_PADRE);
        	cell.setCellStyle(cellStyle);
    	}

    	cell = row.createCell(COLUMNA_RAZON_SOCIAL);
    	cell.setCellValue(Constantes.TEXTO_ENCABEZADO_RAZON_SOCIAL);
    	cell.setCellStyle(cellStyle);

    	cell = row.createCell(COLUMNA_DELEGACION);
    	cell.setCellValue(Constantes.TEXTO_ENCABEZADO_DELEGACION);
    	cell.setCellStyle(cellStyle);

    	cell = row.createCell(COLUMNA_SUBDELEGACION);
    	cell.setCellValue(Constantes.TEXTO_ENCABEZADO_SUBDELEGACION);
    	cell.setCellStyle(cellStyle);
    	
    	cell = row.createCell(COLUMNA_TIPO_MODIFICACION);
    	cell.setCellValue(Constantes.TEXTO_ENCABEZADO_TIPO_MODIFICACION);
    	cell.setCellStyle(cellStyle);

    	cell = row.createCell(COLUMNA_FECHA_REGISTRO);
    	cell.setCellValue(Constantes.TEXTO_ENCABEZADO_FECHA_REGISTRO);
    	cell.setCellStyle(cellStyle);

    	cell = row.createCell(COLUMNA_TIPO_PERSONA);
    	cell.setCellValue(Constantes.TEXTO_ENCABEZADO_TIPO_PERSONA);
    	cell.setCellStyle(cellStyle);

    	cell = row.createCell(COLUMNA_TIPO_REGISTRO);
    	cell.setCellValue(Constantes.TEXTO_ENCABEZADO_TIPO_REGISTRO);
    	cell.setCellStyle(cellStyle);

    	cell = row.createCell(COLUMNA_ESTATUS);
    	cell.setCellValue(Constantes.TEXTO_ENCABEZADO_ESTATUS);
    	cell.setCellStyle(cellStyle);

    	cell = row.createCell(COLUMNA_FECHA_REVISION);
    	cell.setCellValue(Constantes.TEXTO_FECHA_REVISION);
    	cell.setCellStyle(cellStyle);

    	cell = row.createCell(COLUMNA_FECHA_AUTORIZACION);
    	cell.setCellValue(Constantes.TEXTO_FECHA_AUTORIZACION);
    	cell.setCellStyle(cellStyle);

    	cell = row.createCell(COLUMNA_CLASE_DECLARADA);
    	cell.setCellValue(Constantes.TEXTO_CLASE_DECLARADA);
    	cell.setCellStyle(cellStyle);

    	cell = row.createCell(COLUMNA_FRACCION_DECLARADA);
    	cell.setCellValue(Constantes.TEXTO_FRACCION_DECLARADA);
    	cell.setCellStyle(cellStyle);

    	cell = row.createCell(COLUMNA_PRIMA_DECLARADA);
    	cell.setCellValue(Constantes.TEXTO_PRIMA_DECLARADA);
    	cell.setCellStyle(cellStyle);

    	cell = row.createCell(COLUMNA_CLASE_RECTIFICADA);
    	cell.setCellValue(Constantes.TEXTO_CLASE_RECTIFICADA);
    	cell.setCellStyle(cellStyle);

    	cell = row.createCell(COLUMNA_FRACCION_RECTIFICADA);
    	cell.setCellValue(Constantes.TEXTO_FRACCION_RECTIFICADA);
    	cell.setCellStyle(cellStyle);

    	cell = row.createCell(COLUMNA_PRIMA_RECTIFICADA);
    	cell.setCellValue(Constantes.TEXTO_PRIMA_RECTIFICADA);
    	cell.setCellStyle(cellStyle);

    	if(incremento == 1){
        	cell = row.createCell(COLUMNA_DES_COMENTARIO);
        	cell.setCellValue(Constantes.TEXTO_DES_COMENTARIO);
        	cell.setCellStyle(cellStyle);    		
    	}else{
        	cell = row.createCell(COLUMNA_FOLIO_RESOLUCION);
        	cell.setCellValue(Constantes.TEXTO_FOLIO_RESOLUCION);
        	cell.setCellStyle(cellStyle);
    	}
    	    	
    	cell = row.createCell(COLUMNA_AUTORIZACION);
    	cell.setCellValue(Constantes.TEXTO_ENCABEZADO_MODIFICADA);
    	cell.setCellStyle(cellStyle);

    	cell = row.createCell(COLUMNA_MOD_CLEM);
    	cell.setCellValue(Constantes.TEXTO_ENCABEZADO_MOD_CLEM);
    	cell.setCellStyle(cellStyle);

    	ReporteAnalisis vo = null;
    	for (Iterator<ReporteAnalisis> iterator = listaRetVal.iterator(); iterator.hasNext();) {
			vo = iterator.next();		    	
			
            row = sheet.createRow(filaCont++);//Se crea una fila dentro de la hoja
            
            row.createCell(COLUMNA_NRP).setCellValue(vo.getRegPatron());
            if(new Integer(vo.getCveIdTipoPersona()).longValue() == TipoPersonaEnum.FISICA.getId()){
            	row.createCell(COLUMNA_RAZON_SOCIAL).setCellValue(vo.getNombre());
            }else{
            	row.createCell(COLUMNA_RAZON_SOCIAL).setCellValue(vo.getRazonSocial());
            }
        	row.createCell(COLUMNA_DELEGACION).setCellValue(vo.getDelegDesc());
        	row.createCell(COLUMNA_SUBDELEGACION).setCellValue(vo.getSdelegDesc());
        	row.createCell(COLUMNA_TIPO_MODIFICACION).setCellValue(vo.getDesTipoTramite());
            row.createCell(COLUMNA_FECHA_REGISTRO).setCellValue(vo.getFecPresentacion() == null ? null : df.format(vo.getFecPresentacion()));
        	row.createCell(COLUMNA_TIPO_PERSONA).setCellValue(vo.getTipoPersona());
        	row.createCell(COLUMNA_TIPO_REGISTRO).setCellValue(vo.getDesIndRpc());
            row.createCell(COLUMNA_ESTATUS).setCellValue(vo.getDesCausasAnalisis());
            if(vo.getFecAnalisis() != null){
            	row.createCell(COLUMNA_FECHA_REVISION).setCellValue(df.format(vo.getFecAnalisis()));
            }else
            	row.createCell(COLUMNA_FECHA_REVISION).setCellValue("");
            if(vo.getFecAutorizacion() != null){
            	row.createCell(COLUMNA_FECHA_AUTORIZACION).setCellValue(df.format(vo.getFecAutorizacion()));
            }else
            	row.createCell(COLUMNA_FECHA_AUTORIZACION).setCellValue("");
            row.createCell(COLUMNA_CLASE_DECLARADA).setCellValue(vo.getClaseD());
        	row.createCell(COLUMNA_FRACCION_DECLARADA).setCellValue(vo.getFraccionD());
            row.createCell(COLUMNA_PRIMA_DECLARADA).setCellValue(vo.getPrimaD());
        	row.createCell(COLUMNA_CLASE_RECTIFICADA).setCellValue(vo.getClaseR());
        	row.createCell(COLUMNA_FRACCION_RECTIFICADA).setCellValue(vo.getFraccionR());
        	row.createCell(COLUMNA_PRIMA_RECTIFICADA).setCellValue(vo.getPrimaR());
        	if(incremento == 1){
            	row.createCell(COLUMNA_DES_COMENTARIO).setCellValue(vo.getDesComentario());	
        	}else{
            	row.createCell(COLUMNA_FOLIO_RESOLUCION).setCellValue(vo.getFolioResolucion());
        	}
        	if(vo.getIndModAut()!=null && vo.getIndModAut().trim().length()>0 && Integer.parseInt(vo.getIndModAut())>0)
       			row.createCell(COLUMNA_AUTORIZACION).setCellValue("SI");
       		else 
       			row.createCell(COLUMNA_AUTORIZACION).setCellValue("NO");
        	
        	row.createCell(COLUMNA_MOD_CLEM).setCellValue(vo.getContModClem());
        	
		} //for
			 
		String imageFile = "";
		if(grupoAnalisis == GrupoAnalisisCeEnum.INSCRIPCION_INICIAL.getClave()){
			imageFile = Constantes.PATH_IMAGENES + Constantes.REPORTE_IMAGEN_HEADER_INSCRIPCION;
		}else if(grupoAnalisis == GrupoAnalisisCeEnum.MODIFICACION_PATRONAL.getClave()){
			imageFile = Constantes.PATH_IMAGENES + Constantes.REPORTE_IMAGEN_HEADER_MODIFICACION;
		}
		
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		HSSFClientAnchor anchor = new HSSFClientAnchor(1, 0, 761, 99, (short)0, 0, (short)4, 3);
		patriarch.createPicture(anchor, loadPicture( imageFile, wb ));
		
		//Ajusta ancho de columnas
		sheet.autoSizeColumn(COLUMNA_NRP);
		if(incremento == 1){
			sheet.autoSizeColumn(COLUMNA_NRP_PADRE);
		}
		sheet.autoSizeColumn(COLUMNA_RAZON_SOCIAL);
		sheet.autoSizeColumn(COLUMNA_DELEGACION);
		sheet.autoSizeColumn(COLUMNA_SUBDELEGACION);
		sheet.autoSizeColumn(COLUMNA_TIPO_MODIFICACION);
		sheet.autoSizeColumn(COLUMNA_FECHA_REGISTRO);
		sheet.autoSizeColumn(COLUMNA_TIPO_PERSONA);
		sheet.autoSizeColumn(COLUMNA_TIPO_REGISTRO);
		sheet.autoSizeColumn(COLUMNA_ESTATUS);
		sheet.autoSizeColumn(COLUMNA_FECHA_REVISION);
		sheet.autoSizeColumn(COLUMNA_FECHA_AUTORIZACION);
		sheet.autoSizeColumn(COLUMNA_CLASE_DECLARADA);
		sheet.autoSizeColumn(COLUMNA_FRACCION_DECLARADA);
		sheet.autoSizeColumn(COLUMNA_PRIMA_DECLARADA);
		sheet.autoSizeColumn(COLUMNA_CLASE_RECTIFICADA);
		sheet.autoSizeColumn(COLUMNA_FRACCION_RECTIFICADA);
		sheet.autoSizeColumn(COLUMNA_PRIMA_RECTIFICADA);
		if(incremento == 1){
			sheet.autoSizeColumn(COLUMNA_DES_COMENTARIO);
		}else{
			sheet.autoSizeColumn(COLUMNA_FOLIO_RESOLUCION);
		}
		sheet.autoSizeColumn(COLUMNA_AUTORIZACION);
		sheet.autoSizeColumn(COLUMNA_MOD_CLEM);
		
        return wb;
	}

	
	
	private HSSFWorkbook crearReporteAlmacenes(List<ReporteAnalisis> listaRetVal, int grupoAnalisis) throws Exception {
		System.out.println("almacenes 1.0 ");
		final int COLUMNA_NRP = 0;
		final int COLUMNA_RAZON_SOCIAL = 1;
		final int COLUMNA_NOMBRE_DELEGACION = 2;
		final int COLUMNA_NOMBRE_SUBDELEGACION=3;		
		final int COLUMNA_NOMBRE_MUNICIPIO=4;
		final int COLUMNA_FECHA_REGISTRO=5;
		final int COLUMNA_FECHA_REVISION=6;
		final int COLUMNA_FECHA_AUTORIZACION=7;
		final int COLUMNA_TIPO_PERSONA=8;		
		final int COLUMNA_TIPO_REGISTRO=9;
		final int COLUMNA_ESTATUS=10;
		final int COLUMNA_CLASE_DECLARADA=11;
		final int COLUMNA_FRACCION_DECLARADA=12;
		final int COLUMNA_PRIMA_DECLARADA=13;
		final int COLUMNA_CLASE_RECTIFICADA=14;
		final int COLUMNA_FRACCION_RECTIFICADA=15;
		final int COLUMNA_PRIMA_RECTIFICADA=16;
		final int COLUMNA_FOLIO_RESOLUCION=17;
		final int COLUMNA_CIZ=18;
		final int COLUMNA_TIPO_MOVIMIENTO=19;
		final int COLUMNA_DESC_TIPO_TRAMITE=20;
		
		int filaCont = 4;//contador de fila
		HSSFRow row;//variable de filas excel
        HSSFWorkbook wb = new HSSFWorkbook();//Se crea el libro Excel
        HSSFSheet sheet = wb.createSheet("Reporte MAC");//Se crea una nueva hoja dentro del libro
        HSSFCell cell;//celda simple
           
        Font font = wb.createFont();//crea la fuente de texto
        font.setFontName("Courier New");//modificacion de la fuente de texto
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);//modificacion de la fuente de texto
        font.setColor(HSSFColor.WHITE.index);
        
        
                
        HSSFCellStyle cellStyle;//Celda de estilo        
        cellStyle = wb.createCellStyle();

        
        
        HSSFPalette palette = wb.getCustomPalette();
     // get the color which most closely matches the color you want to use
        System.out.println("Palette "+palette);
//        HSSFColor myColor = palette.findColor((byte)141, (byte)180,(byte) 226);
        
        HSSFColor myColor=palette.findSimilarColor((byte)141, (byte)180,(byte) 226);
        System.out.println("Mycolor "+myColor);
        short palIndex = myColor.getIndex();
        
		cellStyle.setFillForegroundColor(palIndex);		
		
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellStyle.setFont(font);//modificacion  de la fuente en celda
        
        //Encabezados
        row = sheet.createRow(filaCont++);//Se crea una fila dentro de la hoja

        cell = row.createCell(COLUMNA_NRP);
    	cell.setCellValue(Constantes.TEXTO_ENCABEZADO_NRP);
    	cell.setCellStyle(cellStyle);
    	
    	cell = row.createCell(COLUMNA_RAZON_SOCIAL);
    	cell.setCellValue(Constantes.TEXTO_ENCABEZADO_RAZON_SOCIAL);
    	cell.setCellStyle(cellStyle);
    	
    	
    	cell = row.createCell(COLUMNA_NOMBRE_DELEGACION);
    	cell.setCellValue(Constantes.TEXTO_ENCABEZADO_DELEGACION);
    	cell.setCellStyle(cellStyle);
    	
    	cell = row.createCell(COLUMNA_NOMBRE_SUBDELEGACION);
    	cell.setCellValue(Constantes.TEXTO_ENCABEZADO_SUBDELEGACION);
    	cell.setCellStyle(cellStyle);
    	
    	cell = row.createCell(COLUMNA_NOMBRE_MUNICIPIO);
    	cell.setCellValue(Constantes.TEXTO_ENCABEZADO_MUNICIPIO);
    	cell.setCellStyle(cellStyle);
    	
       	cell = row.createCell(COLUMNA_FECHA_REGISTRO);
    	cell.setCellValue(Constantes.TEXTO_ENCABEZADO_FECHA_REGISTRO);
    	cell.setCellStyle(cellStyle);
    	
    	
    	cell = row.createCell(COLUMNA_FECHA_REVISION);
    	cell.setCellValue(Constantes.TEXTO_FECHA_REVISION);
    	cell.setCellStyle(cellStyle);
    	
       	cell = row.createCell(COLUMNA_FECHA_AUTORIZACION);
    	cell.setCellValue(Constantes.TEXTO_FECHA_AUTORIZACION);
    	cell.setCellStyle(cellStyle);
    	
      	cell = row.createCell(COLUMNA_TIPO_PERSONA);
    	cell.setCellValue(Constantes.TEXTO_ENCABEZADO_TIPO_PERSONA);
    	cell.setCellStyle(cellStyle);
    	
    	cell = row.createCell(COLUMNA_TIPO_PERSONA);
    	cell.setCellValue(Constantes.TEXTO_ENCABEZADO_TIPO_PERSONA);
    	cell.setCellStyle(cellStyle);

    	cell = row.createCell(COLUMNA_TIPO_REGISTRO);
    	cell.setCellValue(Constantes.TEXTO_ENCABEZADO_TIPO_REGISTRO);
    	cell.setCellStyle(cellStyle);
    	
    	cell = row.createCell(COLUMNA_ESTATUS);
    	cell.setCellValue(Constantes.TEXTO_ENCABEZADO_ESTATUS);
    	cell.setCellStyle(cellStyle);
    	
    	cell = row.createCell(COLUMNA_CLASE_DECLARADA);
    	cell.setCellValue(Constantes.TEXTO_CLASE_DECLARADA);
    	cell.setCellStyle(cellStyle);
    	
     	cell = row.createCell(COLUMNA_FRACCION_DECLARADA);
    	cell.setCellValue(Constantes.TEXTO_FRACCION_DECLARADA);
    	cell.setCellStyle(cellStyle);
    	
    	cell = row.createCell(COLUMNA_PRIMA_DECLARADA);
    	cell.setCellValue(Constantes.TEXTO_PRIMA_DECLARADA);
    	cell.setCellStyle(cellStyle);
    	
     	cell = row.createCell(COLUMNA_CLASE_RECTIFICADA);
    	cell.setCellValue(Constantes.TEXTO_CLASE_RECTIFICADA);
    	cell.setCellStyle(cellStyle);
    	
     	cell = row.createCell(COLUMNA_FRACCION_RECTIFICADA);
    	cell.setCellValue(Constantes.TEXTO_FRACCION_RECTIFICADA);
    	cell.setCellStyle(cellStyle);
    	
    	
    	cell = row.createCell(COLUMNA_PRIMA_RECTIFICADA);
    	cell.setCellValue(Constantes.TEXTO_PRIMA_RECTIFICADA);
    	cell.setCellStyle(cellStyle);
    	
    	cell = row.createCell(COLUMNA_FOLIO_RESOLUCION);
    	cell.setCellValue(Constantes.TEXTO_FOLIO_RESOLUCION);
    	cell.setCellStyle(cellStyle);
    	
    	cell = row.createCell(COLUMNA_CIZ);
    	cell.setCellValue(Constantes.TEXTO_ENCABEZADO_CIZ);
    	cell.setCellStyle(cellStyle);
    	
    	cell = row.createCell(COLUMNA_TIPO_MOVIMIENTO);
    	cell.setCellValue(Constantes.TEXTO_ENCABEZADO_TIPO_MOVIMIENTO);
    	cell.setCellStyle(cellStyle);
    	
    	
    	cell = row.createCell(COLUMNA_DESC_TIPO_TRAMITE);
    	cell.setCellValue(Constantes.TEXTO_ENCABEZADO_TIPO_TRAMITE);
    	cell.setCellStyle(cellStyle);
    	
    	ReporteAnalisis vo = null;
    	for (Iterator<ReporteAnalisis> iterator = listaRetVal.iterator(); iterator.hasNext();) {
			vo = iterator.next();				
            row = sheet.createRow(filaCont++);           
			row.createCell(COLUMNA_NRP).setCellValue(validaString(vo.getRegPatron()));     
            row.createCell(COLUMNA_RAZON_SOCIAL).setCellValue(validaString(vo.getRazonSocial()));          
            row.createCell(COLUMNA_NOMBRE_DELEGACION).setCellValue(validaString(vo.getDelegDesc()));          
            row.createCell(COLUMNA_NOMBRE_SUBDELEGACION).setCellValue(validaString(vo.getSdelegDesc()));          
            row.createCell(COLUMNA_NOMBRE_MUNICIPIO).setCellValue(validaString(vo.getCveMunicio()));            
            row.createCell(COLUMNA_FECHA_REGISTRO).setCellValue(validaString(vo.getFecRegistro()));          
            row.createCell(COLUMNA_FECHA_REVISION).setCellValue(validaString(vo.getFecRevision()));             
            row.createCell(COLUMNA_FECHA_AUTORIZACION).setCellValue(vo.getFecMovimiento());     
            row.createCell(COLUMNA_TIPO_PERSONA).setCellValue(validaString(vo.getTipoPersona()));     
            row.createCell(COLUMNA_TIPO_REGISTRO).setCellValue(validaString(vo.getCveIdTipoRegistro()));     
            row.createCell(COLUMNA_ESTATUS).setCellValue(validaString(vo.getEstatus()));     
            row.createCell(COLUMNA_CLASE_DECLARADA).setCellValue(validaString(vo.getClaseD()));     
            row.createCell(COLUMNA_FRACCION_DECLARADA).setCellValue(validaString(vo.getFraccionD()));     
            row.createCell(COLUMNA_PRIMA_DECLARADA).setCellValue(validaString(vo.getPrimaD()));     
            row.createCell(COLUMNA_CLASE_RECTIFICADA).setCellValue(validaString(vo.getClaseR()));     
            row.createCell(COLUMNA_FRACCION_RECTIFICADA).setCellValue(validaString(vo.getFraccionR()));  
            row.createCell(COLUMNA_PRIMA_RECTIFICADA).setCellValue(validaString(vo.getPrimaR()));  
            row.createCell(COLUMNA_FOLIO_RESOLUCION).setCellValue(validaString(vo.getFolioResolucion()));  
            row.createCell(COLUMNA_CIZ).setCellValue(validaString(vo.getCveCiz()));  
            row.createCell(COLUMNA_TIPO_MOVIMIENTO).setCellValue(validaString(vo.getTipoMovimiento()));  
            row.createCell(COLUMNA_DESC_TIPO_TRAMITE).setCellValue(validaString(vo.getDesTipoTramite()));   	         	
		} 
			
    	
    	String imageFile = "";
		if(grupoAnalisis == GrupoAnalisisCeEnum.INSCRIPCION_INICIAL.getClave()){
			imageFile = Constantes.PATH_IMAGENES + Constantes.REPORTE_IMAGEN_HEADER_INSCRIPCION;
		}else if(grupoAnalisis == GrupoAnalisisCeEnum.MODIFICACION_PATRONAL.getClave()){
			imageFile = Constantes.PATH_IMAGENES + Constantes.REPORTE_IMAGEN_HEADER_MODIFICACION;
		}
		
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		HSSFClientAnchor anchor = new HSSFClientAnchor(1, 0, 761, 99, (short)0, 0, (short)4, 3);
		patriarch.createPicture(anchor, loadPicture( imageFile, wb ));
		
		
		sheet.autoSizeColumn(COLUMNA_NRP);
		sheet.autoSizeColumn(COLUMNA_RAZON_SOCIAL);
		sheet.autoSizeColumn(COLUMNA_NOMBRE_DELEGACION);
		sheet.autoSizeColumn(COLUMNA_NOMBRE_SUBDELEGACION);
		sheet.autoSizeColumn(COLUMNA_NOMBRE_MUNICIPIO);
		sheet.autoSizeColumn(COLUMNA_FECHA_REGISTRO);		
		sheet.autoSizeColumn(COLUMNA_FECHA_REVISION);
		sheet.autoSizeColumn(COLUMNA_FECHA_AUTORIZACION);
		sheet.autoSizeColumn(COLUMNA_TIPO_PERSONA);
		sheet.autoSizeColumn(COLUMNA_TIPO_REGISTRO);
		sheet.autoSizeColumn(COLUMNA_ESTATUS);
		sheet.autoSizeColumn(COLUMNA_CLASE_DECLARADA);
		sheet.autoSizeColumn(COLUMNA_FRACCION_DECLARADA);
		sheet.autoSizeColumn(COLUMNA_PRIMA_DECLARADA);
		sheet.autoSizeColumn(COLUMNA_CLASE_RECTIFICADA);
		sheet.autoSizeColumn(COLUMNA_FRACCION_RECTIFICADA);
		sheet.autoSizeColumn(COLUMNA_PRIMA_RECTIFICADA);
		sheet.autoSizeColumn(COLUMNA_FOLIO_RESOLUCION);
		sheet.autoSizeColumn(COLUMNA_CIZ);		
		sheet.autoSizeColumn(COLUMNA_TIPO_MOVIMIENTO);
		sheet.autoSizeColumn(COLUMNA_DESC_TIPO_TRAMITE);		
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
			file = Thread.currentThread().getContextClassLoader().getResourceAsStream( filePath);
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


	private String validaString(String param){
		if(param==null){
			return "";
		}else{
			return param;
		}
	}
	
	public String rellenaCeros(String s, int length) {
	     if (s.length() >= length) return s;
	     else return String.format("%0" + (length-s.length()) + "d%s", 0, s);
	}
	
}
