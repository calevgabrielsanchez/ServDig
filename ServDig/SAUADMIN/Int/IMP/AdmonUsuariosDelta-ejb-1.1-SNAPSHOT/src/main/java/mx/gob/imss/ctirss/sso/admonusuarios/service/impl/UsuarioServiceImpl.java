package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.FlushModeType;
import javax.persistence.Query;

import mx.gob.imss.ctirss.admonusuarios.entidad.SsoSolicitud;
import mx.gob.imss.ctirss.sso.admonusuarios.baseservice.GenericService;
import mx.gob.imss.ctirss.sso.admonusuarios.cte.Constantes;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.AdminUserResponseDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.AnalistaDictamenDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ResponsableDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ResponsablesDelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioIdentidadDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioInfoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.services.UsuarioServiceRemote;

/**
 * Session Bean implementation class UsuarioServiceImpl
 */
@Stateless(name = "usuarioService", mappedName = "usuarioService")
public class UsuarioServiceImpl extends GenericService implements
		UsuarioServiceRemote {

	@SuppressWarnings("unchecked")
	@Override
	public AdminUserResponseDTO consultaUsuarioPorCURP(String curp){

		AdminUserResponseDTO adminUserResponseDTO = new AdminUserResponseDTO();

		if (curp.length() != 18) {
			adminUserResponseDTO.setCodigo("0003");
			adminUserResponseDTO
					.setDescripcion("Estructura de la CURP invalida");
			return adminUserResponseDTO;
		}

		List<SsoSolicitud> sols = new ArrayList<SsoSolicitud>();
		String q = "select s  from SsoSolicitud s WHERE s.desUsrCurp =:curp";
		try {
			Query query = em.createQuery(q);
			query.setParameter("curp", curp);
			query.setFlushMode(FlushModeType.AUTO);
			query.setHint("javax.persistence.cache.storeMode", "REFRESH");
			sols = query.getResultList();
			if (sols != null && !sols.isEmpty()) {
				adminUserResponseDTO.setCodigo("0001");
				adminUserResponseDTO
						.setDescripcion("Usuario recuperado exitosamente");
				adminUserResponseDTO.setUsuarioIdentidadDTO(transformaDTO(sols
						.get(0)));
			} else {
				adminUserResponseDTO.setCodigo("0002");
				adminUserResponseDTO.setDescripcion("Usuario no encontrado");
			}
		} catch (Exception e) {
			adminUserResponseDTO.setCodigo("0004");
			adminUserResponseDTO
					.setDescripcion("Ocurrio un error al realizar la peticion");
			e.printStackTrace();
			return adminUserResponseDTO;
		}
		return adminUserResponseDTO;
	}

	private UsuarioIdentidadDTO transformaDTO(SsoSolicitud sol) {
		UsuarioIdentidadDTO usuarioIdentidadDTO = new UsuarioIdentidadDTO();
		usuarioIdentidadDTO.setNombres(sol.getNomNombre());
		usuarioIdentidadDTO.setApellidoMaterno(sol.getNomMaterno());
		usuarioIdentidadDTO.setApellidoPaterno(sol.getNomPaterno());

		if (sol.getDicDelegacion() != null) {
			usuarioIdentidadDTO.setClaveDelegacion(Integer.valueOf(sol
					.getDicDelegacion().getClaveDelegacion()));
			if (sol.getDicDelegacion() != null) {
				usuarioIdentidadDTO.setDescripcionDelegacion(sol
						.getDicDelegacion().getDesDeleg());
			}
		}
		if (sol.getDicSubdelegacion() != null) {
			usuarioIdentidadDTO.setClaveSubDelegacion(Integer.valueOf(sol
					.getDicSubdelegacion().getClaveSubdelegacion()));
			if (sol.getDicSubdelegacion() != null) {
				usuarioIdentidadDTO.setDescripcionSubDelegacion(sol
						.getDicSubdelegacion().getDesSubdelegacion());
			}
		}

		if (sol.getSsoCatdepartamento() != null) {
			usuarioIdentidadDTO.setClaveDepartamento((int) sol
					.getSsoCatdepartamento().getCveSsodepto());
			usuarioIdentidadDTO.setDescripcionDepartamento(sol
					.getSsoCatdepartamento().getDesDepartamento());
			if (sol.getSsoCatdepartamento().getSsoCatareanormativa() != null) {
				usuarioIdentidadDTO.setClaveAreaNormativa((int) sol
						.getSsoCatdepartamento().getSsoCatareanormativa()
						.getCveSsoareanorma());
				usuarioIdentidadDTO.setDescripcionArea(sol
						.getSsoCatdepartamento().getSsoCatareanormativa()
						.getDesAreanorma());
			}
		}
		return usuarioIdentidadDTO;
	}

	@Override
	public ResponsablesDelegacionDTO recuperaResponsables(int claveDelegacion, int claveSubdelegacion, String roles, int claveModulo) {
		// TODO Auto-generated method stub
//		System.out.println("ClaveDelegacion "+claveDelegacion+" ClaveSubdelegacion "+claveSubdelegacion);		
		ResponsablesDelegacionDTO response=new ResponsablesDelegacionDTO();
		
		if(claveDelegacion<=0){
			response.setClave(1);
			response.setMensaje("Parametros de b�squeda incorrectos.");
			return response;
		}
		
	//Version antes de integracion de perfiles SISEC
//		StringBuffer queryString=new StringBuffer();
//		queryString.append(" SELECT ssoSolicitud.desUsrCurp,ssoSolicitud.nomNombre,ssoSolicitud.nomMaterno,ssoSolicitud.nomPaterno, ");
//		queryString.append(" ssoSolicitud.ssoCatpuesto.desPuesto, ssoSolicitud.cveMatricula,ssoSolicitud.refCorreoElectronico ");
//		queryString.append(" from AreaNormativa areaNormativa,SsoCatdepartamento ssoCatdepartamento,SsoSolicitud ssoSolicitud, AccesoModulo accesoModulo, ");
//		queryString.append(" SsoCatdeptomodulo ssoCatdeptomodulo, Modulo modulo, Delegacion delegacion, Subdelegacion subdelegacion ");
//		queryString.append(" WHERE areaNormativa.idAreaNormativa = ssoCatdepartamento.ssoCatareanormativa.cveSsoareanorma ");
//		queryString.append(" AND ssoCatdepartamento.cveSsodepto = ssoSolicitud.ssoCatdepartamento.cveSsodepto ");
//		queryString.append(" AND ssoSolicitud.dicDelegacion.cveIdDelegacion =  delegacion.idDelegacion ");
//		queryString.append(" AND ssoSolicitud.dicSubdelegacion.cveIdSubdelegacion =  subdelegacion.idSubdelegacion ");
//		queryString.append(" AND ssoSolicitud.cveSsosolicitud = accesoModulo.solicitud.idSolicitud ");
//		queryString.append(" AND accesoModulo.deptoModulo.cveDeptoModulo = ssoCatdeptomodulo.cveSsodeptomodulo ");
//		queryString.append(" AND ssoCatdeptomodulo.dicModulo.cveIdModulo = modulo.idModulo ");
//		queryString.append(" AND modulo.idModulo = " + claveModulo );
//		queryString.append(" AND ssoSolicitud.ssoCatestatus.cveSsoestatus = 2 ");
//		queryString.append(" AND delegacion.idDelegacion =  " + claveDelegacion);
//		if(claveSubdelegacion>0){
//			queryString.append(" AND subdelegacion.idSubdelegacion ="+claveSubdelegacion);	
//		}
//		queryString.append(" AND ssoSolicitud.ssoCatpuesto.cveSsopuesto IN ("+roles+") ");
//		queryString.append(" ORDER BY areaNormativa.idAreaNormativa, ssoCatdepartamento.cveSsodepto ");
		
		
		
		
		// VERSION JORGE
		
//		SELECT a.CVE_SSOSOLICITUD, a.DES_USR_CURP, a.NOM_NOMBRE, a.NOM_PATERNO, a.NOM_MATERNO
//		FROM SSO_CATAREANORMATIVA E, SSO_CATDEPARTAMENTO F, SSO_SOLICITUD A, SSO_ACCESOMODULOS K, 
//		SSO_CATDEPTOMODULO M, DIC_MODULO N, sso_perfilessol b
//		WHERE e.CVE_SSOAREANORMA=f.CVE_SSOAREANORMA
//		AND f.CVE_SSODEPTO=a.CVE_SSODEPTO 		
//		AND a.CVE_SSOSOLICITUD=k.CVE_SSOSOLICITUD		
//		AND k.CVE_SSODEPTOMODULO=m.CVE_SSODEPTOMODULO		
//		AND m.CVE_ID_MODULO=n.CVE_ID_MODULO		
//		and a.CVE_SSOSOLICITUD = b.CVE_SSOSOLICITUD
		
//		and a.CVE_ID_DELEGACION = 14
//		and a.CVE_ID_SUBDELEGACION = 51
//		and n.CVE_ID_MODULO = 18
//		and b.CVE_SSOPUESTO = 76
		
//		group by a.CVE_SSOSOLICITUD, a.DES_USR_CURP, a.NOM_NOMBRE, a.NOM_PATERNO, a.NOM_MATERNO
//		ORDER BY a.DES_USR_CURP
		
		StringBuffer queryString=new StringBuffer();
		queryString.append("SELECT a.desUsrCurp as curp,a.nomNombre as nombre,a.nomMaterno as materno,a.nomPaterno as paterno,a.ssoCatpuesto.desPuesto as despuesto, a.cveMatricula as matricula,a.refCorreoElectronico as correo FROM SsoCatareanormativa e,SsoCatdepartamento f,SsoSolicitud a,SsoAccesomodulo k,SsoCatdeptomodulo m,DicModulo n,SsoPerfilessol b  ");
		queryString.append("  WHERE e.cveSsoareanorma=f.ssoCatareanormativa.cveSsoareanorma AND ");
		queryString.append(" f.cveSsodepto=a.ssoCatdepartamento.cveSsodepto AND ");
		queryString.append(" a.cveSsosolicitud=k.ssoSolicitud.cveSsosolicitud AND ");
		queryString.append(" k.ssoCatdeptomodulo.cveSsodeptomodulo=m.cveSsodeptomodulo AND ");
		queryString.append(" m.dicModulo.cveIdModulo=n.cveIdModulo AND ");
		queryString.append(" a.cveSsosolicitud=b.ssoSolicitud.cveSsosolicitud AND ");
		queryString.append(" a.dicDelegacion.cveIdDelegacion="+claveDelegacion+" AND");
		if(claveSubdelegacion > 0) {
			queryString.append(" a.dicSubdelegacion.cveIdSubdelegacion="+claveSubdelegacion+" AND");
		}		
		queryString.append(" n.cveIdModulo="+claveModulo+" AND ");
		queryString.append(" a.ssoCatestatus.cveSsoestatus=2 AND ");
		queryString.append(" b.ssoCatpuesto.cveSsopuesto IN("+roles+") ");
		queryString.append(" GROUP BY curp,nombre,materno,paterno,despuesto,matricula,correo ");
		queryString.append(" ORDER BY nombre,materno,paterno  ");
		
		
//		StringBuffer queryString=new StringBuffer();
//		queryString.append(" SELECT ssoSolicitud.desUsrCurp as curp,ssoSolicitud.nomNombre as nombre,ssoSolicitud.nomMaterno as materno,ssoSolicitud.nomPaterno as paterno, ");
//		queryString.append(" ssoSolicitud.ssoCatpuesto.desPuesto as despuesto, ssoSolicitud.cveMatricula as matricula,ssoSolicitud.refCorreoElectronico as correo ");
//		queryString.append(" from AreaNormativa areaNormativa,SsoCatdepartamento ssoCatdepartamento,SsoSolicitud ssoSolicitud,SsoPerfilessol perfil,SsoCatpuesto puesto, AccesoModulo accesoModulo, ");
//		queryString.append(" SsoCatdeptomodulo ssoCatdeptomodulo, Modulo modulo, Delegacion delegacion, Subdelegacion subdelegacion ");
//		queryString.append(" WHERE areaNormativa.idAreaNormativa = ssoCatdepartamento.ssoCatareanormativa.cveSsoareanorma ");
//		queryString.append(" AND ssoCatdepartamento.cveSsodepto = ssoSolicitud.ssoCatdepartamento.cveSsodepto ");
//		queryString.append(" AND ssoSolicitud.dicDelegacion.cveIdDelegacion =  delegacion.idDelegacion ");
//		queryString.append(" AND ssoSolicitud.dicSubdelegacion.cveIdSubdelegacion =  subdelegacion.idSubdelegacion ");
//		queryString.append(" AND ssoSolicitud.cveSsosolicitud = accesoModulo.solicitud.idSolicitud ");
//		queryString.append(" AND accesoModulo.deptoModulo.cveDeptoModulo = ssoCatdeptomodulo.cveSsodeptomodulo ");
//		queryString.append(" AND ssoCatdeptomodulo.dicModulo.cveIdModulo = modulo.idModulo ");
//		queryString.append(" AND ssoSolicitud.cveSsosolicitud = perfil.ssoSolicitud.cveSsosolicitud  ");
//		queryString.append(" AND perfil.ssoCatpuesto.cveSsopuesto = puesto.cveSsopuesto ");
//		queryString.append(" AND modulo.idModulo = " + claveModulo );
//		queryString.append(" AND ssoSolicitud.ssoCatestatus.cveSsoestatus = 2 ");
//		queryString.append(" AND delegacion.idDelegacion =  " + claveDelegacion);
//		if(claveSubdelegacion>0){
//			queryString.append(" AND subdelegacion.idSubdelegacion ="+claveSubdelegacion);	
//		}		
//		queryString.append(" AND ssoSolicitud.ssoCatpuesto.cveSsopuesto IN ("+roles+") ");
//		queryString.append(" GROUP BY curp,nombre,materno,paterno,despuesto,matricula,correo ");
//		queryString.append(" ORDER BY nombre,materno,paterno  ");
		
		try{
			System.out.println("Consulta "+queryString.toString());
			Query query = em.createQuery(queryString.toString());
			List<Object[]> valores=query.getResultList();
			System.out.println("Total de valores "+valores.size());
			List<ResponsableDTO> responsables=new ArrayList<ResponsableDTO>();
			ResponsableDTO res;
			Object[] registro;
			for(Object ob:valores){
				registro=(Object[]) ob;
				res=new ResponsableDTO();		
				res.setCurp((String) registro[0]);
				res.setNombre((String) registro[1]);
				res.setSegundoApellido((String) registro[2]);
				res.setPrimerApellido((String) registro[3]);
				res.setPuesto((String) registro[4]);
				if(registro[5]!=null){
					res.setMatricula(Integer.parseInt((String) registro[5]));
				}			
				res.setCorreoElectronico((String) registro[6]);
				responsables.add(res);
			}
			response.setClave(0);
			response.setMensaje("Consulta Exitosa");
			response.setResponsables(responsables);
			
			if(valores.isEmpty()){
				response.setClave(2);
				response.setMensaje("No existen datos");			
			}
			
		}catch(Exception e){
			e.printStackTrace();
			response.setClave(3);
			response.setMensaje("El sistema no esta disponible.");
		}
		return response;
	}

	@Override
	public AnalistaDictamenDTO obtenerAnalistaNivelCentralPorCurp(String curp) throws AdmonUsuariosException {
		AnalistaDictamenDTO analistaDictamenDTO = null;
		StringBuilder queryString = new StringBuilder();
		
		try {
			queryString.append("SELECT ");
			queryString.append("solicitud.desUsrCurp, solicitud.nomNombre, ");
			queryString.append("solicitud.nomPaterno, solicitud.nomMaterno, puesto.desPuesto, ");
			queryString.append("solicitud.cveMatricula, solicitud.refCorreoElectronico, ");
			queryString.append("solicitud.ssoCatestatus.cveSsoestatus, estatus.desEstatus ");
			queryString.append("FROM ");
			queryString.append("SsoSolicitud solicitud, SsoPerfilessol perfil, SsoCatpuesto puesto, SsoCatestatus estatus ");
			queryString.append("WHERE ");
			queryString.append("solicitud.cveSsosolicitud = perfil.ssoSolicitud.cveSsosolicitud ");
			queryString.append("AND perfil.ssoCatpuesto.cveSsopuesto = puesto.cveSsopuesto ");
			queryString.append("AND solicitud.ssoCatestatus.cveSsoestatus = ESTATUS.cveSsoestatus ");
			queryString.append("AND puesto.cveSsopuesto = "+Constantes.PUESTO_ANALISTA_DICTAMEN);
			queryString.append(" AND solicitud.desUsrCurp = '"+curp+"'");
			
			Query query = em.createQuery(queryString.toString());
			List<Object[]> listObjects = query.getResultList();
			
			if (listObjects != null && !listObjects.isEmpty() && listObjects.size() != 0) {
				Object[] object = listObjects.get(0);
				
				analistaDictamenDTO = new AnalistaDictamenDTO();
				analistaDictamenDTO.setDesUsrCurp((String) object[0]);
				analistaDictamenDTO.setNomNombre((String) object[1]);
				analistaDictamenDTO.setNomPaterno((String) object[2]);
				analistaDictamenDTO.setNomMaterno((String) object[3]);
				analistaDictamenDTO.setDesPuesto((String) object[4]);
				analistaDictamenDTO.setCveMatricula((String) object[5]);
				analistaDictamenDTO.setRefCorreoElectronico((String) object[6]);
				analistaDictamenDTO.setCveSsoEstatus((Long) object[7]);
				analistaDictamenDTO.setDesEstatus((String) object[8]);
				
			} else {
				throw new AdmonUsuariosException("No existen registros de Usuarios con la curp "+curp);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AdmonUsuariosException("No se pudo obtener el Analista de Dictamen con la curp "+curp);
		}
		return analistaDictamenDTO;
	}

	@Override
	public List<AnalistaDictamenDTO> obtenerAnalistasNivelCentral() throws AdmonUsuariosException {
		List<AnalistaDictamenDTO> listAnalistasDictamenDTOs = null;
		StringBuilder queryString = new StringBuilder();
		try {
			queryString.append("SELECT ");
			queryString.append("solicitud.desUsrCurp, solicitud.nomNombre, ");
			queryString.append("solicitud.nomPaterno, solicitud.nomMaterno, puesto.desPuesto, ");
			queryString.append("solicitud.cveMatricula, solicitud.refCorreoElectronico, ");
			queryString.append("solicitud.ssoCatestatus.cveSsoestatus, estatus.desEstatus ");
			queryString.append("FROM ");
			queryString.append("SsoSolicitud solicitud, SsoPerfilessol perfil, SsoCatpuesto puesto, SsoCatestatus estatus ");
			queryString.append("WHERE ");
			queryString.append("solicitud.cveSsosolicitud = perfil.ssoSolicitud.cveSsosolicitud ");
			queryString.append("AND perfil.ssoCatpuesto.cveSsopuesto = puesto.cveSsopuesto ");
			queryString.append("AND solicitud.ssoCatestatus.cveSsoestatus = " +Constantes.ESTATUS.AUTORIZADO.getOpcion()+ " ");
			queryString.append("AND solicitud.ssoCatestatus.cveSsoestatus = ESTATUS.cveSsoestatus ");
			queryString.append("AND puesto.cveSsopuesto = "+Constantes.PUESTO_ANALISTA_DICTAMEN);
			
			Query query = em.createQuery(queryString.toString());
			List<Object[]> listObjects = query.getResultList();
			
			Object[] registro;
			AnalistaDictamenDTO analistaDictamenDTO;
			
			if (listObjects != null && !listObjects.isEmpty() && listObjects.size() != 0) {
				
				listAnalistasDictamenDTOs = new ArrayList<AnalistaDictamenDTO>();
				
				for (Object[] objects : listObjects) {
					registro = (Object[]) objects;
					analistaDictamenDTO = new AnalistaDictamenDTO();
					analistaDictamenDTO.setDesUsrCurp((String) registro[0]);
					analistaDictamenDTO.setNomNombre((String) registro[1]);
					analistaDictamenDTO.setNomPaterno((String) registro[2]);
					analistaDictamenDTO.setNomMaterno((String) registro[3]);
					analistaDictamenDTO.setDesPuesto((String) registro[4]);
					analistaDictamenDTO.setCveMatricula((String) registro[5]);
					analistaDictamenDTO.setRefCorreoElectronico((String) registro[6]);
					analistaDictamenDTO.setCveSsoEstatus((Long) registro[7]);
					analistaDictamenDTO.setDesEstatus((String) registro[8]);
					
					listAnalistasDictamenDTOs.add(analistaDictamenDTO);
				}
			} else {
				throw new AdmonUsuariosException("NO se obtuvieron resultados en la busqueda.");
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AdmonUsuariosException("No se pudo obtener la lista de Analistas de Dictamen de la base de datos.");
		}
		return listAnalistasDictamenDTOs;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public UsuarioInfoDTO consultaInfoUsuarioPorCURP(String curp) {
		UsuarioInfoDTO usuario = null;
		List<SsoSolicitud> sols = new ArrayList<SsoSolicitud>();
		String q = "select s from SsoSolicitud s WHERE s.desUsrCurp =:curp";
		try {
			Query query = em.createQuery(q);
			query.setParameter("curp", curp);
			query.setFlushMode(FlushModeType.AUTO);
			query.setHint("javax.persistence.cache.storeMode", "REFRESH");
			sols = query.getResultList();
			if (sols != null && !sols.isEmpty()) {
				SsoSolicitud sol = sols.get(0);
				usuario = new UsuarioInfoDTO();
				usuario.setApellidoMaterno(sol.getNomMaterno());
				usuario.setApellidoPaterno(sol.getNomPaterno());
				usuario.setNombre(sol.getNomNombre());
				usuario.setCorreoElectronico(sol.getRefCorreoElectronico());
				usuario.setEstado((int)sol.getSsoCatestatus().getCveSsoestatus());
			}			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return usuario;
	}
	
}
