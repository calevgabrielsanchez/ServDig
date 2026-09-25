package mx.gob.imss.dashboardServicios.infraestructura.utilerias;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

/**
 * @author Josué Hernández Ramírez
 * @company IMSS (Instituto Mexicano del Seguro Social
 *	20/01/2012
 */
public class Menu {
	private Application app;
	 public MenuModel getMenu(){
		 MenuModel menu=new DefaultMenuModel();
		 Submenu submenu = new Submenu();
		 submenu.setLabel("Inicio");
		 MenuItem menuItem= new  MenuItem();
		 menu.addSubmenu(submenu);
		 submenu = new Submenu();
		 submenu.setLabel("Configuración");
		 menuItem= new  MenuItem();
		 menuItem.setValue("Sistemas");
		 menuItem.setUrl("/xhtml/Catalogos/Sistemas.xhtml");
		 submenu.getChildren().add(menuItem);
		 menuItem= new  MenuItem();
		 menuItem.setValue("Servicios");
		 menuItem.setUrl("/xhtml/Catalogos/Servicios.xhtml");
		 submenu.getChildren().add(menuItem);
		 menu.addSubmenu(submenu);
		 submenu = new Submenu();
		 submenu.setLabel("Operacion");
		 menuItem= new  MenuItem();
		 menuItem.setValue("Bitacora servicios");
		 menuItem.setUrl("/xhtml/Operacion/Bitacora.xhtml");
		 submenu.getChildren().add(menuItem);
		 menu.addSubmenu(submenu);
		 
		 return menu;
	 }

}
