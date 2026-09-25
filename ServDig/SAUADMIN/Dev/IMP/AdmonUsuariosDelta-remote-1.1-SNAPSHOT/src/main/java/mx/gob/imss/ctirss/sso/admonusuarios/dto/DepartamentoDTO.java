/**
 * 
 */
package mx.gob.imss.ctirss.sso.admonusuarios.dto;

import java.io.Serializable;

/**
 * @author Alan Garcia
 *
 */

//Added by Alan Garcia

public class DepartamentoDTO  implements Serializable{
	
	/**
	 * 
	 */
	private static   long serialVersionUID = 7909142984660178222L;
	
	private long cveSsodepto;
	
	private String desDepartamento;
	
	private String desLeyendaAcuse;
	
	private DepartamentoDTO deptoGeneralDTO;
	
	public DepartamentoDTO()
	{
		cveSsodepto = -99;
		desDepartamento = "";
	}

	public DepartamentoDTO(long claveDepto)
	{
		cveSsodepto = claveDepto;
	}

	public long getCveSsodepto() {
		return cveSsodepto;
	}
	public void setCveSsodepto(long cveSsodepto) {
		this.cveSsodepto = cveSsodepto;
	}
	public String getDesDepartamento() {
		return desDepartamento;
	}
	public void setDesDepartamento(String desDepartamento) {
		this.desDepartamento = desDepartamento;
	}
	
	
	
	
	
//	/** cve departamento*/
//	private String cveDepartamento;
//	/** Nombre departamento */
//	private String nombreDepartamento;

	/** area normativa */
	private AreaNormativaDTO areaNormativa;
	/** area normativa */
	private DepartamentoDTO departamentoPadre;
	

	public AreaNormativaDTO getAreaNormativa() {
		return areaNormativa;
	}
	public void setAreaNormativa(AreaNormativaDTO areaNormativa) {
		this.areaNormativa = areaNormativa;
	}
	public DepartamentoDTO getDepartamentoPadre() {
		return departamentoPadre;
	}
	public void setDepartamentoPadre(DepartamentoDTO departamentoPadre) {
		this.departamentoPadre = departamentoPadre;
	}
	public DepartamentoDTO getDeptoGeneralDTO() {
		return deptoGeneralDTO;
	}
	public void setDeptoGeneralDTO(DepartamentoDTO deptoGeneralDTO) {
		this.deptoGeneralDTO = deptoGeneralDTO;
	}

	public String getDesLeyendaAcuse() {
		return desLeyendaAcuse;
	}

	public void setDesLeyendaAcuse(String desLeyendaAcuse) {
		this.desLeyendaAcuse = desLeyendaAcuse;
	}
	
}
