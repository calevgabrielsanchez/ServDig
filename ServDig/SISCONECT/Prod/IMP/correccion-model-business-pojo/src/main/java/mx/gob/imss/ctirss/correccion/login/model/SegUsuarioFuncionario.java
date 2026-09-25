package mx.gob.imss.ctirss.correccion.login.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import mx.gob.imss.ctirss.correccion.catalogos.model.SacDelegacion;
import mx.gob.imss.ctirss.correccion.catalogos.model.SacSubdelegacion;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;


/**
 * The persistent class for the SEG_USUARIO_FUNCIONARIO database table.
 * 
 */
@Entity
@Table(name="SEG_USUARIO_FUNCIONARIO")
public class SegUsuarioFuncionario extends AbstractModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CVE_ID_USUARIO_FUNCIONARIO_GENERATOR", sequenceName="CRS_CVE_ID_USUARIO_FUNCIONARIO")
	@GeneratedValue(generator="CVE_ID_USUARIO_FUNCIONARIO_GENERATOR")
	@Column(name="CVE_ID_USUARIO_FUNCIONARIO")
	private Long cveIdUsuarioFuncionario;

	@Column(name="DES_CARGO")
	private String desCargo;

	@Column(name="NUM_EXTENSION_CONTACTO")
	private String numExtensionContacto;

	@Column(name="NUM_LADA_CONTACTO")
	private Long numLadaContacto;

	@Column(name="NUM_TELEFONO_CONTACTO")
	private Long numTelefonoContacto;

	@Column(name="REF_CORREO_ELECTRONICO_TRABAJO")
	private String refCorreoElectronicoTrabajo;

	@Column(name="TIP_CENTRO_TRABAJO")
	private Integer tipCentroTrabajo;
	
	@Column(name="IND_VIGENCIA")
	private Boolean indVigencia;

	//bi-directional many-to-one association to SegUsuario
    @ManyToOne
	@JoinColumn(name="CVE_ID_USUARIO")
	private SegUsuario segUsuario;

	//bi-directional many-to-one association to SacCargo
    @ManyToOne
	@JoinColumn(name="CVE_FK_CARGO")
	private SacCargo sacCargo;
    
	//bi-directional many-to-one association to SacDelegacion
    @ManyToOne
	@JoinColumn(name="CVE_ID_DELEGACION")
	private SacDelegacion sacDelegacion;

	//bi-directional many-to-one association to SacSubdelegacion
    @ManyToOne
	@JoinColumn(name="CVE_ID_SUBDELEGACION")
	private SacSubdelegacion sacSubdelegacion;

    public SegUsuarioFuncionario() {
    }

	public Long getCveIdUsuarioFuncionario() {
		return this.cveIdUsuarioFuncionario;
	}

	public void setCveIdUsuarioFuncionario(Long cveIdUsuarioFuncionario) {
		this.cveIdUsuarioFuncionario = cveIdUsuarioFuncionario;
	}


	public String getDesCargo() {
		return this.desCargo;
	}

	public void setDesCargo(String desCargo) {
		this.desCargo = desCargo;
	}

	public String getNumExtensionContacto() {
		return this.numExtensionContacto;
	}

	public void setNumExtensionContacto(String numExtensionContacto) {
		this.numExtensionContacto = numExtensionContacto;
	}

	public Long getNumLadaContacto() {
		return this.numLadaContacto;
	}

	public void setNumLadaContacto(Long numLadaContacto) {
		this.numLadaContacto = numLadaContacto;
	}

	public Long getNumTelefonoContacto() {
		return this.numTelefonoContacto;
	}

	public void setNumTelefonoContacto(Long numTelefonoContacto) {
		this.numTelefonoContacto = numTelefonoContacto;
	}

	public String getRefCorreoElectronicoTrabajo() {
		return this.refCorreoElectronicoTrabajo;
	}

	public void setRefCorreoElectronicoTrabajo(String refCorreoElectronicoTrabajo) {
		this.refCorreoElectronicoTrabajo = refCorreoElectronicoTrabajo;
	}

	public Integer getTipCentroTrabajo() {
		return this.tipCentroTrabajo;
	}

	public void setTipCentroTrabajo(Integer tipCentroTrabajo) {
		this.tipCentroTrabajo = tipCentroTrabajo;
	}

	public SegUsuario getSegUsuario() {
		return this.segUsuario;
	}

	public void setSegUsuario(SegUsuario segUsuario) {
		this.segUsuario = segUsuario;
	}
	
	public SacCargo getSacCargo() {
		return this.sacCargo;
	}

	public void setSacCargo(SacCargo sacCargo) {
		this.sacCargo = sacCargo;
	}

	/**
	 * @return the sacDelegacion
	 */
	public SacDelegacion getSacDelegacion() {
		return sacDelegacion;
	}

	/**
	 * @param sacDelegacion the sacDelegacion to set
	 */
	public void setSacDelegacion(SacDelegacion sacDelegacion) {
		this.sacDelegacion = sacDelegacion;
	}

	/**
	 * @return the sacSubdelegacion
	 */
	public SacSubdelegacion getSacSubdelegacion() {
		return sacSubdelegacion;
	}

	/**
	 * @param sacSubdelegacion the sacSubdelegacion to set
	 */
	public void setSacSubdelegacion(SacSubdelegacion sacSubdelegacion) {
		this.sacSubdelegacion = sacSubdelegacion;
	}

	/**
	 * @return the indVigencia
	 */
	public Boolean getIndVigencia() {
		return indVigencia;
	}

	/**
	 * @param indVigencia the indVigencia to set
	 */
	public void setIndVigencia(Boolean indVigencia) {
		this.indVigencia = indVigencia;
	}
	
}