package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@NamedQueries({
	@NamedQuery(name = "getUsuarioFuncionario", 
			query = "select u from DitUsuarioFuncionario u where u.ditUsuario.cveIdUsuario=:idUsuario")
})
/**
 * The persistent class for the DIT_USUARIO_FUNCIONARIO database table.
 * 
 */
@Entity
@Table(name="DIT_USUARIO_FUNCIONARIO")
public class DitUsuarioFuncionario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_USUARIO_FUNCIONARIO", nullable=false, precision=22)
	private long cveIdUsuarioFuncionario;

	@Column(name="DES_CARGO", length=100)
	private String desCargo;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_EXTENSION_CONTACTO", length=50)
	private String numExtensionContacto;

	@Column(name="NUM_LADA_CONTACTO", precision=22)
	private BigDecimal numLadaContacto;

	@Column(name="NUM_TELEFONO_CONTACTO", precision=22)
	private BigDecimal numTelefonoContacto;

	@Column(name="REF_CORREO_ELECTRONICO_TRABAJO", length=255)
	private String refCorreoElectronicoTrabajo;

	@Column(name="TIP_CENTRO_TRABAJO", precision=22)
	private BigDecimal tipCentroTrabajo;

	//bi-directional many-to-one association to DitUsuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_USUARIO")
	private DitUsuario ditUsuario;

	//bi-directional many-to-one association to DicDelegacion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_DELEGACION")
	private DicDelegacion dicDelegacion;

	//bi-directional many-to-one association to DicSubdelegacion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_SUBDELEGACION")
	private DicSubdelegacion dicSubdelegacion;
	
	//bi-directional many-to-one association to DicSubdelegacion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_UMF")
	private DicUmf dicUmf;


    public DitUsuarioFuncionario() {
    }

	public long getCveIdUsuarioFuncionario() {
		return this.cveIdUsuarioFuncionario;
	}

	public void setCveIdUsuarioFuncionario(long cveIdUsuarioFuncionario) {
		this.cveIdUsuarioFuncionario = cveIdUsuarioFuncionario;
	}

	public String getDesCargo() {
		return this.desCargo;
	}

	public void setDesCargo(String desCargo) {
		this.desCargo = desCargo;
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

	public String getNumExtensionContacto() {
		return this.numExtensionContacto;
	}

	public void setNumExtensionContacto(String numExtensionContacto) {
		this.numExtensionContacto = numExtensionContacto;
	}

	public BigDecimal getNumLadaContacto() {
		return this.numLadaContacto;
	}

	public void setNumLadaContacto(BigDecimal numLadaContacto) {
		this.numLadaContacto = numLadaContacto;
	}

	public BigDecimal getNumTelefonoContacto() {
		return this.numTelefonoContacto;
	}

	public void setNumTelefonoContacto(BigDecimal numTelefonoContacto) {
		this.numTelefonoContacto = numTelefonoContacto;
	}

	public String getRefCorreoElectronicoTrabajo() {
		return this.refCorreoElectronicoTrabajo;
	}

	public void setRefCorreoElectronicoTrabajo(String refCorreoElectronicoTrabajo) {
		this.refCorreoElectronicoTrabajo = refCorreoElectronicoTrabajo;
	}

	public BigDecimal getTipCentroTrabajo() {
		return this.tipCentroTrabajo;
	}

	public void setTipCentroTrabajo(BigDecimal tipCentroTrabajo) {
		this.tipCentroTrabajo = tipCentroTrabajo;
	}

	public DitUsuario getDitUsuario() {
		return this.ditUsuario;
	}

	public void setDitUsuario(DitUsuario ditUsuario) {
		this.ditUsuario = ditUsuario;
	}
	
	public DicDelegacion getDicDelegacion() {
		return this.dicDelegacion;
	}

	public void setDicDelegacion(DicDelegacion dicDelegacion) {
		this.dicDelegacion = dicDelegacion;
	}
	
	public DicSubdelegacion getDicSubdelegacion() {
		return this.dicSubdelegacion;
	}

	public void setDicSubdelegacion(DicSubdelegacion dicSubdelegacion) {
		this.dicSubdelegacion = dicSubdelegacion;
	}

	public DicUmf getDicUmf() {
		return dicUmf;
	}

	public void setDicUmf(DicUmf dicUmf) {
		this.dicUmf = dicUmf;
	}
	
	
	
}