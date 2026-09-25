package mx.gob.imss.ctirss.delta.riesgosTrabajo.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.riesgosTrabajo.RiesgosTrabajoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.TipoDescargaArchivo;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.PatronRiesgosTrabajo;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.RiesgoTrabajo;
import mx.gob.imss.distss.delta.rtt.service.interfaces.ConsultalRiesgoTrabajoServiceRemote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping(value = "/historialRiesgoTrabajo/")
public class ConsultarHistorialRiesgoTrabajoController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsultarHistorialRiesgoTrabajoController.class); 

    @Autowired
    private ConsultalRiesgoTrabajoServiceRemote consultaHistRTTService;

    private static final String HISTORIAL_RIESGO_TRABAJO_INICIO = "historialRiesgoTrabajo";
    private static final String HISTORIAL_RIESGO_TRABAJO_NOMBRES = "historialRTTNombres";
    private static final String HISTORIAL_RIESGO_TRABAJO_CONSULTA = "consultaHistorialRiesgosTrabajo";
    private static final String RIESGO_TRABAJO_ERROR = "errorHistorialRiesgos";

    /**
     * Verifica si usuario puede consultar los riegos trabajo
     *
     * @param response
     * @param request
     * @param sessionStatus
     * @param session
     * @return
     */
    @RequestMapping(value = "/buscarRiesgosTrabajo", method = {RequestMethod.GET, RequestMethod.POST})
    public String validarUsuario(HttpServletResponse response, HttpServletRequest request, SessionStatus sessionStatus, HttpSession session) {

        LOGGER.debug("Validando perfiles");
        
        String usuarioNetIq = request.getHeader("SSO_UID");
        Integer delegacion = request.getHeader("SSO_DELEGACION") != null ? Integer.parseInt(request.getHeader("SSO_DELEGACION")) : null ;
        Integer subdelegacion = request.getHeader("SSO_SUBDELEGACION") != null ? Integer.parseInt(request.getHeader("SSO_SUBDELEGACION")) : null ;
        String perfil = request.getHeader("SSO_PERFIL");
        
        
        LOGGER.debug("[NETIQ] SSO_UID " + usuarioNetIq);
        LOGGER.debug("[NETIQ] SSO_DELEGACION " + delegacion);
        LOGGER.debug("[NETIQ] SSO_SUBDELEGACION " + subdelegacion);
        LOGGER.debug("[NETIQ] SSO_PERFIL " + perfil);
        
        //Se recupera los datos del funcionario
        UsuarioSSO usuario = new UsuarioSSO();//procesarUsuarioSSO(request);
        usuario.setNombre(usuarioNetIq);
        usuario.setDelegacion(delegacion);
        usuario.setSubdelegacion(subdelegacion);
        
        LOGGER.debug("El usuario []", usuario);
        session.setAttribute("funcionario", usuario);
        session.setAttribute("usuario", convertUsuarioSSO(usuario));
        return HISTORIAL_RIESGO_TRABAJO_INICIO;
    }
    
    private Usuario convertUsuarioSSO(UsuarioSSO usuarioSSO) {
    	Usuario usuario = null;
    	Map<Long, String> perfilPantalla = new HashMap<Long, String>();
    	perfilPantalla.put(1L, "CENTRAL");
    	perfilPantalla.put(2L, "DELEGACI&Oacute;N");
    	perfilPantalla.put(3L, "SUBDELEGACI&Oacute;N");
    	
    	
    	if(usuarioSSO != null){
    		usuario = new Usuario();
	    	usuario.setUsuario(usuarioSSO.getNombre());
	    	usuario.setPerfilUsuario(new PerfilUsuario());
	    	Long idPerfilUsuario = null;
	    	Integer idDelegacion = usuarioSSO.getDelegacion();
	    	Integer idSubdelegacion = usuarioSSO.getSubdelegacion();
	    	
	    	//si el usuario no trae ni subdelegacion ni delegacion, es normativo
	    	if(idDelegacion == null && idSubdelegacion == null) {
	    		idPerfilUsuario = 1L;
	    	} else if(idDelegacion != null && idSubdelegacion == null) {
	    		idPerfilUsuario = 2L;
	    	} else {
	    		idPerfilUsuario = 3L;
	    	}
	    	
	    	usuario.getPerfilUsuario().setIdPerfilUsuario(idPerfilUsuario);
			usuario.getPerfilUsuario().setDescripcion(perfilPantalla.get(idPerfilUsuario));
    		
    		if(usuario.getPerfilUsuario().getIdPerfilUsuario() != null) {
    			long idPerfil = usuario.getPerfilUsuario().getIdPerfilUsuario();
    			UsuarioFuncionario usuarioF = new UsuarioFuncionario();
    			if(idPerfil == 2) {
    				Delegacion delegacion = consultaHistRTTService.getDelegacionUsuario(usuarioSSO.getDelegacion().longValue());
    				usuarioF.setDelegacion(delegacion);
    			} else if(idPerfil == 3) {
    				Subdelegacion subdelegacion = consultaHistRTTService.getSubdelegacionUsuario(usuarioSSO.getSubdelegacion().longValue());
    				usuarioF.setSubdelegacion(subdelegacion);
    				usuarioF.setDelegacion(subdelegacion.getDelegacion());
    			}
    			usuario.setUsuarioFuncionario(usuarioF);
    			
    		}
    	}
    	
    	return usuario;
    }

    /**
     * Busca los riesgo de trabajo y los muestra en pantalla
     *
     * @param model
     * @param sessionStatus
     * @param session
     * @param rp
     * @return
     */
    @RequestMapping(value = "/consultar/{rp}/{periodo}", method = {RequestMethod.GET, RequestMethod.POST})
    public String obtenerRiesgosTrabajo(Model model, SessionStatus sessionStatus, HttpSession session, @PathVariable String rp, 
    		@PathVariable Integer periodo, @RequestParam(required=false) Long delegacion,  @RequestParam(required=false) Long subdelegacion) {

        LOGGER.debug("Entrando a consultar historial");

        //Limpiamos la sesion
        limpiarDatos(model, session);

        String view = HISTORIAL_RIESGO_TRABAJO_CONSULTA;

        try {

            //Obtenemos los datos del patron
        	PatronRiesgosTrabajo patron = null;
        	if(!rp.equals("sinNRP")){
        		patron = consultaHistRTTService.buscarPatron(rp,periodo,delegacion,subdelegacion, OrigenSolicitudEnum.VENTANILLA);
        	}
        	
        	LOGGER.debug("El periodo de busqueda es de " + patron.getInicioPeriodo() + " a " + patron.getFinPeriodo());
            //obtenemos los riesgos de trabajo 
            List<RiesgoTrabajo> riesgosTrabajo = consultaHistRTTService.obtenerRiesgosTrabajoPatronalesPeriodo(patron,OrigenSolicitudEnum.VENTANILLA);

            //Guardamos los datos del patron en sesion
            session.setAttribute("patron", patron);
            LOGGER.debug("Los datos del patron son " + patron);
            
           session.setAttribute("riesgosTrabajo", riesgosTrabajo);

        } catch (RiesgosTrabajoException ex) {
            LOGGER.debug(ex.getMessage());
            //mandamos la pantalla de que no tiene riesgos de trabajo
            view = RIESGO_TRABAJO_ERROR;
        }

        return view;
    }
    
    @RequestMapping(value = "/obtenerRFCs/{rfc}", method = {RequestMethod.GET, RequestMethod.POST})
    public String obtenerRFCs(Model model, SessionStatus sessionStatus, HttpSession session, @PathVariable String rfc, 
    		@RequestParam(required=false) Long delegacion,  @RequestParam(required=false) Long subdelegacion, @RequestParam(required=false) Integer periodo) {

        LOGGER.debug("Entrando a obtenerRFCs");

        //Limpiamos la sesion
        limpiarDatos(model, session);

        String view = HISTORIAL_RIESGO_TRABAJO_NOMBRES;

        try {
            //Obtenemos los datos del patron
        	List<Object[]> mapPatrones = null;
        	if(!rfc.equals("sinRFC")){
        		mapPatrones = consultaHistRTTService.buscarPatronPorRFC(rfc.toUpperCase(), delegacion, subdelegacion, periodo);
        	}
            model.addAttribute("patrones", mapPatrones);
        } catch (RiesgosTrabajoException ex) {
            LOGGER.debug(ex.getMessage());
            //mandamos la pantalla de que no tiene riesgos de trabajo
            view = RIESGO_TRABAJO_ERROR;
        }

        return view;
    }
    
    @RequestMapping(value = "/obtenerNombres/{nombre}/{tipo}", method = {RequestMethod.GET, RequestMethod.POST})
    public String obtenerNombres(Model model, SessionStatus sessionStatus, HttpSession session, @PathVariable String nombre,@PathVariable String tipo, 
    		@RequestParam(required=false) Long delegacion,  @RequestParam(required=false) Long subdelegacion, @RequestParam(required=false) Integer periodo) {

        LOGGER.debug("Entrando a consultar historial");

        //Limpiamos la sesion
        limpiarDatos(model, session);

        String view = HISTORIAL_RIESGO_TRABAJO_NOMBRES;

        try {
            //Obtenemos los datos del patron
        	List<Object[]> mapPatrones = null;
        	if(!nombre.equals("sinNombreORazonSocial")){
        		mapPatrones = consultaHistRTTService.buscarPatronPorNombre(nombre.toUpperCase(), OrigenSolicitudEnum.VENTANILLA, tipo, delegacion, subdelegacion, periodo);
        	}
        	LOGGER.debug("");
            model.addAttribute("patrones", mapPatrones);
        } catch (RiesgosTrabajoException ex) {
            LOGGER.debug(ex.getMessage());
            //mandamos la pantalla de que no tiene riesgos de trabajo
            view = RIESGO_TRABAJO_ERROR;
        }

        return view;
    }

    /**
     * Genera excel
     *
     * @param response
     * @param request
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/generarExcel", method = {RequestMethod.POST, RequestMethod.GET})
    public String generarExcelRiesgosTrabajo(HttpServletResponse response, HttpServletRequest request, Model model, HttpSession session) {

        //Obtenemos los datos del patron de la session
        PatronRiesgosTrabajo patron = (PatronRiesgosTrabajo) session.getAttribute("patron");

        try {
            //Generamos el excel
            byte[] reporteXLS = consultaHistRTTService.generarDocumentoRiesgosTrabajo(patron, TipoDescargaArchivo.XLS,OrigenSolicitudEnum.VENTANILLA);

            //Si el excel se genero lo mandamos a la vista
            LOGGER.debug("Se genero el reporte");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=riesgos_trabajo_" + patron.getNrp() + ".xls");
            response.setContentLength(reporteXLS.length);
            OutputStream ouputStream = response.getOutputStream();
            ouputStream.write(reporteXLS, 0, reporteXLS.length);
            ouputStream.flush();
            ouputStream.close();

        } catch (RiesgosTrabajoException e) {
            PrintWriter out;
            try {
                LOGGER.debug("Error al generar xls {}", e);
                //Si el excel no se genero mandamos un mensaje
                response.reset();
                response.setHeader("Expires", "0");
                response.setHeader("Cache-Control", "no-cache");
                response.setContentType("text/html; charset=UTF-8");
                out = response.getWriter();
                out.println("<link type=\"text/css\" href=\"/delta/resources/estilos/bootstrap/bootstrap.min.css\" rel=\"stylesheet\" />");
                out.println("<html><body><div style=\"text-align: center;\" class=\"alert alert-danger\"><h4>" + e.getMessage()
                        + "</h4></div></body></html>");
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (IOException ex) {
                LOGGER.debug("Error al generar xls {}", e);
            }
        } catch (IOException e) {
            LOGGER.debug("Error al generar xls {}", e);
        }
        return null;

    }

    /**
     * Metodo para limpiar session
     *
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/comunes/limpiarDatos")
    public @ResponseBody
    Map<String, ? extends Object> limpiarDatos(Model model, HttpSession session) {

        //Removemos los datos de la seccion
        session.removeAttribute("patron");
        session.removeAttribute("periodoConsulta");
        session.removeAttribute("riesgosTrabajo");

        LOGGER.debug("Se han limpiado los datos de sesion");

        return null;

    }
    
    @RequestMapping(value = "/comunes/getPeriodos", method = RequestMethod.POST)
    public @ResponseBody Map<String,  ? extends  Object> getPeriodos() {
    	Map<String, Object> periodos = new HashMap<String, Object>();
    	Calendar fecha = Calendar.getInstance();
    	fecha.setTime(new Date());
    	int anioLiberacion = 2016;
    	int actual = fecha.get(Calendar.YEAR);
    	
    	
    	List<Integer> anios = new ArrayList<Integer>();
    	anios.add(actual);
    	
    	for(int i=1; i<6; i++) {
    		int periodoAnterior = actual-i;
    		if(periodoAnterior >= anioLiberacion) {
    			anios.add(periodoAnterior);
    			continue;
    		} 
    		break;
    	}
    	
    	periodos.put("actual", actual);
    	periodos.put("periodos", anios);
    	
    	return periodos;
    }
    
    @RequestMapping(value = "/comunes/getDelegaciones", method = RequestMethod.POST)
    public @ResponseBody Map<String,  ? extends  Object> getDelegaciones() {
    	Map<String, Object> periodos = new HashMap<String, Object>();
    	List<Delegacion> delegaciones = consultaHistRTTService.findDelegacionesActivas();
    	periodos.put("delegaciones", delegaciones);
    	
    	return periodos;
    }
    
    @RequestMapping(value = "/comunes/getSubdelegaciones/{idDelegacion}", method = RequestMethod.POST)
    public @ResponseBody Map<String,  ? extends  Object> getSubDelegaciones(@PathVariable Long idDelegacion) {
    	Map<String, Object> periodos = new HashMap<String, Object>();
    	List<Subdelegacion> subdelegaciones = consultaHistRTTService.findSubDelegacionesActivas(idDelegacion.longValue());
    	periodos.put("subdelegaciones", subdelegaciones);
    	
    	return periodos;
    }

}
