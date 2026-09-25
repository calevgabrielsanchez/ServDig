package mx.gob.imss.digital.modelo.cobranza;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ToStringBuilder;
/**
 * 
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cuota", namespace = "http://mx.gob.imss.digital.modelo.cobranza", propOrder = {
        "nombre",
        "desTipoAportacion",
        "aportacion",
        "idRama",
        "idTipoAportacion",
        "factorCalculo",
        "actualizacion",
        "recargo"
    })
@XmlRootElement(name = "cuota", namespace = "http://mx.gob.imss.digital.modelo.cobranza")
public class RamaCalculo implements java.io.Serializable {

	/**
	 * Serial version UID
	 */
    private static final long serialVersionUID = -8926950578129640251L;


    /**
     * Nombre de la rama de calculo
     */
    @XmlElement( nillable = true, required = true)
    private String nombre;
    /**
     * Descripcion del tipo de aportacion
     */
    @XmlElement( nillable = true, required = true)
    private String desTipoAportacion;
    /**
     * Cantidad de aportacion
     */
    @XmlElement( nillable = false, required = true)
    private BigDecimal aportacion;
    /**
     * id del tipo de aportacion
     * 1	PATRONAL
     * 2	OBRERO
     * 
     */
    @XmlElement( nillable = false, required = true)
    private Integer idTipoAportacion;
    /**
     * Id de la rama 
     * 1	CUOTA FIJA
	 * 2	EXCEDENTE
	 * 3	PRESTACIONES EN DINERO
	 * 4	GASTOS MÉDICOS PENSIONADOS
	 * 5	RIESGOS DE TRABAJO
	 * 6	INVALIDEZ Y VIDA
	 * 7	GUARDERÍAS Y PRESTACIONES SOCIALES
	 * 8	RETIRO
     */
    @XmlElement( nillable = false, required = true)
    private Integer idRama;
    
    /**
     * FActor de calculo por el cual se genera la cuota
     */
    @XmlElement( nillable = false, required = true)
    private BigDecimal factorCalculo;
    
    /**
     * Cantidad calculada por actualizacion
     */
    private BigDecimal actualizacion;
    
    /**
     * Cantidad que calculada por ercargo
     */
    private BigDecimal recargo;

    /**
     * Cantidad que calculada por ercargo
     */
    @XmlTransient
    private BigDecimal descuento;

    public RamaCalculo() {
    }

    public RamaCalculo(String nombre, String desTipoAportacion, BigDecimal factorCalculo) {
        this.nombre = nombre;
        this.desTipoAportacion = desTipoAportacion;
        this.factorCalculo = factorCalculo;
    }
    
    public RamaCalculo(String nombre, String desTipoAportacion, BigDecimal factorCalculo,
    		Integer idRama, Integer idTipoAportacion) {
        this.nombre = nombre;
        this.desTipoAportacion = desTipoAportacion;
        this.factorCalculo = factorCalculo;
        this.idRama = idRama;
        this.idTipoAportacion = idTipoAportacion;
    }
    
    public RamaCalculo(String nombre, String desTipoAportacion, BigDecimal factorCalculo,
    		long idRama, long idTipoAportacion) {
        this.nombre = nombre;
        this.desTipoAportacion = desTipoAportacion;
        this.factorCalculo = factorCalculo;
        this.idRama = (int)idRama;
        this.idTipoAportacion = (int)idTipoAportacion;
    }
    
    public RamaCalculo(String nombre, String desTipoAportacion, BigDecimal factorCalculo,
    		long idRama, long idTipoAportacion, BigDecimal aportacion) {
        this.nombre = nombre;
        this.desTipoAportacion = desTipoAportacion;
        this.factorCalculo = factorCalculo;
        this.idRama = (int)idRama;
        this.idTipoAportacion = (int)idTipoAportacion;
        this.aportacion = aportacion;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDesTipoAportacion() {
        return desTipoAportacion;
    }

    public void setDesTipoAportacion(String desTipoAportacion) {
        this.desTipoAportacion = desTipoAportacion;
    }

    public BigDecimal getAportacion() {
        return aportacion;
    }

    public void setAportacion(BigDecimal aportacion) {
        this.aportacion = aportacion;
    }    

    /**
	 * @return the idTipoAportacion
	 */
	public Integer getIdTipoAportacion() {
		return idTipoAportacion;
	}

	/**
	 * @param idTipoAportacion the idTipoAportacion to set
	 */
	public void setIdTipoAportacion(Integer idTipoAportacion) {
		this.idTipoAportacion = idTipoAportacion;
	}

	/**
	 * @return the idRama
	 */
	public Integer getIdRama() {
		return idRama;
	}

	/**
	 * @param idRama the idRama to set
	 */
	public void setIdRama(Integer idRama) {
		this.idRama = idRama;
	}
	
	

	/**
     * @return the factorCalculo
     */
    public BigDecimal getFactorCalculo() {
        return factorCalculo;
    }

    /**
     * @param factorCalculo the factorCalculo to set
     */
    public void setFactorCalculo(BigDecimal factorCalculo) {
        this.factorCalculo = factorCalculo;
    }
    
    

    /**
     * @return the actualizacion
     */
    public BigDecimal getActualizacion() {
        return actualizacion;
    }

    /**
     * @param actualizacion the actualizacion to set
     */
    public void setActualizacion(BigDecimal actualizacion) {
        this.actualizacion = actualizacion;
    }

    /**
     * @return the recargo
     */
    public BigDecimal getRecargo() {
        return recargo;
    }

    /**
     * @param recargo the recargo to set
     */
    public void setRecargo(BigDecimal recargo) {
        this.recargo = recargo;
    }

    public String toString() {
        return new ToStringBuilder(this)
        		.append("idRama", idRama)
        		.append("idTipoAportacion", idTipoAportacion)
                .append("nombre", nombre)
                .append("desTipoAportacion", desTipoAportacion)
                .append("aportacion", aportacion)
                .append("factorCalculo", factorCalculo)
                .toString();
    }

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

}
