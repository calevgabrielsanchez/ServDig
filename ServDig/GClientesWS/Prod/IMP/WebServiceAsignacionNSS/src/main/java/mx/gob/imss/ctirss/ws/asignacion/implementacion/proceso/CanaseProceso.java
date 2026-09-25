/**
 * @author Acosta Julieta y Douglas Dulce
 * Fecha: 08/08/2003
 * Utima modificacion:
 * Regla de negocio para la asignación de NSS
 * Version 1.0
 */

package mx.gob.imss.ctirss.ws.asignacion.implementacion.proceso;
import gob.imss.tecnologia.comunes.excepciones.ExcepcionIMSS;
import gob.imss.tecnologia.comunes.excepciones.ManejadorErrores;

import java.util.List;

import mx.gob.imss.ctirss.ws.asignacion.implementacion.bean.AsignacionNSSBean;
import mx.gob.imss.ctirss.ws.asignacion.implementacion.bean.CuentaUsuMainframeBean;
import mx.gob.imss.ctirss.ws.asignacion.implementacion.bean.ResponseMainFrameBean;
import mx.gob.imss.ctirss.ws.asignacion.implementacion.dao.CuentaMainframeDAOImpl;
import mx.gob.imss.ctirss.ws.asignacion.implementacion.exception.NoHayConsecutivoException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;


public class CanaseProceso {
	
	private static final String DAO = "dao";
	private static final String JDBCDAO_XML = "jdbcDao.xml";
	private CuentaMainframeDAOImpl dao;
	private final int USUARIO_DEBE_CAMBIAR_PASSWORD = 50;
	private final int DATOS_USUARIO_INCORRECTOS = 51;
	private final int CUENTA_USUARIO_REVOCADA = 52;
	private final int USUARIO_FIRMADO_OTRA_TERMINAL = 53;
	private final int CREDENCIALES_SIN_PERMISO_ACCESO = 54;
	
	private final int ID_SISTEMA = 2; //Del proyecto DELTA DIC_MODULO el 2 corresponde a la asignación de NSS
	
	private static Logger log = Logger.getLogger(CanaseProceso.class);
	
	
	private void iniciarSpring(){
		ClassPathResource resource = new ClassPathResource(JDBCDAO_XML);
		XmlBeanFactory factory = new XmlBeanFactory(resource);
		dao= (CuentaMainframeDAOImpl) factory.getBean(DAO);		
	}
	
	/**
     *Descripcion: Genera el NSS obtiene CIZ (Clave del centro de
     *informacion de zona) y manda a llamar el alta en Canase     
     *@param AsignacionNSSBean NsBean
     *@return ResponseMainFrameBean objResponse
     *@throws ExcepcionIMSS
     */
    public ResponseMainFrameBean alta(AsignacionNSSBean nSSBean) throws ExcepcionIMSS, NoHayConsecutivoException {
    	//Esta clase sera reemplazada para hacer el alta directa en CANASE por medio de la aplicacion CICS

    	ResponseMainFrameBean objResponse = null;
    	List listaCuentas = null;
    	CuentaUsuMainframeBean objCuenta = null;
    	try {
    		log.debug("Inicia el método alta");
    		this.iniciarSpring();
    		//Obtengo las cuentas de usuario de la base de datos
    		listaCuentas = dao.obtenerCuentaUsuario(ID_SISTEMA);
    		if (listaCuentas != null) {
    			for (int cont = 0; cont < listaCuentas.size(); cont++) {
    				objCuenta = (CuentaUsuMainframeBean)listaCuentas.get(cont);
    				MainFrameProceso alta = new MainFrameProceso(objCuenta.getCuentaUsuario(), objCuenta.getContraseniaUsuMainframe());
    				objResponse = alta.altaCanase(nSSBean);
					if (objResponse.getErrorNumber() == USUARIO_FIRMADO_OTRA_TERMINAL
							|| objResponse.getErrorNumber() == USUARIO_DEBE_CAMBIAR_PASSWORD
							|| objResponse.getErrorNumber() == DATOS_USUARIO_INCORRECTOS
							|| objResponse.getErrorNumber() == CUENTA_USUARIO_REVOCADA
							|| objResponse.getErrorNumber() == CREDENCIALES_SIN_PERMISO_ACCESO) {
                    	// Aqui es donde debo verificar que la cuenta no este utilizada
						log.debug("La cuenta "+ objCuenta.getCuentaUsuario() + " tiene alguna caracteristica por la cual no se puede utilizar y es por: "
										+ objResponse.getErrorMessage());
    					continue;
                    } else {
                    	log.debug("Aqui rompo el ciclo porque ya me pude firmar u ocurrio algun otro codigo de error");
                    	break;
                    }
    			}    			
                /*Obteniendo NSS*/
                log.debug("Id lugar nacimietno " + nSSBean.getLugarNacimiento());
                log.debug("nSSBean en CAnaseProceso " + nSSBean);
                if(nSSBean.getSFolio() != null && nSSBean.getSFolio().length() != 0) {
                    log.debug("Aqui hacia algo con la base de datos con el folio");                
                }
    		}            
        } catch (ExcepcionIMSS e) {
        	log.error("ExcepcionIMSS CanaseProceso.alta() " , e);
        	log.error(e.getMensaje(), e);
            throw e;
        } catch (NoHayConsecutivoException nhce) {
        	log.error("NoHayConsecutivoException CanaseProceso.alta() " , nhce);
        	log.error(nhce.getMessage(), nhce);
            throw nhce;
        }
        catch (Exception e) {
        	log.error("Exception CanaseProceso.alta() " , e);
        	log.error(e.getMessage(), e);
            throw ManejadorErrores.getExcepcionIMSS(e, "");
        }
        return objResponse;
    }    
}