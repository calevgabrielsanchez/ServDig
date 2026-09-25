/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto;

import java.io.Serializable;

/**
 * @author JUAN MANUEL MARQUEZ
 *
 */
public class RequisitosDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int aprobado;
	private String motivo;
	
	
	public RequisitosDTO(){
		
	}
	
	public RequisitosDTO(int aprobado, String motivo){
		this.aprobado = aprobado;
		this.motivo = motivo;
	}
	
	/**
	 * @return the motivo
	 */
	public String getMotivo() {
		return motivo;
	}
	/**
	 * @param motivo the motivo to set
	 */
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	/**
	 * @return the aprobado
	 */
	public int getAprobado() {
		return aprobado;
	}
	/**
	 * @param aprobado the aprobado to set
	 */
	public void setAprobado(int aprobado) {
		this.aprobado = aprobado;
	}
	
	@Override
	public String toString() {
		String string = "Indicador aprovado: " + this.aprobado + "\nMotivo: " + this.motivo;
		return string;
	}
	
	
}
