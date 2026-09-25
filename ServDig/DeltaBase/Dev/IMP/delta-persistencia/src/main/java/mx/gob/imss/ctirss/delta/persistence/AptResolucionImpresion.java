package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the APT_RESOLUCION_IMPRESION database table.
 * 
 */
@Entity
@Table(name="APT_RESOLUCION_IMPRESION")
@NamedQuery(name="AptResolucionImpresion.findAll", query="SELECT a FROM AptResolucionImpresion a")
public class AptResolucionImpresion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AptResolucionImpresionPK id;

	@Column(name="CONSIDERACION_NEGATIVA")
	private String consideracionNegativa;

	@Column(name="RESOLUTIVO_ADICIONAL")
	private String resolutivoAdicional;

	@Column(name="RESOLUTIVO_ESPECIAL")
	private String resolutivoEspecial;

	@Column(name="RESOLUTIVOS_AUTOMATICOS")
	private String resolutivosAutomaticos;

	@Column(name="RESOLUTIVOS_SEMIAUTOMATICOS")
	private String resolutivosSemiautomaticos;

	//bi-directional many-to-one association to AptDatosImpresion
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="ID_NSS", referencedColumnName="ID_NSS", insertable=false, updatable=false),
		@JoinColumn(name="ID_SOLICITUD", referencedColumnName="ID_SOLICITUD", insertable=false, updatable=false)
		})
	private AptDatosImpresion aptDatosImpresion;

	public AptResolucionImpresion() {
	}

	public AptResolucionImpresionPK getId() {
		return this.id;
	}

	public void setId(AptResolucionImpresionPK id) {
		this.id = id;
	}

	public String getConsideracionNegativa() {
		return this.consideracionNegativa;
	}

	public void setConsideracionNegativa(String consideracionNegativa) {
		this.consideracionNegativa = consideracionNegativa;
	}

	public String getResolutivoAdicional() {
		return this.resolutivoAdicional;
	}

	public void setResolutivoAdicional(String resolutivoAdicional) {
		this.resolutivoAdicional = resolutivoAdicional;
	}

	public String getResolutivoEspecial() {
		return this.resolutivoEspecial;
	}

	public void setResolutivoEspecial(String resolutivoEspecial) {
		this.resolutivoEspecial = resolutivoEspecial;
	}

	public String getResolutivosAutomaticos() {
		return this.resolutivosAutomaticos;
	}

	public void setResolutivosAutomaticos(String resolutivosAutomaticos) {
		this.resolutivosAutomaticos = resolutivosAutomaticos;
	}

	public String getResolutivosSemiautomaticos() {
		return this.resolutivosSemiautomaticos;
	}

	public void setResolutivosSemiautomaticos(String resolutivosSemiautomaticos) {
		this.resolutivosSemiautomaticos = resolutivosSemiautomaticos;
	}

	public AptDatosImpresion getAptDatosImpresion() {
		return this.aptDatosImpresion;
	}

	public void setAptDatosImpresion(AptDatosImpresion aptDatosImpresion) {
		this.aptDatosImpresion = aptDatosImpresion;
	}

}