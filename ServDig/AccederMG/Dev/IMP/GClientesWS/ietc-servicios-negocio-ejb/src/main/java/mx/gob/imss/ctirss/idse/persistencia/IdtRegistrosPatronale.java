package mx.gob.imss.ctirss.idse.persistencia;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the IDT_REGISTROS_PATRONALES database table.
 * 
 */
@Entity
@Table(name="IDT_REGISTROS_PATRONALES")
public class IdtRegistrosPatronale implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="IDT_REGISTROS_PATRONALES_CVEREGISTROPATRONAL_GENERATOR", sequenceName="SEQ_REG_PATRON", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="IDT_REGISTROS_PATRONALES_CVEREGISTROPATRONAL_GENERATOR")
	@Column(name="CVE_REGISTRO_PATRONAL")
	private Long cveRegistroPatronal;

	@Column(name="REF_DOMICILIO_CENTRO_TRAB")
	private String refDomicilioCentroTrab;

	@Column(name="REF_RAZON_SOCIAL")
	private String refRazonSocial;

	@Column(name="REF_REGISTRO_PATRONAL")
	private String refRegistroPatronal;

	//bi-directional many-to-one association to IdrBloqueRegistro
	@OneToMany(cascade=CascadeType.ALL, mappedBy="idtRegistrosPatronale")
	private List<IdrBloqueRegistro> idrBloqueRegistros;

	//bi-directional one-to-one association to IdtCartasEmpresa
	@OneToOne(cascade=CascadeType.ALL, mappedBy="idtRegistrosPatronale")
	private IdtCartasEmpresa idtCartasEmpresa;

	//bi-directional many-to-one association to IdtHistoricoMovimiento
	@OneToMany(cascade=CascadeType.ALL, mappedBy="idtRegistrosPatronale")
	private List<IdtHistoricoMovimiento> idtHistoricoMovimientos;

	//bi-directional many-to-one association to IdcEstatusRegPat
    @ManyToOne
	@JoinColumn(name="CVE_ESTATUS_REG_PAT")
	private IdcEstatusRegPat idcEstatusRegPat;

	//bi-directional many-to-one association to IdrPersonasFisica
    @ManyToOne
	@JoinColumn(name="CVE_PERSONA_FISICA")
	private IdrPersonasFisica idrPersonasFisica;

	//bi-directional many-to-one association to IdrPersonasMorale
    @ManyToOne
	@JoinColumn(name="CVE_PERSONA_MORAL")
	private IdrPersonasMorale idrPersonasMorale;

    public IdtRegistrosPatronale() {
    }

	public Long getCveRegistroPatronal() {
		return this.cveRegistroPatronal;
	}

	public void setCveRegistroPatronal(Long cveRegistroPatronal) {
		this.cveRegistroPatronal = cveRegistroPatronal;
	}

	public String getRefDomicilioCentroTrab() {
		return this.refDomicilioCentroTrab;
	}

	public void setRefDomicilioCentroTrab(String refDomicilioCentroTrab) {
		this.refDomicilioCentroTrab = refDomicilioCentroTrab;
	}

	public String getRefRazonSocial() {
		return this.refRazonSocial;
	}

	public void setRefRazonSocial(String refRazonSocial) {
		this.refRazonSocial = refRazonSocial;
	}

	public String getRefRegistroPatronal() {
		return this.refRegistroPatronal;
	}

	public void setRefRegistroPatronal(String refRegistroPatronal) {
		this.refRegistroPatronal = refRegistroPatronal;
	}

	public List<IdrBloqueRegistro> getIdrBloqueRegistros() {
		return this.idrBloqueRegistros;
	}

	public void setIdrBloqueRegistros(List<IdrBloqueRegistro> idrBloqueRegistros) {
		this.idrBloqueRegistros = idrBloqueRegistros;
	}
	
	public IdtCartasEmpresa getIdtCartasEmpresa() {
		return this.idtCartasEmpresa;
	}

	public void setIdtCartasEmpresa(IdtCartasEmpresa idtCartasEmpresa) {
		this.idtCartasEmpresa = idtCartasEmpresa;
	}
	
	public List<IdtHistoricoMovimiento> getIdtHistoricoMovimientos() {
		return this.idtHistoricoMovimientos;
	}

	public void setIdtHistoricoMovimientos(List<IdtHistoricoMovimiento> idtHistoricoMovimientos) {
		this.idtHistoricoMovimientos = idtHistoricoMovimientos;
	}
	
	public IdcEstatusRegPat getIdcEstatusRegPat() {
		return this.idcEstatusRegPat;
	}

	public void setIdcEstatusRegPat(IdcEstatusRegPat idcEstatusRegPat) {
		this.idcEstatusRegPat = idcEstatusRegPat;
	}
	
	public IdrPersonasFisica getIdrPersonasFisica() {
		return this.idrPersonasFisica;
	}

	public void setIdrPersonasFisica(IdrPersonasFisica idrPersonasFisica) {
		this.idrPersonasFisica = idrPersonasFisica;
	}
	
	public IdrPersonasMorale getIdrPersonasMorale() {
		return this.idrPersonasMorale;
	}

	public void setIdrPersonasMorale(IdrPersonasMorale idrPersonasMorale) {
		this.idrPersonasMorale = idrPersonasMorale;
	}
	
}