package mx.gob.imss.cit.dacvass.servicios.externos.service.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import gob.imss.webservice.sat.rfc.cliente.SalidaSAT;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.ErrorResponseBean;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sat.Actividades;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sat.Identificacion;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sat.MensajeControl;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sat.Mensajes;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sat.Obligaciones;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sat.Regimenes;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sat.RepLegales;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sat.RespuestaWSSat;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sat.Roles;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sat.Sucursales;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sat.Ubicacion;

public class ParserRespuestaWSSatToRest {
	private static final Logger log = LoggerFactory
            .getLogger(ParserRespuestaWSSatToRest.class);
	
	
	public static RespuestaWSSat setRespuestaSatToRest(SalidaSAT salidaSat)throws ServiciosRestException{
		RespuestaWSSat respuestaSat = new RespuestaWSSat();
		try {
			if(salidaSat != null) {
				respuestaSat = new RespuestaWSSat();
				BeanUtils.copyProperties(salidaSat,respuestaSat, new String[]
						{"actividad","identificacion", "mensaje", "mensajeControl","obligacion",
								"regimen", "replegal", "rol", "sucursal", "ubicacion"});
				
				setRespuestaSatActividadesToRest(salidaSat, respuestaSat);
				setRespuestaSatIdentificacionToRest(salidaSat, respuestaSat);
				setRespuestaSatMensajesToRest(salidaSat, respuestaSat);
				setRespuestaSatMensajeControlToRest(salidaSat, respuestaSat);
				setRespuestaSatObligacionToRest(salidaSat, respuestaSat);
				setRespuestaSatRegimnesToRest(salidaSat, respuestaSat);
				setRespuestaSatRepLegalesToRest(salidaSat, respuestaSat);
				setRespuestaSatRolesToRest(salidaSat, respuestaSat);
				setRespuestaSatSucursalesToRest(salidaSat, respuestaSat);
				setRespuestaSatUbicacionesToRest(salidaSat, respuestaSat);
				
			}
			return respuestaSat;
		}catch(ServiciosRestException e) {
			log.error("ocurrio algun error en los parsers", e);
			throw e;
		}catch(Exception e) {
			log.error("ocurrio un error en el parser setRespuestaSatToRest ", e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error en el parser setRespuestaSatToRest  :" +  e.getMessage(), 
					"ocurrio un error en el parser setRespuestaSatToRest  :" +  e.getMessage()));
		}
		
	}
	
	
	public static void setRespuestaSatActividadesToRest(SalidaSAT salidaSat, RespuestaWSSat respuestaSat)throws ServiciosRestException{
		List<Actividades> lstActividadesRest= null;
		try {
			if(salidaSat.getActividad() != null && !salidaSat.getActividad().isEmpty()) {
				   lstActividadesRest= new ArrayList<Actividades>();
				   for(gob.imss.webservice.sat.rfc.cliente.Actividades actividades: salidaSat.getActividad()) {
					   Actividades actividadesRest = new Actividades(); 
					   BeanUtils.copyProperties(actividades,actividadesRest);
					   lstActividadesRest.add(actividadesRest);
				   }
			}
			respuestaSat.setActividad(lstActividadesRest);
		}catch(Exception e) {
			log.error("ocurrio un error en el parser setRespuestaSatActividadesToRest ", e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error en el parser setRespuestaSatActividadesToRest  :" +  e.getMessage(), 
					"ocurrio un error en el parser setRespuestaSatActividadesToRest  :" +  e.getMessage()));
		}	
	}
	
	public static void setRespuestaSatIdentificacionToRest(SalidaSAT salidaSat, RespuestaWSSat respuestaSat)throws ServiciosRestException{
		  List<Identificacion> lstIdentificacion= null;
		try {
			if(salidaSat.getIdentificacion() != null && !salidaSat.getIdentificacion().isEmpty()) {
				lstIdentificacion= new ArrayList<Identificacion>();
				   for(gob.imss.webservice.sat.rfc.cliente.Identificacion identificacion: salidaSat.getIdentificacion()) {
					   Identificacion identificacionRest = new Identificacion(); 
					   BeanUtils.copyProperties(identificacion,identificacionRest);
					   lstIdentificacion.add(identificacionRest);
				   }
			}
			respuestaSat.setIdentificacion(lstIdentificacion);
		}catch(Exception e) {
			log.error("ocurrio un error en el parser setRespuestaSatIdentificacionToRest ", e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error en el parser setRespuestaSatIdentificacionToRest  :" +  e.getMessage(), 
					"ocurrio un error en el parser setRespuestaSatIdentificacionToRest  :" +  e.getMessage()));
		}	
	}
	
	public static void setRespuestaSatMensajesToRest(SalidaSAT salidaSat, RespuestaWSSat respuestaSat)throws ServiciosRestException{
		  List<Mensajes> lstMensajes= null;
		try {
			if(salidaSat.getMensaje() != null && !salidaSat.getMensaje().isEmpty()) {
				lstMensajes= new ArrayList<Mensajes>();
				   for(gob.imss.webservice.sat.rfc.cliente.Mensajes mensaje: salidaSat.getMensaje()) {
					   Mensajes mensajeRest = new Mensajes(); 
					   BeanUtils.copyProperties(mensaje,mensajeRest);
					   lstMensajes.add(mensajeRest);
				   }
			}
			respuestaSat.setMensaje(lstMensajes);
		}catch(Exception e) {
			log.error("ocurrio un error en el parser setRespuestaSatMensajesToRest ", e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error en el parser setRespuestaSatMensajesToRest  :" +  e.getMessage(), 
					"ocurrio un error en el parser setRespuestaSatMensajesToRest  :" +  e.getMessage()));
		}	
	}
	
	public static void setRespuestaSatMensajeControlToRest(SalidaSAT salidaSat, RespuestaWSSat respuestaSat)throws ServiciosRestException{
		  MensajeControl mensajeControl = null;
		try {
			if(salidaSat.getMensajeControl() != null) {
				 mensajeControl = new MensajeControl();
				 BeanUtils.copyProperties(salidaSat.getMensajeControl(),mensajeControl);
				
			}
			respuestaSat.setMensajeControl(mensajeControl);
		}catch(Exception e) {
			log.error("ocurrio un error en el parser setRespuestaSatMensajeControlToRest ", e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error en el parser setRespuestaSatMensajeControlToRest  :" +  e.getMessage(), 
					"ocurrio un error en el parser setRespuestaSatMensajeControlToRest  :" +  e.getMessage()));
		}
		
	}
	
	public static void setRespuestaSatObligacionToRest(SalidaSAT salidaSat, RespuestaWSSat respuestaSat)throws ServiciosRestException{  
		  List<Obligaciones> lstObligacion = null;
		try {
			if(salidaSat.getObligacion() != null && !salidaSat.getObligacion().isEmpty()) {
				lstObligacion= new ArrayList<Obligaciones>();
				   for(gob.imss.webservice.sat.rfc.cliente.Obligaciones obligacion: salidaSat.getObligacion()) {
					   Obligaciones obligacionRest = new Obligaciones(); 
					   BeanUtils.copyProperties(obligacion,obligacionRest);
					   lstObligacion.add(obligacionRest);
				   }
			}
			respuestaSat.setObligacion(lstObligacion);
		}catch(Exception e) {
			log.error("ocurrio un error en el parser setRespuestaSatObligacionToRest ", e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error en el parser setRespuestaSatObligacionToRest  :" +  e.getMessage(), 
					"ocurrio un error en el parser setRespuestaSatObligacionToRest  :" +  e.getMessage()));
		}
		
	}
	
	public static void setRespuestaSatRegimnesToRest(SalidaSAT salidaSat, RespuestaWSSat respuestaSat)throws ServiciosRestException{  
		 List<Regimenes> lstRegimen = null;
		try {
			if(salidaSat.getRegimen() != null && !salidaSat.getRegimen().isEmpty()) {
				lstRegimen=  new ArrayList<Regimenes>();
				   for(gob.imss.webservice.sat.rfc.cliente.Regimenes regimen: salidaSat.getRegimen()) {
					   Regimenes regimenesRest = new Regimenes(); 
					   BeanUtils.copyProperties(regimen,regimenesRest);
					   lstRegimen.add(regimenesRest);
				   }
			}
			respuestaSat.setRegimen(lstRegimen);
		}catch(Exception e) {
			log.error("ocurrio un error en el parser setRespuestaSatRegimnesToRest ", e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error en el parser setRespuestaSatRegimnesToRest  :" +  e.getMessage(), 
					"ocurrio un error en el parser setRespuestaSatRegimnesToRest  :" +  e.getMessage()));
		}		
	}
	
	public static void setRespuestaSatRepLegalesToRest(SalidaSAT salidaSat, RespuestaWSSat respuestaSat)throws ServiciosRestException{  
		  List<RepLegales> lstReplegal =null;
		try {
			if(salidaSat.getReplegal() != null && !salidaSat.getReplegal().isEmpty()) {
				lstReplegal=  new ArrayList<RepLegales>();
				   for(gob.imss.webservice.sat.rfc.cliente.RepLegales repLegales: salidaSat.getReplegal()) {
					   RepLegales repLegalesRest = new RepLegales(); 
					   BeanUtils.copyProperties(repLegales,repLegalesRest);
					   lstReplegal.add(repLegalesRest);
				   }
			}
			respuestaSat.setReplegal(lstReplegal);
		}catch(Exception e) {
			log.error("ocurrio un error en el parser setRespuestaSatRepLegalesToRest ", e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error en el parser setRespuestaSatRepLegalesToRest  :" +  e.getMessage(), 
					"ocurrio un error en el parser setRespuestaSatRepLegalesToRest  :" +  e.getMessage()));
		}		
	}
	
	public static void setRespuestaSatRolesToRest(SalidaSAT salidaSat, RespuestaWSSat respuestaSat)throws ServiciosRestException{  
		List<Roles> lstRol = null;
		try {
			if(salidaSat.getRol() != null && !salidaSat.getRol().isEmpty()) {
				lstRol=  new ArrayList<Roles>();
				   for(gob.imss.webservice.sat.rfc.cliente.Roles  roles: salidaSat.getRol()) {
					   Roles rolesRest = new Roles(); 
					   BeanUtils.copyProperties(roles,rolesRest);
					   lstRol.add(rolesRest);
				   }
			}
			respuestaSat.setRol(lstRol);
		}catch(Exception e) {
			log.error("ocurrio un error en el parser setRespuestaSatRolesToRest ", e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error en el parser setRespuestaSatRolesToRest  :" +  e.getMessage(), 
					"ocurrio un error en el parser setRespuestaSatRolesToRest  :" +  e.getMessage()));
		}	
	}
	
	public static void setRespuestaSatSucursalesToRest(SalidaSAT salidaSat, RespuestaWSSat respuestaSat)throws ServiciosRestException{  
		 List<Sucursales> lstSucursal = null;
		try {
			if(salidaSat.getSucursal() != null && !salidaSat.getSucursal().isEmpty()) {
				lstSucursal= new ArrayList<Sucursales>();
				   for(gob.imss.webservice.sat.rfc.cliente.Sucursales  sucursales: salidaSat.getSucursal()) {
					   Sucursales sucursalesRest = new Sucursales(); 
					   BeanUtils.copyProperties(sucursales,sucursalesRest);
					   lstSucursal.add(sucursalesRest);
				   }
			}
			respuestaSat.setSucursal(lstSucursal);
		}catch(Exception e) {
			log.error("ocurrio un error en el parser setRespuestaSatSucursalesToRest ", e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error en el parser setRespuestaSatSucursalesToRest  :" +  e.getMessage(), 
					"ocurrio un error en el parser setRespuestaSatSucursalesToRest  :" +  e.getMessage()));
		}	
	}
	
	public static void setRespuestaSatUbicacionesToRest(SalidaSAT salidaSat, RespuestaWSSat respuestaSat)throws ServiciosRestException{  
		List<Ubicacion> lstUbicacion = null;
		try {
			if(salidaSat.getUbicacion() != null && !salidaSat.getUbicacion().isEmpty()) {
				lstUbicacion= new ArrayList<Ubicacion>();
				   for(gob.imss.webservice.sat.rfc.cliente.Ubicacion  ubicacion: salidaSat.getUbicacion()) {
					   Ubicacion ubicacionRest = new Ubicacion(); 
					   BeanUtils.copyProperties(ubicacion,ubicacionRest);
					   lstUbicacion.add(ubicacionRest);
				   }
			}
			respuestaSat.setUbicacion(lstUbicacion);
		}catch(Exception e) {
			log.error("ocurrio un error en el parser setRespuestaSatUbicacionesToRest ", e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error en el parser setRespuestaSatUbicacionesToRest  :" +  e.getMessage(), 
					"ocurrio un error en el parser setRespuestaSatUbicacionesToRest  :" +  e.getMessage()));
		}	
	}

}
