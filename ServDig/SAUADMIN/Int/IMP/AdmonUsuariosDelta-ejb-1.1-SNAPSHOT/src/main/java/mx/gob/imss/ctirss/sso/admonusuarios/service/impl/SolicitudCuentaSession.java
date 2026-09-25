/**
 * 
 */
package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.admonusuarios.entidad.SsoAccesomodulo;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoCatdeptomodulo;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoCatestatus;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoSolicitud;
import mx.gob.imss.ctirss.admonusuarios.entities.DeptoModulo;
import mx.gob.imss.ctirss.admonusuarios.entities.Estatus;
import mx.gob.imss.ctirss.admonusuarios.entities.Notificacion;
import mx.gob.imss.ctirss.admonusuarios.entities.PerfilesSolicitud;
import mx.gob.imss.ctirss.admonusuarios.entities.Puesto;
import mx.gob.imss.ctirss.admonusuarios.entities.Solicitudes;
import mx.gob.imss.ctirss.admonusuarios.entities.Delegacion;
import mx.gob.imss.ctirss.admonusuarios.entities.Subdelegacion;
import mx.gob.imss.ctirss.admonusuarios.entities.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.sso.admonusuarios.baseservice.GenericService;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.enums.EstadoSolicitud;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonPerfilesSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.services.CatalogosSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.services.SolicitudCuentaSessionLocal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author cesarAgustin
 * 
 */
@Stateless(name = "solicitudCuentaSession", mappedName = "solicitudCuentaSession")
public class SolicitudCuentaSession extends GenericService implements SolicitudCuentaSessionLocal {

	/** Log de la clase */
	private static Log log = LogFactory.getLog(SolicitudCuentaSession.class);

	@EJB
	private AdmonPerfilesSessionLocal admonPerfiles;
	@EJB
	private CatalogosSessionLocal catalogoService;

	private Long idSolicitudRegistrada = 0L;



	@Override
	public void registrarNotificacionCuentaNVer(UsuarioDTO usuarioSolicitud,
			Long sol) throws AdmonUsuariosException {
		String perfiles = "";
		try {
			Query query = em.createNamedQuery("Solicitudes.findById");
			query.setParameter("id", sol);
			Solicitudes solicitudes = (Solicitudes) query.getSingleResult();
			Notificacion notificacion = new Notificacion();
			notificacion.setFechaNotificacion(new Date());
			notificacion.setTipoNotificcion(1L);
			notificacion.setSolicitud(solicitudes);
			em.persist(notificacion);

		} catch (Exception ex) {
			System.out.println("Error general");
			ex.printStackTrace();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UsuarioDTO> obtenerSolicitudesAbiertas(Long idAprobador)
			throws AdmonUsuariosException {
		List<UsuarioDTO> listUsuariosTO = new ArrayList<UsuarioDTO>();

		return listUsuariosTO;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UsuarioDTO> obtenerSolicitudesPendientesPorAprobador(
			Long idAprobador) throws AdmonUsuariosException {
		List<UsuarioDTO> listUsuariosTO = new ArrayList<UsuarioDTO>();
		HashMap<Long, Long> map = new HashMap<Long, Long>();
		Long solicitudVal = 0L;
		// Query query = em.createNamedQuery("Aprobadores.findByMatricula");
		// query.setParameter("idMatricula",String.valueOf(idAprobador));
		// List<Aprobadores> listRegPorAprobacion = query.getResultList();

		/*
		 * for (Aprobadores aprobador : listRegPorAprobacion) { query =
		 * em.createNamedQuery("Solicitudes.findById"); query.setParameter("id",
		 * aprobador.getSolicitud().getIdSolicitud()); Solicitudes solicitud =
		 * (Solicitudes) query.getSingleResult(); solicitudVal =
		 * aprobador.getSolicitud().getIdSolicitud();
		 * if(map.get(solicitudVal)==null&&solicitud.getEstatus().intValue()
		 * ==1){ UsuarioDTO usuarioDTO = new UsuarioDTO();
		 * usuarioDTO.setIdSolicitud(solicitud.getIdSolicitud());
		 * usuarioDTO.setNombres(solicitud.getNomNombre());
		 * usuarioDTO.setApellidoPaterno(solicitud.getNomPaterno());
		 * usuarioDTO.setApellidoMaterno(solicitud.getNomMaterno());
		 * listUsuariosTO.add(usuarioDTO); map.put(solicitudVal, solicitudVal);
		 * } }
		 */
		map.clear();
		return listUsuariosTO;
	}

	@Override
	public UsuarioDTO obtenerDetalleSolicitud(Long idSolicitud)
			throws AdmonUsuariosException {
		System.out.println("idSolicitud " + idSolicitud);
		/*
		 * Query query = em.createNamedQuery("Solicitudes.findById");
		 * query.setParameter("id", idSolicitud); Solicitudes solicitud =
		 * (Solicitudes) query.getSingleResult();
		 * System.out.println("solicitud.getIdSolicitud() "
		 * +solicitud.getIdSolicitud());
		 * usuarioDTO.setIdSolicitud(solicitud.getIdSolicitud());
		 * usuarioDTO.setNombres(solicitud.getNomNombre());
		 * usuarioDTO.setApellidoPaterno(solicitud.getNomPaterno());
		 * usuarioDTO.setApellidoMaterno(solicitud.getNomMaterno());
		 * usuarioDTO.setCorreoElectronico(solicitud.getNomCorreoElectonico());
		 */
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setClaveDelegacion(-99);
		usuarioDTO.setClaveSubDelegacion(-99);
		usuarioDTO.setClaveUMF(-99);

		// if(solicitud.getCurp()!= null)
		usuarioDTO.setCurp("dsadasd"); // solicitud.getCurp()
		return usuarioDTO;
	}

	@Override
	public UsuarioDTO obtenerTodoDetalleSolicitud(Long idSolicitud)
			throws AdmonUsuariosException {
		// Llena todos los datos

		Query query = em.createNamedQuery("Solicitudes.findById");
		query.setParameter("id", idSolicitud);

		UsuarioDTO usuarioDTO = new UsuarioDTO();

		/*
		 * Solicitudes solicitud = (Solicitudes) query.getSingleResult();
		 * 
		 * UsuarioDTO usuarioDTO = new UsuarioDTO();
		 * usuarioDTO.setIdSolicitud(solicitud.getIdSolicitud());
		 * usuarioDTO.setMatricula(solicitud.getNomMatricula());
		 * usuarioDTO.setNombres(solicitud.getNomNombre());
		 * usuarioDTO.setApellidoPaterno(solicitud.getNomPaterno());
		 * usuarioDTO.setApellidoMaterno(solicitud.getNomMaterno());
		 * usuarioDTO.setCurp(solicitud.getCurp());
		 * usuarioDTO.setCorreoElectronico(solicitud.getNomCorreoElectonico());
		 */
		usuarioDTO.setTelefono("987654321");
		/*
		 * query = em.createNamedQuery("Departamento.findById");
		 * query.setParameter("id",
		 * solicitud.getDepartamento().getCveDepartamento()); Departamento dep =
		 * (Departamento) query.getSingleResult();
		 * 
		 * usuarioDTO.setClaveAreNormativa(dep.getAreaNormativa().getIdAreaNormativa
		 * ().intValue());
		 * usuarioDTO.setClaveDepartamento(dep.getCveDepartamento().intValue());
		 */
		usuarioDTO.setClavePuesto(-99); // solicitud.getPuesto().getCvePuesto().intValue()
		try {
			usuarioDTO.setClaveDelegacion(-99); // solicitud.getDelegacion().getIdDelegacion().intValue()
		} catch (Exception e) {
			usuarioDTO.setClaveDelegacion(-99);
		}
		try {
			usuarioDTO.setClaveSubDelegacion(-99); // solicitud.getSubdelegacion().getIdSubdelegacion().intValue()
		} catch (Exception e) {
			usuarioDTO.setClaveSubDelegacion(-99);
		}
		try {
			usuarioDTO.setClaveUMF(-99); // solicitud.getUnidadMedicaFamiliar().getIdUmf().intValue()
		} catch (Exception e) {
			usuarioDTO.setClaveUMF(-99);
		}
		usuarioDTO.setClaveModulo(-99L);

		return usuarioDTO;
	}

	@Override
	public void actualizarSolicitud(Long idSolicitud)
			throws AdmonUsuariosException {
		Query query = em.createNamedQuery("Solicitud.findId");
		query.setParameter("id", idSolicitud);

		Solicitudes solicitud = (Solicitudes) query.getSingleResult();
		solicitud.setEstatus(EstadoSolicitud.APROBADO.getId());

		em.persist(solicitud);
	}

	@Override
	public void actualizarSolicitudBD(Long idSolicitud, UsuarioDTO usuario)
			throws AdmonUsuariosException {

		Query query = em.createNamedQuery("Solicitudes.findById");
		query.setParameter("id", idSolicitud);

		Solicitudes solicitud = (Solicitudes) query.getSingleResult();
		solicitud.setEstatus(EstadoSolicitud.SOLICITADO.getId());
		solicitud.setNomCorreoElectonico(usuario.getCorreoElectronico());
		solicitud.setRegistroActualizado(new Date());
		em.persist(solicitud);
	}

	@Override
	public void actualizarSolicitudBDEliminacion(Long idSolicitud,
			UsuarioDTO usuario, int tipoBaja) throws AdmonUsuariosException {
		Query query = em.createNamedQuery("Solicitudes.findById");
		query.setParameter("id", idSolicitud);

		Solicitudes solicitud = (Solicitudes) query.getSingleResult();
		if (tipoBaja == 4)
			solicitud.setEstatus(EstadoSolicitud.BAJA.getId());
		else
			solicitud.setEstatus(EstadoSolicitud.BAJA_POR_CAMBIO_DE_CURP
					.getId());
		solicitud.setRegistroActualizado(new Date());
		em.persist(solicitud);
	}

	@Override
	public void actualizarSolicitudBDRecuperacion(Long idSolicitud,
			int delegacion, int subdelegacion, int umf)
			throws AdmonUsuariosException {
		Query query = em.createNamedQuery("Solicitudes.findById");
		query.setParameter("id", idSolicitud);
		Solicitudes solicitud = (Solicitudes) query.getSingleResult();

		solicitud.setEstatus(EstadoSolicitud.SOLICITADO.getId());
		if (delegacion != -99) {
			query = em.createNamedQuery("Delegacion.findId");
			query.setParameter("id", delegacion);
			Delegacion del = (Delegacion) query.getSingleResult();
			solicitud.setDelegacion(del);
		}
		if (subdelegacion != -99) {
			query = em.createNamedQuery("Subdelegacion.findSubID");
			query.setParameter("id", subdelegacion);
			Subdelegacion sdel = (Subdelegacion) query.getSingleResult();
			solicitud.setSubdelegacion(sdel);
		}
		if (umf != -99) {
			query = em.createNamedQuery("UnidadMedicaFamiliar.findUmfID");
			query.setParameter("id", umf);
			UnidadMedicaFamiliar unidadmedico = (UnidadMedicaFamiliar) query
					.getSingleResult();
			solicitud.setUnidadMedicaFamiliar(unidadmedico);
		}
		solicitud.setRegistroActualizado(new Date());
		em.persist(solicitud);
	}

	@Override
	public void actualizarSolicitudAprobadaBD(Long idSolicitud,
			UsuarioDTO usuario) throws AdmonUsuariosException {
		/*
		 * Query query = em.createNamedQuery("Solicitudes.findById");
		 * query.setParameter("id", idSolicitud);
		 * 
		 * Solicitudes solicitud = (Solicitudes) query.getSingleResult();
		 * solicitud.setEstatus(EstadoSolicitud.APROBADO.getId());
		 * solicitud.setRegistroActualizado(new Date()); em.persist(solicitud);
		 */
	}

	@Override
	public void actualizarSolicitudRechazadaBD(Long idSolicitud,
			UsuarioDTO usuario) throws AdmonUsuariosException {
		/*
		 * Query query = em.createNamedQuery("Solicitudes.findById");
		 * query.setParameter("id", idSolicitud);
		 * 
		 * Solicitudes solicitud = (Solicitudes) query.getSingleResult();
		 * solicitud.setEstatus(EstadoSolicitud.RECHAZADO.getId());
		 * solicitud.setRegistroActualizado(new Date()); em.persist(solicitud);
		 */
	}

	@Override
	public void eliminaPerfilesModulosBD(Long idSolicitud, UsuarioDTO usuario)
			throws AdmonUsuariosException {

		Query query = em.createNamedQuery("PerfilesSolicitud.deleteAllByClave");
		query.setParameter("id", idSolicitud);
		int results = query.executeUpdate();
		em.flush();
		query = em.createNamedQuery("AccesoModulo.deleteAllByClave");
		query.setParameter("id", idSolicitud);
		results = query.executeUpdate();
		em.flush();
		query = em.createNamedQuery("Notificacion.deleteAllByClave");
		query.setParameter("id", idSolicitud);
		results = query.executeUpdate();
		em.flush();
		query = em.createNamedQuery("Aprobadores.deleteAllByClave");
		query.setParameter("id", idSolicitud);
		results = query.executeUpdate();
		em.flush();
	}

	@Override
	public void registrarPerfiles(long idSolicitud, int cvePuesto,String defaultRol) throws AdmonUsuariosException {
		try {
			Query query = em.createNamedQuery("Solicitudes.findById");
			query.setParameter("id", idSolicitud);
			Solicitudes solicitudes = (Solicitudes) query.getSingleResult();

			query = em.createNamedQuery("Puesto.findByClave");
			query.setParameter("id", cvePuesto);
			Puesto puesto = (Puesto) query.getSingleResult();

			PerfilesSolicitud perSol = new PerfilesSolicitud();
			perSol.setSolicitud(solicitudes);
			perSol.setPuesto(puesto);
			perSol.setFechaRegistro(new Date());
			perSol.setDefaultRol(defaultRol);
			em.persist(perSol);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdmonUsuariosException();
		}
	}

	@Override
	public void registrarModulos(Long idSolicitud, int cveDepto, int cveMod,
			int status) throws AdmonUsuariosException {
		String q = "select s  from SsoSolicitud s WHERE s.cveSsosolicitud =:id";
		Query query = em.createQuery(q);
		query.setParameter("id", idSolicitud);
		List<SsoSolicitud> sols = query.getResultList();
		SsoSolicitud solOriginal = sols.get(0);

		SsoAccesomodulo accMod = new SsoAccesomodulo();
		accMod.setFecFechareg(new Date());
		accMod.setSsoAprobador(null);
		accMod.setSsoCatestatus(getCatStatus(1));
		accMod.setSsoSolicitud(solOriginal);
		accMod.setSsoCatdeptomodulo(getCatDeptoModulo(cveMod, cveDepto));
		super.saveorupdate(accMod);

	}

	private SsoCatdeptomodulo getCatDeptoModulo(long cveIdModulo,
			long cveIdDepto) {
		final Query q = em
				.createQuery("select s from SsoCatdeptomodulo s where s.dicModulo.cveIdModulo = :idMod and s.ssoCatdepartamento.cveSsodepto = :idDepto");
		q.setParameter("idMod", cveIdModulo);
		q.setParameter("idDepto", cveIdDepto);
		return (SsoCatdeptomodulo) q.getResultList().get(0);
	}

	private SsoCatestatus getCatStatus(long cveEstatus) {
		final Query q = em
				.createQuery("select s from SsoCatestatus s where s.cveSsoestatus = :id");
		q.setParameter("id", cveEstatus);
		return (SsoCatestatus) q.getResultList().get(0);
	}

	@Override
	public void eliminarPerfiles(Long idSolicitud, int cvePuesto)
			throws AdmonUsuariosException {
		Query query = em
				.createNamedQuery("PerfilesSolicitud.deleteAllByClavePerfil");
		query.setParameter("idSol", idSolicitud);
		query.setParameter("idPuesto", cvePuesto);
		int results = query.executeUpdate();
		em.flush();
	}

	@Override
	public void eliminarModulos(Long idSolicitud, int cveDepto, int cveMod)
			throws AdmonUsuariosException {
		Query query = em.createNamedQuery("DeptoModulo.findByClaves");
		query.setParameter("cveDepto", cveDepto);
		query.setParameter("cveModulo", cveMod);
		List mods = query.getResultList();
		DeptoModulo deptoMod = (DeptoModulo) mods.get(0);

		query = em.createNamedQuery("AccesoModulo.deleteAllByClaveDepMod");
		query.setParameter("idDeptoMod", deptoMod.getCveDeptoModulo());
		query.setParameter("idSol", idSolicitud);
		int results = query.executeUpdate();
		em.flush();
	}

	@Override
	public void eliminarModulosId(int cveMod) throws AdmonUsuariosException {
		Query query = em.createNamedQuery("AccesoModulo.deleteAllById");
		query.setParameter("id", cveMod);
		int results = query.executeUpdate();
		em.flush();
	}

	@Override
	public void eliminarAprobador(Long idSolicitud, int cveDepto, int cveMod)
			throws AdmonUsuariosException {
		Query query = em.createNamedQuery("Aprobadores.deleteAllByClavesMod");
		query.setParameter("idSol", idSolicitud);
		query.setParameter("idMod", cveMod);
		int results = query.executeUpdate();
		em.flush();
	}

	public Throwable getCause(Throwable t) {
		return t.getCause();
	}

	@Override
	public String buscaCurpExistente(String curp) throws AdmonUsuariosException {
		Query query = em.createNamedQuery("Solicitudes.findCurp");
		query.setParameter("idcurp", curp);
		List<Solicitudes> sols = query.getResultList();
		if (sols != null && sols.size() > 0) {
			Solicitudes solicitud = (Solicitudes) sols.get(0);
			if (solicitud != null)
				return solicitud.getCurp();
		}
		return "";
	}

	@Override
	public String buscaEstatusSolicitud(String curp)
			throws AdmonUsuariosException {
		String respuesta = "";
		Query query = em.createNamedQuery("Solicitudes.findCurp");
		query.setParameter("idcurp", curp);
		Solicitudes solicitud = (Solicitudes) query.getSingleResult();
		em.flush();
		query = em.createNamedQuery("Estatus.findByClave");
		query.setParameter("id", solicitud.getEstatus().intValue());
		Estatus estatus = (Estatus) query.getSingleResult();
		em.flush();
		return estatus.getNombreEstatus();
	}

	@Override
	public Long obtenerIDSolicitud(String curp) throws AdmonUsuariosException {
		Long respuesta = buscaSolByCurp(curp);
		return respuesta;
	}

	@Override
	public Long buscaSolByCurp(String curp) throws AdmonUsuariosException {
		Query query = em.createNamedQuery("Solicitudes.findCurp");
		query.setParameter("idcurp", curp);
		Solicitudes sol = (Solicitudes) query.getSingleResult();
		return sol.getIdSolicitud();
	}

	@Override
	public String obtenerDatosUsuarioEnLDAP(String curp)
			throws AdmonUsuariosException {

		String respuesta = "";
		/*
		 * Query query = em.createNamedQuery("Solicitud.findCurp");
		 * query.setParameter("idcurp", curp); Solicitudes solicitud =
		 * (Solicitudes) query.getSingleResult();
		 */
		respuesta = "respuesta";
		// respuesta =
		// solicitud.getDescripcionPerfil()+" "+solicitud.getDescripcionGrupos()+" "+solicitud.getDescripcionSistemas();
		return respuesta;
	}



	@Override
	public void registrarCambioUsuarioExterno(UsuarioDTO usr)
			throws AdmonUsuariosException {
		// TODO Auto-generated method stub

	}
}
