package mx.gob.imss.cit.cda.web.loggin.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote;
import mx.gob.imss.cit.cda.web.app.common.model.UserProfile;
import mx.gob.imss.cit.cda.web.constants.RolUsuarioEnum;
import mx.gob.imss.cit.cda.web.constants.SessionConstants;
import mx.gob.imss.cit.cda.web.utils.RolLoginUtil;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.ClienteWebserviceResponsablesSubdelegacionException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = "/login")
public class LoginCDAController extends AbstractController {

    @Autowired
    private PersonaBusinessRemote personaBusiness;

    @Autowired
    private DomicilioServiceBusinessRemote domicilioService;


    @Autowired
    @Qualifier("responsablesDelegacionBusiness")
    private ResponsablesDelegacionRemote responsablesDelegacionBusiness;

    @Autowired
    private RolLoginUtil rolLoginUtil;

    @Autowired
    private SessionController sessionController;
    
    private final Logger LOG = LoggerFactory.getLogger(LoginCDAController.class);
    
    private static final String CURP = "curp";

    @RequestMapping(value = "")
    public Object identificarFuncionario(HttpServletRequest request,
            HttpSession session) {

        LOG.info("antes del obtener datos del openAM");
        UsuarioSSO usuario = procesarUsuarioSSO(request);
        
        
        
        UserProfile userProfile = new UserProfile();

        Usuario funcionario = null;
        try {
            LOG.info("antes del web service de datos del funcionario" + usuario.getCurp());
            funcionario = ((ResponsablesDelegacionRemote) responsablesDelegacionBusiness)
                    .recuperaUsuarioEsquemaSeguridadByCURP(usuario.getCurp());
            LOG.info("se obtuvieron datos del web service de datos del funcionario");
        } catch (ClienteWebserviceResponsablesSubdelegacionException e) {
            LOG.error(
                    "ClienteWebserviceResponsablesSubdelegacionException {} ",
                    e);
        }
        
        LOG.info("Se obtuvieron los datos del funcionario");        
        if(usuario.getSubdelegacion() != null){
            LOG.info("   subdelegacion:" + usuario.getSubdelegacion());
            
            Subdelegacion subdelegacion = domicilioService
                .obtenerSubdelegacionPorId(usuario.getSubdelegacion()
                        .longValue());
            userProfile.setIdSubdelegacion(usuario.getSubdelegacion().longValue());
            userProfile.setSubdelegacion(subdelegacion.getDescripcion());
        }

        userProfile
                .setIdPersona(Long.valueOf(usuario.getIdPersona() != null ? usuario
                        .getIdPersona() : 0L));
        
        
        userProfile.setUsuario(usuario.getCurp());
        if(usuario.getDelegacion()!= null){
            LOG.info("   delegacion:" + usuario.getDelegacion());
            userProfile.setIdDelegacion(usuario.getDelegacion().longValue());
        }
        userProfile.setSistemas(usuario.getSistemas());
        
        LOG.info("se termino de llenr el userprofile" );
        if(usuario.getPerfiles() != null){
            List<String> listaPerfiles = new ArrayList<String>();
            userProfile.setPerfil(usuario.getPerfil());
            LOG.info("Valor obtenido de LDAP para userProfile.getPerfil() ->"+userProfile.getPerfil());
            for(String perfil : usuario.getPerfiles()){
                listaPerfiles.add(perfil);
                LOG.info("Perfil anadido a lista de perfiles de CDA ->"+perfil);
            }
            
            userProfile.setRoles(listaPerfiles);
            
            if(userProfile.getPerfil() == null){
                
                if(!listaPerfiles.isEmpty() ){
                    String perfil =  obtenerPerfilMayor(usuario.getPerfiles() ); 
                    userProfile.setPerfil(perfil);
                }                       
                
                LOG.info("Se coloca primer perfil a getPerfilDescripcion de userProfile ->"+userProfile.getPerfilDescripcion());
            }
            userProfile.setPerfilDescripcion(userProfile.getPerfil());
        }
        
        if(funcionario.getFisica()!=null){
            userProfile.setNombreCompleto(funcionario.getFisica()
                    .getNombreCompleto());

            log.debug("---CDA--- Nombre Persona Completo: "
                    + funcionario.getFisica().getNombreCompleto());
        }
       
        log.debug("---CDA--- perfiles: "+Arrays.toString(usuario.getPerfiles()));
      
       

        RolUsuarioEnum rol = RolUsuarioEnum.fromRol(usuario.getPerfiles());
        if (rol != null) {
            userProfile.setPerfil(rol.getRol());
            userProfile.setPerfilDescripcion(rol.getDescripcion());
            log.debug("---CDA--- REDIRECCION A " + rol.getDescripcion());
            sessionController.validarSesionUsuario(session, userProfile);
            session.setAttribute(SessionConstants.USER_PROFILE, userProfile);
            log.debug("---CDA--- Usuario :" + userProfile.getUsuario()
                    + "en sesion: " + session.getId());
            String url = "/"
                    + request.getSession().getServletContext()
                            .getInitParameter("webAppRootKey") + "/"
                    + rol.getDefaultUrl();
            log.debug("url:" + url);
            
            return new RedirectView(url);
        } else {
            return logoutVentanilla(session, request);
        }

    }

    @RequestMapping(value = "loginResponsable")
    public Object loginResponsable(HttpServletRequest request,
            HttpSession session) {
        UserProfile userProfile = new UserProfile();
        log.debug("__CDA__ USUARIO RESPONSABLE >> "
                + request.getParameter(CURP));
        userProfile.setSubdelegacion("Jefe departamento afiliaci\u00f3n");
        userProfile.setIdSubdelegacion(21L);
        userProfile.setPerfil(RolUsuarioEnum.VENTANILLA.getRol());
        userProfile.setPerfilDescripcion(RolUsuarioEnum.VENTANILLA
                .getDescripcion());
        userProfile.setSistemas(new String[] { "IMSS_DIGITAL" });
        Subdelegacion subdelegacion = domicilioService
                .obtenerSubdelegacionPorId(userProfile.getIdSubdelegacion());
        userProfile.setIdDelegacion(subdelegacion.getDelegacion().getId());
        obtenerDatosUsuario(request.getParameter(CURP), userProfile);
        session.setAttribute(SessionConstants.USER_PROFILE, userProfile);
        return new RedirectView("/"
                + request.getSession().getServletContext()
                        .getInitParameter("webAppRootKey")
                + "/atencionResponsable");
    }

    @RequestMapping(value = "loginAutorizador")
    public Object loginAutorizador(HttpServletRequest request,
            HttpSession session) {
        UserProfile userProfile = new UserProfile();
        log.debug("__CDA__ USUARIO AUTORIZADOR >> "
                + request.getParameter(CURP));
        userProfile.setSubdelegacion("Subdelegacion");
        userProfile.setIdSubdelegacion(21L);
        userProfile.setPerfil(RolUsuarioEnum.AUTORIZADOR_OCE.getRol());
        userProfile.setPerfilDescripcion(RolUsuarioEnum.AUTORIZADOR_OCE
                .getDescripcion());
        userProfile.setSistemas(new String[] { "IMSS_DIGITAL" });
        Subdelegacion subdelegacion = domicilioService
                .obtenerSubdelegacionPorId(userProfile.getIdSubdelegacion());
        userProfile.setIdDelegacion(subdelegacion.getDelegacion().getId());
        obtenerDatosUsuario(request.getParameter(CURP), userProfile);
        session.setAttribute(SessionConstants.USER_PROFILE, userProfile);
        return new RedirectView("/"
                + request.getSession().getServletContext()
                        .getInitParameter("webAppRootKey")
                + "/atencionAutorizador");
    }

    private UserProfile obtenerDatosUsuario(String curp, UserProfile userProfile) {
        userProfile.setUsuario(curp);

        List<Fisica> personas = personaBusiness
                .buscarPersonaFisicaPorCurpEnImss(curp);
        if (!personas.isEmpty()) {
            userProfile.setIdPersona(personas.get(0).getIdPersona());
            log.debug("NombrePersonaCompletoUser"
                    + personas.get(0).getNombreCompleto());
            userProfile.setNombreCompleto(personas.get(0).getNombreCompleto());
        } else {
            userProfile.setIdPersona(0L);
            userProfile.setNombreCompleto("");
        }
        return userProfile;
    }
    
    private String obtenerPerfilMayor(String[] perfiles){
        
        RolUsuarioEnum rolFinal = null;
        RolUsuarioEnum rolTemporal = null;
        
        for (String perfilSSO : perfiles) {
            
            if(perfilSSO.equals(RolUsuarioEnum.TIT_COORD_AFILIACION.getRol()) ||  
                    perfilSSO.equals(RolUsuarioEnum.TIT_AFILIACION_OBLIGATORIO.getRol()) || 
                    perfilSSO.equals(RolUsuarioEnum.TIT_INSCRIP_ASEG.getRol()) ||
                    perfilSSO.equals(RolUsuarioEnum.TIT_SOPORTE_AFILIACION.getRol()) ||
                    perfilSSO.equals(RolUsuarioEnum.JEFE_COORD_AFILIACION.getRol()) ||
                    perfilSSO.equals(RolUsuarioEnum.ANALISTA_COORD_AFLICIACION.getRol()) ||
                    perfilSSO.equals(RolUsuarioEnum.JAC.getRol()) ||  
                    perfilSSO.equals(RolUsuarioEnum.SUPERVISOR.getRol()) ||
                    perfilSSO.equals(RolUsuarioEnum.AUTORIZADOR_OCE.getRol()) ||
                    perfilSSO.equals(RolUsuarioEnum.AUTORIZADOR_DAV.getRol()) ||
                    perfilSSO.equals(RolUsuarioEnum.AUTORIZADOR_DAV2.getRol()) ||
                    perfilSSO.equals(RolUsuarioEnum.SUBDELEGADO.getRol()) ||
                    perfilSSO.equals(RolUsuarioEnum.VENTANILLA.getRol()) 
                  ){
                
                    rolTemporal = RolUsuarioEnum.fromRol(perfilSSO);
                    
                    if(rolFinal == null || rolFinal.getNivel() > rolTemporal.getNivel()){
                        rolFinal = rolTemporal;
                    }
            }
        }
        
        
        return rolFinal.getRol();
    }

    private Object logoutVentanilla(HttpSession session,
            HttpServletRequest request) {
        if (session != null) {
            sessionController.limpiarSesion(session);
            UserProfile userProfile = (UserProfile) session
                    .getAttribute(SessionConstants.USER_PROFILE);
            if (userProfile != null) {
                log.debug("---CDA--- Eliminando usuario: "
                        + userProfile.getUsuario() + " de la sesion: "
                        + session.getId());
                session.removeAttribute(SessionConstants.USER_PROFILE);
            }
            session.invalidate();
        }
        if(request!=null)
        {
            log.debug("request no null");
        }
        log.debug("---CDA--- SESION VENTANILLA TERMINADA");
        return new RedirectView("/j_spring_security_check");
    }
    
   


}
