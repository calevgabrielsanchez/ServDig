package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the FDC_FRACCION database table.
 * 
 */
@Entity
@Table(name="FDC_FRACCION")
public class FdcFraccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdcFraccionPK id;

	@Column(name="CVE_CLASE", length=5)
	private String cveClase;

	@Column(name="DESC_ACTIVIDAD", length=200)
	private String descActividad;

	@Column(name="NOM_ACTIVIDAD", length=200)
	private String nomActividad;

	@Column(name="NUM_PRIMA_MEDIA", precision=11, scale=5)
	private BigDecimal numPrimaMedia;

	//bi-directional many-to-one association to FdcGrupo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_DIVISION", referencedColumnName="CVE_DIVISION", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="CVE_GRUPO", referencedColumnName="CVE_GRUPO", nullable=false, insertable=false, updatable=false)
		})
	private FdcGrupo fdcGrupo;

    public FdcFraccion() {
    }

	public FdcFraccionPK getId() {
		return this.id;
	}

	public void setId(FdcFraccionPK id) {
		this.id = id;
	}
	
	public String getCveClase() {
		return this.cveClase;
	}

	public void setCveClase(String cveClase) {
		this.cveClase = cveClase;
	}

	public String getDescActividad() {
		return this.descActividad;
	}

	public void setDescActividad(String descActividad) {
		this.descActividad = descActividad;
	}

	public String getNomActividad() {
		return this.nomActividad;
	}

	public void setNomActividad(String nomActividad) {
		this.nomActividad = nomActividad;
	}

	public BigDecimal getNumPrimaMedia() {
		return this.numPrimaMedia;
	}

	public void setNumPrimaMedia(BigDecimal numPrimaMedia) {
		this.numPrimaMedia = numPrimaMedia;
	}

	public FdcGrupo getFdcGrupo() {
		return this.fdcGrupo;
	}

	public void setFdcGrupo(FdcGrupo fdcGrupo) {
		this.fdcGrupo = fdcGrupo;
	}
	
}