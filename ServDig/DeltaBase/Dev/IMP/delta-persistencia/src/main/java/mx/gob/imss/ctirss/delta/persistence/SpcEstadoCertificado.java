package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the SPC_ESTADO_CERTIFICADO database table.
 * 
 */
@Entity
@Table(name="SPC_ESTADO_CERTIFICADO")
@NamedQuery(name="SpcEstadoCertificado.findAll", query="SELECT s FROM SpcEstadoCertificado s")
public class SpcEstadoCertificado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SPC_ESTADO_CERTIFICADO_IDESTADOCERTIFICADO_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SPC_ESTADO_CERTIFICADO_IDESTADOCERTIFICADO_GENERATOR")
	@Column(name="ID_ESTADO_CERTIFICADO")
	private String idEstadoCertificado;

	@Column(name="DES_ESTADO_CERTIFICADO")
	private String desEstadoCertificado;

	//bi-directional many-to-one association to SptCertificadoDerecho
	@OneToMany(mappedBy="spcEstadoCertificado")
	private List<SptCertificadoDerecho> sptCertificadoDerechos;

	public SpcEstadoCertificado() {
	}

	public String getIdEstadoCertificado() {
		return this.idEstadoCertificado;
	}

	public void setIdEstadoCertificado(String idEstadoCertificado) {
		this.idEstadoCertificado = idEstadoCertificado;
	}

	public String getDesEstadoCertificado() {
		return this.desEstadoCertificado;
	}

	public void setDesEstadoCertificado(String desEstadoCertificado) {
		this.desEstadoCertificado = desEstadoCertificado;
	}

	public List<SptCertificadoDerecho> getSptCertificadoDerechos() {
		return this.sptCertificadoDerechos;
	}

	public void setSptCertificadoDerechos(List<SptCertificadoDerecho> sptCertificadoDerechos) {
		this.sptCertificadoDerechos = sptCertificadoDerechos;
	}

	public SptCertificadoDerecho addSptCertificadoDerecho(SptCertificadoDerecho sptCertificadoDerecho) {
		getSptCertificadoDerechos().add(sptCertificadoDerecho);
		sptCertificadoDerecho.setSpcEstadoCertificado(this);

		return sptCertificadoDerecho;
	}

	public SptCertificadoDerecho removeSptCertificadoDerecho(SptCertificadoDerecho sptCertificadoDerecho) {
		getSptCertificadoDerechos().remove(sptCertificadoDerecho);
		sptCertificadoDerecho.setSpcEstadoCertificado(null);

		return sptCertificadoDerecho;
	}

}