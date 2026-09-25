package mx.gob.imss.ctirss.delta.model.gestion.asegurado.integracion;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * Clase auxiliar que funciona como wrapper de la informaci&oacute;n
 * significativa generada por la finalizaci&oacute;n de un alta de NSS. Este
 * wrapper es devuelto por el servicio de finalizaci&oacute;n, procesado por el
 * OSB y publicado como mensaje por el Comet para que en la vista este
 * disponible esta informaci&oacute;n o utilizado para la generaci&oacute;n del
 * archivo para estudiantes-IDSE <br>
 * <br>
 * IMPORTANTE: Esta clase no debe contener ENUMS, ya que el OSB no puede
 * procesarlos.
 * 
 * @author Marco S&aacute;nchez
 * 
 */
public class AseguradoWrapper extends AbstractModel {

	private static final long serialVersionUID = 1L;

	public static int ASIGNADO = 1;
	public static int RECUPERADO = 2;
	public static int ERROR = 3;

	private String nombre;
	private String primerApellido;
	private String segundoApellido;

	private String curp;

	private String nss;
	private UMFSimple umf;

	private String nrp;

	// 1 = ASIGNADO; 2 = RECUPERADO; 3 = ERROR
	private int estatusRegistro;
	private String msgProceso;

	private String fechaOperacion;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getNss() {
		return nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	public UMFSimple getUmf() {
		return umf;
	}

	public void setUmf(UMFSimple umf) {
		this.umf = umf;
	}

	public String getNrp() {
		return nrp;
	}

	public void setNrp(String nrp) {
		this.nrp = nrp;
	}

	public int getEstatusRegistro() {
		return estatusRegistro;
	}

	public void setEstatusRegistro(int estatusRegistro) {
		this.estatusRegistro = estatusRegistro;
	}

	public String getMsgProceso() {
		return msgProceso;
	}

	public void setMsgProceso(String msgProceso) {
		this.msgProceso = msgProceso;
	}

	public String getFechaOperacion() {
		return fechaOperacion;
	}

	public void setFechaOperacion(String fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}
}
