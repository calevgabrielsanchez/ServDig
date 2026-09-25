package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.FirmaElectronicaSegPortType;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.FirmaElectronicaSegService;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.FirmaSimpleRequestType;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.FirmaSimpleResponseType;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.ObjectFactory;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.ResultadoType;

import mx.gob.imss.ctirss.admonusuarios.entidad.DicDelegacion;
import mx.gob.imss.ctirss.admonusuarios.entidad.DicDelegacion_;
import mx.gob.imss.ctirss.admonusuarios.entidad.DicModulo;
import mx.gob.imss.ctirss.admonusuarios.entidad.DicSubdelegacion;
import mx.gob.imss.ctirss.admonusuarios.entidad.DicSubdelegacion_;
import mx.gob.imss.ctirss.admonusuarios.entidad.DicUmf;
import mx.gob.imss.ctirss.admonusuarios.entidad.DicUmf_;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoAccesomodulo;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoAccesomodulo_;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoAprobador;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoCatdepartamento;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoCatdepartamento_;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoCatdeptomodulo;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoCatdeptomodulo_;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoCatestatus;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoCatestatus_;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoCatpuesto;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoPerfilessol;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoPerfilessol_;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoSolicitud;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoSolicitud_;
import mx.gob.imss.ctirss.admonusuarios.entities.Estatus;
import mx.gob.imss.ctirss.admonusuarios.entities.Solicitudes;
import mx.gob.imss.ctirss.admonusuarios.entities.UsuarioMovimientos;

import mx.gob.imss.ctirss.sso.admonusuarios.baseservice.GenericService;
import mx.gob.imss.ctirss.sso.admonusuarios.cte.Constantes;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.AreaNormativaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DepartamentoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.EstatusDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ModuloDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PerfilDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SolicitudDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.selloDigital.PeticionFirmadoSimple;
import mx.gob.imss.ctirss.sso.admonusuarios.selloDigital.RespuestaFirmadoSimple;
import mx.gob.imss.ctirss.sso.admonusuarios.service.BitacoraServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.MensajeriaSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.SolicitudServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonRolesSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.services.CatalogosSessionLocal;

import org.apache.log4j.Logger;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import org.springframework.core.io.ClassPathResource;

@Stateless(name = "solicitudCriteria", mappedName = "solicitudCriteria")
public class SolicitudServiceImpl extends GenericService implements SolicitudServiceLocal {

	private final static Logger logger = Logger.getLogger(SolicitudServiceImpl.class);

	@EJB
	private BitacoraServiceLocal bitacoraService;

	@EJB
	private MensajeriaSessionLocal mensajeria;

	@EJB
	private CatalogosSessionLocal catalogoService;

	@EJB
	private AdmonUsuariosSessionLocal AdmonUsuariosService;

	@EJB
	private AdmonRolesSessionLocal AdmonRolesService;

	private static final String ACTIVO = "Active";

	public static long ESTATUS_SOLICITADO = 1;
	public static long ESTATUS_AUTORIZADO = 2;

	public List<SolicitudDTO> solicitudesByFiltro(SolicitudDTO filtro, Long idEstatus,
			boolean quitaNulos, String curpExcluye) throws AdmonUsuariosException {
		List<SolicitudDTO> lista = null;
		try {
			cb = em.getCriteriaBuilder();
			CriteriaQuery<Tuple> criteria = cb.createTupleQuery();
			Root<SsoAccesomodulo> a = criteria.from(SsoAccesomodulo.class);
			Path<SsoSolicitud> sol = a.get(SsoAccesomodulo_.ssoSolicitud);

			criteria.multiselect(sol).distinct(true);
			Predicate condicion = null;
			DepartamentoDTO deptoPadre = deptoGeneralByIdDepto(filtro.getDptoDTO().getCveSsodepto());
			Long idDeptoGeneral = deptoPadre.getDeptoGeneralDTO().getCveSsodepto();
			List<Long> deptos = deptosByDeptoGeneral(idDeptoGeneral);

			if (filtro.getCveMatricula() != null 
					&& !filtro.getCveMatricula().equals("")) {
				condicion = cb.equal(a.get(SsoAccesomodulo_.ssoSolicitud)
								.get(SsoSolicitud_.cveMatricula),
						filtro.getCveMatricula());
			} else if (filtro.getDesUsrCurp() != null 
					&& !filtro.getDesUsrCurp().equals("")) {
				condicion = cb.equal(a.get(SsoAccesomodulo_.ssoSolicitud)
								.get(SsoSolicitud_.desUsrCurp),
						filtro.getDesUsrCurp());
			} else if (filtro.getUmfDTO() != null && filtro.getUmfDTO().getCveUmf() != null
					&& filtro.getUmfDTO().getCveUmf() > 0) {
				condicion = cb.equal(a.get(SsoAccesomodulo_.ssoSolicitud)
								.get(SsoSolicitud_.dicUmf).get(DicUmf_.cveIdUmf),
						filtro.getUmfDTO().getCveUmf());
			} else if (filtro.getSubdelDTO() != null 
					&& filtro.getSubdelDTO().getCveSubelegacion() != 0) {
				condicion = cb.equal(a.get(SsoAccesomodulo_.ssoSolicitud)
								.get(SsoSolicitud_.dicSubdelegacion)
								.get(DicSubdelegacion_.cveIdSubdelegacion),
						filtro.getSubdelDTO().getCveSubelegacion());
				if (quitaNulos == true) {
					Predicate condicion3 = cb.isNull(a.get(SsoAccesomodulo_.ssoSolicitud)
							.get(SsoSolicitud_.dicUmf).get(DicUmf_.cveIdUmf));
					condicion = cb.and(condicion, condicion3);
				}
			} else if (filtro.getDelDTO() != null 
					&& filtro.getDelDTO().getCveDelegacion() != 0) {
				condicion = cb.equal(a.get(SsoAccesomodulo_.ssoSolicitud)
								.get(SsoSolicitud_.dicDelegacion)
								.get(DicDelegacion_.cveIdDelegacion),
						filtro.getDelDTO().getCveDelegacion());
				if (quitaNulos == true) {
					Predicate condicion3 = cb.isNull(a.get(SsoAccesomodulo_.ssoSolicitud)
							.get(SsoSolicitud_.dicUmf).get(DicUmf_.cveIdUmf));
					Predicate condicion4 = cb.isNull(a.get(SsoAccesomodulo_.ssoSolicitud)
							.get(SsoSolicitud_.dicSubdelegacion)
							.get(DicSubdelegacion_.cveIdSubdelegacion));
					condicion = cb.and(condicion, condicion3, condicion4);
				}
			} else if (filtro.getDptoDTO() != null && filtro.getDptoDTO().getCveSsodepto() != 0) {
				condicion = a.get(SsoAccesomodulo_.ssoSolicitud)
						.get(SsoSolicitud_.ssoCatdepartamento)
						.get(SsoCatdepartamento_.cveSsodepto).in(deptos);
				if (quitaNulos == true) {
					Predicate condicion3 = cb.isNull(a.get(SsoAccesomodulo_.ssoSolicitud)
							.get(SsoSolicitud_.dicUmf).get(DicUmf_.cveIdUmf));
					Predicate condicion4 = cb.isNull(a.get(SsoAccesomodulo_.ssoSolicitud)
							.get(SsoSolicitud_.dicSubdelegacion)
							.get(DicSubdelegacion_.cveIdSubdelegacion));
					Predicate condicion5 = cb.isNull(a.get(SsoAccesomodulo_.ssoSolicitud)
							.get(SsoSolicitud_.dicDelegacion)
							.get(DicDelegacion_.cveIdDelegacion));
					condicion = cb.and(condicion, condicion3, condicion4, condicion5);
				}
			}

			if (idEstatus != null) {
				Predicate condicion2 = cb.equal(a.get(SsoAccesomodulo_.ssoSolicitud)
						.get(SsoSolicitud_.ssoCatestatus)
						.get(SsoCatestatus_.cveSsoestatus), idEstatus);
				condicion = cb.and(condicion, condicion2);
			}

			criteria.where(condicion);
			List<Tuple> tuples = em.createQuery(criteria).getResultList();
			SolicitudDTO solDTO = new SolicitudDTO();

			lista = new ArrayList<SolicitudDTO>();
			List<PerfilDTO> perfiles = new ArrayList<PerfilDTO>();
			List<ModuloDTO> modulos = new ArrayList<ModuloDTO>();
			for (Tuple tuple : tuples) {
				solDTO = new SolicitudDTO();
				SsoSolicitud solicitud = (SsoSolicitud) tuple.get(sol);

				solDTO.setCveSsosolicitud(solicitud.getCveSsosolicitud());
				solDTO.setNomMaterno(solicitud.getNomMaterno());
				solDTO.setNomNombre(solicitud.getNomNombre());
				solDTO.setNomPaterno(solicitud.getNomPaterno());
				solDTO.setFecRegistroAlta(solicitud.getFecRegistroAlta());

				solDTO.setDesUsrCurp(solicitud.getDesUsrCurp());
				solDTO.setCveMatricula(solicitud.getCveMatricula());
				solDTO.setRefCorreoElectronico(solicitud.getRefCorreoElectronico());
				solDTO.setDesTelefonoOfi(solicitud.getDesTelefonoOfi());

				perfiles = perfilesBySol(solDTO.getCveSsosolicitud());
				modulos = modulosBySolAndDepto(solicitud.getCveSsosolicitud(), deptos, false);

				solDTO.getAreaNorm().setDesAreanorma(solicitud.getSsoCatdepartamento()
						.getSsoCatareanormativa().getDesAreanorma());
				if (solicitud.getDicDelegacion() != null) {
					solDTO.getDelDTO().setCveDelegacion(
							solicitud.getDicDelegacion().getCveIdDelegacion());
					solDTO.getDelDTO().setNombreDelegacion(
							solicitud.getDicDelegacion().getDesDeleg());
				}
				if (solicitud.getDicSubdelegacion() != null) {
					solDTO.getSubdelDTO().setCveSubelegacion(
							solicitud.getDicSubdelegacion().getCveIdSubdelegacion());
					solDTO.getSubdelDTO().setNombreSubelegacion(
							solicitud.getDicSubdelegacion().getDesSubdelegacion());
				}
				if (solicitud.getDicUmf() != null) {
					solDTO.getUmfDTO().setCveUmf(
							solicitud.getDicUmf().getCveIdUmf());
					solDTO.getUmfDTO().setNombreUmf(
							solicitud.getDicUmf().getNomUnidad());
				}

				solDTO.getPuestoDTO().setCvePuesto(
						solicitud.getSsoCatpuesto().getCveSsopuesto());
				solDTO.getPuestoDTO().setNombrePuesto(
						solicitud.getSsoCatpuesto().getDesPuesto());
				solDTO.getDptoDTO().setCveSsodepto(
						solicitud.getSsoCatdepartamento().getCveSsodepto());
				solDTO.getDptoDTO().setDesDepartamento(
						solicitud.getSsoCatdepartamento().getDesDepartamento());
				solDTO.getEstatusDTO().setCveSsoestatus(
						solicitud.getSsoCatestatus().getCveSsoestatus());
				solDTO.getEstatusDTO().setDesEstatus(
						solicitud.getSsoCatestatus().getDesEstatus());
				solDTO.setModulosDTO(modulos);
				solDTO.setPerfilesDTO(perfiles);

				if (curpExcluye != null) {
					if (!solDTO.getDesUsrCurp().trim()
							.equals(curpExcluye.trim())) {
						lista.add(solDTO);
					}
				} else {
					lista.add(solDTO);
				}
			}
		} catch (Exception ex) {
			logger.error("Error::solicitudesByFiltro... " + ex.getMessage());
			logger.error("  ", ex);
			throw new AdmonUsuariosException();
		}
		return lista;
	}

	public List<ModuloDTO> modulosBySolAndDepto(Long idSolicitud, List<Long> deptos, boolean depto) {
		cb = em.getCriteriaBuilder();
		CriteriaQuery<SsoAccesomodulo> criteria = cb.createQuery(SsoAccesomodulo.class);
		Root<SsoAccesomodulo> root = criteria.from(SsoAccesomodulo.class);
		criteria.select(root);
		if (depto) {
			Predicate condicion = cb.equal(
					root.get(SsoAccesomodulo_.ssoSolicitud).get(
							SsoSolicitud_.cveSsosolicitud), idSolicitud);
			Predicate condicion2 = root.get(SsoAccesomodulo_.ssoCatdeptomodulo)
					.get(SsoCatdeptomodulo_.ssoCatdepartamento)
					.get(SsoCatdepartamento_.cveSsodepto).in(deptos);
			criteria.where(condicion, condicion2);
		} else {
			Predicate condicion = cb.equal(root.get(SsoAccesomodulo_.ssoSolicitud).get(
					SsoSolicitud_.cveSsosolicitud), idSolicitud);
			criteria.where(condicion);
		}
		List<SsoAccesomodulo> moduls = em.createQuery(criteria).getResultList();
		ModuloDTO mod;
		List<ModuloDTO> modulos = new ArrayList<ModuloDTO>();
		for (SsoAccesomodulo modulo : moduls) {
			mod = new ModuloDTO();
			mod.setCveIdModulo(modulo.getSsoCatdeptomodulo()
					.getDicModulo().getCveIdModulo());
			mod.setDesModulo(modulo.getSsoCatdeptomodulo()
					.getDicModulo().getDesModulo());
			mod.setAreaNorm(new AreaNormativaDTO());
			mod.getAreaNormDTO().setDesAreanorma(
					modulo.getSsoCatdeptomodulo().getSsoCatdepartamento()
							.getSsoCatareanormativa().getDesAreanorma());
			mod.setDptoDTO(new DepartamentoDTO());
			mod.getDptoDTO().setDesDepartamento(
					modulo.getSsoCatdeptomodulo().getSsoCatdepartamento()
							.getDesDepartamento());
			mod.setCveAccesoModulo(modulo.getCveSsoaccesomodulo());
			mod.getEstatusDTO().setCveSsoestatus(
					modulo.getSsoCatestatus().getCveSsoestatus());
			mod.getEstatusDTO().setDesEstatus(
					modulo.getSsoCatestatus().getDesEstatus());
			modulos.add(mod);
		}
		return modulos;
	}

	public List<SolicitudDTO> solicitudesExternasByDepto(Long idDepto, Long idEstatus) 
			throws AdmonUsuariosException {
		cb = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteria = cb.createTupleQuery();
		Root<SsoAccesomodulo> root = criteria.from(SsoAccesomodulo.class);
		Path<SsoSolicitud> sol = root.get(SsoAccesomodulo_.ssoSolicitud);
		criteria.multiselect(sol).distinct(true);

		DepartamentoDTO deptoPadre = deptoGeneralByIdDepto(idDepto);
		Long idDeptoGeneral = deptoPadre.getDeptoGeneralDTO().getCveSsodepto();
		List<Long> deptos = deptosByDeptoGeneral(idDeptoGeneral);

		Predicate condicion = root.get(SsoAccesomodulo_.ssoCatdeptomodulo)
				.get(SsoCatdeptomodulo_.ssoCatdepartamento)
				.get(SsoCatdepartamento_.cveSsodepto).in(deptos);
		Predicate condicion2 = cb.not(root.get(SsoAccesomodulo_.ssoSolicitud)
				.get(SsoSolicitud_.ssoCatdepartamento)
				.get(SsoCatdepartamento_.cveSsodepto).in(idDepto));
		Predicate c;
		if (idEstatus != null) {
			Predicate condicion3 = cb.equal(
					root.get(SsoAccesomodulo_.ssoSolicitud)
							.get(SsoSolicitud_.ssoCatestatus)
							.get(SsoCatestatus_.cveSsoestatus), idEstatus);
			c = cb.and(condicion, condicion2, condicion3);
		} else {
			c = cb.and(condicion, condicion2);
		}

		criteria.where(c);

		List<Tuple> tuples = em.createQuery(criteria).getResultList();
		SolicitudDTO solDTO = new SolicitudDTO();

		List<SolicitudDTO> lista = new ArrayList<SolicitudDTO>();
		List<PerfilDTO> perfiles = new ArrayList<PerfilDTO>();
		List<ModuloDTO> modulos = new ArrayList<ModuloDTO>();

		for (Tuple tuple : tuples) {
			solDTO = new SolicitudDTO();

			SsoSolicitud solicitud = (SsoSolicitud) tuple.get(sol);

			solDTO.setCveSsosolicitud(solicitud.getCveSsosolicitud());
			solDTO.setNomMaterno(solicitud.getNomMaterno());
			solDTO.setNomNombre(solicitud.getNomNombre());
			solDTO.setNomPaterno(solicitud.getNomPaterno());
			solDTO.setFecRegistroAlta(solicitud.getFecRegistroAlta());

			solDTO.setDesUsrCurp(solicitud.getDesUsrCurp());
			solDTO.setCveMatricula(solicitud.getCveMatricula());
			solDTO.setRefCorreoElectronico(solicitud.getRefCorreoElectronico());
			solDTO.setDesTelefonoOfi(solicitud.getDesTelefonoOfi());

			perfiles = perfilesBySol(solDTO.getCveSsosolicitud());
			modulos = modulosBySolAndDepto(solicitud.getCveSsosolicitud(), deptos, true);

			solDTO.getAreaNorm().setDesAreanorma(
					solicitud.getSsoCatdepartamento().getSsoCatareanormativa().getDesAreanorma());
			if (solicitud.getDicDelegacion() != null) {
				solDTO.getDelDTO().setCveDelegacion(
						solicitud.getDicDelegacion().getCveIdDelegacion());
				solDTO.getDelDTO().setNombreDelegacion(
						solicitud.getDicDelegacion().getDesDeleg());
			}
			if (solicitud.getDicSubdelegacion() != null) {
				solDTO.getSubdelDTO().setCveSubelegacion(
						solicitud.getDicSubdelegacion().getCveIdSubdelegacion());
				solDTO.getSubdelDTO().setNombreSubelegacion(
						solicitud.getDicSubdelegacion().getDesSubdelegacion());
			}
			if (solicitud.getDicUmf() != null) {
				solDTO.getUmfDTO().setCveUmf(
						solicitud.getDicUmf().getCveIdUmf());
				solDTO.getUmfDTO().setNombreUmf(
						solicitud.getDicUmf().getNomUnidad());
			}

			solDTO.getPuestoDTO().setCvePuesto(solicitud.getSsoCatpuesto().getCveSsopuesto());
			solDTO.getPuestoDTO().setNombrePuesto(solicitud.getSsoCatpuesto().getDesPuesto());
			solDTO.getDptoDTO().setCveSsodepto(solicitud.getSsoCatdepartamento().getCveSsodepto());
			solDTO.getDptoDTO().setDesDepartamento(solicitud.getSsoCatdepartamento().getDesDepartamento());
			solDTO.setEstatusDTO(new EstatusDTO(solicitud.getSsoCatestatus().getCveSsoestatus()));
			solDTO.getEstatusDTO().setDesEstatus(solicitud.getSsoCatestatus().getDesEstatus());
			solDTO.setModulosDTO(modulos);
			solDTO.setPerfilesDTO(perfiles);

			lista.add(solDTO);
		}
		return lista;
	}

	public List<PerfilDTO> perfilesBySol(Long idSol) throws AdmonUsuariosException {
		cb = em.getCriteriaBuilder();
		CriteriaQuery<SsoPerfilessol> criteria = cb.createQuery(SsoPerfilessol.class);
		Root<SsoPerfilessol> a = criteria.from(SsoPerfilessol.class);
		criteria.select(a);

		Predicate condicion = cb.equal(a.get(SsoPerfilessol_.ssoSolicitud)
				.get(SsoSolicitud_.cveSsosolicitud), idSol);
		criteria.where(condicion);

		List<SsoPerfilessol> perfs = em.createQuery(criteria).getResultList();
		List<PerfilDTO> perfiles = new ArrayList<PerfilDTO>();
		PerfilDTO perfil;

		for (SsoPerfilessol p : perfs) {
			perfil = new PerfilDTO();
			perfil.setCveSsoperfilessol(p.getCveSsoperfilessol());
			perfil.setPuestoDTO(getPuestoDTO(p.getSsoCatpuesto()));
			perfil.setAreaNormDTO(getAreaNormativaDTO(p.getSsoCatpuesto()
					.getSsoCatdepartamento().getSsoCatareanormativa()));
			perfil.setDeptoDTO(getDeptoDTO(p.getSsoCatpuesto().getSsoCatdepartamento()));
			perfil.setDesDefault(p.getDesDefault());
			perfiles.add(perfil);
		}
		return perfiles;
	}

	public List<SsoPerfilessol> getPerfiles(long id) {
		final Query q = em.createQuery("select s from SsoPerfilessol s where s.ssoSolicitud.cveSsosolicitud = :id");
		q.setParameter("id", id);
		return q.getResultList();
	}

	public List<PuestoDTO> puestosByPerfiles(List<PerfilDTO> perfiles) throws AdmonUsuariosException {
		List<PuestoDTO> puestos = new ArrayList<PuestoDTO>();
		for (PerfilDTO p : perfiles) {
			puestos.add(p.getPuestoDTO());
		}
		return puestos;
	}

	public List<ModuloDTO> modulosBySol(Long idSolicitud)
			throws AdmonUsuariosException {
		cb = em.getCriteriaBuilder();
		CriteriaQuery<SsoAccesomodulo> criteria = cb.createQuery(SsoAccesomodulo.class);
		Root<SsoAccesomodulo> root = criteria.from(SsoAccesomodulo.class);
		criteria.select(root);
		Predicate condicion = cb.equal(root.get(SsoAccesomodulo_.ssoSolicitud)
				.get(SsoSolicitud_.cveSsosolicitud), idSolicitud);
		criteria.where(condicion);
		List<SsoAccesomodulo> moduls = em.createQuery(criteria).getResultList();
		ModuloDTO mod;
		List<ModuloDTO> modulos = new ArrayList<ModuloDTO>();
		for (SsoAccesomodulo modulo : moduls) {
			mod = new ModuloDTO();
			mod.setCveIdModulo(modulo.getSsoCatdeptomodulo().getDicModulo().getCveIdModulo());
			mod.setDesModulo(modulo.getSsoCatdeptomodulo().getDicModulo().getDesModulo());
			mod.setAreaNorm(getAreaNormativaDTO(
					modulo.getSsoCatdeptomodulo().getSsoCatdepartamento().getSsoCatareanormativa()));
			mod.setAreaNormDTO(getAreaNormativaDTO(
					modulo.getSsoCatdeptomodulo().getSsoCatdepartamento().getSsoCatareanormativa()));
			mod.setDptoDTO(getDeptoDTO(modulo.getSsoCatdeptomodulo().getSsoCatdepartamento()));
			mod.setCveAccesoModulo(modulo.getCveSsoaccesomodulo());
			mod.setEstatusDTO(getEstatusDTO(modulo.getSsoCatestatus()));
			modulos.add(mod);
		}
		return modulos;
	}

	public List<Long> deptosByDeptoGeneral(Long idDeptoPadre)
			throws AdmonUsuariosException {
		cb = em.getCriteriaBuilder();
		CriteriaQuery<SsoCatdepartamento> criteria = cb.createQuery(SsoCatdepartamento.class);
		Root<SsoCatdepartamento> root = criteria.from(SsoCatdepartamento.class);
		criteria.select(root);
		Predicate condicion = cb.equal(
				root.get(SsoCatdepartamento_.ssoCatdepartamentoGeneral).get(
						SsoCatdepartamento_.cveSsodepto), idDeptoPadre);
		criteria.where(condicion);
		List<SsoCatdepartamento> deptos = em.createQuery(criteria).getResultList();
		List<Long> departamentos = new ArrayList<Long>();
		for (SsoCatdepartamento d : deptos) {
			departamentos.add(d.getCveSsodepto());
		}
		if (deptos.size() == 0)
			departamentos.add(idDeptoPadre);
		return departamentos;
	}

	public List<ModuloDTO> listarModulosByDepartamento(Long cveDepartamento)
			throws AdmonUsuariosException {
		cb = em.getCriteriaBuilder();
		CriteriaQuery<SsoCatdeptomodulo> criteria = cb.createQuery(SsoCatdeptomodulo.class);
		Root<SsoCatdeptomodulo> root = criteria.from(SsoCatdeptomodulo.class);
		criteria.select(root);
		Predicate condicion = cb.equal(root.get(SsoCatdeptomodulo_.cveSsodeptomodulo), cveDepartamento);
		criteria.where(condicion);
		List<SsoCatdeptomodulo> dptoModulos = em.createQuery(criteria).getResultList();
		List<ModuloDTO> modulos = new ArrayList<ModuloDTO>();
		ModuloDTO modDTO;
		for (SsoCatdeptomodulo d : dptoModulos) {
			modDTO = new ModuloDTO();
			modDTO.setCveIdModulo(d.getDicModulo().getCveIdModulo());
			modDTO.setDesModulo(d.getDicModulo().getDesModulo());
			modulos.add(modDTO);
		}
		return modulos;
	}

	public DepartamentoDTO deptoGeneralByIdDepto(Long idDepto)
			throws AdmonUsuariosException {
		cb = em.getCriteriaBuilder();
		CriteriaQuery<SsoCatdepartamento> criteria = cb.createQuery(SsoCatdepartamento.class);
		Root<SsoCatdepartamento> root = criteria.from(SsoCatdepartamento.class);
		criteria.select(root);
		Predicate condicion = cb.equal(root.get(SsoCatdepartamento_.cveSsodepto), idDepto);
		criteria.where(condicion);
		List l = em.createQuery(criteria).getResultList();
		DepartamentoDTO dep = null;
		if (l != null && l.size() > 0) {
			SsoCatdepartamento depto = (SsoCatdepartamento) l.get(0);
			dep = new DepartamentoDTO();
			dep.setDeptoGeneralDTO(new DepartamentoDTO());
			if (depto.getSsoCatdepartamentoGeneral() != null) {
				dep.getDeptoGeneralDTO().setCveSsodepto(
						depto.getSsoCatdepartamentoGeneral().getCveSsodepto());
				dep.getDeptoGeneralDTO().setDesDepartamento(
						depto.getSsoCatdepartamentoGeneral()
								.getSsoCatdepartamentoGeneral().getDesDepartamento());
			} else {
				dep.getDeptoGeneralDTO().setCveSsodepto(depto.getCveSsodepto());
				dep.getDeptoGeneralDTO().setDesDepartamento(depto.getDesDepartamento());
			}

		}
		return dep;
	}

	public boolean actualizaStatusSol(SolicitudDTO solDTO, Long idStatus)
			throws AdmonUsuariosException {
		logger.info("Inicia actualiza status solicitud");

		List<SsoSolicitud> sols = new ArrayList<SsoSolicitud>();
		String q = "select s  from SsoSolicitud s WHERE s.cveSsosolicitud =:id";
		try {
			Query query = em.createQuery(q);
			query.setParameter("id", solDTO.getCveSsosolicitud());
			sols = query.getResultList();
			if (sols != null && sols.size() > 0) {
				SsoSolicitud solOriginal = (SsoSolicitud) sols.get(0);
				solOriginal.setSsoCatestatus(getCatStatus(idStatus));
				super.saveorupdate(solOriginal);
			}

		} catch (Exception ex) {
			logger.error("Ocurrio un error: ", ex);
			throw new AdmonUsuariosException();
		}
		// emf = em.getEntityManagerFactory();
		// EntityManager em2 = emf.createEntityManager();
		// SsoSolicitud sol;
		// sol = em.find(SsoSolicitud.class, solDTO.getCveSsosolicitud());
		// sol.getSsoCatestatus().setCveSsoestatus(idStatus);
		// em2.merge(sol);
		// em2.flush();
		logger.info("Finaliza actualiza status solicitud");
		return true;
	}

	public boolean actualizaStatusModulo(ModuloDTO moduloDTO, Long idEstatus) throws AdmonUsuariosException {
		emf = em.getEntityManagerFactory();
		EntityManager em2 = emf.createEntityManager();
		SsoAccesomodulo modulo;
		modulo = em.find(SsoAccesomodulo.class, moduloDTO.getCveAccesoModulo());
		modulo.getSsoCatestatus().setCveSsoestatus(idEstatus);
		em2.merge(modulo);
		em2.flush();
		return true;
	}

	public void borrarPerfil(PerfilDTO per) throws AdmonUsuariosException {
		try {
			PerfilDTO p = (PerfilDTO) per;
			SsoPerfilessol cdm = em.find(SsoPerfilessol.class, p.getCveSsoperfilessol());
			super.delete(cdm);
		} catch (Exception ex) {
			logger.error("Ocurrio un error al borrar perfil: ", ex);
			throw new AdmonUsuariosException();
		}
	}

	public SsoPerfilessol getPerfil(long id) {
		final Query q = em.createQuery("select s from SsoPerfilessol s where s.cveSsoperfilessol = :id");
		q.setParameter("id", id);
		return (SsoPerfilessol) q.getResultList().get(0);
	}

	@Override
	public String getPerfilDesc(long id) {
		final Query q = em.createQuery("select s from SsoCatpuesto s where s.cveSsopuesto = :id");
		q.setParameter("id", id);
		return ((SsoCatpuesto) q.getResultList().get(0)).getDesPuesto();
	}

	@Override
	public String getModuloDesc(long id) {
		final Query q = em.createQuery("select s from DicModulo s where s.cveIdModulo = :id");
		q.setParameter("id", id);
		return ((DicModulo) q.getResultList().get(0)).getDesModulo();
	}

	@Override
	public String getPuestoDesc(long id) {
		final Query q = em.createQuery("select s from SsoCatpuesto s where s.cveSsopuesto = :id");
		q.setParameter("id", id);
		return ((SsoCatpuesto) q.getResultList().get(0)).getDesPuesto();
	}

	@Override
	public String getDeptoDesc(long id) {
		final Query q = em.createQuery("select s from SsoCatdepartamento s where s.cveSsodepto = :id");
		q.setParameter("id", id);
		return ((SsoCatdepartamento) q.getResultList().get(0)).getDesDepartamento();
	}

	@Override
	public String getDelegacionDesc(long id) {
		final Query q = em.createQuery("select s from DicDelegacion s where s.cveIdDelegacion = :id");
		q.setParameter("id", id);
		return ((DicDelegacion) q.getResultList().get(0)).getDesDeleg();
	}

	@Override
	public String getSubdeleagcionDesc(long id) {
		final Query q = em.createQuery("select s from DicSubdelegacion s where s.cveIdSubdelegacion = :id");
		q.setParameter("id", id);
		return ((DicSubdelegacion) q.getResultList().get(0)).getDesSubdelegacion();
	}

	public SsoAccesomodulo getModulo(long id) {
		final Query q = em.createQuery("select s from SsoAccesomodulo s where s.cveSsoaccesomodulo = :id");
		q.setParameter("id", id);
		return (SsoAccesomodulo) q.getResultList().get(0);
	}

	public void borrarModulo(ModuloDTO tabla) throws AdmonUsuariosException {
		try {
			emf = em.getEntityManagerFactory();
			EntityManager em2 = emf.createEntityManager();
			ModuloDTO m = (ModuloDTO) tabla;
			SsoAccesomodulo cdm = getModulo(m.getCveAccesoModulo());
			em2.remove(em2.merge(cdm));
			em2.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdmonUsuariosException();
		}
	}

	public boolean agregaPerfil(PerfilDTO perfil) throws AdmonUsuariosException {
		try {
			SsoPerfilessol perf = new SsoPerfilessol();
			SsoCatpuesto catPuesto = em.find(SsoCatpuesto.class, perfil.getPuestoDTO().getCvePuesto());
			SsoSolicitud sol = em.find(SsoSolicitud.class, perfil.getSolicitudDTO().getCveSsosolicitud());
			perf.setFecFechaRegistro(new Date());
			perf.setSsoCatpuesto(catPuesto);
			perf.setSsoSolicitud(sol);
			perf.setDesDefault(perfil.getDesDefault());

			final Query q = em.createQuery(
					"select p from SsoPerfilessol p where p.ssoCatpuesto.cveSsopuesto = :idPuesto and p.ssoSolicitud.cveSsosolicitud = :idSolicitud");
			q.setParameter("idPuesto", catPuesto.getCveSsopuesto());
			q.setParameter("idSolicitud", sol.getCveSsosolicitud());

			if (q.getResultList().size() != 0)
				return false;
			super.saveorupdate(perf);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdmonUsuariosException();
		}
		return true;
	}

	public boolean agregaModulo(ModuloDTO m, Long idSol, Long idAprobador)
			throws AdmonUsuariosException {
		try {
			emf = em.getEntityManagerFactory();

			SsoAccesomodulo am = new SsoAccesomodulo();
			SsoSolicitud sol = em.find(SsoSolicitud.class, idSol);
			SsoAprobador aprobador = em.find(SsoAprobador.class, idAprobador);
			SsoCatestatus estatus = em.find(SsoCatestatus.class,
					Constantes.ESTATUS.SOLICITADO.getOpcion());

			SsoCatdeptomodulo deptomod = buscaCatDeptoModulo(m.getCveIdModulo(),
					sol.getSsoCatdepartamento().getCveSsodepto());

			am.setFecFechareg(new Date());
			am.setSsoAprobador(aprobador);
			am.setSsoCatestatus(estatus);
			am.setSsoSolicitud(sol);
			deptomod.getCveSsodeptomodulo();
			am.setSsoCatdeptomodulo(deptomod);

			final Query q = em.createQuery(
					"select m from SsoAccesomodulo m where m.ssoSolicitud.cveSsosolicitud = :idSolicitud and m.ssoCatdeptomodulo.cveSsodeptomodulo = :idDeptoMod");
			q.setParameter("idSolicitud", sol.getCveSsosolicitud());
			q.setParameter("idDeptoMod", deptomod.getCveSsodeptomodulo());
			if (q.getResultList().size() != 0)
				return false;
			this.saveorupdate(am);

		} catch (Exception e) {
			e.printStackTrace();
			throw new AdmonUsuariosException();
		}
		return true;
	}

	private SsoCatdeptomodulo buscaCatDeptoModulo(long idModulo, long idDepto) {
		final Query q = em.createQuery(
				"select s from SsoCatdeptomodulo s where s.dicModulo.cveIdModulo =  :idMod and s.ssoCatdepartamento.cveSsodepto = :idDepto");
		q.setParameter("idMod", idModulo);
		q.setParameter("idDepto", idDepto);
		return (SsoCatdeptomodulo) q.getResultList().get(0);
	}

	public SsoCatdeptomodulo agregaDepto(ModuloDTO m)
			throws AdmonUsuariosException {
		emf = em.getEntityManagerFactory();
		EntityManager em2 = emf.createEntityManager();
		DicModulo mod = em.find(DicModulo.class, m.getCveIdModulo());
		SsoCatdepartamento depto = em.find(SsoCatdepartamento.class,
				m.getDptoDTO().getCveSsodepto());
		SsoCatdeptomodulo deptomod = new SsoCatdeptomodulo();
		deptomod.setDicModulo(mod);
		deptomod.setSsoCatdepartamento(depto);
		em2.persist(deptomod);
		em2.flush();

		cb = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteria = cb.createTupleQuery();
		Root<SsoCatdeptomodulo> root = criteria.from(SsoCatdeptomodulo.class);
		Expression<Long> maximum = cb.max(root.get(SsoCatdeptomodulo_.cveSsodeptomodulo));

		criteria.multiselect(maximum);
		Tuple max = em.createQuery(criteria).getSingleResult();
		deptomod.setCveSsodeptomodulo(max.get(maximum));
		return deptomod;
	}

	public void autorizaRechazaSolicitudBitacora(String operacion,
			SolicitudDTO solicitud, Long idAprobador, Long idEstatus,
			String correo) throws AdmonUsuariosException {
		try {
			emf = em.getEntityManagerFactory();
			bitacoraService.guardaBitacora(operacion + " Solicitud",
					solicitud.getCveSsosolicitud(),
					solicitud.datosBitacora(), idAprobador, idEstatus);
			for (PerfilDTO p : solicitud.getPerfilesDTO()) {
				bitacoraService.guardaBitacora(operacion + "Perfil",
						solicitud.getCveSsosolicitud(), p.datosBitacora(),
						idAprobador, solicitud.getEstatusDTO().getCveSsoestatus());
			}
			for (ModuloDTO m : solicitud.getModulosDTO()) {
				bitacoraService.guardaBitacora(operacion + " Modulo",
						solicitud.getCveSsosolicitud(), m.datosBitacora(),
						idAprobador, m.getEstatusDTO().getCveSsoestatus());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdmonUsuariosException();
		}
	}

	public void agregaBorraModuloPerfilBitacora(String operacion,
			SolicitudDTO solicitud, Long idAprobador, Long idEstatus,
			String correo) throws AdmonUsuariosException {
		emf = em.getEntityManagerFactory();
		bitacoraService.guardaBitacora(operacion,
				solicitud.getCveSsosolicitud(), solicitud.datosBitacora(),
				idAprobador, idEstatus);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudDTO> searchSolicitudes(SolicitudDTO sol, String excluyeCurp)
			throws AdmonUsuariosException {
		List<SsoSolicitud> sols = new ArrayList<SsoSolicitud>();
		List<SsoSolicitud> tmp = new ArrayList<SsoSolicitud>();
		EntityManager em1 = em.getEntityManagerFactory().createEntityManager();
		StringBuilder sbQuery = new StringBuilder("Select s from SsoSolicitud s WHERE ");
		try {
			if (sol.getDelDTO() != null) {
				if (sol.getSubdelDTO() != null) {
					if (sol.getUmfDTO() != null) {
						logger.info("::: Inicia la busqueda a nivel UMF :::");
						logger.info("::: Parametros.umf[" + sol.getUmfDTO().getCveUmf() + "]");
						logger.info("::: Parametros.delegacion[" + sol.getDelDTO().getCveDelegacion() + "]");
						logger.info("::: Parametros.subdelegacion[" + sol.getSubdelDTO().getCveSubelegacion() + "]");

						sbQuery.append(" s.dicDelegacion.cveIdDelegacion = :del and s.dicSubdelegacion.cveIdSubdelegacion = :subdel and s.dicUmf.cveIdUmf = :umf ");
						sbQuery.append(" Order By s.desUsrCurp ASC");
						// Construye el query
						logger.debug("::: Sbquery=[" + sbQuery.toString() + "] :::");
						Query query = em1.createQuery(sbQuery.toString())
								.setParameter("del", sol.getDelDTO().getCveDelegacion())
								.setParameter("subdel", sol.getSubdelDTO().getCveSubelegacion())
								.setParameter("umf", sol.getUmfDTO().getCveUmf())
								.setFlushMode(FlushModeType.AUTO)
								.setHint("javax.persistence.cache.storeMode", "REFRESH");
						// Resultados
						sols = query.getResultList();
						em1.getEntityManagerFactory().getCache().evictAll();
						em1.flush();
						em1.clear();
						em1.close();

						logger.info("::: Finaliza la busqueda a nivel UMF :::");
					} else {
						logger.info("::: Inicia la busqueda a nivel Subdelegacion :::");
						logger.info("::: Parametros.subdelegacion[" + sol.getSubdelDTO().getCveSubelegacion() + "]");

						sbQuery.append(" s.dicDelegacion.cveIdDelegacion = :del and s.dicSubdelegacion.cveIdSubdelegacion = :subdel and s.dicUmf is null ");
						sbQuery.append(" Order By s.desUsrCurp ASC");
						// Construye el query
						logger.debug("::: Sbquery=[" + sbQuery.toString() + "] :::");
						Query query = em1.createQuery(sbQuery.toString())
								.setParameter("del", sol.getDelDTO().getCveDelegacion())
								.setParameter("subdel", sol.getSubdelDTO().getCveSubelegacion())
								.setFlushMode(FlushModeType.AUTO)
								.setHint("javax.persistence.cache.storeMode", "REFRESH");
						// Resultados
						sols = query.getResultList();
						em1.getEntityManagerFactory().getCache().evictAll();
						em1.flush();
						em1.clear();
						em1.close();

						logger.info("::: Finaliza la busqueda a nivel Subdelegacion :::");
					}
				} else {
					logger.info("::: Inicia la busqueda por Delegacion :::");
					logger.info("::: Parametros.delegacion[" + sol.getDelDTO().getCveDelegacion() + "]");

					sbQuery.append(" s.dicDelegacion.cveIdDelegacion = :del and s.dicSubdelegacion is null and s.dicUmf is null ");
					sbQuery.append(" Order By s.desUsrCurp ASC");
					// Construye el query
					logger.debug("::: Sbquery=[" + sbQuery.toString() + "] :::");
					Query query = em1.createQuery(sbQuery.toString())
							.setParameter("del", sol.getDelDTO().getCveDelegacion())
							.setFlushMode(FlushModeType.AUTO)
							.setHint("javax.persistence.cache.storeMode", "REFRESH");
					// Resultados
					sols = query.getResultList();
					em1.getEntityManagerFactory().getCache().evictAll();
					em1.flush();
					em1.clear();
					em1.close();

					logger.info("::: Finaliza la busqueda por Delegacion :::");
				}
			} else {
				if (sol.getDptoDTO() != null) {
					logger.info("::: Inicia la busqueda a nivel departamento :::");
					logger.info("::: Parametros.departamento[" + sol.getDptoDTO().getCveSsodepto() + "]");
					// Obtiene departamento
					SsoCatdepartamento dep = getDepto(sol.getDptoDTO().getCveSsodepto());
					if (dep.getSsoCatdepartamentoPadre() != null) {
						logger.info("::: Parametros.departamentoPadre["
								+ dep.getSsoCatdepartamentoPadre().getCveSsodepto() + "]");

						sbQuery.append(" s.dicDelegacion is null and s.dicSubdelegacion is null and s.dicUmf is null and (s.ssoCatdepartamento.ssoCatdepartamentoPadre.cveSsodepto = :deptoPadre ");
						sbQuery.append(" or s.ssoCatdepartamento.cveSsodepto = :deptoPadre  or s.ssoCatdepartamento.ssoCatdepartamentoPadre.cveSsodepto = :depto) ");
						sbQuery.append(" Order By s.desUsrCurp ASC");
						// Construye el query
						logger.debug("::: Sbquery=[" + sbQuery.toString() + "] :::");
						Query query = em1.createQuery(sbQuery.toString());
						query.setParameter("depto", sol.getDptoDTO().getCveSsodepto());
						query.setParameter("deptoPadre", dep.getSsoCatdepartamentoPadre().getCveSsodepto());
						//em1.refresh(sols);
						query.setFlushMode(FlushModeType.AUTO);
						query.setHint("javax.persistence.cache.storeMode", "REFRESH");
						// Resultados
						sols = query.getResultList();
						em1.getEntityManagerFactory().getCache().evictAll();
						em1.flush();
						em1.clear();
						em1.close();
					} else {
						StringBuilder sbQuery1 = new StringBuilder(sbQuery.toString())
								.append(" s.dicDelegacion is null and s.dicSubdelegacion is null and s.dicUmf is null and s.ssoCatdepartamento.cveSsodepto = :depto ")
								.append(" Order By s.desUsrCurp ASC");
						// Construye el query
						logger.debug("::: Sbquery1=[" + sbQuery1.toString() + "] :::");
						Query query = em1.createQuery(sbQuery1.toString());
						query.setParameter("depto", sol.getDptoDTO().getCveSsodepto());
						query.setFlushMode(FlushModeType.AUTO);
						query.setHint("javax.persistence.cache.storeMode", "REFRESH");
						// Resultados
						sols = query.getResultList();

						List<SsoSolicitud> tmpSol = new ArrayList<SsoSolicitud>();
						StringBuilder sbQuery2 = new StringBuilder(sbQuery.toString())
								.append(" s.dicDelegacion is null and s.dicSubdelegacion is null and s.dicUmf is null and s.ssoCatdepartamento.ssoCatdepartamentoPadre.cveSsodepto = :depto ")
								.append(" Order By s.desUsrCurp ASC");
						logger.debug("::: Sbquery2=[" + sbQuery2.toString() + "] :::");
						query = em1.createQuery(sbQuery2.toString());
						query.setParameter("depto", sol.getDptoDTO().getCveSsodepto());
						query.setFlushMode(FlushModeType.AUTO);
						query.setHint("javax.persistence.cache.storeMode", "REFRESH");
						// Resultados
						tmpSol = query.getResultList();

						for (SsoSolicitud solic : tmpSol) {
							sols.add(solic);
						}
						em1.getEntityManagerFactory().getCache().evictAll();
						em1.flush();
						em1.clear();
						em1.close();
					}
					logger.info("::: Finaliza la busqueda a nivel departamento :::");
				} else {
					logger.info("::: Inicia busqueda a nivel central :::");

					sbQuery.append(" s.dicDelegacion is null and s.dicSubdelegacion is null and s.dicUmf is null ");
					sbQuery.append(" Order By s.desUsrCurp ASC");
					// Construye el query
					logger.debug("::: Sbquery=[" + sbQuery.toString() + "] :::");
					Query query = em1.createQuery(sbQuery.toString());
					query.setFlushMode(FlushModeType.AUTO);
					query.setHint("javax.persistence.cache.storeMode", "REFRESH");
					// Resultados
					sols = query.getResultList();
					em1.getEntityManagerFactory().getCache().evictAll();
					em1.flush();
					em1.clear();
					em1.close();

					logger.info("::: Finaliza busqueda a nivel central :::");
				}

			}
			if (sols != null && sols.size() > 0 && sol.getEstatusDTO() != null) {
				for (SsoSolicitud solic : sols) {
					if (solic.getSsoCatestatus().getCveSsoestatus() == sol.getEstatusDTO().getCveSsoestatus())
						tmp.add(solic);
				}
			} else {
				tmp = sols;
			}
		} catch (Exception ex) {
			logger.error("Error::searchSolicitudes ++ " + ex.getMessage());
			logger.error("  ", ex);
		}

		if (excluyeCurp != null) {
			return getSolicitudesDTO(tmp, excluyeCurp);
		}
		else {
			return getSolicitudesDTO(tmp);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudDTO> searchSolCurp(SolicitudDTO sol, long estatus)
			throws AdmonUsuariosException {
		List<SsoSolicitud> sols = new ArrayList<SsoSolicitud>();
		String q = "select s  from SsoSolicitud s WHERE s.desUsrCurp =:curp  and s.ssoCatestatus.cveSsoestatus =:estatus ";
		try {
			Query query = em.createQuery(q);
			query.setParameter("curp", sol.getDesUsrCurp());
			query.setParameter("estatus", estatus);
			query.setFlushMode(FlushModeType.AUTO);
			query.setHint("javax.persistence.cache.storeMode", "REFRESH");
			// Resultados
			sols = query.getResultList();

		} catch (Exception ex) {
			logger.error("Error::searchSolCurp ++ " + ex.getMessage());
			logger.error("  ", ex);
			throw new AdmonUsuariosException();
		}
		return getSolicitudesDTO(sols);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void reactivaSolicitud(SolicitudDTO sol) throws AdmonUsuariosException {
		List<SsoSolicitud> sols = new ArrayList<SsoSolicitud>();
		String q = "select s  from SsoSolicitud s WHERE s.cveSsosolicitud =:id";
		try {
			Query query = em.createQuery(q);
			query.setParameter("id", sol.getCveSsosolicitud());
			sols = query.getResultList();
			if (sols != null && sols.size() > 0) {
				SsoSolicitud solOriginal = (SsoSolicitud) sols.get(0);
				solOriginal.setSsoCatestatus(getCatStatus(1));
				if (sol.getDelDTO() != null && sol.getDelDTO().getCveDelegacion() > 0)
					solOriginal.setDicDelegacion(getDelegacion(sol.getDelDTO().getCveDelegacion()));
				else
					solOriginal.setDicDelegacion(null);
				if (sol.getSubdelDTO() != null && sol.getSubdelDTO().getCveSubelegacion() > 0)
					solOriginal.setDicSubdelegacion(getSubdelegacion(sol.getSubdelDTO().getCveSubelegacion()));
				else
					solOriginal.setDicSubdelegacion(null);
				if (sol.getUmfDTO() != null && sol.getUmfDTO().getCveUmf().intValue() > 0)
					solOriginal.setDicUmf(getUmf(sol.getUmfDTO().getCveUmf()));
				else
					solOriginal.setDicUmf(null);

				if (sol.getDptoDTO() != null && new Integer(sol.getDptoDTO().getCveSsodepto() + "").intValue() > 0)
					solOriginal.setSsoCatdepartamento(getDepto(sol.getDptoDTO().getCveSsodepto()));

				if (sol.getPuestoDTO() != null && new Integer(sol.getPuestoDTO().getCvePuesto() + "").intValue() > 0)
					solOriginal.setSsoCatpuesto(getPuesto(sol.getPuestoDTO().getCvePuesto()));

				for (SsoPerfilessol persol : solOriginal.getSsoPerfilessols()) {
					SsoPerfilessol pDel = getPerfilSol(persol.getCveSsoperfilessol());
					super.delete(pDel);
					bitacoraService.guardaPuestoBitSolicitud(solOriginal, sol.getCveAprobador(),
							pDel.getSsoCatpuesto().getCveSsopuesto(), false);
				}
				for (SsoAccesomodulo accMod : solOriginal.getSsoAccesomodulos()) {
					SsoAccesomodulo accMDel = getAccesoModudulo(accMod.getCveSsoaccesomodulo());
					super.delete(accMDel);
					bitacoraService.guardaModulosBitSolicitud(solOriginal, sol.getCveAprobador(),
							accMDel.getSsoCatdeptomodulo().getDicModulo().getCveIdModulo(), false);
				}

				Set<SsoPerfilessol> list = new HashSet<SsoPerfilessol>();
				for (PuestoDTO puesto : sol.getPuestosDTO()) {

					SsoPerfilessol per = new SsoPerfilessol();
					per.setDesDefault(puesto.getDefaultRol());
					per.setFecFechaRegistro(new Date());
					per.setSsoCatpuesto(getCatPuesto(puesto.getCvePuesto()));
					per.setSsoSolicitud(solOriginal);
					super.saveorupdate(per);
					bitacoraService.guardaPuestoBitSolicitud(solOriginal, sol.getCveAprobador(),
							per.getSsoCatpuesto().getCveSsopuesto(), true);
					list.add(per);

				}
				solOriginal.setSsoPerfilessols(list);

				Set<SsoAccesomodulo> list2 = new HashSet<SsoAccesomodulo>();
				for (ModuloDTO modulo : sol.getModulosDTO()) {
					SsoAccesomodulo accMod = new SsoAccesomodulo();
					accMod.setFecFechareg(new Date());
					accMod.setSsoAprobador(null);
					accMod.setSsoCatestatus(getCatStatus(ESTATUS_AUTORIZADO));
					accMod.setSsoSolicitud(solOriginal);
					accMod.setSsoCatdeptomodulo(
							getCatDeptoModulo(modulo.getCveIdModulo(), modulo.getDptoDTO().getCveSsodepto()));
					super.saveorupdate(accMod);
					bitacoraService.guardaModulosBitSolicitud(solOriginal, sol.getCveAprobador(),
							accMod.getSsoCatdeptomodulo().getDicModulo().getCveIdModulo(), true);
					list2.add(accMod);

				}
				solOriginal.setSsoAccesomodulos(list2);
				solOriginal.setDesTelefonoOfi(sol.getDesTelefonoOfi());
				solOriginal.setRefCorreoElectronico(sol.getRefCorreoElectronico());
				solOriginal.setSsoCatestatus(getCatStatus(ESTATUS_SOLICITADO));
				super.saveorupdate(solOriginal);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdmonUsuariosException();
		}
	}

	@Override
	public void aplicaCambiosSolicitud(SolicitudDTO sol) throws AdmonUsuariosException {
		List<SsoSolicitud> sols = new ArrayList<SsoSolicitud>();
		String q = "select s  from SsoSolicitud s WHERE s.cveSsosolicitud =:id";
		try {
			Query query = em.createQuery(q);
			query.setParameter("id", sol.getCveSsosolicitud());
			sols = query.getResultList();
			if (sols != null && sols.size() > 0) {
				SsoSolicitud solOriginal = (SsoSolicitud) sols.get(0);

				for (SsoPerfilessol persol : solOriginal.getSsoPerfilessols()) {
					SsoPerfilessol pDel = getPerfilSol(persol.getCveSsoperfilessol());
					super.delete(pDel);
					bitacoraService.guardaPuestoBitSolicitud(solOriginal, sol.getCveAprobador(),
							pDel.getSsoCatpuesto().getCveSsopuesto(), false);
				}
				for (SsoAccesomodulo accMod : solOriginal.getSsoAccesomodulos()) {
					SsoAccesomodulo accMDel = getAccesoModudulo(accMod.getCveSsoaccesomodulo());
					super.delete(accMDel);
					bitacoraService.guardaModulosBitSolicitud(solOriginal, sol.getCveAprobador(),
							accMDel.getSsoCatdeptomodulo().getDicModulo().getCveIdModulo(), false);
				}

				Set<SsoPerfilessol> list = new HashSet<SsoPerfilessol>();
				for (PerfilDTO perfil : sol.getPerfilesDTO()) {
					SsoPerfilessol per = new SsoPerfilessol();
					per.setDesDefault(perfil.getDesDefault());
					per.setFecFechaRegistro(new Date());
					per.setSsoCatpuesto(getCatPuesto(perfil.getPuestoDTO().getCvePuesto()));
					per.setSsoSolicitud(solOriginal);
					super.saveorupdate(per);
					bitacoraService.guardaPuestoBitSolicitud(solOriginal, sol.getCveAprobador(),
							per.getSsoCatpuesto().getCveSsopuesto(), true);
					list.add(per);

				}
				solOriginal.setSsoPerfilessols(list);

				Set<SsoAccesomodulo> list2 = new HashSet<SsoAccesomodulo>();
				for (ModuloDTO modulo : sol.getModulosDTO()) {
					SsoAccesomodulo accMod = new SsoAccesomodulo();
					accMod.setFecFechareg(new Date());
					accMod.setSsoAprobador(null);
					accMod.setSsoCatestatus(getCatStatus(ESTATUS_AUTORIZADO));
					accMod.setSsoSolicitud(solOriginal);
					accMod.setSsoCatdeptomodulo(
							getCatDeptoModulo(modulo.getCveIdModulo(), modulo.getDptoDTO().getCveSsodepto()));
					super.saveorupdate(accMod);
					bitacoraService.guardaModulosBitSolicitud(solOriginal, sol.getCveAprobador(),
							accMod.getSsoCatdeptomodulo().getDicModulo().getCveIdModulo(), true);
					list2.add(accMod);

				}
				solOriginal.setSsoAccesomodulos(list2);
				solOriginal.setDesTelefonoOfi(sol.getDesTelefonoOfi());
				solOriginal.setEstatus(ESTATUS_SOLICITADO);
				solOriginal.setSsoCatestatus(getCatStatus(ESTATUS_SOLICITADO));
				solOriginal.setDesTelefonoOfi(sol.getDesTelefonoOfi());
				solOriginal.setRefCorreoElectronico(sol.getRefCorreoElectronico());
				super.saveorupdate(solOriginal);
				AdmonUsuariosService.desactivarUsuario(solOriginal.getDesUsrCurp());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdmonUsuariosException();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void aplicaCambiosSolicitudLdap(SolicitudDTO sol) throws AdmonUsuariosException {
		int numeroIntento = 0;
		List<SsoSolicitud> sols = new ArrayList<SsoSolicitud>();
		String q = "select s  from SsoSolicitud s WHERE s.cveSsosolicitud =:id";
		try {
			Query query = em.createQuery(q);
			query.setParameter("id", sol.getCveSsosolicitud());
			sols = query.getResultList();
			if (sols != null && sols.size() > 0) {
				SsoSolicitud solOriginal = (SsoSolicitud) sols.get(0);

				logger.info(
						"########## SE OBTIENE USUARIO DEL LDAP ANTES DE MODIFICAR  [" + sol.getDesUsrCurp() + "] ##########");

				UsuarioDTO user = AdmonUsuariosService.obtenUsuario(sol.getDesUsrCurp());

				if (user != null) {
					logger.info("########## NOMBRES [" + user.getNombres() + "] ##########");
					logger.info("########## APELLIDO PATERNO [" + user.getApellidoPaterno() + "] ##########");
					logger.info("########## APELLIDO MATERNO [" + user.getApellidoMaterno() + "] ##########");
					logger.info("########## UID [" + user.getUid() + "] ##########");
					logger.info("########## PASSWORD [" + user.getPassword() + "] ##########");
					logger.info("########## NUEVO PASSWORD [" + user.getNewPassword() + "] ##########");
					logger.info("########## CORREO ELECTRONICO [" + user.getCorreoElectronico() + "] ##########");
					logger.info("########## CLAVE DELEGACION [" + user.getClaveDelegacion() + "] ##########");
					logger.info("########## CLAVE SUBDELEGACION [" + user.getClaveSubDelegacion() + "] ##########");
					logger.info("########## CLAVE UMF [" + user.getClaveUMF() + "] ##########");
					logger.info("########## CURP [" + user.getCurp() + "] ##########");
					logger.info("########## ID BDTU [" + user.getIdBdtu() + "] ##########");
					logger.info("########## SERIAL [" + user.getSerial() + "] ##########");
					logger.info("########## MODULOS [" + user.getModulos() + "] ##########");
					logger.info("########## PERFILES [" + user.getPerfiles() + "] ##########");
					logger.info("########## PASSWORD [" + user.getPassword() + "] ##########");
					logger.info("########## DESCRIPCION CARGO [" + user.getDescripcionCargo() + "] ##########");
					logger.info("########## MATRICULA [" + user.getMatricula() + "] ##########");
					logger.info("########## ACTIVO [" + user.isActivo() + "] ##########");

					this.insertarRolesPerfilesLdap(sol, solOriginal, user);

					UsuarioDTO userValidarPerfiles = AdmonUsuariosService.obtenUsuario(sol.getDesUsrCurp());

					String[] arrayPerfiles = getPerfiles(userValidarPerfiles.getPerfiles());

					List<PerfilDTO> listPerfiles = new ArrayList<PerfilDTO>();

					for (int i = 0; i < arrayPerfiles.length; i++) {
						PerfilDTO perfil = new PerfilDTO();
						perfil.setDesDefault(arrayPerfiles[i]);
						listPerfiles.add(perfil);
					}

					boolean listDiferentes = this.comparaLista(sol, listPerfiles);

					while (listDiferentes && numeroIntento < 3) {
						this.insertarRolesPerfilesLdap(sol, solOriginal, user);
						listDiferentes = this.comparaLista(sol, listPerfiles);
						numeroIntento++;
					}

					if (listDiferentes && numeroIntento == 3) {
						logger.info(
								"########## ERROR: No fue posible agregar los Roles y los Perfiles al Usuario del LDAP ##########");
					}

					for (ModuloDTO modulo : sol.getModulosDTO()) {
						SsoAccesomodulo accMod = new SsoAccesomodulo();
						accMod.setFecFechareg(new Date());
						accMod.setSsoAprobador(null);
						accMod.setSsoCatestatus(getCatStatus(2));
						accMod.setSsoSolicitud(solOriginal);
						accMod.setSsoCatdeptomodulo(
								getCatDeptoModulo(modulo.getCveIdModulo(), modulo.getDptoDTO().getCveSsodepto()));

						logger.info("########## SE AGREGA EL IMSS SISTEMAS ["
								+ accMod.getSsoCatdeptomodulo().getDicModulo().getDesModulo()
								+ "] AL USUARIO DEL LDAP ##########");

						AdmonUsuariosService.agregarModuloAprobador(sol.getDesUsrCurp(),
								accMod.getSsoCatdeptomodulo().getDicModulo().getDesModulo());
					}

					if (user.getPerfiles() != null && user.getPerfiles().indexOf("APROBADOR") > -1) {
						logger.info(
								"########## SE AGREGA EL ROL Y EL PERFIL DE [APROBADOR] AL USUARIO DEL LDAP YA QUE CONTABA CON ESTE PERFIL ##########");
						AdmonUsuariosService.agregaPerfilAprobador(sol.getDesUsrCurp(), "APROBADOR");
						AdmonRolesService.asignaRolUsuario(sol.getDesUsrCurp(), "APROBADOR");
					}

					if (user.getMatricula() == null) {

						logger.info("########## SE MODIFICA LA MATRICULA DEL USUARIO DEL LDAP ##########");

						AdmonUsuariosService.modificarMatriculaUsuario(sol.getDesUsrCurp(), sol.getCveMatricula());
					}

					logger.info("########## SE DESACTIVA EL USUARIO EN EL LDAP ##########");

					AdmonUsuariosService.desactivarUsuario(solOriginal.getDesUsrCurp());
				} else {
					logger.info("########## NO SE ENCONTRO EL USUARIO EN EL LDAP ##########");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdmonUsuariosException();
		}
	}
	
//	public static void main(String[] args) {
//		int numeroIntento =  0;
//		List<PerfilDTO> listPerfilAdmin = new ArrayList<PerfilDTO>();
//		List<PerfilDTO> listPerfilLdap = new ArrayList<PerfilDTO>();
//		String[] arrayPerfilesLdap = new String[5];
//		
//		PerfilDTO perfilDTO1 = new PerfilDTO();
//		perfilDTO1.setDesDefault("A");
//		
//		PerfilDTO perfilDTO2 = new PerfilDTO();
//		perfilDTO2.setDesDefault("B");
//		
//		PerfilDTO perfilDTO3 = new PerfilDTO();
//		perfilDTO3.setDesDefault("C");
//		
//		PerfilDTO perfilDTO4 = new PerfilDTO();
//		perfilDTO4.setDesDefault("D");
//		
//		PerfilDTO perfilDTO5 = new PerfilDTO();
//		perfilDTO5.setDesDefault("E");
//		
//		listPerfilAdmin.add(perfilDTO1);
//		listPerfilAdmin.add(perfilDTO2);
//		listPerfilAdmin.add(perfilDTO3);
//		listPerfilAdmin.add(perfilDTO4);
//		listPerfilAdmin.add(perfilDTO5);
//		
//		arrayPerfilesLdap[0] = "A";
//		arrayPerfilesLdap[1] = "B";
//		arrayPerfilesLdap[2] = "C";
//		arrayPerfilesLdap[3] = "D";
//		arrayPerfilesLdap[4] = "E";
//		
//		for (int i = 0; i < arrayPerfilesLdap.length; i++) {
//			PerfilDTO perfil = new PerfilDTO();
//			perfil.setDesDefault(arrayPerfilesLdap[i]);
//			listPerfilLdap.add(perfil);
//		}
//		
//		SolicitudDTO sol = new SolicitudDTO();
//		sol.setPerfilesDTO(listPerfilAdmin);
//		
//		SolicitudServiceImpl solicitudServiceLocal = new SolicitudServiceImpl();
//		
//		boolean listDiferentes = solicitudServiceLocal.comparaLista(sol, listPerfilLdap);
//		
//		System.out.println("########## primera compracion ["+listDiferentes+"] ##########");
//		
//		while (listDiferentes && numeroIntento < 3) {
//			System.out.println("########## No. intento ["+numeroIntento+"] ##########");
//			listDiferentes = solicitudServiceLocal.comparaLista(sol, listPerfilLdap);
//			numeroIntento++;
//		}
//		
//		if (listDiferentes && numeroIntento == 3) {
//			System.out.println("########## ERROR: No fue posible agregar los Roles y los Perfiles al Usuario del LDAP ##########");
//		} else {
//			System.out.println("########## Se agregaron los Roles y los Perfiles al Usuario del LDAP ##########");
//		}
//	}

	private boolean comparaLista(SolicitudDTO sol, List<PerfilDTO> listPerfiles) {
		boolean diferente = true;

		for (PerfilDTO perfilAdmin : sol.getPerfilesDTO()) {

			diferente = true;

			for (PerfilDTO perfilInsertado : listPerfiles) {

				if (perfilAdmin.getPuestoDTO().getNombrePuesto().equals(perfilInsertado.getDesDefault())) {
					diferente = false;
					logger.info("########## " + perfilAdmin.getPuestoDTO().getNombrePuesto() + " vs "
							+ perfilInsertado.getDesDefault() + " - "
							+ (diferente != false ? "PERFILES DIFERENTES" : "PERFILES IGUALES") + " ##########");
					break;
				}
				logger.info("########## " + perfilAdmin.getPuestoDTO().getNombrePuesto() + " vs "
						+ perfilInsertado.getDesDefault() + " - "
						+ (diferente != false ? "PERFILES DIFERENTES" : "PERFILES IGUALES") + " ##########");
			}
			if (diferente) {
				break;
			}
		}

		return diferente;
	}

	private void insertarRolesPerfilesLdap(SolicitudDTO sol, SsoSolicitud solOriginal, UsuarioDTO user)
			throws AdmonUsuariosException {
		logger.info("");
		logger.info(
				"########## SE INICIA LA ELIMINACION EN EL LDAP DE LOS ROLES, IMSS SISTEMAS Y PERFILES QUE TENIA EL USUARIO DEL LDAP ##########");

		AdmonRolesService.revocaRolesUsuario(sol.getDesUsrCurp(), getPerfiles(user.getPerfiles()));
		AdmonUsuariosService.modificaPerfilAprobador(sol.getDesUsrCurp(), " ");
		AdmonUsuariosService.modificaModuloAprobador(sol.getDesUsrCurp(), " ");

		logger.info("########## NUMERO DE ROLES Y PERFILES QUE SE INSERTARAN AL USUARIO DEL LDAP [" + sol.getPerfilesDTO().size() + "] ##########");

		for (PerfilDTO perfil : sol.getPerfilesDTO()) {
			SsoPerfilessol per = new SsoPerfilessol();
			per.setDesDefault(perfil.getDesDefault());
			per.setFecFechaRegistro(new Date());
			per.setSsoCatpuesto(getCatPuesto(perfil.getPuestoDTO().getCvePuesto()));
			per.setSsoSolicitud(solOriginal);

			logger.info("########## SE AGREGA EL ROL Y EL PERFIL [" + per.getSsoCatpuesto().getDesPuesto() + "] AL USUARIO DEL LDAP ##########");

			AdmonRolesService.asignaRolUsuario(sol.getDesUsrCurp(), per.getSsoCatpuesto().getDesPuesto());
			AdmonUsuariosService.agregaPerfilAprobador(sol.getDesUsrCurp(), per.getSsoCatpuesto().getDesPuesto());
		}
	}

	public String[] getPerfiles(String per) {
		String[] result = null;
		if (per != null && per.indexOf(",") > -1) {
			result = per.split(",");
		} else {
			result = new String[1];
			result[0] = per;
		}
		return result;
	}

	@Override
	public void actualizaAreaAdsUser(SolicitudDTO sol) throws AdmonUsuariosException {
		try {
			UsuarioDTO userLdap;
			userLdap = AdmonUsuariosService.obtenUsuario(sol.getDesUsrCurp());
			userLdap.setClaveDelegacion((int) sol.getDelegacionId());
			userLdap.setClaveSubDelegacion((int) sol.getSubdelegacionId());
			userLdap.setClaveUMF((int) sol.getUmfId());
			userLdap.setDescripcionCargo(sol.getPuestoDTO().getNombrePuesto());
			userLdap.setCorreoElectronico(sol.getRefCorreoElectronico());
			userLdap.setDescripcionArea(sol.getDptoDTO().getDesDepartamento());
			AdmonUsuariosService.modificarUsuario(userLdap);
		} catch (AdmonUsuariosException e) {
			e.printStackTrace();
			throw new AdmonUsuariosException();
		}

	}

	private SsoAccesomodulo getAccesoModudulo(Long cveSsoaccesomodulo) {
		final Query q = em.createQuery("select s from SsoAccesomodulo s where s.cveSsoaccesomodulo = :id");
		q.setParameter("id", cveSsoaccesomodulo);
		return (SsoAccesomodulo) q.getResultList().get(0);
	}

	private SsoPerfilessol getPerfilSol(Long cveSsoperfilessol) {
		final Query q = em.createQuery("select s from SsoPerfilessol s where s.cveSsoperfilessol = :id");
		q.setParameter("id", cveSsoperfilessol);
		return (SsoPerfilessol) q.getResultList().get(0);
	}

	private SsoCatdeptomodulo getCatDeptoModulo(long cveIdModulo, long cveIdDepto) {
		final Query q = em.createQuery(
				"select s from SsoCatdeptomodulo s where s.dicModulo.cveIdModulo = :idMod and s.ssoCatdepartamento.cveSsodepto = :idDepto");
		q.setParameter("idMod", cveIdModulo);
		q.setParameter("idDepto", cveIdDepto);
		return (SsoCatdeptomodulo) q.getResultList().get(0);
	}

	private SsoCatestatus getCatStatus(long cveEstatus) {
		final Query q = em.createQuery("select s from SsoCatestatus s where s.cveSsoestatus = :id");
		q.setParameter("id", cveEstatus);
		return (SsoCatestatus) q.getResultList().get(0);
	}

	private SsoCatpuesto getCatPuesto(long id) {
		final Query q = em.createQuery("select s from SsoCatpuesto s where s.cveSsopuesto = :id");
		q.setParameter("id", id);
		return (SsoCatpuesto) q.getResultList().get(0);
	}

	private DicDelegacion getDelegacion(long id) {
		final Query q = em.createQuery("select s from DicDelegacion s where s.cveIdDelegacion = :id");
		q.setParameter("id", id);
		return (DicDelegacion) q.getResultList().get(0);
	}

	private DicSubdelegacion getSubdelegacion(long id) {
		final Query q = em.createQuery("select s from DicSubdelegacion s where s.cveIdSubdelegacion = :id");
		q.setParameter("id", id);
		return (DicSubdelegacion) q.getResultList().get(0);
	}

	private DicUmf getUmf(long id) {
		final Query q = em.createQuery("select s from DicUmf s where s.cveIdUmf = :id");
		q.setParameter("id", id);
		List result = q.getResultList();
		return (DicUmf) result.get(0);
	}

	private SsoCatdepartamento getDepto(long id) {
		final Query q = em.createQuery("select s from SsoCatdepartamento s where s.cveSsodepto = :id");
		q.setParameter("id", id);
		return (SsoCatdepartamento) q.getResultList().get(0);
	}

	private SsoCatpuesto getPuesto(long id) {
		final Query q = em.createQuery("select s from SsoCatpuesto s where s.cveSsopuesto = :id");
		q.setParameter("id", id);
		return (SsoCatpuesto) q.getResultList().get(0);
	}

	@Override
	public RespuestaFirmadoSimple getSelloDigital(String cadenaOriginal,
			String secuenciaNotaria, String rfc) {
		RespuestaFirmadoSimple firma = null;

		try {
			String serie = this.getPropiedadDeProperties("serie");
			PeticionFirmadoSimple peticion = new PeticionFirmadoSimple();

			String jsonParams = null;

			peticion.setId_llavefirma(serie);

			if (secuenciaNotaria != null) {
				peticion.setTramite(secuenciaNotaria);
			} else {
				peticion.setAplicacion(this.getPropiedadDeProperties("aplicacion"));
				if (rfc == null) {
					rfc = this.getPropiedadDeProperties("rfc.imss");
				}
				peticion.setRfc(rfc);
			}

			peticion.setCadenaoriginal(cadenaOriginal);

			jsonParams = this.convertirAJSON(peticion, PeticionFirmadoSimple.class.getName());

			logger.info("Los parametros para la firma son: " + jsonParams);
			logger.info("Serie: " + serie);

			ObjectFactory objf = new ObjectFactory();
			FirmaSimpleRequestType fsrt = objf.createFirmaSimpleRequestType();
			fsrt.setJsonParms(jsonParams);
			FirmaElectronicaSegService firmaElectronicaSegService = new FirmaElectronicaSegService();
			FirmaElectronicaSegPortType firmaElectronicaSegPortType = firmaElectronicaSegService
					.getFirmaElectronicaSegPortTypePort();

			FirmaSimpleResponseType respuestaFirmado = firmaElectronicaSegPortType.firmaSimple(fsrt);

			ResultadoType resultado = respuestaFirmado.getResultado();
			if (resultado.getCodigo() != 0) {
				logger.info("Codigo: " + resultado.getCodigo());
				logger.info("Descripcion: " + resultado.getTexto());
			} else {
				logger.warn("la respuesta del servicio es: " + respuestaFirmado.getJsonSalida());
				firma = (RespuestaFirmadoSimple) this.convertirAObjeto(respuestaFirmado.getJsonSalida(),
						RespuestaFirmadoSimple.class.getName());

				logger.warn("Secuencia de notaria arrojada por la firma: " + firma.getId());
				logger.warn("Numero de serie firmante: " + firma.getNoSerie());
				logger.warn("Firma: " + firma.getSello());
			}

		} catch (Exception ex) {
			logger.error("##### Existio un error al obtener el sello digital en el servicio web: " + ex.getMessage());
			logger.error("##### ocurrio un error", ex);
		}

		return firma;
	}

	private String getPropiedadDeProperties(String propiedad) {
		String propertie = null;
		Properties properties = new Properties();

		try {
			InputStream is = new ClassPathResource("firmaDigital.properties").getInputStream();
			properties.load(is);
			is.close();

			propertie = properties.getProperty(propiedad);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		return propertie;
	}

	private String convertirAJSON(Object object, String clase) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String jsonParams = null;

		try {
			jsonParams = mapper.writeValueAsString((Class.forName(clase).cast(object)));
		} catch (JsonGenerationException e) {
			logger.error("Error en el parseo a JSON", e);
			throw new Exception("No fue posible generar el json");
		} catch (JsonMappingException e) {
			logger.error("Error en el parseo a JSON", e);
			throw new Exception("No fue posible generar el json");
		} catch (IOException e) {
			logger.error("Error en el parseo a JSON", e);
			throw new Exception("No fue posible generar el json");
		}

		return jsonParams;
	}

	private Object convertirAObjeto(String json, String clase) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Object object = null;
		logger.warn("El string del json a convertir es : " + json + " y se convertira a : " + clase);
		try {
			object = mapper.readValue(json, Class.forName(clase));
		} catch (JsonParseException e) {
			logger.error("Error en el parseo a JSON", e);
			throw new Exception("No fue posible generar el json");
		} catch (JsonMappingException e) {
			logger.error("Error en el parseo a JSON", e);
			throw new Exception("No fue posible generar el json");
		} catch (IOException e) {
			logger.error("Error en el parseo a JSON", e);
			throw new Exception("No fue posible generar el json");
		}
		logger.warn("Se ha generado correctamente el objeto a partir de un string json");

		return Class.forName(clase).cast(object);
	}

	@Override
	public void guardaMovimientosUsuario(String idSolicitud, int estatus, String desMovimiento,
			List<PuestoDTO> listaRoles, List<ModuloDTO> listaModulos, Long idAprobador)
			throws AdmonUsuariosException {
		Query query = em.createNamedQuery("Solicitudes.findById");
		query.setParameter("id", Long.parseLong(idSolicitud));
		Solicitudes solicitud = (Solicitudes) query.getSingleResult();

		query = em.createNamedQuery("Estatus.findByClave");
		query.setParameter("id", estatus);
		Estatus estatusObj = (Estatus) query.getSingleResult();

		String datosMovimiento = "EL USUARIO " + solicitud.getNomNombre() + " "
				+ solicitud.getNomPaterno() + " " + solicitud.getNomMaterno()
				+ " CON LA CURP " + solicitud.getCurp()
				+ " HA REALIZADO EL MOVIMIENTO DE: " + desMovimiento
				+ " CON LOS SIGUIENTES PERFILES Y MODULOS ";

		datosMovimiento += " GRUPOS: ";
		if (estatus != 1) {
			for (PuestoDTO rol : listaRoles) {
				datosMovimiento += rol.getNombrePuesto() + " ,";
			}
		}

		datosMovimiento += " MODULOS: ";
		if (estatus != 1) {
			for (ModuloDTO mod : listaModulos) {
				datosMovimiento += mod.getDesModulo() + " ,";
			}
		}

		UsuarioMovimientos usrMov = new UsuarioMovimientos();
		usrMov.setSolicitud(solicitud);
		usrMov.setEstatus(estatusObj);
		usrMov.setDatosMovimiento(datosMovimiento);
		// usrMov.setAprobador(aprobador);
		usrMov.setFechaRegistro(new Date());
		em.persist(usrMov);

	}

	@Override
	public int registrarSolicitudCuentaNVer(UsuarioDTO usuarioSolicitud)
			throws AdmonUsuariosException {
		String perfiles = "";
		SsoSolicitud solicitud = new SsoSolicitud();
		try {
			if (usuarioSolicitud.getNombres() != null) {
				solicitud.setNomNombre(usuarioSolicitud.getNombres());
			}
			if (usuarioSolicitud.getApellidoPaterno() != null) {
				solicitud.setNomPaterno(usuarioSolicitud.getApellidoPaterno());
			}
			if (usuarioSolicitud.getApellidoMaterno() != null) {
				if (usuarioSolicitud.getApellidoMaterno().trim().length() > 0) {
					solicitud.setNomMaterno(usuarioSolicitud.getApellidoMaterno());
				} else {
					solicitud.setNomMaterno(" ");
				}
			} else {
				solicitud.setNomMaterno(" ");
			}
			if (usuarioSolicitud.getCorreoElectronico() != null) {
				solicitud.setRefCorreoElectronico(usuarioSolicitud.getCorreoElectronico());
			}
			if (usuarioSolicitud.getTelefono() != null) {
				solicitud.setDesTelefonoOfi(usuarioSolicitud.getTelefono());
			}
			solicitud.setFecRegistroAlta(new Date());
			if (usuarioSolicitud.getMatricula() != null) {
				solicitud.setCveMatricula(usuarioSolicitud.getMatricula());
			}
			solicitud.setSsoCatestatus(getCatStatus(1));

			if (usuarioSolicitud.getClaveDelegacion() != null && usuarioSolicitud.getClaveDelegacion() != -99
					&& usuarioSolicitud.getClaveDelegacion() > 0) {
				solicitud.setDicDelegacion(getDelegacion(usuarioSolicitud.getClaveDelegacion()));
			}
			if (usuarioSolicitud.getClaveSubDelegacion() != null && usuarioSolicitud.getClaveSubDelegacion() != -99
					&& usuarioSolicitud.getClaveSubDelegacion() > 0) {
				solicitud.setDicSubdelegacion(getSubdelegacion(usuarioSolicitud.getClaveSubDelegacion()));
			}
			if (usuarioSolicitud.getClaveUMF() != null && usuarioSolicitud.getClaveUMF() != -99
					&& usuarioSolicitud.getClaveUMF() > 0) {
				solicitud.setDicUmf(getUmf(usuarioSolicitud.getClaveUMF()));
			}
			SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy-MM-dd");
			int complemento = Integer.parseInt(usuarioSolicitud.getCurp().substring(4, 6));
			String agregado = "";
			if (complemento < 20)
				agregado = "20";
			else
				agregado = "19";
			Date fecha = formatoDeFecha.parse(agregado
					+ usuarioSolicitud.getCurp().substring(4, 6) + "-"
					+ usuarioSolicitud.getCurp().substring(6, 8) + "-"
					+ usuarioSolicitud.getCurp().substring(8, 10));
			solicitud.setFecUsrNacimiento(fecha);
			if (usuarioSolicitud.getCurp() != null) {
				solicitud.setDesUsrCurp(usuarioSolicitud.getCurp());

			}
			solicitud.setSsoCatdepartamento(getDepto(usuarioSolicitud.getClaveDepartamento().longValue()));
			solicitud.setSsoCatpuesto(getCatPuesto(usuarioSolicitud.getClavePuesto().longValue()));
			solicitud.setSsoCatestatus(getCatStatus(1));
			solicitud.setCveIdEntidad(15 + "");
			solicitud.setCveSsosolicitud(null);

			solicitud.setNss(usuarioSolicitud.getNssNom());
			solicitud.setPuesto(usuarioSolicitud.getPuestoDescNom());
			solicitud.setDepartamento(usuarioSolicitud.getDepartamentoDescNom());
			solicitud.setCveDelegacion(usuarioSolicitud.getCveDelegacionNom());
			solicitud.setCveSubdelegacion(usuarioSolicitud.getCveSubdelegacionNom());
			solicitud.setCveUmf(usuarioSolicitud.getCveUmfNom());
			solicitud.setEstatus(usuarioSolicitud.getCveEstatusNom());

			super.saveorupdate(solicitud);

			return 0;
		} catch (AdmonUsuariosException aue) {
			logger.error("registrarSolicitudCuentaNVer", aue);
			throw new AdmonUsuariosException("Error al registrar solicitud");
		} catch (Exception ex) {
			logger.error("General registrarSolicitudCuentaNVer", ex);
			throw new AdmonUsuariosException("Error al registrar solicitud");
		}
	}

}
