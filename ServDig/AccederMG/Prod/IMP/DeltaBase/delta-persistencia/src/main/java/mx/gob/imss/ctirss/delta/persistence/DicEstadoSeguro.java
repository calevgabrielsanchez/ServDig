package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_ESTADO_SEGURO database table.
 * 
 */
@Entity
@Table(name="DIC_ESTADO_SEGURO")
@NamedQuery(name="DicEstadoSeguro.findAll", query="SELECT d FROM DicEstadoSeguro d")
public class DicEstadoSeguro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_ESTADO_SEGURO")
	private Long cveIdEstadoSeguro;

	@Column(name="DES_ESTADO_SEGURO")
	private String desEstadoSeguro;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitSeguroIvro
	@OneToMany(mappedBy="dicEstadoSeguro")
	private List<DitSeguroIvro> ditSeguroIvros;

	public DicEstadoSeguro() {
	}

	public Long getCveIdEstadoSeguro() {
		return this.cveIdEstadoSeguro;
	}

	public void setCveIdEstadoSeguro(Long cveIdEstadoSeguro) {
		this.cveIdEstadoSeguro = cveIdEstadoSeguro;
	}

	public String getDesEstadoSeguro() {
		return this.desEstadoSeguro;
	}

	public void setDesEstadoSeguro(String desEstadoSeguro) {
		this.desEstadoSeguro = desEstadoSeguro;
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

	public List<DitSeguroIvro> getDitSeguroIvros() {
		return this.ditSeguroIvros;
	}

	public void setDitSeguroIvros(List<DitSeguroIvro> ditSeguroIvros) {
		this.ditSeguroIvros = ditSeguroIvros;
	}

	public DitSeguroIvro addDitSeguroIvro(DitSeguroIvro ditSeguroIvro) {
		getDitSeguroIvros().add(ditSeguroIvro);
		ditSeguroIvro.setDicEstadoSeguro(this);

		return ditSeguroIvro;
	}

	public DitSeguroIvro removeDitSeguroIvro(DitSeguroIvro ditSeguroIvro) {
		getDitSeguroIvros().remove(ditSeguroIvro);
		ditSeguroIvro.setDicEstadoSeguro(null);

		return ditSeguroIvro;
	}

}