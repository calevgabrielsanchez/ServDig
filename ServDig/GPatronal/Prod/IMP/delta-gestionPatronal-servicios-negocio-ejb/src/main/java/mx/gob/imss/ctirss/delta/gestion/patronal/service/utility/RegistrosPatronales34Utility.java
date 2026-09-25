package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioCamino;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioCarretera;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.MunicipioIMSS;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoAdministracion;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoAmbito;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoAsentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDerechoTransito;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoMargen;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoTerminoGeneral;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.digital.modelo.domicilio.Camino;
import mx.gob.imss.digital.modelo.domicilio.Carretera;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;
import mx.gob.imss.digital.modelo.patron.RegistroPatronal;
import mx.gob.imss.digital.modelo.patron.RegistrosPatronales;
import mx.gob.imss.digital.modelo.persona.Persona;

import org.springframework.util.CollectionUtils;

@Stateless(mappedName = "registrosPatronales34Utility", name = "registrosPatronales34Utility")
public class RegistrosPatronales34Utility 
	extends AbstractServiceUtility implements RegistrosPatronales34UtilityLocal{

	
	@Override
	public List<Long> getModalidad34(){
		List<Long> idsModalidades = new ArrayList<Long>(1);
		idsModalidades.add(ModalidadEnum.TREINTAYCUATRO.getId());
		return idsModalidades;
	}
	
	@Override
	public void transformarSujetosObligados(
			Persona persona, List<SujetoObligado> listaSujetosObligados) {		
		
		List<RegistroPatronal> listaRegistroPatronal =new ArrayList<RegistroPatronal>();		
		for(SujetoObligado so : listaSujetosObligados){			
			if(so!=null){
				RegistroPatronal rp = transformarSujetoToRP(so);
				listaRegistroPatronal.add(rp);			
			}
		}
		//Cargar arreglo de RPs a la persona
		if (!CollectionUtils.isEmpty(listaRegistroPatronal)) {
			RegistroPatronal[] aRegistrosPatronales = (RegistroPatronal[]) listaRegistroPatronal
				.toArray(new RegistroPatronal[listaRegistroPatronal.size()]);
			persona.setRegistrosPatronales(new RegistrosPatronales());
			persona.getRegistrosPatronales().setRegistrosPatronal(aRegistrosPatronales);
			log.debug("RPs agregados al RFC: " + persona.getRfc());
		}
	}
	
	@Override
	public RegistroPatronal transformarSujetoToRP(SujetoObligado so){
		RegistroPatronal rp = null;
		if(so != null){
			if(so.getCntroTrabajo()!=null){
				so.getCntroTrabajo().setMediosContacto(null);
				so.getCntroTrabajo().setCveIdPatronSujetoObligado(null);
			}			
			rp = new RegistroPatronal();
			//Se agrega código para asegurar tener el nrp a 11 posiciones cuando es un nrp migrado
			rp.setNumeroRegistroPatronal(obtenerNRPCompleto(so));
			rp.setDigitoVerificador(so.getDigVerificador());
			rp.setModalidad(cargarModalidad(so.getModalidad()));
			rp.setMunicipioImss(cargarMunicipioImss(so.getMunicipioIMSS()));			
			rp.setCentrotrabajo(cargarCentroDeTrabajo(so.getCntroTrabajo()));
			log.debug("RP Localizado: " + rp.getNumeroRegistroPatronal()
				+ (rp.getModalidad()!=null?rp.getModalidad().getNumModalidad():" ")+rp.getDigitoVerificador());					
		}
		return rp;
	}
	
	private String obtenerNRPCompleto(SujetoObligado so){
		StringBuffer sb = new StringBuffer();
		//Se agrega código para asegurar tener el nrp a 11 posiciones cuando es un nrp migrado
		if(so.getNumeroRegistroPatronal().length() == 10){
			sb.append(so.getNumeroRegistroPatronal()).append(so.getDigVerificador());
		}else if(so.getNumeroRegistroPatronal().length() == 8){
			sb.append(so.getNumeroRegistroPatronal()).append(
				so.getModalidad().getNumModalidad()).append(so.getDigVerificador());			
		}else{
			sb.append(so.getNumeroRegistroPatronal());
		}
		return sb.toString();
	}
	
	public Municipio getMunicipioNegocio(mx.gob.imss.digital.modelo.domicilio.Municipio municipioHelper){
		try {
			return transformaMunicipio(municipioHelper);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();			
		}
		return null;
	}
	
	
	private mx.gob.imss.digital.modelo.comun.Modalidad cargarModalidad(Modalidad modalidad){
		mx.gob.imss.digital.modelo.comun.Modalidad modalidadHelper = null;
		try {
			if(modalidad != null){
				modalidadHelper = new mx.gob.imss.digital.modelo.comun.Modalidad();	
				super.copyBeans(modalidad, modalidadHelper);	
				log.debug("Copiando modalidad: " + modalidadHelper);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return modalidadHelper;
	}
	
	private mx.gob.imss.digital.modelo.domicilio.MunicipioIMSS cargarMunicipioImss(MunicipioIMSS municipioIMSS){
		mx.gob.imss.digital.modelo.domicilio.MunicipioIMSS municipioIMSSHelper = null;
		try {
			if(municipioIMSS!=null && municipioIMSS.getIdMunicipio()!=null){
				municipioIMSSHelper = new mx.gob.imss.digital.modelo.domicilio.MunicipioIMSS();
				//Preparar TipoAmbito y Subdelegacion
				mx.gob.imss.digital.modelo.domicilio.TipoAmbito tipoAmbito 
					= transformaTipoAmbito(municipioIMSS.getTipoAmbito());				
				mx.gob.imss.digital.modelo.domicilio.Subdelegacion subdelegacion 
					= transformaSubdelegacion(municipioIMSS.getSubdelegacion());
				//Eliminar referencias
				municipioIMSS.setTipoAmbito(null);
				municipioIMSS.setSubdelegacion(null);
				super.copyBeans(municipioIMSS, municipioIMSSHelper);				
				municipioIMSSHelper.setTipoAmbito(tipoAmbito);
				municipioIMSSHelper.setSubdelegacion(subdelegacion);
				log.debug("Copiando municipioIMSS: " + municipioIMSSHelper);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return municipioIMSSHelper;
	}
	
	private mx.gob.imss.digital.modelo.domicilio.Domicilio cargarCentroDeTrabajo(
			mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domicilio){
		
		mx.gob.imss.digital.modelo.domicilio.Domicilio domicilioHelper = null;
		try {
			if(domicilio != null){			
				domicilioHelper = new mx.gob.imss.digital.modelo.domicilio.Domicilio();			
				
				//Preparar Localidad
				mx.gob.imss.digital.modelo.domicilio.Localidad localidad 
					= transformaLocalidad(domicilio.getLocalidad());
				//Preparar Asentamiento
				mx.gob.imss.digital.modelo.domicilio.Asentamiento asentamiento 
					= transformaAsentamiento(domicilio.getAsentamiento());					
				//Preparar Camino
				mx.gob.imss.digital.modelo.domicilio.Camino camino 
					= transformaCamino(domicilio.getDomicilioCamino());
				//Preparar Carretera
				mx.gob.imss.digital.modelo.domicilio.Carretera carretera 
					= transformaCarretera(domicilio.getDomicilioCarretera());
				//Preparar TipoDomicilio
				mx.gob.imss.digital.modelo.domicilio.TipoDomicilio tipoDomicilio 
					= transformaTipoDomicilio(domicilio.getTipoDomicilio());				
				//Preparar VialidadPrimaria
				mx.gob.imss.digital.modelo.domicilio.Vialidad vialidadPrimaria 
					= transformaVialidad(domicilio.getVialidadPrimaria());				
				//Preparar VialidadReferenciaPosterior
				mx.gob.imss.digital.modelo.domicilio.Vialidad vialidadReferenciaPosterior 
					= transformaVialidad(domicilio.getVialidadReferenciaPosterior());				
				//Preparar VialidadReferenciaPrimaria
				mx.gob.imss.digital.modelo.domicilio.Vialidad vialidadReferenciaPrimaria 
					= transformaVialidad(domicilio.getVialidadReferenciaPrimaria());				
				//Preparar VialidadReferenciaSecundaria
				mx.gob.imss.digital.modelo.domicilio.Vialidad vialidadReferenciaSecundaria 
					= transformaVialidad(domicilio.getVialidadReferenciaSecundaria());				
				//Preparar CP
				String codigoPostal = null;
				if(domicilio.getCodigoPostal() != null)
					codigoPostal = domicilio.getCodigoPostal().getCodigoPostal();
				
				//Eliminar referencias
				domicilio.setLocalidad(null);
				domicilio.setAsentamiento(null);
				domicilio.setDomicilioCamino(null);
				domicilio.setDomicilioCarretera(null);
				domicilio.setTipoDomicilio(null);
				domicilio.setVialidadPrimaria(null);
				domicilio.setVialidadReferenciaPosterior(null);
				domicilio.setVialidadReferenciaPrimaria(null);
				domicilio.setVialidadReferenciaSecundaria(null);
				domicilio.setCodigoPostal(null);				
				//Quitar informacion NO requerida a transformar
				domicilio.setEstadoAdministracionAnteriorDomicilio(null);
				domicilio.setEstadoAdministracionDomicilio(null);
																		
				super.copyBeans(domicilio, domicilioHelper);
				
				domicilioHelper.setIdDomicilio(domicilio.getClave().longValue());
				domicilioHelper.setLocalidad(localidad);
				domicilioHelper.setAsentamiento(asentamiento);
				domicilioHelper.setCamino(camino);
				domicilioHelper.setCarretera(carretera);
				domicilioHelper.setTipoDomicilio(tipoDomicilio);
				domicilioHelper.setVialidadPrimaria(vialidadPrimaria);
				domicilioHelper.setVialidadReferenciaPosterior(vialidadReferenciaPosterior);
				domicilioHelper.setVialidadReferenciaPrimaria(vialidadReferenciaPrimaria);
				domicilioHelper.setVialidadReferenciaSecundaria(vialidadReferenciaSecundaria);
				domicilioHelper.setCodigoPostal(codigoPostal);
				log.debug("Copiando centroTrabajo: " + domicilioHelper);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return domicilioHelper;
	}
		
	private mx.gob.imss.digital.modelo.domicilio.Subdelegacion transformaSubdelegacion(Subdelegacion subdelegacionHelper) 
			throws IllegalAccessException, InvocationTargetException{
		
		mx.gob.imss.digital.modelo.domicilio.Subdelegacion subdelegacion = null;
		if(subdelegacionHelper !=null 
				&& subdelegacionHelper.getId() !=null){			
			subdelegacion = new mx.gob.imss.digital.modelo.domicilio.Subdelegacion();
			//Cargar delegacion para setearla despues de copyBean de Subdelegacion
			mx.gob.imss.digital.modelo.domicilio.Delegacion delegacion 
				= transformaDelegacion(subdelegacionHelper.getDelegacion());
			subdelegacionHelper.setDelegacion(null);
			super.copyBeans(subdelegacionHelper, subdelegacion);
			subdelegacion.setDelegacion(delegacion);
		}
		return subdelegacion;
	}
	
	private mx.gob.imss.digital.modelo.domicilio.Delegacion transformaDelegacion(Delegacion delegacionHelper) 
			throws IllegalAccessException, InvocationTargetException{
		
		mx.gob.imss.digital.modelo.domicilio.Delegacion delegacion = null;
		if(delegacionHelper != null 
				&& delegacionHelper.getId() != null){
			delegacion = new mx.gob.imss.digital.modelo.domicilio.Delegacion();
			super.copyBeans(delegacionHelper, delegacion);
		}
		return delegacion;
	}
	
	private mx.gob.imss.digital.modelo.domicilio.TipoDomicilio transformaTipoDomicilio(
			TipoDomicilio tipoDomicilioHelper){		
		
		mx.gob.imss.digital.modelo.domicilio.TipoDomicilio tipoDomicilio = null;
		if(tipoDomicilioHelper !=null 
				&& tipoDomicilioHelper.getClave() !=null){
			tipoDomicilio = new mx.gob.imss.digital.modelo.domicilio.TipoDomicilio();
			tipoDomicilio.setIdTipoDomicilio(tipoDomicilioHelper.getClave().longValue());
			tipoDomicilio.setDescripcion(tipoDomicilioHelper.getDescripcion());
		}
		return tipoDomicilio;
	}

	private mx.gob.imss.digital.modelo.domicilio.Vialidad transformaVialidad(Vialidad vialidadHelper) 
			throws IllegalAccessException, InvocationTargetException{		
		
		mx.gob.imss.digital.modelo.domicilio.Vialidad vialidad = null;
		if(vialidadHelper != null && vialidadHelper.getClave() != null){
			vialidad = new mx.gob.imss.digital.modelo.domicilio.Vialidad();
			//Preparar TipoVialidad
			mx.gob.imss.digital.modelo.domicilio.TipoVialidad tipoVialidad = 
				transformaTipoVialidad(vialidadHelper.getTipoVialidad());
			vialidadHelper.setTipoVialidad(null);
			super.copyBeans(vialidadHelper, vialidad);
			vialidad.setTipoVialidad(tipoVialidad);
		}
		return vialidad;		
	}
		
	private mx.gob.imss.digital.modelo.domicilio.Camino transformaCamino(DomicilioCamino caminoHelper) 
			throws IllegalAccessException, InvocationTargetException{
		
		mx.gob.imss.digital.modelo.domicilio.Camino camino = null;
		if(caminoHelper != null){
			camino = new mx.gob.imss.digital.modelo.domicilio.Camino();
			//Preparar TipoTerminoGeneral y TipoMargen
			mx.gob.imss.digital.modelo.domicilio.TipoTerminoGeneral tipoTerminoGeneral 
				= transformaTipoTerminoGeneral(caminoHelper.getTerminoGeneral());
			mx.gob.imss.digital.modelo.domicilio.TipoMargen tipoMargen 
				= transformaTipoMargen(caminoHelper.getMargen());
			//Eliminar referencias
			caminoHelper.setTerminoGeneral(null);
			caminoHelper.setMargen(null);			
			super.copyBeans(caminoHelper, camino);
			camino.setTerminoGeneral(tipoTerminoGeneral);
			camino.setMargen(tipoMargen);
		}
		return camino;
	}
	
	private mx.gob.imss.digital.modelo.domicilio.Carretera transformaCarretera(
			DomicilioCarretera carreteraHelper)throws IllegalAccessException, InvocationTargetException{
		
		mx.gob.imss.digital.modelo.domicilio.Carretera carretera = null;
		if(carreteraHelper!=null){
			carretera = new mx.gob.imss.digital.modelo.domicilio.Carretera();		
			//Preparar TipoTerminoGeneral,TipoDerechoTransito y TipoAdministracion
			mx.gob.imss.digital.modelo.domicilio.TipoTerminoGeneral tipoTerminoGeneral 
				= transformaTipoTerminoGeneral(carreteraHelper.getTerminoGeneral());
			mx.gob.imss.digital.modelo.domicilio.TipoDerechoTransito tipoDerechoTransito 
				= transformaTipoDerechoTransito(carreteraHelper.getDerechoTransito());
			mx.gob.imss.digital.modelo.domicilio.TipoAdministracion tipoAdministracion 
				= transformaTipoAdministracion(carreteraHelper.getAdministracion());
			//Eliminar referencias
			carreteraHelper.setTerminoGeneral(null);
			carreteraHelper.setDerechoTransito(null);
			carreteraHelper.setAdministracion(null);			
			super.copyBeans(carreteraHelper, carretera);
			carretera.setTerminoGeneral(tipoTerminoGeneral);
			carretera.setDerechoTransito(tipoDerechoTransito);
			carretera.setAdministracion(tipoAdministracion);
		}
		return carretera;
	}
	
	private mx.gob.imss.digital.modelo.domicilio.Localidad transformaLocalidad(
			Localidad localidadHelper)throws IllegalAccessException, InvocationTargetException{
		
		mx.gob.imss.digital.modelo.domicilio.Localidad localidad = null;
		if(localidadHelper != null 
				&& localidadHelper.getClave()!=null){
			localidad = new mx.gob.imss.digital.modelo.domicilio.Localidad();
			//Preparar Municipio
			mx.gob.imss.digital.modelo.domicilio.Municipio municipio 
				= transformaMunicipio(localidadHelper.getMunicipio());
			localidadHelper.setMunicipio(null);			
			localidadHelper.setAsentamientos(null);
			super.copyBeans(localidadHelper, localidad);
			localidad.setMunicipio(municipio);
		}
		return localidad;
	}
	
	private mx.gob.imss.digital.modelo.domicilio.Asentamiento transformaAsentamiento(
			Asentamiento asentamientoHelper)throws IllegalAccessException, InvocationTargetException{
		
		mx.gob.imss.digital.modelo.domicilio.Asentamiento asentamiento = null;
		if(asentamientoHelper != null 
				&& asentamientoHelper.getClave()!=null){
			asentamiento = new mx.gob.imss.digital.modelo.domicilio.Asentamiento();
			//Preparar Localidad
			mx.gob.imss.digital.modelo.domicilio.Localidad localidad 
				= transformaLocalidad(asentamientoHelper.getLocalidad());
			//Preparar Municipio
			mx.gob.imss.digital.modelo.domicilio.Municipio municipio 
				= transformaMunicipio(asentamientoHelper.getMunicipio());
			//Preparar TipoAsentamiento
			mx.gob.imss.digital.modelo.domicilio.TipoAsentamiento tipoAsentamiento 
				= transformaTipoAsentamiento(asentamientoHelper.getTipoAsentamiento());
			//Preparar Municipio
			String codigoPostal = null;
			if(asentamientoHelper.getCodigoPostal() != null)
				codigoPostal = asentamientoHelper.getCodigoPostal().getCodigoPostal();
			
			asentamientoHelper.setTipoAsentamiento(null);
			asentamientoHelper.setLocalidad(null);
			asentamientoHelper.setMunicipio(null);		
			asentamientoHelper.setCodigoPostal(null);
			super.copyBeans(asentamientoHelper, asentamiento);
			asentamiento.setTipoAsentamiento(tipoAsentamiento);
			asentamiento.setLocalidad(localidad);
			asentamiento.setMunicipio(municipio);
			asentamiento.setCodigoPostal(codigoPostal);
			
		}
		return asentamiento;
	}
	
	
	private mx.gob.imss.digital.modelo.domicilio.Municipio transformaMunicipio(
			Municipio municipioHelper)throws IllegalAccessException, InvocationTargetException{
		mx.gob.imss.digital.modelo.domicilio.Municipio municipio = null;
		if(municipioHelper != null 
				&& municipioHelper.getClave()!=null){
			municipio = new mx.gob.imss.digital.modelo.domicilio.Municipio(); 
			//Preparar EntidadFederativa
			mx.gob.imss.digital.modelo.domicilio.EntidadFederativa entidadFederativa 
				= transformaEntidadFederativa(municipioHelper.getEntidadFederativa());
			municipioHelper.setEntidadFederativa(null);
			municipioHelper.setLocalidades(null);
			super.copyBeans(municipioHelper, municipio);
			municipio.setEntidadFederativa(entidadFederativa);
		}
		return municipio;
	}
	
	private mx.gob.imss.digital.modelo.domicilio.EntidadFederativa transformaEntidadFederativa(
			EntidadFederativa entidadFederativaHelper)throws IllegalAccessException, InvocationTargetException{
		mx.gob.imss.digital.modelo.domicilio.EntidadFederativa entidadFederativa = null;
		if(entidadFederativaHelper != null 
				&& entidadFederativaHelper.getClave()!=null){
			entidadFederativa = new mx.gob.imss.digital.modelo.domicilio.EntidadFederativa();
			entidadFederativaHelper.setMunicipios(null);
			super.copyBeans(entidadFederativaHelper, entidadFederativa);
		}
		return entidadFederativa;
	}
	
	private mx.gob.imss.digital.modelo.domicilio.TipoDerechoTransito transformaTipoDerechoTransito(
			TipoDerechoTransito tipoDerechoTransitoHelper)throws IllegalAccessException, InvocationTargetException{		
		mx.gob.imss.digital.modelo.domicilio.TipoDerechoTransito tipoDerechoTransito = null;
		if(tipoDerechoTransitoHelper != null){
			tipoDerechoTransito = new mx.gob.imss.digital.modelo.domicilio.TipoDerechoTransito();
			super.copyBeans(tipoDerechoTransitoHelper, tipoDerechoTransito);
		}		
		return tipoDerechoTransito;
	}
	
	private mx.gob.imss.digital.modelo.domicilio.TipoAdministracion transformaTipoAdministracion(
			TipoAdministracion tipoAdministracionHelper)throws IllegalAccessException, InvocationTargetException{		
		mx.gob.imss.digital.modelo.domicilio.TipoAdministracion tipoAdministracion = null;
		if(tipoAdministracionHelper != null){
			tipoAdministracion = new mx.gob.imss.digital.modelo.domicilio.TipoAdministracion();
			super.copyBeans(tipoAdministracionHelper, tipoAdministracion);
		}		
		return tipoAdministracion;
	}
	
	private mx.gob.imss.digital.modelo.domicilio.TipoMargen transformaTipoMargen(
			TipoMargen tipoMargenHelper)throws IllegalAccessException, InvocationTargetException{		
		mx.gob.imss.digital.modelo.domicilio.TipoMargen tipoMargen = null;
		if(tipoMargenHelper != null){
			tipoMargen = new mx.gob.imss.digital.modelo.domicilio.TipoMargen();
			super.copyBeans(tipoMargenHelper, tipoMargen);
		}		
		return tipoMargen;
	}

	private mx.gob.imss.digital.modelo.domicilio.TipoTerminoGeneral transformaTipoTerminoGeneral(
			TipoTerminoGeneral tipoTerminoGeneralHelper)throws IllegalAccessException, InvocationTargetException{		
		mx.gob.imss.digital.modelo.domicilio.TipoTerminoGeneral tipoTerminoGeneral = null;
		if(tipoTerminoGeneralHelper != null){
			tipoTerminoGeneral = new mx.gob.imss.digital.modelo.domicilio.TipoTerminoGeneral();
			super.copyBeans(tipoTerminoGeneralHelper, tipoTerminoGeneral);
		}		
		return tipoTerminoGeneral;
	}

	private mx.gob.imss.digital.modelo.domicilio.TipoVialidad transformaTipoVialidad(
			TipoVialidad tipoVialidadHelper) throws IllegalAccessException, InvocationTargetException{	
		mx.gob.imss.digital.modelo.domicilio.TipoVialidad tipoVialidad = null;
		if(tipoVialidadHelper != null){
			tipoVialidad = new mx.gob.imss.digital.modelo.domicilio.TipoVialidad();
			super.copyBeans(tipoVialidadHelper, tipoVialidad);			
		}
		return tipoVialidad;
	}
	
	private mx.gob.imss.digital.modelo.domicilio.TipoAmbito transformaTipoAmbito(TipoAmbito tipoAmbitoHelper) 
			throws IllegalAccessException, InvocationTargetException{
		mx.gob.imss.digital.modelo.domicilio.TipoAmbito tipoAmbito = null;
		if(tipoAmbitoHelper !=null 
				&& tipoAmbitoHelper.getClave() !=null){
			tipoAmbito = new mx.gob.imss.digital.modelo.domicilio.TipoAmbito();
			super.copyBeans(tipoAmbitoHelper, tipoAmbito);
		}
		return tipoAmbito;
	}
	
	
	private mx.gob.imss.digital.modelo.domicilio.TipoAsentamiento transformaTipoAsentamiento(TipoAsentamiento tipoAsentamientoHelper) 
			throws IllegalAccessException, InvocationTargetException{
		mx.gob.imss.digital.modelo.domicilio.TipoAsentamiento tipoAsentamiento = null;
		if(tipoAsentamientoHelper !=null 
				&& tipoAsentamientoHelper.getClave() !=null){
			tipoAsentamiento = new mx.gob.imss.digital.modelo.domicilio.TipoAsentamiento();
			super.copyBeans(tipoAsentamientoHelper, tipoAsentamiento);
		}
		return tipoAsentamiento;
	}

	
	private Municipio transformaMunicipio(mx.gob.imss.digital.modelo.domicilio.Municipio 
			municipioHelper) throws IllegalAccessException, InvocationTargetException{
		Municipio municipio = null;
		if(municipioHelper != null 
				&& municipioHelper.getClave()!=null){
			municipio = new Municipio(); 
			//Preparar EntidadFederativa
			EntidadFederativa entidadFederativa 
				= transformaEntidadFederativa(municipioHelper.getEntidadFederativa());
			municipioHelper.setEntidadFederativa(null);
			super.copyBeans(municipioHelper, municipio);
			municipio.setEntidadFederativa(entidadFederativa);
		}
		return municipio;
	}
		
	private EntidadFederativa transformaEntidadFederativa(mx.gob.imss.digital.modelo.domicilio.EntidadFederativa 
			entidadFederativaHelper) throws IllegalAccessException, InvocationTargetException{
		EntidadFederativa entidadFederativa = null;
		if(entidadFederativaHelper != null 
				&& entidadFederativaHelper.getClave()!=null){
			entidadFederativa = new EntidadFederativa();			
			super.copyBeans(entidadFederativaHelper, entidadFederativa);
		}
		return entidadFederativa;
	}
	
	
	
	// IMSS DIGITAL MODELO A IMSS MODELO NEGOCIO
	public mx.gob.imss.ctirss.delta.model.domicilio.Domicilio transformarDomicilioImssDigitalAModeloNegocio(Domicilio domicilioImssDigital){
		mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domicilioModelo = null;
		try {
			if(domicilioImssDigital!=null){			
				domicilioModelo = new mx.gob.imss.ctirss.delta.model.domicilio.Domicilio();
				
				//Preparar Localidad
				Localidad localidad 
					= transformaLocalidadAModelo(domicilioImssDigital.getLocalidad());
				//Preparar Asentamiento
				Asentamiento asentamiento 
					= transformaAsentamientoAModelo(domicilioImssDigital.getAsentamiento());					
				//Preparar Camino
				DomicilioCamino camino 
					= transformaCamino(domicilioImssDigital.getCamino());
				//Preparar Carretera
				DomicilioCarretera carretera 
					= transformaCarretera(domicilioImssDigital.getCarretera());
				//Preparar TipoDomicilio
				TipoDomicilio tipoDomicilio 
					= transformaTipoDomicilioAModelo(domicilioImssDigital.getTipoDomicilio());				
				//Preparar VialidadPrimaria
				Vialidad vialidadPrimaria 
					= transformaVialidad(domicilioImssDigital.getVialidadPrimaria());				
				//Preparar VialidadReferenciaPosterior
				Vialidad vialidadReferenciaPosterior 
					= transformaVialidad(domicilioImssDigital.getVialidadReferenciaPosterior());				
				//Preparar VialidadReferenciaPrimaria
				Vialidad vialidadReferenciaPrimaria 
					= transformaVialidad(domicilioImssDigital.getVialidadReferenciaPrimaria());				
				//Preparar VialidadReferenciaSecundaria
				Vialidad vialidadReferenciaSecundaria 
					= transformaVialidad(domicilioImssDigital.getVialidadReferenciaSecundaria());				
				//Preparar CP
				String codigoPostal = null;
				if(domicilioImssDigital.getCodigoPostal() != null)
					codigoPostal = domicilioImssDigital.getCodigoPostal();
				
				//Eliminar referencias
				domicilioImssDigital.setLocalidad(null);
				domicilioImssDigital.setAsentamiento(null);
				domicilioImssDigital.setCamino(null);
				domicilioImssDigital.setCarretera(null);
				domicilioImssDigital.setTipoDomicilio(null);
				domicilioImssDigital.setVialidadPrimaria(null);
				domicilioImssDigital.setVialidadReferenciaPosterior(null);
				domicilioImssDigital.setVialidadReferenciaPrimaria(null);
				domicilioImssDigital.setVialidadReferenciaSecundaria(null);
				domicilioImssDigital.setCodigoPostal(null);				
																		
				super.copyBeans(domicilioImssDigital, domicilioModelo);
				
				domicilioModelo.setLocalidad(localidad);
				domicilioModelo.setAsentamiento(asentamiento);
				domicilioModelo.setDomicilioCamino(camino);
				domicilioModelo.setDomicilioCarretera(carretera);
				domicilioModelo.setTipoDomicilio(tipoDomicilio);
				domicilioModelo.setVialidadPrimaria(vialidadPrimaria);
				domicilioModelo.setVialidadReferenciaPosterior(vialidadReferenciaPosterior);
				domicilioModelo.setVialidadReferenciaPrimaria(vialidadReferenciaPrimaria);
				domicilioModelo.setVialidadReferenciaSecundaria(vialidadReferenciaSecundaria);
				domicilioModelo.setCodigoPostal(new CodigoPostal());
				domicilioModelo.getCodigoPostal().setCodigoPostal(codigoPostal);
				
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return domicilioModelo;
	}
	
	private Localidad transformaLocalidadAModelo(
			mx.gob.imss.digital.modelo.domicilio.Localidad localidadHelper)throws IllegalAccessException, InvocationTargetException{
		
		Localidad localidad = null;
		if(localidadHelper != null 
				&& localidadHelper.getClave()!=null){
			localidad = new Localidad();
			//Preparar Municipio
			Municipio municipio 
				= transformaMunicipioAModelo(localidadHelper.getMunicipio());
			localidadHelper.setMunicipio(null);			
			super.copyBeans(localidadHelper, localidad);
			localidad.setMunicipio(municipio);
		}
		return localidad;
	}
	
	private Municipio transformaMunicipioAModelo(
			mx.gob.imss.digital.modelo.domicilio.Municipio municipioHelper)throws IllegalAccessException, InvocationTargetException{
		Municipio municipio = null;
		if(municipioHelper != null 
				&& municipioHelper.getClave()!=null){
			municipio = new Municipio(); 
			//Preparar EntidadFederativa
			EntidadFederativa entidadFederativa 
				= transformaEntidadFederativaAModelo(municipioHelper.getEntidadFederativa());
			municipioHelper.setEntidadFederativa(null);
			super.copyBeans(municipioHelper, municipio);
			municipio.setEntidadFederativa(entidadFederativa);
		}
		return municipio;
	}
	
	private EntidadFederativa transformaEntidadFederativaAModelo(
			mx.gob.imss.digital.modelo.domicilio.EntidadFederativa entidadFederativaHelper)throws IllegalAccessException, InvocationTargetException{
		EntidadFederativa entidadFederativa = null;
		if(entidadFederativaHelper != null 
				&& entidadFederativaHelper.getClave()!=null){
			entidadFederativa = new EntidadFederativa();
			
			super.copyBeans(entidadFederativaHelper, entidadFederativa);
		}
		return entidadFederativa;
	}
	
	private Asentamiento transformaAsentamientoAModelo(
			mx.gob.imss.digital.modelo.domicilio.Asentamiento asentamientoHelper)throws IllegalAccessException, InvocationTargetException{
		
		Asentamiento asentamiento = null;
		if(asentamientoHelper != null 
				&& asentamientoHelper.getClave()!=null){
			asentamiento = new Asentamiento();
			//Preparar Localidad
			Localidad localidad 
				= transformaLocalidadAModelo(asentamientoHelper.getLocalidad());
			//Preparar Municipio
			Municipio municipio 
				= transformaMunicipioAModelo(asentamientoHelper.getMunicipio());
			//Preparar TipoAsentamiento
			TipoAsentamiento tipoAsentamiento 
				= transformaTipoAsentamientoAModelo(asentamientoHelper.getTipoAsentamiento());
			//Preparar Municipio
			String codigoPostal = null;
			if(asentamientoHelper.getCodigoPostal() != null)
				codigoPostal = asentamientoHelper.getCodigoPostal();
			
			asentamientoHelper.setTipoAsentamiento(null);
			asentamientoHelper.setLocalidad(null);
			asentamientoHelper.setMunicipio(null);		
			asentamientoHelper.setCodigoPostal(null);
			super.copyBeans(asentamientoHelper, asentamiento);
			asentamiento.setTipoAsentamiento(tipoAsentamiento);
			asentamiento.setLocalidad(localidad);
			asentamiento.setMunicipio(municipio);
			asentamiento.setCodigoPostal(new CodigoPostal());
			asentamiento.getCodigoPostal().setCodigoPostal(codigoPostal);
			
		}
		return asentamiento;
	}
	
	private TipoAsentamiento transformaTipoAsentamientoAModelo(mx.gob.imss.digital.modelo.domicilio.TipoAsentamiento tipoAsentamientoHelper) 
			throws IllegalAccessException, InvocationTargetException{
		TipoAsentamiento tipoAsentamiento = null;
		if(tipoAsentamientoHelper !=null 
				&& tipoAsentamientoHelper.getClave() !=null){
			tipoAsentamiento = new TipoAsentamiento();
			super.copyBeans(tipoAsentamientoHelper, tipoAsentamiento);
		}
		return tipoAsentamiento;
	}

	private DomicilioCamino transformaCamino(Camino caminoHelper) 
			throws IllegalAccessException, InvocationTargetException{
		
		DomicilioCamino camino = null;
		if(caminoHelper != null){
			camino = new DomicilioCamino();
			//Preparar TipoTerminoGeneral y TipoMargen
			TipoTerminoGeneral tipoTerminoGeneral 
				= transformaTipoTerminoGeneralAModelo(caminoHelper.getTerminoGeneral());
			TipoMargen tipoMargen 
				= transformaTipoMargenAModelo(caminoHelper.getMargen());
			//Eliminar referencias
			caminoHelper.setTerminoGeneral(null);
			caminoHelper.setMargen(null);			
			super.copyBeans(caminoHelper, camino);
			camino.setTerminoGeneral(tipoTerminoGeneral);
			camino.setMargen(tipoMargen);
		}
		return camino;
	}
	
	private TipoTerminoGeneral transformaTipoTerminoGeneralAModelo(
			mx.gob.imss.digital.modelo.domicilio.TipoTerminoGeneral tipoTerminoGeneralHelper)throws IllegalAccessException, InvocationTargetException{		
		TipoTerminoGeneral tipoTerminoGeneral = null;
		if(tipoTerminoGeneralHelper != null){
			tipoTerminoGeneral = new TipoTerminoGeneral();
			super.copyBeans(tipoTerminoGeneralHelper, tipoTerminoGeneral);
		}		
		return tipoTerminoGeneral;
	}
	
	private TipoMargen transformaTipoMargenAModelo(
			mx.gob.imss.digital.modelo.domicilio.TipoMargen tipoMargenHelper)throws IllegalAccessException, InvocationTargetException{		
		TipoMargen tipoMargen = null;
		if(tipoMargenHelper != null){
			tipoMargen = new TipoMargen();
			super.copyBeans(tipoMargenHelper, tipoMargen);
		}		
		return tipoMargen;
	}
	
	private DomicilioCarretera transformaCarretera(
			Carretera carreteraHelper)throws IllegalAccessException, InvocationTargetException{
		
		DomicilioCarretera carretera = null;
		if(carreteraHelper!=null){
			carretera = new DomicilioCarretera();		
			//Preparar TipoTerminoGeneral,TipoDerechoTransito y TipoAdministracion
			TipoTerminoGeneral tipoTerminoGeneral 
				= transformaTipoTerminoGeneralAModelo(carreteraHelper.getTerminoGeneral());
			TipoDerechoTransito tipoDerechoTransito 
				= transformaTipoDerechoTransitoAModelo(carreteraHelper.getDerechoTransito());
			TipoAdministracion tipoAdministracion 
				= transformaTipoAdministracionAModelo(carreteraHelper.getAdministracion());
			//Eliminar referencias
			carreteraHelper.setTerminoGeneral(null);
			carreteraHelper.setDerechoTransito(null);
			carreteraHelper.setAdministracion(null);			
			super.copyBeans(carreteraHelper, carretera);
			carretera.setTerminoGeneral(tipoTerminoGeneral);
			carretera.setDerechoTransito(tipoDerechoTransito);
			carretera.setAdministracion(tipoAdministracion);
		}
		return carretera;
	}
	
	private TipoDerechoTransito transformaTipoDerechoTransitoAModelo(
			mx.gob.imss.digital.modelo.domicilio.TipoDerechoTransito tipoDerechoTransitoHelper)throws IllegalAccessException, InvocationTargetException{		
		TipoDerechoTransito tipoDerechoTransito = null;
		if(tipoDerechoTransitoHelper != null){
			tipoDerechoTransito = new TipoDerechoTransito();
			super.copyBeans(tipoDerechoTransitoHelper, tipoDerechoTransito);
		}		
		return tipoDerechoTransito;
	}
	
	private TipoAdministracion transformaTipoAdministracionAModelo(
			mx.gob.imss.digital.modelo.domicilio.TipoAdministracion tipoAdministracionHelper)throws IllegalAccessException, InvocationTargetException{		
		TipoAdministracion tipoAdministracion = null;
		if(tipoAdministracionHelper != null){
			tipoAdministracion = new TipoAdministracion();
			super.copyBeans(tipoAdministracionHelper, tipoAdministracion);
		}		
		return tipoAdministracion;
	}
	
	private TipoDomicilio transformaTipoDomicilioAModelo(
			mx.gob.imss.digital.modelo.domicilio.TipoDomicilio tipoDomicilioHelper){		
		
		TipoDomicilio tipoDomicilio = null;
		if(tipoDomicilioHelper !=null ){
			tipoDomicilio = new TipoDomicilio();
			tipoDomicilio.setClave(Long.valueOf(tipoDomicilioHelper.getIdTipoDomicilio()).intValue());
			tipoDomicilio.setDescripcion(tipoDomicilioHelper.getDescripcion());
		}
		return tipoDomicilio;
	}
	
	private Vialidad transformaVialidad(mx.gob.imss.digital.modelo.domicilio.Vialidad vialidadHelper) 
			throws IllegalAccessException, InvocationTargetException{		
		
		Vialidad vialidad = null;
		if(vialidadHelper != null && vialidadHelper.getClave() != null){
			vialidad = new Vialidad();
			//Preparar TipoVialidad
			TipoVialidad tipoVialidad = 
				transformaTipoVialidadAModelo(vialidadHelper.getTipoVialidad());
			vialidadHelper.setTipoVialidad(null);
			super.copyBeans(vialidadHelper, vialidad);
			vialidad.setTipoVialidad(tipoVialidad);
		}
		return vialidad;		
	}
	
	private TipoVialidad transformaTipoVialidadAModelo(
			mx.gob.imss.digital.modelo.domicilio.TipoVialidad tipoVialidadHelper) throws IllegalAccessException, InvocationTargetException{	
		TipoVialidad tipoVialidad = null;
		if(tipoVialidadHelper != null){
			tipoVialidad = new TipoVialidad();
			super.copyBeans(tipoVialidadHelper, tipoVialidad);			
		}
		return tipoVialidad;
	}
}
