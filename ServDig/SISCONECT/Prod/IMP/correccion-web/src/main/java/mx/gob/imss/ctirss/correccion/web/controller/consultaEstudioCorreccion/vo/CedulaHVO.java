/**
 * @author Oscar German Beltran Ortega
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 30/04/2012
 */
package mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.vo;


/**
 * Objeto visual el cual nos apoya al momento de consultar
 * las cédulas elaboradas por el patrón, en este caso
 * particular la "Cédula H".
 * 
 * @author Oscar Beltran Ortega
 * @version 1.0.1
 *
 */
public class CedulaHVO {
	
	private String registroPatronal;
	private String nuNss;
	private String nombreAsegurado;
	private String apPaternoAsegurado;
	private String apMaternoAsegurado;
	private String nuAntiguedadAnios;
	private String txCategoria;
	private String txMes;
	private Integer cveMes;
	private String txRemuneracion;
	private String impRemuneracion;
	private String inTpoPercepcion;
	private String nuDiasSalDev;
	private String impPercepVarDiaria;
	private String impCotizo;
	
	
	/**
	 * Constructor default
	 */
	public CedulaHVO() {}
	
	/**
	 * Constructor que inicializa variables
	 */
	public CedulaHVO(Object[] obj){
		int i = 0;
		
		setNuNss(String.valueOf(obj[i++]));
		setRegistroPatronal(String.valueOf(obj[i++]));
		setNombreAsegurado(String.valueOf(obj[i++]));
		setApPaternoAsegurado(String.valueOf(obj[i++]));
		setApMaternoAsegurado(String.valueOf(obj[i++]));
		setNuAntiguedadAnios(String.valueOf(obj[i++]));
		setTxCategoria(String.valueOf(obj[i++]));
		setTxMes(String.valueOf(obj[i++]));
		setTxRemuneracion(String.valueOf(obj[i++]));
		setImpRemuneracion(String.valueOf(obj[i++]));
		setInTpoPercepcion(String.valueOf(obj[i++]));
		setCveMes(Integer.valueOf(String.valueOf(obj[i++])));
		setNuDiasSalDev(String.valueOf(obj[i++]));
		setImpPercepVarDiaria(String.valueOf(obj[i++]));
		setImpCotizo(String.valueOf(obj[i++]));
	
		
	}

	/**
	 * @return the registroPatronal
	 */
	public String getRegistroPatronal() {
		return registroPatronal;
	}

	/**
	 * @param registroPatronal the registroPatronal to set
	 */
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}

	/**
	 * @return the nuNss
	 */
	public String getNuNss() {
		return nuNss;
	}

	/**
	 * @param nuNss the nuNss to set
	 */
	public void setNuNss(String nuNss) {
		this.nuNss = nuNss;
	}

	/**
	 * @return the nombreAsegurado
	 */
	public String getNombreAsegurado() {
		return nombreAsegurado;
	}

	/**
	 * @param nombreAsegurado the nombreAsegurado to set
	 */
	public void setNombreAsegurado(String nombreAsegurado) {
		this.nombreAsegurado = nombreAsegurado;
	}

	/**
	 * @return the apPaternoAsegurado
	 */
	public String getApPaternoAsegurado() {
		return apPaternoAsegurado;
	}

	/**
	 * @param apPaternoAsegurado the apPaternoAsegurado to set
	 */
	public void setApPaternoAsegurado(String apPaternoAsegurado) {
		this.apPaternoAsegurado = apPaternoAsegurado;
	}

	/**
	 * @return the apMaternoAsegurado
	 */
	public String getApMaternoAsegurado() {
		return apMaternoAsegurado;
	}

	/**
	 * @param apMaternoAsegurado the apMaternoAsegurado to set
	 */
	public void setApMaternoAsegurado(String apMaternoAsegurado) {
		this.apMaternoAsegurado = apMaternoAsegurado;
	}

	/**
	 * @return the nuAntiguedadAnios
	 */
	public String getNuAntiguedadAnios() {
		return nuAntiguedadAnios;
	}

	/**
	 * @param nuAntiguedadAnios the nuAntiguedadAnios to set
	 */
	public void setNuAntiguedadAnios(String nuAntiguedadAnios) {
		this.nuAntiguedadAnios = nuAntiguedadAnios;
	}

	/**
	 * @return the txCategoria
	 */
	public String getTxCategoria() {
		return txCategoria;
	}

	/**
	 * @param txCategoria the txCategoria to set
	 */
	public void setTxCategoria(String txCategoria) {
		this.txCategoria = txCategoria;
	}

	/**
	 * @return the txMes
	 */
	public String getTxMes() {
		return txMes;
	}

	/**
	 * @param txMes the txMes to set
	 */
	public void setTxMes(String txMes) {
		this.txMes = txMes;
	}

	/**
	 * @return the cveMes
	 */
	public Integer getCveMes() {
		return cveMes;
	}

	/**
	 * @param cveMes the cveMes to set
	 */
	public void setCveMes(Integer cveMes) {
		this.cveMes = cveMes;
	}

	/**
	 * @return the txRemuneracion
	 */
	public String getTxRemuneracion() {
		return txRemuneracion;
	}

	/**
	 * @param txRemuneracion the txRemuneracion to set
	 */
	public void setTxRemuneracion(String txRemuneracion) {
		this.txRemuneracion = txRemuneracion;
	}

	/**
	 * @return the impRemuneracion
	 */
	public String getImpRemuneracion() {
		return impRemuneracion;
	}

	/**
	 * @param impRemuneracion the impRemuneracion to set
	 */
	public void setImpRemuneracion(String impRemuneracion) {
		this.impRemuneracion = impRemuneracion;
	}

	/**
	 * @return the inTpoPercepcion
	 */
	public String getInTpoPercepcion() {
		return inTpoPercepcion;
	}

	/**
	 * @param inTpoPercepcion the inTpoPercepcion to set
	 */
	public void setInTpoPercepcion(String inTpoPercepcion) {
		this.inTpoPercepcion = inTpoPercepcion;
	}

	/**
	 * @return the nuDiasSalDev
	 */
	public String getNuDiasSalDev() {
		return nuDiasSalDev;
	}

	/**
	 * @param nuDiasSalDev the nuDiasSalDev to set
	 */
	public void setNuDiasSalDev(String nuDiasSalDev) {
		this.nuDiasSalDev = nuDiasSalDev;
	}

	/**
	 * @return the impPercepVarDiaria
	 */
	public String getImpPercepVarDiaria() {
		return impPercepVarDiaria;
	}

	/**
	 * @param impPercepVarDiaria the impPercepVarDiaria to set
	 */
	public void setImpPercepVarDiaria(String impPercepVarDiaria) {
		this.impPercepVarDiaria = impPercepVarDiaria;
	}

	/**
	 * @return the impCotizo
	 */
	public String getImpCotizo() {
		return impCotizo;
	}

	/**
	 * @param impCotizo the impCotizo to set
	 */
	public void setImpCotizo(String impCotizo) {
		this.impCotizo = impCotizo;
	}
	



	
	

}
