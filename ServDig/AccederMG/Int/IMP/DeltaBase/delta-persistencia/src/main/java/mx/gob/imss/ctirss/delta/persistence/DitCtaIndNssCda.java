package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@NamedQueries({
        @NamedQuery(name = "ditCtaIndNssCda.listUniqueNssTramite", query = "select distinct d.nss from DitCtaIndNssCda d where d.ditDetalleNss.cveDetalleNss = :nss"),
        @NamedQuery(name = "ditCtaIndNssCda.listPeriodosPorIdTramite", query = "select a from "
                + "DitCtaIndNssCda a " + ",DitDetalleNss b "
                + ",DitCorreccionDatosAsegurado c "
                + "where a.ditDetalleNss.cveDetalleNss = b.cveDetalleNss "
                + "and b.correccionDatosAsegurado.cveIdCorreccionDatosAsegurado = c.cveIdCorreccionDatosAsegurado "
                + "and c.tramite.cveIdTramite = :idTramite"),
        @NamedQuery(name = "ditCtaIndNssCda.existeInformacionPorIdTramite", query = "select count(a) from "
                + "DitCtaIndNssCda a " + ",DitDetalleNss b "
                + ",DitCorreccionDatosAsegurado c "
                + "where a.ditDetalleNss.cveDetalleNss = b.cveDetalleNss "
                + "and b.correccionDatosAsegurado.cveIdCorreccionDatosAsegurado = c.cveIdCorreccionDatosAsegurado "
                + "and c.tramite.cveIdTramite = :idTramite"),
		@NamedQuery(name = "ditCtaIndNssCda.existenMovimientosPorFolio", query = 
				"select c.cveIdCorreccionDatosAsegurado from "
				+ "DitCorreccionCtaIndCda a, DitCorreccionDatosAsegurado c, mx.gob.imss.ctirss.delta.persistence.DitMovAclaracionNssCda motivo "
				+ "where a.cveIdMovAclaracionNss = motivo.cveIdMovAclaracionNss and "
				+ "motivo.cveIdDetalleNssCda.correccionDatosAsegurado.cveIdCorreccionDatosAsegurado = c.cveIdCorreccionDatosAsegurado and "
				+ "c.tramite.ditSolicitud.refFolio = :reffolio and "
				+ "a.fecRegistroBaja is null ") })
@Table(name = "DIT_CTA_IND_NSS_CDA")
public class DitCtaIndNssCda  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SEQ_DITCTAINDNSSCDA", sequenceName = "SEQ_DITCTAINDNSSCDA")
    @GeneratedValue(generator = "SEQ_DITCTAINDNSSCDA")
    @Column(name = "CVE_ID_CTA_IND")
    private Long cveIdCtaInd;

    @Column(name = "NSS", length = 11)
    private String nss;

    @Column(name = "CVE_REGISTRO_PATRONAL", length = 11)
    private String cveRegistroPatronal;

    @Column(name = "CVE_MODALIDAD")
    private String cveModalidad;

    // @Temporal( TemporalType.TIMESTAMP)
    @Column(name = "FEC_INI_MOV")
    private String fecIniMov;

    @Column(name = "CVE_CONSEC_PERIODOS")
    private Long cveConsecPeriodos;

    @Column(name = "CURP")
    private String curp;

    // @Temporal( TemporalType.TIMESTAMP)
    @Column(name = "FEC_FIN_MOV")
    private String fecFinMov;

    @Column(name = "CVE_INI_MOV")
    private char cveIniMov;

    @Column(name = "CVE_FIN_MOV")
    private char cveFinMov;

    @Column(name = "CVE_TIPO_INI_MOV")
    private Long cveTipoIniMov;

    @Column(name = "CVE_TIPO_FIN_MOV")
    private Long cveTipoFinMov;

    // @Temporal( TemporalType.TIMESTAMP)
    @Column(name = "FEC_RECEPCION_MOV")
    private String fecRecepcionMov;

    @Column(name = "SALARIO_BASE")
    private Double salarioBase;

    @Column(name = "CVE_TIPO_SALARIO")
    private String cveTipoSalario;

    @Column(name = "CVE_JORNADA_SEMANAL")
    private String cveJornadaSemanal;

    @Column(name = "CVE_EVENTUAL")
    private String cveEventual;

    @Column(name = "CVE_SUBR_SERVICIOS")
    private String cveSubrServicios;

    @Column(name = "CVE_HUELGA")
    private String cveHuelga;

    @Column(name = "CVE_EXT_CONV_SUSP")
    private String cveExtConvSusp;

    // @Temporal( TemporalType.TIMESTAMP)
    @Column(name = "FEC_ACTUALIZACION")
    private String fecActualizacion;

    @Column(name = "CVE_DELEGACION_ORIGEN")
    private Long cveDelegacionOrigen;

    @Column(name = "CVE_CIZ")
    private Long cveCiz;

    // @Temporal( TemporalType.TIMESTAMP)
    @Column(name = "FEC_CARGA")
    private String fecCarga;

    /** FK **/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CVE_ID_DETALLE_NSS_CDA")
    private DitDetalleNss ditDetalleNss;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CVE_ID_ORIGEN_PERIODO_CTA_IND")
    private DicOrigenCtaIndCda dicOrigenCtaIndCda;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEC_REGISTRO_ALTA")
    private Date fecRegistroAlta;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEC_REGISTRO_BAJA")
    private Date fecRegistroBaja;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEC_REGISTRO_ACTUALIZADO")
    private Date fecRegistroActualizado;
	
	@Column(name = "NOM_RAZON_SOCIAL")
	private String nomRazonSocial;
	
    @JoinColumn(name = "CVE_ID_CTA_IND_PADRE", referencedColumnName = "CVE_ID_CTA_IND")
    @ManyToOne(fetch = FetchType.LAZY)
    private DitCtaIndNssCda cveIdCtaIndPadre;
    
    @Transient
    private String tipoRegularizacion;
    
    @Transient
    private String nssDestino;
    
    @Transient
    private Long origenCaptura;
    
    @Column(name = "IND_HISTORICO_CENTRAL")
    private Integer indHistoricoCentral;

    public Integer getIndHistoricoCentral() {
        return indHistoricoCentral;
    }

    public void setIndHistoricoCentral(Integer indHistoricoCentral) {
        this.indHistoricoCentral = indHistoricoCentral;
    }
    
    public Long getCveIdCtaInd() {
        return cveIdCtaInd;
    }

    public void setCveIdCtaInd(Long cveIdCtaInd) {
        this.cveIdCtaInd = cveIdCtaInd;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getCveRegistroPatronal() {
        return cveRegistroPatronal;
    }

    public void setCveRegistroPatronal(String cveRegistroPatronal) {
        this.cveRegistroPatronal = cveRegistroPatronal;
    }

    public String getCveModalidad() {
        return cveModalidad;
    }

    public void setCveModalidad(String cveModalidad) {
        this.cveModalidad = cveModalidad;
    }

    public Long getCveConsecPeriodos() {
        return cveConsecPeriodos;
    }

    public void setCveConsecPeriodos(Long cveConsecPeriodos) {
        this.cveConsecPeriodos = cveConsecPeriodos;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public char getCveIniMov() {
        return cveIniMov;
    }

    public void setCveIniMov(char cveIniMov) {
        this.cveIniMov = cveIniMov;
    }

    public char getCveFinMov() {
        return cveFinMov;
    }

    public void setCveFinMov(char cveFinMov) {
        this.cveFinMov = cveFinMov;
    }

    public Long getCveTipoIniMov() {
        return cveTipoIniMov;
    }

    public void setCveTipoIniMov(Long cveTipoIniMov) {
        this.cveTipoIniMov = cveTipoIniMov;
    }

    public Long getCveTipoFinMov() {
        return cveTipoFinMov;
    }

    public void setCveTipoFinMov(Long cveTipoFinMov) {
        this.cveTipoFinMov = cveTipoFinMov;
    }

    public Double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(Double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public String getCveTipoSalario() {
        return cveTipoSalario;
    }

    public void setCveTipoSalario(String cveTipoSalario) {
        this.cveTipoSalario = cveTipoSalario;
    }

    public String getCveJornadaSemanal() {
        return cveJornadaSemanal;
    }

    public void setCveJornadaSemanal(String cveJornadaSemanal) {
        this.cveJornadaSemanal = cveJornadaSemanal;
    }

    public String getCveEventual() {
        return cveEventual;
    }

    public void setCveEventual(String cveEventual) {
        this.cveEventual = cveEventual;
    }

    public String getCveSubrServicios() {
        return cveSubrServicios;
    }

    public void setCveSubrServicios(String cveSubrServicios) {
        this.cveSubrServicios = cveSubrServicios;
    }

    public String getCveHuelga() {
        return cveHuelga;
    }

    public void setCveHuelga(String cveHuelga) {
        this.cveHuelga = cveHuelga;
    }

    public String getCveExtConvSusp() {
        return cveExtConvSusp;
    }

    public void setCveExtConvSusp(String cveExtConvSusp) {
        this.cveExtConvSusp = cveExtConvSusp;
    }

    public Long getCveDelegacionOrigen() {
        return cveDelegacionOrigen;
    }

    public void setCveDelegacionOrigen(Long cveDelegacionOrigen) {
        this.cveDelegacionOrigen = cveDelegacionOrigen;
    }

    public Long getCveCiz() {
        return cveCiz;
    }

    public void setCveCiz(Long cveCiz) {
        this.cveCiz = cveCiz;
    }

    public DitDetalleNss getDitDetalleNss() {
        return ditDetalleNss;
    }

    public void setDitDetalleNss(DitDetalleNss ditDetalleNss) {
        this.ditDetalleNss = ditDetalleNss;
    }

    public DicOrigenCtaIndCda getDicOrigenCtaIndCda() {
        return dicOrigenCtaIndCda;
    }

    public void setDicOrigenCtaIndCda(DicOrigenCtaIndCda dicOrigenCtaIndCda) {
        this.dicOrigenCtaIndCda = dicOrigenCtaIndCda;
    }

    public Date getFecRegistroAlta() {
        return fecRegistroAlta;
    }

    public void setFecRegistroAlta(Date fecRegistroAlta) {
        this.fecRegistroAlta = fecRegistroAlta;
    }

    public Date getFecRegistroBaja() {
        return fecRegistroBaja;
    }

    public void setFecRegistroBaja(Date fecRegistroBaja) {
        this.fecRegistroBaja = fecRegistroBaja;
    }

    public Date getFecRegistroActualizado() {
        return fecRegistroActualizado;
    }

    public void setFecRegistroActualizado(Date fecRegistroActualizado) {
        this.fecRegistroActualizado = fecRegistroActualizado;
    }

    public String getFecIniMov() {
        return fecIniMov;
    }

    public void setFecIniMov(String fecIniMov) {
        this.fecIniMov = fecIniMov;
    }

    public String getFecFinMov() {
        return fecFinMov;
    }

    public void setFecFinMov(String fecFinMov) {
        this.fecFinMov = fecFinMov;
    }

    public String getFecRecepcionMov() {
        return fecRecepcionMov;
    }

    public void setFecRecepcionMov(String fecRecepcionMov) {
        this.fecRecepcionMov = fecRecepcionMov;
    }

    public String getFecActualizacion() {
        return fecActualizacion;
    }

    public void setFecActualizacion(String fecActualizacion) {
        this.fecActualizacion = fecActualizacion;
    }

    public String getFecCarga() {
        return fecCarga;
    }

    public void setFecCarga(String fecCarga) {
        this.fecCarga = fecCarga;
    }
	
	public String getNomRazonSocial() {
        return nomRazonSocial;
    }

    public void setNomRazonSocial(String nomRazonSocial) {
        this.nomRazonSocial = nomRazonSocial;
    }
    
    public DitCtaIndNssCda getCveIdCtaIndPadre() {
        return cveIdCtaIndPadre;
    }

    public void setCveIdCtaIndPadre(DitCtaIndNssCda cveIdCtaIndPadre) {
        this.cveIdCtaIndPadre = cveIdCtaIndPadre;
    }

    public String getTipoRegularizacion() {
        return tipoRegularizacion;
    }

    public void setTipoRegularizacion(String tipoRegularizacion) {
        this.tipoRegularizacion = tipoRegularizacion;
    }

    public Long getOrigenCaptura() {
        return origenCaptura;
    }

    public void setOrigenCaptura(Long origenCaptura) {
        this.origenCaptura = origenCaptura;
    }

    public String getNssDestino() {
        return nssDestino;
    }

    public void setNssDestino(String nssDestino) {
        this.nssDestino = nssDestino;
    }

}
