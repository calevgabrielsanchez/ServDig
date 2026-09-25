package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.afiliacion.baja;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBException;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.MunicipioImssNoLocalizadoException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.global.service.interfaces.AfiliacionGlobalServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.afiliacion.RegistroPatronalServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.baja.RegistroPatronalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.clasificacion.actividad.economica.ActividadEcServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.RegistrosPatronales34UtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ConcluirAltaPatronalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ParametrosServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.MunicipioIMSS;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RegistroPatronal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro;
import mx.gob.imss.distss.digital.jaxb.util.JaxbUtil;

/**
 * 
 * @author Hugo Martinez
 * @Projecto: delta-gestionPatronal-servicios-negocio-ejb
 * @Package: mx.gob.imss.ctirss.delta.gestion.patronal.service.business.afiliacion.baja
 * @Archivo: BajaPatronalServiceBusiness.java
 * @Fecha: 10/01/2013 09:35:19
 */
@Stateless(name="registroPatronalServiceBusiness" ,mappedName="registroPatronalServiceBusiness")
public class RegistroPatronalServiceBusiness extends AbstractServiceBusiness implements RegistroPatronalServiceBusinessRemote{
	@EJB
	RegistroPatronalServiceEntityLocal registroPatronalEntity;
	@EJB
	SujetoObligadoServiceBusinessRemote sujetoObligadoBusiness;
	@EJB
	DomicilioServiceBusinessRemote domicilioService;
	@EJB 
	ParametrosServiceBusinessRemote parametrosBusiness;
	@EJB
	ActividadEcServiceRemote clasificacionService;
	@EJB
	SolicitudServiceBusinessRemote solicitudService;
	@EJB
	ConcluirAltaPatronalBusinessRemote concluirAltaService;
	@EJB
	AfiliacionGlobalServiceRemote afiliacionGlobalservice;
	@EJB
	SujetoObligadoUtilityLocal registroPatronalutility;
	@EJB
	PersonaBusinessRemote personaBusiness;
	@EJB
	RegistrosPatronales34UtilityLocal registroPatronal34Utility;
	
	
	
	@Override
	public DatosSalidaPaginador<SujetoObligado> paginarRegistrosPatronales(
			DatosEntradaPaginador<SujetoObligado> input) {
		log.error("consultando Registros patronales para baja");
		SujetoObligado sujetoObligado=input.getModelo();
		DatosSalidaPaginador<SujetoObligado> output = null;

		if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
			output = registroPatronalEntity.consultarRegistrosPatronalesPersonaFisica(input);
		}else if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
			output = registroPatronalEntity.consultarRegistrosPatronalesPersonaMoral(input);
		}
				
		List<SujetoObligado> sujetosObligadosComplementarios=new ArrayList<SujetoObligado>();

		
		for(SujetoObligado sujetoObligadoActual : output.getAaData()){
			sujetosObligadosComplementarios.add(sujetoObligadoBusiness.obtenerDetalleRP(sujetoObligadoActual));
		}
		
		output.setAaData(sujetosObligadosComplementarios);
		
		return output;

	}

	@Override
	public void darDeBajaRPs(List<String> registrosPatronales)
			throws GestionPatronalBusinessException {
		try {
			this.registroPatronalEntity.darDeBajaRPs(registrosPatronales);
		} catch (Exception e) {
			throw new GestionPatronalBusinessException(e.getMessage()); 
		}
	}

	@Override
	public void registrarRegistroPatronal(RegistroPatronal RegistroPatronal)
			throws GestionPatronalBusinessException {
		throw new GestionPatronalBusinessException("No me han hecho nada aún!!!!");
		
	}

	@Override
	public RegistroPatronal obtenerDatosRegistroPatronal(
			Long cveIdPatronSujetoObligado) {
		return registroPatronalEntity.obtenerDatosGenerales(cveIdPatronSujetoObligado);
	}

	@Override
	public String obtenerCorreoDeNotificacion(Long cveIdPatronSujetoObligado) {
		
		return registroPatronalEntity.obtenerCorreoPorIdentificador(cveIdPatronSujetoObligado);
	}

	@Override
	public String obtenerCorreoDeNotificacion(String numeroRegistroPatronal) throws GestionPatronalBusinessException {
		String regPatronSubdelegacion= null;
		String numModalidad = null;
		String digitoVerificador = null;
		
		
		if(numeroRegistroPatronal==null)
			throw new GestionPatronalBusinessException("Registro Patronal inválido");
		
		if(numeroRegistroPatronal.length()<10 || numeroRegistroPatronal.length()>11)
			throw new GestionPatronalBusinessException("Registro Patronal inválido");
		
		
		if(numeroRegistroPatronal.length()==10){
			regPatronSubdelegacion= numeroRegistroPatronal.substring(0, 8);
			numModalidad = numeroRegistroPatronal.substring(8,10);
			digitoVerificador = null;
		}
		
		if(numeroRegistroPatronal.length()==11){
			regPatronSubdelegacion= numeroRegistroPatronal.substring(0, 8);
			numModalidad = numeroRegistroPatronal.substring(8,10);
			digitoVerificador = numeroRegistroPatronal.substring(10);
		}
		
		return registroPatronalEntity.obtenerCorreoPorNumeroDeRegistroPatronal(regPatronSubdelegacion,numModalidad,digitoVerificador);
	}

	@Override
	public String obtenerCorreoFiscal(Long idPersona, Long idTipoPersona) {
		return registroPatronalEntity.obtenerCorreoFiscal(idPersona, idTipoPersona);
	}
	
	@Override
   	public String obtenerNrpModalidad34PersonaFisica(Long idPersona) throws GestionPatronalBusinessException {
		Fisica pf=personaBusiness.getPersonaFisica(idPersona);
		MunicipioIMSS munImssDeDmParticular=null;
		try {
			munImssDeDmParticular = obtenerMunicipioImssDeDomicilioParticularPorIdPersona(idPersona);
		} catch (DomicilioNoLocalizadoException e) {
			throw new GestionPatronalBusinessException(e.getMessage());
		} catch (MunicipioImssNoLocalizadoException e) {
			throw new GestionPatronalBusinessException(e.getMessage());
		}
		SujetoObligado nrp = registroPatronalEntity.obtenerNrpModalidad34PersonaFisica(pf.getRfc(), munImssDeDmParticular.getCvecMunicipioSINDO());
		if(nrp==null)
			return null;
		
		if(!nrp.getFisica().getIdPersona().equals(idPersona))
			throw new GestionPatronalBusinessException("Se encontró un registro patronal asociado al rfc proporcionado sin embargo hay inconsistencia en la información.");
		
		return new StringBuffer().append(nrp.getNumeroRegistroPatronal()).append(nrp.getModalidad().getNumModalidad()).append(nrp.getDigVerificador()).toString();
    }
	
	
	@Override
   	public String obtenerNrpConvencionalPorModalidad(String cveMunImss, String numModalidad) throws GestionPatronalBusinessException {
       	return registroPatronalEntity.obtenerNrpConvencionalPorMunicipio(cveMunImss, numModalidad);
    }
	
	@Override
	public String obtenerNrpConvencionalPorDomicilioYModalidad(String cveMun,
			String cveEnt, String cp, String numModalidad)
			throws GestionPatronalBusinessException {
		Municipio municipio = new Municipio();
		municipio.setEntidadFederativa(new EntidadFederativa());
		municipio.setClave(cveMun);
		municipio.getEntidadFederativa().setClave(cveEnt);
		try {
			List<MunicipioIMSS> municipiosCandidatos = domicilioService
					.getMunicipioIMSSbyEstadoMunCP(municipio, cp);
			MunicipioIMSS municipioImssDom = municipiosCandidatos.get(0);

			Long idSubdelegacion = municipioImssDom.getSubdelegacion().getId();
			Long idDelegacion = municipioImssDom.getSubdelegacion().getDelegacion().getId();

			return registroPatronalEntity
					.obtenerNrpConvencionalPorSubdelegacionDelegacion(
							idDelegacion, idSubdelegacion, numModalidad);
		} catch (MunicipioImssNoLocalizadoException e) {
			throw new GestionPatronalBusinessException(e.getMessage());
		}
	}
	
	@Override
	public String obtenerNrpConvencionalPorDomicilioParticularYModalidad(Long idPersona, String numModalidad) throws GestionPatronalBusinessException{
		MunicipioIMSS munImssDeDmParticular=null;
		Long idSubdelegacion=null;
		Long idDelegacion=null;
		try {
			munImssDeDmParticular = obtenerMunicipioImssDeDomicilioParticularPorIdPersona(idPersona);
			idSubdelegacion= munImssDeDmParticular.getSubdelegacion().getId();
			idDelegacion=munImssDeDmParticular.getSubdelegacion().getDelegacion().getId();
			return registroPatronalEntity.obtenerNrpConvencionalPorSubdelegacionDelegacion(idDelegacion, 
					idSubdelegacion, numModalidad);
		} catch (DomicilioNoLocalizadoException e) {
			throw new GestionPatronalBusinessException(e.getMessage());
		} catch (MunicipioImssNoLocalizadoException e) {
			throw new GestionPatronalBusinessException(e.getMessage());
		}
		
    }
	
	@Override
	public String crearNrpDomestico(String nrp, Long idSolicitud, Long idPersona) throws GestionPatronalBusinessException{
		Domicilio domicilio=null;
		MunicipioIMSS munImss = null;
//		try {
//			domicilio = obtenerDomicilioParticularPorIdPersona(idPersona);
//			munImss = obtenerMunicipioImssPorDomicilio(domicilio);
//		} catch (DomicilioNoLocalizadoException e1) {
//			throw new GestionPatronalBusinessException(e1.getMessage());
//		} catch (MunicipioImssNoLocalizadoException e){
//			throw new GestionPatronalBusinessException(e.getMessage());
//		}
		
		Solicitud solicitud = solicitudService.consultarSolicitudPorId(idSolicitud);
		TramiteSeguroIvro tramiteImssDigital;
		for(Tramite tramite : solicitud.getTramites()){
			if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.COMPRA_SEGURO_DOMESTICO.getCodigo())
					|| tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.RENOVACION_SEGURO_DOMESTICO.getCodigo())){
				try {
				tramiteImssDigital = JaxbUtil.unmarshaller(tramite.getDetalleTramiteXml(),
						TramiteSeguroIvro.class);
				mx.gob.imss.digital.modelo.domicilio.Domicilio domicilioImssDig = tramiteImssDigital.getRegistroPatronal().getCentrotrabajo();
				domicilio = registroPatronal34Utility.transformarDomicilioImssDigitalAModeloNegocio(domicilioImssDig);
				munImss = obtenerMunicipioImssPorDomicilio(domicilio);
				} catch (JAXBException e1) {
	                log.debug("Transformando tramite seguro ivro error", e1);
	                e1.printStackTrace();
	    			throw new GestionPatronalBusinessException(e1.getMessage());
	            } catch (MunicipioImssNoLocalizadoException e) {
	    			e.printStackTrace();
	    			throw new GestionPatronalBusinessException(e.getMessage());
				} 
			}
		}
		
		SujetoObligado sujetoTramite = inicializarRegistroPatronalDomestico(idPersona, nrp, domicilio, munImss);
		OrigenSolicitud origenSolicitud =solicitud.getOrigenSolicitud();
		TramiteSujetoObligado tramiteAlta = new TramiteSujetoObligado();
		tramiteAlta.setTipoTramite(new TipoTramite());
		//NrpDomestico es para personas fisica.
		tramiteAlta.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.ALTA_SRT.getCodigo());
		tramiteAlta.setSujetoObligado(sujetoTramite);
		tramiteAlta.setFechaTramite(Calendar.getInstance().getTime());
		tramiteAlta.setFechaPresentacion(Calendar.getInstance().getTime());
		tramiteAlta.setEstadoTramite(new EstadoTramite());
		tramiteAlta.getEstadoTramite().setIdEstadoTramitePersona(EstadoSolicitudEnum.ATENDIDA.getCodigo());
		solicitudService.agregarTramiteASolicitud(idSolicitud, tramiteAlta);
		tramiteAlta=(TramiteSujetoObligado)afiliacionGlobalservice.concluirTramiteAltaPatronal(tramiteAlta, nrp, solicitud.getCertificado(), origenSolicitud);
		try {
			System.err.println("Tramite de alta: "+tramiteAlta.getSujetoObligado());
			solicitudService.actualizarTramiteDeAltaEnSolicitud(tramiteAlta);
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
			throw new GestionPatronalBusinessException(e.getMessage());
		} catch (TramiteNoEncontradoException e) {
			e.printStackTrace();
			throw new GestionPatronalBusinessException(e.getMessage());
		}
		try {
		solicitudService.generarDocumentos(solicitud.getNoFolioSolicitud());
		}catch(Exception e) {
			log.error("ocurrio un erro al generar los documentos " , e);
		}
		
		concluirAltaService.reportarMovimientoAltaPatronal(nrp, tramiteAlta.getSujetoObligado());
		
		return nrp;
	}
	
	@Override
	public String obtenerClaveMunicipioImss(String cveMun, String cveEnt, String cp) throws GestionPatronalBusinessException {
		Municipio municipio = new Municipio();
		municipio.setEntidadFederativa(new EntidadFederativa());
		municipio.setClave(cveMun);
		municipio.getEntidadFederativa().setClave(cveEnt);
		try {
			List<MunicipioIMSS> municipiosCandidatos
			= domicilioService.getMunicipioIMSSbyEstadoMunCP(
					municipio, 
					cp);
			return municipiosCandidatos.get(0).getCvecMunicipioSINDO();
		} catch (MunicipioImssNoLocalizadoException e) {
			throw new GestionPatronalBusinessException(e.getMessage());
		}
	}
	
	
	
	@Override
	public String obtenerClaveMunicipioImssDeDomicilioParticularPorIdPersona(Long idPersona) throws GestionPatronalBusinessException {
	
		try {
			return obtenerMunicipioImssDeDomicilioParticularPorIdPersona(idPersona).getCvecMunicipioSINDO();
		} catch (DomicilioNoLocalizadoException e) {
			throw new GestionPatronalBusinessException(e.getMessage());
		} catch (MunicipioImssNoLocalizadoException e) {
			throw new GestionPatronalBusinessException(e.getMessage());
		}
	}
	
	private MunicipioIMSS obtenerMunicipioImssPorDomicilio(Domicilio domicilio) throws MunicipioImssNoLocalizadoException{
		List<MunicipioIMSS> municipiosCandidatos
				= domicilioService.getMunicipioIMSSbyEstadoMunCP(
						domicilio.getAsentamiento().getLocalidad().getMunicipio(), 
						domicilio.getCodigoPostal().getCodigoPostal());
		return municipiosCandidatos.get(0);
	}
	
	private Domicilio obtenerDomicilioParticularPorIdPersona(Long idPersona) throws DomicilioNoLocalizadoException{
		Persona persona = creaPersonaFisicaparaConsulta(idPersona);
		List<Long> tiposDomicilio = new ArrayList<Long>();
		tiposDomicilio.add(TipoDomicilioEnum.PARTICULAR.getCodigo());
		
		List<Domicilio> domiciliosParticulares=domicilioService.obtenerDomiciliosPersonaPorTipo(persona, tiposDomicilio);
		Domicilio domParticular = domiciliosParticulares.get(0);
		return domParticular;
	}

	@Override
	public MunicipioIMSS obtenerMunicipioImssDeDomicilioParticularPorIdPersona(Long idPersona)
			throws DomicilioNoLocalizadoException, MunicipioImssNoLocalizadoException{
		Domicilio domParticular = obtenerDomicilioParticularPorIdPersona(idPersona);
		List<MunicipioIMSS> municipiosCandidatos
					= domicilioService.getMunicipioIMSSbyEstadoMunCP(
				domParticular.getAsentamiento().getLocalidad().getMunicipio(), 
				domParticular.getCodigoPostal().getCodigoPostal());
		return municipiosCandidatos.get(0);
	}
	
	private Persona creaPersonaFisicaparaConsulta(Long idPersona){
		Persona persona = new Persona();
		persona.setIdPersona(idPersona);
		persona.setTipoPersona(new TipoPersona());
		persona.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
		return persona;
	}
	
	
	
	private SujetoObligado inicializarRegistroPatronalDomestico(Long idPersona, String nrp, Domicilio domicilio, MunicipioIMSS municipioImss) throws GestionPatronalBusinessException{
		SujetoObligado so = new SujetoObligado();
		Fisica pf=personaBusiness.getPersonaFisica(idPersona);
		CentroTrabajo cTrabajo = registroPatronalutility.convertirDomicilioACentroTrabajo(domicilio);
		cTrabajo.setClave(null);
		cTrabajo.setCveIdPatronSujetoObligado(null);
		cTrabajo.setCveIdPersonafDom(null);
		if(cTrabajo.getDicTipoDomicilio()==null)
			cTrabajo.setDicTipoDomicilio(new TipoDomicilio());
		
		cTrabajo.getDicTipoDomicilio().setClave(TipoDomicilioEnum.CENTRO_TRABAJO.getCodigo().intValue());
		
		so.setFisica(pf);
		so.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
		so.setNumeroRegistroPatronal(nrp);
		so.setMunicipioIMSS(municipioImss);
		so.setSubdelegacion(municipioImss.getSubdelegacion());
		so.setClasificacion(obtenerClasificacionDomestico());
		so.setCntroTrabajo(cTrabajo);
		StringBuffer nombreComercial=new StringBuffer();
		if(pf.getNombre()!=null){
			nombreComercial.append(pf.getNombre());
		}
		if(pf.getPrimerApellido()!=null){
			nombreComercial.append(" ").append(pf.getPrimerApellido());
			
		}
		if(pf.getSegundoApellido()!=null){
			nombreComercial.append(" ").append(pf.getSegundoApellido());
			
		}
		so.setNombreComercial(nombreComercial.toString());
		
		return so;
	}
	
	private Clasificacion obtenerClasificacionDomestico() throws GestionPatronalBusinessException{
		Clasificacion cl = new Clasificacion();
		String fraccionDomesticos = parametrosBusiness.obtenerParametroDeConfiguracion("CVE_FRACCION_DOMESTICOS");
		Fraccion fraccion = clasificacionService.obtenerFraccionPornumFraccionCompleta(fraccionDomesticos);
		cl.setFraccion(fraccion);
		
		cl.setIndDistribuyeEntrega(0);
		cl.setIndPrestaServicioPersonal(0);
		cl.setIndServiciosATerceros(0);
		cl.setIndTransporteAjeno(0);
		cl.setIndTransportePropio(0);
		
		cl.setFecEfecto(Calendar.getInstance().getTime());
		return cl;
	}
	

	public Date obtenerEstadoHuelga(String regPatronal){
		return registroPatronalEntity.obtenerEstadoHuelga(regPatronal);
	}
	
	public List<RegistroPatronal> obtenerPatronesConMunicipiosImss(List<String> registrosPatronales){
		return registroPatronalEntity.obtenerPatronesConMunicipiosImss(registrosPatronales);
	}
	
}
