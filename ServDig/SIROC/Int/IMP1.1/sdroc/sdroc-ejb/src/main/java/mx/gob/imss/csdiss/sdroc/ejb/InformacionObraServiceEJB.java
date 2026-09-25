package mx.gob.imss.csdiss.sdroc.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import mx.gob.imss.csdiss.sdroc.dto.CalendarioReporteDTO;
import mx.gob.imss.csdiss.sdroc.dto.InformacionIncidenciaDTO;
import mx.gob.imss.csdiss.sdroc.dto.InformacionObraDTO;
import mx.gob.imss.csdiss.sdroc.dto.RegistroPatronalDTO;
import mx.gob.imss.csdiss.sdroc.entity.RocCalendarioReporte;
import mx.gob.imss.csdiss.sdroc.entity.RotInformacionIncidencia;
import mx.gob.imss.csdiss.sdroc.exception.BusinessException;
import mx.gob.imss.csdiss.sdroc.orm.dao.CalendarioReporteDao;
import mx.gob.imss.csdiss.sdroc.orm.dao.InformacionIncidenciaDao;
import mx.gob.imss.csdiss.sdroc.orm.dao.InformacionObraDao;
import mx.gob.imss.csdiss.sdroc.service.interfaces.InformacionIncidenciaService;
import mx.gob.imss.csdiss.sdroc.service.interfaces.InformacionObraService;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;



@Interceptors(SpringBeanAutowiringInterceptor.class)
@Stateless(name = "ejb/InformacionObraServiceEJB", mappedName = "ejb/InformacionObraServiceEJB")
@Remote(InformacionObraService.class)
public class InformacionObraServiceEJB implements InformacionObraService,Serializable  {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3486558632160555538L;
	
	@Autowired
	private InformacionObraService informacionObraService;
	
	@Autowired
	InformacionObraDao informacionObraDao;
	
	@Autowired
	InformacionIncidenciaDao informacionIncidenciaDao;
	
	@Autowired
	InformacionIncidenciaService informacionIncidenciaService;
	
	@Autowired
	CalendarioReporteDao calendarioReporteDao;
	
	@Override
	public SujetoObligado consultarInfoPatron(String rp) {
		
		return informacionObraDao.getInfoPatron(rp);
	}

	/**
	 * Metodo "Recortado" para buscar las obras que se msotraran al inicio
	 */
	@Override
	public Map<String, Object> consultarObrasPorRfcYRp(String cveRfc, String cvRegPatronal, Long inicio, Long fin) throws BusinessException {
		
		List<InformacionObraDTO> obrasDto = null;
		List<InformacionObraDTO> obrasResult = null;
		Map<Integer, Object> calendarios = new HashMap<Integer, Object>();
		Map<String, Object> mapObras = informacionObraDao.findObrasByRfcAndRp(cveRfc, cvRegPatronal, inicio, fin);
		obrasDto = (List<InformacionObraDTO>) mapObras.get("obras");
		
		if(obrasDto != null && !obrasDto.isEmpty()) {
			obrasResult = new ArrayList<InformacionObraDTO>();
			Calendar calendar = Calendar.getInstance();
			CalendarioReporteDTO bimestreABuscar = null;
			int anio = 0;
			
			CalendarioReporteDTO rocCalendarioActual = null;
			Calendar calendarActual = Calendar.getInstance();
			int mesActualCalendario = calendarActual.get(Calendar.MONTH) + 1;
			System.out.println("El mes del calendatio inicial es " + mesActualCalendario);
			rocCalendarioActual = calendarioReporteDao.findMesPresentacionDto(String.valueOf(mesActualCalendario));
			calendarios.put(mesActualCalendario, rocCalendarioActual);
			
			for(InformacionObraDTO obraDto: obrasDto) {
				
				if(obraDto.getFecFinObra().before(new Date())) {
					calendar.setTime(obraDto.getFecFinObra());
				}  else {
					calendar.setTime(new Date());
				}
				
				int mesPresentacion = calendar.get(Calendar.MONTH) + 1;
				System.out.println("Voy a consultal el mes presentacion de la obra " + obraDto.getCveRegistroObra() + " - " +mesPresentacion);

				CalendarioReporteDTO almacenado = (CalendarioReporteDTO) calendarios.get(mesPresentacion);
				if( almacenado != null) {
					System.out.println("Ya estaba almacenado el calendario para el mes " + mesPresentacion);
					bimestreABuscar = almacenado;
				} else {
					System.out.println("No estaba almacenado elcalendario para el mes " + mesPresentacion);
					bimestreABuscar = calendarioReporteDao.findMesPresentacionDto(String.valueOf(mesPresentacion));
					calendarios.put(mesPresentacion, bimestreABuscar);
				}
				
				if(bimestreABuscar.getCveBimCalendario() == 6) {
					anio = calendar.get(Calendar.YEAR) -1;
				} else {
					anio = calendar.get(Calendar.YEAR);
				}
				
				int numIncumplimientos = 0;
				int resultado = 0;
				//validar regVerde
				numIncumplimientos = informacionIncidenciaDao.numReporBimestralByCveInfoPresentadas(obraDto.getCveInformacionObra(), bimestreABuscar.getCveBimCalendario(), anio);

				if(numIncumplimientos == 0){
					numIncumplimientos = findIncumplimiento(obraDto, resultado, rocCalendarioActual);
				}else{
					numIncumplimientos = 0;                                                                          
				}
				obraDto.setNumIncumplimientos(numIncumplimientos);
				obrasResult.add(obraDto);
			}
		}
		mapObras.put("obras", obrasResult);
	
		return mapObras;
	}
	
	private int findIncumplimiento(InformacionObraDTO obraDto, int resultado, CalendarioReporteDTO rocCalendanrioActual){
		
		Calendar calendarioInio = Calendar.getInstance();
		calendarioInio.setTime(obraDto.getFecIniObra());
		Calendar calendarioFin = Calendar.getInstance();
		calendarioFin.setTime(obraDto.getFecFinObra());
		
		int numIncumplimientos = informacionIncidenciaDao.numReporBimestralByCveInfoNoReportada(obraDto.getCveInformacionObra());
		RocCalendarioReporte calenFin = calendarioReporteDao.findMesDeclarar(String.valueOf(calendarioFin.get(Calendar.MONTH) + 1));
		
		int anioFin = calendarioFin.get(Calendar.YEAR);
		int anioInicio = calendarioInio.get(Calendar.YEAR);
		int mesActual = Calendar.getInstance().get(Calendar.MONTH);
		int annoAct = Calendar.getInstance().get(Calendar.YEAR);
		int annoDef = annoAct - anioFin;
		int annoDefIni = annoAct - anioInicio;
		
		if (obraDto.getFecFinObra().before(new Date())) {
			//validar que la fecha de inicio y fecha fin correspondan anio
			if(anioInicio == anioFin){
				numIncumplimientos = findIncumlimientosFechasIguales(obraDto, resultado, calenFin, annoDefIni, numIncumplimientos, rocCalendanrioActual);

			}else{
				//son distinto anio automaticamente son >= a dos bimestres
				
				// la duracion excede un bimestre
				CalendarioReporteDTO calenActual = rocCalendanrioActual;

				//valida el bimestre que corresponde conforme la fecha del sistema contra fecha fin obra
				if(calenActual.getCveBimCalendario().intValue() == calenFin.getCveBimCalendario().intValue()){
					RocCalendarioReporte calenActual2 = calendarioReporteDao.findMesDeclarar(String.valueOf(mesActual + 1));
					resultado = calenActual2.getCveBimCalendario().intValue() - calenFin.getCveBimCalendario().intValue();

				}else{
					//no es el mismo bimestre contra el actual a presentar 
					resultado = calenActual.getCveBimCalendario().intValue() - calenFin.getCveBimCalendario().intValue();
				}
				
				if(resultado < 0){
					//si la resta de los bimestres son negativos se valida que los aÃ±os sean distintos
					if(annoDef > 0){
						numIncumplimientos = numIncumplimientos + 1;
					}
				}else{
					numIncumplimientos = numIncumplimientos + 1;
				}
				
			}

		}else{
			//el periodo de la obra toca mas de un anio
			if(annoDefIni > 0){
				CalendarioReporteDTO calenActual = rocCalendanrioActual;

				int bimestre = 0;
				if(calenActual.getCveBimCalendario() == 1){
					bimestre = 6;
				}else{
					bimestre = calenActual.getCveBimCalendario().intValue() - 1;
				}
				RotInformacionIncidencia rotInformacionIncidencia = informacionIncidenciaDao.findLastReporteBimestralByCveInformacionObra(obraDto.getCveInformacionObra());
				if (rotInformacionIncidencia != null) {
					if (rotInformacionIncidencia.getRocCalendarioReporte().getCveBimCalendario() != bimestre){
						numIncumplimientos = numIncumplimientos + 1;
					}							
				}							
			}
		}
		return numIncumplimientos;	
	}
	
	private int findIncumlimientosFechasIguales(InformacionObraDTO obraDto,  int resultado,
			RocCalendarioReporte calenFin, int annoDef, int numIncumplimientos, CalendarioReporteDTO rocCalendarioActual){
		int numIncumpl = numIncumplimientos;

		RocCalendarioReporte calenInicio = calendarioReporteDao.findMesDeclarar(String.valueOf(obraDto.getFecIniObra().getMonth() + 1));

		//para cuando la obra corresponde al mismo bimestre 
		if(calenInicio.getCveBimCalendario().intValue() == calenFin.getCveBimCalendario().intValue()){
			numIncumpl = 0;                           
		}else{
			//TODO sacar en un metodo cuando funcione :)
			// la duracion excede un bimestre
			CalendarioReporteDTO calenActual = rocCalendarioActual;

			//valida el bimestre que corresponde conforme la fecha del sistema contra fecha fin obra
			//log.debug("valida el bimestre que corresponde conforme la fecha del sistema contra fecha fin obra");
			if(calenActual.getCveBimCalendario().intValue() == calenFin.getCveBimCalendario().intValue()){
				RocCalendarioReporte calenActual2 = calendarioReporteDao.findMesDeclarar(String.valueOf(new Date().getMonth() + 1));
				resultado = calenActual2.getCveBimCalendario().intValue() - calenFin.getCveBimCalendario().intValue();

			}else{
				//no es el mismo bimestre contra el actual a presentar 
				resultado = calenActual.getCveBimCalendario().intValue() - calenFin.getCveBimCalendario().intValue();
			}
			if(resultado < 0){
				//si la resta de los bimestres son negativos se valida que los aÃ±os sean distintos
				if(annoDef > 0){
					numIncumpl = numIncumpl + 1;
				}
			}else{
				numIncumpl = numIncumpl + 1;
			}
		}
		return  numIncumpl;
	}
	public List<InformacionObraDTO> consultarInformacionObrasPorCveRfcYCveRegPatronal(String cveRfc,
			String cvRegPatronal) throws BusinessException {
		return informacionObraService.consultarInformacionObrasPorCveRfcYCveRegPatronal(cveRfc, cvRegPatronal);
	}


	public InformacionObraDTO consultarInformacionObraPorCveRegistroObra(String cveRegistroObra)
			throws BusinessException {
		return informacionObraService.consultarInformacionObraPorCveRegistroObra(cveRegistroObra);
	}


	public List<InformacionObraDTO> consultarTodasInformacionObras() throws BusinessException {
		return informacionObraService.consultarTodasInformacionObras();
	}


	public InformacionObraDTO insertarInformacionObra(InformacionObraDTO informacionObraDTO) throws BusinessException {
		return informacionObraService.insertarInformacionObra(informacionObraDTO);
		
	}

	public void actualizarInformacionObra(InformacionObraDTO informacionObraDTO) throws BusinessException {
		informacionObraService.actualizarInformacionObra(informacionObraDTO);
	}

	public InformacionObraDTO consultarInformacionObraPorId(
			Long cveInformacionObra) throws BusinessException {
		return informacionObraService.consultarInformacionObraPorId(cveInformacionObra);
	}


	public List<InformacionObraDTO> consultarInformacionObrasPorCveRfc(String cveRfc) throws BusinessException {
		// TODO Auto-generated method stub
		return informacionObraService.consultarInformacionObrasPorCveRfc(cveRfc);
	}


	public List<InformacionObraDTO> consultarInformacionObrasPorCveRegPatronal(String cvRegPatronal)
			throws BusinessException {
		// TODO Auto-generated method stub
		return informacionObraService.consultarInformacionObrasPorCveRegPatronal(cvRegPatronal);
	}


	public List<InformacionObraDTO> consultarInformacionObrasPorCveRegistroObraPrincipal(Long cveRegistroObraPrincipal)
			throws BusinessException {
		return informacionObraService.consultarInformacionObrasPorCveRegistroObraPrincipal(cveRegistroObraPrincipal);
	}


	public int consultarNumeroTotalObrasPorCveRfcyAnio(String cveRfc, String anio) throws BusinessException {
		return informacionObraService.consultarNumeroTotalObrasPorCveRfcyAnio(cveRfc, anio);
	}


	public List<InformacionObraDTO> consultarInformacionObrasPorCveRfcyAnio(String cveRfc, String anio)
			throws BusinessException {
		return informacionObraService.consultarInformacionObrasPorCveRfcyAnio(cveRfc, anio);
	}


	public List<RegistroPatronalDTO> consultarformacionObraAgrupadaByCveRfc(String cveRfc) throws BusinessException {
		return informacionObraService.consultarformacionObraAgrupadaByCveRfc(cveRfc);
	}


	public List<InformacionObraDTO> consultarReporteGeneralObrasPorCveRfcYAnio(String cveRfc, String anio) {

		return informacionObraService.consultarReporteGeneralObrasPorCveRfcYAnio(cveRfc, anio);
	}


	public List<InformacionObraDTO> consultarReporteGeneralObrasPorCveRegPatronal(String cvRegPatronal)
			throws BusinessException {
		return informacionObraService.consultarReporteGeneralObrasPorCveRegPatronal(cvRegPatronal);
	}

}
