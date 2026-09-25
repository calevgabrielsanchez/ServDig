package mx.gob.imss.ctirss.delta.gestion.patronal.service.business;

import java.math.BigDecimal;
import java.util.Date;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.exception.gestion.patronal.RepresentanteLegalInvalidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.patronal.RepresentanteLegalYaExisteException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.DiferenciasRENAPOContraSAT;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.IndividuoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rep.legal.RepresentanteLegalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.test.EJBLocator;
import mx.gob.imss.ctirss.delta.model.enums.EstatusPersona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoAccionAfectacionEnum;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

public class RegistrodeEmpresaTest {
    private static final Logger log = LoggerFactory.getLogger(RegistrodeEmpresaTest.class);

    private IndividuoServiceBusinessRemote individuoServiceBusiness;
    private PersonaBusinessRemote personaBusiness;
    private RepresentanteLegalServiceBusinessRemote representanteLegalServiceBusinessRemote;

    @Before
    public void before() throws NamingException {
    	individuoServiceBusiness = EjbLocator.getIndividuoServiceBusiness();
        log.debug("::: Obtuve servicio EJB: {}", individuoServiceBusiness);
        personaBusiness = EjbLocator.getPersonaBusinessRemote();
        log.debug("::: Obtuve servicio EJB: {}", personaBusiness);
        representanteLegalServiceBusinessRemote = EjbLocator.getRLService();
        log.debug("::: Obtuve servicio EJB: {}", representanteLegalServiceBusinessRemote);
    }

    
	@Test
	public void localizarPM() {
		try {
			log.debug("::: Obteniendo EJB - " + new Date());
			IndividuoServiceBusinessRemote ejb = EJBLocator.getIndividuoServiceBusiness();
			Persona p = new Persona();
			p.setRfc("NWM9709244W4");
			boolean anterior = false;
			// NWM9709244W4 - 588596
			// IKU210623IE4 - 5430758
			log.debug("::: Buscando PM");
			Moral m = null;
			if (anterior) {
				log.debug("::: Consultando por metodo anterior, " + new Date());
				m = (Moral) ejb.consultarPersonaMoralIMSSPorRFC(p);
			} else {
				log.debug("::: Consultando por metodo actual, " + new Date());
				m = (Moral) ejb.consultarPersonaMoralIMSSPorRFC_AP(p);
			}

			System.out.println(m);
			log.debug("::: FIN - " + new Date());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}    
    
    
    
    @Test
    public void registrarEmpresaValidacion() {

    	log.debug("::: Iniciando validacion para registro de empresa representada");
        Persona resultado;
        
        //Datos de la empresa a repesentar
        Fisica oForm = new Fisica();
        oForm.setRfc("GER201218SW8"); //RFC de la PF o la PM
        oForm.setTipoPersona(new TipoPersona());
        oForm.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL); //Tipo de persona fisica o moral
        
        //Datos del RL, no puede representarse a si mismo
        Fisica datosPersonales = new Fisica();
        datosPersonales.setIdPersona(new Long("83610132"));
        
        try {
            resultado = this.getErroresNegocio(datosPersonales, oForm);
        } catch (Exception e) {
        	log.debug("Ocurrio un error al registrar el RFC: " + oForm.getRfc() + " [ " +e.getMessage() + " ]");
        	e.printStackTrace();
            resultado = new Persona();
            resultado.setErrorFormGeneral("Ocurrio un error inesperado");
        }
    	
        System.out.println("Resultado: ");
        System.out.println(resultado);

    }
    
	private Persona getErroresNegocio(Fisica datosPersonales, Fisica busquedaPersona) {
		EstatusPersona estatusPersonaARepresentar = EstatusPersona.Existe_en_bdtu;
		Persona resultado = new Persona();
		Persona personaEncontrada = null;
		
		try {			
			if(busquedaPersona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
				personaEncontrada = individuoServiceBusiness.consultarPersonaFisicaIMSSPorRFC(busquedaPersona);
				log.debug("::: Id de la persona encontrada: " + personaEncontrada.getIdPersona());
				personaEncontrada.setTipoPersona(new TipoPersona());
				personaEncontrada.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
			}else if(busquedaPersona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_MORAL)){
				personaEncontrada = individuoServiceBusiness.consultarPersonaMoralIMSSPorRFC(busquedaPersona);
				log.debug("::: Id de la persona encontrada: " + personaEncontrada.getIdPersona());
				personaEncontrada.setTipoPersona(new TipoPersona());
				personaEncontrada.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
			}
		} catch (PersonasNoLocalizadasException e1) {
			estatusPersonaARepresentar = EstatusPersona.Inexistente;
			log.error("El RFC no se encuentra registrado dn BDTU, se buscar� en el SAT");
			try{
				if(busquedaPersona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
					personaEncontrada = personaBusiness.buscarPersonaFisicaPorRfcEnSat(busquedaPersona.getRfc());
					if(personaEncontrada==null){
						resultado.setErrorFormGeneral("El RFC no se encuentra registrado ante el IMSS ni ante el SAT, " +
							"por favor verifique la informaci�n y vuelva a intentarlo");
						return resultado;
					}
					personaEncontrada.setTipoPersona(new TipoPersona());
					personaEncontrada.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
				}else if(busquedaPersona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_MORAL)){
					personaEncontrada = personaBusiness.buscarPersonaMoralPorRfcEnSat(busquedaPersona.getRfc());
					if(personaEncontrada==null){
						resultado.setErrorFormGeneral("El RFC no se encuentra registrado ante el IMSS ni ante el SAT, " +
							"por favor verifique la informaci�n y vuelva a intentarlo");
						return resultado;
					}
					personaEncontrada.setTipoPersona(new TipoPersona());
					personaEncontrada.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
				}
			}catch(ClienteWebserviceSatRfcException cwsException){
				cwsException.printStackTrace();
				resultado.setErrorFormGeneral("El RFC no se encuentra registrado ante el IMSS ni ante el SAT, " +
					"por favor verifique la informaci�n y vuelva a intentarlo");
				return resultado;
			}			
		} catch (ClienteWebserviceRenapoCurpException e) {
			resultado.setErrorFormGeneral(e.getMessage());
			e.printStackTrace();
			return resultado;
		} catch (ClienteWebserviceSatRfcException e) {
			resultado.setErrorFormGeneral(e.getMessage());
			e.printStackTrace();
			return resultado;
		} catch (ErrorComparacionDatosRENAPOException e) {
			resultado.setErrorFormGeneral(e.getMessage());
			e.printStackTrace();
			return resultado;
		} catch (ErrorComparacionDatosSATException e) {
			resultado.setErrorFormGeneral(e.getMessage());
			e.printStackTrace();
			return resultado;
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			resultado.setErrorFormGeneral(e.getMessage());
			e.printStackTrace();
			return resultado;
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			resultado.setErrorFormGeneral(e.getMessage());
			e.printStackTrace();
			return resultado;
		} catch (DiferenciasRENAPOContraSAT e) {
			resultado.setErrorFormGeneral(e.getMessage());
			e.printStackTrace();
			return resultado;
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			resultado.setErrorFormGeneral(e.getMessage());
			e.printStackTrace();
			return resultado;
		} catch (PersonaSinCalificacionesException e) {
			resultado.setErrorFormGeneral(e.getMessage());
			e.printStackTrace();
			return resultado;
		}
		
		//Verificamos que se haya encontrado algun patron sujeto obligado en caso contrario se muestra mensaje de error
		if(personaEncontrada != null) {
				//SE crea un objeto representante legal para saber si ya existe la relacion
				RepresentanteLegal representante = new RepresentanteLegal();
				representante.setCveIdPersona(personaEncontrada.getIdPersona());
				representante.setPersonaFisica(datosPersonales);
				representante.setTipoPersonaRepresentada(personaEncontrada.getTipoPersona());
				representante.setAccion(TipoAccionAfectacionEnum.AGREGAR);
				representante.setIndActAdmonDominio(BigDecimal.ONE);//Por defecto se agregan actos de administraci�n y dominio
				//Verificamos que no exista la relacion como epresentante
				try {
					if(!estatusPersonaARepresentar.equals(EstatusPersona.Inexistente))//Si la persona no existe la validaci�n no se aplica
						representanteLegalServiceBusinessRemote.validaExisteRepresentanteLegal(representante);
				} catch (RepresentanteLegalInvalidoException e) {
					e.printStackTrace();
					log.error("::: Mensaje de error: " + e.getMessage());
					resultado.setErrorFormGeneral("Ocurrio un error inesperado");
					return resultado;
				} catch (RepresentanteLegalYaExisteException e) {
					e.printStackTrace();
					log.error("::: Mensaje de error: " + e.getMessage());
					resultado.setErrorFormGeneral("Ya existe la relacion como representante legal");
					return resultado;
				}				
				if(personaEncontrada.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)) {
					Fisica perFisica = new Fisica();
					Fisica fisicaEncontrada = (Fisica)personaEncontrada;
					perFisica.setCurp(fisicaEncontrada.getCurp());					
					perFisica.setRfc(fisicaEncontrada.getRfc());
					perFisica.setNombre(fisicaEncontrada.getNombre().trim() + " " +
							(fisicaEncontrada.getPrimerApellido() != null ? fisicaEncontrada.getPrimerApellido() : "")+ " " + 
							(fisicaEncontrada.getSegundoApellido() != null ? fisicaEncontrada.getSegundoApellido() : "") );
					
					return perFisica;
				} else {
					Moral perMoral = new Moral();
					Moral moralEncontrada = (Moral)personaEncontrada;	
					log.debug("Moral encontrada: " + moralEncontrada.getIdPersona() + " - " + moralEncontrada.getRazonSocial());

					//perMoral.setRazonSocial(moralEncontrada.getRazonSocial().replace("\"", "\\\""));
					if(moralEncontrada.getRazonSocial() != null) {
						perMoral.setRazonSocial(moralEncontrada.getRazonSocial().replace("\"", "\\\""));
					}else {
						Moral personaEncontradaSat = null;
						Moral moralEncontradaSat = null;	
						try {
							personaEncontradaSat = personaBusiness.buscarPersonaMoralPorRfcEnSat(busquedaPersona.getRfc());
						} catch (ClienteWebserviceSatRfcException e) {
							e.printStackTrace();
							resultado.setErrorFormGeneral("Error al obtener el RFC ante el SAT, " +
								"por favor verifique la información y vuelva a intentarlo");
							return resultado;
						}
						moralEncontradaSat = (Moral)personaEncontradaSat;	
						log.debug("moralEncontradaSat.getRazonSocial(): " + moralEncontradaSat.getRazonSocial());
						perMoral.setRazonSocial(moralEncontradaSat.getRazonSocial().replace("\"", "\\\""));
					}
										
					perMoral.setTipoSociedad(moralEncontrada.getTipoSociedad());					
					perMoral.setRfc(moralEncontrada.getRfc());	
					
					return perMoral;
				}
		}
		resultado.setErrorFormGeneral("El RFC no se encuentra registrado ante el IMSS");
		return resultado;
	}    
    

}

