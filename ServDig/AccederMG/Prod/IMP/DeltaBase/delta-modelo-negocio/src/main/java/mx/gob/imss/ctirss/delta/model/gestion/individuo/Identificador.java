package mx.gob.imss.ctirss.delta.model.gestion.individuo;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * 111012
 * @author ICCSRG
 *
 */
public class Identificador extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3147092612841408077L;

	private long idIdentificador;
	private Persona persona;
	private TipoIdentificador tipoIdentificador;
	private String identificadora;
	private long vigente;
	
	
	/**
	 * @return the idIdentificador
	 */
	public long getIdIdentificador() {
		return idIdentificador;
	}
	
	/**
	 * @param idIdentificador the idIdentificador to set
	 */
	public void setIdIdentificador(long idIdentificador) {
		this.idIdentificador = idIdentificador;
	}
	
	/**
	 * @return the persona
	 */
	public Persona getPersona() {
		return persona;
	}
	
	/**
	 * @param persona the persona to set
	 */
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	
	/**
	 * @return the tipoIdentificador
	 */
	public TipoIdentificador getTipoIdentificador() {
		return tipoIdentificador;
	}
	
	/**
	 * @param tipoIdentificador the tipoIdentificador to set
	 */
	public void setTipoIdentificador(TipoIdentificador tipoIdentificador) {
		this.tipoIdentificador = tipoIdentificador;
	}
	
	/**
	 * @return the identificadora
	 */
	public String getIdentificadora() {
		return identificadora;
	}
	
	/**
	 * @param identificadora the identificadora to set
	 */
	public void setIdentificadora(String identificadora) {
		this.identificadora = identificadora;
	}
	
	/**
	 * @return the vigente
	 */
	public long getVigente() {
		return vigente;
	}
	
	/**
	 * @param vigente the vigente to set
	 */
	public void setVigente(long vigente) {
		this.vigente = vigente;
	}
	
}
