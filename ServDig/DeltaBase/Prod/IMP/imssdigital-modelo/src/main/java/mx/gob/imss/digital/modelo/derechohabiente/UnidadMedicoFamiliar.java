package mx.gob.imss.digital.modelo.derechohabiente;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.digital.modelo.domicilio.Subdelegacion;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "unidadMedicoFamiliar", namespace = "mx.gob.imss.digital.modelo.derechohabiente")
@XmlRootElement(name = "unidadMedicoFamiliar", namespace = "mx.gob.imss.digital.modelo.derechohabiente")
public class UnidadMedicoFamiliar implements Serializable {
	private static final long serialVersionUID = 1L;
	private Subdelegacion subdelegacion;
	private TipoUMF tipoUMF;
	private ClavePresupuestal clavePresupuestal;
	private NivelAtencion nivelAtencion;
	private long idUMF;
	private String descripcion;
	private BigDecimal generacionCita;
	private BigDecimal noConsultorio;
	private String nombreCorto;
	private BigDecimal noEconomico;
	private String desDireccion;

	public Subdelegacion getSubdelegacion() {
		return subdelegacion;
	}

	public void setSubdelegacion(Subdelegacion subdelegacion) {
		this.subdelegacion = subdelegacion;
	}

	public TipoUMF getTipoUMF() {
		return tipoUMF;
	}

	public void setTipoUMF(TipoUMF tipoUMF) {
		this.tipoUMF = tipoUMF;
	}

	public ClavePresupuestal getClavePresupuestal() {
		return clavePresupuestal;
	}

	public void setClavePresupuestal(ClavePresupuestal clavePresupuestal) {
		this.clavePresupuestal = clavePresupuestal;
	}

	public NivelAtencion getNivelAtencion() {
		return nivelAtencion;
	}

	public void setNivelAtencion(NivelAtencion nivelAtencion) {
		this.nivelAtencion = nivelAtencion;
	}

	public long getIdUMF() {
		return idUMF;
	}

	public void setIdUMF(long idUMF) {
		this.idUMF = idUMF;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getGeneracionCita() {
		return generacionCita;
	}

	public void setGeneracionCita(BigDecimal generacionCita) {
		this.generacionCita = generacionCita;
	}

	public BigDecimal getNoConsultorio() {
		return noConsultorio;
	}

	public void setNoConsultorio(BigDecimal noConsultorio) {
		this.noConsultorio = noConsultorio;
	}

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public BigDecimal getNoEconomico() {
		return noEconomico;
	}

	public void setNoEconomico(BigDecimal noEconomico) {
		this.noEconomico = noEconomico;
	}

	public String getDesDireccion() {
		return desDireccion;
	}

	public void setDesDireccion(String desDireccion) {
		this.desDireccion = desDireccion;
	}

}
