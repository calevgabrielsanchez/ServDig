package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import mx.gob.imss.ctirss.admonusuarios.entidad.SsoActivaCuenta;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoCatestatus;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoSolicitud;
import mx.gob.imss.ctirss.sso.admonusuarios.baseservice.GenericService;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ActivaCuentaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SolicitudDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.service.ActivaCuentaServiceLocal;

@Stateless(name = "activarCuenta", mappedName = "activarCuenta")
public class ActivaCuentaServiceImpl extends GenericService implements ActivaCuentaServiceLocal{


	
	public static long ESTATUS_CENTA_INACTIVA = 11;
	public static long ESTATUS_CUENTA_ACTIVA = 12;
	
	@Override
	public ActivaCuentaDTO obtenActivaCuentaBySolicitud(SolicitudDTO sol)throws AdmonUsuariosException {
		SsoActivaCuenta acCuenta = getSsoActivaCuenta(sol.getCveSsosolicitud());
		if(acCuenta!=null)
			return getActivarCuentaDTO(acCuenta);
		else
			return null;
	}
	
	
	@Override
	public void registraActivaCuenta(SolicitudDTO sol)throws AdmonUsuariosException {
		SsoActivaCuenta aC = new SsoActivaCuenta();
		aC.setSsoSolicitud(new SsoSolicitud(sol.getCveSsosolicitud()));
		aC.setSsoEstatus(new SsoCatestatus(ESTATUS_CENTA_INACTIVA));
		saveorupdate(aC);
	}

	private SsoActivaCuenta getSsoActivaCuenta(	long id) {
		final Query q = em.createQuery("select s from SsoActivaCuenta s where s.ssoSolicitud.cveSsosolicitud = :id and s.ssoEstatus.cveSsoestatus = 11");
		q.setParameter("id", id);
		return (SsoActivaCuenta) q.getResultList().get(0);
	}

	private SsoActivaCuenta getSsoActivaCuentaMD5(String md5) {
		final Query q = em.createQuery("select s from SsoActivaCuenta s where s.claveMD5 = :md5");
		q.setParameter("md5", md5);
		return (SsoActivaCuenta) q.getResultList().get(0);
	}


	@Override
	public ActivaCuentaDTO obtenActivaCuentaByClaveMD5(String md5)throws AdmonUsuariosException {
		SsoActivaCuenta acCuenta = getSsoActivaCuentaMD5(md5);
		if(acCuenta!=null)
			return getActivarCuentaDTO(acCuenta);
		else
			return null;
	}

	@Override
	public boolean actualizaEstatusActivaCuenta(ActivaCuentaDTO aC)throws AdmonUsuariosException {
		emf = em.getEntityManagerFactory();
		EntityManager em2 = emf.createEntityManager();
		SsoActivaCuenta aCuenta = em.find(SsoActivaCuenta.class, aC.getClaveActivaCuenta());
		aCuenta.setSsoEstatus(getCatStatus(aC.getEstatus().getCveSsoestatus()));
		aCuenta.setFechaActiva(aC.getFechaActiva());
		em2.merge(aCuenta);
		em2.flush();
		return true;
	}

	private SsoCatestatus getCatStatus(long cveEstatus) {
		final Query q = em.createQuery("select s from SsoCatestatus s where s.cveSsoestatus = :id");
		q.setParameter("id", cveEstatus);
		return (SsoCatestatus) q.getResultList().get(0);
	}


}
