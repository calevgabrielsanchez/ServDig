package mx.gob.imss.ctirss.delta.cobranza.service.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.cobranza.service.entities.DrtFecCierrOpCob;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;

@Stateless(name = "fechaCierreEdoAdoEntity", mappedName = "fechaCierreEdoAdoEntity")
public class FechaCierreEdoAdoEntity extends AbstractEntity implements
		FechaCierreEdoAdoEntityLocal {

	@Override
	public String getUltimaFechaCorte() {
		
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy", new Locale("ES"));
		
		Criteria consulta = this.getSession().createCriteria(DrtFecCierrOpCob.class);
		consulta.setProjection(Projections.max("fecCierreOpCob"));
		
		Date fechaAlta = (Date) consulta.uniqueResult();
		String fecha = formateador.format(fechaAlta);
		
		return fecha;
	}

}
