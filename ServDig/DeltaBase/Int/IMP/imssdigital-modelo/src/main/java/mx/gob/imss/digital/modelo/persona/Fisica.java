package mx.gob.imss.digital.modelo.persona;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.digital.modelo.domicilio.EntidadFederativa;
import mx.gob.imss.digital.modelo.domicilio.Pais;

import org.apache.commons.lang.StringUtils;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fisica", namespace = "http://mx.gob.imss.digital.modelo.persona")
@XmlRootElement(name = "fisica", namespace = "http://mx.gob.imss.digital.modelo.persona")
public class Fisica extends Persona implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 7542724168099821641L;
    
    /**
     * Clave de la persona fisica 
     */
    private Long cveFisica;
    /**
     * nombre de la persona 
     * 
     */
	private String nombre;
    /**
     * Primer apellido de la persona
     */
	private String primerApellido;
    /**
     * segundo apellido de la persona
     */
	private String segundoApellido;
    /**
     * Fecha de nacimiento
     */
	private Date fechaNacimiento;
	/**
	 * Lugar de nacimiento
	 */
	private EntidadFederativa lugarNacimiento;
	/**
	 * Fecha de defuncion
	 */
	private Date fechaDefuncion;
	/**
	 * Estado civil de la persona
	 */
	private EstadoCivil estadoCivil;
	/**
	 * CURP
	 */
	private String curp;
	/**
	 * Pais
	 */
    private Pais pais;
    /**
     * Sexo
     */
    private Sexo sexo;
    /***/
    private String curpRenapo;
    /***/
    private String fechaNacimientoFormateada;
    /***/
    private Date fechaRegistro;
    /***/
    private Date fechaBaja;
    /***/
    private Date fechaModificacion;
    /***/
    private String altaEnImss;
    /***/
    private Integer numeroLineaArchivo;
    /***/
    private String busqAprox;
    /***/
    private String estadosFormateados;
    /***/
    private String subEstadosFormateados;
    /***/
    private Integer mesRegistroNac;
    /***/
    private Integer anioRegistroNac;
    
    /**
     * NUmero de seguridad social
     */
    private String nss;
    /**
     * NUmero de seguridad social
     */
    private String nssCifrado;
    /**
     * Estatus en renapo
     */
    private String estatusRenapo;
    /**
     * Clave de estatus de renapo
     */
    private String cveEstatusRenapo;
    /**
     * Identificador de la modalidad de la persona, si se encuentra asociada a  alguna
     */
    private Long idModalidad;
    
	/**
	 * Constructor por omision
	 */
	public Fisica() {
		super();
		sexo = new Sexo();
		lugarNacimiento = new EntidadFederativa();
	}
    
	/**
	 * 
	 * @param idPersona
	 * @param idPersonaFisica
	 * @param nombre
	 * @param primerApellido
	 * @param segundoApellido
	 * @param rfc
	 * @param curp
	 */
	public Fisica(Long idPersona, Long idPersonaFisica, String nombre, String primerApellido, String segundoApellido, String rfc, String curp) {
		super();
		sexo = new Sexo();
		lugarNacimiento = new EntidadFederativa();
		this.setTipoPersona(new TipoPersona());
		this.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
		this.setIdPersona(idPersona);
		this.cveFisica=idPersonaFisica;
		this.nombre = nombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.setRfc(rfc);
		this.curp=curp;
	}
	
	
	public Long getCveFisica() {
		return cveFisica;
	}

	public void setCveFisica(Long cveFisica) {
		this.cveFisica = cveFisica;
	}

	public String getNss(){
		return nss;
	}
	
	public void setNss(String nss){
		this.nss = nss;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
		
		if (fechaNacimiento != null) {
			this.fechaNacimientoFormateada = dateToStringConFormato(fechaNacimiento, "dd/MM/yyyy");
		}
	}
	public  String dateToStringConFormato(final Date fecha,
            final String formatoFecha) {
        String dateAsString = null;
        if (fecha != null && StringUtils.isNotBlank(formatoFecha)) {
            dateAsString = new SimpleDateFormat(formatoFecha)
                    .format(fecha);
        }
        return dateAsString;
    }

	public EntidadFederativa getLugarNacimiento() {
		return lugarNacimiento;
	}

	public void setLugarNacimiento(EntidadFederativa lugarNacimiento) {
		this.lugarNacimiento = lugarNacimiento;
	}

	public Date getFechaDefuncion() {
		return fechaDefuncion;
	}

	public void setFechaDefuncion(Date fechaDefuncion) {
		this.fechaDefuncion = fechaDefuncion;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getCurpRenapo() {
        return curpRenapo;
    }

    public void setCurpRenapo(String curpRenapo) {
        this.curpRenapo = curpRenapo;
    }

    public String getFechaNacimientoFormateada() {
        return fechaNacimientoFormateada;
    }

    public void setFechaNacimientoFormateada(String fechaNacimientoFormateada) {
        this.fechaNacimientoFormateada = fechaNacimientoFormateada;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getAltaEnImss() {
        return altaEnImss;
    }

    public void setAltaEnImss(String altaEnImss) {
        this.altaEnImss = altaEnImss;
    }

    public Integer getNumeroLineaArchivo() {
        return numeroLineaArchivo;
    }

    public void setNumeroLineaArchivo(Integer numeroLineaArchivo) {
        this.numeroLineaArchivo = numeroLineaArchivo;
    }

    public String getBusqAprox() {
        return busqAprox;
    }

    public void setBusqAprox(String busqAprox) {
        this.busqAprox = busqAprox;
    }

    public String getEstadosFormateados() {
        return estadosFormateados;
    }

    public void setEstadosFormateados(String estadosFormateados) {
        this.estadosFormateados = estadosFormateados;
    }

    public String getSubEstadosFormateados() {
        return subEstadosFormateados;
    }

    public void setSubEstadosFormateados(String subEstadosFormateados) {
        this.subEstadosFormateados = subEstadosFormateados;
    }

    

	/**
	 * @return the estatusRenapo
	 */
	public String getEstatusRenapo() {
		return estatusRenapo;
	}

	/**
	 * @param estatusRenapo the estatusRenapo to set
	 */
	public void setEstatusRenapo(String estatusRenapo) {
		this.estatusRenapo = estatusRenapo;
	}
	
	/**
	 * @return the cveEstatusRenapo
	 */
	public String getCveEstatusRenapo() {
		return cveEstatusRenapo;
	}

	/**
	 * @param cveEstatusRenapo the cveEstatusRenapo to set
	 */
	public void setCveEstatusRenapo(String cveEstatusRenapo) {
		this.cveEstatusRenapo = cveEstatusRenapo;
	}

	public Integer getMesRegistroNac() {
		return mesRegistroNac;
	}

	public void setMesRegistroNac(Integer mesRegistroNac) {
		this.mesRegistroNac = mesRegistroNac;
	}

	public Integer getAnioRegistroNac() {
		return anioRegistroNac;
	}

	public void setAnioRegistroNac(Integer anioRegistroNac) {
		this.anioRegistroNac = anioRegistroNac;
	}	

	public String getNombreCompleto() {
		StringBuffer nombreCompleto = new StringBuffer();
		
		if (StringUtils.isNotEmpty(this.nombre)) {
			nombreCompleto.append(this.nombre.trim());
		}
		
		if (StringUtils.isNotEmpty(this.primerApellido)) {
			nombreCompleto.append(" ");
			nombreCompleto.append(this.primerApellido.trim());
		}
		
		if (StringUtils.isNotEmpty(this.segundoApellido)) {
			nombreCompleto.append(" ");
			nombreCompleto.append(this.segundoApellido.trim());
		}
		
		return nombreCompleto.toString();
		
	}
	
	

	/**
     * @return the nssCifrado
     */
    public String getNssCifrado() {
        return nssCifrado;
    }

    /**
     * @param nssCifrado the nssCifrado to set
     */
    public void setNssCifrado(String nssCifrado) {
        this.nssCifrado = nssCifrado;
    }

    public int hashCodeDatosBasicos() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((curp == null) ? 0 : curp.hashCode());
		result = prime * result
				+ ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
		result = prime * result
				+ ((lugarNacimiento == null) ? 0 : lugarNacimiento.getClave().hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((primerApellido == null) ? 0 : primerApellido.hashCode());
		result = prime * result
				+ ((segundoApellido == null) ? 0 : segundoApellido.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.getIdSexo().hashCode());
		
		return result;
	}

    /**
     * @return the idModalidad
     */
    public Long getIdModalidad() {
        return idModalidad;
    }

    /**
     * @param idModalidad the idModalidad to set
     */
    public void setIdModalidad(Long idModalidad) {
        this.idModalidad = idModalidad;
    }
    
    
}
