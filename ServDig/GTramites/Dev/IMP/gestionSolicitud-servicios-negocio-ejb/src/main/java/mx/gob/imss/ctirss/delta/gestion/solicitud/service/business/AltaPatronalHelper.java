package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.domicilio.AsentamientoNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.VialidadesNoLocalizadasException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.AltaPatronalHelperRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.MunicipioIMSS;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteMoral;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sindo.MovimientoPatronalType;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sindo.util.MovimientoPatronalTypeBuilder;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name="altaPatronalHelper", mappedName="altaPatronalHelper")
public class AltaPatronalHelper
    implements AltaPatronalHelperRemote, AltaPatronalHelperLocal {

    private static final Logger log = LoggerFactory.getLogger(AltaPatronalHelper.class);

    private static final String ARGUMENTO_AUDITORIA = "02";

    private static final String ARGUMENTO_DEFAULT = "00";

    @EJB
	PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
	@EJB
	PersonaMoralBusinessRemote personaMoralServiceBusiness;
	@EJB
	DomicilioServiceBusinessRemote domicilioService;


    public MovimientoPatronalType createMovimientoPatronalAlta(Solicitud solicitud) {
    	TramiteSujetoObligado tso = null;
    	for(Tramite tramite : solicitud.getTramites()){
    		if(tramite instanceof TramiteSujetoObligado)
    			tso = (TramiteSujetoObligado)tramite;
    	}
    	System.err.println("createMovimientoPatronalAlta tso: "+tso);
    	Persona persona = obtenerPersona(solicitud);
        SujetoObligado sujetoObligado = tso.getSujetoObligado();

        return creaMovimiento(sujetoObligado, persona);
    }

    private String numeroFolio(Subdelegacion subdelegacion) {
        String diaJuliano = String.format("%tj", System.currentTimeMillis());
        String folioPrveio = subdelegacion.getClave().trim() + diaJuliano;
        String nuevoFolio = subdelegacion.getClave().trim() + "411";
        log.info("Folo previo: {}", folioPrveio);
        log.info("Nuevo folio: {}", nuevoFolio);
        return nuevoFolio;
    }

    private String nombrePatron(Fisica fisica) {
        StringBuilder nombrePersona = new StringBuilder(fisica.getNombre())
                .append(" ")
                .append(fisica.getPrimerApellido());

        if (fisica.getSegundoApellido() != null) {
            nombrePersona.append(" ")
                    .append(fisica.getSegundoApellido());
        }

        return nombrePersona.toString()
                .replaceAll("[\u00F1\u00D1]", "#");//no enies, cambiarlas por '#'
    }

    private String safeNull(String nullablestring) {
        if (nullablestring == null)
            return "";
        else if(nullablestring.equalsIgnoreCase("NULL"))
        	return "";

        return nullablestring;
    }


    private String safeNull(Number nullable) {
        if (nullable == null) {
            return "";
        }
        return nullable.toString();
    }

    private Persona obtenerPersona(Solicitud solicitud){
    	Tramite tramiteModificacionDatosGenerales=null;
		Tramite tramiteAltaSRT=null;
		Persona persona=null;
		for(Tramite tramite : solicitud.getTramites()){
			TipoTramiteEnum tipoTramite = TipoTramiteEnum.obternerEnumById(tramite.getTipoTramite().getIdTipoTramite());
			log.error("Tipo Tramite en solicitud de Alta: "+tipoTramite);
			switch(tipoTramite){
				case ALTA_SRT:
					tramiteAltaSRT = tramite;
					break;
				case ALTA_SRT_PM:
					tramiteAltaSRT = tramite;
					break;
				case ACTUALIZACION_DATOS_GENERALES:
					tramiteModificacionDatosGenerales=tramite;
					break;
				case ACTUALIZACION_DENOMINACION_SOCIAL://TODO eliminar cuando se elimine del tipo de tramite
					tramiteModificacionDatosGenerales=tramite;
					break;
				case ACTUALIZACION_DATOS_USUARIO:
					tramiteModificacionDatosGenerales=tramite;
					break;
				default:
					log.error("Tipo de tramite invalido");
			}

		}

		if(tramiteModificacionDatosGenerales!=null){
			if(tramiteModificacionDatosGenerales instanceof TramiteFisica){
				TramiteFisica tf = (TramiteFisica)tramiteModificacionDatosGenerales;
				ICADatosRespuesta datosICA = tf.getDatosICA();
				datosICA = personaFisicaServiceBusiness.integrarCambios(datosICA);
				persona=datosICA.getPersonaFisicaIMSS();
			}else if(tramiteModificacionDatosGenerales instanceof TramiteMoral){
				TramiteMoral tm = (TramiteMoral)tramiteModificacionDatosGenerales;
				ICADatosRespuesta datosICA = tm.getDatosICA();
				datosICA = personaMoralServiceBusiness.integrarCambios(datosICA);
				persona=datosICA.getPersonaMoralIMSS();
			}
		}else{
			TramiteSujetoObligado tso = (TramiteSujetoObligado)tramiteAltaSRT;
			SujetoObligado sujetoObligado=tso.getSujetoObligado();
			boolean isFisica = sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA);

	        if(isFisica)
	        	persona = sujetoObligado.getFisica();
	        else if(!isFisica)
	        	persona = sujetoObligado.getMoral();

		}

		return persona;
    }


    public MovimientoPatronalType creaMovimiento(SujetoObligado sujetoObligado, Persona persona){

    	Subdelegacion subdelegacion = sujetoObligado.getSubdelegacion();
        System.err.println("createMovimientoPatronalAlta subdelegacion: "+subdelegacion);
        Delegacion delegacion = subdelegacion.getDelegacion();
        System.err.println("createMovimientoPatronalAlta delegacion: "+delegacion);
        boolean isFisica = sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA);

        if(persona==null)
        	persona = sujetoObligado.getFisica()!=null ? sujetoObligado.getFisica() : sujetoObligado.getMoral();


        Date fecEfecto = sujetoObligado.getClasificacion().getFecEfecto();
        MunicipioIMSS municipio = sujetoObligado.getMunicipioIMSS();

        Modalidad modalidad = sujetoObligado.getModalidad();
        String numModalidad=null;
        Integer tipoEmision=0;
        Integer tipoPago=0;
        Integer subrogacionServicios=0;
        if(modalidad != null)
        	numModalidad=modalidad.getNumModalidad();

        if(numModalidad !=null && (numModalidad.equals("10") || numModalidad.equals("13"))){
        	tipoEmision=0;
        	if(numModalidad.equals("10") ){
        		subrogacionServicios=0; //urbanos
            	if(isFisica && persona.getIndRIF()!=null && persona.getIndRIF())
            		tipoPago=0;
        	}else if(numModalidad.equals("13")){
        		subrogacionServicios=1;
        	}
        }else if(numModalidad!=null && numModalidad.equals("34")){
    		tipoPago=3;
    		tipoEmision=1;
    	}


        CodigoPostal codigoPostal = null;
        Localidad localidad = null;
        String domicilioCompleto = "";
        StringBuffer localidadSindo = new StringBuffer();
        String calle = "";
        String localidaAMandar  = "";
		if(sujetoObligado.getClasificacion().getIndRegPatClase()!=null &&
				sujetoObligado.getClasificacion().getIndRegPatClase().intValue()==1){

	    	try {
	    		DomicilioFiscal domicilioFiscal = domicilioService.consultarDomicilioFiscalPersona(persona);
	    		localidad = domicilioFiscal.getAsentamiento().getLocalidad();
	    		codigoPostal = domicilioFiscal.getCodigoPostal();
	    		calle = StringUtils.isNotBlank(domicilioFiscal.getCalle()) ? domicilioFiscal.getCalle(): domicilioFiscal.getVialidadPrimaria().getNombre() ;

		        StringBuffer domicilioStr = new StringBuffer(calle)//TODO revisar si el origen del nombre de calle no es domicilio.getVialidadPrimaria().getNombre()
                	.append(" ").append(safeNull(domicilioFiscal.getNumExterior1()));

		        if(domicilioFiscal.getNumExteriorAlf()!=null)
                	domicilioStr.append(" ").append(safeNull(domicilioFiscal.getNumExteriorAlf()));
                if(domicilioFiscal.getNumInterior()!=null)
                	domicilioStr.append(" ").append(safeNull(domicilioFiscal.getNumInterior()));
                if(domicilioFiscal.getNumInteriorAlf()!=null)
                	domicilioStr.append(" ").append(safeNull(domicilioFiscal.getNumInteriorAlf()));
                if(domicilioFiscal.getAsentamiento()!=null && domicilioFiscal.getAsentamiento().getNombre()!=null)
                	domicilioStr.append(" ").append(safeNull(domicilioFiscal.getAsentamiento().getNombre()));

                domicilioStr.toString().replaceAll("[\u00F1\u00D1]", "#");
                domicilioCompleto = domicilioStr.toString().toUpperCase();
                localidadSindo.append(localidad.getMunicipio().getNombre()).append(" ")
                	.append(localidad.getMunicipio().getEntidadFederativa().getNombre());
	    	} catch (DomicilioNoLocalizadoException e) {
				e.printStackTrace();
			}
		}else{
			boolean calleCatalogo = false;
	        Domicilio domicilio = sujetoObligado.getCntroTrabajo();
	        localidad = domicilio.getAsentamiento().getLocalidad();

	        localidaAMandar = domicilio.getAsentamiento().getNombre() + " "
	        + localidad.getMunicipio().getNombre()+ "" + localidad.getMunicipio().getEntidadFederativa().getNombre();

	        //Si el municipio, el estado o la colonia traen caracteres raros se vuelve a consultar la informacion de BD
	        if(contieneCaracteresRaros(localidaAMandar)) {
	        	log.debug("El municipio, estado o colonia tienen caracteres raros");
	        	try {
					Asentamiento asentamiento = domicilioService.getAsentamiento(domicilio.getAsentamiento());
					domicilio.setAsentamiento(asentamiento);
					localidad = asentamiento.getLocalidad();
				} catch (AsentamientoNoLocalizadoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DomicilioNoLocalizadoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	        } else {
	        	log.debug("El municipio, estado o colonia NO tienen caracteres raros:  " + localidaAMandar);
	        }

	        codigoPostal = domicilio.getCodigoPostal();
	        //Verificamos si el nombre de la calle viene de catalogo
	        calleCatalogo = StringUtils.isBlank(domicilio.getCalle());
	        //Si la calle viene de catalogo y tiene caracteres extranos la consultamos de BD
	        if(calleCatalogo && contieneCaracteresRaros(domicilio.getVialidadPrimaria().getNombre())) {
	        	log.debug("La calle contiene caracteres raros " + domicilio.getVialidadPrimaria().getNombre());
	        	try {
					Vialidad vialidadPrimaria = domicilioService.getVialidad(domicilio.getVialidadPrimaria());
					domicilio.setVialidadPrimaria(vialidadPrimaria);
				} catch (VialidadesNoLocalizadasException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	        } else if (calleCatalogo){
	        	log.debug("La calle NO contiene caracteres raros " + domicilio.getVialidadPrimaria().getNombre());
	        } else if(!calleCatalogo){
	        	domicilio.setCalle(convertirAcentos(domicilio.getCalle()));
	        }

	        calle = !calleCatalogo ? domicilio.getCalle(): domicilio.getVialidadPrimaria().getNombre();

	        StringBuffer domicilioStr = new StringBuffer(calle)//TODO revisar si el origen del nombre de calle no es domicilio.getVialidadPrimaria().getNombre()
	                .append(" ").append(safeNull(domicilio.getNumExterior1()));
	        if(domicilio.getNumExteriorAlf()!=null)
	        	domicilioStr.append(" ").append(safeNull(domicilio.getNumExteriorAlf()));
	        if(domicilio.getNumInterior()!=null)
	        	domicilioStr.append(" ").append(safeNull(domicilio.getNumInterior()));
	        if(domicilio.getNumInteriorAlf()!=null)
	        	domicilioStr.append(" ").append(safeNull(domicilio.getNumInteriorAlf()));
	        if(domicilio.getAsentamiento()!=null && domicilio.getAsentamiento().getNombre()!=null)
	        	domicilioStr.append(" ").append(safeNull(domicilio.getAsentamiento().getNombre()));

	        domicilioStr.toString().replaceAll("[\u00F1\u00D1]", "#");
	        domicilioCompleto = domicilioStr.toString().toUpperCase();
	        localidadSindo.append(localidad.getMunicipio().getNombre()).append(" ")
	        	.append(localidad.getMunicipio().getEntidadFederativa().getNombre());

		}

        Clasificacion clasificacion = sujetoObligado.getClasificacion();
        Fraccion fraccion = clasificacion.getFraccion();
        Boolean auditoria = clasificacion.getAuditoria();

        String curp = isFisica ? ((Fisica)persona).getCurp() : "";
	    String nombrePatron = isFisica ? nombrePatron((Fisica)persona) : ((Moral)persona).getRazonSocial();
	    if(!isFisica){
	    	Moral moral = ((Moral)persona);
	    	if(moral.getTipoSociedad()!=null && StringUtils.isNotBlank(moral.getTipoSociedad().getDescripcionAbreviada()))
	    		nombrePatron+=" "+moral.getTipoSociedad().getDescripcionAbreviada();
	    }

        System.err.println("createMovimientoPatronalAlta CIZ: "+delegacion.getCiz());
        System.err.println("createMovimientoPatronalAlta FOLIO: "+numeroFolio(subdelegacion));
        System.err.println("createMovimientoPatronalAlta curp: "+curp);
        System.err.println("createMovimientoPatronalAlta municipio sindo: "+municipio.getCvecMunicipioSINDO());
        System.err.println("createMovimientoPatronalAlta nombre patron: "+nombrePatron);
        System.err.println("createMovimientoPatronalAlta clase: "+fraccion.getClase().getClave().intValue());
        System.err.println("createMovimientoPatronalAlta fraccion: "+Integer.valueOf(fraccion.getNumFraccion().trim()));
        System.err.println("createMovimientoPatronalAlta subdelegacion: "+Integer.valueOf(subdelegacion.getClave().trim()) + "---");
        System.err.println("createMovimientoPatronalAlta delegacion: "+Integer.valueOf(delegacion.getClave().trim())+ "---");
        System.err.println("createMovimientoPatronalAlta cp: "+codigoPostal.getCodigoPostal());
        System.err.println("createMovimientoPatronalAlta localidad : "+ localidad.getNombre());
        System.err.println("createMovimientoPatronalAlta auditoria: "+auditoria);

        MovimientoPatronalTypeBuilder builder = new MovimientoPatronalTypeBuilder();
        return builder.withDelegacionOrigen(Integer.parseInt(delegacion.getClave().trim()))
                .withCiz(delegacion.getCiz())
                .withSubdelegacionOrigen(Integer.parseInt(subdelegacion.getClave().trim()))
                .withClaveAplicacion(1)
                .withTipoMovimiento(1)
                .withOrigenMovimiento(6)
                .withNumeroFolio(numeroFolio(subdelegacion))
                .withArgumento( (auditoria!=null && auditoria) ? ARGUMENTO_AUDITORIA : ARGUMENTO_DEFAULT )
                .withFechaRecepcion(new Date()) // Verificar si es fecha actual
                .withFechaMovimiento(fecEfecto)
                .withCurp(curp)
                .withSubrogacionServicio(subrogacionServicios) //Servicios Urbanos
                .withClaveMunicipio(municipio.getCvecMunicipioSINDO())
                .withNombrePatron(nombrePatron.trim())
              //se agrego remplace para el domicilio
                .withDomicilioPatron(convertirAcentos(domicilioCompleto.trim()).replaceAll("[\u0028\u0029]", "").replaceAll("´", " ").trim())
                .withCodigoPostal(codigoPostal.getCodigoPostal())
                .withLocalidad(convertirAcentos(localidadSindo.toString().toUpperCase()))
                .withGiro(clasificacion.getGiro())
                .withGrupo(Integer.parseInt(fraccion.getGrupo().getNumGrupo().trim()))
                .withDivision(Integer.parseInt(fraccion.getGrupo().getDivision().getNumDivision().trim()))
                .withClase(fraccion.getClase().getClave().intValue())
                .withFraccion(Integer.valueOf(fraccion.getNumFraccion().trim()))
                .withPsp(0)//Se solicito cambio para que siempre se envie el valor 0 dentro del AYPENT
                .withFechaCambioCla(Integer.parseInt(String.format("%1$ty%1$tm", sujetoObligado.getClasificacion().getFecEfecto()).trim())) //Fecha surte efecto formato AAmm ej: 1309 para septiembre 2013
                .withRfc(persona.getRfc())
                .withTipoPago(tipoPago) //????????????????????????
                .withMesEmi(tipoEmision)  //????????????????????????
                .build();
    }

    private boolean contieneCaracteresRaros(String cadena) {
    	boolean contieneCaracteresRaros = false;
    	 Pattern pat = Pattern.compile("[\u0081\u00C3\u00BF\u00B3\u0083\u00a9\u008d]");
    	 Matcher mat = pat.matcher(cadena);
    	contieneCaracteresRaros = mat.find();
    	System.err.println("La cadena " + cadena + " contiene caracteres raros? " + contieneCaracteresRaros);
    	return contieneCaracteresRaros;
    }

//    private void convertirCadenaAunicode(String cadena) {
//    	System.err.println("la cadena " + cadena + " contiene los siguientes caracteres: ");
//    	char[] caracteres = cadena.toCharArray();
//
//    	for(char c: caracteres) {
//    		String s = String.format ("\\u%04x", (int)c);
//    		System.err.println("El caracter" + c + " su codigo es " + s);
//    	}
//
//    	contieneCaracteresRaros(cadena);
//
//    }
//

    private String convertirAcentos(String cadena) {

    	cadena = cadena.replace("\u0081", "");
    	cadena = cadena.replace("\u00c1", "A");
    	cadena = cadena.replace("\u00C9", "E");
    	cadena = cadena.replace("\u00CD", "I");
    	cadena = cadena.replace("\u00D3", "O");
    	cadena = cadena.replace("\u00DA", "U");

    	cadena = cadena.replace("\u00C0", "A");
    	cadena = cadena.replace("\u00E0", "a");

    	cadena = cadena.replace("\u00C8", "E");
    	cadena = cadena.replace("\u00E8", "e");

    	cadena = cadena.replace("\u00CC", "I");
    	cadena = cadena.replace("\u00EC", "i");

    	cadena = cadena.replace("\u00F2", "o");
    	cadena = cadena.replace("\u00D2", "O");

    	cadena = cadena.replace("\u00D9", "U");
    	cadena = cadena.replace("\u00F9", "u");

    	return cadena;
    }

}

