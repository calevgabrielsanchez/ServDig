package mx.gob.imss.ctirss.delta.gestion.patronal.test;

import java.io.Serializable;

public class ReporteCifrasDTO implements Serializable{

	private String rfcPatSustituto = "";
	private String regPatSustituto= "";
	private String clasePatSustituto= "";
	private String fraccionPatSustituto= "";
	private String primaPatSustituto= "";
	private String delegacionPatSustituto= "";
	private String subDelegacionPatSustituto= "";
	
	private String registroPatronalSustituido= "";
	private String clasePatSustituido= "";
	private String fraccionPatSustituido= "";
	private String primaPatSustituido= "";
	private String delegacionPatSustituido= "";
	private String subDelegacionPatSustituido= "";
	
	private String fechaRegistro= "";
	private String fechaEfecto= "";
	
	
	public ReporteCifrasDTO(){ }
	
	public ReporteCifrasDTO(String rfcPatSustituto, String regPatSustituto, String clasePatSustituto,
			String fraccionPatSustituto, String primaPatSustituto, String delegacionPatSustituto,
			String subDelegacionPatSustituto, String fechaRegistro, String fechaEfecto) {
		super();
		this.rfcPatSustituto = rfcPatSustituto;
		this.regPatSustituto = regPatSustituto;
		this.clasePatSustituto = clasePatSustituto;
		this.fraccionPatSustituto = fraccionPatSustituto;
		this.primaPatSustituto = primaPatSustituto;
		this.delegacionPatSustituto = delegacionPatSustituto;
		this.subDelegacionPatSustituto = subDelegacionPatSustituto;
		this.fechaRegistro = fechaRegistro;
		this.fechaEfecto = fechaEfecto;
	}

	/**
	 * @return the rfcPatSustituto
	 */
	public String getRfcPatSustituto() {
		return rfcPatSustituto;
	}

	/**
	 * @param rfcPatSustituto the rfcPatSustituto to set
	 */
	public void setRfcPatSustituto(String rfcPatSustituto) {
		this.rfcPatSustituto = rfcPatSustituto;
	}

	/**
	 * @return the regPatSustituto
	 */
	public String getRegPatSustituto() {
		return regPatSustituto;
	}

	/**
	 * @param regPatSustituto the regPatSustituto to set
	 */
	public void setRegPatSustituto(String regPatSustituto) {
		this.regPatSustituto = regPatSustituto;
	}

	/**
	 * @return the clasePatSustituto
	 */
	public String getClasePatSustituto() {
		return clasePatSustituto;
	}

	/**
	 * @param clasePatSustituto the clasePatSustituto to set
	 */
	public void setClasePatSustituto(String clasePatSustituto) {
		this.clasePatSustituto = clasePatSustituto;
	}

	/**
	 * @return the fraccionPatSustituto
	 */
	public String getFraccionPatSustituto() {
		return fraccionPatSustituto;
	}

	/**
	 * @param fraccionPatSustituto the fraccionPatSustituto to set
	 */
	public void setFraccionPatSustituto(String fraccionPatSustituto) {
		this.fraccionPatSustituto = fraccionPatSustituto;
	}

	/**
	 * @return the primaPatSustituto
	 */
	public String getPrimaPatSustituto() {
		return primaPatSustituto;
	}

	/**
	 * @param primaPatSustituto the primaPatSustituto to set
	 */
	public void setPrimaPatSustituto(String primaPatSustituto) {
		this.primaPatSustituto = primaPatSustituto;
	}

	/**
	 * @return the delegacionPatSustituto
	 */
	public String getDelegacionPatSustituto() {
		return delegacionPatSustituto;
	}

	/**
	 * @param delegacionPatSustituto the delegacionPatSustituto to set
	 */
	public void setDelegacionPatSustituto(String delegacionPatSustituto) {
		this.delegacionPatSustituto = delegacionPatSustituto;
	}

	/**
	 * @return the subDelegacionPatSustituto
	 */
	public String getSubDelegacionPatSustituto() {
		return subDelegacionPatSustituto;
	}

	/**
	 * @param subDelegacionPatSustituto the subDelegacionPatSustituto to set
	 */
	public void setSubDelegacionPatSustituto(String subDelegacionPatSustituto) {
		this.subDelegacionPatSustituto = subDelegacionPatSustituto;
	}

	/**
	 * @return the registroPatronalSustituido
	 */
	public String getRegistroPatronalSustituido() {
		return registroPatronalSustituido;
	}

	/**
	 * @param registroPatronalSustituido the registroPatronalSustituido to set
	 */
	public void setRegistroPatronalSustituido(String registroPatronalSustituido) {
		this.registroPatronalSustituido = registroPatronalSustituido;
	}

	/**
	 * @return the clasePatSustituido
	 */
	public String getClasePatSustituido() {
		return clasePatSustituido;
	}

	/**
	 * @param clasePatSustituido the clasePatSustituido to set
	 */
	public void setClasePatSustituido(String clasePatSustituido) {
		this.clasePatSustituido = clasePatSustituido;
	}

	/**
	 * @return the fraccionPatSustituido
	 */
	public String getFraccionPatSustituido() {
		return fraccionPatSustituido;
	}

	/**
	 * @param fraccionPatSustituido the fraccionPatSustituido to set
	 */
	public void setFraccionPatSustituido(String fraccionPatSustituido) {
		this.fraccionPatSustituido = fraccionPatSustituido;
	}

	/**
	 * @return the primaPatSustituido
	 */
	public String getPrimaPatSustituido() {
		return primaPatSustituido;
	}

	/**
	 * @param primaPatSustituido the primaPatSustituido to set
	 */
	public void setPrimaPatSustituido(String primaPatSustituido) {
		this.primaPatSustituido = primaPatSustituido;
	}

	/**
	 * @return the delegacionPatSustituido
	 */
	public String getDelegacionPatSustituido() {
		return delegacionPatSustituido;
	}

	/**
	 * @param delegacionPatSustituido the delegacionPatSustituido to set
	 */
	public void setDelegacionPatSustituido(String delegacionPatSustituido) {
		this.delegacionPatSustituido = delegacionPatSustituido;
	}

	/**
	 * @return the subDelegacionPatSustituido
	 */
	public String getSubDelegacionPatSustituido() {
		return subDelegacionPatSustituido;
	}

	/**
	 * @param subDelegacionPatSustituido the subDelegacionPatSustituido to set
	 */
	public void setSubDelegacionPatSustituido(String subDelegacionPatSustituido) {
		this.subDelegacionPatSustituido = subDelegacionPatSustituido;
	}

	/**
	 * @return the fechaRegistro
	 */
	public String getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * @return the fechaEfecto
	 */
	public String getFechaEfecto() {
		return fechaEfecto;
	}
	
	/**
	 * @param fechaEfecto the fechaEfecto to set
	 */
	public void setFechaEfecto(String fechaEfecto) {
		this.fechaEfecto = fechaEfecto;
	}
	


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return rfcPatSustituto + "|" + regPatSustituto + "|"
				+ clasePatSustituto + "|" + fraccionPatSustituto + "|"
				+ primaPatSustituto + "|" + delegacionPatSustituto
				+ "|" + subDelegacionPatSustituto + "|"
				+ registroPatronalSustituido + "|" + clasePatSustituido + "|"
				+ fraccionPatSustituido + "|" + primaPatSustituido + "|"
				+ delegacionPatSustituido + "|" + subDelegacionPatSustituido
				+ "|" + fechaRegistro + "|" + fechaEfecto+"\n";
	}



	
	private static final long serialVersionUID = 1L;

}
