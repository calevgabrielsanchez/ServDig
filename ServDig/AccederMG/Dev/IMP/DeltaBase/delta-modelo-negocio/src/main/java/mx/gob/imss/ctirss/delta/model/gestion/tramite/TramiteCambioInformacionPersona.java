package mx.gob.imss.ctirss.delta.model.gestion.tramite;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;

@XmlRootElement
public class TramiteCambioInformacionPersona extends Tramite {

	private static final long serialVersionUID = -6912770055025201143L;

	// Contiene los datos del ICA
	private ICADatosRespuesta datosICA;
	// Contiene los datos de la modificación manual
	private MDMDatosEntrada datosModifManual;

	private Fisica fisica;
	private Moral moral;

	/**
	 * @return the datosICA
	 */
	public ICADatosRespuesta getDatosICA() {
		return datosICA;
	}

	/**
	 * @param datosICA
	 *            the datosICA to set
	 */
	public void setDatosICA(ICADatosRespuesta datosICA) {
		this.datosICA = datosICA;
	}

	/**
	 * @return the datosModifManual
	 */
	public MDMDatosEntrada getDatosModifManual() {
		return datosModifManual;
	}

	/**
	 * @param datosModifManual
	 *            the datosModifManual to set
	 */
	public void setDatosModifManual(MDMDatosEntrada datosModifManual) {
		this.datosModifManual = datosModifManual;
	}

	/**
	 * @return the fisica
	 */
	public Fisica getFisica() {
		return fisica;
	}

	/**
	 * @param fisica
	 *            the fisica to set
	 */
	public void setFisica(Fisica fisica) {
		this.fisica = fisica;
	}

	/**
	 * @return the moral
	 */
	public Moral getMoral() {
		return moral;
	}

	/**
	 * @param moral
	 *            the moral to set
	 */
	public void setMoral(Moral moral) {
		this.moral = moral;
	}

}
