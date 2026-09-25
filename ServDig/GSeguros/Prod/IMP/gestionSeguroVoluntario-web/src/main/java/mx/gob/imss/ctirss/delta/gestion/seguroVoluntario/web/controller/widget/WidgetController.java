/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.controller.widget;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.exception.IVROExceptionGenerico;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util.SeguroIvroUtil;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.servicios.SeguroIndividualServices;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.persona.TipoPersona;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controler para el widget de los segurs ivro
 * 
 * @author NOVUTECK1
 * 
 */
@Controller
@RequestMapping(value = "/widget")
public class WidgetController extends AbstractController {
    
    /** The Constant VIEW_WIDGET_IVRO_PERSONAL_BODY. */
    private static final String VIEW_WIDGET_IVRO_PERSONAL_BODY = "widgetPersonaIvroIndivContenido";
    /**
     * Inicio del widget
     */
    private static final String VIEW_WIDGET_IVRO_PERSONAL_START = "widgetPersonaIvroIndivInit";

    /**
     * Servicios para la consulta de seguors
     */
    @Autowired
    private SeguroIndividualServices seguroServices;
    
    /**
     * Metodo para pintar el widget de seguros individuales 
     * @param model
     * @param session
     * @param request
     * @param idPersona
     * @param rfcFisica
     * @return
     */
    @RequestMapping(value = "/ivro/individual/{idPersona}/{rfcFisica}", method = RequestMethod.GET)
    public String initPersonaIvroWidget(Model model, HttpSession session,
            HttpServletRequest request, @PathVariable Long idPersona, @PathVariable String rfcFisica) {
        
        Persona persona = new Persona();
        persona.setIdPersona(idPersona);

        TipoPersona tipoPersona = new TipoPersona();
        tipoPersona.setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
        persona.setTipoPersona(tipoPersona);
        persona.setRfc(rfcFisica);

        model.addAttribute("persona", persona);
        model.addAttribute("rfc", rfcFisica);

        return VIEW_WIDGET_IVRO_PERSONAL_START;
    }

    /**
     * muestra los seguros asociados a una persona e indica si los puede renovar o comprar nuevos
     * @param model
     * @param session
     * @param request
     * @param idPersona
     * @return
     */
    @RequestMapping(value = "/ivro/individual/seguros/{idPersona}", method = RequestMethod.GET)
    public String detallePersonaIvroWidget(Model model, HttpSession session,
            HttpServletRequest request, @PathVariable Long idPersona) {
        SegurosIvro segurosIvro = null;
        try {
        	segurosIvro = seguroServices.obtenSeguroIndividual(idPersona);
		} catch (IVROExceptionGenerico e) {
			log.error("Se presentó error en WS ivro: "+e.getMessage());
		}
        SeguroIvro[] seguros = segurosIvro != null && segurosIvro.getSeguroIvro() != null 
        	? segurosIvro.getSeguroIvro() : new SeguroIvro[]{};
        boolean comprar = SeguroIvroUtil.puedeComprarSeguro(seguros);
        boolean renovar = SeguroIvroUtil.puedeRenovarSeguro(seguros);
        boolean extemporanea=SeguroIvroUtil.esRenovacionExtemporanea(seguros);
        if(seguros != null && seguros.length > 0) {
            SeguroIvro seguroDe = seguroServices.getDetalleSeguro(seguros[0].getCveIdSeguroIvro());
            seguroDe.setFechaFin(SeguroIvroUtil.getFechaFinalSeguro(seguroDe));
            seguros[0] = seguroDe;
        }                
        model.addAttribute("renova", renovar);
        model.addAttribute("compra", comprar);
        model.addAttribute("seguros", seguros);
        model.addAttribute("esExtemporanea", extemporanea);

        return VIEW_WIDGET_IVRO_PERSONAL_BODY;
    }
    
    
    
    
}
