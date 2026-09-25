package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.utility;

import java.math.BigInteger;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.derechohabiente.ClavePresupuestal;
import mx.gob.imss.ctirss.delta.model.derechohabiente.NivelAtencion;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoUMF;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.persistence.AccNivelAtencion;
import mx.gob.imss.ctirss.delta.persistence.DgDomicilioGeografico;
import mx.gob.imss.ctirss.delta.persistence.DicClavePresupuestal;
import mx.gob.imss.ctirss.delta.persistence.DicDelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicTipoUmf;
import mx.gob.imss.ctirss.delta.persistence.DicUmf;

@Stateless
public class UnidadMedicaFamiliarServiceUtility extends AbstractServiceUtility
		implements UnidadMedicaFamiliarServiceUtilityLocal {
	@Override
	public UnidadMedicaFamiliar convertirEntityToModel(DicUmf dicUmf) throws Exception {
		UnidadMedicaFamiliar unidadMedicaFamiliar = null;

		if (dicUmf != null) {
			unidadMedicaFamiliar = new UnidadMedicaFamiliar();
			unidadMedicaFamiliar.setIdUMF(dicUmf.getCveIdUmf());
			unidadMedicaFamiliar.setDescripcion(dicUmf.getNomUnidad());
			unidadMedicaFamiliar.setNombreCorto(dicUmf.getNomCorto());
			unidadMedicaFamiliar.setGeneracionCita(dicUmf.getIndGeneracionCita());
			unidadMedicaFamiliar.setNoConsultorio(dicUmf.getNumConsultorio());
			unidadMedicaFamiliar.setNoEconomico(dicUmf.getNumEconom());
			Subdelegacion subdelegacion = convertirEntityToModelSubdelegacion(dicUmf.getDicSubdelegacion());
			unidadMedicaFamiliar.setSubdelegacion(subdelegacion);
			unidadMedicaFamiliar.setClavePresupuestal(convertirEntityToModelClavePresupuestal(
					dicUmf.getDicClavePresupuestal()));
			unidadMedicaFamiliar.setNivelAtencion(convertirEntityToModelNivelAtencion(
					dicUmf.getAccNivelAtencion()));

			unidadMedicaFamiliar.setTipoUMF(convertirEntityToModelTipoUMF(dicUmf.getDicTipoUmf()));
		}

		return unidadMedicaFamiliar;
	}

	private TipoUMF convertirEntityToModelTipoUMF(DicTipoUmf dicTipoUmf) {
		TipoUMF tipoUMF = null;

		if (dicTipoUmf != null) {
			tipoUMF = new TipoUMF();
			tipoUMF.setIdTipoUMF(new BigInteger(String.valueOf(dicTipoUmf.getCveIdTipoUmf())));
			tipoUMF.setDescripcion(dicTipoUmf.getDesTipoUmf());
		}

		return tipoUMF;
	}

	private NivelAtencion convertirEntityToModelNivelAtencion(AccNivelAtencion accNivelAtencion) {
		NivelAtencion nivelAtencion = null;

		if (accNivelAtencion != null) {
			nivelAtencion = new NivelAtencion();
			nivelAtencion.setIdNivelAtencion(new Long(accNivelAtencion.getCveNivelAtencion()));
		}

		return nivelAtencion;
	}

	private ClavePresupuestal convertirEntityToModelClavePresupuestal(DicClavePresupuestal dicClavePresupuestal) {
		ClavePresupuestal clavePresupuestal = null;
		
		if (dicClavePresupuestal != null) {
			clavePresupuestal = new ClavePresupuestal();
			clavePresupuestal.setIdClavePresupuestal(dicClavePresupuestal.getCveIdClavePresupuestal());
			clavePresupuestal.setClavePresupuestal(dicClavePresupuestal.getCvePresupuestal());
			clavePresupuestal.setDescripcion(dicClavePresupuestal.getDesClavePresupuestal());
		}
		
		return clavePresupuestal;
	}

	private String convertirEntityToModelDesDireccion(
			DgDomicilioGeografico dgDomicilioGeografico) {
		StringBuffer desDireccion = new StringBuffer("");

		String calle = "";
		String numero = "";
		String colAsent = "";
		String cp = "";
		String delegacion = "";
		String entidadFederativa = "";
		String sl = " ";

		if (dgDomicilioGeografico != null) {
			try {
				calle = dgDomicilioGeografico.getDgVialidadByCveViaPrin().getNomVia();
			} catch (Exception e) {
			}
			if (dgDomicilioGeografico.getNumextnum() != null) {
				numero = dgDomicilioGeografico.getNumextnum().toString();
			}
			if (dgDomicilioGeografico.getDgAsentamiento() != null) {
				colAsent = dgDomicilioGeografico.getDgAsentamiento().getNomAsen();
				try {
					delegacion = dgDomicilioGeografico.getDgAsentamiento().getDgCatMunicipio().getNomMun();
					if (dgDomicilioGeografico.getDgAsentamiento().getDgCatMunicipio().getDgCatEstado() != null) {
						entidadFederativa = dgDomicilioGeografico.getDgAsentamiento()
								.getDgCatMunicipio().getDgCatEstado().getNomEnt();
					}
				} catch (Exception e) {
				}
			}
			if (dgDomicilioGeografico.getDgCodigosPostale() != null) {
				cp = dgDomicilioGeografico.getDgCodigosPostale().getId().getCodigo();
			}
		}

		desDireccion.append("Calle:");
		desDireccion.append(calle);
		desDireccion.append(" Num.");
		desDireccion.append(numero);
		desDireccion.append(sl);
		desDireccion.append("Colonia/Asentamiento:");
		desDireccion.append(colAsent);
		desDireccion.append(sl);
		desDireccion.append("CP:");
		desDireccion.append(cp);
		desDireccion.append(sl);
		desDireccion.append("Delegacion:");
		desDireccion.append(delegacion);
		desDireccion.append(sl);
		desDireccion.append("EntidadFederativa:");
		desDireccion.append(entidadFederativa);

		return desDireccion.toString();
	}

	private Subdelegacion convertirEntityToModelSubdelegacion(DicSubdelegacion dicSubdelegacion) {
		Subdelegacion subdelegacion = null;

		if (dicSubdelegacion != null) {
			subdelegacion = new Subdelegacion();
			subdelegacion.setId(dicSubdelegacion.getCveIdSubdelegacion());
			subdelegacion.setDescripcion(dicSubdelegacion.getDesSubdelegacion());
			subdelegacion.setClave(dicSubdelegacion.getClaveSubdelegacion());

			Delegacion delegacion = convertirEntityToModelDelegacion(dicSubdelegacion
					.getDicDelegacion());
			subdelegacion.setDelegacion(delegacion);
		}

		return subdelegacion;
	}

	private Delegacion convertirEntityToModelDelegacion(DicDelegacion dicDelegacion) {
		Delegacion delegacion = null;

		if (dicDelegacion != null) {
			delegacion = new Delegacion();
			delegacion.setDescripcion(dicDelegacion.getDesDeleg());
			delegacion.setId(dicDelegacion.getCveIdDelegacion());
			delegacion.setClave(dicDelegacion.getClaveDelegacion());
			delegacion.setCiz(dicDelegacion.getCveCiz());
		}

		return delegacion;
	}

}
