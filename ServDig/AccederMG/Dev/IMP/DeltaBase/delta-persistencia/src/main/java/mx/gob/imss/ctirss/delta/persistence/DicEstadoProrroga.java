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
 * The persistent class for the DIC_ESTADO_PRORROGA database table.
 * 
 */
@Entity
@Table(name="DIC_ESTADO_PRORROGA")
public class DicEstadoProrroga implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_ESTADO_PRORROGA", nullable=false, precision=22)
	private Integer cveIdEstadoProrroga;

	@Column(name="DES_ESTADO_PRORROGA", length=20)
	private String desEstadoProrroga;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

  //bi-directional many-to-one association to DitProrroga
	@OneToMany(mappedBy="dicEstadoProrroga")
	private List<DitProrroga> ditProrrogas;
	
    public DicEstadoProrroga() {
    }


	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}


	public Integer getCveIdEstadoProrroga() {
		return cveIdEstadoProrroga;
	}


	public void setCveIdEstadoProrroga(Integer cveIdEstadoProrroga) {
		this.cveIdEstadoProrroga = cveIdEstadoProrroga;
	}


	public String getDesEstadoProrroga() {
		return desEstadoProrroga;
	}


	public void setDesEstadoProrroga(String desEstadoProrroga) {
		this.desEstadoProrroga = desEstadoProrroga;
	}


	public List<DitProrroga> getDitProrrogas() {
		return ditProrrogas;
	}


	public void setDitProrrogas(List<DitProrroga> ditProrrogas) {
		this.ditProrrogas = ditProrrogas;
	}
	
}