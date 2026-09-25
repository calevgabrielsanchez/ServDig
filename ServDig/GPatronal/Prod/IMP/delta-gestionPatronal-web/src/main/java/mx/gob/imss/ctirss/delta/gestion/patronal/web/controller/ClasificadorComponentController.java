package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value="/clasificadorComponent")
public class ClasificadorComponentController {

    private static final String VIEW_CLASIFICADOR_COMP = "clasificador/clasificadorComponent";

    @RequestMapping(value = "/init",method = {RequestMethod.POST, RequestMethod.GET})
    public String home(HttpServletRequest request, HttpSession session) {
        return VIEW_CLASIFICADOR_COMP;
    }
}
