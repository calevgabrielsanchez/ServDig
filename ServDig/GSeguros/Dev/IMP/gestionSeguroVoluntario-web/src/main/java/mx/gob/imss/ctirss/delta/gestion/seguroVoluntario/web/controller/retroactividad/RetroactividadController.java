package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.controller.retroactividad;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.BeneficioRissServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.CalculoPagosRequest;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.CalculoPagosResponse;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.ModalidadResponseException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.ValidaRetroactividadDTO;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.ValidaRetroactividadRequest;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.ValidaRetroactividadResponse;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.retroactividad.vo.FilaAnioVigencia;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.retroactividad.vo.MesEstadoRetroactividad;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.RetroactividadServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.controller.BajaExpresaController;
import mx.gob.imss.digital.modelo.cobranza.DatosCalculoCuota;
import mx.gob.imss.ws.pagos.ivro.implementacion.ClienteWebserviceValidaPagosVentanilla;

@Controller
@RequestMapping(value = "/retroactividad")
@SessionAttributes("dtoResponse")
public class RetroactividadController extends AbstractController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RetroactividadController.class);
	
	private static final String OPCION_RETROACTIVIDAD = "seleccionRetroactividad";
	private static final String WIZARD_ALTA = "wizardContinuacionVoluntariaAltaInit";
	private static final String PERIODOS_RETRO = "mostrarPeriodosRetro";
	private static final String USUARIO_MODALIDAD = "MODALIDAD40";

	@Autowired
    @Qualifier("retroActividadServiceBusiness")
	RetroactividadServiceRemote retroactividadServiceRemote;
	
	
	@RequestMapping(value = "/permisoRetroactividad", method = RequestMethod.GET)
	public String permisoRetroactividad(@RequestParam("nss") String nss) {
		System.out.println("permiso Retroactividad");
		
		ValidaRetroactividadRequest requestServicioRetro = new ValidaRetroactividadRequest();
		requestServicioRetro.setNss(nss);
		requestServicioRetro.setUsuario(USUARIO_MODALIDAD);
		
		return "seleccionRetroactividad";
		
	}
	
	
	@RequestMapping(value = "/periodos/{opcion}", method = RequestMethod.GET)
	public String mostrarPeriodos(@PathVariable String opcion) {
		System.out.println("Mostrar periodos");		
		if(Integer.valueOf(opcion) == 1) {
			return PERIODOS_RETRO;
		}else {
			return WIZARD_ALTA;
		}		
	}
	
	@RequestMapping(value = "/periodos/{opcion}", method = RequestMethod.POST)
	public String mostrarPeriodosPost(Model model, HttpServletRequest request, HttpSession session,
			@ModelAttribute("dtoResponse") ValidaRetroactividadDTO  dtoResponse) {
		System.out.println("Mostrar periodos");
		System.out.println(dtoResponse);
		System.out.println("Imprimiendo objeto dto");
		System.out.println(dtoResponse.getIdCalculo());
		
		//Obtener años	
		Calendar calInicio = Calendar.getInstance();
		calInicio.setTime(dtoResponse.getFechaInicio());
		int anioInicio = calInicio.get(Calendar.YEAR);
		int mesInicio = calInicio.get(Calendar.MONTH);

		Calendar calFin = Calendar.getInstance();
		calFin.setTime(dtoResponse.getFechaFin());
		int anioFin = calFin.get(Calendar.YEAR);
		int mesFin = calFin.get(Calendar.MONTH);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	    
	    System.out.println(anioInicio);
	    System.out.println(anioFin);
	    
	    String[] nombresMeses = {"Ene", "Feb", "Mar", "Abr", "May", "Jun", 
                "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};
	    
	    List<Integer> listaAnios = new ArrayList<Integer>();
	    for (int anio = anioInicio; anio <= anioFin; anio++) {
	    	System.out.println(anio);
	        listaAnios.add(anio);
	    }	
		
        List<FilaAnioVigencia> filasCuadricula = new ArrayList<FilaAnioVigencia>();
        
        for (int anio = anioInicio; anio <= anioFin; anio++) {
            List<MesEstadoRetroactividad> listaMesesAnio = new ArrayList<MesEstadoRetroactividad>();
            
            for (int m = 0; m < 12; m++) {

                boolean enRango = false;
                
                int tiempoActual = anio * 12 + m;
                int tiempoInicio = anioInicio * 12 + mesInicio;
                int tiempoFin = anioFin * 12 + mesFin;
                
                if (tiempoActual >= tiempoInicio && tiempoActual <= tiempoFin) {
                    enRango = true;
                }
                
                listaMesesAnio.add(new MesEstadoRetroactividad(nombresMeses[m], m, anio, enRango));
            }
            
            filasCuadricula.add(new FilaAnioVigencia(anio, listaMesesAnio));
        }
        
        
        model.addAttribute("filasCuadricula", filasCuadricula);
        model.addAttribute("fechaInicio", sdf.format(dtoResponse.getFechaInicio()));
        model.addAttribute("fechaFin", sdf.format(dtoResponse.getFechaFin()));
        
		
		
		
		return PERIODOS_RETRO;		
	}	
	
	@RequestMapping(value = "/confirmarRetroactividad/{nssCifrado}", method = RequestMethod.GET)
	public String confirmarDatosRetroact(@PathVariable String nssCifrado) {
		
	System.out.println("Mostrar periodos");
		
		return "confirmarDatosRetroactividad";
		
	}	

	/*
     * ==========================================================
     * CALCULO DE PAGOS
     * ==========================================================
     */
    @RequestMapping(
            value = "/calculoPagos",
            method = RequestMethod.POST
    )
    @ResponseBody
    public CalculoPagosResponse calculoPagos(
            @RequestBody CalculoPagosRequest request)
            throws ModalidadResponseException {

    	
        LOGGER.info(
                "Iniciando consumo Servicio 2 - Calculo Pagos"
        );
       
		CalculoPagosResponse response = retroactividadServiceRemote.calculoPagosRetroactividad(request);
		
		if(response!=null && !response.getCodigo().equals("200")) {
			LOGGER.info(
	                "Ejecucion correcta de Servicio 2 - Calculo Pagos"
	        );
		}
		
        
        LOGGER.info(
                "Finaliza consumo Servicio 2 - Calculo Pagos"
        );
 
        return response;
    }
	
	
}
