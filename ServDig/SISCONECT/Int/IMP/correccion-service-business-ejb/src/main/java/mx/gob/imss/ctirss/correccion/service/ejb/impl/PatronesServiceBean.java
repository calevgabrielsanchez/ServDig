/**
 * 
 */
package mx.gob.imss.ctirss.correccion.service.ejb.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cia.infraestructura.exception.BusinessException;
import mx.gob.imss.ctirss.correccion.model.CrcActeconomica;
import mx.gob.imss.ctirss.correccion.model.CrcPatron;
import mx.gob.imss.ctirss.correccion.model.CrcPatronPK;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.model.SatUbicacion;
import mx.gob.imss.ctirss.correccion.service.ejb.PatronesServiceRemote;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.CatalogoDAOLocal;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.ICatalogoDAO;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.PatronDaoLocal;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.impl.CatalogoDAOBean;
import mx.gob.imss.ctirss.correccion.service.impl.IntegracionSindoServiceImpl;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.satic.integracion.InformacionPatronSalidaVO;
import mx.gob.imss.ctirss.correccion.catalogos.model.SacSubdelegacion;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;

import org.apache.log4j.Logger;
import org.openuri.www.Patron;
import org.springframework.beans.factory.annotation.Autowired;

import com.sun.org.apache.bcel.internal.generic.ISUB;

/**
 * @author Juan Manuel Lopez Lozano
 * @since 08/10/2011
 *
 */ 
@Stateless(name="patronesService", mappedName = "patronesService") 
public class PatronesServiceBean implements PatronesServiceRemote{

	
    private static final int EXITO = 1;
    private static final int ERROR = -1;
    private static final int ERROR_SUBDELEGACION = -2;
    private static final int TIPO_MOVIMIENTO_BAJA = 2;
    private final static Logger logger = Logger.getLogger(PatronesServiceBean.class);
	@EJB PatronDaoLocal daoPatron;
	@EJB CatalogoDAOLocal<AbstractModel> daoCatalogo;
	@Autowired IntegracionSindoServiceImpl service = new IntegracionSindoServiceImpl();
	
	@Autowired
	private ICatalogoDAO<AbstractModel> dao;
	

    public CatalogoDAOLocal<AbstractModel> getDaoCatalogo() {
		return daoCatalogo;
	}


	public void setDaoCatalogo(CatalogoDAOLocal<AbstractModel> daoCatalogo) {
		this.daoCatalogo = daoCatalogo;
	}


	public IntegracionSindoServiceImpl getService() {
		return service;
	}


	public void setService(IntegracionSindoServiceImpl service) {
		this.service = service;
	}


	public PatronDaoLocal getDaoPatron() {
		return daoPatron;
	}


	public void setDaoPatron(PatronDaoLocal daoPatron) {
		this.daoPatron = daoPatron;
	}


	public SatPatron saveOrUpdatePatron(SatPatron patron){
		SatPatron retorno = null;
       	retorno =  daoPatron.saveOrUpdate(patron);
        return retorno;
    }

	/**
	 * Metodo que valida si existe un registro patronal
	 */
	
    public SatPatron validaRegistroPatronalWS(String registroPatronal, boolean esPirncipal){

    	SatPatron retorno = null;
        int digVer = 0;
        try {
            digVer = generaDigitoVerificador(registroPatronal);
        } catch (NumberFormatException numException) {
        	logger.error(numException.getMessage(),numException);
            return retorno;//"Registro Patronal no existe o es inv\u00e1lido.";
        }

        try {
        	
            // Ejecutando Web - Service
        	logger.debug("ENTRANDO AL LLAMADO AL SERVICIO obtenerDatosPatron");
            InformacionPatronSalidaVO salida = service.obtenerDatosPatron(registroPatronal.substring(0,8), registroPatronal.substring(8, 10), new Integer(digVer).toString());
            logger.debug("Obtuvimos salida: " + salida.toString());
            if (salida == null)
                throw new Exception("Error. No existe el centro de trabajo en SINDO");

            int result = salida.getDescripcion().indexOf("Exception");

            if (result > 0)
                return retorno;//"Web Service no disponible. Por favor intente m\u00e1s tarde.";

            Patron miPatronPrueba = null;


            // Se obtuvo exito al encontrar registro patronal
            if (salida.getExito() == EXITO || salida.getCveTipoMov() == TIPO_MOVIMIENTO_BAJA) {
                if (salida.getCveTipoMov() != TIPO_MOVIMIENTO_BAJA) {
                    miPatronPrueba = settingPatron(salida, registroPatronal);
                    retorno = saveOrUpdatePatron(obtenRegistroPatron(miPatronPrueba));
                   
                }
                else{
                    if(!esPirncipal)
                    {
                        miPatronPrueba = settingPatron(salida, registroPatronal);
                        retorno = saveOrUpdatePatron(obtenRegistroPatron(miPatronPrueba));
                    }else{
                    	miPatronPrueba = settingPatron(salida, registroPatronal);
                        retorno = saveOrUpdatePatron(obtenRegistroPatron(miPatronPrueba));
                    }
                }
               
                ArrayList listaSubs = null;
               
                try{
                	listaSubs = (ArrayList) daoCatalogo.consultaLibrePorClave(0L, "from SacSubdelegacion d where d.cveCodigo = '"+miPatronPrueba.getCveSubDelegacion()+"' and d.sacDelegacion.cveCodigo = '"+miPatronPrueba.getCveDelegacion()+"'" );
                	if(listaSubs!=null && listaSubs.size()>0){
                		SacSubdelegacion sub = (SacSubdelegacion) listaSubs.get(0);
                		retorno.setCveSubdelegacion(sub.getCvePk().intValue());
                		saveOrUpdatePatron(retorno);
                	}
                	
                }catch (Exception e) {
                	logger.error(e.getMessage(), e);
    				e.printStackTrace();
    			}
                
                retorno.getUbicacion().getMunicipio().getSacSubdelegacion().setCveCodigo(miPatronPrueba.getCveSubDelegacion()+"");
                retorno.getUbicacion().getMunicipio().getSacSubdelegacion().getSacDelegacion().setCveCodigo(miPatronPrueba.getCveDelegacion()+"");
                
                
                if(listaSubs!=null && listaSubs.size()>0){
                	SacSubdelegacion sub = (SacSubdelegacion)listaSubs.get(0);
                	retorno.getUbicacion().getMunicipio().getSacSubdelegacion().setNomNombre(sub.getNomNombre());
                }
                
            } 
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return retorno;
    }

    /**
	 * Metodo que valida si existe un registro patronal
	 * Utilizado en los modulos de seguimiento genericos
	 * 
	 */	
	public SatPatron validaRegistroPatronalWS(String registroPatronal, Long subdelegacion) {

		SatPatron patronRespuesta = new SatPatron();
		Long idSubdelPatron=0L;
		int digVer = 0;
		try {
			digVer = generaDigitoVerificador(registroPatronal);
		} catch (NumberFormatException numException) {
			patronRespuesta.setCveRespuestaWS(ERROR); //error
			patronRespuesta.setDescRespuestaWS("Error. Digito verificador invalido");
			return patronRespuesta;// "Registro Patronal no existe o es inv\u00e1lido.";
		}

		try {
			// Ejecutando Web - Service
			InformacionPatronSalidaVO patronWS = service.obtenerDatosPatron(
					registroPatronal.substring(0, 8),
					registroPatronal.substring(8, 10),
					new Integer(digVer).toString());

			if (patronWS == null ){
				patronRespuesta.setCveRespuestaWS(ERROR); //error
				patronRespuesta.setDescRespuestaWS("No se obtuvo respuesta de SINDO");
				return patronRespuesta;// "Web Service no disponible. Por favor intente m\u00e1s tarde.";
			}				
			
			if (patronWS.getCodigo() == 1002){
				patronRespuesta.setCveRespuestaWS(ERROR); //error
				patronRespuesta.setDescRespuestaWS("No existe el registro patronal");
				return patronRespuesta;
			}				
			
			System.out.println("salida WS="+ patronWS.getDescripcion() + ""+ patronWS.getExito());
			int result = patronWS.getDescripcion().indexOf("Exception");

			if (result > 0) {
				patronRespuesta.setCveRespuestaWS(ERROR); //error
				patronRespuesta.setDescRespuestaWS(patronWS.getDescripcion());
				return patronRespuesta;// "Web Service no disponible. Por favor intente m\u00e1s tarde.";
			}	
			Patron patronPaso = null;

			// Se obtuvo exito al encontrar registro patronal
			if (patronWS.getExito() == EXITO
					|| patronWS.getCveTipoMov() == TIPO_MOVIMIENTO_BAJA) {
				patronPaso = settingPatron(patronWS, registroPatronal);
				patronRespuesta = saveOrUpdatePatron(obtenRegistroPatron(patronPaso));
			}
			
			ArrayList listaSubs = null;
            
            try{
            	System.out.println("Query "+"from SacSubdelegacion d where d.cveCodigo = '"+patronPaso.getCveSubDelegacion()+"' and d.sacDelegacion.cveCodigo = '"+patronPaso.getCveDelegacion()+"'");
            	listaSubs = (ArrayList) daoCatalogo.consultaLibrePorClave(0L, "from SacSubdelegacion d where d.cveCodigo = '"+patronPaso.getCveSubDelegacion()+"' and d.sacDelegacion.cveCodigo = '"+patronPaso.getCveDelegacion()+"'" );
            	if(listaSubs!=null && listaSubs.size()>0){
            		SacSubdelegacion sub = (SacSubdelegacion) listaSubs.get(0);
            		patronRespuesta.setCveSubdelegacion(sub.getCvePk().intValue());
            		System.out.println("ClaveSubdelegacion "+sub.getCvePk().intValue());
            		saveOrUpdatePatron(patronRespuesta);
            	}
            	
            }catch (Exception e) {
				e.printStackTrace();
			}
            
            patronRespuesta.getUbicacion().getMunicipio().getSacSubdelegacion().setCveCodigo(patronPaso.getCveSubDelegacion()+"");
            patronRespuesta.getUbicacion().getMunicipio().getSacSubdelegacion().getSacDelegacion().setCveCodigo(patronPaso.getCveDelegacion()+"");
            
            
            if(listaSubs!=null && listaSubs.size()>0){
            	SacSubdelegacion sub = (SacSubdelegacion)listaSubs.get(0);
            	patronRespuesta.getUbicacion().getMunicipio().getSacSubdelegacion().setNomNombre(sub.getNomNombre());
            }
			
//			idSubdelPatron=patronRespuesta.getUbicacion().getMunicipio().getSacSubdelegacion().getCvePk();
			idSubdelPatron=patronRespuesta.getCveSubdelegacion().longValue();
			
			System.out.println("&&& -> subdelUser="+subdelegacion+", subdelPatron="+idSubdelPatron);
			
			if (patronWS.getCveTipoMov() == TIPO_MOVIMIENTO_BAJA){
				patronRespuesta.setCveRespuestaWS(ERROR);
				patronRespuesta.setDescRespuestaWS("El registro patronal se encuentra dado de baja");
			} else if (!subdelegacion.equals(ConstantesBusiness.NO_VALIDAR_SUBDELEGACION) && !subdelegacion.equals(idSubdelPatron)){
				patronRespuesta.setCveRespuestaWS(ERROR_SUBDELEGACION);
				patronRespuesta.setDescRespuestaWS("El registro patronal no corresponde a la subdelegaci\u00f3n");
			} else {
				patronRespuesta.setCveRespuestaWS(patronWS.getExito());
				patronRespuesta.setDescRespuestaWS("Ejecucion exitosa");
			}			
			
			patronRespuesta.setCveTipoMovWS( ""+patronWS.getCveTipoMov());			
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			patronRespuesta.setCveRespuestaWS(ERROR);
			patronRespuesta.setDescRespuestaWS("Error en el servicio:" + e.getMessage());
			e.printStackTrace();
		}		
		
		return patronRespuesta;
	}

    /**
     * Recibe como parametro un objeto de tipo Patron obtenido desde el servicio de sindo y lo transforma
     * a un objeto de tipo Registro Patron que es nuestro entity dentro del proyecto para ingresarlo en base de datos
     * @param miPatronPrueba
     * @return
     */
    private SatPatron obtenRegistroPatron(Patron miPatronPrueba) {
        if(miPatronPrueba!=null)
        {
        	SatPatron patron = new SatPatron();
            patron.setRegistroPatronal(miPatronPrueba.getPatron()+miPatronPrueba.getDigitoVerificador());

            if(miPatronPrueba.getRazonSocial()!=null && miPatronPrueba.getRazonSocial().trim().length()>0)
            	// Se eliminan espacios adicionales de SINDO. GSV, 06/09/2012
            	patron.setRazonSocial(miPatronPrueba.getRazonSocial().trim());
            else
            	patron.setRazonSocial(" ");
            if(miPatronPrueba.getRfc()!=null&&miPatronPrueba.getRfc().length()>0)
            	patron.setRfc(miPatronPrueba.getRfc());
            if(miPatronPrueba.getCurp()!=null&&miPatronPrueba.getCurp().length()>0)
            	patron.setCurp(miPatronPrueba.getRfc());
            patron.setNumHibernateVersion(new Integer("355"));
            
            SatUbicacion ubicacion = new SatUbicacion();
            if(miPatronPrueba.getCalle()!=null&&miPatronPrueba.getCalle().trim().length()>0)
            	ubicacion.setCalle(miPatronPrueba.getCalle());
            if(miPatronPrueba.getNumInter()!=null&&miPatronPrueba.getNumInter().trim().length()>0)
                ubicacion.setNumeroInterior(miPatronPrueba.getNumInter());
            if(miPatronPrueba.getNumExt()!=null&&miPatronPrueba.getNumExt().trim().length()>0)
            	ubicacion.setNumeroExterior(miPatronPrueba.getNumExt());
            if(miPatronPrueba.getColonia()!=null&&miPatronPrueba.getColonia().trim().length()>0)
            	ubicacion.setColonia(miPatronPrueba.getColonia());
            ubicacion.setCodigoPostal(miPatronPrueba.getCodPostal()+"");
            if(miPatronPrueba.getEMail()!=null&&miPatronPrueba.getEMail().trim().length()>0)
            	ubicacion.seteMail(miPatronPrueba.getEMail());
            if(miPatronPrueba.getTelefono()!=null&&miPatronPrueba.getTelefono().trim().length()>0)
            	ubicacion.setTelefono(new Integer(miPatronPrueba.getTelefono()));

            if(miPatronPrueba.getCveMunicipio()!=null&&miPatronPrueba.getCveMunicipio().trim().length()>0)
            {
            	ubicacion.setMunicipio(daoPatron.obtenmunicipio(miPatronPrueba.getCveMunicipio()));
            	ubicacion.setFkMunicipio(ubicacion.getMunicipio().getCvePK());
            	if (ubicacion.getMunicipio().getSacSubdelegacion()!=null) {
            		ubicacion.getMunicipio().getSacSubdelegacion().getSacDelegacion();
            	}
            }
            ubicacion.setNumHibernateVersion(new Integer("3"));
            patron.setUbicacion(ubicacion);
            
            return patron;
        }
        return null;
    }

    
    
    public int generaDigitoVerificador(String nrp) {
        int factorDeConversion = 10;
        int digitoVerificador = 0;
        int paso3 = 0;
        boolean bandera = true;
        String alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String clave = "";
        int primeraLetra = alfabeto.indexOf(nrp.toUpperCase().charAt(0));
        if (primeraLetra != -1) {
            clave = (primeraLetra + factorDeConversion) + nrp.substring(1, nrp.length());
        } else {
            clave = nrp;
        }
        int i = clave.length() - 1;
        while (i >= 0) {
            if (bandera) {
                int porDos = Integer.parseInt("" + clave.charAt(i)) * 2;
                if (porDos > 9)// si el resultado es un numero de dos cifras, es necesario tratar
                               // estas por separado.
                {
                    paso3 += (porDos % 10) + (porDos / 10);
                } else {
                    paso3 += porDos;
                }
                bandera = false;
            } else {
                paso3 += Integer.parseInt("" + clave.charAt(i));
                bandera = true;
            }
            i--;
        }
        digitoVerificador = 10 - (paso3 % 10);
        if (digitoVerificador > 9) {
            digitoVerificador = 0;
        }

        return digitoVerificador;
    }

	/**
	 * recibe un objeto de tipo InformacionPatronSalidaVO obtenido desde el webservice de SINDO y regresa Un Objeto de  tipo Patron con los
	 * datos del webservice de patrones
	 * @param salida
	 * @param registroPatronal
	 * @return
	 * @throws BusinessException
	 */
    public Patron settingPatron(InformacionPatronSalidaVO salida, String registroPatronal){
        Patron miPatronPrueba = new Patron();
        short shortPrueba1 = 1;
        Calendar calendar = Calendar.getInstance();

        miPatronPrueba.setPatron(registroPatronal);
        miPatronPrueba.setRegistroPatronalUnico(registroPatronal);

        miPatronPrueba.setAdicPens(10);
        miPatronPrueba.setAnioCveModal(shortPrueba1);
        miPatronPrueba.setCalle(salida.getDomicilio());
        miPatronPrueba.setCausaBaja("");
        miPatronPrueba.setClase("");
        miPatronPrueba.setClaveModalUnica(shortPrueba1);
        miPatronPrueba.setCodPostal(salida.getCodigoPostal().equals("") ? 0 : Integer.parseInt(salida.getCodigoPostal()));
        miPatronPrueba.setColonia("");
        miPatronPrueba.setCurp(salida.getCURP());
        miPatronPrueba.setCveDelegacion(salida.getCveDelegacion());
        miPatronPrueba.setCveDelegacionEmision(shortPrueba1);
        miPatronPrueba.setCveDivision(shortPrueba1);
        miPatronPrueba.setCveGrupoActividadEcon(shortPrueba1);
        miPatronPrueba.setCveLada(14);
        miPatronPrueba.setCveModal(Integer.parseInt(registroPatronal.substring(8, 10)));
        miPatronPrueba.setCveMunicipio(salida.getCveMunicipio());
        miPatronPrueba.setCveSubDelegacion(salida.getCveSubDelegacion());
        miPatronPrueba.setCveSubdelegacionEmision(shortPrueba1);
        miPatronPrueba.setCveTipoMovimiento(new Integer(salida.getCveTipoMov()).toString());
        miPatronPrueba.setDigitoVerificador((short) generaDigitoVerificador(registroPatronal));
        miPatronPrueba.setDomicilio(salida.getDomicilio());
        miPatronPrueba.setEMail(salida.getCorreo());
        miPatronPrueba.setFax("");
        miPatronPrueba.setFechaActualizacion(calendar);
        miPatronPrueba.setFechaInicioHuelga(calendar);
        miPatronPrueba.setFechaMovimiento(calendar);
        miPatronPrueba.setFecIniAct(calendar);
        miPatronPrueba.setFraccion("");
        miPatronPrueba.setGiro("");
        miPatronPrueba.setKeyAutentif("");
        miPatronPrueba.setLocalidad(salida.getLocalidad());
        miPatronPrueba.setMesEmision(shortPrueba1);
        miPatronPrueba.setRazonSocial(salida.getRazonSocial());
        miPatronPrueba.setRfc(salida.getRFC());
        miPatronPrueba.setSecNotif(shortPrueba1);
        return miPatronPrueba;
    }


	@Override
	public SatPatron getById(Long cvePk) {
		return daoPatron.getById(cvePk);
	}


	@Override
	public SatPatron getByRegistroPatronal(String registroPatronal) {
		return daoPatron.getByRegistroPatronal(registroPatronal);
	}


    
	

}
