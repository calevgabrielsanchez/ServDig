package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.DiasFestivosDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.SolicitudCitaEntityLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.UmfCodigoPostalDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.UmfTurnoDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.AgendarCitaServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.util.DateUtils;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.DiasFestivosParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.UmfTurnoParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.UnidadMedicaFamiliarParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.AgendarCitaSinCapacidadException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractService;
import mx.gob.imss.ctirss.delta.model.derechohabiente.DiasFestivos;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UMFTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.TurnoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.CitaSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DgAsentamiento;
import mx.gob.imss.ctirss.delta.persistence.DgAsentamientoPK;
import mx.gob.imss.ctirss.delta.persistence.DgDomicilioGeografico;
import mx.gob.imss.ctirss.delta.persistence.DicUmf;
import mx.gob.imss.ctirss.delta.persistence.DitUmfTurno;

@Stateless(name = "agendarCitaService", mappedName = "agendarCitaService")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AgendarCitaService extends AbstractService implements AgendarCitaServiceRemote,AgendarCitaServiceLocal {

	private static final BigDecimal SIN_CITA = new BigDecimal(0);
	private static int MAXIMO_DIAS=60;
	private static int DIAS_A_RECORRER_SIN_CITA=1;

	

	@EJB
	private DiasFestivosDaoLocal diasFestivosDao;
	@EJB
	private UmfCodigoPostalDaoLocal umfCodigoPostalDao;
	@EJB
	private UmfTurnoDaoLocal umfTurnoDao;
	@EJB
	private SolicitudCitaEntityLocal solicitudCitaEntity;


	
	
	@Override
	public CitaSolicitud getCitaByUmf(Long idUmf)
			throws DerechohabientesBusinessException, Exception {
		// TODO Auto-generated method stub
		boolean turnoEncontrado=false;
		int diasRecorridos=0;
		
		CitaSolicitud cita = new CitaSolicitud();
		
		DicUmf dicUmf = null;
		List<Date> fechasInhabiles=null;
		
		List <DitUmfTurno> ditUmfTurnos=null;
		
		Date fechaInicial=new Date();
		Date fechaProxCita=new Date();
		//obtener los dias inabiles
		dicUmf = umfTurnoDao.getUmfById(idUmf);
		
		ditUmfTurnos=dicUmf.getDitUmfTurnos();
		fechasInhabiles=this.getFechasInhabiles();
		UnidadMedicaFamiliar umf = UnidadMedicaFamiliarParser.persisToModel(dicUmf);
		
		if(!umf.isGeneracionCita().equals(SIN_CITA)){
		
			
			while(!turnoEncontrado&&diasRecorridos<=MAXIMO_DIAS){//mientras no encontremos el turno y no se hallan recorrido 60 dias buscar un turno

				//buscar Solicitudes UMFpara el dia habil matutino hasta que exista
				if(!turnoEncontrado){	//si no encontramos el turno se usca la proxima fecha habil para buscar cita
					//fechaProxCita recorrerla un dia
					fechaProxCita=DateUtils.recorrerUnDia(fechaProxCita);
					//Buscar proxima fecha habil
					fechaProxCita=DateUtils.getProximaFechaHAbil(fechaProxCita, fechasInhabiles);
					//contar dias que se recorrieron
					diasRecorridos=DateUtils.getDaysBetweenDates(fechaInicial, fechaProxCita);

				}

				turnoEncontrado=this.encontrarUMFconCapacidad(TurnoEnum.MATUTINO.getId(), cita, fechaProxCita, umf,ditUmfTurnos);

			}
			//fin del ciclo
			if(!turnoEncontrado){
				AgendarCitaSinCapacidadException.throwException();
			}
		}else{//SI NO HAY CITAS
		
			//busca el umfTurno matutino
			
			UMFTurno umfTurno = this.getUmfTurno(TurnoEnum.MATUTINO.getId(), ditUmfTurnos);
			
			//rrecorrer 60 Dias
			fechaProxCita=DateUtils.recorrerNDias(fechaProxCita,DIAS_A_RECORRER_SIN_CITA);
			//Buscar proxima fecha habil
			fechaProxCita=DateUtils.getProximaFechaHAbil(fechaProxCita, fechasInhabiles);
			
			
			cita.setFechaHora(fechaProxCita);
			cita.setUmf(umfTurno.getUnidadMedicaFamiliar());
			cita.setTurno(umfTurno.getTurno());
			
			
		}

		
		return cita;
	}

	@Override
	public CitaSolicitud getCita(Domicilio domicilio) throws Exception{ 
		DgDomicilioGeografico dgDomicilioGeografico=null;
		boolean turnoEncontrado=false;
		int diasRecorridos=0;
		
		CitaSolicitud cita = new CitaSolicitud();
		UnidadMedicaFamiliar umf=null;
		DicUmf dicUmf=null;
		List<UnidadMedicaFamiliar> umfs = null;

		List<Date> fechasInhabiles=null;
		
		List <DitUmfTurno> ditUmfTurnos=null;
		
		Date fechaInicial=new Date();
		Date fechaProxCita=new Date();
		//obtener los dias inabiles
		fechasInhabiles=this.getFechasInhabiles();
		
		//Buscar la Umf corresondiente al domicilio 
		//dgDomicilioGeografico=DomicilioParser.modelToPersist(domicilio);
		dgDomicilioGeografico=this.myDomicilioParser(domicilio);
		//
		dicUmf=this.umfCodigoPostalDao.getUMFDomicilio(dgDomicilioGeografico);
		
		ditUmfTurnos=dicUmf.getDitUmfTurnos();
		
		umf=UnidadMedicaFamiliarParser.persisToModel(dicUmf);
		//si  hay citas en esa UMF
		if(umf.isGeneracionCita() != null && !umf.isGeneracionCita().equals(SIN_CITA)){
		
			
			while(!turnoEncontrado&&diasRecorridos<=MAXIMO_DIAS){//mientras no encontremos el turno y no se hallan recorrido 60 dias buscar un turno

				//buscar Solicitudes UMFpara el dia habil matutino hasta que exista
				if(!turnoEncontrado){	//si no encontramos el turno se usca la proxima fecha habil para buscar cita
					//fechaProxCita recorrerla un dia
					fechaProxCita=DateUtils.recorrerUnDia(fechaProxCita);
					//Buscar proxima fecha habil
					fechaProxCita=DateUtils.getProximaFechaHAbil(fechaProxCita, fechasInhabiles);
					//contar dias que se recorrieron
					diasRecorridos=DateUtils.getDaysBetweenDates(fechaInicial, fechaProxCita);

				}

				turnoEncontrado=this.encontrarUMFconCapacidad(TurnoEnum.MATUTINO.getId(), cita, fechaProxCita, umf,ditUmfTurnos);

			}
			//fin del ciclo
			if(!turnoEncontrado){
				AgendarCitaSinCapacidadException.throwException();
			}
		}else{//SI NO HAY CITAS
		
			//busca el umfTurno matutino
			UMFTurno umfTurno = this.getUmfTurno(TurnoEnum.MATUTINO.getId(), ditUmfTurnos);
			//rrecorrer 60 Dias
			fechaProxCita=DateUtils.recorrerNDias(fechaProxCita,DIAS_A_RECORRER_SIN_CITA);
			//Buscar proxima fecha habil
			fechaProxCita=DateUtils.getProximaFechaHAbil(fechaProxCita, fechasInhabiles); 
			cita.setFechaHora(fechaProxCita);
			cita.setUmf(umfTurno.getUnidadMedicaFamiliar());
			cita.setTurno(umfTurno.getTurno());
			
		}

		
		return cita;
	}
	
	/**
	 * @param turno
	 * @param solicitud
	 * @param fechaProxCita
	 * @param umf
	 * @return
	 * @throws Exception 
	 * @throws DerechohabientesBusinessException 
	 */
	private boolean encontrarUMFconCapacidad(long turno,  CitaSolicitud cita , Date fechaProxCita,UnidadMedicaFamiliar umf,List<DitUmfTurno> ditUmfTurnos) 
	throws DerechohabientesBusinessException, Exception{
		boolean resp=false;

		Long numSolicitudes=null;
		UMFTurno umfTurno=null;
		
		//turno Matutino
		umfTurno=this.getUmfTurno(turno, ditUmfTurnos);
		cita.setFechaHora(fechaProxCita);
		cita.setUmf(umfTurno.getUnidadMedicaFamiliar());
		cita.setTurno(umfTurno.getTurno());
		
		
		//buscar solicitud dao cuantas hay count
		numSolicitudes=this.solicitudCitaEntity.countSolicitudesFechaTurnoUmf(cita);
		if(umfTurno.getNoCita().intValue()>numSolicitudes.intValue()){//si la capacidad esmayor  que las solicitudes

			resp=true;
		}else{
			if(turno!=TurnoEnum.VESPERTINO.getId()){
				resp=encontrarUMFconCapacidad(TurnoEnum.VESPERTINO.getId(),cita,fechaProxCita,umf,ditUmfTurnos);
			}
		}
		return resp;

	}
	
	@Override 
	public List<Date> getFechasInhabiles() throws DerechohabientesBusinessException,Exception{
		List<Date> dateList=new ArrayList<Date>();
		List <DiasFestivos> diasFestivosL=DiasFestivosParser.persistToModelList(diasFestivosDao.findDiasFestivos());
		for(DiasFestivos dF:diasFestivosL){
			dateList.add(dF.getFecha());
		}
		return dateList;
	}

	private DgDomicilioGeografico myDomicilioParser(Domicilio domicilio){
		DgDomicilioGeografico dgDomicilioGeografico=new DgDomicilioGeografico();
		
		dgDomicilioGeografico.setDgAsentamiento(new DgAsentamiento());
		dgDomicilioGeografico.getDgAsentamiento().setId(new DgAsentamientoPK());
		dgDomicilioGeografico.getDgAsentamiento().getId().setCveAsen(domicilio.getAsentamiento().getClave().toString());
		//dgDomicilioGeografico.getDgAsentamiento().getId().setCveLoc(domicilio.getAsentamiento().getLocalidad().getClave().toString());
		dgDomicilioGeografico.getDgAsentamiento().getId().setCveMun(domicilio.getAsentamiento().getLocalidad().getMunicipio().getClave().toString());
		dgDomicilioGeografico.getDgAsentamiento().getId().setCveEnt(domicilio.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getClave().toString());
		//dgDomicilioGeografico.getDgAsentamiento().getId().setCvePeriodo(new Long(1).longValue());
		
		return dgDomicilioGeografico;
	}
	//se trae el turno por id Turno
	private UMFTurno getUmfTurno(Long idTurno,List<DitUmfTurno> ditUmfTurnos) throws DerechohabientesBusinessException{
		
		UMFTurno umfTurno=null;
		for(DitUmfTurno dicTurno:ditUmfTurnos){
			if(dicTurno.getDicTurno().getCveIdTurno()==idTurno.longValue()){
				umfTurno=UmfTurnoParser.persisToModel(dicTurno);
			}
			
		}
		
		return umfTurno;
	}


	
}
