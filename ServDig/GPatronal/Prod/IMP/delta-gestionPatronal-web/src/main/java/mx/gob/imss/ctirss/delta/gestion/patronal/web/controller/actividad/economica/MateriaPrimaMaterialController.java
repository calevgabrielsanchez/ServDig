/**
 * delta-gestionPatronal-web22/05/2012
 * mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.actividad.economica22/05/2012
 * MateriaPrimaMaterialController.java
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
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.actividad.economica.MateriaPrimaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.MateriaMaterialDataTable;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MateriaPrima;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.web.validator.MateriaPrimaValidator;

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
@RequestMapping(value="/materiasprimas")
public class MateriaPrimaMaterialController extends AbstractController {
	
	@Autowired
	private MateriaPrimaServiceBusinessRemote materiaPrimaServiceBusiness;
	
	@RequestMapping(method=RequestMethod.GET)
	public String getMateriaPrima (@ModelAttribute SujetoObligado sujetoObligado, Model model , HttpSession session) {
    	
    	MateriaPrima materiaPrima = new MateriaPrima();
    	materiaPrima.setSujetoObligado(sujetoObligado);
    	
    	model.addAttribute("materiaprima", materiaPrima);
    	
		return "materiaprima";
	}
	
	@RequestMapping(value="/paginar", method=RequestMethod.POST)
    public @ResponseBody DatosSalidaPaginador<MateriaPrima> pagina(@RequestBody MateriaMaterialDataTable aoData ) {
    	
    	DatosEntradaPaginador<MateriaPrima> send = new DatosEntradaPaginador<MateriaPrima>();
        send.parserArray(aoData.getAoData());
        send.setModelo(aoData.getoForm());
        
        DatosSalidaPaginador<MateriaPrima> reply = this.materiaPrimaServiceBusiness.paginarMateriasPrimas(send);
        reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/agregar", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> agregar(@RequestBody MateriaPrima oForm,
			HttpServletResponse response) {

		Map result = new HashMap<String, Object>();
		Errors errors = new BindException(oForm, "model");

		MateriaPrimaValidator.getInstance().validate(oForm, errors);

		if (errors.hasErrors()) {
			this.procesaErroresDeCaptura(errors, result, response);
			return result;
		}

		try {
			this.materiaPrimaServiceBusiness.agregarMateriaPrima(oForm);
			result.put("oModel", oForm);
		} catch (Exception e) {
			this.procesarErrorDeNegocio(new AbstractException(e), result, response);
			return result;
		} 
		return result;
	}

}
