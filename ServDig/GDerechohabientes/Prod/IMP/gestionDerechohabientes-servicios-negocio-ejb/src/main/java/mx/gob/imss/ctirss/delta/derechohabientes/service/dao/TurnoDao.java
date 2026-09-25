package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.TurnoParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Turno;
import mx.gob.imss.ctirss.delta.persistence.DicTurno;



@Stateless(name = "turnoDao", mappedName = "turnoDao")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TurnoDao extends AbstractServiceEntity implements TurnoDaoLocal  {

	@Override
	public DicTurno getTurno(Long idTurno) {
		DicTurno resp = null;

		
		Criteria queryTurno = this.getSession().createCriteria(DicTurno.class);
		queryTurno.add(Restrictions.eq("cveIdTurno", idTurno));
		queryTurno.add(Restrictions.isNull("fecRegistroBaja"));
		resp = (DicTurno) queryTurno.uniqueResult();
			
		
		return resp;
	}
	
	@Override
	public  List<Turno> getTurnos() throws DerechohabientesBusinessException{
		
		List<Turno> turnos = null;
		
		try {
			
			Criteria queryTurno = this.getSession().createCriteria(DicTurno.class);
			queryTurno.add(Restrictions.isNull("fecRegistroBaja"));
			
			List<DicTurno> dicTurnos = queryTurno.list();
			
			if(dicTurnos != null && !dicTurnos.isEmpty()){
				turnos = new ArrayList<Turno>();
				
				for(DicTurno dicTurno : dicTurnos) {
					Turno turno = TurnoParser.persisToModel(dicTurno);
					turnos.add(turno);
				}
			}
			
			
		} catch (Exception e) {
			log.error("Error - getMedicosByUMF", e);
			DerechohabientesBusinessException.throwException("No fue posible consultar los turnos");
		}		
		
		return turnos;
	
	}
	
	@Override
	public Turno getTurnoByID(Long idTurno) throws DerechohabientesBusinessException{
		DicTurno resp = null;
		Criteria queryTurno = this.getSession().createCriteria(DicTurno.class);
		queryTurno.add(Restrictions.eq("cveIdTurno", idTurno));
		queryTurno.add(Restrictions.isNull("fecRegistroBaja"));
		resp = (DicTurno) queryTurno.uniqueResult();
			
		Turno turno = TurnoParser.persisToModel(resp);
		return turno;
	}

	



	
}
