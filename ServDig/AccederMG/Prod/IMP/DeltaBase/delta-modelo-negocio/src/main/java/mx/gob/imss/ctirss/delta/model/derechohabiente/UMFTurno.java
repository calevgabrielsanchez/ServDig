/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.derechohabiente;

import java.io.Serializable;
import java.math.BigInteger;


/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hugo Armando Mart-nez Cham-nica
 *  @Proyecto: delta
 *  @Archivo: UMFTurno.java
 *  @Paquete: mx.gob.imss.ctirss.delta.model.derechohabiente
 *  @Fecha: 11:48:08
 */
public class UMFTurno implements Serializable{

	private static final long serialVersionUID = 1L;
	//base
	private Long idTurno;
	private Long idUmf;
	
	//derechohabiente
	protected Turno turno;
	protected UnidadMedicaFamiliar unidadMedicaFamiliar;
	protected BigInteger noCita;
	
	
	public Turno getTurno() {
		return turno;
	}
	public void setTurno(Turno turno) {
		this.turno = turno;
	}
	public UnidadMedicaFamiliar getUnidadMedicaFamiliar() {
		return unidadMedicaFamiliar;
	}
	public void setUnidadMedicaFamiliar(UnidadMedicaFamiliar unidadMedicaFamiliar) {
		this.unidadMedicaFamiliar = unidadMedicaFamiliar;
	}
	public BigInteger getNoCita() {
		return noCita;
	}
	public void setNoCita(BigInteger noCita) {
		this.noCita = noCita;
	}
	/**
	 * @return the idTurno
	 */
	public Long getIdTurno() {
		return idTurno;
	}
	/**
	 * @param idTurno the idTurno to set
	 */
	public void setIdTurno(Long idTurno) {
		this.idTurno = idTurno;
	}
	/**
	 * @return the idUmf
	 */
	public Long getIdUmf() {
		return idUmf;
	}
	/**
	 * @param idUmf the idUmf to set
	 */
	public void setIdUmf(Long idUmf) {
		this.idUmf = idUmf;
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("UMFTURNO [ID_TURNO="+this.idTurno+"]");
		builder.append("UMFTURNO [ID_UMF="+this.idUmf+"]");
		return builder.toString();
	}
	
}
