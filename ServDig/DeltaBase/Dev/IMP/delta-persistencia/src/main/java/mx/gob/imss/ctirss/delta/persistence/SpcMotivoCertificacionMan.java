package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the SPC_MOTIVO_CERTIFICACION_MAN database table.
 * 
 */
@Entity
@Table(name="SPC_MOTIVO_CERTIFICACION_MAN")
@NamedQuery(name="SpcMotivoCertificacionMan.findAll", query="SELECT s FROM SpcMotivoCertificacionMan s")
public class SpcMotivoCertificacionMan implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SPC_MOTIVO_CERTIFICACION_MAN_IDMOTIVOCERTIFICACIONMAN_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SPC_MOTIVO_CERTIFICACION_MAN_IDMOTIVOCERTIFICACIONMAN_GENERATOR")
	@Column(name="ID_MOTIVO_CERTIFICACION_MAN")
	private String idMotivoCertificacionMan;

	@Column(name="DES_MOTIVO_CERTIFICACION_MAN")
	private String desMotivoCertificacionMan;

	//bi-directional many-to-one association to SptCertificadoDerecho
	@OneToMany(mappedBy="spcMotivoCertificacionMan")
	private List<SptCertificadoDerecho> sptCertificadoDerechos;

	public SpcMotivoCertificacionMan() {
	}

	public String getIdMotivoCertificacionMan() {
		return this.idMotivoCertificacionMan;
	}

	public void setIdMotivoCertificacionMan(String idMotivoCertificacionMan) {
		this.idMotivoCertificacionMan = idMotivoCertificacionMan;
	}

	public String getDesMotivoCertificacionMan() {
		return this.desMotivoCertificacionMan;
	}

	public void setDesMotivoCertificacionMan(String desMotivoCertificacionMan) {
		this.desMotivoCertificacionMan = desMotivoCertificacionMan;
	}

	public List<SptCertificadoDerecho> getSptCertificadoDerechos() {
		return this.sptCertificadoDerechos;
	}

	public void setSptCertificadoDerechos(List<SptCertificadoDerecho> sptCertificadoDerechos) {
		this.sptCertificadoDerechos = sptCertificadoDerechos;
	}

	public SptCertificadoDerecho addSptCertificadoDerecho(SptCertificadoDerecho sptCertificadoDerecho) {
		getSptCertificadoDerechos().add(sptCertificadoDerecho);
		sptCertificadoDerecho.setSpcMotivoCertificacionMan(this);

		return sptCertificadoDerecho;
	}

	public SptCertificadoDerecho removeSptCertificadoDerecho(SptCertificadoDerecho sptCertificadoDerecho) {
		getSptCertificadoDerechos().remove(sptCertificadoDerecho);
		sptCertificadoDerecho.setSpcMotivoCertificacionMan(null);

		return sptCertificadoDerecho;
	}

}