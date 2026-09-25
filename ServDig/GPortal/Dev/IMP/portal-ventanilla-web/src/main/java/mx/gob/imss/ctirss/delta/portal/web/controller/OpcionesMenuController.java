package mx.gob.imss.ctirss.delta.portal.web.controller;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.portal.web.model.DatosEntradaDetalleIdentidadSujeto;
import mx.gob.imss.ctirss.delta.portal.web.utils.OpcionesVentnaillaConfig;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/menu")
public class OpcionesMenuController extends AbstractController {

	@RequestMapping(value = "/identidad", method = RequestMethod.POST)
	public String obtenerMenuIdentidad(
			@RequestBody DatosEntradaDetalleIdentidadSujeto datosEntrada,
			HttpServletRequest request, Model model) {

		request.setAttribute("idTipoPersona", datosEntrada.getIdTipoPersona());

		return "accionesIdentidad";
	}

	@RequestMapping(value = "/sujeto", method = RequestMethod.POST)
	public String obtenerMenuSujeto(
			@RequestBody DatosEntradaDetalleIdentidadSujeto datosEntrada,
			HttpServletRequest request, Model model) {

		request.setAttribute("idTipoSujeto", datosEntrada.getIdTipoSujeto());

		return "accionesSujeto";
	}
	
	@RequestMapping(value = "/check", method = { RequestMethod.GET, RequestMethod.POST })	
	public @ResponseBody boolean isTramiteHabilitado(@RequestParam Long idTramite) {
	
		this.log.debug("Se va a checar si el trámite "
				+ TipoTramiteEnum.obternerEnumById(idTramite.intValue())
				+ " está habilitado ");
		
		boolean isTramiteHabilitado = false;
		
		
		if (idTramite.intValue() == TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES
				.getCodigo().intValue()) {
			isTramiteHabilitado = OpcionesVentnaillaConfig.isOpcionHabilitada("ind_actualizar_datos");
		}
		
		this.log.debug("El trámite "
				+ TipoTramiteEnum.obternerEnumById(idTramite.intValue())
				+ " está habilitado -> " + isTramiteHabilitado);
		
		return isTramiteHabilitado;
	}
}