package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DerechosArcoServiceRemote;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.BloqueoDerechosArco;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("derechosArco/*")
public class DerechosArcoController extends AbstractController {

    @Autowired
    private DerechosArcoServiceRemote derechosArcoServiceRemote;

    @ResponseBody
    @RequestMapping(value = "generarSolicitud/{nss}/{isBloqueo}", method = RequestMethod.POST)
    public Object generarSolicitudDerechosArco(HttpSession session, HttpServletResponse response, HttpServletRequest request, @RequestParam("observaciones") String motivos,
            @PathVariable("nss") String nss, @PathVariable("isBloqueo") boolean isBloqueo) throws Exception {

        log.info("Entrando a generar solicitud de derechos arco isBloqueo: " + isBloqueo + "\nMotivos: " + motivos);
        Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
        try {
            byte[] contents;
            if (isBloqueo) {
                contents = derechosArcoServiceRemote.bloquearDerechosArco(nss, usuario, motivos);
            } else {
                contents = derechosArcoServiceRemote.desbloquearDerechosArco(nss, usuario, motivos);
            }
            response.addHeader("Accept-Ranges","bytes");
            response.addHeader("Cache-Control","public");
            response.addHeader("Cache-Control","must-revalidate");
            response.addHeader("Pragma","public");
            response.setContentType("application/pdf");
            response.addHeader("expires","0");
            response.addHeader("Content-disposition", "inline;filename=\"acuseDerechosArco" + nss + ".pdf\"");
            response.setContentLength(contents.length);
            response.getOutputStream().write(contents);
            response.flushBuffer();
        } catch (Exception e) {
            log.error("Error al " + (isBloqueo ? "bloquear" : "desbloquear" ) + " los derechos ARCO: " + e);
            request.setAttribute("error", "Error al procesar la solicitud de " + (isBloqueo ? "bloqueo" : "desbloqueo" ) + " de Derechos ARCO. Por favor reintente.");
            throw e;
        }

        return null;
    }

    @ResponseBody
    @RequestMapping(value = "validaNSS", method = RequestMethod.POST)
    public Object validarNSS(@RequestParam("nss") String nss) {

        log.info("Se valida el NSS: " + nss);
        BloqueoDerechosArco bloqueoDerechosArco = derechosArcoServiceRemote
                .consultaBloqueo(nss, TipoTramiteEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOHAB.getCodigo().longValue());

        if (bloqueoDerechosArco != null) {
            return bloqueoDerechosArco.isIndBloqueo();
        } else {
            return null;
        }
    }

}
