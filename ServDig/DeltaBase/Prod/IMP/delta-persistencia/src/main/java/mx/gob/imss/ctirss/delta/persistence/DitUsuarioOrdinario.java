package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@NamedQueries({
	@NamedQuery(name = "getUsuarioOrdinario", 
			query = "select u from DitUsuarioOrdinario u where u.ditUsuario.cveIdUsuario=:idUsuario")
})
/**
 * The persistent class for the DIT_USUARIO_ORDINARIO database table.
 * 
 */
@Entity
@Table(name="DIT_USUARIO_ORDINARIO")
public class DitUsuarioOrdinario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_USUARIO_ORDINARIO", nullable=false, precision=22)
	private long cveIdUsuarioOrdinario;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="TIP_USUARIO_ORDINARIO", precision=22)
	private BigDecimal tipUsuarioOrdinario;

	//bi-directional many-to-one association to DitUsuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_USUARIO")
	private DitUsuario ditUsuario;

	//bi-directional many-to-one association to DitPatronSujetoObligado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO")
	private DitPatronSujetoObligado ditPatronSujetoObligado;

    public DitUsuarioOrdinario() {
    }

	public long getCveIdUsuarioOrdinario() {
		return this.cveIdUsuarioOrdinario;
	}

	public void setCveIdUsuarioOrdinario(long cveIdUsuarioOrdinario) {
		this.cveIdUsuarioOrdinario = cveIdUsuarioOrdinario;
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

	public BigDecimal getTipUsuarioOrdinario() {
		return this.tipUsuarioOrdinario;
	}

	public void setTipUsuarioOrdinario(BigDecimal tipUsuarioOrdinario) {
		this.tipUsuarioOrdinario = tipUsuarioOrdinario;
	}

	public DitUsuario getDitUsuario() {
		return this.ditUsuario;
	}

	public void setDitUsuario(DitUsuario ditUsuario) {
		this.ditUsuario = ditUsuario;
	}
	
	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return this.ditPatronSujetoObligado;
	}

	public void setDitPatronSujetoObligado(DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
	}
	
}