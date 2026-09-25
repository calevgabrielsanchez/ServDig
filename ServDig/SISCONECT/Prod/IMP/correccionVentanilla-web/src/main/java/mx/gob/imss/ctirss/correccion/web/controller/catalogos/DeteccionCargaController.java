
package mx.gob.imss.ctirss.correccion.web.controller.catalogos;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.login.model.SegUsuarioFuncionario;
import mx.gob.imss.ctirss.correccion.model.ErrorValidation;
import mx.gob.imss.ctirss.correccion.presentacion.service.interfaces.PresentacionCorreccionServiceController;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.session.ConstantesSession;
import mx.gob.imss.ctirss.correccion.session.UserSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/deteccion/carga")
public class DeteccionCargaController  extends AbstractController{
	
	private final static Logger log = Logger.getLogger(DeteccionCargaController.class);
	
	private final static String NOMBRE_SO_SOLARIS="SunOS";
	
	@Autowired
	private PresentacionCorreccionServiceController businessController;
	
	@Autowired
	private ICatalogoService<SegUsuarioFuncionario> catalogoServiceBean;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model, HttpServletRequest request) {
		DeteccionCargaModel modelo = new DeteccionCargaModel();
		model.addAttribute(modelo);
		request.setAttribute("detalles","");
		return "deteccion/cargaMain";
	}
	
	/**
	 * Metodo que ejecuta la descarga de la plantilla de deteccion en formato excel
	 * @param model
	 * @param request
	 * @param response
	 * @return   urlPath
	 * @Author Oscar Beltran Ortega
	 * @version 1.0.0
	 */
	@RequestMapping(value="/descargaXls")
	public String descargaXlsDeteccionCarga(Model model,HttpServletRequest request, HttpServletResponse response){		
		System.setProperty("file.encoding","UTF-8");
		String archivo = request.getSession().getServletContext().getRealPath("/resources/archivos/deteccion/PlantillaCargaDeteccion.xls");
		//String msgError ="";
		Workbook book = null;
		WritableWorkbook wBook = null;
		DeteccionCargaModel form = new DeteccionCargaModel();
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			UserSession user = (UserSession) request.getSession().getAttribute(ConstantesSession.USR_SESSION);
			log.debug("usr.del.id :: "+user.getIdDelegacion()+", usr.subDel.id :: "+ user.getIdSubDelegacion());
			List<SegUsuarioFuncionario> censores = businessController.cargarCensores(user.getIdDelegacion(), user.getIdSubDelegacion());
			log.debug("censores.size() :: "+censores.size());
			WorkbookSettings ws = new WorkbookSettings();
			ws.setSuppressWarnings(true);
			ws.setEncoding("CP1250");
			log.debug("Leyendo archivo");
			book = Workbook.getWorkbook(new File(archivo), ws);
			log.debug("Archivo leido");
			wBook = Workbook.createWorkbook(baos, book);
			WritableSheet wSheet = wBook.getSheet(0);
			
			Label lDelegacion = new Label(21, 0, user.getNombreDelegacion());
			Label lSubDelegacion = new Label(21, 1, user.getNombreSubDelegacion());
			
			wSheet.addCell(lDelegacion);
			wSheet.addCell(lSubDelegacion);
			
			for(int i = 0 ; i < censores.size() ; i++){
				int j = i + 1;
				WritableCell cell = wSheet.getWritableCell("X" + j);
				WritableCell cell1 = wSheet.getWritableCell("Y" + j);
				if(cell.getType() == CellType.LABEL){
					Label l = (Label) cell;
					l.setString(censores.get(i).getCveIdUsuarioFuncionario().toString());
				}else if(cell.getType() == CellType.EMPTY){
					Label l = new Label(23, (j-1), censores.get(i).getCveIdUsuarioFuncionario().toString());
					wSheet.addCell(l);
				}
				if(cell1.getType() == CellType.LABEL){
					Label l = (Label) cell1;
					l.setString(censores.get(i).getSegUsuario().getNomNombre());
				}else if(cell1.getType() == CellType.EMPTY){
					Label l = new Label(24, (j-1), censores.get(i).getSegUsuario().getNomNombre());
					wSheet.addCell(l);
				}
			}
			wBook.write();
			wBook.close();
			wBook = null;
			
			ServletOutputStream out = response.getOutputStream();
			
			response.setContentType(ConstantesBusiness.CONTENT_TYPE_XLS);
			response.setContentLength(baos.size());
			response.setHeader("Content-disposition", "attachment; filename=CargaDeteccion.xls");
			baos.writeTo(out);
			baos.flush();
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
			form.setMsg(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			form.setMsg(e.getMessage());
		} catch (BiffException e) {
			log.error(e.getMessage(), e);
			form.setMsg(e.getMessage());
		} catch (RowsExceededException e) {
			log.error(e.getMessage(), e);
			form.setMsg(e.getMessage());
		} catch (WriteException e) {
			log.error(e.getMessage(), e);
			form.setMsg(e.getMessage());
		}finally{
			if(book != null){
				book.close();
			}
			if(wBook != null){
				try {
					wBook.close();
				} catch (WriteException e) {
					log.error(e.getMessage(), e);
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		
		
		model.addAttribute("deteccionCargaModel",form);
		request.setAttribute("msg", form.getMsg());
		
		return "deteccion/cargaDeteccion/descargaResponse";
	}
	
	
	
	/**
	 * Metodo recibe la plnatilla de deteccion en formato excel y hace la carga de la informacion contenida en el excel
	 * @param DeteccionCargaModel modelo con los datos 
	 * @param request
	 * @Author Oscar Beltran Ortega
	 * @version 1.0.0
	 */
	 
    @RequestMapping(value = "/cargarXls", method= RequestMethod.POST) 
    public  String handleUpload(DeteccionCargaModel modelo, HttpServletRequest request, HttpServletResponse response) {
		System.setProperty("file.encoding","UTF-8");
		StringBuffer builder = new StringBuffer();
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html");
		Workbook book = null;
		List<ErrorValidation> registros = new ArrayList<ErrorValidation>();
		try {
			log.debug("Cargando archivo :: " + modelo.getArchivo().getOriginalFilename());
			book = Workbook.getWorkbook(modelo.getArchivo().getInputStream());
			
			Sheet sheet = book.getSheet("Detec");
			UserSession user = (UserSession) super.getUsuarioFirmado(request);
			Cell[] encabezado = sheet.getRow(0);
			if(encabezado != null && 
					encabezado.length > 0){
				if(validaEncabezado(encabezado[0].getContents())){
					for (int i = 2; i < sheet.getRows(); i++) {
						Cell[] celdas = sheet.getRow(i);
						if(!isHeader(celdas)){
							CrtDeteccion det = new CrtDeteccion();
							List<ErrorValidation> errsVal = validaRegistroDeteccion(det, celdas, i - 1);
							det.setFecFechareg(new Date());
							det.setCveUsuario(user.getCurpUsuario().toString());
							if(errsVal.size() == 0){
								registros.addAll(businessController.guardarDeteccion(det, user.getCveCodigoDelegacion(), user.getCveCodigoSubDelegacion(),user.getIdSubDelegacion()));
							}else{
								registros.addAll(errsVal);
							}
						}
					}
				}else{
					registros.add(new ErrorValidation("Este no es un archivo correcto para la carga no contiene la palabra clave.", true));
				}
			}else{
				registros.add(new ErrorValidation("No contiene datos este libro.", true));
			}
			
		} catch (IllegalStateException e) {
			registros.add(new ErrorValidation(e.getLocalizedMessage(), true));
			log.error(e.getMessage(), e);
		} catch (BiffException e) {
			registros.add(new ErrorValidation(e.getLocalizedMessage(), true));
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			registros.add(new ErrorValidation(e.getLocalizedMessage(), true));
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			registros.add(new ErrorValidation("Ha ocurrido un error en la lectura del libro de excel", true));
			log.error(e.getMessage(), e);
		}finally {
			if (book != null) {
				book.close();
			}
		}
		
		int numInserted = 0;
		int numError = 0;
		for (ErrorValidation err : registros) {
			if (err.isError()) {
				numError += 1;
			} else {
				numInserted += 1;
			}
		}
		

		
		
		builder.append(numInserted);
		builder.append("--");
		builder.append(numError);
		for (ErrorValidation err : registros) {
			if (err.isError()) {
				builder.append("--");
				builder.append(err.getError());
			}
		}
		request.setAttribute("detalles",builder.toString());
		

		return "deteccion/cargaMain";
    }
	
	public static void main(String[] args){
		Workbook book = null;
		try {
//			book = Workbook.getWorkbook(new File("C:\\Users\\User\\Downloads\\CargaDeteccion.xls"));
			book = Workbook.getWorkbook(new File("C:\\Users\\User\\Downloads\\CargaPromocion.xls"));
			int hojaNum = 0;
			for(Sheet hoja : book.getSheets()){
				log.debug("Hoja nombre: " + hoja.getName() + " , num hoja: " + hojaNum++);
				for(int i = 0; i < hoja.getRows() ; i++){
					log.debug("Fila: " + i);
					for(Cell col :  hoja.getRow(i)){
						log.debug("---> " + col.getContents());
					}
				}
			}
		} catch (BiffException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}finally{
			if (book!=null) {
				book.close(); 
			}
		}
	}
	
	private boolean validaEncabezado(String contents) {
		char[] chars = contents.toUpperCase().toCharArray();
		char[] palClave = {'D','E','T','E','C','C','I','N'};
		
		if(chars[0] == palClave[0] && //D
				chars[1] == palClave[1] && //e 
				chars[2] == palClave[2] && //t
				chars[3] == palClave[3] && //e
				chars[4] == palClave[4] && //c
				chars[5] == palClave[5] ){ //c
			return true;
		}
		
		return false;
	}
	

	private boolean isHeader(Cell[] celdas) {
		boolean isHeader = false;
		for (Cell celda : celdas) {
			String valCell = celda.getContents();
			if(valCell.equalsIgnoreCase("[-]")){
				isHeader = true;
			}
		}
		return isHeader;
	}
	
	private List<ErrorValidation> validaRegistroDeteccion(CrtDeteccion deteccion, Cell[] fila, int numFila){
		List<ErrorValidation> errs = new ArrayList<ErrorValidation>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy",Locale.US);
		String so = System.getProperty("os.name");
		log.info("os.name : " + so);
		
		if(fila != null){
			
			if(fila[0].getContents() == null){
				errs.add(new ErrorValidation("La fecha es requerida para el registro " + numFila, true));
			}else if(fila[0].getContents() != null &&
					!ConstantesBusiness.isSpace(fila[0].getContents())){
				try {
					
					if(NOMBRE_SO_SOLARIS.equals(so)){
						deteccion.setFecFechadeteccionFc(formatter.parse(fomatDateSolaris(fila[0].getContents())));
					}else{
						deteccion.setFecFechadeteccionFc(formatter.parse(fila[0].getContents()));
					}
					
				} catch (ParseException e) {
					errs.add(new ErrorValidation("La fecha de Deteccion no tiene un formato correcto dd/MM/yy para la fila " + numFila, true));
				}
			}
			
			if(fila[1] != null){
				if(ConstantesBusiness.isNumber(fila[1].getContents()) &&
						!ConstantesBusiness.isZero(fila[1].getContents())){
					deteccion.setCveCensor(Integer.parseInt(fila[1].getContents()));
				}
			}
			
			if(fila[2] != null &&
					!ConstantesBusiness.isSpace(fila[2].getContents())){
				deteccion.setNuReportectrlobra(fila[2].getContents());

			}
			
			if(fila[3] != null &&
					!ConstantesBusiness.isSpace(fila[3].getContents())){
				deteccion.setRegPatron(fila[3].getContents());
			}
			
			if(fila[4] != null){
				if(ConstantesBusiness.isNumber(fila[4].getContents()) &&
						!ConstantesBusiness.isZero(fila[4].getContents())){
					deteccion.setCveFkZona(Integer.parseInt(fila[4].getContents()));
				}
			}
			
			if(fila[5] != null){
				if(ConstantesBusiness.isNumber(fila[5].getContents()) &&
						!ConstantesBusiness.isZero(fila[5].getContents())){
					deteccion.setCvePkTipObra(Integer.parseInt(fila[5].getContents()));
				}
			}
			
			if(fila[6] != null){
				if(ConstantesBusiness.isNumber(fila[6].getContents()) &&
						!ConstantesBusiness.isZero(fila[6].getContents())){
					deteccion.setCvePkFaseConst(Integer.parseInt(fila[6].getContents()));
				}
			}
			
			if(fila[7] != null &&
					!ConstantesBusiness.isSpace(fila[7].getContents())){
				deteccion.setDesDependenciapub(fila[7].getContents());
			}
			
			if(fila[8] != null){
				if(ConstantesBusiness.isNumber(fila[8].getContents())){
					deteccion.setCanSuperficie(new BigDecimal(fila[8].getContents()));
				}
				
			}
			
			if(fila[9] != null){
				if(ConstantesBusiness.isNumber(fila[9].getContents())){
					deteccion.setImpCostoobra(new BigDecimal(fila[9].getContents()));
				}
			}
			
			if(fila[10] != null &&
					!ConstantesBusiness.isSpace(fila[10].getContents())){
				try {
					if(NOMBRE_SO_SOLARIS.equals(so)){
						deteccion.setFecFechainicioEst(formatter.parse(fomatDateSolaris(fila[10].getContents())));
					}else{
						deteccion.setFecFechainicioEst(formatter.parse(fila[10].getContents()));
					}
					
				} catch (ParseException e) {
					errs.add(new ErrorValidation("La fecha inicio de la obra no tiene un formato correcto dd/MM/yy para la fila " + numFila, true));
				}
			}
			
			if(fila[11] != null &&
					!ConstantesBusiness.isSpace(fila[11].getContents())){
				try {
					if(NOMBRE_SO_SOLARIS.equals(so)){
						deteccion.setFecFechaterminoEst(formatter.parse(fomatDateSolaris(fila[11].getContents())));
					}else{
						deteccion.setFecFechaterminoEst(formatter.parse(fila[11].getContents()));
					}
					
				} catch (ParseException e) {
					errs.add(new ErrorValidation("La fecha termino de la obra no tiene un formato correcto dd/MM/yy para la fila " + numFila, true));
				}
			}
			
			if(fila[12] != null){
				if(ConstantesBusiness.isNumber(fila[12].getContents())){
					deteccion.setPorAvanceobraEst(new BigDecimal(fila[12].getContents()));
				}
			}
			
			if(fila[13] != null){
				if(ConstantesBusiness.isNumber(fila[13].getContents())){
					deteccion.setCveTipocorr(Integer.parseInt(fila[13].getContents()));
				}
			}else{
				errs.add(new ErrorValidation("El tipo de la correccion es requerido para la fila " + numFila, true));
			}
			
			if(fila[14] != null &&
					!ConstantesBusiness.isSpace(fila[14].getContents())){
				deteccion.setTipClaseobra(fila[14].getContents());
			}else{
				errs.add(new ErrorValidation("La clase de la obra es requerida para la fila " + numFila, true));
			}
		}
		
		return errs;
	}
	
	/**
	 * 
	 * Metodo que le da formato a la fecha (String) que recibe(dd/MM/yyyy) en el formato que el sistema operativo
	 * Solaris la genera MM/dd/yyyy 
	 * @Author Oscar Beltran Ortega
	 * @version 1.0.0
	 */
	private String fomatDateSolaris(String fechaSolaris){
		
		String[] datosFechas = fechaSolaris.split("/");
		String fechaFormateada = datosFechas[1]+"/" + datosFechas[0] + "/" + datosFechas[2];
		log.debug("fechaFormateada SunOS::  " + fechaFormateada);
		
		return fechaFormateada;
	}
	
	
	/**
	 * 
	 * Metodo que presenta la pantalla(popup) de descarga de archivo con el mensaje de que se tardara un tiempo
	 * en generar la descarga de la plantilla de deteccion
	 * @Author Oscar Beltran Ortega
	 * @version 1.0.0
	 */
	@RequestMapping(value="/descargaWindow" , method=RequestMethod.GET)
	public String showDescargaWindow(Model model,HttpServletRequest request) {
		
		DeteccionCargaModel form = new DeteccionCargaModel();
		//form.set
		//form.setIdArchivoDescarga(new Integer(request.getParameter("idArchivoDescarga")).intValue());
		//form.setFolioCorreccion(String.valueOf(request.getParameter("folioCorreccion")));
		//form.setPeriodo(String.valueOf(request.getParameter("periodo")));
		
		model.addAttribute("deteccionCargaModel",form);		
		 return "catalogos/deteccion/cargaDeteccion/descargaWindow";
		 
	}
	
	
	/**
	 * 
	 * Metodo que redirecciona a la pantalla principal de la descarga de la plantilla de deteccion
	 * @Author Oscar Beltran Ortega
	 * @version 1.0.0
	 * 
	 */
	@RequestMapping(value="/descargaReturn" , method=RequestMethod.POST)
	public String getCreateForm2(Model model) {
		
		DeteccionCargaModel form = new DeteccionCargaModel();
		model.addAttribute("deteccionCargaModel",form);
		
		return "deteccion/cargaMain";
	}
}
