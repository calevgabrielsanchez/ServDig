package mx.gob.imss.ctirss.delta.model.gestion.solicitud;
	
import java.util.Date;
import java.math.BigDecimal;

public class MovtoPatSujetoObligado {

    private long cveIdMovtoPatSujOblig;
    private BigDecimal cveIdPatronDestino;
    private Date fecAvisoSat;
    private Date fecInforme;
    private Date fecMovimiento;
    private Date fecOficio;
    private Date fecRegistroActualizado;
    private Date fecRegistroAlta;
    private String folioMovimiento;
    private String numeroOficio;
    private String observaciones;
    private String registroPatronal;
    private Integer idTipoMovimiento;
    private Integer causa;

    public long getCveIdMovtoPatSujOblig() {
        return cveIdMovtoPatSujOblig;
    }

    public void setCveIdMovtoPatSujOblig(long cveIdMovtoPatSujOblig) {
        this.cveIdMovtoPatSujOblig = cveIdMovtoPatSujOblig;
    }

    public BigDecimal getCveIdPatronDestino() {
        return cveIdPatronDestino;
    }

    public void setCveIdPatronDestino(BigDecimal cveIdPatronDestino) {
        this.cveIdPatronDestino = cveIdPatronDestino;
    }

    public Date getFecAvisoSat() {
        return fecAvisoSat;
    }

    public void setFecAvisoSat(Date fecAvisoSat) {
        this.fecAvisoSat = fecAvisoSat;
    }

    public Date getFecInforme() {
        return fecInforme;
    }

    public void setFecInforme(Date fecInforme) {
        this.fecInforme = fecInforme;
    }

    public Date getFecMovimiento() {
        return fecMovimiento;
    }

    public void setFecMovimiento(Date fecMovimiento) {
        this.fecMovimiento = fecMovimiento;
    }

    public Date getFecOficio() {
        return fecOficio;
    }

    public void setFecOficio(Date fecOficio) {
        this.fecOficio = fecOficio;
    }

    public Date getFecRegistroActualizado() {
        return fecRegistroActualizado;
    }

    public void setFecRegistroActualizado(Date fecRegistroActualizado) {
        this.fecRegistroActualizado = fecRegistroActualizado;
    }

    public Date getFecRegistroAlta() {
        return fecRegistroAlta;
    }

    public void setFecRegistroAlta(Date fecRegistroAlta) {
        this.fecRegistroAlta = fecRegistroAlta;
    }

    public String getFolioMovimiento() {
        return folioMovimiento;
    }

    public void setFolioMovimiento(String folioMovimiento) {
        this.folioMovimiento = folioMovimiento;
    }

    public String getNumeroOficio() {
        return numeroOficio;
    }

    public void setNumeroOficio(String numeroOficio) {
        this.numeroOficio = numeroOficio;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setRegistroPatronal(String registroPatronal) {
        this.registroPatronal = registroPatronal;
    }

    public String getRegistroPatronal() {
        return registroPatronal;
    }

    public void setIdTipoMovimiento(Integer idTipoMovimiento) {
        this.idTipoMovimiento = idTipoMovimiento;
    }

    public Integer getIdTipoMovimiento() {
        return idTipoMovimiento;
    }

    public void setCausa(Integer causa) {
        this.causa = causa;
    }

    public Integer getCausa() {
        return causa;
    }

}
