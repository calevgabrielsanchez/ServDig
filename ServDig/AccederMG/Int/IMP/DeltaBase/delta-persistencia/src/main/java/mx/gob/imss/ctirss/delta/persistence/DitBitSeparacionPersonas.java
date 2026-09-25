package mx.gob.imss.ctirss.delta.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "DIT_BIT_SEPARACION_PERSONAS")
public class DitBitSeparacionPersonas implements Serializable {

    private static final long serialVersionUID = -3226265966239367464L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CVE_ID_DETALLE_NSS_CDA")
    private DitDetalleNss detalleNssCda;

    @Column(name = "CVE_ID_TRAMITE")
    private Long cveIdTramite;

    @Id
    @Column(name = "CVE_ID_ASIGNACION_NSS", precision = 22)
    private Long cveIdAsignacionNss;

    @Column(name = "CVE_ID_PERSONA_ANTERIOR", precision = 22)
    private Long cveIdPersonaAnterior;

    @Column(name = "CVE_ID_PERSONA_NUEVO", precision = 22)
    private Long cveIdPersonaNuevo;

    @Column(name = "CVE_ID_PAIS", precision = 22)
    private Long cveIdPais;

    @Column(name = "CVE_ID_SEXO", precision = 22)
    private Long cveIdSexo;

    @Column(name = "CVE_ID_ESTADO_CIVIL", precision = 22)
    private Long cveIdEstadoCivil;

    @Column(name = "NOM_NOMBRE")
    private String nomNombre;

    @Column(name = "NOM_PRIMER_APELLIDO")
    private String nomPrimerApellido;

    @Column(name = "NOM_SEGUNDO_APELLIDO")
    private String nomSegundoApellido;

    @Column(name = "CURP", length = 50)
    private String curp;

    @Column(name = "RFC", length = 50)
    private String rfc;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEC_NACIMIENTO")
    private Date fecNacimiento;

    @Column(name = "OBSERVACIONES")
    private String observaciones;

    @Column(name = "IND_PER_AUTORIZADA", precision = 22)
    private Long indPerAutorizada;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEC_DEFUNCION")
    private Date fecDefuncion;

    @Column(name = "CVE_ENT", length = 2)
    private String cveEnt;

    @Column(name = "NUM_ANIO_NAC_REG", precision = 22)
    private Integer numAnioNacReg;

    @Column(name = "NUM_MES_NAC_REG", precision = 22)
    private Integer numMesNacReg;

    @Column(name = "CVE_USUARIO")
    private String cveUsuario;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEC_REGISTRO_ALTA")
    private Date fecRegistroAlta;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEC_REGISTRO_ACTUALIZADO")
    private Date fecRegistroActualizado;

    public DitDetalleNss getDetalleNssCda() {

        return detalleNssCda;
    }

    public void setDetalleNssCda(DitDetalleNss detalleNssCda) {

        this.detalleNssCda = detalleNssCda;
    }

    public Long getCveIdTramite() {

        return cveIdTramite;
    }

    public void setCveIdTramite(Long cveIdTramite) {

        this.cveIdTramite = cveIdTramite;
    }

    public Long getCveIdAsignacionNss() {

        return cveIdAsignacionNss;
    }

    public void setCveIdAsignacionNss(Long cveIdAsignacionNss) {

        this.cveIdAsignacionNss = cveIdAsignacionNss;
    }

    public Long getCveIdPersonaAnterior() {

        return cveIdPersonaAnterior;
    }

    public void setCveIdPersonaAnterior(Long cveIdPersonaAnterior) {

        this.cveIdPersonaAnterior = cveIdPersonaAnterior;
    }

    public Long getCveIdPersonaNuevo() {

        return cveIdPersonaNuevo;
    }

    public void setCveIdPersonaNuevo(Long cveIdPersonaNuevo) {

        this.cveIdPersonaNuevo = cveIdPersonaNuevo;
    }

    public Long getCveIdPais() {

        return cveIdPais;
    }

    public void setCveIdPais(Long cveIdPais) {

        this.cveIdPais = cveIdPais;
    }

    public Long getCveIdSexo() {

        return cveIdSexo;
    }

    public void setCveIdSexo(Long cveIdSexo) {

        this.cveIdSexo = cveIdSexo;
    }

    public Long getCveIdEstadoCivil() {

        return cveIdEstadoCivil;
    }

    public void setCveIdEstadoCivil(Long cveIdEstadoCivil) {

        this.cveIdEstadoCivil = cveIdEstadoCivil;
    }

    public String getNomNombre() {

        return nomNombre;
    }

    public void setNomNombre(String nomNombre) {

        this.nomNombre = nomNombre;
    }

    public String getNomPrimerApellido() {

        return nomPrimerApellido;
    }

    public void setNomPrimerApellido(String nomPrimerApellido) {

        this.nomPrimerApellido = nomPrimerApellido;
    }

    public String getNomSegundoApellido() {

        return nomSegundoApellido;
    }

    public void setNomSegundoApellido(String nomSegundoApellido) {

        this.nomSegundoApellido = nomSegundoApellido;
    }

    public String getCurp() {

        return curp;
    }

    public void setCurp(String curp) {

        this.curp = curp;
    }

    public String getRfc() {

        return rfc;
    }

    public void setRfc(String rfc) {

        this.rfc = rfc;
    }

    public Date getFecNacimiento() {

        return fecNacimiento;
    }

    public void setFecNacimiento(Date fecNacimiento) {

        this.fecNacimiento = fecNacimiento;
    }

    public String getObservaciones() {

        return observaciones;
    }

    public void setObservaciones(String observaciones) {

        this.observaciones = observaciones;
    }

    public Long getIndPerAutorizada() {

        return indPerAutorizada;
    }

    public void setIndPerAutorizada(Long indPerAutorizada) {

        this.indPerAutorizada = indPerAutorizada;
    }

    public Date getFecDefuncion() {

        return fecDefuncion;
    }

    public void setFecDefuncion(Date fecDefuncion) {

        this.fecDefuncion = fecDefuncion;
    }

    public String getCveEnt() {

        return cveEnt;
    }

    public void setCveEnt(String cveEnt) {

        this.cveEnt = cveEnt;
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

    public String getCveUsuario() {

        return cveUsuario;
    }

    public void setCveUsuario(String cveUsuario) {

        this.cveUsuario = cveUsuario;
    }

    public Date getFecRegistroAlta() {

        return fecRegistroAlta;
    }

    public void setFecRegistroAlta(Date fecRegistroAlta) {

        this.fecRegistroAlta = fecRegistroAlta;
    }

    public Date getFecRegistroActualizado() {

        return fecRegistroActualizado;
    }

    public void setFecRegistroActualizado(Date fecRegistroActualizado) {

        this.fecRegistroActualizado = fecRegistroActualizado;
    }
}
