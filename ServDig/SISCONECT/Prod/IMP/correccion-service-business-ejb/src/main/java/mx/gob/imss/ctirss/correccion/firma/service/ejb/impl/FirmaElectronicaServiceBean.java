/**
 * 
 */
package mx.gob.imss.ctirss.correccion.firma.service.ejb.impl;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.FirmaElectronicaSegPortType;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.FirmaElectronicaSegService;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.FirmaSimpleRequestType;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.FirmaSimpleResponseType;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.ObjectFactory;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.RegistroSeguimientoRequestType;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.RegistroSeguimientoResponseType;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.ResultadoType;
import mx.gob.imss.ctirss.correccion.bean.RespuestaFirmadoSimple;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTramiteMensajes;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtTramitePresentado;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.ConfigTramite;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.ParamSolicitudTramite;
import mx.gob.imss.ctirss.correccion.firma.service.ejb.FirmaElectronicaServiceRemote;
import mx.gob.imss.ctirss.correccion.firma.service.interfaces.Archivo;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractDocumentoElectronicoModel;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.service.AbstractService;
import mx.gob.imss.ctirss.correccion.framework.exception.comun.ServicioRemotoNoDisponibleException;
import mx.gob.imss.ctirss.correccion.framework.exception.firma.CertificadoInvalidoException;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.CatalogoDAOLocal;
import mx.gob.imss.ctirss.correccion.session.TipoCertificado;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.enums.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.idse.pkcs7.webservices.IWSFirmaDigital;
import mx.gob.imss.idse.pkcs7.webservices.IWSFirmaDigitalProxy;
import mx.gob.imss.idse.pkcs7.webservices.Pkcs7Bean;
import mx.gob.imss.idse.pkcs7.webservices.Pkcs7BeanTipoCertificado;
import mx.gob.imss.idse.pkcs7.webservices.Pkcs7BeanTipoNotaria;
import mx.gob.imss.idse.pkcs7.webservices.RespuestaAuthenticateBean;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;


//import com.forgerock.opendj.util.Validator;
/**
 * @author vaguirre
 * 
 */
@Stateless(name = "firmaElectronicaService", mappedName = "firmaElectronicaService")
public class FirmaElectronicaServiceBean<T extends AbstractModel> extends AbstractService implements
		FirmaElectronicaServiceRemote {
	
	
	@EJB CatalogoDAOLocal<T> daoDelta;
	
	/**
	 * 
	 */
	private final static Logger logger = Logger
			.getLogger(FirmaElectronicaServiceBean.class);
	/**
	 * Respuesta exitosa de parte del servicio web del SAT
	 */
	private int CODIGO_RESPUESTA_SAT_EXITO = 1;
	/**
	 * Respuesta erronea de parte del servicio web del SAT
	 */
	private int CODIGO_RESPUESTA_SAT_ERROR = 2;

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.imss.ctirss.correccion.firma.service.interfaces.
	 * FirmaElectronicaService
	 * #validarCertificado(mx.gob.imss.ctirss.correccion.framework
	 * .base.model.AbstractDocumentoElectronicoModel)
	 */
	@Override
	public AbstractDocumentoElectronicoModel validarCertificado(
			AbstractDocumentoElectronicoModel doc)
			throws ServicioRemotoNoDisponibleException,
			CertificadoInvalidoException {

		try {

			if (doc.getTipoCertificado().ordinal() == TipoCertificado.SAT
					.ordinal()) {

				final Pkcs7Bean pkcs7Bean = new Pkcs7Bean();
				pkcs7Bean.setPkcs7(doc.getFirmaElectronica());

				pkcs7Bean.setTipoCertificado(Pkcs7BeanTipoCertificado.SAT);
				pkcs7Bean.setTipoNotaria(Pkcs7BeanTipoNotaria.IDSE);

				IWSFirmaDigital digital = new IWSFirmaDigitalProxy();
				RespuestaAuthenticateBean respuestaAuthenticateBean = digital
						.authenticate(pkcs7Bean);
				logger.debug(respuestaAuthenticateBean.getCodRespuesta());
				logger.debug(respuestaAuthenticateBean.getCadenaOriginal());

				if (respuestaAuthenticateBean.getCodRespuesta() == CODIGO_RESPUESTA_SAT_EXITO) {
					logger.debug("OK -> folio :: "
							+ respuestaAuthenticateBean.getSecuencia());
					doc.setFolioNotarial(respuestaAuthenticateBean
							.getSecuencia());
				} else {
					if (respuestaAuthenticateBean.getCodRespuesta() == CODIGO_RESPUESTA_SAT_ERROR) {
						logger.warn(respuestaAuthenticateBean.getMsgRespuesta());
						throw new CertificadoInvalidoException(
								respuestaAuthenticateBean.getMsgRespuesta());
					}
				}
			} else {
				// TODO VAP agregar la validaci—n para certificado IDSE
				//necesito autenticar la caducida del certificado
				//obtener el folio
				logger.error("Flujo no implementado");
				throw new ServicioRemotoNoDisponibleException();
			}
			return doc;
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			throw new ServicioRemotoNoDisponibleException();
		}

	}

	@Override
	public void guardarTramitePresentado(CrtTramitePresentado tramitePresentado) {
		// TODO Auto-generated method stub
			
		daoDelta.agrega((T) tramitePresentado);
		
	//		logger.info("ejecutando dao tramite presentado");
//		CrtTramitePresentado t=new CrtTramitePresentado();
//		t.setCveMensaje(1);
//		t.setCveTramite(1);
//		t.setCveSolcorr(45L);
//		t.setIdTramiteRefNotaria("asdfasdfasdf-asdfasdfasd");
//		t.setCveUsuarioCurp("HEAJ870608");
//		t.setFecFechaReg(Calendar.getInstance().getTime());		
//		daoDelta.agrega((T) t);
		

	}

	@Override
	public CrcTramiteMensajes recuperaMensaje(CrcTramiteMensajes mensaje) {
		// TODO Auto-generated method stub
		StringBuilder query=new StringBuilder();
		query.append("FROM  CrcTramiteMensajes tram where tram.cveTramite="+mensaje.getCveTramite()+" and tram.vigencia=1");
		logger.info("Query "+"FROM  CrcTramiteMensajes tram where tram.cveTramite="+mensaje.getCveTramite()+" and tram.vigencia=1");
		List<CrcTramiteMensajes> lista=(List<CrcTramiteMensajes>) daoDelta.consultaLibrePorClave(0L, query.toString());
		if(lista!=null && !lista.isEmpty()){
			return lista.get(0);
		}
		return null;
	}

	
	public CrtTramitePresentado buscaTramitePresentado(CrtTramitePresentado crtTramitePresentado, UserSession usrSession){
		int idRol = (int)usrSession.getCveRol();
		String regPatronal = usrSession.getRegistroPatronal();
		if(regPatronal != null){
			regPatronal = regPatronal.substring(0,regPatronal.length()-1);
		}
		StringBuilder query = new StringBuilder();
		query.append(" FROM CrtSolicitudcorr A ");
		query.append(" WHERE A.nuFolio = '" + crtTramitePresentado.getNuFolio() + "' ");
		List crtSolicitud = daoDelta.consultaLibrePorClave(0L, query.toString());
		CrtSolicitudcorr solicitud = new CrtSolicitudcorr();
		if(crtSolicitud.size()!=0){
			solicitud= (CrtSolicitudcorr)crtSolicitud.get(0);
		}
		query = new StringBuilder();
		query.append(" FROM CrtTramitePresentado B ");
		query.append(" WHERE B.cveSolcorr = " + solicitud.getCveSolicitudCorr());
		query.append(" AND B.cveTramite = " + crtTramitePresentado.getCveTramite());
		
//		if(idRol == ConstantesBusiness.ROL_USER_INTERNET){
//			query.append(" AND B.desRegPatronal = '" + regPatronal +"'");
//		}
		List crtTramitesPresentados = daoDelta.consultaLibrePorClave(0L, query.toString());
		if(crtTramitesPresentados.size() != 0){
			crtTramitePresentado = (CrtTramitePresentado)crtTramitesPresentados.get(0);
		}else{
			crtTramitePresentado = null;
		}
		return crtTramitePresentado;
	}

	@Override
	public RespuestaFirmadoSimple getSelloDigital(CrtSolicitudcorr solicitud) {
		

		
		
		String selloDigital = "";
		String idTramite=null;
		if(solicitud.getIdTramite()!=null){
			idTramite=solicitud.getIdTramite();
		}
		
		RespuestaFirmadoSimple res=this.getSelloDigital(solicitud.getSelloIMSS(),null,null);
		if(res!=null){
			logger.info("Se recupera el sello ");
			selloDigital=res.getSello();
		}
		
		return res;
	}
	
	private String getSerie() {
		String serie = null;
		Properties properties = new Properties();
		
		try {
			InputStream is = new ClassPathResource("firmaDigital.properties").getInputStream();
			properties.load(is);
			is.close();			
			serie = properties.getProperty("serie");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		
		
		return serie;
	}

	@Override
	public RespuestaFirmadoSimple getSelloDigital(String cadenaOriginal,
			String secuenciaNotaria, String rfc) {
		 RespuestaFirmadoSimple firma = null;
		 System.out.println("Inicializando Recuperacion de sello para correccion patronal 2");
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
           
            System.out.println("Los parametros para la firma son: " + jsonParams);
            System.out.println("Serie: " + serie);
           
       
            ObjectFactory objf = new ObjectFactory();
            FirmaSimpleRequestType fsrt = objf.createFirmaSimpleRequestType();
            fsrt.setJsonParms(jsonParams);
                FirmaElectronicaSegService firmaElectronicaSegService = new FirmaElectronicaSegService();
                FirmaElectronicaSegPortType firmaElectronicaSegPortType = firmaElectronicaSegService.getFirmaElectronicaSegPortTypePort();

                FirmaSimpleResponseType respuestaFirmado= firmaElectronicaSegPortType.firmaSimple(fsrt);
               
                ResultadoType resultado = respuestaFirmado.getResultado();
                if (resultado.getCodigo() != 0){
                	System.out.println("Codigo: " + resultado.getCodigo());
                	System.out.println("Descripcion: " + resultado.getTexto());              
            }
            else {
            	System.out.println("la respuesta del servicio es: " + respuestaFirmado.getJsonSalida());
                firma = (RespuestaFirmadoSimple) this.convertirAObjeto(respuestaFirmado.getJsonSalida(), RespuestaFirmadoSimple.class.getName());

                System.out.println("Secuencia de notaria arrojada por la firma: " + firma.getId());
                System.out.println("Numero de serie firmante: " + firma.getNoSerie());
                System.out.println("Firma: " + firma.getSello());
            }
       
        }
        catch (Exception ex){
            logger.error("ocurrio un error",ex);
            ex.printStackTrace();
        }
       
        return firma; 
	}
	
	  private String convertirAJSON(Object object, String clase) throws Exception {
          ObjectMapper mapper = new ObjectMapper();
          String jsonParams = null;
         
          try {
                  jsonParams = mapper.writeValueAsString((Class.forName(clase).cast(object)));
          } catch (JsonGenerationException e) {
                  logger.error("Error en el parseo a JSON", e);
                  throw new Exception("No fue posible generar el json");
          } catch (JsonMappingException e) {
        	  logger.error("Error en el parseo a JSON", e);
                  throw new Exception("No fue posible generar el json");
          } catch (IOException e) {
        	  logger.error("Error en el parseo a JSON", e);
                  throw new Exception("No fue posible generar el json");
          }
         
          return jsonParams;
  } 
	
	
	  private Object convertirAObjeto(String json, String clase) throws Exception {
          ObjectMapper mapper = new ObjectMapper();
          Object object = null;
          logger.warn("El string del json a convertir es : " + json + " y se convertira a : " + clase);
          try {
                  object = mapper.readValue(json, Class.forName(clase));
          } catch (JsonParseException e) {
        	  logger.error("Error en el parseo a JSON", e);
                  throw new Exception("No fue posible generar el json");
          } catch (JsonMappingException e) {
        	  logger.error("Error en el parseo a JSON", e);
                  throw new Exception("No fue posible generar el json");
          } catch (IOException e) {
        	  logger.error("Error en el parseo a JSON", e);
                  throw new Exception("No fue posible generar el json");
          }
          logger.warn("Se ha generado correctamente el objeto a partir de un string json");
         
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

	@Override
	public void guardarArchivoFirmado(String secuenciaNotaria, Archivo archivo) {
		// TODO Auto-generated method stub
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
        	   logger.error("Error en el parseo a JSON", e);
                   return;
           } catch (JsonMappingException e) {
        	   logger.error("Error en el parseo a JSON", e);
                   return;
           } catch (IOException e) {
        	   logger.error("Error en el parseo a JSON", e);
                   return;
           }
          
           logger.warn("Asi quedo el archivo objeto JSON: " + jsonParams);
          
           ObjectFactory of = new ObjectFactory();
           RegistroSeguimientoRequestType peticionSeguimiento = of.createRegistroSeguimientoRequestType();
           peticionSeguimiento.setJsonParms(jsonParams);
          
           FirmaElectronicaSegService firmaElectronicaSegService = new FirmaElectronicaSegService();
           FirmaElectronicaSegPortType firmaElectronicaSegPortType = firmaElectronicaSegService.getFirmaElectronicaSegPortTypePort();

           RegistroSeguimientoResponseType respuestaPeticion = firmaElectronicaSegPortType.registroSeguimiento(peticionSeguimiento);

           ResultadoType resultado = respuestaPeticion.getResultado();
           
           logger.info("Resultado "+resultado.getCodigo());
           logger.info("Codigo "+resultado.getTexto());
           logger.info("Json "+respuestaPeticion.getJsonSalida());
           logger.info("Salida");
           if(resultado.getCodigo() == 0) {
                   try {
                           RespuestaGuardadoArchivosFirma respuestaGuardado = mapper.readValue(respuestaPeticion.getJsonSalida(), RespuestaGuardadoArchivosFirma.class);
                           logger.info("Archivo Enviado exitosamente");
                           logger.info("Id del documento: "+ respuestaGuardado.getArchivos().get(0).getId());
                           logger.info("Nombre del archivo: " + respuestaGuardado.getArchivos().get(0).getNombre());
                   } catch (JsonParseException e) {
                	   e.printStackTrace();
                	   logger.error("Error en el parseo a JSON", e);
                   } catch (JsonMappingException e) {
                	   e.printStackTrace();
                	   logger.error("Error en el parseo a JSON", e);
                   } catch (IOException e) {
                	   e.printStackTrace();
                	   logger.error("Error en el parseo a JSON", e);
                   }
                   logger.warn("El archivo ha sido guardado correctamente");
           } else {
        	   logger.error("Codigo: " + resultado.getCodigo());
        	   logger.error("Descripcion: " + resultado.getTexto());
           } 
	} 

	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Solicitud guardarSolicitudTramite(Solicitud solicitud, ParamSolicitudTramite parametro){
		
		Hashtable<String, String> h = new Hashtable<String, String>(7);
		Object ob = null;
		Object serviceFirma=null;
		h.put(Context.INITIAL_CONTEXT_FACTORY, getPropiedad("service.provider.context"));
		h.put(Context.PROVIDER_URL, getPropiedad("service.provider.url"));
		h.put(Context.SECURITY_PRINCIPAL, getPropiedad("service.security.principal"));
		h.put(Context.SECURITY_CREDENTIALS, getPropiedad("service.security.credentials"));
		
		InitialContext context = null;
		try {
			context = new InitialContext(h);
			ob = context.lookup(getPropiedad("service.jndi.bean.solicitud"));
			SolicitudBusinessRemote solicitudBusiness=(SolicitudBusinessRemote) ob;
			logger.info("SolicitudBusinessRemote "+solicitudBusiness);			
			
			serviceFirma=context.lookup(getPropiedad("service.jndi.bean.firmaSolicitud"));
			FirmaDigitalBusinessRemote firmaDigitalBusinessRemote=(FirmaDigitalBusinessRemote)serviceFirma;
			
			
			logger.info("Sellado  "+parametro.getRespuestaFirmadoSimple());
			logger.info("Firma  "+parametro.getFirmaElectronicaResultado());
			solicitud=solicitudBusiness.crear(solicitud);
			logger.info("Clave Solicitud "+solicitud.getSolicitudId());
			if(parametro.getFirmaElectronicaResultado()!=null){
				firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud, parametro.getFirmaElectronicaResultado());
			}else{
				mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoSimple res=new mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoSimple();
				res.setId(parametro.getRespuestaFirmadoSimple().getId());
				res.setNoSerie(parametro.getRespuestaFirmadoSimple().getNoSerie());
				res.setSello(parametro.getRespuestaFirmadoSimple().getSello());
				res.setTramite(parametro.getRespuestaFirmadoSimple().getTramite());				 
				
				FirmaElectronica fiEl=firmaDigitalBusinessRemote.convertirRespuestaFirmadoSimple(parametro.getCadenaOriginal(), res);
				logger.info("Firma electronica Convertida "+fiEl);
				
				firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud, fiEl);
			}
						
			
			
		}catch(Exception e ){
			e.printStackTrace();
		}finally{
			try {
				context.close();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return solicitud;
	}
	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public SujetoObligado recuperaSujetoObligado(CrtSolicitudcorr crtSolicitud){
		String rp=crtSolicitud.getPatronCorregir().getRegistroPatronal();
		logger.info("Recuperando sujetoOblidado de  "+rp);

		Hashtable<String, String> h = new Hashtable<String, String>(7);
		SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;
		SujetoObligado sujetoObligado=new SujetoObligado();
		sujetoObligado.setNumeroRegistroPatronal(rp);
		
		Object ob = null;
		h.put(Context.INITIAL_CONTEXT_FACTORY, getPropiedad("service.provider.context"));
		h.put(Context.PROVIDER_URL, getPropiedad("service.provider.url"));
		h.put(Context.SECURITY_PRINCIPAL, getPropiedad("service.security.principal"));
		h.put(Context.SECURITY_CREDENTIALS, getPropiedad("service.security.credentials"));
		
		InitialContext context = null;
		try {
			context = new InitialContext(h);
			ob = context.lookup(getPropiedad("service.jndi.bean.sujetoObligado"));			
			sujetoObligadoServiceBusiness=(SujetoObligadoServiceBusinessRemote) ob;			
			sujetoObligado=sujetoObligadoServiceBusiness.obtenerDetalleSujetoObligadoActividadEconomica(sujetoObligado);
			
		}catch(Exception e ){
			e.printStackTrace();
		}finally{
			try {
				context.close();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}		
		return sujetoObligado;
	}
	
	
	@Override
	public Solicitud generaNuevaSolicitudTramite(ParamSolicitudTramite paramSolicitudTramite){
		
		Solicitud sol = new Solicitud();
		TipoSolicitud tipoSolicitud=new TipoSolicitud();
		EstadoSolicitud estadoSolicitud=new EstadoSolicitud();
		OrigenSolicitud origenSolicitud=new OrigenSolicitud();
		Subdelegacion subdelegacion = new Subdelegacion();
		System.out.println(paramSolicitudTramite.getClaveSubDelegacion());
		subdelegacion.setId(paramSolicitudTramite.getClaveSubDelegacion());
		
		tipoSolicitud.setIdTipoSolicitud(TipoSolicitudEnum.CORRECCION_PATRONAL.getId());
		estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.REGISTRADA.getId().intValue());
		
		if(paramSolicitudTramite.getUsuarioInternet()==1){
			origenSolicitud.setIdTipoSolicitud(OrigenSolicitudEnum.INTERNET.getId());
		}else{
			origenSolicitud.setIdTipoSolicitud(OrigenSolicitudEnum.VENTANILLA.getId());
		}
		sol.setSubdelegacion(subdelegacion);
		sol.setTipoSolicitud(tipoSolicitud);
		sol.setEstadoSolicitud(estadoSolicitud);	
    	sol.setOrigenSolicitud(origenSolicitud);
		sol.setCadenaOriginal(paramSolicitudTramite.getCadenaOriginal());
		sol.setSelloDigital(paramSolicitudTramite.getSelloDigital());
		sol.setSecuenciaDeNotaria(paramSolicitudTramite.getTramiteNotaria());
		
		//Fechas
		sol.setFechaPresentacion(Calendar.getInstance().getTime());
		//sol.setFechaActualizacion(Calendar.getInstance().getTime());
		//Fechas
		
		sol.setSujetoObligado(paramSolicitudTramite.getSujetoObligado());
		sol.setNoFolioSolicitud(paramSolicitudTramite.getNumeroFolio());
		System.out.println("FOLIO: "+sol.getNoFolioSolicitud());

		Usuario solicitante = new Usuario();
		solicitante.setUsuario(paramSolicitudTramite.getCveUsuario());
		sol.setSolicitante(solicitante);

		List<Tramite> tramites=new ArrayList<Tramite>();
		ConfigTramite config=generaConfiguracionSolicitudCorreccion();
		TramiteSujetoObligado trSujeObl=new TramiteSujetoObligado();
		trSujeObl.setSujetoObligado(paramSolicitudTramite.getSujetoObligado());		
		trSujeObl.setEstadoTramite(config.getEstadoTramite());
		trSujeObl.setTipoTramite(config.getTipoTramite());
		
		//Fechas
		trSujeObl.setFechaConclusion(config.getFechaConclusion());
		trSujeObl.setFechaPresentacion(config.getFechaPresentacion());
		trSujeObl.setFechaTramite(config.getFechaTramite());
		//Fechas	
		
		tramites.add(trSujeObl);
    	sol.setTramites(tramites);		
		return sol;
	}
	
	@Override
	public ParamSolicitudTramite generaParametro(CrtSolicitudcorr solicitud,CrtTramitePresentado tramite,SujetoObligado sujetoObligado){
		ParamSolicitudTramite parametro=new ParamSolicitudTramite();
		parametro.setCadenaOriginal(solicitud.getCadenaOriginal());
		parametro.setRegistroPatronal(solicitud.getPatronCorregir().getRegistroPatronal());
		parametro.setSelloDigital(solicitud.getSelloIMSS());
		parametro.setSujetoObligado(sujetoObligado);
		parametro.setTramiteNotaria(tramite.getIdTramiteRefNotaria());
		parametro.setUsuarioInternet(solicitud.getIdFormaPresenta());
		parametro.setNumeroFolio(solicitud.getNuFolio());
		parametro.setFirmaElectronicaResultado(solicitud.getFirmaElectroResultado());
		parametro.setRespuestaFirmadoSimple( solicitud.getRespuestaObjetoFirmadoSimple());
		parametro.setClaveSubDelegacion(solicitud.getCveSubdelegacion());
		parametro.setCveUsuario(solicitud.getCveUsuario());
		
		logger.info("Params");
		logger.info("Cadena Original "+parametro.getCadenaOriginal());
		logger.info("Registro Patronal "+parametro.getRegistroPatronal());
		logger.info("Sello "+parametro.getSelloDigital());
		logger.info("SujetoObligado "+parametro.getSujetoObligado());
		logger.info("Tramite Notaria "+parametro.getTramiteNotaria());
		logger.info("RespuestaFirmado Simple "+parametro.getRespuestaFirmadoSimple());
		logger.info("FirmaElectronica "+parametro.getFirmaElectronicaResultado());
		System.out.println("Numero de Folio "+parametro.getNumeroFolio());
		return parametro;
	}
	
	  private String getPropiedad(String propiedad) {
          String propertie = null;
          Properties properties = new Properties();         
          try {
               InputStream is = new ClassPathResource("config.properties").getInputStream();
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

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Solicitud agregaNuevoTramite(Long  cveSolicitudBDTU, String rp, ConfigTramite configTramite) {
		// TODO Auto-generated method stub
		Hashtable<String, String> h = new Hashtable<String, String>(7);
		Object ob = null;
		Object obSujetoObligado=null;
		h.put(Context.INITIAL_CONTEXT_FACTORY, getPropiedad("service.provider.context"));
		h.put(Context.PROVIDER_URL, getPropiedad("service.provider.url"));
		h.put(Context.SECURITY_PRINCIPAL, getPropiedad("service.security.principal"));
		h.put(Context.SECURITY_CREDENTIALS, getPropiedad("service.security.credentials"));
		Solicitud sol = new Solicitud();
		InitialContext context = null;
		try {
			context = new InitialContext(h);
			ob = context.lookup(getPropiedad("service.jndi.bean.solicitud"));
			obSujetoObligado=context.lookup(getPropiedad("service.jndi.bean.sujetoObligado"));
			
			SolicitudBusinessRemote solicitudBusiness=(SolicitudBusinessRemote) ob;
			SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness=(SujetoObligadoServiceBusinessRemote) obSujetoObligado;		
					
			SujetoObligado sjObliga=new SujetoObligado();
			System.out.println("Rp Enbvio "+rp);
			sjObliga.setNumeroRegistroPatronal(rp);
			sjObliga=sujetoObligadoServiceBusiness.obtenerDetalleSujetoObligadoActividadEconomica(sjObliga);
			
			sol.setSolicitudId(cveSolicitudBDTU);
			sol=solicitudBusiness.consultar(sol);
			
			System.out.println("Solicitud recuperada numeroTramitesAsignada "+sol.getTramites().size());
			
			//Se agrega un nuevo tramite
			TramiteSujetoObligado trSujeObl=new TramiteSujetoObligado();			

			
			trSujeObl.setSujetoObligado(sjObliga);	
			trSujeObl.setEstadoTramite(configTramite.getEstadoTramite());
			trSujeObl.setTipoTramite(configTramite.getTipoTramite());
			trSujeObl.setFechaConclusion(configTramite.getFechaConclusion());
			trSujeObl.setFechaPresentacion(configTramite.getFechaPresentacion());
			trSujeObl.setFechaTramite(configTramite.getFechaTramite());
			
			sol.getTramites().add(trSujeObl);				
			solicitudBusiness.actualizarTramites(sol);
			logger.info("Total Tramites "+sol.getTramites().size());
			System.out.println("Total Tramites "+sol.getTramites().size());
		}catch(Exception e ){
			e.printStackTrace();
		}finally{
			try {
				context.close();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}		
		return sol;
	}



	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void cerrarSolicitudBDTU(Long cveSolicitudBDTU) {
		// TODO Auto-generated method stub
		
		Hashtable<String, String> h = new Hashtable<String, String>(7);
		Object ob = null;
		h.put(Context.INITIAL_CONTEXT_FACTORY, getPropiedad("service.provider.context"));
		h.put(Context.PROVIDER_URL, getPropiedad("service.provider.url"));
		h.put(Context.SECURITY_PRINCIPAL, getPropiedad("service.security.principal"));
		h.put(Context.SECURITY_CREDENTIALS, getPropiedad("service.security.credentials"));
		
		InitialContext context = null;
		try {
			context = new InitialContext(h);
			ob = context.lookup(getPropiedad("service.jndi.bean.solicitud"));
			SolicitudBusinessRemote solicitudBusiness=(SolicitudBusinessRemote) ob;
			logger.info("Solicitud a concluir "+cveSolicitudBDTU);
			solicitudBusiness.actualizarSolicitudAEstatusConcluida(cveSolicitudBDTU);
			logger.info("Termina cierra de Solicitud BDTU");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				context.close();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}

	@Override
	public ConfigTramite generaConfiguracionSolicitudCorreccion() {
		// TODO Auto-generated method stub
		ConfigTramite config=new ConfigTramite();
		
		EstadoTramite estadoTramite=new EstadoTramite();
		TipoTramite tipoTramite=new TipoTramite();			
		estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.ACTIVO.getCodigo());		
		tipoTramite.setIdTipoTramite(TipoTramiteEnum.CORRECCION_PATRONAL_CORP01.getCodigo());
		
		
		
		config.setEstadoTramite(estadoTramite);
		config.setTipoTramite(tipoTramite);
		//config.setFechaConclusion(Calendar.getInstance().getTime());
		config.setFechaPresentacion(Calendar.getInstance().getTime());
		config.setFechaTramite(Calendar.getInstance().getTime());
		return config;
	}
	
	

	@Override
	public ConfigTramite generaConfiguracionProrroga() {
		// TODO Auto-generated method stub
		
		ConfigTramite config=new ConfigTramite();
		
		EstadoTramite estadoTramite=new EstadoTramite();
		TipoTramite tipoTramite=new TipoTramite();			
		estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.ACTIVO.getCodigo());		
		tipoTramite.setIdTipoTramite(TipoTramiteEnum.PRORROGA_CORRECCION_PATRONAL_CORP03.getCodigo());
		
		
		config.setEstadoTramite(estadoTramite);
		config.setTipoTramite(tipoTramite);
		
		//config.setFechaConclusion(Calendar.getInstance().getTime());
		config.setFechaPresentacion(Calendar.getInstance().getTime());
		config.setFechaTramite(Calendar.getInstance().getTime());
		
		return config;
	}

	@Override
	public ConfigTramite generaConfiguracionPresentacion() {
		// TODO Auto-generated method stub
		
		ConfigTramite config=new ConfigTramite();
		
		EstadoTramite estadoTramite=new EstadoTramite();
		TipoTramite tipoTramite=new TipoTramite();			
		estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.ACTIVO.getCodigo());		
		tipoTramite.setIdTipoTramite(TipoTramiteEnum.PRESENTACION_CORRECCION_PATRONAL_CORP02.getCodigo());
		
		
		config.setEstadoTramite(estadoTramite);
		config.setTipoTramite(tipoTramite);
		
		//config.setFechaConclusion(Calendar.getInstance().getTime());
		config.setFechaPresentacion(Calendar.getInstance().getTime());
		config.setFechaTramite(Calendar.getInstance().getTime());
		return config;
	}
	
	

	
}
