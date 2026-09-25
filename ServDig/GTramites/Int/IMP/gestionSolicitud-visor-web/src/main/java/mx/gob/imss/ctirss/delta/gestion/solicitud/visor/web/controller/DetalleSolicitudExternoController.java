package mx.gob.imss.ctirss.delta.gestion.solicitud.visor.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.util.Base64Cipher;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rep.legal.RepresentanteLegalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite32D;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteMoral;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramitePersonaAutorizada;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRiss;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/solicitud/detalle")
public class DetalleSolicitudExternoController extends AbstractController {
	@Autowired
	private SolicitudBusinessRemote solicitudBusiness;
	@Autowired
	private RepresentanteLegalServiceBusinessRemote representanteLegalServiceBusiness;
	@Autowired
	private SolicitudServiceBusinessRemote solicitudServiceBusiness;
	@Autowired
	private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;

	@RequestMapping(value="/{folio}", method = RequestMethod.GET)
	public String mostrarDetalleSolicitudGet(@PathVariable String folio,
			Model model, HttpSession session, HttpServletRequest request) {
		mostrarDetalleSolicitudCommon(folio, model, session, request, true);		
		return "detalleSolicitudExterno";
	}
	
	@RequestMapping(value="/ciudadano/{folio}", method = RequestMethod.GET)
	public String mostrarDetalleSolicitud(@PathVariable String folio,
			Model model, HttpSession session, HttpServletRequest request) {
		mostrarDetalleSolicitudCommon(folio, model, session, request, true);		
		return "portletSolicitudesDetalleSimple";
	}

	@RequestMapping(value="/ciudadano/folioNormal/{folio}", method = RequestMethod.GET)
	public String mostrarDetalleSolicitudFolioNormal(@PathVariable String folio,
			Model model, HttpSession session, HttpServletRequest request) {
		mostrarDetalleSolicitudCommon(folio, model, session, request, false);		
		return "portletSolicitudesDetalleSimple";
	}
	
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
	public String mostrarDetalleSolicitudPost(@RequestParam String folio,
			Model model, HttpSession session, HttpServletRequest request) {
		mostrarDetalleSolicitudCommon(folio, model, session, request, true);
		return "detalleSolicitudExterno";
	}
	
	public void mostrarDetalleSolicitudCommon(String folio, Model model, 
			HttpSession session, HttpServletRequest request, boolean descifrar) {		
		this.log.debug("Folio cifrado: " + folio);
		String folioDescifrado = null;
		try {
			boolean bFisica = false;
			folioDescifrado = ((descifrar) ? Base64Cipher.descrifrar(folio) : folio);
			this.log.debug("Folio descifrado: " + folioDescifrado);

			Solicitud solicitud = new Solicitud();
			solicitud.setNoFolioSolicitud(folioDescifrado);

			solicitud = solicitudBusiness.consultarFolio(solicitud);

			if (solicitud.getEstadoSolicitud().getIdEstadoSolicitud()
					.equals(EstadoSolicitudEnum.ATENDIDA.getCodigo())) {
				SujetoObligado sujOblig = obtenerSujObligTramites(solicitud.getTramites());

				this.log.debug("Sujeto Obligado para mostrar en el detalle de la solicitud ("
						+ solicitud.getNoFolioSolicitud() + ") -> " + sujOblig);

				solicitud.setSujetoObligado(sujOblig);

				TipoPersonaEnum tipoPersona = null;
				Long idPersona = null;
				Long idTipoPersona = null;

				if (sujOblig != null && sujOblig.getFisica() != null) {
					bFisica = true;
					tipoPersona = TipoPersonaEnum.FISICA;
					idPersona = sujOblig.getFisica().getIdPersona();
					idTipoPersona = tipoPersona.getId();
				} else if (sujOblig != null && sujOblig.getMoral() != null) {
					tipoPersona = TipoPersonaEnum.MORAL;
					idPersona = sujOblig.getMoral().getIdPersona();
					idTipoPersona = tipoPersona.getId();
				} else {
					this.log.warn("La Solicitud " + folioDescifrado + " no tiene persona asociada");
				}

				this.log.debug("Obteniendo representantes para solicitado por....");

				if (idPersona != null && tipoPersona != null) {
					List<RepresentanteLegal> representantesLegales = representanteLegalServiceBusiness
							.obtenerRepresentantesLegalesConActosAdmonPorPersona(
									idPersona, tipoPersona);

					model.addAttribute("listaRepresentantesSolicitantes",
							representantesLegales);
					this.log.debug("Finaliza obteniendo representantes para solicitado por...."
							+ representantesLegales);
				}
			}

			if (solicitud.getSujetoObligado() != null
					&& StringUtils.isNotBlank(solicitud.getSujetoObligado().getNumeroRegistroPatronal())) {
				String nrp = solicitud.getSujetoObligado().getNumeroRegistroPatronal();

				if (nrp.length() == 8) {
					// Falta modalidad y dígito verificador
					nrp += solicitud.getSujetoObligado().getModalidad().getNumModalidad();
					nrp += solicitud.getSujetoObligado().getDigVerificador();
					solicitud.getSujetoObligado().setNumeroRegistroPatronal(nrp);
				} else if (nrp.length() == 10) {
					// Falta dígito verificador
					nrp += solicitud.getSujetoObligado().getDigVerificador();
					solicitud.getSujetoObligado().setNumeroRegistroPatronal(nrp);
				}
			}

			model.addAttribute("solicitud", solicitud);
			model.addAttribute("bFisica", bFisica);

			request.setAttribute("idSolicitud", solicitud.getSolicitudId());
			request.setAttribute("sujetoObligado", solicitud.getSujetoObligado());
		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
			e.printStackTrace();
			Solicitud solicitud = new Solicitud();
			solicitud.setNoFolioSolicitud(folioDescifrado);
			solicitud.setErrorFormGeneral(e.getMessage());
			model.addAttribute("solicitud", solicitud);
		} catch (Exception e) {
			this.log.error(e);
			e.printStackTrace();
			Solicitud solicitud = new Solicitud();
			solicitud.setNoFolioSolicitud(folioDescifrado);
			solicitud.setErrorFormGeneral("Ocurrió un error inesperado.");
			model.addAttribute("solicitud", solicitud);
		}		
	}
	
	private SujetoObligado obtenerSujObligTramites(List<Tramite> tramites) {
		SujetoObligado sujOblig = null;

		for (Tramite tramite : tramites) {
			if (tramite instanceof TramiteFisica) {
				sujOblig = new SujetoObligado();
				sujOblig.setFisica(((TramiteFisica) tramite).getFisica());
				break;
			} else if (tramite instanceof TramiteMoral) {
				sujOblig = new SujetoObligado();
				sujOblig.setMoral(((TramiteMoral) tramite).getMoral());
				break;
			} else if (tramite instanceof TramiteAsegurado) {
				sujOblig = new SujetoObligado();
				sujOblig.setFisica(((TramiteAsegurado) tramite).getFisica());
				break;
			} else if (tramite instanceof TramiteSujetoObligado) {
				sujOblig = ((TramiteSujetoObligado) tramite).getSujetoObligado();
				break;
			} else if (tramite instanceof TramiteRiss) {
				sujOblig = new SujetoObligado();
				TramiteRiss tramiteRiss = (TramiteRiss) tramite;
				Fisica fisica = tramiteRiss.getFisica();

				if (fisica != null) {
					sujOblig.setFisica(fisica);
				} else if (!CollectionUtils.isEmpty(tramiteRiss
						.getListaCveIdSujetosObligados())) {
					/*
					 * Se toma la primera posición ya que para este tramite los
					 * sujetos obligados pertenecen a la misma persona
					 */
					sujOblig.setCveIdSujetoObligado(tramiteRiss.getListaCveIdSujetosObligados().get(0));
					sujOblig.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
					sujOblig = sujetoObligadoServiceBusiness.obtenerDetalleRP(sujOblig);
				}

				break;
			} else if (tramite instanceof TramitePersonaAutorizada) {
				sujOblig = new SujetoObligado();
				TramitePersonaAutorizada tramitePersonaAut = (TramitePersonaAutorizada) tramite;

				if (tramitePersonaAut.getPersonaFisica() != null) {
					sujOblig.setFisica(tramitePersonaAut.getPersonaFisica());
				} else if (tramitePersonaAut.getPersonaMoral() != null) {
					sujOblig.setMoral(tramitePersonaAut.getPersonaMoral());
				}

				break;
			} else if (tramite instanceof TramiteRepresentanteLegal) {
				sujOblig = new SujetoObligado();
				sujOblig.setFisica(((TramiteRepresentanteLegal) tramite).getFisica());

				break;
			} else if (tramite instanceof Tramite32D) {
				sujOblig = new SujetoObligado();
				Tramite32D tramite32d = (Tramite32D) tramite;

				if (tramite32d.getPersonaFM() != null) {
					if (tramite32d.getPersonaFM() instanceof Fisica) {
						sujOblig.setFisica((Fisica) tramite32d.getPersonaFM());
					} else {
						sujOblig.setMoral((Moral) tramite32d.getPersonaFM());
					}
				}
				break;
			}
		}

		return sujOblig;
	}
}
