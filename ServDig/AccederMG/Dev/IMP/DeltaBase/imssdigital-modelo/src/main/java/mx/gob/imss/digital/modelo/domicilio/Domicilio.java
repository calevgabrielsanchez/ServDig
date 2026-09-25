/**
 * 
 */
package mx.gob.imss.digital.modelo.domicilio;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * MOdelo que representa el domicilio fisico de una persona, o entidad.
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "domicilio", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
@XmlRootElement(name = "domicilio", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
public class Domicilio implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    /**
     * Identificador del doicilio    
     */
    private Long idDomicilio;
    /**
     * Calle
     */
    private String calle;
    /**
     * Colonia
     */
    private String colonia;
    /**
     * numero exterior
     */
    private Integer numExterior1;
    /**
     * numero exterior alfanumerico
     */
    private String numExteriorAlf;
    /**
     * numero exterior
     */
    private Integer numExterior2;
    /**
     * numero interior
     */
    private Integer numInterior;
    /**
     * numero interior alfanumerico
     */
    private String numInteriorAlf;
    
    /**
     * latitud
     */
    private BigDecimal latitud;
    /**
     * longitud
     */
    private BigDecimal longitud;
    /**
     * Codigo postal
     */
    private String codigoPostal;
    /**
     * Localidad a la cual pertenece el domicilio, entidad federativa y municipio
     */
    private Localidad localidad;
    /**
     * Asentamiento a la cual pertenece el domicilio, entidad federativa y municipio
     */
    private Asentamiento asentamiento;
    /**
     * Tipo de domicilio
     */
    private TipoDomicilio tipoDomicilio;
    /**
     * Descripcion del domicilio
     */
    private String descripcion;
    
    /**
     * Contiene información de un camino
     */
    private Camino camino;
    
    /**
     * Contiene los datos de la carretera
     */
    private Carretera carretera;
    
    private Vialidad vialidadPrimaria;
	
	private Vialidad vialidadReferenciaPrimaria;
	
	private Vialidad vialidadReferenciaSecundaria;
	
	private Vialidad vialidadReferenciaPosterior;
	
	private TipoAmbito ambito;
	
    
    /**
     * @return the idDomicilio
     */
    public Long getIdDomicilio() {
        return idDomicilio;
    }

    /**
     * @param idDomicilio the idDomicilio to set
     */
    public void setIdDomicilio(Long idDomicilio) {
        this.idDomicilio = idDomicilio;
    }

    /**
     * @return the calle
     */
    public String getCalle() {
        return calle;
    }

    /**
     * @param calle the calle to set
     */
    public void setCalle(String calle) {
        this.calle = calle;
    }

    /**
     * @return the colonia
     */
    public String getColonia() {
        return colonia;
    }

    /**
     * @param colonia the colonia to set
     */
    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    /**
     * @return the numExterior1
     */
    public Integer getNumExterior1() {
        return numExterior1;
    }

    /**
     * @param numExterior1 the numExterior1 to set
     */
    public void setNumExterior1(Integer numExterior1) {
        this.numExterior1 = numExterior1;
    }

    /**
     * @return the numExteriorAlf
     */
    public String getNumExteriorAlf() {
        return numExteriorAlf;
    }

    /**
     * @param numExteriorAlf the numExteriorAlf to set
     */
    public void setNumExteriorAlf(String numExteriorAlf) {
        this.numExteriorAlf = numExteriorAlf;
    }

    /**
     * @return the numExterior2
     */
    public Integer getNumExterior2() {
        return numExterior2;
    }

    /**
     * @param numExterior2 the numExterior2 to set
     */
    public void setNumExterior2(Integer numExterior2) {
        this.numExterior2 = numExterior2;
    }

    /**
     * @return the numInterior
     */
    public Integer getNumInterior() {
        return numInterior;
    }

    /**
     * @param numInterior the numInterior to set
     */
    public void setNumInterior(Integer numInterior) {
        this.numInterior = numInterior;
    }

    /**
     * @return the numInteriorAlf
     */
    public String getNumInteriorAlf() {
        return numInteriorAlf;
    }

    /**
     * @param numInteriorAlf the numInteriorAlf to set
     */
    public void setNumInteriorAlf(String numInteriorAlf) {
        this.numInteriorAlf = numInteriorAlf;
    }

    /**
     * @return the latitud
     */
    public BigDecimal getLatitud() {
        return latitud;
    }

    /**
     * @param latitud the latitud to set
     */
    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    /**
     * @return the longitud
     */
    public BigDecimal getLongitud() {
        return longitud;
    }

    /**
     * @param longitud the longitud to set
     */
    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    /**
     * @return the codigoPostal
     */
    public String getCodigoPostal() {
        return codigoPostal;
    }

    /**
     * @param codigoPostal the codigoPostal to set
     */
    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    /**
     * @return the localidad
     */
    public Localidad getLocalidad() {
        return localidad;
    }

    /**
     * @param localidad the localidad to set
     */
    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    /**
     * @return the asentamiento
     */
    public Asentamiento getAsentamiento() {
		return asentamiento;
	}

    /**
     * @param asentamiento the asentamiento to set
     */
	public void setAsentamiento(Asentamiento asentamiento) {
		this.asentamiento = asentamiento;
	}

	/**
     * @return the tipoDomicilio
     */
    public TipoDomicilio getTipoDomicilio() {
        return tipoDomicilio;
    }

    /**
     * @param tipoDomicilio the tipoDomicilio to set
     */
    public void setTipoDomicilio(TipoDomicilio tipoDomicilio) {
        this.tipoDomicilio = tipoDomicilio;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

	public Camino getCamino() {
		return camino;
	}

	public void setCamino(Camino camino) {
		this.camino = camino;
	}

	public Carretera getCarretera() {
		return carretera;
	}

	public void setCarretera(Carretera carretera) {
		this.carretera = carretera;
	}

	/**
	 * @return the vialidadPrimaria
	 */
	public Vialidad getVialidadPrimaria() {
		return vialidadPrimaria;
	}

	/**
	 * @param vialidadPrimaria the vialidadPrimaria to set
	 */
	public void setVialidadPrimaria(Vialidad vialidadPrimaria) {
		this.vialidadPrimaria = vialidadPrimaria;
	}

	/**
	 * @return the vialidadReferenciaPrimaria
	 */
	public Vialidad getVialidadReferenciaPrimaria() {
		return vialidadReferenciaPrimaria;
	}

	/**
	 * @param vialidadReferenciaPrimaria the vialidadReferenciaPrimaria to set
	 */
	public void setVialidadReferenciaPrimaria(Vialidad vialidadReferenciaPrimaria) {
		this.vialidadReferenciaPrimaria = vialidadReferenciaPrimaria;
	}

	/**
	 * @return the vialidadReferenciaSecundaria
	 */
	public Vialidad getVialidadReferenciaSecundaria() {
		return vialidadReferenciaSecundaria;
	}

	/**
	 * @param vialidadReferenciaSecundaria the vialidadReferenciaSecundaria to set
	 */
	public void setVialidadReferenciaSecundaria(
			Vialidad vialidadReferenciaSecundaria) {
		this.vialidadReferenciaSecundaria = vialidadReferenciaSecundaria;
	}

	/**
	 * @return the vialidadReferenciaPosterior
	 */
	public Vialidad getVialidadReferenciaPosterior() {
		return vialidadReferenciaPosterior;
	}

	/**
	 * @param vialidadReferenciaPosterior the vialidadReferenciaPosterior to set
	 */
	public void setVialidadReferenciaPosterior(Vialidad vialidadReferenciaPosterior) {
		this.vialidadReferenciaPosterior = vialidadReferenciaPosterior;
	}

	/**
	 * @return the ambito
	 */
	public TipoAmbito getAmbito() {
		return ambito;
	}

	/**
	 * @param ambito the ambito to set
	 */
	public void setAmbito(TipoAmbito ambito) {
		this.ambito = ambito;
	}
	
	
}
