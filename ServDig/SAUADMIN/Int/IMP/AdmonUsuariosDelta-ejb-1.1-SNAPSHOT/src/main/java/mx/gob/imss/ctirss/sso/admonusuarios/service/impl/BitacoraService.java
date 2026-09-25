package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import mx.gob.imss.ctirss.admonusuarios.entidad.DicModulo;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoAprobador;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoCatestatus;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoCatpuesto;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoPerfilessol;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoSolicitud;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoUsrMovimientos;
import mx.gob.imss.ctirss.sso.admonusuarios.baseservice.GenericService;
import mx.gob.imss.ctirss.sso.admonusuarios.cte.Constantes;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SolicitudDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.service.BitacoraServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.siap.cliente.ClienteConsultaCurpSiap;
import mx.gob.imss.ctirss.sso.admonusuarios.siap.modelo.UsuarioNominaResponse;
import mx.gob.imss.ctirss.sso.admonusuarios.ttds.cliente.ClienteConsultaCurpTTDS;
import mx.gob.imss.ctirss.sso.admonusuarios.ttds.modelo.UsuarioTTD;

@Stateless (name="bitacoraService", mappedName="bitacoraService")
public class BitacoraService extends GenericService implements BitacoraServiceLocal{

	public void guardaBitacora(String operacion, Long idSol, String descripcion, Long idAprobador, Long idEstatus) {
		
		try {
			SsoUsrMovimientos bitacora = new SsoUsrMovimientos();
			SsoAprobador aprobador = em.find(SsoAprobador.class, idAprobador);
			SsoCatestatus estatus = em.find(SsoCatestatus.class,idEstatus);
			SsoSolicitud sol = em.find(SsoSolicitud.class, idSol);
			
			bitacora = new SsoUsrMovimientos();

			bitacora.setDesDatosMovimientos(operacion + "|"  + descripcion);
			
			bitacora.setFechaRegistro(new Date());
			bitacora.setSsoAprobador(aprobador);
			bitacora.setSsoCatEstatus(estatus);
			bitacora.setSsoSolicitud(sol);
			super.saveorupdate(bitacora);
		} catch (AdmonUsuariosException e) {
			e.printStackTrace();
		}
	}
	
	public SsoAprobador obtenDatosSession(Long idAprobador) {
		SsoAprobador sa = em.find(SsoAprobador.class, idAprobador);
		
		return sa;
	}
		
	public UsuarioNominaResponse datosSIAP(String curp, String matricula) {
		UsuarioNominaResponse unr = null;
		
		ClienteConsultaCurpSiap cccs = new ClienteConsultaCurpSiap();
		
		try {
			unr = cccs.invocarServicioConsultaCurpSiap(curp, matricula);
			System.out.println("### Dato en empKeyProS [" + unr.getTipoContratacion() + "] ###");
			System.out.println("### Dato en statusSIAP [" + unr.getEstatus() + "] ###");
		}catch(Exception exc) {
			System.out.println("### Error al obtener datos SIAP del CURP [ " + curp + " ] ###");
			System.out.println(exc.getCause());
			System.out.println(exc.getMessage());
			System.out.println(exc.getStackTrace());
		}
		
		return unr;
	}
	
	public UsuarioTTD datosTTD(String curp, String matricula) {
		UsuarioTTD ut = null;
		
		ClienteConsultaCurpTTDS ccct = new ClienteConsultaCurpTTDS();
		
		try {
			ut = ccct.invocarServicioConsultaCurpTTDS(curp, matricula);
			System.out.println("### Dato en empKeyPro [" + ut.getTipoContratacion() + "] ###");
			System.out.println("### Dato en statusTTD [" + ut.getEstatus() + "] ###");
		}catch(Exception exc) {
			System.out.println("### Error al obtener datos TTD del CURP [ " + curp + " ] ###");
			System.out.println(exc.getCause());
			System.out.println(exc.getMessage());
			System.out.println(exc.getStackTrace());
		}
		
		return ut;
	}

	@Override
	public void guardaSolicitudBit(Long idSol, Long idAprobador,int tpMov)throws AdmonUsuariosException {
		
		try {
			SsoUsrMovimientos bitacora = new SsoUsrMovimientos();
			SsoAprobador aprobador = em.find(SsoAprobador.class, idAprobador);
			SsoSolicitud sol = em.find(SsoSolicitud.class, idSol);
			SsoCatestatus estatus = em.find(SsoCatestatus.class,sol.getEstatus());
			
			SolicitudDTO solicitud = getSolicitudDTO(sol);
			
			bitacora = new SsoUsrMovimientos();

			String mov = "EL APROBADOR "+ aprobador.getSsoSolicitud().getDesUsrCurp();
			
			if(Constantes.TIPO_MOV_REGISTRO==tpMov)
				mov = mov +" HA REGISTRADO LA SOLICITUD PARA LA CUENTA USUARIO: "+solicitud.getDesUsrCurp()+ " QUE CORRESPONDE A LA PERSONA "+ solicitud.getNombreCompleto();
			if(Constantes.TIPO_MOV_AUTORIZACION==tpMov)
				mov = mov +" HA AUTORIZADO LA CUENTA DE USUARIO: "+solicitud.getDesUsrCurp()+ " CON LOS SIGUIENTES GRUPOS: "+solicitud.getGruposDesc() +" Y LOS SIGUIENTES MODULOS: "+solicitud.getModulosDesc()+" : " + solicitud.datosBitacora();
			if(Constantes.TIPO_MOV_MODIFICACION==tpMov)
				mov = mov +" HA REALIZADO ACTUALIZACIONES A LA CUENTA DE USUARIO: "+ solicitud.getDesUsrCurp() + ". LA CUENTA ESTARA INACTIVA HASTA SU AUTORIZACION.";
			if(Constantes.TIPO_MOV_BAJA==tpMov)
				mov = mov +" HA DADO DE BAJA DEFINITIVA LA CUENTA DE USUARIO: "+ solicitud.getDesUsrCurp() + ". LA CUENTA ESTARA INACTIVA HASTA SU REACTIVACION.";
			if(Constantes.TIPO_MOV_BAJA_CURP==tpMov)
				mov = mov +" HA DADO DE BAJA POR CAMBIO DE CURP LA CUENTA DE USUARIO: "+ solicitud.getDesUsrCurp() + ". LA CUENTA ESTARA INACTIVA HASTA SU REACTIVACION.";
			if(Constantes.TIPO_MOV_RECUPERACION==tpMov)
				mov = mov +" HA RECUPERADO LA CUENTA DE USUARIO: "+ solicitud.getDesUsrCurp() + ". LA CUENTA ESTARA INACTIVA HASTA SU AUTORIZACION. : " + solicitud.datosBitacora();
			if(Constantes.TIPO_MOV_REENVIO_NOTIF==tpMov)
				mov = mov +" HA REENVIADO LA NOTIFICACION DE ACTIVACION DE LA CUENTA DE USUARIO: "+ solicitud.getDesUsrCurp() + ". LA CUENTA ESTARA INACTIVA HASTA SU REACTIVACION.";
			if(Constantes.TIPO_MOV_RECHAZO_CUENTA==tpMov)
				mov = mov +" HA RECHAZADO EL REGISTRO DE LA CUENTA DE USUARIO: "+ solicitud.getDesUsrCurp() + ". LA CUENTA ESTARA INACTIVA HASTA SU RECUPERACION.";
			
			if(mov.length()>500)
				mov = mov.substring(0,499);

			bitacora.setDesDatosMovimientos(mov);
			bitacora.setFechaRegistro(new Date());
			bitacora.setSsoAprobador(aprobador);
			bitacora.setSsoCatEstatus(estatus);
			bitacora.setSsoSolicitud(sol);
			super.saveorupdate(bitacora);
		} catch (AdmonUsuariosException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void guardaSolicitudBitSolicitud(SsoSolicitud sol, Long idAprobador,int tpMov)throws AdmonUsuariosException {
		
		try {
			SsoUsrMovimientos bitacora = new SsoUsrMovimientos();
			SsoAprobador aprobador = em.find(SsoAprobador.class, idAprobador);
			SsoCatestatus estatus = em.find(SsoCatestatus.class,sol.getEstatus());
			
			SolicitudDTO solicitud = getSolicitudDTO(sol);
			
			bitacora = new SsoUsrMovimientos();

			String mov = "EL APROBADOR "+ aprobador.getSsoSolicitud().getDesUsrCurp();
			
			if(Constantes.TIPO_MOV_REGISTRO==tpMov)
				mov = mov +" HA REGISTRADO LA SOLICITUD PARA LA CUENTA USUARIO: "+solicitud.getDesUsrCurp()+ " QUE CORRESPONDE A LA PERSONA "+ solicitud.getNombreCompleto();
			if(Constantes.TIPO_MOV_AUTORIZACION==tpMov)
				mov = mov +" HA AUTORIZADO LA CUENTA DE USUARIO: "+solicitud.getDesUsrCurp()+ " CON LOS SIGUIENTES GRUPOS: "+solicitud.getGruposDesc() +" Y LOS SIGUIENTES MODULOS: "+solicitud.getModulosDesc()+" : " + solicitud.datosBitacora();
			if(Constantes.TIPO_MOV_MODIFICACION==tpMov)
				mov = mov +" HA REALIZADO ACTUALIZACIONES A LA CUENTA DE USUARIO: "+ solicitud.getDesUsrCurp() + ". LA CUENTA ESTARA INACTIVA HASTA SU AUTORIZACION.";
			if(Constantes.TIPO_MOV_BAJA==tpMov)
				mov = mov +" HA DADO DE BAJA DEFINITIVA LA CUENTA DE USUARIO: "+ solicitud.getDesUsrCurp() + ". LA CUENTA ESTARA INACTIVA HASTA SU REACTIVACION.";
			if(Constantes.TIPO_MOV_BAJA_CURP==tpMov)
				mov = mov +" HA DADO DE BAJA POR CAMBIO DE CURP LA CUENTA DE USUARIO: "+ solicitud.getDesUsrCurp() + ". LA CUENTA ESTARA INACTIVA HASTA SU REACTIVACION.";
			if(Constantes.TIPO_MOV_RECUPERACION==tpMov)
				mov = mov +" HA RECUPERADO LA CUENTA DE USUARIO: "+ solicitud.getDesUsrCurp() + ". LA CUENTA ESTARA INACTIVA HASTA SU AUTORIZACION. : " + solicitud.datosBitacora();
			if(Constantes.TIPO_MOV_REENVIO_NOTIF==tpMov)
				mov = mov +" HA REENVIADO LA NOTIFICACION DE ACTIVACION DE LA CUENTA DE USUARIO: "+ solicitud.getDesUsrCurp() + ". LA CUENTA ESTARA INACTIVA HASTA SU REACTIVACION.";
			if(Constantes.TIPO_MOV_RECHAZO_CUENTA==tpMov)
				mov = mov +" HA RECHAZADO EL REGISTRO DE LA CUENTA DE USUARIO: "+ solicitud.getDesUsrCurp() + ". LA CUENTA ESTARA INACTIVA HASTA SU RECUPERACION.";
			
			if(mov.length()>500)
				mov = mov.substring(0,499);

			bitacora.setDesDatosMovimientos(mov);
			bitacora.setFechaRegistro(new Date());
			bitacora.setSsoAprobador(aprobador);
			bitacora.setSsoCatEstatus(estatus);
			bitacora.setSsoSolicitud(sol);
			super.saveorupdate(bitacora);
		} catch (AdmonUsuariosException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void guardaSolicitudBitActivacion(Long solId)throws AdmonUsuariosException {
		
		try {
			SsoUsrMovimientos bitacora = new SsoUsrMovimientos();
			SsoSolicitud sol = em.find(SsoSolicitud.class,solId);
			SsoCatestatus estatus = sol.getSsoCatestatus(); 
			
			SolicitudDTO solicitud = getSolicitudDTO(sol);
			
			bitacora = new SsoUsrMovimientos();

			String mov = "EL USUARIO  "+ solicitud.getDesUsrCurp() +" HA ACTIVADO SU CUENTA. LA CUENTA HA QUEDADO ACTIVA.";
			
			if(mov.length()>500)
				mov = mov.substring(0,499);

			bitacora.setDesDatosMovimientos(mov);
			bitacora.setFechaRegistro(new Date());
			bitacora.setSsoCatEstatus(estatus);
			bitacora.setSsoSolicitud(sol);
			super.saveorupdate(bitacora);
		} catch (AdmonUsuariosException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void guardaPuestoBit(Long idSol, Long idAprobador,Long idPuesto,boolean add)throws AdmonUsuariosException {
		
		try {
			SsoUsrMovimientos bitacora = new SsoUsrMovimientos();
			SsoAprobador aprobador = em.find(SsoAprobador.class, idAprobador);
			SsoSolicitud sol = em.find(SsoSolicitud.class, idSol);
			SsoCatestatus estatus = em.find(SsoCatestatus.class,sol.getEstatus());
			SsoCatpuesto puesto  = em.find(SsoCatpuesto.class,idPuesto); 
			
			SolicitudDTO solicitud = getSolicitudDTO(sol);
			
			bitacora = new SsoUsrMovimientos();

			String mov = "";
			
			if(add)
				mov = mov +"SE AGREGO EL GRUPO (PUESTO):"+puesto.getDesPuesto()+" A LA CUENTA DE USUARIO: "+solicitud.getDesUsrCurp();
			else
				mov = mov +"SE ELIMINO EL GRUPO (PUESTO):"+puesto.getDesPuesto()+" A LA CUENTA DE USUARIO: "+solicitud.getDesUsrCurp();
			

			bitacora.setDesDatosMovimientos(mov);
			bitacora.setFechaRegistro(new Date());
			bitacora.setSsoAprobador(aprobador);
			bitacora.setSsoCatEstatus(estatus);
			bitacora.setSsoSolicitud(sol);
			super.saveorupdate(bitacora);
		} catch (AdmonUsuariosException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void guardaPuestoBitSolicitud(SsoSolicitud sol, Long idAprobador,Long idPuesto,boolean add)throws AdmonUsuariosException {
		
		try {
			SsoUsrMovimientos bitacora = new SsoUsrMovimientos();
			SsoAprobador aprobador = em.find(SsoAprobador.class, idAprobador);
			SsoCatestatus estatus = em.find(SsoCatestatus.class,sol.getEstatus());
			SsoCatpuesto puesto  = em.find(SsoCatpuesto.class,idPuesto); 
			
			
			bitacora = new SsoUsrMovimientos();

			String mov = "";
			
			if(add)
				mov = mov +"SE AGREGO EL GRUPO (PUESTO):"+puesto.getDesPuesto()+" A LA CUENTA DE USUARIO: "+sol.getDesUsrCurp();
			else
				mov = mov +"SE ELIMINO EL GRUPO (PUESTO):"+puesto.getDesPuesto()+" A LA CUENTA DE USUARIO: "+sol.getDesUsrCurp();
			

			bitacora.setDesDatosMovimientos(mov);
			bitacora.setFechaRegistro(new Date());
			bitacora.setSsoAprobador(aprobador);
			bitacora.setSsoCatEstatus(estatus);
			bitacora.setSsoSolicitud(sol);
			super.saveorupdate(bitacora);
		} catch (AdmonUsuariosException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void guardaModulosBit(Long idSol, Long idAprobador, Long idModulo,boolean add)throws AdmonUsuariosException {
		
		try {
			SsoUsrMovimientos bitacora = new SsoUsrMovimientos();
			SsoAprobador aprobador = em.find(SsoAprobador.class, idAprobador);
			SsoSolicitud sol = em.find(SsoSolicitud.class, idSol);
			SsoCatestatus estatus = em.find(SsoCatestatus.class,sol.getEstatus());
			DicModulo modulo = em.find(DicModulo.class,idModulo); 
			
			SolicitudDTO solicitud = getSolicitudDTO(sol);
			
			bitacora = new SsoUsrMovimientos();

			String mov = "";
			
			if(add)
				mov = mov +"SE AGREGO  EL MODULO: "+modulo.getDesModulo()+" A LA CUENTA DE USUARIO: "+solicitud.getDesUsrCurp();
			else
				mov = mov +"SE ELIMINO  EL MODULO: "+modulo.getDesModulo()+" A LA CUENTA DE USUARIO: "+solicitud.getDesUsrCurp();
			

			bitacora.setDesDatosMovimientos(mov);
			bitacora.setFechaRegistro(new Date());
			bitacora.setSsoAprobador(aprobador);
			bitacora.setSsoCatEstatus(estatus);
			bitacora.setSsoSolicitud(sol);
			super.saveorupdate(bitacora);
		} catch (AdmonUsuariosException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void guardaModulosBitSolicitud(SsoSolicitud sol, Long idAprobador, Long idModulo,boolean add)throws AdmonUsuariosException {
		
		try {
			SsoUsrMovimientos bitacora = new SsoUsrMovimientos();
			SsoAprobador aprobador = em.find(SsoAprobador.class, idAprobador);
			SsoCatestatus estatus = em.find(SsoCatestatus.class,sol.getEstatus());
			DicModulo modulo = em.find(DicModulo.class,idModulo); 
			
			
			bitacora = new SsoUsrMovimientos();

			String mov = "";
			
			if(add)
				mov = mov +"SE AGREGO  EL MODULO: "+modulo.getDesModulo()+" A LA CUENTA DE USUARIO: "+sol.getDesUsrCurp();
			else
				mov = mov +"SE ELIMINO  EL MODULO: "+modulo.getDesModulo()+" A LA CUENTA DE USUARIO: "+sol.getDesUsrCurp();
			

			bitacora.setDesDatosMovimientos(mov);
			bitacora.setFechaRegistro(new Date());
			bitacora.setSsoAprobador(aprobador);
			bitacora.setSsoCatEstatus(estatus);
			bitacora.setSsoSolicitud(sol);
			super.saveorupdate(bitacora);
		} catch (AdmonUsuariosException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void guardaAprobadorBit(Long idSol, Long idAprobador,boolean add)throws AdmonUsuariosException {
		
		try {
			SsoUsrMovimientos bitacora = new SsoUsrMovimientos();
			SsoAprobador aprobador = em.find(SsoAprobador.class, idAprobador);
			SsoSolicitud sol = em.find(SsoSolicitud.class, idSol);
			SsoCatestatus estatus = em.find(SsoCatestatus.class,sol.getEstatus());
			
			SolicitudDTO solicitud = getSolicitudDTO(sol);
			
			bitacora = new SsoUsrMovimientos();

			String mov = "EL APROBADOR "+ aprobador.getSsoSolicitud().getDesUsrCurp();
			
			if(add)
				mov = mov +" OTORGO PERMISOS DE APROBADOR A LA CUENTA DE USUARIO: "+solicitud.getDesUsrCurp();
			else
				mov = mov +" REVOCO PERMISOS DE APROBADOR  A LA CUENTA DE USUARIO: "+solicitud.getDesUsrCurp();
			

			bitacora.setDesDatosMovimientos(mov);
			bitacora.setFechaRegistro(new Date());
			bitacora.setSsoAprobador(aprobador);
			bitacora.setSsoCatEstatus(estatus);
			bitacora.setSsoSolicitud(sol);
			super.saveorupdate(bitacora);
		} catch (AdmonUsuariosException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void guardaLoginAprobadorBit(Long idAprobador)throws AdmonUsuariosException {
		
		try {
			SsoUsrMovimientos bitacora = new SsoUsrMovimientos();
			SsoAprobador aprobador = em.find(SsoAprobador.class, idAprobador);
			
			
			bitacora = new SsoUsrMovimientos();

			String mov = "EL APROBADOR "+ aprobador.getSsoSolicitud().getDesUsrCurp()+" SE FIRMO AL SISTEMA";

			bitacora.setDesDatosMovimientos(mov);
			bitacora.setFechaRegistro(new Date());
			bitacora.setSsoAprobador(aprobador);
			super.saveorupdate(bitacora);
		} catch (AdmonUsuariosException e) {
			e.printStackTrace();
		}
	}


}
