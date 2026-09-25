/**
 * 
 */
package mx.gob.imss.digital.modelo.tramite;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.digital.modelo.cobranza.Compra;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;
import mx.gob.imss.digital.modelo.comun.Modalidad;
import mx.gob.imss.digital.modelo.cuestionario.PersonaCuestionario;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;
import mx.gob.imss.digital.modelo.patron.RegistroPatronal;
import mx.gob.imss.digital.modelo.persona.Fisica;

/**
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tramiteSeguroIvro", namespace = "http://mx.gob.imss.digital.modelo.tramite")
@XmlRootElement(name = "tramiteSeguroIvro", namespace = "http://mx.gob.imss.digital.modelo.tramite")
public class TramiteSeguroIvro extends Tramite implements Serializable{

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Compra realizada
     */
    private Compra compra;
    /**
     * Cotizacion que se genera para el tramite
     */
    private Cotizacion cotizacion;
    
    /**
     * fecha de inicio del seguro
     */
    private Date fechaInicio;
    /**
     * Fecha final del seguro
     */
    private Date fechaFin;
    /**
     * Identificador de la modalidad
     */
    private Modalidad modalidad;
    
    /**
     * beneficiarios del seguro
     */
    private Fisica[] beneficiarios;
    /**
     * Indica si el tramite se trata de una renovacion
     */
    private Boolean renovacion = false;
    /**
     * Si el tramite se trata de un seguro de renovacion, aqui se guardara el id del 
     * seguro que se esta renovando
     */
    private Long idSeguroAnterior;
    
    /**
     * Lista de cuestionarios que se aplicaron en la solicitud del tramite
     */
    private PersonaCuestionario[] cuetionarios;
    /**
     * Propiedad que indica si la compra del seguro aplica cuestionario
     */
    private Boolean aplicaCuestionario;
    /**
     * Nrp de una persona fisica con genete a su cargo, ese elemnto va debido 
     * que los nrp de 35 no existen tal cuel solo son calculados
     */
    private String nrpFisica;
    
    /*
     * Atributos para Seguro Familiar (modalidad 33) y para CVRO (modalidad 40)
     */
    private Fisica solicitante;
	private Domicilio domicilioSeguro;
	private boolean desdeExtranjero;
	// Cuando el solicitante desea asegurarse de forma individual
	private boolean soloSolicitante;
    
    /**
     * Se agrega el registro patronal para las adecuaciones del trámite de seguro doméstico
     * solicitadas por el usuario normativo
     */
    private RegistroPatronal registroPatronal;
    
    /**
     * @return the compra
     */
    public Compra getCompra() {
        return compra;
    }

    /**
     * @param compra the compra to set
     */
    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    /**
     * @return the cotizacion
     */
    public Cotizacion getCotizacion() {
        return cotizacion;
    }

    /**
     * @param cotizacion the cotizacion to set
     */
    public void setCotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
    }    

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the modalidad
     */
    public Modalidad getModalidad() {
        return modalidad;
    }

    /**
     * @param modalidad the modalidad to set
     */
    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    /**
     * @return the beneficiarios
     */
    public Fisica[] getBeneficiarios() {
        return beneficiarios;
    }

    /**
     * @param beneficiarios the beneficiarios to set
     */
    public void setBeneficiarios(Fisica[] beneficiarios) {
        this.beneficiarios = beneficiarios != null ? beneficiarios.clone() : null;
    }

    /**
     * @return the renovacion
     */
    public Boolean getRenovacion() {
        return renovacion;
    }

    /**
     * @param renovacion the renovacion to set
     */
    public void setRenovacion(Boolean renovacion) {
        this.renovacion = renovacion;
    }

    /**
     * @return the idSeguroAnterior
     */
    public Long getIdSeguroAnterior() {
        return idSeguroAnterior;
    }

    /**
     * @param idSeguroAnterior the idSeguroAnterior to set
     */
    public void setIdSeguroAnterior(Long idSeguroAnterior) {
        this.idSeguroAnterior = idSeguroAnterior;
    }

    /**
     * @return the cuetionarios
     */
    public PersonaCuestionario[] getCuetionarios() {
        return cuetionarios;
    }

    /**
     * @param cuetionarios the cuetionarios to set
     */
    public void setCuetionarios(PersonaCuestionario[] cuetionarios) {
        this.cuetionarios = cuetionarios != null ? cuetionarios.clone() : null;
    }

    /**
     * @return the aplicaCuestionario
     */
    public Boolean getAplicaCuestionario() {
        return aplicaCuestionario;
    }

    /**
     * @param aplicaCuestionario the aplicaCuestionario to set
     */
    public void setAplicaCuestionario(Boolean aplicaCuestionario) {
        this.aplicaCuestionario = aplicaCuestionario;
    }

    /**
     * @return the nrpFisica
     */
    public String getNrpFisica() {
        return nrpFisica;
    }

    /**
     * @param nrpFisica the nrpFisica to set
     */
    public void setNrpFisica(String nrpFisica) {
        this.nrpFisica = nrpFisica;
    }

	/**
	 * @return the registroPatronal
	 */
	public RegistroPatronal getRegistroPatronal() {
		return registroPatronal;
	}

	/**
	 * @param registroPatronal the registroPatronal to set
	 */
	public void setRegistroPatronal(RegistroPatronal registroPatronal) {
		this.registroPatronal = registroPatronal;
	}

	/**
	 * @return the solicitante
	 */
	public Fisica getSolicitante() {
		return solicitante;
	}

	/**
	 * @param solicitante the solicitante to set
	 */
	public void setSolicitante(Fisica solicitante) {
		this.solicitante = solicitante;
	}

	/**
	 * @return the domicilioSeguro
	 */
	public Domicilio getDomicilioSeguro() {
		return domicilioSeguro;
	}

	/**
	 * @param domicilioSeguro the domicilioSeguro to set
	 */
	public void setDomicilioSeguro(Domicilio domicilioSeguro) {
		this.domicilioSeguro = domicilioSeguro;
	}

	/**
	 * @return the desdeExtranjero
	 */
	public boolean isDesdeExtranjero() {
		return desdeExtranjero;
	}

	/**
	 * @param desdeExtranjero the desdeExtranjero to set
	 */
	public void setDesdeExtranjero(boolean desdeExtranjero) {
		this.desdeExtranjero = desdeExtranjero;
	}

	/**
	 * @return the soloSolicitante
	 */
	public boolean isSoloSolicitante() {
		return soloSolicitante;
	}

	/**
	 * @param soloSolicitante the soloSolicitante to set
	 */
	public void setSoloSolicitante(boolean soloSolicitante) {
		this.soloSolicitante = soloSolicitante;
	}    
}
