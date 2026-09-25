package mx.gob.imss.ctirss.correccion.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuElement implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long cvePkMenu;
	private String cveFkMenuItem;
	private String nombreProceso;
	private String href;
	private List<MenuElement> children;
	private boolean leaf;
	private boolean expanded;
	private String detalleModulo;
	private boolean linkBloqueado;
	
	
	public MenuElement(){
	
		this.children=new ArrayList<MenuElement>();
		this.leaf=false;
		//this.nombreProceso="";
//		this.href="www.gogle.com";
//		this.expanded=true;
	}
	
	public String getNombreProceso() {
		return nombreProceso;
	}
	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
		this.leaf=true;
	}
	public List<MenuElement> getChildren() {
		return children;
	}
	public void setChildren(List<MenuElement> children) {
		this.children = children;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public Long getCvePkMenu() {
		return cvePkMenu;
	}

	public void setCvePkMenu(Long cvePkMenu) {
		this.cvePkMenu = cvePkMenu;
	}

	public String getCveFkMenuItem() {
		return cveFkMenuItem;
	}

	public void setCveFkMenuItem(String cveFkMenuItem) {
		this.cveFkMenuItem = cveFkMenuItem;
	}

	public String getDetalleModulo() {
		return detalleModulo;
	}

	public void setDetalleModulo(String detalleModulo) {
		this.detalleModulo = detalleModulo;
	}

	public boolean isLinkBloqueado() {
		return linkBloqueado;
	}

	public void setLinkBloqueado(boolean linkBloqueado) {
		this.linkBloqueado = linkBloqueado;
	}
	
	
	
	
}
