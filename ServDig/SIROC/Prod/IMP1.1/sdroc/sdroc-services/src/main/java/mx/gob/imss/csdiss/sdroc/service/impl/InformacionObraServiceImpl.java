package mx.gob.imss.csdiss.sdroc.service.impl;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.gob.imss.csdiss.sdroc.dto.ClasificacionObraDTO;
import mx.gob.imss.csdiss.sdroc.dto.InformacionObraDTO;
import mx.gob.imss.csdiss.sdroc.dto.InformacionPatronDTO;
import mx.gob.imss.csdiss.sdroc.dto.RegistroPatronalDTO;
import mx.gob.imss.csdiss.sdroc.dto.TipoObraDTO;
import mx.gob.imss.csdiss.sdroc.dto.TipoPatronDTO;
import mx.gob.imss.csdiss.sdroc.dto.UbicacionObraDTO;
import mx.gob.imss.csdiss.sdroc.entity.RocCalendarioReporte;
import mx.gob.imss.csdiss.sdroc.entity.RotAvisoObra;
import mx.gob.imss.csdiss.sdroc.entity.RotInformacionIncidencia;
import mx.gob.imss.csdiss.sdroc.entity.RotInformacionObra;
import mx.gob.imss.csdiss.sdroc.exception.BusinessException;
import mx.gob.imss.csdiss.sdroc.orm.dao.AvisoObraDao;
import mx.gob.imss.csdiss.sdroc.orm.dao.CalendarioReporteDao;
import mx.gob.imss.csdiss.sdroc.orm.dao.InformacionIncidenciaDao;
import mx.gob.imss.csdiss.sdroc.orm.dao.InformacionObraDao;
import mx.gob.imss.csdiss.sdroc.service.interfaces.InformacionIncidenciaService;
import mx.gob.imss.csdiss.sdroc.service.interfaces.InformacionObraService;
import mx.gob.imss.csdiss.sdroc.service.util.ConvertUtil;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 
 * 
 * Clase que implementa la interface **** que permite obtener los parametros del sistema
 * 
 * @author Brian Hernandez Garcia
 * 
 */
@Service
@Transactional
public class InformacionObraServiceImpl implements InformacionObraService  {
	
	private static final Logger log = LoggerFactory.getLogger(InformacionObraServiceImpl.class);
	
	@Autowired
	InformacionObraDao informacionObraDao;
	
	@Autowired
	InformacionIncidenciaDao informacionIncidenciaDao;
	
	@Autowired
	InformacionIncidenciaService informacionIncidenciaService;
	
	@Autowired
	CalendarioReporteDao calendarioReporteDao;
	
	@Autowired
	AvisoObraDao avisoObraDao;

	/**
	 * Metodo "Recortado" para buscar las obras que se msotraran al inicio
	 */
	@Override
	public Map<String, Object> consultarObrasPorRfcYRp(String cveRfc, String cvRegPatronal, Long inicio, Long fin ) throws BusinessException {
		
		return null;
	}

	@Override
	@SuppressWarnings("deprecation")
	public List<InformacionObraDTO> consultarInformacionObrasPorCveRfcYCveRegPatronal(String cveRfc, String cvRegPatronal)
			throws BusinessException {
		
		
		List<InformacionObraDTO> listaInformacionObrasDTO = new ArrayList<InformacionObraDTO>();
		List<RotInformacionObra> listaInformacionObras = null;
		listaInformacionObras = informacionObraDao.findByRfcAndRp(cveRfc, cvRegPatronal);
		if(listaInformacionObras != null){
			for (RotInformacionObra rotInformacionObra : listaInformacionObras) {
				InformacionObraDTO informacionObraDTO = toConvertInformacionObraDTO(rotInformacionObra);
				RocCalendarioReporte bimestreAbuscar = null;
				int anno = 0;
				//se vaida si la fecha termino es mayor a la actual
				if (rotInformacionObra.getFecFinObra().before(new Date())) {
					//se busca el bimestre presentado correspondiente a la fecha de termno
					bimestreAbuscar = calendarioReporteDao.findMesPresentacion(String.valueOf(rotInformacionObra.getFecFinObra().getMonth() + 1));
					//
					if(bimestreAbuscar.getCveBimCalendario() == 6){
						anno = rotInformacionObra.getFecFinObra().getYear() + 1900 - 1;
					}else{
						anno = rotInformacionObra.getFecFinObra().getYear() + 1900;
					}
				}else{
					bimestreAbuscar = calendarioReporteDao.findMesPresentacion(String.valueOf(new Date().getMonth() + 1));
					
					if(bimestreAbuscar.getCveBimCalendario() == 6){
						anno = Calendar.getInstance().get(Calendar.YEAR) - 1;
					}else{
						anno = Calendar.getInstance().get(Calendar.YEAR);
					}
					
				}
				int numIncumplimientos = 0;
				int resultado = 0;
				//validar regVerde
				numIncumplimientos = informacionIncidenciaDao.numReporBimestralByCveInfoPresentadas(new Long(rotInformacionObra.getCveInformacionObra()), bimestreAbuscar.getCveBimCalendario(), anno);

				if(numIncumplimientos == 0){
					
					numIncumplimientos = obtenerNumeroIncumplimientos(rotInformacionObra, resultado);
				}else{
					numIncumplimientos = 0;                                                                          
				}
				informacionObraDTO.setNumIncumplimientos(numIncumplimientos);
				listaInformacionObrasDTO.add(informacionObraDTO);
			}
		}

		return listaInformacionObrasDTO;
}

	
	
	public int obtenerNumeroIncumplimientos(RotInformacionObra rotInformacionObra, int resultado){
		int numIncumplimientos = 0;
		//validar regOtros
		numIncumplimientos = informacionIncidenciaDao.numReporBimestralByCveInfoNoReportada(new Long(rotInformacionObra.getCveInformacionObra()));
		RocCalendarioReporte calenFin = calendarioReporteDao.findMesDeclarar(String.valueOf(rotInformacionObra.getFecFinObra().getMonth() + 1));
		int annoFin = rotInformacionObra.getFecFinObra().getYear() + 1900;
		int annoInicio = rotInformacionObra.getFecIniObra().getYear() + 1900;
		int annoAct = Calendar.getInstance().get(Calendar.YEAR);
		int annoDef = annoAct - annoFin;
		int annoDefIni = annoAct - annoInicio;
		
		if (rotInformacionObra.getFecFinObra().before(new Date())) {
			//validar que la fecha de inicio y fecha fin correspondan aÃ±o
			if(rotInformacionObra.getFecIniObra().getYear()+1900 == rotInformacionObra.getFecFinObra().getYear()+1900){
				numIncumplimientos = obtenerNumeroIncumplimientosCuandoFechaInicioYfechaFinSonIguales(rotInformacionObra, resultado, calenFin, annoDefIni, numIncumplimientos);

			}else{
				//son distinto aÃ±o automaticamente son >= a dos bimestres
				
				// la duracion excede un bimestre
				RocCalendarioReporte calenActual = calendarioReporteDao.findMesPresentacion(String.valueOf(new Date().getMonth() + 1));

				//valida el bimestre que corresponde conforme la fecha del sistema contra fecha fin obra
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
						numIncumplimientos = numIncumplimientos + 1;
					}
				}else{
					numIncumplimientos = numIncumplimientos + 1;
				}
				
			}

		}else{
			//el periodo de la obra toca mas de un aÃ±o 
			if(annoDefIni > 0){
				RocCalendarioReporte calenActual = calendarioReporteDao.findMesPresentacion(String.valueOf(new Date().getMonth() + 1));

				int bimestre = 0;
				if(calenActual.getCveBimCalendario() == 1){
					bimestre = 6;
				}else{
					bimestre = calenActual.getCveBimCalendario().intValue() - 1;
				}
				RotInformacionIncidencia rotInformacionIncidencia = informacionIncidenciaDao.findLastReporteBimestralByCveInformacionObra(new Long(rotInformacionObra.getCveInformacionObra()));
				if (rotInformacionIncidencia != null) {
					if (rotInformacionIncidencia.getRocCalendarioReporte().getCveBimCalendario() != bimestre){
						numIncumplimientos = numIncumplimientos + 1;
					}							
				}							
			}
		}
		return numIncumplimientos;	
	}

	
	public int obtenerNumeroIncumplimientosCuandoFechaInicioYfechaFinSonIguales(RotInformacionObra rotInformacionObra,  int resultado,
			RocCalendarioReporte calenFin, int annoDef, int numIncumplimientos){
		int numIncumpl = numIncumplimientos;

		RocCalendarioReporte calenInicio = calendarioReporteDao.findMesDeclarar(String.valueOf(rotInformacionObra.getFecIniObra().getMonth() + 1));

		//para cuando la obra corresponde al mismo bimestre 
		if(calenInicio.getCveBimCalendario().intValue() == calenFin.getCveBimCalendario().intValue()){
			numIncumpl = 0;                           
		}else{
			//TODO sacar en un metodo cuando funcione :)
			// la duracion excede un bimestre
			RocCalendarioReporte calenActual = calendarioReporteDao.findMesPresentacion(String.valueOf(new Date().getMonth() + 1));

			//valida el bimestre que corresponde conforme la fecha del sistema contra fecha fin obra
			log.debug("valida el bimestre que corresponde conforme la fecha del sistema contra fecha fin obra");
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
	

	@Override
	public InformacionObraDTO consultarInformacionObraPorCveRegistroObra(String cveRegistroObra)
			throws BusinessException {
		
		InformacionObraDTO informacionObraDTO = null;
		RotInformacionObra rotInformacionObra;
		rotInformacionObra = informacionObraDao.findByCveRegistroObra(cveRegistroObra);
		if(rotInformacionObra != null)
			informacionObraDTO = toConvertInformacionObraDTO(rotInformacionObra);
		
		return informacionObraDTO;
	}


	@Override
	public List<InformacionObraDTO> consultarTodasInformacionObras() throws BusinessException {
		List<InformacionObraDTO> listaInformacionObrasDTO = new ArrayList<InformacionObraDTO>();
		List<RotInformacionObra> listaInformacionObras = null;
		listaInformacionObras = informacionObraDao.findAll();
		if(listaInformacionObras != null){
			for (RotInformacionObra rotInformacionObra : listaInformacionObras) {
				InformacionObraDTO informacionObraDTO = new InformacionObraDTO();
				informacionObraDTO = toConvertInformacionObraDTO(rotInformacionObra);
				informacionObraDTO.setNumIncumplimientos(informacionIncidenciaDao.numReporteBimestralByCveInformacionObra(new Long(rotInformacionObra.getCveInformacionObra())));
				//informacionObraDTO.setNumIncumplimientos(reporteBimestralService.numIncumplimientosInformacionObra(new Long(rotInformacionObra.getCveInformacionObra())));
				listaInformacionObrasDTO.add(informacionObraDTO);
			}
		}
		
		return listaInformacionObrasDTO;
	}

	@Override
		public void actualizarInformacionObra(InformacionObraDTO informacionObraDTO) throws BusinessException {
		RotInformacionObra rotInformacionObra = null;
		rotInformacionObra = ConvertUtil.toConvertRotInformacionObra(informacionObraDTO);
		informacionObraDao.merge(rotInformacionObra);
	}

	@Override
	public InformacionObraDTO insertarInformacionObra(InformacionObraDTO informacionObraDTO) throws BusinessException {
		StringBuilder logString = new StringBuilder();
		RotInformacionObra rotInformacionObra = null;	
		Long cveInformacionObra = null;

		logString.append("SDROC-REST > insertarInformacionObra INI" + "\\n");
		//logString.append("SDROC-REST > insertarInformacionObra " + informacionObraDao.getCveRegistroObra()  + "\\n");
		logString.append("SDROC-REST > insertarInformacionObra informacionObraDTO " + informacionObraDTO.getRefObservacion() + "\\n");
		logString.append("SDROC-REST > insertarInformacionObra Llenado DTO "  + "\\n");

		// Cambio en cveTipoPatron si el tipo de usuario es intermediario

		String cveRegistroObra = informacionObraDao.getCveRegistroObra();
		int cveTipoPatron = informacionObraDTO.getInformacionPatronDTO().getTipoPatronDTO().getCveTipoPatron().intValue();

		if (cveTipoPatron == 4){
			informacionObraDTO.setCveRegistroObra(cveRegistroObra.replace('C', 'I'));
		} else {
			informacionObraDTO.setCveRegistroObra(cveRegistroObra);
		}

		logString.append("SDROC-REST > insertarInformacionObra llamada convert util"  + "\\n");
		rotInformacionObra = ConvertUtil.toConvertRotInformacionObra(informacionObraDTO);	
		logString.append("SDROC-REST > insertarInformacionObra peticion de guardado "  + "\\n");
		
		try{
			
			if(StringUtils.isNotEmpty(informacionObraDTO.getRefCveAvisoObra())){
				RotAvisoObra rotAvisoObra =avisoObraDao.findByCveRegistroAvisoObra(informacionObraDTO.getNumProcedimiento());
				if(rotAvisoObra != null){
					//cerrarAvisoObra(rotAvisoObra);
					rotInformacionObra.setRefCveAvisoObra(rotAvisoObra.getCveAvisoObra());
				}
			}	
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> SDROC-PROD-TEST  INTENTO GUARDADO OBRA ");
			//guardado de obra
			try {
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> SDROC-PROD-TEST  INICIA GUARDADO OBRA ");
				cveInformacionObra = informacionObraDao.save(rotInformacionObra);	
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> SDROC-PROD-TEST  TERMINA GUARDADO OBRA " + cveInformacionObra);
			} catch (Exception e) {
				e.printStackTrace();
				e.getMessage();
			}
			
		
			logString.append("SDROC-REST > insertarInformacionObra recuperacion de cve informacion obra "  + "\\n");
			informacionObraDTO.setCveInformacionObra(cveInformacionObra);
			
			logString.append("SDROC-REST > insertarInformacionObra revision numero procedimiento "  + "\\n");
			
			if(informacionObraDTO.getNumProcedimiento() != null &&
					!((informacionObraDTO.getTipoObraDTO().getClasificacionObraDTO().getCveClasificacionObra().intValue() == 1 && informacionObraDTO.getInformacionPatronDTO().getTipoPatronDTO().getCveTipoPatron().intValue() == 1)
					|| (informacionObraDTO.getTipoObraDTO().getClasificacionObraDTO().getCveClasificacionObra().intValue() == 2 && informacionObraDTO.getInformacionPatronDTO().getTipoPatronDTO().getCveTipoPatron().intValue() == 2))){
				//se envia el numero de procedimiento y si no viene vacio se cierra la obra
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> SDROC-PROD-TEST  busca aviso obra ");
				RotAvisoObra rotAvisoObra =avisoObraDao.findByCveRegistroAvisoObra(informacionObraDTO.getNumProcedimiento(), informacionObraDTO.getInformacionPatronDTO().getCveRfc(), informacionObraDTO.getInformacionPatronDTO().getCveRegPatronal());
				
				if(rotAvisoObra != null){
					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> SDROC-PROD-TEST  INTENTO cerrar aviso ");
					logString.append("SDROC-REST > Se actualiza el estatus de la obra"  + "\\n");
					cerrarAvisoObra(rotAvisoObra);
					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> SDROC-PROD-TEST  fin cerrar aviso ");
				}
			}	
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			logString.append("**********ERROR al generar la insercion******"  + "\\n");
		}
		logString.append("SDROC-REST > insertarInformacionObra calculo de bimestres extraordinarios DTO "  + "\\n");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> SDROC-PROD-TEST  INTENTO carga bimesteres extemporaneos");
		informacionIncidenciaService.cargarBimestresExtemporaneos(rotInformacionObra.getFecIniObra(),  rotInformacionObra.getFecFinObra(), cveInformacionObra);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> SDROC-PROD-TEST  fin bimestres extemporaneos");
		return informacionObraDTO;
	}
	
	/**
	 * Actualizar aviso de obra 
	 */
	private void cerrarAvisoObra(RotAvisoObra avisoObra) {
		if(avisoObra.getCveAvisoObra() != null){
			avisoObraDao.updateEstatusAvisoObra(avisoObra.getCveAvisoObra());
		}
	}


	@Override
	public InformacionObraDTO consultarInformacionObraPorId(Long cveInformacionObra)
			throws BusinessException {
		InformacionObraDTO informacionObraDTO = null;
		RotInformacionObra rotInformacionObra;
//		rotInformacionObra = informacionObraDao.findByID(cveInformacionObra);
		rotInformacionObra = informacionObraDao.findByCveInformacionObra(cveInformacionObra);
		if(rotInformacionObra != null)
			informacionObraDTO = toConvertInformacionObraDTO(rotInformacionObra);
		
		return informacionObraDTO;
	}


	@Override
	public List<InformacionObraDTO> consultarInformacionObrasPorCveRfc(String cveRfc) throws BusinessException {
		List<InformacionObraDTO> listaInformacionObrasDTO = new ArrayList<InformacionObraDTO>();
		List<RotInformacionObra> listaInformacionObras = null;
		listaInformacionObras = informacionObraDao.findByCveRfc(cveRfc);
		if(listaInformacionObras != null){
			for (RotInformacionObra rotInformacionObra : listaInformacionObras) {
				listaInformacionObrasDTO.add(toConvertInformacionObraDTO(rotInformacionObra));
			}
		}
		
		return listaInformacionObrasDTO;
	}


	@Override
	public List<InformacionObraDTO> consultarInformacionObrasPorCveRegPatronal(String cvRegPatronal)
			throws BusinessException {
		List<InformacionObraDTO> listaInformacionObrasDTO = new ArrayList<InformacionObraDTO>();
		List<RotInformacionObra> listaInformacionObras = null;
		listaInformacionObras = informacionObraDao.findByCvRegPatronal(cvRegPatronal);
		if(listaInformacionObras != null){
			for (RotInformacionObra rotInformacionObra : listaInformacionObras) {
				listaInformacionObrasDTO.add(toConvertInformacionObraDTO(rotInformacionObra));
			}
		}
		
		return listaInformacionObrasDTO;
	}


	@Override
	public List<InformacionObraDTO> consultarInformacionObrasPorCveRegistroObraPrincipal(Long cveRegistroObraPrincipal)
			throws BusinessException {
		
		List<InformacionObraDTO> listaInformacionObrasDTO = new ArrayList<InformacionObraDTO>();
		List<RotInformacionObra> listaInformacionObras = null;
		listaInformacionObras = informacionObraDao.findAllByCveRegistroObraPrincipal(cveRegistroObraPrincipal);
		if(listaInformacionObras != null){
			for (RotInformacionObra rotInformacionObra : listaInformacionObras) {
				listaInformacionObrasDTO.add(toConvertInformacionObraDTO(rotInformacionObra));
			}
		}
		
		return listaInformacionObrasDTO;
	}


	@Override
	public int consultarNumeroTotalObrasPorCveRfcyAnio(String cveRfc, String anio) throws BusinessException {
		
		int numTotalObras = 0;		
		numTotalObras = informacionObraDao.numInformacionObraByCveRfcAndAnio(cveRfc, anio);

		return numTotalObras;
	}


	@Override
	public List<InformacionObraDTO> consultarInformacionObrasPorCveRfcyAnio(String cveRfc, String anio)
			throws BusinessException {
		List<InformacionObraDTO> listaInformacionObrasDTO = new ArrayList<InformacionObraDTO>();
		List<RotInformacionObra> listaInformacionObras = null;
		listaInformacionObras = informacionObraDao.findInformacionObraByCveRfcAndAnio(cveRfc, anio);
		if(listaInformacionObras != null){
			for (RotInformacionObra rotInformacionObra : listaInformacionObras) {
				listaInformacionObrasDTO.add(toConvertInformacionObraDTO(rotInformacionObra));
			}
		}
		
		return listaInformacionObrasDTO;
	}


	@Override
	public List<RegistroPatronalDTO> consultarformacionObraAgrupadaByCveRfc(String cveRfc) throws BusinessException {
		
		List<RegistroPatronalDTO> listaRegistroPatronalDTO = new ArrayList<RegistroPatronalDTO>();
		List<Object[]> listaInformacionObras = null;
		listaInformacionObras = informacionObraDao.findAllByCveRfcGroupByCveRegPatronalAndSubdelegacionAndDelegacion(cveRfc);
		
		if(listaInformacionObras != null){
			for (Object[] informacionObra : listaInformacionObras) {
				
				RegistroPatronalDTO registroPatronalDTO = new RegistroPatronalDTO();
				registroPatronalDTO.setCveRegPatronal((String)informacionObra[0]);
				registroPatronalDTO.setNomDelegacion((String)informacionObra[1]);
				registroPatronalDTO.setNomSubdelegacion((String)informacionObra[2]);
				registroPatronalDTO.setNumObrasRegistradas((Long)informacionObra[3]);
				
				listaRegistroPatronalDTO.add(registroPatronalDTO);
			}
		}
		
		return listaRegistroPatronalDTO;
	}


	@Override
	public List<InformacionObraDTO> consultarReporteGeneralObrasPorCveRfcYAnio(String cveRfc, String anio) {
		// TODO Auto-generated method stub
		
		List<InformacionObraDTO> listaInformacionObras = new ArrayList<InformacionObraDTO>();
		List<Object[]> resultadoConsulta = null;
		
		resultadoConsulta = informacionObraDao.findAllInformacionObraForReport(cveRfc, anio);
		for (Object[] objects : resultadoConsulta) {
			
			InformacionObraDTO informacionObra = new InformacionObraDTO();
			InformacionPatronDTO informacionPatron = new InformacionPatronDTO();
			TipoObraDTO tipoObra = new TipoObraDTO();
			ClasificacionObraDTO clasificacion = new ClasificacionObraDTO();
			TipoPatronDTO tipoPatron = new TipoPatronDTO();
			UbicacionObraDTO ubicacionObra = new UbicacionObraDTO();
			
			informacionPatron.setCveRegPatronal((String) objects[0]);
			
			informacionObra.setCveRegistroObra((String) objects[1]);
			if(objects[2] != null)
				informacionObra.setCveRegistroObraPrincipalReporte((String) objects[2]);
			
			informacionObra.setInformacionPatronDTO(informacionPatron);			
			clasificacion.setDesClasificacionObra((String)objects[3]);
			tipoObra.setClasificacionObraDTO(clasificacion);
			
			informacionObra.setTipoObraDTO(tipoObra);
			
			tipoPatron.setDesTipoPatron((String)objects[4]);
			ubicacionObra.setCalle((String)objects[5]);
			ubicacionObra.setNumExterior(String.valueOf(objects[6]));
			ubicacionObra.setNumExteriorAlf(String.valueOf(objects[7]));
			ubicacionObra.setNumInterior(String.valueOf(objects[8]));
			ubicacionObra.setNumInteriorAlf(String.valueOf(objects[9]));
			ubicacionObra.setRefColonia((String)objects[10]);
			ubicacionObra.setRefMunicipio((String)objects[11]);
			ubicacionObra.setRefEntidad((String)objects[12]);
			ubicacionObra.setCodigoPostal((String)objects[13]);
			
			BigDecimal valor = (BigDecimal) objects[14];
			
			tipoPatron.setCveTipoPatron(valor.longValue());
			informacionPatron.setTipoPatronDTO(tipoPatron);
			informacionObra.setInformacionPatronDTO(informacionPatron);
			informacionObra.setUbicacionObraDTO(ubicacionObra);
			
			Timestamp stamp = (Timestamp) objects[15];
			Timestamp stamp1 = (Timestamp) objects[16];
			Timestamp stamp2 = (Timestamp) objects[17];
			Timestamp stamp3 = (Timestamp) objects[18];
			
			
			BigDecimal valor2 = (BigDecimal) objects[19];
			
			informacionObra.setFecIniObra(new Date(stamp.getTime()));
			informacionObra.setFecFinObra(new Date(stamp1.getTime()));
			informacionObra.setFecIniContrato(new Date(stamp2.getTime()));
			informacionObra.setFecFinContrato(new Date(stamp3.getTime()));
			informacionObra.setImpObra(valor2.doubleValue());
			
			listaInformacionObras.add(informacionObra);
			
		}
		
		return listaInformacionObras;
	}	

	@Override
	public List<InformacionObraDTO> consultarReporteGeneralObrasPorCveRegPatronal(String cvRegPatronal) {
		// TODO Auto-generated method stub
		
		List<InformacionObraDTO> listaInformacionObras = new ArrayList<InformacionObraDTO>();
		List<Object[]> resultadoConsulta = null;
		
		resultadoConsulta = informacionObraDao.findAllInformacionObraForReportByCvRegPatronal(cvRegPatronal);
		for (Object[] objects : resultadoConsulta) {
			
			InformacionObraDTO informacionObra = new InformacionObraDTO();
			InformacionPatronDTO informacionPatron = new InformacionPatronDTO();
			TipoObraDTO tipoObra = new TipoObraDTO();
			ClasificacionObraDTO clasificacion = new ClasificacionObraDTO();
			TipoPatronDTO tipoPatron = new TipoPatronDTO();
			UbicacionObraDTO ubicacionObra = new UbicacionObraDTO();
						
			informacionObra.setCveRegistroObra((String) objects[0]);
			if(objects[2] != null)
				informacionObra.setCveRegistroObraPrincipalReporte((String) objects[1]);
			
			informacionObra.setInformacionPatronDTO(informacionPatron);			
			clasificacion.setDesClasificacionObra((String)objects[2]);
			tipoObra.setClasificacionObraDTO(clasificacion);
			
			informacionObra.setTipoObraDTO(tipoObra);
			
			tipoPatron.setDesTipoPatron((String)objects[3]);
			ubicacionObra.setCalle((String)objects[4]);
			ubicacionObra.setNumExterior(String.valueOf(objects[5]));
			ubicacionObra.setNumExteriorAlf(String.valueOf(objects[6]));
			ubicacionObra.setNumInterior(String.valueOf(objects[7]));
			ubicacionObra.setNumInteriorAlf(String.valueOf(objects[8]));
			ubicacionObra.setRefColonia((String)objects[9]);
			ubicacionObra.setRefMunicipio((String)objects[10]);
			ubicacionObra.setRefEntidad((String)objects[11]);
			ubicacionObra.setCodigoPostal((String)objects[12]);
			
			BigDecimal valor = (BigDecimal) objects[13];
			
			tipoPatron.setCveTipoPatron(valor.longValue());
			informacionPatron.setTipoPatronDTO(tipoPatron);
			informacionObra.setInformacionPatronDTO(informacionPatron);
			informacionObra.setUbicacionObraDTO(ubicacionObra);
			
			Timestamp stamp = (Timestamp) objects[14];
			Timestamp stamp1 = (Timestamp) objects[15];
			Timestamp stamp2 = (Timestamp) objects[16];
			Timestamp stamp3 = (Timestamp) objects[17];
			
			
			BigDecimal valor2 = (BigDecimal) objects[18];
			
			informacionObra.setFecIniObra(new Date(stamp.getTime()));
			informacionObra.setFecFinObra(new Date(stamp1.getTime()));
			informacionObra.setFecIniContrato(new Date(stamp2.getTime()));
			informacionObra.setFecFinContrato(new Date(stamp3.getTime()));
			informacionObra.setImpObra(valor2.doubleValue());
			
			listaInformacionObras.add(informacionObra);
			
		}
		
		return listaInformacionObras;
	}	
	
	private InformacionObraDTO toConvertInformacionObraDTO(RotInformacionObra rotInformacionObra){
		InformacionObraDTO informacionObraDTO = new InformacionObraDTO();
		
		informacionObraDTO.setCveInformacionObra(rotInformacionObra.getCveInformacionObra());
		informacionObraDTO.setCveRegistroObraPrincipal(rotInformacionObra.getCveRegistroObraPrincipal());
		informacionObraDTO.setCveRegistroObra(rotInformacionObra.getCveRegistroObra());
		informacionObraDTO.setFecFinContrato(rotInformacionObra.getFecFinContrato());
		informacionObraDTO.setFecFinObra(rotInformacionObra.getFecFinObra());
		informacionObraDTO.setFecIniContrato(rotInformacionObra.getFecIniContrato());
		informacionObraDTO.setFecIniObra(rotInformacionObra.getFecIniObra());
		informacionObraDTO.setImpContratado(rotInformacionObra.getImpContratado());
		informacionObraDTO.setImpEjercido(rotInformacionObra.getImpEjercido());
		informacionObraDTO.setImpObra(rotInformacionObra.getImpObra());
		informacionObraDTO.setNumLicitacion(rotInformacionObra.getNumLicitacion());
		informacionObraDTO.setNumProcedimiento(rotInformacionObra.getNumProcedimiento());
		informacionObraDTO.setRefAcuseReg(rotInformacionObra.getRefAcuseReg());
		informacionObraDTO.setRefObservacion(rotInformacionObra.getRefObservacion());
		informacionObraDTO.setRefSupConstruccion(rotInformacionObra.getRefSupConstruccion());
		informacionObraDTO.setCveIdTramite(rotInformacionObra.getCveIdTramite());
		informacionObraDTO.setStpCreaReg(rotInformacionObra.getFecRegistroAlta());
		informacionObraDTO.setNumSeqNotaria(rotInformacionObra.getNumSeqNotaria());
        /*
		 * Se agregan nuevos parametros para complementar acusse de registro de obra 29112022
		 */
		informacionObraDTO.setNumAproxTrabajadores(rotInformacionObra.getNumAproxTrabajadores() != null ? rotInformacionObra.getNumAproxTrabajadores() : 0);
		informacionObraDTO.setNumRegStps(rotInformacionObra.getNumRegStps());
		
		if(rotInformacionObra.getNumActualiza() != null) {
			informacionObraDTO.setNumActualiza(rotInformacionObra.getNumActualiza());
		} else {
			informacionObraDTO.setNumActualiza(0);
		}
		
		if(rotInformacionObra.getRocSubdelegacion() != null)
			informacionObraDTO.setSubDelegacionDTO(rotInformacionObra.getRocSubdelegacion().toConvertSubdelegacionDTO());
		if(rotInformacionObra.getRocTipoObra() != null)
			informacionObraDTO.setTipoObraDTO(rotInformacionObra.getRocTipoObra().toConvertTipoObraDTO());
		if(rotInformacionObra.getRotUbicacionObras() != null)
			informacionObraDTO.setUbicacionObraDTO(rotInformacionObra.getRotUbicacionObras().toConvertUbicacionObraDTO());
		if(rotInformacionObra.getRotInformacionPatron() != null)
			informacionObraDTO.setInformacionPatronDTO(rotInformacionObra.getRotInformacionPatron().toConvertInformacionPatronDTO());
		if(rotInformacionObra.getRocEstatusObra() != null)
			informacionObraDTO.setEstatusObraDTO(rotInformacionObra.getRocEstatusObra().toConvertTipoObraDTO());
		if(rotInformacionObra.getRocObjetoContrato() != null)
			informacionObraDTO.setObjetoContratoDTO(rotInformacionObra.getRocObjetoContrato().toConvertObjetoContratoDTO());
		
		return informacionObraDTO;
	}

	@Override
	public SujetoObligado consultarInfoPatron(String rp) {
		// TODO Auto-generated method stub
		return null;
	}

}
