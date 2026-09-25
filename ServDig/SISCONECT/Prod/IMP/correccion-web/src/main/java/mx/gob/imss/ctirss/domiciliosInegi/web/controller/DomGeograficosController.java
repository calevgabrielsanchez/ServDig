package mx.gob.imss.ctirss.domiciliosInegi.web.controller;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtTramitePresentado;
import mx.gob.imss.ctirss.correccion.firma.service.interfaces.FirmaElectronicaService;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.session.ConstantesSession;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.firma.FirmaElectronicaBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;

import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.EstadoSolicitudEnum;


import mx.gob.imss.ctirss.delta.model.enums.TipoSolicitudEnum;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;




import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;


import mx.gob.imss.ctirss.domiciliosInegi.model.DgCatAmbito;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgCatEstado;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgCatMunicipio;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;
import mx.gob.imss.ctirss.domiciliosInegi.service.interfaces.DomiciliosInegiService;

@Controller
@RequestMapping(value="/domiciliosGeograficos")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DomGeograficosController extends AbstractController{
	
	@Autowired
	private DomiciliosInegiService<DgDomicilioGeografico> domiciliosInegiServiceBean;
	
	
	@Autowired
	protected DomicilioServiceBusinessRemote domiciliosServiceBean;
	
	
	
	
//	Beans para la prueba del almacenamiento del tramite
	@Autowired
	private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;

	@Autowired
	private SolicitudBusinessRemote solicitudBusiness;
	
	
	@Autowired
	private FirmaElectronicaService firmaElectronicaService;
		
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model) {
		model.addAttribute(new DgDomicilioGeografico());
		
		 return "domiciliosGeograficos/domGeoMain";
	}
	
	/*@RequestMapping(method=RequestMethod.POST)
	public String getCreateGenericForm(Model model,DgDomicilioGeografico dg) {
		
		if(dg==null) dg = new DgDomicilioGeografico();
		
		
		model.addAttribute(ConstantesSession.DOMICILIOS_GEOGRAFICOS_MODEL,dg);
		
		 return "domiciliosGeograficos/domGeoGeneric";
	}*/
	
	@RequestMapping(method=RequestMethod.POST)
	public String getCreateGenericForm(Model model,DgDomicilioGeografico dg,HttpServletRequest request) {
		
		if(dg==null) {dg = new DgDomicilioGeografico();}
		
		if(dg.getDgCatLocalidad().getDgCatAmbito()==null){
			UserSession user = getUsuarioFirmado(request);
			
			
			dg.getDgCatLocalidad().setDgCatAmbito(new DgCatAmbito());
			dg.getDgCatLocalidad().getDgCatAmbito().setAmbito(1);
			dg.getDgCatLocalidad().setDgCatMunicipio(new DgCatMunicipio());		
			dg.getDgCatLocalidad().getDgCatMunicipio().setDgCatEstado(new DgCatEstado());
					
			
			Integer delegacion = Integer.parseInt(user.getCveCodigoDelegacion());
			
			/*Dado las diferencias en los catalogos de SAC_DELEGACION y DG_CAT_ESTADO se 
			 *realiza el sigueinte ajuste 
			 */

			if(delegacion>=16 && delegacion<=31){delegacion-=1;}
			else if(delegacion>=32 && delegacion<=34){delegacion-=2;}
			else if(delegacion>=35 && delegacion<=40){delegacion = 9;}

			dg.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().setCveEnt(delegacion.toString().length()>1 ? 
																					delegacion.toString():"0"+delegacion);

		}
				
		model.addAttribute(ConstantesSession.DOMICILIOS_GEOGRAFICOS_MODEL,dg);
		
		 return "domiciliosGeograficos/domGeoGeneric";
	}
	
	@RequestMapping(value="/modificar" , method=RequestMethod.POST)
	public @ResponseBody DgDomicilioGeografico modify(@RequestBody DgDomicilioGeografico domicilioInegi, HttpServletResponse response) {
		System.out.println(".--. EN MODIFICAR");
		this.domiciliosInegiServiceBean.modificar(domicilioInegi);
		System.out.println(".--. MODIFICO");
		return domicilioInegi;
	}
	
	@RequestMapping(value="/eliminar" , method=RequestMethod.POST)
	public @ResponseBody DgDomicilioGeografico delete(@RequestBody DgDomicilioGeografico domicilioInegi, HttpServletResponse response) {
		System.out.println(".--. EN eliminar");
		this.domiciliosInegiServiceBean.eliminar(domicilioInegi);
		System.out.println("ELIMINO");
		return domicilioInegi;
	}
	
	@RequestMapping(value="/agregar" , method=RequestMethod.POST)
	public @ResponseBody DgDomicilioGeografico create(@RequestBody DgDomicilioGeografico dgDomicilioGeografico, HttpServletResponse response) {
		
		dgDomicilioGeografico.getDgAsentamiento().getId().setCveEnt(dgDomicilioGeografico.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getCveEnt());
		dgDomicilioGeografico.getDgAsentamiento().getId().setCveLoc(dgDomicilioGeografico.getDgCatLocalidad().getId().getCveLoc());
		dgDomicilioGeografico.getDgAsentamiento().getId().setCveMun(dgDomicilioGeografico.getDgCatLocalidad().getDgCatMunicipio().getId().getCveMun());
		dgDomicilioGeografico.getDgAsentamiento().getId().setCvePeriodo(1);
		
		dgDomicilioGeografico.getDgCatLocalidad().getId().setCveEnt(dgDomicilioGeografico.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getCveEnt());
		dgDomicilioGeografico.getDgCatLocalidad().getId().setCveMun(dgDomicilioGeografico.getDgCatLocalidad().getDgCatMunicipio().getId().getCveMun());
		dgDomicilioGeografico.getDgCatLocalidad().getId().setCvePeriodo(1);
		
		dgDomicilioGeografico.getDgCatLocalidad().getDgCatMunicipio().getId().setCveEnt(dgDomicilioGeografico.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getCveEnt());
		
		dgDomicilioGeografico.getDgCodigosPostales().getId().setCveAsen(dgDomicilioGeografico.getDgAsentamiento().getId().getCveAsen());
		dgDomicilioGeografico.getDgCodigosPostales().getId().setCveEnt(dgDomicilioGeografico.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getCveEnt());
		dgDomicilioGeografico.getDgCodigosPostales().getId().setCveLoc(dgDomicilioGeografico.getDgCatLocalidad().getId().getCveLoc());
		dgDomicilioGeografico.getDgCodigosPostales().getId().setCveMun(dgDomicilioGeografico.getDgCatLocalidad().getDgCatMunicipio().getId().getCveMun());
		dgDomicilioGeografico.getDgCodigosPostales().getId().setCvePeriodo(1);
		dgDomicilioGeografico.getDgCodigosPostales().setDgAsentamiento(dgDomicilioGeografico.getDgAsentamiento());
		
		this.domiciliosInegiServiceBean.agregar(dgDomicilioGeografico);		
		return dgDomicilioGeografico;
	}

	
	@RequestMapping(value="/sessionDomicilioGeografico", method=RequestMethod.POST )
	public @ResponseBody DgDomicilioGeografico almacenaSessionDomicilioInegi(@RequestBody DgDomicilioGeografico domicilioInegi,
			HttpServletRequest request) {
		 
		setDomicilioInegiSession(domicilioInegi.getHastableKeyDG(), domicilioInegi, request);
		 
		return domicilioInegi;
	}

	
	@RequestMapping(value="/recuperaDomicilioBDTU", method=RequestMethod.POST )
	public @ResponseBody Domicilio recuperaDomicilioBDTU(@RequestBody Domicilio domicilioInegi,
			HttpServletRequest request) {
		System.out.println("Recuperando Domicilio "+domicilioInegi.getClave());
		System.out.println("Servicio Inyectado "+domiciliosServiceBean);
		Domicilio domConsulta=null;
		try {
			domConsulta=domiciliosServiceBean.consultarDomicilio(domicilioInegi);
		} catch (DomicilioNoLocalizadoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return domConsulta;
	}
	
	
	
	
	@RequestMapping(value="/guardarDomicilioGeograficoBDTU", method=RequestMethod.POST )
	public @ResponseBody DgDomicilioGeografico guardarDomicilioGeograficoBDTU(@RequestBody Domicilio domicilioInegi,
			HttpServletRequest request) {
		
		
		
		 
		
		System.out.println("Domicilio convertido "+domicilioInegi);
		System.out.println("Recuperando JSON de domicilios goegraficos "+domiciliosServiceBean);
		Domicilio domAlmacenada=null;
		DgDomicilioGeografico domi=null;
		try {
			domAlmacenada=domiciliosServiceBean.registrarDomicilio(domicilioInegi);
			
			domi=new DgDomicilioGeografico();
			domi.setDomicilioBDTU(domAlmacenada);
			System.out.println("El id es  "+request.getParameter("idObjetoSesion"));
			domi.setHastableKeyDG(request.getParameter("idObjetoSesion"));			
			new DomGeograficosController().almacenaSessionDomicilioInegi(domi, request);
			
			
			
			System.out.println("IDDomicilio "+domAlmacenada.getClave());
			
		} catch (DomicilioNoValidoException e) {
			
			e.printStackTrace();
		}		
		return domi;
	}
	
	@RequestMapping(value="/getTramiteTest", method=RequestMethod.POST )
	public @ResponseBody Object getTramiteTest(@RequestBody Domicilio domicilioInegi,
			HttpServletRequest request) {
			System.out.println("getTramiteTest  "+firmaElectronicaService);
			CrtSolicitudcorr sol=new CrtSolicitudcorr();
			//sol.setPatron("B7521333106");
			SujetoObligado obl=firmaElectronicaService.recuperaSujetoObligado(sol);
			System.out.println("Obligado  "+obl);
			return null;	
	}
	
	
	
	@RequestMapping(value="/recuperaTramite", method=RequestMethod.POST )
	public @ResponseBody Object recuperaTramite(@RequestBody Domicilio domicilioInegi,
			HttpServletRequest request) {
		System.out.println("sujetoObligadoServiceBusiness "+sujetoObligadoServiceBusiness);
		System.out.println("solicitudBusiness "+solicitudBusiness);
	
	
		SujetoObligado sujeto=new SujetoObligado();
	

		
		sujeto=sujetoObligadoServiceBusiness.obtenerDetalleSujetoObligadoActividadEconomica(sujeto);
		
		
		System.out.println("Sujeti Obligado "+sujeto);
		
		Solicitud sol = new Solicitud();
		TipoSolicitud tipoSolicitud=new TipoSolicitud();
		EstadoSolicitud estadoSolicitud=new EstadoSolicitud();
		OrigenSolicitud origenSolicitud=new OrigenSolicitud();
		
		tipoSolicitud.setIdTipoSolicitud(TipoSolicitudEnum.REGISTRO.getId());
		estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.REGISTRADA.getId().intValue());
		origenSolicitud.setIdTipoSolicitud(OrigenSolicitudEnum.INTERNET.getId());
		
		
		
		sol.setTipoSolicitud(tipoSolicitud);
		sol.setEstadoSolicitud(estadoSolicitud);	
    	sol.setOrigenSolicitud(origenSolicitud);
		sol.setCadenaOriginal("||test|test||");
		sol.setSelloDigital("kDzAfMmntQpC5YQ68e4DXmzmMO0lG4fBN6hfgxby7swDBeh5PUxnyPwCLROnYi3EiExTmIIxEnFngli9ry7oGaLst3Gywt21IXNOrKIIBbBCaZCf7eCOknVqL2VPdVDy8nQ6eofzuDwlR3ZDTnADPfogyWeXePG2/bHlCQRgIrU=");
		sol.setSecuenciaDeNotaria("a8918fb4-693a-4985-a963-ebfc4a316378");
		sol.setFechaPresentacion(Calendar.getInstance().getTime());
		sol.setFechaActualizacion(Calendar.getInstance().getTime());
		sol.setSujetoObligado(sujeto);
		//sol.setNoFolioSolicitud(noFolioSolicitud)

		List<Tramite> tramites=new ArrayList<Tramite>();
		

		EstadoTramite estadoTramite=new EstadoTramite();
		estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.ACTIVO.getCodigo());		
		
		TipoTramite tipoTramite=new TipoTramite();
		tipoTramite.setIdTipoTramite(TipoTramiteEnum.ACTIVIDAD_ECONOMICA.getCodigo());
		
		TramiteSujetoObligado trSujeObl=new TramiteSujetoObligado();
		trSujeObl.setSujetoObligado(sujeto);
		
		
		trSujeObl.setEstadoTramite(estadoTramite);
		trSujeObl.setTipoTramite(tipoTramite);
		
		trSujeObl.setFechaConclusion(Calendar.getInstance().getTime());
		trSujeObl.setFechaPresentacion(Calendar.getInstance().getTime());
		trSujeObl.setFechaTramite(Calendar.getInstance().getTime());

		
		tramites.add(trSujeObl);
    	sol.setTramites(tramites);

		
		try {
			sol=solicitudBusiness.crear(sol);
			System.out.println("La solicitud almnacenada es "+sol.getSolicitudId());
			System.out.println("sol. "+sol.toString());
		} catch (SolicitudNoValidaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	
	
}