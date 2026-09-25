package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "DIC_PRESTACION_DERECHOHAB")
public class DicPrestacionDerechohab implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2741751413799170378L;

	@Id
	@Column(name="CVE_ID_PRESTACION_DERECHOHAB")
	private Long cveIdPrestacionDerechohab;
	
	@Column(name="IND_SERVICIO_PENSION")
	private Long indServicioPensiona;
	
	@Column(name ="NOM_PRESTACION_DERECHOHAB")
	private String nomPrestacionDerechohab;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	public Long getCveIdPrestacionDerechohab() {
		return cveIdPrestacionDerechohab;
	}

	public void setCveIdPrestacionDerechohab(Long cveIdPrestacionDerechohab) {
		this.cveIdPrestacionDerechohab = cveIdPrestacionDerechohab;
	}

	public Long getIndServicioPensiona() {
		return indServicioPensiona;
	}

	public void setIndServicioPensiona(Long indServicioPensiona) {
		this.indServicioPensiona = indServicioPensiona;
	}

	public String getNomPrestacionDerechohab() {
		return nomPrestacionDerechohab;
	}

	public void setNomPrestacionDerechohab(String nomPrestacionDerechohab) {
		this.nomPrestacionDerechohab = nomPrestacionDerechohab;
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
    
}