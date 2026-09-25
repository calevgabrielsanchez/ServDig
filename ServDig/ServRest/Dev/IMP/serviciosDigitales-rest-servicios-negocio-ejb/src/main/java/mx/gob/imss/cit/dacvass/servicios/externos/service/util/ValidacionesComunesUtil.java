package mx.gob.imss.cit.dacvass.servicios.externos.service.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.ErrorResponseBean;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;

public class ValidacionesComunesUtil {
	
	private static final Logger log = LoggerFactory
            .getLogger(ValidacionesComunesUtil.class);
	
	public static void validaIdCatalogo (Long idCatalogo) throws ServiciosRestException{
		if(idCatalogo == null || idCatalogo == -1) {
			log.error("el valor de idCatalogo es nulo o negativo");
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
					"Los parametros de entrada no pueden ser nulos", "Los parametros de entrada no pueden ser nulos"));
		}
	}
	
	public static void validaIdCatalogo (String idCatalogo) throws ServiciosRestException{
		if(idCatalogo == null || StringUtils.isEmpty(idCatalogo)) {
			log.error("el valor de idCatalogo es nulo o negativo");
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
					"Los parametros de entrada no pueden ser nulos", "Los parametros de entrada no pueden ser nulos"));
		}
	}
	
	public static ServiciosRestException getExcepcionConsultaCatalogo(Exception ex) {
		return new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
				"Ocurrio un Error al consultar el catalogo",  ex.getMessage()));
		
	}
	
	public static ServiciosRestException getExcepcionConsultaSiscob(Exception ex, String nrp) {
		return new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
				"Ocurrio un Error al consultar los creditos del patron " + nrp,  ex.getMessage()),ex);
		
	}
	
	public static ServiciosRestException getServiciosRestException(Exception ex, String msg) {
		return new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
				msg + " " + ex.getMessage(),  ex.getMessage()),ex);
	}
	
	public static ServiciosRestException getServiciosRestException(String msg) {
		return new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
				msg , msg));
	}
	
	public static String  validaEstructuraNSS (String nss) throws ServiciosRestException{
		if(nss == null || nss.length() < 10 || nss.length() > 11  || !StringUtils.isNumeric(nss) ) 
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
				"El NSS no puede ser nulo o no cuenta con la estructura valida", "El NSS no puede ser nulo o no cuenta con la estructura valida"));
		return nss = nss.length() ==10? nss + generaDigitoVerificador(nss): nss;
	}

	public static String validaEstructuraNRP (String nrp) throws ServiciosRestException{
	if(StringUtils.isEmpty(nrp) || (nrp.length() <10 || nrp.length()> 11  )) 
		throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
				"El Registro Patronal no puede ser nulo o no cuenta con la estructura valida", "El Registro Patronal no puede ser nulo o no cuenta con la estructura valida"));
		return nrp.toUpperCase();
	}
	
	
	public static void validaIdCatalogo (String idCatalogo, String nombreElemento) throws ServiciosRestException{
		if(idCatalogo == null || StringUtils.isEmpty(idCatalogo)) {
			log.error("el valor de elemento  es nulo o negativo");
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
					"El parametro " + nombreElemento + " no pueden ser nulo", "El parametro " + nombreElemento + " no pueden ser nulo"));
		}
	}
	
	public static void validaIdCatalogo (Long idCatalogo, String nombreElemento) throws ServiciosRestException{
		if(idCatalogo == null || idCatalogo == -1) {
			log.error("el valor de elemento  es nulo o negativo");
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
					"El parametro " + nombreElemento + " no pueden ser nulo o negativo", "El parametro " + nombreElemento + " no pueden ser nulo o negativo"));
		}
	}
	
	public static String validaEstructuraCurp (String curp) throws ServiciosRestException{
		if(curp == null ||  !validarCurp(curp)) 
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
				"El CURP no puede ser nulo o no cuenta con la estructura valida", "El CURP no puede ser nulo o no cuenta con la estructura valida"));
		return curp.toUpperCase();
	}
	
	public static void validaObjetoNulo(Object objeto, String msg) throws ServiciosRestException{
		if(objeto == null) 
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
				msg, msg));
	
	}
	
	public static void validaListaNoVacia(List lista, Long tamanioMaximo, String nombreElemento)throws ServiciosRestException{
		if(lista == null ||  lista.isEmpty()) {
			log.error("la lista llego nula o vacia");
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
					"La lista " + nombreElemento + " no pueden ser nulo o vacia", "El lista " + nombreElemento + " no pueden ser nulo o vacia"));
		}
		if(tamanioMaximo != null) {
			if(lista.size()> tamanioMaximo.intValue()) {
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
						"La lista " + nombreElemento + " exede el numero maximo de elmentos" + tamanioMaximo, 
								 "La lista " + nombreElemento + " exede el numero maximo de elmentos" + tamanioMaximo));
			}
		}
	}
	
	public static void validaListaNulaVacia(List objLista, String msg) throws ServiciosRestException {
		if( objLista == null || objLista.isEmpty()) {
				log.error("la lista  llego nula");
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo404, ErrorResponseBean.codigo404Descripcion,
						msg, msg));
		}
		
	}
	
	public static void validaObjetoRespuestaNulo(Object objeto, String msg) throws ServiciosRestException{
		if(objeto == null) 
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo404, ErrorResponseBean.codigo404Descripcion,
				msg, msg));
	
	}
	
	public static String validaEstructuraRfc (String rfc) throws ServiciosRestException{
		if(rfc == null ||  (rfc.length() <12 || rfc.length() >13) ) 
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
				"El RFC no puede ser nulo o no cuenta con la estructura valida", "El RFC no puede ser nulo o no cuenta con la estructura valida"));
		return rfc.toUpperCase();
	}
	
	public static void validaStringNuloOVacio(String objetoStr, String msg) throws ServiciosRestException{
		if(StringUtils.isEmpty(objetoStr)) 
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
				msg, msg));
	
	}
	
	public static boolean isStringNuloVacio(String objetoStr) {
		return StringUtils.isBlank(objetoStr);
	}
	
	public static Integer generaDigitoVerificador(String nss){  
		int suma = 0;
		int resultado = 0;
		for(int i = 1 ; i <= nss.length() ; i++){
			if(i%2==0){
				int multiplicacion = (Integer.parseInt(nss.charAt(i-1)+"")) * 2;
				if(multiplicacion > 9 ){
					suma = suma + ((multiplicacion-10)+1);
				}else{
					suma = suma + multiplicacion;
				}
			}else{
				suma = suma + (Integer.parseInt(nss.charAt(i-1)+""));
			}	
		}
		int modulo = suma%10;
		if(modulo == 0 ){
			resultado = 0;
		}else if( modulo < 10){
			resultado = 10-modulo;
		}
		return resultado;
	}
	
	/*M�todo que tiene la funci�n de validar el curp*/
    public static  boolean validarCurp(String curp){
    	curp=curp.toUpperCase().trim();
    return curp.matches("[A-Z]{4}[0-9]{6}[H,M,X][A-Z]{5}[A-Z0-9]{2}");
    }
    
    
	
}
