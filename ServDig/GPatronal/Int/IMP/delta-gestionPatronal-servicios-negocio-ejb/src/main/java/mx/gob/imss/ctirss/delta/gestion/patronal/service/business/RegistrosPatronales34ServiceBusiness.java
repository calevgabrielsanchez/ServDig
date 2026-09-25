package mx.gob.imss.ctirss.delta.gestion.patronal.service.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.domicilio.MunicipioImssNoLocalizadoException;
import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.RegistrosPatronales34ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.clasificacion.actividad.economica.ActividadEcServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.RegistrosPatronales34UtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ParametrosServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.MunicipioIMSS;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;
import mx.gob.imss.digital.modelo.patron.RegistroPatronal;
import mx.gob.imss.digital.modelo.persona.Persona;

import org.springframework.util.CollectionUtils;

@Stateless(name="registrosPatronales34ServiceBusiness" ,mappedName="registrosPatronales34ServiceBusiness")
public class RegistrosPatronales34ServiceBusiness 
	extends AbstractServiceBusiness implements	RegistrosPatronales34ServiceBusinessRemote{

	@EJB
    private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;
	@EJB
	private RegistrosPatronales34UtilityLocal registrosPatronales34Utility;
	@EJB
	private DomicilioServiceBusinessRemote domicilioServiceBusiness;
	@EJB 
	private ParametrosServiceBusinessRemote parametrosBusiness;
	@EJB
	private ActividadEcServiceRemote clasificacionService;
	
	
	@Override
	public Persona obtenerNRPs34(Persona persona) {
		SujetoObligado sujetoObligado = new SujetoObligado();		
		try {
			//Obtener Sujeto Obligado
			obtenerSujetoObligado(sujetoObligado, persona);
			//Obtener solo RPs Modalidad 34
			sujetoObligado.setIdsModalidadesConsulta(registrosPatronales34Utility.getModalidad34());			
			//Obtener RPs y transformarlos
			List<SujetoObligado> listaSujetosObligados = sujetoObligadoServiceBusiness
				.obtenerDetalleSujetoObligado(sujetoObligado);			
			if (!CollectionUtils.isEmpty(listaSujetosObligados)) {
				registrosPatronales34Utility
					.transformarSujetosObligados(persona, listaSujetosObligados);
			}
		} catch (AbstractException e) {
			log.error(e);
			e.printStackTrace();			
		}		
		return persona;
	}
	
	@Override
	public RegistroPatronal obtenerNRPDomesticoXDomicilioCT(Persona persona, Domicilio domicilio)
		throws GestionPatronalBusinessException {
	
		RegistroPatronal registroPatronal=null;
		try {			
			if(persona != null && persona.getRfc() != null 
				&& domicilio != null 
				&& domicilio.getCodigoPostal() != null 
				&& domicilio.getAsentamiento() != null 
				&& domicilio.getAsentamiento().getMunicipio() != null
				&& domicilio.getAsentamiento().getMunicipio().getClave() != null
				&& domicilio.getAsentamiento().getMunicipio().getEntidadFederativa() != null
				&& domicilio.getAsentamiento().getMunicipio().getEntidadFederativa().getClave() != null){
				
				//Transformar objeto municipio
				Municipio objMunicipio = registrosPatronales34Utility
					.getMunicipioNegocio(domicilio.getAsentamiento().getMunicipio());
				//Obtener Municipios IMSS con base a (Estado, Municipio y CP)
				List<MunicipioIMSS> listaMunicipiosIMSS =	domicilioServiceBusiness
						.getMunicipioIMSSbyEstadoMunCP(objMunicipio, domicilio.getCodigoPostal());
				if (!CollectionUtils.isEmpty(listaMunicipiosIMSS)) {
					Long idFraccionDomestico = getIdFraccionDomestico();
					for(MunicipioIMSS municipioIMSS : listaMunicipiosIMSS){
						//Obtener RP con base al RFC, Tipo Persona, MunicipioIMSS y Fraccion Domestico
						registroPatronal = registrosPatronales34Utility.transformarSujetoToRP(
							sujetoObligadoServiceBusiness.obtenerRegistroPatronalEnMunicipioIMSSPorFraccion(
								persona.getRfc(), TipoPersona.FISICA.longValue(), 
									Long.valueOf(municipioIMSS.getIdMunicipio()), idFraccionDomestico, null));
						
						//Si se encontro RP, retornarlo
						if(registroPatronal != null)
							break;
						
					}
				}
			}
		} catch (MunicipioImssNoLocalizadoException e) {
			throw new GestionPatronalBusinessException(e.getMessage());
		}
		return registroPatronal;
	}
	
	
	private Long getIdFraccionDomestico(){
		Long idFraccion = 0L;
		try {
			String fraccionDomesticos = parametrosBusiness.obtenerParametroDeConfiguracion("CVE_FRACCION_DOMESTICOS");
			if(fraccionDomesticos!=null){
				Fraccion fraccion = clasificacionService
					.obtenerFraccionPornumFraccionCompleta(fraccionDomesticos);
				if(fraccion!=null){
					idFraccion = fraccion.getId();
					log.debug("IdFraccionDomestico " +  idFraccion);
				}
			}
		} catch (AbstractException ae) {
			log.error(ae);
			ae.printStackTrace();
		}
		return idFraccion;
	}
	

	private void obtenerSujetoObligado(SujetoObligado sujetoObligado, Persona persona){
		if(persona.getRfc().trim().length() > 12){
			Fisica fisica = new Fisica();
			fisica.setRfc(persona.getRfc());
			fisica.setIdPersona(persona.getIdPersona());
			sujetoObligado.setFisica(fisica);	
			sujetoObligado.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
		}else{
			Moral moral = new Moral();
			moral.setRfc(persona.getRfc());
			moral.setIdPersona(persona.getIdPersona());
			sujetoObligado.setMoral(moral);
			sujetoObligado.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
		}
		//Indicador de fltro (La busqueda se realizara por rFC)
		sujetoObligado.setFiltroPorIdPersona(false);
		log.debug("Filtros Busqueda RPs " + sujetoObligado.toString());
	}
	
	
}
