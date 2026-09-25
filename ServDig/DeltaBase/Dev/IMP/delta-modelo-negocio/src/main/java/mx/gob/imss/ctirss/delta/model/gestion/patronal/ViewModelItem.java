package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class ViewModelItem extends AbstractModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1436212118205869996L;
	private Long idVista;

	public Long getIdVista() {
		return idVista;
	}

	public void setIdVista(Long idVista) {
		this.idVista = idVista;
	}

//	@Override
//	public int compareTo(ViewModelItem o) {
//		return idVista.compareTo(o.getIdVista());
//	}
	
//	@Override
//	public boolean equals(Object o) {
//		if(o instanceof ViewModelItem){
//			ViewModelItem p = (ViewModelItem)o;
//			if(idVista.intValue() == p.getIdVista().intValue()){
//					return true;
//			}
//		}
//		return false;
//	}
}
