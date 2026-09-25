package mx.gob.imss.ctirss.correccion.web.controller.promocion;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import mx.gob.imss.ctirss.correccion.bean.SelectBean;
import mx.gob.imss.ctirss.correccion.framework.exception.promocion.NotFoundRegistroPatronalException;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.model.ErrorValidation;
import mx.gob.imss.ctirss.correccion.presentacion.service.interfaces.PresentacionCorreccionServiceController;
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
@RequestMapping(value="/promocion/carga")
public class PromocionCargaController {
	
	Logger log = Logger.getLogger(PromocionCargaController.class);
	
	@Autowired
	private PresentacionCorreccionServiceController businessController;

	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model,HttpServletRequest request) {
		PromocionCargaModel modelo = new PromocionCargaModel();
		Map<String, List<SelectBean>> mapa = businessController.obtenerTipoPromocionYOrigenSelector(ConstantesBusiness.FLUJO_PROMOCION, -1);
		modelo.setOrigenes(mapa.get(ConstantesBusiness.LISTA_ORIGENES));
		modelo.setTiposPromocion(mapa.get(ConstantesBusiness.LISTA_TIPOS_PROMOCION));
		model.addAttribute(modelo);
		request.setAttribute("detalles","");
		return "promocion/cargaMain";
	}
	
	@RequestMapping(value="/descargaXls")
	public String descargaXlsPromocionCarga(Model model,HttpServletRequest request, HttpServletResponse response){
		String archivo = request.getSession().getServletContext().getRealPath("/resources/archivos/promocion/CargaPromocion.xls");
		PromocionCargaModel form = new PromocionCargaModel();
		try {
			InputStream is = new FileInputStream(archivo);
			ByteArrayOutputStream bous = new ByteArrayOutputStream();
			int nRead = 0;
			
			byte[] data = new byte[1024 * 10];
			while((nRead = is.read(data, 0, data.length)) != -1){
				bous.write(data, 0, nRead);
			}
			response.setContentType(ConstantesBusiness.CONTENT_TYPE_XLS);
			response.setHeader("Content-disposition", "attachment; filename=CargaPromocion.xls");
			bous.flush();
			response.getOutputStream().write(bous.toByteArray());
		} catch (FileNotFoundException e) {
			form.setMsg(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			form.setMsg(e.getMessage());
			e.printStackTrace();
		}
		model.addAttribute("promocionCargaModel",form);
		request.setAttribute("msg", form.getMsg());
		
		return "promocion/descargaResponse";
	}
	
	@RequestMapping(value="/buscarCriteriosSeleccion")
	public @ResponseBody PromocionCargaModel buscarCriteriosSeleccion(@RequestBody PromocionCargaModel modelo, HttpServletRequest request){
		PromocionCargaModel retmodel = new PromocionCargaModel();
		List<SelectBean> criteriosSel = businessController.obtenerCriteriosSeleccion(modelo.getIdTipo().intValue(), modelo.getIdOrigen().intValue());
		retmodel.setCriterios(criteriosSel);
		return retmodel;	
	}
	
	
    @RequestMapping(value = "/cargarXls", method= RequestMethod.POST) 
    public String handleUpload(PromocionCargaModel modelo, HttpServletRequest request,HttpServletResponse response) {
    	
    	System.setProperty("file.encoding","UTF-8");
    	StringBuffer builder = new StringBuffer();
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html");
		Workbook book = null;
		List<ErrorValidation> registros = new ArrayList<ErrorValidation>();
		try {
			UserSession user = (UserSession) request.getSession().getAttribute(ConstantesSession.USR_SESSION);
			book = Workbook.getWorkbook(modelo.getArchivo().getInputStream());
			Sheet sheet = book.getSheet("Prom");
			for (int i = 0; i < sheet.getRows(); i++) {
				Cell[] celdas = sheet.getRow(i);
				if (celdas != null && celdas.length == 3 && !isHeader(celdas)) {
					try{
						Long delegacion = new Long(celdas[0].getContents());
						Long subDelegacion = new Long(celdas[1].getContents());
						registros.addAll(businessController.guardaCriterioSeleccion(modelo.getIdTipo(),	modelo.getIdCriterio(), delegacion,
									subDelegacion, celdas[2].getContents(),	String.valueOf(user.getCveIdUsuario())));
					}catch (NumberFormatException e) {
						registros.add(new ErrorValidation("No se inserta este registro por falta de Delegacion / SubDelegacion para la fila ".concat(String.valueOf(i - 1)), true));						
					}catch(NotFoundRegistroPatronalException nfRP){
						registros.add(new ErrorValidation("No se inserta este registro por falta de Registro patronal / Patron dado de baja ".concat(String.valueOf(i - 1)), true));
					}
				}
			}
		} catch (IllegalStateException e) {
			registros.add(new ErrorValidation(e.getLocalizedMessage(), true));
		} catch (BiffException e) {
			registros.add(new ErrorValidation(e.getLocalizedMessage(), true));
		} catch (IOException e) {
			registros.add(new ErrorValidation(e.getLocalizedMessage(), true));
		} finally {
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
		return "promocion/cargaMain";
    }
	
	private boolean isHeader(Cell[] celdas) {
		boolean isHeader = false;
		for (Cell celda : celdas) {
//			log.debug(celda.getContents() + " ");
			String valCell = celda.getContents();
			if(valCell.startsWith("ID") || valCell.startsWith("RP") ||
					valCell.startsWith("[-]") || ConstantesBusiness.isSpace(valCell)){
				isHeader = true;
			}
		}
		return isHeader;
	}
	
	
	/**
	 * 
	 * Metodo que presenta la pantalla(popup) de descarga de archivo con el mensaje de que se tardara un tiempo
	 * en generar la descarga de la plantilla de normativo-Selector
	 * @Author Oscar Beltran Ortega
	 * @version 1.0.0
	 */
	@RequestMapping(value="/descargaWindow" , method=RequestMethod.GET)
	public String showDescargaWindow(Model model,HttpServletRequest request) {
		
		PromocionCargaModel form = new PromocionCargaModel();

		
		model.addAttribute("promocionCargaModel",form);		
		 return "promocion/descargaWindow";
		 
	}
	
	
	/**
	 * 
	 * Metodo que redirecciona a la pantalla principal de la descarga de la plantilla de promocion
	 * @Author Oscar Beltran Ortega
	 * @version 1.0.0
	 * 
	 */
	@RequestMapping(value="/descargaReturn" , method=RequestMethod.POST)
	public String getCreateForm2(Model model) {
		
		PromocionCargaModel form = new PromocionCargaModel();
		model.addAttribute("promocionCargaModel",form);
		
		return "promocion/cargaMain";
	}
}
