package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ADT_CAT_CALIDAD database table.
 * 
 */
@Entity
@Table(name="ADT_CAT_CALIDAD")
@NamedQuery(name="AdtCatCalidad.findAll", query="SELECT a FROM AdtCatCalidad a")
public class AdtCatCalidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_CALIDAD_ASEG")
	private long cveCalidadAseg;

	@Column(name="DES_CALIDAD")
	private String desCalidad;

	//bi-directional many-to-one association to Emptyfpenrollmentevent
	@OneToMany(mappedBy="adtCatCalidad")
	private List<Emptyfpenrollmentevent> emptyfpenrollmentevents;

	public AdtCatCalidad() {
	}

	public long getCveCalidadAseg() {
		return this.cveCalidadAseg;
	}

	public void setCveCalidadAseg(long cveCalidadAseg) {
		this.cveCalidadAseg = cveCalidadAseg;
	}

	public String getDesCalidad() {
		return this.desCalidad;
	}

	public void setDesCalidad(String desCalidad) {
		this.desCalidad = desCalidad;
	}

	public List<Emptyfpenrollmentevent> getEmptyfpenrollmentevents() {
		return this.emptyfpenrollmentevents;
	}

	public void setEmptyfpenrollmentevents(List<Emptyfpenrollmentevent> emptyfpenrollmentevents) {
		this.emptyfpenrollmentevents = emptyfpenrollmentevents;
	}

	public Emptyfpenrollmentevent addEmptyfpenrollmentevent(Emptyfpenrollmentevent emptyfpenrollmentevent) {
		getEmptyfpenrollmentevents().add(emptyfpenrollmentevent);
		emptyfpenrollmentevent.setAdtCatCalidad(this);

		return emptyfpenrollmentevent;
	}

	public Emptyfpenrollmentevent removeEmptyfpenrollmentevent(Emptyfpenrollmentevent emptyfpenrollmentevent) {
		getEmptyfpenrollmentevents().remove(emptyfpenrollmentevent);
		emptyfpenrollmentevent.setAdtCatCalidad(null);

		return emptyfpenrollmentevent;
	}

}