package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.alta;

import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.SociosDataTable;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/afiliacion/alta/socios")
public class SociosAltaController extends AbstractController {
	
	/**
	 * Utilizado para mostrar los socios (en sus variantes) contenidos en el sujeto obligado (sujeto tramite)
	 * en el objeto session
	 * @param params
	 * @param session
	 * @param locale
	 * @return DatosSalidaPaginador
	 */
	public DatosSalidaPaginador<Socio> visualizarSocios(SociosDataTable params, HttpSession session, Locale locale) {
		DatosSalidaPaginador<Socio> output= new DatosSalidaPaginador<Socio>();
		DatosEntradaPaginador<Socio> input = new DatosEntradaPaginador<Socio>();
		
		input.parserArray(params.getAoData());
		input.setModelo(params.getoForm());
		
		
		SujetoObligado sujetoTramite = (SujetoObligado) session.getAttribute("sujetoTramite");
		
		if (sujetoTramite.getMoral() != null) {
			if (sujetoTramite.getMoral().getSocios() != null) {
				output.setsEcho(input.getsEcho());
				output.setAaData(sujetoTramite.getMoral().getSocios());
			}
		}
		// SECCION DE PRUEBA
		output.setsEcho(input.getsEcho());
		
		if (sujetoTramite.getMoral() == null){
			sujetoTramite.setMoral(new Moral());
			sujetoTramite.getMoral().setSocios(new ArrayList<Socio>());
		}
		
		output.setAaData(sujetoTramite.getMoral().getSocios());
		
		output.setiTotalRecords(output.getAaData()!=null ? output.getAaData().size() : 0);
		output.setiTotalDisplayRecords(output.getAaData()!=null ? output.getAaData().size() : 0);
		
		if (output.getAaData() == null){ // aaData can't be null
			output.setAaData(new ArrayList<Socio>());
		}
		return output;
	}

}
