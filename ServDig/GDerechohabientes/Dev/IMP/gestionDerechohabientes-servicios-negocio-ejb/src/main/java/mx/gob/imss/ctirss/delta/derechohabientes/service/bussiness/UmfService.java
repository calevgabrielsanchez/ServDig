package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.DelegacionDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.MedicoEnTurnoDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.UmfCodigoPostalDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.UmfDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.UmfTurnoDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.UmfServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.UnidadMedicaFamiliarParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.CodigoSinUmfException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Consultorio;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Turno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.persistence.DicUmf;
import mx.gob.imss.ctirss.delta.persistence.DitUmfCodPo;

@Stateless(name="umfService",mappedName="umfService")
public class UmfService  extends AbstractServiceBusiness implements UmfServiceRemote,UmfServiceLocal{

	@EJB(name = "subDelegacionDAO") 
	private UmfDaoLocal umfDao;
	@EJB(name = "medicoEnTurnoDao") 
	private MedicoEnTurnoDaoLocal medicoEnTurnoDaoLocal;
	@EJB(name = "delegacionDAO") 
	private DelegacionDaoLocal delegacionDaoLocal;
	@EJB(name = "umfCodigoPostalDAO") 
	private UmfCodigoPostalDaoLocal umfCodigoPostalDaoLocal;
	@EJB(name = "umfTurnoDao") 
	private UmfTurnoDaoLocal umfTurnoDao;
	
	/**
	 * Metodo para obtener el consultorio con menor poblacion en una umf
	 * paraun turno especificado
	 * @param idUmf
	 * @param idTurno
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public Consultorio getConsultorioMenorPoblacion(Long idUmf,
			Long idTurno, Boolean mostrarVirtuales) throws DerechohabientesBusinessException {
		
		Consultorio consultorio = null;
		
		consultorio = umfDao.getConsultorioConMenorPoblacion(idUmf, idTurno, mostrarVirtuales);
		
		return consultorio;
	}


	@Override
	public List<MedicoEnTurno> getMedicosByUmf(Long idUmf)  throws DerechohabientesBusinessException{
		List<MedicoEnTurno> medicos = null;
		
			try {
				umfDao.getMedicosByUMF(idUmf);
			} catch (Exception e) {
				log.error("error al obtener medicos",e);
			}
		
		if(medicos == null) 
			DerechohabientesBusinessException.throwException(ExceptionMessages.SIN_INFORMACION, "");
		
		return medicos;
	}
	

	/**
	 * Metodo para obtener los turnos disponibles en uns UMF
	 * @param idUmf - La umf de donde se quieren obtener las umfs disponibles
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public List<Turno> getTurnosDisponiblesPorUmf(Long idUmf)
			throws DerechohabientesBusinessException {
		List<Turno> turnos = null;
		
		turnos = umfDao.findTurnosDisponiblesPorUmf(idUmf);
		
		return turnos;
	}



	@Override
	public MedicoFamiliar getMedico(Long idMedico)  throws DerechohabientesBusinessException{
		MedicoFamiliar medico = null;
		try {
			medico = umfDao.getMedico(idMedico);
		} catch (Exception e) {
			log.error("error al obtener medico",e);
		}
		return medico;
	}
	
	@Override
	public List<UnidadMedicaFamiliar> findUmfbySubDelagacionDelegacion(Long idSubdelegacion)  throws DerechohabientesBusinessException{
		List<UnidadMedicaFamiliar> datos = null;
		try {
			List<DicUmf> umfs = umfDao.findUmfbySubDelagacion(idSubdelegacion);
			datos =   UnidadMedicaFamiliarParser.persisToModelList(umfs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datos;
	}
	
	/**
	 * Metodo que obtiene los  consultorio disponibles en un turno
	 * para una umf
	 * @param idUmf - La umf donde se buscaran
	 * @param idTurno - El turno en donde se buscaran los consultorios
	 */
	@Override
	public List<Consultorio> findConsultorioByUmfTurno(Long idUmf, Long idTurno, Boolean mostrarVirtuales)  throws DerechohabientesBusinessException{
		List<Consultorio> datos = null;
		try {
			datos = umfDao.findConsultoriosByUmfTurno(idUmf, idTurno, mostrarVirtuales);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datos;
	}
	
	

	@Override
	public List<MedicoEnTurno> findMedicosByUmfTurnoConsultorioConPoblacion(
			Long idUmf, Long idTurno, Long idConsultorio)
			throws DerechohabientesBusinessException {
		// TODO Auto-generated method stub
		List<MedicoEnTurno> datos = this.findMedicosByUmfTurnoConsultorio(idUmf, idTurno, idConsultorio);
		List<MedicoEnTurno> datosAux = null;
		
		if(datos != null && !datos.isEmpty()) {
			datosAux = new ArrayList<MedicoEnTurno>();

			for(MedicoEnTurno medico: datos) {
				try{
					medico.setPoblacion(medicoEnTurnoDaoLocal.getPoblacionByIdConsturnoMedico(medico.getIdMedicoContultorioTurno()));
				} catch(Exception e) {
					log.debug("no se pudo obtener la poblacion del consultorio",e);
				}
				datosAux.add(medico);
			}
		}
		
		return datosAux;
	}


	@Override
	public List<MedicoEnTurno> findMedicosByUmfTurnoConsultorio(Long idUmf,
			Long idTurno, Long idConsultorio)  throws DerechohabientesBusinessException{
		// TODO Auto-generated method stub
		List<MedicoEnTurno> datos = null;
		try {
			
			datos = umfDao.findMedicosByUmfTurnoConsultorio(idUmf, idTurno, idConsultorio);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datos;
	}
	
	
	
	@Override
	public List<MedicoEnTurno> findMedicosPoblacionByUmfTurno(Long idUmf,
			Long idTurno) throws DerechohabientesBusinessException {
		List<MedicoEnTurno> datos = null;
		try {
			datos = medicoEnTurnoDaoLocal.getMedicosPoblacionByUmfTurno(idUmf, idTurno);
		} catch (Exception e) {
			e.printStackTrace();
			DerechohabientesBusinessException.throwException("No de pudieron recuperar a los medicos", ExceptionMessages.ERROR_DATOS);
		}
		return datos;
	}

	public List<MedicoEnTurno> findMedicosByUmfTurno( Long idUmf, Long idTurno) throws DerechohabientesBusinessException{
		// TODO Auto-generated method stub
		List<MedicoEnTurno> datos = null;
		try {
			datos = umfDao.findMedicosByUmfTurno(idUmf, idTurno);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datos;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<MedicoEnTurno> findMedicosByUmfTurnoConsultorioMedicoEsp(
			Long idUmf, Long idTurno, Long idConsultorio,
			Long idMedicoEspecialidad) throws DerechohabientesBusinessException, Exception {
		
		List<MedicoEnTurno> datos = null;
		
		datos = umfDao.findMedicosByUmfTurnoConsultorioMedicoEsp(idUmf, idTurno, idConsultorio,idMedicoEspecialidad);
		Collections.sort(datos, new Comparator() {

			public int compare(Object o1, Object o2) {
				MedicoEnTurno m1 = (MedicoEnTurno) o1;
				MedicoEnTurno m2 = (MedicoEnTurno) o2;
				
				return m1.getMedicoFamiliar().getNombre().compareTo(m2.getMedicoFamiliar().getNombre());
			}
		});
		return datos;
	}	
	
	@Override
	public List<CodigoPostal> findCodigosPostalByUmf(Long idUmf)  throws DerechohabientesBusinessException{
		// TODO Auto-generated method stub
		List<CodigoPostal> datos = null;
		
		try {
			umfDao.findCodigosPostalesByUmf(idUmf);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return datos;
	}

	@Override
	public List<Asentamiento> findAsentamientosByUmg(Long idUmf)  throws DerechohabientesBusinessException{
		// TODO Auto-generated method stub
		List<Asentamiento> datos = null;
		try {
			datos = umfDao.findAsentamientosByUmf(idUmf);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datos;
	}
	
	@Override
	public List<EntidadFederativa> findEstadosByDelegacion(Long idDelegacion) throws DerechohabientesBusinessException, Exception{
		// TODO Auto-generated method stub
		List<EntidadFederativa> datos = delegacionDaoLocal.findEntidaFederativaByDelegacion(idDelegacion);
		return datos;
	}

	@Override
	public List<Municipio> findMunicipiosByDelegacionEstado(Long idDelegacion,
			Long idEstado)  throws DerechohabientesBusinessException,Exception{
		// TODO Auto-generated method stub
		List<Municipio> datos = delegacionDaoLocal.findMunicipiosByDelegacionEstado(idDelegacion, idEstado);
		return datos;
	}

	@Override
	public List<Asentamiento> finAsentamientosByDelegacionEstadoMunicipio(
			Long idDelegacion, Long idEstado, Long idMunicipio)  throws DerechohabientesBusinessException,Exception{
		// TODO Auto-generated method stub
		List<Asentamiento> datos = delegacionDaoLocal.findAsentamientoByDelegacionMunicipioEntidad(idDelegacion, idMunicipio, idEstado);
		return datos;
	}

	
	@Override
	public List<UnidadMedicaFamiliar> findUmfByCodigoPostal(String codigoPostal) throws DerechohabientesBusinessException,CodigoSinUmfException,Exception {
		List<UnidadMedicaFamiliar> datos = null;
		datos = umfCodigoPostalDaoLocal.getUmfByCodigoPostal(codigoPostal);
		
		return datos;
	}
	
	@Override
	public List<UnidadMedicaFamiliar> findUmfByCodigoPostal(String codigoPostal, Integer notEqualIndUmfCfe) throws DerechohabientesBusinessException,CodigoSinUmfException,Exception {
		
		List<UnidadMedicaFamiliar> datos = null;
		datos = umfCodigoPostalDaoLocal.getUmfByCodigoPostal(codigoPostal,notEqualIndUmfCfe);
		
		return datos;
	}
	
	

	@Override
	public List<UnidadMedicaFamiliar> findUmfsByAsentamiento(
			Asentamiento asentamiento, Boolean incrluirCFE)
			throws DerechohabientesBusinessException, CodigoSinUmfException,
			Exception {
		
		return umfCodigoPostalDaoLocal.getUmfByAsentamiento(asentamiento, incrluirCFE);
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Consultorio> findConsultorioByUmfTurnoMedicoEsp(Long idUmf,
			Long idTurno, Long idMedicoEspecialidad)
			throws DerechohabientesBusinessException, Exception {
		
		List<Consultorio> datos = null;		
		
		datos = umfDao.findConsultoriosByUmfTurnoMedicoEsp(idUmf, idTurno, idMedicoEspecialidad);
		Collections.sort(datos, new Comparator() {

			public int compare(Object o1, Object o2) {
				Consultorio c1 = (Consultorio) o1;
				Consultorio c2 = (Consultorio) o2;
				
				return c1.getDescripcion().compareTo(c2.getDescripcion());
			}
		});
		return datos;
	}

	@Override
	public UnidadMedicaFamiliar getUnidadMedicaFamiliarById(Long idUmf)
			throws DerechohabientesBusinessException {
		DicUmf dicUmf = null;
		try {
			dicUmf = umfTurnoDao.getUmfById(idUmf);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DerechohabientesBusinessException.throwException(ExceptionMessages.ERROR_DATOS, e.getCause().getMessage());
		}
		return UnidadMedicaFamiliarParser.persisToModel(dicUmf);
	}

	@Override
	public Boolean mismaCircunscripcion(Long idUmfOrigen, Long idUmfDestino)
			throws DerechohabientesBusinessException {
		// TODO Auto-generated method stub
		DicUmf origen = null;
		DicUmf destino = null;
		
		try {
			origen = umfTurnoDao.getUmfById(idUmfOrigen);
			destino = umfTurnoDao.getUmfById(idUmfDestino);
			
			Long idDelOrigen = origen.getDicSubdelegacion().getDicDelegacion().getCveIdDelegacion();
			Long idDelDestino = destino.getDicSubdelegacion().getDicDelegacion().getCveIdDelegacion();
			
			if(idDelOrigen.equals(idDelDestino)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			DerechohabientesBusinessException.throwException(ExceptionMessages.ERROR_DATOS, e.getCause().getMessage());
		}
		return false;
	}   
	
	@Override
	public UnidadMedicaFamiliar findUmfCodPosByCodPos(String codigoPostal) throws DerechohabientesBusinessException {
		DitUmfCodPo ditUmf = null;
		UnidadMedicaFamiliar umf = null;
		
		try {
			ditUmf = umfCodigoPostalDaoLocal.getUmfCodPosByCodPos(codigoPostal);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DerechohabientesBusinessException();
		}		
		
		umf =  UnidadMedicaFamiliarParser.persisToModel(ditUmf);		
		
		return umf;
	}
	
	/**metodo que consulta las UMF asociadas a una subdelegacion filtrando el nivel de atencion en caso de ser nulo no se concidera como filtro**
	 * 
	 * @param idSubdelegacion
	 * @param nivelAtencion
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws Exception
	 */
	@Override
	public List<UnidadMedicaFamiliar> findUnidadesBySubdelegacionNivelAtencion(Long idSubdelegacion, Long nivelAtencion)throws DerechohabientesBusinessException,Exception {
			log.debug("llege al servio para consultar umb por subdelegacion y nivel atencion " + idSubdelegacion + "****" + nivelAtencion);
		 return umfDao.findUnidadesBySubdelegacionNivelAtencion(idSubdelegacion, nivelAtencion );
	}
	
	
}

