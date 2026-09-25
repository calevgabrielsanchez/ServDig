package mx.gob.imss.dacvass.scheduler.cron.service.entity;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.dacvass.scheduler.cron.dto.ProcOdiCompFiscDto;
import mx.gob.imss.dacvass.scheduler.cron.entity.ProcOdiCompFiscEntity;
import mx.gob.imss.dacvass.scheduler.cron.service.utility.ProcOdiCompFiscUtilityLocal;

@Stateless(name="consultaEstatusCfdiEntity", mappedName="consultaEstatusCfdiEntity")
public class ConsultaEstatusCfdiEntity implements ConsultaEstatusCfdiEntityLocal {
	
	private static final Logger log = LoggerFactory.getLogger(ConsultaEstatusCfdiEntity.class);
	
	@PersistenceContext(unitName = "consultaEstatusCfdiPersistenceUnit")
	protected EntityManager entityManager;
	
	@EJB
	ProcOdiCompFiscUtilityLocal procOdiCompFiscUtility;

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcOdiCompFiscDto> obtenerUuidParaConsultar() {
		log.info("############ SE OBTIENEN LOS UUID PARA CONSULTAR LOS ESTATUS ############");
		
		List<ProcOdiCompFiscDto> listProcOdiCompFiscDto = null;
		List<ProcOdiCompFiscEntity> listProcOdiCompFiscEntity = null;
		ProcOdiCompFiscDto procOdiCompFiscDto = null;
		
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("select p from ProcOdiCompFiscEntity p ");
		stringBuffer.append("where p.cveCodigoRespuesta in (902, 911)");

		Query query = this.entityManager.createQuery(stringBuffer.toString());
		listProcOdiCompFiscEntity = query.getResultList();
		
		if (listProcOdiCompFiscEntity != null && !listProcOdiCompFiscEntity.isEmpty()) {
			log.info("############ EL TAMAÑO DE LA LISTA DE UUID ES [" + listProcOdiCompFiscEntity.size() + "] ############");
			
			listProcOdiCompFiscDto = new ArrayList<ProcOdiCompFiscDto>();
			
			log.info("############ TRANSFORMANDO ENTIDAD ProcOdiCompFiscEntity A OBJETO DTO ProcOdiCompFiscDto ############");
			for (ProcOdiCompFiscEntity procOdiCompFiscEntity : listProcOdiCompFiscEntity) {
				procOdiCompFiscDto = new ProcOdiCompFiscDto();
				procOdiCompFiscDto = procOdiCompFiscUtility.convertirEntityToModel(procOdiCompFiscEntity);
				listProcOdiCompFiscDto.add(procOdiCompFiscDto);
			}
			log.info("############ FINALIZA TRANSFORMACION DE ENTIDAD ProcOdiCompFiscEntity A OBJETO DTO ProcOdiCompFiscDto ############");
			
		} else {
			log.info("############ NO SE OBTUVIERON UUID PARA VALIDAR ESTATUS DE CFDI ############");
		}
		
		return listProcOdiCompFiscDto;
	}

	@Override
	public void actualizaCodigoRespuesta(ProcOdiCompFiscDto procOdiCompFiscDto) {
		log.info("############ SE ACTUALIZARA EL REGISTRO CON CVE_REGISTRO [" + procOdiCompFiscDto.getCveRegistro() + "] EL CODIGO DE RESPUESTA EN LA TABLA PROC_ODI_COMP_FISC ############");
		
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("update PROC_ODI_COMP_FISC ");
		stringBuffer.append("set CVE_CODIGORESPUESTA = :codigoRespuesta ");
		stringBuffer.append("where CVE_REGISTRO = :cveRegistro");
		
		Query query = this.entityManager.createNativeQuery(stringBuffer.toString());
		query.setParameter("codigoRespuesta", procOdiCompFiscDto.getCveCodigoRespuesta());
		query.setParameter("cveRegistro",	procOdiCompFiscDto.getCveRegistro());
		int rowCount = query.executeUpdate();
		
		log.info("############ SE ACTUALIZO [" + rowCount +"] DEL REGISTRO CON CVE_REGISTRO [" + procOdiCompFiscDto.getCveRegistro() + "] EN LA TABLA PROC_ODI_COMP_FISC ############");
	}

}
