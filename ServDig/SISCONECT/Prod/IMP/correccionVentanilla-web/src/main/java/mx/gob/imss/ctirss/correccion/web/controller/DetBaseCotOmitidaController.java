package mx.gob.imss.ctirss.correccion.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.correccion.commons.sbc.vo.ExcedentesTopadosVO;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcEjercicio;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcPercepciones;
import mx.gob.imss.ctirss.correccion.correccion.service.interfaces.SolicitudService;
import mx.gob.imss.ctirss.correccion.detBaseCotOmitida.service.interfaces.DetBaseCotOmitidaService;
import mx.gob.imss.ctirss.correccion.deteccion.base.paginador.model.DetBaseCotOmitidaWrapperDataTable;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.model.CrtBalanzaComp;
import mx.gob.imss.ctirss.correccion.model.CrtCopPagadasAnual;
import mx.gob.imss.ctirss.correccion.model.CrtDetBaseCotOmDet;
import mx.gob.imss.ctirss.correccion.model.CrtDetBaseCotOmitida;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
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
 * @date 10/01/2012
 */
@Controller
@RequestMapping(value="/catalogo/detBaseCotOmitida")
public class DetBaseCotOmitidaController extends AbstractController{
	
	@Autowired
	private DetBaseCotOmitidaService<CrtDetBaseCotOmitida> detBaseCotOmitidaService;
	@Autowired
	private DetBaseCotOmitidaService<CrtDetBaseCotOmDet> detBaseCotOmitidaDetService;
	@Autowired
	private DetBaseCotOmitidaService<CrtBalanzaComp> detBaseCotOmitidaBalService;
	@Autowired
	private DetBaseCotOmitidaService<CrtAnexosolcorrpat> detBaseCotOmitidaServiceAnexo;
	@Autowired
	private DetBaseCotOmitidaService<CrtCopPagadasAnual> detBaseCotOmitidaServiceCopPagadas;
	@Autowired
	private SolicitudService<CrtSolicitudcorr> solicitudService;
	@Autowired
	private IPatronesService patronesService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model,HttpServletRequest request) {
		model.addAttribute("crtDetBaseCotOmitida",new CrtDetBaseCotOmitida());
		return determinaURL(request, "detBaseCotOmitida/solicitud/detBaseCotOmitidaMain", "detBaseCotOmitida/solicitud/detBaseCotOmitidaMain/patron");
	}
	
	@RequestMapping(value="/buscaFolio" , method=RequestMethod.POST)
	public @ResponseBody List<CrtAnexosolcorrpat> buscaFolio(@RequestBody CrtSolicitudcorr solicitud) {
		
		List<CrtAnexosolcorrpat> lstAnexos = new ArrayList<CrtAnexosolcorrpat>();
		
		solicitud = this.solicitudService.consultarFolio(solicitud);
		
		if(solicitud != null){
			
			lstAnexos = this.detBaseCotOmitidaService.buscaAnexosporidSolicitud(solicitud);
		}
		if(lstAnexos != null && lstAnexos.size() > 0){
			SatPatron patron = new SatPatron();
			for (Iterator iterator = lstAnexos.iterator(); iterator.hasNext();) {
				CrtAnexosolcorrpat crtAnexosolcorrpat = (CrtAnexosolcorrpat) iterator.next();
				
				patron = this.patronesService.getById(crtAnexosolcorrpat.getCvePatron());
				if(patron != null){
					crtAnexosolcorrpat.setPatron(patron);
				}
			}
			
		}else{
			return null;
		}
		return lstAnexos;
	}
	
	@RequestMapping(value="/buscaEjercicio" , method=RequestMethod.POST)
	public @ResponseBody List<CrcEjercicio> buscaEjercicio(@RequestBody CrcEjercicio ejercicio) {
		
		List<CrcEjercicio> lstEjercicio = new ArrayList<CrcEjercicio>();
		
		lstEjercicio = this.detBaseCotOmitidaService.buscaEjercicio(ejercicio);
		
		return lstEjercicio;
	}
	
	@RequestMapping(value="/paginar" , method=RequestMethod.POST)
	public @ResponseBody DatosSalidaPaginador<CrtDetBaseCotOmitida> paginar(@RequestBody DetBaseCotOmitidaWrapperDataTable aoData , HttpServletRequest request) {
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		List<CrtDetBaseCotOmitida> lstdeterminacion = new ArrayList<CrtDetBaseCotOmitida>();
		
		send.parserArray(aoData.getAoData());	
		send.setModelo(aoData.getoForm());
		
		DatosSalidaPaginador<CrtDetBaseCotOmitida> reply = this.detBaseCotOmitidaService.paginar(send);
		if(reply != null && reply.getAaData().size() > 0){
			// llenar folio razon , ejercicio
			CrtAnexosolcorrpat anexo = new CrtAnexosolcorrpat();
			
			for (Iterator iterator = reply.getAaData().iterator(); iterator.hasNext();) {
				CrtDetBaseCotOmitida detBaseCotOmitida = (CrtDetBaseCotOmitida) iterator.next();
				
				detBaseCotOmitida.setEjercicio(detBaseCotOmitida.getCveEjercicio().toString());
				anexo.setCveAnexoSolicitudCorrPat(detBaseCotOmitida.getCveAnexoSolCorrPat().intValue());
				anexo = this.detBaseCotOmitidaServiceAnexo.buscaAnexosById(anexo);
				if(anexo != null){
					detBaseCotOmitida.setRazonSocial(anexo.getTxRazonSocial());
					if(anexo.getCveSolicitudCorr() != null){
						List<CrtSolicitudcorr> lstSolicitud = new ArrayList<CrtSolicitudcorr>();
						lstSolicitud = this.solicitudService.consultarSolicitudesById(anexo.getCveSolicitudCorr());
						if(lstSolicitud != null && lstSolicitud.size() > 0){
							detBaseCotOmitida.setFolioCorreccion(lstSolicitud.get(0).getNuFolio());
						}
					}
				}
			}
				
			
		}
		reply.setsEcho(send.getsEcho());
        
        return reply;
	}
	
	@RequestMapping(value="/buscaPercepciones" , method=RequestMethod.POST)
	public @ResponseBody List<CrcPercepciones> buscaPercepciones(@RequestBody CrtBalanzaComp model) {
		
		List<CrcPercepciones> lstPercepciones = new ArrayList<CrcPercepciones>();
		List lstT = new ArrayList();
		
		lstT = this.detBaseCotOmitidaBalService.buscaPercepciones(model);
		lstPercepciones.addAll(lstT);
		
		return lstPercepciones;
	}
	
	@RequestMapping(value="/calculaBaloAux" , method=RequestMethod.POST)
	public @ResponseBody CrtBalanzaComp calculaBalanza(@RequestBody CrtBalanzaComp model) {
				
		model = this.detBaseCotOmitidaBalService.calculaBalanza(model);
		
		return model;
	}
	
	@RequestMapping(value="/calculaMenos" , method=RequestMethod.POST)
	public @ResponseBody CrtCopPagadasAnual calculaMenos(@RequestBody CrtCopPagadasAnual model) {
				
		model = this.detBaseCotOmitidaServiceCopPagadas.calculaMenos(model);
		
		return model;
	}
	
	@RequestMapping(value="/calculaExcedente" , method=RequestMethod.POST)
	public @ResponseBody ExcedentesTopadosVO calculaExcedente(@RequestBody ExcedentesTopadosVO excedenteTopadoVO) {
		try {
			excedenteTopadoVO = this.detBaseCotOmitidaServiceCopPagadas.calculaExcedenteTopado(excedenteTopadoVO);	
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return excedenteTopadoVO;
	}
	
	@RequestMapping(value="/llenaCabecera" , method=RequestMethod.POST)
	public @ResponseBody CrtAnexosolcorrpat llenaCabecera(@RequestBody CrtBalanzaComp model) {
				
		Integer cveAnexo = model.getCveAnexosolcorrpat();
		Integer cveEjercicio = model.getCveEjercicio();
		CrtAnexosolcorrpat anexo = new CrtAnexosolcorrpat();
		anexo.setCveAnexoSolicitudCorrPat(cveAnexo);
		anexo = this.detBaseCotOmitidaServiceAnexo.buscaAnexosById(anexo);
		SatPatron patron = this.patronesService.getById(anexo.getCvePatron());
		if(patron != null){
			anexo.setPatron(patron);
		}
		CrtSolicitudcorr solicitud = new CrtSolicitudcorr();
		solicitud.setCveSolicitudCorr(anexo.getCveSolicitudCorr());
		List<CrtSolicitudcorr> lstSolicitud = this.solicitudService.consultarSolicitudesById(solicitud.getCveSolicitudCorr());
		if(lstSolicitud != null && lstSolicitud.size() > 0){
			anexo.setSolicitudCorreccion(lstSolicitud.get(0));
		}
		anexo.setCrcEjercicio(new CrcEjercicio());
		anexo.getCrcEjercicio().setCveEjercicio(cveEjercicio.longValue());
		
		return anexo;
	}
	
	@RequestMapping(value="/guardaDetBaseCotOm" , method=RequestMethod.POST)
	public @ResponseBody CrtDetBaseCotOmitida guardaDetBaseCotOm(@RequestBody CrtDetBaseCotOmitida model, HttpServletRequest request) {
				
		UserSession session = this.getUsuarioFirmado(request);
		if(session != null && session.getCurpUsuario() != null){
			model.setCveUsuario(session.getCurpUsuario().toString());
		}
		model.setFecFechaRegistro(new Date());
		model = this.detBaseCotOmitidaService.guardaDetBaseCotOm(model);
		
		return model;
	}
	
	@RequestMapping(value="/guardaDetBaseCotOmDet" , method=RequestMethod.POST)
	public @ResponseBody CrtDetBaseCotOmDet guardaDetBaseCotOmDet(@RequestBody CrtDetBaseCotOmDet model) {
		model = this.detBaseCotOmitidaDetService.guardaDetBaseCotOmDet(model);
		return model;
	}

	
	@RequestMapping(value="/validaDetBaseCotOm" , method=RequestMethod.POST)
	public @ResponseBody CrtDetBaseCotOmitida validaDetBaseCotOm(@RequestBody CrtDetBaseCotOmitida model) {
		model = this.detBaseCotOmitidaService.validaDetBaseCotOm(model);
		
		//Si la cedula I esta vacia manda una bandera
		if(model!=null && model.isCedulaIVacia()){
			return model;
		}
		
		if(model != null && model.getCveDetBaseCotOmit() == null){
			model = null;
		}
		return model;
	}
}
