package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.alta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.RepresentanteLegalDataTable;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/afiliacion/alta/rl")
public class RepresentanteLegalAltaController extends AbstractController{
	
	@RequestMapping(value = "/agregar", method = RequestMethod.POST)
	public @ResponseBody boolean agregarRepLegal(@RequestBody SujetoObligado sujetoTramiteObj, HttpSession session) {
		
		SujetoObligado sujetoTramite = (SujetoObligado) session.getAttribute("sujetoTramite");
		TipoPersona tipoPersona = new TipoPersona();
		boolean respuesta = true; // evaluar cambio de valor
		
		System.err.println("alex >>> sujetoTramiteObj que llega: " + sujetoTramiteObj);
		
		System.err.println("alex >>> sujetoObligado de la sesion: " + sujetoTramite);
		
		//if (sujetoObligado.getTipoPersonaFiscal().name().equals(TipoPersonaFiscal.FISICA.name())){
		//if (true){
			tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
			// INICIAN LOS DATOS DE PRUEBA
			sujetoTramiteObj.getRepresentanteLegalAux().setCveIdPersona(1L);
			sujetoTramiteObj.getRepresentanteLegalAux().setTipoPersonaRepresentada(tipoPersona);
			sujetoTramiteObj.getRepresentanteLegalAux().setCveIdRepresentanteLegal(1L);
			sujetoTramiteObj.getRepresentanteLegalAux().setIndActAdmonDominio(BigDecimal.ONE);
			
			Fisica fisica = new Fisica();
			fisica.setIdPersona(sujetoTramiteObj.getRepresentanteLegalAux().getPersonaFisica().getIdPersona());
			fisica.setRfc("GACT121212");
			fisica.setCurp("GACT121212HDFRRR09");
			fisica.setNombre("tu");
			fisica.setPrimerApellido("adf");
			fisica.setSegundoApellido("ff");
			
			sujetoTramiteObj.getRepresentanteLegalAux().setPersonaFisica(fisica);
			
			sujetoTramite.getFisica().getRepresentantesLegales().add(sujetoTramiteObj.getRepresentanteLegalAux());
			
			
			/*sujetoTramiteObj.getRepresentanteLegalAux().setCveIdPersona(sujetoObligado.getFisica().getIdPersona());
			sujetoTramiteObj.getRepresentanteLegalAux().setTipoPersonaRepresentada(tipoPersona);
			sujetoObligado.getFisica().getRepresentantesLegales().add(sujetoTramiteObj.getRepresentanteLegalAux());*/
		//}
		/* else if (sujetoObligado.getTipoPersonaFiscal().name().equals(TipoPersonaFiscal.MORAL.name())) {
			tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
			sujetoTramiteObj.getRepresentanteLegalAux().setCveIdPersona(sujetoObligado.getMoral().getIdPersona());
			sujetoTramiteObj.getRepresentanteLegalAux().setTipoPersonaRepresentada(tipoPersona);
    		sujetoObligado.getMoral().getRepresentantesLegales().add(sujetoTramiteObj.getRepresentanteLegalAux());
		}*/
		
		//send.setModelo(sujetoTramiteObj.getRepresentanteLegalAux());
		session.setAttribute("sujetoTramite",sujetoTramite); // manejado por referencia, no hace falta volver a subir por ahora        	                                                           		
	    return respuesta;
		
	}
	
	/**
	 * Utilizado para mostrar los representantes legales contenidos en el sujeto obligado (sujeto tramite)
	 * en el objeto session
	 * @param params
	 * @param session
	 * @param locale
	 * @return DatosSalidaPaginador
	 */
	public DatosSalidaPaginador<RepresentanteLegal> visualizarRepresentantes(RepresentanteLegalDataTable params, HttpSession session, Locale locale) {
		DatosSalidaPaginador<RepresentanteLegal> output= new DatosSalidaPaginador<RepresentanteLegal>();
		DatosEntradaPaginador<RepresentanteLegal> input = new DatosEntradaPaginador<RepresentanteLegal>();
		
		input.parserArray(params.getAoData());
		input.setModelo(params.getoForm());
		
		
		SujetoObligado sujetoTramite = (SujetoObligado) session.getAttribute("sujetoTramite");
		
		if (sujetoTramite.getFisica()!=null){
			if (sujetoTramite.getFisica().getRepresentantesLegales()!=null){
				output.setsEcho(input.getsEcho());					
				output.setAaData(sujetoTramite.getFisica().getRepresentantesLegales());
			}					
		}
		if (sujetoTramite.getMoral()!=null){
			if (sujetoTramite.getMoral().getRepresentantesLegales()!=null){
				output.setsEcho(input.getsEcho());
				output.setAaData(sujetoTramite.getMoral().getRepresentantesLegales());
			}				
		}
		
		// SECCION DE PRUEBA
		output.setsEcho(input.getsEcho());
		if (sujetoTramite.getFisica() == null){
			sujetoTramite.setFisica(new Fisica());
			sujetoTramite.getFisica().setRepresentantesLegales(new ArrayList<RepresentanteLegal>());
		}
		
		output.setAaData(sujetoTramite.getFisica().getRepresentantesLegales());
		
		output.setiTotalRecords(output.getAaData()!=null ? output.getAaData().size() : 0);
		output.setiTotalDisplayRecords(output.getAaData()!=null ? output.getAaData().size() : 0);
		
		if (output.getAaData() == null){ // aaData can't be null
			output.setAaData(new ArrayList<RepresentanteLegal>());
		}
		return output;
	}

}
