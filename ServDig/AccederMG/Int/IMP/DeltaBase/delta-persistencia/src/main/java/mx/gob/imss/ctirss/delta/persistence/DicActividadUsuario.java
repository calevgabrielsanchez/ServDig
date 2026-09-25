package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_ACTIVIDAD_USUARIO database table.
 * 
 */
@Entity
@Table(name="DIC_ACTIVIDAD_USUARIO")
public class DicActividadUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_ACTIVIDAD_USUARIO", nullable=false, precision=22)
	private long cveIdActividadUsuario;

	@Column(name="DES_ACTIVIDAD_USUARIO", length=100)
	private String desActividadUsuario;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitBitacora
	@OneToMany(mappedBy="dicActividadUsuario")
	private List<DitBitacora> ditBitacoras;

    public DicActividadUsuario() {
    }

	public long getCveIdActividadUsuario() {
		return this.cveIdActividadUsuario;
	}

	public void setCveIdActividadUsuario(long cveIdActividadUsuario) {
		this.cveIdActividadUsuario = cveIdActividadUsuario;
	}

	public String getDesActividadUsuario() {
		return this.desActividadUsuario;
	}

	public void setDesActividadUsuario(String desActividadUsuario) {
		this.desActividadUsuario = desActividadUsuario;
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

	public List<DitBitacora> getDitBitacoras() {
		return this.ditBitacoras;
	}

	public void setDitBitacoras(List<DitBitacora> ditBitacoras) {
		this.ditBitacoras = ditBitacoras;
	}
	
}