/*
 * Created on 14/10/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mx.gob.imss.ctirss.ws.asignacion.implementacion.util;

import java.util.HashMap;

import mx.gob.imss.ctirss.ws.asignacion.implementacion.bean.LabelValueBean;

import org.apache.log4j.Logger;




/**
 * @author CAPACITACION
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
 public class CatalogoLugarNacimiento {
	 
    private static Logger log = Logger.getLogger(CatalogoLugarNacimiento.class);
	 
    
	private HashMap mapLugares = null;
	private HashMap mapLugaresXDescripicion = null;
	private static CatalogoLugarNacimiento instanceCatalogo;
	
    public static synchronized CatalogoLugarNacimiento getInstance(){
		if(instanceCatalogo == null){
		    instanceCatalogo = new CatalogoLugarNacimiento();
		}
		return  instanceCatalogo;
	}

	
	public LabelValueBean obtenLugarNacimientoPorIndice(String indice){
	    LabelValueBean infoLugarNacimiento= null;
	    try
	    {
			//log.debug("El elemento que tiene el mapa es x id " + indice);
			//log.debug("mapa tamaño " + mapLugares.size());
			infoLugarNacimiento = (LabelValueBean)this.mapLugares.get(indice);
			
	    }
	    catch (Exception e)
	    {
	        log.error(e);
	    }
	    return infoLugarNacimiento;

	}

	public LabelValueBean obtenLugarNacimientoPorDescCorta(String descripcionCorta){
	    LabelValueBean infoLugarNacimiento= null;
	    try
	    {
			//log.debug("El elemento que tiene el mapa en obtenLugarNacimientoPorDescCorta es" + descripcionCorta);
			infoLugarNacimiento = (LabelValueBean)this.mapLugaresXDescripicion.get(descripcionCorta);
			
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	    return infoLugarNacimiento;

	}
}