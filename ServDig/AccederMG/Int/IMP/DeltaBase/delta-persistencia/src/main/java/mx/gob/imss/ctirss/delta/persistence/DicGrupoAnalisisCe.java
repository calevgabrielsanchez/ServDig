package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the DIC_GRUPO_ANALISIS_CE database table.
 * 
 */
@Entity
@Table(name="DIC_GRUPO_ANALISIS_CE")
public class DicGrupoAnalisisCe implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_GRUPO_ANALISIS_CE", nullable=false, precision=22)
	private long cveIdGrupoAnalisisCe;

	@Column(name="DES_GRUPO_ANALISIS_CE", length=30)
	private String desGrupoAnalisisCe;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
    
    //bi-directional many-to-one association to DitAnalisisCe
	@OneToMany(mappedBy="dicGrupoAnalisisCe")
	private List<DitAnalisisCe> ditAnalisisCes;

    public DicGrupoAnalisisCe() {
    }

	public long getCveIdGrupoAnalisisCe() {
		return cveIdGrupoAnalisisCe;
	}

	public void setCveIdGrupoAnalisisCe(long cveIdGrupoAnalisisCe) {
		this.cveIdGrupoAnalisisCe = cveIdGrupoAnalisisCe;
	}

	public String getDesGrupoAnalisisCe() {
		return desGrupoAnalisisCe;
	}

	public void setDesGrupoAnalisisCe(String desGrupoAnalisisCe) {
		this.desGrupoAnalisisCe = desGrupoAnalisisCe;
	}

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public List<DitAnalisisCe> getDitAnalisisCes() {
		return ditAnalisisCes;
	}

	public void setDitAnalisisCes(List<DitAnalisisCe> ditAnalisisCes) {
		this.ditAnalisisCes = ditAnalisisCes;
	}

}