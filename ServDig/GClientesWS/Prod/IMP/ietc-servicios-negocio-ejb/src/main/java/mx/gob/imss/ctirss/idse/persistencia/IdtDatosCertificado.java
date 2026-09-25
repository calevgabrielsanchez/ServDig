package mx.gob.imss.ctirss.idse.persistencia;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the IDT_DATOS_CERTIFICADO database table.
 * 
 */
@Entity
@Table(name="IDT_DATOS_CERTIFICADO")
public class IdtDatosCertificado implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private IdtDatosCertificadoPK id;

	@Column(name="NOM_NOMBRE_COMPLETO")
	private String nomNombreCompleto;

	@Column(name="REF_CORREO_ELECTRONICO")
	private String refCorreoElectronico;

	@Column(name="REF_CURP")
	private String refCurp;

	@Column(name="REF_DOMICILIO_FISCAL")
	private String refDomicilioFiscal;

	@Column(name="REF_NOMBRE_USUARIO")
	private String refNombreUsuario;

	//bi-directional many-to-one association to IdrPersonasFisica
	@OneToMany(mappedBy="idtDatosCertificado")
	private List<IdrPersonasFisica> idrPersonasFisicas;

	//bi-directional many-to-one association to IdrPersonasMorale
	@OneToMany(mappedBy="idtDatosCertificado")
	private List<IdrPersonasMorale> idrPersonasMorales;

	//bi-directional many-to-one association to IdcEstatusFiel
    @ManyToOne
	@JoinColumn(name="CVE_ESTATUS_FIEL")
	private IdcEstatusFiel idcEstatusFiel;

    public IdtDatosCertificado() {
    }

	public IdtDatosCertificadoPK getId() {
		return this.id;
	}

	public void setId(IdtDatosCertificadoPK id) {
		this.id = id;
	}
	
	public String getNomNombreCompleto() {
		return this.nomNombreCompleto;
	}

	public void setNomNombreCompleto(String nomNombreCompleto) {
		this.nomNombreCompleto = nomNombreCompleto;
	}

	public String getRefCorreoElectronico() {
		return this.refCorreoElectronico;
	}

	public void setRefCorreoElectronico(String refCorreoElectronico) {
		this.refCorreoElectronico = refCorreoElectronico;
	}

	public String getRefCurp() {
		return this.refCurp;
	}

	public void setRefCurp(String refCurp) {
		this.refCurp = refCurp;
	}

	public String getRefDomicilioFiscal() {
		return this.refDomicilioFiscal;
	}

	public void setRefDomicilioFiscal(String refDomicilioFiscal) {
		this.refDomicilioFiscal = refDomicilioFiscal;
	}

	public String getRefNombreUsuario() {
		return this.refNombreUsuario;
	}

	public void setRefNombreUsuario(String refNombreUsuario) {
		this.refNombreUsuario = refNombreUsuario;
	}

	public List<IdrPersonasFisica> getIdrPersonasFisicas() {
		return this.idrPersonasFisicas;
	}

	public void setIdrPersonasFisicas(List<IdrPersonasFisica> idrPersonasFisicas) {
		this.idrPersonasFisicas = idrPersonasFisicas;
	}
	
	public List<IdrPersonasMorale> getIdrPersonasMorales() {
		return this.idrPersonasMorales;
	}

	public void setIdrPersonasMorales(List<IdrPersonasMorale> idrPersonasMorales) {
		this.idrPersonasMorales = idrPersonasMorales;
	}
	
	public IdcEstatusFiel getIdcEstatusFiel() {
		return this.idcEstatusFiel;
	}

	public void setIdcEstatusFiel(IdcEstatusFiel idcEstatusFiel) {
		this.idcEstatusFiel = idcEstatusFiel;
	}
	
}