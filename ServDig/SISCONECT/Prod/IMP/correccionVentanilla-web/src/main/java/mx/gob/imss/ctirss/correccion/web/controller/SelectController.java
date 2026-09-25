/**
 * 
 */
package mx.gob.imss.ctirss.correccion.web.controller;

import java.util.ArrayList;
import java.util.List;


import mx.gob.imss.ctirss.correccion.bean.SelectBean;
import mx.gob.imss.ctirss.correccion.catalogos.model.SacDelegacion;
import mx.gob.imss.ctirss.correccion.catalogos.model.SacSubdelegacion;
import mx.gob.imss.ctirss.correccion.service.interfaces.ISelectService;

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
public class SelectController {

	
	@Autowired
	private ISelectService componentComboService;	
	
	/**
	 * 
	 * @param clazEntityName : NOmbre de la clase Entity a filtrar
	 * @return
	 */
	@RequestMapping(value="/simple", method=RequestMethod.GET )
    public @ResponseBody List<SelectBean> getSelectOptions(	@RequestParam String clazEntityName) {
	    Class claz;
	    List<SelectBean> selectOpts = null;
	    List<SelectBean> selectDelete = new ArrayList<SelectBean>();
	    int id=0;
		try {
			claz = Class.forName(clazEntityName);			
			
			selectOpts = this.componentComboService.getOptions(claz);	
			
			if(claz.equals(SacDelegacion.class)){
				for(SelectBean be:selectOpts){
					id=Integer.valueOf(be.getId());
					//estas Delegaciones ya no son validas
					if(id==9 || id==35 || id==36 || id==37 || id==38){
						selectDelete.add(be);
					}
				}
			}
			
			for(SelectBean de:selectDelete){
				selectOpts.remove(de);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	@RequestMapping(value="/relacion", method=RequestMethod.GET )
    public @ResponseBody List<SelectBean> getSelectOptions(	@RequestParam String clazEntityName, 
    														@RequestParam String entityParentName,
    														@RequestParam String valueParent,
    														@RequestParam String entityRelationName,
    														@RequestParam String idRelation,
    														@RequestParam String fieldRelation,
    														@RequestParam String valueRelation)
    														{
	    Class claz;
	    List<SelectBean> selectOpts = null;
		try {
			claz = Class.forName(clazEntityName);
			selectOpts = this.componentComboService.getOptions(claz, entityParentName, valueParent, entityRelationName, valueRelation, fieldRelation, valueRelation);	
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
    														@RequestParam String valueParent) {
	    Class claz;
	    List<SelectBean> selectOpts = null;
	    List<SelectBean> selectDelete = new ArrayList<SelectBean>();
	    int id=0;
		try {
			claz = Class.forName(clazEntityName);
			selectOpts = this.componentComboService.getOptions(claz, entityParentName, valueParent);				
			if(claz.equals(SacSubdelegacion.class)){
				for(SelectBean be:selectOpts){
					id=Integer.valueOf(be.getId());
					//estas subdelegaciones ya no son validas
					if(id==8 || id==46 || id==68){
						selectDelete.add(be);
					}
				}
			}
			for(SelectBean de:selectDelete){
				selectOpts.remove(de);
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return selectOpts;
    }	
	
	
	@RequestMapping(value="/doble", method=RequestMethod.GET )
    public @ResponseBody List<SelectBean> getSelectOptions(	@RequestParam String clazEntityName, 
    														@RequestParam String entityParentName, 
    														@RequestParam String valueParent, 
    														@RequestParam String entityParentName2, 
    														@RequestParam String valueParent2) {
	    Class claz;
	    List<SelectBean> selectOpts = null;
		try {
			claz = Class.forName(clazEntityName);
			selectOpts = this.componentComboService.getOptions(claz, entityParentName, valueParent);	
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return selectOpts;
    }	
	
	
	/**
	 * 
	 * @param clazEntityName : NOmbre de la clase Entity a filtrar
	 * @return
	 */
	@RequestMapping(value="/simpleCustom", method=RequestMethod.GET )
    public @ResponseBody List<SelectBean> getSelectOptions(	@RequestParam String clazEntityName,
    														@RequestParam String param,
    														@RequestParam String paramValue,
    														@RequestParam String paramValue1) {
	    Class claz;
	    List<SelectBean> selectOpts = null;
		try {
			claz = Class.forName(clazEntityName);
			selectOpts = this.componentComboService.getOptions(claz, param, paramValue, paramValue1);			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return selectOpts;
    }
	
	
}
