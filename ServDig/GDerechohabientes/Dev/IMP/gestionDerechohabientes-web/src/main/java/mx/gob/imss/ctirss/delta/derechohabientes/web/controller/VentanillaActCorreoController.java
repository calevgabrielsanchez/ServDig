/**
 * WelcomeController.java
 * @package mx.gob.imss.ctirss.delta.derechohabientes.web.controller
 * @project gestionDerechohabientes-web	
 */
package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.Date;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.Busqueda;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.Login;
import mx.gob.imss.ctirss.delta.derechohabientes.web.utils.OpcionesProperties;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.ActualizaCorreo;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos.ServiciosDTO;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.PerfilesEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.FileUploadVB;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.util.Constants;
import mx.gob.imss.ctirss.delta.model.utility.bean.GrupoFamiliarDataTable;
import mx.gob.imss.ctirss.delta.model.utility.bean.ActualizaCorreoDataTable;
import mx.gob.imss.ctirss.delta.model.asegurado.ActualizaCorreoIn;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Juan Manuel Marquez
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 10/04/2011
 */
@Controller
@RequestMapping(value = "/inicioVentanilla")
public class VentanillaActCorreoController extends AbstractController {
	private static final Logger logger = Logger.getLogger(VentanillaActCorreoController.class);
	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarService;
	@Autowired
	private OpcionesProperties opcionesProperties;
	
	
	@RequestMapping(value = "/listar/ActualizaCorreo", method = RequestMethod.POST)
    public @ResponseBody DatosSalidaPaginador<ActualizaCorreo> recuperaDatosActCorreo(@RequestBody ActualizaCorreoDataTable aoData, 
    		SessionStatus status, HttpSession session, HttpServletRequest request){
		
		Usuario usuario = (Usuario) session.getAttribute("usuarioreporte");
		log.debug("el usuario en ventanilla administrativa es: " + usuario);
		DatosEntradaPaginador<ActualizaCorreoIn> envio = new DatosEntradaPaginador<ActualizaCorreoIn>();
		ActualizaCorreoIn datosEnvio = new ActualizaCorreoIn();
		
		datosEnvio.setNss("");
		datosEnvio.setFecha(null);
		datosEnvio.setEstado("");
		datosEnvio.setFolio("");
		datosEnvio.setIdSubdelegacion(String.valueOf(usuario.getUsuarioFuncionario().getSubdelegacion().getId()));
		
		envio.setModelo(datosEnvio);
        envio.parserArray(aoData.getAoData());
        
        DatosSalidaPaginador<ActualizaCorreo> salida = null;
		try {
			salida = grupoFamiliarService.paginarActualizaCorreo(envio);		
		} catch (DerechohabientesBusinessException e) {
			logger.debug(e.getMessage());
			request.setAttribute("errores", "exception.RNGD0073");
		}catch (Exception e) {
			logger.debug(e.getMessage());
			request.setAttribute("exception", "exception.RNGD0073");
			
		}
        salida.setsEcho(envio.getsEcho());
        
        System.out.println("VALOR en el controller...."  + salida.getAaData().get(0).toString());
        log.info("Valor de saloda del controller....."+ salida.getAaData().get(0).toString() );
        
        return  salida;	
		
	}
	
	@RequestMapping(value = "/listar/ActualizaCorreoFiltros", method = RequestMethod.POST)
    public @ResponseBody DatosSalidaPaginador<ActualizaCorreo> recuperaDatosActCorreoPorFiltro(@RequestBody ActualizaCorreoDataTable aoData, 
    		SessionStatus status, HttpSession session, HttpServletRequest request){
		
		Usuario usuario = (Usuario) session.getAttribute("usuarioreporte");
		log.debug("el usuario en ventanilla administrativa es: " + usuario);
		
		DatosEntradaPaginador<ActualizaCorreoIn> envio = new DatosEntradaPaginador<ActualizaCorreoIn>();
		ActualizaCorreoIn datosEnvio = new ActualizaCorreoIn();
		
		log.debug("los datos enviados desde el front son: " + aoData.getoForm());
		log.debug("los datos enviados desde el front son: " + aoData.getoForm().folio);
		
		datosEnvio.setFolio(aoData.getoForm().folio);
		try{
			datosEnvio.setFecha(new SimpleDateFormat("dd/MM/yyyy").parse(aoData.getoForm().fechaRegistroAlta));
		}catch(Exception ex){
			datosEnvio.setFecha(null);
		}
		
		datosEnvio.setNss(aoData.getoForm().nss);
		datosEnvio.setEstado(aoData.getoForm().idEstado);
		datosEnvio.setIdSubdelegacion(String.valueOf(usuario.getUsuarioFuncionario().getSubdelegacion().getId()));
		
		
		log.debug("el objeto datosEnvio es: " + datosEnvio);
		
		envio.setModelo(datosEnvio);
        envio.parserArray(aoData.getAoData());
        
        log.debug("el objeto envío es: " + envio);
        
        DatosSalidaPaginador<ActualizaCorreo> salida = new DatosSalidaPaginador<ActualizaCorreo>();
		try {
			salida = grupoFamiliarService.paginarActualizaCorreo(envio);		
		} catch (DerechohabientesBusinessException e) {
			logger.debug(e.getMessage());
			request.setAttribute("errores", "exception.RNGD0073");
		}catch (Exception e) {
			logger.debug(e.getMessage());
			request.setAttribute("exception", "exception.RNGD0073");
			
		}
        salida.setsEcho(envio.getsEcho());
        
        System.out.println("VALOR en el controller...."  + salida.getAaData().get(0).toString());
        log.info("Valor de saloda del controller....."+ salida.getAaData().get(0).toString() );
        
        return  salida;	
		
	}
	
	
	

}
