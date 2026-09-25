package mx.gob.imss.distss.portal.vigencia.grupo.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.distss.portal.vigencia.grupo.controller.pagination.FiltroDummyDataTable;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FiltroSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Proyecto: IMSS Digital - ${artifactId}
 */
@Controller
@RequestMapping(value = "/portlet")
public class PortletController extends AbstractController {


	@RequestMapping(value = "/dummy/{idDummy}", method = RequestMethod.GET)
	public String initPortletDummy(Model model, HttpServletRequest request,
			@PathVariable Long idDummy) {

		model.addAttribute("idDummy", idDummy);

		return "portletDummyInicial";
	}

	@RequestMapping(value = "/dummy/resumen/{idDummy}")
	public String detallePortletDummy(Model model, @PathVariable Long idDummy,
			HttpServletRequest request) {

		String datosDummy = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In adipiscing nulla in lacus porttitor viverra. Donec tempus felis vitae dui consectetur, non commodo est placerat. Integer non eros est. Aliquam porttitor in orci sit amet tincidunt. Donec nec varius enim. Donec sit amet posuere velit. Phasellus commodo quam eu massa aliquam posuere. Quisque pharetra ipsum non urna porta, at dapibus mi pharetra. Sed auctor, arcu non consequat sodales, enim magna commodo sem, in ultricies ligula libero in dolor. Morbi gravida lacus id luctus hendrerit. Nam ac quam ullamcorper, imperdiet quam sed, vehicula risus. Sed ac risus a enim mollis pellentesque. In a metus suscipit, dapibus orci scelerisque, cursus dolor.";

		model.addAttribute("DATOS_DUMMY", datosDummy);

		return "portletDummyContenido";
	}

	@RequestMapping(value = "/consultarDummy", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody
	DatosSalidaPaginador<TipoTramite> listarSolicitudes(
			@RequestBody FiltroDummyDataTable paramsDataTable,
			@ModelAttribute FiltroSolicitud filtrosBusqueda, Model model,
			HttpSession session, Locale locale) {

		DatosEntradaPaginador<TipoTramite> input = new DatosEntradaPaginador<TipoTramite>();
		input.parserArray(paramsDataTable.getAoData());
		DatosSalidaPaginador<TipoTramite> output = null;

		Usuario usuario = (Usuario) session.getAttribute("usuario");

		this.log.debug("Filtros: " + paramsDataTable.getoForm());

		this.log.debug("Echo: " + input.getsEcho());

		output.setsEcho(input.getsEcho());

		return output;
	}
}
