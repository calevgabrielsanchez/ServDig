package mx.gob.imss.dashboardServicios.Controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import mx.gob.imss.dashboardServicios.infraestructura.utilerias.Menu;

import org.primefaces.model.MenuModel;

/**
 * @author Josué Hernández Ramírez
 * @company IMSS (Instituto Mexicano del Seguro Social 20/01/2012
 */
@ManagedBean(name = "menuMb")
@SessionScoped
public class MenuMB implements Serializable {

	private static final long serialVersionUID = -7422613903917389669L;
	private MenuModel menu;

	/**
	 * ejecucion del constructor para la carga del menu
	 */
	public MenuMB() {
		Menu menul = new Menu();
		menu = menul.getMenu();
	}

	/**
	 * @return the menu
	 */
	public MenuModel getMenu() {
		return menu;
	}

	/**
	 * @param menu
	 *            the menu to set
	 */
	public void setMenu(MenuModel menu) {
		this.menu = menu;
	}

}
