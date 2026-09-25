/**
 * 
 */
package mx.imss.ctirss.web.controller.login;

import java.util.ArrayList;
import java.util.List;

import mx.imss.ctirss.catalogos.model.DlcMenu;
import mx.imss.ctirss.framework.base.controller.AbstractController;
import mx.imss.ctirss.login.model.SegMenu;
import mx.imss.ctirss.session.MenuVO;

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
	protected List<MenuVO> transformarMenu(List<DlcMenu> listaIn) {
		final List<MenuVO> listaResp = new ArrayList<MenuVO>();
		MenuVO resp = null;
		for (DlcMenu in : listaIn) {
			resp = new MenuVO();
			resp.setCvePK(in.getCveIdMenu());
			if (in.getDlcMenu() != null) {
				resp.setCveFkMenuItem(String.valueOf(in.getDlcMenu().getCveIdMenu()));
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
