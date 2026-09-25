package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
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

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.NssRelacionadoVariasPersonasException;
import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ComparacionSinDiferenciasException;
import mx.gob.imss.ctirss.delta.exception.individuo.DatosInsuficientesICAException;
import mx.gob.imss.ctirss.delta.exception.individuo.DatosInsuficientesModificacionException;
import mx.gob.imss.ctirss.delta.exception.individuo.DiferenciasRENAPOContraSAT;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RegistroPersonaFisicaException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.DatosInsuficientesParaConsultaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.utility.CompararPersonaFisicaEntidadExternaUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.utility.PersonaFisicaServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.domicilio.Pais;
import mx.gob.imss.ctirss.delta.model.enums.CambioComparacionEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoAdministracionEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoContactoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CURP;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.AfectarDatosPersonaWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Calificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CalificacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ClavesRenapo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.DatosPersonaSAT;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosConsulta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.SituacionSAT;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCambioInformacionPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFisica;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.AfectarDatosPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessLocal;
import mx.gob.imss.ctirss.gestionpersonas.servicios.entity.PersonaFisicaServiceEntityLocal;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessLocal;

@Stateless(name = "personaFisicaServiceBusiness", mappedName = "personaFisicaServiceBusiness")
public class PersonaFisicaServiceBusiness extends AbstractServiceBusiness implements PersonaFisicaServiceBusinessRemote{
	
    @EJB
    private ComponentesExternosBusinessLocal componentesExternosBusiness;   
    @EJB
    private CalificacionesPersonaBusinessServiceRemote calificacionesPersonaBusinessService;
    @EJB
    private EstadosPersonaFisicaServiceBusinessRemote estadosPersonaFisicaServiceBusiness;
    @EJB
    private IdentificadoresPersonaFisicaServiceBusinessRemote identificadoresPersonaFisicaServiceBusiness;
	@EJB
	private PersonaFisicaServiceUtilityLocal personaFisicaServiceUtility;
	@EJB
	private PersonaFisicaServiceEntityLocal personaFisicaServiceEntity;
	@EJB
	private LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote localizarPersonaFisicaEnRENAPOServiceBusiness;
	@EJB
	private LocalizarPersonaFisicaEnSATServiceBusinessRemote localizarPersonaFisicaEnSATServiceBusiness;
	@EJB
	private CompararPersonaFisicaEntidadExternaUtilityLocal compararPersonaFisicaEntidadExternaUtility;
	@EJB
	private ServiciosPersonaBusinessLocal serviciosPersonaBusiness;
	@EJB
	private AfectarDatosPersonaBusinessRemote afectarDatosPersonaBusiness;
	@EJB
	IndividuoServiceBusinessRemote individuoServiceBusiness;
	
	@Override
	public Boolean isSocio(Long idPersona) {
		
		return personaFisicaServiceEntity.isSocio(idPersona);
	}

	@Override
	public Boolean isPersonaAutorizada(Long idPersona) {
		
		return personaFisicaServiceEntity.isPersonaAutorizada(idPersona);
	}

	@Override
	public Map<String, Boolean> getRolesPorPersona(Long idPersona) {
		
		return componentesExternosBusiness.getRolesPorPersona(idPersona);
	}

	@Override
	public Boolean personaRegistradaComoDerechohabiente(Long idPersona, Boolean activo) {
		
		return componentesExternosBusiness.registradoComoDerechohabiente(idPersona, activo);
	}

	@Override
	public Fisica getPersonaEnRenapo(String curp) throws CURPNoLocalizadoEnEntidadExternaException, 
	ClienteWebserviceRenapoCurpException, ErrorValidacionDatosConsultaEnEntidaExternaException{
		
		Fisica fisica  = localizarPersonaFisicaEnRENAPOServiceBusiness.localizarPersonaFisicaEnRENAPOxCURP(curp);
		
		return fisica;
	}

	/**
	 * 081012
	 * Metodo encargado de actualizar una persona fisica
	 * @param fisica
	 * @return
	 * @throws
	 */
	@Override
	public Fisica actualizar(Fisica fisica){
		
        Fisica fisicaResultado = null;
        if (fisica != null) {

        	try{
        		
        		componentesExternosBusiness.altaDomicilios(fisica);
        		componentesExternosBusiness.altaMediosContacto(fisica);
        		componentesExternosBusiness.altaDocumentosProbatorios(fisica);
        		
//        		personaFisicaResultado = personaEntity.actualizarPersonaFisica(fisica);
        		DitPersona ditPersona = personaFisicaServiceUtility.transformarAEntidad(fisica);
        		personaFisicaServiceUtility.convertirMayusculas(ditPersona);
        		personaFisicaServiceEntity.actualizar(ditPersona);
        		fisicaResultado = personaFisicaServiceUtility.transformarAModelo(ditPersona);
        		
          	}catch(Exception e){
        		e.printStackTrace();
        	} 
        }
        
        return fisicaResultado;
	}

	@Override
	public Fisica registrar(Fisica fisica) throws RegistroPersonaFisicaException{
		
        Fisica fisicaResultado = null;
        if (fisica != null) {

        	try{
        		
        		log.debug("Inicio del registro de personas");
        		
        		this.log.debug("Alta de domicilcios ...");
        		componentesExternosBusiness.altaDomicilios(fisica);
        		this.log.debug("Alta de medios de contacto ...");
        		componentesExternosBusiness.altaMediosContacto(fisica);
        		this.log.debug("Alta de documentos probatorios ...");
        		componentesExternosBusiness.altaDocumentosProbatorios(fisica);
         
        		DitPersona ditPersona = personaFisicaServiceUtility.transformarAEntidad(fisica);
        		personaFisicaServiceUtility.convertirMayusculas(ditPersona);
        		
        		// Aqui se da de alta a la persona fisica junto con sus dependencias, pero no las relaciones con calificaciones ni estados ni identificadores.
        		// Las relaciones que si se crean son las de domicilios, medios de contacto, y documentos probatorios
        		Fisica personaAux = personaFisicaServiceEntity.registrar(ditPersona);
        		fisicaResultado = personaFisicaServiceUtility.transformarAModelo(ditPersona);
        		
        		//Establecemos el id de le persona fisica
        		fisicaResultado.setCveFisica(personaAux.getCveFisica());
        		// Aqui se dan de alta las relaciones de la persona con sus calificaciones, estados, e identificadores
        		calificacionesPersonaBusinessService.registrar(fisicaResultado);
        		estadosPersonaFisicaServiceBusiness.registrar(fisicaResultado);
        		identificadoresPersonaFisicaServiceBusiness.registrar(fisicaResultado);
        		
        		log.debug("Fin del registro de personas");
        		
          	}catch(Exception e){
          		this.log.error(e.getMessage(), e);
          		throw new RegistroPersonaFisicaException(e.getMessage());
        	} 
        }
        
        return fisicaResultado;
	}
	
//  /**
//  * 121012
//  * Metodo encargado de registrar una persona fisica
//  * @param fisica
//  * @return
//  */
//	@Override
//	public Fisica registrar(Fisica fisica){
//		
//     Fisica fisicaResultado = null;
//     if (fisica != null) {
//
//     	try{
//     		
//     		log.debug("Inicio del registro de personas");
//     		
//     		componentesExternosBusiness.altaDomicilios(fisica);
//     		componentesExternosBusiness.altaMediosContacto(fisica);
//     		componentesExternosBusiness.altaDocumentosProbatorios(fisica);
//
//     		calificacionesPersonaBusinessService.registrar(fisica);
//     		estadosPersonaFisicaServiceBusiness.registrar(fisica);
//     		identificadoresPersonaFisicaServiceBusiness.registrar(fisica);
//
//     		DitPersona ditPersona = personaFisicaServiceUtility.transformarAEntidad(fisica);
//     		personaFisicaServiceUtility.convertirMayusculas(ditPersona);
//     		personaFisicaServiceEntity.registrar(ditPersona);
//     		fisicaResultado = personaFisicaServiceUtility.transformarAModelo(ditPersona);
//     		
//     		log.debug("Fin del registro de personas");
//     		
//       	}catch(Exception e){
//     		e.printStackTrace();
//     	} 
//     }
//     
//     return fisicaResultado;
//	}
	
	
	/**
	 * @throws PersonaNoEncontradaException 
	 * @throws ErrorValidacionDatosConsultaEnEntidaExternaException 
	 * @throws ClienteWebserviceRenapoCurpException 
	 * @throws CURPNoLocalizadoEnEntidadExternaException 
	 * @throws ClienteWebserviceSatRfcException 
	 * @throws RFCNoLocalizadoEnEntidadExternaException 
	 * @throws ErrorComparacionDatosRENAPOException 
	 * @throws ComparacionSinDiferenciasException 
	 * @throws DatosInsuficientesICAException	
	 * @throws DiferenciasRENAPOContraSAT 
	 * @throws PersonaFisicaNoEncontradaException 
	 * 
	 */
	public ICADatosRespuesta identificarCambios(ICADatosConsulta parametros) throws PersonaNoEncontradaException, CURPNoLocalizadoEnEntidadExternaException, 
		ClienteWebserviceRenapoCurpException, ErrorValidacionDatosConsultaEnEntidaExternaException, RFCNoLocalizadoEnEntidadExternaException, ClienteWebserviceSatRfcException, ErrorComparacionDatosRENAPOException, ComparacionSinDiferenciasException, DatosInsuficientesICAException, DiferenciasRENAPOContraSAT, PersonaFisicaNoEncontradaException {
		
		Fisica personaIMSS = null;
		Fisica personaEntidadREN = null;
		Fisica personaEntidadSAT = null;
		Map<String, String> mensajes = new HashMap<String, String>();
		Map<String, CambioComparacionEnum> mapaCambios = new HashMap<String, CambioComparacionEnum>();
		ICADatosRespuesta objRetorno = new ICADatosRespuesta();
		ICADatosRespuesta objComparacionPersonas = new ICADatosRespuesta();
		
		if (parametros.getPersonaFisica()!=null && parametros.getPersonaFisica().getIdPersona()!=null) {
			personaIMSS =  serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(parametros.getPersonaFisica().getIdPersona());
		} else {
			throw new DatosInsuficientesICAException("No se puede realizar la comparaci?n, ya que no se proporcion? el ID de la persona");
		}
		
		if (personaIMSS==null) {
			throw new PersonaNoEncontradaException(parametros.getPersonaFisica().getIdPersona());
		}
		
		if (parametros.getIndicadorConsultaRENAPO()) {
			if (parametros.getPersonaFisica().getCurp()!=null && StringUtils.isNotBlank(parametros.getPersonaFisica().getCurp())) {
				personaEntidadREN = localizarPersonaFisicaEnRENAPOServiceBusiness.localizarPersonaFisicaEnRENAPOxCURP(parametros.getPersonaFisica().getCurp());
			} else if (parametros.getPersonaFisica().getNombre()!=null && StringUtils.isNotBlank(parametros.getPersonaFisica().getNombre()) && 
					parametros.getPersonaFisica().getPrimerApellido()!=null && StringUtils.isNotBlank(parametros.getPersonaFisica().getPrimerApellido()) &&
					parametros.getPersonaFisica().getSexo()!=null && parametros.getPersonaFisica().getSexo().getIdSexo()!=null &&
					parametros.getPersonaFisica().getLugarNacimiento()!=null && StringUtils.isNotBlank(parametros.getPersonaFisica().getLugarNacimiento().getClave())) {
				personaEntidadREN = localizarPersonaFisicaEnRENAPOServiceBusiness.localizarPersonaFisicaEnRENAPOxDatosBasicos(parametros.getPersonaFisica());
			} else if (personaIMSS.getCurp()!=null && StringUtils.isNotBlank(personaIMSS.getCurp())) {
				personaEntidadREN = localizarPersonaFisicaEnRENAPOServiceBusiness.localizarPersonaFisicaEnRENAPOxCURP(personaIMSS.getCurp());
			} else if (personaIMSS.getNombre()!=null && StringUtils.isNotBlank(personaIMSS.getNombre()) && 
					personaIMSS.getPrimerApellido()!=null && StringUtils.isNotBlank(personaIMSS.getPrimerApellido()) &&
					personaIMSS.getSexo()!=null && personaIMSS.getSexo().getIdSexo()!=null &&
					personaIMSS.getLugarNacimiento()!=null && StringUtils.isNotBlank(personaIMSS.getLugarNacimiento().getClave())) {
				personaEntidadREN = localizarPersonaFisicaEnRENAPOServiceBusiness.localizarPersonaFisicaEnRENAPOxDatosBasicos(personaIMSS);
			} else {
				String mensaje = "No se cuenta con datos necesarios (CURP ? Datos B?sicos) para realizar consulta a RENAPO";
				mensajes.put("MSG02", mensaje);
				throw new DatosInsuficientesICAException(mensaje);
			}
			
			/*
			 * Se checa el estatus del CURP, s?lo se realiza cuando se desea mostrar
			 * la pantalla
			 */
			if(parametros.getIndicadorMostrarPantalla() && personaEntidadREN != null){
				if(!personaEntidadREN.getCveEstatusRenapo().equals("AN")){
					StringBuffer estatusCURP = new StringBuffer();
					estatusCURP.append("En RENAPO la persona tiene el estatus ");
					estatusCURP.append(personaEntidadREN.getEstatusRenapo());
					
					mensajes.put("ESTATUS_CURP", estatusCURP.toString());
				}
			}
		}
		
				
		if (parametros.getIndicadorConsultaSAT()) {
			if (parametros.getPersonaFisica().getRfc()!=null && StringUtils.isNotBlank(parametros.getPersonaFisica().getRfc())) {
				personaEntidadSAT = localizarPersonaFisicaEnSATServiceBusiness.localizarPersonaFisicaEnSATxRFC(parametros.getPersonaFisica().getRfc());
			} else if (personaIMSS.getRfc()!=null && StringUtils.isNotBlank(personaIMSS.getRfc())) {
				personaEntidadSAT = localizarPersonaFisicaEnSATServiceBusiness.localizarPersonaFisicaEnSATxRFC(personaIMSS.getRfc());
			} else {
				String mensaje = "No se cuenta con datos necesarios (RFC) para realizar consulta a SAT";
				mensajes.put("MSG05", mensaje);
				throw new DatosInsuficientesICAException(mensaje);
			}
		} 
		
		if(personaEntidadSAT != null){
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
		}
		
		if (personaEntidadREN!=null && personaEntidadSAT!=null) {
			boolean entidadesIguales = compararPersonaFisicaEntidadExternaUtility.comparaNombreDePersonaFisica(personaEntidadREN, personaEntidadSAT);
			if (entidadesIguales) {
				
				/*
				 * Se compara el CURP que trae el SAT contra el de RENAPO, en
				 * caso de que sean diferentes, se toma el CURP que regres? el
				 * SAT y con con ese CURP se consulta de nuevo a RENAPO y se
				 * compara el CURP de RENAPO contra el CURP de RENAPO-SAT, en
				 * caso de que existan diferencias o que segunda consulta a
				 * RENAPO falle se notifica en la respuesta del caso de uso
				 * (Tambien se considera como diferencia en caso de que el SAT no traiga CURP)
				 */
				if (!personaEntidadREN.getCurp().equals(
						personaEntidadSAT.getCurp())) {
					if (StringUtils.isBlank(personaEntidadSAT.getCurp())) {
						this.log.warn("Existe Diferencia en CURP de entidades debido a que la CURP proveniente del SAT es vacia ");
						mensajes.put("DiferenciasRENAPOContraSAT", "Existe Diferencia en CURP de entidades");
					} else {
						try{
							String curpSAT = localizarPersonaFisicaEnRENAPOServiceBusiness
									.localizarPersonaFisicaEnRENAPOxCURP(personaEntidadSAT.getCurp()).getCurp();

							if(!personaEntidadREN.getCurp().equals(curpSAT)){
								mensajes.put("DiferenciasRENAPOContraSAT", "Existe Diferencia en CURP de entidades");
								
							}
						} catch (CURPNoLocalizadoEnEntidadExternaException e){
							this.log.warn("Existe Diferencia en CURP de entidades debido a : " + e.getMessage());
							mensajes.put("DiferenciasRENAPOContraSAT", "Existe Diferencia en CURP de entidades");
						} catch (ClienteWebserviceRenapoCurpException e) {
							this.log.warn("Existe Diferencia en CURP de entidades debido a : " + e.getMessage());
							mensajes.put("DiferenciasRENAPOContraSAT", "Existe Diferencia en CURP de entidades");
						} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e){
							this.log.warn("Existe Diferencia en CURP de entidades debido a : " + e.getMessage());
							mensajes.put("DiferenciasRENAPOContraSAT", "Existe Diferencia en CURP de entidades");
						}
					}
				}
				
				/*
				 * Se integran los campos de la entidad del SAT con la de RENAPO
				 * para tener una sola entidad.
				 */
				personaEntidadREN.setRfc(personaEntidadSAT.getRfc());
				personaEntidadREN.setDomicilioFiscal(personaEntidadSAT.getDomicilioFiscal());
				personaEntidadREN.setTelefonoFijoFiscal(personaEntidadSAT.getTelefonoFijoFiscal());
				personaEntidadREN.setTelefonoMovilFiscal(personaEntidadSAT.getTelefonoMovilFiscal());
				personaEntidadREN.setCorreoElectronicoFiscal(personaEntidadSAT.getCorreoElectronicoFiscal());
				personaEntidadREN.setSituacionesSAT(personaEntidadSAT.getSituacionesSAT());
				personaEntidadREN.setDatosPersonaSAT(personaEntidadSAT.getDatosPersonaSAT());
				
				objComparacionPersonas = compararPersonaFisicaEntidadExternaUtility
						.compararDosPersonasFisicas(personaIMSS,
								personaEntidadREN, mensajes,
								parametros.getIndicadorConsultaRENAPO(),
								parametros.getIndicadorConsultaSAT());
				
				mapaCambios = objComparacionPersonas.getCambios();
				mensajes = objComparacionPersonas.getTraza();
				objRetorno.setPersonaFisicaEE(personaEntidadREN);
			} else {
				DiferenciasRENAPOContraSAT exception = new DiferenciasRENAPOContraSAT();
				mensajes.put("MSG08", exception.getMessage());
				throw exception;
			}
			
		} else if (personaEntidadREN!=null) {
			objComparacionPersonas = compararPersonaFisicaEntidadExternaUtility
					.compararDosPersonasFisicas(personaIMSS, personaEntidadREN,
							mensajes, parametros.getIndicadorConsultaRENAPO(),
							parametros.getIndicadorConsultaSAT());
			mapaCambios = objComparacionPersonas.getCambios();
			mensajes = objComparacionPersonas.getTraza();
			objRetorno.setPersonaFisicaEE(personaEntidadREN);
		} else if (personaEntidadSAT!=null) {
			objComparacionPersonas = compararPersonaFisicaEntidadExternaUtility
					.compararDosPersonasFisicas(personaIMSS, personaEntidadSAT,
							mensajes, parametros.getIndicadorConsultaRENAPO(),
							parametros.getIndicadorConsultaSAT());
			mapaCambios = objComparacionPersonas.getCambios();
			mensajes = objComparacionPersonas.getTraza();
			objRetorno.setPersonaFisicaEE(personaEntidadSAT);
		} else {
			personaIMSS = null;
			throw new DatosInsuficientesICAException("No se seleccion? ninguna entidad externa para realizar la comparaci?n.");
		}
				
		// Se checa si existieron diferencias
		if (mensajes.get("MSG01-RENAPO") != null || mensajes.get("MSG02-SAT") != null) {
			
			// Se settea una nueva lista para asegurse de que s?lo se tengan las calificaciones necesarias
			personaIMSS.setPersonaCalificaciones(new ArrayList<PersonaCalificacion>());
			
			if (mensajes.get("MSG01-RENAPO") != null && parametros.getIndicadorConsultaRENAPO()) {	
				// Se agrega la calificaci?n "VALIDADO POR RENAPO"
				Calificacion calificacion = new Calificacion();
				calificacion.setIdCalificacion(CalificacionEnum.VALIDADO_RENAPO
						.getCodigo().longValue());
				calificacion.setDescripcion(CalificacionEnum.VALIDADO_RENAPO.getDescripcion());
				PersonaCalificacion personaCalificacion = new PersonaCalificacion();
				personaCalificacion.setCalificacion(calificacion);
				personaCalificacion.setFechaCalificacion(new Date());
	
				personaIMSS.getPersonaCalificaciones().add(personaCalificacion);
			} else {
				/* 
				 * Se quita el mensaje de cambios RENAPO, ya que aunque
				 * el servico que compara devuelve diferencias, originalmente
				 * no se solicit? realizar la comparaci?n de datos RENPAO  
				 */
				mensajes.remove("MSG01-RENAPO");
			}
			if (mensajes.get("MSG02-SAT") != null && parametros.getIndicadorConsultaSAT()) {				
				// Se agrega la calificaci?n "VALIDADO POR SAT"
				Calificacion calificacion = new Calificacion();
				calificacion.setIdCalificacion(CalificacionEnum.VALIDADO_SAT
						.getCodigo().longValue());
				calificacion.setDescripcion(CalificacionEnum.VALIDADO_SAT.getDescripcion());
				PersonaCalificacion personaCalificacion = new PersonaCalificacion();
				personaCalificacion.setCalificacion(calificacion);
				personaCalificacion.setFechaCalificacion(new Date());
	
				personaIMSS.getPersonaCalificaciones().add(personaCalificacion);
			} else {
				/* 
				 * Se quita el mensaje de cambios SAT, ya que aunque
				 * el servico que compara devuelve diferencias, originalmente
				 * no se solicit? realizar la comparaci?n de datos SAT  
				 */
				mensajes.remove("MSG02-SAT");
			}
		}else if (personaEntidadREN != null || personaEntidadSAT != null){
			//No existieron diferencias, por lo tanto se termina el caso de uso
			throw new ComparacionSinDiferenciasException();
		}
		
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		/*
		 * Se da formato compatible, ya que el formato en que se devuelve de la
		 * base de datos no es compatible con el parser de JSON de Spring MVC
		 */
		if(personaIMSS.getFechaNacimiento() != null){
			try {
				personaIMSS.setFechaNacimiento(format.parse(format.format(personaIMSS.getFechaNacimiento())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
		}
		
		/*
		 * Se da formato compatible, ya que el formato en que se devuelve de la
		 * base de datos no es compatible con el parser de JSON de Spring MVC
		 */
		if(personaIMSS.getFechaDefuncion() != null){
			try {
				personaIMSS.setFechaDefuncion(format.parse(format.format(personaIMSS.getFechaDefuncion())));
			} catch (ParseException e) {
				this.log.warn(e);
			}
		}
		
		if(personaIMSS.getDatosPersonaSAT() != null){
			personaIMSS.getDatosPersonaSAT().setPersona(null);
			
			/*
			 * Se da formato compatible, ya que el formato en que se devuelve de la
			 * base de datos no es compatible con el parser de JSON de Spring MVC
			 */
			try {
				if(personaIMSS.getDatosPersonaSAT().getFechaConstitucion()!=null){
					personaIMSS.getDatosPersonaSAT().setFechaConstitucion(format.parse(format.format(personaIMSS.getDatosPersonaSAT().getFechaConstitucion())));
				}
				if(personaIMSS.getDatosPersonaSAT().getFechaInicioOperaciones()!=null){
					personaIMSS.getDatosPersonaSAT().setFechaInicioOperaciones(format.parse(format.format(personaIMSS.getDatosPersonaSAT().getFechaInicioOperaciones())));
				}
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
		 * Se settea vacia la lista de medios de contacto particulares, ya que en
		 * el ICA no se modifican
		 */
		if (personaIMSS.getMediosContacto() != null
				&& !personaIMSS.getMediosContacto().isEmpty()) {
			personaIMSS.getMediosContacto().clear();
		}
		
		/*
		 * Se settea vacia la lista de documentos probatorios, ya que el
		 * documento probatorio resultante del ICA ya se tiene en el atributo
		 * correspondiente de la persona
		 */
		if (personaIMSS.getDocumentosProbatorios() != null
				&& !personaIMSS.getDocumentosProbatorios().isEmpty()) {
			personaIMSS.getDocumentosProbatorios().clear();
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
		
		objRetorno.setPersonaFisicaIMSS(personaIMSS);
		objRetorno.setTraza(mensajes);
		objRetorno.setCambios(mapaCambios);
		
		objRetorno.setIndicadorConsultaRENAPO(parametros.getIndicadorConsultaRENAPO());
		objRetorno.setIndicadorConsultaSAT(parametros.getIndicadorConsultaSAT());
		
		return objRetorno;
	}

	@Override
	public ICADatosRespuesta integrarCambios(ICADatosRespuesta icaDatosRespuesta){

		Map<String, CambioComparacionEnum> diferencias = icaDatosRespuesta.getCambios();
		Fisica entidadImss = icaDatosRespuesta.getPersonaFisicaIMSS();
		Fisica entidadExterna = icaDatosRespuesta.getPersonaFisicaEE();
				
		if (icaDatosRespuesta.getTraza().get("MSG01-RENAPO") != null
				|| icaDatosRespuesta.getTraza().get("MSG02-SAT") != null) {
			
			if(diferencias.get("nombre") != null && (diferencias.get("nombre") == CambioComparacionEnum.CAMBIO || 
					diferencias.get("nombre") == CambioComparacionEnum.NUEVO)){
				entidadImss.setNombre(entidadExterna.getNombre());
			}
			if(diferencias.get("primerApellido") != null && (diferencias.get("primerApellido") == CambioComparacionEnum.CAMBIO ||
					diferencias.get("primerApellido") == CambioComparacionEnum.NUEVO)){
				entidadImss.setPrimerApellido(entidadExterna.getPrimerApellido());
			}
			if(diferencias.get("segundoApellido") != null && (diferencias.get("segundoApellido") == CambioComparacionEnum.CAMBIO ||
					diferencias.get("segundoApellido") == CambioComparacionEnum.NUEVO)){
				entidadImss.setSegundoApellido(entidadExterna.getSegundoApellido());
			}
			
			if (icaDatosRespuesta.getTraza().get("MSG01-RENAPO") != null) {
				
				if(diferencias.get("curp") != null && (diferencias.get("curp") == CambioComparacionEnum.CAMBIO ||
						diferencias.get("curp") == CambioComparacionEnum.NUEVO)){
					entidadImss.setCurp(entidadExterna.getCurp());
				}
				if(diferencias.get("sexo") != null && (diferencias.get("sexo") == CambioComparacionEnum.CAMBIO ||
						diferencias.get("sexo") == CambioComparacionEnum.NUEVO)){
					entidadImss.setSexo(entidadExterna.getSexo());
				}
				if(diferencias.get("fechaNacimiento") != null && (diferencias.get("fechaNacimiento") == CambioComparacionEnum.CAMBIO ||
						diferencias.get("fechaNacimiento") == CambioComparacionEnum.NUEVO)){
					entidadImss.setFechaNacimiento(entidadExterna.getFechaNacimiento());
				}
				if(diferencias.get("lugarNacimiento") != null && (diferencias.get("lugarNacimiento") == CambioComparacionEnum.CAMBIO ||
						diferencias.get("lugarNacimiento") == CambioComparacionEnum.NUEVO)){
					entidadImss.setLugarNacimiento(entidadExterna.getLugarNacimiento());
					
					/*
					 *  Se checa si el lugar de nacimiento es NACIDO EN EL EXTRANJERO (98) o
					 *  SERVICIO EXTERIOR MEXICANO (99), de ser as?, es necesario cambiar el pa?s
					 */
					if (entidadExterna.getLugarNacimiento().getClave().equals(ClavesRenapo.FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.get("NE").toString()) || 
							entidadExterna.getLugarNacimiento().getClave().equals(ClavesRenapo.FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.get("SE").toString())) {
						Pais pais = new Pais();
						pais.setIdPais(2);
						entidadImss.setPais(pais);
					}
				}
				if(diferencias.get("nacionalidad") != null && (diferencias.get("nacionalidad") == CambioComparacionEnum.CAMBIO ||
						diferencias.get("nacionalidad") == CambioComparacionEnum.NUEVO)){
					entidadImss.setPais(entidadExterna.getPais());
				}
				
				//Documentos probatorios
				if(diferencias.get("actaNacimiento") != null && (diferencias.get("actaNacimiento") == CambioComparacionEnum.CAMBIO
							|| diferencias.get("actaNacimiento") == CambioComparacionEnum.NUEVO)) {
					
					Nacimiento nacimiento = entidadImss.getActaNacimiento();
					Nacimiento nacimientoExterna = entidadExterna.getActaNacimiento();
						
					/* Se checa si las actas a comparar son nulas, si es as?
					 * se buscan dentro de la lista de documentos probatorios 
					 */
					if (nacimiento == null) {
						if (entidadImss.getDocumentosProbatorios() != null) {
							for (DocumentoProbatorio documento : entidadImss.getDocumentosProbatorios()) {
								if (documento instanceof Nacimiento) {
									nacimiento = (Nacimiento) documento;
									break;
								}
							}
						}
					}
					
					if (nacimientoExterna == null) {
						if (entidadExterna.getDocumentosProbatorios() != null) {
							for (DocumentoProbatorio documento : entidadExterna.getDocumentosProbatorios()) {
								if (documento instanceof Nacimiento) {
									nacimientoExterna = (Nacimiento) documento;
									break;
								}
							}
						}
					}
					
					if (diferencias.get("actaNacimiento") == CambioComparacionEnum.CAMBIO) {
						if (diferencias.get("actaNacimiento.anio") != null
								&& diferencias.get("actaNacimiento.anio") != CambioComparacionEnum.NINGUNO) {
							nacimiento.setAnio(nacimientoExterna.getAnio());
						}
						if (diferencias.get("actaNacimiento.tomo") != null
								&& diferencias.get("actaNacimiento.tomo") != CambioComparacionEnum.NINGUNO){
							nacimiento.setTomo(nacimientoExterna.getTomo());
						}
						if (diferencias.get("actaNacimiento.crip") != null
								&& diferencias.get("actaNacimiento.crip") != CambioComparacionEnum.NINGUNO){
							nacimiento.setCrip(nacimientoExterna.getCrip());
						}
						if (diferencias.get("actaNacimiento.foja") != null
								&& diferencias.get("actaNacimiento.foja") != CambioComparacionEnum.NINGUNO){
							nacimiento.setNoFoja(nacimientoExterna.getNoFoja());
						}
						if (diferencias.get("actaNacimiento.libro") != null
								&& diferencias.get("actaNacimiento.libro") != CambioComparacionEnum.NINGUNO){
							nacimiento.setNoLibro(nacimientoExterna.getNoLibro());
						}
						if (diferencias.get("actaNacimiento.acta") != null
								&& diferencias.get("actaNacimiento.acta") != CambioComparacionEnum.NINGUNO){
							nacimiento.setNoActa(nacimientoExterna.getNoActa());
						}
						if ((diferencias.get("actaNacimiento.municipio") != null
								&& diferencias.get("actaNacimiento.municipio") != CambioComparacionEnum.NINGUNO)
								|| (diferencias.get("actaNacimiento.entidad") != null
										&& diferencias.get("actaNacimiento.entidad") != CambioComparacionEnum.NINGUNO)) {
							nacimiento.setMunicipio(nacimientoExterna.getMunicipio());
						}
						nacimiento.setEstadoAdministracionDocto(EstadoAdministracionEnum.MODIFICADO);
						
						DocumentoPorTipo documentoPorTipo = new DocumentoPorTipo();
						documentoPorTipo.setIdDocumentoPorTipo(DocumentoPorTipoEnum.ACTA_NACIMIENTO.getId());
						nacimiento.setDocumentoPorTipo(documentoPorTipo);
					} else if (diferencias.get("actaNacimiento") == CambioComparacionEnum.NUEVO) {
						DocumentoPorTipo documentoPorTipo = new DocumentoPorTipo();
						documentoPorTipo.setIdDocumentoPorTipo(DocumentoPorTipoEnum.ACTA_NACIMIENTO.getId());
						nacimientoExterna.setDocumentoPorTipo(documentoPorTipo);
						
						entidadImss.setActaNacimientoAux(nacimientoExterna);
						entidadImss.getActaNacimiento().setEstadoAdministracionDocto(EstadoAdministracionEnum.NUEVO);
					}
				} else if(diferencias.get("documentoMigratorio") != null) {
					
					CURP docAux = entidadExterna.getDocumentoMigratorio();
					// Se settea a nulo el municipio ya que no nos interesa
					docAux.setMunicipio(null);
					
					if (diferencias.get("documentoMigratorio") == CambioComparacionEnum.CAMBIO){
						CURP docImss = entidadImss.getDocumentoMigratorio();
						
						if (diferencias.get("documentoMigratorio.numRegExtranjeros") != null){
							docImss.setNumFolioExtranjero(docAux.getNumFolioExtranjero());
						} 
						if (diferencias.get("documentoMigratorio.numExpediente") != null){
							docImss.setNoActa(docAux.getNoActa());
						}
						docImss.setEstadoAdministracionDocto(EstadoAdministracionEnum.MODIFICADO);
					} else if (diferencias.get("documentoMigratorio") == CambioComparacionEnum.NUEVO){
						docAux.setEstadoAdministracionDocto(EstadoAdministracionEnum.NUEVO);
						entidadImss.setDocumentoMigratorioAux(docAux);
			
					}
				} else if(diferencias.get("cartaNaturalizacion") != null) {
					
					CURP docAux = entidadExterna.getCartaNaturalizacion();
					// Se settea a nulo el municipio ya que no nos interesa
					docAux.setMunicipio(null);
							
					if (diferencias.get("cartaNaturalizacion") == CambioComparacionEnum.CAMBIO){
						CURP docImss = entidadImss.getCartaNaturalizacion();
						
						if (diferencias.get("cartaNaturalizacion.anio") != null){
							docImss.setAnioRegistro(docAux.getAnioRegistro());
						} 
						if (diferencias.get("cartaNaturalizacion.folio") != null){
							docImss.setNumFolioExtranjero(docAux.getNumFolioExtranjero());
						}
						docImss.setEstadoAdministracionDocto(EstadoAdministracionEnum.MODIFICADO);
					} else if (diferencias.get("cartaNaturalizacion") == CambioComparacionEnum.NUEVO){
						docAux.setEstadoAdministracionDocto(EstadoAdministracionEnum.NUEVO);
						entidadImss.setCartaNaturalizacionAux(docAux);
					}
				} else if(diferencias.get("numUnicoExtranjero") != null) {
					CURP docAux = entidadExterna.getNumeroUnicoExtranjero();
					// Se settea a nulo el municipio ya que no nos interesa
					docAux.setMunicipio(null);
							
					if (diferencias.get("numUnicoExtranjero") == CambioComparacionEnum.CAMBIO){
						CURP docImss = entidadImss.getNumeroUnicoExtranjero();
						
						if (diferencias.get("numUnicoExtranjero.folio") != null){
							docImss.setNumFolioExtranjero(docAux.getNumFolioExtranjero());
						}
						docImss.setEstadoAdministracionDocto(EstadoAdministracionEnum.MODIFICADO);
					} else if (diferencias.get("numUnicoExtranjero") == CambioComparacionEnum.NUEVO){
						docAux.setEstadoAdministracionDocto(EstadoAdministracionEnum.NUEVO);
						entidadImss.setNumeroUnicoExtranjeroAux(docAux);
					}			
				} else if(diferencias.get("certificadoNacionalidad") != null) {
					CURP docAux = entidadExterna.getCertificadoNacionalidadMexicana();
					// Se settea a nulo el municipio ya que no nos interesa
					docAux.setMunicipio(null);
					
					if (diferencias.get("certificadoNacionalidad") == CambioComparacionEnum.CAMBIO){
						CURP docImss = entidadImss.getCertificadoNacionalidadMexicana();
						
						if (diferencias.get("certificadoNacionalidad.anio") != null){
							docImss.setAnioRegistro(docAux.getAnioRegistro());
						}
						if (diferencias.get("certificadoNacionalidad.folio") != null){
							docImss.setNumFolioExtranjero(docAux.getNumFolioExtranjero());
						}
						docImss.setEstadoAdministracionDocto(EstadoAdministracionEnum.MODIFICADO);
					} else if (diferencias.get("certificadoNacionalidad") == CambioComparacionEnum.NUEVO){
						docAux.setEstadoAdministracionDocto(EstadoAdministracionEnum.NUEVO);
						entidadImss.setCertificadoNacionalidadMexicanaAux(docAux);
					}
				} else if(diferencias.get("oficioRefugiado") != null) {
					CURP docAux = entidadExterna.getOficioSolicitanteRefugiado();
					// Se settea a nulo el municipio ya que no nos interesa
					docAux.setMunicipio(null);
							
					if (diferencias.get("oficioRefugiado") == CambioComparacionEnum.CAMBIO){
						CURP docImss = entidadImss.getOficioSolicitanteRefugiado();
						
						if (diferencias.get("oficioRefugiado.folio") != null){
							docImss.setNumFolioExtranjero(docAux.getNumFolioExtranjero());
						}
						docImss.setEstadoAdministracionDocto(EstadoAdministracionEnum.MODIFICADO);
					} else if (diferencias.get("oficioRefugiado") == CambioComparacionEnum.NUEVO){
						docAux.setEstadoAdministracionDocto(EstadoAdministracionEnum.NUEVO);
						entidadImss.setOficioSolicitanteRefugiadoAux(docAux);
					}
				} else if(diferencias.get("formaMigratoria") != null) {
					CURP docAux = entidadExterna.getFormaMigratoriaTurista();
					// Se settea a nulo el municipio ya que no nos interesa
					docAux.setMunicipio(null);
							
					if (diferencias.get("formaMigratoria") == CambioComparacionEnum.CAMBIO){
						CURP docImss = entidadImss.getFormaMigratoriaTurista();
						
						if (diferencias.get("formaMigratoria.folio") != null){
							docImss.setNumFolioExtranjero(docAux.getNumFolioExtranjero());
						}
						docImss.setEstadoAdministracionDocto(EstadoAdministracionEnum.MODIFICADO);
					} else if (diferencias.get("formaMigratoria") == CambioComparacionEnum.NUEVO){
						docAux.setEstadoAdministracionDocto(EstadoAdministracionEnum.NUEVO);
						entidadImss.setFormaMigratoriaTuristaAux(docAux);
					}					
				} 
			}
			
			if (icaDatosRespuesta.getTraza().get("MSG02-SAT") != null) {
				if(diferencias.get("rfc") != null && (diferencias.get("rfc") == CambioComparacionEnum.CAMBIO ||
						diferencias.get("rfc") == CambioComparacionEnum.NUEVO)){
					entidadImss.setRfc(entidadExterna.getRfc());
				}
				
				if ((diferencias.get("fechaConstitucion") != null && (diferencias
						.get("fechaConstitucion") == CambioComparacionEnum.CAMBIO || diferencias
						.get("fechaConstitucion") == CambioComparacionEnum.NUEVO))
						|| (diferencias.get("fechaInicioOperaciones") != null)
						&& (diferencias.get("fechaInicioOperaciones") == CambioComparacionEnum.CAMBIO || diferencias
								.get("fechaInicioOperaciones") == CambioComparacionEnum.NUEVO)) {
					
					if(entidadImss.getDatosPersonaSAT() == null) {
						entidadImss.setDatosPersonaSAT(new DatosPersonaSAT());
					}
					
					if (diferencias.get("fechaConstitucion") == CambioComparacionEnum.CAMBIO || diferencias
							.get("fechaConstitucion") == CambioComparacionEnum.NUEVO) {
						if (entidadExterna.getDatosPersonaSAT() != null
								&& entidadExterna.getDatosPersonaSAT().getFechaConstitucion() != null) {
							entidadImss.getDatosPersonaSAT().setFechaConstitucion(entidadExterna.getDatosPersonaSAT().getFechaConstitucion());
						} else {
							this.log.warn("Se encontr\u00f3 diferencia en la fecha de constituci\u00f3n, pero no se puede integrar ya que es nula");
						}
					}
					
					if (diferencias.get("fechaInicioOperaciones") == CambioComparacionEnum.CAMBIO || diferencias
							.get("fechaInicioOperaciones") == CambioComparacionEnum.NUEVO) {
						if (entidadExterna.getDatosPersonaSAT() != null
								&& entidadExterna.getDatosPersonaSAT().getFechaInicioOperaciones() != null) {
							entidadImss.getDatosPersonaSAT().setFechaInicioOperaciones(entidadExterna.getDatosPersonaSAT().getFechaInicioOperaciones());
						} else {
							this.log.warn("Se encontr\u00f3 diferencia en la fecha de inicio de operaciones, pero no se puede integrar ya que es nula");
						}
						
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
								diferencias.get("inmueble") == CambioComparacionEnum.NUEVO)) || 
								(diferencias.get("entidad") != null && (diferencias.get("entidad") == CambioComparacionEnum.CAMBIO || 
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
					// Si est? nula se crea una nueva
					entidadImss.setMediosContactoFiscales(new ArrayList<MedioContacto>());
				} else {
					/*
					 * Si ya tiene la lista, se limpia para agregar los medios de
					 * contacto como instancias de MedioContacto
					 */
					entidadImss.getMediosContactoFiscales().clear();
				}
				
				if(diferencias.get("correoElectronico") != null && (diferencias.get("correoElectronico") == CambioComparacionEnum.CAMBIO || 
						diferencias.get("correoElectronico") == CambioComparacionEnum.NUEVO)){
					
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
					tipoMedioContacto.setDescripcion("Correo electr?nico");
					medio.setTipoMedioContacto(tipoMedioContacto);
					medio.setEstadoAdministracionMedioContacto(entidadExterna.getCorreoElectronicoFiscal().getEstadoAdministracionMedioContacto());
					
					entidadImss.getMediosContactoFiscales().add(medio);
				} else if (entidadImss.getCorreoElectronicoFiscal() != null){
					MedioContacto medio = new MedioContacto();
					medio.setClave(entidadImss.getCorreoElectronicoFiscal().getClave());
					medio.setDesFormaContacto(entidadImss.getCorreoElectronicoFiscal().getCorreo());
					TipoMedioContacto tipoMedioContacto = new TipoMedioContacto();
					tipoMedioContacto.setIdTipoMedioContacto(TipoContactoEnum.CORREO_ELECTRONICO.getId());
					tipoMedioContacto.setDescripcion("Correo electr?nico");
					medio.setTipoMedioContacto(tipoMedioContacto);
					
					entidadImss.getMediosContactoFiscales().add(medio);
				}
				
				if(diferencias.get("telefonoFijo") != null && 
						(diferencias.get("telefonoFijo") == CambioComparacionEnum.CAMBIO || diferencias.get("telefonoFijo") == CambioComparacionEnum.NUEVO)){
					
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
					tipoMedioContacto.setDescripcion("Tel?fono Fijo");
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
					tipoMedioContacto.setDescripcion("Tel?fono Fijo");
					medio.setTipoMedioContacto(tipoMedioContacto);
					
					entidadImss.getMediosContactoFiscales().add(medio);
				}
				
				if(diferencias.get("telefonoMovil") != null && (diferencias.get("telefonoMovil") == CambioComparacionEnum.CAMBIO || 
						diferencias.get("telefonoMovil") == CambioComparacionEnum.NUEVO)){
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
					tipoMedioContacto.setDescripcion("Tel?fono M?vil");
					medio.setTipoMedioContacto(tipoMedioContacto);
					medio.setEstadoAdministracionMedioContacto(entidadExterna.getTelefonoMovilFiscal().getEstadoAdministracionMedioContacto());
					
					entidadImss.getMediosContactoFiscales().add(medio);
				} else if (entidadImss.getTelefonoMovilFiscal() != null){
					MedioContacto medio = new MedioContacto();
					medio.setClave(entidadImss.getTelefonoMovilFiscal().getClave());
					medio.setDesFormaContacto(entidadImss.getTelefonoMovilFiscal().getNumero());
					TipoMedioContacto tipoMedioContacto = new TipoMedioContacto();
					tipoMedioContacto.setIdTipoMedioContacto(TipoContactoEnum.TELEFONO_MOVIL.getId());
					tipoMedioContacto.setDescripcion("Tel?fono M?vil");
					medio.setTipoMedioContacto(tipoMedioContacto);
					
					entidadImss.getMediosContactoFiscales().add(medio);
				}				
				
				if(diferencias.get("situacionSAT") != null && (diferencias.get("situacionSAT") == CambioComparacionEnum.CAMBIO ||
						diferencias.get("situacionSAT") == CambioComparacionEnum.NUEVO)){
					
					/*
					 * Se crea una lista nueva para garantizar que se tenga la
					 * situaci?n que se necesita
					 */					
					SituacionSAT situacionSAT = entidadExterna.getSituacionesSAT().get(0);
					
					List<SituacionSAT> situaciones = new ArrayList<SituacionSAT>();
					situaciones.add(situacionSAT);
					
					entidadImss.setSituacionesSAT(situaciones);
				}
			}
		}

		return icaDatosRespuesta;
	}

	@Override
	public ICADatosRespuesta compararDosPersonasFisicas(Fisica persona1,
			Fisica persona2, Map<String, String> mensajes)
			throws ErrorComparacionDatosRENAPOException {

		ICADatosRespuesta icaDatosRespuesta = this.compararPersonaFisicaEntidadExternaUtility
				.compararDosPersonasFisicas(persona1, persona2, mensajes, true, true);

		return icaDatosRespuesta;
	}

	@Override
	public MDMDatosEntrada modificacionManual(MDMDatosEntrada mdmDatosEntrada)
			throws DatosInsuficientesModificacionException,
			PersonaNoEncontradaException, PersonaFisicaNoEncontradaException {

		log.debug("Entrando al servicio de modificacion manual de la persona fisica [idPersona = "
				+ mdmDatosEntrada.getPersonaFisica().getIdPersona() + "]");
		
		Fisica personaIMSS = null;
		
		if (mdmDatosEntrada.getIndCapturaNombre()
				|| mdmDatosEntrada.getIndCapturaCURP()
				|| mdmDatosEntrada.getIndCapturaSexo()
				|| mdmDatosEntrada.getIndCapturaFechaNacimiento()
				|| mdmDatosEntrada.getIndCapturaLugarNacimiento()
				|| mdmDatosEntrada.getIndCapturaDocumentoProbatorio()) {
			mdmDatosEntrada.setIndCapturaDatosRENAPO(Boolean.TRUE);
		}else{
			mdmDatosEntrada.setIndCapturaDatosRENAPO(Boolean.FALSE);
		}
		if(mdmDatosEntrada.getIndCapturaRFC() ||
				mdmDatosEntrada.getIndCapturaDomicilioFiscal() ||
				mdmDatosEntrada.getIndCapturaMediosContactoFiscales()){
			mdmDatosEntrada.setIndCapturaDatosSAT(Boolean.TRUE);
		}else{
			mdmDatosEntrada.setIndCapturaDatosSAT(Boolean.FALSE);
		}
		if (mdmDatosEntrada.getIndCapturaDomicilioParticular()
				|| mdmDatosEntrada.getIndCapturaMediosContactoParticular()) {
			mdmDatosEntrada.setIndCapturaDatosComplementarios(Boolean.TRUE);
		}else{
			mdmDatosEntrada.setIndCapturaDatosComplementarios(Boolean.FALSE);
		}
		
		if (!mdmDatosEntrada.getIndCapturaDatosRENAPO()
				&& !mdmDatosEntrada.getIndCapturaDatosSAT()
				&& !mdmDatosEntrada.getIndCapturaDatosComplementarios()) {
			throw new DatosInsuficientesModificacionException(
					"No se puede realizar la modificaci?n manual, ya que no especific? la informaci?n a modificar");
		}
		
		if (mdmDatosEntrada.getPersonaFisica() != null
				&& mdmDatosEntrada.getPersonaFisica().getIdPersona() != null) {
			personaIMSS = serviciosPersonaBusiness
					.buscarPersonaFisicaParaModificacionManual(mdmDatosEntrada
							.getPersonaFisica().getIdPersona());
		} else {
			throw new DatosInsuficientesModificacionException(
					"No se puede realizar la modificaci?n manual, ya que no se proporcion? el ID de la persona");
		}
		
		if(personaIMSS == null){
			throw new PersonaNoEncontradaException(mdmDatosEntrada.getPersonaFisica().getIdPersona());
		}
		
		mdmDatosEntrada.setPersonaFisica(personaIMSS);
		
		return mdmDatosEntrada;
	}
	
	@Override
	public MDMDatosEntrada procesarModificacionManual(MDMDatosEntrada mdmDatosEntrada)
			throws ErrorComparacionDatosRENAPOException,
			PersonaFisicaNoEncontradaException {

		Fisica personaModificada = mdmDatosEntrada.getPersonaFisica();
		
		log.debug("Entrando al servicio que procesa la modificacion manual de la persona fisica [idPersona = "
				+ personaModificada.getIdPersona() + "]");
		
		/*
		 * Se busca la persona original, para poder compararla contra lo que se
		 * modific? y as? saber qu? campos fueron modificados.
		 */
		Fisica personaOriginal = this.serviciosPersonaBusiness
				.buscarPersonaFisicayDPyDyMCEnIMSS(personaModificada
						.getIdPersona());

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
		
		// Se settea el curp a los documentos probatorios de tipo CURP
		if (personaModificada.getDocumentosProbatorios() != null
				&& !personaModificada.getDocumentosProbatorios().isEmpty()) {
			String curp;
			if (personaModificada.getCurp() == null) {
				if (personaOriginal.getCurp() != null) {
					curp = personaOriginal.getCurp();
				} else {
					curp = "";
				}
			} else{
				curp = personaModificada.getCurp();
			}
			
			for (DocumentoProbatorio docProbatorio : personaModificada.getDocumentosProbatorios()) {
				if (docProbatorio instanceof CURP) {
					((CURP) docProbatorio).setCurp(curp);
				}
			}
		}
		
		Map<String, String> mensajes = new HashMap<String, String>();

		ICADatosRespuesta datosRespuesta = this.compararPersonaFisicaEntidadExternaUtility
				.compararDosPersonasFisicas(personaOriginal, personaModificada,
						mensajes, true, true);

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
		
		// Se valida si hubo cambios, si es as? se agrega la calificaci?n IMSS
		if (datosRespuesta.getTraza().containsKey("MSG01-RENAPO")
				|| datosRespuesta.getTraza().containsKey("MSG02-SAT")) {

			Calificacion calificacion = new Calificacion();
			calificacion.setIdCalificacion(CalificacionEnum.VALIDADO_IMSS
					.getCodigo().longValue());
			calificacion.setDescripcion(CalificacionEnum.VALIDADO_IMSS.getDescripcion());
			
			PersonaCalificacion personaCalificacion = new PersonaCalificacion();
			personaCalificacion.setCalificacion(calificacion);
			personaCalificacion.setFechaCalificacion(new Date());
			
			/*
			 * Se crea una nueva lista, para asegurar que s?lo se tenga la
			 * calificaci?n requerida
			 */
			personaModificada.setPersonaCalificaciones(new ArrayList<PersonaCalificacion>());

			personaModificada.getPersonaCalificaciones().add(personaCalificacion);
			
			// Se agrega el mensaje de validado IMSS
				datosRespuesta.getTraza().put("VALIDADO_IMSS", "Validado por IMSS");
		}
		
		return mdmDatosEntrada;
	}
	
 

	@Override
	public Long obtenerIDPersonaFisica(Long idPersona)
			throws PersonaFisicaNoEncontradaException {
		
		return this.personaFisicaServiceEntity.obtenerIDPersonaFisicaEscVirtual(idPersona);
	}


    @Override
    public Long obtenerIDPersonaFisicaEscVirtual(Long idPersona)
            throws PersonaFisicaNoEncontradaException {

        return this.personaFisicaServiceEntity.obtenerIDPersonaFisicaEscVirtual(idPersona);
    }


	@Override
	public Fisica guardarPersonaFisica(Fisica fisica) {

		return this.personaFisicaServiceEntity.guardarPersonaFisica(fisica);
	}

	@Override
	public void afectarDatosPersonaFisica(AfectarDatosPersonaWrapper datosPersona)
			throws PersonaFisicaNoEncontradaException {
		
		this.personaFisicaServiceEntity.afectarDatosPersonaFisica(datosPersona);
	}
	
	@Override
	public boolean comparaDatosBasicosRENAPO(Fisica fisicaRENAPO,
			Fisica fisicaSugerida) throws ErrorComparacionDatosRENAPOException {

		return this.compararPersonaFisicaEntidadExternaUtility
				.comparaDatosBasicosRENAPO(fisicaRENAPO, fisicaSugerida);
	}

	@Override
	public Integer comparaDiferenciaDatosBasicosRENAPO(Fisica personaRenapo,
			Fisica personaSugerida) throws ErrorComparacionDatosRENAPOException {
		return compararPersonaFisicaEntidadExternaUtility
				.comparaDiferenciaDatosBasicosRENAPO(personaRenapo, personaSugerida);
	}

	@Override
	public boolean comparaDatosBasicosSAT(Fisica fisicaSat,
			Fisica fisicaSugerida) throws ErrorComparacionDatosSATException {
		return compararPersonaFisicaEntidadExternaUtility
				.comparaDatosBasicosSAT(fisicaSat, fisicaSugerida);
	}
	
	
	
	@Override
	public List<Fisica> localizarPersonaFisicaPorDatosBasicosEnImssConFechaOMesYAniodeNacimiento(
			Fisica fisica) throws DatosInsuficientesParaConsultaException {
		// Primero se validan los datos requeridos para la b?squeda
		if (StringUtils.isBlank(fisica.getNombre())) {
			throw new DatosInsuficientesParaConsultaException();
		} else if (StringUtils.isBlank(fisica.getPrimerApellido())) {
			throw new DatosInsuficientesParaConsultaException();
		} else if (fisica.getFechaNacimiento() == null) {
			throw new DatosInsuficientesParaConsultaException();
		} else if (fisica.getLugarNacimiento() == null
				|| StringUtils.isBlank(fisica.getLugarNacimiento().getClave())) {
			throw new DatosInsuficientesParaConsultaException();
		} else if (fisica.getSexo() == null
				|| fisica.getSexo().getIdSexo() == null) {
			throw new DatosInsuficientesParaConsultaException();
		}
		
		Fisica personaSinN = null;
		personaSinN = this.getPersonaSinCaracteresEspeciales(fisica);
		
		List<Fisica> respuesta = this.personaFisicaServiceEntity
				.localizarPersonaFisicaPorDatosBasicosConFechaYSinFechaNacimiento(fisica);
		
		if(personaSinN != null) {
			List<Fisica> fisicasN = this.personaFisicaServiceEntity.localizarPersonaFisicaPorDatosBasicosConFechaYSinFechaNacimiento(
					personaSinN);
			if(fisicasN!=null) {
				if(respuesta == null){
					respuesta = new ArrayList();
				}
				respuesta.addAll(fisicasN);
			}
		}
        
		this.log.debug("Se encontraron " + respuesta.size()
				+ " personas que coincidieron con datos b?sicos");
            	
        return respuesta;
	}

	@Override
	public List<Fisica> localizarPersonaFisicaPorDatosBasicosEnImssConNSS(
			Fisica fisica) throws DatosInsuficientesParaConsultaException {
		
		// Primero se validan los datos requeridos para la b?squeda
		if (StringUtils.isBlank(fisica.getNombre())) {
			throw new DatosInsuficientesParaConsultaException();
		} else if (StringUtils.isBlank(fisica.getPrimerApellido())) {
			throw new DatosInsuficientesParaConsultaException();
		} else if (fisica.getFechaNacimiento() == null) {
			throw new DatosInsuficientesParaConsultaException();
		} else if (fisica.getLugarNacimiento() == null
				|| StringUtils.isBlank(fisica.getLugarNacimiento().getClave())) {
			throw new DatosInsuficientesParaConsultaException();
		} else if (fisica.getSexo() == null
				|| fisica.getSexo().getIdSexo() == null) {
			throw new DatosInsuficientesParaConsultaException();
		}
		
		this.log.debug("se busca a la persona que cuenta con CURP y no esta en baja");
		Fisica personaSinN = null;
		personaSinN = this.getPersonaSinCaracteresEspeciales(fisica);
		
		List<Fisica> respuesta = this.personaFisicaServiceEntity
				.localizarPersonaFisicaPorDatosBasicosEnImssConNSS(fisica);
		
		if(personaSinN != null) {
			List<Fisica> fisicasN = this.personaFisicaServiceEntity.localizarPersonaFisicaPorDatosBasicosEnImssConNSS(personaSinN);
			if(fisicasN!=null) {
				if(respuesta == null){
					respuesta = new ArrayList();
				}
				respuesta.addAll(fisicasN);
			}
		}
        
		this.log.debug("Se encontraron " + respuesta.size()
				+ " personas que coincidieron con datos b?sicos");
            	
        return respuesta;
    }

	@Override
	public List<AsignacionNSS> localizarNssPorDatosBasicosEnImss(
			Fisica fisica) throws DatosInsuficientesParaConsultaException {
		
		// Primero se validan los datos requeridos para la b?squeda
		if (StringUtils.isBlank(fisica.getNombre())) {
			throw new DatosInsuficientesParaConsultaException();
		} else if (StringUtils.isBlank(fisica.getPrimerApellido())) {
			throw new DatosInsuficientesParaConsultaException();
		} else if (fisica.getFechaNacimiento() == null) {
			throw new DatosInsuficientesParaConsultaException();
		} else if (fisica.getLugarNacimiento() == null
				|| StringUtils.isBlank(fisica.getLugarNacimiento().getClave())) {
			throw new DatosInsuficientesParaConsultaException();
		} else if (fisica.getSexo() == null
				|| fisica.getSexo().getIdSexo() == null) {
			throw new DatosInsuficientesParaConsultaException();
		}
		
		fisica.setNombre(fisica.getNombre().toUpperCase());
		fisica.setPrimerApellido(fisica.getPrimerApellido().toUpperCase());
		if(!StringUtils.isBlank(fisica.getSegundoApellido())) {
			fisica.setSegundoApellido(fisica.getSegundoApellido().toUpperCase());
		}
		
		Fisica personaSinN = null;
		personaSinN = this.getPersonaSinCaracteresEspeciales(fisica);
		
		List<AsignacionNSS> respuesta = this.personaFisicaServiceEntity.localizarNssPorDatosBasicos(fisica);
        
		if(respuesta != null) {
			this.log.debug("Se encontraron " + respuesta.size()
					+ " nss que coinciden con los datos de la persona");
		}
		
            	
        return respuesta;
    }
	
	@Override
	public List<Fisica> localizarPersonaFisicaPorDatosBasicosEnImss(
			Fisica fisica) throws DatosInsuficientesParaConsultaException {
		
		// Primero se validan los datos requeridos para la b?squeda
		if (StringUtils.isBlank(fisica.getNombre())) {
			throw new DatosInsuficientesParaConsultaException();
		} else if (StringUtils.isBlank(fisica.getPrimerApellido())) {
			throw new DatosInsuficientesParaConsultaException();
		} else if (fisica.getFechaNacimiento() == null) {
			throw new DatosInsuficientesParaConsultaException();
		} else if (fisica.getLugarNacimiento() == null
				|| StringUtils.isBlank(fisica.getLugarNacimiento().getClave())) {
			throw new DatosInsuficientesParaConsultaException();
		} else if (fisica.getSexo() == null
				|| fisica.getSexo().getIdSexo() == null) {
			throw new DatosInsuficientesParaConsultaException();
		}
		
		Fisica personaSinN = null;
		personaSinN = this.getPersonaSinCaracteresEspeciales(fisica);
		
		List<Fisica> respuesta = this.personaFisicaServiceEntity
				.localizarPersonaFisicaPorDatosBasicosEnImss(fisica);
		
		if(personaSinN != null) {
			List<Fisica> fisicasN = this.personaFisicaServiceEntity.localizarPersonaFisicaPorDatosBasicosEnImss(personaSinN);
			if(fisicasN!=null) {
				if(respuesta == null){
					respuesta = new ArrayList();
				}
				respuesta.addAll(fisicasN);
			}
		}
        
		this.log.debug("Se encontraron " + respuesta.size()
				+ " personas que coincidieron con datos b?sicos");
            	
        return respuesta;
    }
	
	private Fisica getPersonaSinCaracteresEspeciales (Fisica personaFisica) {
		Fisica personaCaracteres = null;

		String nombreCompleto = (personaFisica.getNombre() != null ? personaFisica.getNombre() : "") + " " +
		(StringUtils.isEmpty(personaFisica.getPrimerApellido()) ? "" : personaFisica.getPrimerApellido()) + " " +
		(StringUtils.isEmpty(personaFisica.getSegundoApellido()) ? "" : personaFisica.getSegundoApellido());

		log.debug("El nombre completo recibido es: " + nombreCompleto);
		char[] caracteres = {'?','?'};
		boolean existe_n = nombreCompleto.contains("?") || nombreCompleto.contains("?");

		log.debug("Existe ?: " + existe_n + ", existe ?: " + existe_n);

		if(existe_n) {
			try {
				personaCaracteres = (Fisica) BeanUtils.cloneBean(personaFisica);
			} catch (IllegalAccessException e) {
				log.error(e);
			} catch (InstantiationException e) {
				log.error(e);
			} catch (InvocationTargetException e) {
				log.error(e);
			} catch (NoSuchMethodException e) {
				log.error(e);
			}
			
			if(!StringUtils.isEmpty(personaCaracteres.getNombre())) {
				personaCaracteres.setNombre(personaCaracteres.getNombre().replace("?", "#"));
				personaCaracteres.setNombre(personaCaracteres.getNombre().replace("?", "#"));

				log.debug("El nombre quedo de la siguiente manera: " + personaCaracteres.getNombre());
			}

			if(!StringUtils.isEmpty(personaCaracteres.getPrimerApellido())) {
				personaCaracteres.setPrimerApellido(personaCaracteres.getPrimerApellido().replace("?", "#"));
				personaCaracteres.setPrimerApellido(personaCaracteres.getPrimerApellido().replace("?", "#"));

				log.debug("El primer apellido quedo de la siguiente manera: " + personaCaracteres.getPrimerApellido());
			}

			if(!StringUtils.isEmpty(personaCaracteres.getSegundoApellido())) {
				personaCaracteres.setSegundoApellido(personaCaracteres.getSegundoApellido().replace("?", "#"));
				personaCaracteres.setSegundoApellido(personaCaracteres.getSegundoApellido().replace("?", "#"));

				log.debug("El segundo apellido quedo de la siguiente manera: " + personaCaracteres.getSegundoApellido());
			}
		}
		
		return personaCaracteres;
	}

	@Override
	public Fisica localizarPersonaFisicaPorNss(String nss)
			throws PersonasNoLocalizadasException,
			NssRelacionadoVariasPersonasException {
		return personaFisicaServiceEntity.localizarPersonaPorNss(nss);
	}
	
		@Override
	public Fisica localizarPersonaFisicaPorNssCertificacion(String nss)
			throws PersonasNoLocalizadasException,
			NssRelacionadoVariasPersonasException {
		return personaFisicaServiceEntity.localizarPersonaPorNssCertificacion(nss);
	}
	
	@Override
	public void actualizarRFC(Fisica fisica)
			throws DatosInsuficientesModificacionException {
		
		if (fisica.getIdPersona() == null) {
			throw new DatosInsuficientesModificacionException(
					"El id de la persona es requerido para actualizar el RFC");
		}
		
		if (StringUtils.isBlank(fisica.getRfc())) {
			throw new DatosInsuficientesModificacionException(
					"El RFC es requerido para actualizarlo");
		}
		
		try {
			Long cveFisica = this.obtenerIDPersonaFisica(fisica.getIdPersona());
			fisica.setCveFisica(cveFisica);
		} catch (PersonaFisicaNoEncontradaException e1) {
			this.log.warn(e1);
		}
		
		AfectarDatosPersonaWrapper datosPersona = new AfectarDatosPersonaWrapper();
		datosPersona.setFisica(fisica);
		datosPersona.setModificarDatosSAT(true);
		datosPersona.setModificarRFC(true);
		
		try {
			this.afectarDatosPersonaBusiness.afectarDatosPersonaFisica(datosPersona);
		} catch (PersonaNoEncontradaException e) {
			this.log.error(e);
		}
	}
	
	/**
     * dado los datos de una persona que se acaba de crear en BDTU, es necesario sincronizar su 
     * informacion conra el sat y renapo
     * @param idPersona identificador de la persona nueva
     * @param rfc rfc de la persona
     * @param curp el curp de la persona fisica
     * @throws AfectacionDatosPersonaException Error al sincronizar la informacion
     */
    public void generarICAPersonaFisica(long idPersona, String rfc, String curp) throws AfectacionDatosPersonaException {    
        
        ICADatosConsulta icaDatosConsulta = new ICADatosConsulta();     
        icaDatosConsulta.setPersonaFisica(new Fisica());
        icaDatosConsulta.getPersonaFisica().setIdPersona(idPersona);
        icaDatosConsulta.getPersonaFisica().setRfc(rfc);
        icaDatosConsulta.getPersonaFisica().setCurp(curp);
        icaDatosConsulta.setIndicadorConsultaRENAPO(true);
        icaDatosConsulta.setIndicadorConsultaSAT(true);
        icaDatosConsulta.setIndicadorMostrarPantalla(false);
        try {
            ICADatosRespuesta icaDatosRespuesta = identificarCambios(icaDatosConsulta);
            icaDatosRespuesta = integrarCambios(icaDatosRespuesta);        
            
            TramiteCambioInformacionPersona tcp = new TramiteCambioInformacionPersona();
            tcp.setDatosICA(icaDatosRespuesta);
            log.info("Pasa por generarICAPersonaFisica...");  
            afectarDatosPersonaBusiness.afectarDatos(tcp, null);
            
        } catch (Exception e) {
            throw new AfectacionDatosPersonaException(e.getMessage());
        }
        
        
    }
    
    @Override
	public Fisica getPersonaFisica(Long idPersonaFisica){
    	return personaFisicaServiceEntity.getPersonaFisica(idPersonaFisica);
    }
    
    
    /**
  	 * Expone servicio para comparar los datos basicos de un asegeruado en bdtu contraRENAPO 
  	 * conciderando que la persona pueda o no taer fecha de nacimiento o mes y a?o de nacimiento
  	 * @param fisicaRENAPO
  	 * @param fisicaSugerida
  	 * @return
  	 * @throws ErrorComparacionDatosRENAPOException
  	 */
    @Override
  	public boolean comparaDatosBasicosAseguradoMesAnioNacRENAPO(Fisica fisicaRENAPO, Fisica fisicaAsegurado)
  			throws ErrorComparacionDatosRENAPOException{
  		
    	return compararPersonaFisicaEntidadExternaUtility.comparaDatosBasicosAseguradoMesAnioNacRENAPO(fisicaRENAPO, fisicaAsegurado);
  	}

    @Override
    public void procesarActualizacionesPF(List<Fisica> listaPF){
    	if(!CollectionUtils.isEmpty(listaPF)){
    		for(Fisica pf : listaPF){    					
    		    	try {	
    		    		//Obtener Persona (DitPersona / DitPersonaFisica)
    		    		String rfc = pf.getRfc();
    		    		String nss = pf.getNss();
    		    		System.err.println("NSS " + nss);
    		    		
    		    		Fisica fisica = localizarPersonaFisicaPorNss(nss);    		    		
    					DitPersona ditPersona = personaFisicaServiceEntity.getDitPersona(fisica.getIdPersona());
    			    	DitPersonaFisica ditPersonaFisica = personaFisicaServiceEntity.getDitPersonaFisica(fisica.getIdPersona());
    			    	
    			    	System.err.println("Id Persona " + fisica.getIdPersona());    					
    			    	System.err.println("Procesando......................");
    			    	//Actualizar RFC
    			    	if (!(ditPersona.getRfc() != null && StringUtils.isNotBlank(ditPersona.getRfc()))) {
    			    		ditPersona.setRfc(rfc);
    			    		personaFisicaServiceEntity.actualizarDitPersona(ditPersona);
    			    		System.err.println("Actualiza RFC en DitPersona " + rfc);
    			    	}
    			    	//Insertar Persona Fisica
    			    	if(ditPersonaFisica==null){
    			    		fisica.setRfc(ditPersona.getRfc());
    			    		guardarPersonaFisica(fisica);
    			    		System.err.println("Registra PF " + fisica.getCveFisica());
    			    	}else{
    			    		if (!(ditPersonaFisica.getRfc() != null && StringUtils.isNotBlank(ditPersonaFisica.getRfc()))) {
    			    			//Existe PF pero no tiene RFC, asociar el de ditPersona o en su defecto el parametro
    			    			if (ditPersona.getRfc() != null && StringUtils.isNotBlank(ditPersona.getRfc()))
    			        			rfc = ditPersona.getRfc();
    			    			
    			    			ditPersonaFisica.setRfc(rfc);
    			    			System.err.println("Actualiza RFC en DitPersonaFisica " + rfc);
    			        	}
    			    	}	    	
    				} catch (AbstractException e) {
    					System.err.println("Error " + e.getMessage());
    				}
    		}
    	}
    }
    
    @Override
    public void complementarDatosPersonas(String rfc, Long idPersona){
    	if (rfc != null && StringUtils.isNotBlank(rfc) && idPersona!=null) {
    		try {    			
	    		DitPersona ditPersona = personaFisicaServiceEntity.getDitPersona(idPersona);
	    		DitPersonaFisica ditPersonaFisica = personaFisicaServiceEntity.getDitPersonaFisica(idPersona);
	    		//Actualizar RFC de la Persona.
	    		if (ditPersona != null) {
		    		ditPersona.setRfc(rfc);
		    		personaFisicaServiceEntity.actualizarDitPersona(ditPersona);	    	
		    	}
	    		//Insertar Persona Fisica
		    	if(ditPersonaFisica==null){
		    		Fisica fisica = new Fisica();
		    		fisica.setIdPersona(ditPersona.getCveIdPersona());
		    		fisica.setRfc(ditPersona.getRfc());
		    		guardarPersonaFisica(fisica);		    		
		    	}else{
		    		//Existe PF pero no tiene RFC
		    		ditPersonaFisica.setRfc(rfc);		        	
		    	}
    		} catch (Exception e) {
    			log.error("Error " + e.getMessage());
			}
		}    	
    	log.info("Fin complementarDatosPersonas...");    	
    }
    
    @Override
	public List<AsignacionNSS> localizarNssCl3PorDatosBasicosSinFechaNac(
			Fisica fisica) throws DatosInsuficientesParaConsultaException {
		// Primero se validan los datos requeridos para la b?squeda
		if (StringUtils.isBlank(fisica.getNombre())) {
			throw new DatosInsuficientesParaConsultaException();
		} else if (StringUtils.isBlank(fisica.getPrimerApellido())) {
			throw new DatosInsuficientesParaConsultaException();
		} else if (fisica.getLugarNacimiento() == null
				|| StringUtils.isBlank(fisica.getLugarNacimiento().getClave())) {
			throw new DatosInsuficientesParaConsultaException();
		} else if (fisica.getSexo() == null
				|| fisica.getSexo().getIdSexo() == null) {
			throw new DatosInsuficientesParaConsultaException();
		}
		
		Fisica personaSinN = null;
		personaSinN = this.getPersonaSinCaracteresEspeciales(fisica);
		List<AsignacionNSS> respuesta = new ArrayList();
		
		 respuesta = this.personaFisicaServiceEntity.localizarNssCl3PorDatosBasicosSinFechaNac(fisica);
		
		if(personaSinN != null) {
			List<AsignacionNSS> fisicasN = this.personaFisicaServiceEntity.localizarNssCl3PorDatosBasicosSinFechaNac(
					personaSinN);
			if(fisicasN!=null) {
				if(respuesta == null){
					respuesta = new ArrayList();
				}
					respuesta.addAll(fisicasN);
			}
		}
        
            	
        return respuesta;
	}

	@Override
	public Long registrarNuevaPersonaCDA(Fisica fisica, Long idPersonaAnterior, Long idAsignacionNSS) throws Exception {

		DitPersona ditPersona = personaFisicaServiceUtility.transformarAEntidad(fisica);
		ditPersona.setFecRegistroAlta(new Date());
		ditPersona.setIndPerAutorizada(BigDecimal.ONE);
		ditPersona.setNumAnioNacReg(fisica.getAnioRegistroNac());
		ditPersona.setNumMesNacReg(fisica.getMesRegistroNac());
		ditPersona.setFecDefuncion(fisica.getFechaDefuncion());
		return personaFisicaServiceEntity.registrarNuevaPersonaCDA(ditPersona, idPersonaAnterior, idAsignacionNSS);
	}
	
    /**
  	 * Expone servicio para revisar la situacion del contribuyente en SAT y RENAPO 
  	 * @param fisica
  	 * @return
     * @throws ClienteWebserviceRenapoCurpException 
     * @throws CURPNoLocalizadoEnEntidadExternaException 
  	 * @throws ErrorComparacionDatosRENAPOException
  	 **/
	@Override
	public Fisica revisaSituacionContribuyente(Fisica fisica)
			throws ErrorComparacionDatosSATException, RFCNoLocalizadoEnEntidadExternaException, ClienteWebserviceSatRfcException, ErrorValidacionDatosConsultaEnEntidaExternaException, CURPNoLocalizadoEnEntidadExternaException, ClienteWebserviceRenapoCurpException{
		Fisica personaEntidadSAT = null;
		Fisica personaEntidadREN = null;
		//Aqui consultar persona en SAT y RENAPO y aplicar reglas para situacion del contribuyente
		log.debug("::: Buscamos a la PF en RENAPO, CURP: " + fisica.getCurp() + "-" + fisica.getRfc());
		personaEntidadREN = localizarPersonaFisicaEnRENAPOServiceBusiness.localizarPersonaFisicaEnRENAPOxCURP(fisica.getCurp());
		log.debug("::: Buscamos a la PF en SAT: " + fisica.getRfc());
		personaEntidadSAT = localizarPersonaFisicaEnSATServiceBusiness.localizarPFEnSATxRFCSitCont(fisica.getRfc());

		log.debug("Se validara situacion actual del contribuyente en RENAPO para PF, " + fisica.getRfc() + "-" + fisica.getCurp());
		individuoServiceBusiness.revisaSituacionContribuyenteRENAPO(fisica.getRfc(), personaEntidadSAT.getCurp(), personaEntidadREN);

		log.debug("::: Situaciones SAT encontradas: " + personaEntidadSAT.getSituacionesSAT().size() + ", "  + fisica.getRfc());
		log.debug("Se validara situacion actual del contribuyente en SAT para PF, " + fisica.getRfc());
		individuoServiceBusiness.revisaSituacionContribuyenteSAT(personaEntidadSAT.getSituacionesSAT(), fisica.getRfc(), TipoPersonaEnum.FISICA.getId());
		//		throw new ErrorComparacionDatosSATException("Exception FAKE para ver mensaje de error en pantalla: " + personaEntidadSAT.getSituacionesSAT().size());
		
		return personaEntidadSAT;
	}
	
}
