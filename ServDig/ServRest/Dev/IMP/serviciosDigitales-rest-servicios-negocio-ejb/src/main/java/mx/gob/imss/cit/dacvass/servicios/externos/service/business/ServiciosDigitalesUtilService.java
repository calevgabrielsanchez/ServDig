package mx.gob.imss.cit.dacvass.servicios.externos.service.business;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.ErrorResponseBean;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.Adjunto;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.CorreoElectronicoRest;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.SelloDigitalRest;
import mx.gob.imss.cit.dacvass.servicios.externos.service.entity.catalogo.ICatalogoServiceEntityLocal;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IServiciosDigitalesUtilServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.util.ValidacionesComunesUtil;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.EnvioCorreoElectronicoBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.model.dto.CorreoElectronicoDTO;
import mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoSimple;


@Stateless(name = "serviciosDigitalesUtilService", mappedName = "serviciosDigitalesUtilService")
public class ServiciosDigitalesUtilService extends AbstractServiceBusiness implements  IServiciosDigitalesUtilServiceRemote{
	
	private static final Logger LOG = LoggerFactory
            .getLogger(ServiciosDigitalesUtilService.class);
	@EJB( mappedName = "envioCorreoElectronicoBusiness")
	private EnvioCorreoElectronicoBusinessRemote envioCorreoElectronicoBusiness; 
	
	@EJB(mappedName = "firmaDigitalBusiness")
	private  FirmaDigitalBusinessRemote firmaDigitalBusiness; 
	
	/**
	@EJB(mappedName = "agendarCitaService")
	private AgendarCitaServiceRemote agendarCitaService;
	**/
	@EJB
	ICatalogoServiceEntityLocal catalogoServiceEntity; 
	
	
	/**
	 * Metodo encargado de enviar correco electronico
	 * @param correoDto
	 * @throws Exception
	 */
	@Override
	public void enviarCorreo(CorreoElectronicoRest correoRest) throws ServiciosRestException{
		CorreoElectronicoDTO correo = this.parseCorreoRestToDto(correoRest);
		try {
			envioCorreoElectronicoBusiness.enviarCorreo(correo, correoRest.getRemitente());
		}catch (Exception e) {
			LOG.error("ocurrio un error en el envio del correo", e);
			System.out.println("ocurrio un error en el envio del correo" + e.getMessage());
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error en el envio del correo", e.getMessage()),e);
			
		}
	}
	
	
	/**
	 * Servivio que obtiene el sello Digital a traves de la cadena origina
	 * @param cadenaOriginal String
	 * @return String
	 */
	@Override
	public SelloDigitalRest getSelloDigital(SelloDigitalRest selloRest) throws ServiciosRestException{
		this.validaDatosSello(selloRest);
		try {
		RespuestaFirmadoSimple respuesta = firmaDigitalBusiness.getSelloDigital(selloRest.getCadenaOriginal(), selloRest.getSecuenciaNotaria(), 
				selloRest.getRfc());
		return this.parseFirmaToSello(respuesta);
		}catch (Exception e) {
			LOG.error("ocurrio un error al generar el sello", e);
			System.out.println("ocurrio un error al generar el sello " + e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error al generar el sello", e.getMessage()),e);
		
		}
	};
	
	
	private SelloDigitalRest parseFirmaToSello(RespuestaFirmadoSimple firma) throws Exception {
		SelloDigitalRest sello = new SelloDigitalRest();
		try {
	
		BeanUtils.copyProperties(sello, firma);
		sello.setSello(firma.getSello());
		
		}catch (Exception e) {
			System.out.println("ocurrio un error al setear las propuedades de la firma al sello rest " + e.getMessage());
			throw e;
		}
		return sello;
		
	}
	
	private void validaDatosSello(SelloDigitalRest selloRest) throws ServiciosRestException {
		System.out.println("llegue a validar el contenido del sello " );
		String descripcionValidacion = null;
		if(StringUtils.isEmpty(selloRest.getCadenaOriginal())) {
			descripcionValidacion = "El parametro cadena original no puede ser vacio";
		}else if (StringUtils.isEmpty(selloRest.getRfc())) {
			descripcionValidacion = "El parametro rfc no puede ser vacio";
		}
		if(descripcionValidacion != null) {
			System.out.println("llegue a la validcion del sello " );
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
					descripcionValidacion, descripcionValidacion), new Exception(descripcionValidacion));
		}
		
		
	}
	
	private CorreoElectronicoDTO parseCorreoRestToDto(CorreoElectronicoRest correoRest) throws ServiciosRestException {
		CorreoElectronicoDTO correoDto = new CorreoElectronicoDTO();
		
		if(StringUtils.isEmpty(correoRest.getAsunto()) || StringUtils.isEmpty(correoRest.getCuerpoCorreo()) ||
				StringUtils.isEmpty(correoRest.getRemitente())|| (correoRest.getCorreoPara() == null || StringUtils.isEmpty(correoRest.getCorreoPara()[0]))) {
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
					"Los parametros de entrada no pueden ser nulos", "Los parametros de entrada no pueden ser nulos"), new Exception("Los parametros de entrada no pueden ser nulos"));
		}
		
		
		//(BeanUtils.copyProperties(correoDto, correoRest);
		correoDto.setAsunto(correoRest.getAsunto());
		correoDto.setCorreoCopia(correoRest.getCorreoCopia());
		correoDto.setCorreoPara(correoRest.getCorreoPara());
		correoDto.setCuerpoCorreo(correoRest.getCuerpoCorreo());
		
		try {
			if(correoRest.getAdjuntos() != null ) {
				System.out.println("los adjuntos no son nulos" + correoRest.getAdjuntos().size());
				Map<String, byte[]> adjuntosDTO =  new HashMap<String, byte[]> ();
				 for (Adjunto adjunto: correoRest.getAdjuntos()) 
			       { 
					 System.out.println("llegue a los adjuntos llave [" +adjunto.getNombreAdjunto() +"valor ["+adjunto.getAdjuntoBase64()+"]" );
					
					 adjuntosDTO.put(adjunto.getNombreAdjunto(), Base64.decodeBase64(adjunto.getAdjuntoBase64().getBytes()));
					
			       } 
				 correoDto.setAdjuntos(adjuntosDTO);
			}
		
		}catch (Exception e) {
			System.out.println("ocurrio un error al setear los archivos adjuntos " + e.getMessage());
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error al setear los archivos adjuntos", e.getMessage()), e);
		}
		return correoDto;
		
	}
	
	
	@Override
	public List<Date> getDiasInhabilesPorAnio(Long numAnio)throws ServiciosRestException{
		ValidacionesComunesUtil.validaObjetoNulo(numAnio, "El numero de anio no puede ser nulo");
		try {
			return catalogoServiceEntity.getDiasinhabilesByAnio(numAnio);
		}catch(Exception e){
			System.out.println("ocurrio un error al consultar dias inhabiles" + e.getMessage());
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error al consultar los dias inhabiles ", e.getMessage() ),e);
			
		}
		
	}
	
	

}
