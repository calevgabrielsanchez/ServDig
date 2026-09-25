package mx.gob.imss.digital.modelo.persona;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "moral", namespace = "http://mx.gob.imss.digital.modelo.persona")
@XmlRootElement(name = "moral", namespace = "http://mx.gob.imss.digital.modelo.persona")
public class Moral extends Persona {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 4028913331113322982L;
    /**
     * Clave de la persona moral
     */
    private Long cveMoral;
    /**
     * Razon ocial 
     */
    private String razonSocial;

    /**
     * Numero de registro patronal
     */
    private String nrp;
    /**
     * RFC asociado en el SAT
     */
    private String rfcSat;
    /**
     * Acta constitutiva
     */
    private String actaConstitutiva;
    /**
     * descripcion de la situacion
     */
    private String desSituacion;
    /**
     * identificador de su situacion
     */
    private Integer idSituacion;
    /**
     * fecha de cracion formateada
     */
    private String fechaCreacionFormateada;
    /**
     * fecha de cracion
     */
    private Date fechaCreacion;
    /**
     * Fecha de registro
     */
    private Date fechaRegistro;
    /**
     * fecha de baja
     */
    private Date fechaBaja;
    /**
     * fecha de modificacion
     */
    private Date fechaModificacion;
    /**
     * Busqueda Aproximada
     */
    private String busqAprox;
    /**
     * identificador del act constitutiva
     */
    private Long idActaConstitutiva;
    /**
     * Alta en el imss
     */
    private String altaEnImss;
    /**
     * NUmero de linea de archivo
     */
    private Integer numeroLineaArchivo;
    /**
     * estados formateados
     */
    private String estadosFormateados;
    /**
     * subestados formateados
     */
    private String subEstadosFormateados;
    /**
     * Indicador de acreditado
     */
	private BigDecimal indAcreditado;

	/**
	 * Constructor por omision
	 */
	public Moral(){
		
	}
	
	/**
	 * Constructor de la clase
	 * @param cveIdPersonaMoral
	 * @param rfc
	 * @param denominacionRazonSocial
	 */
	public Moral(Long cveIdPersonaMoral, String rfc, String denominacionRazonSocial ){
		super();
		this.setTipoPersona(new TipoPersona());
		this.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
		this.setIdPersona(cveIdPersonaMoral);
		this.setRfc(rfc);
		this.razonSocial=denominacionRazonSocial;
	}

    /**
     * @return the cveMoral
     */
    public Long getCveMoral() {
        return cveMoral;
    }

    /**
     * @param cveMoral the cveMoral to set
     */
    public void setCveMoral(Long cveMoral) {
        this.cveMoral = cveMoral;
    }

    /**
     * @return the razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * @param razonSocial the razonSocial to set
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * @return the nrp
     */
    public String getNrp() {
        return nrp;
    }

    /**
     * @param nrp the nrp to set
     */
    public void setNrp(String nrp) {
        this.nrp = nrp;
    }

    /**
     * @return the rfcSat
     */
    public String getRfcSat() {
        return rfcSat;
    }

    /**
     * @param rfcSat the rfcSat to set
     */
    public void setRfcSat(String rfcSat) {
        this.rfcSat = rfcSat;
    }

    /**
     * @return the actaConstitutiva
     */
    public String getActaConstitutiva() {
        return actaConstitutiva;
    }

    /**
     * @param actaConstitutiva the actaConstitutiva to set
     */
    public void setActaConstitutiva(String actaConstitutiva) {
        this.actaConstitutiva = actaConstitutiva;
    }

    /**
     * @return the desSituacion
     */
    public String getDesSituacion() {
        return desSituacion;
    }

    /**
     * @param desSituacion the desSituacion to set
     */
    public void setDesSituacion(String desSituacion) {
        this.desSituacion = desSituacion;
    }

    /**
     * @return the idSituacion
     */
    public Integer getIdSituacion() {
        return idSituacion;
    }

    /**
     * @param idSituacion the idSituacion to set
     */
    public void setIdSituacion(Integer idSituacion) {
        this.idSituacion = idSituacion;
    }

    /**
     * @return the fechaCreacionFormateada
     */
    public String getFechaCreacionFormateada() {
        return fechaCreacionFormateada;
    }

    /**
     * @param fechaCreacionFormateada the fechaCreacionFormateada to set
     */
    public void setFechaCreacionFormateada(String fechaCreacionFormateada) {
        this.fechaCreacionFormateada = fechaCreacionFormateada;
    }

    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the fechaRegistro
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * @param fechaRegistro the fechaRegistro to set
     */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * @return the fechaBaja
     */
    public Date getFechaBaja() {
        return fechaBaja;
    }

    /**
     * @param fechaBaja the fechaBaja to set
     */
    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    /**
     * @return the fechaModificacion
     */
    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    /**
     * @param fechaModificacion the fechaModificacion to set
     */
    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    /**
     * @return the busqAprox
     */
    public String getBusqAprox() {
        return busqAprox;
    }

    /**
     * @param busqAprox the busqAprox to set
     */
    public void setBusqAprox(String busqAprox) {
        this.busqAprox = busqAprox;
    }

    /**
     * @return the idActaConstitutiva
     */
    public Long getIdActaConstitutiva() {
        return idActaConstitutiva;
    }

    /**
     * @param idActaConstitutiva the idActaConstitutiva to set
     */
    public void setIdActaConstitutiva(Long idActaConstitutiva) {
        this.idActaConstitutiva = idActaConstitutiva;
    }

    /**
     * @return the altaEnImss
     */
    public String getAltaEnImss() {
        return altaEnImss;
    }

    /**
     * @param altaEnImss the altaEnImss to set
     */
    public void setAltaEnImss(String altaEnImss) {
        this.altaEnImss = altaEnImss;
    }

    /**
     * @return the numeroLineaArchivo
     */
    public Integer getNumeroLineaArchivo() {
        return numeroLineaArchivo;
    }

    /**
     * @param numeroLineaArchivo the numeroLineaArchivo to set
     */
    public void setNumeroLineaArchivo(Integer numeroLineaArchivo) {
        this.numeroLineaArchivo = numeroLineaArchivo;
    }

    /**
     * @return the estadosFormateados
     */
    public String getEstadosFormateados() {
        return estadosFormateados;
    }

    /**
     * @param estadosFormateados the estadosFormateados to set
     */
    public void setEstadosFormateados(String estadosFormateados) {
        this.estadosFormateados = estadosFormateados;
    }

    /**
     * @return the subEstadosFormateados
     */
    public String getSubEstadosFormateados() {
        return subEstadosFormateados;
    }

    /**
     * @param subEstadosFormateados the subEstadosFormateados to set
     */
    public void setSubEstadosFormateados(String subEstadosFormateados) {
        this.subEstadosFormateados = subEstadosFormateados;
    }

    /**
     * @return the indAcreditado
     */
    public BigDecimal getIndAcreditado() {
        return indAcreditado;
    }

    /**
     * @param indAcreditado the indAcreditado to set
     */
    public void setIndAcreditado(BigDecimal indAcreditado) {
        this.indAcreditado = indAcreditado;
    }
	
	
}
