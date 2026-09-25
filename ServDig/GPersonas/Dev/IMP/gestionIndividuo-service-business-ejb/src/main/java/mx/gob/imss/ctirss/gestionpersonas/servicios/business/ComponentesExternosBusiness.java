package mx.gob.imss.ctirss.gestionpersonas.servicios.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.documento.probatorio.PersonaSinDocumentosException;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.RegistrarDocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.NssRelacionadoVariasPersonasException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.RegistrarMedioContactoException;
import mx.gob.imss.ctirss.delta.exception.usuario.EsquemaSegurdiadException;
import mx.gob.imss.ctirss.delta.exception.usuario.UsuarioNoEncontradoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.util.Utilerias;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.reporte.ManejadorReportesRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CorreccionDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.model.enums.PerfilesEsquemaSeguridadEnum;
import mx.gob.imss.ctirss.delta.model.enums.RolesEsquemaSeguridadEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.UsuarioTransfer;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.persona.hlda.HldaVO;
import mx.gob.imss.ctirss.gestionpersonas.servicios.hlda.interfaces.HldaClientServiceRemote;
import mx.gob.imss.ctirss.sso.admonusuarios.service.impl.AdmonUsuarios;
import mx.gob.imss.ctirss.sso.admonusuarios.service.impl.AdmonUsuariosService;
import mx.gob.imss.ctirss.sso.admonusuarios.service.impl.PerfilDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.service.impl.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.service.impl.UsuarioDTO;

import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;



@Stateless(name = "componentesExternosBusiness", mappedName = "componentesExternosBusiness")
public class ComponentesExternosBusiness extends AbstractServiceBusiness
		implements ComponentesExternosBusinessLocal,
		ComponentesExternosBusinessRemote {
	
	  private static final int TAMANO_PASSWORD = 12;

    @EJB(mappedName = "domicilioServiceBusiness")
	private DomicilioServiceBusinessRemote domicilioServiceBusinessRemote;
    
    @EJB(mappedName = "mediosContactoServiceBusiness")
    private MediosContactoServiceBusinessRemote mediosContactoServiceBusinessRemote;
    
    @EJB(mappedName = "documentoProbatorioServiceBusiness")
    private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;
    
    @EJB(mappedName = "manejadorReportesBusiness")
    private ManejadorReportesRemote manejadorReportesRemote;
    
    @EJB(mappedName = "solicitudBusiness")
    private SolicitudBusinessRemote solicitudBusiness;
    
    @EJB(mappedName = "personaFisicaServiceBusiness")
    private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusinessRemote;
    
    @EJB(mappedName = "firmaDigitalBusiness")
    private FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;
    
    @EJB(mappedName = "hldaClientServiceBusiness")
	HldaClientServiceRemote hldaService;
    
    @EJB(mappedName = "grupoFamiliarService")
    private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
    
    @EJB(mappedName = "")
    private CorreccionDerechohabienteServiceRemote correccionDerechohabienteServiceRemote;
	
    @Override
	public Map<String, Boolean> getRolesPorPersona(Long idPersona) {
		Map<String, Boolean> roles = null;
		
		try {
			roles = grupoFamiliarServiceRemote.getRolesPorPersona(idPersona,null);
		} catch(Exception e) {
			log.error("Ocurrio un error al consultar los roles de la persona" + idPersona, e);
		}
		
		return roles;
	}

	@Override
	public TramiteCorreccionDerechohabiente saveTramiteCorreccionDatosDerechohabiente(
			GrupoFamiliar grupo, Fisica fisica, Long idSolicitud) {
		
    	TramiteCorreccionDerechohabiente correccion = null;
    	
    	try {
	    	correccion = correccionDerechohabienteServiceRemote.guardarTramiteCorreccionDatosDerechohabienteDependiente(
	    			grupo, fisica, idSolicitud);
    	} catch(Exception e) {
    		log.error("Ocurrio un error al generar el tramite de correccion de derechohabiente", e);
    	}
		return correccion;
	}

	public List<GrupoFamiliar> obtenerGruposPorIdPersonaYPersonaInteresada(Long idPersona, Long idPersonaInteresada) {
    	List<GrupoFamiliar> grupos = null;
    	
    	try {
    		grupos = grupoFamiliarServiceRemote.findGruposFamiliaresPorPersonaYPersonaInteresada(idPersona,idPersonaInteresada);
    	}catch(Exception e) {
    		log.error("Ocurrio un error al consultar los grupos de la persona", e);
    	}
    	return grupos;
    }
    
    @Override
	public Boolean registradoComoDerechohabiente(Long idPersona, Boolean activo){
		Boolean registrado = false;
		
		try {
			registrado = grupoFamiliarServiceRemote.personaRegistradaComoDerechohabiente(idPersona, activo);
		} catch(Exception e) {
			registrado = false;
			log.error("Ocurrio un error al verificar si la persona esta registrada como derechohabiente",e);
		}
		
		return registrado;
	}

	public void altaDocumentosProbatorios(Persona persona) throws RegistrarDocumentoProbatorioException {
		try {
	    	//VERIFICA SI LA PERSONA TIENE DOCUMENTOS PROBATORIOS ASOCIADOS PARA SER REGISTRADOS EN BD
	        if(persona.getDocumentosProbatorios() != null && !persona.getDocumentosProbatorios().isEmpty()) {
	                Boolean documentosValidos = validarDocumentosProbatorios(persona.getDocumentosProbatorios());
	                if (documentosValidos) {
	                    List<DocumentoProbatorio> docsProbatorios = documentoProbatorioServiceBusinessRemote.registrarDocumentos(persona.getDocumentosProbatorios());
	                    persona.getDocumentosProbatorios().clear();
	                    persona.getDocumentosProbatorios().addAll(docsProbatorios);
	                }
	        }//VERIFICA SI LA PERSONA TIENE DOCUMENTOS PROBATORIOS ASOCIADOS PARA SER REGISTRADOS EN BD
		} catch (Exception e) {
			Log.error(e.getMessage(), e);
		}
    }
    
    private Boolean validarDocumentosProbatorios(final List<DocumentoProbatorio> documentosProbatorios) {
        Boolean documentosValidos = Boolean.TRUE;
        
        for (DocumentoProbatorio documentoProbatorio : documentosProbatorios) {
           
        	if(documentoProbatorio instanceof Nacimiento) {
                Nacimiento nacimiento = (Nacimiento) documentoProbatorio;
               this.log.debug("Validando el acta de nacimiento " + nacimiento);
                if(nacimiento.getNoJuzgado() == null || nacimiento.getAnio() == null || nacimiento.getNoLibro() == null) {
                	this.log.warn("El documento probatorio (NACIMIENTO) no es valido ...");
                    documentosValidos = Boolean.FALSE;
                    break;
                }else{
                	this.log.debug("El documento probatorio NACIMIENTO es valido ");
                }
            }
        	
        	
        	
        	
        }
        return documentosValidos;
    }
    
    public void altaDomicilios(Persona persona) throws DomicilioNoValidoException {
    	
    	//VERIFICA SI LA PERSONA TIENE DOMICILIOS ASOCIADOS PARA SER REGISTRADOS EN BD
        if (persona.getDomicilios() != null && !persona.getDomicilios().isEmpty()) {
        	
            List<Domicilio> domEnt = new ArrayList<Domicilio>();
            for (Domicilio domicilio : persona.getDomicilios()) {
                domicilio = domicilioServiceBusinessRemote.registrarDomicilio(domicilio);
                TipoDomicilio tipoDomicilio = new TipoDomicilio();
                tipoDomicilio.setClave(Utilerias.convertir(TipoDomicilioEnum.PARTICULAR.getCodigo()));
                domicilio.setTipoDomicilio(tipoDomicilio);
                domEnt.add(domicilio);
            }
            persona.getDomicilios().clear();
            persona.getDomicilios().addAll(domEnt);
        }//VERIFICA SI LA PERSONA TIENE DOMICILIOS ASOCIADOS PARA SER REGISTRADOS EN BD    	
    }
    
    public void altaMediosContacto(Persona persona){
    	
    	//VERIFICA SI LA PERSONA TIENE MEDIOS DE CONTACTO ASOCIADOS PARA SER REGISTRADOS EN BD
        if (persona.getMediosContacto() != null && !persona.getMediosContacto().isEmpty()) {
            List<? extends MedioContacto> mediosContantoEnt = new ArrayList<MedioContacto>();
            try {
                mediosContantoEnt = mediosContactoServiceBusinessRemote.registrarMedioDeContacto(persona.getMediosContacto());
            } catch (RegistrarMedioContactoException e) {
            	e.printStackTrace();
            }
            persona.getMediosContacto().clear();
            persona.getMediosContacto().addAll(mediosContantoEnt);
        }//VERIFICA SI LA PERSONA TIENE MEDIOS DE CONTACTO ASOCIADOS PARA SER REGISTRADOS EN BD
    }
       
    public void getDomiciliosPersona(Persona persona){
    	List<Domicilio> domicilios = null;
    	
    	try {
	    	if (persona instanceof Moral){
	    		domicilios = this.domicilioServiceBusinessRemote.consultarDomiciliosPersonaMoral(persona);
	    	} else if (persona instanceof Fisica){
	    		domicilios = this.domicilioServiceBusinessRemote.consultarDomiciliosPersonaFisica(persona);
	    	}
    	} catch (DomicilioNoLocalizadoException e) {
    		this.log.warn(e);
    	}

    	persona.setDomicilios(domicilios);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<MedioContacto> getMediosContactoPersona(Persona persona){
    	
    	List<? extends MedioContacto> mediosContacto = null;
    	Persona personaBusqueda = new Persona();
    	personaBusqueda.setIdPersona(persona.getIdPersona());
    	
    	TipoPersona tipoPersona = new TipoPersona();

    	if (persona instanceof Moral){
    		tipoPersona.setIdTipoPersona(2L);
    	}
    	else{
    		tipoPersona.setIdTipoPersona(1L);
    	}
    	
    	personaBusqueda.setTipoPersona(tipoPersona);
    	
		try {
			mediosContacto = mediosContactoServiceBusinessRemote.consultarMedioDeContactoPersona(personaBusqueda);
			persona.setMediosContacto((List<MedioContacto>) mediosContacto);
		} catch (PersonaSinMedioDeContactoException e) {
			mediosContacto = new ArrayList<MedioContacto>();
		}    	
		
		return (List<MedioContacto>) mediosContacto;
    }
    
    @SuppressWarnings("unchecked")
	public List<DocumentoProbatorio> getDocumentosProbatoriosPersona(Persona persona){
    	
    	List<? extends DocumentoProbatorio> documentosProbatorios = null;
    	Persona personaBusqueda = new Persona();
    	personaBusqueda.setIdPersona(persona.getIdPersona());
    	
    	TipoPersona tipoPersona = new TipoPersona();

    	if (persona instanceof Moral){
    		tipoPersona.setIdTipoPersona(2L);
    	}
    	else{
    		tipoPersona.setIdTipoPersona(1L);
    	}
    	
    	personaBusqueda.setTipoPersona(tipoPersona);
    	
		try {
	        documentosProbatorios = documentoProbatorioServiceBusinessRemote.consultarDocumentosDePersona(personaBusqueda);
			persona.setDocumentosProbatorios((List<DocumentoProbatorio>) documentosProbatorios);
		} catch (PersonaSinDocumentosException e) {
			documentosProbatorios = new ArrayList<DocumentoProbatorio>();
		}        	
		
		return (List<DocumentoProbatorio>) documentosProbatorios;
    }

	@Override
	public void getDomicilioFiscalPersona(Persona persona) {
		
		DomicilioFiscal domicilioFiscal = null;
    	    		
    	try {
			domicilioFiscal = domicilioServiceBusinessRemote.consultarDomicilioFiscalPersona(persona);
		} catch (DomicilioNoLocalizadoException e) {
			log.info("PERSONA ID " + persona.getIdPersona() + "sin domicilio fiscal");
		}
    		
    	persona.setDomicilioFiscal(domicilioFiscal);

	}

	@SuppressWarnings("unchecked")
	@Override
	public void getMediosContactoFiscalesPersona(Persona persona) {

		try {
			List<? extends MedioContacto> mediosContacto = mediosContactoServiceBusinessRemote
					.consultarMediosFiscalesPersona(persona);
			persona.setMediosContactoFiscales((List<MedioContacto>) mediosContacto);
		} catch (PersonaSinMedioDeContactoException e) {
			log.info("PERSONA ID " + persona.getIdPersona()
					+ "sin medios de contacto fiscales");
		}

	}
	

    /**
     * Metodo que crea un usuario en el esquema de seguridad 
     * @param fisica objeto con los datos de la persona a dar de alta
     * @param cveIdsolicitud recive el n?mero de solicitud con el cual se creo el registro
     * @return String con la respuesta del servicio de alta
     * @throws EsquemaSegurdiadException
     */
	@Override
	public String crearUsuarioEsquemaSeguridad(Usuario usuario, 
			Long cveIdsolicitud)
			throws EsquemaSegurdiadException {
		
		return this.crearUsuario(this.parserUsuarioTObyFisica(usuario, cveIdsolicitud));
		
	}
	
	/**
	 * Servicio que se encarga de recuperar los datos basicos de un usuario de seguridad 
	 * @param curp del usuario a recuperar
	 * @return Usuario con los datos basicos seteados
	 * @throws EsquemaSegurdiadException
	 * @throws UsuarioNoEncontradoException
	 */
	@Override
	public Usuario recuperaUsuarioEsquemaSeguridadByCURP(String curp)	throws EsquemaSegurdiadException,
											UsuarioNoEncontradoException{
		AdmonUsuariosService objAdmonUsuariosService = new AdmonUsuariosService();
		AdmonUsuarios objAdmonUsuariosSession = objAdmonUsuariosService.getAdmonUsuariosPort();
   	 UsuarioDTO usrTO  = null;
   	
		try{
			usrTO = objAdmonUsuariosSession.obtenUsuario(curp);
			if(usrTO == null)
				throw new UsuarioNoEncontradoException("No se encontro el usuario en el esquema de seguridad");
		}catch(Exception ex){
			this.log.error("estoy en el cliente de SSO y cocurrio un error al recuperar la info del usuario", ex);
			throw new EsquemaSegurdiadException(ex.getMessage());
		}
		try{
			return this.parserUsuarioSSOtoUsuarioDelta(usrTO);
		}catch(Exception exe){
			this.log.debug("ocurrio un error al momento de hacer el parser del objeto de seguridad");
			throw new EsquemaSegurdiadException(exe.getMessage());
		}
	}
	
	
	@Override
	public void actualizaUsuarioEsquemaSeguridad(Usuario objUsuario) throws EsquemaSegurdiadException{
		
		AdmonUsuariosService objAdmonUsuariosService = new AdmonUsuariosService();
		AdmonUsuarios objAdmonUsuariosSession = objAdmonUsuariosService.getAdmonUsuariosPort();
	   	UsuarioDTO usrTO  = null;
	   	boolean actualizacion = false;
	   	boolean usuarioDesactivado = false;
			try{
				if(objUsuario.getUsuario().equalsIgnoreCase(objUsuario.getFisica().getCurp())){
					this.log.debug("entre al la actualizacion de password");
					//TODO : se cambio por que no se requiere el servicio de actualizacin ni setear el DTO
					 usrTO = parserUsuarioTObyFisicaByActualizacion(objUsuario, null);
					 actualizacion = objAdmonUsuariosSession.modificarUsuario(usrTO);
					
					 objAdmonUsuariosSession.regeneraPassword(objUsuario.getUsuario(), objUsuario.getPassword());
					 
					 
				}else{
					this.log.debug("entre al la actualizacion de curp");
					if(objAdmonUsuariosSession.desactivarUsuario(objUsuario.getUsuario())){
						this.log.debug("paso la baja logica");
						usuarioDesactivado= true;
						 objAdmonUsuariosSession.agregarUsuarioConPerfiles(this.parserUsuarioTObyFisica(objUsuario, null));
						 actualizacion = true;
						 this.log.debug("pase la creacion de la cuenta");
					}
				}
				 
				if(!actualizacion){
					throw new EsquemaSegurdiadException("No se pudo actualiar la informaci?n del usuario");
				}
			}catch(Exception ex){
				this.log.error("estoy en el cliente de SSO y cocurrio un error al tratar de actualizar la info del usuario", ex);
				if(usuarioDesactivado){
					try{
						objAdmonUsuariosSession.activarUsuario(objUsuario.getUsuario());
					}catch(Exception ei){
						this.log.error("Ocurrio un error al tratar de reactivar la cuenta " + objUsuario.getUsuario(), ei );
						throw new EsquemaSegurdiadException(ex.getMessage());
					}
				}
				throw new EsquemaSegurdiadException(ex.getMessage());
			}
		
	}
	
	 /**
     * Servicio para consultar si existe una cuenta en el esquema de seguridad
     * @param curp recibe un string con el curp de la cuenta a buscar
     * @return true si existe la cuenta, false en caso contrario
     * @throws EsquemaSegurdiadException
     */
	
	@Override
	public boolean existeUsuarioEsquemaSeguridadByCURP(String curp)
			throws EsquemaSegurdiadException {
		AdmonUsuariosService objAdmonUsuariosService = new AdmonUsuariosService();
		AdmonUsuarios objAdmonUsuariosSession = objAdmonUsuariosService.getAdmonUsuariosPort();
		try{
			return objAdmonUsuariosSession.existeUsuario(curp);
		}catch(Exception ex){
			this.log.error("estoy en el cliente de SSO y cocurrio un error", ex);
			throw new EsquemaSegurdiadException("No se pudo consultar el usuario en el esquema de seguridad [" + ex.getMessage() +"]" );
		} 
	
	}
	
	/**
     * Servicio encargado de eliminar un usuario del esquema de seguridad
     * @param curp con el valor de la llave a eliminar
     * @return true si fue exitosa la eliminacion del registro
     * @throws EsquemaSegurdiadException
     */
	@Override
	public boolean eliminaUsuarioEsquemaSeguridadByCURP(String curp)
			throws EsquemaSegurdiadException {
		AdmonUsuariosService objAdmonUsuariosService = new AdmonUsuariosService();
		AdmonUsuarios objAdmonUsuariosSession = objAdmonUsuariosService.getAdmonUsuariosPort();
		try{
			return objAdmonUsuariosSession.eliminarUsuario(curp);
		}catch(Exception ex){
			this.log.error("estoy en el cliente de SSO y cocurrio un error", ex);
			throw new EsquemaSegurdiadException("No se pudo eliminar el usario ["+ ex.getMessage()  +"]" );
		} 
	
	}
	
	private UsuarioDTO parserUsuarioTObyFisica(Usuario usuario, 
			Long cveIdsolicitud){
		Fisica fisica = usuario.getFisica();
		UsuarioDTO objUsuarioTO = this.FisicatoUsuario(fisica);
		objUsuarioTO.setCorreoElectronico("pruebas@pruebas.gob.mx");
		this.log.debug("el certificado que llego aqui es [" + usuario.getPassword()+"]");
		objUsuarioTO.setPassword(usuario.getPassword());
		objUsuarioTO.setSerial(usuario.getPassword());
		
		return objUsuarioTO;

	} 
	
	
	private UsuarioDTO parserUsuarioTObyFisicaByActualizacion(Usuario usuario, 
			Long cveIdsolicitud){
		Fisica fisica = usuario.getFisica();
		UsuarioDTO objUsuarioTO = new UsuarioDTO();
		objUsuarioTO.setActivo(true);
		objUsuarioTO.setApellidoMaterno(fisica.getSegundoApellido());
		objUsuarioTO.setApellidoPaterno(fisica.getPrimerApellido());
		objUsuarioTO.setCurp(fisica.getCurp());
		objUsuarioTO.setIdBdtu(fisica.getIdPersona()+"");
		objUsuarioTO.setNombres(fisica.getNombre());
		objUsuarioTO.setCorreoElectronico("pruebas@pruebas.gob.mx");
		this.log.debug("el certificado que llego aqui es [" + usuario.getPassword()+"]");
		objUsuarioTO.setPassword(usuario.getPassword());
		//objUsuarioTO.setConfirmarPassword(usuario.getPassword());
		objUsuarioTO.setNewPassword(usuario.getPassword());
		objUsuarioTO.setSerial(usuario.getPassword());
		//objUsuarioTO.setUid(usuario.getUsuario());

		this.log.debug("el usuario nuevo que llego aqui es [" + fisica.getCurp()+"]");
		this.log.debug("el usuario viejo es aqui es [" + usuario.getUsuario() +"]");
		/*if (cveIdsolicitud != null){
			objUsuarioTO.setIdSolicitud(cveIdsolicitud);
		}*/
		
		List<PuestoDTO> objRollArray = new ArrayList<PuestoDTO>();
		PuestoDTO objRoll = new PuestoDTO();
		
		objRoll.setNombrePuesto(RolesEsquemaSeguridadEnum.USUARIO_EXTERNO.name());
		objRollArray.add(objRoll);
		
		PerfilDTO objPerfil = new PerfilDTO();
		objPerfil.setCveSsoperfilessol(PerfilesEsquemaSeguridadEnum.USUARIO_EXTERNO.getId());
		objPerfil.setDesDefault(PerfilesEsquemaSeguridadEnum.USUARIO_EXTERNO.name());
		
		objPerfil.setPuestoDTO(objRoll);
		
		objUsuarioTO.setRolesData(objRollArray);
		objUsuarioTO.setDescripcionCargo("USUARIO EXTERNO");
		
		return objUsuarioTO;
	}
	
	
	private Usuario parserUsuarioSSOtoUsuarioDelta(UsuarioDTO objSSO){
		
		Usuario objUsuario = new Usuario();
		Fisica objFisica = new Fisica();
		CorreoElectronico objcorreoElectronico = new CorreoElectronico();
			objUsuario.setPassword(objSSO.getSerial());
			objcorreoElectronico.setCorreo(objSSO.getCorreoElectronico());			
			if(StringUtils.isNotBlank(objSSO.getIdBdtu())){
				objFisica.setIdPersona(Long.parseLong(objSSO.getIdBdtu()));
			}
			objFisica.setCurp(objSSO.getCurp());
			objFisica.setCorreoElectronico(objcorreoElectronico);
			objFisica.setNombre(objSSO.getNombres());
			objFisica.setPrimerApellido(objSSO.getApellidoPaterno());
			objFisica.setSegundoApellido(objSSO.getApellidoMaterno());
			objUsuario.setFisica(objFisica);
		return objUsuario;
	}
	
	
	@Override
	public void guardarYAsociarDomiciliosPersona(Persona persona)
			throws DomicilioNoValidoException {
		
		// Se checa si la persona tiene domicilios
		if (persona.getDomicilios() != null && !persona.getDomicilios().isEmpty()
				&& persona.getIdPersona() != null) {
        	
            List<Domicilio> domEnt = new ArrayList<Domicilio>();
            
            for (Domicilio domicilio : persona.getDomicilios()) {
            	if (domicilio.getClave() == null) {
	                domicilio = domicilioServiceBusinessRemote.registrarDomicilio(domicilio);
	                
	                if (domicilio.getDicTipoDomicilio() == null) {
		                TipoDomicilio tipoDomicilio = new TipoDomicilio();
		                tipoDomicilio.setClave(Utilerias.convertir(TipoDomicilioEnum.PARTICULAR.getCodigo()));
		                domicilio.setDicTipoDomicilio(tipoDomicilio);
	                }
	                
	                if (persona instanceof Fisica) {
	                	this.domicilioServiceBusinessRemote.asociarDomicilioPersona(domicilio, persona.getIdPersona());
	                } else if (persona instanceof Moral) {
	                	this.domicilioServiceBusinessRemote.asociarDomicilioPersonaMoral(domicilio, persona.getIdPersona());
	                } else {
	                	this.log.warn("No se pudo asociar el domicilio a la persona especificada");
	                }
	                
	                domEnt.add(domicilio);
            	} else {
            		this.log.warn("El domicilio a guardar ya cuenta con id : " + domicilio.getClave());
            	}
            }
            persona.getDomicilios().clear();
            persona.getDomicilios().addAll(domEnt);
        } else {
        	this.log.error("No se recibieron los parametros necesarios para realizar el alta y asociacion de domicilios");
        }
	}
    
	@Override
	public void guardarYAsociarMediosContactoPersona(Persona persona) {

		// Se checa que la persona tenga medios para dar de alta
		if (persona.getMediosContacto() != null
				&& !persona.getMediosContacto().isEmpty()
				&& persona.getIdPersona() != null) {

			List<? extends MedioContacto> mediosContantoEnt = new ArrayList<MedioContacto>();

			for (MedioContacto medio : persona.getMediosContacto()) {
				if (medio.getClave() == null) {
					try {
						if (persona instanceof Fisica) {
							this.mediosContactoServiceBusinessRemote
									.registrarAsociarMedioContactoPersona(medio,
											persona.getIdPersona());
						} else if (persona instanceof Moral) {
							this.mediosContactoServiceBusinessRemote
									.registrarAsociarMedioContactoPersonaMoral(
											medio, persona.getIdPersona());
						}
					} catch (RegistrarMedioContactoException e) {
						this.log.error(e);
					}
				} else {
					this.log.warn("El medio de contacto a guardar ya cuenta con id : " + medio.getClave());
				}
			}

			persona.getMediosContacto().clear();
			persona.getMediosContacto().addAll(mediosContantoEnt);
		} else {
			this.log.error("No se recibieron los parametros necesarios para realizar el alta y asociacion de medio de contacto");
		}
	}
	
	@Override
	public List<Domicilio> obtenerDomicilioParticularPersona(Persona persona) {
		
		List<Domicilio> domicilioParticular = null;
		
		if (persona.getTipoPersona() == null) {
			TipoPersona tipoPersona = new TipoPersona();
			
			if (persona instanceof Moral) {
				tipoPersona.setIdTipoPersona(TipoPersonaEnum.MORAL.getId());
			} else {
				tipoPersona.setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
			}
			
			persona.setTipoPersona(tipoPersona);

		}
		
		List<Long> tiposDomicilio = new ArrayList<Long>();
		tiposDomicilio.add(TipoDomicilioEnum.PARTICULAR.getCodigo());
		
		try {
			domicilioParticular = this.domicilioServiceBusinessRemote.obtenerDomiciliosPersonaPorTipo(persona, tiposDomicilio);
		} catch (DomicilioNoLocalizadoException e) {
			this.log.error(e);
			domicilioParticular = new ArrayList<Domicilio>();
		}
		
		return domicilioParticular;
	}
	
	
	/**
	 * Inserta una nueva solicitud del tipo y con el estado proporcionados
	 * 
	 * @author Hugo Armando Mart&iacute;nez Cham&oacute;nica
	 * @param tipoSolicitud
	 * @param estadoSolicitud
	 * @param usuario
	 * @return Solicitud
	 */
	private Solicitud construirSolicitudHLDA(TipoSolicitudEnum tipoSolicitud,
			EstadoSolicitudEnum estadoSolicitud, Usuario usuario, Date fechaSolicitud) {
		System.out.println("Iniciando la creacion de  la solicitud");
		
		Solicitud solicitud = new Solicitud();
		EstadoSolicitud estado = new EstadoSolicitud();
		estado.setIdEstadoSolicitud(estadoSolicitud.getCodigo());
		solicitud.setEstadoSolicitud(estado);
		solicitud.setFechaSolicitud(fechaSolicitud);
		solicitud.setFechaPresentacion(fechaSolicitud);
		solicitud.setFechaConclusion(fechaSolicitud);
		TipoSolicitud tipo = new TipoSolicitud();
		tipo.setIdTipoSolicitud(tipoSolicitud.getValor().longValue());
		tipo.setDescripcion("IMPRESION DE SEMANAS COTIZADAS");
		solicitud.setTipoSolicitud(tipo);
		solicitud.setSolicitante(usuario);

		log.debug("Terminando de crear la solicitud");
		return solicitud;
	}

	private Tramite construirTramiteHLDA(TipoTramiteEnum tipo,
			EstadoTramiteEnum estado, Fisica fisica, Date fechaTramite) {
		TramiteFisica tramite = new TramiteFisica();
		EstadoTramite estadoTramite = new EstadoTramite();
		estadoTramite.setIdEstadoTramitePersona(estado.getCodigo());
		TipoTramite tipoTramite = new TipoTramite();
		tipoTramite.setIdTipoTramite(tipo.getCodigo());
		tramite.setFisica(fisica);
		tramite.setFechaTramite(fechaTramite);
		tramite.setFechaPresentacion(fechaTramite);
		tramite.setFechaConclusion(fechaTramite);
		tramite.setEstadoTramite(estadoTramite);
		tramite.setTipoTramite(tipoTramite);
		return tramite;
	}

	@Override
	public byte[] obtenerReporteDeHistoriaLaboral(String nss, Usuario usuario) {

		byte[] reporte = null;

		try {
			HldaVO hlda = hldaService.getHldaVO(nss);
			Fisica fisica = personaFisicaServiceBusinessRemote
					.localizarPersonaFisicaPorNss(nss);

			Date fechaSolicitud = Calendar.getInstance().getTime();
			Solicitud solicitud = construirSolicitudHLDA(
					TipoSolicitudEnum.IMPRESION_DE_SEMANAS_COTIZADAS,
					EstadoSolicitudEnum.ATENDIDA, usuario, fechaSolicitud);
			Tramite tramite = construirTramiteHLDA(
					TipoTramiteEnum.CONSULTA_DE_SEMANAS_COTIZADAS,
					EstadoTramiteEnum.CERRADO, fisica, fechaSolicitud);
			solicitud.setTramites(new ArrayList<Tramite>());
			solicitud.getTramites().add(tramite);
			
			solicitud = solicitudBusiness.crear(solicitud);
			FirmaElectronica datosFirma = obtenerDatosFirmaElectronica(
					solicitud, fisica, nss);
			firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud,
					datosFirma);
			reporte = manejadorReportesRemote.generaReporteSemanasCotizadas(
					hlda, datosFirma);
			solicitudBusiness.actualizarDocumentosTramite(solicitud
					.getTramites().get(0).getTramiteId(),
					TipoDocumentoTramiteEnum.COMPROBANTE.getCodigo()
							.longValue(), reporte);
			 
		} catch (PersonasNoLocalizadasException e) {
			this.log.error(e);
		} catch (NssRelacionadoVariasPersonasException e) {
			this.log.error(e);
		} catch (Exception e) {
			this.log.error(e);
		}

		return reporte;
	}
		
	private FirmaElectronica obtenerDatosFirmaElectronica(Solicitud solicitud, Persona persona, String nss){
		FirmaElectronica firma = new FirmaElectronica();
		Map<String, String> datosSello = firmaDigitalBusinessRemote.getCadenaOriginalYSelloDigital(solicitud, persona, null, nss);
		firma.setCadenaOriginal(datosSello.get("cadenaOriginal"));
		firma.setReciboNotarial(datosSello.get("secuenciaNotaria"));
		firma.setRecibo(datosSello.get("selloDigital"));
		firma.setSerialCertificado(datosSello.get("numeroSerie"));
		return firma;
	}

	@Override
	public String crearUsuarioEsquemaSeguridad(Fisica usuario,
			Usuario usuarioDto) throws EsquemaSegurdiadException {
		return this.crearUsuario(this.mergeUsuarioFisica(usuario, usuarioDto));
	}
	
	private String crearUsuario(UsuarioDTO usuarioDTO) throws EsquemaSegurdiadException {
		AdmonUsuariosService objAdmonUsuariosService = new AdmonUsuariosService();
		AdmonUsuarios objAdmonUsuariosSession = objAdmonUsuariosService.getAdmonUsuariosPort();
		try{
			return objAdmonUsuariosSession.agregarUsuarioConPerfiles(usuarioDTO);
		}catch(Exception ex){
			this.log.error("ocurrio un erro al tratar de salvar al usuario", ex);
			throw new EsquemaSegurdiadException("No pudo darse de alta el usuario en el esquema de seguridad");

		} 
	}
	
	private UsuarioDTO mergeUsuarioFisica(Fisica fisica, Usuario usuarioDto) {
		UsuarioDTO aux = this.FisicatoUsuario(fisica);
		aux.setCorreoElectronico(usuarioDto.getCorreo());
		aux.setTelefono(usuarioDto.getTelefono());
		aux.setPassword(usuarioDto.getPassword());
		aux.setSerial(usuarioDto.getPassword());
		return aux;
	}

	private UsuarioDTO FisicatoUsuario(Fisica fisica) {
		UsuarioDTO objUsuarioTO = new UsuarioDTO();
		objUsuarioTO.setActivo(true);
		objUsuarioTO.setApellidoMaterno(fisica.getSegundoApellido());
		objUsuarioTO.setApellidoPaterno(fisica.getPrimerApellido());
		objUsuarioTO.setCurp(fisica.getCurp());
		objUsuarioTO.setIdBdtu(fisica.getIdPersona()+"");
		objUsuarioTO.setNombres(fisica.getNombre());
		
		List<PuestoDTO> objRollArray = new ArrayList<PuestoDTO>();
		PuestoDTO objRoll = new PuestoDTO();

		objRoll.setNombrePuesto(RolesEsquemaSeguridadEnum.USUARIO_EXTERNO.name());
		objRollArray.add(objRoll);
		
		PerfilDTO objPerfil = new PerfilDTO();
		objPerfil.setCveSsoperfilessol(PerfilesEsquemaSeguridadEnum.USUARIO_EXTERNO.getId());
		objPerfil.setDesDefault(PerfilesEsquemaSeguridadEnum.USUARIO_EXTERNO.name());

		objPerfil.setPuestoDTO(objRoll);
		objUsuarioTO.setRolesData(objRollArray);
		objUsuarioTO.setDescripcionCargo("USUARIO EXTERNO");

		return objUsuarioTO;
	}

	@Override
	public String crearUsuarioService(UsuarioTransfer usuarioTransfer)
			throws EsquemaSegurdiadException {
		System.out.println("ANtes del casteo");		
				
		
		UsuarioDTO user=parserUsuarioTOUser(usuarioTransfer.getFisica(), usuarioTransfer.getSolicitud(), usuarioTransfer.getPassword());
		
		
		
		System.out.println("Despues  del casteo");
		AdmonUsuariosService objAdmonUsuariosService = new AdmonUsuariosService();
		AdmonUsuarios objAdmonUsuariosSession = objAdmonUsuariosService.getAdmonUsuariosPort();
		try{
			return objAdmonUsuariosSession.agregarUsuarioConPerfiles(user);			
		}catch(Exception ex){
			this.log.error("ocurrio un erro al tratar de salvar al usuario", ex);
			throw new EsquemaSegurdiadException("No pudo darse de alta el usuario en el esquema de seguridad");

		} 
	}
	
	@Override
	public boolean regenerarPasswordEsquemaSeguridad(String curp,
			String serial) throws EsquemaSegurdiadException {
		AdmonUsuariosService objAdmonUsuariosService = new AdmonUsuariosService();
		AdmonUsuarios objAdmonUsuariosSession = objAdmonUsuariosService.getAdmonUsuariosPort();
		try{
			return objAdmonUsuariosSession.regeneraPassword(curp, serial);
		}catch(Exception ex){
			this.log.error("ocurrio un erro al tratar de regenerar password al usuario", ex);
			throw new EsquemaSegurdiadException("\"ocurrio un erro al tratar de regenerar password al usuario");

		} 
	}
	
	
public UsuarioDTO parserUsuarioTOUser(Fisica fisica, Solicitud solicitud, String password) {
        
    	String passwordSSO;
    	
    	if(password==null){
    		System.out.println("Pasword Genreado dinamicamente");
    		passwordSSO= crearPassword(fisica.getCurp());
    	}else{
    		System.out.println("Pasword seteado");
    		passwordSSO=password;
    	}
        //seteamos los datos de usuario
        UsuarioDTO objUsuarioTO = new UsuarioDTO();
        objUsuarioTO.setActivo(true);
        objUsuarioTO.setApellidoMaterno(fisica.getSegundoApellido());
        objUsuarioTO.setApellidoPaterno(fisica.getPrimerApellido());
        objUsuarioTO.setCurp(fisica.getCurp());
        objUsuarioTO.setIdBdtu(fisica.getIdPersona() + "");
        objUsuarioTO.setNombres(fisica.getNombre());
        objUsuarioTO.setCorreoElectronico(fisica.getCorreoElectronico().getCorreo());
        objUsuarioTO.setSerial(solicitud.getSecuenciaDeNotaria());
        objUsuarioTO.setPassword(passwordSSO);
        objUsuarioTO.setNewPassword(passwordSSO);

        List<PuestoDTO> objRollArray = new ArrayList<PuestoDTO>();
        PuestoDTO objRoll = new PuestoDTO();

        objRoll.setNombrePuesto(RolesEsquemaSeguridadEnum.USUARIO_EXTERNO.name());
        objRoll.setDefaultRol(RolesEsquemaSeguridadEnum.USUARIO_EXTERNO.name());
        objRollArray.add(objRoll);

        PerfilDTO objPerfil = new PerfilDTO();
        objPerfil.setCveSsoperfilessol(PerfilesEsquemaSeguridadEnum.USUARIO_EXTERNO.getId());
        objPerfil.setDesDefault(PerfilesEsquemaSeguridadEnum.USUARIO_EXTERNO.name());

        objPerfil.setPuestoDTO(objRoll);

        objUsuarioTO.setRolesData(objRollArray);
        objUsuarioTO.setDescripcionCargo("USUARIO EXTERNO");

        return objUsuarioTO;
    }
	
	
	
	   private String crearPassword(String curp) {
	        StringBuilder password = new StringBuilder();
	        StringBuilder base = new StringBuilder();
	        char[] cadenaCurp = curp.toCharArray();
	        char[] cadenaBase = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'X', 'A', 'Z'};
	        for (int cont = 0; cont < TAMANO_PASSWORD; cont++) {
	            int indice = (int) (Math.random() * 2 + 1);
	            switch (indice) {
	                case 1:
	                    password.append(cadenaCurp[cont]);
	                    break;
	                case 2:
	                    int caracter = (int) (Math.random() * 12);
	                    password.append(cadenaBase[caracter]);
	                    break;
	            }
	        }
	        return password.toString();
	    }
}
