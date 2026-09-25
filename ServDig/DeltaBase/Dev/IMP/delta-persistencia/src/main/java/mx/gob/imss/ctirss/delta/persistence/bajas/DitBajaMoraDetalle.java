package mx.gob.imss.ctirss.delta.persistence.bajas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "DIT_BAJA_MORA_DETALLE")
public class DitBajaMoraDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
        name = "SEQ_BAJA_MORA_DETALLE",
        sequenceName = "SEQ_BAJA_MORA_DETALLE",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "SEQ_BAJA_MORA_DETALLE"
    )
    @Column(name = "CVE_ID_DETALLE", nullable = false, precision = 22)
    private Long cveIdDetalle;

    /**
     * FK a DIT_BAJA_SEGURO (relación 1:1 - UNIQUE constraint en DDL)
     * CORREGIDO: Ahora usa @OneToOne como DitBajaReingresoDetalle
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CVE_ID_BAJA", nullable = false, unique = true)
    private DitBajaSeguro ditBajaSeguro;
    

    /**
     * Meses consecutivos en mora (mínimo 2)
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEC_ULTIMO_PAGO")
    private Date fecUltimoPago;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "CVE_ID_DETALLE", referencedColumnName = "CVE_ID_DETALLE")
    private List<DitBajaMoraPagoVencido> pagosVencidos;
    
    
    
    
    
    // ========================================
    // Getters y Setters
    // ========================================
    public Long getCveIdDetalle() {
        return cveIdDetalle;
    }

    public void setCveIdDetalle(Long cveIdDetalle) {
        this.cveIdDetalle = cveIdDetalle;
    }

    public DitBajaSeguro getDitBajaSeguro() {
        return ditBajaSeguro;
    }

    public void setDitBajaSeguro(DitBajaSeguro ditBajaSeguro) {
        this.ditBajaSeguro = ditBajaSeguro;
    }

    public List<DitBajaMoraPagoVencido> getPagosVencidos() {
		return pagosVencidos;
	}

	public void setPagosVencidos(List<DitBajaMoraPagoVencido> pagosVencidos) {
		this.pagosVencidos = pagosVencidos;
	}

	/**
     * Método helper para obtener CVE_ID_BAJA cuando se necesite
     * (mantiene compatibilidad con código existente)
     */
    public Long getCveIdBaja() {
        return ditBajaSeguro != null ? ditBajaSeguro.getCveIdBaja() : null;
    }

   

    public Date getFecUltimoPago() {
        return fecUltimoPago;
    }

    public void setFecUltimoPago(Date fecUltimoPago) {
        this.fecUltimoPago = fecUltimoPago;
    }


    // ========================================
    // hashCode, equals, toString
    // ========================================

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cveIdDetalle == null) ? 0 : cveIdDetalle.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        DitBajaMoraDetalle other = (DitBajaMoraDetalle) obj;
        if (cveIdDetalle == null) {
            if (other.cveIdDetalle != null) return false;
        } else if (!cveIdDetalle.equals(other.cveIdDetalle)) {
            return false;
        }
        return true;
    }

    
	@Override
	public String toString() {
		return "DitBajaMoraDetalle [cveIdDetalle=" + cveIdDetalle + 
				", ditBajaSeguro=" + ditBajaSeguro +
				", fecUltimoPago=" + fecUltimoPago + 
				", pagosVencidos=" + pagosVencidos +
				"]";
	}
}
