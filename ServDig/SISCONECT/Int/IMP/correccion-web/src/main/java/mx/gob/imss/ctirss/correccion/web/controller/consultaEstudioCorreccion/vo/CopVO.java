package mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.vo;

public class CopVO {

	/**
	 * Indica el registro patronal que se está
	 * utilizando.
	 */
	private String registroPatronal;
	/**
	 * Indica el numero de folio SUA que se está
	 * utilizando.
	 */	
	private String folioSua;
	/**
	 * Indica el numero de Orden de Ingreso que se está
	 * utilizando.
	 */		
	private String ordenIngreso;
	/**
	 * Indica el numero de credito que se está
	 * utilizando.
	 */	
	private String numCredito;
	/**
	 * Indica la fecha de pago que se está
	 * utilizando.
	 */	
	private String fechaPago;
	/**
	 * Indica el tipo de documento que se está
	 * utilizando.
	 */		
	private String tipoDocumento;
	/**
	 * Indica el numero de trabajadores regularizados. 	 
	 */	
	private String numTrabajadoresRegularizados;
	/**
	 * Indica el periodo para las COP. 	 
	 */		
	private String copPeriodo;
	/**
	 * Indica la suerte principal COP. 	 
	 */		
	private String copSp;
	/**
	 * Indica el monto de actualizacion COP. 	 
	 */		
	private String copAct;
	/**
	 * Indica el monto de recargos COP. 	 
	 */			
	private String copRec;
	/**
	 * Indica el monto de la suma de conceptos COP
	 * SP + Act + Rec. 	 
	 */			
	private String copTotal;
	/**
	 * Indica el periodo para las RCV. 	 
	 */			
	private String rcvPeriodo;
	/**
	 * Indica la suerte principal RCV. 	 
	 */			
	private String rcvSp;
	/**
	 * Indica el monto de actualizacion RCV. 	 
	 */			
	private String rcvAct;
	/**
	 * Indica el monto de recargos RCV. 	 
	 */				
	private String rcvRec;
	/**
	 * Indica el monto de la suma de conceptos RCV
	 * SP + Act + Rec. 	 
	 */		
	private String rcvTotal;
	
	
	private String numeroAlta;
	
	
	private String numeroBaja;
	
	
	private String modificacionSalario;
	/**
	 * Permite inicializar el objeto a través de una consulta genérica SQL Ansi,
	 * en donde se le pasará un Obj tipo Object y el constructor desdoblará la
	 * información.
	 * 
	 * @param Object[]
	 * @author Gerardo Salazar Vega 
	 */
	public CopVO(Object[] obj) {
		int i = 0;
		setRegistroPatronal(String.valueOf(obj[i++]));
		setFolioSua(String.valueOf(obj[i++]));
		setOrdenIngreso(String.valueOf(obj[i++]));
		setNumCredito(String.valueOf(obj[i++]));
		setFechaPago(String.valueOf(obj[i++]));
		setTipoDocumento(String.valueOf(obj[i++]));
		setNumTrabajadoresRegularizados(String.valueOf(obj[i++]));
		setCopPeriodo(String.valueOf(obj[i++]));
		setCopSp(String.valueOf(obj[i++]));
		setCopAct(String.valueOf(obj[i++]));
		setCopRec(String.valueOf(obj[i++]));
		setCopTotal(String.valueOf(obj[i++]));
		setRcvPeriodo(String.valueOf(obj[i++]));
		setRcvSp(String.valueOf(obj[i++]));
		setRcvAct(String.valueOf(obj[i++]));
		setRcvRec(String.valueOf(obj[i++]));
		setRcvTotal(String.valueOf(obj[i++]));
		setNumeroAlta(String.valueOf(obj[i++]));
		setNumeroBaja(String.valueOf(obj[i++]));
		setModificacionSalario(String.valueOf(obj[i++]));
	}
	/**
	 * Devuelve el registro patronal.
	 * 
	 * @return registroPatronal
	 * @author Gerardo Salazar Vega
	 */
	public String getRegistroPatronal() {
		return registroPatronal;
	}
	/**Asigna el registro patronal.
	 * 
	 * @param registroPatronal 
	 * @author Gerardo Salazar Vega
	 */
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}
	/**
	 * Devuelve el folio SUA.
	 * 
	 * @return folioSua
	 * @author Gerardo Salazar Vega
	 */
	public String getFolioSua() {
		return folioSua;
	}
	/**Asigna el folio SUA.
	 * 
	 * @param folioSua
	 * @author Gerardo Salazar Vega
	 */
	public void setFolioSua(String folioSua) {
		this.folioSua = folioSua;
	}
	/**
	 * Devuelve el numero de orden de ingreso.
	 * 
	 * @return ordenIngreso
	 * @author Gerardo Salazar Vega
	 */
	public String getOrdenIngreso() {
		return ordenIngreso;
	}
	/**
	 * Asigna el numero de orden de ingreso.
	 * 
	 * @param ordenIngreso
	 * @author Gerardo Salazar Vega
	 */
	public void setOrdenIngreso(String ordenIngreso) {
		this.ordenIngreso = ordenIngreso;
	}
	/**
	 * Devuelve el numero de credito.
	 * 
	 * @return numCredito
	 * @author Gerardo Salazar Vega
	 */
	public String getNumCredito() {
		return numCredito;
	}
	/**
	 * Asigna el numero de credito.
	 * 
	 * @param numCredito 
	 * @author Gerardo Salazar Vega
	 */
	public void setNumCredito(String numCredito) {
		this.numCredito = numCredito;
	}
	/**
	 * Devuelve la fecha de pago.
	 * 
	 * @return fechaPago
	 * @author Gerardo Salazar Vega
	 */
	public String getFechaPago() {
		return fechaPago;
	}
	/**
	 * Asigna la fecha de pago.
	 * 
	 * @param fechaPago 
	 * @author Gerardo Salazar Vega
	 */
	public void setFechaPago(String fechaPago) {
		this.fechaPago = fechaPago;
	}
	/**
	 * Devuelve el tipo de documento.
	 * 
	 * @return tipoDocumento
	 * @author Gerardo Salazar Vega
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	/**
	 * Asigna el tipo de documento.
	 * 
	 * @param tipoDocumento
	 * @author Gerardo Salazar Vega
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	/**
	 * Devuelve el numero de trabajadores regularizados.
	 * 
	 * @return numTrabajadoresRegularizados
	 * @author Gerardo Salazar Vega
	 */
	public String getNumTrabajadoresRegularizados() {
		return numTrabajadoresRegularizados;
	}
	/**
	 * Asigna el numero de trabajadores regularizados.
	 * 
	 * @param numTrabajadoresRegularizados 
	 * @author Gerardo Salazar Vega
	 */
	public void setNumTrabajadoresRegularizados(String numTrabajadoresRegularizados) {
		this.numTrabajadoresRegularizados = numTrabajadoresRegularizados;
	}
	
	/**
	 * Devuelve un valor Double con el numero de trabajadores regularizados.
	 * 
	 * @return Numero de trabajadores regularizados.
	 * @author Gerardo Salazar Vega
	 */
	public Double getNumTrabajadoresRegularizadosDbl() {
		return Double.parseDouble(numTrabajadoresRegularizados);
	}	
	/**
	 * Devuelve el periodo COP.
	 * 
	 * @return copPeriodo
	 * @author Gerardo Salazar Vega
	 */
	public String getCopPeriodo() {
		return copPeriodo;
	}
	/**
	 * Asigna el periodo COP.
	 * 
	 * @param copPeriodo 
	 * @author Gerardo Salazar Vega
	 */
	public void setCopPeriodo(String copPeriodo) {
		this.copPeriodo = copPeriodo;
	}
	/**
	 * Devuelve la suerte principal COP.
	 * 
	 * @return copSp
	 * @author Gerardo Salazar Vega
	 */
	public String getCopSp() {
		return copSp;
	}
	/**
	 * Devuelve un valor Double con el numero de suerte principal.
	 * 
	 * @return copSp
	 * @author Gerardo Salazar Vega
	 */
	public Double getCopSpDbl() {
		return Double.parseDouble(copSp);
	}		
	/**
	 * Asigna la suerte principal COP.
	 * 
	 * @param copSp 
	 * @author Gerardo Salazar Vega
	 */
	public void setCopSp(String copSp) {
		this.copSp = copSp;
	}
	/**
	 * Devuelve el valor de actualizacion COP.
	 * 
	 * @return copAct
	 * @author Gerardo Salazar Vega
	 */
	public String getCopAct() {
		return copAct;
	}
	/**
	 * Devuelve un valor Double con el numero copAct.
	 * 
	 * @return copAct
	 * @author Gerardo Salazar Vega
	 */
	public Double getCopActDbl() {
		return Double.parseDouble(copAct);
	}	
	/**
	 * Asigna el valor de actualizacion COP.
	 * 
	 * @param copAct 
	 * @author Gerardo Salazar Vega
	 */
	public void setCopAct(String copAct) {
		this.copAct = copAct;
	}
	/**
	 * Devuelve el valor de recargo COP.
	 * 
	 * @return copRec
	 * @author Gerardo Salazar Vega
	 */
	public String getCopRec() {
		return copRec;
	}
	/**
	 * Devuelve un valor Double con el numero de recargo COP.
	 * 
	 * @return copRec
	 * @author Gerardo Salazar Vega
	 */
	public Double getCopRecDbl() {
		return Double.parseDouble(copRec);
	}	
	/**
	 * Asigna el valor de recargo COP.
	 * 
	 * @param copRec
	 * @author Gerardo Salazar Vega
	 */
	public void setCopRec(String copRec) {
		this.copRec = copRec;
	}
	/**
	 * Devuelve el monto de la suma de conceptos COP.
	 * 
	 * @return copTotal
	 * @author Gerardo Salazar Vega
	 */
	public String getCopTotal() {
		return copTotal;
	}
	/**
	 * Devuelve un valor Double con el monto de la suma de conceptos COP.
	 * 
	 * @return copTotal
	 * @author Gerardo Salazar Vega
	 */
	public Double getCopTotalDbl() {
		return Double.parseDouble(copTotal);
	}	
	/**
	 * Asigna el monto de la suma de conceptos COP.
	 * 
	 * @param copTotal 
	 * @author Gerardo Salazar Vega
	 */
	public void setCopTotal(String copTotal) {
		this.copTotal = copTotal;
	}
	/**
	 * Devuelve el periodo RCV.
	 * 
	 * @return rcvPeriodo
	 * @author Gerardo Salazar Vega
	 */
	public String getRcvPeriodo() {
		return rcvPeriodo;
	}
	/**
	 * Asigna el periodo RCV.
	 * 
	 * @param rcvPeriodo 
	 * @author Gerardo Salazar Vega
	 */
	public void setRcvPeriodo(String rcvPeriodo) {
		this.rcvPeriodo = rcvPeriodo;
	}
	/**
	 * Devuelve la suerte principal RCV.
	 * 
	 * @return rcvSp
	 * @author Gerardo Salazar Vega
	 */
	public String getRcvSp() {
		return rcvSp;
	}
	/**
	 * Devuelve un valor Double con el valor de suerte principal RCV.
	 * 
	 * @return rcvSp
	 * @author Gerardo Salazar Vega
	 */
	public Double getRcvSpDbl() {
		return Double.parseDouble(rcvSp);
	}	
	/**
	 * Asigna la suerte principal COP.
	 * 
	 * @param rcvSp
	 * @author Gerardo Salazar Vega 
	 */
	public void setRcvSp(String rcvSp) {
		this.rcvSp = rcvSp;
	}
	/**
	 * Devuelve el valor de actualizacion RCV.
	 * 
	 * @return rcvAct
	 * @author Gerardo Salazar Vega
	 */
	public String getRcvAct() {
		return rcvAct;
	}
	/**
	 * Devuelve un valor Double con el valor de actualizacion RCV.
	 * 
	 * @return rcvAct
	 * @author Gerardo Salazar Vega
	 */
	public Double getRcvActDbl() {
		return Double.parseDouble(rcvAct);
	}	
	/**
	 * Asigna el valor de actualizacion RCV.
	 * 
	 * @param rcvAct
	 * @author Gerardo Salazar Vega 
	 */
	public void setRcvAct(String rcvAct) {
		this.rcvAct = rcvAct;
	}
	/**
	 * Devuelve el valor de recargo RCV.
	 * 
	 * @return rcvRec
	 * @author Gerardo Salazar Vega
	 */
	public String getRcvRec() {
		return rcvRec;
	}
	/**
	 * Devuelve un valor Double con el valor de recargo RCV.
	 * 
	 * @return rcvRec
	 * @author Gerardo Salazar Vega
	 */
	public Double getRcvRecDbl() {
		return Double.parseDouble(rcvRec);
	}	
	/**
	 * Asigna el valor de recargo RCV.
	 * 
	 * @param rcvRec
	 * @author Gerardo Salazar Vega 
	 */
	public void setRcvRec(String rcvRec) {
		this.rcvRec = rcvRec;
	}
	/**
	 * Devuelve el monto de la suma de conceptos RCV.
	 * 
	 * @return rcvTotal
	 * @author Gerardo Salazar Vega
	 */
	public String getRcvTotal() {
		return rcvTotal;
	}
	/**
	 * Devuelve un valor Double con el monto de la suma de conceptos RCV.
	 * 
	 * @return rcvTotal
	 * @author Gerardo Salazar Vega
	 */
	public Double getRcvTotalDbl() {
		return Double.parseDouble(rcvTotal);
	}		
	/**
	 * Asigna el monto de la suma de conceptos RCV.
	 * 
	 * @param rcvTotal
	 * @author Gerardo Salazar Vega
	 */
	public void setRcvTotal(String rcvTotal) {
		this.rcvTotal = rcvTotal;
	}
	public String getNumeroAlta() {
		return numeroAlta;
	}
	public void setNumeroAlta(String numeroAlta) {
		this.numeroAlta = numeroAlta;
	}
	public String getNumeroBaja() {
		return numeroBaja;
	}
	public void setNumeroBaja(String numeroBaja) {
		this.numeroBaja = numeroBaja;
	}
	public String getModificacionSalario() {
		return modificacionSalario;
	}
	public void setModificacionSalario(String modificacionSalario) {
		this.modificacionSalario = modificacionSalario;
	}
	
	
	
}
