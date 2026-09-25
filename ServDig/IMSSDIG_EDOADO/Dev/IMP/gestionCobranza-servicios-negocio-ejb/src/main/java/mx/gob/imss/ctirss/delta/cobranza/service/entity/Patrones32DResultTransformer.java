package mx.gob.imss.ctirss.delta.cobranza.service.entity;

import java.math.BigDecimal;

import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.CausaBajaPatronEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

import org.hibernate.transform.BasicTransformerAdapter;

public class Patrones32DResultTransformer extends BasicTransformerAdapter {

	private static final long serialVersionUID = 1L;
	public final static Patrones32DResultTransformer INSTANCE;

	static {
		INSTANCE = new Patrones32DResultTransformer();
	}

	private Patrones32DResultTransformer() {

	}

	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {

		SujetoObligado so = new SujetoObligado();

		so.setNumeroRegistroPatronal(tuple[0].toString());
		
		Modalidad modalidad = new Modalidad();
		modalidad.setNumModalidad(tuple[1].toString());
		so.setModalidad(modalidad);
		
		so.setDigVerificador(tuple[2].toString());
		
		Delegacion delegacion = new Delegacion();
		delegacion.setClave(tuple[3].toString());
		
		Subdelegacion subdelegacion = new Subdelegacion();
		subdelegacion.setClave(tuple[4].toString());
		subdelegacion.setDescripcion(tuple[5].toString());
		subdelegacion.setDelegacion(delegacion);
		so.setSubdelegacion(subdelegacion);
		
		so.setNumeroTrabajadores(tuple[6] == null ? 0 : ((BigDecimal)tuple[6]).intValue());
		
		boolean indBaja = (((BigDecimal)tuple[7]).intValue()) == 0 ? false : true;
		if (indBaja) {
			so.setDescSituacionBaja(CausaBajaPatronEnum.BAJA.getDescripcion());
		}
		
		boolean indHuelga = (((BigDecimal)tuple[8]).intValue()) == 0 ? false : true;
		if (indHuelga) {
			so.setDescSituacionBaja(CausaBajaPatronEnum.HUELGA.getDescripcion());
		}
		
		if(!indBaja && !indHuelga) {
			so.setDescSituacionBaja(tuple[9].toString());
		}
		
		return so;
	}

}
