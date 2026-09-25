package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_PERSONA_CALIFICACION database table.
 * 
 */
@Entity
@Table(name="DIC_PERSONA_CALIFICACION")
public class DicPersonaCalificacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_CALIFICACION", nullable=false, precision=22)
	private Long cveIdCalificacion;

	@Column(name="DES_CALIFICACION", length=50)
	private String desCalificacion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitHistPersonaCalificacion
	@OneToMany(mappedBy="dicPersonaCalificacion")
	private List<DitHistPersonaCalificacion> ditHistPersonaCalificacions;

	//bi-directional many-to-one association to DitHistPersonaMoralCalific
	@OneToMany(mappedBy="dicPersonaCalificacion")
	private List<DitHistPersonaMoralCalific> ditHistPersonaMoralCalifics;

    public DicPersonaCalificacion() {
    }

	public Long getCveIdCalificacion() {
		return this.cveIdCalificacion;
	}

	public void setCveIdCalificacion(Long cveIdCalificacion) {
		this.cveIdCalificacion = cveIdCalificacion;
	}

	public String getDesCalificacion() {
		return this.desCalificacion;
	}

	public void setDesCalificacion(String desCalificacion) {
		this.desCalificacion = desCalificacion;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public List<DitHistPersonaCalificacion> getDitHistPersonaCalificacions() {
		return this.ditHistPersonaCalificacions;
	}

	public void setDitHistPersonaCalificacions(List<DitHistPersonaCalificacion> ditHistPersonaCalificacions) {
		this.ditHistPersonaCalificacions = ditHistPersonaCalificacions;
	}
	
	public List<DitHistPersonaMoralCalific> getDitHistPersonaMoralCalifics() {
		return this.ditHistPersonaMoralCalifics;
	}

	public void setDitHistPersonaMoralCalifics(List<DitHistPersonaMoralCalific> ditHistPersonaMoralCalifics) {
		this.ditHistPersonaMoralCalifics = ditHistPersonaMoralCalifics;
	}
	
}