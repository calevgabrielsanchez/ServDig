/**
 * 
 */
package mx.gob.imss.ctirss.delta.cobranza.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author vanderluk
 *
 */
public class LoteCFDI implements Serializable{

	private RegistroCFDI[] registros;
	
	private Date fechaLoteProceso;

	/**
	 * @return the registros
	 */
	public RegistroCFDI[] getRegistros() {
		return registros;
	}

	/**
	 * @param registros the registros to set
	 */
	public void setRegistros(RegistroCFDI[] registros) {
		this.registros = registros;
	}

	/**
	 * @return the fechaLoteProceso
	 */
	public Date getFechaLoteProceso() {
		return fechaLoteProceso;
	}

	/**
	 * @param fechaLoteProceso the fechaLoteProceso to set
	 */
	public void setFechaLoteProceso(Date fechaLoteProceso) {
		this.fechaLoteProceso = fechaLoteProceso;
	}

}
