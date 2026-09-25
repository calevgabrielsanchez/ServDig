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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the IDR_PERSONAS_MORALES database table.
 * 
 */
@Entity
@Table(name="IDR_PERSONAS_MORALES")
public class IdrPersonasMorale implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="IDR_PERSONAS_MORALES_CVEPERSONAMORAL_GENERATOR", sequenceName="SEQ_PERSONA_MORAL", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="IDR_PERSONAS_MORALES_CVEPERSONAMORAL_GENERATOR")
	@Column(name="CVE_PERSONA_MORAL")
	private Long cvePersonaMoral;

	//bi-directional many-to-one association to IdtDatosCertificado
    @ManyToOne(cascade=CascadeType.ALL)
	@JoinColumns({
		@JoinColumn(name="CVE_SERIAL_FIEL", referencedColumnName="CVE_SERIAL_FIEL"),
		@JoinColumn(name="REF_RFC_ASOCIADO", referencedColumnName="REF_RFC_ASOCIADO")
		})
	private IdtDatosCertificado idtDatosCertificado;

	//bi-directional many-to-one association to IdtRegistrosPatronale
	@OneToMany(mappedBy="idrPersonasMorale")
	private List<IdtRegistrosPatronale> idtRegistrosPatronales;

	//bi-directional many-to-one association to IdtRepresentado
	@OneToMany(mappedBy="patronMoral")
	private List<IdtRepresentado> idtRepresentados;

    public IdrPersonasMorale() {
    }

	public Long getCvePersonaMoral() {
		return this.cvePersonaMoral;
	}

	public void setCvePersonaMoral(Long cvePersonaMoral) {
		this.cvePersonaMoral = cvePersonaMoral;
	}

	public IdtDatosCertificado getIdtDatosCertificado() {
		return this.idtDatosCertificado;
	}

	public void setIdtDatosCertificado(IdtDatosCertificado idtDatosCertificado) {
		this.idtDatosCertificado = idtDatosCertificado;
	}
	
	public List<IdtRegistrosPatronale> getIdtRegistrosPatronales() {
		return this.idtRegistrosPatronales;
	}

	public void setIdtRegistrosPatronales(List<IdtRegistrosPatronale> idtRegistrosPatronales) {
		this.idtRegistrosPatronales = idtRegistrosPatronales;
	}
	
	public List<IdtRepresentado> getIdtRepresentados() {
		return this.idtRepresentados;
	}

	public void setIdtRepresentados(List<IdtRepresentado> idtRepresentados) {
		this.idtRepresentados = idtRepresentados;
	}
	
}