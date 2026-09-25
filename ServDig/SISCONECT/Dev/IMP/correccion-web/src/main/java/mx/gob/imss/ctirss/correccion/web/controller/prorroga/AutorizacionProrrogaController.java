package mx.gob.imss.ctirss.correccion.web.controller.prorroga;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTipoCorr;
import mx.gob.imss.ctirss.correccion.correccion.service.interfaces.SolicitudService;
import mx.gob.imss.ctirss.correccion.deteccion.base.paginador.model.CrtProrrogaWrapperDataTable;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.model.CrcStatus;
import mx.gob.imss.ctirss.correccion.model.CrtProrroga;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.prorroga.service.interfaces.ProrrogaService;
import mx.gob.imss.ctirss.correccion.service.interfaces.IPatronesService;
import mx.gob.imss.ctirss.correccion.session.UserSession;

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
 * @date 28/12/2011
 */
@Controller
@RequestMapping(value="/solicitud/autorizacionProrroga")
public class AutorizacionProrrogaController extends AbstractController{
	
	@Autowired
	private ProrrogaService<CrtProrroga> prorrogaService;
	@Autowired
	private ProrrogaService<CrcStatus> prorrogaStatusService;
	@Autowired
	private ProrrogaService<CrcTipoCorr> prorrogaTipoService;
	@Autowired
	private SolicitudService<CrtSolicitudcorr> solicitudService;
	@Autowired
	private IPatronesService patronesService;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model) {
		model.addAttribute("crtProrroga",new CrtProrroga());
		return "autorizacionProrroga/autorizacionProrrogaMain";
		}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/paginar" , method=RequestMethod.POST)
	public @ResponseBody DatosSalidaPaginador<CrtProrroga> paginar(@RequestBody CrtProrrogaWrapperDataTable aoData , HttpServletRequest request) {
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		UserSession usrSession = getUsuarioFirmado(request);
		send.parserArray(aoData.getAoData());	
		send.setModelo(aoData.getoForm());
		
		DatosSalidaPaginador<CrtProrroga> reply = this.prorrogaService.paginaAP(send);
		List<CrtProrroga> listaPro=new ArrayList<CrtProrroga>();
		if(reply != null && reply.getAaData().size() > 0){
			List<CrtSolicitudcorr> solicitud = new ArrayList<CrtSolicitudcorr>();
			
			for (Iterator iterator = reply.getAaData().iterator(); iterator.hasNext();) {
				CrtProrroga crtProrroga = (CrtProrroga) iterator.next();
				
				if(crtProrroga != null && crtProrroga.getCveSolicitudcorr() != null){
					List<CrcStatus> lststatus = this.prorrogaStatusService.llenarStatus();
					crtProrroga.setLstStatus(lststatus);
					solicitud = this.solicitudService.consultarSolicitudesById(crtProrroga.getCveSolicitudcorr().intValue(),usrSession.getIdSubDelegacion());
					if(solicitud != null && solicitud.size() > 0){
						crtProrroga.setNuFolio(solicitud.get(0).getNuFolio() == null ? "" : solicitud.get(0).getNuFolio());
						crtProrroga.setTipoCorreccion(solicitud.get(0).getCveTipoCorreccion() == null ? "" : solicitud.get(0).getCveTipoCorreccion().toString());
						crtProrroga.setFecPeriodoIni(solicitud.get(0).getFecFechaPeriodoIni() == null ? "" : solicitud.get(0).getFecFechaPeriodoIni().toString());
						crtProrroga.setFecPeriodoFin(solicitud.get(0).getFecFechaPeriodoFin() == null ? "" : solicitud.get(0).getFecFechaPeriodoFin().toString());
						if(solicitud.get(0).getFecFechaLimite() != null){
							crtProrroga.setFecLimite(solicitud.get(0).getFecFechaLimite().toString());
						}
						if(solicitud.get(0).getCvePatron() != null){
							SatPatron patron = new SatPatron();
							patron = this.patronesService.getById(solicitud.get(0).getCvePatron());
							if(patron != null){
								crtProrroga.setRazonSocial(patron.getRazonSocial());
								crtProrroga.setRegPatronal(patron.getRegistroPatronal());
							}
						}
						listaPro.add(crtProrroga);
					}
					
					if(crtProrroga.getTipoCorreccion() != null){
						CrcTipoCorr tipoCorreccion = new CrcTipoCorr();
						tipoCorreccion = this.prorrogaTipoService.buscaTipoCorr(crtProrroga.getTipoCorreccion());
						if(tipoCorreccion != null){
							crtProrroga.setTipoCorreccion(tipoCorreccion.getTxDescripcion());
						}
					}
				}
			}
				
			
		}
		reply.setAaData(listaPro);
		reply.setiTotalRecords(listaPro.size());
		reply.setiTotalRecords(listaPro.size());
		reply.setsEcho(send.getsEcho());
        
        return reply;
	}
	
	@RequestMapping(value="/guardar" , method=RequestMethod.POST)
	public @ResponseBody CrtProrroga guardar(@RequestBody String model, HttpServletRequest request) {
		
		List<CrtProrroga> lstprorroga = new ArrayList<CrtProrroga>();
		UserSession session = this.getUsuarioFirmado(request);
		
		model = model.substring(1, model.length() - 1);
		StringTokenizer tokens = new StringTokenizer(model, "$");
		while(tokens.hasMoreTokens()){
			String str=tokens.nextToken();
			String [] temp = str.split("/");
			String id = temp[0];
			String status = temp[1];
			CrtProrroga prorroga = new CrtProrroga();
			if(id != null){
				prorroga.setCveSolprorroga(Long.valueOf(id));
			}
			if(status != null){
				prorroga.setStatus(status);
			}
			if(session != null && session.getCveIdUsuario() != null){
				prorroga.setCveUser(session.getCveIdUsuario().toString());
			}
			lstprorroga.add(prorroga);
        }
		this.prorrogaService.guardar(lstprorroga);
        
        return null;
	}
	

}
