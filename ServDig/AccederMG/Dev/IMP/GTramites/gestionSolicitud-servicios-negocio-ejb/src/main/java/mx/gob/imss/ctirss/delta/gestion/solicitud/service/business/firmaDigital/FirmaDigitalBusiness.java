package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.firmaDigital;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.FirmaElectronicaSegPortType;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.FirmaElectronicaSegService;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.FirmaSimpleRequestType;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.FirmaSimpleResponseType;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.FirmaXMLSegRequestType;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.FirmaXMLSegResponseType;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.ObjectFactory;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.RegistroSeguimientoRequestType;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.RegistroSeguimientoResponseType;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.ResultadoType;
import mx.gob.imss.ctirss.delta.exception.firma.ErrorEnInvocacionRecursoRemotoException;
import mx.gob.imss.ctirss.delta.exception.firma.FirmaDigitalException;
import mx.gob.imss.ctirss.delta.exception.firma.RecursoRemotoNoDisponibleException;
import mx.gob.imss.ctirss.delta.exception.firma.RegistroPatronalInvalidoEnCertificadoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity.SolicitudFirmaDigitalServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.model.firma.Archivo;
import mx.gob.imss.ctirss.delta.model.firma.PeticionFirmadoSimple;
import mx.gob.imss.ctirss.delta.model.firma.PeticionFirmadoXmlSimple;
import mx.gob.imss.ctirss.delta.model.firma.PeticionGuardadoArchivosFirma;
import mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoSimple;
import mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoXmlSimple;
import mx.gob.imss.ctirss.delta.model.firma.RespuestaGuardadoArchivosFirma;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

@Stateless(mappedName = "firmaDigitalBusiness", name = "firmaDigitalBusiness")
public class FirmaDigitalBusiness extends AbstractServiceBusiness implements
		FirmaDigitalBusinessRemote {

	@EJB
	private ServiciosExternosFirmaDigitalFIELBusinessLocal serviciosExternosFirmaDigitalFIELBusiness;
	@EJB
	private ServiciosExternosFirmaDigitalIMSSBusinessLocal serviciosExternosFirmaDigitalIMSSBusiness;
	@EJB
	private SolicitudFirmaDigitalServiceEntityLocal solicitudFirmaDigitalServiceEntity;

	@Override
	public FirmaElectronica procesarFirmaConFIEL(FirmaElectronica firmaElectronica)
			throws FirmaDigitalException {

		firmaElectronica.setTipoCertificado(ConstantesFirmaDigital.CERTIFICADO_SAT);
		
		// Se valida el certificado
		firmaElectronica = serviciosExternosFirmaDigitalFIELBusiness.validarCertificado(firmaElectronica);
		
		// Se procede a guardar en notaria
		firmaElectronica = serviciosExternosFirmaDigitalFIELBusiness.guardarNotaria(firmaElectronica);
		
		return firmaElectronica;
	}

	@Override
	public FirmaElectronica procesarFirmaConIMSS(FirmaElectronica firmaElectronica)
			throws RegistroPatronalInvalidoEnCertificadoException,
			RecursoRemotoNoDisponibleException,
			ErrorEnInvocacionRecursoRemotoException, FirmaDigitalException {

		/*
		 * Se valida que el registro patronal del certificado y el que se
		 * recibiï¿½ como parï¿½metro sea el mismo
		 */
		// TODO: MASE - Validar RP contra RP del certificado
//		this.serviciosExternosFirmaDigitalIMSSBusiness
//				.validaRegistroPatronaEnCertificado(firmaElectronica);

		// Se valida el certificado
		firmaElectronica = this.serviciosExternosFirmaDigitalIMSSBusiness
				.validarCertificado(firmaElectronica);

		// Se procede a guardar en notaria
		firmaElectronica = this.serviciosExternosFirmaDigitalIMSSBusiness
				.guardarEnNotaria(firmaElectronica);
		
		return firmaElectronica;
	}
	
	/**
	 * 
	 * @param firmaElectronica
	 * @return
	 * @throws FirmaDigitalException
	 */
	@Override
	public FirmaElectronica validarCertificadoFIEL (FirmaElectronica firmaElectronica)
			throws FirmaDigitalException {

		firmaElectronica.setTipoCertificado(ConstantesFirmaDigital.CERTIFICADO_SAT);
		
		// Se valida el certificado
		firmaElectronica = serviciosExternosFirmaDigitalFIELBusiness.validarCertificado(firmaElectronica);
		
		return firmaElectronica;
	}

	/**
	 * 
	 * @param solicitud
	 * @param firma
	 */
	@Override
	public void insertarSolicitudFirmaDigital(Solicitud solicitud,
			FirmaElectronica firma) {
		this.log.debug("Id de la solicitud ---"+ solicitud.getSolicitudId());
		solicitudFirmaDigitalServiceEntity.insertarSolicitudFirmaDigital(solicitud, firma);
	}

	
	
	@Override
	public FirmaElectronica getFirmaElectronica(Solicitud solicitud) {
		FirmaElectronica firma = null;
		firma = solicitudFirmaDigitalServiceEntity.consultarFirmaElectronica(solicitud);
		return firma;
	}

	/**
	 * 
	 * @param cadenaOriginal
	 * @return
	 */
	@Override
	public RespuestaFirmadoSimple getSelloDigital(String cadenaOriginal,String secuenciaNotaria, String rfc) {
		RespuestaFirmadoSimple firma = null;
		
		 try{   
	            String serie = this.getPropiedadDeProperties("serie");
	            PeticionFirmadoSimple peticion = new PeticionFirmadoSimple();
	            
	            String jsonParams = null;
	            
	            peticion.setId_llavefirma(serie);
	            
	            if(secuenciaNotaria != null) {
	            	peticion.setTramite(secuenciaNotaria);
	            } else {
	            	peticion.setAplicacion(this.getPropiedadDeProperties("aplicacion"));
	            	if(rfc == null) {
	            		rfc = this.getPropiedadDeProperties("rfc.imss");
	            	}
	            	peticion.setRfc(rfc);
	            }
	            
	            peticion.setCadenaoriginal(cadenaOriginal);
	            
	            jsonParams = this.convertirAJSON(peticion, PeticionFirmadoSimple.class.getName());
	            
	            log.warn("Serie: " + serie);
	            
                
	            ObjectFactory objf = new ObjectFactory();
	            FirmaSimpleRequestType fsrt = objf.createFirmaSimpleRequestType();
	            fsrt.setJsonParms(jsonParams);
	        	FirmaElectronicaSegService firmaElectronicaSegService = new FirmaElectronicaSegService();
	    		FirmaElectronicaSegPortType firmaElectronicaSegPortType = firmaElectronicaSegService.getFirmaElectronicaSegPortTypePort();
	    		FirmaSimpleResponseType respuestaFirmado= firmaElectronicaSegPortType.firmaSimple(fsrt);
	    		
	    		ResultadoType resultado = respuestaFirmado.getResultado();
	    		if (resultado.getCodigo() != 0){
	            	log.error("Codigo: " + resultado.getCodigo());
	            	log.error("Descripcion: " + resultado.getTexto());               
	            }
	            else {
	            	//log.warn("la respuesta del servicio es: " + respuestaFirmado.getJsonSalida());
	            	firma = (RespuestaFirmadoSimple) this.convertirAObjeto(respuestaFirmado.getJsonSalida(), RespuestaFirmadoSimple.class.getName());
	                log.warn("Secuencia de notaria arrojada por la firma: " + firma.getId());
	            	log.warn("Numero de serie firmante: " + firma.getNoSerie());
	                log.warn("Firma: " + firma.getSello());
	            } 
	        
	        }
	        catch (Exception ex){
	            log.error("ocurrio un error",ex);
	        }
		
		return firma;
	}

	@Override
	public FirmaElectronica convertirRespuestaFirmadoSimple(String cadenaOriginal,RespuestaFirmadoSimple firmado) {
		
		FirmaElectronica firmaElectronica = new FirmaElectronica();
		
        firmaElectronica.setCadenaOriginal(cadenaOriginal);
        firmaElectronica.setReciboNotarial(firmado.getTramite());
        firmaElectronica.setSecuenciaNotaria(firmado.getTramite());
        firmaElectronica.setSerialCertificado(firmado.getNoSerie());
        firmaElectronica.setRecibo(firmado.getSello());
        firmaElectronica.setUrlAcuseFirma("");
        firmaElectronica.setIniciaVigenciaCertificado(new Date());
        firmaElectronica.setFinVigenciaCertificado(new Date());
		
        return firmaElectronica;
	}

	/**
	 * 
	 * @param solicitud
	 * @return
	 */
	@Override
	public Map<String, String> getCadenaOriginalYSelloDigital(
			Solicitud solicitud, Persona persona, String rp, String nss) {
		Map<String,String> cadenas = null;
		String cadenaOriginal = null;
		RespuestaFirmadoSimple selloDigital = null;
		
		if(solicitud != null) {
			cadenas = new HashMap<String, String>();
			cadenaOriginal = this.generarCadenaOriginal(solicitud,persona,rp,nss);
			//log.error("La cadena original es: " + cadenaOriginal);
			selloDigital = this.getSelloDigital(cadenaOriginal, solicitud.getSecuenciaDeNotaria(), persona.getRfc());
			
			cadenas.put("cadenaOriginal", cadenaOriginal);
			if(selloDigital != null) {
				cadenas.put("selloDigital", selloDigital.getSello());
				cadenas.put("secuenciaNotaria",selloDigital.getId());
				cadenas.put("numeroSerie", selloDigital.getNoSerie());
				cadenas.put("tramite", selloDigital.getTramite());
			}
		}
		
		return cadenas;
	}

	/**
	 * 
	 * @param secuenciaNotaria
	 * @param archivo
	 */
	@Override
	public void guardarArchivoFirmado(String secuenciaNotaria, Archivo archivo){
		
		String jsonParams= "";
		ObjectMapper mapper = new ObjectMapper();
		PeticionGuardadoArchivosFirma peticion = new PeticionGuardadoArchivosFirma();
		
		List<Archivo> archivos = new ArrayList<Archivo>();
		archivos.add(archivo);
		
		peticion.setTramite(secuenciaNotaria);
		peticion.setArchivos(archivos);
		
		try {
			jsonParams = mapper.writeValueAsString(peticion);
		} catch (JsonGenerationException e) {
			log.error("Error en el parseo a JSON", e);
			return;
		} catch (JsonMappingException e) {
			log.error("Error en el parseo a JSON", e);
			return;
		} catch (IOException e) {
			log.error("Error en el parseo a JSON", e);
			return;
		}
		
		//log.warn("Asi quedo el archivo objeto JSON: " + jsonParams);
		
		ObjectFactory of = new ObjectFactory();
		log.error("se construye el objeto del WS" + new Date());
		RegistroSeguimientoRequestType peticionSeguimiento = of.createRegistroSeguimientoRequestType();
		peticionSeguimiento.setJsonParms(jsonParams);
		
		FirmaElectronicaSegService firmaElectronicaSegService = new FirmaElectronicaSegService();
		FirmaElectronicaSegPortType firmaElectronicaSegPortType = firmaElectronicaSegService.getFirmaElectronicaSegPortTypePort();
		log.error("pase la creacion de la instancia del WS" + new Date());
		RegistroSeguimientoResponseType respuestaPeticion = firmaElectronicaSegPortType.registroSeguimiento(peticionSeguimiento);
		ResultadoType resultado = respuestaPeticion.getResultado();
		log.error("concluye la invocacion para el salvado en el WS" + new Date());
		
		if(resultado.getCodigo() == 0) {
			try {
				RespuestaGuardadoArchivosFirma respuestaGuardado = mapper.readValue(respuestaPeticion.getJsonSalida(), RespuestaGuardadoArchivosFirma.class);
				log.warn("Id del documento: "+ respuestaGuardado.getArchivos().get(0).getId());
				log.warn("Nombre del archivo: " + respuestaGuardado.getArchivos().get(0).getNombre());
			} catch (JsonParseException e) {
				log.error("Error en el parseo a JSON", e);
			} catch (JsonMappingException e) {
				log.error("Error en el parseo a JSON", e);
			} catch (IOException e) {
				log.error("Error en el parseo a JSON", e);
			}
			log.warn("El archivo ha sido guardado correctamente");
		} else {
			log.error("Código: " + resultado.getCodigo());
        	log.error("Descripción: " + resultado.getTexto());
		}
		
		
	}
	
	
	
	/**
	 * 
	 * @param secuenciaNotaria
	 * @param nombreArchivo
	 * @param archivo
	 */
	@Override
	public void guardarArchivoFirmado(String secuenciaNotaria, String nombreArchivo, byte[] archivo) {
		archivo = Base64.encodeBase64(archivo);
		String archivoBase64 = new String(archivo);
		
		this.guardarArchivoFirmado(secuenciaNotaria, nombreArchivo, archivoBase64);
		
	}

	/**
	 * 
	 * @param secuenciaNotaria
	 * @param nombreArchivo
	 * @param archivoBase64
	 */
	@Override
	public void guardarArchivoFirmado(String secuenciaNotaria, String nombreArchivo,
			String archivoBase64) {
		
		Archivo archivo = new Archivo();
		archivo.setNombre(nombreArchivo);
		archivo.setBuffer(archivoBase64);
		
		this.guardarArchivoFirmado(secuenciaNotaria, archivo);
		
	}

	private String convertirAJSON(Object object, String clase) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String jsonParams = null;
		
		try {
			jsonParams = mapper.writeValueAsString((Class.forName(clase).cast(object)));
		} catch (JsonGenerationException e) {
			log.error("Error en el parseo a JSON", e);
			throw new Exception("No fue posible generar el json");
		} catch (JsonMappingException e) {
			log.error("Error en el parseo a JSON", e);
			throw new Exception("No fue posible generar el json");
		} catch (IOException e) {
			log.error("Error en el parseo a JSON", e);
			throw new Exception("No fue posible generar el json");
		}
		
		return jsonParams;
	}
	
	
	private Object convertirAObjeto(String json, String clase) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Object object = null;
		//log.warn("El string del json a convertir es : " + json + " y se convertira a : " + clase);
		try {
			object = mapper.readValue(json, Class.forName(clase));
		} catch (JsonParseException e) {
			log.error("Error en el parseo a JSON", e);
			throw new Exception("No fue posible generar el json");
		} catch (JsonMappingException e) {
			log.error("Error en el parseo a JSON", e);
			throw new Exception("No fue posible generar el json");
		} catch (IOException e) {
			log.error("Error en el parseo a JSON", e);
			throw new Exception("No fue posible generar el json");
		}
		log.warn("Se ha generado correctamente el objeto a partir de un string json");
		
		return Class.forName(clase).cast(object);
	}
	
	private String getPropiedadDeProperties(String propiedad) {
		String propertie = null;
		Properties properties = new Properties();
		
		try {
			InputStream is = new ClassPathResource("firmaDigital.properties").getInputStream();
			properties.load(is);
			is.close();
			
			propertie = properties.getProperty(propiedad);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		
		
		return propertie;
	}
	
	public String generarCadenaOriginal(Solicitud solicitud, Persona persona, String rp, String nss) {
		Locale locMEX = new Locale("es", "MX");
		DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss", locMEX);
		StringBuffer contenidoAFirmar = new StringBuffer();

		// Inicio
		contenidoAFirmar.append("||");
		contenidoAFirmar.append("Invocante:portalimssdigital|");

		// Denominacion del Tramite o servicio
		contenidoAFirmar.append("Tramite:");
		if(!solicitud.getTramites().isEmpty()){
			if(solicitud.getTipoSolicitud().getIdTipoSolicitud().longValue() == TipoSolicitudEnum.ALTA_PATRONAL.getValor().longValue() ){
				for(Tramite tr : solicitud.getTramites()){
					if(tr.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ALTA_SRT_PM.getCodigo().intValue() ||
							tr.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ALTA_SRT.getCodigo().intValue()){
							contenidoAFirmar.append(tr.getTipoTramite().getDescripcion()).append("|");
							break;
					}
				}
				
			}
			else{
				contenidoAFirmar.append(solicitud.getTramites().get(0).getTipoTramite().getDescripcion()).append("|");
			}
		}
		// Fecha Electronica
		String strFechaElectronica = dateFormat.format(solicitud.getFechaConclusion() != null ? solicitud.getFechaConclusion() : new Date());
		contenidoAFirmar.append("Fecha:");
		contenidoAFirmar.append(strFechaElectronica).append("|");

		if(solicitud.getNoFolioSolicitud() != null && solicitud.getNoFolioSolicitud().trim().length() > 0){
			// Folio
			contenidoAFirmar.append("Folio:");
			contenidoAFirmar.append(solicitud.getNoFolioSolicitud()).append("|");
		}
		
		// RFC
		if(StringUtils.isNotBlank(persona.getRfc())) {
			contenidoAFirmar.append("RFC:");
			contenidoAFirmar.append(persona.getRfc()).append("|");
		}

		// Nombre, denominacion o razon social del interesado (y en su caso el de su representante o persona autorizada)
		StringBuffer sbnombre = new StringBuffer();
		if (persona instanceof Fisica) {
			sbnombre.append(((Fisica)persona).getNombre()).append(" ");
			sbnombre.append(((Fisica)persona).getPrimerApellido()).append(" ");
			if (StringUtils.isNotBlank(((Fisica) persona).getSegundoApellido())) {
				sbnombre.append(((Fisica) persona).getSegundoApellido());
			}
		} else {
			Moral moral = ((Moral)persona);
			sbnombre.append(moral.getRazonSocial());
			if(moral.getTipoSociedad()!=null
					&&  StringUtils.isNotBlank(moral.getTipoSociedad().getDescripcionAbreviada()))
				sbnombre.append(" ").append(moral.getTipoSociedad().getDescripcionAbreviada());
			
		}
		contenidoAFirmar.append("Nombre o Razon Social:");
		contenidoAFirmar.append(sbnombre.toString()).append("|");

		if (persona instanceof Fisica) {
			contenidoAFirmar.append("CURP:");
			contenidoAFirmar.append(((Fisica)persona).getCurp()).append("|");
		} 
		
		if(rp != null && rp.trim().length() > 0) {
			// Registro Patronal(No aplica)
			contenidoAFirmar.append("Registro Patronal:");
			contenidoAFirmar.append((rp != null ? rp : "") + "|");
		}

		if(nss != null && nss.trim().length() > 0){
			// NSS(No aplica)
			contenidoAFirmar.append("Numero de Seguridad Social:");
			contenidoAFirmar.append((nss != null ? nss : "") + "|");
		}
		
		contenidoAFirmar.append("|");
		
		return contenidoAFirmar.toString();
	}
	
	@Override
	public RespuestaFirmadoXmlSimple firmarXML(String xml, String nombreArchivo) {

		this.log.debug("Se va a firmar XML con los siguientes datos: XML ["
				+ xml + "], nombreArchivo [" + nombreArchivo + "]");
		
		RespuestaFirmadoXmlSimple firma = null;
		
		try {
			String serie = this.getPropiedadDeProperties("serie");
			PeticionFirmadoXmlSimple peticion = new PeticionFirmadoXmlSimple();

			peticion.setNombre(nombreArchivo);
			peticion.setAplicacion(this.getPropiedadDeProperties("aplicacion"));
			peticion.setId_llavefirma(serie);
			peticion.setXmls(new String(Base64.encodeBase64(xml.getBytes())));
//			peticion.setNumSerie("");
			peticion.setRfc(this.getPropiedadDeProperties("rfc.imss"));

			this.log.debug("Se va a generar JSON de petición para la firma de XML");
			
			String jsonParams = this.convertirAJSON(peticion,
					PeticionFirmadoXmlSimple.class.getName());

			ObjectFactory objf = new ObjectFactory();
			
			FirmaXMLSegRequestType request = objf.createFirmaXMLSegRequestType();
			request.setJsonParms(jsonParams);
			
			FirmaElectronicaSegService firmaElectronicaSegService = new FirmaElectronicaSegService();
			FirmaElectronicaSegPortType firmaElectronicaSegPortType = firmaElectronicaSegService
					.getFirmaElectronicaSegPortTypePort();
			
			//this.log.debug("JSON de entrada para firmado de XML -> " + request.getJsonParms());
			
			FirmaXMLSegResponseType response = firmaElectronicaSegPortType
					.firmaXML(request);

			ResultadoType resultado = response.getResultado();
			
			if (resultado.getCodigo() != 0) {
				this.log.error("Error al firmar el XML codigo ["
						+ resultado.getCodigo() + "], descripcion ["
						+ resultado.getTexto() + "]");
				
				firma = new RespuestaFirmadoXmlSimple();
				firma.setExito(resultado.getCodigo());
				firma.setClaveError(resultado.getCodigo());
				firma.setDescripcion(resultado.getTexto());
				
			} else {
				//this.log.debug("Firmado de XML exitoso, la respuesta del servicio es: "
				//		+ response.getJsonSalida());
				
				this.log.debug("Se va a transformar el JSON de respuesta del firmado XML a objeto de negocio");
				
				firma = (RespuestaFirmadoXmlSimple) this.convertirAObjeto(response.getJsonSalida(),
						RespuestaFirmadoXmlSimple.class.getName());
				
				this.log.debug("Tranformación exitosa [JSON -> obj. negocio]");
				
				firma.setExito(0);
				firma.setClaveError(null);
				firma.setDescripcion(null);
			}

		} catch (Exception ex) {
			log.error("Ocurrio un error inespeardo al firmar el XML", ex);
			firma = new RespuestaFirmadoXmlSimple();
			firma.setExito(100);
			firma.setClaveError(100);
			firma.setDescripcion(ex.getMessage());
		}

		return firma;

	}
}
