package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the DIC_ESTADO_BENEFICIO database table.
 * 
 */
@Entity
@Table(name = "DIC_ESTADO_BENEFICIO")
public class DicEstadoBeneficio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CVE_ID_ESTADO_BENEFICIO")
	private Long cveIdEstadoBeneficio;

	@Column(name = "DES_ESTADO_BENEFICIO")
	private String desEstadoBeneficio;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
	
	//bi-directional many-to-one association to DitPersonaBeneficio
	@OneToMany(mappedBy="dicEstadoBeneficio")
	private List<DitPersonaBeneficio> ditPersonaBeneficios;
	
	//bi-directional many-to-one association to DitPatSujObligBeneficio
	@OneToMany(mappedBy="dicEstadoBeneficio")
	private List<DitPatSujObligBeneficio> ditPatSujObligBeneficios;
	
	public DicEstadoBeneficio() {
	}

	public Long getCveIdEstadoBeneficio() {
		return this.cveIdEstadoBeneficio;
	}

	public void setCveIdEstadoBeneficio(Long cveIdEstadoBeneficio) {
		this.cveIdEstadoBeneficio = cveIdEstadoBeneficio;
	}

	public String getDesEstadoBeneficio() {
		return this.desEstadoBeneficio;
	}

	public void setDesEstadoBeneficio(String desEstadoBeneficio) {
		this.desEstadoBeneficio = desEstadoBeneficio;
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

	public List<DitPersonaBeneficio> getDitPersonaBeneficios() {
		return ditPersonaBeneficios;
	}

	public void setDitPersonaBeneficios(
			List<DitPersonaBeneficio> ditPersonaBeneficios) {
		this.ditPersonaBeneficios = ditPersonaBeneficios;
	}

	public List<DitPatSujObligBeneficio> getDitPatSujObligBeneficios() {
		return ditPatSujObligBeneficios;
	}

	public void setDitPatSujObligBeneficios(
			List<DitPatSujObligBeneficio> ditPatSujObligBeneficios) {
		this.ditPatSujObligBeneficios = ditPatSujObligBeneficios;
	}
	
}