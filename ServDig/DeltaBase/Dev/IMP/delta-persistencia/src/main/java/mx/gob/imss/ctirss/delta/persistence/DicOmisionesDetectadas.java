package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the DIC_OMISIONES_DETECTADAS database table.
 * 
 */
@Entity
@Table(name="DIC_OMISIONES_DETECTADAS")
public class DicOmisionesDetectadas implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_OMISIONES_DETECTADAS", nullable=false, precision=22)
	private long cveIdOmisionesDetectadas;

	@Column(name="DES_OMISIONES", length=255)
	private String desOmisiones;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
    
    // bi-directional many-to-one association to DitHistOmisiones
 	@OneToMany(mappedBy = "dicOmisionesDetectadas")
 	private List<DitHistOmisiones> ditHistOmisiones;
    
    //GETTERS AND SETTERS
    
    public long getCveIdOmisionesDetectadas() {
		return cveIdOmisionesDetectadas;
	}

	public void setCveIdOmisionesDetectadas(long cveIdOmisionesDetectadas) {
		this.cveIdOmisionesDetectadas = cveIdOmisionesDetectadas;
	}

	public String getDesOmisiones() {
		return desOmisiones;
	}

	public void setDesOmisiones(String desOmisiones) {
		this.desOmisiones = desOmisiones;
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

	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public List<DitHistOmisiones> getDitHistOmisiones() {
		return ditHistOmisiones;
	}

	public void setDitHistOmisiones(List<DitHistOmisiones> ditHistOmisiones) {
		this.ditHistOmisiones = ditHistOmisiones;
	}
	
}