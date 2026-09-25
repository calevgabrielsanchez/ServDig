/**
 * DicDivisionCatalogoController.java
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
import mx.gob.imss.ctirss.correccion.base.paginador.model.DicDivisionWrapperDataTable;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.DicDivision;
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
 * @date Mon Oct 03 15:20:30 CDT 2011
 */
@Controller
@RequestMapping(value="/catalogo/dicdivision")
public class DicDivisionCatalogoController extends AbstractController {

    @Autowired
    private ICatalogoService<DicDivision> catalogoServiceBean;

    
    
    @RequestMapping(method=RequestMethod.GET)
    public String getCreateForm(Model model) {
        model.addAttribute(new DicDivision());
         return "catalogos/dicdivision/dicdivisionMain";
    }
    
    @RequestMapping(value="/modificar" , method=RequestMethod.POST)
    public @ResponseBody DicDivision modify(@RequestBody DicDivision dicdivision, HttpServletResponse response) {
        this.catalogoServiceBean.actualizar(dicdivision);
        return dicdivision;
    }    
    
    @RequestMapping(value="/eliminar" , method=RequestMethod.POST)
    public @ResponseBody DicDivision delete(@RequestBody DicDivision dicdivision, HttpServletResponse response) {
        this.catalogoServiceBean.eliminar(dicdivision);
        return dicdivision;
    }        
    
    @RequestMapping(value="/agregar" , method=RequestMethod.POST)
    public @ResponseBody DicDivision create(@RequestBody DicDivision dicdivision) {
        this.catalogoServiceBean.agregar(dicdivision);
        return dicdivision;
    }
    
    @RequestMapping(value="/paginar", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<DicDivision> pagina(@RequestBody DicDivisionWrapperDataTable aoData ) {
        DatosEntradaPaginador<DicDivision> send = new DatosEntradaPaginador();
        send.parserArray(aoData.getAoData());
        send.setModelo(aoData.getoForm());
        DatosSalidaPaginador<DicDivision> reply = this.catalogoServiceBean.pagina(send);
        reply.setsEcho(send.getsEcho());
        return reply;
    }
    
    @RequestMapping(value="/consultaPorClave")
    public @ResponseBody DicDivision consultaPorClave(@RequestBody DicDivision dicdivision){
        if(dicdivision.getCveIdDivision()>0){
            dicdivision = catalogoServiceBean.consultaPorClave(dicdivision);    
        }
        return dicdivision;
    }        
    
    
    // internal helpers    
    private Map<String, String> validationMessages(Set<ConstraintViolation<DicDivision>> failures) {
        Map<String, String> failureMessages = new HashMap<String, String>();
        for (ConstraintViolation<DicDivision> failure : failures) {
            failureMessages.put(failure.getPropertyPath().toString(), failure.getMessage());
        }
        return failureMessages;
    }   

	@RequestMapping(value="/cargarDivisionesActivas", method=RequestMethod.GET )
    public @ResponseBody List<DicDivision> getDivisionesActivas() {
		System.out.println(".--..-.--..-  cargara divisiones activas");
        List <DicDivision> divisiones = catalogoServiceBean.consultar(new DicDivision());
        System.out.println(".--..-.--..-  cargara divisiones activas2");
        for(DicDivision division:divisiones){
        	System.out.println("division:"+division.cveIdDivision+";descripcion:"+division.getDesDivision());
        }
        return divisiones;
    }    
    
}
