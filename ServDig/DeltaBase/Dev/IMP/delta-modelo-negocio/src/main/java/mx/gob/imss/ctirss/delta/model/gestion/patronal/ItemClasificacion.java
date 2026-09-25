package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class ItemClasificacion extends AbstractModel implements Comparable<ItemClasificacion>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -884730693090713994L;
	private Long idVista;
	private SujetoObligado sujetoObligado;
	
	/**
	 * @return the idVista
	 */
	public Long getIdVista() {
		return idVista;
	}

	/**
	 * @param idVista the idVista to set
	 */
	public void setIdVista(Long idVista) {
		this.idVista = idVista;
	}


	public SujetoObligado getSujetoObligado() {
		return sujetoObligado;
	}

	public void setSujetoObligado(SujetoObligado sujetoObligado) {
		this.sujetoObligado = sujetoObligado;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("idVista=");
		builder.append(idVista);
		return builder.toString();
	}

	@Override
	public int compareTo(ItemClasificacion arg0) {
		return idVista.compareTo(arg0.getIdVista());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idVista == null) ? 0 : idVista.hashCode());
		result = prime * result
				+ ((sujetoObligado == null) ? 0 : sujetoObligado.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object o){
		if(o instanceof ItemClasificacion){
			ItemClasificacion p = (ItemClasificacion)o;
			if(idVista.intValue() == p.getIdVista().intValue()){
				if(sujetoObligado.getCveIdSujetoObligado() == null && p.getSujetoObligado().getCveIdSujetoObligado() == null){
					return true;
				}
				else if(sujetoObligado.getCveIdSujetoObligado() != null
						&& p.getSujetoObligado().getCveIdSujetoObligado() != null
						&& sujetoObligado.getCveIdSujetoObligado().intValue() == p.getSujetoObligado().getCveIdSujetoObligado().intValue()){
					return true;
				}
			}
		}
		return false;
	}
}
