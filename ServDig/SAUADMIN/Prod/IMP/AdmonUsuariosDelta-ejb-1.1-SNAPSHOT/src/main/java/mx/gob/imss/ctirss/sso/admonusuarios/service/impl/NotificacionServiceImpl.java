package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.admonusuarios.entidad.SsoActivaCuenta;
import mx.gob.imss.ctirss.sso.admonusuarios.baseservice.GenericService;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ActivaCuentaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SolicitudDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.service.NotificacionServiceLocal;

@Stateless(name = "notificacionService", mappedName = "notificacionService")
public class NotificacionServiceImpl extends GenericService implements NotificacionServiceLocal {

	private final static Logger logger = Logger.getLogger(NotificacionServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<ActivaCuentaDTO> searchNotificaciones(SolicitudDTO sol) throws AdmonUsuariosException {
		List<SsoActivaCuenta> acs = new ArrayList<SsoActivaCuenta>();
		String q = "select s  from SsoActivaCuenta s WHERE";
		try {
			if (sol.getDelDTO() != null) {
				if (sol.getSubdelDTO() != null) {
					if (sol.getUmfDTO() != null) {
						q = q + " s.ssoSolicitud.dicDelegacion.cveIdDelegacion = :del and s.ssoSolicitud.dicSubdelegacion.cveIdSubdelegacion = :subdel and s.ssoSolicitud.dicUmf.cveIdUmf = :umf and s.ssoEstatus.cveSsoestatus = 11";
						Query query = em.createQuery(q);
						query.setParameter("del", sol.getDelDTO().getCveDelegacion());
						query.setParameter("subdel", sol.getSubdelDTO().getCveSubelegacion());
						query.setParameter("umf", sol.getUmfDTO().getCveUmf());
						logger.debug("########## CONSULTA NOTIFICACIONES 1 ##########");
						logger.debug("########## DELEGACION [" + sol.getDelDTO().getCveDelegacion() + "] ##########");
						logger.debug("########## SUBDELEGACION [" + sol.getSubdelDTO().getCveSubelegacion() + "] ##########");
						logger.debug("########## UMF [" + sol.getUmfDTO().getCveUmf() + "] ##########");
						logger.debug("########## " + query.toString() + " ##########");
						acs = query.getResultList();
					} else {
						q = q + " s.ssoSolicitud.dicDelegacion.cveIdDelegacion = :del and s.ssoSolicitud.dicSubdelegacion.cveIdSubdelegacion = :subdel and s.ssoSolicitud.dicUmf is null and s.ssoEstatus.cveSsoestatus = 11";
						Query query = em.createQuery(q);
						query.setParameter("del", sol.getDelDTO().getCveDelegacion());
						query.setParameter("subdel", sol.getSubdelDTO().getCveSubelegacion());
						logger.debug("########## CONSULTA NOTIFICACIONES 2 ##########");
						logger.debug("########## DELEGACION [" + sol.getDelDTO().getCveDelegacion() + "] ##########");
						logger.debug("########## SUBDELEGACION [" + sol.getSubdelDTO().getCveSubelegacion() + "] ##########");
						logger.debug("########## " + query.toString() + " ##########");
						acs = query.getResultList();
					}
				} else {
					q = q + " s.ssoSolicitud.dicDelegacion.cveIdDelegacion = :del and s.ssoSolicitud.dicSubdelegacion is null and s.ssoSolicitud.dicUmf is null and s.ssoEstatus.cveSsoestatus = 11";
					Query query = em.createQuery(q);
					query.setParameter("del", sol.getDelDTO().getCveDelegacion());
					logger.debug("########## CONSULTA NOTIFICACIONES 3 ##########");
					logger.debug("########## DELEGACION [" + sol.getDelDTO().getCveDelegacion() + "] ##########");
					logger.debug("########## " + query.toString() + " ##########");
					acs = query.getResultList();
				}
			} else {
				q = q + " s.ssoSolicitud.dicDelegacion is null and s.ssoSolicitud.dicSubdelegacion is null and s.ssoSolicitud.dicUmf is null and (s.ssoSolicitud.ssoCatdepartamento.cveSsodepto = :depto or s.ssoSolicitud.ssoCatdepartamento.ssoCatdepartamentoPadre.cveSsodepto = :deptoPadre) and s.ssoEstatus.cveSsoestatus = 11";
				Query query = em.createQuery(q);
				query.setParameter("depto", sol.getDptoDTO().getCveSsodepto());
				query.setParameter("deptoPadre", sol.getDptoDTO().getCveSsodepto());
				logger.debug("########## CONSULTA NOTIFICACIONES 4 ##########");
				logger.debug("########## DEPARTAMENTO [" + sol.getDptoDTO().getCveSsodepto() + "] ##########");
				logger.debug("########## DEPARTAMENTO PADRE [" + sol.getDptoDTO().getCveSsodepto() + "] ##########");
				logger.debug("########## " + query.toString() + " ##########");
				acs = query.getResultList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getActivaCuentasDTO(acs);
	}

	@Override
	public boolean actualizaNotificaciones(ActivaCuentaDTO acDto) throws AdmonUsuariosException {
		emf = em.getEntityManagerFactory();
		EntityManager em2 = emf.createEntityManager();
		SsoActivaCuenta ac;
		ac = em.find(SsoActivaCuenta.class, acDto.getClaveActivaCuenta());
		ac.setFechaVigencia(acDto.getFechaVigencia());
		em2.merge(ac);
		em2.flush();
		return true;
	}

}
