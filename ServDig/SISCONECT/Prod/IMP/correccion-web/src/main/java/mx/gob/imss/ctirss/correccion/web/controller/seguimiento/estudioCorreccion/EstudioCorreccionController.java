/**
 * 
 */
package mx.gob.imss.ctirss.correccion.web.controller.seguimiento.estudioCorreccion;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.commons.paginador.seguimiento.estudioCorreccion.TareasWrapperDataTable;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.CedulaValidacionVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.CorreccionSeguimientoGenericoVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.DerivacionFiscalSeguimientoCorrVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.EstudioSolicitudCorreccionVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.RecepcionSeguimientoVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.ResumenSeguimientoVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.RevOficiosVO;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.presentacion.AbstractPresentacionQuerys;
import mx.gob.imss.ctirss.correccion.presentacion.service.interfaces.PresentacionCorreccionServiceController;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.session.UserSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 
 * Controlador para gestionar las actividades del seguimiento de estudio de correccion
 * @author CesarAgustin
 * @version 1.0.0
 *
 */
@Controller
@RequestMapping("/seguimiento/estudioCorreccion")
public class EstudioCorreccionController extends AbstractController {
	
	final static Logger LOGGER = Logger.getLogger(EstudioCorreccionController.class);
	
	@Autowired
	private PresentacionCorreccionServiceController presentacionService;
	
	
	@Autowired
	private ICatalogoService<AbstractModel> consultaGenerica;
	
	
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model, HttpServletRequest request) {
		model.addAttribute(new EstudioSolicitudCorreccionVO());
		model.addAttribute("correccionSeguimientoGenericoVO", new CorreccionSeguimientoGenericoVO());
		model.addAttribute("recepcionSeguimientoVO", new RecepcionSeguimientoVO());
		model.addAttribute("revOficiosVO", new RevOficiosVO());
		model.addAttribute("derivDictamenTabVO", new RevOficiosVO());
		model.addAttribute("derivFiscalTabVO", new DerivacionFiscalSeguimientoCorrVO());
		model.addAttribute("reactivacionTabVO", new DerivacionFiscalSeguimientoCorrVO());
		model.addAttribute("cedulaValidacionVO", new CedulaValidacionVO());
		
		
	    return "seguimiento/estudioCorreccion/listaTareasMain";
	}
	
	@RequestMapping(value="/recuperaEjercicios", method=RequestMethod.POST)
	public @ResponseBody List<AbstractModel> recuperaEjercicios(@RequestBody String aoData,
			HttpServletResponse response, HttpServletRequest request)  {
		
		StringBuilder query=new StringBuilder();
		query.append("SELECT distinct trab.cveEjercicio FROM CrtSolicitudcorr sol,CrtAnexosolcorrpat anexo,CrtEjertrabajador trab ");
		query.append(" where sol.cveSolicitudCorr=anexo.cveSolicitudCorr ");
		query.append(" and trab.cveAcexoCorrPat=anexo.cveAnexoSolicitudCorrPat ");
		query.append(" and sol.nuFolio='"+aoData.replace("\"","")+"'");
		List<AbstractModel> ejerccios=consultaGenerica.consultaLibrePorClave(0L, query.toString());
		System.out.println("Recuperando ejerciciosa "+ejerccios.size());
		return ejerccios;
	}
	
	
	@RequestMapping(value="/pagina", method=RequestMethod.POST)
	public @ResponseBody DatosSalidaPaginador<EstudioSolicitudCorreccionVO> consultaSolicitudesCorr(@RequestBody TareasWrapperDataTable aoData,
			HttpServletResponse response, HttpServletRequest request)  {
		
		LOGGER.info("/**** Controlador para consulta de estudios de solicitudes de corrección ****/");
		UserSession user = getUsuarioFirmado(request);
		aoData.getoForm().setIdSubDelegacion(user.getIdSubDelegacion());
		
		//aoData.getoForm().setCveEstatus((Integer)2);
		
		List<EstudioSolicitudCorreccionVO> listRetorno = presentacionService.consultaSolicitudesCorr(aoData.getoForm());
		
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		
		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());
		
		DatosSalidaPaginador<EstudioSolicitudCorreccionVO> reply = new DatosSalidaPaginador<EstudioSolicitudCorreccionVO>();
		
		reply.setAaData(listRetorno);
		reply.setiTotalRecords(listRetorno.size());
		reply.setiTotalDisplayRecords(listRetorno.size());
		
		reply.setsEcho(send.getsEcho());
		
		return reply;
	}
	
	@RequestMapping(value="/obtenerFechaServidor.do", method=RequestMethod.POST)
	public @ResponseBody String obtenerFechaServidor(HttpServletRequest request){
		return ConstantesBusiness.dateToStringFormat(new Date(), ConstantesBusiness.dd_mm_yyyy);
	}

	@RequestMapping(value="/seguimiento", method=RequestMethod.POST)
	public @ResponseBody RecepcionSeguimientoVO segumientoEstudioCorreccion(@RequestBody EstudioSolicitudCorreccionVO clase,
			HttpServletResponse response) {
		
		LOGGER.info("/**** Controlador para consulta el detalle de la solicitud de corrección con id ::  ****/"+clase.getIdSolicitud());
		
		RecepcionSeguimientoVO recepcionSeguimientoVO = presentacionService.detalleSolicitudCorreccion(clase.getIdSolicitud());
		
		return recepcionSeguimientoVO;
	}
	
	/*@RequestMapping(value="/guardarSeguimiento", method=RequestMethod.POST)
	public @ResponseBody RecepcionSeguimientoVO guardaSeguimiento (@RequestBody RecepcionSeguimientoVO clase, HttpServletResponse response,
			HttpServletRequest request) {
		
		LOGGER.info("/**** Guardar seguimiento ::  ****"+clase.getPago()+" : "+clase.getAfilia()+" : "+clase.getDocto()+" : "+clase.getObservaciones());
		
		UserSession user = getUsuarioFirmado(request);
		
		clase.setCveUsuario(user.getCveIdUsuario().toString());
		clase.setFechaRegistro(Functions.stringToDate(ConstantesBusiness.dateToStringFormat(new Date(), ConstantesBusiness.dd_mm_yyyy)));
		
		Boolean resultado =  presentacionService.guardarRecepcionSeguimiento(clase);
		
		return new RecepcionSeguimientoVO();
	}*/
	
	@RequestMapping(value="/seguimientoCorreccionMain", method=RequestMethod.POST)
	public @ResponseBody CorreccionSeguimientoGenericoVO seguimientoCorreccionMain(@RequestBody CorreccionSeguimientoGenericoVO clase, Model model, HttpServletResponse response , HttpServletRequest request) {
		
		CorreccionSeguimientoGenericoVO salidaVO = presentacionService.llenaCorreccionMain(clase.getCveSolCorr());
		UserSession user = getUsuarioFirmado(request);
		salidaVO.setNombreFuncionario(user.getNombreCompleto());
		salidaVO.setUser(user);
		
		return salidaVO;
	}
	
	/**
	 * Metodo que da la consulta del seguimiendo de correcion de un folio, presenta el resumen correspondiente a la consulta
	 * 
	 * @author Jorge Hernandez Almazan
	 * @param clase 
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/seguimientoCorreccionResumen", method=RequestMethod.POST)
	public @ResponseBody ResumenSeguimientoVO seguimientoCorreccionResumen(@RequestBody CorreccionSeguimientoGenericoVO clase,HttpServletResponse response, HttpServletRequest request) {
		LOGGER.info("/**** Registro patronal para el resumena ::  ****/"+clase.getRegPatronal()+" con folio "+clase.getCveSolCorr());
		
		String SQL = AbstractPresentacionQuerys.PAGO_COPS_RPS_INSCRITOS.replace("{1}",clase.getCveSolCorr().toString());	
		List<?> rpsInscritos = consultaGenerica.consultaSQL(SQL);		
		ResumenSeguimientoVO resumenSeguimientoVO=null;		
		resumenSeguimientoVO = presentacionService.llenaResumenSeguimiento(
				presentacionService.buscarFolioDeCorreccion(clase
						.getCveSolCorr()), clase.getRegPatronal(), clase
							.getCveSolCorr(), rpsInscritos);			
		UserSession user = getUsuarioFirmado(request);
		resumenSeguimientoVO.setUser(user);
		return resumenSeguimientoVO;
	}
	
	
	
}
