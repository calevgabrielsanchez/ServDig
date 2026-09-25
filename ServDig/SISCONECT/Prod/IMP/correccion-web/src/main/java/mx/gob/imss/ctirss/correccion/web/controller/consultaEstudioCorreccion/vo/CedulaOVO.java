package mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.vo;

import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model.ConsultasEstudioCorreccion;

/**
 * Objeto visual el cual nos apoya al momento de consultar las cédulas
 * elaboradas por el patrón, en este caso particular la "Cédula O" [Analisis de
 * Tiempo Extra].
 * 
 * @see ConsultaEstudioCorreccionVO
 * @author Enrique Duran Jimenez
 * @version 1.0.0
 * 
 */
public class CedulaOVO {

	/**
	 * Contiene el nombre del trabajador.
	 */
	private String nombre;
	/**
	 * Contiene el apellido paterno del trabajador.
	 */
	private String apellidoPaterno;
	/**
	 * Contiene el apellido materno del trabajador.
	 */
	private String apellidoMaterno;
	/**
	 * Contiene el rfc del trabajador.
	 */
	private String rfc;
	/**
	 * Contiene Tiempo Extra del mes de enero del trabajador.
	 */
	private String enero;
	/**
	 * Contiene Tiempo Extra del mes de febrero del trabajador.
	 */
	private String febrero;
	/**
	 * Contiene Tiempo Extra del mes de marzo del trabajador.
	 */
	private String marzo;
	/**
	 * Contiene Tiempo Extra del mes de abril del trabajador.
	 */
	private String abril;
	/**
	 * Contiene Tiempo Extra del mes de mayo del trabajador.
	 */
	private String mayo;
	/**
	 * Contiene Tiempo Extra del mes de junio del trabajador.
	 */
	private String junio;
	/**
	 * Contiene Tiempo Extra del mes de julio del trabajador.
	 */
	private String julio;
	/**
	 * Contiene Tiempo Extra del mes de agosto del trabajador.
	 */
	private String agosto;
	/**
	 * Contiene Tiempo Extra del mes de septiembre del trabajador.
	 */
	private String septiembre;
	/**
	 * Contiene Tiempo Extra del mes de octubre del trabajador.
	 */
	private String octubre;
	/**
	 * Contiene Tiempo Extra del mes de noviembre del trabajador.
	 */
	private String noviembre;
	/**
	 * Contiene Tiempo Extra del mes de diciembre del trabajador.
	 */
	private String diciembre;
	/**
	 * Contiene la suma de Tiempo Extra de todo el anio del trabajador.
	 */
	private Double totalAnio=0.0;
	
	/**
	 * Permite inicializar el objeto a través de una consulta genérica SQL Ansi,
	 * en donde se le pasará un Obj tipo Object y el constructor desdoblará la
	 * información.
	 * 
	 * @param obj
	 * @see ConsultasEstudioCorreccion
	 * @author Enrique Duran Jimenez
	 */
	public CedulaOVO(Object[] obj) {

		int i = 0;

		setNombre(String.valueOf(obj[i++]));
		setApellidoPaterno(String.valueOf(obj[i++]));
		setApellidoMaterno(String.valueOf(obj[i++]));
		setRfc(String.valueOf(obj[i++]));
		setEnero(String.valueOf(obj[i++]));
		setFebrero(String.valueOf(obj[i++]));
		setMarzo(String.valueOf(obj[i++]));
		setAbril(String.valueOf(obj[i++]));
		setMayo(String.valueOf(obj[i++]));
		setJunio(String.valueOf(obj[i++]));
		setJulio(String.valueOf(obj[i++]));
		setAgosto(String.valueOf(obj[i++]));
		setSeptiembre(String.valueOf(obj[i++]));
		setOctubre(String.valueOf(obj[i++]));
		setNoviembre(String.valueOf(obj[i++]));
		setDiciembre(String.valueOf(obj[i++]));
	}

	/**
	 * Devuelve el nombre del trabajador.
	 * 
	 * @return El nombre del trabajador
	 * @author Enrique Duran Jimenez
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Asigna el nombre del trabajador.
	 * 
	 * @param nombre
	 * @author Enrique Duran Jimenez
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve el apellido paterno del trabajador.
	 * 
	 * @return El apellidoPaterno del trabajador
	 * @author Enrique Duran Jimenez
	 */
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	/**
	 * Asigna el apellido paterno del trabajador.
	 * 
	 * @param apellidoPaterno
	 * @author Enrique Duran Jimenez
	 */
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	/**
	 * Devuelve el apellido materno del trabajador.
	 * 
	 * @return the apellidoMaterno
	 * @author Enrique Duran Jimenez
	 */
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	/**
	 * Asigna el apellido materno del trabajador.
	 * 
	 * @param apellidoMaterno
	 * @author Enrique Duran Jimenez
	 */
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	/**
	 * Obtiene el valor del rfc del trabajador.
	 * 
	 * @return El rfc del trabajador
	 * @author Enrique Duran Jimenez
	 */
	public String getRfc() {
		return rfc;
	}

	/**
	 * Asigna el valor del rfc del trabajador.
	 * 
	 * @param rfc
	 * @author Enrique Duran Jimenez
	 */
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	/**
	 * Devuelve una cadena con Tiempo Extra para el mes de enero del
	 * trabajador.
	 * 
	 * @return Tiempo Extra para el mes de enero del trabajador
	 * @author Enrique Duran Jimenez
	 */
	public String getEnero() {
		return enero;
	}

	/**
	 * Devuelve un valor Double con Tiempo Extra para el mes de enero del
	 * trabajador.
	 * 
	 * @return Tiempo Extra para el mes de enero del trabajador
	 * @author Enrique Duran Jimenez
	 */
	public Double getEneroDbl() {
		return Double.parseDouble(enero);
	}

	/**
	 * Asigna el valor de Tiempo Extra para el mes de enero del
	 * trabajador.
	 * 
	 * @param enero
	 * 
	 * @author Enrique Duran Jimenez
	 */
	public void setEnero(String enero) {
		this.enero = enero;
	}

	/**
	 * Devuelve una cadena con Tiempo Extra para el mes de febrero del
	 * trabajador.
	 * 
	 * @return Tiempo Extra para el mes de febrero del trabajador
	 * @author Enrique Duran Jimenez
	 */
	public String getFebrero() {
		return febrero;
	}

	/**
	 * Devuelve un valor Double con Tiempo Extra para el mes de febrero del
	 * trabajador.
	 * 
	 * @return Tiempo Extra para el mes de febrero del trabajador
	 * @author Enrique Duran Jimenez
	 */
	public Double getFebreroDbl() {
		return Double.parseDouble(febrero);
	}

	/**
	 * Asigna el valor de Tiempo Extra para el mes de febrero del
	 * trabajador.
	 * 
	 * @param febrero
	 * 
	 * @author Enrique Duran Jimenez
	 */
	public void setFebrero(String febrero) {
		this.febrero = febrero;
	}

	/**
	 * Devuelve una cadena con Tiempo Extra para el mes de marzo del
	 * trabajador.
	 * 
	 * @return Tiempo Extra para el mes de marzo del trabajador
	 * @author Enrique Duran Jimenez
	 */
	public String getMarzo() {
		return marzo;
	}

	/**
	 * Devuelve un valor Double con Tiempo Extra para el mes de marzo del
	 * trabajador.
	 * 
	 * @return Tiempo Extra para el mes de marzo del trabajador
	 * @author Enrique Duran Jimenez
	 */
	public Double getMarzoDbl() {
		return Double.parseDouble(marzo);
	}

	/**
	 * Asigna el valor de Tiempo Extra para el mes de marzo del
	 * trabajador.
	 * 
	 * @param marzo
	 * 
	 * @author Enrique Duran Jimenez
	 */
	public void setMarzo(String marzo) {
		this.marzo = marzo;
	}

	/**
	 * Devuelve una cadena con Tiempo Extra para el mes de abril del
	 * trabajador.
	 * 
	 * @return Tiempo Extra para el mes de abril del trabajador
	 * @author Enrique Duran Jimenez
	 */
	public String getAbril() {
		return abril;
	}

	/**
	 * Devuelve un valor Double con Tiempo Extra para el mes de abril del
	 * trabajador.
	 * 
	 * @return Tiempo Extra para el mes de abril del trabajador
	 * @author Enrique Duran Jimenez
	 */
	public Double getAbrilDbl() {
		return Double.parseDouble(abril);
	}

	/**
	 * Asigna el valor de Tiempo Extra para el mes de abril del
	 * trabajador.
	 * 
	 * @param abril
	 * 
	 * @author Enrique Duran Jimenez
	 */
	public void setAbril(String abril) {
		this.abril = abril;
	}

	/**
	 * Devuelve una cadena con Tiempo Extra para el mes de mayo del
	 * trabajador.
	 * 
	 * @return Tiempo Extra para el mes de mayo del trabajador
	 * @author Enrique Duran Jimenez
	 */
	public String getMayo() {
		return mayo;
	}

	/**
	 * Devuelve un valor Double con Tiempo Extra para el mes de mayo del
	 * trabajador.
	 * 
	 * @return Tiempo Extra para el mes de mayo del trabajador
	 * @author Enrique Duran Jimenez
	 */
	public Double getMayoDbl() {
		return Double.parseDouble(mayo);
	}

	/**
	 * Asigna el valor de Tiempo Extra para el mes de mayo del
	 * trabajador.
	 * 
	 * @param mayo
	 * 
	 * @author Enrique Duran Jimenez
	 */
	public void setMayo(String mayo) {
		this.mayo = mayo;
	}

	/**
	 * Devuelve una cadena con Tiempo Extra para el mes de junio del
	 * trabajador.
	 * 
	 * @return Tiempo Extra para el mes de junio del trabajador
	 * @author Enrique Duran Jimenez
	 */
	public String getJunio() {
		return junio;
	}

	/**
	 * Devuelve un valor Double con Tiempo Extra para el mes de junio del
	 * trabajador.
	 * 
	 * @return Tiempo Extra para el mes de junio del trabajador
	 * @author Enrique Duran Jimenez
	 */
	public Double getJunioDbl() {
		return Double.parseDouble(junio);
	}

	/**
	 * Asigna el valor de Tiempo Extra para el mes de junio del
	 * trabajador.
	 * 
	 * @param junio
	 * 
	 * @author Enrique Duran Jimenez
	 */
	public void setJunio(String junio) {
		this.junio = junio;
	}

	/**
	 * Devuelve una cadena con Tiempo Extra para el mes de julio del
	 * trabajador.
	 * 
	 * @return Tiempo Extra para el mes de julio del trabajador
	 * @author Enrique Duran Jimenez
	 */
	public String getJulio() {
		return julio;
	}

	/**
	 * Devuelve un valor Double con Tiempo Extra para el mes de julio del
	 * trabajador.
	 * 
	 * @return Tiempo Extra para el mes de julio del trabajador
	 * @author Enrique Duran Jimenez
	 */
	public Double getJulioDbl() {
		return Double.parseDouble(julio);
	}

	/**
	 * Asigna el valor de Tiempo Extra para el mes de julio del
	 * trabajador.
	 * 
	 * @param julio
	 * 
	 * @author Enrique Duran Jimenez
	 */
	public void setJulio(String julio) {
		this.julio = julio;
	}

	/**
	 * Devuelve una cadena con Tiempo Extra para el mes de agosto del
	 * trabajador.
	 * 
	 * @return Tiempo Extra para el mes de agosto del trabajador
	 * @author Enrique Duran Jimenez
	 */
	public String getAgosto() {
		return agosto;
	}

	/**
	 * Devuelve un valor Double con Tiempo Extra para el mes de agosto del
	 * trabajador.
	 * 
	 * @return Tiempo Extra para el mes de agosto del trabajador
	 * @author Enrique Duran Jimenez
	 */
	public Double getAgostoDbl() {
		return Double.parseDouble(agosto);
	}

	/**
	 * Asigna el valor de Tiempo Extra para el mes de agosto del
	 * trabajador.
	 * 
	 * @param agosto
	 * 
	 * @author Enrique Duran Jimenez
	 */
	public void setAgosto(String agosto) {
		this.agosto = agosto;
	}

	/**
	 * Devuelve una cadena con Tiempo Extra para el mes de septiembre del
	 * trabajador.
	 * 
	 * @return Tiempo Extra para el mes de septiembre del trabajador
	 * @author Enrique Duran Jimenez
	 */
	public String getSeptiembre() {
		return septiembre;
	}

	/**
	 * Devuelve un valor Double con Tiempo Extra para el mes de septiembre del
	 * trabajador.
	 * 
	 * @return Tiempo Extra para el mes de septiembre del trabajador
	 * @author Enrique Duran Jimenez
	 */
	public Double getSeptiembreDbl() {
		return Double.parseDouble(septiembre);
	}

	/**
	 * Asigna el valor de Tiempo Extra para el mes de septiembre del
	 * trabajador.
	 * 
	 * @param septiembre
	 * 
	 * @author Enrique Duran Jimenez
	 */
	public void setSeptiembre(String septiembre) {
		this.septiembre = septiembre;
	}

	/**
	 * Devuelve una cadena con Tiempo Extra para el mes de octubre del
	 * trabajador.
	 * 
	 * @return Tiempo Extra para el mes de octubre del trabajador
	 * @author Enrique Duran Jimenez
	 */
	public String getOctubre() {
		return octubre;
	}

	/**
	 * Devuelve un valor Double con Tiempo Extra para el mes de octubre del
	 * trabajador.
	 * 
	 * @return Tiempo Extra para el mes de octubre del trabajador
	 * @author Enrique Duran Jimenez
	 */
	public Double getOctubreDbl() {
		return Double.parseDouble(octubre);
	}

	/**
	 * Asigna el valor de Tiempo Extra para el mes de octubre del
	 * trabajador.
	 * 
	 * @param octubre
	 * 
	 * @author Enrique Duran Jimenez
	 */
	public void setOctubre(String octubre) {
		this.octubre = octubre;
	}

	/**
	 * Devuelve una cadena con Tiempo Extra para el mes de noviembre del
	 * trabajador.
	 * 
	 * @return Tiempo Extra para el mes de noviembre del trabajador
	 * @author Enrique Duran Jimenez
	 */
	public String getNoviembre() {
		return noviembre;
	}

	/**
	 * Devuelve un valor Double con Tiempo Extra para el mes de noviembre del
	 * trabajador.
	 * 
	 * @return Tiempo Extra para el mes de noviembre del trabajador
	 * @author Enrique Duran Jimenez
	 */
	public Double getNoviembreDbl() {
		return Double.parseDouble(noviembre);
	}

	/**
	 * Asigna el valor de Tiempo Extra para el mes de noviembre del
	 * trabajador.
	 * 
	 * @param noviembre
	 * 
	 * @author Enrique Duran Jimenez
	 */
	public void setNoviembre(String noviembre) {
		this.noviembre = noviembre;
	}

	/**
	 * Devuelve una cadena con Tiempo Extra para el mes de diciembre del
	 * trabajador.
	 * 
	 * @return Tiempo Extra para el mes de diciembre del trabajador
	 * @author Enrique Duran Jimenez
	 */
	public String getDiciembre() {
		return diciembre;
	}

	/**
	 * Devuelve un valor Double con Tiempo Extra para el mes de diciembre del
	 * trabajador.
	 * 
	 * @return Tiempo Extra para el mes de diciembre del trabajador
	 * @author Enrique Duran Jimenez
	 */
	public Double getDiciembreDbl() {
		return Double.parseDouble(diciembre);
	}

	/**
	 * Asigna el valor de Tiempo Extra para el mes de diciembre del
	 * trabajador.
	 * 
	 * @param diciembre
	 * 
	 * @author Enrique Duran Jimenez
	 */
	public void setDiciembre(String diciembre) {
		this.diciembre = diciembre;
	}

	/**
	 * Devuelve un valor Double con Tiempo Extra del anio del
	 * trabajador.
	 * 
	 * @return Tiempo Extra del anio del trabajador
	 * @author Enrique Duran Jimenez
	 */
	public Double getTotalAnio() {
		totalAnio = 0.0;
		return totalAnio = getEnero() == "null" ? 0.0 : getEneroDbl() + getFebrero() == "null" ? 0.0 : getFebreroDbl() 
				+ getMarzo() == "null" ? 0.0 : getMarzoDbl()
				+ getAbril() == "null" ? 0.0 : getAbrilDbl()
				+ getMayo() == "null" ? 0.0 : getMayoDbl()
				+ getJunio() == "null" ? 0.0 : getJunioDbl() 
				+ getJulio() == "null" ? 0.0 : getJulioDbl()
				+ getAgosto() == "null" ? 0.0 : getAgostoDbl() 
				+ getSeptiembre() == "null" ? 0.0 : getSeptiembreDbl()
				+ getOctubre() == "null" ? 0.0 : getOctubreDbl()
				+ getNoviembre() == "null" ? 0.0 : getNoviembreDbl() 
				+ getDiciembre() == "null" ? 0.0 : getDiciembreDbl();
	}

	/**
	 * Asigna el valor de Tiempo Extra del anio del
	 * trabajador.
	 * 
	 * @param totalAnio
	 * 
	 * @author Enrique Duran Jimenez
	 */
	public void setTotalAnio(Double totalAnio) {
		this.totalAnio = totalAnio;
	}

}
