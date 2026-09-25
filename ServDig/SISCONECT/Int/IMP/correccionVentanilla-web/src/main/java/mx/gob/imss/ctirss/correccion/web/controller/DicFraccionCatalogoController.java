/**
 * DicFraccionCatalogoController.java
 * @package mx.gob.imss.delta.web.controller 
 * @project ANONIMO
 */
package mx.gob.imss.ctirss.correccion.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;


import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DicFraccionWrapperDataTable;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.DicFraccion;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ANONIMO
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date Wed Oct 05 16:37:10 CDT 2011
 */
@Controller
@RequestMapping(value="/catalogo/dicfraccion")
public class DicFraccionCatalogoController extends AbstractController {

    @Autowired
    private ICatalogoService<DicFraccion> catalogoServiceBean;
    
    @RequestMapping(method=RequestMethod.GET)
    public String getCreateForm(Model model) {
        model.addAttribute(new DicFraccion());
         return "catalogos/dicfraccion/dicfraccionMain";
    }
    
    @RequestMapping(value="/modificar" , method=RequestMethod.POST)
    public @ResponseBody DicFraccion modify(@RequestBody DicFraccion dicfraccion, HttpServletResponse response) {
        this.catalogoServiceBean.actualizar(dicfraccion);
        return dicfraccion;
    }    
    
    @RequestMapping(value="/eliminar" , method=RequestMethod.POST)
    public @ResponseBody DicFraccion delete(@RequestBody DicFraccion dicfraccion, HttpServletResponse response) {
        this.catalogoServiceBean.eliminar(dicfraccion);
        return dicfraccion;
    }        
    
    @RequestMapping(value="/agregar" , method=RequestMethod.POST)
    public @ResponseBody DicFraccion create(@RequestBody DicFraccion dicfraccion) {
        this.catalogoServiceBean.agregar(dicfraccion);
        return dicfraccion;
    }
    
    @RequestMapping(value="/paginar", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<DicFraccion> pagina(@RequestBody DicFraccionWrapperDataTable aoData ) {
        DatosEntradaPaginador send = new DatosEntradaPaginador<AbstractModel>();
        send.parserArray(aoData.getAoData());
        send.setModelo(aoData.getoForm());
        DatosSalidaPaginador<DicFraccion> reply = this.catalogoServiceBean.pagina(send);
        reply.setsEcho(send.getsEcho());
        return reply;
    }
    
    @RequestMapping(value="/consultaPorClave")
    public @ResponseBody DicFraccion consultaPorClave(@RequestBody DicFraccion dicfraccion){
        System.out.println("CVE ID A BUSCAR:"+dicfraccion.getCveIdFraccion());
        if(dicfraccion.getCveIdFraccion()>0){
            dicfraccion = catalogoServiceBean.consultaPorClave(dicfraccion);    
        }
        return dicfraccion;
    }
    
    // internal helpers    
    private Map<String, String> validationMessages(Set<ConstraintViolation<DicFraccion>> failures) {
        Map<String, String> failureMessages = new HashMap<String, String>();
        for (ConstraintViolation<DicFraccion> failure : failures) {
            failureMessages.put(failure.getPropertyPath().toString(), failure.getMessage());
        }
        return failureMessages;
    }    
}
