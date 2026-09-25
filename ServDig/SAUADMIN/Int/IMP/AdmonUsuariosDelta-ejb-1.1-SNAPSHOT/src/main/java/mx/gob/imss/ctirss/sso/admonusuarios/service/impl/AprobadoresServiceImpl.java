package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.admonusuarios.entidad.SsoAprobador;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoCatestatus;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoSolicitud;
import mx.gob.imss.ctirss.sso.admonusuarios.baseservice.GenericService;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.AprobadorDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SolicitudDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.service.AprobadoresServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.BitacoraServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonRolesSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionLocal;


@Stateless (name="aprobadorService", mappedName="aprobadorService")
public class AprobadoresServiceImpl extends GenericService implements AprobadoresServiceLocal {

	private final static Logger logger = Logger.getLogger(AprobadoresServiceImpl.class);

	private static long ESTATUS_AUTORIZADO = 2;
	private static long ESTATUS_BAJA = 4;

	private static Long CERO_LONG = 0L;

	private static String ADMON_USUARIOS = "APROBADOR"; 

	@EJB
	private BitacoraServiceLocal bitacoraService;
	
	@EJB
	private AdmonUsuariosSessionLocal AdmonUsuariosService;
	
	@EJB
	private AdmonRolesSessionLocal AdmonRolesService;

	
	@SuppressWarnings("unchecked")
	@Override
	public List<AprobadorDTO> consultaAprobadoresBySolicitud(List<SolicitudDTO> sols) throws AdmonUsuariosException {
		List<AprobadorDTO> result = new ArrayList<AprobadorDTO>(); 
		for (SolicitudDTO sol : sols) 
		{
			final Query q = em.createQuery("select s from SsoAprobador s where s.ssoSolicitud.cveSsosolicitud = :id");
			q.setParameter("id", sol.getCveSsosolicitud());
			
			List<SsoAprobador> obj = q.getResultList();
			
			 if(obj!=null&&obj.size()>0)
			 {
				 SsoAprobador apr =  (SsoAprobador)obj.get(0);
				 AprobadorDTO dto = new AprobadorDTO();
				 dto.setCveIdAprobador(apr.getCveIdAprobador());
				 dto.setMatricula(apr.getCveMatricula());
				 dto.setSolicitud(getSolicitudDTO(apr.getSsoSolicitud()));
				 dto.setEstatus(getEstatusDTO(apr.getSsoCatestatus()));
				 result.add(dto);
			}
		}		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AprobadorDTO validaAprobadorByCurp(String curp) throws AdmonUsuariosException {
		AprobadorDTO result = null;

		final Query q = em.createQuery(
				"select s from SsoAprobador s where s.ssoSolicitud.desUsrCurp = :curp and s.ssoCatestatus.cveSsoestatus = :status");
		q.setParameter("curp", curp);
		q.setParameter("status", ESTATUS_AUTORIZADO);

		List<SsoAprobador> obj = q.getResultList();

		if (obj != null && obj.size() > 0) {
			logger.info("########## Se encontraron [" + obj.size() + "] registros del aprobador ##########");
//			result = new AprobadorDTO();
			SsoAprobador apr = (SsoAprobador) obj.get(0);
			AprobadorDTO dto = new AprobadorDTO();
			dto.setCveIdAprobador(apr.getCveIdAprobador());
			dto.setMatricula(apr.getCveMatricula());
			dto.setSolicitud(getSolicitudDTO(apr.getSsoSolicitud()));
			dto.setEstatus(getEstatusDTO(apr.getSsoCatestatus()));
			
			logger.debug(":::::: datos bitacora [" + dto.getSolicitud() != null ? dto.getSolicitud().datosBitacora() : " " + "] ::::::");
			
			// Valida si es admin
			if (apr.getAdmingral() != null && apr.getAdmingral() > CERO_LONG) {
				if (dto.getSolicitud().getDelegacionId() > CERO_LONG 
						&& dto.getSolicitud().getSubdelegacionId() == CERO_LONG
						&& dto.getSolicitud().getUmfId() == CERO_LONG
						&& (apr.getAdmingral().intValue() == 11 || apr.getAdmingral().intValue() == 33)) {
					dto.setTipoAprobadorDpes(11332);
				} else {
					dto.setTipoAprobador(apr.getAdmingral().intValue());
				}
			} else {
				if (dto.getSolicitud().getDelegacionId() > CERO_LONG 
						&& dto.getSolicitud().getSubdelegacionId() == CERO_LONG
						&& dto.getSolicitud().getUmfId() == CERO_LONG) {
					dto.setTipoAprobador(2);
				} else {
					dto.setTipoAprobador(0);
				}
			}
			result = dto;
		}
		return result;
	}


	@Override
	public void guarda(List<AprobadorDTO> solicitudes,List<AprobadorDTO> aprobadores) throws AdmonUsuariosException {
		if(aprobadores!=null&&aprobadores.size()>0)
		{
			for(AprobadorDTO ap :aprobadores)
			{
				if(ap.getCveIdAprobador()>0)
				{
					final Query q = em.createQuery("select s from SsoAprobador s where s.cveIdAprobador = :id");
					q.setParameter("id", ap.getCveIdAprobador());
					
					SsoAprobador aprobador = (SsoAprobador)q.getResultList().get(0);
					aprobador.setSsoCatestatus(getCatStatus(ESTATUS_AUTORIZADO));
					aprobador.setSsoSolicitud(getSolicitud(ap.getSolicitud().getCveSsosolicitud()));
					super.saveorupdate(aprobador);
					AdmonUsuariosService.agregaPerfilAprobador(aprobador.getSsoSolicitud().getDesUsrCurp(), ADMON_USUARIOS);
					AdmonRolesService.asignaRolUsuario(aprobador.getSsoSolicitud().getDesUsrCurp(), ADMON_USUARIOS);
				}
				else
				{
					SsoAprobador aprobador = new SsoAprobador();
					aprobador.setCveMatricula(ap.getSolicitud().getCveMatricula());
					aprobador.setSsoCatestatus(getCatStatus(ESTATUS_AUTORIZADO));
					aprobador.setSsoSolicitud(getSolicitud(ap.getSolicitud().getCveSsosolicitud()));
					super.saveorupdate(aprobador);
					AdmonUsuariosService.agregaPerfilAprobador(aprobador.getSsoSolicitud().getDesUsrCurp(), ADMON_USUARIOS);
					AdmonRolesService.asignaRolUsuario(aprobador.getSsoSolicitud().getDesUsrCurp(), ADMON_USUARIOS);
				}
			}
		}
		
		if(solicitudes!=null&&solicitudes.size()>0)
		{
			for(AprobadorDTO ap :solicitudes)
			{
				if(ap.getCveIdAprobador()>0)
				{
					final Query q = em.createQuery("select s from SsoAprobador s where s.cveIdAprobador = :id");
					q.setParameter("id", ap.getCveIdAprobador());
					
					SsoAprobador aprobador = (SsoAprobador)q.getResultList().get(0);
					aprobador.setSsoCatestatus(getCatStatus(ESTATUS_BAJA));
					aprobador.setSsoSolicitud(getSolicitud(ap.getSolicitud().getCveSsosolicitud()));
					super.saveorupdate(aprobador);
					AdmonUsuariosService.borrarPerfilAprobador(aprobador.getSsoSolicitud().getDesUsrCurp(), ADMON_USUARIOS);
					AdmonRolesService.revocaRolUsuario(aprobador.getSsoSolicitud().getDesUsrCurp(), ADMON_USUARIOS);
				}
			}
		}
		
	}

	
	@Override
	public void addAprobador(AprobadorDTO ap) throws AdmonUsuariosException {
		if(ap.getMatricula()==null)
			ap.setMatricula("");
		if(ap.getCveIdAprobador()>0)
		{
			final Query q = em.createQuery("select s from SsoAprobador s where s.cveIdAprobador = :id");
			q.setParameter("id", ap.getCveIdAprobador());
			
			SsoAprobador aprobador = (SsoAprobador)q.getResultList().get(0);
			aprobador.setSsoCatestatus(getCatStatus(ESTATUS_AUTORIZADO));
			aprobador.setSsoSolicitud(getSolicitud(ap.getSolicitud().getCveSsosolicitud()));
			super.saveorupdate(aprobador);
			AdmonUsuariosService.agregaPerfilAprobador(aprobador.getSsoSolicitud().getDesUsrCurp(), ADMON_USUARIOS);
			AdmonRolesService.asignaRolUsuario(aprobador.getSsoSolicitud().getDesUsrCurp(), ADMON_USUARIOS);
		}
		else
		{
			SsoAprobador aprobador = new SsoAprobador();
			aprobador.setCveMatricula(ap.getSolicitud().getCveMatricula());
			aprobador.setSsoCatestatus(getCatStatus(ESTATUS_AUTORIZADO));
			aprobador.setSsoSolicitud(getSolicitud(ap.getSolicitud().getCveSsosolicitud()));
			super.saveorupdate(aprobador);
			AdmonUsuariosService.agregaPerfilAprobador(aprobador.getSsoSolicitud().getDesUsrCurp(), ADMON_USUARIOS);
			AdmonRolesService.asignaRolUsuario(aprobador.getSsoSolicitud().getDesUsrCurp(), ADMON_USUARIOS);
		}
		
	}
	
	@Override
	public void addAprobadorDirDpes(AprobadorDTO ap, Long tipoAprobador) throws AdmonUsuariosException {
		if(ap.getMatricula()==null)
			ap.setMatricula("");
		if(ap.getCveIdAprobador()>0)
		{
			final Query q = em.createQuery("select s from SsoAprobador s where s.cveIdAprobador = :id");
			q.setParameter("id", ap.getCveIdAprobador());
			
			SsoAprobador aprobador = (SsoAprobador)q.getResultList().get(0);
			aprobador.setSsoCatestatus(getCatStatus(ESTATUS_AUTORIZADO));
			aprobador.setSsoSolicitud(getSolicitud(ap.getSolicitud().getCveSsosolicitud()));
			aprobador.setAdmingral(tipoAprobador);
			super.saveorupdate(aprobador);
			AdmonUsuariosService.agregaPerfilAprobador(aprobador.getSsoSolicitud().getDesUsrCurp(), ADMON_USUARIOS);
			AdmonRolesService.asignaRolUsuario(aprobador.getSsoSolicitud().getDesUsrCurp(), ADMON_USUARIOS);
		}
		else
		{
			SsoAprobador aprobador = new SsoAprobador();
			aprobador.setCveMatricula(ap.getSolicitud().getCveMatricula());
			aprobador.setSsoCatestatus(getCatStatus(ESTATUS_AUTORIZADO));
			aprobador.setSsoSolicitud(getSolicitud(ap.getSolicitud().getCveSsosolicitud()));
			aprobador.setAdmingral(tipoAprobador);
			super.saveorupdate(aprobador);
			AdmonUsuariosService.agregaPerfilAprobador(aprobador.getSsoSolicitud().getDesUsrCurp(), ADMON_USUARIOS);
			AdmonRolesService.asignaRolUsuario(aprobador.getSsoSolicitud().getDesUsrCurp(), ADMON_USUARIOS);
		}
		
	}

	
	@Override
	public void delAprobador(AprobadorDTO ap) throws AdmonUsuariosException 
	{
		if(ap.getMatricula()==null)
			ap.setMatricula("");
		final Query q = em.createQuery("select s from SsoAprobador s where s.ssoSolicitud.cveSsosolicitud = :id");
		q.setParameter("id", ap.getSolicitud().getCveSsosolicitud());
		
		SsoAprobador aprobador = (SsoAprobador)q.getResultList().get(0);
		aprobador.setSsoCatestatus(getCatStatus(ESTATUS_BAJA));
		aprobador.setSsoSolicitud(getSolicitud(ap.getSolicitud().getCveSsosolicitud()));
		super.saveorupdate(aprobador);
		AdmonUsuariosService.borrarPerfilAprobador(aprobador.getSsoSolicitud().getDesUsrCurp(), ADMON_USUARIOS);
		AdmonRolesService.revocaRolUsuario(aprobador.getSsoSolicitud().getDesUsrCurp(), ADMON_USUARIOS);
	}

	@Override
	public void bajaAprobador(String curp) throws AdmonUsuariosException 
	{
		final Query q = em.createQuery("select s from SsoAprobador s where s.ssoSolicitud.desUsrCurp = :curp");
		q.setParameter("curp", curp);
		List<SsoAprobador> aprobadores = q.getResultList();
		if(aprobadores!=null&&aprobadores.size()>0)
		{
			SsoAprobador aprobador = aprobadores.get(0);
			aprobador.setSsoCatestatus(getCatStatus(ESTATUS_BAJA));
			super.saveorupdate(aprobador);
			AdmonUsuariosService.borrarPerfilAprobador(aprobador.getSsoSolicitud().getDesUsrCurp(), ADMON_USUARIOS);
			AdmonRolesService.revocaRolUsuario(aprobador.getSsoSolicitud().getDesUsrCurp(), ADMON_USUARIOS);
		}
	}

	private SsoSolicitud getSolicitud(long cveSsosolicitud) {
		final Query q = em.createQuery("select s from SsoSolicitud s where s.cveSsosolicitud = :id");
		q.setParameter("id", cveSsosolicitud);
		return (SsoSolicitud)q.getResultList().get(0);
	}

	private SsoCatestatus getCatStatus(long cveEstatus) {
		final Query q = em.createQuery("select s from SsoCatestatus s where s.cveSsoestatus = :id");
		q.setParameter("id", cveEstatus);
		return (SsoCatestatus)q.getResultList().get(0);
	}

}
