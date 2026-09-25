package mx.gob.imss.ctirss.delta.gestion.medio.contacto.web.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.web.beans.MedioContactoDataTable;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.PropietarioMedioContactoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoContacto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/medioContacto")
public class CmpMedioContactoController extends AbstractController{

	@Autowired
	private MediosContactoServiceBusinessRemote medioContactoService;

	
	/**
	 * Metodo para obtener los datos del sujeto obligado para la mostrarlos en
	 * la cabecera de la solicitud.
	 * 
	 * @param idSolicitud
	 * @param model
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
	public String inicio(@ModelAttribute SujetoObligado sujetoObligado,
			Model model, HttpSession session) {
		Integer tpPropietario = new Integer(1);
		Long idPropietario = new Long(1);
		Long idSolicitud = new Long(1);
		Long idSujetoObligado = new Long(1);
		model.addAttribute("tpPropietario", tpPropietario);
		model.addAttribute("idPropietario", idPropietario);
		model.addAttribute("idSolicitud", idSolicitud);
		model.addAttribute("idSujetoObligado", idSujetoObligado);
		return "medioContacto";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/paginarMedioContacto", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<MedioContacto> paginarMedioContacto(
			@RequestParam("tpPropietario") Integer tpPropietario, @RequestParam("idPropietario") Long idPropietario,
			@RequestParam("idSolicitud") Long idSolicitud, @RequestParam("idSujetoObligado") Long idSujetoObligado,
			@RequestBody MedioContactoDataTable params, HttpSession session) {
		log.info("\n\nEN EL METODO paginarMedioContacto");
		DatosSalidaPaginador<MedioContacto> output = new DatosSalidaPaginador<MedioContacto>();
		DatosEntradaPaginador<MedioContacto> input = new DatosEntradaPaginador<MedioContacto>();
		input.parserArray(params.getAoData());
		input.setModelo(params.getoForm());
		List<MedioContacto> lista = Collections.EMPTY_LIST;
		log.info("PROPIEDAD [idSolicitud]     :"+idSolicitud);
		log.info("PROPIEDAD [tpPropietario]   :"+tpPropietario);
		log.info("PROPIEDAD [idPropietario]   :"+idPropietario);
		log.info("PROPIEDAD [idSujetoObligado]:"+idSujetoObligado);
		try {
			PropietarioMedioContactoEnum propietario = PropietarioMedioContactoEnum.obternerEnumById(tpPropietario);
			if(idSolicitud <= 0){
				idSolicitud = null;
			}
			lista = medioContactoService.consultarMediosContactoPorTipoPropietario(idPropietario, idSolicitud, propietario,
					idSujetoObligado);
			log.debug("LISTA MEDIOS DE CONTACTO: "+lista);
			if(lista == null){
				lista = Collections.EMPTY_LIST;
			}
			output.setAaData(lista);
			output.setiTotalDisplayRecords(lista.size());
			output.setiTotalRecords(lista.size());
			output.setsEcho(input.getsEcho());
		} catch (Exception e) {
			output = new DatosSalidaPaginador<MedioContacto>();
			output.setAaData(lista);
			output.setsEcho(input.getsEcho());
			log.error(input, e);
		}
		return output;
	}
	
	@RequestMapping(value = "/cargarComboTipoMedioContacto", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> cargarComboTipoMaquinaria(
			HttpServletResponse response) {
		log.info("EN EL METODO cargarComboTipoMaquinaria");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<TipoContacto> catalogo = medioContactoService.consultarTipoContacto();
			result.put("catalogo", catalogo);
		}catch(Exception e){
			String message = "";
			message="Ocurrio un error al obtener los datos del cat&aactue;logo< Tipo de Maquinaria.";
			result.put("mensajeError", message);
		}
		return result;
	}
}
