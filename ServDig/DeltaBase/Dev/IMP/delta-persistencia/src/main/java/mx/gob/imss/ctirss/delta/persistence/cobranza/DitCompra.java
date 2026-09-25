/**
 * 
 */
package mx.gob.imss.ctirss.delta.persistence.cobranza;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Clase persistente para el manejo de las compras de seguros
 * @author NOVUTECK1
 *
 */
@Entity
@Table(name = "DIT_COMPRA")
public class DitCompra implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Id de la compra
     */
    @Id
    @SequenceGenerator(name = "DIT_COMPRA_CVEIDCOMPRA_GENERATOR", sequenceName = "SEQ_DITCOMPRA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_COMPRA_CVEIDCOMPRA_GENERATOR")
    @Column(name="CVE_ID_COMPRA", nullable=false, precision=22)
    private Long cveIdCompra;

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
     * Fecha en la que se actualiza el registro
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_LIMITE_PAGO")
    private Date fecLimitePago;
    
    /**
     * Cotizacion asociada a la compra
     */
    @ManyToOne()
    @JoinColumn(name="CVE_ID_COTIZACION")
    private DitCotizacion ditCotizacion;
    /**
     * Forma del pago
     */
    @ManyToOne()
    @JoinColumn(name="CVE_ID_FORMA_PAGO")
    private DicFormaPago dicFormaPgo;
    /**
     * Estado de la compra
     */
    @ManyToOne()
    @JoinColumn(name="CVE_ID_ESTADO_COMPRA")
    private DicEstadoCompra dicEstadoCompra;
    /**
     * Lista de pagos asociados a la compr
     */
    @OneToMany(mappedBy="ditCompra")
    private List<DitPago> ditPagos;
    /**
     * Cantidad a pagar
     */
    @Transient
    private BigDecimal numMonto;
    /**
     * @return the cveIdCompra
     */
    public Long getCveIdCompra() {
        return cveIdCompra;
    }
    /**
     * @param cveIdCompra the cveIdCompra to set
     */
    public void setCveIdCompra(Long cveIdCompra) {
        this.cveIdCompra = cveIdCompra;
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
     * @return the ditCotizacion
     */
    public DitCotizacion getDitCotizacion() {
        return ditCotizacion;
    }
    /**
     * @param ditCotizacion the ditCotizacion to set
     */
    public void setDitCotizacion(DitCotizacion ditCotizacion) {
        this.ditCotizacion = ditCotizacion;
    }
    /**
     * @return the dicFormaPgo
     */
    public DicFormaPago getDicFormaPgo() {
        return dicFormaPgo;
    }
    /**
     * @param dicFormaPgo the dicFormaPgo to set
     */
    public void setDicFormaPgo(DicFormaPago dicFormaPgo) {
        this.dicFormaPgo = dicFormaPgo;
    }
    /**
     * @return the dicEstadoCompra
     */
    public DicEstadoCompra getDicEstadoCompra() {
        return dicEstadoCompra;
    }
    /**
     * @param dicEstadoCompra the dicEstadoCompra to set
     */
    public void setDicEstadoCompra(DicEstadoCompra dicEstadoCompra) {
        this.dicEstadoCompra = dicEstadoCompra;
    }
    /**
     * @return the ditPagos
     */
    public List<DitPago> getDitPagos() {
        return ditPagos;
    }
    /**
     * @param ditPagos the ditPagos to set
     */
    public void setDitPagos(List<DitPago> ditPagos) {
        this.ditPagos = ditPagos;
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
     * @return the numMonto
     */
    public BigDecimal getNumMonto() {
        return numMonto;
    }
    /**
     * @param numMonto the numMonto to set
     */
    public void setNumMonto(BigDecimal numMonto) {
        this.numMonto = numMonto;
    }
    
}
