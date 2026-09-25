package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;


/**
 * The persistent class for the DIT_PERSONA database table.
 * 
 */
@Entity
@Table(name="DIT_PERSONA")
public class DitPersona implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1515601554724454209L;

	@Id
    @SequenceGenerator(name = "DIT_PERSONA_CVEIDPERSONA_GENERATOR", sequenceName = "SEQ_DITPERSONA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_PERSONA_CVEIDPERSONA_GENERATOR")
	@Column(name="CVE_ID_PERSONA", nullable=false, precision=22)
	private Long cveIdPersona;

	@Column(name="CURP", length=50)
	private String curp;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_DEFUNCION")
	private Date fecDefuncion;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_NACIMIENTO")
	private Date fecNacimiento;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_PER_AUTORIZADA", precision=22)
	private BigDecimal indPerAutorizada;

	@Column(name="NOM_NOMBRE", length=255)
	private String nomNombre;

	@Column(name="NOM_PRIMER_APELLIDO", length=255)
	private String nomPrimerApellido;

	@Column(name="NOM_SEGUNDO_APELLIDO", length=255)
	private String nomSegundoApellido;

	@Column(length=255)
	private String observaciones;

	@Column(length=50)
	private String rfc;
	
	@Column(name="NUM_ANIO_NAC_REG", precision=22)
	private Integer numAnioNacReg;
	
	@Column(name="NUM_MES_NAC_REG", precision=22)
	private Integer numMesNacReg;

	//bi-directional many-to-one association to DitAsignacionNss
	@OneToMany(mappedBy="ditPersona", fetch=FetchType.EAGER)
	@Where(clause = "FEC_REGISTRO_BAJA is null")
	private List<DitAsignacionNss> ditAsignacionNsses;
	
	//bi-directional many-to-one association to DitAsignacionNss
	
	/*
	@OneToMany(mappedBy="ditPersona")
	*/
	@Transient
	private List<DitBitacoraSegSolicitud> ditBitacoraSegSolicitudes;
	
	
	//bi-directional many-to-one association to DitAsignacionNss
	/*
	@OneToMany(mappedBy="ditPersona")*/
	@Transient
	private List<DitBitacoraSegTramite> ditBitacoraSegTramites;
	
	//bi-directional many-to-one association to DitAsigNssParentesco
	@OneToMany(mappedBy="ditPersona", fetch=FetchType.LAZY)
	private List<DitAsigNssParentesco> ditAsigNssParentescos;

	//bi-directional one-to-one association to DitDerechohabiente
	@OneToMany(mappedBy="ditPersona", fetch=FetchType.LAZY)
	private List<DitPersonaDerechohabiente> ditPersonaDerechohabientes;

	
	//TODO VALIDAR LA FORMA EN QUE SE RECUPERAN LOS DATOS
	//bi-directional many-to-many association to DitDocumentoProbatorio
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
		name="DIT_DOCTOS_PERSONA"
		, joinColumns={
			@JoinColumn(name="CVE_ID_PERSONA")
			}
		, inverseJoinColumns={
			@JoinColumn(name="CVE_ID_DOCUMENTO_PROBATORIO")
			}
		)
	private List<DitDocumentoProbatorio> ditDocumentoProbatorios;

	//bi-directional many-to-one association to DitFormaMigratoria
	@OneToMany(mappedBy="ditPersona", fetch=FetchType.LAZY)
	private List<DitFormaMigratoria> ditFormaMigratorias;

	//bi-directional many-to-one association to DitHistEstadoPersona
	@OneToMany(mappedBy="ditPersona")
	private List<DitHistEstadoPersona> ditHistEstadoPersonas;

	//bi-directional many-to-one association to DitHistPersonaCalificacion
	@OneToMany(mappedBy="ditPersona")
	private List<DitHistPersonaCalificacion> ditHistPersonaCalificacions;

	//bi-directional many-to-one association to DgCatEstado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ENT")
	private DgCatEstado dgCatEstado;

	//bi-directional many-to-one association to DicEstadoCivil
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_ESTADO_CIVIL")
	private DicEstadoCivil dicEstadoCivil;

	//bi-directional many-to-one association to DicSexo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_SEXO")
	private DicSexo dicSexo;

	//bi-directional many-to-one association to DicPai
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PAIS")
	private DicPai dicPai;

	//bi-directional many-to-one association to DitPersonafContacto
	@OneToMany(mappedBy="ditPersona")
	private List<DitPersonafContacto> ditPersonafContactos;

	//bi-directional many-to-one association to DitPersonafDom
	@OneToMany(mappedBy="ditPersona",  fetch=FetchType.LAZY)
	private List<DitPersonafDom> ditPersonafDoms;

	//bi-directional many-to-one association to DitPersonaDefuncion
	@OneToMany(mappedBy="ditPersona")
	private List<DitPersonaDefuncion> ditPersonaDefuncions;

	//bi-directional many-to-one association to DitPersonaDomExtranjero
	@OneToMany(mappedBy="ditPersona")
	private List<DitPersonaDomExtranjero> ditPersonaDomExtranjeros;

	//bi-directional many-to-one association to DitPersonaFisica
	@OneToMany(mappedBy="ditPersona")
	private List<DitPersonaFisica> ditPersonaFisicas;

	//bi-directional many-to-one association to DitPresentadorAviso
	@OneToMany(mappedBy="ditPersona")
	private List<DitPresentadorAviso> ditPresentadorAvisos;

	//bi-directional many-to-one association to DitRepresentanteLegal
	@OneToMany(mappedBy="ditPersona")
	private List<DitRepresentanteLegal> ditRepresentanteLegals;

	

	//bi-directional many-to-one association to DitTramitePersonaFisica
	@OneToMany(mappedBy="ditPersona")
	private List<DitTramitePersonaFisica> ditTramitePersonaFisicas;

	//bi-directional many-to-one association to DitUsuario
	@OneToMany(mappedBy="ditPersona")
	private List<DitUsuario> ditUsuarios;

	//bi-directional many-to-one association to DitPersonaInteresadaSol
	@OneToMany(mappedBy="ditPersona")
	private List<DitPersonaInteresadaSol> ditPersonaInteresadaSols;
	
	
	//bi-directional many-to-one association to DitPersonaFisica
	@OneToMany(mappedBy="ditPersona")
	private List<DitIdentificador> ditIdentificadores;

	//bi-directional many-to-one association to DitLlavePatron
	@OneToMany(mappedBy="ditPersona" , fetch=FetchType.LAZY)
	private List<DitLlavePatron> ditLlavePatrones;

	// bi-directional many-to-one association to DitLlaveAsegurado
	@OneToMany(mappedBy = "ditPersona" , fetch=FetchType.LAZY)
	private List<DitLlaveAsegurado> ditLlaveAsegurados;

	//bi-directional many-to-one association to DitPersonaFisica
//	@OneToMany(mappedBy="ditPersona")
//	private List<DicTipoIdentificador> dicTiposIdentificador;
	
	//bi-directional many-to-one association to DitPersonaBeneficio
	@OneToMany(mappedBy="ditPersona", fetch=FetchType.LAZY)
	private List<DitPersonaBeneficio> ditPersonaBeneficios;
	
	//bi-directional many-to-one association to DitSeguroIvro
	@OneToMany(mappedBy="ditPersona")
	private List<DitSeguroIvro> ditSeguroIvros;

	
    public DitPersona() {
    }
    
	public DitPersona(Long cveIdPersona) {
		super();
		this.cveIdPersona = cveIdPersona;
	}
	
	public Long getCveIdPersona() {
		return this.cveIdPersona;
	}

	public void setCveIdPersona(Long cveIdPersona) {
		this.cveIdPersona = cveIdPersona;
	}

	public String getCurp() {
		return this.curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public Date getFecDefuncion() {
		return this.fecDefuncion;
	}

	public void setFecDefuncion(Date fecDefuncion) {
		this.fecDefuncion = fecDefuncion;
	}

	public Date getFecNacimiento() {
		return this.fecNacimiento;
	}

	public void setFecNacimiento(Date fecNacimiento) {
		this.fecNacimiento = fecNacimiento;
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

	public BigDecimal getIndPerAutorizada() {
		return this.indPerAutorizada;
	}

	public void setIndPerAutorizada(BigDecimal indPerAutorizada) {
		this.indPerAutorizada = indPerAutorizada;
	}

	public String getNomNombre() {
		return this.nomNombre;
	}

	public void setNomNombre(String nomNombre) {
		this.nomNombre = nomNombre;
	}

	public String getNomPrimerApellido() {
		return this.nomPrimerApellido;
	}

	public void setNomPrimerApellido(String nomPrimerApellido) {
		this.nomPrimerApellido = nomPrimerApellido;
	}

	public String getNomSegundoApellido() {
		return this.nomSegundoApellido;
	}

	public void setNomSegundoApellido(String nomSegundoApellido) {
		this.nomSegundoApellido = nomSegundoApellido;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getRfc() {
		return this.rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	
	public List<DitAsignacionNss> getDitAsignacionNsses() {
		return this.ditAsignacionNsses;
	}

	public void setDitAsignacionNsses(List<DitAsignacionNss> ditAsignacionNsses) {
		this.ditAsignacionNsses = ditAsignacionNsses;
	}
	
	public List<DitAsigNssParentesco> getDitAsigNssParentescos() {
		return this.ditAsigNssParentescos;
	}

	public void setDitAsigNssParentescos(List<DitAsigNssParentesco> ditAsigNssParentescos) {
		this.ditAsigNssParentescos = ditAsigNssParentescos;
	}
	
	public List<DitDocumentoProbatorio> getDitDocumentoProbatorios() {
	    if(ditDocumentoProbatorios == null) {
	        ditDocumentoProbatorios = new ArrayList<DitDocumentoProbatorio>();
	    }
		return this.ditDocumentoProbatorios;
	}

	public void setDitDocumentoProbatorios(List<DitDocumentoProbatorio> ditDocumentoProbatorios) {
		this.ditDocumentoProbatorios = ditDocumentoProbatorios;
	}
	
	public List<DitFormaMigratoria> getDitFormaMigratorias() {
		return this.ditFormaMigratorias;
	}

	public void setDitFormaMigratorias(List<DitFormaMigratoria> ditFormaMigratorias) {
		this.ditFormaMigratorias = ditFormaMigratorias;
	}
	
	public List<DitHistEstadoPersona> getDitHistEstadoPersonas() {
	    if(ditHistEstadoPersonas == null) {
	        ditHistEstadoPersonas = new ArrayList<DitHistEstadoPersona>();
	    }
		return this.ditHistEstadoPersonas;
	}

	public void setDitHistEstadoPersonas(List<DitHistEstadoPersona> ditHistEstadoPersonas) {
		this.ditHistEstadoPersonas = ditHistEstadoPersonas;
	}
	
	public List<DitHistPersonaCalificacion> getDitHistPersonaCalificacions() {
	    if(ditHistPersonaCalificacions == null) {
	        ditHistPersonaCalificacions = new ArrayList<DitHistPersonaCalificacion>();
	    }
		return this.ditHistPersonaCalificacions;
	}

	public void setDitHistPersonaCalificacions(List<DitHistPersonaCalificacion> ditHistPersonaCalificacions) {
		this.ditHistPersonaCalificacions = ditHistPersonaCalificacions;
	}
	
	public DgCatEstado getDgCatEstado() {
		return this.dgCatEstado;
	}

	public void setDgCatEstado(DgCatEstado dgCatEstado) {
		this.dgCatEstado = dgCatEstado;
	}
	
	public DicEstadoCivil getDicEstadoCivil() {
		return this.dicEstadoCivil;
	}

	public void setDicEstadoCivil(DicEstadoCivil dicEstadoCivil) {
		this.dicEstadoCivil = dicEstadoCivil;
	}
	
	public DicSexo getDicSexo() {
		return this.dicSexo;
	}

	public void setDicSexo(DicSexo dicSexo) {
		this.dicSexo = dicSexo;
	}
	
	public DicPai getDicPai() {
		return this.dicPai;
	}

	public void setDicPai(DicPai dicPai) {
		this.dicPai = dicPai;
	}
	
	public List<DitPersonafContacto> getDitPersonafContactos() {
	    if(ditPersonafContactos == null) {
	        ditPersonafContactos = new ArrayList<DitPersonafContacto>();
	    }
		return this.ditPersonafContactos;
	}

	public void setDitPersonafContactos(List<DitPersonafContacto> ditPersonafContactos) {
		this.ditPersonafContactos = ditPersonafContactos;
	}
	
	public List<DitPersonafDom> getDitPersonafDoms() {
	    if(ditPersonafDoms == null) {
	        ditPersonafDoms = new ArrayList<DitPersonafDom>();
	    }
		return this.ditPersonafDoms;
	}

	public void setDitPersonafDoms(List<DitPersonafDom> ditPersonafDoms) {
		this.ditPersonafDoms = ditPersonafDoms;
	}
	
	public List<DitPersonaDefuncion> getDitPersonaDefuncions() {
	    if(ditPersonaDefuncions == null) {
	        ditPersonaDefuncions = new ArrayList<DitPersonaDefuncion>();
	    }
		return this.ditPersonaDefuncions;
	}

	public void setDitPersonaDefuncions(List<DitPersonaDefuncion> ditPersonaDefuncions) {
		this.ditPersonaDefuncions = ditPersonaDefuncions;
	}
	
	public List<DitPersonaDomExtranjero> getDitPersonaDomExtranjeros() {
	    if(ditPersonaDomExtranjeros == null) {
	        ditPersonaDomExtranjeros = new ArrayList<DitPersonaDomExtranjero>();
	    }
		return this.ditPersonaDomExtranjeros;
	}

	public void setDitPersonaDomExtranjeros(List<DitPersonaDomExtranjero> ditPersonaDomExtranjeros) {
		this.ditPersonaDomExtranjeros = ditPersonaDomExtranjeros;
	}
	
	public List<DitPersonaFisica> getDitPersonaFisicas() {
	    if(ditPersonaFisicas == null) {
	        ditPersonaFisicas = new ArrayList<DitPersonaFisica>();
	    }
		return this.ditPersonaFisicas;
	}

	public void setDitPersonaFisicas(List<DitPersonaFisica> ditPersonaFisicas) {
		this.ditPersonaFisicas = ditPersonaFisicas;
	}
	
	public List<DitPresentadorAviso> getDitPresentadorAvisos() {
	    if(ditPresentadorAvisos == null) {
	        ditPresentadorAvisos = new ArrayList<DitPresentadorAviso>();
	    }
		return this.ditPresentadorAvisos;
	}

	public void setDitPresentadorAvisos(List<DitPresentadorAviso> ditPresentadorAvisos) {
		this.ditPresentadorAvisos = ditPresentadorAvisos;
	}
	
	public List<DitRepresentanteLegal> getDitRepresentanteLegals() {
		return this.ditRepresentanteLegals;
	}

	public void setDitRepresentanteLegals(List<DitRepresentanteLegal> ditRepresentanteLegals) {
		this.ditRepresentanteLegals = ditRepresentanteLegals;
	}
	
	
	
	public List<DitTramitePersonaFisica> getDitTramitePersonaFisicas() {
		return this.ditTramitePersonaFisicas;
	}

	public void setDitTramitePersonaFisicas(List<DitTramitePersonaFisica> ditTramitePersonaFisicas) {
		this.ditTramitePersonaFisicas = ditTramitePersonaFisicas;
	}
	
	public List<DitUsuario> getDitUsuarios() {
		return this.ditUsuarios;
	}

	public void setDitUsuarios(List<DitUsuario> ditUsuarios) {
		this.ditUsuarios = ditUsuarios;
	}
	
	
	public List<DitIdentificador> getDitIdentificadores() {
	    if(ditIdentificadores == null) {
	    	ditIdentificadores = new ArrayList<DitIdentificador>();
	    }
		return this.ditIdentificadores;
	}

	public void setDitIdentificadores(List<DitIdentificador> ditIdentificadores) {
		this.ditIdentificadores = ditIdentificadores;
	}
	
//	public List<DicTipoIdentificador> getDicTiposIdentificador() {
//	    if(dicTiposIdentificador == null) {
//	    	dicTiposIdentificador = new ArrayList<DicTipoIdentificador>();
//	    }
//		return this.dicTiposIdentificador;
//	}
//	
//	public void setDicTiposIdentificador(List<DicTipoIdentificador> dicTiposIdentificador) {
//		this.dicTiposIdentificador = dicTiposIdentificador;
//	}

	
	@Transient
    public String getDicEstadoPersonaAsString() {
        final StringBuilder estadoStrB = new StringBuilder();

        if (ditHistEstadoPersonas != null && !ditHistEstadoPersonas.isEmpty()) {
            for (DitHistEstadoPersona ditHistEstadoPersona : ditHistEstadoPersonas) {
                DicEstadoPersona dicEstadoPersonaInstance = ditHistEstadoPersona.getDicEstadoPersona();
                if (dicEstadoPersonaInstance != null && dicEstadoPersonaInstance.getDesEstadoPersona() != null) {
                    estadoStrB.append(dicEstadoPersonaInstance.getDesEstadoPersona());
                    estadoStrB.append(", ");
                }
            }
        }

        return estadoStrB.toString();
    }

	@Transient
    public String getDicPersonaCalificacionAsString() {
        final StringBuilder califStrB = new StringBuilder();

        if (ditHistPersonaCalificacions != null && !ditHistPersonaCalificacions.isEmpty()) {
            for (DitHistPersonaCalificacion ditHistPersonaCalificacion : ditHistPersonaCalificacions) {
                if (ditHistPersonaCalificacion != null && ditHistPersonaCalificacion.getDicPersonaCalificacion() != null && ditHistPersonaCalificacion.getDicPersonaCalificacion().getDesCalificacion() != null) {
                    califStrB.append(ditHistPersonaCalificacion.getDicPersonaCalificacion().getDesCalificacion());
                    califStrB.append(", ");
                }
            }
        }

        return califStrB.toString();
    }

	/**
	 * @return the ditBitacoraSegSolicitudes
	 */
	
	public List<DitBitacoraSegSolicitud> getDitBitacoraSegSolicitudes() {
		return ditBitacoraSegSolicitudes;
	}
	

	/**
	 * @param ditBitacoraSegSolicitudes the ditBitacoraSegSolicitudes to set
	 */
	
	public void setDitBitacoraSegSolicitudes(
			List<DitBitacoraSegSolicitud> ditBitacoraSegSolicitudes) {
		this.ditBitacoraSegSolicitudes = ditBitacoraSegSolicitudes;
	}
	
	/**
	 * @return the ditBitacoraSegTramites
	 */
	
	public List<DitBitacoraSegTramite> getDitBitacoraSegTramites() {
		return ditBitacoraSegTramites;
	}
	
	/**
	 * @param ditBitacoraSegTramites the ditBitacoraSegTramites to set
	 */
	
	public void setDitBitacoraSegTramites(
			List<DitBitacoraSegTramite> ditBitacoraSegTramites) {
		this.ditBitacoraSegTramites = ditBitacoraSegTramites;
	}
	

	public List<DitLlavePatron> getDitLlavePatrones() {
		return ditLlavePatrones;
	}

	public void setDitLlavePatrones(List<DitLlavePatron> ditLlavePatrones) {
		this.ditLlavePatrones = ditLlavePatrones;
	}

	public List<DitLlaveAsegurado> getDitLlaveAsegurados() {
		return ditLlaveAsegurados;
	}

	public void setDitLlaveAsegurados(List<DitLlaveAsegurado> ditLlaveAsegurados) {
		this.ditLlaveAsegurados = ditLlaveAsegurados;
	}

	public Integer getNumAnioNacReg() {
		return numAnioNacReg;
	}

	public void setNumAnioNacReg(Integer numAnioNacReg) {
		this.numAnioNacReg = numAnioNacReg;
	}

	public Integer getNumMesNacReg() {
		return numMesNacReg;
	}

	public void setNumMesNacReg(Integer numMesNacReg) {
		this.numMesNacReg = numMesNacReg;
	}

	public List<DitPersonaInteresadaSol> getDitPersonaInteresadaSols() {
		return ditPersonaInteresadaSols;
	}

	public void setDitPersonaInteresadaSols(
			List<DitPersonaInteresadaSol> ditPersonaInteresadaSols) {
		this.ditPersonaInteresadaSols = ditPersonaInteresadaSols;
	}
	
	public List<DitPersonaBeneficio> getDitPersonaBeneficios() {
		return this.ditPersonaBeneficios;
	}

	public void setDitPersonaBeneficios(List<DitPersonaBeneficio> ditPersonaBeneficios) {
		this.ditPersonaBeneficios = ditPersonaBeneficios;
	}

	public List<DitSeguroIvro> getDitSeguroIvros() {
		return ditSeguroIvros;
	}

	public void setDitSeguroIvros(List<DitSeguroIvro> ditSeguroIvros) {
		this.ditSeguroIvros = ditSeguroIvros;
	}

	public List<DitPersonaDerechohabiente> getDitPersonaDerechohabientes() {
		return ditPersonaDerechohabientes;
	}

	public void setDitPersonaDerechohabientes(
			List<DitPersonaDerechohabiente> ditPersonaDerechohabientes) {
		this.ditPersonaDerechohabientes = ditPersonaDerechohabientes;
	}

}