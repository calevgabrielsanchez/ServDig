package mx.gob.imss.ctirss.correccion.web.controller.seguimiento.pagos;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.base.paginador.model.CrtRevPagosWrapper;
import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model.CrtCobranzaPagos;
import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model.CrtRevPagos;
import mx.gob.imss.ctirss.correccion.seguimiento.service.interfaces.pagos.PagosService;
import mx.gob.imss.ctirss.correccion.web.controller.seguimiento.pagos.vo.PagosVO;
import mx.gob.imss.ctirss.correccion.web.controller.seguimiento.pagos.vo.RegistroPatronalVO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Permite controlar los procesos involucrados
 * con la pantalla de pagos (pagosSeguimientoMain.jsp)
 * 
 * @version 1.1.0
 * @author Marco Antonio Nieto Plett
 *
 */
@Controller
@RequestMapping(value = "/seguimiento/ec/pagos")
public class PagosSeguimientoController extends AbstractController{


	/**
	 * Servicio que controla los pagos
	 */
	@Autowired
	private PagosService<?> pagosService;
	
	/**
	 * Servicio que controla los procesos
	 * de los pagos.
	 */
	@Autowired
	private ProcesosPagos ProcesosPagos;
	
	/**
	 * Logger de la clase
	 */
	private final static Logger logger = Logger.getLogger(PagosSeguimientoController.class);
	
	/**
	 * Permite probar la pantalla de pagos sin pasar
	 * por algún flujo en especial.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/testPantallaPagos" , method=RequestMethod.GET)
	public String testPantallaPagos(Model model, HttpServletRequest request) throws Exception {
		return "testPago";
	}
	
	/**
	 * Se ejecuta al momento de llamar a la ventana modal que
	 * controla a los pagos. <br>
	 * Es necesario enviar lo siguientes parámentros en el request (GET)<br>
	 * <b>periodoInicial:</b>DD/MM/YYYY<br>
	 * <b>periodoFinal:</b>DD/MM/YYYY<br>
	 * <b>folioCorreccion:</b>DDSS/XX/YYYY/####<br>
	 * <b>idPresentacion:</b>Númerico<br>
	 * <b>indTipoPago:</b> 1 para Cédula de Revisión y 2 Para Cédula de Validación<br><br>
	 * 
	 * Inicializa los siguientes objetos: PagosVO, CrtRevPagos.
	 *
	 * @see CrtRevPagos
	 * @see PagosVO
	 * @see ProcesosPagos
	 * @param model
	 * @param request
	 * @return Tile
	 * @throws Exception Personalizada.
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model, HttpServletRequest request) throws Exception {
		
		logger.info("Iniciando Carga de Pantalla Pagos Estudio de Corrección Folio");
		
		request.getSession().removeAttribute("mapaRegistrosPatronales");
		
		PagosVO pagosVO = ProcesosPagos.initModel(new PagosVO(), request); 
		
		RegistroPatronalVO rpVO = ProcesosPagos.obtenerRegistrosPatronales(pagosVO);
		
		if(ProcesosPagos.validarContenidoRPs(rpVO.getListaRPS())){
			
			ProcesosPagos.inicializarTablaPagosAuditor(rpVO,pagosVO,getUsuarioFirmado(request));
			
			logger.info("Asignando modelo de la forma del folio:"+pagosVO.getFolioCorreccion());
			
			pagosVO.initComboBoxes(rpVO.getListaRPS());
			
			request.getSession().setAttribute("mapaRegistrosPatronales", pagosVO.getMapaRegistrosPatronales());
			
			model.addAttribute("pagosVO",pagosVO);
			
		}else{
			logger.error("Folio Inválido:"+pagosVO.getFolioCorreccion()+" ::No existen registros patronales asociados::");
		
			throw new Exception("No se encontró ningún registro patronal asociado a los datos enviados. Favor de verficiar");
		}
		 
		return "pagosSeguimientoMain";
	}
	
	/**
	 * Método ejecutado a través de JSON, permite agregar o modificar un
	 * registro de tipo pago.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @see PagosService
	 * @see ProcesosPagos
	 * @param pagosVO
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/agregarModificar" , method=RequestMethod.POST)
	public @ResponseBody PagosVO agregarModificar(@RequestBody PagosVO pagosVO, HttpServletRequest request,HttpServletResponse response) {
		
		try {
			
			pagosVO.setMsg("");
						
			
			
			pagosVO.setPagoModel(ProcesosPagos.completarDatosObligatorios(getUsuarioFirmado(request),pagosVO.getPagoModel()));
			pagosVO.setPagoModel(pagosService.saveOrUpdate(pagosVO.getPagoModel()));
			pagosVO.setMsg(pagosVO.getPagoModel().getExito());
			
		} catch (SQLException e) {
			pagosVO.setMsg(e.getCause().getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			pagosVO.setMsg(e.getCause().toString());
		}
		
		return pagosVO;
		
	}
	
	/**
	 * Permite recuperar una lista de pagos realizados (CRT_REVPAGOS)
	 * @author Marco Antonio Nieto Plett
	 * 
	 * @param aoData
	 * @param request
	 * @return Lista de pagos realizafos
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/listarPagosRealizados" , method=RequestMethod.POST)
	public@ResponseBody	DatosSalidaPaginador<CrtRevPagos> listarPagosRealizados(@RequestBody CrtRevPagosWrapper aoData,HttpServletRequest request) {
		
		Map mapaRps = (Map) request.getSession().getAttribute("mapaRegistrosPatronales");
		
		DatosEntradaPaginador send = new DatosEntradaPaginador();

		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());

		
		List<CrtRevPagos> lsPagosRealizados;
		
		try {
			

			lsPagosRealizados = ProcesosPagos.obtenerListaPagos(aoData.getoForm().getCvePresentacorr(), 
					                                            aoData.getoForm().getIndTipopago(),
					                                            aoData.getoForm().getCveRegulaPagos(),
					                                            mapaRps);
			
		} catch (SQLException e) {
			lsPagosRealizados = new ArrayList<CrtRevPagos>();
			e.printStackTrace();
		} catch (Exception e) {
			lsPagosRealizados = new ArrayList<CrtRevPagos>();
			e.printStackTrace();
		}
		
		DatosSalidaPaginador<CrtRevPagos> response = new DatosSalidaPaginador<CrtRevPagos>();		
		
		//int iTotalRecords = 0;
		int iTotalDisplayRecords = lsPagosRealizados.size();
		 
		response.setAaData(lsPagosRealizados);
		response.setiTotalDisplayRecords(iTotalDisplayRecords);
		response.setiTotalRecords(iTotalDisplayRecords);
		
		response.setsEcho(send.getsEcho());
		
		
		
		return response;
		
	}
	
	
	@RequestMapping(value="/recuperaDetalleCobranzaPago" , method=RequestMethod.POST)
	public @ResponseBody List<CrtCobranzaPagos> recuperaDetalleCobranzaPagos(@RequestBody CrtCobranzaPagos aoData,HttpServletRequest request) {
		System.out.println("Recuperando detalle ");
		List<CrtCobranzaPagos> datos=pagosService.recuperaDetalleCobranza(aoData);
		SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
		for(CrtCobranzaPagos pago:datos){
			pago.setFecCarga(formatoDeFecha.format(pago.getFecFechaCarga()));
			pago.setFecPago(formatoDeFecha.format(pago.getFecFechapago()));			
		}
		return datos;
	}
	
	
	
	
	
	
	
	/**
	 * Permite eliminar un pago de la lista. Solo privilegios arriba de supervisor
	 * 
	 * @author Marco Antonio Nieto Plett
	 * 
	 * @param pagosVO
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/eliminarPago" , method=RequestMethod.POST)
	public @ResponseBody PagosVO eliminarPago(@RequestBody PagosVO pagosVO, HttpServletResponse response) {
		
		try {
			pagosVO.setMsg("");	
			pagosVO.setPagoModel(pagosService.delete(pagosVO.getPagoModel()));
			pagosVO.setMsg(pagosVO.getPagoModel().getExito());
		} catch (SQLException e) {
			pagosVO.setMsg(e.getCause().getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			pagosVO.setMsg(e.getCause().getMessage());
			e.printStackTrace();
		}
		
		return pagosVO;
		
	}
	
	/**
	 * Permite obtener la información de un págo en específico.
	 * @author Marco Antonio Nieto Plett
	 * 
	 * @param pagosVO
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/consultarPago" , method=RequestMethod.POST)
	public @ResponseBody PagosVO consultarPago(@RequestBody PagosVO pagosVO, HttpServletResponse response) {
		
		try {
			pagosVO.setMsg("");
			pagosVO.setPagoModel(pagosService.findById(pagosVO.getPagoModel()));
			
			if(pagosVO.getPagoModel().getCveRegulaPagos()!=null){
				
				RegistroPatronalVO registro =ProcesosPagos.getRegistroPatronalPromocion(pagosVO);
				
				pagosVO.getPagoModel().setCveAnexosolcorrpat(registro.getId());
				
			}
			
		} catch (SQLException e) {
			pagosVO.setMsg(e.getCause().getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			pagosVO.setMsg(e.getCause().getMessage());
			e.printStackTrace();
		}
		
		return pagosVO;
		
	}
	
	/**
	 * Permite obtener el total pagado de todos los registros patronales
	 * y todos los periodos.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * 
	 * @param nuFolioCorreccion
	 * @param cveRegulaPago
	 * @param tipoPago (1,2,3)
	 * @return
	 */
	@RequestMapping(value="/obtenerSumarizado" , method=RequestMethod.POST)
	public CrtRevPagos obtenerSumarizado(String nuFolioCorreccion, Integer tipoPago,Integer cveRegulaPago) {
		
		CrtRevPagos model = new CrtRevPagos();
		try {
			model = pagosService.getSumarizado(nuFolioCorreccion,tipoPago,cveRegulaPago);
		} catch (SQLException e) {
			model.setError(e.getCause().getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			model.setError(e.getCause().getMessage());
			e.printStackTrace();
		}
		return model;
		
	}
	
	/**
	 * Permite obtener los pagos realizados agrupados por registro patronal.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/listarPagosRealizadosPorRP" , method=RequestMethod.POST)
	public @ResponseBody List<CrtRevPagos> listarPagosRealizadosPorRP(@RequestBody CrtRevPagos model,HttpServletRequest request) {
	
		List<CrtRevPagos> lsPagosRealizados;
		
		try {
			
			lsPagosRealizados = ProcesosPagos.obtenerListaPagosPorRP(model.getCvePresentacorr(), model.getIndTipopago());
			
		} catch (SQLException e) {
			lsPagosRealizados = new ArrayList<CrtRevPagos>();
			e.printStackTrace();
		} catch (Exception e) {
			lsPagosRealizados = new ArrayList<CrtRevPagos>();
			e.printStackTrace();
		}
		
		
		return lsPagosRealizados;
		
	}
	

}
