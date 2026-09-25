package mx.gob.imss.ctirss.delta.riesgosTrabajo.web.usuario.controller;

import mx.gob.imss.ctirss.delta.riesgosTrabajo.web.util.ConstantesUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Scope(ConstantesUtil.DEFAULT_SCOPE_CONTROLLERS)
@RequestMapping(value = ConstantesUtil.HOME)
public class Usuario {

    @RequestMapping(value = ConstantesUtil.CERRAR_SESION, method = RequestMethod.GET)
    public RedirectView cerrarSesion(HttpSession session, HttpServletRequest request) {
        //Removemos los datos de la seccion
        session.removeAttribute(ConstantesUtil.USUARIO);
        session.removeAttribute(ConstantesUtil.PATRON);
        session.removeAttribute(ConstantesUtil.PERIODO_CONSULTA);
        session.removeAttribute(ConstantesUtil.FECHA_TRAMITE);
        session.removeAttribute(ConstantesUtil.RIESGOS_TRABAJO);
        session.removeAttribute(ConstantesUtil.REPORTE_OBTENIDO);
        session.removeAttribute(ConstantesUtil.FECHA_SINIESTRALIDAD);

        session.invalidate();
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(request.getSession().getServletContext().getInitParameter("STATIC_LOGOUT_PATH"));

        return redirectView;
    }


}
