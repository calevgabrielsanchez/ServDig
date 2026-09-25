/**
 * DicGrupoCatalogoController.java
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
import mx.gob.imss.ctirss.correccion.base.paginador.model.DicGrupoWrapperDataTable;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.DicGrupo;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ANONIMO
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date Wed Oct 05 10:58:38 CDT 2011
 */
@Controller
@RequestMapping(value="/catalogo/dicgrupo")
public class DicGrupoCatalogoController extends AbstractController {

    @Autowired
    private ICatalogoService<DicGrupo> catalogoServiceBean;
    
    @RequestMapping(method=RequestMethod.GET)
    public String getCreateForm(Model model) {
        model.addAttribute(new DicGrupo());
         return "catalogos/dicgrupo/dicgrupoMain";
    }
    
    @RequestMapping(value="/modificar" , method=RequestMethod.POST)
    public @ResponseBody DicGrupo modify(@RequestBody DicGrupo dicgrupo, HttpServletResponse response) {
        this.catalogoServiceBean.actualizar(dicgrupo);
        return dicgrupo;
    }    
    
    @RequestMapping(value="/eliminar" , method=RequestMethod.POST)
    public @ResponseBody DicGrupo delete(@RequestBody DicGrupo dicgrupo, HttpServletResponse response) {
        this.catalogoServiceBean.eliminar(dicgrupo);
        return dicgrupo;
    }        
    
    @RequestMapping(value="/agregar" , method=RequestMethod.POST)
    public @ResponseBody DicGrupo create(@RequestBody DicGrupo dicgrupo) {
        this.catalogoServiceBean.agregar(dicgrupo);
        return dicgrupo;
    }
    
    @RequestMapping(value="/paginar", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<DicGrupo> pagina(@RequestBody DicGrupoWrapperDataTable aoData ) {
        DatosEntradaPaginador send = new DatosEntradaPaginador<AbstractModel>();
        send.parserArray(aoData.getAoData());
        send.setModelo(aoData.getoForm());
        DatosSalidaPaginador<DicGrupo> reply = this.catalogoServiceBean.pagina(send);
        reply.setsEcho(send.getsEcho());
        return reply;
    }
    
    @RequestMapping(value="/consultaPorClave")
    public @ResponseBody DicGrupo consultaPorClave(@RequestBody DicGrupo dicgrupo){
        if(dicgrupo.getCveIdGrupo()>0){
            dicgrupo = catalogoServiceBean.consultaPorClave(dicgrupo);    
        }
        return dicgrupo;
    }
    
    // internal helpers    
    private Map<String, String> validationMessages(Set<ConstraintViolation<DicGrupo>> failures) {
        Map<String, String> failureMessages = new HashMap<String, String>();
        for (ConstraintViolation<DicGrupo> failure : failures) {
            failureMessages.put(failure.getPropertyPath().toString(), failure.getMessage());
        }
        return failureMessages;
    }    
    
	@RequestMapping(value="/cargarGrupos", method=RequestMethod.GET )
    public @ResponseBody List<DicGrupo> getGrupoXDivision(@RequestParam Long claveConsultar) {
		System.out.println(".--.---.--.-..--. en getGrupoXDivision");
		List <DicGrupo> grupos = catalogoServiceBean.consultaDicGrupoXDicDivision(claveConsultar);
		System.out.println(".--.---.--.-..--. en getGrupoXDivision retorna la lista:");
		for(DicGrupo grupo: grupos){
			System.out.println(".-.-grupo:"+grupo.cveIdGrupo+"; desc:"+grupo.getDesGrupo());
		}
        return grupos;
    }     

}
