/**
 * 
 */
package mx.gob.imss.digital.modelo.cobranza;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "movimientoEmpleado", namespace = "http://mx.gob.imss.digital.modelo.cobranza", propOrder = {
        "tipoMovimiento",
        "fecha",
        "dias",
        "folio",
        "salario",
        "salarioMod40"
    })
@XmlRootElement(name="movimientoEmpleado", namespace = "http://mx.gob.imss.digital.modelo.cobranza")
public class MovimientoEmpleado implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Tipo de movimiento para el empleado 
	 * 
	 * 00	Carga Inicial
     * 01	Alta
     * 02	Baja
     * 07	Modificación de salario
     * 08	Reingreso
     * 10	Aportación complementaria
     * 11	Ausentismo
     * 12	Incapacidad
	 * 
	 */
	@XmlElement( nillable = false, required = true)
	private int tipoMovimiento;
	
	/**
	 * fecha en que inicia el movimiento o incidencia
	 */
	@XmlElement( nillable = false, required = true)
	private Date fecha;
	
	/**
	 * Folio del movimiento o de la incidencia
	 */
	private String folio;
	
	/**
	 * Dias de duracion en la incidencia
	 */
	@XmlElement( nillable = false, required = true)
	private int dias;
	
	
	/**
	 * Salario con el que se calcula la incidencia
	 */
	@XmlElement( nillable = false, required = true)
	private BigDecimal salario;
	
	/**
	 * Nuevos campos agregados para la modalidad 40
	*/
	
	@XmlElement( nillable = true, required = false)
	private BigDecimal salarioMod40;

	/**
	 * @return the tipoMovimiento
	 */
	public int getTipoMovimiento() {
		return tipoMovimiento;
	}

	/**
	 * @param tipoMovimiento the tipoMovimiento to set
	 */
	public void setTipoMovimiento(int tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * @return the dias
	 */
	public int getDias() {
		return dias;
	}

	/**
	 * @param dias the dias to set
	 */
	public void setDias(int dias) {
		this.dias = dias;
	}

	/**
	 * @return the salario
	 */
	public BigDecimal getSalario() {
		return salario;
	}

	/**
	 * @param salario the salario to set
	 */
	
	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}
	
	/**
	 * @return the salarioAnterior
	 */
	public BigDecimal getSalarioMod40() {
		return salarioMod40;
	}

	/**
	 * @param salario the salarioAnterior to set
	 */
	public void setSalarioMod40(BigDecimal salarioMod40) {
		this.salarioMod40 = salarioMod40;
	}
	
	

}
