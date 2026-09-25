package mx.gob.imss.ctirss.gestionpersonas.servicios.business;

import gob.imss.webservice.renapo.curp.implementacion.ClienteWebserviceCurp;
import gob.imss.webservice.sat.rfc.implementacion.ClienteWebserviceRfc;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.buzon.consultarfc.implementacion.BuzonTributarioWs;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaConVariosNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaSinNSSException;
import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ComparacionSinDiferenciasException;
import mx.gob.imss.ctirss.delta.exception.individuo.DatosInsuficientesICAException;
import mx.gob.imss.ctirss.delta.exception.individuo.DiferenciasRENAPOContraSAT;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ExisteMasDeUnConjuntoDeCalifiacionesPorCurpException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RegistroPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RegistroPersonaFisicaException;
import mx.gob.imss.ctirss.delta.exception.individuo.identificador.IdentificadoresNoExistentesException;
import mx.gob.imss.ctirss.delta.exception.individuo.identificador.PersonaSinIdentificadoresException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.NumeroMaximoResultadosSuperadoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.IdentificadoresPersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.utility.PersonaFisicaServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.utility.PersonaFisicaServiceValidateLocal;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.utility.PersonaMoralServiceValidateLocal;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.DatosSalidaPaginador;
import mx.gob.imss.ctirss.gestionpersonas.servicios.entity.PersonaFisicaServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.ModuloEnum;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.enums.CambioComparacionEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoAdministracionEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoIdentificadorEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.*;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.nss.Serie;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSociedad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCambioInformacionPersona;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteMoral;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.gestionpersonas.servicios.entity.PersonaEntityLocal;
import mx.gob.imss.ctirss.gestionpersonas.servicios.entity.PersonaMoralEntityLocal;
import mx.gob.imss.ctirss.gestionpersonas.servicios.entity.ValidacionesComunes;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessLocal;
import mx.gob.imss.ctirss.gestionpersonas.servicios.utility.AfectarDatosPersonaUtilityLocal;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

@Stateless(name = "personaBusiness", mappedName = "personaBusiness")
public class PersonaBusiness extends AbstractServiceBusiness implements PersonaBusinessRemote, PersonaBusinessLocal {
   
    @EJB
    private transient PersonaEntityLocal personaEntity;

    @EJB
    private transient PersonaMoralEntityLocal personaMoralEntity;

    @EJB
    private transient PersonaFisicaServiceUtilityLocal personaFisicaServiceUtility;
    
    @EJB
    private ComponentesExternosBusinessLocal componentesExternosBusiness;
    
    @EJB
    private ServiciosPersonaBusinessLocal serviciosPersonaBusiness;
    
    @EJB
    private IdentificadoresPersonaFisicaServiceBusinessRemote identificadorPersonaFisicaServiceBusiness;

    @EJB
    private SolicitudBusinessRemote solicitudBusiness;
    
    @EJB
    private AfectarDatosPersonaBusinessRemote afectarDatosPersonaBusiness;
    
    @EJB
    private AfectarDatosPersonaUtilityLocal afectarDatosPersonaUtility;

    @EJB
    private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
    
    @EJB
    private PersonaMoralBusinessRemote personaMoralBusiness;
    
    @EJB
    private PersonaFisicaServiceValidateLocal personaFisicaServiceValidate;
    
    @EJB
    private PersonaMoralServiceValidateLocal personaMoralServiceValidate;
    
	//Preguntar  VIC
	@EJB
	PersonaFisicaServiceEntityLocal personaFisicaServiceEntity;
    
    public Fisica getDatosComplementariosPersonaFisica(Long idPersona){
    	Fisica persona = new Fisica();

		try {
			persona = serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(idPersona);
		} catch (PersonaFisicaNoEncontradaException e) {
			log.error(e);
		}

    	return persona;
    }

    @Override
    public Fisica getPersonaFisica(final Long idPersona) {
        Fisica personaFisica = null; // NOPMD
        if (idPersona != null) {
            final Fisica personaFisicaParam = new Fisica();
            personaFisicaParam.setIdPersona(idPersona);
            final List<Fisica> personas = personaEntity.buscarPersonaFisica(personaFisicaParam, 0, 0);
            if (personas.isEmpty()) {
                log.debug("No se ha encontrado la persona de id " + idPersona);
            } else {
                if (personas.size() > 1) {
                    log.warn("La busqueda por id ha arrojado mas de un resultado idPersona = " + idPersona);
                }
                personaFisica = personas.get(0);
            }
        }
        return personaFisica;
    }

    @Override
    public String getRazonSocial(Long idPersona, Long tipoPersona) {
        String nombreRazonSocial = null; // NOPMD

            nombreRazonSocial = personaEntity.getRazonSocial(idPersona,tipoPersona);
            if (nombreRazonSocial== null || nombreRazonSocial.isEmpty()) {
                log.debug("No se ha encontrado la razon social de  " + idPersona);
            } else {
                log.debug("Se encontro la razon social de " + idPersona);
            }

        return nombreRazonSocial;
    }
    
    @Override
    public String getRazonSocial(String rfc,String razonSocial) {
        String nombreRazonSocial = null; // NOPMD

            nombreRazonSocial = personaEntity.getRazonSocial(rfc, razonSocial);
            if (nombreRazonSocial== null || nombreRazonSocial.isEmpty()) {
                log.debug("No se ha encontrado la razon social de  " + rfc);
            } else {
                log.debug("Se encontro la razon social de " + rfc);
            }

        return nombreRazonSocial;
    }


    @Override
    public Fisica altaPersonaFisica(final Fisica personaFisica) throws DomicilioNoValidoException {
        Fisica personaFisicaResultado = null;

        //VERIFICA SI LA PERSONA FISICA NO VIENE NULA
        if (personaFisica != null) {

        	try{
        		//ALTA DE DOMICILIOS ASIGNADOS A LA PERSONA
        		componentesExternosBusiness.altaDomicilios(personaFisica);
            	
        		//ALTA DE MEDIOS DE CONTACTO ASIGNADOS A LA PERSONA
        		componentesExternosBusiness.altaMediosContacto(personaFisica);
        		
        		//ALTA DE DOCUMENTOS PROBATORIOS
        		componentesExternosBusiness.altaDocumentosProbatorios(personaFisica);        	
           	}
        	catch(Exception e){
        		e.printStackTrace();
        	} 
            personaFisicaResultado = personaEntity.altaPersonaFisica(personaFisica);
        }
        return personaFisicaResultado;
    }

    @Override
    public Fisica buscarPersonaFisicaPorRfcEnSat(String rfc) throws ClienteWebserviceSatRfcException { 
		ClienteWebserviceRfc clienteWebserviceRfc = new ClienteWebserviceRfc();			
        return clienteWebserviceRfc.buscarPersonaFisicaPorRfcEnSat(rfc);
    }

    @Override
    public Moral buscarPersonaMoralPorRfcEnSat(final String rfc) throws ClienteWebserviceSatRfcException {    
		ClienteWebserviceRfc clienteWebserviceRfc = new ClienteWebserviceRfc();
		Moral personaMoral = clienteWebserviceRfc.buscarPersonaMoralPorRfcEnSat(rfc);
		
		if (personaMoral != null){
			//OBTENEMOS EL ID-TIPO-SOCIEDAD EN BASE A LA DESCRIPCION RECUPERADA EN EL SERVICIO DEL SAT
	        TipoSociedad tipoSociedad = personaMoralEntity.getTipoSociedadByDescripcion(personaMoral.getTipoSociedad().getDescripcionAbreviada());

	        System.out.println("Tipo de sociedad\n\n " + tipoSociedad);
	        if (tipoSociedad != null) {
	        	personaMoral.setTipoSociedad(tipoSociedad);
	        }			
		}
        return personaMoral;
    }

    @Override
    public List<Fisica> buscarPersonaFisicaPorRfcEnImss(final String rfc) {
        List<Fisica> response = null;
        if (rfc != null) {
            final Fisica persona = new Fisica();
            persona.setRfc(rfc);
            response = personaEntity.buscarPersonaFisica(persona, 0, 0);
        }
        return response;
    }

    @Override
    public List<Fisica> buscarPersonaFisicaPorCurpEnImss(final String curp) {
        List<Fisica> listaPersonasFisicas = null;
        if (curp != null) {
            final Fisica persona = new Fisica();
            persona.setCurp(curp);
            listaPersonasFisicas = personaEntity.buscarPersonaFisica(persona, 0, 0);
        }
        return listaPersonasFisicas;
    }

    @Override
    public DatosSalidaPaginador<Fisica> getPersonaFisicaFiltro(final DatosEntradaPaginador<Fisica> paramsPager) throws NumeroMaximoResultadosSuperadoException {
        log.debug("PersonaBusiness. getPersonaFisicaFiltro: " + paramsPager.getModelo());
        log.debug("PersonaBusiness. getPersonaFisicaFiltro Start: " + paramsPager.getiDisplayStart());
        log.debug("PersonaBusiness. getPersonaFisicaFiltro Length: " + paramsPager.getiDisplayLength());

        //OBTENEMOS LA CONSULTA DE LAS PERSONAS FISICAS EN BASE AL FILTRO DE BUSQUEDA
        List<Fisica> listaPersonasFisicasResultado = personaEntity.buscarPersonaFisica(paramsPager.getModelo(), paramsPager.getiDisplayStart(), paramsPager.getiDisplayLength());
        DatosSalidaPaginador<Fisica> response = new DatosSalidaPaginador<Fisica>();

        if (listaPersonasFisicasResultado != null) {
            response.setAaData(listaPersonasFisicasResultado);
            response.setiTotalDisplayRecords(personaEntity.getTotalRegistrosBusquedaPersonaFisica());
            final Integer iTotalRecords = personaEntity.contarNumPersonasTotal().intValue();
            response.setiTotalRecords(iTotalRecords);
        } else {
            response.setAaData(new ArrayList<Fisica>());
            response.setiTotalDisplayRecords(0);
        }
        ValidacionesComunes.validaMaximoResultadosConsulta(response.getiTotalDisplayRecords());

        return response;
    }

	public Fisica buscarPersonaFisicaPorDatosBasicosEnRenapo(
			final String sNombres, final String sPrimerApellido,
			final String sSegundoApellido, final int iIdSexo,
			final Date fechaNacimiento, final int iIdEntidadFederativa)
			throws ClienteWebserviceRenapoCurpException {
		Fisica fisica = null;

		ClienteWebserviceCurp cliente = new ClienteWebserviceCurp();
		fisica = cliente.buscarPersonaFisicaPorDatosBasicosEnRenapo(sNombres,
				sPrimerApellido, sSegundoApellido, iIdSexo, fechaNacimiento,
				iIdEntidadFederativa);

		return fisica;
	}

	@Override
	public Fisica buscarPersonaFisicaPorCurpEnRenapo(final String curp)
			throws ClienteWebserviceRenapoCurpException {

		this.log.debug("buscarPersonaFisicaPorCurpEnRenapo LALO: " + curp);
		Fisica fisica = new Fisica();

		//ClienteWebserviceCurp cliente = new ClienteWebserviceCurp();
		//fisica = cliente.buscarPersonaFisicaPorCurpEnRenapo(curp);
		
		//Fisica fisicaRenapo = new Fisica();
		fisica.setCurp(curp);
		
		
		
		Calendar nac = Calendar.getInstance();
		nac.set(1989, 10, 20);
		fisica.setFechaNacimiento(nac.getTime());
		
		fisica.setRfc("AAAAXXXXXXHHH");
		
		this.log.debug(" Persona encontrada en el RENAPO " + fisica);
		return fisica;
	}
 
    @Override
    public List<Fisica> buscarPersonaFisicaPorDatosBasicosEnImss(final Fisica personaFisica) {
        System.out.println("\n\nbuscarPersonaFisicaPorDatosBasicosEnImss. Inicio " + new Date());
        System.out.println("buscarPersonaFisicaPorDatosBasicosEnImss. Datos de entrada: " + personaFisica);
        List<Fisica> respuesta = personaEntity.buscarPersonaFisica(personaFisica, 0, 0);
        System.out.println("\buscarPersonaFisicaPorDatosBasicosEnImss. Final " + new Date() + "\n\n");    	
        return respuesta;
    }
    
    /**
     * Servicio para actualizar una Persona, pero de momento s�lo se actulizar� la fecha de defunci�n
     * @param persona
     */
    public void actualizarPersona(final Fisica fisica) throws PersonaNoEncontradaException {
    	this.log.debug("Iniciando la actualizacion de la persona ==[" + fisica + "]====" );
    	personaEntity.actualizarPersona(fisica);
    }
    
    public List<Serie> getSeriesNss(Long idDelegacion, Long idSubDelegacion){
    	return personaEntity.getSeriesNss(idDelegacion, idSubDelegacion);
    }
    
    /**
     * Este metodo recibe un objeto Serie del cual extraemos el idSerie y regresamos un objeto Serie completo
     * @param serie
     * @return
     */
    public Serie getSerie(Serie serie){
    	return personaEntity.getSerie(serie);
    }
    
    /**
     * 191807 211112
     * Metodo que busca una persona fisica en el IMSS y en entidades externas, que regresa un objeto para peticiones asincronas
     * -/persona/fisica/busqueda-embebida
     * @param pf
     * @return
     * @throws NumeroMaximoResultadosSuperadoException
     * @throws ClienteWebserviceSatRfcException
     * @throws ClienteWebserviceRenapoCurpException
     */
    @Override
    public DatosSalidaPaginador<Fisica> buscarPersonaFisicaEnIMSSyEE(Fisica pf) throws NumeroMaximoResultadosSuperadoException, ClienteWebserviceSatRfcException, ClienteWebserviceRenapoCurpException {

        // Primero buscamos a la persona fisica en el IMSS, 
        List<Fisica> listaPersonasFisicasResultado = serviciosPersonaBusiness.localizarPersonaFisica(pf.getCurp(), pf.getRfc(), pf.getNombre(), pf.getPrimerApellido(), pf.getSegundoApellido(), pf.getFechaNacimiento(), pf.getLugarNacimiento(), pf.getSexo());
        DatosSalidaPaginador<Fisica> response = new DatosSalidaPaginador<Fisica>();

        if (listaPersonasFisicasResultado != null) {
            response.setAaData(listaPersonasFisicasResultado);
            response.setiTotalDisplayRecords(listaPersonasFisicasResultado.size());
            final Integer iTotalRecords = personaEntity.contarNumPersonasTotal().intValue();
            response.setiTotalRecords(iTotalRecords);
        } else {
            response.setAaData(new ArrayList<Fisica>());
            response.setiTotalDisplayRecords(0);
        }
        ValidacionesComunes.validaMaximoResultadosConsulta(response.getiTotalDisplayRecords());

        return response;
    }
   
    /**
     * 
     */
    public Fisica buscarPersnaPorID(Long idPersona) throws PersonaNoEncontradaException {
    	Fisica personaRet = personaEntity.buscarPersonaPorId(idPersona); 
    	
    	if (personaRet==null) {
    		 throw new PersonaNoEncontradaException(idPersona);
    	}    	
 
    	return personaRet;
    }

	@Override
	public void afectarDatosPersona(AfectarDatosPersonaWrapper datosPersona)
			throws PersonaNoEncontradaException {

		this.personaEntity.afectarDatosPersona(datosPersona);
		
	}
	
	@Override
	public void afectarCalificacionesPersona(AfectarDatosPersonaWrapper datosPersona){
		this.personaEntity.afectarCalificacionesPersona(datosPersona);
	}
	
	@Override
	public void afectarIdentificadoresPersona(AfectarDatosPersonaWrapper datosPersona){
		
		this.log.debug("Se van a modificar los identificadores de la persona [cveMoral = "
				+ datosPersona.getFisica().getIdPersona() + "]");
		
		Fisica fisica = new Fisica();
		fisica.setCveFisica(datosPersona.getFisica().getCveFisica());
		fisica.setIdPersona(datosPersona.getFisica().getIdPersona());
		
		fisica.setCurp(datosPersona.getFisica().getCurp());
		fisica.setRfc(StringUtils.isNotBlank(datosPersona.getFisica()
				.getRfcVigente()) ? datosPersona.getFisica().getRfcVigente()
				: datosPersona.getFisica().getRfc());
		
		try {
			// Se obtienen los identificadores vigentes de la persona
			List<Identificador> identificadores = this.identificadorPersonaFisicaServiceBusiness
					.obtenerIdentificadoresPersona(fisica.getIdPersona());
			List<Identificador> nuevosIdentificadores = new ArrayList<Identificador>();
			
			for(Identificador identificador : identificadores){
				// Se checa el tipo de identificador y que tenga algun valor asignado 
				if (identificador.getTipoIdentificador()
						.getIdTipoIdentificador() == TipoIdentificadorEnum.CURP
						.getCodigo() && StringUtils.isNotBlank(fisica.getCurp())) {
					/*
					 * Si el identificador actual es diferente al capturado se
					 * debe guardar
					 */
					if(!identificador.getIdentificadora().equals(fisica.getCurp())){
						
						this.log.debug("Se tiene un identificador CURP diferente al vigente");
						
						this.identificadorPersonaFisicaServiceBusiness.expirarIdentificador(identificador);
						
						Identificador identificadorNuevo = new Identificador();
						
						TipoIdentificador tipoIdentificador = new TipoIdentificador();
						tipoIdentificador.setIdTipoIdentificador(TipoIdentificadorEnum.CURP.getCodigo());
						
						identificadorNuevo.setTipoIdentificador(tipoIdentificador);
						identificadorNuevo.setVigente(1);
						
						nuevosIdentificadores.add(identificadorNuevo);
						
					}
				} else if (identificador.getTipoIdentificador()
						.getIdTipoIdentificador() == TipoIdentificadorEnum.RFC
						.getCodigo() && StringUtils.isNotBlank(fisica.getRfc())) {
					if(!identificador.getIdentificadora().equals(fisica.getRfc())){
						
						this.log.debug("Se tiene un identificador RFC diferente al vigente");
						
						this.identificadorPersonaFisicaServiceBusiness.expirarIdentificador(identificador);
						
						Identificador identificadorNuevo = new Identificador();
						
						TipoIdentificador tipoIdentificador = new TipoIdentificador();
						tipoIdentificador.setIdTipoIdentificador(TipoIdentificadorEnum.RFC.getCodigo());
						
						identificadorNuevo.setTipoIdentificador(tipoIdentificador);
						identificadorNuevo.setVigente(1);
						
						nuevosIdentificadores.add(identificadorNuevo);
					}
				}
			}
			
			if(!nuevosIdentificadores.isEmpty()){
				try {
					fisica.setIdentificadores(nuevosIdentificadores);
					this.identificadorPersonaFisicaServiceBusiness.registrar(fisica);
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
			
			if(StringUtils.isNotBlank(fisica.getCurp())){
				Identificador identificador = new Identificador();
				
				TipoIdentificador tipoIdentificador = new TipoIdentificador();
				tipoIdentificador.setIdTipoIdentificador(TipoIdentificadorEnum.CURP.getCodigo());
				
				identificador.setTipoIdentificador(tipoIdentificador);
				identificador.setVigente(1);
				
				identificadores.add(identificador);
			}
			
			if(StringUtils.isNotBlank(fisica.getRfc())){
				Identificador identificador = new Identificador();
				
				TipoIdentificador tipoIdentificador = new TipoIdentificador();
				tipoIdentificador.setIdTipoIdentificador(TipoIdentificadorEnum.RFC.getCodigo());
				
				identificador.setTipoIdentificador(tipoIdentificador);
				identificador.setVigente(1);
				
				identificadores.add(identificador);
			}
			
			try {
				fisica.setIdentificadores(identificadores);
				this.identificadorPersonaFisicaServiceBusiness.registrar(fisica);
			} catch (IdentificadoresNoExistentesException e1) {
				this.log.warn(e1);
			}
		}
	}
	
	@Override
	public String obtenerNombrePersona(Persona persona) {
		return this.personaEntity.obtenerNombrePersona(persona);
	}

	@Override
	public String obtenerCurpPersona(Long idPersona) {
		return personaEntity.obtenerCurpPersona(idPersona);
	}

	@Override
	public Integer obtenerEdadPersona(Long idPersona) {
		return personaEntity.obtenerEdadPersona(idPersona);
	}

	@Override
	public void ejecutarTramiteCambioInfoPersona(Tramite tramite,
			Long idSolicitud, Long idModulo) throws AfectacionDatosPersonaException,
			PersonaNoEncontradaException, RegistroPersonaFisicaException {

		if (tramite instanceof TramiteFisica) {
			TramiteFisica tramiteFisica = (TramiteFisica) tramite;

			if (tramiteFisica.getFisica() != null) {
				/*
				 * Si el atributo fisica es diferente de nulo, significa que la
				 * persona no existe y se debe crear
				 */
				this.personaFisicaServiceBusiness.registrar(tramiteFisica.getFisica());
				
			} else if (tramiteFisica.getDatosICA() != null || tramiteFisica.getDatosMDM() != null) {

				TramiteCambioInformacionPersona tramiteCambioInfo = new TramiteCambioInformacionPersona();
				TramiteFisica tramiteFisicaAux = new TramiteFisica();
				Map<String, CambioComparacionEnum> diferencias = null;
				
				if (tramiteFisica.getDatosICA() != null) {
					diferencias = tramiteFisica.getDatosICA().getCambios();
					
					tramiteCambioInfo.setDatosICA(tramiteFisica.getDatosICA());
					
					tramiteFisicaAux.setDatosICA(tramiteFisica.getDatosICA());
					tramiteFisicaAux.setFisica(tramiteFisica.getDatosICA().getPersonaFisicaIMSS());
				} else if (tramiteFisica.getDatosMDM() != null) {
					diferencias = tramiteFisica.getDatosMDM().getCambios();
					
					tramiteCambioInfo.setDatosModifManual(tramiteFisica.getDatosMDM());
					
					tramiteFisicaAux.setDatosMDM(tramiteFisica.getDatosMDM());
					tramiteFisicaAux.setFisica(tramiteFisica.getDatosMDM().getPersonaFisica());
				}
				
				// Se valida que hayan existido cambios que realizar
				if (afectarDatosPersonaUtility.existenDiferencias(diferencias)) {
					// Se crea el tramite y se asocia a la solicitud recibida
					Date fechaActual = new Date();
					tramiteFisicaAux.setFechaTramite(fechaActual);
					tramiteFisicaAux.setFechaConclusion(fechaActual);
					tramiteFisicaAux.setFechaEfecto(fechaActual);
					tramiteFisicaAux.setFechaPresentacion(fechaActual);
					
					EstadoTramite estadoTramite = new EstadoTramite();
					estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getCodigo());
					estadoTramite.setDescripcion(EstadoTramiteEnum.INICIADO.getDescripcion());
					tramiteFisicaAux.setEstadoTramite(estadoTramite);
					
					TipoTramite tipoTramite = new TipoTramite();
					tipoTramite.setIdTipoTramite(TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES.getCodigo());
					tramiteFisicaAux.setTipoTramite(tipoTramite);
	
					tramiteFisicaAux = (TramiteFisica) this.solicitudBusiness.crearTramiteASolicitud(tramiteFisicaAux, idSolicitud);
					
					tramiteCambioInfo.setTramiteId(tramiteFisicaAux.getTramiteId());
					
					Modulo modulo = new Modulo();
					modulo.setIdModulo(idModulo);
	
					// Llamada al servicio de afectaci�n de datos de una persona
					this.afectarDatosPersonaBusiness.afectarDatos(tramiteCambioInfo,modulo);
				} else {
					this.log.debug("No existen cambios que afectar para la persona f�sica recibida");
				}

			}
		} else if (tramite instanceof TramiteMoral) {
			TramiteMoral tramiteMoral = (TramiteMoral) tramite;

			if (tramiteMoral.getMoral() != null) {
				/*
				 * Si el atributo fisica es diferente de nulo, significa que la
				 * persona no existe y se debe crear
				 */
				this.personaMoralBusiness.altaPersonaMoral(tramiteMoral.getMoral());
				
			} else if (tramiteMoral.getDatosICA() != null || tramiteMoral.getDatosMDM() != null) {

				TramiteCambioInformacionPersona tramiteCambioInfo = new TramiteCambioInformacionPersona();
				TramiteMoral tramiteMoralAux = new TramiteMoral();
				Map<String, CambioComparacionEnum> diferencias = null;
				
				if (tramiteMoral.getDatosICA() != null) {
					diferencias = tramiteMoral.getDatosICA().getCambios();
					
					tramiteCambioInfo.setDatosICA(tramiteMoral.getDatosICA());
					
					tramiteMoralAux.setDatosICA(tramiteMoral.getDatosICA());
					tramiteMoralAux.setMoral(tramiteMoral.getDatosICA().getPersonaMoralIMSS());
				} else if (tramiteMoral.getDatosMDM() != null) {
					diferencias = tramiteMoral.getDatosMDM().getCambios();
					
					tramiteCambioInfo.setDatosModifManual(tramiteMoral.getDatosMDM());
					
					tramiteMoralAux.setDatosMDM(tramiteMoral.getDatosMDM());
					tramiteMoralAux.setMoral(tramiteMoral.getDatosMDM().getPersonaMoral());
				}
				
				// Se valida que hayan existido cambios que realizar
				if (afectarDatosPersonaUtility.existenDiferencias(diferencias)) {
					// Se crea el tramite y se asocia a la solicitud recibida
					Date fechaActual = new Date();
					tramiteMoralAux.setFechaTramite(fechaActual);
					tramiteMoralAux.setFechaConclusion(fechaActual);
					tramiteMoralAux.setFechaEfecto(fechaActual);
					tramiteMoralAux.setFechaPresentacion(fechaActual);
					
					EstadoTramite estadoTramite = new EstadoTramite();
					estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getCodigo());
					estadoTramite.setDescripcion(EstadoTramiteEnum.INICIADO.getDescripcion());
					tramiteMoralAux.setEstadoTramite(estadoTramite);
					
					TipoTramite tipoTramite = new TipoTramite();
					tipoTramite.setIdTipoTramite(TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES.getCodigo());
					tramiteMoralAux.setTipoTramite(tipoTramite);
	
					tramiteMoralAux = (TramiteMoral) this.solicitudBusiness.crearTramiteASolicitud(tramiteMoralAux, idSolicitud);
					
					tramiteCambioInfo.setTramiteId(tramiteMoralAux.getTramiteId());
					
					Modulo modulo = new Modulo();
					modulo.setIdModulo(idModulo);
	
					// Llamada al servicio de afectaci�n de datos de una persona
					this.afectarDatosPersonaBusiness.afectarDatos(tramiteCambioInfo,modulo);
				} else {
					this.log.debug("No existen cambios que afectar para la persona moral recibida");
				}

			}
		}
	}
	
	public Fisica consutalPersonaByCurpConCalificion (String strCURP)throws ExisteMasDeUnConjuntoDeCalifiacionesPorCurpException{
		long cveIdPersonaComparar = 0;
		int numRegistroLista = 0;
		int countMaxima =0;
		int countParcial= 0;
		int numRegistro=0;
		Fisica objFisicaCalificacion = null;
		try{
//			List <DitPersona> objLstPersona =(List<DitPersona>) this.personaEntity.getPersonasConCalificacionByCurp(strCURP);
			List <DitPersona> objLstPersona =(List<DitPersona>) this.personaEntity.getPersonaEscVirtualByCurp(strCURP);

			for(DitPersona objPersona :objLstPersona ){
				this.log.debug("entrando a iterar los registros");
				//valuacion de las personas para determinar a la persona con la califiacion mas alta o con mas calificaciones
			    if(cveIdPersonaComparar == objPersona.getCveIdPersona().longValue()){
			    	countParcial++;
			    	if(countParcial >countMaxima){
			    		countMaxima = countParcial;
			    		numRegistroLista =numRegistro;
			    		this.log.debug("incrementa al meximo");
			    	}
					
				}else{
					
					cveIdPersonaComparar = objPersona.getCveIdPersona().longValue();
					numRegistroLista =numRegistro;
					countParcial =1;
					
				}
			 
			    numRegistro++;	
			}
			if(numRegistro >0){
				this.log.debug("recupera a la persona "+  numRegistroLista);
				DitPersona objDitPersonaFinal = (DitPersona) objLstPersona.get(numRegistroLista);
				objFisicaCalificacion = personaFisicaServiceUtility.transformarAModelo(objDitPersonaFinal);
			}
		}catch(Exception e){
			this.log.error("ocurrio un error no esperado" ,e);
			
		}
		
		return objFisicaCalificacion;
	}
	
	@Override
	public List<Fisica> obtenerPersonaNssByCurp(String curp) {
		
		return this.personaEntity.obtenerPersonaNssByCurp(curp);
	}

    @Override
    public List<Fisica> obtenerPersonaNssByCurpNoIndActivo(String curp) {

        return this.personaEntity.obtenerPersonaNssByCurpNoIndActivo(curp);
    }
	
	@Override
	public List<AsignacionNSS> obtenerNsssByCurp(String curp) {
		
		return this.personaEntity.obtenerNsssByCurp(curp);
	}

	@Override
	public String obtenerNssPersona(Long idPersona)
			throws PersonaConVariosNSSException, PersonaSinNSSException {
		
		List<String> listaNSS = this.personaEntity.obtenerNssPersona(idPersona, false);
		String nss = null;
		
		if (!CollectionUtils.isEmpty(listaNSS)){
			if (listaNSS.size() == 1) {
				nss = listaNSS.get(0);
			} else {
				throw new PersonaConVariosNSSException(idPersona);
			}
		} else {
			this.log.info("La persona " + idPersona + " no cuenta con NSS");
			throw new PersonaSinNSSException(idPersona);
		}
		
		return nss;
	}
	
	@Override
	public String obtenerNssVigentePersona(Long idPersona)
			throws PersonaConVariosNSSException, PersonaSinNSSException {
		
		List<String> listaNSS = this.personaEntity.obtenerNssPersona(idPersona, true);
		String nss = null;
		
		if (!CollectionUtils.isEmpty(listaNSS)){
			if (listaNSS.size() == 1) {
				nss = listaNSS.get(0);
			} else {
				throw new PersonaConVariosNSSException("La persona " + idPersona + " cuenta con m�s de un NSS vigente");
			}
		} else {
			String msg = "La persona " + idPersona + " no cuenta con NSS vigente";
			this.log.info(msg);
			throw new PersonaSinNSSException(msg);
		}
		
		return nss;
	}

	@Override
	public Persona registrarPersonaConDatosSat(Persona persona,
			boolean crearSolicitud)throws ClienteWebserviceSatRfcException, DomicilioNoValidoException, SolicitudNoValidaException {
		TipoPersonaFiscal tp=null;
		Fiel fiel = persona.getFiel();
		
		if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.FISICA.longValue()))
			tp = TipoPersonaFiscal.FISICA;
		else
			tp = TipoPersonaFiscal.MORAL;
		
		Persona personaRegistrada=null;
		
		ICADatosRespuesta icaRespuesta = null;
		
		switch(tp){
			case FISICA:
				Fisica personaFisica = this.buscarPersonaFisicaPorRfcEnSat(persona.getRfc());
				personaFisica = altaPersonaFisica(personaFisica);
				personaEntity.agregarDatosPersonaFisica(personaFisica);
				personaFisica.setTipoPersona(persona.getTipoPersona());
				icaRespuesta = ejecutarICA(personaFisica);
				personaRegistrada = icaRespuesta.getPersonaFisicaIMSS();
				personaRegistrada.setTipoPersona(persona.getTipoPersona());
				personaRegistrada.setFiel(fiel);
				registrarDatosCertificadoPersona(personaRegistrada);
				break;
				
			case MORAL:
				Moral personaMoral = this.buscarPersonaMoralPorRfcEnSat(persona.getRfc());
				personaMoral = personaMoralBusiness.altaPersonaMoral(personaMoral);
				personaMoral.setTipoPersona(persona.getTipoPersona());
				icaRespuesta = ejecutarICA(personaMoral);
				personaRegistrada = icaRespuesta.getPersonaMoralIMSS();
				personaRegistrada.setTipoPersona(persona.getTipoPersona());
				personaRegistrada.setFiel(fiel);
				registrarDatosCertificadoPersona(personaRegistrada);
				break;
		}
		
		if(crearSolicitud){
			Solicitud solicitud=inicializarSolicitudDeAltaPersona(icaRespuesta);
			solicitudBusiness.crear(solicitud);
			afectarDatosICA(solicitud.getTramites().get(0));
		}
		
		return personaRegistrada;
	}
	
	private void afectarDatosICA(Tramite tramite){
		try{
			System.err.println("Tramite Identificado");
			Modulo modulo = new Modulo();
			modulo.setIdModulo(ModuloEnum.PATRONES.getCodigo().longValue());
			if(tramite instanceof TramiteMoral){
				System.err.println("Tramite Moral Identificado");
                log.info("afectarDatos(TramiteMoral, Modulo)");
                TramiteCambioInformacionPersona tcp = new TramiteCambioInformacionPersona();
                tcp.setTramiteId(tramite.getTramiteId());
                tcp.setDatosICA(((TramiteMoral)tramite).getDatosICA());
                tcp.setDatosModifManual(((TramiteMoral)tramite).getDatosMDM());
                afectarDatosPersonaBusiness.afectarDatos(tcp, modulo);
                log.info("despues de personaService.afectarDatos(TramiteFisica, Modulo)");
			}
			if(tramite instanceof TramiteFisica){
                TramiteFisica tramiteFisica = (TramiteFisica) tramite;
                log.info("afectarDatos(TramiteFisica, Modulo)");
                TramiteCambioInformacionPersona tcp = new TramiteCambioInformacionPersona();
                tcp.setTramiteId(tramiteFisica.getTramiteId());
                tcp.setDatosICA(tramiteFisica.getDatosICA());
                tcp.setDatosModifManual(tramiteFisica.getDatosMDM());
                afectarDatosPersonaBusiness.afectarDatos(tcp, modulo);//Instruccion problematica. Encolar mensaje primero
                log.info("despues de personaService.afectarDatos(TramiteFisica, Modulo)");
                
			}
			
			
			//enviarMovimientosDeActualizacionDatosGeneralesASindo(persona, notificarSindo);
			
		} catch (AfectacionDatosPersonaException e) {
			e.printStackTrace();
		} catch (PersonaNoEncontradaException e) {
			e.printStackTrace();
		}
	}
		
	private Solicitud inicializarSolicitudDeAltaPersona(ICADatosRespuesta icaDatosRespuesta){
		Solicitud solicitud = new Solicitud();
		TipoSolicitud tipoSolicitud = new TipoSolicitud();
		tipoSolicitud.setIdTipoSolicitud(TipoSolicitudEnum.REGISTRO_PERSONA.getId());
		EstadoSolicitud estadoSolicitud = new EstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getCodigo());
		EstadoTramite estadoTramite = new EstadoTramite();
		estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
		TipoTramite tipoTramite = new TipoTramite();
		
		
		solicitud.setTipoSolicitud(tipoSolicitud);
		solicitud.setEstadoSolicitud(estadoSolicitud);
		solicitud.setTramites(new ArrayList<Tramite>());
		
		if(icaDatosRespuesta.getPersonaFisicaIMSS()!=null){
			TramiteFisica tramiteFisica=new TramiteFisica();
			tramiteFisica.setEstadoTramite(estadoTramite);
			tramiteFisica.setFisica(icaDatosRespuesta.getPersonaFisicaIMSS());
			tipoTramite.setIdTipoTramite(TipoTramiteEnum.REGISTRO_DE_PERSONA.getCodigo());
			tramiteFisica.setTipoTramite(tipoTramite);
			tramiteFisica.setDatosICA(icaDatosRespuesta);
			solicitud.getTramites().add(tramiteFisica);
		}else{
			TramiteMoral tramiteMoral = new TramiteMoral();
			tramiteMoral.setEstadoTramite(estadoTramite);
			tramiteMoral.setMoral(icaDatosRespuesta.getPersonaMoralIMSS());
			tipoTramite.setIdTipoTramite(TipoTramiteEnum.REGISTRO_PERSONA_MORAL.getCodigo());
			tramiteMoral.setTipoTramite(tipoTramite);
			tramiteMoral.setDatosICA(icaDatosRespuesta);
			solicitud.getTramites().add(tramiteMoral);
		}
		
		return solicitud;
	}
	
	private Solicitud inicializarSolicitudDeAltaPersona(Persona persona) {
		Solicitud solicitud = new Solicitud();
		TipoSolicitud tipoSolicitud = new TipoSolicitud();
		tipoSolicitud.setIdTipoSolicitud(TipoSolicitudEnum.REGISTRO_PERSONA
				.getId());
		EstadoSolicitud estadoSolicitud = new EstadoSolicitud(
				EstadoSolicitudEnum.ATENDIDA.getCodigo());
		EstadoTramite estadoTramite = new EstadoTramite();
		estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO
				.getCodigo());
		TipoTramite tipoTramite = new TipoTramite();

		solicitud.setTipoSolicitud(tipoSolicitud);
		solicitud.setEstadoSolicitud(estadoSolicitud);
		solicitud.setTramites(new ArrayList<Tramite>());

		if (persona instanceof Fisica) {

			Fisica fisica = (Fisica) persona;

			TramiteFisica tramiteFisica = new TramiteFisica();
			tramiteFisica.setEstadoTramite(estadoTramite);
			tramiteFisica.setFisica(fisica);
			tipoTramite.setIdTipoTramite(TipoTramiteEnum.REGISTRO_DE_PERSONA
					.getCodigo());
			tramiteFisica.setTipoTramite(tipoTramite);
			solicitud.getTramites().add(tramiteFisica);
		} else {

			Moral moral = (Moral) persona;

			TramiteMoral tramiteMoral = new TramiteMoral();
			tramiteMoral.setEstadoTramite(estadoTramite);
			tramiteMoral.setMoral(moral);
			tipoTramite.setIdTipoTramite(TipoTramiteEnum.REGISTRO_PERSONA_MORAL
					.getCodigo());
			tramiteMoral.setTipoTramite(tipoTramite);
			solicitud.getTramites().add(tramiteMoral);
		}

		return solicitud;
	}
	
	private ICADatosRespuesta ejecutarICA(Persona persona){
		ICADatosConsulta icaDatosConsulta = new ICADatosConsulta();
		ICADatosRespuesta icaDatosRespuesta =null;
		try{
			// se setean los datos requeridos para hacer la consulta de
			// informaci�n completa
			icaDatosConsulta.setIndicadorConsultaSAT(Boolean.TRUE);
			icaDatosConsulta.setIndicadorMostrarPantalla(Boolean.TRUE);
						
			if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersonaEnum.FISICA.getId())){
				Fisica objfisicaRecuperado = this.serviciosPersonaBusiness
					.buscarPersonaFisicayDPyDyMCEnIMSS(persona.getIdPersona());
				persona.setRfc(objfisicaRecuperado.getRfc());
				icaDatosConsulta.setPersonaFisica(objfisicaRecuperado);
				icaDatosConsulta.setIndicadorConsultaRENAPO(Boolean.TRUE);
				icaDatosRespuesta = personaFisicaServiceBusiness.identificarCambios(icaDatosConsulta);
				icaDatosRespuesta = personaFisicaServiceBusiness.integrarCambios(icaDatosRespuesta);
				return icaDatosRespuesta;
			}else{
				Moral objMoralRecuperado = this.serviciosPersonaBusiness
						.buscarPersonaMoralyDPyDyMCEnIMSS(persona.getIdPersona());
				
				persona.setRfc(objMoralRecuperado.getRfc());
				icaDatosConsulta.setPersonaMoral(objMoralRecuperado);
				icaDatosRespuesta = personaMoralBusiness.identificarCambios(icaDatosConsulta);
				icaDatosRespuesta = personaMoralBusiness.integrarCambios(icaDatosRespuesta);
				
				return icaDatosRespuesta;
			}
		
			// Se detectan cambios en ICA
		} catch (ComparacionSinDiferenciasException e1) {
			log.error("No se encontraron diferencias");
		} catch (PersonaNoEncontradaException e) {
			e.printStackTrace();
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			e.printStackTrace();
		} catch (ClienteWebserviceRenapoCurpException e) {
			e.printStackTrace();
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			e.printStackTrace();
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			e.printStackTrace();
		} catch (ClienteWebserviceSatRfcException e) {
			e.printStackTrace();
		} catch (ErrorComparacionDatosRENAPOException e) {
			e.printStackTrace();
		} catch (DatosInsuficientesICAException e) {
			e.printStackTrace();
		} catch (DiferenciasRENAPOContraSAT e) {
			e.printStackTrace();
		} catch (PersonaFisicaNoEncontradaException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void registrarDatosCertificadoPersona(Persona persona){
		personaEntity.registrarFiel(persona);
	}

	
	/** 
	 * @Proyecto: GPersona
	 * @Archivo: PersonaBusiness.java
	 * Funcionalidad: Realizara la consulta de persona fiscica por medio del cve_id perosna y se 
	 * realizara la consulta de la FIEL por medio de este dato
	 * @Motivo del cambio: Atenccion a la incidencia 4946384 / INC1052861 
	 * @Fecha: 24/06/2022
	 */
	@Override
	public Fiel obtenerDatosFiel(Persona persona) {
		System.out.println("Entrando a consulta de certificaddos");
		Long idPersonaFisica = null;
		Long idPersonaMoral = null;
		
		System.out.println("Tipo de persona: "+ persona.getTipoPersona().getIdTipoPersona());
		if(persona.getTipoPersona().getIdTipoPersona() == 1){
			System.out.println("Tipo de persona fisca: ");
			
		try {
			idPersonaFisica = personaFisicaServiceEntity.obtenerIDPersonaFisicaEscVirtual(persona.getIdPersona());
		} catch (PersonaFisicaNoEncontradaException e) {
			this.log.info(" -- Fiel no encontrada con el idPersona: "+ idPersonaFisica);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			this.log.info("La persona que se utilizara para la consulta de la FIEL es de tipo Fisica:" + idPersonaFisica);
			persona.setIdPersonaFisica(idPersonaFisica);
		
			
		
		}else if (persona.getTipoPersona().getIdTipoPersona() == 2){
			System.out.println("Tipo de persona moral: ");
			idPersonaMoral= persona.getIdPersona();
			persona.setIdPersona(idPersonaMoral);
			this.log.info("La persona que se utilizara para la consulta de la FIEL es de tipo Moral:" + idPersonaMoral);
		}
		
		this.log.info("El valor de id persona ya sea PF o PM es :" + idPersonaFisica + idPersonaMoral);
		
		return personaEntity.obtenerDatosFiel(persona);
			
	}

	@Override
	public void reportarAcreditacion(Persona persona) {
		personaEntity.actualizarIndAcreditado(persona);
	}
	
	
	@Override
	public List<Fisica> buscarEnPersonaYGrupoFamiliar(final String curp, Long idAsignacionNss) {
		 
		List<Fisica> listaPersonasFisicas = null;
	     
		if (curp != null && idAsignacionNss != null) {
		    listaPersonasFisicas = personaEntity.buscarEnPersonaYGrupoFamiliar(curp, idAsignacionNss);
	    }
	        
	    return listaPersonasFisicas;
	     
	}
	
	
	@Override
	public int totalRegistroPersonaFMPorRFC(Persona persona) {
		return personaEntity.totalRegistroPersonaFMPorRFC(persona);
	}
	
	@Override
	public ValidacionIdentidadTramite validarIdentidad(Persona persona, int tipoTramite) {
		
		@SuppressWarnings("unused")
		final String INFO = "INFO" ;
		final String WARNING = "WARNING";
		final String ERROR = "ERROR";

		/*
		 * S�lo cuando existan error de nivel ERROR, la identidad no ser� v�lida
		 * y el tr�mite no podr� ser ejecutado, en caso de WARNINGS o INFO la
		 * identidad ser� v�lida y el tr�mite pude ser iniciado
		 */
		boolean isIdentidaValida = true;
		ValidacionIdentidadTramite validacionIdentidad = new ValidacionIdentidadTramite();
		List<ErrorValidacionIdentidadTramite> errores = new ArrayList<ErrorValidacionIdentidadTramite>();
		ErrorValidacionIdentidadTramite error = null;		
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		if (tipoTramite == TipoTramiteEnum.ALTA_SRT.getCodigo().intValue()) {
			// Se valida que la persona fisica tenga CURP y RFC
			Fisica fisica = (Fisica) persona;
			
			/*if (StringUtils.isBlank(fisica.getCurp())) {
				parametros.put("CONSULTA_RENAPO", true);
				
				error = new ErrorValidacionIdentidadTramite();
				error.setNivelError(WARNING);
				error.setMensaje("La persona no cuenta con CURP, se recomienda actualizar sus datos");
				error.setTramiteSolucion(TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES.getCodigo());
				error.setParametros(parametros);
				errores.add(error);
			}*/
			
			if(StringUtils.isBlank(fisica.getRfc())){
				parametros.put("CONSULTA_SAT", true);
				
				error = new ErrorValidacionIdentidadTramite();
				error.setNivelError(ERROR);
				error.setMensaje("Para poder continuar el con el tr�mite es necesario que la persona cuenta con RFC");
				error.setTramiteSolucion(TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES.getCodigo());
				error.setParametros(parametros);
				errores.add(error);
				isIdentidaValida = false;
			}
		} else if (tipoTramite == TipoTramiteEnum.ALTA_SRT_PM.getCodigo().intValue()) {
			// Se valida que la persona moral tenga RFC
			Moral moral = (Moral) persona;
			
			if (StringUtils.isBlank(moral.getRfc())){
				parametros.put("CONSULTA_SAT", true);
				
				error = new ErrorValidacionIdentidadTramite();
				error.setNivelError(ERROR);
				error.setMensaje("Para poder continuar el con el tr�mite es necesario que la persona cuenta con RFC");
				error.setTramiteSolucion(TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES.getCodigo());
				error.setParametros(parametros);
				errores.add(error);
				isIdentidaValida = false;
			}
		} else if (tipoTramite == TipoTramiteEnum.COMPRA_SEGURO_INDIVIDUAL.getCodigo().intValue()) {
			Fisica fisica = (Fisica) persona;
			
			/*if (StringUtils.isBlank(fisica.getCurp())) {
				parametros.put("CONSULTA_RENAPO", true);
				
				error = new ErrorValidacionIdentidadTramite();
				error.setNivelError(WARNING);
				error.setMensaje("La persona no cuenta con CURP, se recomienda actualizar sus datos");
				error.setTramiteSolucion(TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES.getCodigo());
				error.setParametros(parametros);
				errores.add(error);
			}*/
		}

		validacionIdentidad.setValida(isIdentidaValida);
		validacionIdentidad.setErrores(errores);
				
		return validacionIdentidad;
		
	}

	@Override
	public boolean validarExistenciaFielPersona(long idPersona) {
		boolean fielActiva = false;
		try{
			this.log.info(" -- Metodo PersonaBussiness: "+idPersona);
			fielActiva = this.personaEntity.registradoConFiel(idPersona);
			this.log.info(" -- El status de la fiel encontrada es: "+fielActiva);
		}catch(Exception ex){
			this.log.info(" -- Fiel no encontrada con el idPersona: "+idPersona);
			ex.printStackTrace();
		}
		return fielActiva;
	}
	
	@Override
	public Persona registrarPersonaConEntidadesExternas(Persona persona,
			boolean crearSolicitud) throws RegistroPersonaException,
			ClienteWebserviceRenapoCurpException,
			ClienteWebserviceSatRfcException, RegistroPersonaFisicaException,
			SolicitudNoValidaException, PersonaNoEncontradaException, DomicilioNoValidoException {
	
		String msgError = null;
		
		if (persona instanceof Fisica) {
			Fisica fisica = (Fisica) persona;
			Fisica fisicaSat = null;
			
			String curp = fisica.getCurp();
			String rfc = fisica.getRfc();
			
			this.log.debug("Se recibieron CURP ["
					+ curp
					+ "] y RFC ["
					+ rfc
					+ "] para el registro de una persona fisica a partir de las entidades externas");
			
			if (StringUtils.isBlank(curp) && StringUtils.isBlank(rfc)) {
				throw new RegistroPersonaException(
						"Para registrar una persona fisica a partir de las entidades externas, es requerido CURP y/o RFC");
			}			

			if (fisica.getPersonaCalificaciones() == null) {
				fisica.setPersonaCalificaciones(new ArrayList<PersonaCalificacion>());
			} else {
				fisica.getPersonaCalificaciones().clear();
			}
			
			if (StringUtils.isNotBlank(curp)) {
				this.log.debug("Se va a consultar RENAPO con el CURP [" + curp
						+ "] para crear persona nueva");
				
				msgError = this.personaFisicaServiceValidate.validarCURP(curp);
				
				if (StringUtils.isNotBlank(msgError)) {
					throw new RegistroPersonaException("La CURP [" + curp
							+ "] no es valida debido a: " + msgError);
				}

				fisica = this.buscarPersonaFisicaPorCurpEnRenapo(curp);

				this.log.debug("La consulta a RENAPO con el CURP [" + curp
						+ "] para crear persona nueva fue exitosa");
				
				// Se agrega la calificacion RENAPO
				Calificacion calificacion = new Calificacion();
				calificacion.setIdCalificacion(CalificacionEnum.VALIDADO_RENAPO.getCodigo().longValue());
				calificacion.setDescripcion(CalificacionEnum.VALIDADO_RENAPO.getDescripcion());
				PersonaCalificacion personaCalificacion = new PersonaCalificacion();
				personaCalificacion.setCalificacion(calificacion);
				personaCalificacion.setFechaCalificacion(new Date());

				fisica.getPersonaCalificaciones().add(personaCalificacion);
			}
			
			if (StringUtils.isNotBlank(rfc)) {
				this.log.debug("Se va a consultar al SAT con el RFC [" + rfc
						+ "] para crear persona nueva");

				msgError = this.personaFisicaServiceValidate.validarRFC(rfc);
				
				if (StringUtils.isNotBlank(msgError)) {
					throw new RegistroPersonaException("El RFC [" + rfc
							+ "] no es valido debido a: " + msgError);
				}
				
				fisicaSat = this.buscarPersonaFisicaPorRfcEnSat(rfc);

				this.log.debug("La consulta al SAT con el RFC [" + rfc
						+ "] para crear persona nueva fue exitosa");
				
				// Se checa si ya se consulto RENAPO
				if (StringUtils.isBlank(curp)) {
					fisica = fisicaSat;
				} else {
					fisica.setRfc(fisicaSat.getRfc());
				}
				
				// Se agrega la calificacion SAT
				Calificacion calificacion = new Calificacion();
				calificacion.setIdCalificacion(CalificacionEnum.VALIDADO_SAT.getCodigo().longValue());
				calificacion.setDescripcion(CalificacionEnum.VALIDADO_SAT.getDescripcion());
				PersonaCalificacion personaCalificacion = new PersonaCalificacion();
				personaCalificacion.setCalificacion(calificacion);
				personaCalificacion.setFechaCalificacion(new Date());

				fisica.getPersonaCalificaciones().add(personaCalificacion);
			}
			
			this.log.debug("Se va a registrar la nueva persona fisica (CURP ["
					+ curp + "] y RFC [" + rfc
					+ "]) con los datos obtenidos de las entidades externas");
			
			fisica = this.altaPersonaFisica(fisica);
			
			/*
			 * S�lo cuando se haya ido al SAT es necesario guardar la
			 * informaci�n complementaria
			 */
			if (StringUtils.isNotBlank(rfc)) {
				fisica.setDomicilioFiscal(fisicaSat.getDomicilioFiscal());
				fisica.setMediosContactoFiscales(this.agregarEstadoMediosFiscales(fisicaSat.getMediosContactoFiscales()));
				fisica.setSituacionesSAT(fisicaSat.getSituacionesSAT());
				fisica.setDatosPersonaSAT(fisicaSat.getDatosPersonaSAT());
				
				/*
				 * Se utiliza el servicio de afectar para guardar la informaci�n
				 * complementaria del SAT
				 */
				AfectarDatosPersonaWrapper datosPersona = new AfectarDatosPersonaWrapper();
				datosPersona.setFisica(fisica);
				datosPersona.setModificarDatosSAT(true);
				datosPersona.setModificarDomicilioFiscal(true);
				datosPersona.setModificarMediosContactoFiscales(true);
				datosPersona.setModificarSituacion(true);
				datosPersona.setModificarFechaCreacion(true);
				
				this.afectarDatosPersonaBusiness.afectarDatosPersonaFisica(datosPersona);
			}
			
			persona = fisica;
			
			if(crearSolicitud){
				this.log.debug("Se va a crear solicitud de Registro de Persona para la persona fisica con CURP ["
						+ curp + "] y RFC [" + rfc + "]");
				
				Solicitud solicitud=inicializarSolicitudDeAltaPersona(persona);
				solicitudBusiness.crear(solicitud);
				
				this.log.debug("La creacion de la solicitud de Registro de Persona para la persona fisica con CURP ["
						+ curp + "] y RFC [" + rfc + "]");
			} else {
				this.log.debug("No se crea solicitud de Registro de Persona para la persona fisica con CURP ["
						+ curp + "] y RFC [" + rfc + "]");
			}
			
			this.log.debug("El registro la nueva persona fisica (CURP ["
					+ curp + "] y RFC [" + rfc
					+ "]) con los datos obtenidos de las entidades externas fue exitoso");
			
		} else if (persona instanceof Moral) {
			Moral moral = (Moral) persona;
			Moral moralSat = null;
			
			String rfc = moral.getRfc();
			
			this.log.debug("Se recibio RFC ["
					+ rfc
					+ "] para el registro de una persona moral a partir de las entidades externas");
			
			if (StringUtils.isBlank(rfc)) {
				throw new RegistroPersonaException(
						"Para registrar una persona moral a partir de las entidades externas, el RFC es requerido");
			}
			
			if (moral.getPersonaCalificaciones()== null) {
				moral.setPersonaCalificaciones(new ArrayList<PersonaCalificacion>());
			} else {
				moral.getPersonaCalificaciones().clear();
			}
			
			this.log.debug("Se va a consultar al SAT con el RFC [" + rfc
					+ "] para crear persona moral nueva");
			
			msgError = this.personaMoralServiceValidate.validarRFC(rfc);
			
			if (StringUtils.isNotBlank(msgError)) {
				throw new RegistroPersonaException("El RFC [" + rfc
						+ "] no es valido debido a: " + msgError);
			}
			
			moralSat = this.buscarPersonaMoralPorRfcEnSat(rfc);
			moral = moralSat;
			
			this.log.debug("La consulta al SAT con el RFC [" + rfc
					+ "] para crear persona moral nueva fue exitosa");
			
			// Se agrega la calificacion SAT
			Calificacion calificacion = new Calificacion();
			calificacion.setIdCalificacion(CalificacionEnum.VALIDADO_SAT.getCodigo().longValue());
			calificacion.setDescripcion(CalificacionEnum.VALIDADO_SAT.getDescripcion());
			PersonaCalificacion personaCalificacion = new PersonaCalificacion();
			personaCalificacion.setCalificacion(calificacion);
			personaCalificacion.setFechaCalificacion(new Date());

			moral.getPersonaCalificaciones().add(personaCalificacion);
			
			this.log.debug("Se va a registrar la nueva persona moral (RFC [" + rfc
					+ "]) con los datos obtenidos de las entidades externas");
			
			moral = this.personaMoralBusiness.altaPersonaMoral(moral);
			
			moral.setDomicilioFiscal(moralSat.getDomicilioFiscal());
			moral.setMediosContactoFiscales(this.agregarEstadoMediosFiscales(moralSat.getMediosContactoFiscales()));
			moral.setSituacionesSAT(moralSat.getSituacionesSAT());
			moral.setDatosPersonaSAT(moralSat.getDatosPersonaSAT());
			
			/*
			 * Se utiliza el servicio de afectar para guardar la informaci�n
			 * complementaria del SAT
			 */
			AfectarDatosPersonaWrapper datosPersona = new AfectarDatosPersonaWrapper();
			datosPersona.setMoral(moral);
			datosPersona.setModificarDatosSAT(true);
			datosPersona.setModificarDomicilioFiscal(true);
			datosPersona.setModificarMediosContactoFiscales(true);
			datosPersona.setModificarSituacion(true);
			datosPersona.setModificarFechaCreacion(true);
			
			this.afectarDatosPersonaBusiness.afectarDatosPersonaMoral(datosPersona);
			
			persona = moral;
			
			if(crearSolicitud){
				this.log.debug("Se va a crear solicitud de Registro de Persona para la persona moral con RFC "
						+ rfc);
				
				Solicitud solicitud=inicializarSolicitudDeAltaPersona(persona);
				solicitudBusiness.crear(solicitud);
				
				this.log.debug("La creacion de la solicitud de Registro de Persona para la persona moral con RFC "
						+ rfc + " fue exitosa");
			} else {
				this.log.debug("No se crea solicitud de Registro de Persona para la persona moral con RFC "
						+ rfc);
			}
			
			this.log.debug("El registro de la nueva persona moral (RFC [" + rfc
					+ "]) con los datos obtenidos de las entidades externas fue exitoso");
		} else {
			throw new RegistroPersonaException("El tipo de persona a registrar no es v�lido");
		}
					
		return persona;
	}
	
	private List<MedioContacto> agregarEstadoMediosFiscales(List<MedioContacto> mediosContactoFiscales) {
		
		for (MedioContacto medio : mediosContactoFiscales) {
			medio.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.NUEVO);
		}

		return mediosContactoFiscales;
		
	}

	@Override
	public Object actualizaFechaBajaEntidad(Object entidad) {
		// TODO Auto-generated method stub		
		return personaEntity.actualizaFechaBajaEntidad(entidad);		
	}

	public Fisica obtenerPersonaPorId(Long idPersona){
		return personaEntity.obtenerPersonaPorId(idPersona);
	}
	
	public Fisica obtenerInformacionDatosAsegurado(String nss, String curp) {
		return personaEntity.obtenerInformacionDatosAsegurado( nss, curp);
	}

	@Override
	public UsuarioBuzonRespuesta consultaRfcEnBuzonTributario(String rfc, String rp){
		UsuarioBuzonRespuesta usuarioBuzonRespuesta = BuzonTributarioWs.consultaRfc(rfc, rp);
		return usuarioBuzonRespuesta;
	}
	
	
	/**
	 * Metodo que busca a la persona que hizo el registro de portal con fiel en casp de no encontrar datos regresa nulo
	 * @param crup
	 * @return
	 * @throws Exception
	 */
	@Override
	public Fisica getFisicaBySolicitudRegistroPortalConFiel(String curp) throws Exception{
		return personaEntity.getFisicaBySolicitudRegistroPortalConFiel(curp);
	}
	
	// Se agrega cambios para correccion de calidacion de certificado INC363848
    @Override
    public Fisica buscarPFPorRfcEnSatSitCont(String rfc) throws ClienteWebserviceSatRfcException { 
		ClienteWebserviceRfc clienteWebserviceRfc = new ClienteWebserviceRfc();			
        return clienteWebserviceRfc.buscarPFPorRfcEnSatSitCont(rfc);
    }
    
    @Override
    public Moral buscarPMPorRfcEnSatSitCont(final String rfc) throws ClienteWebserviceSatRfcException {    
		ClienteWebserviceRfc clienteWebserviceRfc = new ClienteWebserviceRfc();
		Moral personaMoral = clienteWebserviceRfc.buscarPMPorRfcEnSatSitCont(rfc);
		
		System.out.println("PARA VER SI DESDE EL SAT VIENE EL TIPO SOCIEDAD " + personaMoral.getTipoSociedad().getIdTipoSociedad() + personaMoral.getTipoSociedad().getDescripcionAbreviada() );
		System.out.println("PARA VER SI DESDE EL SAT VIENE EL TIPO SOCIEDAD " + personaMoral.getTipoSociedad().getDescripcion());
		
		if (personaMoral != null){
			//OBTENEMOS EL ID-TIPO-SOCIEDAD EN BASE A LA DESCRIPCION RECUPERADA EN EL SERVICIO DEL SAT
	        TipoSociedad tipoSociedad = personaMoralEntity.getTipoSociedadByDescripcion(personaMoral.getTipoSociedad().getDescripcionAbreviada());

	        System.out.println("Tipo de sociedad\n\n " + tipoSociedad);
	        if (tipoSociedad != null) {
	        	personaMoral.setTipoSociedad(tipoSociedad);
	        }			
		}
        return personaMoral;
    }    
    
}

