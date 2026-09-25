package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_PERFIL_USUARIO database table.
 * 
 */
@Entity
@Table(name="DIC_PERFIL_USUARIO")
public class DicPerfilUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_PERFIL_USUARIO", nullable=false, precision=22)
	private long cveIdPerfilUsuario;

	@Column(name="DES_PERFIL_USUARIO", length=100)
	private String desPerfilUsuario;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="TIP_PERFIL", precision=22)
	private BigDecimal tipPerfil;

	//bi-directional many-to-one association to DicPerfilUsuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PERFIL_PADRE")
	private DicPerfilUsuario dicPerfilUsuario;

	//bi-directional many-to-one association to DicPerfilUsuario
	@OneToMany(mappedBy="dicPerfilUsuario")
	private List<DicPerfilUsuario> dicPerfilUsuarios;

	//bi-directional many-to-one association to DitPerfilAccion
	@OneToMany(mappedBy="dicPerfilUsuario")
	private List<DitPerfilAccion> ditPerfilAccions;

	//bi-directional many-to-one association to DitUsuario
	@OneToMany(mappedBy="dicPerfilUsuario")
	private List<DitUsuario> ditUsuarios;

    public DicPerfilUsuario() {
    }

	public long getCveIdPerfilUsuario() {
		return this.cveIdPerfilUsuario;
	}

	public void setCveIdPerfilUsuario(long cveIdPerfilUsuario) {
		this.cveIdPerfilUsuario = cveIdPerfilUsuario;
	}

	public String getDesPerfilUsuario() {
		return this.desPerfilUsuario;
	}

	public void setDesPerfilUsuario(String desPerfilUsuario) {
		this.desPerfilUsuario = desPerfilUsuario;
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

	public BigDecimal getTipPerfil() {
		return this.tipPerfil;
	}

	public void setTipPerfil(BigDecimal tipPerfil) {
		this.tipPerfil = tipPerfil;
	}

	public DicPerfilUsuario getDicPerfilUsuario() {
		return this.dicPerfilUsuario;
	}

	public void setDicPerfilUsuario(DicPerfilUsuario dicPerfilUsuario) {
		this.dicPerfilUsuario = dicPerfilUsuario;
	}
	
	public List<DicPerfilUsuario> getDicPerfilUsuarios() {
		return this.dicPerfilUsuarios;
	}

	public void setDicPerfilUsuarios(List<DicPerfilUsuario> dicPerfilUsuarios) {
		this.dicPerfilUsuarios = dicPerfilUsuarios;
	}
	
	public List<DitPerfilAccion> getDitPerfilAccions() {
		return this.ditPerfilAccions;
	}

	public void setDitPerfilAccions(List<DitPerfilAccion> ditPerfilAccions) {
		this.ditPerfilAccions = ditPerfilAccions;
	}
	
	public List<DitUsuario> getDitUsuarios() {
		return this.ditUsuarios;
	}

	public void setDitUsuarios(List<DitUsuario> ditUsuarios) {
		this.ditUsuarios = ditUsuarios;
	}
	
}