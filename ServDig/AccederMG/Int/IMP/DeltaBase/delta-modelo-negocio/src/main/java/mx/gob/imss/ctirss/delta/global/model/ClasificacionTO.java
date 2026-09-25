package mx.gob.imss.ctirss.delta.global.model;

import java.math.BigDecimal;
import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;

/**
 * 
 * @author Hugo Martinez
 * Clase que representa la clasificacion de una empresa, es empleada para
 * exponerse como parametro en los servicios de negocio.
 */
public class ClasificacionTO extends AbstractModel  {
	
	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 3562451133324409445L;
	
	private Long id;
	private Date fecPresentacion;
	private Date fecEfecto;
	private Integer indTransportePropio;
	private Integer indTransporteAjeno;
	private Integer indDistribuyeEntrega;
	private Integer indServiciosATerceros;
	private Integer indPrestaServicioPersonal;
	private Integer indRegPatClase;
	private String giro;
	private Long cveIdFraccionClase;
	private Fraccion fraccion;
	private RegistroPatronalTO registroPatronal;
	private BigDecimal primaSRTActual;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Date getFecPresentacion() {
		return fecPresentacion;
	}


	public void setFecPresentacion(Date fecPresentacion) {
		this.fecPresentacion = fecPresentacion;
	}


	public Date getFecEfecto() {
		return fecEfecto;
	}


	public void setFecEfecto(Date fecEfecto) {
		this.fecEfecto = fecEfecto;
	}


	public Integer getIndTransportePropio() {
		return indTransportePropio;
	}


	public void setIndTransportePropio(Integer indTransportePropio) {
		this.indTransportePropio = indTransportePropio;
	}


	public Integer getIndTransporteAjeno() {
		return indTransporteAjeno;
	}


	public void setIndTransporteAjeno(Integer indTransporteAjeno) {
		this.indTransporteAjeno = indTransporteAjeno;
	}


	public Integer getIndDistribuyeEntrega() {
		return indDistribuyeEntrega;
	}


	public void setIndDistribuyeEntrega(Integer indDistribuyeEntrega) {
		this.indDistribuyeEntrega = indDistribuyeEntrega;
	}


	public Integer getIndServiciosATerceros() {
		return indServiciosATerceros;
	}


	public void setIndServiciosATerceros(Integer indServiciosATerceros) {
		this.indServiciosATerceros = indServiciosATerceros;
	}


	public Integer getIndPrestaServicioPersonal() {
		return indPrestaServicioPersonal;
	}


	public void setIndPrestaServicioPersonal(Integer indPrestaServicioPersonal) {
		this.indPrestaServicioPersonal = indPrestaServicioPersonal;
	}


	public Integer getIndRegPatClase() {
		return indRegPatClase;
	}


	public void setIndRegPatClase(Integer indRegPatClase) {
		this.indRegPatClase = indRegPatClase;
	}


	public String getGiro() {
		return giro;
	}


	public void setGiro(String giro) {
		this.giro = giro;
	}


	public Long getCveIdFraccionClase() {
		return cveIdFraccionClase;
	}


	public void setCveIdFraccionClase(Long cveIdFraccionClase) {
		this.cveIdFraccionClase = cveIdFraccionClase;
	}


	public Fraccion getFraccion() {
		return fraccion;
	}


	public void setFraccion(Fraccion fraccion) {
		this.fraccion = fraccion;
	}


	public BigDecimal getPrimaSRTActual() {
		return primaSRTActual;
	}


	public void setPrimaSRTActual(BigDecimal primaSRTActual) {
		this.primaSRTActual = primaSRTActual;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public RegistroPatronalTO getRegistroPatronal() {
		return registroPatronal;
	}


	public void setRegistroPatronal(RegistroPatronalTO registroPatronal) {
		this.registroPatronal = registroPatronal;
	}
	
}
