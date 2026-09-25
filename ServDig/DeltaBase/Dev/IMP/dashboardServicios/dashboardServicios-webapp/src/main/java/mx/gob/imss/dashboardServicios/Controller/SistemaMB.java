package mx.gob.imss.dashboardServicios.Controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;


import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.Sistema;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.servicios.business.interfaces.ConsultaBusinessRemote;
import mx.gob.imss.dashboardServicios.infraestructura.utilerias.EjbLocator;

/**
 * @author Josué Hernández Ramírez 20/01/2012
 */
// @ManagedBean(name="sistemaMB", eager=true)
// @SessionScoped
@ManagedBean(name = "sistemaMB")
@ViewScoped
public class SistemaMB implements Serializable {
	private static final long serialVersionUID = 8129893073170694453L;
	private SistemaDTO sistemaDTO;
	private boolean muestraResultado;
	private boolean ejecucionProceso;
	private Sistema detalleSeleccionado;
	private Sistema eliminaDetalle;
	private ConsultaBusinessRemote consulta;
	FacesMessage msg = null;
	RequestContext context = null;

	public SistemaMB() {
		consulta = EjbLocator.getEjbRemote();
		if (sistemaDTO == null) {
			sistemaDTO = new SistemaDTO();
			sistemaDTO.setSistemaTemp(new Sistema());
			muestraResultado = false;
		}
	}

	/**
	 * metodo para realizar la busqueda de los sistemas
	 */
	public void buscaSistemas() {
		System.out.println("************* proceso de busqueda");
		System.out.println("descripcion: " + sistemaDTO.getDescripcionSistema());

		Sistema filtroBusqueda = new Sistema();
		filtroBusqueda.setDesSistema(sistemaDTO.getDescripcionSistema());
		sistemaDTO.setLstResultados(consulta.getSistemas(filtroBusqueda));

		// sistemaDTO.getSistemaTemp().setDesSistema(sistemaDTO.getDescSis());
		// sistemaDTO.setLstResultados(consulta.getSistemas(sistemaDTO.getSistemaTemp()));

		this.muestraResultado = true;
	}

	/**
	 * Metodo agregar un nuevo sistema
	 */
	public void agregaSistema() {
		System.out.println("************* proceso de alta");
		ejecucionProceso = false;
		context = RequestContext.getCurrentInstance();
		if (sistemaDTO.getSistemaTemp().getCveSistema() != null
				&& !sistemaDTO.getSistemaTemp().getCveSistema().equals("")
				&& sistemaDTO.getSistemaTemp().getDesSistema() != null
				&& !sistemaDTO.getSistemaTemp().getCveSistema().equals("")) {
			ejecucionProceso = true;
			muestraResultado = false;
			sistemaDTO.setDescripcionSistema("");
			if (consulta.altaSistema(sistemaDTO.getSistemaTemp())) {
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Se agrego el sistema: ", sistemaDTO.getSistemaTemp()
								.getCveSistema());

			} else {
				msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"error al agregar el sistema: ", sistemaDTO
								.getSistemaTemp().getCveSistema());
			}
			sistemaDTO.setSistemaTemp(new Sistema());
		}

		FacesContext.getCurrentInstance().addMessage(null, msg);
		context.addCallbackParam("ejecucionProceso", ejecucionProceso);
	}

	/**
	 * Metodo eliminar un registro
	 */
	public void eliminaSistema() {
		System.out.println("************* cve a eliminar: "+ eliminaDetalle.getCveSistema());
		if (eliminaDetalle != null) {
			if (consulta.eliminaSistema(eliminaDetalle.getCveSistema())) {
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Se elimino el sistema: ",
						eliminaDetalle.getCveSistema());
				muestraResultado = false;
			} else {
				msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"error al eliminar el sistema: ",
						eliminaDetalle.getCveSistema());
			}
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/**
	 * Metodo para modificar un registro
	 */
	public void editaSistema() {
		System.out.println("************* proceso de edicion");
		ejecucionProceso = false;
		context = RequestContext.getCurrentInstance();
		if (detalleSeleccionado != null) {
			if (detalleSeleccionado.getCveSistema() != null
					&& !detalleSeleccionado.getCveSistema().equals("")
					&& detalleSeleccionado.getDesSistema() != null
					&& !detalleSeleccionado.equals("")) {
				if (consulta.actualizaSistema(detalleSeleccionado)) {
					ejecucionProceso = true;
					muestraResultado = false;
					sistemaDTO.setDescripcionSistema("");
					msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Se edito el sistema: ",
							detalleSeleccionado.getCveSistema());
				} else {
					msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error al  editar el sistema: ",
							detalleSeleccionado.getCveSistema());
				}

			}
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"error al editar el sistema: ", null);
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
		context.addCallbackParam("ejecucionProceso", ejecucionProceso);
	}

	/**
	 * @return the sistemaDTO
	 */
	public SistemaDTO getSistemaDTO() {
		return sistemaDTO;
	}

	/**
	 * @param sistemaDTO
	 *            the sistemaDTO to set
	 */
	public void setSistemaDTO(SistemaDTO sistemaDTO) {
		this.sistemaDTO = sistemaDTO;
	}

	/**
	 * @return the muestraResultado
	 */
	public boolean isMuestraResultado() {
		return muestraResultado;
	}

	/**
	 * @param muestraResultado
	 *            the muestraResultado to set
	 */
	public void setMuestraResultado(boolean muestraResultado) {
		this.muestraResultado = muestraResultado;
	}

	/**
	 * @return the detalleSeleccionado
	 */
	public Sistema getDetalleSeleccionado() {
		return detalleSeleccionado;
	}

	/**
	 * @param detalleSeleccionado
	 *            the detalleSeleccionado to set
	 */
	public void setDetalleSeleccionado(Sistema detalleSeleccionado) {
		System.out
				.println("detalle sel:" + detalleSeleccionado.getCveSistema());
		this.detalleSeleccionado = detalleSeleccionado;
	}

	/**
	 * @return the eliminaDetalle
	 */
	public Sistema getEliminaDetalle() {
		return eliminaDetalle;
	}

	/**
	 * @param eliminaDetalle
	 *            the eliminaDetalle to set
	 */
	public void setEliminaDetalle(Sistema eliminaDetalle) {
		this.eliminaDetalle = eliminaDetalle;
	}

}
