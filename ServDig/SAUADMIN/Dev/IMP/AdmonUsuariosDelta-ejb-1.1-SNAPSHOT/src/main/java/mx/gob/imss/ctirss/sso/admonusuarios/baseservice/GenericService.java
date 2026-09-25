package mx.gob.imss.ctirss.sso.admonusuarios.baseservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.admonusuarios.abstractModel.AbstractModel;
import mx.gob.imss.ctirss.admonusuarios.entidad.DicDelegacion;
import mx.gob.imss.ctirss.admonusuarios.entidad.DicSubdelegacion;
import mx.gob.imss.ctirss.admonusuarios.entidad.DicUmf;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoAccesomodulo;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoActivaCuenta;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoCatareanormativa;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoCatdepartamento;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoCatestatus;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoCatpuesto;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoPerfilessol;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoSolicitud;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.ActivaCuentaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.AreaNormativaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DepartamentoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.EstatusDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ModuloDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PerfilDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SolicitudDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SubdelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UmfDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;

public abstract class GenericService {
	
	private final static Logger logger = Logger.getLogger(GenericService.class);
	
	@PersistenceContext
	protected EntityManager em;

	protected CriteriaBuilder cb;
	
	protected EntityManagerFactory emf;


	
	
	protected void saveorupdate(AbstractModel abs)throws AdmonUsuariosException
	{
		try {
			em.persist(abs);
			em.flush();
		} catch (Exception ex) {
			logger.error("Error::saveorupdate " + ex.getMessage());
			logger.error("  ", ex);
			throw new AdmonUsuariosException(); 
		}
	}
	


	protected void delete(AbstractModel abs)throws AdmonUsuariosException
	{
		try {
			em.remove(abs);
			em.flush();
		} catch (Exception ex) {
			logger.error("Error::delete " + ex.getMessage());
			logger.error("  ", ex);
			throw new AdmonUsuariosException(); 
		}
	}

	
	private UmfDTO getUmfDTO(DicUmf dicUmf) {
		UmfDTO dto = null;
		if(dicUmf!=null)
		{
			dto = new UmfDTO();
			dto.setCveUmf(dicUmf.getCveIdUmf());
			dto.setNombreUmf(dicUmf.getNomUnidad());
		}
		return dto;
	}
	
	protected SolicitudDTO getSolicitudDTO(SsoSolicitud sol) {
		try {
			SolicitudDTO dto = new SolicitudDTO();
			dto.setCveMatricula(sol.getCveMatricula());
			dto.setDesUsrCurp(sol.getDesUsrCurp());
			dto.setNomMaterno(sol.getNomMaterno());
			dto.setCveSsosolicitud(sol.getCveSsosolicitud());
			dto.setNomNombre(sol.getNomNombre());
			dto.setNomPaterno(sol.getNomPaterno());
			dto.setRefCorreoElectronico(sol.getRefCorreoElectronico());
			dto.setDesTelefonoOfi(sol.getDesTelefonoOfi());
			
			dto.setModulosDTO(getModulos(sol.getSsoAccesomodulos()));
			dto.setPuestosDTO(getPuestos(sol.getSsoPerfilessols()));
			dto.setPerfilesDTO(getPerfiles(sol.getSsoPerfilessols()));
			
			dto.setFecRegistroAlta(sol.getFecRegistroAlta());
			dto.setEstatusDTO(getEstatusDTO(sol.getSsoCatestatus()));
			// Area normativa
			if (sol.getSsoCatdepartamento() != null) {
				dto.setAreaNorm(getAreaNormativaDTO(sol.getSsoCatdepartamento()
						.getSsoCatareanormativa()));
			} else {
				dto.setAreaNorm(null);
			}
			dto.setPuestoDTO(getPuestoDTO(sol.getSsoCatpuesto()));
			dto.setDptoDTO(getDeptoDTO(sol.getSsoCatdepartamento()));

			dto.setNssNom(sol.getNss());
			dto.setPuestoDescNom(sol.getPuesto());
			dto.setDepartamentoDescNom(sol.getDepartamento());
			dto.setCveDelegacionNom(sol.getCveDelegacion());
			dto.setCveEstatusNom(sol.getEstatus());

			// Delegacion
			dto.setDelDTO(getDelegacionDTO(sol.getDicDelegacion()));
			// Subdelegacion
			dto.setSubdelDTO(getSubdelegacionDTO(sol.getDicSubdelegacion()));
			// UMF
			dto.setUmfDTO(getUmfDTO(sol.getDicUmf()));

			return dto;
		} catch (Exception ex) {
			logger.error("Error::getSolicitudDTO " + ex.getMessage());
			logger.error("  ", ex);
		}
		return null;
	}

	protected SubdelegacionDTO getSubdelegacionDTO(DicSubdelegacion dicSubdelegacion) {
		try {
			SubdelegacionDTO dto = null;
			if (dicSubdelegacion != null) {
				dto = new SubdelegacionDTO();
				dto.setCveSubelegacion(dicSubdelegacion.getCveIdSubdelegacion());
				dto.setNombreSubelegacion(dicSubdelegacion.getDesSubdelegacion());
			}
			return dto;
		} catch (Exception ex) {
			logger.error("Error::getSubdelegacionDTO " + ex.getMessage());
			logger.error("  ", ex);
		}
		return null;
	}

	protected DelegacionDTO getDelegacionDTO(DicDelegacion dicDelegacion) {
		try {
			DelegacionDTO dto = null;
			if (dicDelegacion != null) {
				dto = new DelegacionDTO();
				dto.setCveDelegacion(dicDelegacion.getCveIdDelegacion());
				dto.setNombreDelegacion(dicDelegacion.getDesDeleg());
			}
			return dto;
		} catch (Exception ex) {
			logger.error("Error::getDelegacionDTO " + ex.getMessage());
			logger.error("  ", ex);
		}
		return null;
	}

	protected PuestoDTO getPuestoDTO(SsoCatpuesto ssoCatpuesto) {
		try {
			PuestoDTO dto = null;
			if (ssoCatpuesto != null) {
				dto = new PuestoDTO();
				dto.setCvePuesto(ssoCatpuesto.getCveSsopuesto());
				dto.setNombrePuesto(ssoCatpuesto.getDesPuesto());
				dto.setDepartamento(getDeptoDTO(ssoCatpuesto.getSsoCatdepartamento()));
			}
			return dto;
		} catch (Exception ex) {
			logger.error("Error::getPuestoDTO " + ex.getMessage());
			logger.error("  ", ex);
		}
		return null;
	}

	protected EstatusDTO getEstatusDTO(SsoCatestatus status) {
		try {
			EstatusDTO dto = new EstatusDTO();
			dto.setCveSsoestatus(status.getCveSsoestatus());
			dto.setDesEstatus(status.getDesEstatus());
			return dto;
		} catch (Exception ex) {
			logger.error("Error::getEstatusDTO " + ex.getMessage());
			logger.error("  ", ex);
		}
		return null;
	}

	protected List<PuestoDTO> getPuestos(Set<SsoPerfilessol> perfiles) {
		try {
			List<PuestoDTO> result = new ArrayList<PuestoDTO>();
			for (SsoPerfilessol perfil : perfiles) {
				if (perfil != null) {
					PuestoDTO dto = new PuestoDTO();
					dto.setCvePuesto(perfil.getSsoCatpuesto().getCveSsopuesto());
					dto.setDefaultRol(perfil.getDesDefault());
					dto.setNombreArea(perfil.getSsoCatpuesto().getSsoCatdepartamento()
							.getSsoCatareanormativa().getDesAreanorma());
					dto.setNombreDepartamento(perfil.getSsoCatpuesto()
							.getSsoCatdepartamento().getDesDepartamento());
					dto.setNombrePuesto(perfil.getSsoCatpuesto().getDesPuesto());
					dto.setNuevoReg(false);
					result.add(dto);
				}
			}
			return result;
		} catch (Exception ex) {
			logger.error("Error::getPuestos " + ex.getMessage());
			logger.error("  ", ex);
		}
		return null;
	}

	protected List<PerfilDTO> getPerfiles(Set<SsoPerfilessol> perfiles) {
		try {
			List<PerfilDTO> result = new ArrayList<PerfilDTO>();
			for (SsoPerfilessol perfil : perfiles) {
				if (perfil != null) {
					PerfilDTO dto = new PerfilDTO();
					if(perfil.getCveSsoperfilessol()!=null)
						dto.setCveSsoperfilessol(perfil.getCveSsoperfilessol());
					dto.setDesDefault(perfil.getDesDefault());
					dto.setAreaNormDTO(getAreaNormativaDTO(perfil.getSsoCatpuesto()
							.getSsoCatdepartamento().getSsoCatareanormativa()));
					dto.setDeptoDTO(getDeptoDTO(perfil.getSsoCatpuesto()
							.getSsoCatdepartamento()));
					dto.setPuestoDTO(getPuestoDTO(perfil.getSsoCatpuesto()));
					dto.setSolicitudDTO(null);
					result.add(dto);
				}
			}
			return result;
		} catch (Exception ex) {
			logger.error("Error::getPerfiles " + ex.getMessage());
			logger.error("  ", ex);
		}
		return null;
	}

	protected List<ModuloDTO> getModulos(Set<SsoAccesomodulo> accModulos) {
		try {
			List<ModuloDTO> result = new ArrayList<ModuloDTO>();
			for (SsoAccesomodulo accMod : accModulos) {
				ModuloDTO dto = new ModuloDTO();
				dto.setNuevoReg(false);
				if (accMod.getSsoCatdeptomodulo() != null) {
					// Modulo
					if (accMod.getSsoCatdeptomodulo().getDicModulo() != null) {
						dto.setCveIdModulo(accMod.getSsoCatdeptomodulo()
								.getDicModulo().getCveIdModulo());
						dto.setDesModulo(accMod.getSsoCatdeptomodulo()
								.getDicModulo().getDesModulo());
					}
					// Area Normativa
					if (accMod.getSsoCatdeptomodulo().getSsoCatdepartamento() != null) {
						dto.setAreaNorm(getAreaNormativaDTO(accMod.getSsoCatdeptomodulo()
								.getSsoCatdepartamento().getSsoCatareanormativa()));
					}
					// Departamento
					dto.setDptoDTO(getDeptoDTO(accMod.getSsoCatdeptomodulo()
							.getSsoCatdepartamento()));
				}
				dto.setCveAccesoModulo(accMod.getCveSsoaccesomodulo() != null ? accMod.getCveSsoaccesomodulo() : null);

				result.add(dto);
			}
			return result;
		} catch (Exception ex) {
			logger.error("Error::getModulos " + ex.getMessage());
			logger.error("  ", ex);
		}
		return null;
	}

	protected DepartamentoDTO getDeptoDTO(SsoCatdepartamento ssoCatdepartamento) {
		try {
			DepartamentoDTO dto = null;
			if (ssoCatdepartamento != null) {
				dto = new DepartamentoDTO();
				dto.setCveSsodepto(ssoCatdepartamento.getCveSsodepto());
				dto.setDesDepartamento(ssoCatdepartamento.getDesDepartamento());
				dto.setAreaNormativa(getAreaNormativaDTO(ssoCatdepartamento.getSsoCatareanormativa()));
				dto.setDesLeyendaAcuse(ssoCatdepartamento.getDesLeyendaAcuse());
			}
			return dto;
		} catch (Exception ex) {
			logger.error("Error::getDeptoDTO " + ex.getMessage());
			logger.error("  ", ex);
		}
		return null;
	}

	protected AreaNormativaDTO getAreaNormativaDTO(SsoCatareanormativa ssoCatareanormativa) {
		try {
			AreaNormativaDTO dto = null;
			if (ssoCatareanormativa != null) {
				dto = new AreaNormativaDTO();
				dto.setCveSsoareanorma(ssoCatareanormativa.getCveSsoareanorma());
				dto.setDesAreanorma(ssoCatareanormativa.getDesAreanorma());
			}
			return dto;
		} catch (Exception ex) {
			logger.error("Error::getAreaNormativaDTO " + ex.getMessage());
			logger.error("  ", ex);
		}
		return null;
	}

	
	protected List<SolicitudDTO> getSolicitudesDTO(List<SsoSolicitud> sols) {
		try {
			if (sols != null && sols.size() > 0) {
				List<SolicitudDTO> result = new ArrayList<SolicitudDTO>();
				for (SsoSolicitud sol : sols) {
					SolicitudDTO dto = getSolicitudDTO(sol);
					result.add(dto);
				}
				return result;
			}
			return null;
		} catch (Exception ex) {
			logger.error("Error::getSolicitudesDTO " + ex.getMessage());
			logger.error("  ", ex);
		}
		return null;
	}

	
	protected List<ActivaCuentaDTO> getActivaCuentasDTO(List<SsoActivaCuenta> acs) {
		try {
			if (acs != null && acs.size() > 0) {
				List<ActivaCuentaDTO> result = new ArrayList<ActivaCuentaDTO>();
				for (SsoActivaCuenta ac : acs) {
					ActivaCuentaDTO dto = getActivaCuentasDTO(ac);
					result.add(dto);
				}
				return result;
			}
			return null;
		} catch (Exception ex) {
			logger.error("Error::getActivaCuentasDTO " + ex.getMessage());
			logger.error("  ", ex);
		}
		return null;
	}

	private ActivaCuentaDTO getActivaCuentasDTO(SsoActivaCuenta ac) {
		try {
			if(ac!=null)
			{
				ActivaCuentaDTO dto = new ActivaCuentaDTO();
				dto.setClaveActivaCuenta(ac.getSsoClaveActiva());
				dto.setClaveMD5(ac.getClaveMD5());
				dto.setEstatus(getEstatusDTO(ac.getSsoEstatus()));
				dto.setFechaActiva(ac.getFechaActiva());
				dto.setFechaRegistro(ac.getFechaRegistro());
				dto.setFechaVigencia(ac.getFechaVigencia());
				dto.setSolicitud(getSolicitudDTO(ac.getSsoSolicitud()));
				return dto;
			}
			return null;
		} catch (Exception ex) {
			logger.error("Error::getActivaCuentasDTO " + ex.getMessage());
			logger.error("  ", ex);
		}
		return null;
	}



	protected List<SolicitudDTO> getSolicitudesDTO(List<SsoSolicitud> sols,String curp) {
		try {
			if (sols != null && sols.size() > 0) {
				List<SolicitudDTO> result = new ArrayList<SolicitudDTO>();
				for (SsoSolicitud sol : sols) {
//					System.out.println("***********************Datos de la solicitud in********************************");
//					System.out.println("curp : "+sol.getDesUsrCurp());
//					if(sol.getSsoPerfilessols()!=null)
//						System.out.println("numero de perfiles :"+sol.getSsoPerfilessols().size());
//					else
//						System.out.println("numero de perfiles: 0");
//					if(sol.getSsoAccesomodulos()!=null)
//						System.out.println("numero de modulos :"+sol.getSsoAccesomodulos().size());
//					else
//						System.out.println("numero de modulos: 0");
					if (!sol.getDesUsrCurp().trim().equals(curp.trim())) {
						SolicitudDTO dto = getSolicitudDTO(sol);
						result.add(dto);
					}
//					System.out.println("***********************Datos de la solicitud out********************************");
				}
				return result;
			}
			return null;
		} catch (Exception ex) {
			logger.error("Error::getSolicitudesDTO " + ex.getMessage());
			logger.error("  ", ex);
		}
		return null;
	}

	protected ActivaCuentaDTO getActivarCuentaDTO(SsoActivaCuenta aC) {
		try {
			ActivaCuentaDTO dto = new ActivaCuentaDTO();
			dto.setClaveActivaCuenta(aC.getSsoClaveActiva());
			dto.setClaveMD5(aC.getClaveMD5());
			dto.setEstatus(getEstatusDTO(aC.getSsoEstatus()));
			dto.setFechaActiva(aC.getFechaActiva());
			dto.setFechaRegistro(aC.getFechaRegistro());
			dto.setFechaVigencia(aC.getFechaVigencia());
			dto.setSolicitud(getSolicitudDTO(aC.getSsoSolicitud()));
			return dto;
		} catch (Exception ex) {
			logger.error("Error::getActivarCuentaDTO " + ex.getMessage());
			logger.error("  ", ex);
		}
		return null;
	}



}
