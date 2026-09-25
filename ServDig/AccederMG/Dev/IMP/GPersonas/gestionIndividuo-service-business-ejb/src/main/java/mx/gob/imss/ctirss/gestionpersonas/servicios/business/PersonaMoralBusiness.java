package mx.gob.imss.ctirss.gestionpersonas.servicios.business;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;

import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ComparacionSinDiferenciasException;
import mx.gob.imss.ctirss.delta.exception.individuo.DatosInsuficientesICAException;
import mx.gob.imss.ctirss.delta.exception.individuo.DatosInsuficientesModificacionException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaMoralNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.identificador.IdentificadoresNoExistentesException;
import mx.gob.imss.ctirss.delta.exception.individuo.identificador.PersonaSinIdentificadoresException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.NumeroMaximoResultadosSuperadoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.IdentificadoresPersonaMoralServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.IndividuoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaMoralEnSATServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.utility.CompararPersonaMoralEntidadExternaUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.enums.CambioComparacionEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoAdministracionEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoContactoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoIdentificadorEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.AfectarDatosPersonaWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Calificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CalificacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.DatosPersonaSAT;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosConsulta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Identificador;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.SituacionSAT;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoIdentificador;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSociedad;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCambioInformacionPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;
import mx.gob.imss.ctirss.gestionpersonas.servicios.entity.PersonaMoralConversor;
import mx.gob.imss.ctirss.gestionpersonas.servicios.entity.PersonaMoralEntityLocal;
import mx.gob.imss.ctirss.gestionpersonas.servicios.entity.ValidacionesComunes;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessLocal;

@Stateless(name = "personaMoralBusiness", mappedName = "personaMoralBusiness")
public class PersonaMoralBusiness extends AbstractServiceBusiness implements PersonaMoralBusinessRemote, PersonaMoralBusinessLocal {

    @EJB
    private PersonaMoralEntityLocal personaMoralEntity;
    
    @EJB
    private ComponentesExternosBusinessLocal componentesExternosBusiness;
    
    @EJB
    private ServiciosPersonaBusinessLocal personaBusiness;
    
    @EJB
    private LocalizarPersonaMoralEnSATServiceBusinessRemote localizarPersonaMoralEnSATServiceBusiness;
    
    @EJB
    private CompararPersonaMoralEntidadExternaUtilityLocal compararPersonaMoralEntidadExternaUtility;
    
    @EJB
    private IdentificadoresPersonaMoralServiceBusinessRemote identificadoresPersonaMoralServiceBusiness;
    
    @EJB
    private AfectarDatosPersonaBusinessRemote afectarDatosPersonaBusiness;
    
    @EJB
    private AfiliacionServiceBusinessRemote afiliacionServiceBusiness;
    
	@EJB
	IndividuoServiceBusinessRemote individuoServiceBusiness;

    @Override
    public Moral getPersonaMoral_AP(final Long idPersonaMoral) {
    	log.debug("::: Buscando la persona Moral en BDTU, PersonaMoralBusiness.getPersonaMoral_AP");
        Moral personaMoral = null;
        if (idPersonaMoral != null) {
            Moral personaMoralParam = new Moral();
            personaMoralParam.setIdPersona(idPersonaMoral);
            List<Moral> personas = personaMoralEntity.buscarPersonaMoral_AP(personaMoralParam);            
            if (!personas.isEmpty()) {
                personaMoral = personas.get(0);
            }
        }
        return personaMoral;
    }

    @Override
    public Moral getPersonaMoral(final Long idPersonaMoral) {
    	log.debug("::: Buscando la persona Moral en BDTU, PersonaMoralBusiness.getPersonaMoral");
        Moral personaMoral = null;
        if (idPersonaMoral != null) {
            Moral personaMoralParam = new Moral();
            personaMoralParam.setIdPersona(idPersonaMoral);
            List<Moral> personas = personaMoralEntity.buscarPersonaMoral(personaMoralParam);
            
            if (!personas.isEmpty()) {
                personaMoral = personas.get(0);
            }
        }
        return personaMoral;
    }

    public Moral getDatosComplementariosPersonaMoral(Long idPersona){
    	Moral persona = new Moral();
    	persona.setIdPersona(idPersona);
    	componentesExternosBusiness.getDomiciliosPersona(persona);
    	componentesExternosBusiness.getMediosContactoPersona(persona);
    	return persona;
    }
    
    @Override
    public List<Moral> getPersonaMoral(Moral personaMoral) {
        return personaMoralEntity.buscarPersonaMoral(personaMoral);
    }

    @Override
    public Moral altaPersonaMoral(Moral personaMoral) {
        Moral personaMoralResultado = null;
        
        //VERIFICA SI LA PERSONA MORAL NO VIENE NULA
        if (personaMoral != null) {
        	
            //VERIFICA QUE NO SE INTENTE DAR DE ALTA UNA PERSONA MORAL YA REGISTRADA EN BD
            if (personaMoral.getIdPersona() == null) {
                
            	try{
            		
                    //CONVIERTE A MAYUSCULAS LOS ATRIBUTOS DE LA PERSONA MORAL
                    convertirMayusculas(personaMoral);
                    
            		//ALTA DE DOMICILIOS ASIGNADOS A LA PERSONA
            		componentesExternosBusiness.altaDomicilios(personaMoral);
                	
            		//ALTA DE MEDIOS DE CONTACTO ASIGNADOS A LA PERSONA
            		componentesExternosBusiness.altaMediosContacto(personaMoral);

                	DitPersonaMoral ditPersonaMoral = PersonaMoralConversor.convertirPersonaMoralToEntity(personaMoral);
                    personaMoralResultado = PersonaMoralConversor.convertirEntityToPersonaMoral(personaMoralEntity.altaPersonaMoral(ditPersonaMoral));
                	
            	}
            	catch(Exception e){
            		e.printStackTrace();
            	}
            	
            } else {
                log.warn("No se puede guardar esta persona, debido a que su ID no es nulo");
            }
        }
        return personaMoralResultado;
    }
    
    @Override
    public List<Moral> buscarPersonaMoralPorRfcEnImss(String rfc) {
    	log.debug("::: Buscando en PersonaMoralBusiness.buscarPersonaMoralPorRfcEnImss");
        List<Moral> response = null;
        if (!StringUtils.isBlank(rfc)) {
            Moral persona = new Moral();
            persona.setRfc(rfc);
            
            //CONVIERTE A MAYUSCULAS LOS ATRIBUTOS DE LA PERSONA MORAL
            convertirMayusculas(persona);
            
            List<Moral> resultLst = personaMoralEntity.buscarPersonaMoral(persona);
            log.error("Lista de personas morales encontradas...." + resultLst);
            response = resultLst;
        }
        return response;
    }
    
    @Override
    public List<Moral> buscarPersonaMoralPorRfcEnImss_AP(String rfc) {
    	log.debug("::: Buscando en PersonaMoralBusiness.buscarPersonaMoralPorRfcEnImss_AP");
        List<Moral> response = null;
        if (!StringUtils.isBlank(rfc)) {
            Moral persona = new Moral();
            persona.setRfc(rfc);
            
            //CONVIERTE A MAYUSCULAS LOS ATRIBUTOS DE LA PERSONA MORAL
            convertirMayusculas(persona);
            
            List<Moral> resultLst = personaMoralEntity.buscarPersonaMoral_RFC_AP(persona);
            log.error("Lista de personas morales encontradas...." + resultLst);
            response = resultLst;
        }
        return response;
    }    

    public DatosSalidaPaginador<Moral> getPersonaMoralFiltro(DatosEntradaPaginador<Moral> paramsPager) throws NumeroMaximoResultadosSuperadoException {
    	log.debug("::: Buscando en PersonaMoralBusiness.getPersonaMoralFiltro");
        
        DatosSalidaPaginador<Moral> response = null;
        
        //CONVIERTE A MAYUSCULAS LOS ATRIBUTOS DE LA PERSONA MORAL
        convertirMayusculas(paramsPager.getModelo());
        
        try {
			response = this.personaMoralEntity.paginar(paramsPager);
		} catch (Exception e) {
			e.printStackTrace();
		}
        ValidacionesComunes.validaMaximoResultadosConsulta(response.getiTotalDisplayRecords());

        return response;
    }

    private void convertirMayusculas(Moral moral){
       	if (moral != null){
            if (moral.getActaConstitutiva() != null){
            	moral.setActaConstitutiva(moral.getActaConstitutiva().toUpperCase());	
            }
            if (moral.getRazonSocial() != null){
            	moral.setRazonSocial(moral.getRazonSocial().toUpperCase());	
            }
//            if (moral.getNombreComercial() != null){
//            	moral.setNombreComercial(moral.getNombreComercial().toUpperCase());	
//            }
            if (moral.getRfc() != null){
            	moral.setRfc(moral.getRfc().toUpperCase());            	
            }
    	}        	
    }
    
    
    @Override
    public void actualizarPersonaMoral(Moral moral) throws PersonaNoEncontradaException{
    	this.personaMoralEntity.actualizarPersonaMoral(moral);
    }
    
    
	public ICADatosRespuesta identificarCambios(ICADatosConsulta parametros)
			throws PersonaNoEncontradaException, RFCNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceSatRfcException, ErrorValidacionDatosConsultaEnEntidaExternaException,
			ComparacionSinDiferenciasException, DatosInsuficientesICAException {
		
		log.debug("::: Identificando cambios sobre PersonaMoralBusiness.identificarCambios");
		
		Moral personaIMSS = null;
		Moral personaEntidadSAT = null;
		Map<String, String> mensajes = new HashMap<String, String>();
		Map<String, CambioComparacionEnum> mapaCambios = new HashMap<String, CambioComparacionEnum>();
		ICADatosRespuesta objRetorno = new ICADatosRespuesta();
		ICADatosRespuesta objComparacionPersonas = new ICADatosRespuesta();
		
		if (parametros.getPersonaMoral().getIdPersona() != null) {
			personaIMSS =  personaBusiness.buscarPersonaMoralyDPyDyMCEnIMSS(parametros.getPersonaMoral().getIdPersona());
		} else {
			throw new DatosInsuficientesICAException(
					"No se puede realizar la comparaci�n, ya que no se proporciono el ID de la persona");
		}
		
		if (personaIMSS==null) {
			throw new PersonaNoEncontradaException(parametros.getPersonaMoral().getIdPersona());
		}
		
		log.error("Para ver si llego el tipo de sociedad de la consulta de IMSS" + personaIMSS.getTipoSociedad());
		
		// Consulta a SAT
		if (parametros.getPersonaMoral().getRfc() != null && StringUtils.isNotBlank(parametros.getPersonaMoral().getRfc())) {
			personaEntidadSAT = localizarPersonaMoralEnSATServiceBusiness.localizarPersonaMoralEnSATxRFC(parametros.getPersonaMoral().getRfc());
		} else if (personaIMSS.getRfc()!=null && StringUtils.isNotBlank(personaIMSS.getRfc())) {
			personaEntidadSAT = localizarPersonaMoralEnSATServiceBusiness.localizarPersonaMoralEnSATxRFC(personaIMSS.getRfc());
		} else {
			String mensaje = "No se cuenta con datos necesarios (RFC) para realizar consulta a SAT";
			mensajes.put("MSG05", mensaje);
			throw new DatosInsuficientesICAException(mensaje);
		}
		
		log.error("Para ver si llego el tipo de sociedad de la consulta del SAT" + personaEntidadSAT.getTipoSociedad());
		
		if (personaEntidadSAT != null) {
			
			/* Se recorre la lista de medios de contacto y se setean a los 
			 * atributos
			 */
			MedioContacto medioContacto = null;
			for(int i = 0; i < personaEntidadSAT.getMediosContactoFiscales().size(); i++){
				medioContacto = personaEntidadSAT.getMediosContactoFiscales().get(i);
				if (medioContacto.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoContactoEnum.CORREO_ELECTRONICO.getId())) {
					CorreoElectronico correoSat = new CorreoElectronico();
					correoSat.setCorreo(medioContacto.getDesFormaContacto());
					correoSat.setTipoMedioContacto(medioContacto.getTipoMedioContacto());
					personaEntidadSAT.setCorreoElectronicoFiscalAux(correoSat);
				} else if (medioContacto.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoContactoEnum.TELEFONO_FIJO.getId())) {
					TelefonoFijo telFijoSat = new TelefonoFijo();
					telFijoSat.setNumero(medioContacto.getDesFormaContacto());
					telFijoSat.setTipoMedioContacto(medioContacto.getTipoMedioContacto());
					personaEntidadSAT.setTelefonoFijoFiscalAux(telFijoSat);
				} else if (medioContacto.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoContactoEnum.TELEFONO_MOVIL.getId())) {
					TelefonoMovil telMovilSat = new TelefonoMovil();
					telMovilSat.setNumero(medioContacto.getDesFormaContacto());
					telMovilSat.setTipoMedioContacto(medioContacto.getTipoMedioContacto());
					personaEntidadSAT.setTelefonoMovilFiscalAux(telMovilSat);
				}
			}
			
			/*
			 * Ya que el objeto devuelto por el WS del SAT no trae la fecha de
			 * creaci�n formateada se settea
			 */
			if(personaEntidadSAT.getDatosPersonaSAT() != null){
				if(personaEntidadSAT.getDatosPersonaSAT().getFechaConstitucion() != null){
					personaEntidadSAT.setFechaCreacionFormateada(new SimpleDateFormat(
							"dd/MM/yyyy").format(personaEntidadSAT.getDatosPersonaSAT().getFechaConstitucion()));
				}
			}
						
			objComparacionPersonas = compararPersonaMoralEntidadExternaUtility.compararDosPersonasMorales(personaIMSS, personaEntidadSAT, mensajes);
			
			log.error("Para ver que regreso al comparar los datos de la PM de IMSS y SAT" + objComparacionPersonas.getCambios());
			
			mapaCambios = objComparacionPersonas.getCambios();
			mensajes = objComparacionPersonas.getTraza();
			objRetorno.setPersonaMoralEE(personaEntidadSAT);
		} else {
			personaIMSS = null;
			mensajes.put("MSGE02", "No se cuenta con datos necesarios para realizar la comparaci�n");
		}
				
		// Se checa si existieron diferencias
		if (mensajes.get("MSG02-SAT") != null) {
			
			// Se settea una nueva lista para asegurse de que s�lo se tengan las calificaciones necesarias
			personaIMSS.setPersonaCalificaciones(new ArrayList<PersonaCalificacion>());
			
			// Se agrega la calificaci�n "VALIDADO POR SAT"
			Calificacion calificacion = new Calificacion();
			calificacion.setIdCalificacion(CalificacionEnum.VALIDADO_SAT
					.getCodigo().longValue());
			calificacion.setDescripcion(CalificacionEnum.VALIDADO_SAT
					.getDescripcion());
			PersonaCalificacion personaCalificacion = new PersonaCalificacion();
			personaCalificacion.setCalificacion(calificacion);
			personaCalificacion.setFechaCalificacion(new Date());

			personaIMSS.getPersonaCalificaciones().add(personaCalificacion);
		} else if (personaEntidadSAT != null) {
			// No existieron diferencias, por lo tanto se termina el caso de uso
			throw new ComparacionSinDiferenciasException();
		}
		
		/*
		 * Se settean en nulo los siguientes atributos para que el parseo a JSON
		 * no falle
		 */
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		if(personaIMSS.getDatosPersonaSAT() != null){
			personaIMSS.getDatosPersonaSAT().setPersona(null);
			
			/*
			 * Se da formato compatible, ya que el formato en que se devuelve de la
			 * base de datos no es compatible con el parser de JSON de Spring MVC
			 */
			try {
				personaIMSS.getDatosPersonaSAT().setFechaConstitucion(format.parse(format.format(personaIMSS.getDatosPersonaSAT().getFechaConstitucion())));
				personaIMSS.getDatosPersonaSAT().setFechaInicioOperaciones(format.parse(format.format(personaIMSS.getDatosPersonaSAT().getFechaInicioOperaciones())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
			
		}
		
		if (personaIMSS.getSituacionesSAT() != null
				&& !personaIMSS.getSituacionesSAT().isEmpty()) {
			personaIMSS.getSituacionesSAT().get(0).setPersona(null);
			
			/*
			 * Se da formato compatible, ya que el formato en que se devuelve de la
			 * base de datos no es compatible con el parser de JSON de Spring MVC
			 */
			try {
				personaIMSS.getSituacionesSAT().get(0).setFechaSituacion(format.parse(format.format(personaIMSS.getSituacionesSAT().get(0).getFechaSituacion())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
		}
		
		/*
		 * Se da formato compatible, ya que el formato en que se devuelve de la
		 * base de datos no es compatible con el parser de JSON de Spring MVC
		 */
		if (personaIMSS.getFechaRegistro() != null){
			try {
				personaIMSS.setFechaRegistro(format.parse(format.format(personaIMSS.getFechaRegistro())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
		}
		
		/*
		 * Se da formato compatible, ya que el formato en que se devuelve de la
		 * base de datos no es compatible con el parser de JSON de Spring MVC
		 */
		if (personaIMSS.getFechaModificacion() != null){
			try {
				personaIMSS.setFechaModificacion(format.parse(format.format(personaIMSS.getFechaModificacion())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
		}
		
		/*
		 * Se da formato compatible, ya que el formato en que se devuelve de la
		 * base de datos no es compatible con el parser de JSON de Spring MVC
		 */
		if (personaIMSS.getFechaCreacion() != null){
			try {
				personaIMSS.setFechaCreacion(format.parse(format.format(personaIMSS.getFechaCreacion())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
		}
		
		/*
		 * Se settea vacia la lista de medios de contacto particulares, ya que en
		 * el ICA no se modifican
		 */
		if (personaIMSS.getMediosContacto() != null
				&& !personaIMSS.getMediosContacto().isEmpty()) {
			personaIMSS.getMediosContacto().clear();
		}
				
		/*
		 * Se settea vacia la lista de medios de contacto fiscales, ya que los
		 * medios fiscales resultantes del ICA ya se tiene en los atributos
		 * correspondientes de la persona
		 */
		if (personaIMSS.getMediosContactoFiscales() != null
				&& !personaIMSS.getMediosContactoFiscales().isEmpty()) {
			personaIMSS.getMediosContactoFiscales().clear();
		}
		
		if (personaIMSS.getEscrituraConstitutiva() != null
				&& personaIMSS.getEscrituraConstitutiva().getFechaExpedicion() != null) {
			try {
				personaIMSS.getEscrituraConstitutiva().setFechaExpedicion(format.parse(format.format(personaIMSS.getEscrituraConstitutiva().getFechaExpedicion())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
		}
		
		if (personaIMSS.getRegistroSindicato() != null
				&& personaIMSS.getRegistroSindicato().getFechaRegistro() != null) {
			try {
				personaIMSS.getRegistroSindicato().setFechaRegistro(format.parse(format.format(personaIMSS.getRegistroSindicato().getFechaRegistro())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
		}
		
		objRetorno.setPersonaFisicaIMSS(null);
		objRetorno.setPersonaMoralIMSS(personaIMSS);
		objRetorno.setTraza(mensajes);
		objRetorno.setCambios(mapaCambios);
		
		/*
		 * Siempre se settea true ya que en el ICA de una persona moral siempre
		 * se consulta al SAT
		 */
		objRetorno.setIndicadorConsultaSAT(true);
		
		return objRetorno;
	}
	
	@Override
	public ICADatosRespuesta integrarCambios(ICADatosRespuesta icaDatosRespuesta){
	
		Map<String, CambioComparacionEnum> diferencias = icaDatosRespuesta.getCambios();
		Moral entidadImss = icaDatosRespuesta.getPersonaMoralIMSS();
		Moral entidadExterna = icaDatosRespuesta.getPersonaMoralEE();
		
		if (icaDatosRespuesta.getTraza().get("MSG02-SAT") != null) {
			
			if(diferencias.get("nombreRazonSocial") != null && (diferencias.get("nombreRazonSocial") == CambioComparacionEnum.CAMBIO ||
					diferencias.get("nombreRazonSocial") == CambioComparacionEnum.NUEVO)){
				entidadImss.setRazonSocial(entidadExterna.getRazonSocial());
			}
			
			if(diferencias.get("fechaConstitucion") != null && (diferencias.get("fechaConstitucion") == CambioComparacionEnum.CAMBIO ||
					diferencias.get("fechaConstitucion") == CambioComparacionEnum.NUEVO)){
				entidadImss.setFechaCreacion(entidadExterna.getFechaCreacion());
			}
			
			if(diferencias.get("tipoSociedad") != null && (diferencias.get("tipoSociedad") == CambioComparacionEnum.CAMBIO ||
					diferencias.get("tipoSociedad") == CambioComparacionEnum.NUEVO)){
				entidadImss.setTipoSociedad(entidadExterna.getTipoSociedad());
			}
			
			if(diferencias.get("rfc") != null && (diferencias.get("rfc") == CambioComparacionEnum.CAMBIO ||
					diferencias.get("rfc") == CambioComparacionEnum.NUEVO)){
				entidadImss.setRfc(entidadExterna.getRfc());
			}
			
			if (diferencias.get("fechaConstitucion") != null || diferencias.get("fechaInicioOperaciones") != null ) {
				
				if(entidadImss.getDatosPersonaSAT() == null) {
					entidadImss.setDatosPersonaSAT(new DatosPersonaSAT());
				}
				
				if (diferencias.get("fechaConstitucion") == CambioComparacionEnum.CAMBIO || diferencias
						.get("fechaConstitucion") == CambioComparacionEnum.NUEVO) {
					entidadImss.getDatosPersonaSAT().setFechaConstitucion(entidadExterna.getDatosPersonaSAT().getFechaConstitucion());
				}
				
				if (diferencias.get("fechaInicioOperaciones") == CambioComparacionEnum.CAMBIO || diferencias
						.get("fechaInicioOperaciones") == CambioComparacionEnum.NUEVO) {
					entidadImss.getDatosPersonaSAT().setFechaInicioOperaciones(entidadExterna.getDatosPersonaSAT().getFechaInicioOperaciones());
				}
			}
			
			if(diferencias.get("domicilioFiscal") != null){ 
				if(diferencias.get("domicilioFiscal") == CambioComparacionEnum.NUEVO){
					entidadImss.setDomicilioFiscal(entidadExterna.getDomicilioFiscal());
				}else if (diferencias.get("domicilioFiscal") == CambioComparacionEnum.CAMBIO){
					if(diferencias.get("codigoPostal") != null && (diferencias.get("codigoPostal") == CambioComparacionEnum.CAMBIO ||
							diferencias.get("codigoPostal") == CambioComparacionEnum.NUEVO)){
						entidadImss.getDomicilioFiscal().setCodigoPostal(entidadExterna.getDomicilioFiscal().getCodigoPostal());
					}
					if(diferencias.get("calle") != null && (diferencias.get("calle") == CambioComparacionEnum.CAMBIO ||
							diferencias.get("calle") == CambioComparacionEnum.NUEVO)){
						entidadImss.getDomicilioFiscal().setCalle(entidadExterna.getDomicilioFiscal().getCalle());
					}
					if(diferencias.get("colonia") != null && (diferencias.get("colonia") == CambioComparacionEnum.CAMBIO ||
							diferencias.get("colonia") == CambioComparacionEnum.NUEVO)){
						entidadImss.getDomicilioFiscal().setColonia(entidadExterna.getDomicilioFiscal().getColonia());
					}
					if(diferencias.get("numeExt") != null && (diferencias.get("numeExt") == CambioComparacionEnum.CAMBIO ||
							diferencias.get("numeExt") == CambioComparacionEnum.NUEVO)){
						entidadImss.getDomicilioFiscal().setNumExteriorAlf(entidadExterna.getDomicilioFiscal().getNumExteriorAlf());
					}
					if(diferencias.get("numeInt") != null && (diferencias.get("numeInt") == CambioComparacionEnum.CAMBIO ||
							diferencias.get("numeInt") == CambioComparacionEnum.NUEVO)){
						entidadImss.getDomicilioFiscal().setNumInteriorAlf(entidadExterna.getDomicilioFiscal().getNumInteriorAlf());
					}
					if(diferencias.get("entreCalle1") != null && (diferencias.get("entreCalle1") == CambioComparacionEnum.CAMBIO ||
							diferencias.get("entreCalle1") == CambioComparacionEnum.NUEVO)){
						entidadImss.getDomicilioFiscal().setVialidadReferenciaPrimaria(entidadExterna.getDomicilioFiscal().getVialidadReferenciaPrimaria());
					}
					if(diferencias.get("entreCalle2") != null && (diferencias.get("entreCalle2") == CambioComparacionEnum.CAMBIO ||
							diferencias.get("entreCalle2") == CambioComparacionEnum.NUEVO)){
						entidadImss.getDomicilioFiscal().setVialidadReferenciaSecundaria(entidadExterna.getDomicilioFiscal().getVialidadReferenciaSecundaria());
					}
					if(diferencias.get("referencia") != null && (diferencias.get("referencia") == CambioComparacionEnum.CAMBIO ||
							diferencias.get("referencia") == CambioComparacionEnum.NUEVO)){
						entidadImss.getDomicilioFiscal().setDescripcion(entidadExterna.getDomicilioFiscal().getDescripcion());
					}
					if(diferencias.get("vialidad") != null && (diferencias.get("vialidad") == CambioComparacionEnum.CAMBIO ||
							diferencias.get("vialidad") == CambioComparacionEnum.NUEVO)){
						entidadImss.getDomicilioFiscal().setVialidadPrimaria(entidadExterna.getDomicilioFiscal().getVialidadPrimaria());
					}
					if ((diferencias.get("inmueble") != null && (diferencias.get("inmueble") == CambioComparacionEnum.CAMBIO || 
							diferencias.get("inmueble") == CambioComparacionEnum.NUEVO)) || (diferencias.get("entidad") != null && 
							(diferencias.get("entidad") == CambioComparacionEnum.CAMBIO || 
							diferencias.get("entidad") == CambioComparacionEnum.NUEVO))|| (diferencias.get("localidad") != null 
							&& (diferencias.get("localidad") == CambioComparacionEnum.CAMBIO || 
							diferencias.get("localidad") == CambioComparacionEnum.NUEVO)) || 
							(diferencias.get("municipio") != null && (diferencias.get("municipio") == CambioComparacionEnum.CAMBIO || 
							diferencias.get("municipio") == CambioComparacionEnum.NUEVO))) {
						entidadImss.getDomicilioFiscal().setAsentamiento(entidadExterna.getDomicilioFiscal().getAsentamiento());
					}
				}
			}
			
			
			// Medios de contacto fiscales
			if (entidadImss.getMediosContactoFiscales() == null) {
				// Si est� nula se crea una nueva
				entidadImss.setMediosContactoFiscales(new ArrayList<MedioContacto>());
			} else {
				/*
				 * Si ya tiene la lista, se limpia para agregar los medios de
				 * contacto como instancias de MedioContacto
				 */
				entidadImss.getMediosContactoFiscales().clear();
			}
			
			if (diferencias.get("correoElectronico") != null
					&& (diferencias.get("correoElectronico") == CambioComparacionEnum.CAMBIO || diferencias
							.get("correoElectronico") == CambioComparacionEnum.NUEVO)) {
				if (diferencias.get("correoElectronico") == CambioComparacionEnum.CAMBIO){
					entidadExterna.getCorreoElectronicoFiscal().setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.MODIFICADO);
					entidadExterna.getCorreoElectronicoFiscal().setClave(entidadImss.getCorreoElectronicoFiscal().getClave());
				} else if (diferencias.get("correoElectronico") == CambioComparacionEnum.NUEVO){
					entidadExterna.getCorreoElectronicoFiscal().setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.NUEVO);
				} 
				
				entidadImss.setCorreoElectronicoFiscalAux(entidadExterna.getCorreoElectronicoFiscal());
				
				MedioContacto medio = new MedioContacto();
				medio.setDesFormaContacto(entidadExterna.getCorreoElectronicoFiscal().getCorreo());
				TipoMedioContacto tipoMedioContacto = new TipoMedioContacto();
				tipoMedioContacto.setIdTipoMedioContacto(TipoContactoEnum.CORREO_ELECTRONICO.getId());
				tipoMedioContacto.setDescripcion("Correo electr�nico");
				medio.setTipoMedioContacto(tipoMedioContacto);
				medio.setEstadoAdministracionMedioContacto(entidadExterna.getCorreoElectronicoFiscal().getEstadoAdministracionMedioContacto());
				
				entidadImss.getMediosContactoFiscales().add(medio);
			} else if (entidadImss.getCorreoElectronicoFiscal() != null){
				MedioContacto medio = new MedioContacto();
				medio.setClave(entidadImss.getCorreoElectronicoFiscal().getClave());
				medio.setDesFormaContacto(entidadImss.getCorreoElectronicoFiscal().getCorreo());
				TipoMedioContacto tipoMedioContacto = new TipoMedioContacto();
				tipoMedioContacto.setIdTipoMedioContacto(TipoContactoEnum.CORREO_ELECTRONICO.getId());
				tipoMedioContacto.setDescripcion("Correo electr�nico");
				medio.setTipoMedioContacto(tipoMedioContacto);
				
				entidadImss.getMediosContactoFiscales().add(medio);
			}
			
			if (diferencias.get("telefonoFijo") != null
					&& (diferencias.get("telefonoFijo") == CambioComparacionEnum.CAMBIO || diferencias
							.get("telefonoFijo") == CambioComparacionEnum.NUEVO)) { 
				if (diferencias.get("telefonoFijo") == CambioComparacionEnum.CAMBIO){
					entidadExterna.getTelefonoFijoFiscal().setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.MODIFICADO);
					entidadExterna.getTelefonoFijoFiscal().setClave(entidadImss.getTelefonoFijoFiscal().getClave());
				}else if (diferencias.get("telefonoFijo") == CambioComparacionEnum.NUEVO){
					entidadExterna.getTelefonoFijoFiscal().setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.NUEVO);
				}
				
				entidadImss.setTelefonoFijoFiscalAux(entidadExterna.getTelefonoFijoFiscal());
				
				MedioContacto medio = new MedioContacto();
							
				StringBuffer telefono = new StringBuffer();
				telefono.append(StringUtils.isEmpty(entidadExterna.getTelefonoFijoFiscal().getClaveLada()) ? " " : entidadExterna.getTelefonoFijoFiscal().getClaveLada()).append("|");
				telefono.append(StringUtils.isEmpty(entidadExterna.getTelefonoFijoFiscal().getNumero()) ? " " : entidadExterna.getTelefonoFijoFiscal().getNumero()).append("|");
				telefono.append(StringUtils.isEmpty(entidadExterna.getTelefonoFijoFiscal().getExtension()) ? " " : entidadExterna.getTelefonoFijoFiscal().getExtension());
				
				medio.setDesFormaContacto(telefono.toString());
				TipoMedioContacto tipoMedioContacto = new TipoMedioContacto();
				tipoMedioContacto.setIdTipoMedioContacto(TipoContactoEnum.TELEFONO_FIJO.getId());
				tipoMedioContacto.setDescripcion("Tel�fono Fijo");
				medio.setTipoMedioContacto(tipoMedioContacto);
				medio.setEstadoAdministracionMedioContacto(entidadExterna.getTelefonoFijoFiscal().getEstadoAdministracionMedioContacto());
				
				entidadImss.getMediosContactoFiscales().add(medio);
			} else if (entidadImss.getTelefonoFijoFiscal() != null){
				MedioContacto medio = new MedioContacto();
				medio.setClave(entidadImss.getTelefonoFijoFiscal().getClave());
				
				StringBuffer telefono = new StringBuffer();
				telefono.append(StringUtils.isEmpty(entidadImss.getTelefonoFijoFiscal().getClaveLada()) ? " " : entidadImss.getTelefonoFijoFiscal().getClaveLada()).append("|");
				telefono.append(StringUtils.isEmpty(entidadImss.getTelefonoFijoFiscal().getNumero()) ? " " : entidadImss.getTelefonoFijoFiscal().getNumero()).append("|");
				telefono.append(StringUtils.isEmpty(entidadImss.getTelefonoFijoFiscal().getExtension()) ? " " : entidadImss.getTelefonoFijoFiscal().getExtension());
				
				medio.setDesFormaContacto(telefono.toString());
				TipoMedioContacto tipoMedioContacto = new TipoMedioContacto();
				tipoMedioContacto.setIdTipoMedioContacto(TipoContactoEnum.TELEFONO_FIJO.getId());
				tipoMedioContacto.setDescripcion("Tel�fono Fijo");
				medio.setTipoMedioContacto(tipoMedioContacto);
				
				entidadImss.getMediosContactoFiscales().add(medio);
			}
			
			if (diferencias.get("telefonoMovil") != null
					&& (diferencias.get("telefonoMovil") == CambioComparacionEnum.CAMBIO || diferencias
							.get("telefonoMovil") == CambioComparacionEnum.NUEVO)) {
				if (diferencias.get("telefonoMovil") == CambioComparacionEnum.CAMBIO){
					entidadExterna.getTelefonoMovilFiscal().setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.MODIFICADO);
					entidadExterna.getTelefonoMovilFiscal().setClave(entidadImss.getTelefonoMovilFiscal().getClave());
				} else if (diferencias.get("telefonoMovil") == CambioComparacionEnum.NUEVO){
					entidadExterna.getTelefonoMovilFiscal().setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.NUEVO);
				}
				entidadImss.setTelefonoMovilFiscalAux(entidadExterna.getTelefonoMovilFiscal());
				
				MedioContacto medio = new MedioContacto();
				medio.setDesFormaContacto(entidadExterna.getTelefonoMovilFiscal().getNumero());
				TipoMedioContacto tipoMedioContacto = new TipoMedioContacto();
				tipoMedioContacto.setIdTipoMedioContacto(TipoContactoEnum.TELEFONO_MOVIL.getId());
				tipoMedioContacto.setDescripcion("Tel�fono M�vil");
				medio.setTipoMedioContacto(tipoMedioContacto);
				medio.setEstadoAdministracionMedioContacto(entidadExterna.getTelefonoMovilFiscal().getEstadoAdministracionMedioContacto());
				
				entidadImss.getMediosContactoFiscales().add(medio);
			} else if (entidadImss.getTelefonoMovilFiscal() != null){
				MedioContacto medio = new MedioContacto();
				medio.setClave(entidadImss.getTelefonoMovilFiscal().getClave());
				medio.setDesFormaContacto(entidadImss.getTelefonoMovilFiscal().getNumero());
				TipoMedioContacto tipoMedioContacto = new TipoMedioContacto();
				tipoMedioContacto.setIdTipoMedioContacto(TipoContactoEnum.TELEFONO_MOVIL.getId());
				tipoMedioContacto.setDescripcion("Tel�fono M�vil");
				medio.setTipoMedioContacto(tipoMedioContacto);
				
				entidadImss.getMediosContactoFiscales().add(medio);
			}
			
			
			if(diferencias.get("situacionSAT") != null && (diferencias.get("situacionSAT") == CambioComparacionEnum.CAMBIO ||
					diferencias.get("situacionSAT") == CambioComparacionEnum.NUEVO)){
				
				/*
				 * Se crea una lista nueva para garantizar que se tenga la
				 * situaci�n que se necesita
				 */				
				SituacionSAT situacionSAT = entidadExterna.getSituacionesSAT().get(0);
				
				List<SituacionSAT> situaciones = new ArrayList<SituacionSAT>();
				situaciones.add(situacionSAT);
				
				entidadImss.setSituacionesSAT(situaciones);
			}
		}
		
		
	
		return icaDatosRespuesta;
	}
	
	@Override
	public ICADatosRespuesta compararDosPersonasMorales(Moral persona1,
			Moral persona2, Map<String, String> mensajes) {
	
		ICADatosRespuesta icaDatosRespuesta = this.compararPersonaMoralEntidadExternaUtility
				.compararDosPersonasMorales(persona1, persona2, mensajes);
	
		return icaDatosRespuesta;
	}

	@Override
	public MDMDatosEntrada modificacionManual(MDMDatosEntrada mdmDatosEntrada)
			throws DatosInsuficientesModificacionException, PersonaNoEncontradaException {
		
		log.debug("Entrando al servicio de la modificacion manual de la persona moral [cveMoral = "
				+ mdmDatosEntrada.getPersonaMoral().getCveMoral() + "]");
		
		Moral personaIMSS = null;
		
		if(mdmDatosEntrada.getIndCapturaRFC() ||
				mdmDatosEntrada.getIndCapturaDomicilioFiscal() ||
				mdmDatosEntrada.getIndCapturaMediosContactoFiscales() ||
				mdmDatosEntrada.getIndCapturaRazonSocial() ||
				mdmDatosEntrada.getIndCapturaFechaConstitucion() ||
				mdmDatosEntrada.getIndCapturaTipoSociedad()){
			mdmDatosEntrada.setIndCapturaDatosSAT(Boolean.TRUE);
		}else{
			mdmDatosEntrada.setIndCapturaDatosSAT(Boolean.FALSE);
		}
		
		if (mdmDatosEntrada.getIndCapturaActaConstitutiva()
				|| mdmDatosEntrada.getIndCapturaRegistroSindicato()) {
			mdmDatosEntrada.setIndCapturaDatosComplementarios(Boolean.TRUE);
		} else {
			mdmDatosEntrada.setIndCapturaDatosComplementarios(Boolean.FALSE);
		}
		
		if (!mdmDatosEntrada.getIndCapturaDatosSAT()
				&& !mdmDatosEntrada.getIndCapturaDatosComplementarios()) {
			throw new DatosInsuficientesModificacionException(
					"No se puede realizar la modificaci�n manual, ya que no especific� la informaci�n a modificar");
		}
		
		/* Los indicadores de Acta Constitutiva y Registro de Sindicato
		 * son mutuamente excluyentes, por lo tanto, si ambos se eligieron
		 * se lanza una excepci�n
		 */
		if(mdmDatosEntrada.getIndCapturaActaConstitutiva()
				&& mdmDatosEntrada.getIndCapturaRegistroSindicato()){
			throw new DatosInsuficientesModificacionException(
					"No se puede realizar la modificaci�n manual, ya que no se puede modificar el Acta Constitutiva y el Registro Sindical al mismo tiempo");
		}
		
		if (mdmDatosEntrada.getPersonaMoral() != null
				&& mdmDatosEntrada.getPersonaMoral().getIdPersona() != null) {
			log.debug("::: Buscando la persona Moral en PersonaMoralBusiness.modificacionManual");
			personaIMSS = personaBusiness
					.buscarPersonaMoralParaModificacionManual(mdmDatosEntrada
							.getPersonaMoral().getIdPersona());
		} else {
			throw new DatosInsuficientesModificacionException(
					"No se puede realizar la modificaci�n manual, ya que no se proporcion� el ID de la persona");
		}
		
		if (personaIMSS==null) {
			throw new PersonaNoEncontradaException(mdmDatosEntrada
					.getPersonaMoral().getIdPersona());
		}
		
		mdmDatosEntrada.setPersonaMoral(personaIMSS);
		
		return mdmDatosEntrada;
	}
	
	@Override
	public MDMDatosEntrada procesarModificacionManual(MDMDatosEntrada mdmDatosEntrada){

		log.debug(":::PersonaMoralBusiness.procesarModificacionManual");
					
		Moral personaModificada = mdmDatosEntrada.getPersonaMoral();
		
		log.debug("Entrando al servicio que procesa la modificacion manual de la persona moral [cveMoral = "
				+ personaModificada.getCveMoral() + "]");
		
		Moral personaOriginal = this.personaBusiness
				.buscarPersonaMoralyDPyDyMCEnIMSS(personaModificada
						.getCveMoral());
		
		/*
		 * Se recorre la lista de medios fiscales de contacto de la persona modificada 
		 * y se setean a los atributos para que en el servicio de comparar se tengan 
		 * como lo requiere
		 */		
		if (personaModificada.getMediosContactoFiscales() != null
				&& !personaModificada.getMediosContactoFiscales().isEmpty()) {
			MedioContacto medioContacto = null;
			for(int i = 0; i < personaModificada.getMediosContactoFiscales().size(); i++){
				medioContacto = personaModificada.getMediosContactoFiscales().get(i);
				if (medioContacto.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoContactoEnum.CORREO_ELECTRONICO.getId())) {
					CorreoElectronico correoSat = new CorreoElectronico();
					correoSat.setCorreo(medioContacto.getDesFormaContacto());
					correoSat.setTipoMedioContacto(medioContacto.getTipoMedioContacto());
					correoSat.setEstadoAdministracionMedioContacto(medioContacto.getEstadoAdministracionMedioContacto());
					personaModificada.setCorreoElectronicoFiscalAux(correoSat);
				} else if (medioContacto.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoContactoEnum.TELEFONO_FIJO.getId())) {
					TelefonoFijo telFijoSat = new TelefonoFijo();
					String numero[] = medioContacto.getDesFormaContacto().split("\\|");
					telFijoSat.setClaveLada(numero[0]);
					telFijoSat.setNumero(numero[1]);
					telFijoSat.setExtension(numero[2]);
					telFijoSat.setTipoMedioContacto(medioContacto.getTipoMedioContacto());
					telFijoSat.setEstadoAdministracionMedioContacto(medioContacto.getEstadoAdministracionMedioContacto());
					personaModificada.setTelefonoFijoFiscalAux(telFijoSat);
				} else if (medioContacto.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoContactoEnum.TELEFONO_MOVIL.getId())) {
					TelefonoMovil telMovilSat = new TelefonoMovil();
					telMovilSat.setNumero(medioContacto.getDesFormaContacto());
					telMovilSat.setTipoMedioContacto(medioContacto.getTipoMedioContacto());
					telMovilSat.setEstadoAdministracionMedioContacto(medioContacto.getEstadoAdministracionMedioContacto());
					personaModificada.setTelefonoMovilFiscalAux(telMovilSat);
				}
			}
		}
		
		Map<String, String> mensajes = new HashMap<String, String>();
		
		ICADatosRespuesta datosRespuesta = this.compararPersonaMoralEntidadExternaUtility
				.compararDosPersonasMorales(personaOriginal, personaModificada,
						mensajes);
		
		/*
		 * Se settean nulos los campos de medios de contacto (fiscales y
		 * particulares) para evitar que se dupliquen en las listas
		 * correspondientes
		 */
		personaModificada.setCorreoElectronicoAux(null);
		personaModificada.setTelefonoFijoAux(null);
		personaModificada.setTelefonoMovilAux(null);
		personaModificada.setFacebookAux(null);
		personaModificada.setTwitterAux(null);
		personaModificada.setCorreoElectronicoFiscalAux(null);
		personaModificada.setTelefonoFijoFiscalAux(null);
		personaModificada.setTelefonoMovilFiscalAux(null);
		
		mdmDatosEntrada.setTraza(datosRespuesta.getTraza());
		mdmDatosEntrada.setCambios(datosRespuesta.getCambios());
		
		/*
		 * Si la fecha de inicio de operaciones es nula, se toma la fecha de
		 * constituci�n
		 */
		if(personaModificada.getDatosPersonaSAT() != null){
			if (personaModificada.getDatosPersonaSAT().getFechaConstitucion() != null
					&& personaModificada.getDatosPersonaSAT()
							.getFechaInicioOperaciones() == null) {
				personaModificada.getDatosPersonaSAT()
						.setFechaInicioOperaciones(
								personaModificada.getDatosPersonaSAT()
										.getFechaConstitucion());
			}
		}
		
		// Se valida si hubo cambios, si es as� se agrega la calificaci�n IMSS
		if (datosRespuesta.getTraza().containsKey("MSG02-SAT")
				|| mdmDatosEntrada.getIndCapturaDatosComplementarios()) {

			Calificacion calificacion = new Calificacion();
			calificacion.setIdCalificacion(CalificacionEnum.VALIDADO_IMSS
					.getCodigo().longValue());
			calificacion.setDescripcion(CalificacionEnum.VALIDADO_IMSS.getDescripcion());
			
			PersonaCalificacion personaCalificacion = new PersonaCalificacion();
			personaCalificacion.setCalificacion(calificacion);
			personaCalificacion.setFechaCalificacion(new Date());
			
			/*
			 * Se crea una nueva lista, para asegurar que s�lo se tenga la
			 * calificaci�n requerida
			 */
			personaModificada.setPersonaCalificaciones(new ArrayList<PersonaCalificacion>());
			
			personaModificada.getPersonaCalificaciones().add(personaCalificacion);
			
			// Se agrega el mensaje de validado IMSS
			datosRespuesta.getTraza().put("VALIDADO_IMSS", "Validado por IMSS");
			
		}
		
		return mdmDatosEntrada;
	}

	@Override
	public void afectarDatosPersonaMoral(AfectarDatosPersonaWrapper datosPersona)
			throws PersonaMoralNoEncontradaException {
		this.personaMoralEntity.afectarDatosPersonaMoral(datosPersona);		
	}
 
	@Override
	public void afectarCalificacionesPersona(
			AfectarDatosPersonaWrapper datosPersona) {
		this.personaMoralEntity.afectarCalificacionesPersona(datosPersona);
	}
	
	@Override
	public void afectarIdentificadoresPersona(AfectarDatosPersonaWrapper datosPersona){
		
		this.log.debug("Se van a modificar los identificadores de la persona [cveMoral = "
				+ datosPersona.getMoral().getCveMoral() + "]");
		
		Moral moral = new Moral();
		moral.setCveMoral(datosPersona.getMoral().getCveMoral());
		
		moral.setRfc(StringUtils.isNotBlank(datosPersona.getMoral()
				.getRfcVigente()) ? datosPersona.getMoral().getRfcVigente()
				: datosPersona.getMoral().getRfc());
		
		try {
			// Se obtienen los identificadores vigentes de la persona
			List<Identificador> identificadores = this.identificadoresPersonaMoralServiceBusiness
					.obtenerIdentificadoresPersona(moral.getCveMoral());
			List<Identificador> nuevosIdentificadores = new ArrayList<Identificador>();
			
			for(Identificador identificador : identificadores){
				if (identificador.getTipoIdentificador()
						.getIdTipoIdentificador() == TipoIdentificadorEnum.RFC
						.getCodigo() && StringUtils.isNotBlank(moral.getRfc())) {
					if(!identificador.getIdentificadora().equals(moral.getRfc())){
						
						this.log.debug("Se tiene un identificador diferente al vigente");
						
						// Se expira el modificador anterior
						this.identificadoresPersonaMoralServiceBusiness.expirarIdentificador(identificador);
						
						Identificador identificadorNuevo = new Identificador();
						
						TipoIdentificador tipoIdentificador = new TipoIdentificador();
						tipoIdentificador.setIdTipoIdentificador(TipoIdentificadorEnum.RFC.getCodigo());
						
						identificadorNuevo.setTipoIdentificador(tipoIdentificador);
						identificadorNuevo.setVigente(1);
						
						nuevosIdentificadores.add(identificadorNuevo);
					}
				}
			}
			
			// Se guardan los nuevos modificadores
			if(!nuevosIdentificadores.isEmpty()){
				try {
					moral.setIdentificadores(nuevosIdentificadores);
					this.identificadoresPersonaMoralServiceBusiness.registrar(moral);
				} catch (IdentificadoresNoExistentesException e) {
					this.log.warn(e);
				}
			}
		} catch (PersonaSinIdentificadoresException e) {
			this.log.warn(e);
			
			/* La persona no cuenta con identificadores, por lo tanto,
			 * se guardan como nuevos 
			 */
			List<Identificador> identificadores = new ArrayList<Identificador>();
						
			if(StringUtils.isNotBlank(moral.getRfc())){
				Identificador identificador = new Identificador();
				
				TipoIdentificador tipoIdentificador = new TipoIdentificador();
				tipoIdentificador.setIdTipoIdentificador(TipoIdentificadorEnum.RFC.getCodigo());
				
				identificador.setTipoIdentificador(tipoIdentificador);
				identificador.setVigente(1);
				
				identificadores.add(identificador);
			}
			
			try {
				moral.setIdentificadores(identificadores);
				this.identificadoresPersonaMoralServiceBusiness.registrar(moral);
			} catch (IdentificadoresNoExistentesException e1) {
				this.log.warn(e1);
			}
		}
	}
	
	
	
	@Override
	public ICADatosRespuesta identificarSoloCambios(ICADatosConsulta parametros)
			throws PersonaNoEncontradaException, RFCNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceSatRfcException, ErrorValidacionDatosConsultaEnEntidaExternaException, DatosInsuficientesICAException {
		log.debug("::: Identificando cambios sobre PersonaMoralBusiness.identificarSoloCambios");
		Moral personaIMSS = null;
		Moral personaEntidadSAT = null;
		Map<String, String> mensajes = new HashMap<String, String>();
		Map<String, CambioComparacionEnum> mapaCambios = new HashMap<String, CambioComparacionEnum>();
		ICADatosRespuesta objRetorno = new ICADatosRespuesta();
		ICADatosRespuesta objComparacionPersonas = new ICADatosRespuesta();
		
		if (parametros.getPersonaMoral().getIdPersona() != null) {
			personaIMSS =  personaBusiness.buscarPersonaMoralyDPyDyMCEnIMSS(parametros.getPersonaMoral().getIdPersona());
		} else {
			throw new DatosInsuficientesICAException(
					"No se puede realizar la comparaci�n, ya que no se proporciono el ID de la persona");
		}
		
		if (personaIMSS==null) {
			throw new PersonaNoEncontradaException(parametros.getPersonaMoral().getIdPersona());
		}
		
		// Consulta a SAT
		if (parametros.getPersonaMoral().getRfc() != null && StringUtils.isNotBlank(parametros.getPersonaMoral().getRfc())) {
			personaEntidadSAT = localizarPersonaMoralEnSATServiceBusiness.localizarPersonaMoralEnSATxRFC(parametros.getPersonaMoral().getRfc());
		} else if (personaIMSS.getRfc()!=null && StringUtils.isNotBlank(personaIMSS.getRfc())) {
			personaEntidadSAT = localizarPersonaMoralEnSATServiceBusiness.localizarPersonaMoralEnSATxRFC(personaIMSS.getRfc());
			this.log.debug("Para ver que pase por el metodo identificarSoloCambios y se obtenga tipo de sociedad" + personaEntidadSAT.getTipoSociedad());
		} else {
			String mensaje = "No se cuenta con datos necesarios (RFC) para realizar consulta a SAT";
			mensajes.put("MSG05", mensaje);
			throw new DatosInsuficientesICAException(mensaje);
		}
		
		if (personaEntidadSAT != null) {
			
			/* Se recorre la lista de medios de contacto y se setean a los 
			 * atributos
			 */
			MedioContacto medioContacto = null;
			for(int i = 0; i < personaEntidadSAT.getMediosContactoFiscales().size(); i++){
				medioContacto = personaEntidadSAT.getMediosContactoFiscales().get(i);
				if (medioContacto.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoContactoEnum.CORREO_ELECTRONICO.getId())) {
					CorreoElectronico correoSat = new CorreoElectronico();
					correoSat.setCorreo(medioContacto.getDesFormaContacto());
					correoSat.setTipoMedioContacto(medioContacto.getTipoMedioContacto());
					personaEntidadSAT.setCorreoElectronicoFiscalAux(correoSat);
				} else if (medioContacto.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoContactoEnum.TELEFONO_FIJO.getId())) {
					TelefonoFijo telFijoSat = new TelefonoFijo();
					telFijoSat.setNumero(medioContacto.getDesFormaContacto());
					telFijoSat.setTipoMedioContacto(medioContacto.getTipoMedioContacto());
					personaEntidadSAT.setTelefonoFijoFiscalAux(telFijoSat);
				} else if (medioContacto.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoContactoEnum.TELEFONO_MOVIL.getId())) {
					TelefonoMovil telMovilSat = new TelefonoMovil();
					telMovilSat.setNumero(medioContacto.getDesFormaContacto());
					telMovilSat.setTipoMedioContacto(medioContacto.getTipoMedioContacto());
					personaEntidadSAT.setTelefonoMovilFiscalAux(telMovilSat);
				}
			}
			
			/*
			 * Ya que el objeto devuelto por el WS del SAT no trae la fecha de
			 * creaci�n formateada se settea
			 */
			if(personaEntidadSAT.getDatosPersonaSAT() != null){
				if(personaEntidadSAT.getDatosPersonaSAT().getFechaConstitucion() != null){
					personaEntidadSAT.setFechaCreacionFormateada(new SimpleDateFormat(
							"dd/MM/yyyy").format(personaEntidadSAT.getDatosPersonaSAT().getFechaConstitucion()));
				}
			}
						
			objComparacionPersonas = compararPersonaMoralEntidadExternaUtility.compararDosPersonasMorales(personaIMSS, personaEntidadSAT, mensajes);
			mapaCambios = objComparacionPersonas.getCambios();
			mensajes = objComparacionPersonas.getTraza();
			objRetorno.setPersonaMoralEE(personaEntidadSAT);
		} else {
			personaIMSS = null;
			mensajes.put("MSGE02", "No se cuenta con datos necesarios para realizar la comparaci�n");
		}
				
		// Se checa si existieron diferencias
		if (mensajes.get("MSG02-SAT") != null) {
			
			// Se settea una nueva lista para asegurse de que s�lo se tengan las calificaciones necesarias
			personaIMSS.setPersonaCalificaciones(new ArrayList<PersonaCalificacion>());
			
			// Se agrega la calificaci�n "VALIDADO POR SAT"
			Calificacion calificacion = new Calificacion();
			calificacion.setIdCalificacion(CalificacionEnum.VALIDADO_SAT
					.getCodigo().longValue());
			calificacion.setDescripcion(CalificacionEnum.VALIDADO_SAT
					.getDescripcion());
			PersonaCalificacion personaCalificacion = new PersonaCalificacion();
			personaCalificacion.setCalificacion(calificacion);
			personaCalificacion.setFechaCalificacion(new Date());

			personaIMSS.getPersonaCalificaciones().add(personaCalificacion);
		} else if (personaEntidadSAT != null) {
			// No existieron diferencias, por lo tanto se termina el caso de uso
			objRetorno.setCambios(new HashMap<String, CambioComparacionEnum>());
		}
		
		/*
		 * Se settean en nulo los siguientes atributos para que el parseo a JSON
		 * no falle
		 */
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		if(personaIMSS.getDatosPersonaSAT() != null){
			personaIMSS.getDatosPersonaSAT().setPersona(null);
			
			/*
			 * Se da formato compatible, ya que el formato en que se devuelve de la
			 * base de datos no es compatible con el parser de JSON de Spring MVC
			 */
			try {
				personaIMSS.getDatosPersonaSAT().setFechaConstitucion(format.parse(format.format(personaIMSS.getDatosPersonaSAT().getFechaConstitucion())));
				personaIMSS.getDatosPersonaSAT().setFechaInicioOperaciones(format.parse(format.format(personaIMSS.getDatosPersonaSAT().getFechaInicioOperaciones())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
			
		}
		
		if (personaIMSS.getSituacionesSAT() != null
				&& !personaIMSS.getSituacionesSAT().isEmpty()) {
			personaIMSS.getSituacionesSAT().get(0).setPersona(null);
			
			/*
			 * Se da formato compatible, ya que el formato en que se devuelve de la
			 * base de datos no es compatible con el parser de JSON de Spring MVC
			 */
			try {
				personaIMSS.getSituacionesSAT().get(0).setFechaSituacion(format.parse(format.format(personaIMSS.getSituacionesSAT().get(0).getFechaSituacion())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
		}
		
		/*
		 * Se da formato compatible, ya que el formato en que se devuelve de la
		 * base de datos no es compatible con el parser de JSON de Spring MVC
		 */
		if (personaIMSS.getFechaRegistro() != null){
			try {
				personaIMSS.setFechaRegistro(format.parse(format.format(personaIMSS.getFechaRegistro())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
		}
		
		/*
		 * Se da formato compatible, ya que el formato en que se devuelve de la
		 * base de datos no es compatible con el parser de JSON de Spring MVC
		 */
		if (personaIMSS.getFechaModificacion() != null){
			try {
				personaIMSS.setFechaModificacion(format.parse(format.format(personaIMSS.getFechaModificacion())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
		}
		
		/*
		 * Se da formato compatible, ya que el formato en que se devuelve de la
		 * base de datos no es compatible con el parser de JSON de Spring MVC
		 */
		if (personaIMSS.getFechaCreacion() != null){
			try {
				personaIMSS.setFechaCreacion(format.parse(format.format(personaIMSS.getFechaCreacion())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
		}
		
		/*
		 * Se settea vacia la lista de medios de contacto particulares, ya que en
		 * el ICA no se modifican
		 */
		if (personaIMSS.getMediosContacto() != null
				&& !personaIMSS.getMediosContacto().isEmpty()) {
			personaIMSS.getMediosContacto().clear();
		}
				
		/*
		 * Se settea vacia la lista de medios de contacto fiscales, ya que los
		 * medios fiscales resultantes del ICA ya se tiene en los atributos
		 * correspondientes de la persona
		 */
		if (personaIMSS.getMediosContactoFiscales() != null
				&& !personaIMSS.getMediosContactoFiscales().isEmpty()) {
			personaIMSS.getMediosContactoFiscales().clear();
		}
		
		if (personaIMSS.getEscrituraConstitutiva() != null
				&& personaIMSS.getEscrituraConstitutiva().getFechaExpedicion() != null) {
			try {
				personaIMSS.getEscrituraConstitutiva().setFechaExpedicion(format.parse(format.format(personaIMSS.getEscrituraConstitutiva().getFechaExpedicion())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
		}
		
		if (personaIMSS.getRegistroSindicato() != null
				&& personaIMSS.getRegistroSindicato().getFechaRegistro() != null) {
			try {
				personaIMSS.getRegistroSindicato().setFechaRegistro(format.parse(format.format(personaIMSS.getRegistroSindicato().getFechaRegistro())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
		}
		
		objRetorno.setPersonaFisicaIMSS(null);
		objRetorno.setPersonaMoralIMSS(personaIMSS);
		objRetorno.setTraza(mensajes);
		objRetorno.setCambios(mapaCambios);
		
		/*
		 * Siempre se settea true ya que en el ICA de una persona moral siempre
		 * se consulta al SAT
		 */
		objRetorno.setIndicadorConsultaSAT(true);
		
		return objRetorno;
	}
	
	public Integer consultaActaConstitutivaPersonaMoral(Long cveIdPersona) {
		return personaMoralEntity.consultaActaConstitutivaPersonaMoral(cveIdPersona);
	}
	
	public Integer consultaSindicatoPersonaMoral(Long cveIdPersona) {
		return personaMoralEntity.consultaSindicatoPersonaMoral(cveIdPersona);
	}
	
	public Integer validaAcreditadoPersonaMoral(Long cveIdPersona) {
		return personaMoralEntity.validaIndAcreditado(cveIdPersona);
	}
	
	/**
     * Dada una persona moral creada porque que no se habia encontrado en BDTU
     * Se manda a ser la sincronizacion de datos con el sat 
     * @param idPersona Identificador de la persona moral
     * @param rfc Rfc de la persona moral
     * @throws GestionPatronalBusinessException Error al sincronizar la persona moral
     */
    public void generarICAPersonaMoral(long idPersona, String rfc) throws AfectacionDatosPersonaException {
        
        ICADatosConsulta icaDatosConsulta = new ICADatosConsulta();
        icaDatosConsulta.setPersonaMoral(new Moral());
        icaDatosConsulta.getPersonaMoral().setIdPersona(idPersona);
        icaDatosConsulta.getPersonaMoral().setRfc(rfc);
        icaDatosConsulta.setIndicadorConsultaRENAPO(false);
        icaDatosConsulta.setIndicadorConsultaSAT(true);
        icaDatosConsulta.setIndicadorMostrarPantalla(false);

        try {
            ICADatosRespuesta icaDatosRespuesta = identificarCambios(icaDatosConsulta);
            icaDatosRespuesta = integrarCambios(icaDatosRespuesta);
            
            TramiteCambioInformacionPersona tcp = new TramiteCambioInformacionPersona();
            tcp.setDatosICA(icaDatosRespuesta);
            this.log.debug("Pasa por la clase PersonaMoralBusiness");
            afectarDatosPersonaBusiness.afectarDatos(tcp, null);
        } catch (Exception e) {
            throw new AfectacionDatosPersonaException(e.getMessage());
        }
        
        
    }
    
	/**
     * Dada una persona moral creada porque que no se habia encontrado en BDTU
     * Se manda a ser la sincronizacion de datos con el sat 
     * @param idPersona Identificador de la persona moral
     * @param rfc Rfc de la persona moral
     * @throws GestionPatronalBusinessException Error al sincronizar la persona moral
     */
    public void generarICAPersonaMoral_AP(long idPersona, String rfc) throws AfectacionDatosPersonaException {
        
        ICADatosConsulta icaDatosConsulta = new ICADatosConsulta();
        icaDatosConsulta.setPersonaMoral(new Moral());
        icaDatosConsulta.getPersonaMoral().setIdPersona(idPersona);
        icaDatosConsulta.getPersonaMoral().setRfc(rfc);
        icaDatosConsulta.setIndicadorConsultaRENAPO(false);
        icaDatosConsulta.setIndicadorConsultaSAT(true);
        icaDatosConsulta.setIndicadorMostrarPantalla(false);

        try {
            ICADatosRespuesta icaDatosRespuesta = identificarCambios_AP(icaDatosConsulta);
            icaDatosRespuesta = integrarCambios(icaDatosRespuesta);
            
            TramiteCambioInformacionPersona tcp = new TramiteCambioInformacionPersona();
            tcp.setDatosICA(icaDatosRespuesta);
            this.log.debug("Pasa por la clase PersonaMoralBusiness");
            afectarDatosPersonaBusiness.afectarDatos(tcp, null);
        } catch (Exception e) {
            throw new AfectacionDatosPersonaException(e.getMessage());
        }
        
        
    }    
    
    
	//Se agrega cambio para version de produccion
    @Override
    public void actualizaRazonSocialTipoSociedad(Long cveIdPersona, String nombreRazonSocial, TipoSociedad tipoSociedad) {
        personaMoralEntity.actualizaRazonSocialTipoSociedad(cveIdPersona, nombreRazonSocial, tipoSociedad);
    }
    
    @Override
	public ICADatosRespuesta identificarCambios_AP(ICADatosConsulta parametros)
			throws PersonaNoEncontradaException, RFCNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceSatRfcException, ErrorValidacionDatosConsultaEnEntidaExternaException,
			ComparacionSinDiferenciasException, DatosInsuficientesICAException {
		
    	log.debug("::: Identificando cambios sobre PersonaMoralBusiness.identificarCambiosAP");
		Moral personaIMSS = null;
		Moral personaEntidadSAT = null;
		Map<String, String> mensajes = new HashMap<String, String>();
		Map<String, CambioComparacionEnum> mapaCambios = new HashMap<String, CambioComparacionEnum>();
		ICADatosRespuesta objRetorno = new ICADatosRespuesta();
		ICADatosRespuesta objComparacionPersonas = new ICADatosRespuesta();
		
		if (parametros.getPersonaMoral().getIdPersona() != null) {
			//Cambio para obtener los datos de la PM - INC110489
			//personaIMSS =  personaBusiness.buscarPersonaMoralyDPyDyMCEnIMSS(parametros.getPersonaMoral().getIdPersona());
			log.debug("::: Voy a buscar a la PM :" + parametros.getPersonaMoral().getIdPersona());
			personaIMSS = personaBusiness.buscarPMyDPyDyMCEnIMSS_AP(parametros.getPersonaMoral().getIdPersona());
		} else {
			throw new DatosInsuficientesICAException(
					"No se puede realizar la comparación, ya que no se proporciono el ID de la persona");
		}
		
		if (personaIMSS==null) {
			throw new PersonaNoEncontradaException(parametros.getPersonaMoral().getIdPersona());
		}
		
		log.error("Para ver si llego el tipo de sociedad de la consulta de IMSS" + personaIMSS.getTipoSociedad());
		
		// Consulta a SAT
		if (parametros.getPersonaMoral().getRfc() != null && StringUtils.isNotBlank(parametros.getPersonaMoral().getRfc())) {
			personaEntidadSAT = localizarPersonaMoralEnSATServiceBusiness.localizarPersonaMoralEnSATxRFC(parametros.getPersonaMoral().getRfc());
		} else if (personaIMSS.getRfc()!=null && StringUtils.isNotBlank(personaIMSS.getRfc())) {
			personaEntidadSAT = localizarPersonaMoralEnSATServiceBusiness.localizarPersonaMoralEnSATxRFC(personaIMSS.getRfc());
		} else {
			String mensaje = "No se cuenta con datos necesarios (RFC) para realizar consulta a SAT";
			mensajes.put("MSG05", mensaje);
			throw new DatosInsuficientesICAException(mensaje);
		}
		
		log.error("Para ver si llego el tipo de sociedad de la consulta del SAT" + personaEntidadSAT.getTipoSociedad());
		
		if (personaEntidadSAT != null) {
			
			/* Se recorre la lista de medios de contacto y se setean a los 
			 * atributos
			 */
			MedioContacto medioContacto = null;
			for(int i = 0; i < personaEntidadSAT.getMediosContactoFiscales().size(); i++){
				medioContacto = personaEntidadSAT.getMediosContactoFiscales().get(i);
				if (medioContacto.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoContactoEnum.CORREO_ELECTRONICO.getId())) {
					CorreoElectronico correoSat = new CorreoElectronico();
					correoSat.setCorreo(medioContacto.getDesFormaContacto());
					correoSat.setTipoMedioContacto(medioContacto.getTipoMedioContacto());
					personaEntidadSAT.setCorreoElectronicoFiscalAux(correoSat);
				} else if (medioContacto.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoContactoEnum.TELEFONO_FIJO.getId())) {
					TelefonoFijo telFijoSat = new TelefonoFijo();
					telFijoSat.setNumero(medioContacto.getDesFormaContacto());
					telFijoSat.setTipoMedioContacto(medioContacto.getTipoMedioContacto());
					personaEntidadSAT.setTelefonoFijoFiscalAux(telFijoSat);
				} else if (medioContacto.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoContactoEnum.TELEFONO_MOVIL.getId())) {
					TelefonoMovil telMovilSat = new TelefonoMovil();
					telMovilSat.setNumero(medioContacto.getDesFormaContacto());
					telMovilSat.setTipoMedioContacto(medioContacto.getTipoMedioContacto());
					personaEntidadSAT.setTelefonoMovilFiscalAux(telMovilSat);
				}
			}
			
			/*
			 * Ya que el objeto devuelto por el WS del SAT no trae la fecha de
			 * creaci�n formateada se settea
			 */
			if(personaEntidadSAT.getDatosPersonaSAT() != null){
				if(personaEntidadSAT.getDatosPersonaSAT().getFechaConstitucion() != null){
					personaEntidadSAT.setFechaCreacionFormateada(new SimpleDateFormat(
							"dd/MM/yyyy").format(personaEntidadSAT.getDatosPersonaSAT().getFechaConstitucion()));
				}
			}
						
			objComparacionPersonas = compararPersonaMoralEntidadExternaUtility.compararDosPersonasMorales(personaIMSS, personaEntidadSAT, mensajes);
			
			log.error("Para ver que regreso al comparar los datos de la PM de IMSS y SAT" + objComparacionPersonas.getCambios());
			
			mapaCambios = objComparacionPersonas.getCambios();
			mensajes = objComparacionPersonas.getTraza();
			objRetorno.setPersonaMoralEE(personaEntidadSAT);
		} else {
			personaIMSS = null;
			mensajes.put("MSGE02", "No se cuenta con datos necesarios para realizar la comparaci�n");
		}
				
		// Se checa si existieron diferencias
		if (mensajes.get("MSG02-SAT") != null) {
			
			// Se settea una nueva lista para asegurse de que s�lo se tengan las calificaciones necesarias
			personaIMSS.setPersonaCalificaciones(new ArrayList<PersonaCalificacion>());
			
			// Se agrega la calificaci�n "VALIDADO POR SAT"
			Calificacion calificacion = new Calificacion();
			calificacion.setIdCalificacion(CalificacionEnum.VALIDADO_SAT
					.getCodigo().longValue());
			calificacion.setDescripcion(CalificacionEnum.VALIDADO_SAT
					.getDescripcion());
			PersonaCalificacion personaCalificacion = new PersonaCalificacion();
			personaCalificacion.setCalificacion(calificacion);
			personaCalificacion.setFechaCalificacion(new Date());

			personaIMSS.getPersonaCalificaciones().add(personaCalificacion);
		} else if (personaEntidadSAT != null) {
			// No existieron diferencias, por lo tanto se termina el caso de uso
			throw new ComparacionSinDiferenciasException();
		}
		
		/*
		 * Se settean en nulo los siguientes atributos para que el parseo a JSON
		 * no falle
		 */
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		if(personaIMSS.getDatosPersonaSAT() != null){
			personaIMSS.getDatosPersonaSAT().setPersona(null);
			
			/*
			 * Se da formato compatible, ya que el formato en que se devuelve de la
			 * base de datos no es compatible con el parser de JSON de Spring MVC
			 */
			try {
				personaIMSS.getDatosPersonaSAT().setFechaConstitucion(format.parse(format.format(personaIMSS.getDatosPersonaSAT().getFechaConstitucion())));
				personaIMSS.getDatosPersonaSAT().setFechaInicioOperaciones(format.parse(format.format(personaIMSS.getDatosPersonaSAT().getFechaInicioOperaciones())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
			
		}
		
		if (personaIMSS.getSituacionesSAT() != null
				&& !personaIMSS.getSituacionesSAT().isEmpty()) {
			personaIMSS.getSituacionesSAT().get(0).setPersona(null);
			
			/*
			 * Se da formato compatible, ya que el formato en que se devuelve de la
			 * base de datos no es compatible con el parser de JSON de Spring MVC
			 */
			try {
				personaIMSS.getSituacionesSAT().get(0).setFechaSituacion(format.parse(format.format(personaIMSS.getSituacionesSAT().get(0).getFechaSituacion())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
		}
		
		/*
		 * Se da formato compatible, ya que el formato en que se devuelve de la
		 * base de datos no es compatible con el parser de JSON de Spring MVC
		 */
		if (personaIMSS.getFechaRegistro() != null){
			try {
				personaIMSS.setFechaRegistro(format.parse(format.format(personaIMSS.getFechaRegistro())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
		}
		
		/*
		 * Se da formato compatible, ya que el formato en que se devuelve de la
		 * base de datos no es compatible con el parser de JSON de Spring MVC
		 */
		if (personaIMSS.getFechaModificacion() != null){
			try {
				personaIMSS.setFechaModificacion(format.parse(format.format(personaIMSS.getFechaModificacion())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
		}
		
		/*
		 * Se da formato compatible, ya que el formato en que se devuelve de la
		 * base de datos no es compatible con el parser de JSON de Spring MVC
		 */
		if (personaIMSS.getFechaCreacion() != null){
			try {
				personaIMSS.setFechaCreacion(format.parse(format.format(personaIMSS.getFechaCreacion())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
		}
		
		/*
		 * Se settea vacia la lista de medios de contacto particulares, ya que en
		 * el ICA no se modifican
		 */
		if (personaIMSS.getMediosContacto() != null
				&& !personaIMSS.getMediosContacto().isEmpty()) {
			personaIMSS.getMediosContacto().clear();
		}
				
		/*
		 * Se settea vacia la lista de medios de contacto fiscales, ya que los
		 * medios fiscales resultantes del ICA ya se tiene en los atributos
		 * correspondientes de la persona
		 */
		if (personaIMSS.getMediosContactoFiscales() != null
				&& !personaIMSS.getMediosContactoFiscales().isEmpty()) {
			personaIMSS.getMediosContactoFiscales().clear();
		}
		
		if (personaIMSS.getEscrituraConstitutiva() != null
				&& personaIMSS.getEscrituraConstitutiva().getFechaExpedicion() != null) {
			try {
				personaIMSS.getEscrituraConstitutiva().setFechaExpedicion(format.parse(format.format(personaIMSS.getEscrituraConstitutiva().getFechaExpedicion())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
		}
		
		if (personaIMSS.getRegistroSindicato() != null
				&& personaIMSS.getRegistroSindicato().getFechaRegistro() != null) {
			try {
				personaIMSS.getRegistroSindicato().setFechaRegistro(format.parse(format.format(personaIMSS.getRegistroSindicato().getFechaRegistro())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
		}
		
		objRetorno.setPersonaFisicaIMSS(null);
		objRetorno.setPersonaMoralIMSS(personaIMSS);
		objRetorno.setTraza(mensajes);
		objRetorno.setCambios(mapaCambios);
		
		/*
		 * Siempre se settea true ya que en el ICA de una persona moral siempre
		 * se consulta al SAT
		 */
		objRetorno.setIndicadorConsultaSAT(true);
		
		return objRetorno;
	}
    
	@Override
	public ICADatosRespuesta identificarSoloCambios_AP(ICADatosConsulta parametros)
			throws PersonaNoEncontradaException, RFCNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceSatRfcException, ErrorValidacionDatosConsultaEnEntidaExternaException, DatosInsuficientesICAException {
		log.debug("::: Identificando cambios sobre PersonaMoralBusiness.identificarSoloCambios_AP");
		Moral personaIMSS = null;
		Moral personaEntidadSAT = null;
		Map<String, String> mensajes = new HashMap<String, String>();
		Map<String, CambioComparacionEnum> mapaCambios = new HashMap<String, CambioComparacionEnum>();
		ICADatosRespuesta objRetorno = new ICADatosRespuesta();
		ICADatosRespuesta objComparacionPersonas = new ICADatosRespuesta();
		
		if (parametros.getPersonaMoral().getIdPersona() != null) {
			//Cambio para obtener los datos de la PM - INC110489
			//personaIMSS =  personaBusiness.buscarPersonaMoralyDPyDyMCEnIMSS(parametros.getPersonaMoral().getIdPersona());
			log.debug("::: Voy a buscar a la PM :" + parametros.getPersonaMoral().getIdPersona());
			personaIMSS = personaBusiness.buscarPMyDPyDyMCEnIMSS_AP(parametros.getPersonaMoral().getIdPersona());
			
		} else {
			throw new DatosInsuficientesICAException(
					"No se puede realizar la comparaci�n, ya que no se proporciono el ID de la persona");
		}
		
		if (personaIMSS==null) {
			throw new PersonaNoEncontradaException(parametros.getPersonaMoral().getIdPersona());
		}
		
		// Consulta a SAT
		if (parametros.getPersonaMoral().getRfc() != null && StringUtils.isNotBlank(parametros.getPersonaMoral().getRfc())) {
			personaEntidadSAT = localizarPersonaMoralEnSATServiceBusiness.localizarPersonaMoralEnSATxRFC(parametros.getPersonaMoral().getRfc());
		} else if (personaIMSS.getRfc()!=null && StringUtils.isNotBlank(personaIMSS.getRfc())) {
			personaEntidadSAT = localizarPersonaMoralEnSATServiceBusiness.localizarPersonaMoralEnSATxRFC(personaIMSS.getRfc());
			this.log.debug("Para ver que pase por el metodo identificarSoloCambios y se obtenga tipo de sociedad" + personaEntidadSAT.getTipoSociedad());
		} else {
			String mensaje = "No se cuenta con datos necesarios (RFC) para realizar consulta a SAT";
			mensajes.put("MSG05", mensaje);
			throw new DatosInsuficientesICAException(mensaje);
		}
		
		if (personaEntidadSAT != null) {
			
			/* Se recorre la lista de medios de contacto y se setean a los 
			 * atributos
			 */
			MedioContacto medioContacto = null;
			for(int i = 0; i < personaEntidadSAT.getMediosContactoFiscales().size(); i++){
				medioContacto = personaEntidadSAT.getMediosContactoFiscales().get(i);
				if (medioContacto.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoContactoEnum.CORREO_ELECTRONICO.getId())) {
					CorreoElectronico correoSat = new CorreoElectronico();
					correoSat.setCorreo(medioContacto.getDesFormaContacto());
					correoSat.setTipoMedioContacto(medioContacto.getTipoMedioContacto());
					personaEntidadSAT.setCorreoElectronicoFiscalAux(correoSat);
				} else if (medioContacto.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoContactoEnum.TELEFONO_FIJO.getId())) {
					TelefonoFijo telFijoSat = new TelefonoFijo();
					telFijoSat.setNumero(medioContacto.getDesFormaContacto());
					telFijoSat.setTipoMedioContacto(medioContacto.getTipoMedioContacto());
					personaEntidadSAT.setTelefonoFijoFiscalAux(telFijoSat);
				} else if (medioContacto.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoContactoEnum.TELEFONO_MOVIL.getId())) {
					TelefonoMovil telMovilSat = new TelefonoMovil();
					telMovilSat.setNumero(medioContacto.getDesFormaContacto());
					telMovilSat.setTipoMedioContacto(medioContacto.getTipoMedioContacto());
					personaEntidadSAT.setTelefonoMovilFiscalAux(telMovilSat);
				}
			}
			
			/*
			 * Ya que el objeto devuelto por el WS del SAT no trae la fecha de
			 * creaci�n formateada se settea
			 */
			if(personaEntidadSAT.getDatosPersonaSAT() != null){
				if(personaEntidadSAT.getDatosPersonaSAT().getFechaConstitucion() != null){
					personaEntidadSAT.setFechaCreacionFormateada(new SimpleDateFormat(
							"dd/MM/yyyy").format(personaEntidadSAT.getDatosPersonaSAT().getFechaConstitucion()));
				}
			}
						
			objComparacionPersonas = compararPersonaMoralEntidadExternaUtility.compararDosPersonasMorales(personaIMSS, personaEntidadSAT, mensajes);
			mapaCambios = objComparacionPersonas.getCambios();
			mensajes = objComparacionPersonas.getTraza();
			objRetorno.setPersonaMoralEE(personaEntidadSAT);
		} else {
			personaIMSS = null;
			mensajes.put("MSGE02", "No se cuenta con datos necesarios para realizar la comparaci�n");
		}
				
		// Se checa si existieron diferencias
		if (mensajes.get("MSG02-SAT") != null) {
			
			// Se settea una nueva lista para asegurse de que s�lo se tengan las calificaciones necesarias
			personaIMSS.setPersonaCalificaciones(new ArrayList<PersonaCalificacion>());
			
			// Se agrega la calificaci�n "VALIDADO POR SAT"
			Calificacion calificacion = new Calificacion();
			calificacion.setIdCalificacion(CalificacionEnum.VALIDADO_SAT
					.getCodigo().longValue());
			calificacion.setDescripcion(CalificacionEnum.VALIDADO_SAT
					.getDescripcion());
			PersonaCalificacion personaCalificacion = new PersonaCalificacion();
			personaCalificacion.setCalificacion(calificacion);
			personaCalificacion.setFechaCalificacion(new Date());

			personaIMSS.getPersonaCalificaciones().add(personaCalificacion);
		} else if (personaEntidadSAT != null) {
			// No existieron diferencias, por lo tanto se termina el caso de uso
			objRetorno.setCambios(new HashMap<String, CambioComparacionEnum>());
		}
		
		/*
		 * Se settean en nulo los siguientes atributos para que el parseo a JSON
		 * no falle
		 */
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		if(personaIMSS.getDatosPersonaSAT() != null){
			personaIMSS.getDatosPersonaSAT().setPersona(null);
			
			/*
			 * Se da formato compatible, ya que el formato en que se devuelve de la
			 * base de datos no es compatible con el parser de JSON de Spring MVC
			 */
			try {
				personaIMSS.getDatosPersonaSAT().setFechaConstitucion(format.parse(format.format(personaIMSS.getDatosPersonaSAT().getFechaConstitucion())));
				personaIMSS.getDatosPersonaSAT().setFechaInicioOperaciones(format.parse(format.format(personaIMSS.getDatosPersonaSAT().getFechaInicioOperaciones())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
			
		}
		
		if (personaIMSS.getSituacionesSAT() != null
				&& !personaIMSS.getSituacionesSAT().isEmpty()) {
			personaIMSS.getSituacionesSAT().get(0).setPersona(null);
			
			/*
			 * Se da formato compatible, ya que el formato en que se devuelve de la
			 * base de datos no es compatible con el parser de JSON de Spring MVC
			 */
			try {
				personaIMSS.getSituacionesSAT().get(0).setFechaSituacion(format.parse(format.format(personaIMSS.getSituacionesSAT().get(0).getFechaSituacion())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
		}
		
		/*
		 * Se da formato compatible, ya que el formato en que se devuelve de la
		 * base de datos no es compatible con el parser de JSON de Spring MVC
		 */
		if (personaIMSS.getFechaRegistro() != null){
			try {
				personaIMSS.setFechaRegistro(format.parse(format.format(personaIMSS.getFechaRegistro())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
		}
		
		/*
		 * Se da formato compatible, ya que el formato en que se devuelve de la
		 * base de datos no es compatible con el parser de JSON de Spring MVC
		 */
		if (personaIMSS.getFechaModificacion() != null){
			try {
				personaIMSS.setFechaModificacion(format.parse(format.format(personaIMSS.getFechaModificacion())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
		}
		
		/*
		 * Se da formato compatible, ya que el formato en que se devuelve de la
		 * base de datos no es compatible con el parser de JSON de Spring MVC
		 */
		if (personaIMSS.getFechaCreacion() != null){
			try {
				personaIMSS.setFechaCreacion(format.parse(format.format(personaIMSS.getFechaCreacion())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
		}
		
		/*
		 * Se settea vacia la lista de medios de contacto particulares, ya que en
		 * el ICA no se modifican
		 */
		if (personaIMSS.getMediosContacto() != null
				&& !personaIMSS.getMediosContacto().isEmpty()) {
			personaIMSS.getMediosContacto().clear();
		}
				
		/*
		 * Se settea vacia la lista de medios de contacto fiscales, ya que los
		 * medios fiscales resultantes del ICA ya se tiene en los atributos
		 * correspondientes de la persona
		 */
		if (personaIMSS.getMediosContactoFiscales() != null
				&& !personaIMSS.getMediosContactoFiscales().isEmpty()) {
			personaIMSS.getMediosContactoFiscales().clear();
		}
		
		if (personaIMSS.getEscrituraConstitutiva() != null
				&& personaIMSS.getEscrituraConstitutiva().getFechaExpedicion() != null) {
			try {
				personaIMSS.getEscrituraConstitutiva().setFechaExpedicion(format.parse(format.format(personaIMSS.getEscrituraConstitutiva().getFechaExpedicion())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
		}
		
		if (personaIMSS.getRegistroSindicato() != null
				&& personaIMSS.getRegistroSindicato().getFechaRegistro() != null) {
			try {
				personaIMSS.getRegistroSindicato().setFechaRegistro(format.parse(format.format(personaIMSS.getRegistroSindicato().getFechaRegistro())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
		}
		
		objRetorno.setPersonaFisicaIMSS(null);
		objRetorno.setPersonaMoralIMSS(personaIMSS);
		objRetorno.setTraza(mensajes);
		objRetorno.setCambios(mapaCambios);
		
		/*
		 * Siempre se settea true ya que en el ICA de una persona moral siempre
		 * se consulta al SAT
		 */
		objRetorno.setIndicadorConsultaSAT(true);
		
		return objRetorno;
	}	

    /**
  	 * Expone servicio para revisar la situacion del contribuyente en SAT 
  	 * @param moral
  	 * @return
  	 * @throws ErrorComparacionDatosRENAPOException
  	 **/
	public Moral revisaSituacionContribuyente(Moral moral)
			throws ErrorComparacionDatosSATException, RFCNoLocalizadoEnEntidadExternaException, ClienteWebserviceSatRfcException, ErrorValidacionDatosConsultaEnEntidaExternaException{
		Moral personaEntidadSAT = null;
		//Aqui consultar persona en SAT y aplicar reglas para situacion del contribuyente
		log.debug("::: Buscamos a la PM en SAT: " + moral.getRfc());
		personaEntidadSAT = localizarPersonaMoralEnSATServiceBusiness.localizarPMEnSATxRFCSitCont(moral.getRfc());
		log.debug("::: Situaciones SAT encontradas: " + personaEntidadSAT.getSituacionesSAT().size() + ", se revidara la situacion del contribuyente, " + moral);
		individuoServiceBusiness.revisaSituacionContribuyenteSAT(personaEntidadSAT.getSituacionesSAT(), moral.getRfc(), TipoPersonaEnum.MORAL.getId());
		return personaEntidadSAT;
	}		
	
}
