package mx.gob.imss.ctirss.correccion.web.controller.auditor;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.ctirss.correccion.administracion.auditor.AuditorService;
import mx.gob.imss.ctirss.correccion.administracion.auditor.paginador.CrtAuditorAsignadoWrapperDataTable;
import mx.gob.imss.ctirss.correccion.administracion.auditor.paginador.CrtSegUsuarioWrapperDataTable;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CgcCatcriterioseleccion;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTipoCorr;
import mx.gob.imss.ctirss.correccion.correccion.service.interfaces.SolicitudService;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.framework.utils.enums.CatEstatus;
import mx.gob.imss.ctirss.correccion.invitacion.service.interfaces.InvitacionService;
import mx.gob.imss.ctirss.correccion.login.model.SegUsuario;
import mx.gob.imss.ctirss.correccion.model.CrtAuditorAsignado;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacion;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.model.SsoUsuarios;
import mx.gob.imss.ctirss.correccion.promocion.base.paginador.model.CrtPromocionWrapperDataTable;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.promocion.service.interfaces.PromocionService;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
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
 *
 * @date 24/05/2012
 * @version 1.1.1
 * Controlador para el modulo Asignar Auditor
 */
@Controller
@RequestMapping(value="/administracion/asignaAuditor")
@JsonIgnoreProperties(ignoreUnknown=true)
public class AsignaAuditorController extends AbstractController{

	@Autowired	private IPatronesService patronesService;
	@Autowired	private ICatalogoService catalogoServiceBean;
	@Autowired	private ICatalogoService<CrcTipoCorr> catalogoCriteriosServiceBean;
	@Autowired  private AuditorService<CrtPromocion> auditorService;
	@Autowired  private AuditorService<CrtInvitacion> auditorInvitacionService;
	@Autowired  private AuditorService<CrtSolicitudcorr> auditorSolicitudService;
//	@Autowired  private AuditorService<SegUsuario> auditorAsiganadoSolicitudService;
	@Autowired  private AuditorService<SsoUsuarios> auditorAsiganadoSolicitudService;
	@Autowired  private PromocionService<CrtPromocion> promocionService;
	@Autowired  private AuditorService<CrtAuditorAsignado> crtAuditorAsignadoService;
	@Autowired  private InvitacionService<CrtInvitacion> invitacionService;
	@Autowired	private ICatalogoService<CrtSolicitudcorr> solicitudService;
	@Autowired	private SolicitudService<CrtSolicitudcorr> solicitudCorrService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model) {
		return "asignaAuditor/asignarAuditorMain";
	}
	
	/**
	 * @author Enrique Duran Jimenez
	 * @param aoData
	 * @return List<CrcTipoCorr>
	 * @since 24/05/2012
	 * Metodo para llenar el combo de Origen, regresa una lista de tipo CrcTipoCorr
	 */
	@RequestMapping(value="/cboOrigen", method=RequestMethod.POST )
    public @ResponseBody List<CrcTipoCorr> cboFuente(@RequestBody CgcCatcriterioseleccion aoData) {
		
		List<CrcTipoCorr> lstResult = this.catalogoCriteriosServiceBean.consultaSQL(" SELECT ct.CVE_TIPOCORR,ct.ID_TIPOCORR,ct.TX_DESCRIPCION FROM CRC_TIPOCORR ct WHERE ct.ID_TIPOCORR IN (1,2)  ORDER BY ct.TX_DESCRIPCION ");		
		
		return lstResult;
	}
	
	/**
	 * Metodo para obtener la fecha del sistema en los datepicker
	 * @author Enrique Duran Jimenez
	 * @since 25/05/2012
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/obtenerFechaServidor.do", method=RequestMethod.POST)
	public @ResponseBody String obtenerFechaServidor(HttpServletRequest request){
		return ConstantesBusiness.dateToStringFormat(new Date(), ConstantesBusiness.DD_MM_YYYY);
	}
	
	/**
	 * Metodo para obtener la fecha del sistema menos 45 dias en los datepicker
	 * @author Enrique Duran Jimenez
	 * @since 25/05/2012
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/obtenerFechaServidorMinima.do", method=RequestMethod.POST)
	public @ResponseBody String obtenerFechaServidorMinima(HttpServletRequest request){
		Calendar calendario = Calendar.getInstance();
		calendario.add(Calendar.DAY_OF_YEAR, -44);
		Date dia = calendario.getTime();
		return ConstantesBusiness.dateToStringFormat(dia, ConstantesBusiness.DD_MM_YYYY);
	}
	
	/**
	 * Metodo que pagina las promociones
	 * @author Enrique Duran Jimenez
	 * @since 25/05/2012
	 * @param aoData forma de la pantalla
	 * @param request
	 * @return DatosSalidaPaginador<CrtPromocion>
	 */
	@RequestMapping(value="/paginarPromocion", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CrtPromocion> paginarPromocion(@RequestBody CrtPromocionWrapperDataTable aoData , HttpServletRequest request) {
		
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		UserSession session = this.getUsuarioFirmado(request);
		if(session != null && session.getIdSubDelegacion() != null){
			aoData.getoForm().setSdelegOrig(Long.valueOf(session.getIdSubDelegacion()));
		}
		send.parserArray(aoData.getAoData());	
		send.setModelo(aoData.getoForm());
		
		DatosSalidaPaginador<CrtPromocion> reply  = new DatosSalidaPaginador<CrtPromocion>();
		List<CrtPromocion> lstCrtPromocion = new ArrayList<CrtPromocion>();
		
		if(aoData.getoForm() != null && (aoData.getoForm().getIdOrigen() != null || aoData.getoForm().getFolioTemp() != null)){
			reply = this.auditorService.paginarPromocion(send);
		
			if(reply.getAaData() != null && reply.getAaData().size() > 0)
				lstCrtPromocion.addAll(reply.getAaData());
			// recorremos la lista para asignar domicilio si traen cveDeteccion
			if(lstCrtPromocion != null && lstCrtPromocion.size() > 0){
				for (Iterator iterator = lstCrtPromocion.iterator(); iterator.hasNext();) {
					CrtPromocion crtPromocion = (CrtPromocion) iterator.next();
					if(crtPromocion.getCveFkPatron() != null){
						SatPatron patron = this.patronesService.getById(crtPromocion.getCveFkPatron());
						if(patron != null){
							crtPromocion.setRegPatron(patron.getRegistroPatronal());
							crtPromocion.setRazonSocial(patron.getRazonSocial());
						}
					}
					if(crtPromocion.getFecInicialDictamen() != null){
						SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
						crtPromocion.setFechaInicio(formato.format(crtPromocion.getFecInicialDictamen()));						
					}
					if(crtPromocion.getFecFinalDictamen() != null){
						SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
						crtPromocion.setFechaFin(formato.format(crtPromocion.getFecFinalDictamen()));						
					}
					if(crtPromocion.getFecFechaoficiopro() != null){
						SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
						crtPromocion.setFechaAtencion(formato.format(crtPromocion.getFecFechaoficiopro()));						
					}
				}
			}else{
				reply.setAaData(lstCrtPromocion);
				reply.setiTotalDisplayRecords(0);
				reply.setiTotalRecords(0);
			}
		}else{
			reply.setAaData(lstCrtPromocion);
			reply.setiTotalDisplayRecords(0);
			reply.setiTotalRecords(0);
		}
		
		System.out.println(".-.-controller realizo consulta) {");
		reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	
	/**
	 * Metodo que pagina las INVITACIONES
	 * @author Enrique Duran Jimenez
	 * @since 25/05/2012
	 * @param aoData forma de la pantalla
	 * @param request
	 * @return DatosSalidaPaginador<CrtInvitacion>
	 */
	@RequestMapping(value="/paginarInvitacion", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CrtInvitacion> paginarInvitacion(@RequestBody CrtPromocionWrapperDataTable aoData , HttpServletRequest request) {
		
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		UserSession session = this.getUsuarioFirmado(request);
		if(session != null && session.getIdSubDelegacion() != null){
			aoData.getoForm().setSdelegOrig(Long.valueOf(session.getIdSubDelegacion()));
		}
		send.parserArray(aoData.getAoData());	
		send.setModelo(aoData.getoForm());
		
		DatosSalidaPaginador<CrtInvitacion> reply  = new DatosSalidaPaginador<CrtInvitacion>();
		List<CrtInvitacion> lstCrtInvitacion = new ArrayList<CrtInvitacion>();
		
		if(aoData.getoForm() != null){
			reply = this.auditorInvitacionService.paginarInvitacion(send);
		
			if(reply.getAaData() != null && reply.getAaData().size() > 0)
				lstCrtInvitacion.addAll(reply.getAaData());
			// recorremos la lista para asignar domicilio si traen cveDeteccion
			if(lstCrtInvitacion != null && lstCrtInvitacion.size() > 0){
				for (Iterator iterator = lstCrtInvitacion.iterator(); iterator.hasNext();) {
					CrtInvitacion crtInvitacion = (CrtInvitacion) iterator.next();
					if(crtInvitacion.getSatPatron() != null && crtInvitacion.getSatPatron().getCvePK() != null){
						SatPatron patron = this.patronesService.getById(crtInvitacion.getSatPatron().getCvePK());
						if(patron != null){
							crtInvitacion.setRegPatronal(patron.getRegistroPatronal());
							crtInvitacion.setRazonSocial(patron.getRazonSocial());
						}
					}
					if(crtInvitacion.getFecPeriodoIni() != null){
						SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
						crtInvitacion.setFechaIncial(formato.format(crtInvitacion.getFecPeriodoIni()));						
					}
					if(crtInvitacion.getFecPeriodoFin() != null){
						SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
						crtInvitacion.setFechaFinal(formato.format(crtInvitacion.getFecPeriodoFin()));						
					}
					if(crtInvitacion.getFecFechaemision() != null){
						SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
						crtInvitacion.setFechaEmision(formato.format(crtInvitacion.getFecFechaemision()));						
					}
				}
			}else{
				reply.setAaData(lstCrtInvitacion);
				reply.setiTotalDisplayRecords(0);
				reply.setiTotalRecords(0);
			}
		}else{
			reply.setAaData(lstCrtInvitacion);
			reply.setiTotalDisplayRecords(0);
			reply.setiTotalRecords(0);
		}
		
		System.out.println(".-.-controller realizo consulta) {");
		reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	
	/**
	 * Metodo que pagina las solicitudes de correccion
	 * @author Enrique Duran Jimenez
	 * @since 25/05/2012
	 * @param aoData forma de la pantalla
	 * @param request
	 * @return DatosSalidaPaginador<CrtSolicitudcorr>
	 */
	@RequestMapping(value="/paginarSolicitud", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CrtSolicitudcorr> paginarSolicitud(@RequestBody CrtPromocionWrapperDataTable aoData , HttpServletRequest request) {
		
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		UserSession session = this.getUsuarioFirmado(request);
		if(session != null && session.getIdSubDelegacion() != null){
			aoData.getoForm().setSdelegOrig(Long.valueOf(session.getIdSubDelegacion()));
		}
		send.parserArray(aoData.getAoData());	
		send.setModelo(aoData.getoForm());
		
		DatosSalidaPaginador<CrtSolicitudcorr> reply  = new DatosSalidaPaginador<CrtSolicitudcorr>();
		List<CrtSolicitudcorr> lstCrtSolicitudcorr = new ArrayList<CrtSolicitudcorr>();
		
		if(aoData.getoForm() != null){
			reply = this.auditorSolicitudService.paginarSolicitud(send);
		
			if(reply.getAaData() != null && reply.getAaData().size() > 0)
				lstCrtSolicitudcorr.addAll(reply.getAaData());
			// recorremos la lista para asignar domicilio si traen cveDeteccion
			if(lstCrtSolicitudcorr != null && lstCrtSolicitudcorr.size() > 0){
				for (Iterator iterator = lstCrtSolicitudcorr.iterator(); iterator.hasNext();) {
					CrtSolicitudcorr crtSolicitudcorr = (CrtSolicitudcorr) iterator.next();
					Integer idPatronAnexo = this.auditorSolicitudService.buscaPatronAnexo(crtSolicitudcorr.getCveSolicitudCorr());
					if(idPatronAnexo != null ){
						SatPatron patron = this.patronesService.getById(idPatronAnexo.longValue());
						if(patron != null){
							crtSolicitudcorr.setPatron(patron.getRegistroPatronal());
							crtSolicitudcorr.setRazonSocialPatronCorregir(patron.getRazonSocial());
						}
					}
					if(crtSolicitudcorr.getFecFechaPeriodoIni() != null){
						SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
						crtSolicitudcorr.setFechaInicial(formato.format(crtSolicitudcorr.getFecFechaPeriodoIni()));						
					}
					if(crtSolicitudcorr.getFecFechaPeriodoFin() != null){
						SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
						crtSolicitudcorr.setFechaFinal(formato.format(crtSolicitudcorr.getFecFechaPeriodoFin()));						
					}
					if(crtSolicitudcorr.getFecFechaElacoracionCorreccion() != null){
						SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
						crtSolicitudcorr.setFechaPresentacion(formato.format(crtSolicitudcorr.getFecFechaElacoracionCorreccion()));						
					}
				}
			}else{
				reply.setAaData(lstCrtSolicitudcorr);
				reply.setiTotalDisplayRecords(0);
				reply.setiTotalRecords(0);
			}
		}else{
			reply.setAaData(lstCrtSolicitudcorr);
			reply.setiTotalDisplayRecords(0);
			reply.setiTotalRecords(0);
		}
		
		System.out.println(".-.-controller realizo consulta) {");
		reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	
	/**
	 * Metodo que llena los datos del folio seleccionado
	 * @author Enrique Duran Jimenez
	 * @since 28/05/2012
	 * @param CrtPromocion
	 * @return CrtPromocion
	 */
	@RequestMapping(value="/llenaDatosFolio", method=RequestMethod.POST )
	public @ResponseBody CrtPromocion llenaDatosFolio(@RequestBody CrtPromocion modelo) {
		
		CrtPromocion promocion = new CrtPromocion();
		
		if(modelo.getBandera() != null || !modelo.getBandera().equals("")){
			if(modelo.getBandera().equals("1") || modelo.getBandera().equals("2") || modelo.getBandera().equals("12")){  // Solicitud de correccion
				
				List<CrtSolicitudcorr> listSolicitud = new ArrayList<CrtSolicitudcorr>();
				CrtSolicitudcorr solicitud = new CrtSolicitudcorr();
				solicitud.setCveSolicitudCorr(Integer.valueOf(modelo.getCveTemp()));
				listSolicitud = this.solicitudCorrService.consultarSolicitudesById(solicitud.getCveSolicitudCorr());
				if(listSolicitud != null && listSolicitud.size() > 0){
					solicitud = listSolicitud.get(0);
				}
				if(solicitud != null){
					promocion.setCveSolicitudCorr(solicitud.getCveSolicitudCorr().longValue());
					promocion.setFolioTemp(solicitud.getNuFolio());
					if(solicitud.getFecFechaPeriodoIni() != null){
						SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
						promocion.setFechaInicio(formato.format(solicitud.getFecFechaPeriodoIni()));						
					}
					if(solicitud.getFecFechaPeriodoFin() != null){
						SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
						promocion.setFechaFin(formato.format(solicitud.getFecFechaPeriodoFin()));						
					}
					if(solicitud.getFecFechaElacoracionCorreccion() != null){
						SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
						promocion.setFechaAtencion(formato.format(solicitud.getFecFechaElacoracionCorreccion()));						
					}
					Integer idPatronAnexo = this.auditorSolicitudService.buscaPatronAnexo(solicitud.getCveSolicitudCorr());
					if(idPatronAnexo != null ){
						SatPatron patron = this.patronesService.getById(idPatronAnexo.longValue());
						if(patron != null){
							promocion.setRegPatron(patron.getRegistroPatronal());
							promocion.setRazonSocial(patron.getRazonSocial());
						}
					}
				}
				
			}
			if(modelo.getBandera().equals("3") || modelo.getBandera().equals("4") || modelo.getBandera().equals("5") ||
			   modelo.getBandera().equals("6") || modelo.getBandera().equals("7")){  // Promocion
				
				promocion.setCvePromocion(Long.valueOf(modelo.getCveTemp()));
				promocion =	this.promocionService.consultaPorClave(promocion);
				promocion.setFolioTemp(promocion.getNuFoliopromocion());
				if(promocion.getFecInicialDictamen() != null){
					SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
					promocion.setFechaInicio(formato.format(promocion.getFecInicialDictamen()));						
				}
				if(promocion.getFecFinalDictamen() != null){
					SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
					promocion.setFechaFin(formato.format(promocion.getFecFinalDictamen()));						
				}
				if(promocion.getFecFechaoficiopro() != null){
					SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
					promocion.setFechaAtencion(formato.format(promocion.getFecFechaoficiopro()));						
				}
				if(promocion.getCveFkPatron() != null){
					SatPatron patron = this.patronesService.getById(promocion.getCveFkPatron());
					if(patron != null){
						promocion.setRegPatron(patron.getRegistroPatronal());
						promocion.setRazonSocial(patron.getRazonSocial());
					}
				}
				
			}
			if(modelo.getBandera().equals("10") || modelo.getBandera().equals("11")){  // Invitacion
				CrtInvitacion invitacion = new CrtInvitacion();
				invitacion.setCveInvitacion(BigDecimal.valueOf(Long.valueOf(modelo.getCveTemp())));
				invitacion = (CrtInvitacion) this.invitacionService.consultaPorClave(invitacion);
				if(invitacion != null){
					promocion.setCveInvitacion(invitacion.getCveInvitacion().longValue());
					promocion.setFolioTemp(invitacion.getNuFolioInvitacion());
					if(invitacion.getFecPeriodoIni() != null){
						SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
						promocion.setFechaInicio(formato.format(invitacion.getFecPeriodoIni()));						
					}
					if(invitacion.getFecPeriodoFin() != null){
						SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
						promocion.setFechaFin(formato.format(invitacion.getFecPeriodoFin()));						
					}
					if(invitacion.getFecFechaemision() != null){
						SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
						promocion.setFechaAtencion(formato.format(invitacion.getFecFechaemision()));						
					}
					if(invitacion.getSatPatron() != null){						
							promocion.setRegPatron(invitacion.getSatPatron().getRegistroPatronal());
							promocion.setRazonSocial(invitacion.getSatPatron().getRazonSocial());						
					}
				}
				
			}
		}
		
		return promocion;	
	}
	
	
//	/**
//	 * Metodo que llena el grid de auditores
//	 * @author Enrique Duran Jimenez
//	 * @since 29/05/2012
//	 */
//	@RequestMapping(value="/llenaAuditores", method=RequestMethod.POST )
//	public  @ResponseBody DatosSalidaPaginador<SegUsuario> llenaAuditores(@RequestBody CrtSegUsuarioWrapperDataTable aoData , HttpServletRequest request) {
//		
//		DatosEntradaPaginador send = new DatosEntradaPaginador();
//		UserSession session = this.getUsuarioFirmado(request);
//		if(session != null && session.getIdSubDelegacion() != null){
//			aoData.setoForm(new SegUsuario());
//			aoData.getoForm().setSubDelegacion(session.getIdSubDelegacion());
//		}
//		send.parserArray(aoData.getAoData());	
//		send.setModelo(aoData.getoForm());
//		
//		DatosSalidaPaginador<SegUsuario> reply  = new DatosSalidaPaginador<SegUsuario>();
//		List<SegUsuario> lstSegUsuario = new ArrayList<SegUsuario>();
//		
//		if(aoData.getoForm() != null){
//			reply = this.auditorAsiganadoSolicitudService.paginaAuditoresDisponibles(send);
//			if(reply.getAaData() != null && reply.getAaData().size() > 0)
//				lstSegUsuario.addAll(reply.getAaData());
//			// recorremos la lista para asignar nombre
//			
//		}else{
//			reply.setAaData(lstSegUsuario);
//			reply.setiTotalDisplayRecords(0);
//			reply.setiTotalRecords(0);
//		}
//		
//		System.out.println(".-.-controller realizo consulta) {");
//		reply.setsEcho(send.getsEcho());
//        
//        return reply;
//    }
	
	/**
	 * Metodo que llena el grid de auditores
	 * @author Enrique Duran Jimenez
	 * @since 29/05/2012
	 */
	@RequestMapping(value="/llenaAuditores", method=RequestMethod.POST )
	public  @ResponseBody DatosSalidaPaginador<SsoUsuarios> llenaAuditores(@RequestBody CrtSegUsuarioWrapperDataTable aoData , HttpServletRequest request) {
		
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		UserSession session = this.getUsuarioFirmado(request);
		if(session != null && session.getIdSubDelegacion() != null){
			aoData.setoForm(new SsoUsuarios());
			aoData.getoForm().setCveIdDelegacion(session.getIdDelegacion());
			aoData.getoForm().setCveIdSubDelegacion(session.getIdSubDelegacion());
		}
		send.parserArray(aoData.getAoData());	
		send.setModelo(aoData.getoForm());
		
		DatosSalidaPaginador<SsoUsuarios> reply  = new DatosSalidaPaginador<SsoUsuarios>();
		List<SsoUsuarios> listSsoUsuarios = new ArrayList<SsoUsuarios>();
		
		if(aoData.getoForm() != null){
			reply = this.auditorAsiganadoSolicitudService.paginaAuditoresDisponibles(send);
			if(reply.getAaData() != null && reply.getAaData().size() > 0)
				listSsoUsuarios.addAll(reply.getAaData());
			// recorremos la lista para asignar nombre
			
		}else{
			reply.setAaData(listSsoUsuarios);
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
	 * @since 29/05/2012
	 */
	@RequestMapping(value="/muestraCarga", method=RequestMethod.POST )
	public  @ResponseBody DatosSalidaPaginador<CrtAuditorAsignado> muestraCarga(@RequestBody CrtAuditorAsignadoWrapperDataTable aoData , HttpServletRequest request) {
		
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		send.parserArray(aoData.getAoData());	
		send.setModelo(aoData.getoForm());
		
		DatosSalidaPaginador<CrtAuditorAsignado> reply  = new DatosSalidaPaginador<CrtAuditorAsignado>();
		List<CrtAuditorAsignado> lstCrtAuditorAsignado = new ArrayList<CrtAuditorAsignado>();
		List<CrtAuditorAsignado> lstCrtAuditorAsignadoEliminar = new ArrayList<CrtAuditorAsignado>();
//		if(aoData.getoForm().getCveAuditor() == null){
		if(aoData.getoForm().getCveAuditorUsuarioAsignado() == null){
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
							//Exclusion de estatus de correccion
							
							
							if(solicitudTemp.getCveStatus().intValue()== CatEstatus.CORRECCION_DERIVADA_OTRA_SUBDELEGACION.getId().intValue() ||
							   solicitudTemp.getCveStatus().intValue()== CatEstatus.FOLIO_CANCELADO.getId().intValue() ||
							   solicitudTemp.getCveStatus().intValue()== CatEstatus.CORRECCION_DERIVADA_FISCALIZACION.getId().intValue() ||
							   solicitudTemp.getCveStatus().intValue()== CatEstatus.CORRECCION_DERIVADA_DICTAMEN.getId().intValue() ||
							   solicitudTemp.getCveStatus().intValue()== CatEstatus.SOLICITUD_RECHAZADA.getId().intValue()
									){
								//lstCrtAuditorAsignado.remove(crtAuditorAsignado);
								lstCrtAuditorAsignadoEliminar.add(crtAuditorAsignado);
							}
							
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
			
			
			System.out.println("Total correcciones eliminar "+lstCrtAuditorAsignadoEliminar.size());
			for(CrtAuditorAsignado audi:lstCrtAuditorAsignadoEliminar){
				lstCrtAuditorAsignado.remove(audi);
				System.out.println("Remove");
			}
			reply.setAaData(lstCrtAuditorAsignado);
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
//	 * @since 29/05/2012
//	 */
//	@RequestMapping(value="/asignar", method=RequestMethod.POST )
//	public  @ResponseBody CrtAuditorAsignado asignar(@RequestBody CrtAuditorAsignado aoData , HttpServletRequest request) {
//	
//		UserSession session = this.getUsuarioFirmado(request);
//		aoData.setFecFechaAsignacionIni(new Date());
//		aoData.setCveUsuario(session.getCveIdUsuario());
//		aoData.setFecFechaReg(new Date());
//		aoData = this.crtAuditorAsignadoService.agregar(aoData);
//		
//		return aoData;
//	}
	
	/**
	 * Metodo que llena el grid de auditores
	 * @author Enrique Duran Jimenez
	 * @since 29/05/2012
	 */
	@RequestMapping(value="/asignar", method=RequestMethod.POST )
	public  @ResponseBody CrtAuditorAsignado asignar(@RequestBody CrtAuditorAsignado aoData , HttpServletRequest request) {
	
		UserSession session = this.getUsuarioFirmado(request);
		aoData.setFecFechaAsignacionIni(new Date());
//		session.setCurpUsuario("VEHM790605MDFRRR05");
		aoData.setCveUsuario(session.getCurpUsuario());
		aoData.setFecFechaReg(new Date());
		aoData = this.crtAuditorAsignadoService.agregar(aoData);
		
		return aoData;
	}
	
}
