package mx.gob.imss.cit.dacvass.servicios.externos.service.business;

import java.io.StringReader;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import gob.imss.webservice.renapo.curp.cliente.CurpKioscosBean;
import gob.imss.webservice.renapo.curp.implementacion.ClienteWebserviceCurp;
import gob.imss.webservice.sat.rfc.implementacion.ClienteWebserviceRfc;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.ConstantesComunesServiciosRest;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.ErrorResponseBean;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.ResolucionPension;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.renapo.RespuestaWSRenapo;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sat.RespuestaWSSat;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siap.ConsultaUsuarioDirectorioActivo;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siap.EmpleadoSiapRest;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siap.RepuestaServicioSiap;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siap.UsuarioDirectorioActivo;
import mx.gob.imss.cit.dacvass.servicios.externos.service.activeDirectory.IActiveDirectoryAuthenticationServiceLocal;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IConsultaServiciosExternosServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.util.ParserRespuestaWSSatToRest;
import mx.gob.imss.cit.dacvass.servicios.externos.service.util.ResourceBundleConfiguration;
import mx.gob.imss.cit.dacvass.servicios.externos.service.util.ValidacionesComunesUtil;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;


@Stateless(name = "consultaServiciosExternosService", mappedName = "consultaServiciosExternosService")
public class ConsultaServiciosExternosService extends AbstractServiceBusiness implements IConsultaServiciosExternosServiceRemote {

	private static final Logger log = LoggerFactory
			.getLogger(ConsultaServiciosExternosService.class);

	private final Long CODIGO_EXITO = 200L;
	private static ResourceBundle propertiesSD = ResourceBundleConfiguration.getResourceServiciosDigitales();
	
	@EJB
	private  IActiveDirectoryAuthenticationServiceLocal directorioActioService;


	@Override
	public List<ResolucionPension> getConsultaInfoPension(String curp) throws ServiciosRestException{

		/**
		try {

	    	String cadena = "{\"curp\":\""+curp+"\"}";
    		ClientResponse response = webResource.type("application/json").post(ClientResponse.class, cadena);

    		 response = webResource.type("application/json").post(ClientResponse.class, cadena);
    		if (response.getStatus() != 200) {
    			RespuestaPensionesRest  output = response.getEntity(RespuestaPensionesRest.class);
    			throw new ServiciosRestException(new ErrorResponseBean(response.getStatus()+"", ErrorResponseBean.codigo500Descripcion,
	    				"Ocurrio un Error al consultar el clinete de pensiones", 
	    				output.getMensajeError()));
    		}
		 */

		/*
    	  	try {
    	    	Client client = Client.create();
        		WebResource webResource = client.resource("http://172.16.162.132/TSPI/sistrap/ConsultaAutenticacion/");
        		String cadena = "{\"curp\":\""+curp+"\"}";
        		ClientResponse response = webResource.type("application/json").post(ClientResponse.class, cadena);
        		if (response.getStatus() != 200) {
        			throw new RuntimeException("Failed : HTTP error code : "
        					+ response.getStatus());
        		}


        		 response = webResource.type("application/json").post(ClientResponse.class, cadena);
        		RespuestaPensionesRest  output = response.getEntity(RespuestaPensionesRest.class);
        		System.out.println("tag de oobjeto" + output.getMensajeError() + "y con valor" +output.getResolucionPension().size());
        		return null; //output.getResolucionPension(); 
    	    	}catch(Exception e) {
    	    		System.out.println("ocurrio un error al generar el cliente" + e.getMessage());
    	    	}
		 */
		/**
    		RespuestaPensionesRest  output = response.getEntity(RespuestaPensionesRest.class);
    		if(output.getCodigoError().longValue() == CODIGO_EXITO.longValue()) {
    			return output.getResolucionPension(); 
    		}else return null;

	    	}catch(Exception e) {
	    		System.out.println("ocurrio un error al generar el cliente" + e.getMessage());
	    		LOG.error("Ocurrio un error al consultar el cliente de pensiones", e);
	    		throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
	    				"Ocurrio un Error al consultar el clinete de pensiones", e.getMessage()));
	    	}
		 **/
		return null;	
	}

	@Override
	public  EmpleadoSiapRest getEmpleadoSiapByMatricula(Long numMatricula) throws ServiciosRestException{
		log.debug("llegando al serivcios de  getEmpleadoSiapByMatricula matricula []", numMatricula);
		ValidacionesComunesUtil.validaObjetoNulo(numMatricula, "El numero de matricula no puede ser nulo");
		String ulr = propertiesSD.getString("url.service.catalogo.empleado.siap");
		Integer timeOutConeccion = Integer.getInteger(propertiesSD.getString("timeOut.coneccion.servicios.externos.default.milis"));
		Integer timeOutRespuesta = Integer.getInteger(propertiesSD.getString("timeOut.respuesta.servicios.externos.default.milis"));		
		ulr = ulr.replace(ConstantesComunesServiciosRest.CARACTER_REEMPLAZO_SERCICIO_SIAP, numMatricula.toString());
		try {				
			Client client = Client.create();
			WebResource webResource;
			log.error("la url a consumir es " + ulr );
			client.setConnectTimeout(timeOutConeccion);
			client.setReadTimeout(timeOutRespuesta);
			webResource = client.resource(ulr);
			webResource.type("text/xml");
			webResource.method("GET");
			String response = webResource.get(String.class);
			String respuestaXmlDepurada = interpretaEstructuraXmlRespuesta(response, numMatricula);
			log.debug("la respuesta del servicio depurada es  " + respuestaXmlDepurada);
			JAXBContext context = JAXBContext.newInstance(RepuestaServicioSiap.class);
			Unmarshaller un = context.createUnmarshaller();
			RepuestaServicioSiap respuestaObjXml = (RepuestaServicioSiap) un.unmarshal(new StringReader(respuestaXmlDepurada));
			EmpleadoSiapRest respuestaRest = new EmpleadoSiapRest();
			BeanUtils.copyProperties(respuestaObjXml.getEmpleadoSiap(),respuestaRest);
			return respuestaRest;
		}catch(ServiciosRestException e) {
			throw e;
		}catch (Exception e) {
			log.error("ocurrio un erro al consultar el servicios de SIAP matricula  "+  numMatricula, e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error al consultar el servicios de SIAP matricula  " + numMatricula + " "+ e.getMessage(),
					"ocurrio un error al consultar el servicios de SIAP matricula  " + numMatricula + " " + e.getMessage()));

		}
	}

	private  String interpretaEstructuraXmlRespuesta(String strRespuestaXMLOriginal, Long numMatricula) throws ServiciosRestException{
		log.debug("la respuesta del servicio de SIAP es " + strRespuestaXMLOriginal );
		try {
			if(strRespuestaXMLOriginal == null || strRespuestaXMLOriginal.isEmpty()) {
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo404, ErrorResponseBean.codigo404Descripcion,
						"EL servicio de SIAP no devolvio informacion con la matricula  " + numMatricula, "EL servicio de SIAP no devolvio informacion con la matricula  " + numMatricula ));
			}
			else if(strRespuestaXMLOriginal.contains("No se encontraron registros")) {
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo404, ErrorResponseBean.codigo404Descripcion,
						"No se encontraron registos en SIAP matricula  " + numMatricula, "No se encontraron registos en SIAP matricula  " + numMatricula ));
			}else if (!strRespuestaXMLOriginal.contains("<ERROR>") && strRespuestaXMLOriginal.contains("<MATRICULA>") ) {
				strRespuestaXMLOriginal = strRespuestaXMLOriginal.trim();

			}else {
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
						"Ccurrio un error al leer la repuest del servicios de SIAP matricula  " + numMatricula +" " + strRespuestaXMLOriginal,
						"Ccurrio un error al leer la repuest del servicios de SIAP matricula  " + numMatricula +" " + strRespuestaXMLOriginal));
			}
			return strRespuestaXMLOriginal;
		}catch(ServiciosRestException e) {
			throw e;
		}catch(Exception e){
			log.error("ocurrio un error al leer la respuesta del servicio de SIAP ", e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ccurrio un error al leer la repuest del servicios de SIAP matricula  " + numMatricula +" " + e.getMessage(),
					"Ccurrio un error al leer la repuest del servicios de SIAP matricula  " + numMatricula +" " + e.getMessage()));
		}
	}

	@Override
	public RespuestaWSSat getDatosSatByRfc(String rfc) throws ServiciosRestException {
		rfc = ValidacionesComunesUtil.validaEstructuraRfc(rfc);
		ClienteWebserviceRfc clienteWebserviceRfc = new ClienteWebserviceRfc();
		try {
			return ParserRespuestaWSSatToRest.setRespuestaSatToRest(clienteWebserviceRfc.getDatosRfc(rfc));
		}catch(ServiciosRestException e) {
			log.error("ocurrio un error en el parser de datos sat", e);
			throw e;
		}catch (Exception e) {
			log.error("ocurrio un error al consultar a la persona por RFC en SAT" + rfc, e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error al consultar a la persona por RFC  en SAT " + rfc +" " +e.getMessage(), 
					"ocurrio un error al consultar a la persona por RFC  en SAT " + rfc +" " +e.getMessage()),e);
		}

	}

	@Override
	public RespuestaWSRenapo getDatosPersonaRenapoByCurp(String curp) throws ServiciosRestException {
		curp = ValidacionesComunesUtil.validaEstructuraCurp(curp);
		try {
			ClienteWebserviceCurp cliente = new ClienteWebserviceCurp();
			CurpKioscosBean curpKiosco = cliente.consultaDatosCURP(curp);
			RespuestaWSRenapo renapoRest = null;
			if(curpKiosco != null) {
				renapoRest  = new RespuestaWSRenapo();
				try {
					BeanUtils.copyProperties(curpKiosco,renapoRest );
				}catch(Exception e) {
					log.error("ocurrio un error en el copy properties de getDatosPersonaRenapoByCurp ", e);
					throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
							"ocurrio un error en el copy properties de getDatosPersonaRenapoByCurp :" +  e.getMessage(), 
							"ocurrio un error en el copy properties de getDatosPersonaRenapoByCurp :" +  e.getMessage()));
				}

			}
			return renapoRest;
		}catch(ServiciosRestException e) {
			log.error("ocurrio un error en el copy proerties de renapo", e);
			throw e;
		}catch (Exception e) {
			log.error("ocurrio un error al consultar a la persona en RENAPO" + curp, e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error al consultar a la persona en RENAPO" + curp +" " +e.getMessage(), 
					"ocurrio un error al consultar a la persona en RENAPO" + curp +" " +e.getMessage()),e);
		}
	}

	

	@Override
	public UsuarioDirectorioActivo getDatosUsuarioDirectorioActivo(ConsultaUsuarioDirectorioActivo consulta)
			throws ServiciosRestException {
		log.debug("llegando al serivcios de  getDatosUsuarioDirectorioActivo matricula []", consulta);
		ValidacionesComunesUtil.validaObjetoNulo(consulta, "EL usuario no puede ser nulo");
		ValidacionesComunesUtil.validaStringNuloOVacio(consulta.getClaveUsusaio(), "La clave del usuario no puede ser nula o vacia");
		ValidacionesComunesUtil.validaStringNuloOVacio(consulta.getDominio(), "El domonio de la cuenta de usuario no puede ser nula o vacia");
		try {
			
			return directorioActioService.geDatostUsuarioDirectorioActivo(consulta);
		}catch(ServiciosRestException e){
			log.error("error ya tratado al consultar al usuario" ,e);
			throw e;
		}catch(Exception e) {
			log.error("ocurrio un error no esperado al consultar al usuario" ,e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error no identificado al consultar el usuario en el directorio activo " + consulta.getClaveUsusaio() +" " +e.getMessage(), 
					"ocurrio un error no identificado al consultar el usuario en el directorio activo " + consulta.getClaveUsusaio() +" " +e.getMessage()),e);
		}
	}

	@Override
	public UsuarioDirectorioActivo autenticaUsuarioDirectorioActivo(ConsultaUsuarioDirectorioActivo consulta)
			throws ServiciosRestException {
		log.debug("llegando al serivcios de  autenticaUsuarioDirectorioActivo  []", consulta);
		ValidacionesComunesUtil.validaObjetoNulo(consulta, "EL usuario no puede ser nulo");
		ValidacionesComunesUtil.validaStringNuloOVacio(consulta.getClaveUsusaio(), "La clave del usuario no puede ser nula o vacia");
		ValidacionesComunesUtil.validaStringNuloOVacio(consulta.getDominio(), "El domonio de la cuenta de usuario no puede ser nulo o vacip");
		ValidacionesComunesUtil.validaStringNuloOVacio(consulta.getPassword(), "El password de la cuenta de usuario no puede ser nulu o vacio");
		try {
				UsuarioDirectorioActivo user = getDatosUsuarioDirectorioActivo(consulta);
				if(user != null) {
					directorioActioService.autenticaUsuarioDirectorioActivo(consulta);
						return  user;
				}else {
					throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo404, ErrorResponseBean.codigo404Descripcion,
							"El usuario "+ consulta.getClaveUsusaio() + " no se localizo en el dominio " + consulta.getDominio(), 
							"El usuario " + consulta.getClaveUsusaio() +" no se localizo en el dominio " + consulta.getDominio()));
				}
		}catch(ServiciosRestException e){
			log.error("error ya tratado al consultar al usuario" ,e);
			throw e;
		}catch(Exception e) {
			log.error("ocurrio un error no esperado al auntenticar al usuario" ,e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error no identificado al auntenticar al usuario" + consulta.getClaveUsusaio() +" " +e.getMessage(), 
					"ocurrio un error no identificado al auntenticar al usuario" + consulta.getClaveUsusaio() +" " +e.getMessage()),e);
		}
	}

}
