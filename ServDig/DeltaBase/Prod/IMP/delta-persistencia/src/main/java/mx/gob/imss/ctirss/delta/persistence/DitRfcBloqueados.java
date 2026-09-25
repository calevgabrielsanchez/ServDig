package mx.gob.imss.ctirss.delta.persistence;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "DIT_RFC_BLOQUEADOS")
public class DitRfcBloqueados implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_RFC_BLOQ")
    private Long idRfcBloq;

    @Column(name = "DES_RFC", length = 13)
    private String rfc;

    @Column(name = "FEC_ALTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;

    @Column(name = "FEC_BAJA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;

    // Relaci�n uno-a-muchos con BitacoraIngreso
    @OneToMany(mappedBy="ditRfcBloqueados")
    private List<DitBitacoraIngreso> ditBitacoraIngreso;



	// Constructores
    public DitRfcBloqueados() {
    }

    public DitRfcBloqueados(String rfc, Date fechaAlta) {
        this.rfc = rfc;
        this.fechaAlta = fechaAlta;
    }

    // Getters y Setters
    public Long getIdRfcBloq() {
        return idRfcBloq;
    }

    public void setIdRfcBloq(Long idRfcBloq) {
        this.idRfcBloq = idRfcBloq;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
    
    public List<DitBitacoraIngreso> getDitBitacoraIngreso() {
		return ditBitacoraIngreso;
	}

	public void setDitBitacoraIngreso(List<DitBitacoraIngreso> ditBitacoraIngreso) {
		this.ditBitacoraIngreso = ditBitacoraIngreso;
	}



}