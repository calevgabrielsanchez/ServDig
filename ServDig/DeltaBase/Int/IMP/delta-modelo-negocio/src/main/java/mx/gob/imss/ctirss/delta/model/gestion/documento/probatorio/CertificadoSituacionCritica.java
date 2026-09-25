package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoFamiliar;


@XmlRootElement(name = "CertificadoSituacionCritica")
public class CertificadoSituacionCritica
    extends DocumentoProbatorio implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    protected MedicoFamiliar medicoFamiliar;
    protected String enfermedadPadecida;
    protected Date fechaTerminoIncapacidad;
    protected Date fechaProbableInicio;
    
    public Date getFechaProbableInicio() {
		return fechaProbableInicio;
	}

	public void setFechaProbableInicio(Date fechaProbableInicio) {
		this.fechaProbableInicio = fechaProbableInicio;
	}

	/**
     * Gets the value of the medicoFamiliar property.
     * 
     * @return
     *     possible object is
     *     {@link MedicoFamiliar }
     *     
     */
    public MedicoFamiliar getMedicoFamiliar() {
        return medicoFamiliar;
    }

    /**
     * Sets the value of the medicoFamiliar property.
     * 
     * @param value
     *     allowed object is
     *     {@link MedicoFamiliar }
     *     
     */
    public void setMedicoFamiliar(MedicoFamiliar value) {
        this.medicoFamiliar = value;
    }

    /**
     * Gets the value of the enfermedadPadecida property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnfermedadPadecida() {
        return enfermedadPadecida;
    }

    /**
     * Sets the value of the enfermedadPadecida property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnfermedadPadecida(String value) {
        this.enfermedadPadecida = value;
    }

	/**
	 * @return the fechaTerminoIncapacidad
	 */
	public Date getFechaTerminoIncapacidad() {
		return fechaTerminoIncapacidad;
	}

	/**
	 * @param fechaTerminoIncapacidad the fechaTerminoIncapacidad to set
	 */
	public void setFechaTerminoIncapacidad(Date fechaTerminoIncapacidad) {
		this.fechaTerminoIncapacidad = fechaTerminoIncapacidad;
	}

    
}
