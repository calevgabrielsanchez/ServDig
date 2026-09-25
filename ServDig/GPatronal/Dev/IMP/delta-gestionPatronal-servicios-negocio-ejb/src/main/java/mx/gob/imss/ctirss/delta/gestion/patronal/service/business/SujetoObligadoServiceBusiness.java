package mx.gob.imss.ctirss.delta.gestion.patronal.service.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.gestion.patronal.RelacionConRegistroPatronalExisteException;
import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RegistroPersonaFisicaException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.RegistrarMedioContactoException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaFisicaEnSATServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaMoralEnSATServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.business.rep.legal.RepresentanteLegalServiceBusinessLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.SujetoObligadoServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.afiliacion.RegistroPatronalServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.bitacora.BitacoraServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.centro.trabajo.CentroTrabajoServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.clasificacion.actividad.economica.ClasificacionActividadEconomicaServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.contacto.FormaContactoServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.escritura.constitutiva.EscrituraConstitutivaServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.registro.sindicato.RegistroSindicatoServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.AcuseTramitesVentanillaBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.MovimientoPatronalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.ModuloEnum;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.MunicipioIMSS;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Bien;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EquipoTransporte;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EscrituraConstitutiva;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MateriaPrima;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Personal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Proceso;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Producto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RegistroSindicato;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCambioInformacionPersona;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteMoral;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramitePersonaAutorizada;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sindo.MovimientoPatronalType;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sindo.util.MovimientoPatronalTypeBuilder;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.AfectarDatosPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;





import gob.imss.webservice.sat.rfc.implementacion.ClienteWebserviceRfc;

/**
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hugo Armando Mart?nez Cham?nica
 *  @Proyecto: delta
 *  @Archivo: SujetoObligadoServiceBusiness.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.patronal.service.business
 *  @Fecha: 17:01:50
 */
@Stateless(name="sujetoObligadoServiceBusiness" ,mappedName="sujetoObligadoServiceBusiness")
public class SujetoObligadoServiceBusiness extends AbstractServiceBusiness implements SujetoObligadoServiceBusinessRemote, SujetoObligadoServiceBusinessLocal {

    private static final Logger log = LoggerFactory.getLogger(SujetoObligadoServiceBusiness.class); 
	
	@EJB
	private SujetoObligadoServiceEntityLocal entity;
	
	@EJB
	private EscrituraConstitutivaServiceEntityLocal escrituraConstitutivaServiceEntityLocal;
	
	@EJB
	private RepresentanteLegalServiceBusinessLocal representanteLegalServiceBusiness;
	
	@EJB
	private SujetoObligadoUtilityLocal sujetoObligadoUtility;
	
	@EJB
	private RegistroSindicatoServiceEntityLocal registroSindicatoServiceEntityLocal;
	
	@EJB
	private CentroTrabajoServiceEntityLocal centroTrabajoServiceEntityLocal;
	
	@EJB
	private FormaContactoServiceEntityLocal formaContactoServiceEntity;
	
	@EJB
	private DomicilioServiceBusinessRemote domicilioService; 
	
	@EJB
	private MediosContactoServiceBusinessRemote mediosContactoService;
	
	@EJB
	AfectarDatosPersonaBusinessRemote personaService;
	
	@EJB
	PersonaBusinessRemote personaServiceRemote;
	
    @EJB
    private MovimientoPatronalBusinessRemote movimientoPatronalBusinessRemote;

    @EJB
    private RegistroPatronalServiceEntityLocal registroPatronalServiceEntity;

    @EJB
    private ClasificacionActividadEconomicaServiceEntityLocal clasificacionActividadEconomicaEntity;
    
    @EJB 
    private AfiliacionServiceBusinessRemote afiliacionService;
    
    @EJB
    private SolicitudBusinessRemote solicitudBusinessRemote;

    @EJB
    private FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;
            
    @EJB
    private AcuseTramitesVentanillaBusinessRemote acuseTramitesVentanillaBusinessRemote;
    
    @EJB
    private LocalizarPersonaMoralEnSATServiceBusinessRemote localizarPersonaMoralEnSATServiceBusiness;
    
    @EJB
    private LocalizarPersonaFisicaEnSATServiceBusinessRemote localizarPersonaFisicaEnSATServiceBusiness;
    
    @EJB
    private BitacoraServiceEntityLocal bitacoraEntity;
    
    
    
    
	/**
	 * @author Hugo Armando Mart?nez Cham?nica
	 * Actualiza el nombre comercial de la persona fisica o Moral de acuerdo con el tipoPersonaFiscal.
	 * Si el tipoPersonaFiscal = FISCAL sse actualizan los datos en la tabla DitPersonaFiscal
	 * Si el tipoPersonaFiscal = MORAL se actualizan los datos en la tabla DitPersonaMoral
	 * 
	 *@param sujetoObligado Contiene el identificador del sujeto obligado y la denominacion y raz?n social.
	 */
	@Override
	public void actualizarDenominacionRazonSocial(SujetoObligado sujetoObligado, Usuario usuario) throws GestionPatronalBusinessException{
		log.debug("Actualizar Denominacion razon social");
		
		@SuppressWarnings("unused")
		Long idPersona = null;
		if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
            idPersona = actualizarDenominacionRazonSocialPersonaFisica(sujetoObligado, usuario);
            log.debug("Actualizada Persona Fisica id: {}", idPersona);
		}
		if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
			idPersona = actualizarDenominacionRazonSocialPersonaMoral(sujetoObligado, usuario);
            log.debug("Actualizada Persona Moral id: {}", idPersona);
		}
		
	}
	
	public Boolean esRfcPermitido (String rfc) {
		Boolean permiteRfc = bitacoraEntity.permitedRfc(rfc);
		return permiteRfc;
	}
	
    private Long actualizarDenominacionRazonSocialPersonaFisica(SujetoObligado sujetoObligado, Usuario usuario) throws GestionPatronalBusinessException {
        Long idPersona = sujetoObligado.getFisica().getIdPersona();
        log.info("Actualizando Persona Fisica id: {}", idPersona);
        entity.actualizarDatosGeneralesFisca(sujetoObligado.getFisica());
        return idPersona;
    }

    private Long actualizarDenominacionRazonSocialPersonaMoral(SujetoObligado sujetoObligado, Usuario usuario) throws GestionPatronalBusinessException {
        Long idPersona = sujetoObligado.getMoral().getIdPersona();
        log.info("Actualizando Persona Moral id: {}", idPersona);
        entity.actualizarDatosGeneralesMoral(sujetoObligado.getMoral());
        return idPersona;
    }

    private String buildRazonSocial(SujetoObligado sujetoObligado) {
        Fisica fisica = sujetoObligado.getFisica();
        Moral moral = sujetoObligado.getMoral();
        if (fisica != null) {
        	return (sujetoObligado.getRazonSocialSINDO() != null && !sujetoObligado.getRazonSocialSINDO().isEmpty())?sujetoObligado.getRazonSocialSINDO().trim():buildRazonSocial(fisica);
        }
        else if (moral != null) {
            return (sujetoObligado.getRazonSocialSINDO() != null && !sujetoObligado.getRazonSocialSINDO().isEmpty())?sujetoObligado.getRazonSocialSINDO().trim():buildRazonSocial(moral);
        }
        return null;
    }

    private String buildRazonSocial(Fisica fisica) {
        return String.format("%s %s %s"
                , fisica.getNombre()
                , fisica.getPrimerApellido()
                , safeNull(fisica.getSegundoApellido())).trim();
    }
    
    private String buildRazonSocial(Moral moral) {
    	StringBuffer razonSocial = new StringBuffer();
    	razonSocial.append(moral.getRazonSocial());
    	log.info("Viendo que si trae tipo de sociedad una vez que ya consulto por el SAT y del IMSS: "+ moral.getTipoSociedad());
    	if(moral.getTipoSociedad()!=null)
    		razonSocial.append(" "+safeNull(moral.getTipoSociedad().getDescripcionAbreviada()));
    	String razon = razonSocial.toString().trim();
    	log.info("Revisando que trae el objeto de razonsocial: "+ razonSocial.toString());
    	
        return razon;
    }

    private MovimientoPatronalType buildMovimientoPatronalType05(SujetoObligado sujetoObligado, Subdelegacion subdelegacion, String razonSocialNueva) {

        Delegacion delegacion = subdelegacion.getDelegacion();
        String razonSocial = buildRazonSocial(sujetoObligado);

        return new MovimientoPatronalTypeBuilder()
                .withDelegacionOrigen(Integer.parseInt(delegacion.getClave()))
                .withSubdelegacionOrigen(Integer.parseInt(subdelegacion.getClave()))
                .withCiz(delegacion.getCiz())
                .withClaveAplicacion(1)
                .withNumeroFolio(buildNumeroFolio(subdelegacion))
                .withTipoMovimiento(5)
                .withRegistroPatronal(buildRegistroPatronal(sujetoObligado))
                .withDigitoVerificador(Integer.parseInt(sujetoObligado.getDigVerificador()))
                .withNombrePatron(razonSocial.replaceAll("[\u00F1|\u00D1]", "#"))  //Cambia todas las enie por #
                .withNombrePatronalC(razonSocialNueva.replaceAll("[\u00F1|\u00D1]", "#"))     //Cambia todas las enie por #
                .withCurp(seekCurp(sujetoObligado.getFisica()))
                .withRfc(seekRFC(sujetoObligado))
                .withFechaMovimiento(new Date())
                .withFechaRecepcion(new Date()) //TODO Cambiar por fecha presentacion
                .withOrigenMovimiento(06)
                .build();
    }

    private String safeNull(String nullablestring) {
        if (nullablestring == null) {
            return "";
        }
        return nullablestring;
    }

    private String safeNull(Number nullable) {
        if (nullable == null) {
            return "";
        }
        return "" + nullable;
    }

    private MovimientoPatronalType buildMovimientoPatronalType04(SujetoObligado sujetoObligado
            , Subdelegacion subdelegacionDestino
            , Domicilio domicilio, Subdelegacion subdelegacionOrigen) {
        log.info("Construyendo movimiento patronal type");
        Delegacion delegacionDestino = subdelegacionDestino.getDelegacion();
        String razonSocial = buildRazonSocial(sujetoObligado);

        Clasificacion clasificacion = clasificacionActividadEconomicaEntity.consultarPorPatronSujetoObligado(sujetoObligado.getCveIdSujetoObligado());
        
        String calle = domicilio.getCalle()!=null ? domicilio.getCalle(): domicilio.getVialidadPrimaria().getNombre() ; 
        
        StringBuffer domicilioStr = new StringBuffer(calle)//TODO revisar si el origen del nombre de calle no es domicilio.getVialidadPrimaria().getNombre()
                .append(" ").append(domicilio.getNumExterior1());
                if(domicilio.getNumExteriorAlf()!=null)
                	domicilioStr.append(" ").append(safeNull(domicilio.getNumExteriorAlf()));
                if(domicilio.getNumInterior()!=null)
                	domicilioStr.append(" ").append(safeNull(domicilio.getNumInterior()));
                if(domicilio.getNumInteriorAlf()!=null)
                	domicilioStr.append(" ").append(safeNull(domicilio.getNumInteriorAlf()));
                if(domicilio.getAsentamiento()!=null && domicilio.getAsentamiento().getNombre()!=null)
                	domicilioStr.append(" ").append(safeNull(domicilio.getAsentamiento().getNombre()));
                
                domicilioStr.toString().replaceAll("[\u00F1\u00D1]", "#");
        
//        String claveMunicipio = sujetoObligado.getNumeroRegistroPatronal().
//            replaceFirst("(.{3}).+", "$1");

        Asentamiento asentamiento = domicilio.getAsentamiento();
        Localidad localidad = asentamiento.getLocalidad();
        CodigoPostal codigoPostal = domicilio.getCodigoPostal();
        Fraccion fraccion = clasificacion.getFraccion();
        
        String claveMunicipio = sujetoObligado.getMunicipioIMSS().getCvecMunicipioSINDO();
        
        String localidadSindo = new StringBuffer().append(localidad.getMunicipio().getNombre()).
        		append(" ").append(localidad.getMunicipio().getEntidadFederativa().getNombre()).toString().toUpperCase();
        StringBuffer subdelDestino = new StringBuffer();
        if(subdelegacionDestino!=null){
        	String delegacion = String.format("%02d", Integer.parseInt(subdelegacionDestino.getDelegacion().getClave()));
        	String subdelegacion = String.format("%02d", Integer.parseInt(subdelegacionDestino.getClave()));
        	subdelDestino.append(delegacion).append(subdelegacion);        	
        }
        String subdelegacionDestinoText = subdelDestino.toString();
        return new MovimientoPatronalTypeBuilder()                                                    // <MovimientoCambioDomicilio>
                .withDelegacionOrigen(Integer.parseInt(subdelegacionOrigen.getDelegacion().getClave().trim()))          //   <delegacionOrigen>39</delegacionOrigen>
                .withSubdelegacionOrigen(Integer.parseInt(subdelegacionOrigen.getClave().trim()))    //  En realidad aqui se env?subdel destino no origen <subdelegacionOrigen>16</subdelegacionOrigen>
                .withCiz(delegacionDestino.getCiz())
                .withClaveAplicacion(1)                                                               //   <claveAplicacion>1</claveAplicacion>
                .withNumeroFolio(buildNumeroFolio(subdelegacionOrigen))                              //   En realidad aqui se env?subdel destino no origen <numeroFolio>16156</numeroFolio>
                .withTipoMovimiento(4)                                                                //   <tipoMovimiento>4</tipoMovimiento>
                .withOrigenMovimiento(6) //Verificar                                                  //   <origenMovimiento>1</origenMovimiento>
                .withNombrePatron(razonSocial.replaceAll("[\u00F1|\u00D1]", "#"))                     //   <nombrePatron>ALTERNATIVA EN ADMINISTRACION DE PERSONAL</nombrePatron>
                .withRegistroPatronal(buildRegistroPatronal(sujetoObligado))                          //   <registroPatronal>Y544765110</registroPatronal>
                .withDigitoVerificador(Integer.parseInt(sujetoObligado.getDigVerificador()))          //   <digitoVerificador>1</digitoVerificador>
                .withDomicilioPatron(domicilioStr.toString().toUpperCase())                                                    //   <domicilioPatron>Las Armas</domicilioPatron>
                .withClaveMunicipio(claveMunicipio)                                                   //   <claveMunicipio>Y54</claveMunicipio>
                .withCodigoPostal(codigoPostal.getCodigoPostal())                                     //   <codigoPostal>11500</codigoPostal>
                .withLocalidad(localidadSindo)                                                 		  
                //   <localidadPatron>MIGUEL HIDALGO</localidadPatron>
                .withGrupo(Integer.parseInt(fraccion.getGrupo().getNumGrupo().trim()))                       //  <--- Forma parte de fraccion
                .withDivision(Integer.parseInt(fraccion.getGrupo().getDivision().getNumDivision().trim()))   //  <--- Forma parte de fraccion
                .withFraccion(Integer.parseInt(subdelegacionDestinoText.trim()))         			  //  Delegaci? subdelegaci?rigen <fraccion>6401</fraccion> se resuelve en OSB
                .withFechaMovimiento(new Date())
                .withFechaRecepcion(new Date()) 													  //TODO cambiar por fecha presentacion
                .build();                                                                             // </MovimientoCambioDomicilio>
    }

    private String seekCurp(Fisica fisica) {
        String curp = "";
        if (fisica != null) {
            curp = fisica.getCurp();
        }
        return curp;
    }

    private String seekRFC(SujetoObligado sujetoObligado) {
        Fisica fisica = sujetoObligado.getFisica();
        Moral moral = sujetoObligado.getMoral();
        String rfc = "";
        if (fisica != null) {
            rfc = fisica.getRfc();
        }
        else if (moral != null) {
            rfc = moral.getRfc();
        }
        return rfc;
    }

    private String buildRegistroPatronal(SujetoObligado sujetoObligado) {
        return new StringBuilder()
            .append(sujetoObligado.getNumeroRegistroPatronal())
            .append(sujetoObligado.getModalidad().getNumModalidad()).toString();
    }

    private String buildNumeroFolio(Subdelegacion subdelegacion) {
        //folio era subdelegacion + dia juliano
        String prev_folio = new StringBuilder()
                .append(subdelegacion.getClave())
                .append(new Formatter().format("%03d"
                        , Calendar.getInstance().get(Calendar.DAY_OF_YEAR)))
                .toString();
        log.info(String.format("folio con dia juliano: %s", prev_folio));

        //folio ahora es subdelegacion + 411
        String folio = new  StringBuilder()
            .append(subdelegacion.getClave())
            .append("411")
            .toString();
        log.info(String.format("folio con constante 411: %s", folio));
        return folio;

    }

	/**
	 * @author Hugo Armando Mart?nez Cham?nica
	 * Obtiene los detalles del sujeto obligado en base al identificador proporcionado
	 * @param sujetoObligado Contiene el identificador y tipoPersonaFiscal.
	 */
	@Override
	public List<SujetoObligado> obtenerDetalleSujetoObligado(SujetoObligado sujetoObligado) throws GestionPatronalBusinessException{
	 	log.error("consultando detalle de sujeto obligado");
	 	
	 	List<SujetoObligado> sujetosObligados=new ArrayList<SujetoObligado>();
		if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
			if(sujetoObligado.getFiltroPorIdPersona()){
				sujetosObligados=entity.consultarDetalleSujetoObligadoIDFisica(sujetoObligado);
			}else{
				sujetosObligados=entity.consultarDetalleSujetoObligadoRFCFisica(sujetoObligado);
			}
		}else if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
			if(sujetoObligado.getFiltroPorIdPersona()){
				sujetosObligados=entity.consultarDetalleSujetoObligadoIDMoral(sujetoObligado);	
			}else{
				sujetosObligados=entity.consultarDetalleSujetoObligadoRFCMoral(sujetoObligado);
			}			
		}
				
		List<SujetoObligado> sujetosObligadosComplementarios=new ArrayList<SujetoObligado>();

		if(sujetosObligados==null){
			log.error("No se encuentra rp asociados a la persona");
			//throw new GestionPatronalBusinessException("error.rfc.inexistente");
		}else{
			for(SujetoObligado sujetoObligadoActual : sujetosObligados){
				sujetosObligadosComplementarios.add(obtenerDetalleRP(sujetoObligadoActual));
			}
		}
				
		return sujetosObligadosComplementarios;
	}

	
	@Override
	public SujetoObligado obtenerDetallePrimerSujetoObligado(Persona persona)
			throws GestionPatronalBusinessException {
		
		SujetoObligado encontrado = null;
		
		if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersonaEnum.FISICA.getId())) {
			encontrado = entity.consultarPrimerSujetoObligadoByRfc(persona.getRfc(),TipoPersonaEnum.FISICA);
		} else {
			encontrado = entity.consultarPrimerSujetoObligadoByRfc(persona.getRfc(), TipoPersonaEnum.MORAL);
		}
		
		if(encontrado == null) {
			throw new GestionPatronalBusinessException("El RFC no se encuentra registrado como patron");
		}
		
		return encontrado;
	}

	@Override
	public SujetoObligado obtenerDatosGeneralesPatron(
			SujetoObligado sujetoObligado) {
		System.out.println("*******************SUJETO OBLIGADO ANTES DE DATOS GENERALES: "+"\n"+sujetoObligado);
		sujetoObligado = entity.obtenerDatosGeneralesPatron(sujetoObligado);
		System.out.println("*****************************SUJETO OBLIGADO DESPUES DE DATOS GENERALES: "+"\n"+sujetoObligado);
		agregarDatosContacto(sujetoObligado);
		
		return sujetoObligado;
	}

    @Override
    public SujetoObligado completarDatosGeneralesPatron(SujetoObligado sujetoObligado) {
        sujetoObligado= entity.completarDatosGeneralesPatron(sujetoObligado);
        return sujetoObligado;
    }
    
    

	@Override
	public Long getCvePatronSujetoObligadoPorCveIdPatronGeneral(Long cveIdPatronGeneral) {
		return entity.getCveIdSujetoObligadoPorCvePatronGeneral(cveIdPatronGeneral);
	}

	
	
	@Override
	public Long getCvePatronSujetoObligadoPorRP(String nrp) {
		
		return entity.getCveIdSujetoObligadoPorRP(nrp);
	}

	@Override
	public Modalidad getModalidad(String numModalidad) {
		
		return entity.getModalidadPorNumModalidad(numModalidad);
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote#obtenerDetalleRP(mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado)
	 */
	@Override
	public SujetoObligado obtenerDetalleRP(SujetoObligado sujetoObligado) {
		sujetoObligado=entity.obtenerDetallesRegistroPatronal(sujetoObligado);
		Persona persona = sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA) ? 
				sujetoObligado.getFisica() : sujetoObligado.getMoral();
		Domicilio domicilioEncontrado=obtenerDomicilioFiscal(persona);
		DomicilioFiscal domicilioFiscal = null;
		if(domicilioEncontrado!=null){
			domicilioFiscal = sujetoObligadoUtility.convertDomicilioToDomicilioFiscal(domicilioEncontrado);
			System.err.println("Domicilio Fiscal- : " + domicilioFiscal);
		}
		sujetoObligado.setDomicilioFiscal(domicilioFiscal);
		
		if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)
				&& sujetoObligado.getFisica()!=null)
			sujetoObligado.getFisica().setDomicilioFiscal(domicilioFiscal);
		else if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)
				&& sujetoObligado.getMoral()!=null)
			sujetoObligado.getMoral().setDomicilioFiscal(domicilioFiscal);
		
		Long idDomicilioCT=entity.consultarClaveDomicilioCentroTrabajo(sujetoObligado.getCveIdSujetoObligado());
		
		if(idDomicilioCT!= null){
			System.err.println("SE ENCONTRO EL DOMICILIO: "+idDomicilioCT);
			CentroTrabajo cntroTrabajo = new CentroTrabajo();
			cntroTrabajo.setClave(idDomicilioCT.intValue());
			
			try {
				Domicilio cTrabajo = domicilioService.consultarDomicilio(cntroTrabajo);
				System.err.println("DOMICILIO COMPLETO: "+cTrabajo.getClave()+" Colonia: "+cTrabajo.getColonia());
				CentroTrabajo centro = sujetoObligadoUtility.convertirDomicilioACentroTrabajo(cTrabajo);
				System.err.println("CENTRO DE TRABAJO COMPLETO: "+cTrabajo.getClave()+" Colonia: "+cTrabajo.getColonia());
				centro.setMediosContacto(obtenerMediosContactoDeRegistroPatronal(sujetoObligado.getCveIdSujetoObligado()));
				sujetoObligado.setCntroTrabajo(centro);
			} catch (DomicilioNoLocalizadoException e) {
				e.printStackTrace();
			}
			
			
		}else{
			System.out.println("NO SE ENCONTRO NINGUN CENTRO DE TRABAJO DENTRO DE LA NORMA TECNICA ASOCIADO AL PATRON: "+sujetoObligado.getCveIdSujetoObligado());
			String domicilio=entity.obtenerDomicilioMigrado(sujetoObligado.getCveIdSujetoObligado());
			CentroTrabajo cTrabajo = new CentroTrabajo();
			cTrabajo.setDescripcion(domicilio);
			cTrabajo.setMediosContacto(obtenerMediosContactoDeRegistroPatronal(sujetoObligado.getCveIdSujetoObligado()));
			sujetoObligado.setCntroTrabajo(cTrabajo);
			
		}
		
		Subdelegacion subdelegacionRP = entity.consultarSubdelegacion(sujetoObligado.getCveIdSujetoObligado());
		MunicipioIMSS municipio = centroTrabajoServiceEntityLocal.consultarMunicipioIMSSPorRegistroPatronal(sujetoObligado.getCveIdSujetoObligado());
		
		sujetoObligado.setSubdelegacion(subdelegacionRP);
		sujetoObligado.setMunicipioIMSS(municipio);
		return sujetoObligado;
	}
	
	
	/**
	 * Metodo encargado de recuperar el domicilio del centro de trabajo del patron, si el objeto trae la marca de domiclio Migrado
	 * primero buscara el domicilio de SINDO, en caso de que no venga el valor buscara el domicilio con la norma tecnica
	 * en caso de que no se encuentre buscara nuevamente en el domiclio migrado esto ya que no todos los metodos de consulta setean dicho valor
	 * @param sujetoObligado
	 * @return
	 */
	@Override
	public  CentroTrabajo obtenerDomicilioCentroTrabajoNormaTecnicaOMigradoSindo(SujetoObligado sujetoObligado)
				throws IllegalArgumentException{
		
		CentroTrabajo cTrabajo = null;
		
		if(sujetoObligado == null || sujetoObligado.getCveIdSujetoObligado() == null ){
			throw new IllegalArgumentException("EL patron / su id no pueden ser nulos");
		}
		if(sujetoObligado.getIndMigrDom() != null){
			if(sujetoObligado.getIndMigrDom().intValue() ==1){
			cTrabajo = new CentroTrabajo();
			String domicilio=entity.obtenerDomicilioMigrado(sujetoObligado.getCveIdSujetoObligado());
			 cTrabajo.setDescripcion(domicilio);
			}else{
				cTrabajo =	this.consultarDomicilioCentroTrabajo(sujetoObligado.getCveIdSujetoObligado());
			}
		}else{ // se hace el if asi para los escenarios de las consultas que no setean el indicador de domicilio
			cTrabajo =	this.consultarDomicilioCentroTrabajo(sujetoObligado.getCveIdSujetoObligado());
			if(cTrabajo == null){
				cTrabajo = new CentroTrabajo();
				String domicilio=entity.obtenerDomicilioMigrado(sujetoObligado.getCveIdSujetoObligado());
				 cTrabajo.setDescripcion(domicilio);
			}
		}

		return cTrabajo;
		
	}
	
	private List<MedioContacto> obtenerMediosContactoDeRegistroPatronal(Long cveIdPatron){
		return centroTrabajoServiceEntityLocal.consultarMediosContactoPorIdPatron(cveIdPatron);
	}
	
	@Override
	public Domicilio obtenerDomicilioFiscal(Persona persona){
		Domicilio domicilioFiscal = obtenerDomicilioFiscalGeograficoPersona(persona);
		if(domicilioFiscal !=null){
			return domicilioFiscal;
		}else{
			try {
				return domicilioService.consultarDomicilioFiscalPersona(persona);
			} catch (DomicilioNoLocalizadoException e) {
				return null;
			}
			
		}
	}
	
	private Domicilio obtenerDomicilioFiscalGeograficoPersona(Persona persona){
		Long idPersona=persona.getIdPersona();
		TipoPersonaFiscal tipoPersona = null;
		if(persona instanceof Fisica){
			tipoPersona = TipoPersonaFiscal.FISICA;
		}else if(persona instanceof Moral) {
			tipoPersona = TipoPersonaFiscal.MORAL;
		}
		
		Long idDomicilio=entity.consultarClaveDomicilioFiscal(idPersona, tipoPersona);
		System.err.println("Domicilio Fiscal encontrado: "+idDomicilio);
		if(idDomicilio!=null){
			Domicilio domicilioFiscal = new Domicilio();
			Integer claveDom = idDomicilio != null ? idDomicilio.intValue() : null;
			domicilioFiscal.setClave(claveDom);
			
			Domicilio domicilio = null;
			try {
				domicilio = domicilioService.consultarDomicilio(domicilioFiscal);
				
			} catch (DomicilioNoLocalizadoException e) {
				e.printStackTrace();
			}
			return domicilio;
		}else{
			System.err.println("No existe domicilio fiscal geogr?fico");
			return null;
		}
	}

    private void setRPCompleto(SujetoObligado sujetoObligado) {
		if(sujetoObligado.getNumeroRegistroPatronal().length()==8){
			String numeroRPCompleto = sujetoObligado.getNumeroRegistroPatronal();
			if(sujetoObligado.getModalidad()!=null && sujetoObligado.getModalidad().getNumModalidad()!=null){
				numeroRPCompleto += sujetoObligado.getModalidad().getNumModalidad();
				if(sujetoObligado.getDigVerificador()!=null){
					numeroRPCompleto += sujetoObligado.getDigVerificador();
				}
			}
			System.out.println("Se completa el RP: "+numeroRPCompleto);
			sujetoObligado.setNumeroRegistroPatronal(numeroRPCompleto);
		}
    }
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote#obtenerDetalleSujetoObligadoSolicitud(mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado)
	 */
	@Override
	public SujetoObligado obtenerDetalleSujetoObligadoActividadEconomica(
			SujetoObligado sujetoObligado) {
		System.out.println("PERSONA FISCAL: "+sujetoObligado.getTipoPersonaFiscal());
		log.debug("PERSONA FISCAL: "+sujetoObligado.getTipoPersonaFiscal());
		log.debug("REGISTRO PATRONAL A OBTENER CLASIFICACION: "+sujetoObligado.getNumeroRegistroPatronal());
		System.out.println("REGISTRO PATRONAL A OBTENER CLASIFICACION: "+sujetoObligado.getNumeroRegistroPatronal());
		System.err.println("modalidad: "+sujetoObligado.getModalidad());
		System.err.println("digito ver: "+sujetoObligado.getDigVerificador());
		setRPCompleto(sujetoObligado);
		
		sujetoObligado = entity.consultarPorRegistroPatronal(
				sujetoObligado.getNumeroRegistroPatronal(), sujetoObligado.getTipoPersonaFiscal());
		if(sujetoObligado==null)
			return null;
        log.debug("datos generales patron");
		sujetoObligado=obtenerDatosGeneralesPatron(sujetoObligado);

        log.debug("relaciones sujeto obligado");
	    settersDetalleSujetoObligado(sujetoObligado);
        log.debug("obtenerDetalleSujetoObligadoActividadEconomica regresando sujeto Obligado con RP: {}", sujetoObligado.getNumeroRegistroPatronal());
		
		return sujetoObligado;
	}

    public SujetoObligado obtenerSujetoObligadoActividadEconomica(SujetoObligado sujetoObligado) {
        setRPCompleto(sujetoObligado);
        sujetoObligado = entity.consultarPorRegistroPatronalBasic(sujetoObligado.getNumeroRegistroPatronal(),
                sujetoObligado.getTipoPersonaFiscal());
        if(sujetoObligado==null) {
            return null;
        }
        log.debug("datos generales patron");
        sujetoObligado=completarDatosGeneralesPatron(sujetoObligado);

        return sujetoObligado;
    }

    private void settersDetalleSujetoObligado(SujetoObligado sujetoObligado) {
		// obtenemos representantes legales
		List<RepresentanteLegal> representanteLegales=representanteLegalServiceBusiness.obtenerRepresentanteLegalPorSujetoObligado(sujetoObligado.getCveIdSujetoObligado());
	
		if(representanteLegales == null){
			super.log.debug("No hay ning?n representante legal asociado");
		}else{
			System.out.println("********************************************************************REPRESENTANTES LEGALES**************************************************************************************");
			for(RepresentanteLegal rl: representanteLegales){
				System.out.println(rl.toString());
			}
			System.out.println("********************************************************************FIN DE REPRESENTANTES LEGALES**************************************************************************************");
		}
		sujetoObligado.setRepresentantesLegales(representanteLegales);
		
		sujetoObligado=obtenerDetalleRP(sujetoObligado);
		List<Producto> productos = obtenerListaProductoServicios(sujetoObligado.getCveIdSujetoObligado());
		sujetoObligado.setProductos(productos);
		sujetoObligado.setMateriaPrimaMateriales(obtenerMateriaPrimaMateriales(sujetoObligado.getCveIdSujetoObligado()));
		sujetoObligado.setEquipos(obtenerMaquinariaEquipo(sujetoObligado.getCveIdSujetoObligado()));
		sujetoObligado.setEquiposTransporte(obtenerEquipoTranporte(sujetoObligado.getCveIdSujetoObligado()));
		sujetoObligado.setPersonal(obtenerPersonal(sujetoObligado.getCveIdSujetoObligado()));
		sujetoObligado.setBienes(obtenerBienes(sujetoObligado.getCveIdSujetoObligado()));
		
		Subdelegacion subdelegacion = obtenerSubdelegacion(sujetoObligado.getCveIdSujetoObligado());
		if(subdelegacion!= null){
			System.out.println("\n\n\n\n**************** SUBDELEGACION: "+ subdelegacion.getDescripcion());
			System.out.println("\n\n\n\n**************** DELEGACION: "+ subdelegacion.getDelegacion().getDescripcion());
			sujetoObligado.setSubdelegacion(subdelegacion);
		}
		
		System.out.println("\n\n\n\n\n\n*********************OBTENIENDO PROCESOS");

		List<Proceso> procesos = obtenerProcesos(sujetoObligado.getCveIdSujetoObligado());
		
		Proceso proceso = null;
		if((procesos!=null) && (procesos.size()>0)){
			System.out.println("\n\n\n\n\n\n*********************SI ENCONTRE PROCESOS");
			System.out.println("\n\n\n\n\n\n*********************PROCESOS SIZE: "+procesos.size());
			
			proceso = procesos.get(0);
			System.out.println("PROESO: INICIAL"+proceso.getDesInicial()+"\n INTERMEDIO"+proceso.getDesIntermedio()+"\n FINAL"+proceso.getDesFinal()+"\n");
			
		}else{
			System.out.println("\n\n\n\n\n\n*********************NO ENCONTRE PROCESOS");
		}
		sujetoObligado.setProceso(proceso);
    }

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote#
	 */
	@Override
	public List<Producto> obtenerListaProductoServicios(
			Long idPatronSujetoObligado) {
		return entity.consultarListaProductos(idPatronSujetoObligado);
	}

	@Override
	public EscrituraConstitutiva actualizarEscrituraConstitutiva(
			EscrituraConstitutiva escrituraConstitutiva)
			throws GestionPatronalBusinessException {
		
		
		escrituraConstitutiva = escrituraConstitutivaServiceEntityLocal.actualizarEscrituraConstitutiva(escrituraConstitutiva);
		
//		SujetoObligado sujetoObligado = new SujetoObligado();
//		sujetoObligado.setCveIdSujetoObligado(escrituraConstitutiva.getCveIdPatronSujetoObligado());
//		sujetoObligado.setEscrituraConstitutiva(escrituraConstitutiva);
//		solicitudServiceBusiness.generarSolicitud(TipoSolicitudEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA, EstadoSolicitudEnum.REGISTRADA, usuario, TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA, EstadoTramiteEnum.EN_ESPERA_TRAMITADOR, sujetoObligado);
		return escrituraConstitutiva;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote#obtenerMateriaPrimaMateriales(java.lang.Long)
	 */
	@Override
	public List<MateriaPrima> obtenerMateriaPrimaMateriales(
			Long idPatronSujetoObligado) {
		return entity.consultarMateriaPrimaMateriales(idPatronSujetoObligado);
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote#obtenerMaquinariaEquipo(java.lang.Long)
	 */
	@Override
	public List<MaquinariaEquipo> obtenerMaquinariaEquipo(
			Long idPatronSujetoObligado) {
		return entity.consultarMaquinariaEquipo(idPatronSujetoObligado);
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote#obtenerEquipoTranporte(java.lang.Long)
	 */
	@Override
	public List<EquipoTransporte> obtenerEquipoTranporte(
			Long idPatronSujetoObligado) {
		return entity.consultarEquipoTranporte(idPatronSujetoObligado);
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote#obtenerProcesos(java.lang.Long)
	 */
	@Override
	public List<Proceso> obtenerProcesos(Long idPatronSujetoObligado) {
		return entity.consultarProcesos(idPatronSujetoObligado);
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote#obtenerPersonal(java.lang.Long)
	 */
	@Override	
	public List<Personal> obtenerPersonal(Long idPatronSujetoObligado) {
		return entity.consultarPersonal(idPatronSujetoObligado);
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote#obtenerDetalleRegistroPatronalPorClaveTipoPersona(java.lang.Long, mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal)
	 */
	@Override
	public SujetoObligado obtenerDetalleRegistroPatronalPorClaveTipoPersona(
			Long cveIdSujetoObligado, TipoPersonaFiscal tipoPersona) {
		SujetoObligado sujetoObligado = new SujetoObligado();
		sujetoObligado.setCveIdSujetoObligado(cveIdSujetoObligado);
		sujetoObligado.setTipoPersonaFiscal(tipoPersona);
		sujetoObligado=entity.consultarDetalleSujetoObligado(sujetoObligado);
		sujetoObligado=obtenerDetalleRP(sujetoObligado);
		return sujetoObligado;
	}

	@Override
	public RegistroSindicato actualizarRegistroSindicato(
			RegistroSindicato registroSindicato)
			throws GestionPatronalBusinessException {
		
		registroSindicato = registroSindicatoServiceEntityLocal.actualizarRegistroSindicato(registroSindicato);
		return registroSindicato;
	}
	
	@Override
	public boolean validarSubDelOrigenSubDelDestino(long idSubdelegacionOrigen, long idSubdelegacionDestino) {
		return centroTrabajoServiceEntityLocal.validarSubDelOrigenSubDelDestino(idSubdelegacionOrigen, idSubdelegacionDestino);		
	}
	
	@Override
	public void actualizarNombreComercial(SujetoObligado sujetoObligado) {
		entity.actualizarNombreComercial(sujetoObligado);		
	}
	
	@Override
	public CentroTrabajo actualizarCentroTrabajo(
			CentroTrabajo centroTrabajo, Usuario usuario, Subdelegacion subdelegacionDestino , boolean notificarSindo, String idMunicipioIMSS)
			throws GestionPatronalBusinessException {
		
		
		Subdelegacion subdelegacionOrigen = obtenerSubdelegacion(centroTrabajo.getCveIdPatronSujetoObligado());
		
        log.debug("Actualizando centro de trabajo");
		if(subdelegacionDestino==null)
			subdelegacionDestino = afiliacionService.obtenerSubdelegacionPorDomicilio(centroTrabajo);
		
		centroTrabajo = centroTrabajoServiceEntityLocal.actualizarCentroTrabajo(centroTrabajo);
		centroTrabajoServiceEntityLocal.actualizarSubdelegacionDelRegistroPatronal(centroTrabajo.getCveIdPatronSujetoObligado(), subdelegacionDestino.getId());
		actualizarMunicipioIMSS(centroTrabajo.getCveIdPatronSujetoObligado(), idMunicipioIMSS);
		if(notificarSindo)
			enviarCambioMovimiento4DeltaSINDO(centroTrabajo, subdelegacionOrigen);
		// Datos de Contacto
		formaContactoServiceEntity.eliminarMediosContactoCentroTrabajo(centroTrabajo.getCveIdPatronSujetoObligado());
		try {			
			for (MedioContacto medioContacto : centroTrabajo.getMediosContacto())
				medioContacto.setClave(null);					
			List<MedioContacto> mdContacto = mediosContactoService.registrarMedioDeContacto(centroTrabajo.getMediosContacto());			
			formaContactoServiceEntity.asociarMediosContactoACentroTrabajo(mdContacto, centroTrabajo.getCveIdPatronSujetoObligado());
		} catch (RegistrarMedioContactoException e) {
			e.printStackTrace();
		}
		return centroTrabajo;
	}
	
	private void actualizarMunicipioIMSS(Long idRegistroPatronal, String idNuevoMunicipioIMSS) throws GestionPatronalBusinessException{
		if( StringUtils.isBlank(idNuevoMunicipioIMSS))
			throw new GestionPatronalBusinessException("Debe proporcionar el identificador del nuevo municipio IMSS");
		
		MunicipioIMSS municipio = centroTrabajoServiceEntityLocal.consultarMunicipioIMSSPorRegistroPatronal(idRegistroPatronal);
		
		if(municipio!=null ){
			String idNuevoMunicipio = String.valueOf(idNuevoMunicipioIMSS);
			
			if(!municipio.getIdMunicipio().equals(idNuevoMunicipio))
					centroTrabajoServiceEntityLocal.actualizarMunicipioIMSSDelRegistroPatronal(idRegistroPatronal, idNuevoMunicipioIMSS);
		}
	}
	
    private void enviarCambioMovimiento4DeltaSINDO(CentroTrabajo centroTrabajo, Subdelegacion subdelegacionOrigen) throws GestionPatronalBusinessException {
        log.debug("Preparando envio cambio movimiento 04 Delta --->>> SINDO");
        Long idSujetoObligado = centroTrabajo.getCveIdPatronSujetoObligado();

        SujetoObligado sujetoObligado = new SujetoObligado();
        sujetoObligado.setCveIdSujetoObligado(idSujetoObligado);
        sujetoObligado = entity.consultarDetalleSujetoObligado(sujetoObligado);

        log.debug("Consultando domicilio con id sujeto obligado: {}", idSujetoObligado);
        Domicilio d = domicilioCentroTrabajoSoloSiExiste(idSujetoObligado);

        log.debug("Consultando subdelegacion con id sujeto obligado: {}", idSujetoObligado);
        Subdelegacion subdelegacionDestino = entity.consultarSubdelegacion(idSujetoObligado);
        movimientoPatronalBusinessRemote.enviarModificacionPatronal(buildMovimientoPatronalType04(sujetoObligado, subdelegacionDestino, d, subdelegacionOrigen));

        if (sujetoObligado.getFisica() != null) {
            List<SujetoObligado> rpRelacionados = registroPatronalServiceEntity.listaRPRelacionadosPersonaFisica(sujetoObligado.getNumeroRegistroPatronal());
            log.debug("Fisica: rprelacionados: {}", rpRelacionados);
            for (SujetoObligado so:rpRelacionados) {
                movimientoPatronalBusinessRemote.enviarModificacionPatronal(buildMovimientoPatronalType04(so, subdelegacionDestino, d, subdelegacionOrigen));
            }
        }
        else if (sujetoObligado.getMoral() != null) {
            List<SujetoObligado> rpRelacionados = registroPatronalServiceEntity.listaRPRelacionadosPersonaMoral(sujetoObligado.getNumeroRegistroPatronal());
            log.debug("Moral: rprelacionados: {}", rpRelacionados);
            for (SujetoObligado so:rpRelacionados) {
                movimientoPatronalBusinessRemote.enviarModificacionPatronal(buildMovimientoPatronalType04(so, subdelegacionDestino, d, subdelegacionOrigen));
            }
        }
    }

    private Domicilio domicilioCentroTrabajoSoloSiExiste(Long idSujetoObligado) {
        Long idDomicilioCentroTrabajo = entity.consultarClaveDomicilioCentroTrabajo(idSujetoObligado);
        return centroTrabajoServiceEntityLocal.findDomicilio(idDomicilioCentroTrabajo);
    }
	
	/**
	 * Agrega los medios de contacto de un sujeto obligado
	 * @author Hugo Armando Mart?nez Cham?nica
	 * @param so
	 * void
	 */
	private void agregarDatosContacto(SujetoObligado so){
		Persona persona = new Persona();
		if(so.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
			persona.setIdPersona(so.getFisica().getIdPersona());
			TipoPersona tipoPersona = new TipoPersona();
			//Los Representantes legales son todos personas f?sicas sin excepci?n
			tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
			persona.setTipoPersona(tipoPersona);	
		}else if(so.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
			persona.setIdPersona(so.getMoral().getIdPersona());
			TipoPersona tipoPersona = new TipoPersona();
			//Los Representantes legales son todos personas f?sicas sin excepci?n
			tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
			persona.setTipoPersona(tipoPersona);
			
		}
		
		
		
		List<? extends MedioContacto> mediosContacto = Collections.emptyList();
		try {
			System.out.println("Consultare medios de contacto para Sujeto Obligado: " + so.getCveIdSujetoObligado());
			mediosContacto = mediosContactoService.consultarMedioDeContactoPersona(persona);
			System.out.println("Obtuve medios de contacto para SO");
		} catch (PersonaSinMedioDeContactoException e) {
			System.out.println("La persona no tiene medios de contacto");
			log.debug("La persona no tiene medios de contacto");
		}
		TelefonoFijo telefonoFijo=null;
		TelefonoMovil telefonoMovil=null;
		CorreoElectronico correoElectronico = null;
		for(Object mCon : mediosContacto){
			MedioContacto auxMedio = (MedioContacto)mCon;
			System.out.println("Verificando Telefono Fijo");
			if(TipoMedioContacto.TIPO_TELEFONO_FIJO.
					equals(auxMedio.getTipoMedioContacto().
							getIdTipoMedioContacto())){
				System.out.println("Agregando Telefono Fijo");
				telefonoFijo = (TelefonoFijo)mCon;
			}
			System.out.println("Verificando Telefono Movil");
			if(TipoMedioContacto.TIPO_TELEFONO_MOVIL.
					equals(auxMedio.getTipoMedioContacto().
							getIdTipoMedioContacto())){
				System.out.println("Agregando Telefono Movil");
				telefonoMovil = (TelefonoMovil)mCon;
			}
			System.out.println("Verificando Correo Electronico");
			if(TipoMedioContacto.TIPO_CORREO_ELECTRONICO.
					equals(auxMedio.getTipoMedioContacto().
							getIdTipoMedioContacto())){
				System.out.println("Agregando Correo");
				correoElectronico = (CorreoElectronico)mCon;
			}
			
		}
		
		if(so.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
			so.getFisica().setCorreoElectronico(correoElectronico);
			so.getFisica().setTelefonoFijo(telefonoFijo);
			so.getFisica().setTelefonoMovil(telefonoMovil);
		}else if(so.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
			so.getMoral().setCorreoElectronico(correoElectronico);
			so.getMoral().setTelefonoFijo(telefonoFijo);
			so.getMoral().setTelefonoMovil(telefonoMovil);
		}	
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote#modificarDatosContacto(mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado)
	 */
	@Override
	public void modificarDatosContacto(SujetoObligado so, Usuario usuario) throws GestionPatronalBusinessException {
		//mediosContactoService.
		List<MedioContacto> datosContactoNuevos = new ArrayList<MedioContacto>();
		List<MedioContacto> datosContactoActualizar = new ArrayList<MedioContacto>();
		TelefonoFijo telefonoFijo=null;
		TelefonoMovil telefonoMovil=null;
		CorreoElectronico correoElectronico = null;
		if(so.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
			correoElectronico=so.getFisica().getCorreoElectronico();
			telefonoFijo=so.getFisica().getTelefonoFijo();
			telefonoMovil=so.getFisica().getTelefonoMovil();
		}else if(so.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
			correoElectronico=so.getMoral().getCorreoElectronico();
			telefonoFijo=so.getMoral().getTelefonoFijo();
			telefonoMovil=so.getMoral().getTelefonoMovil();
		}
		if(correoElectronico.getClave()!= null && correoElectronico.getClave()!=0){
			datosContactoActualizar.add(correoElectronico);
		}else if(correoElectronico.getClave()== null || (correoElectronico.getClave()!=null && correoElectronico.getClave()==0)){
			datosContactoNuevos.add(correoElectronico);
		}
		if(telefonoFijo.getClave()!= null && telefonoFijo.getClave()!=0){
			datosContactoActualizar.add(telefonoFijo);
		}else if(telefonoFijo.getClave()== null || (telefonoFijo.getClave()!=null && telefonoFijo.getClave()==0)){
			datosContactoNuevos.add(telefonoFijo);
		}
		if(telefonoMovil.getClave()!= null && telefonoMovil.getClave()!=0){
			datosContactoActualizar.add(telefonoMovil);
		}else if(telefonoMovil.getClave()== null || (telefonoMovil.getClave()!=null && telefonoMovil.getClave()==0)){
			datosContactoNuevos.add(telefonoMovil);
		}
		
		if(!datosContactoActualizar.isEmpty()){
			try {
				mediosContactoService.actualizarMedioDeContacto(datosContactoActualizar);
			} catch (RegistrarMedioContactoException e) {
				e.printStackTrace();
			}
		}
		
		if(!datosContactoNuevos.isEmpty()){
			try {
				Persona persona = null;
				datosContactoNuevos = mediosContactoService.registrarMedioDeContacto(datosContactoNuevos);
				if(so.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
					persona = so.getFisica();
					System.out.println("Persona Fisica para contacto: "+so.getFisica());
				}else if(so.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
					persona = so.getMoral();
					System.out.println("Persona Moral para contacto: "+so.getMoral());
				}
				System.out.println("Persona para contacto: "+persona.getIdPersona());
				entity.asociarMediosContacto(datosContactoNuevos, so.getTipoPersonaFiscal(), persona);
			} catch (RegistrarMedioContactoException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote#obtenerSubdelegacion(java.lang.Long)
	 */
	@Override
	public Subdelegacion obtenerSubdelegacion(Long cveIdSujetoObligado) {
		return entity.consultarSubdelegacion(cveIdSujetoObligado);
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote#obtenerSujetoObligadoPorRepresentanteLegal(java.lang.Long)
	 */
	@Override
	public DatosSalidaPaginador<Persona> obtenerPersonasRepresentadasPorRepresentanteLegal(
			Long cveIdPersona) {
		
		DatosSalidaPaginador<Persona> response = new DatosSalidaPaginador<Persona>();
		
		List<SujetoObligado> sujetos = entity.consultarSujetosRepresentadosPorRepresentanteLegal(cveIdPersona);
		Map<Long,Persona> personas = null;
		List<Persona> personaList = new ArrayList<Persona>();
		if(sujetos!= null & sujetos.size() > 0 ){
			personas = new TreeMap<Long,Persona>();
			for(SujetoObligado sujeto : sujetos){
				if(sujeto.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
					if(sujeto.getNombreComercial()==null || (sujeto.getNombreComercial()!=null &&  sujeto.getNombreComercial().equals("") )){
						sujeto.setNombreComercial(sujeto.getFisica().getNombre()+" "
								+ sujeto.getFisica().getPrimerApellido()+ " "
								+sujeto.getFisica().getSegundoApellido());
					}
					personas.put(sujeto.getFisica().getIdPersona(), sujeto.getFisica());
				}else if(sujeto.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
					if(sujeto.getNombreComercial()==null || (sujeto.getNombreComercial()!=null &&  sujeto.getNombreComercial().equals("") )){
						sujeto.setNombreComercial(sujeto.getMoral().getRazonSocial());
					}
					personas.put(sujeto.getMoral().getIdPersona(), sujeto.getMoral());
				}
			}
		}
		
		if(personas!= null){
			Iterator<Persona> itPersona = personas.values().iterator();
			while( itPersona.hasNext() ){
				personaList.add(itPersona.next());
			}
		}
		
		response.setAaData(personaList);
		response.setiTotalDisplayRecords(0);
		response.setiTotalRecords(personaList.size());
		return response;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote#obtenerPersonaPorIdentificador(java.lang.Long)
	 */
	@Override
	public Persona obtenerPersonaPorIdentificador(Long cveIdPersona) {
		Persona persona = entity.obtenerPersona(cveIdPersona);
		if(persona!=null){
			List<Long> tiposDomicilio = new ArrayList<Long>();
			tiposDomicilio.add(mx.gob.imss.ctirss.delta.model.enums.TipoDomicilioEnum.PARTICULAR.getId());
			try {
				List<Domicilio> domicilios = domicilioService.obtenerDomiciliosPersonaPorTipo(persona, tiposDomicilio);
				log.info("Se encontro domicilio particular");
				persona.setDomicilios(domicilios);
			} catch (DomicilioNoLocalizadoException e) {
				e.printStackTrace();
				log.info("No existe domicilio particular para la persona::::"+persona.getIdPersona());
			}
		}
		return persona;
	}
	
	@Override
	public Persona obtenerPersonaMoralPorIdentificador(Long cveIdPersona) {
		Persona persona = entity.obtenerPersonaMoral(cveIdPersona);
		if(persona!=null){
			List<Long> tiposDomicilio = new ArrayList<Long>();
			tiposDomicilio.add(mx.gob.imss.ctirss.delta.model.enums.TipoDomicilioEnum.PARTICULAR.getId());
			try {
				List<Domicilio> domicilios = domicilioService.obtenerDomiciliosPersonaPorTipo(persona, tiposDomicilio);
				log.info("Se encontro domicilio particular moral");
				persona.setDomicilios(domicilios);
			} catch (DomicilioNoLocalizadoException e) {
				e.printStackTrace();
				log.info("No existe domicilio particular para la persona moral::::"+persona.getIdPersona());
			}
		}
		return persona;
	}
	
	@Override
	public Persona  obtenerMediosContactoPersona(
			Persona persona) throws GestionPatronalBusinessException {
		
		if(persona.getIdPersona()== null || persona.getTipoPersona()==null)
			throw new GestionPatronalBusinessException("El tipo de persona y su identificador son requeridos");
		
		List<? extends MedioContacto> mediosContacto = Collections.emptyList();
		
		try {
			System.out.println("Consultare medios de contacto para Sujeto Obligado: " + persona.getIdPersona());
			mediosContacto = mediosContactoService.consultarMedioDeContactoPersona(persona);
			
			TelefonoFijo telefonoFijo=null;
			TelefonoMovil telefonoMovil=null;
			CorreoElectronico correoElectronico = null;
			
			for(Object mCon : mediosContacto){
				MedioContacto auxMedio = (MedioContacto)mCon;
				System.out.println("Verificando Telefono Fijo");
				if(TipoMedioContacto.TIPO_TELEFONO_FIJO.
						equals(auxMedio.getTipoMedioContacto().
								getIdTipoMedioContacto())){
					System.out.println("Agregando Telefono Fijo");
					telefonoFijo = (TelefonoFijo)mCon;
				}
				System.out.println("Verificando Telefono Movil");
				if(TipoMedioContacto.TIPO_TELEFONO_MOVIL.
						equals(auxMedio.getTipoMedioContacto().
								getIdTipoMedioContacto())){
					System.out.println("Agregando Telefono Movil");
					telefonoMovil = (TelefonoMovil)mCon;
				}
				System.out.println("Verificando Correo Electronico");
				if(TipoMedioContacto.TIPO_CORREO_ELECTRONICO.
						equals(auxMedio.getTipoMedioContacto().
								getIdTipoMedioContacto())){
					System.out.println("Agregando Correo");
					correoElectronico = (CorreoElectronico)mCon;
				}
				
			}
			
			persona.setTelefonoFijo(telefonoFijo);
			persona.setTelefonoMovil(telefonoMovil);
			persona.setCorreoElectronico(correoElectronico);
			
			System.out.println("Obtuve medios de contacto para persona");
		} catch (PersonaSinMedioDeContactoException e) {
			System.out.println("La persona no tiene medios de contacto");
			log.debug("La persona no tiene medios de contacto");
		}
		
		
		return persona;
	}

	@Override
	public List<Bien> obtenerBienes(Long idPatronSujetoObligado){
		return entity.consultarBienes(idPatronSujetoObligado);
	}

	@SuppressWarnings("unused")
	@Override
	public void actualizarDenominacionRazonSocial(Tramite tramite,
			Usuario usuario, boolean notificarSindo) throws GestionPatronalBusinessException {
		
		Long idPersona=null;
		try{
			System.err.println("Tramite Identificado");
			Modulo modulo = new Modulo();
			modulo.setIdModulo(ModuloEnum.PATRONES.getCodigo().longValue());
			Persona persona = null;
			if(tramite instanceof TramiteMoral){
				System.err.println("Tramite Moral Identificado");
                TramiteMoral tramiteMoral = (TramiteMoral) tramite;
                afectarDatos(tramiteMoral, modulo);//En esta versi?l servicio de persona contempla la notificaci? SINDO
                persona = tramiteMoral.getDatosICA().getPersonaMoralIMSS();
			}
			if(tramite instanceof TramiteFisica){
                TramiteFisica tramiteFisica = (TramiteFisica) tramite;
                afectarDatos(tramiteFisica, modulo);//En esta versi?l servicio de persona contempla la notificaci? SINDO
                persona = tramiteFisica.getDatosICA().getPersonaFisicaIMSS();
			}
			
			
			//enviarMovimientosDeActualizacionDatosGeneralesASindo(persona, notificarSindo);
			
		} catch (AfectacionDatosPersonaException e) {
			e.printStackTrace();
		} catch (PersonaNoEncontradaException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void enviarMovimientosDeActualizacionDatosGeneralesASindo(Persona persona, boolean notificarSindo) throws GestionPatronalBusinessException, RFCNoLocalizadoEnEntidadExternaException{
//		SujetoObligado sujetoConsulta = new SujetoObligado();
		String nuevaRazonSocial = "";
		
		Moral personaMoralSAT = null;
		Fisica personaFisicaSAT = null;	
		
		if(!notificarSindo){
			log.error("No se notificara a SINDO el movimiento de actualizacion de datos generales 05");
			return;
		}
		if(persona instanceof Fisica){
			Fisica fisica = (Fisica)persona;
			persona.setTipoPersona(new TipoPersona());
			persona.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
//			sujetoConsulta.setFisica(fisica);
//			sujetoConsulta.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
			personaFisicaSAT = consultaPersonaFisicaWsSATPorRFC(fisica.getRfc()); 
			nuevaRazonSocial = buildRazonSocial(personaFisicaSAT);
		}else if(persona instanceof Moral){
			Moral moral = (Moral)persona;
			persona.setTipoPersona(new TipoPersona());
			persona.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
//			sujetoConsulta.setMoral(moral);
//			sujetoConsulta.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
			personaMoralSAT = consultaPersonaMoralWsSATPorRFC(moral.getRfc());
			nuevaRazonSocial = buildRazonSocial(personaMoralSAT);
			log.info("Sujetos obligados encontrados por el id de la persona: "+ nuevaRazonSocial.toString());
			
		}
		
		try{
			List<SujetoObligado> rps = listarRegistrosPatronalesPorPersona(persona);
			log.info("Sujetos obligados encontrados por el id de la persona: "+ persona.getIdPersona() + ", tipoPersona: "+ persona.getTipoPersona());
			if(rps!=null && rps.size()>0)
				for(SujetoObligado rp : rps){
					queueMessageMovimiento05(rp, rp.getSubdelegacion(), nuevaRazonSocial);
				}
			else
				log.error("No existian registros patronales previos a los cuales generar movimiento 05"); //Se omite la excepcion pues para el alta es posible que no existan registros patronales
		}catch(GestionPatronalBusinessException gpbe){
			gpbe.printStackTrace();
			log.error("Error: "+gpbe.getMessage());
			log.error("No existian registros patronales previos a los cuales generar movimiento 05"); //Se omite la excepcion pues para el alta es posible que no existan registros patronales
		}
	}
	
    @Override
	public void afectarDatos(TramiteMoral tramite, Modulo modulo) throws AfectacionDatosPersonaException, PersonaNoEncontradaException {
        log.info("afectarDatos(TramiteMoral, Modulo)");
        TramiteCambioInformacionPersona tcp = new TramiteCambioInformacionPersona();
        tcp.setTramiteId(tramite.getTramiteId());
        tcp.setDatosICA(tramite.getDatosICA());
        tcp.setDatosModifManual(tramite.getDatosMDM());
        personaService.afectarDatos(tcp, modulo);
        log.info("despues de personaService.afectarDatos(TramiteFisica, Modulo)");
    }

    @Override
	public void queueMessageMovimiento05(SujetoObligado sujetoObligadoPrev, Subdelegacion subdelegacion, String nuevaRazonSocial) {
        MovimientoPatronalType movimientoPatronalType = buildMovimientoPatronalType05(sujetoObligadoPrev, subdelegacion, nuevaRazonSocial);
        movimientoPatronalBusinessRemote.enviarModificacionPatronal(movimientoPatronalType);
        List<SujetoObligado> soRelacionados = registroPatronalServiceEntity
            .listaRPRelacionadosPersonaFisica(sujetoObligadoPrev.getNumeroRegistroPatronal());
        log.info("Patrones relacionados a " + sujetoObligadoPrev.getNumeroRegistroPatronal() + " : "+ soRelacionados.size());
        log.info("Movimiento 05, sujeto obligado principal por id persona: \n", movimientoPatronalType.toString());
        for (SujetoObligado sujetoObligado:soRelacionados) {
        	log.info("Enviando movimiento 05 de Patron Relacionado: "+ sujetoObligado.getNumeroRegistroPatronal());
        	MovimientoPatronalType mov05 = buildMovimientoPatronalType05(sujetoObligado, subdelegacion, nuevaRazonSocial);
        	movimientoPatronalBusinessRemote.enviarModificacionPatronal(mov05);
        	
        }
    }

    @Override
	public void afectarDatos(TramiteFisica tramiteFisica, Modulo modulo) throws AfectacionDatosPersonaException, PersonaNoEncontradaException {
        log.info("afectarDatos(TramiteFisica, Modulo)");
        TramiteCambioInformacionPersona tcp = new TramiteCambioInformacionPersona();
        tcp.setTramiteId(tramiteFisica.getTramiteId());
        tcp.setDatosICA(tramiteFisica.getDatosICA());
        tcp.setDatosModifManual(tramiteFisica.getDatosMDM());
        personaService.afectarDatos(tcp, modulo);//Instruccion problematica. Encolar mensaje primero
        log.info("despues de personaService.afectarDatos(TramiteFisica, Modulo)");
    } 

	@Override
	public void actualizarEscrituraConstitutiva(Tramite tramite,
			Usuario usuario) throws GestionPatronalBusinessException {
		TramiteMoral tMoral = (TramiteMoral)tramite;
		EscrituraConstitutiva escrituraConstitutiva = tMoral.getMoral().getEscrituraConstitutiva();
		escrituraConstitutiva.setCveIdPersonaMoral(tMoral.getMoral().getIdPersona());
		escrituraConstitutivaServiceEntityLocal.actualizarEscrituraConstitutiva(escrituraConstitutiva);
	}

	@Override
	public void actualizarRegistroSindicato(Tramite tramite, Usuario usuario)
			throws GestionPatronalBusinessException {
		TramiteMoral tMoral = (TramiteMoral)tramite;
		RegistroSindicato sindicato = tMoral.getMoral().getRegistroSindicato();
		sindicato.setCveIdPersonaMoral(tMoral.getMoral().getIdPersona());
		registroSindicatoServiceEntityLocal.actualizarRegistroSindicato(sindicato);
	}

	@Override
	public void actualizarDatosDeContacto(Tramite tramite, Usuario usuario)
			throws GestionPatronalBusinessException {
		
//		List<MedioContacto> datosContactoActualizar = new ArrayList<MedioContacto>();
//		List<MedioContacto> datosContactoNuevos = new ArrayList<MedioContacto>();
		List<MedioContacto> mediosTramite = null;
		Persona persona = null;
//		TipoPersonaFiscal tipoPersona = null;
		if(tramite instanceof TramiteFisica){
			TramiteFisica tFisica = (TramiteFisica)tramite;
			persona = tFisica.getFisica();
			if(persona.getTipoPersona()==null){
				persona.setTipoPersona(new TipoPersona());
				persona.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
			}
			mediosTramite = persona.getMediosContacto();
			System.out.println("Medios tramite fisica: "+mediosTramite!=null ? mediosTramite.size() : 0);
//			tipoPersona = TipoPersonaFiscal.FISICA;
		}else if(tramite instanceof TramiteMoral){
			TramiteMoral tMoral = (TramiteMoral)tramite;
			persona = tMoral.getMoral();
			if(persona.getTipoPersona()==null){
				persona.setTipoPersona(new TipoPersona());
				persona.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
			}
			mediosTramite = persona.getMediosContacto();
			System.out.println("Medios tramite moral: "+mediosTramite!=null ? mediosTramite.size() : 0);
//			tipoPersona = TipoPersonaFiscal.MORAL;
		}
		
//		for(MedioContacto medio : mediosTramite){
//			if(medio.getClave()!=null){
//				datosContactoActualizar.add(medio);
//				System.err.println("Se actualizara contacto: "+medio);
//			}else if(medio.getClave()==null){
//				System.err.println("Se agregara nuevo contacto: "+medio);
//				datosContactoNuevos.add(medio);
//			}
//		}
		
		if(!mediosTramite.isEmpty()){
			entity.reemplazarMediosContactoDePersona(persona);	
//				datosContactoNuevos = mediosContactoService.registrarMedioDeContacto(datosContactoNuevos);
				System.out.println("Persona para contacto: "+persona.getIdPersona());
//				entity.asociarMediosContacto(datosContactoNuevos, tipoPersona, persona);
			
		}
	}
	
	@Override
	public Socio obtenerDomicilioFiscal(Long idPersona,
			String tipoPersonaParam) {
		
		Persona persona = null;
		TipoPersona tipoPersona = new TipoPersona();
		
		log.info("/**** SERVICIO PARA OBTENER EL DOMICILIO DE UN SOCIO  ****/");
		log.info("SOCIO PERSONA ID: "+idPersona);
		
//		Long idDomicilio = null;
		Socio socioRespuesta = new Socio();
		
		if (tipoPersonaParam.equalsIgnoreCase("fisica")) {
			persona = new Fisica();
			tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
//			idDomicilio=entity.consultarClaveDomicilioFiscal(Long.parseLong(idPersona), TipoPersonaFiscal.FISICA);
		} else if (tipoPersonaParam.equalsIgnoreCase("moral")
				|| tipoPersonaParam.equalsIgnoreCase("fideicomiso")) {
			persona = new Moral();
			tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
//			idDomicilio=entity.consultarClaveDomicilioFiscal(Long.parseLong(idPersona), TipoPersonaFiscal.MORAL);
		}
		persona.setIdPersona(idPersona);
		persona.setTipoPersona(tipoPersona);
		
		Domicilio domicilio = obtenerDomicilioFiscal(persona);
		
//		log.info("/**** ID DOMICILIO :: "+idDomicilio);
//		
//		if(idDomicilio!=null){
//			Domicilio domicilioFiscal = new Domicilio();
//			Integer claveDom = idDomicilio.intValue();
//			domicilioFiscal.setClave(claveDom);
//			
//			Domicilio domicilio = null;
//			try {
//				domicilio = domicilioService.consultarDomicilio(domicilioFiscal);
//			} catch (DomicilioNoLocalizadoException e) {
//				e.printStackTrace();
//			}			
//			
//			if (domicilio!=null) {
//				try {
//					socioRespuesta.setDomicilioFiscal(sujetoObligadoUtility.convertDomicilioToDomicilioFiscal(domicilio)) ;
//				} catch (Exception e) {					
//					e.printStackTrace();
//				}
//			}
//			log.info("/**** SE OBTUVO EL DOMICILIO CON ID  ****/"+domicilio.getClave());
//		}
		if(domicilio!=null)
			socioRespuesta.setDomicilioFiscal(sujetoObligadoUtility.convertDomicilioToDomicilioFiscal(domicilio));
		
		return socioRespuesta;
	}
	
	@Override
	public void eliminarActaConstitutivaDePersona(Long idPersona) {
		entity.eliminarEscrituraConstitutivaDePersona(idPersona);
	}

	@Override
	public void eliminarSindicatoDePersona(Long idPersona) {
		entity.eliminarRegistroSindicatoDePersona(idPersona);
	}
	
	@Override
	public DatosSalidaPaginador<SujetoObligado> listarRegistrosPatronales(
			DatosEntradaPaginador<SujetoObligado> input) {
		log.error("consultando Registros patronales");
		SujetoObligado sujetoObligado=input.getModelo();
		DatosSalidaPaginador<SujetoObligado> output = null;

		if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
			output = entity.consultarRegistrosPatronalesPersonaFisica(input);
		}else if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
			output = entity.consultarRegistrosPatronalesPersonaMoral(input);
		}
				
		List<SujetoObligado> sujetosObligadosComplementarios=new ArrayList<SujetoObligado>();

		
		for(SujetoObligado sujetoObligadoActual : output.getAaData()){
			sujetosObligadosComplementarios.add(obtenerDetalleRP(sujetoObligadoActual));
		}
		
		output.setAaData(sujetosObligadosComplementarios);
		
		return output;
	}
	
	@Override
	public void actualizarRepresentanteLegal(Tramite tramite, Usuario usuario, Long idSolicitud,
			OrigenSolicitudEnum origenSolicitud) throws GestionPatronalBusinessException {
		
		
		if(tramite instanceof TramiteMoral){
			System.err.println("Tramite Moral Identificado para actualizacion de rep legal tras concluir la solicitud");
			
			List<RepresentanteLegal> representantes = ((TramiteMoral) tramite).getMoral().getRepresentantesLegales();
			representanteLegalServiceBusiness.actualizarRepresentantesLegales(idSolicitud, representantes, origenSolicitud);
			actualizarInformacionPersona(representantes, idSolicitud);
		} else if(tramite instanceof TramiteFisica){
			System.err.println("Tramite Fisica Identificado para actualizacion de rep legal tras concluir la solicitud");
			List<RepresentanteLegal> representantes = ((TramiteFisica) tramite).getFisica().getRepresentantesLegales();
			representanteLegalServiceBusiness.actualizarRepresentantesLegales(idSolicitud, representantes, origenSolicitud);
			actualizarInformacionPersona(representantes, idSolicitud);
		} else if(tramite instanceof TramiteRepresentanteLegal) {
			TramiteRepresentanteLegal tramiteRP = (TramiteRepresentanteLegal) tramite;
			List<RepresentanteLegal> representantes = null;
			Integer cveIdTipoPoder = tramiteRP.getFisica().getTipoPoder().getIdTipoPoder();
			if(tramiteRP.getFisicaRepresentada()!=null) {
				representantes = tramiteRP.getFisicaRepresentada().getRepresentantesLegales();
				for(RepresentanteLegal rl:representantes){
					rl.setPersonaFisicaRepresentada(tramiteRP.getFisicaRepresentada());
					rl.setCveIdTipoPoder(Long.valueOf(cveIdTipoPoder));
				}
				
				
			} else {
				representantes = tramiteRP.getMoralRepresentada().getRepresentantesLegales();
				for(RepresentanteLegal rl:representantes){
					rl.setPersonaMoralRepresentada(tramiteRP.getMoralRepresentada());
					rl.setCveIdTipoPoder(Long.valueOf(cveIdTipoPoder));
				}
			}
			representanteLegalServiceBusiness.actualizarRepresentantesLegales(idSolicitud, 
				representantes, origenSolicitud);
			
		}
	}
	
	private void actualizarInformacionPersona(List<RepresentanteLegal> representantes, Long idSolicitud) {
		try {
			for (RepresentanteLegal representante : representantes) {
				if (representante.getTramiteFisica() != null) {
					this.log.info("Se tienen datos del ICA para la actualizacion del representante legal");
					this.personaServiceRemote.ejecutarTramiteCambioInfoPersona(
							representante.getTramiteFisica(), idSolicitud,
							ModuloEnum.PATRONES.getCodigo().longValue());
				} else {
					personaServiceRemote.actualizarPersona(representante
							.getPersonaFisica());
				}
			}
		} catch (PersonaNoEncontradaException pnee) {
			pnee.printStackTrace();
		} catch (AfectacionDatosPersonaException e) {
			e.printStackTrace();
		} catch (RegistroPersonaFisicaException e) {
			e.printStackTrace();
		}
	}

	@Override
	public DomicilioFiscal obtenerDomicilioFiscalPatron(Persona persona) {
		TipoPersonaFiscal tipoPersona = 
				persona.getTipoPersona().getIdTipoPersona().equals(TipoPersonaFiscal.FISICA.getCodigo().longValue()) ? TipoPersonaFiscal.FISICA : TipoPersonaFiscal.MORAL; 
		Long idDomicilio=entity.consultarClaveDomicilioFiscal(persona.getIdPersona(), tipoPersona);
		System.err.println("Domicilio Fiscal encontrado: "+idDomicilio);
		if(idDomicilio!=null){
			Domicilio domicilioFiscal = new Domicilio();
			Integer claveDom = idDomicilio != null ? idDomicilio.intValue() : null;
			domicilioFiscal.setClave(claveDom);
			
			Domicilio domicilio = null;
			try {
				domicilio = domicilioService.consultarDomicilio(domicilioFiscal);
				
			} catch (DomicilioNoLocalizadoException e) {
				e.printStackTrace();
			}
			return sujetoObligadoUtility.convertDomicilioToDomicilioFiscal(domicilio);
		}
		
		return null;
	}

	@Override
	public SujetoObligado consultarPorNumeroRegistroPatronal(
			String numeroRegistroPatronal) {
		return entity.consultarPorRegistroPatronal(numeroRegistroPatronal, null);
	}

	@Override
	public SujetoObligado obtenerDetallePorRFC(String rfc)
			throws GestionPatronalBusinessException {
		
		if(rfc==null)
			throw new GestionPatronalBusinessException("dato.rfc.requerido");
		
		TipoPersonaFiscal tipoPersonaFiscal = obtenerTipoPersonaFiscal(rfc);
		
		if(tipoPersonaFiscal==null)
			throw new GestionPatronalBusinessException("error.rfc.invalido");
		
		SujetoObligado sujetoObligado = new SujetoObligado();
		sujetoObligado.setTipoPersonaFiscal(tipoPersonaFiscal);
		
		if(tipoPersonaFiscal.equals(TipoPersonaFiscal.FISICA)){
			Fisica fisica = new Fisica();
			fisica.setRfc(rfc);
			sujetoObligado.setFisica(fisica);
		}else if(tipoPersonaFiscal.equals(TipoPersonaFiscal.MORAL)){
			Moral moral = new Moral();
			moral.setRfc(rfc);
			sujetoObligado.setMoral(moral);
		}
		
		List<SujetoObligado> rps = obtenerDetalleSujetoObligado(sujetoObligado);
		if(rps!=null && rps.size()>0){
			sujetoObligado = rps.get(0);
			sujetoObligado.setSujetosObligados(rps);
		}else{
			throw new GestionPatronalBusinessException("error.patron.desconocido");
		}
		
		return sujetoObligado;
	}
	
	private TipoPersonaFiscal obtenerTipoPersonaFiscal(String rfc){
		if (rfc.length() == 13)
			return TipoPersonaFiscal.FISICA;
		else if (rfc.length() == 12)
			return TipoPersonaFiscal.MORAL;
		else
			return null;
	}
	
	@Override
	public CentroTrabajo consultarDomicilioCentroTrabajo(Long cveIdSujetoObligado){
		Long idDomicilioCT = entity.consultarClaveDomicilioCentroTrabajo(cveIdSujetoObligado);
		
		if(idDomicilioCT!= null){
			System.err.println("SE ENCONTRO EL DOMICILIO: "+idDomicilioCT);
			CentroTrabajo cntroTrabajo = new CentroTrabajo();
			cntroTrabajo.setClave(idDomicilioCT.intValue());
			
			try {
				Domicilio cTrabajo = domicilioService.consultarDomicilio(cntroTrabajo);
				CentroTrabajo centro = sujetoObligadoUtility.convertirDomicilioACentroTrabajo(cTrabajo);
				return centro;
			} catch (DomicilioNoLocalizadoException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public List<Subdelegacion> obtenerSubdelegacionesCompatibles(
			Long cveIdSubdelegacion) {		
		return centroTrabajoServiceEntityLocal.obtenerSubdelegacionesCompatibles(cveIdSubdelegacion);
	}
	
	@Override
	public List<SujetoObligado> listarRegistrosPatronalesPorPersona(Persona persona) throws GestionPatronalBusinessException{
		
		if(persona.getIdPersona() == null){
			throw new GestionPatronalBusinessException("El identificador de la persona es requerido");
		}
		if(persona.getTipoPersona() == null || (persona.getTipoPersona() != null && persona.getTipoPersona().getIdTipoPersona()==null)){
			throw new GestionPatronalBusinessException("El tipo de persona es requerido para realizar la consulta");
		}
		
		log.error("consultando Registros patronales");
		List<SujetoObligado> output = null;
		log.error("TipoPersona: "+persona.getTipoPersona().getIdTipoPersona());
		if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
			output = entity.consultarRegistrosPatronalesPersonaFisica(persona.getIdPersona());
		}else if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_MORAL)){
			output = entity.consultarRegistrosPatronalesPersonaMoral(persona.getIdPersona());
		}
				
		List<SujetoObligado> sujetosObligadosComplementarios=new ArrayList<SujetoObligado>();

		
		for(SujetoObligado sujetoObligadoActual : output){
			sujetosObligadosComplementarios.add(obtenerDetalleRP(sujetoObligadoActual));
		}
		
		return sujetosObligadosComplementarios;
	}

	@Override
	public List<SujetoObligado> listarRegistrosPatronalesPorPersonaDatosBasicosPatron(
			Persona persona) throws GestionPatronalBusinessException {
		if(persona.getIdPersona() == null){
			throw new GestionPatronalBusinessException("El identificador de la persona es requerido");
		}
		if(persona.getTipoPersona() == null || (persona.getTipoPersona() != null && persona.getTipoPersona().getIdTipoPersona()==null)){
			throw new GestionPatronalBusinessException("El tipo de persona es requerido para realizar la consulta");
		}
		
		log.error("consultando Registros patronales JC es la onda");
		List<SujetoObligado> output = null;
		
		if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
			Persona fisica = entity.obtenerPersona(persona.getIdPersona());
			output = entity.consultarRegistrosPatronalesRFCPersonaFisica(fisica.getRfc());
		}else if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_MORAL)){
			Persona moral = entity.obtenerPersonaMoral(persona.getIdPersona());
			output = entity.consultarRegistrosPatronalesRFCPersonaMoral(moral.getRfc());
		}
		
		return output;
	}

	@Override
	public SujetoObligado getSujetoObligadoPorMunImmsDeleSubdeRFC(
			SujetoObligado sujetoObligado)
			throws GestionPatronalBusinessException,RelacionConRegistroPatronalExisteException {
		SujetoObligado sujetoEncontrado = null;
		
		sujetoEncontrado = entity.getSujetoObligadoByNrpyCvePersonaFisicaMoral(sujetoObligado);
		
		if(sujetoEncontrado != null) {
			throw new RelacionConRegistroPatronalExisteException();
		}
		
		sujetoEncontrado = entity.getSujetoObligadoByDatosPatronyRfc(sujetoObligado);
		
		return sujetoEncontrado;
	}

	@Override
	public void finalizarSolicitudRecuperacionRP(Solicitud solicitud, FirmaElectronica firmaElectronica)
			throws GestionPatronalBusinessException, SolicitudNoEncontradaException, TramiteNoEncontradoException, SolicitudException {
		
		TramitePersonaAutorizada tPA = (TramitePersonaAutorizada) solicitud.getTramites().get(0);
		List<SujetoObligado> sujetos = tPA.getSujetosObligados();
		
		solicitud.setFechaConclusion(Calendar.getInstance().getTime());
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getCodigo());	

		if(solicitud.getSolicitudId() == null) {
			throw new SolicitudException("La solicitud no cuenta con identificador");
		} 
		//Actualizamos los estados de la solicitud
		solicitudBusinessRemote.actualizarSolicitudAEstatusConcluida(solicitud.getSolicitudId());
		
		if (firmaElectronica != null) {
			firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud, firmaElectronica);
		}
		
		try {
			for(SujetoObligado sujetoObligado: sujetos) {
				if(sujetoObligado.getFisica() != null) {
					sujetoObligado.setFisica(tPA.getPersonaFisica());
				} else {
					sujetoObligado.setMoral(tPA.getPersonaMoral());
				}
				sujetoObligado.setIndPatronConfirmado(1);
				entity.updateRelacionSujetoObligadoPersonaMoralFisica(sujetoObligado);
			}
		} catch(Exception e) {
			log.error("Ocurrio un error al afectar a los patrones",e);
			throw new GestionPatronalBusinessException("No fue posible finalizar la solicitud");
		}
		
		//Generar acuse de tramite para ventanilla
		acuseTramitesVentanillaBusinessRemote.generarAcuseTramiteVentanilla(solicitud, solicitud.getTramites().get(0), 
			TipoTramiteEnum.RECUPERACION_REGISTRO_PATRONAL.getCodigo().longValue());
	}

    public void reEnviarMovimiento04(String registroPatronal) throws GestionPatronalBusinessException {
        SujetoObligado sujetoObligado  = consultarPorNumeroRegistroPatronal(registroPatronal);
        CentroTrabajo centroTrabajo = new CentroTrabajo();
        centroTrabajo.setCveIdPatronSujetoObligado(sujetoObligado.getCveIdSujetoObligado());
        
        enviarCambioMovimiento4DeltaSINDO(centroTrabajo,sujetoObligado.getSubdelegacion());
    }

    public void enviarMovimientoCambioNombre(String registroPatronal, String nuevoNombre) throws GestionPatronalBusinessException {
        log.info("enviarMovimientoCambioNombre. rp: {} nuevo nombre: {}", registroPatronal, nuevoNombre);
        SujetoObligado sujetoObligado = consultarPorNumeroRegistroPatronal(registroPatronal);

        Long idSujetoObligado = sujetoObligado.getCveIdSujetoObligado();
        log.debug("Consultando domicilio con id sujeto obligado: {}", idSujetoObligado);

        Subdelegacion subdelegacion = entity.consultarSubdelegacion(idSujetoObligado);
        queueMessageMovimiento05(sujetoObligado, subdelegacion, nuevoNombre);
        log.info("nombre enviado");
    }
    
    @Override
	public Integer obtenerNumeroDeRPEnMunicipioIMSSPorFraccion(Long idPersona,
			Long tipoPersona, Long idMunicipio, Long idFraccion, Long idRegistroPatronalActual) {
		return  entity.consultarNumeroDeRegistrosPatronalesPorPersonaMunicipioYFraccion(idPersona, tipoPersona, idMunicipio, idFraccion, idRegistroPatronalActual);
	}
    
    @Override
    public SujetoObligado obtenerRegistroPatronalEnMunicipioIMSSPorFraccion(String rfc,
			Long tipoPersona, Long idMunicipio, Long idFraccion, Long idRegistroPatronalActual) {
    	SujetoObligado sujetoObligado = entity.consultarRegistroPatronalPorRFCMunicipioYFraccion(rfc, tipoPersona, idMunicipio, idFraccion, idRegistroPatronalActual);
    	if(sujetoObligado!=null)
    		obtenerDetalleRP(sujetoObligado);
    	
    	return sujetoObligado;
    }

    // Se agrega por INC398780 para buscar NRP con mismo municipio y RFC
    @Override
    public List<SujetoObligado> obtenerNRPEnMunicipioIMSSPorRFCyFraccion(String rfc,
			Long tipoPersona, Long idMunicipio, Long idFraccion, Long idRegistroPatronalActual) {
    	List<SujetoObligado> sujetoObligadoL = entity.consultarNRPPorRFCMunicipioYRfcYFraccion(rfc, tipoPersona, idMunicipio, idFraccion, idRegistroPatronalActual);
    	if(sujetoObligadoL!=null && sujetoObligadoL.size() > 1) {
    		for (Iterator<SujetoObligado> iterator = sujetoObligadoL.iterator(); iterator.hasNext();) {
    			SujetoObligado so = iterator.next();
    			obtenerDetalleRP(so);
			}
    	}
    	return sujetoObligadoL;
    }
    
    /**
	 * Metodo Consulta a los sujetos obligados pm o pf que a partir del RFC clase modalidad y municipio
	 * @param SujetoObligado
	 * @return List <SujetoObligado> con los registros que cumplan la condicion de busqueda
	 */
    
	public List<SujetoObligado> getSujetoObligadoByRfcClaseMunicipioModalidad(
			SujetoObligado obligado){
			if (obligado == null){
				throw new IllegalArgumentException();
			}
			return entity.getSujetoObligadoByRfcClaseMunicipioModalidad(obligado);
	}
    
	
	/**
	 * @author Hugo Armando Mart?nez Cham?nica
	 * Obtiene los detalles del sujeto obligado en base al identificador proporcionado
	 * @param sujetoObligado Contiene el identificador y tipoPersonaFiscal.
	 */
	@Override
	public List<SujetoObligado> obtenerRegistrosPatronalesPrevios(SujetoObligado sujetoObligado) throws GestionPatronalBusinessException{
	 	log.error("consultando detalle de sujeto obligado");
	 	
	 	List<SujetoObligado> sujetosObligados=new ArrayList<SujetoObligado>();
		sujetosObligados=entity.obtenerRegistrosPatronalesExceptoUltimo(sujetoObligado);
				
		List<SujetoObligado> sujetosObligadosComplementarios=new ArrayList<SujetoObligado>();

		if(sujetosObligados==null){
			log.error("No se encuentra rp asociados a la persona");
			//throw new GestionPatronalBusinessException("error.rfc.inexistente");
		}else{
			for(SujetoObligado sujetoObligadoActual : sujetosObligados){
				sujetosObligadosComplementarios.add(obtenerDetalleRP(sujetoObligadoActual));
			}
		}
				
		return sujetosObligadosComplementarios;
	}

	@Override
	public void actualizarMediosContactoCentroTrabajo(
			CentroTrabajo centroTrabajo) {
		
		formaContactoServiceEntity.eliminarMediosContactoCentroTrabajo(centroTrabajo.getCveIdPatronSujetoObligado());
		try {			
			for (MedioContacto medioContacto : centroTrabajo.getMediosContacto())
				medioContacto.setClave(null);
			List<MedioContacto> mdContacto = mediosContactoService.registrarMedioDeContacto(centroTrabajo.getMediosContacto());			
			formaContactoServiceEntity.asociarMediosContactoACentroTrabajo(mdContacto, centroTrabajo.getCveIdPatronSujetoObligado());
		} catch (RegistrarMedioContactoException e) {
			e.printStackTrace();
		}
//		return centroTrabajo;
	
		
	}
	
	@Override
	public SujetoObligado consultarPorRegistroPatronalBasic(String registroPatronal, TipoPersonaFiscal tipoPersona){
		SujetoObligado infoRegistroPatronal = entity.consultarPorRegistroPatronalBasic(registroPatronal, tipoPersona);
		CentroTrabajo cTrabajo = new CentroTrabajo();
		cTrabajo.setMediosContacto(obtenerMediosContactoDeRegistroPatronal(infoRegistroPatronal.getCveIdSujetoObligado()));
		infoRegistroPatronal.setCntroTrabajo(cTrabajo);
		return infoRegistroPatronal;
	}
	
	/**
	 * Metodo que valida si la cveIdPersona tiene la marca activa RPC
	 * 
	 * @param cveIdPersona
	 * @param cveTipoPersona
	 * @return
	 */
	public void validaCveIdPersonaPorRegistroPatronalClaseActivo(String rfc, Integer cveTipoPersona) throws GestionPatronalBusinessException {
		
		Integer numRPC = entity.validaCveIdPersonaPorRegistroPatronalClaseActivo(rfc, cveTipoPersona);
		if(numRPC>0)
			throw new GestionPatronalBusinessException("No puede proceder con el tr?mite debido a que cuenta con Registros Patronales por Clase");
		
	}

	@Override
	public Modalidad getModalidadPatron(Long idPatronSujetoObligado) {
		
		return entity.getModalidadPorIdSujetoObligado(idPatronSujetoObligado);
	}

	@Override
	public EscrituraConstitutiva obtenerEscrituraConstitutivaPorId(Long idEscritura){
		return escrituraConstitutivaServiceEntityLocal.consultarEscritura(idEscritura);
	}
	
	@Override
	public List<RepresentanteLegal> obtenerRepresentantesLegales(Long idPersonaFM, Long idTipoPersona){
		TipoPersonaEnum tipoPersona = null;
		if (TipoPersonaEnum.FISICA.getId() == idTipoPersona.longValue()) {
			tipoPersona = TipoPersonaEnum.FISICA;
		} else {
			tipoPersona = TipoPersonaEnum.MORAL;
		}
		return representanteLegalServiceBusiness
			.obtenerRepresentantesLegalesPorPersona(idPersonaFM, tipoPersona);		
	}
	
	@Override
	public DatosSalidaPaginador<RepresentanteLegal> paginarRepresentanteLegal(
			DatosEntradaPaginador<RepresentanteLegal> datatablein){		
		DatosSalidaPaginador<RepresentanteLegal> response = new DatosSalidaPaginador<RepresentanteLegal>();
		Integer iTotalRecords = 0;
		response.setAaData(new ArrayList<RepresentanteLegal>());
		if(datatablein!=null && datatablein.getModelo()!=null ){			
			RepresentanteLegal rl = (RepresentanteLegal)datatablein.getModelo();
			TipoPersonaEnum tipoPersona = null;
			if (TipoPersonaEnum.FISICA.getId() == rl.getTipoPersonaRepresentada().getIdTipoPersona().longValue()) {
				tipoPersona = TipoPersonaEnum.FISICA;
			} else {
				tipoPersona = TipoPersonaEnum.MORAL;
			}
			List<RepresentanteLegal> lista = representanteLegalServiceBusiness
				.obtenerRepresentantesLegalesPorPersona(rl.getCveIdPersona(), tipoPersona);		
			if(!CollectionUtils.isEmpty(lista)){
				iTotalRecords = lista.size();
				response.setAaData(lista);
			}
		}
		response.setiTotalDisplayRecords(0);
		response.setiTotalRecords(iTotalRecords);				
		return response;
	}
	
	@Override
	public void validaRepresentanteLegalExistente(Long idPersonaFM, Long idTipoPersona) throws GestionPatronalBusinessException {
		List<RepresentanteLegal> representantes = obtenerRepresentantesLegales(idPersonaFM, idTipoPersona);
		if(CollectionUtils.isEmpty(representantes)){
			throw new GestionPatronalBusinessException("La persona moral debe  contar con al menos un representate legal registrado.");
		}
	}

	/**
	 * Consulta basica para obtener solo los datos basicos del patron
	 * 
	 * Registro Patronal
	 * Modalidad(id, descripcion, num, siglas agregado medico)
	 * Digito Verigicador
	 * Datos de la persona fisica (si aplica)
	 * Razon social (persona modal - Si aplica)
	 * @param idPatronGeneral
	 * @return
	 */
	@Override
	public SujetoObligado getDatosBasicosPatronPorIdPatronGeneral(
			Long idPatronGeneral) throws GestionPatronalBusinessException{
		
		SujetoObligado sujeto = null;
		
		if(idPatronGeneral != null) {
			sujeto = entity.getDatosBasicosSOporIdPatronGeneral(idPatronGeneral);
			
			if(sujeto == null) {
				throw new GestionPatronalBusinessException("No se encontro el patr&oacute;n solicitado("+idPatronGeneral+").");
			}
		} else {
			throw new GestionPatronalBusinessException("El id del patron es nulo");
		}
		return sujeto;
	}

	@Override
	public SujetoObligado getDatosBasicosPatronPorIdPatronSujetoObligado(
			Long idPatronSujetoObligado)
			throws GestionPatronalBusinessException {
		SujetoObligado sujeto = null;
		
		if(idPatronSujetoObligado != null) {
			sujeto = entity.getDatosBasicosSOporIdPatronSO(idPatronSujetoObligado);
			
			if(sujeto == null) {
				throw new GestionPatronalBusinessException("No se encontro el patr&oacute;n solicitado(SO - "+idPatronSujetoObligado+").");
			}
		} else {
			throw new GestionPatronalBusinessException("El id del patron es nulo");
		}
		return sujeto;
	}
	
	@Override
	public DomicilioFiscal obtenerDomFiscal(Persona persona) {
		
		return sujetoObligadoUtility.convertDomicilioToDomicilioFiscal(obtenerDomicilioFiscal(persona));
	}
	
	@Override
	public List<SujetoObligado> getListaPatronesPorPersona(Persona persona) throws GestionPatronalBusinessException {
		if(persona.getIdPersona() == null){
			throw new GestionPatronalBusinessException("El identificador de la persona es requerido");
		}
		if(persona.getTipoPersona() == null || (persona.getTipoPersona() != null && persona.getTipoPersona().getIdTipoPersona()==null)){
			throw new GestionPatronalBusinessException("El tipo de persona es requerido para realizar la consulta");
		}
		
		List<SujetoObligado> output = null;
		Persona personaConsulta = null;
		if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
			personaConsulta = entity.obtenerPersona(persona.getIdPersona());
		}else if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_MORAL)){
			personaConsulta = entity.obtenerPersonaMoral(persona.getIdPersona());
		}
		
		output = entity.consultarRegistrosPatronalesRFC(personaConsulta.getRfc(), persona.getTipoPersona().getIdTipoPersona());
		
		return output;
	}
	
	
	
	@Override
	public List<Modalidad> getModalidadades() {
		return entity.getModalidadades();
	}
	
	@Override
    public Integer consultarMarcaRPC(
            String rfc, Integer tipoPersona) {
       
    Integer numIndicadores =  entity.validaCveIdPersonaPorRegistroPatronalClaseActivo(rfc, tipoPersona);
	
    return numIndicadores;
       
    }
	
	private String buildRazonSocialSAT(Moral moral) {
    	StringBuffer razonSocial = new StringBuffer();
    	razonSocial.append(moral.getRazonSocial());
    	if(moral.getTipoSociedad()!=null)
    		razonSocial.append(" "+safeNull(moral.getTipoSociedad().getDescripcionAbreviada()));
    	String razon = razonSocial.toString().trim();
        return razon;
    }
	private String buildRazonSocialSAT(Fisica fisica) {
		
		return String.format("%s %s %s"
                , fisica.getNombre()
                , fisica.getPrimerApellido()
                , safeNull(fisica.getSegundoApellido())).trim();
    	}
	
	private Moral consultaPersonaMoralWsSATPorRFC(String rfc)throws RFCNoLocalizadoEnEntidadExternaException{
		
		Moral pMoral = null;
	
		try {
			pMoral = localizarPersonaMoralEnSATServiceBusiness.localizarPersonaMoralEnSATxRFC(rfc);
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			e.printStackTrace();
			throw new RFCNoLocalizadoEnEntidadExternaException();
		} catch (ClienteWebserviceSatRfcException e) {
			e.printStackTrace();
			throw new RFCNoLocalizadoEnEntidadExternaException();
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			e.printStackTrace();
			throw new RFCNoLocalizadoEnEntidadExternaException();
		}
		return pMoral;
	
	}
	
	private Fisica consultaPersonaFisicaWsSATPorRFC(String rfc) throws RFCNoLocalizadoEnEntidadExternaException{
		
		Fisica pFisica = null;
		
		try {
			pFisica = localizarPersonaFisicaEnSATServiceBusiness.localizarPersonaFisicaEnSATxRFC(rfc);
			
			if(pFisica == null){
				throw new RFCNoLocalizadoEnEntidadExternaException();
			}
			
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			e.printStackTrace();
			throw new RFCNoLocalizadoEnEntidadExternaException();
			
		} catch (ClienteWebserviceSatRfcException e) {
			e.printStackTrace();
			throw new RFCNoLocalizadoEnEntidadExternaException();
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			e.printStackTrace();
			throw new RFCNoLocalizadoEnEntidadExternaException();
		}
		return pFisica;
		
	}
	
	@Override
	public Long consultarClaveDomicilioFiscal(Long idSujetoObligado, TipoPersonaFiscal tipoPersona) {
		// TODO Auto-generated method stub
		return entity.consultarClaveDomicilioFiscal(idSujetoObligado, tipoPersona);
	}	

	@Override
	public String obtenerDomicilioMigrado(Long cveIdPatronSujetoObligado) {
		// TODO Auto-generated method stub
		return entity.obtenerDomicilioMigrado(cveIdPatronSujetoObligado);
	}	
	
	@Override
	public Long consultarClaveDomicilioCentroTrabajo(Long idSujetoObligado) {
		// TODO Auto-generated method stub
		return entity.consultarClaveDomicilioCentroTrabajo(idSujetoObligado);
	}

	@Override
	public CentroTrabajo convertirDomicilioACentroTrabajo(Domicilio domicilio) {
		// TODO Auto-generated method stub
		return sujetoObligadoUtility.convertirDomicilioACentroTrabajo(domicilio);
	}

	@Override
	public DomicilioFiscal convertDomicilioToDomicilioFiscal(Domicilio domicilio) {
		// TODO Auto-generated method stub
		return sujetoObligadoUtility.convertDomicilioToDomicilioFiscal(domicilio);
	}

	@Override
	public SujetoObligado consultarPorRegistroPatronal(String registroPatronal, TipoPersonaFiscal tipoPersona) {
		// TODO Auto-generated method stub
		return entity.consultarPorRegistroPatronalDictamen(registroPatronal, tipoPersona);
	}

	@Override
	public SujetoObligado obtenerDetallesRegistroPatronal(SujetoObligado sujetoObligado) throws Exception{
		// TODO Auto-generated method stub
		return entity.obtenerDetallesRegistroPatronalDictamen(sujetoObligado);
	}

	
   @Override
    public CentroTrabajo getCentroTrabajo(Long cveIdSujetoObligado){
    	
    	CentroTrabajo centroT = new CentroTrabajo();
    		Long idDomicilioCT = consultarClaveDomicilioCentroTrabajo(cveIdSujetoObligado);
		
		if(idDomicilioCT != null) {
			
			System.out.println("SE ENCONTR� EL DOMICILIO: "+idDomicilioCT);
			CentroTrabajo centroTrabajo = new CentroTrabajo();
			centroTrabajo.setClave(idDomicilioCT.intValue());
			
			try {
				Domicilio cTrabajo = domicilioService.consultarDomicilio(centroTrabajo);
				centroT = convertirDomicilioACentroTrabajo(cTrabajo);
				
			} catch(DomicilioNoLocalizadoException e) {
				System.err.println("NO SE ENCONTRO EL DOMICILIO");
			}
			
			
		} else {
			System.out.println("NO SE ENCONTR� NINGUN CENTRO DE TRABAJO DENTRO DE LA NORMA TECNICA ASOCIADO AL PATRON: "+cveIdSujetoObligado);
			String domicilio = obtenerDomicilioMigrado(cveIdSujetoObligado);
			centroT =  new CentroTrabajo();
			centroT.setDescripcion(domicilio);
			
		}
		
		return centroT;
    }
	   
   //Se consulta prima historica, Mm AMSRT-2 - WO436058
	@Override
	public String consultaPrimaHistorica(String nrp, String fecha) {		
		return entity.consultaPrimaHistorica(nrp, fecha);
	}   
   
	@Override
	public boolean isPatronPlataforma(String nrp) throws GestionPatronalBusinessException {
		if (StringUtils.isEmpty(nrp))
			throw new GestionPatronalBusinessException("EL NRP no puede ser nulo o vacio");
		return entity.isPatronPlataforma(nrp);
	}
	
	@Override
	public boolean isPatronListaBlanca(String nrp) throws GestionPatronalBusinessException {
		if (StringUtils.isEmpty(nrp))
			throw new GestionPatronalBusinessException("EL NRP no puede ser nulo o vacio");
		return entity.isPatronListaBlanca(nrp);
	}
	
	@Override
	public Date obtenerFechaDespliegue(String nrp) throws GestionPatronalBusinessException {

		if (StringUtils.isEmpty(nrp)) {
			throw new GestionPatronalBusinessException("El NRP no puede ser nulo o vacío");
		}

		if (nrp.length() < 10) {
			throw new GestionPatronalBusinessException("El NRP debe contener al menos 10 caracteres");
		}

		return entity.obtenerFechaDespliegue(nrp);
	}
   
}

