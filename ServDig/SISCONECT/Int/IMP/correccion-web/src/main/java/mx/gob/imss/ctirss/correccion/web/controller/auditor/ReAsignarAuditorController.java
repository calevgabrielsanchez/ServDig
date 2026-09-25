package mx.gob.imss.ctirss.correccion.web.controller.auditor;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.ctirss.correccion.administracion.auditor.AuditorService;
import mx.gob.imss.ctirss.correccion.administracion.auditor.paginador.CrtAuditorAsignadoWrapperDataTable;
import mx.gob.imss.ctirss.correccion.administracion.auditor.paginador.CrtSegUsuarioWrapperDataTable;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.correccion.service.interfaces.SolicitudService;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.invitacion.service.interfaces.InvitacionService;
import mx.gob.imss.ctirss.correccion.login.model.SegUsuario;
import mx.gob.imss.ctirss.correccion.model.CrtAuditorAsignado;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacion;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.model.SsoUsuarios;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.promocion.service.interfaces.PromocionService;
import mx.gob.imss.ctirss.correccion.service.interfaces.IPatronesService;
import mx.gob.imss.ctirss.correccion.session.UserSession;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 
 * @author Enrique Duran Jimenez
 * @date 04/06/2012
 * @version 1.1.1
 * Controlador para el modulo Reasignar Auditor
 */
@Controller
@RequestMapping(value="/administracion/reAsignaAuditor")
@JsonIgnoreProperties(ignoreUnknown=true)
public class ReAsignarAuditorController extends AbstractController{
	
	@Autowired  private PromocionService<CrtPromocion> promocionService;
	@Autowired	private IPatronesService patronesService;
	@Autowired  private AuditorService<SsoUsuarios> auditorAsignadoUsuarioService;
	@Autowired  private AuditorService<SegUsuario> auditorAsiganadoSolicitudService;
	@Autowired  private AuditorService<CrtAuditorAsignado> crtAuditorAsignadoService;
	@Autowired  private InvitacionService<CrtInvitacion> invitacionService;
	@Autowired	private SolicitudService<CrtSolicitudcorr> solicitudCorrService;
	@Autowired  private AuditorService<CrtSolicitudcorr> auditorSolicitudService;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model) {
		return "reAsignaAuditor/reAsignarAuditorMain";
	}
	
	/**
	 * Metodo que llena los datos del folio seleccionado
	 * @author Enrique Duran Jimenez
	 * @since 05/06/2012
	 * @param CrtPromocion
	 * @return CrtPromocion
	 */
	@RequestMapping(value="/buscar", method=RequestMethod.POST )
	public @ResponseBody CrtPromocion buscar(@RequestBody CrtPromocion modelo, HttpServletRequest request){
		
		String tipo = modelo.getBandera();
		UserSession session = this.getUsuarioFirmado(request);
		modelo.setSdelegOrig(session.getIdSubDelegacion());
		if(modelo.getFolioTemp() != null){
			if(tipo.equals("promocion")){   // promocion
				modelo.setNuFoliopromocion(modelo.getFolioTemp());
				modelo = this.promocionService.consultaPorFolio(modelo);
				if(modelo != null){
					modelo.setFolioTemp(modelo.getNuFoliopromocion());
					if(modelo.getFecInicialDictamen() != null){
						SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
						modelo.setFechaInicio(formato.format(modelo.getFecFinalDictamen()));						
					}
					if(modelo.getFecFinalDictamen() != null){
						SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
						modelo.setFechaFin(formato.format(modelo.getFecFinalDictamen()));						
					}
					if(modelo.getFecFechaemisionpro() != null){
						SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
						modelo.setFechaAtencion(formato.format(modelo.getFecFechaemisionpro()));						
					}
					if(modelo.getCveFkPatron() != null){
						SatPatron patron = this.patronesService.getById(modelo.getCveFkPatron());
						if(patron != null){
							modelo.setRegPatron(patron.getRegistroPatronal());
							modelo.setRazonSocial(patron.getRazonSocial());
						}
					}
					if(modelo.getCveAuditorAsignado() != null){
//						SegUsuario usuario = new SegUsuario(); 
//						usuario.setCveIdUsuario(modelo.getCveAuditorAsignado());
						SsoUsuarios usuario = new SsoUsuarios(); 
						usuario.setDesUsrCurp(modelo.getCveAuditorAsignado());
						usuario =  this.auditorAsignadoUsuarioService.buscaPorCveUsuario(usuario);
						if(usuario != null){
							String nombre = usuario.getNomNombre() + " " + usuario.getNomPaterno() + " " + usuario.getNomMaterno();
							modelo.setAuditor(nombre);
						}
					}
				}else{
					return null;
				}
			}
			if(tipo.equals("invitacion")){   // invitacion
				CrtInvitacion invitacion = new CrtInvitacion();
				invitacion.setNuFolioInvitacion(modelo.getFolioTemp());
				invitacion.setCveFkSubdelegacion(BigDecimal.valueOf(modelo.getSdelegOrig()));
				invitacion = this.invitacionService.consultaPorFolio(invitacion);
				if(invitacion != null){
					modelo.setCveInvitacion(invitacion.getCveInvitacion().longValue());
					modelo.setCveAuditorAsignado(invitacion.getCveAuditorAsignado());
					modelo.setFolioTemp(invitacion.getNuFolioInvitacion());
					if(invitacion.getFecPeriodoIni() != null){
						SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
						modelo.setFechaInicio(formato.format(invitacion.getFecPeriodoIni()));						
					}
					if(invitacion.getFecPeriodoFin() != null){
						SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
						modelo.setFechaFin(formato.format(invitacion.getFecPeriodoFin()));						
					}
					if(invitacion.getFecFechaemision() != null){
						SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
						modelo.setFechaAtencion(formato.format(invitacion.getFecFechaemision()));						
					}
					if(invitacion.getCveFkPatron() != null){
						SatPatron patron = this.patronesService.getById(invitacion.getCveFkPatron());
						if(patron != null){
							modelo.setRegPatron(patron.getRegistroPatronal());
							modelo.setRazonSocial(patron.getRazonSocial());
						}
					}
					if(invitacion.getCveAuditorAsignado() != null){
//						SegUsuario usuario = new SegUsuario(); 
//						usuario.setCveIdUsuario(invitacion.getCveAuditorAsignado());
						SsoUsuarios usuario = new SsoUsuarios(); 
						usuario.setDesUsrCurp(modelo.getCveAuditorAsignado());
						usuario =  this.auditorAsignadoUsuarioService.buscaPorCveUsuario(usuario);
						if(usuario != null){
							String nombre = usuario.getNomNombre() + " " + usuario.getNomPaterno() + " " + usuario.getNomMaterno();
							modelo.setAuditor(nombre);
						}
					}
				}else{
					return null;
				}
			}
			if(tipo.equals("solicitud")){       // solicitud
				CrtSolicitudcorr solicitud = new CrtSolicitudcorr();
				solicitud.setNuFolio(modelo.getFolioTemp());
				solicitud.setCveSubdelegacion(modelo.getSdelegOrig());
				solicitud = this.solicitudCorrService.consultaPorFolioAuditorAsignado(solicitud);
				if(solicitud != null){
					modelo.setCveAuditorAsignado(solicitud.getCveAuditorAsignado());
					modelo.setCveSolicitudCorr(solicitud.getCveSolicitudCorr().longValue());
					modelo.setFolioTemp(solicitud.getNuFolio());
					if(solicitud.getFecFechaPeriodoIni() != null){
						SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
						modelo.setFechaInicio(formato.format(solicitud.getFecFechaPeriodoIni()));						
					}
					if(solicitud.getFecFechaPeriodoFin() != null){
						SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
						modelo.setFechaFin(formato.format(solicitud.getFecFechaPeriodoFin()));						
					}
					if(solicitud.getFecFechaElacoracionCorreccion() != null){
						SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
						modelo.setFechaAtencion(formato.format(solicitud.getFecFechaElacoracionCorreccion()));						
					}
					Integer idPatronAnexo = this.auditorSolicitudService.buscaPatronAnexo(solicitud.getCveSolicitudCorr());
					if(idPatronAnexo != null ){
						SatPatron patron = this.patronesService.getById(idPatronAnexo.longValue());
						if(patron != null){
							modelo.setRegPatron(patron.getRegistroPatronal());
							modelo.setRazonSocial(patron.getRazonSocial());
						}
					}
					if(solicitud.getCveAuditorAsignado() != null){
//						SegUsuario usuario = new SegUsuario(); 
//						usuario.setCveIdUsuario(solicitud.getCveAuditorAsignado());
						SsoUsuarios usuario = new SsoUsuarios(); 
						usuario.setDesUsrCurp(modelo.getCveAuditorAsignado());
						usuario =  this.auditorAsignadoUsuarioService.buscaPorCveUsuario(usuario);
						if(usuario != null){
							String nombre = usuario.getNomNombre() + " " + usuario.getNomPaterno() + " " + usuario.getNomMaterno();
							modelo.setAuditor(nombre);
						}
					}
				}else{
					return null;
				}
				
			}
		}
		
		return modelo;
	}
	
	/**
	 * Metodo que llena el grid de auditores
	 * @author Enrique Duran Jimenez
	 * @since 05/06/2012
	 */
	@RequestMapping(value="/llenaAuditores", method=RequestMethod.POST )
	public  @ResponseBody DatosSalidaPaginador<SegUsuario> llenaAuditores(@RequestBody CrtSegUsuarioWrapperDataTable aoData , HttpServletRequest request) {
		
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		UserSession session = this.getUsuarioFirmado(request);
		if(session != null && session.getIdSubDelegacion() != null){
//			aoData.getoForm().setSubDelegacion(session.getIdSubDelegacion());
		}
		send.parserArray(aoData.getAoData());	
		send.setModelo(aoData.getoForm());
		
		DatosSalidaPaginador<SegUsuario> reply  = new DatosSalidaPaginador<SegUsuario>();
		List<SegUsuario> lstSegUsuario = new ArrayList<SegUsuario>();
		
		if(aoData.getoForm() != null){
			reply = this.auditorAsiganadoSolicitudService.paginaReasignarAuditoresDisponibles(send);
			if(reply.getAaData() != null && reply.getAaData().size() > 0)
				lstSegUsuario.addAll(reply.getAaData());
			// recorremos la lista para asignar nombre
			if(lstSegUsuario != null && lstSegUsuario.size() > 0){
				for (Iterator iterator = lstSegUsuario.iterator(); iterator.hasNext();) {
					SegUsuario segUsuario = (SegUsuario) iterator.next();
					String nombre = "";
					if(segUsuario.getNomPaterno() != null){
						nombre = segUsuario.getNomPaterno() + " ";
					}
					if(segUsuario.getNomMaterno() != null){
						nombre = nombre + segUsuario.getNomMaterno() + " ";
					}
					if (segUsuario.getNomNombre() != null){
						nombre = nombre + segUsuario.getNomNombre();
					}
									
					segUsuario.setNombreCompleto(nombre);
					
				}
			}
			
		}else{
			reply.setAaData(lstSegUsuario);
			reply.setiTotalDisplayRecords(0);
			reply.setiTotalRecords(0);
		}
		
		System.out.println(".-.-controller realizo consulta) {");
		reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	
	/**
	 * Metodo que llena el grid de auditores
	 * @author Enrique Duran Jimenez
	 * @since 05/06/2012
	 */
	@RequestMapping(value="/muestraCarga", method=RequestMethod.POST )
	public  @ResponseBody DatosSalidaPaginador<CrtAuditorAsignado> muestraCarga(@RequestBody CrtAuditorAsignadoWrapperDataTable aoData , HttpServletRequest request) {
		
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		send.parserArray(aoData.getAoData());	
		send.setModelo(aoData.getoForm());
		
		DatosSalidaPaginador<CrtAuditorAsignado> reply  = new DatosSalidaPaginador<CrtAuditorAsignado>();
		List<CrtAuditorAsignado> lstCrtAuditorAsignado = new ArrayList<CrtAuditorAsignado>();
		
		if(aoData.getoForm().getCveAuditor() == null){
			aoData.setoForm(null);
		}
		if(aoData.getoForm() != null){
			reply = this.crtAuditorAsignadoService.paginaCarga(send);
			if(reply.getAaData() != null && reply.getAaData().size() > 0){
				lstCrtAuditorAsignado.addAll(reply.getAaData());
			}
			if(lstCrtAuditorAsignado != null && lstCrtAuditorAsignado.size() > 0){
				for (Iterator iterator = lstCrtAuditorAsignado.iterator(); iterator.hasNext();) {
					CrtAuditorAsignado crtAuditorAsignado = (CrtAuditorAsignado) iterator.next();
					if(crtAuditorAsignado.getCveInvitacion() != null){           // si contiene una invitacion
						CrtInvitacion invitacionTemp = new CrtInvitacion();
						invitacionTemp.setCveInvitacion(BigDecimal.valueOf(crtAuditorAsignado.getCveInvitacion()));
						invitacionTemp = this.invitacionService.consultaPorClave(invitacionTemp);
						if(invitacionTemp != null){
							crtAuditorAsignado.setFolio(invitacionTemp.getNuFolioInvitacion());	
							if(invitacionTemp.getSatPatron() != null){
								crtAuditorAsignado.setRegPatronal(invitacionTemp.getSatPatron().getRegistroPatronal());
								crtAuditorAsignado.setRazonSocial(invitacionTemp.getSatPatron().getRazonSocial());
							}
							if(invitacionTemp.getFecPeriodoIni() != null){
								SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
								crtAuditorAsignado.setFechaIni(formato.format(invitacionTemp.getFecPeriodoIni()));						
							}
							if(invitacionTemp.getFecPeriodoFin() != null){
								SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
								crtAuditorAsignado.setFechaFin(formato.format(invitacionTemp.getFecPeriodoFin()));						
							}
							if(crtAuditorAsignado.getFecFechaAsignacionIni() != null){
								SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
								crtAuditorAsignado.setFechaAsignacion(formato.format(crtAuditorAsignado.getFecFechaAsignacionIni()));						
							}
						}
					}
					if(crtAuditorAsignado.getCvePromocion() != null){   // si contiene una promocion
						CrtPromocion promocionTemp = new CrtPromocion();
						promocionTemp.setCvePromocion(crtAuditorAsignado.getCvePromocion());
						promocionTemp = this.promocionService.consultaPorClave(promocionTemp);
						if(promocionTemp != null){
							crtAuditorAsignado.setFolio(promocionTemp.getNuFoliopromocion());
							if(promocionTemp.getCveFkPatron() != null){
								SatPatron patron = this.patronesService.getById(promocionTemp.getCveFkPatron());
								if(patron != null){
									crtAuditorAsignado.setRegPatronal(patron.getRegistroPatronal());
									crtAuditorAsignado.setRazonSocial(patron.getRazonSocial());
								}
							}
							if(promocionTemp.getFecInicialDictamen() != null){
								SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
								crtAuditorAsignado.setFechaIni(formato.format(promocionTemp.getFecInicialDictamen()));						
							}
							if(promocionTemp.getFecFinalDictamen() != null){
								SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
								crtAuditorAsignado.setFechaFin(formato.format(promocionTemp.getFecFinalDictamen()));						
							}
							if(crtAuditorAsignado.getFecFechaAsignacionIni() != null){
								SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
								crtAuditorAsignado.setFechaAsignacion(formato.format(crtAuditorAsignado.getFecFechaAsignacionIni()));						
							}
						}
					}
					if(crtAuditorAsignado.getCveSolicitudCorr() != null){   // si contiene una Solicitud de correccion
						List<CrtSolicitudcorr> lstSolicitud = new ArrayList<CrtSolicitudcorr>();
						CrtSolicitudcorr solicitudTemp = new CrtSolicitudcorr();
						lstSolicitud = this.solicitudCorrService.consultarSolicitudesById(crtAuditorAsignado.getCveSolicitudCorr().intValue());
						if(lstSolicitud != null && lstSolicitud.size() > 0){
							solicitudTemp = lstSolicitud.get(0);
						}
						if(solicitudTemp != null){
							crtAuditorAsignado.setFolio(solicitudTemp.getNuFolio());
							Integer idPatronAnexo = this.auditorSolicitudService.buscaPatronAnexo(solicitudTemp.getCveSolicitudCorr());
							if(idPatronAnexo != null ){
								SatPatron patron = this.patronesService.getById(idPatronAnexo.longValue());
								if(patron != null){
									crtAuditorAsignado.setRegPatronal(patron.getRegistroPatronal());
									crtAuditorAsignado.setRazonSocial(patron.getRazonSocial());
								}
							}
							if(solicitudTemp.getFecFechaPeriodoIni() != null){
								SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
								crtAuditorAsignado.setFechaIni(formato.format(solicitudTemp.getFecFechaPeriodoIni()));						
							}
							if(solicitudTemp.getFecFechaPeriodoFin() != null){
								SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
								crtAuditorAsignado.setFechaFin(formato.format(solicitudTemp.getFecFechaPeriodoFin()));						
							}
							if(crtAuditorAsignado.getFecFechaAsignacionIni() != null){
								SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
								crtAuditorAsignado.setFechaAsignacion(formato.format(crtAuditorAsignado.getFecFechaAsignacionIni()));						
							}
						}
					}
					
				}
			}
			
		}else{
			reply.setAaData(lstCrtAuditorAsignado);
			reply.setiTotalDisplayRecords(0);
			reply.setiTotalRecords(0);
		}
		
		System.out.println(".-.-controller realizo consulta) {");
		reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	
//	/**
//	 * Metodo que llena el grid de auditores
//	 * @author Enrique Duran Jimenez
//	 * @since 05/06/2012
//	 */
//	@RequestMapping(value="/asignar", method=RequestMethod.POST )
//	public  @ResponseBody CrtAuditorAsignado asignar(@RequestBody CrtAuditorAsignado aoData , HttpServletRequest request) {
//	
//		UserSession session = this.getUsuarioFirmado(request);
//		aoData.setFecFechaAsignacionIni(new Date());
//		aoData.setCveUsuario(session.getCveIdUsuario());
//		aoData.setFecFechaReg(new Date());
//		aoData = this.crtAuditorAsignadoService.buscarAsignado(aoData);
//		
//		return aoData;
//	}
	
	/**
	 * Metodo que llena el grid de auditores
	 * @author Enrique Duran Jimenez
	 * @since 05/06/2012
	 */
	@RequestMapping(value="/asignar", method=RequestMethod.POST )
	public  @ResponseBody CrtAuditorAsignado asignar(@RequestBody CrtAuditorAsignado aoData , HttpServletRequest request) {
	
		UserSession session = this.getUsuarioFirmado(request);
		aoData.setFecFechaAsignacionIni(new Date());
		session.setCurpUsuario("VEHM790605MDFRRR05");
		aoData.setCveUsuario(session.getCurpUsuario());
		aoData.setFecFechaReg(new Date());
		aoData = this.crtAuditorAsignadoService.buscarAsignado(aoData);
		
		return aoData;
	}
	

}
