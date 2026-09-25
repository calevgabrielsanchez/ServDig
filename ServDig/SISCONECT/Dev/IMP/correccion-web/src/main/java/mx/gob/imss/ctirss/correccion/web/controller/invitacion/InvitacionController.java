package mx.gob.imss.ctirss.correccion.web.controller.invitacion;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CgcCatcriterioseleccion;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTipoCorr;
import mx.gob.imss.ctirss.correccion.deteccion.base.paginador.model.CrtDeteccionWrapperDataTable;
import mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion;
import mx.gob.imss.ctirss.correccion.deteccion.service.interfaces.DeteccionService;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.framework.utils.enums.CatEstatus;
import mx.gob.imss.ctirss.correccion.invitacion.service.interfaces.InvitacionService;
import mx.gob.imss.ctirss.correccion.model.CgcCatTipoObra;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacion;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.promocion.base.paginador.model.CrtPromocionWrapperDataTable;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.promocion.service.interfaces.PromocionService;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.service.interfaces.IPatronesService;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;
import mx.gob.imss.ctirss.domiciliosInegi.service.interfaces.DomiciliosInegiService;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author Enrique Duran Jimenez
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 05/12/2011
 */
@Controller
@RequestMapping(value="/catalogo/invitacion")
@JsonIgnoreProperties(ignoreUnknown=true)
public class InvitacionController extends AbstractController{	
	
	@Autowired
	private InvitacionService<CrtDeteccion> invitacionService;
	@Autowired
	private InvitacionService<CrtPromocion> invitacionServiceP;
	@Autowired
	private DeteccionService<CrtDeteccion> deteccionService;
	@Autowired
	private DomiciliosInegiService<DgDomicilioGeografico> domiciliosInegiServiceBean;
	@Autowired
	private PromocionService<CrtPromocion> promocionService;
	@Autowired
	private IPatronesService<?> patronesService;
	
	@Autowired
	private ICatalogoService<CrcTipoCorr> catalogoCriteriosServiceBean;
	@Autowired
	private ICatalogoService<CgcCatTipoObra> catTipoObraService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model) {
		model.addAttribute("crtInvitacion",new CrtInvitacion());
		model.addAttribute(new CrtDeteccion());
		 return "invitacion/solicitud/invitacionMain";
	}
	
	@RequestMapping(value="/paginar", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CrtDeteccion> pagina(@RequestBody CrtDeteccionWrapperDataTable aoData , HttpServletRequest request) {
		System.out.println(".-.-controller public @ResponseBody DatosSalidaPaginador<CrtDeteccion> pagina(@RequestBody CrtDeteccionWrapperDataTable aoData ) {");
		DatosEntradaPaginador<CrtDeteccion> send = new DatosEntradaPaginador<CrtDeteccion>();
		UserSession session = this.getUsuarioFirmado(request);
		if(session != null && session.getIdSubDelegacion() != null){
			aoData.getoForm().setSdelegOrig(BigDecimal.valueOf(session.getIdSubDelegacion()));
		}
		send.parserArray(aoData.getAoData());	
		send.setModelo(aoData.getoForm());
		
		DatosSalidaPaginador<CrtDeteccion> reply = new DatosSalidaPaginador<CrtDeteccion>();
		List<CrtDeteccion> lstCrtDeteccion = new ArrayList<CrtDeteccion>();
		
		if(aoData.getoForm() != null){
			reply = this.invitacionService.pagina(send);
		
			lstCrtDeteccion.addAll(reply.getAaData());
			// recorremos la lista para asignar domicilio si traen cveDeteccion
			if(lstCrtDeteccion != null && lstCrtDeteccion.size() > 0){
				for (Iterator<?> iterator = lstCrtDeteccion.iterator(); iterator.hasNext();) {
					CrtDeteccion crtDeteccion = (CrtDeteccion) iterator.next();
					
					if(crtDeteccion.getDomicilioId() != null){
						
						DgDomicilioGeografico domicilio = new DgDomicilioGeografico();
						domicilio.setDomicilioId(crtDeteccion.getDomicilioId());
						domicilio = domiciliosInegiServiceBean.consultaPorClave(domicilio);
						if(domicilio != null && domicilio.getDescripc() != null){
							crtDeteccion.setDomCalle(domicilio.getDescripc());
						}
					}
							
					
					if(crtDeteccion.getFecFechadeteccionFc() != null){
						SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
						crtDeteccion.setFechaAtencion(formato.format(crtDeteccion.getFecFechadeteccionFc()));
					}
					
					crtDeteccion.setClaseObra(crtDeteccion.getTipClaseobra());
					if(crtDeteccion.getCvePkTipObra() != null){
						CgcCatTipoObra tipoObr = new CgcCatTipoObra();
						tipoObr.getId().setIdTipoobra(crtDeteccion.getCvePkTipObra());
						tipoObr = this.catTipoObraService.consultaPorClave(tipoObr);
						if(tipoObr != null){
							crtDeteccion.setTipoObra(tipoObr.getTipoobra());
						}								
					}
					crtDeteccion.setFaseObra(crtDeteccion.getDesFaseCostruccion());
					
					if(crtDeteccion.getCveFkPatron() != null){
						SatPatron patron = this.patronesService.getById(crtDeteccion.getCveFkPatron());
						if(patron != null){
							crtDeteccion.setRegPatron(patron.getRegistroPatronal());
							crtDeteccion.setNomRazonsocial(patron.getRazonSocial());
						}
					}
					
				}
			}
		}else{
			reply.setAaData(lstCrtDeteccion);
			reply.setiTotalDisplayRecords(0);
			reply.setiTotalRecords(0);
		}
		System.out.println(".-.-controller realizo consulta) {");
		reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	
	@RequestMapping(value="/paginarPromocion", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CrtPromocion> pagina(@RequestBody CrtPromocionWrapperDataTable aoData , HttpServletRequest request) {
		
		DatosEntradaPaginador<CrtPromocion> send = new DatosEntradaPaginador<CrtPromocion>();
		UserSession session = this.getUsuarioFirmado(request);
		if(session != null && session.getIdSubDelegacion() != null){
			aoData.getoForm().setSdelegOrig(Long.valueOf(session.getIdSubDelegacion()));
		}
		send.parserArray(aoData.getAoData());	
		send.setModelo(aoData.getoForm());
		
		DatosSalidaPaginador<CrtPromocion> reply  = new DatosSalidaPaginador<CrtPromocion>();
		List<CrtPromocion> lstCrtPromocion = new ArrayList<CrtPromocion>();
		
		if(aoData.getoForm() != null){
			reply = this.invitacionServiceP.pagina(send);
		
			lstCrtPromocion.addAll(reply.getAaData());
			// recorremos la lista para asignar domicilio si traen cveDeteccion
			if(lstCrtPromocion != null && lstCrtPromocion.size() > 0){
				
				for (Iterator<?> iterator = lstCrtPromocion.iterator(); iterator.hasNext();) {
					CrtPromocion crtPromocion = (CrtPromocion) iterator.next();
					if(crtPromocion.getCveDeteccion() != null){
						
						CrtDeteccion det = new CrtDeteccion();
						det.setCveDeteccion(crtPromocion.getCveDeteccion().longValue());
						det = this.deteccionService.consultaPorClave(det);
						if(det != null && det.getDomicilioId() != null){
							DgDomicilioGeografico domicilio = new DgDomicilioGeografico();
							domicilio.setDomicilioId(det.getDomicilioId());
							domicilio = domiciliosInegiServiceBean.consultaPorClave(domicilio);
							if(domicilio != null && domicilio.getDescripc() != null){
								crtPromocion.setDomicilio(domicilio.getDescripc());
							}
							
						}
						
					}
					if(crtPromocion.getFecFechaemisionpro() != null){
					SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
					crtPromocion.setFechaAtencion(formato.format(crtPromocion.getFecFechaemisionpro()));
					}
					if(crtPromocion.getFecFechanotif() != null){
						SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
						crtPromocion.setFechaNotificacion(formato.format(crtPromocion.getFecFechanotif()));
						}
					
					
				}
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
	
	@RequestMapping(value="/guardar" , method=RequestMethod.POST)
	public @ResponseBody CrtInvitacion guardar(@RequestBody CrtInvitacion invitacion, HttpServletRequest request) {

		UserSession session = this.getUsuarioFirmado(request);
		CrtPromocion promocion = new CrtPromocion();
		if(invitacion.getCveFkPatron() == null){
			if(invitacion.getCvePromocion() != null){
				promocion.setCvePromocion(invitacion.getCvePromocion().longValue());
				promocion = this.promocionService.consultaPorClave(promocion);
			}
			if(promocion != null && promocion.getCveFkPatron() != null){
				invitacion.setCveFkPatron(promocion.getCveFkPatron());
			}
		}
		
		if(invitacion.getCveFkPatron() != null){
			SatPatron patron = this.patronesService.getById(invitacion.getCveFkPatron());
			if(patron != null){
				invitacion.setSatPatron(patron);
			}
		}
		invitacion.setFecFechaoficioinv(new Date());
		invitacion.setFecFechareg(new Date());
		invitacion.setCveUsuario(session.getCveIdUsuario() != null ? session.getCveIdUsuario().toString() : "");
		
		invitacion.setCveFkSubdelegacion(session.getIdSubDelegacion() != null ? BigDecimal.valueOf(session.getIdSubDelegacion()) : null);
		invitacion.setFecFechaemision(Functions.stringToDate(invitacion.getFechaEmision()));		
		
		invitacion.setUsuarioFirmado(session);
		invitacion.setCveEstatus(CatEstatus.EN_PROCESO_NOTIFICACION_OFICIO_PROMOCION.getId());
		invitacion.setCveAuditorAsignado("");
		invitacion.setFecPeriodoIni(Functions.stringToDate(invitacion.getFechaIncial()));
		invitacion.setFecPeriodoFin(Functions.stringToDate(invitacion.getFechaFinal()));
		invitacion = this.invitacionService.guardar(invitacion); 
		
		invitacion.setFechaOfInvitacionTx(Functions.dateToString3(invitacion.getFecFechaoficioinv()));
		return invitacion;
	}
	

	@RequestMapping(value="/buscaFolioDeteccion" , method=RequestMethod.POST)
	public @ResponseBody CrtDeteccion buscaFolioDet(@RequestBody String parametro) {
		
		CrtDeteccion model = new CrtDeteccion();
		model.setCveDeteccion(Long.valueOf(parametro));
		return deteccionService.consultaPorClave(model);
	}
	
	@RequestMapping(value="/buscaFolioPromocion" , method=RequestMethod.POST)
	public @ResponseBody CrtPromocion buscaFolioProm(@RequestBody String parametro) {
		
		CrtPromocion model = new CrtPromocion();
		model.setCvePromocion(Long.valueOf(parametro));
		model = promocionService.consultaPorClave(model);
		return model;
	}
	
	@RequestMapping(value="/validaPatron" , method=RequestMethod.POST)
	public @ResponseBody SatPatron validaPatron(@RequestBody String parametro) {
		String valor = parametro.substring(1, parametro.length() -1);
		SatPatron model = new SatPatron();
		model =  patronesService.validaRegistroPatronalWS(valor, true);
		
		return model;
	}
	
	
	@RequestMapping(value="/cboFuente", method=RequestMethod.POST )
    public @ResponseBody List<CrcTipoCorr> cboFuente(@RequestBody CgcCatcriterioseleccion aoData) {
		
		
		List<CrcTipoCorr> lstResult = this.catalogoCriteriosServiceBean.consultaSQL(" select t.CVE_TIPOCORR,t.ID_TIPOCORR,t.TX_DESCRIPCION from CRC_TIPOCORR t where t.ID_TIPOCORR in (2,3) and CVE_TIPOCORR != 3 order by t.TX_DESCRIPCION ");
				
		
		return lstResult;
	}
	
	
	@RequestMapping(value="/invitacionAntecedente", method=RequestMethod.POST )
    public @ResponseBody CrtInvitacion invitacionAntecedente(@RequestBody CrtInvitacion invitacion) {
		
		CrtPromocion promocion = new CrtPromocion();
		CrtDeteccion deteccion = new CrtDeteccion();
		
		if(invitacion.getTipoPrograma() != null && invitacion.getTipoPrograma().equals("promocion")){
			promocion.setCvePromocion(Long.valueOf(invitacion.getCveTemp()));
			promocion = this.promocionService.consultaPorClave(promocion);
			if(promocion != null){
				invitacion.setFecFechanotifi(promocion.getFecFechanotif());
				invitacion.setFolioAntecedente(promocion.getNuFoliopromocion());
				invitacion.setTipoPrograma(promocion.getCveTipocorr().toString());
				invitacion.setCvePromocion(BigDecimal.valueOf(promocion.getCvePromocion()));
				if(promocion.getCveFkPatron() != null){
					SatPatron patron = this.patronesService.getById(promocion.getCveFkPatron()); 
					if(patron != null){
						invitacion.setCveFkPatron(patron.getCvePK());
						invitacion.setRegPatronal(patron.getRegistroPatronal());
						invitacion.setRazonSocial(patron.getRazonSocial());
						invitacion.setCvePromocion(BigDecimal.valueOf(promocion.getCvePromocion()));
					}
				}
				if(promocion.getCveTipocorr() == 6L || promocion.getCveTipocorr() == 7L){  // EXO y SBC
					Calendar cal = Calendar.getInstance();
					int year = cal.get(Calendar.YEAR) - 2;
					int mes  = cal.get(Calendar.MONTH);
					String mesF = "";
					if (mes < 10){
						mesF = "0" + mes;
					}else{
						mesF = String.valueOf(mes);
					}
					String fechaIni = "01/" + mesF + "/" + year;
					invitacion.setFechaIncial(fechaIni);
					SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
					invitacion.setFechaFinal(formato.format(new Date()));
				}else if(promocion.getCveTipocorr() == 4L){                                 // SATICB
					if(promocion.getCveNroregobraSatic() != null){
						CrtDeteccion detSatic = this.invitacionService.obtieneSATIC(promocion.getCveNroregobraSatic().toString());
						if(detSatic!=null){
							invitacion.setFechaIncial(Functions.dateToString3(Functions.FormateaFecha(detSatic.getFechaIncial(),"dd-MM-yyyy")));
							invitacion.setFechaFinal(Functions.dateToString3(Functions.FormateaFecha(detSatic.getFechaFinal(),"dd-MM-yyyy")));	
						}
					}
				}else if(promocion.getCveTipocorr() == 5L){                                 // EX
					if(promocion.getCveDeteccion() != null){
						CrtDeteccion det = new CrtDeteccion(); 
						det.setCveDeteccion(promocion.getCveDeteccion().longValue());		
						det = this.deteccionService.consultaPorClave(det);
						if (det != null){
							if(det.getFecFechainicioEst() != null){
								invitacion.setFechaIncial(Functions.dateToString3(det.getFecFechainicioEst()));
								if(det.getFecFechaterminoEst() != null && det.getFecFechaterminoEst().after(new Date()) ){
									invitacion.setFechaFinal(Functions.dateToString3(new Date()));
								}else if (det.getFecFechaterminoEst() == null){
									invitacion.setFechaFinal(Functions.dateToString3(new Date()));
								}else{
									invitacion.setFechaFinal(Functions.dateToString3(det.getFecFechaterminoEst()));
								}
							}
						}
					}
				}
				if(promocion.getFecFechanotif() != null){
					SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
					invitacion.setFechaOfInvitacionTx(formato.format(new Date()));
				}
				
			}
		}else if(invitacion.getTipoPrograma() != null && invitacion.getTipoPrograma().equals("deteccion")){
			deteccion.setCveDeteccion(Long.valueOf(invitacion.getCveTemp()));
			deteccion = this.deteccionService.consultaPorClave(deteccion);
			if(deteccion != null){
				invitacion.setCveDeteccion(BigDecimal.valueOf(deteccion.getCveDeteccion()));
				invitacion.setFolioAntecedente(deteccion.getNuFoliodeteccion());
				invitacion.setTipoPrograma(deteccion.getCveTipocorr().toString());
				if(deteccion.getCveFkPatron() != null){
					SatPatron patron = this.patronesService.getById(deteccion.getCveFkPatron()); 
					if(patron != null){
						invitacion.setCveFkPatron(patron.getCvePK());
						invitacion.setRegPatronal(patron.getRegistroPatronal());
						invitacion.setRazonSocial(patron.getRazonSocial());
					}
				}
				invitacion.setFechaIncial(Functions.dateToString3(deteccion.getFecFechainicioEst()));
				if(deteccion.getFecFechaterminoEst() != null && deteccion.getFecFechaterminoEst().after(new Date()) ){
					invitacion.setFechaFinal(Functions.dateToString3(new Date()));
				}else{
					invitacion.setFechaFinal(Functions.dateToString3(deteccion.getFecFechaterminoEst()));
				}
			}
		}
		
		return invitacion;
	}
	
	@RequestMapping(value="/obtenerFechaServidor.do", method=RequestMethod.POST)
	public @ResponseBody String obtenerFechaServidor(HttpServletRequest request){
		return ConstantesBusiness.dateToStringFormat(new Date(), ConstantesBusiness.DD_MM_YYYY);
	}
	
	@RequestMapping(value="/obtenerFechaServidorFormat.do", method=RequestMethod.POST)
	public @ResponseBody String obtenerFechaServidorFormat(HttpServletRequest request){
		return ConstantesBusiness.dateToStringFormat(new Date(), ConstantesBusiness.DD_MM_YYYY);
	}
	
	@RequestMapping(value="/obtenerFechaServidorMinima.do", method=RequestMethod.POST)
	public @ResponseBody String obtenerFechaServidorMinima(HttpServletRequest request){
		Calendar calendario = Calendar.getInstance();
		calendario.add(Calendar.DAY_OF_YEAR, -44);
		Date dia = calendario.getTime();
		return ConstantesBusiness.dateToStringFormat(dia, ConstantesBusiness.DD_MM_YYYY);
	}
}
