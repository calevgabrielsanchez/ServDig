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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the IDT_REPRESENTADOS database table.
 * 
 */
@Entity
@Table(name="IDT_REPRESENTADOS")
public class IdtRepresentado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="IDT_REPRESENTADOS_CVEREPRESENTADOS_GENERATOR", sequenceName="SEQ_REPRESENTADO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="IDT_REPRESENTADOS_CVEREPRESENTADOS_GENERATOR")
	@Column(name="CVE_REPRESENTADOS")
	private Long cveRepresentados;

	//bi-directional many-to-one association to IdrBloqueRegistro
	@OneToMany(cascade=CascadeType.ALL, mappedBy="idtRepresentado")
	private List<IdrBloqueRegistro> idrBloqueRegistros;

	//bi-directional many-to-one association to IdcEstatusRelacion
    @ManyToOne
	@JoinColumn(name="CVE_ESTATUS_RELACION")
	private IdcEstatusRelacion idcEstatusRelacion;

	//bi-directional many-to-one association to IdrPersonasFisica
    @ManyToOne
	@JoinColumn(name="CVE_PERSONA_LEGAL")
	private IdrPersonasFisica representanteLegal;
    
	//bi-directional many-to-one association to IdrPersonasFisica
    @ManyToOne
	@JoinColumn(name="CVE_PERSONA_FISICA")
	private IdrPersonasFisica patronFisica;

	//bi-directional many-to-one association to IdrPersonasMorale
    @ManyToOne
	@JoinColumn(name="CVE_PERSONA_MORAL")
	private IdrPersonasMorale patronMoral;

    
    public IdtRepresentado() {
    }

    
	public Long getCveRepresentados() {
		return cveRepresentados;
	}

	public void setCveRepresentados(Long cveRepresentados) {
		this.cveRepresentados = cveRepresentados;
	}

	public List<IdrBloqueRegistro> getIdrBloqueRegistros() {
		return idrBloqueRegistros;
	}

	public void setIdrBloqueRegistros(List<IdrBloqueRegistro> idrBloqueRegistros) {
		this.idrBloqueRegistros = idrBloqueRegistros;
	}

	public IdcEstatusRelacion getIdcEstatusRelacion() {
		return idcEstatusRelacion;
	}

	public void setIdcEstatusRelacion(IdcEstatusRelacion idcEstatusRelacion) {
		this.idcEstatusRelacion = idcEstatusRelacion;
	}

	public IdrPersonasFisica getRepresentanteLegal() {
		return representanteLegal;
	}

	public void setRepresentanteLegal(IdrPersonasFisica representanteLegal) {
		this.representanteLegal = representanteLegal;
	}

	public IdrPersonasFisica getPatronFisica() {
		return patronFisica;
	}

	public void setPatronFisica(IdrPersonasFisica patronFisica) {
		this.patronFisica = patronFisica;
	}

	public IdrPersonasMorale getPatronMoral() {
		return patronMoral;
	}

	public void setPatronMoral(IdrPersonasMorale patronMoral) {
		this.patronMoral = patronMoral;
	}

	
}