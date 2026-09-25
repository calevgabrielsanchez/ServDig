package mx.gob.imss.ctirss.correccion.base.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

/**
 * The persistent class for the CRT_DETBASECOT_OM_DET database table.
 * 
 */
@MappedSuperclass
public class AbstractCrtDetBaseCotOmDet extends AbstractModel {

	private static final long serialVersionUID = 1L;
	private Long cveDetBaseCotOmDet;
	private BigInteger crtDetBaseCotOmitida;
	private Integer crcPercepciones;
	private Double imRemuneracion;
	private Integer idConceptoOmitido;

	@Id
	@SequenceGenerator(name = "CVE_DETBASECOTOMDET_GENERATOR", sequenceName = "CRS_CVE_DETBASECOTOMDET")
	@GeneratedValue(generator = "CVE_DETBASECOTOMDET_GENERATOR")
	@Column(name = "CVE_DETBASECOTOMDET")
	public Long getCveDetBaseCotOmDet() {
		return cveDetBaseCotOmDet;
	}

	public void setCveDetBaseCotOmDet(Long cveDetBaseCotOmDet) {
		this.cveDetBaseCotOmDet = cveDetBaseCotOmDet;
	}

	@Column(name = "CVE_DETBASECOTOMIT")
	public BigInteger getCrtDetBaseCotOmitida() {
		return crtDetBaseCotOmitida;
	}

	public void setCrtDetBaseCotOmitida(BigInteger crtDetBaseCotOmitida) {
		this.crtDetBaseCotOmitida = crtDetBaseCotOmitida;
	}

	@Column(name = "CVE_PERCEPCION")
	public Integer getCrcPercepciones() {
		return crcPercepciones;
	}

	public void setCrcPercepciones(Integer crcPercepciones) {
		this.crcPercepciones = crcPercepciones;
	}

	@Column(name = "IM_REMUNERACION")
	public Double getImRemuneracion() {
		return imRemuneracion;
	}

	public void setImRemuneracion(Double imRemuneracion) {
		this.imRemuneracion = imRemuneracion;
	}

	@Column(name = "ID_CONCEPTO_OMITIDO")
	public Integer getIdConceptoOmitido() {
		return idConceptoOmitido;
	}

	public void setIdConceptoOmitido(Integer idConceptoOmitido) {
		this.idConceptoOmitido = idConceptoOmitido;
	}

}
