package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_ESTADO_PERSONA database table.
 * 
 */
@Entity
@Table(name="DIC_ESTADO_PERSONA")
public class DicEstadoPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ESTADO_PERSONA", nullable=false, precision=22)
	private Integer cveEstadoPersona;

	@Column(name="DES_ESTADO_PERSONA", length=20)
	private String desEstadoPersona;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitHistEstadoPersona
	@OneToMany(mappedBy="dicEstadoPersona")
	private List<DitHistEstadoPersona> ditHistEstadoPersonas;

	//bi-directional many-to-one association to DitHistEstadoPersonaMoral
	@OneToMany(mappedBy="dicEstadoPersona")
	private List<DitHistEstadoPersonaMoral> ditHistEstadoPersonaMorals;

    public DicEstadoPersona() {
    }

	public Integer getCveEstadoPersona() {
		return this.cveEstadoPersona;
	}

	public void setCveEstadoPersona(Integer cveEstadoPersona) {
		this.cveEstadoPersona = cveEstadoPersona;
	}

	public String getDesEstadoPersona() {
		return this.desEstadoPersona;
	}

	public void setDesEstadoPersona(String desEstadoPersona) {
		this.desEstadoPersona = desEstadoPersona;
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

	public List<DitHistEstadoPersona> getDitHistEstadoPersonas() {
		return this.ditHistEstadoPersonas;
	}

	public void setDitHistEstadoPersonas(List<DitHistEstadoPersona> ditHistEstadoPersonas) {
		this.ditHistEstadoPersonas = ditHistEstadoPersonas;
	}
	
	public List<DitHistEstadoPersonaMoral> getDitHistEstadoPersonaMorals() {
		return this.ditHistEstadoPersonaMorals;
	}

	public void setDitHistEstadoPersonaMorals(List<DitHistEstadoPersonaMoral> ditHistEstadoPersonaMorals) {
		this.ditHistEstadoPersonaMorals = ditHistEstadoPersonaMorals;
	}
	
}