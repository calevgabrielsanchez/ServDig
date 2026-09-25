package mx.gob.imss.ctirss.correccion.web.controller.monitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.gob.imss.ctirss.correccion.correccion.service.interfaces.SolicitudService;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CrtControlFlujoCedula;
import mx.gob.imss.ctirss.correccion.model.CrtControlFlujoCedulaPK;
import mx.gob.imss.ctirss.correccion.model.CrtErrorCargaCed;
import mx.gob.imss.ctirss.correccion.model.CrtEstatusFlujoCedula;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.monitor.service.interfaces.MonitorService;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.web.controller.cedulascontruccion.AbstractCedulasSQL;
import mx.gob.imss.ctirss.correccion.web.controller.cedulascontruccion.DescargaCedula;
import mx.gob.imss.ctirss.correccion.web.controller.monitor.MonitorCedula;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/cedulasCorreccion/monitor")
public class MonitorController extends AbstractController{


	@Autowired
	private SolicitudService<CrtSolicitudcorr> solicitudServiceBean;
	
	@Autowired
	private MonitorService<CrtControlFlujoCedula> ctrControlFlujoServiceBean;
	
	@Autowired
	private ICatalogoService<AbstractModel> catalogoServiceBeanPeriodo;
	
	 List<?> errores = null;
	 
	 MonitorCedula monitorCedula = null;
	 

	 @RequestMapping(method=RequestMethod.GET)
	 public String getUploadForm(Model model,HttpServletRequest request) {

		 monitorCedula = new MonitorCedula();
		 List<CrtControlFlujoCedula> historialInicial = new ArrayList<CrtControlFlujoCedula>();

		 CrtEstatusFlujoCedula crtEstatusFlujoCedula = new CrtEstatusFlujoCedula();
		 crtEstatusFlujoCedula.setCveEstatus(new Long (EstatusCorrecciones.ESTATUS_SIN_OPERACION));
		 crtEstatusFlujoCedula.setTxDescripcion(ConstantesCedulas.ANEXO_SIN_OPERACION);

		 CrtControlFlujoCedula crtControlFlujoCedula = new CrtControlFlujoCedula();
		 crtControlFlujoCedula.setCrtEstatusFlujoCedula(crtEstatusFlujoCedula);

		 historialInicial.add(crtControlFlujoCedula); //Cedula A
		 historialInicial.add(crtControlFlujoCedula); //Cedula G
		 historialInicial.add(crtControlFlujoCedula); //Cedula H
		 historialInicial.add(crtControlFlujoCedula); //Cedula I
		 historialInicial.add(crtControlFlujoCedula); //Cedula O
		 historialInicial.add(crtControlFlujoCedula); //Cedula Q
		 historialInicial.add(crtControlFlujoCedula); //Trabajadores
		 historialInicial.add(crtControlFlujoCedula); //Cops pagadas

		 monitorCedula.setHistorialAnexos(historialInicial);
		 model.addAttribute(monitorCedula);
		 return determinaURL(request, "cedulasCorreccion/monitor/monitorMain", "cedulasCorreccion/monitor/monitorMain/patron");
	 }
	 
	 @RequestMapping(value="/recuperaEstatusMonitor" , method=RequestMethod.POST)
		 public @ResponseBody List<CrtControlFlujoCedula> recuperaEstatusMonitor(@RequestBody DescargaCedula descargaCedula,HttpServletRequest request){ 
		 
		 System.out.println("Recuperando Estaus de Monitor");
		 List<CrtControlFlujoCedula> lstControlCedulas = new ArrayList<CrtControlFlujoCedula>();
		 
		 CrtSolicitudcorr patrones = new CrtSolicitudcorr();
		 patrones.setNuFolio(descargaCedula.getFolioCorreccion());

		 CrtSolicitudcorr resultado = solicitudServiceBean.consultarFolio(patrones);		
		 System.out.println("LA soloicitud de correc  "+resultado);
		 if(resultado!=null){

			 CrtControlFlujoCedulaPK idPk = new CrtControlFlujoCedulaPK();
			 idPk.setCveSolicitudcorr(resultado.getCveSolicitudCorr());
			 idPk.setCveEjercicio(monitorCedula.getPeriodo());
			 CrtControlFlujoCedula  crtControlFlujoCedula = new CrtControlFlujoCedula();
			 crtControlFlujoCedula.setId(idPk);

			  lstControlCedulas = ctrControlFlujoServiceBean.consultar(crtControlFlujoCedula);
			  System.out.println("Total "+lstControlCedulas.size());
		 }
		 
		 
		 
//		 for(CrtControlFlujoCedula flujo:lstControlCedulas){
//			 flujo.setPorcentajeAvance(13f);
//		 }
		 return lstControlCedulas;
	 }
		@RequestMapping(value="/getPeriodosCorreccion" , method=RequestMethod.POST)
		public @ResponseBody List<?> getPeriodosCorreccion(@RequestBody DescargaCedula descargaCedula,HttpServletRequest request) {
			
			
			String SQL = AbstractCedulasSQL.OBTENER_PERIODOS_CORRECCION.replace("{1}",descargaCedula.getFolioCorreccion());
			
			List<?> ls = catalogoServiceBeanPeriodo.consultaSQL(SQL);
					
			return ls;
		}
		//

	 @RequestMapping(value="/validar" , method=RequestMethod.POST)
	 public @ResponseBody CrtSolicitudcorr consultar(@RequestBody MonitorCedula itemCedula) {
		 CrtSolicitudcorr patrones = new CrtSolicitudcorr();
		 patrones.setNuFolio(itemCedula.getFolioCorreccion());
		 return  solicitudServiceBean.consultarFolio(patrones);		
	 }

	 @RequestMapping(value="/monitor" , method=RequestMethod.POST)
	 public String create(MonitorCedula  monitorCedula , HttpServletRequest request, HttpServletResponse response) {

		 //Inicializa el monitor		
		 if(request.getParameter(ConstantesCedulas.FROM_PAGE_MONITOR)==null){
			 monitorCedula.setHistorialAnexos(new ArrayList<CrtControlFlujoCedula>()); 
		 }

		 this.errores = new ArrayList<CrtErrorCargaCed>();

		 CrtSolicitudcorr patrones = new CrtSolicitudcorr();
		 patrones.setNuFolio(monitorCedula.getFolioCorreccion());

		 CrtSolicitudcorr resultado = solicitudServiceBean.consultarFolio(patrones);		

		 if(resultado!=null){

			 CrtControlFlujoCedulaPK idPk = new CrtControlFlujoCedulaPK();
			 idPk.setCveSolicitudcorr(resultado.getCveSolicitudCorr());
			 idPk.setCveEjercicio(monitorCedula.getPeriodo());
			 CrtControlFlujoCedula  crtControlFlujoCedula = new CrtControlFlujoCedula();
			 crtControlFlujoCedula.setId(idPk);

			 List<CrtControlFlujoCedula> lstControlCedulas = ctrControlFlujoServiceBean.consultar(crtControlFlujoCedula);

			 if(lstControlCedulas!=null && !lstControlCedulas.isEmpty()){
	
				 Iterator<CrtControlFlujoCedula> iterControlesCedula =  (Iterator<CrtControlFlujoCedula>) lstControlCedulas.iterator();

				 CrtControlFlujoCedula cfaTMP = null;
				 List<CrtControlFlujoCedula> lstCfa = new ArrayList<CrtControlFlujoCedula>();
				 int index = 1;

				 while(iterControlesCedula.hasNext()){
					 cfaTMP = iterControlesCedula.next();
					 generaMonitor(cfaTMP,lstCfa,new Integer(monitorCedula.getIdArchivoCarga()),index);
					 index= lstCfa.size();
					 index++;
				 }


				 for (int i = lstCfa.size() ; i < 8; i++) {
					 CrtEstatusFlujoCedula completaEstatusFlujoCedula = new CrtEstatusFlujoCedula();
					 completaEstatusFlujoCedula.setCveEstatus(new Long (EstatusCorrecciones.ESTATUS_SIN_OPERACION));
					 completaEstatusFlujoCedula.setTxDescripcion(ConstantesCedulas.ANEXO_SIN_OPERACION);

					 CrtControlFlujoCedula completaControlFlujoCedula = new CrtControlFlujoCedula();
					 completaControlFlujoCedula.setCrtEstatusFlujoCedula(completaEstatusFlujoCedula);
					 lstCfa.add(completaControlFlujoCedula);
				 }

				 monitorCedula.setHistorialAnexos(lstCfa);

			 }// Existe el Folio Pero no tiene cedulas cargadas
			 else{
				 monitorCedula.setMensaje("El de Folio de Corrección no tiene ninguna cédula en operación");	
				 monitorCedula.setHistorialAnexos(complementaLista());
			 }
		 }
		 else{
			 monitorCedula.setMensaje("El de Folio de Corrección es inválido, intente nuevamente");
			 monitorCedula.setHistorialAnexos(complementaLista());
		 }

		 request.setAttribute(ConstantesCedulas.LISTADO_ERRORES_ANEXOS, this.errores);
		 request.setAttribute("monitorCedula",monitorCedula);
		 return determinaURL(request, "cedulasCorreccion/monitor/monitorMain", "cedulasCorreccion/monitor/monitorMain/patron");
	 }


	 private  List<CrtControlFlujoCedula> generaMonitor(CrtControlFlujoCedula cfaTMP,List<CrtControlFlujoCedula> lstCfa,Integer anexoSeleccionado,int index){

		 if(cfaTMP.getId().getCveCedula()==index){

			 lstCfa.add(cfaTMP);
			 if(anexoSeleccionado!=null && anexoSeleccionado.intValue()>0 
					 && anexoSeleccionado.intValue()==cfaTMP.getId().getCveCedula()){
				 try {

					 CrtErrorCargaCed crtErrorCargaCed = new CrtErrorCargaCed();
					 crtErrorCargaCed.setCrtControlFlujoCedula(cfaTMP);

					 this.errores=ctrControlFlujoServiceBean.consultarErrores(crtErrorCargaCed);
				 } catch (Exception e) {                
					 e.printStackTrace();
				 }
			 }
		 }else{
			 CrtEstatusFlujoCedula crtEstatusFlujoCedula = new CrtEstatusFlujoCedula();
			 crtEstatusFlujoCedula.setCveEstatus(new Long (EstatusCorrecciones.ESTATUS_SIN_OPERACION));
			 crtEstatusFlujoCedula.setTxDescripcion(ConstantesCedulas.ANEXO_SIN_OPERACION);

			 CrtControlFlujoCedula crtControlFlujoCedula = new CrtControlFlujoCedula();
			 crtControlFlujoCedula.setCrtEstatusFlujoCedula(crtEstatusFlujoCedula);
			 lstCfa.add(crtControlFlujoCedula);
			 index ++;
			 generaMonitor(cfaTMP,lstCfa,anexoSeleccionado,index);
		 }

		 return lstCfa;
	 }

	
	private List<CrtControlFlujoCedula> complementaLista(){
		 List<CrtControlFlujoCedula> historialfinal = new ArrayList<CrtControlFlujoCedula>();

		 CrtEstatusFlujoCedula flujoCedulaFinal = new CrtEstatusFlujoCedula();
		 flujoCedulaFinal.setCveEstatus(new Long (EstatusCorrecciones.ESTATUS_SIN_OPERACION));
		 flujoCedulaFinal.setTxDescripcion(ConstantesCedulas.ANEXO_SIN_OPERACION);

		 CrtControlFlujoCedula controlFlujoCedulaFinal = new CrtControlFlujoCedula();
		 controlFlujoCedulaFinal.setCrtEstatusFlujoCedula(flujoCedulaFinal);

		 historialfinal.add(controlFlujoCedulaFinal); //Cedula A
		 historialfinal.add(controlFlujoCedulaFinal); //Cedula G
		 historialfinal.add(controlFlujoCedulaFinal); //Cedula H
		 historialfinal.add(controlFlujoCedulaFinal); //Cedula I
		 historialfinal.add(controlFlujoCedulaFinal); //Cedula O
		 historialfinal.add(controlFlujoCedulaFinal); //Cedula Q
		 historialfinal.add(controlFlujoCedulaFinal); //Trabajadores
		 historialfinal.add(controlFlujoCedulaFinal); //COPs Pagadas
		 monitorCedula.setHistorialAnexos(historialfinal);
		 
		return historialfinal;
	}
	
}
