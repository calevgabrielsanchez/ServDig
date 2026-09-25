package mx.gob.imss.csdiss.sdroc.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.csdiss.sdroc.dto.InformacionIncidenciaDTO;
import mx.gob.imss.csdiss.sdroc.dto.InformacionObraDTO;
import mx.gob.imss.csdiss.sdroc.service.RegistroIncidenciaService;
import mx.gob.imss.csdiss.sdroc.service.RegistroObraService;
import mx.gob.imss.csdiss.sdroc.service.interfaces.BloqueoObraService;
import mx.gob.imss.csdiss.sdroc.service.interfaces.InformacionIncidenciaService;
import mx.gob.imss.csdiss.sdroc.service.interfaces.InformacionObraService;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/escritorio")
public class EscritorioRecortadoController {
	/**
	 * logger EscritorioController
	 */
	private static final Logger logger = Logger.getLogger(EscritorioControllerBak.class); 
	
	@Autowired
	RegistroObraService registroObraService;
	
	@Autowired
	SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusinessRemote;
	
	@Autowired
	MediosContactoServiceBusinessRemote mediosContactoServiceBusinessRemote;
	
	@Autowired
	BloqueoObraService bloqueObraService;
	
	@Autowired
	InformacionObraService informacionObraService;
	
	@Autowired
	InformacionIncidenciaService informacionIncidenciaServiceEjb;
	
	@Autowired
	RegistroIncidenciaService registroIncidenciaService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/consultaObras")
	@ResponseBody
	public Map<String, Object> consultaObrasJSON (@RequestBody Map<String, Object> data, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();

		Date _01_AGOSTO_2021 = new Date(121, 7, 1);
		Date _23_JULIO_2021 = new Date(121, 6, 23);

		String rfc = (String)session.getAttribute("rfc");
		if(rfc == null) {
			rfc = (String)data.get("rfc");
		}
		String rp = (String) session.getAttribute("rp");
		
		if(rp==null) {
			rp=(String) data.get("nrp");
		}
		Integer inicio = (Integer)data.get("start");
		Integer fin = (Integer) data.get("length");
		List<InformacionObraDTO> obras = null;
		
		Map<String, Object>  mapObras= informacionObraService.consultarObrasPorRfcYRp(rfc, rp,inicio.longValue(), fin.longValue());
		obras =  (List<InformacionObraDTO>) mapObras.get("obras");
		
		//Verificamos que si se tengan obras
		if(obras != null && !obras.isEmpty()) {
			for (InformacionObraDTO obra : obras) {

				//Validaciones para reforma de outsourcing
				Date fecIniObra = obra.getFecIniObra();

				//antes del 1 de agosto 2021 y despues del 23 de julio 2021
				if (fecIniObra.before(_01_AGOSTO_2021)) {
					obra.setAplicaIncRemplazo(false);
				}
				if (fecIniObra.after(_23_JULIO_2021)) {
					obra.setAplicaIncRemplazo(true);
				}


				String bimestre = consultarReporteBimestralPresentar(obra);
				System.out.println("El bimeste a presentar es " + bimestre);
				if (bimestre.equals("00-0000") || verificaReporteBimestralCorrientePresentado(obra.getFecIniObra(), obra.getFecFinObra())) {
					obra.setNumEvaluacionD32(0);
				} else {
					obra.setNumEvaluacionD32(1);
				}
	
			}
		} else {
			obras = new ArrayList<InformacionObraDTO>();
		}
		result.put("draw", data.get("draw"));
		result.put("recordsTotal", mapObras.get("total"));
		result.put("recordsFiltered",  mapObras.get("total"));
		result.put("data", obras);
		
		System.out.println("Se realiza la consulta ya con el 32D y se envian " + obras.size() + " obras" );
		return result;
	}
	
	private String consultarReporteBimestralPresentar(InformacionObraDTO obra) {
		String bimestre = "00-0000";
		InformacionIncidenciaDTO informacionIncidencia = informacionIncidenciaServiceEjb.consultarUltimoReporteBimestralPorCveInformacionObra(obra);
			
		if (informacionIncidencia != null) {
			if (informacionIncidencia.getCalendarioReporteDTO() != null) {
				if (informacionIncidencia.getCalendarioReporteDTO().getCveBimCalendario() != null
						&& informacionIncidencia.getNumAnio() != null) {
					bimestre = "0"
							.concat(informacionIncidencia.getCalendarioReporteDTO().getCveBimCalendario().toString())
							.concat("-").concat(informacionIncidencia.getNumAnio().toString());
				}
			}
		}

		return bimestre;
	}
	
	private boolean verificaReporteBimestralCorrientePresentado(Date fechaInicio, Date fechaFin) {
		boolean banderaBimestre = false;
		
		Calendar inicio = Calendar.getInstance();
		inicio.setTime(fechaInicio);
		int mesInicio = inicio.get(Calendar.MONTH) + 1;
		Calendar fin = Calendar.getInstance();
		fin.setTime(fechaFin);
		int mesFin = fin.get(Calendar.MONTH) + 1;
		Calendar hoy = Calendar.getInstance();
		int mesHoy = hoy.get(Calendar.MONTH)+ 1;
		Long bimInicio = 0L;
		Long bimFin = 0L;
		Long bimAct =0L;
        int anioInicioObra = inicio.get(Calendar.YEAR);
		int anioFinObra 	  = fin.get(Calendar.YEAR);
		System.out.println("anioInicioObra ... " +anioInicioObra);
		System.out.println("anioFinObra ... " +anioFinObra);
		//Cionsultamos el bimestre inicial
		bimInicio = informacionIncidenciaServiceEjb.consultarBimestreCorrespondiente(String.valueOf(mesInicio)).getCveBimCalendario();
		//Si el mes de fin es el mismo que de inicio se asigna el mismo bimestre y nos evitamos esa consulta
		if(mesInicio == mesFin) {
			bimFin = bimInicio;
		} else {
			bimFin = informacionIncidenciaServiceEjb.consultarBimestreCorrespondiente(String.valueOf(mesFin)).getCveBimCalendario();
		}
		//Verificamos si el mes es el mismo que que el de inicio se asigna el bimestre inicial
		if(mesInicio == mesHoy) {
			bimAct = bimInicio;
		} else if(mesFin == mesHoy) {
			bimAct = bimFin;
		} else{
			bimAct = informacionIncidenciaServiceEjb.consultarBimestreCorrespondiente(String.valueOf(mesHoy)).getCveBimCalendario();
		}
		
		if (fechaInicio.after(hoy.getTime())) {
			banderaBimestre = true;
		}else if (fechaFin.after(hoy.getTime())) {
			if (inicio.get(Calendar.YEAR) == hoy.get(Calendar.YEAR)) {
                if (bimInicio<bimAct && anioInicioObra<anioFinObra) {
					System.out.println("El anio de fin de la obra es mayor que el anio actual...");
					banderaBimestre = true;
				}
				if (bimInicio == bimAct) {
					banderaBimestre = true;
				}
			}
		}else if (inicio.get(Calendar.YEAR) == fin.get(Calendar.YEAR)) {
			if (bimInicio.intValue() == bimFin.intValue()) {
				banderaBimestre = true;
			}
		}

		return banderaBimestre;
	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping("")
	public ModelAndView redirectEscritorio(ModelAndView model, HttpSession session, HttpServletRequest request, 
			@RequestParam(value="rfc",required=false) String cveRfc, @RequestParam(value="nrp",required=false) String cveRp, 
			@RequestParam(value="cveIdPersona",required=false) Long cveIdPersona,
			@RequestParam(value="cveTipoPersona",required=false) Long cveTipoPersona) throws PersonaFisicaNoEncontradaException,IOException {
		
		model = new ModelAndView("/escritorioR");
		
		String idSession=(String) request.getSession().getAttribute("ID_SESSION_BLOQUEO");
		bloqueObraService.liberaObras(idSession);
		Persona persona = null;
		String razonSocial = null;
		String cveDelegacion = null;
		String desDelegacion = null;
		String cveSubDelegacion = null;
		String desSubDelegacion = null;
		String domicilioFiscal = null;		
		String correoEnvio = null;
		String cveCurp = null;
		String refNombre = null;
		String refApellidoPaterno = null;
		String refApellidoMaterno = null;
		Long parametroCveTipoPersona = null;
		List<MedioContacto> mediosPatron = null;
		SujetoObligado patron = null;
		
		
		if(cveRfc != null && cveRp != null && cveIdPersona != null && cveTipoPersona != null )
		{			
			session.setAttribute("rfc", cveRfc);
			session.setAttribute("rp", cveRp);
			session.setAttribute("cveIdPersona", cveIdPersona);
			session.setAttribute("cveTipoPersona", cveTipoPersona);
			
			parametroCveTipoPersona =  	cveTipoPersona;
			//en caso de que vengan los atributos quitamos la persona de la session
			session.removeAttribute("patron");
		}else{
			parametroCveTipoPersona = (Long) session.getAttribute("cveTipoPersona");
			patron = (SujetoObligado)session.getAttribute("patron");
			mediosPatron = (List<MedioContacto>) session.getAttribute("mediosPatron");
		}
		
		//verificamos si tenemos a la persona en session para que ya no la busquemos y asi ahorrarnos
		//los kilos de consulta de Fisica y Moral
		if(patron != null) {
			if(parametroCveTipoPersona != null && parametroCveTipoPersona.equals(new Long(1))) {
				persona = patron.getFisica();
				razonSocial = patron.getFisica().getNombreCompleto();
				refNombre = patron.getFisica().getNombre();
				refApellidoMaterno = patron.getFisica().getPrimerApellido();
				refApellidoMaterno = patron.getFisica().getSegundoApellido();
				cveCurp = patron.getFisica().getCurp();
			} else {
				persona = patron.getMoral();
				razonSocial = patron.getMoral().getRazonSocial();
			}
		} else {
			try {
				patron = informacionObraService.consultarInfoPatron(cveRp);
				System.out.println("El patron encontrado es " + patron);
				logger.error("El patron es por clase " + patron.getClasificacion().getIndRegPatClase());
				CentroTrabajo centro = new CentroTrabajo();
				centro.setCveIdPatronSujetoObligado(patron.getCveIdSujetoObligado());
				mediosPatron = mediosContactoServiceBusinessRemote.consultarMedioContactoDeCentroTrabajo(centro);
				session.setAttribute("cveTipoPersona", patron.getFisica() != null ? 1L : 2L);
				session.setAttribute("patron", patron);
				session.setAttribute("mediosPatron", mediosPatron);
				session.setAttribute("patronRPC", patron.getClasificacion().getIndRegPatClase());
			} catch(Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			}
		}
		
		//para este punto ya se debe tener el patron ya sea de session o consultado, y obtenemos a la persona
		persona = patron.getFisica() != null ? patron.getFisica() : patron.getMoral();
		//obtenemos el nombre o razon social dependiendo de si es patron fisica o moral
		razonSocial = patron.getFisica() != null ? patron.getFisica().getNombreCompleto() : patron.getMoral().getRazonSocial();
		logger.error("el rfc de la persona es " + persona.getRfc());
		cveRfc = persona.getRfc();
		session.setAttribute("rfc", cveRfc);
		//Seteamos los datos de delegacion
		cveDelegacion =  patron.getSubdelegacion().getDelegacion().getId().toString();
		desDelegacion = patron.getSubdelegacion().getDelegacion().getDescripcion();
		//seteamos los datos de la subdelegacion
		cveSubDelegacion = patron.getSubdelegacion().getId().toString();
		desSubDelegacion = patron.getSubdelegacion().getDescripcion();
		//obtenemos el coreo del centro de trabajo
		correoEnvio = this.getCorreo(mediosPatron);		
		
		if( persona != null && persona.getDomicilioFiscal() != null )
		{
			
			domicilioFiscal = persona.getDomicilioFiscal().getCalle();
			if(persona.getDomicilioFiscal().getNumExterior1() != null)
				domicilioFiscal.concat(", ").concat(persona.getDomicilioFiscal().getNumExteriorAlf().toString());
			if(persona.getDomicilioFiscal().getColonia() != null)		
				domicilioFiscal.concat(", ").concat(", ").concat(persona.getDomicilioFiscal().getColonia());
			if(persona.getDomicilioFiscal().getCodigoPostal() != null)
				domicilioFiscal.concat(", ").concat(persona.getDomicilioFiscal().getCodigoPostal().toString());
			if(persona.getDomicilioFiscal().getAsentamiento().getLocalidad().getMunicipio() != null)
				domicilioFiscal.concat(", ").concat(persona.getDomicilioFiscal().getAsentamiento().getLocalidad().getMunicipio().getNombre());
			if(persona.getDomicilioFiscal().getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa() != null)
				domicilioFiscal.concat(", ").concat(persona.getDomicilioFiscal().getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre());
			
		
		}else{
			domicilioFiscal = "No Disponible";
		}
		
		session.setAttribute("cveCurp", cveCurp);
		session.setAttribute("refNombre", refNombre);
		session.setAttribute("refApellidoPaterno", refApellidoPaterno);
		session.setAttribute("refApellidoMaterno", refApellidoMaterno);
		session.setAttribute("domicilioFiscal", domicilioFiscal);
		/*
		 * Datos Delegacion y Subdelegacion		
		 */
		session.setAttribute("cveDelegacion", cveDelegacion);
		session.setAttribute("desDelegacion", desDelegacion);
		session.setAttribute("cveSubDelegacion", cveSubDelegacion);
		session.setAttribute("desSubDelegacion", desSubDelegacion);
		
		session.setAttribute("correoEnvio", correoEnvio);
		session.setAttribute("rs", razonSocial);	
		
		return model;
	}
	
	/**
	 * Busqueda de medios de contacto por persona que se encuentra en la sesion
	 * @param mediosContacto
	 * @return String
	 */
	private String getCorreo(List<MedioContacto> mediosContacto) {
		String correoEnvio = null;
		
		if(mediosContacto != null) {
			//en caso de tener medios de contacto los recorremos
			for(MedioContacto medioContacto : mediosContacto) {
				//y buscamos el primero que sea de tipo 1 (Correo electronico)
				if(medioContacto.getTipoMedioContacto().getIdTipoMedioContacto().equals(1L)) {
					//en caso de encontrar un correo lo seteamos en la variable y terminsamos el ciclo
					correoEnvio = medioContacto.getDesFormaContacto();
					break;
				}
			}
		}
		
		return correoEnvio;
	}

}
