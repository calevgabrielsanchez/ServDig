package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
//MVIEW_DIT_PERSONA
@Table(name = "DIV_PERSONA")
public class DitPersonaView implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer cveIdPersona;
	private String curp;
	private Date fecDefuncion;
	private Date fecNacimiento;
	private Date fecRegistroActualizado;
	private Date fecRegistroAlta;
	private Date fecRegistroBaja;
	private BigDecimal indPerAutorizada;
	private String nomNombre;
	private String nomPrimerApellido;
	private String nomSegundoApellido;
	private String observaciones;
	private String rfc;
	private DicPai dicPais;
	private DicSexo dicSexo;
	private DicEstadoCivil dicEstadoCivil;
	private Integer cveEntidadFederativaNac;
    private String nombreEntidadFederativaNac;
    private DgCatEstado entidadNacimiento;
    private Collection<DicEstadoPersona> dicEstadoPersona;
    private List<DitPersonafContacto> ditPersonafContactos;
    private List<DitPersonafDom> ditPersonafDoms;
    private List<DitDocumentoProbatorio> ditDocumentoProbatorios;    
	private List<DitAsignacionNss> ditAsignacionNss;
	private List<DitHistPersonaCalificacion> ditHistPersonaCalificacions;
	private Integer numAnioNacReg;
	private Integer numMesNacReg;
    
	//bi-directional many-to-one association to DitAsignacionNss
	@OneToMany(mappedBy="ditPersonaView", fetch=FetchType.EAGER)
	@Where(clause = "FEC_REGISTRO_BAJA is null")
    public List<DitAsignacionNss> getDitAsignacionNss() {
        return ditAsignacionNss;
    }

    public void setDitAsignacionNss(List<DitAsignacionNss> ditAsignacionNss) {
        this.ditAsignacionNss = ditAsignacionNss;
    }
	
    //bi-directional many-to-one association to DitHistPersonaCalificacion
  	@OneToMany(mappedBy="ditPersonaView")
    public List<DitHistPersonaCalificacion> getDitHistPersonaCalificacions() {
		return ditHistPersonaCalificacions;
	}

	public void setDitHistPersonaCalificacions(
			List<DitHistPersonaCalificacion> ditHistPersonaCalificacions) {
		this.ditHistPersonaCalificacions = ditHistPersonaCalificacions;
	}

	@Id
	@Column(name="CVE_ID_PERSONA", nullable=false, precision=22)
	public Integer getCveIdPersona() {
		return this.cveIdPersona;
	}

	public void setCveIdPersona(final Integer cveIdPersona) {
		this.cveIdPersona = cveIdPersona;
	}


	@Column(length=50)
	public String getCurp() {
		return this.curp;
	}

	public void setCurp(final String curp) {
		this.curp = curp;
	}


    @Temporal(TemporalType.DATE)
	@Column(name="FEC_DEFUNCION")
	public Date getFecDefuncion() {
		return this.fecDefuncion;
	}

	public void setFecDefuncion(final Date fecDefuncion) {
		this.fecDefuncion = fecDefuncion;
	}


    @Temporal(TemporalType.DATE)
	@Column(name="FEC_NACIMIENTO")
	public Date getFecNacimiento() {
		return this.fecNacimiento;
	}

	public void setFecNacimiento(final Date fecNacimiento) {
		this.fecNacimiento = fecNacimiento;
	}


    @Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(final Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}


    @Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(final Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}


    @Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(final Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}


	@Column(name="IND_PER_AUTORIZADA", precision=22)
	public BigDecimal getIndPerAutorizada() {
		return this.indPerAutorizada;
	}

	public void setIndPerAutorizada(final BigDecimal indPerAutorizada) {
		this.indPerAutorizada = indPerAutorizada;
	}


	@Column(name="NOM_NOMBRE", length=255)
	public String getNomNombre() {
		return this.nomNombre;
	}

	public void setNomNombre(final String nomNombre) {
		this.nomNombre = nomNombre;
	}


	@Column(name="NOM_PRIMER_APELLIDO", length=255)
	public String getNomPrimerApellido() {
		return this.nomPrimerApellido;
	}

	public void setNomPrimerApellido(final String nomPrimerApellido) {
		this.nomPrimerApellido = nomPrimerApellido;
	}


	@Column(name="NOM_SEGUNDO_APELLIDO", length=255)
	public String getNomSegundoApellido() {
		return this.nomSegundoApellido;
	}

	public void setNomSegundoApellido(final String nomSegundoApellido) {
		this.nomSegundoApellido = nomSegundoApellido;
	}


	@Column(length=255)
	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(final String observaciones) {
		this.observaciones = observaciones;
	}


	@Column(length=50)
	public String getRfc() {
		return this.rfc;
	}

	public void setRfc(final String rfc) {
		this.rfc = rfc;
	}


	//uni-directional many-to-one association to DicPai
    @ManyToOne
	@JoinColumn(name="CVE_ID_PAIS")
	public DicPai getDicPais() {
		return this.dicPais;
	}

	public void setDicPais(final DicPai dicPais) {
		this.dicPais = dicPais;
	}
	

	//uni-directional many-to-one association to DicSexo
    @ManyToOne
	@JoinColumn(name="CVE_ID_SEXO")
	public DicSexo getDicSexo() {
		return this.dicSexo;
	}

	public void setDicSexo(final DicSexo dicSexo) {
		this.dicSexo = dicSexo;
	}
	

	//uni-directional many-to-one association to DicEstadoCivil
    @ManyToOne
	@JoinColumn(name="CVE_ID_ESTADO_CIVIL")
	public DicEstadoCivil getDicEstadoCivil() {
		return this.dicEstadoCivil;
	}

	public void setDicEstadoCivil(final DicEstadoCivil dicEstadoCivil) {
		this.dicEstadoCivil = dicEstadoCivil;
	}
	
	
    // TODO Eliminar siguientes props de ent fed nac a favor de la relacion nueva a la tabla DgCatEstado.
	@Transient
	public Integer getCveEntidadFederativaNac() {
	    Integer cveEntidadFederativaNacInt = null; // NOPMD
	    if(cveEntidadFederativaNac == null) {
	        if(entidadNacimiento != null) {
	            cveEntidadFederativaNacInt = Integer.parseInt(entidadNacimiento.getCveEnt());
	        }
	    } else {
	        cveEntidadFederativaNacInt = cveEntidadFederativaNac;
	    }
		return cveEntidadFederativaNacInt;
	}
	
	public void setCveEntidadFederativaNac(final Integer cveEntidadFederativaNac) {
		this.cveEntidadFederativaNac = cveEntidadFederativaNac;
	}


	@Transient
    public String getNombreEntidadFederativaNac() {
        String nombreEntidadFederativaNacInt = null; // NOPMD
        if(nombreEntidadFederativaNac == null) {
            if(entidadNacimiento != null) {
                nombreEntidadFederativaNacInt = entidadNacimiento.getNomEnt();
            }
        } else {
            nombreEntidadFederativaNacInt = nombreEntidadFederativaNac;
        }
        return nombreEntidadFederativaNacInt;
    }

    public void setNombreEntidadFederativaNac(final String nombreEntidadFederativaNac) {
        this.nombreEntidadFederativaNac = nombreEntidadFederativaNac;
    }


    //uni-directional many-to-one association to DgCatEstado
    @ManyToOne
    @JoinColumn(name="CVE_ENT")
    public DgCatEstado getEntidadNacimiento() {
        return entidadNacimiento;
    }

    public void setEntidadNacimiento(final DgCatEstado entidadNacimiento) {
        this.entidadNacimiento = entidadNacimiento;
    }

    @ManyToMany
    @JoinTable(name="DIT_HIST_ESTADO_PERSONA",
    joinColumns=@JoinColumn(name="CVE_ID_PERSONA"),
    inverseJoinColumns=@JoinColumn(name="CVE_ESTADO_PERSONA"))
    public Collection<DicEstadoPersona> getDicEstadoPersona() {
        return dicEstadoPersona;
    }

    public void setDicEstadoPersona(Collection<DicEstadoPersona> dicEstadoPersona) {
        this.dicEstadoPersona = dicEstadoPersona;
    }

    //bi-directional many-to-one association to DitPersonafContacto
    @OneToMany(mappedBy="ditPersonaView")
    public List<DitPersonafContacto> getDitPersonafContactos() {
        if(ditPersonafContactos == null) {
            ditPersonafContactos = new ArrayList<DitPersonafContacto>();
        }
        return this.ditPersonafContactos;
    }

    public void setDitPersonafContactos(List<DitPersonafContacto> ditPersonafContactos) {
        this.ditPersonafContactos = ditPersonafContactos;
    }
    
    //bi-directional many-to-one association to DitPersonafDom
    @OneToMany(mappedBy="ditPersonaView")
    public List<DitPersonafDom> getDitPersonafDoms() {
        if(ditPersonafDoms == null) {
            ditPersonafDoms = new ArrayList<DitPersonafDom>();
        }
        return this.ditPersonafDoms;
    }

    public void setDitPersonafDoms(List<DitPersonafDom> ditPersonafDoms) {
        this.ditPersonafDoms = ditPersonafDoms;
    }

    @Transient
    public String getDicEstadoPersonaAsString() {
        final StringBuilder estadoStrB = new StringBuilder();
        
        if(dicEstadoPersona != null && !dicEstadoPersona.isEmpty()) {
            for (DicEstadoPersona dicEstadoPersonaInstance : dicEstadoPersona) {
                if(dicEstadoPersonaInstance != null && dicEstadoPersonaInstance.getDesEstadoPersona() != null) {
                    estadoStrB.append(dicEstadoPersonaInstance.getDesEstadoPersona());
                    estadoStrB.append(", ");
                }
            }
        }
        
        return estadoStrB.toString();
    }

    //bi-directional many-to-many association to DitDocumentoProbatorio
    @ManyToMany(mappedBy="ditPersonasView")
    public List<DitDocumentoProbatorio> getDitDocumentoProbatorios() {
        if(ditDocumentoProbatorios == null) {
            ditDocumentoProbatorios = new ArrayList<DitDocumentoProbatorio>();
        }
        return ditDocumentoProbatorios;
    }

    public void setDitDocumentoProbatorios(List<DitDocumentoProbatorio> ditDocumentoProbatorios) {
        this.ditDocumentoProbatorios = ditDocumentoProbatorios;
    }

    @Column(name="NUM_ANIO_NAC_REG", precision=22)
	public Integer getNumAnioNacReg() {
		return numAnioNacReg;
	}

	public void setNumAnioNacReg(Integer numAnioNacReg) {
		this.numAnioNacReg = numAnioNacReg;
	}


	@Column(name="NUM_MES_NAC_REG", precision=22)
	public Integer getNumMesNacReg() {
		return numMesNacReg;
	}

	public void setNumMesNacReg(Integer numMesNacReg) {
		this.numMesNacReg = numMesNacReg;
	}

}