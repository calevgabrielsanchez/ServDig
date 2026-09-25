package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_PERFIL_ACCION database table.
 * 
 */
@Entity
@Table(name="DIT_PERFIL_ACCION")
public class DitPerfilAccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_PERFIL_ACCION", nullable=false, precision=22)
	private long cveIdPerfilAccion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicPerfilUsuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PERFIL_USUARIO")
	private DicPerfilUsuario dicPerfilUsuario;

	//bi-directional many-to-one association to DicMenuAccion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_MENU_ACCION")
	private DicMenuAccion dicMenuAccion;

    public DitPerfilAccion() {
    }

	public long getCveIdPerfilAccion() {
		return this.cveIdPerfilAccion;
	}

	public void setCveIdPerfilAccion(long cveIdPerfilAccion) {
		this.cveIdPerfilAccion = cveIdPerfilAccion;
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

	public DicPerfilUsuario getDicPerfilUsuario() {
		return this.dicPerfilUsuario;
	}

	public void setDicPerfilUsuario(DicPerfilUsuario dicPerfilUsuario) {
		this.dicPerfilUsuario = dicPerfilUsuario;
	}
	
	public DicMenuAccion getDicMenuAccion() {
		return this.dicMenuAccion;
	}

	public void setDicMenuAccion(DicMenuAccion dicMenuAccion) {
		this.dicMenuAccion = dicMenuAccion;
	}
	
}