package mx.gob.imss.ctirss.delta.persistence.bajas;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

//import mx.gob.imss.ctirss.delta.persistence.cobranza.DitPago;

import java.math.BigDecimal;


@Entity
@Table( name = "DIT_BAJA_MORA_PAGOS_VENCIDOS" )
public class DitBajaMoraPagoVencido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pagoVencidoSeq")
    @SequenceGenerator(
        name = "pagoVencidoSeq",
        sequenceName = "SEQ_BAJA_MORA_PAGO_VENCIDO",
        allocationSize = 1
    )
    @Column(name = "CVE_ID_PAGO_VENCIDO", nullable = false)
    private Long cveIdPagoVencido;

    @Column(name = "CVE_ID_DETALLE", nullable = false)
    private Long cveIdDetalle;

    @Column(name = "NUM_MES_MORA")
    private Integer numMesMora;

    @Column(name = "TXT_LINEA_DE_CAPTURA", length = 100)
    private String txtLineaDeCaptura;

    @Column(name = "NUM_MONTO")
    private BigDecimal numMonto;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEC_INICIO_PERIODO")
    private Date fecInicioPeriodo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEC_FIN_PERIODO")
    private Date fecFinPeriodo;

    @Column(name = "NUM_RECARGOS")
    private BigDecimal numRecargos;
    
    
    // --------------------------------

	public Long getCveIdPagoVencido() {
		return cveIdPagoVencido;
	}

	public void setCveIdPagoVencido(Long cveIdPagoVencido) {
		this.cveIdPagoVencido = cveIdPagoVencido;
	}

	public Long getCveIdDetalle() {
		return cveIdDetalle;
	}

	public void setCveIdDetalle(Long cveIdDetalle) {
		this.cveIdDetalle = cveIdDetalle;
	}

	public Integer getNumMesMora() {
		return numMesMora;
	}

	public void setNumMesMora(Integer numMesMora) {
		this.numMesMora = numMesMora;
	}

	public String getTxtLineaDeCaptura() {
		return txtLineaDeCaptura;
	}

	public void setTxtLineaDeCaptura(String txtLineaDeCaptura) {
		this.txtLineaDeCaptura = txtLineaDeCaptura;
	}

	public BigDecimal getNumMonto() {
		return numMonto;
	}

	public void setNumMonto(BigDecimal numMonto) {
		this.numMonto = numMonto;
	}

	public Date getFecInicioPeriodo() {
		return fecInicioPeriodo;
	}

	public void setFecInicioPeriodo(Date fecInicioPeriodo) {
		this.fecInicioPeriodo = fecInicioPeriodo;
	}

	public Date getFecFinPeriodo() {
		return fecFinPeriodo;
	}

	public void setFecFinPeriodo(Date fecFinPeriodo) {
		this.fecFinPeriodo = fecFinPeriodo;
	}

	public BigDecimal getNumRecargos() {
		return numRecargos;
	}

	public void setNumRecargos(BigDecimal numRecargos) {
		this.numRecargos = numRecargos;
	}
    
	@Override
	public String toString() {
	    return "BajaMoraPagosVencidosEntity{" +
	            "cveIdPagoVencido=" + cveIdPagoVencido +
	            ", numMesMora=" + numMesMora +
	            ", txtLineaDeCaptura='" + txtLineaDeCaptura + '\'' +
	            ", numMonto=" + numMonto +
	            ", fecInicioPeriodo=" + fecInicioPeriodo +
	            ", fecFinPeriodo=" + fecFinPeriodo +
	            ", numRecargos=" + numRecargos +
	            '}';
	}

}
