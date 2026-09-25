/**
 * delta-gestionPatronal-web22/05/2012
 * mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.actividad.economica22/05/2012
 * ProductoServicioController.java
 * 22/05/2012
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.actividad.economica;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.actividad.economica.ProductoServicioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.ProductoServicioDataTable;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Producto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.web.validator.ProductoServicioValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Luci Duran Silva
 * Instituto Mexicano del Seguro Social
 */
@Controller
@RequestMapping(value="/productos")
public class ProductoServicioController extends AbstractController {
	
	@Autowired
	private ProductoServicioServiceBusinessRemote productoServicioServiceBusiness;
	
	@RequestMapping(method=RequestMethod.GET)
	public String getProductos (@ModelAttribute SujetoObligado sujetoObligado, Model model , HttpSession session) {

    	Producto producto = new Producto();
    	
    	producto.setSujetoObligado(sujetoObligado);
    	
    	model.addAttribute("producto", producto);
		return "productos";
	}
	
	@RequestMapping(value="/paginar", method=RequestMethod.POST)
    public @ResponseBody DatosSalidaPaginador<Producto> pagina(@RequestBody ProductoServicioDataTable aoData ) {
    	
    	DatosEntradaPaginador<Producto> send = new DatosEntradaPaginador<Producto>();
        send.parserArray(aoData.getAoData());
        send.setModelo(aoData.getoForm());
        DatosSalidaPaginador<Producto> reply = this.productoServicioServiceBusiness.paginarProductos(send);
        reply.setsEcho(send.getsEcho());
        
        return reply;
        
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/agregar" , method=RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object>  agregar(@RequestBody Producto oForm ,  HttpServletResponse response ){
    	
    	Map result = new HashMap < String , Object>();
    	Errors errors = new BindException(oForm, "model");
    	new ProductoServicioValidator().validate(oForm, errors);
		if( errors.hasErrors()){
			this.procesaErroresDeCaptura(errors, result, response);
			return result;
		}
    	
    	try {
    		this.productoServicioServiceBusiness.agregarProductoServicio(oForm);
			result.put("oModel", oForm);
		} catch (Exception e) {
			this.procesarErrorDeNegocio(new AbstractException(e), result, response);
			return result;
		} 

    	return result;
	}

}
