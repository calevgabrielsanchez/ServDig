package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.delta.framework.base.entity.AbstractEntity;


/**
 * The persistent class for the DIT_PERSONA_MORAL database table.
 * 
 */
@Entity
@Table(name="DIT_PERSONA_MORAL")
public class DitPersonaMoral extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIT_PERSONA_MORAL_CVEIDPERSONAMORAL_GENERATOR", sequenceName="SEQ_DITPERSONAMORAL", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIT_PERSONA_MORAL_CVEIDPERSONAMORAL_GENERATOR")
	@Column(name="CVE_ID_PERSONA_MORAL", unique=true, nullable=false, precision=22)
	private Long cveIdPersonaMoral;

	@Column(name="DENOMINACION_RAZON_SOCIAL", length=255)
	private String denominacionRazonSocial;

	@Column(name="DES_CAMARA_ORGANIZACION", length=255)
	private String desCamaraOrganizacion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_ACREDITADO", precision=22)
	private BigDecimal indAcreditado;

	@Column(name="IND_CONCURSO_MER", precision=22)
	private BigDecimal indConcursoMer;


	@Column(name="RFC", length=50)
	private String rfc;

	//bi-directional many-to-one association to DitActaConstitutiva
	@OneToMany(mappedBy="ditPersonaMoral")
	private List<DitActaConstitutiva> ditActaConstitutivas;

	//bi-directional many-to-one association to DitHistEstadoPersonaMoral
	@OneToMany(mappedBy="ditPersonaMoral")
	private List<DitHistEstadoPersonaMoral> ditHistEstadoPersonaMorals;

	//bi-directional many-to-one association to DitHistPersonaMoralCalific
	@OneToMany(mappedBy="ditPersonaMoral")
	private List<DitHistPersonaMoralCalific> ditHistPersonaMoralCalifics;

	//bi-directional many-to-one association to DitPatronSujetoObligado
	@OneToMany(mappedBy="ditPersonaMoral", fetch=FetchType.LAZY)
	private List<DitPatronSujetoObligado> ditPatronSujetoObligados;

	//bi-directional many-to-one association to DitPersonamContacto
	@OneToMany(mappedBy="ditPersonaMoral")
	private List<DitPersonamContacto> ditPersonamContactos;

	//bi-directional many-to-one association to DitPersonamDom
	@OneToMany(mappedBy="ditPersonaMoral")
	private List<DitPersonamDom> ditPersonamDoms;

	//bi-directional many-to-one association to DicTipoSociedad
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_SOCIEDAD")
	private DicTipoSociedad dicTipoSociedad;

	//bi-directional many-to-one association to DitSindicato
	@OneToMany(mappedBy="ditPersonaMoral")
	private List<DitSindicato> ditSindicatos;

	
	
	@OneToMany(mappedBy="ditPersonaMoral")
	private List<DitTramitePersonaMoral> ditTramitePersonaMorals;
	
	
	//bi-directional many-to-one association to DitSocio
	@OneToMany(mappedBy="ditPersonaMoral")
	private List<DitSocio> ditSocios;

	//bi-directional many-to-one association to DitSocio
	@OneToMany(mappedBy="ditPatron")
	private List<DitSocio> ditPatrones;
	
	//bi-directional many-to-one association to DitPersonaMContactoFiscal
	@OneToMany(mappedBy="ditPersonaMoral")
	private List<DitPersonaMContactoFiscal> ditPersonaMContactoFiscales;

	//bi-directional many-to-one association to DitPersonaMDomFiscal
	@OneToMany(mappedBy="ditPersonaMoral")
	private List<DitPersonaMDomFiscal> ditPersonaMDomFiscales;
	
	//bi-directional many-to-one association to DitSituacionSat
	@OneToMany(mappedBy="ditPersonaMoral")
	private List<DitSituacionSat> ditSituacionesSat;
	
	//bi-directional many-to-one association to DitIdentificadorMoral
	@OneToMany(mappedBy="ditPersonaMoral")
	private List<DitIdentificadorMoral> ditIdentificadores;
	
	//bi-directional many-to-one association to DitDatosPersonaSat
	@OneToMany(mappedBy="ditPersonaMoral")
	private List<DitDatosPersonaSat> ditDatosPersonaSat;

	// bi-directional many-to-one association to DitLlavePatron
	@OneToMany(mappedBy = "ditPersonaMoral", fetch=FetchType.LAZY)
	private List<DitLlavePatron> ditLlavePatrones;
	
	@OneToOne(mappedBy="ditPersonaMoral", cascade = CascadeType.REMOVE)
  	private DitDatosCertificadoFiel ditDatosCertificadoFiel;
		
	public DitPersonaMoral() {
    }

    
    
	public List<DitTramitePersonaMoral> getDitTramitePersonaMorals() {
		return ditTramitePersonaMorals;
	}



	public void setDitTramitePersonaMorals(
			List<DitTramitePersonaMoral> ditTramitePersonaMorals) {
		this.ditTramitePersonaMorals = ditTramitePersonaMorals;
	}



	public Long getCveIdPersonaMoral() {
		return this.cveIdPersonaMoral;
	}

	public void setCveIdPersonaMoral(Long cveIdPersonaMoral) {
		this.cveIdPersonaMoral = cveIdPersonaMoral;
	}

	public String getDenominacionRazonSocial() {
		return this.denominacionRazonSocial;
	}

	public void setDenominacionRazonSocial(String denominacionRazonSocial) {
		this.denominacionRazonSocial = denominacionRazonSocial;
	}

	public String getDesCamaraOrganizacion() {
		return this.desCamaraOrganizacion;
	}

	public void setDesCamaraOrganizacion(String desCamaraOrganizacion) {
		this.desCamaraOrganizacion = desCamaraOrganizacion;
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

	public BigDecimal getIndAcreditado() {
		return this.indAcreditado;
	}

	public void setIndAcreditado(BigDecimal indAcreditado) {
		this.indAcreditado = indAcreditado;
	}

	public BigDecimal getIndConcursoMer() {
		return this.indConcursoMer;
	}

	public void setIndConcursoMer(BigDecimal indConcursoMer) {
		this.indConcursoMer = indConcursoMer;
	}

	public String getRfc() {
		return this.rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public List<DitActaConstitutiva> getDitActaConstitutivas() {
	    if(ditActaConstitutivas == null) {
	        ditActaConstitutivas = new ArrayList<DitActaConstitutiva>();
	    }
		return this.ditActaConstitutivas;
	}

	public void setDitActaConstitutivas(List<DitActaConstitutiva> ditActaConstitutivas) {
		this.ditActaConstitutivas = ditActaConstitutivas;
	}
	
	public List<DitHistEstadoPersonaMoral> getDitHistEstadoPersonaMorals() {
	    if(ditHistEstadoPersonaMorals == null) {
	        ditHistEstadoPersonaMorals = new ArrayList<DitHistEstadoPersonaMoral>();
	    }
		return this.ditHistEstadoPersonaMorals;
	}

	public void setDitHistEstadoPersonaMorals(List<DitHistEstadoPersonaMoral> ditHistEstadoPersonaMorals) {
		this.ditHistEstadoPersonaMorals = ditHistEstadoPersonaMorals;
	}
	
	public List<DitHistPersonaMoralCalific> getDitHistPersonaMoralCalifics() {
	    if(ditHistPersonaMoralCalifics == null) {
	        ditHistPersonaMoralCalifics = new ArrayList<DitHistPersonaMoralCalific>();
	    }
		return this.ditHistPersonaMoralCalifics;
	}

	public void setDitHistPersonaMoralCalifics(List<DitHistPersonaMoralCalific> ditHistPersonaMoralCalifics) {
		this.ditHistPersonaMoralCalifics = ditHistPersonaMoralCalifics;
	}
	
	public List<DitPatronSujetoObligado> getDitPatronSujetoObligados() {
		return this.ditPatronSujetoObligados;
	}

	public void setDitPatronSujetoObligados(List<DitPatronSujetoObligado> ditPatronSujetoObligados) {
		this.ditPatronSujetoObligados = ditPatronSujetoObligados;
	}
	
	public List<DitPersonamContacto> getDitPersonamContactos() {
	    if(ditPersonamContactos == null) {
	        ditPersonamContactos = new ArrayList<DitPersonamContacto>();
	    }
		return this.ditPersonamContactos;
	}

	public void setDitPersonamContactos(List<DitPersonamContacto> ditPersonamContactos) {
		this.ditPersonamContactos = ditPersonamContactos;
	}
	
	public List<DitPersonamDom> getDitPersonamDoms() {
	    if(ditPersonamDoms == null) {
	        ditPersonamDoms = new ArrayList<DitPersonamDom>();
	    }
		return ditPersonamDoms;
	}

	public void setDitPersonamDoms(List<DitPersonamDom> ditPersonamDoms) {
		this.ditPersonamDoms = ditPersonamDoms;
	}
	
	public DicTipoSociedad getDicTipoSociedad() {
		return this.dicTipoSociedad;
	}

	public void setDicTipoSociedad(DicTipoSociedad dicTipoSociedad) {
		this.dicTipoSociedad = dicTipoSociedad;
	}
	
	public List<DitSindicato> getDitSindicatos() {
		return this.ditSindicatos;
	}

	public void setDitSindicatos(List<DitSindicato> ditSindicatos) {
		this.ditSindicatos = ditSindicatos;
	}
	
	

	@Transient
    public String getDicEstadoPersonaAsString() {
        final StringBuilder estadoStrB = new StringBuilder();
        
        if(ditHistEstadoPersonaMorals != null && !ditHistEstadoPersonaMorals.isEmpty()) {
            for (DitHistEstadoPersonaMoral ditHistEstadoPersonaMoral : ditHistEstadoPersonaMorals) {
                DicEstadoPersona dicEstadoPersona = ditHistEstadoPersonaMoral.getDicEstadoPersona();
                if(dicEstadoPersona != null && dicEstadoPersona.getDesEstadoPersona() != null) {
                    estadoStrB.append(dicEstadoPersona.getDesEstadoPersona());
                    estadoStrB.append(", ");
                }
            }
        }
        
        return estadoStrB.toString();
    }

	@Transient
    public String getDicPersonaCalificacionAsString() {
        final StringBuilder califStrB = new StringBuilder();
        
        if(ditHistPersonaMoralCalifics!= null && !ditHistPersonaMoralCalifics.isEmpty()) {
            for (DitHistPersonaMoralCalific ditHistPersonaMoralCalificacion : ditHistPersonaMoralCalifics) {
                DicPersonaCalificacion dicPersonaCalificacion = ditHistPersonaMoralCalificacion.getDicPersonaCalificacion();
                if(dicPersonaCalificacion != null && dicPersonaCalificacion.getDesCalificacion() != null) {
                    califStrB.append(dicPersonaCalificacion.getDesCalificacion());
                    califStrB.append(", ");
                }
            }
        }
        
        return califStrB.toString();
    }



	public List<DitSocio> getDitSocios() {
		return ditSocios;
	}



	public void setDitSocios(List<DitSocio> ditSocios) {
		this.ditSocios = ditSocios;
	}



	public List<DitSocio> getDitPatrones() {
		return ditPatrones;
	}



	public void setDitPatrones(List<DitSocio> ditPatrones) {
		this.ditPatrones = ditPatrones;
	}



	public List<DitPersonaMContactoFiscal> getDitPersonaMContactoFiscales() {
		return ditPersonaMContactoFiscales;
	}

	public void setDitPersonaMContactoFiscales(
			List<DitPersonaMContactoFiscal> ditPersonaMContactoFiscales) {
		this.ditPersonaMContactoFiscales = ditPersonaMContactoFiscales;
	}

	public List<DitPersonaMDomFiscal> getDitPersonaMDomFiscales() {
		return ditPersonaMDomFiscales;
	}

	public void setDitPersonaMDomFiscales(
			List<DitPersonaMDomFiscal> ditPersonaMDomFiscales) {
		this.ditPersonaMDomFiscales = ditPersonaMDomFiscales;
	}

	public List<DitSituacionSat> getDitSituacionesSat() {
		return ditSituacionesSat;
	}

	public void setDitSituacionesSat(List<DitSituacionSat> ditSituacionesSat) {
		this.ditSituacionesSat = ditSituacionesSat;
	}

	public List<DitIdentificadorMoral> getDitIdentificadores() {
		return ditIdentificadores;
	}

	public void setDitIdentificadores(
			List<DitIdentificadorMoral> ditIdentificadores) {
		this.ditIdentificadores = ditIdentificadores;
	}

	public List<DitDatosPersonaSat> getDitDatosPersonaSat() {
		return ditDatosPersonaSat;
	}
	
	public void setDitDatosPersonaSat(List<DitDatosPersonaSat> ditDatosPersonaSat) {
		this.ditDatosPersonaSat = ditDatosPersonaSat;
	}

	public List<DitLlavePatron> getDitLlavePatrones() {
		return ditLlavePatrones;
	}

	public void setDitLlavePatrones(List<DitLlavePatron> ditLlavePatrones) {
		this.ditLlavePatrones = ditLlavePatrones;
	}

	public DitDatosCertificadoFiel getDitDatosCertificadoFiel() {
		return ditDatosCertificadoFiel;
	}

	public void setDitDatosCertificadoFiel(
			DitDatosCertificadoFiel ditDatosCertificadoFiel) {
		this.ditDatosCertificadoFiel = ditDatosCertificadoFiel;
	}

}