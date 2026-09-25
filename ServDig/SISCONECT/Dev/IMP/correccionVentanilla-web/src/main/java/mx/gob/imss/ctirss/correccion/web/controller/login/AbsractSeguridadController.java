/**
 * 
 */
package mx.gob.imss.ctirss.correccion.web.controller.login;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.login.model.SegMenu;
import mx.gob.imss.ctirss.correccion.session.MenuVO;

/**
 * @author Vladimir Aguirre Piedragil
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 09/01/2012
 */
public abstract class AbsractSeguridadController extends AbstractController{
	/**
	 * Transforma el menu del back end en un menu del front end
	 * 
	 * @param listaIn
	 * @return
	 */
	protected List<MenuVO> transformarMenu(List<SegMenu> listaIn) {
		final List<MenuVO> listaResp = new ArrayList<MenuVO>();
		MenuVO resp = null;
		for (SegMenu in : listaIn) {
			resp = new MenuVO();
			resp.setCvePK(in.getCveIdMenu());
			if (in.getSegMenu() != null) {
				resp.setCveFkMenuItem(String.valueOf(in.getSegMenu().getCveIdMenu()));
			}
			// resp.setCveFkRol(in.getCveFkRol());
			resp.setDesEtiqueta(in.getDesDescripcion());
			resp.setDesURL(in.getDesVinculo());
			resp.setNumOrden(in.getNumOrden());
			listaResp.add(resp);
		}
		return listaResp;
	}
}
