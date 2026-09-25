/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.web.controller;

import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.select.bean.SelectBean;
import mx.gob.imss.ctirss.delta.service.interfaces.ISelectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Juan Manuel Lopez Lozano
 * @since  08/10/2011
 *
 */
@Controller
@RequestMapping(value="/combo")
public class SelectController extends AbstractController {

	
	@Autowired
	private ISelectService componentComboService;	
	
	/**
	 * 
	 * @param clazEntityName : NOmbre de la clase Entity a filtrar
	 * @return
	 */
	@RequestMapping(value="/simple", method=RequestMethod.GET )
    public @ResponseBody List<SelectBean> getSelectOptions(	@RequestParam String clazEntityName){
		
		
		this.log.debug("getSelectOptions ----");
		
	    List<SelectBean> selectOpts = null;
		try {
			selectOpts = this.componentComboService.getOptions(clazEntityName);			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.log.error("****MENSAJE: "+e.getMessage());
		}
		return selectOpts;
    }
	
	/**
	 * 
	 * @param clazEntityName : NOmbre de la clase Entity a filtrar
	 * @param entityParentName : NOmbre del atributo del FK padre
	 * @param valueParent : Valor del FK padre
	 * @return
	 */
	@RequestMapping(value="/dependiente", method=RequestMethod.GET )
    public @ResponseBody List<SelectBean> getSelectOptions(	@RequestParam String clazEntityName, 
    														@RequestParam String entityParentName, 
    														@RequestParam String valueParent){
	    List<SelectBean> selectOpts = null;
		try {
			selectOpts = this.componentComboService.getOptions(clazEntityName, entityParentName, valueParent);	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.log.error("****MENSAJE: "+e.getMessage());
		}
		return selectOpts;
    }	
	
}
