/**
 * 
 */
package mx.gob.imss.ctirss.delta.persistence.cobranza;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Clase persistente para el manejo de los pagos
 * @author NOVUTECK1
 *
 */
@Entity
@Table( name = "DIT_PAGO" )
public class DitPago implements Serializable {
    
    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Id del pago
     */
    @Id
    @SequenceGenerator(name = "DIT_PAGO_CVEIDPAGO_GENERATOR", sequenceName = "SEQ_DITPAGO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_PAGO_CVEIDPAGO_GENERATOR")
    @Column(name="CVE_ID_PAGO", nullable=false, precision=22)
    private Long cveIdPago;

    /**
     * FEcha en que se da de baja el registro
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_REGISTRO_BAJA")
    private Date fecRegistroBaja;
    /**
     * Fecha en la que se actualiza el registro
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_REGISTRO_ACTUALIZADO")
    private Date fecRegistroActualizado;

    /**
     * Fecha en la que se da de alta el registro
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_REGISTRO_ALTA")
    private Date fecRegistroAlta = new Date();
    /**
     * Compra asociada al pago
     */
    @ManyToOne
    @JoinColumn( name = "CVE_ID_COMPRA")
    private DitCompra ditCompra;
    /**
     * Estado del pago
     */
    @ManyToOne
    @JoinColumn(name = "CVE_ID_ESTADO_PAGO")
    private DicEstadoPago dicEstadoPago;
    /**
     * Detalle del pago
     */
    @OneToOne( mappedBy = "ditPago")
    private DitDetallePago ditDetallePago;
    /**
     * FEcha en la que se realiza el pago
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_PAGO")
    private Date fecPago;
    /**
     * fecha limite para pagar
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_LIMITA_PAGO")
    private Date fecLimitePago;
    /**
     * Fecha inicio de pago
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_INICIO_PERIODO")
    private Date fecIniPeriodo;
    /**
     * Fecha final del periodo de pago
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_FIN_PERIODO")
    private Date fecFinPeriodo;
    /**
     * Referencia a la linea de captura
     */
    @Column( name = "DES_LINEA_CAPTURA")
    private String desLineaCaptura;
    /**
     * Referencia del PDF de la linea de captura
     */
    @Column( name = "REF_PDF_LC")
    private byte[] refPdfLc;
    /**
     * @return the cveIdPago
     */
    public Long getCveIdPago() {
        return cveIdPago;
    }
    /**
     * @param cveIdPago the cveIdPago to set
     */
    public void setCveIdPago(Long cveIdPago) {
        this.cveIdPago = cveIdPago;
    }
    /**
     * @return the fecRegistroBaja
     */
    public Date getFecRegistroBaja() {
        return fecRegistroBaja;
    }
    /**
     * @param fecRegistroBaja the fecRegistroBaja to set
     */
    public void setFecRegistroBaja(Date fecRegistroBaja) {
        this.fecRegistroBaja = fecRegistroBaja;
    }
    /**
     * @return the fecRegistroActualizado
     */
    public Date getFecRegistroActualizado() {
        return fecRegistroActualizado;
    }
    /**
     * @param fecRegistroActualizado the fecRegistroActualizado to set
     */
    public void setFecRegistroActualizado(Date fecRegistroActualizado) {
        this.fecRegistroActualizado = fecRegistroActualizado;
    }
    /**
     * @return the fecRegistroAlta
     */
    public Date getFecRegistroAlta() {
        return fecRegistroAlta;
    }
    /**
     * @param fecRegistroAlta the fecRegistroAlta to set
     */
    public void setFecRegistroAlta(Date fecRegistroAlta) {
        this.fecRegistroAlta = fecRegistroAlta;
    }
    /**
     * @return the ditCompra
     */
    public DitCompra getDitCompra() {
        return ditCompra;
    }
    /**
     * @param ditCompra the ditCompra to set
     */
    public void setDitCompra(DitCompra ditCompra) {
        this.ditCompra = ditCompra;
    }
    /**
     * @return the dicEstadoPago
     */
    public DicEstadoPago getDicEstadoPago() {
        return dicEstadoPago;
    }
    /**
     * @param dicEstadoPago the dicEstadoPago to set
     */
    public void setDicEstadoPago(DicEstadoPago dicEstadoPago) {
        this.dicEstadoPago = dicEstadoPago;
    }
    /**
     * @return the ditDetallePago
     */
    public DitDetallePago getDitDetallePago() {
        return ditDetallePago;
    }
    /**
     * @param ditDetallePago the ditDetallePago to set
     */
    public void setDitDetallePago(DitDetallePago ditDetallePago) {
        this.ditDetallePago = ditDetallePago;
    }
    /**
     * @return the fecPago
     */
    public Date getFecPago() {
        return fecPago;
    }
    /**
     * @param fecPago the fecPago to set
     */
    public void setFecPago(Date fecPago) {
        this.fecPago = fecPago;
    }
    /**
     * @return the fecLimitePago
     */
    public Date getFecLimitePago() {
        return fecLimitePago;
    }
    /**
     * @param fecLimitePago the fecLimitePago to set
     */
    public void setFecLimitePago(Date fecLimitePago) {
        this.fecLimitePago = fecLimitePago;
    }
    /**
     * @return the fecIniPeriodo
     */
    public Date getFecIniPeriodo() {
        return fecIniPeriodo;
    }
    /**
     * @param fecIniPeriodo the fecIniPeriodo to set
     */
    public void setFecIniPeriodo(Date fecIniPeriodo) {
        this.fecIniPeriodo = fecIniPeriodo;
    }
    /**
     * @return the fecFinPeriodo
     */
    public Date getFecFinPeriodo() {
        return fecFinPeriodo;
    }
    /**
     * @param fecFinPeriodo the fecFinPeriodo to set
     */
    public void setFecFinPeriodo(Date fecFinPeriodo) {
        this.fecFinPeriodo = fecFinPeriodo;
    }
    /**
     * @return the desLineaCaptura
     */
    public String getDesLineaCaptura() {
        return desLineaCaptura;
    }
    /**
     * @param desLineaCaptura the desLineaCaptura to set
     */
    public void setDesLineaCaptura(String desLineaCaptura) {
        this.desLineaCaptura = desLineaCaptura;
    }
    
    /**
     * @return the pdfLineaCaptura
     */
    public byte[] getRefPdfLc() {
        return refPdfLc;
    }
    /**
     * @param pdfLineaCaptura the pdfLineaCaptura to set
     */
    public void setRefPdfLc(byte[] refPdfLc) {
        this.refPdfLc = refPdfLc;
    }  
    
}
