package mx.gob.imss.csdiss.sdroc.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import mx.gob.imss.csdiss.sdroc.dto.CalendarioReporteDTO;
import mx.gob.imss.csdiss.sdroc.dto.InformacionIncidenciaDTO;
import mx.gob.imss.csdiss.sdroc.dto.InformacionObraDTO;
import mx.gob.imss.csdiss.sdroc.dto.MotivoDTO;
import mx.gob.imss.csdiss.sdroc.dto.MotivoTipoIncidenciaDTO;
import mx.gob.imss.csdiss.sdroc.dto.TipoIncidenciaDTO;
import mx.gob.imss.csdiss.sdroc.dto.TipoRegistroDTO;
import mx.gob.imss.csdiss.sdroc.entity.RocCalendarioReporte;
import mx.gob.imss.csdiss.sdroc.entity.RocMotivoIncidencia;
import mx.gob.imss.csdiss.sdroc.entity.RocTipoIncidencia;
import mx.gob.imss.csdiss.sdroc.entity.RotInformacionIncidencia;
import mx.gob.imss.csdiss.sdroc.entity.RotInformacionObra;
import mx.gob.imss.csdiss.sdroc.exception.BusinessException;
import mx.gob.imss.csdiss.sdroc.orm.dao.CalendarioReporteDao;
import mx.gob.imss.csdiss.sdroc.orm.dao.InformacionIncidenciaDao;
import mx.gob.imss.csdiss.sdroc.orm.dao.InformacionObraDao;
import mx.gob.imss.csdiss.sdroc.orm.dao.MotivoTipoIncidenciaDao;
import mx.gob.imss.csdiss.sdroc.orm.dao.TipoIncidenciaDao;
import mx.gob.imss.csdiss.sdroc.service.interfaces.InformacionIncidenciaService;
import mx.gob.imss.csdiss.sdroc.service.util.ConvertUtil;
import mx.gob.imss.csdiss.sdroc.util.constants.EnumEstatusFinalObra;
import mx.gob.imss.csdiss.sdroc.util.constants.EnumEstatusIncidendiaTipoObra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Clase que implementa la interface **** que permite obtener los parametros del
 * sistema
 * Agregando
 *
 * @author Brian Hernandez Garcia
 *
 */
@Service
@Transactional
public class InformacionIncidenciaServiceImpl implements InformacionIncidenciaService {

	private static final Logger log = LoggerFactory.getLogger(InformacionIncidenciaServiceImpl.class);

	@Autowired
	InformacionIncidenciaDao informacionIncidenciaDao;

	@Autowired
	MotivoTipoIncidenciaDao motivoTipoIncidenciaDao;

	@Autowired
	InformacionObraDao informacionObraDao;

	@Autowired
	TipoIncidenciaDao tipoIncidenciaDao;

	@Autowired
	CalendarioReporteDao calendarioReporteDao;

	@Override
	@SuppressWarnings("deprecation")
	public InformacionIncidenciaDTO insertarInformacionIncidencia(InformacionIncidenciaDTO informacionIncidenciaDTO)
			throws BusinessException {

		RotInformacionIncidencia rotInformacionIncidencia = null;
		RocMotivoIncidencia rocMotivoIncidencia = null;
		RocTipoIncidencia rocTipoIncidencia = null;
		Long cveInformacionIncidencia = null;
		String desTipoIncidencia = null;
		String cadImpEjercido = "";

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

		Long cveTipoIncidencia = new Long(0);

		cveTipoIncidencia = informacionIncidenciaDTO.getMotivoTipoIncidenciaDTO().getTipoIncidenciaDTO()
				.getCveTipoIncidencia();
		rocTipoIncidencia = tipoIncidenciaDao.findByID(new Long(cveTipoIncidencia));

		if (rocTipoIncidencia != null)
			desTipoIncidencia = rocTipoIncidencia.getDesTipoIncidencia().toUpperCase();

		EnumEstatusIncidendiaTipoObra enumEstatusIncidendiaTipoObra = EnumEstatusIncidendiaTipoObra
				.valueOf(desTipoIncidencia);

		rocMotivoIncidencia = motivoTipoIncidenciaDao.findMotivoIncidenciaByMotivoTipoIncidencia(
				new Long(informacionIncidenciaDTO.getMotivoTipoIncidenciaDTO().getMotivoDTO().getCveMotivo()),
				informacionIncidenciaDTO.getMotivoTipoIncidenciaDTO().getTipoIncidenciaDTO().getCveTipoIncidencia());

		rotInformacionIncidencia = ConvertUtil.toConvertInformacionIncidencia(informacionIncidenciaDTO);

		rotInformacionIncidencia.setRocMotivoTipoIncidencia(rocMotivoIncidencia);

		informacionIncidenciaDTO.setMotivoTipoIncidenciaDTO(rocMotivoIncidencia.toConvertMotivoTipoIncidenciaDTO());

		RotInformacionObra infoObraValida = informacionObraDao
				.findByCveInformacionObra(new Long(informacionIncidenciaDTO.getCveInformacionObra()));

		if (cveTipoIncidencia == 6) {
			RocCalendarioReporte calendario = calendarioReporteDao
					.findCveBimCalendario(informacionIncidenciaDTO.getCalendarioReporteDTO().getCveBimCalendario());

			if (Calendar.getInstance().get(Calendar.MONTH) > calendario.getFecIniPerPresentado().getMonth()) {
				rotInformacionIncidencia.getRocTipoRegistro().setCveTipoRegistro(2L);
			} else {
				if (Calendar.getInstance().get(Calendar.DATE) > 17) {
					rotInformacionIncidencia.getRocTipoRegistro().setCveTipoRegistro(2L);
				} else {
					rotInformacionIncidencia.getRocTipoRegistro().setCveTipoRegistro(1L);
				}
			}

			rotInformacionIncidencia.setRocCalendarioReporte(calendario);
			rotInformacionIncidencia.getRocMotivoTipoIncidencia().setCveMotivoTipoIncidencia(14L);
			rotInformacionIncidencia.setNumAnio(informacionIncidenciaDTO.getNumAnio());

		}
		if (cveTipoIncidencia == 4) {
			rotInformacionIncidencia.setFecActualizacion(rotInformacionIncidencia.getStpRegIncidencia());
		}
		cveInformacionIncidencia = informacionIncidenciaDao.save(rotInformacionIncidencia);

		if (cveInformacionIncidencia != null && cveInformacionIncidencia.intValue() != 0) {

			elegirAccionRealizar(enumEstatusIncidendiaTipoObra, rotInformacionIncidencia, infoObraValida, informacionIncidenciaDTO, cadImpEjercido);

//			switch (enumEstatusIncidendiaTipoObra) {
//
//			case ACTUALIZACION:
//				if (rotInformacionIncidencia.getFecFinObra() != null) {
//					cadImpEjercido = cadImpEjercido + ", fecFinObra = to_date('"
//							+ formato.format(rotInformacionIncidencia.getFecFinObra()) + "','dd/MM/yyyy')";
//				}
//				if (rotInformacionIncidencia.getImpObra() != null) {
//					cadImpEjercido = cadImpEjercido + ", impObra = " + rotInformacionIncidencia.getImpObra();
//				}
//				if (rotInformacionIncidencia.getRefSupConstruccion() != null) {
//					cadImpEjercido = cadImpEjercido + ", refSupConstruccion = "
//							+ rotInformacionIncidencia.getRefSupConstruccion();
//				}
//				int numActualizacion = infoObraValida.getNumActualiza() + 1;
//				cadImpEjercido = cadImpEjercido + ", numActualiza = " + numActualizacion;
//				informacionObraDao.updateInformacionObran(new Long(informacionIncidenciaDTO.getCveInformacionObra()),
//						new Long(EnumEstatusFinalObra.ACTIVA.getEstatus()), cadImpEjercido);
//
//				// recalculo de Bimestres no Presentados
//				if (rotInformacionIncidencia.getFecFinObra() != null
//						&& infoObraValida.getFecFinObra() != rotInformacionIncidencia.getFecFinObra()) {
//					// se valida si la nueva fecha termino es mayor a la fecha
//					// termino anterior
//					if (rotInformacionIncidencia.getFecFinObra().after(infoObraValida.getFecFinObra())) {
//						// se consulta el ultimo reporte bimestral presentado
//						InformacionIncidenciaDTO repotePresentado = consultarUltimoReporteBimestralPresentado(
//								new Long(informacionIncidenciaDTO.getCveInformacionObra()));
//						if (repotePresentado != null) {
//							Date fecha = null;
//							// XXX si la nueva fecha fin es mayor a a actual se
//							// genera en base a la fecha presentacion del
//							// reprote bimestral
//							// de lo contrario se genera en base a la fecha
//							// declaracion del reprote bimestral
//							if (rotInformacionIncidencia.getFecFinObra().after(new Date())) {
//
//								fecha = new Date(repotePresentado.getNumAnio() - 1900,
//										repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado().getMonth(),
//										repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado().getDate());
//							} else {
//								fecha = new Date(repotePresentado.getNumAnio() - 1900,
//										repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado().getMonth(),
//										repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado().getDate());
//								// si la fecha de inicio del periodo del reporte
//								// bimestral es menor a la fecha de inicio de la
//								// obra se toma el periodo presentado
//								if (fecha.before(infoObraValida.getFecIniObra())) {
//									Integer anio = 0;
//									// en el caso en el que el reporte bimestral
//									// sea el sexto del anio se hace el salto
//									// del anio
//									if (repotePresentado.getCalendarioReporteDTO().getCveBimCalendario() == 6) {
//										anio = repotePresentado.getNumAnio();
//										repotePresentado.setNumAnio(anio + 1);
//									}
//									// se cambia la busqueda por el reporte
//									// bimestral presentado pero con la fecha de
//									// presentacion del reporte y no la de
//									// declaracion
//									fecha = new Date(repotePresentado.getNumAnio() - 1900,
//											repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado()
//													.getMonth(),
//											repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado()
//													.getDate());
//								}
//								// daniel else{
//								// XXX fecha = new
//								// Date(repotePresentado.getNumAnio()-1900,
//								// repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado().getMonth(),repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado().getDate());
//								// daniel }
//								// else{
//								// XXX add REANUDACION
//								// calcular los meses de duracion conforme la
//								// fecha calculada y la fecha fin de la obra
//								// Integer mesesDuracion = 0;
//								// mesesDuracion = diferenciaEnMeses(fecha,
//								// rotInformacionIncidencia.getFecFinObra());
//								// if(mesesDuracion > 3){
//								// //se cambia la busqueda por el reporte
//								// bimestral presentado pero con la fecha de
//								// presentacion del reporte y no la de
//								// declaracion
//								// fecha = new
//								// Date(repotePresentado.getNumAnio()-1900,
//								// repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado().getMonth(),repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado().getDate());
//								// }
//								// }
//							}
//							// se calculan los bimestres no presentados en base
//							// a la fecha generada del reprote bimestral
//							// la nueva fecha termino y la fecha actual
//
//							RocCalendarioReporte calendario;
//							int anioReg;
//							if (rotInformacionIncidencia.getFecFinObra().after(new Date())) {
//								calendario = calendarioReporteDao
//										.findMesPresentacion(String.valueOf(new Date().getMonth() + 1));
//								anioReg = Calendar.getInstance().get(Calendar.YEAR);
//							} else {
//								calendario = calendarioReporteDao.findMesPresentacion(
//										String.valueOf(rotInformacionIncidencia.getFecFinObra().getMonth() + 1));
//								anioReg = rotInformacionIncidencia.getFecFinObra().getYear() + 1900;
//							}
//
//							if (calendario.getCveBimCalendario() == 6) {
//								anioReg--;
//							}
//
//							// if
//							// (!rotInformacionIncidencia.getRocCalendarioReporte().getCveBimCalendario().equals(calendario.getCveBimCalendario())
//							// && (rotInformacionIncidencia.getNumAnio() ==
//							// anioReg)){
//							// XXX revisar con frank
//
//							String bimPresentar = calendario.getCveBimCalendario().toString()
//									.concat(String.valueOf(anioReg));
//							String bimPresentado = repotePresentado.getCalendarioReporteDTO().getCveBimCalendario()
//									.toString().concat(repotePresentado.getNumAnio().toString());
//
//							if (!bimPresentar.equals(bimPresentado)) {
//								cargarBimestresExtemporaneos(fecha, rotInformacionIncidencia.getFecFinObra(),
//										new Long(informacionIncidenciaDTO.getCveInformacionObra()));
//							}
//
//							// if
//							// (!repotePresentado.getCalendarioReporteDTO().getCveBimCalendario().equals(calendario.getCveBimCalendario())
//							// && (repotePresentado.getNumAnio() == anioReg)){
//							// cargarBimestresExtemporaneos(fecha,
//							// rotInformacionIncidencia.getFecFinObra(), new
//							// Long(informacionIncidenciaDTO.getCveInformacionObra()));
//							// }
//
//						} else {
//							// si no hay reprotes bimestrales calculados, se
//							// valida si la nueva fecha de termino es mayor a la
//							// actual
//							// if(rotInformacionIncidencia.getFecFinObra().after(new
//							// Date())){
//							// se calculan los bimestres no presentados en base
//							// a la fecha de termino anterior
//							// la nueva fecha termino y la fecha actual
//							// cargarBimestresExtemporaneos(infoObraValida.getFecFinObra(),
//							// rotInformacionIncidencia.getFecFinObra(), new
//							// Long(informacionIncidenciaDTO.getCveInformacionObra()));
//							// }
//							if (rotInformacionIncidencia.getFecFinObra().before(new Date())) {
//								// se calculan los bimestres no presentados en
//								// base a la fecha de termino anterior
//								// la nueva fecha termino y la fecha actual
//								cargarBimestresExtemporaneos(infoObraValida.getFecFinObra(),
//										rotInformacionIncidencia.getFecFinObra(),
//										new Long(informacionIncidenciaDTO.getCveInformacionObra()));
//							} else {
//								if (infoObraValida.getFecFinObra().before(new Date())) {
//									cargarBimestresExtemporaneos(infoObraValida.getFecFinObra(),
//											rotInformacionIncidencia.getFecFinObra(),
//											new Long(informacionIncidenciaDTO.getCveInformacionObra()));
//								}
//							}
//						}
//					}
//				}
//
//				break;
//
//			case CANCELACION:
//				if (infoObraValida.getImpEjercido() <= rotInformacionIncidencia.getImpEjercido()) {
//					cadImpEjercido = ", impEjercido = " + rotInformacionIncidencia.getImpEjercido();
//				}
//				informacionObraDao.updateInformacionObran(new Long(informacionIncidenciaDTO.getCveInformacionObra()),
//						new Long(EnumEstatusFinalObra.CANCELADA.getEstatus()), cadImpEjercido);
//				break;
//
//			case REANUDACION:
//				if (rotInformacionIncidencia.getFecFinObra() != null) {
//					cadImpEjercido = cadImpEjercido + ", fecFinObra = to_date('"
//							+ formato.format(rotInformacionIncidencia.getFecFinObra()) + "','dd/MM/yyyy')";
//				}
//				if (rotInformacionIncidencia.getRefSupConstruccion() != null) {
//					cadImpEjercido = cadImpEjercido + ", refSupConstruccion = "
//							+ rotInformacionIncidencia.getRefSupConstruccion();
//				}
//				if (rotInformacionIncidencia.getImpObra() != null) {
//					if (!rotInformacionIncidencia.getImpObra().isNaN())
//						cadImpEjercido = cadImpEjercido + ", impObra = " + rotInformacionIncidencia.getImpObra();
//				}
//
//				cadImpEjercido = cadImpEjercido + ", numActualiza = 0";
//				informacionObraDao.updateInformacionObran(new Long(informacionIncidenciaDTO.getCveInformacionObra()),
//						new Long(EnumEstatusFinalObra.ACTIVA_REACTIVADA.getEstatus()), cadImpEjercido);
//
//				if (rotInformacionIncidencia.getFecFinObra() != null) {
//
//					Date fechaFinNueva = new Date(rotInformacionIncidencia.getFecFinObra().getYear(),
//							rotInformacionIncidencia.getFecFinObra().getMonth(),
//							rotInformacionIncidencia.getFecFinObra().getDate());
//					Date fechaFinAnterio = new Date(infoObraValida.getFecFinObra().getYear(),
//							infoObraValida.getFecFinObra().getMonth(), infoObraValida.getFecFinObra().getDate());
//
//					// recalculo de Bimestres no Presentados
//					if (!fechaFinAnterio.equals(fechaFinNueva)) {
//						if (rotInformacionIncidencia.getFecFinObra().after(infoObraValida.getFecFinObra())) {
//							// se buscan reportes bimestrales presentados
//							InformacionIncidenciaDTO repotePresentado = consultarUltimoReporteBimestralPresentado(
//									new Long(informacionIncidenciaDTO.getCveInformacionObra()));
//							if (repotePresentado != null) {
//								Date fecha = null;
//								// XXX si la nueva fecha fin es mayor a a actual
//								// se genera en base a la fecha presentacion del
//								// reprote bimestral
//								// de lo contrario se genera en base a la fecha
//								// declaracion del reprote bimestral
//								if (rotInformacionIncidencia.getFecFinObra().after(new Date())) {
//									// fecha = new
//									// Date(repotePresentado.getNumAnio()-1900,
//									// repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado().getMonth(),repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado().getDate());
//									Integer anoi = 0;
//									if (repotePresentado.getCalendarioReporteDTO().getCveBimCalendario() == 6) {
//										anoi = repotePresentado.getNumAnio();
//										repotePresentado.setNumAnio(anoi + 1);
//									}
//									fecha = new Date(repotePresentado.getNumAnio() - 1900,
//											repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado()
//													.getMonth(),
//											repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado()
//													.getDate());
//								} else {
//									fecha = new Date(repotePresentado.getNumAnio() - 1900,
//											repotePresentado.getCalendarioReporteDTO().getFecIniPerDeclarado()
//													.getMonth(),
//											repotePresentado.getCalendarioReporteDTO().getFecIniPerDeclarado()
//													.getDate());
//									// si la fecha de inicio del periodo del
//									// reporte bimestral es menor a la fecha de
//									// inicio de la obra se toma el periodo
//									// presentado
//									if (fecha.before(infoObraValida.getFecIniObra())) {
//										Integer anio = 0;
//										// en el caso en el que el reporte
//										// bimestral sea el sexto del anio se
//										// hace el salto del anio
//										if (repotePresentado.getCalendarioReporteDTO().getCveBimCalendario() == 6) {
//											anio = repotePresentado.getNumAnio();
//											repotePresentado.setNumAnio(anio + 1);
//										}
//										// se cambia la busqueda por el reporte
//										// bimestral presentado pero con la
//										// fecha de presentacion del reporte y
//										// no la de declaracion
//										fecha = new Date(repotePresentado.getNumAnio() - 1900,
//												repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado()
//														.getMonth(),
//												repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado()
//														.getDate());
//									} else {
//										// XXX add REANUDACION
//										// calcular los meses de duracion
//										// conforme la fecha calculada y la
//										// fecha fin de la obra
//										Integer mesesDuracion = 0;
//										mesesDuracion = diferenciaEnMeses(fecha,
//												rotInformacionIncidencia.getFecFinObra());
//										if (mesesDuracion > 3) {
//											// se cambia la busqueda por el
//											// reporte bimestral presentado pero
//											// con la fecha de presentacion del
//											// reporte y no la de declaracion
//											fecha = new Date(repotePresentado.getNumAnio() - 1900,
//													repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado()
//															.getMonth(),
//													repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado()
//															.getDate());
//										}
//									}
//								}
//								// se calculan los bimestres no presentados en
//								// base a la fecha generada del reprote
//								// bimestral
//								// la nueva fecha termino y la fecha actual
//								cargarBimestresExtemporaneos(fecha, rotInformacionIncidencia.getFecFinObra(),
//										new Long(informacionIncidenciaDTO.getCveInformacionObra()));
//
//								// Date fecha = new
//								// Date(repotePresentado.getNumAnio()-1900,
//								// repotePresentado.getCalendarioReporteDTO().getFecIniPerDeclarado().getMonth(),repotePresentado.getCalendarioReporteDTO().getFecIniPerDeclarado().getDate());
//								// //se obtiene la fecha del ultimo reporte
//								// bimestral presentado y se envia la fecha del
//								// periodo inmediato presentado
//								// cargarBimestresExtemporaneos(fecha,
//								// rotInformacionIncidencia.getFecFinObra(), new
//								// Long(informacionIncidenciaDTO.getCveInformacionObra()));
//							} else {
//								// si no hay reprotes bimestrales calculados, se
//								// valida si la nueva fecha de termino es mayor
//								// a la actual
//								if (rotInformacionIncidencia.getFecFinObra().before(new Date())) {
//									// se calculan los bimestres no presentados
//									// en base a la fecha de termino anterior
//									// la nueva fecha termino y la fecha actual
//									eliminaReportesBimestralesNoPresentados(
//											rotInformacionIncidencia.getCveInformacionObra());
//									cargarBimestresExtemporaneos(infoObraValida.getFecIniObra(),
//											rotInformacionIncidencia.getFecFinObra(),
//											new Long(informacionIncidenciaDTO.getCveInformacionObra()));
//									// XXX
//									// cargarBimestresExtemporaneos(infoObraValida.getFecIniObra(),
//									// rotInformacionIncidencia.getFecFinObra(),
//									// new
//									// Long(informacionIncidenciaDTO.getCveInformacionObra()));
//								} else {
//									if (infoObraValida.getFecFinObra().before(new Date())) {
//										eliminaReportesBimestralesNoPresentados(
//												rotInformacionIncidencia.getCveInformacionObra());
//										cargarBimestresExtemporaneos(infoObraValida.getFecIniObra(),
//												rotInformacionIncidencia.getFecFinObra(),
//												new Long(informacionIncidenciaDTO.getCveInformacionObra()));
//										// cargarBimestresExtemporaneos(infoObraValida.getFecFinObra(),
//										// rotInformacionIncidencia.getFecFinObra(),
//										// new
//										// Long(informacionIncidenciaDTO.getCveInformacionObra()));
//									}
//								}
//
//								// if(rotInformacionIncidencia.getFecFinObra().before(new
//								// Date())){
//								// cargarBimestresExtemporaneos(infoObraValida.getFecFinObra(),
//								// rotInformacionIncidencia.getFecFinObra(), new
//								// Long(informacionIncidenciaDTO.getCveInformacionObra()));
//								// }
//							}
//						} else {
//							SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
//							int annio = 0;
//
//							RocCalendarioReporte calendario = calendarioReporteDao.findMesDeclarar(
//									String.valueOf(rotInformacionIncidencia.getFecFinObra().getMonth() + 1));
//
//							if (calendario.getCveBimCalendario() == 1) {
//								annio = (rotInformacionIncidencia.getFecFinObra().getYear() + 1900) - 1;
//							} else {
//								annio = rotInformacionIncidencia.getFecFinObra().getYear() + 1900;
//							}
//							// se busca el ultimo reporte bimestral reportado
//							RotInformacionIncidencia rotInfoIncidencia = informacionIncidenciaDao
//									.findLastReporteBimPresenByCveInfoObra(
//											new Long(informacionIncidenciaDTO.getCveInformacionObra()));
//							if (rotInfoIncidencia != null) {
//								// se elimina hasta el reporte bimestral
//								// XXX REANUDAX
//								// eliminaReportesBimestralesNoPresentados(rotInformacionIncidencia.getCveInformacionObra());
//								eliminaReporteBimByCveInfoObra(
//										new Long(rotInformacionIncidencia.getCveInformacionObra()),
//										format.format(rotInformacionIncidencia.getFecFinObra()), annio);
//								// cargarBimestresExtemporaneos(infoObraValida.getFecIniObra(),
//								// rotInformacionIncidencia.getFecFinObra(), new
//								// Long(informacionIncidenciaDTO.getCveInformacionObra()));
//							} else {
//								// se elimina todo
//								// eliminaReporteBimByCveInfoObraDeclarado(new
//								// Long(rotInformacionIncidencia.getCveInformacionObra()),
//								// format.format(rotInformacionIncidencia.getFecFinObra()),
//								// annio);
//								eliminaReportesBimestralesNoPresentados(
//										rotInformacionIncidencia.getCveInformacionObra());
//								cargarBimestresExtemporaneos(infoObraValida.getFecIniObra(),
//										rotInformacionIncidencia.getFecFinObra(),
//										new Long(informacionIncidenciaDTO.getCveInformacionObra()));
//							}
//						}
//					}
//				}
//				break;
//
//			case REPORTE_BIMESTRAL:
//				if (infoObraValida.getImpEjercido() <= rotInformacionIncidencia.getImpEjercido()) {
//					cadImpEjercido = ", impEjercido = " + rotInformacionIncidencia.getImpEjercido();
//				}
//				informacionObraDao.updateInformacionObran(new Long(informacionIncidenciaDTO.getCveInformacionObra()),
//						infoObraValida.getRocEstatusObra().getCveEstatusObra(), cadImpEjercido);
//				break;
//
//			case SUSPENCION:
//				if (infoObraValida.getImpEjercido() <= rotInformacionIncidencia.getImpEjercido()) {
//					cadImpEjercido = ", impEjercido = " + rotInformacionIncidencia.getImpEjercido();
//				}
//				informacionObraDao.updateInformacionObran(new Long(informacionIncidenciaDTO.getCveInformacionObra()),
//						new Long(EnumEstatusFinalObra.SUSPENDIDA.getEstatus()), cadImpEjercido);
//				break;
//
//			case TERMINACION:
//				if (infoObraValida.getImpEjercido() <= rotInformacionIncidencia.getImpEjercido()) {
//					cadImpEjercido = ", impEjercido = " + rotInformacionIncidencia.getImpEjercido();
//				}
//				informacionObraDao.updateInformacionObran(new Long(informacionIncidenciaDTO.getCveInformacionObra()),
//						new Long(EnumEstatusFinalObra.TERMINADA.getEstatus()), cadImpEjercido);
//				break;
//
//			}
//
		}

		return informacionIncidenciaDTO;

	}


	public void elegirAccionRealizar(EnumEstatusIncidendiaTipoObra enumEstatusIncidendiaTipoObra, RotInformacionIncidencia rotInformacionIncidencia,
					RotInformacionObra infoObraValida, InformacionIncidenciaDTO informacionIncidenciaDTO,String cadImpEjercido){

		switch (enumEstatusIncidendiaTipoObra) {

		case ACTUALIZACION:
			realizarAccionActualizar(rotInformacionIncidencia, infoObraValida, informacionIncidenciaDTO);
			break;

		case CANCELACION:
			realizarAccionCancelacion(rotInformacionIncidencia, infoObraValida, informacionIncidenciaDTO, cadImpEjercido);
			break;

		case REANUDACION:
			realizarAccionReanudar(rotInformacionIncidencia, infoObraValida, informacionIncidenciaDTO);
			break;

		case REPORTE_BIMESTRAL:
			generarAccionReporteBimestral(rotInformacionIncidencia, infoObraValida, informacionIncidenciaDTO, cadImpEjercido);
			break;

		case SUSPENCION:
			if (infoObraValida.getImpEjercido() <= rotInformacionIncidencia.getImpEjercido()) {
				cadImpEjercido = ", impEjercido = " + rotInformacionIncidencia.getImpEjercido();
			}
			informacionObraDao.updateInformacionObran(new Long(informacionIncidenciaDTO.getCveInformacionObra()),
					new Long(EnumEstatusFinalObra.SUSPENDIDA.getEstatus()), cadImpEjercido);
			break;

		case TERMINACION:
			if (infoObraValida.getImpEjercido() <= rotInformacionIncidencia.getImpEjercido()) {
				cadImpEjercido = ", impEjercido = " + rotInformacionIncidencia.getImpEjercido();
			}
			informacionObraDao.updateInformacionObran(new Long(informacionIncidenciaDTO.getCveInformacionObra()),
					new Long(EnumEstatusFinalObra.TERMINADA.getEstatus()), cadImpEjercido);
			break;
		}

	}

	public void realizarAccionActualizar(RotInformacionIncidencia rotInformacionIncidencia,RotInformacionObra infoObraValida,
				InformacionIncidenciaDTO informacionIncidenciaDTO){

		String cadImpEjercido = generarCadenaImpEjercidoParaActualizar(rotInformacionIncidencia, infoObraValida);
		informacionObraDao.updateInformacionObran(new Long(informacionIncidenciaDTO.getCveInformacionObra()),
			new Long(EnumEstatusFinalObra.ACTIVA.getEstatus()), cadImpEjercido);

		//recalculo de Bimestres no Presentados
		if(rotInformacionIncidencia.getFecFinObra()!=null && infoObraValida.getFecFinObra() != rotInformacionIncidencia.getFecFinObra()){
		//se valida si la nueva fecha termino es mayor a la fecha termino anterior
		if(rotInformacionIncidencia.getFecFinObra().after(infoObraValida.getFecFinObra())){
				recaluloBimestresNoPresentadosParaActualizar(rotInformacionIncidencia, infoObraValida, informacionIncidenciaDTO);
			}
		}
	}

	public String generarCadenaImpEjercidoParaActualizar(RotInformacionIncidencia rotInformacionIncidencia, RotInformacionObra infoObraValida){
		String cadImpEjercido = "";
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

		if (rotInformacionIncidencia.getFecFinObra() != null) {
			cadImpEjercido = cadImpEjercido + ", fecFinObra = to_date('"
					+ formato.format(rotInformacionIncidencia.getFecFinObra()) + "','dd/MM/yyyy')";
		}
		if (rotInformacionIncidencia.getImpObra() != null) {
			cadImpEjercido = cadImpEjercido + ", impObra = " + rotInformacionIncidencia.getImpObra();
		}
		if (rotInformacionIncidencia.getRefSupConstruccion() != null) {
			cadImpEjercido = cadImpEjercido + ", refSupConstruccion = "
					+ rotInformacionIncidencia.getRefSupConstruccion();
		}

		int numActualizacion = infoObraValida.getNumActualiza() + 1;
		cadImpEjercido = cadImpEjercido + ", numActualiza = " + numActualizacion;

		return cadImpEjercido;
	}

	public void recaluloBimestresNoPresentadosParaActualizar(RotInformacionIncidencia rotInformacionIncidencia,RotInformacionObra infoObraValida,
			InformacionIncidenciaDTO informacionIncidenciaDTO){
		//se consulta el ultimo reporte bimestral presentado
		InformacionIncidenciaDTO repotePresentado = consultarUltimoReporteBimestralPresentado(new Long(informacionIncidenciaDTO.getCveInformacionObra()));
		if(repotePresentado != null){
			Date fecha = generarFechaApartirDelReporteBimestral(rotInformacionIncidencia, repotePresentado, infoObraValida);

			//se calculan los bimestres no presentados en base a la fecha generada del reprote bimestral
			//la nueva fecha termino y la fecha actual

			RocCalendarioReporte calendario;
			int anioReg;
			if(rotInformacionIncidencia.getFecFinObra().after(new Date())){
				 calendario = calendarioReporteDao.findMesPresentacion(String.valueOf(new Date().getMonth() + 1));
				 anioReg = Calendar.getInstance().get(Calendar.YEAR);
			}else{
				 calendario = calendarioReporteDao.findMesPresentacion(String.valueOf(rotInformacionIncidencia.getFecFinObra().getMonth() + 1));
				 anioReg = rotInformacionIncidencia.getFecFinObra().getYear() + 1900;
			}

			if(calendario.getCveBimCalendario() == 6){
			    anioReg--;
			  }

			String bimPresentar = calendario.getCveBimCalendario().toString().concat(String.valueOf(anioReg));
			String bimPresentado = repotePresentado.getCalendarioReporteDTO().getCveBimCalendario().toString().concat(repotePresentado.getNumAnio().toString());

			if(!bimPresentar.equals(bimPresentado)){
				cargarBimestresExtemporaneos(fecha, rotInformacionIncidencia.getFecFinObra(), new Long(informacionIncidenciaDTO.getCveInformacionObra()));
			}

		} else {
			// si no hay reprotes bimestrales calculados, se valida si la nueva fecha de termino es mayor a la actual
			if(rotInformacionIncidencia.getFecFinObra().before(new Date())){
				//se calculan los bimestres no presentados en base a la fecha de termino anterior
				//la nueva fecha termino y la fecha actual
				cargarBimestresExtemporaneos(infoObraValida.getFecFinObra(), rotInformacionIncidencia.getFecFinObra(), new Long(informacionIncidenciaDTO.getCveInformacionObra()));
			}else {
				if(infoObraValida.getFecFinObra().before(new Date())){
					cargarBimestresExtemporaneos(infoObraValida.getFecFinObra(), rotInformacionIncidencia.getFecFinObra(), new Long(informacionIncidenciaDTO.getCveInformacionObra()));
				}
			}
		}
	}


	public Date generarFechaApartirDelReporteBimestral(RotInformacionIncidencia rotInformacionIncidencia,InformacionIncidenciaDTO repotePresentado,
			RotInformacionObra infoObraValida){
		Date fecha = null;
		//XXX si la nueva fecha fin es mayor a a actual se genera en base a la fecha presentacion del reprote bimestral
		//de lo contrario se genera en base a la fecha declaracion del reprote bimestral
		if(rotInformacionIncidencia.getFecFinObra().after(new Date())){
			fecha = new Date(repotePresentado.getNumAnio()-1900, repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado().getMonth(),repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado().getDate());
		}else{
			fecha = new Date(repotePresentado.getNumAnio()-1900, repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado().getMonth(),repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado().getDate());
			//si la fecha de inicio del periodo del reporte bimestral es menor a la fecha de inicio de la obra se toma el periodo presentado
			if(fecha.before(infoObraValida.getFecIniObra())){
				Integer anio = 0;
				//en el caso en el que el reporte bimestral sea el sexto del anio se hace el salto del anio
				if(repotePresentado.getCalendarioReporteDTO().getCveBimCalendario() == 6){
					anio = repotePresentado.getNumAnio();
					repotePresentado.setNumAnio(anio + 1);
				}
				//se cambia la busqueda por el reporte bimestral presentado pero con la fecha de presentacion del reporte y no la de declaracion
				fecha = new Date(repotePresentado.getNumAnio()-1900, repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado().getMonth(),repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado().getDate());
			}
		}
		return fecha;
	}


	public void realizarAccionCancelacion(RotInformacionIncidencia rotInformacionIncidencia,RotInformacionObra infoObraValida,
				InformacionIncidenciaDTO informacionIncidenciaDTO, String cadImpEjercido){
		if (infoObraValida.getImpEjercido() <= rotInformacionIncidencia.getImpEjercido()) {
			cadImpEjercido = ", impEjercido = " + rotInformacionIncidencia.getImpEjercido();
		}
		informacionObraDao.updateInformacionObran(new Long(informacionIncidenciaDTO.getCveInformacionObra()),
				new Long(EnumEstatusFinalObra.CANCELADA.getEstatus()), cadImpEjercido);
	}


	public void realizarAccionReanudar(RotInformacionIncidencia rotInformacionIncidencia, RotInformacionObra infoObraValida,
			InformacionIncidenciaDTO informacionIncidenciaDTO){

		String cadImpEjercido = generarCadenaImpEjercidoParaReanudar(rotInformacionIncidencia);

		informacionObraDao.updateInformacionObran(new Long(informacionIncidenciaDTO.getCveInformacionObra()),
				new Long(EnumEstatusFinalObra.ACTIVA_REACTIVADA.getEstatus()), cadImpEjercido);

		if (rotInformacionIncidencia.getFecFinObra() != null) {

			Date fechaFinNueva = new Date(rotInformacionIncidencia.getFecFinObra().getYear(), rotInformacionIncidencia.getFecFinObra().getMonth(),rotInformacionIncidencia.getFecFinObra().getDate());
			Date fechaFinAnterio = new Date(infoObraValida.getFecFinObra().getYear(), infoObraValida.getFecFinObra().getMonth(),infoObraValida.getFecFinObra().getDate());

			//recalculo de Bimestres no Presentados
			if(!fechaFinAnterio.equals(fechaFinNueva)){
				generarRecalculoBimestresNoPresentadosParaReanudar(rotInformacionIncidencia, infoObraValida, informacionIncidenciaDTO);

			}
		}
	}

	public String generarCadenaImpEjercidoParaReanudar(RotInformacionIncidencia rotInformacionIncidencia){
		String cadImpEjercido = "";
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

		if (rotInformacionIncidencia.getFecFinObra() != null) {
			cadImpEjercido = cadImpEjercido + ", fecFinObra = to_date('"
					+ formato.format(rotInformacionIncidencia.getFecFinObra()) + "','dd/MM/yyyy')";
		}
		if (rotInformacionIncidencia.getRefSupConstruccion() != null) {
			cadImpEjercido = cadImpEjercido + ", refSupConstruccion = "
					+ rotInformacionIncidencia.getRefSupConstruccion();
		}
		if (rotInformacionIncidencia.getImpObra() != null) {
			if (!rotInformacionIncidencia.getImpObra().isNaN())
				cadImpEjercido = cadImpEjercido + ", impObra = " + rotInformacionIncidencia.getImpObra();
		}

		cadImpEjercido = cadImpEjercido + ", numActualiza = 0";

		return cadImpEjercido;
	}


	public void generarRecalculoBimestresNoPresentadosParaReanudar(RotInformacionIncidencia rotInformacionIncidencia, RotInformacionObra infoObraValida,
			InformacionIncidenciaDTO informacionIncidenciaDTO){
		if(rotInformacionIncidencia.getFecFinObra().after(infoObraValida.getFecFinObra())){
			//se buscan reportes bimestrales presentados
			InformacionIncidenciaDTO repotePresentado = consultarUltimoReporteBimestralPresentado(new Long(informacionIncidenciaDTO.getCveInformacionObra()));
			if(repotePresentado != null){
				Date fecha = generarFechaParaAccionReanudar(rotInformacionIncidencia, infoObraValida, repotePresentado);

				//se calculan los bimestres no presentados en base a la fecha generada del reprote bimestral
				//la nueva fecha termino y la fecha actual
				cargarBimestresExtemporaneos(fecha, rotInformacionIncidencia.getFecFinObra(), new Long(informacionIncidenciaDTO.getCveInformacionObra()));

			} else {
				  //si no hay reprotes bimestrales calculados, se valida si la nueva fecha de termino es mayor a la actual
				if(rotInformacionIncidencia.getFecFinObra().before(new Date())){
					//se calculan los bimestres no presentados en base a la fecha de termino anterior
					//la nueva fecha termino y la fecha actual
					eliminaReportesBimestralesNoPresentados(rotInformacionIncidencia.getCveInformacionObra());
					cargarBimestresExtemporaneos(infoObraValida.getFecIniObra(), rotInformacionIncidencia.getFecFinObra(), new Long(informacionIncidenciaDTO.getCveInformacionObra()));
				}else {
					if(infoObraValida.getFecFinObra().before(new Date())){
						eliminaReportesBimestralesNoPresentados(rotInformacionIncidencia.getCveInformacionObra());
						cargarBimestresExtemporaneos(infoObraValida.getFecIniObra(), rotInformacionIncidencia.getFecFinObra(), new Long(informacionIncidenciaDTO.getCveInformacionObra()));
					}
				}

			}
		}else{
			SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
			int annio= 0;

			RocCalendarioReporte calendario = calendarioReporteDao.findMesDeclarar(String.valueOf(rotInformacionIncidencia.getFecFinObra().getMonth()+1));

			if(calendario.getCveBimCalendario() == 1){
				annio = (rotInformacionIncidencia.getFecFinObra().getYear()+1900)-1;
			}else{
				annio = rotInformacionIncidencia.getFecFinObra().getYear()+1900;
			}
			//se busca el ultimo reporte bimestral reportado
			RotInformacionIncidencia rotInfoIncidencia = informacionIncidenciaDao.findLastReporteBimPresenByCveInfoObra(new Long(informacionIncidenciaDTO.getCveInformacionObra()));
			if (rotInfoIncidencia != null) {
				//se elimina hasta el reporte bimestral
				 eliminaReporteBimByCveInfoObra(new Long(rotInformacionIncidencia.getCveInformacionObra()), format.format(rotInformacionIncidencia.getFecFinObra()), annio);
			}else{
				//se elimina todo
				eliminaReportesBimestralesNoPresentados(rotInformacionIncidencia.getCveInformacionObra());
				cargarBimestresExtemporaneos(infoObraValida.getFecIniObra(), rotInformacionIncidencia.getFecFinObra(), new Long(informacionIncidenciaDTO.getCveInformacionObra()));
			}
		}
	}



	public Date generarFechaParaAccionReanudar(RotInformacionIncidencia rotInformacionIncidencia, RotInformacionObra infoObraValida,
			InformacionIncidenciaDTO repotePresentado){
		Date fecha = null;
		//XXX si la nueva fecha fin es mayor a a actual se genera en base a la fecha presentacion del reprote bimestral
		//de lo contrario se genera en base a la fecha declaracion del reprote bimestral
		if(rotInformacionIncidencia.getFecFinObra().after(new Date())){//fecha = new Date(repotePresentado.getNumAnio()-1900, repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado().getMonth(),repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado().getDate());
			Integer anoi = 0;
			if(repotePresentado.getCalendarioReporteDTO().getCveBimCalendario() == 6){
				anoi = repotePresentado.getNumAnio();
				repotePresentado.setNumAnio(anoi + 1);
			}
			fecha = new Date(repotePresentado.getNumAnio()-1900, repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado().getMonth(),repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado().getDate());
		}else{
			fecha = new Date(repotePresentado.getNumAnio()-1900, repotePresentado.getCalendarioReporteDTO().getFecIniPerDeclarado().getMonth(),repotePresentado.getCalendarioReporteDTO().getFecIniPerDeclarado().getDate());
			//si la fecha de inicio del periodo del reporte bimestral es menor a la fecha de inicio de la obra se toma el periodo presentado
			if(fecha.before(infoObraValida.getFecIniObra())){
				Integer anio = 0;
				//en el caso en el que el reporte bimestral sea el sexto del anio se hace el salto del anio
				if(repotePresentado.getCalendarioReporteDTO().getCveBimCalendario() == 6){
					anio = repotePresentado.getNumAnio();
					repotePresentado.setNumAnio(anio + 1);
				}
				//se cambia la busqueda por el reporte bimestral presentado pero con la fecha de presentacion del reporte y no la de declaracion
				fecha = new Date(repotePresentado.getNumAnio()-1900, repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado().getMonth(),repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado().getDate());
			}else{
				//XXX add REANUDACION
				// calcular los meses de duracion conforme la fecha calculada y la fecha fin de la obra
				Integer mesesDuracion = 0;
				mesesDuracion = diferenciaEnMeses(fecha, rotInformacionIncidencia.getFecFinObra());
				if(mesesDuracion > 3){
					//se cambia la busqueda por el reporte bimestral presentado pero con la fecha de presentacion del reporte y no la de declaracion
					fecha = new Date(repotePresentado.getNumAnio()-1900, repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado().getMonth(),repotePresentado.getCalendarioReporteDTO().getFecIniPerPresentado().getDate());
				}
			}
		}
		return fecha;
	}


	public void generarAccionReporteBimestral(RotInformacionIncidencia rotInformacionIncidencia, RotInformacionObra infoObraValida,
			InformacionIncidenciaDTO informacionIncidenciaDTO,String cadImpEjercido){
		if (infoObraValida.getImpEjercido() <= rotInformacionIncidencia.getImpEjercido()) {
			cadImpEjercido = ", impEjercido = " + rotInformacionIncidencia.getImpEjercido();
		}
		informacionObraDao.updateInformacionObran(new Long(informacionIncidenciaDTO.getCveInformacionObra()),
				infoObraValida.getRocEstatusObra().getCveEstatusObra(), cadImpEjercido);
	}

	@Override
	public List<InformacionIncidenciaDTO> consultarUltimasIncidenciaPorTipoIncidenciaPorCveInformacionObra(
			Long cveInformacionObra) throws BusinessException {
		List<InformacionIncidenciaDTO> listaInformacionIncidencia = new ArrayList<InformacionIncidenciaDTO>();

		List<RocTipoIncidencia> listaRocTipoIncidencia = tipoIncidenciaDao.findAll();
		for (RocTipoIncidencia rocTipoIncidencia : listaRocTipoIncidencia) {

			RotInformacionIncidencia rotInformacionIncidencia = informacionIncidenciaDao
					.findLastIncidenciaByTipoIncidenciaByCveInformacionObra(cveInformacionObra,
							rocTipoIncidencia.getCveTipoIncidencia());
			if (rotInformacionIncidencia != null)
				listaInformacionIncidencia.add(rotInformacionIncidencia.toConvertInformacionIncidenciaDTO());
		}

		return listaInformacionIncidencia;
	}

	@Override
	public InformacionIncidenciaDTO consultarUltimaIncidenciaPorTipoIncidenciaPorCveInformacionObra(
			Long cveInformacionObra, Long cveTipoIncidencia) throws BusinessException {

		InformacionIncidenciaDTO informacionIncidenciaDTO = null;

		RotInformacionIncidencia rotInformacionIncidencia = informacionIncidenciaDao
				.findLastIncidenciaByTipoIncidenciaByCveInformacionObra(cveInformacionObra, cveTipoIncidencia);
		if (rotInformacionIncidencia != null)
			informacionIncidenciaDTO = rotInformacionIncidencia.toConvertInformacionIncidenciaDTO();

		return informacionIncidenciaDTO;
	}


	public InformacionIncidenciaDTO consultarUltimoReporteBimestralPorCveInformacionObra(InformacionObraDTO obra) throws BusinessException {
		InformacionIncidenciaDTO informacionIncidenciaDTO = null;
		//Datos de la fecha actual
		Calendar calFecActual = Calendar.getInstance();
		calFecActual.setTime(new Date());
		int anioActual = calFecActual.get(Calendar.YEAR);
		int mesActual = calFecActual.get(Calendar.MONTH)+1;
		//datos de la fecha fin de obra
		Calendar calFecFinObra = Calendar.getInstance();
		calFecFinObra.setTime(obra.getFecFinObra());
		int anioFinObra = calFecFinObra.get(Calendar.YEAR);
		int mesFinObra = calFecFinObra.get(Calendar.MONTH)+1;

		System.out.println("[ConsultaReportePorObra] - Entro a verificar para fechas fin iguales para la obra " + obra.getCveRegistroObra());
		System.out.println("[ConsultaReportePorObra] - Fecha actual es: " +mesActual + " - " + anioActual +  " el fin de obra es "+ mesFinObra+" - " + anioFinObra);
		System.out.println("[ConsultaReportePorObra] - El anio actual es: " + anioActual +  " el fin de obra es " + anioFinObra);

		if (anioFinObra > anioActual) {

			/*
			 * el anio de fin de obra es mayor a la fecha de hoy mi tope es el
			 * anio actual reviso el ultimo bimestre a presentar si es el 6 le
			 * quito un año al año actual
			 *
			 */
			log.debug("[ConsultaReportePorObra] - Entro al if de anio fin de obra mayor");
			informacionIncidenciaDTO = this.obteneterIncidenciaSiFinDeObraEsMayorAFechaActual(obra);

		} else if (anioFinObra == anioActual && mesFinObra >= mesActual) {
			// Mismo Anno de fin de obra y fecha actual iguales Mismo Mes
			System.out.println("[ConsultaReportePorObra] - Entroal if cuando el anio es el mismo y las fecha de fin es mayo o igual al mes actual " + obra.getCveRegistroObra());
			informacionIncidenciaDTO = this.obteneterIncidenciaSiFinDeObraEsIgualFechaActual(obra.getCveInformacionObra(), obra);
		} else {
			log.debug("[ConsultaReportePorObra] - Entro al if cuando el anio es diferente y el mes");
			informacionIncidenciaDTO = this.obteneterIncidenciaSiFinDeObraEsMenorAFechaActual(obra.getCveInformacionObra(), obra);
		}


		return informacionIncidenciaDTO;
	}

	private InformacionIncidenciaDTO obteneterIncidenciaSiFinDeObraEsMayorAFechaActual(InformacionObraDTO obra){

		InformacionIncidenciaDTO informacionIncidenciaDTO = null;

		int anioReg = Calendar.getInstance().get(Calendar.YEAR);
		int anioActual = Calendar.getInstance().get(Calendar.YEAR);
		Long cveInformacionObra = obra.getCveInformacionObra();
		RocCalendarioReporte calendario = calendarioReporteDao.findMesPresentacion(String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1));
		RotInformacionIncidencia rotInformacionIncidencia = informacionIncidenciaDao.findLastReporteBimestralByCveInformacionObra(cveInformacionObra);
		log.debug("El bimestre es : " + calendario.getCveBimCalendario());

		if(calendario.getCveBimCalendario() == 6){
			anioReg--;
		}

		if (rotInformacionIncidencia != null) {
			log.debug("Si encontre resporte bimestral para la obra " + cveInformacionObra);
			if (!rotInformacionIncidencia.getRocCalendarioReporte().getCveBimCalendario().equals(calendario.getCveBimCalendario())
					&& (rotInformacionIncidencia.getNumAnio() == anioReg
							||  rotInformacionIncidencia.getNumAnio() == anioActual)) {
				informacionIncidenciaDTO = new InformacionIncidenciaDTO();
				informacionIncidenciaDTO.setCveInformacionObra(cveInformacionObra);
				informacionIncidenciaDTO.setCalendarioReporteDTO(calendario.toConvertCalendarioReporteDTO());

				if(calendario.getCveBimCalendario() == 6){
					informacionIncidenciaDTO.setNumAnio(anioActual - 1);
				}else{
					informacionIncidenciaDTO.setNumAnio(anioActual);
				}
			}else {

				if(rotInformacionIncidencia.getRocCalendarioReporte().getCveBimCalendario()
						.equals(calendario.getCveBimCalendario())
						&& (rotInformacionIncidencia.getNumAnio() == anioReg || rotInformacionIncidencia
								.getNumAnio() == anioActual)){

				}else{
					informacionIncidenciaDTO = new InformacionIncidenciaDTO();
					informacionIncidenciaDTO.setCveInformacionObra(cveInformacionObra);
					informacionIncidenciaDTO.setCalendarioReporteDTO(calendario.toConvertCalendarioReporteDTO());
					informacionIncidenciaDTO.setNumAnio(anioActual);
				}

			}
		} else {
			log.debug("No encontre resporte bimestral para la obra " + cveInformacionObra);
			cargarBimestresExtemporaneos(obra.getFecIniObra(), obra.getFecFinObra(), cveInformacionObra);
			RotInformacionIncidencia rotInformacionIncidencia2 = informacionIncidenciaDao.findLastReporteBimestralByCveInformacionObra(cveInformacionObra);
			if (rotInformacionIncidencia2 != null) {
				log.debug("encontre reporte despues de cargar bimestres extemporaneos");
				System.out.println("encontre reporte despues de cargar bimestres extemporaneos");
				if (!rotInformacionIncidencia2.getRocCalendarioReporte().getCveBimCalendario().equals(calendario.getCveBimCalendario())
					&& rotInformacionIncidencia2.getNumAnio() == anioActual) {

					informacionIncidenciaDTO = new InformacionIncidenciaDTO();
					informacionIncidenciaDTO.setCveInformacionObra(cveInformacionObra);
					informacionIncidenciaDTO.setCalendarioReporteDTO(calendario.toConvertCalendarioReporteDTO());
					informacionIncidenciaDTO.setNumAnio(anioActual);
				}
			} else {
				log.debug("No encontre reporte despues de cargar bimestres extemporaneos");
				System.out.println("No encontre reporte despues de cargar bimestres extemporaneos");
				informacionIncidenciaDTO = new InformacionIncidenciaDTO();
				informacionIncidenciaDTO.setCveInformacionObra(cveInformacionObra);
				informacionIncidenciaDTO.setCalendarioReporteDTO(calendario.toConvertCalendarioReporteDTO());

				if(calendario.getCveBimCalendario() == 6){
					informacionIncidenciaDTO.setNumAnio(anioActual - 1);
				}else{
					informacionIncidenciaDTO.setNumAnio(anioActual);
				}
			}
		}
		return informacionIncidenciaDTO;
	}

	private InformacionIncidenciaDTO obteneterIncidenciaSiFinDeObraEsMenorAFechaActual(Long cveInformacionObra, InformacionObraDTO regActual){
		InformacionIncidenciaDTO informacionIncidenciaDTO = null;
		Calendar calendarActual = Calendar.getInstance();
		calendarActual.setTime(new Date());
		Calendar calendarFin = Calendar.getInstance();
		calendarFin.setTime(regActual.getFecFinObra());

		int anioActual = calendarActual.get(Calendar.YEAR);
		int anioReg = calendarFin.get(Calendar.YEAR);
		int anioFinObra = anioReg;
		int mesFin = calendarFin.get(Calendar.MONTH) + 1;

		System.out.println("El anio actual con calendar es " + anioActual + " y el anio reg es " + anioReg + " y el mes de fin es " + mesFin);

		RocCalendarioReporte calendario = calendarioReporteDao.findMesPresentacion(String.valueOf(mesFin));


		System.out.println("El bimestre que corresponde al fin de obra es " + calendario.getCveBimCalendario());

		if(calendario.getCveBimCalendario() == 6){
			anioReg--;
		}

		RotInformacionIncidencia rotInformacionIncidencia = informacionIncidenciaDao.findLastReporteBimestralByCveInformacionObra(cveInformacionObra);

		if (rotInformacionIncidencia != null) {

			System.out.println("El anio de la ultima incidencia es " + rotInformacionIncidencia.getNumAnio() +
					" y el bimestre es " + rotInformacionIncidencia.getRocCalendarioReporte().getCveBimCalendario() + " con id " + rotInformacionIncidencia.getCveInformacionIncidencia());

			if (!rotInformacionIncidencia.getRocCalendarioReporte().getCveBimCalendario().equals(calendario.getCveBimCalendario())
					&& (rotInformacionIncidencia.getNumAnio() == anioReg || rotInformacionIncidencia.getNumAnio() == anioActual)) {

				informacionIncidenciaDTO = new InformacionIncidenciaDTO();
				informacionIncidenciaDTO.setCveInformacionObra(cveInformacionObra);
				informacionIncidenciaDTO.setCalendarioReporteDTO(calendario.toConvertCalendarioReporteDTO());
				if(calendario.getCveBimCalendario() == 6){
					System.out.println("1. El anio que se pone es " + (regActual.getFecFinObra().getYear() + 1900 - 1) + " y el anio con calendar es " + (anioReg-1));
					informacionIncidenciaDTO.setNumAnio(anioFinObra - 1);
				}else{
					System.out.println("2. El anio que se pone es " + (regActual.getFecFinObra().getYear() + 1900) + " y el anio con calendar es " + (anioReg));
					informacionIncidenciaDTO.setNumAnio(anioFinObra);
				}

			}else{
				if (!rotInformacionIncidencia.getRocCalendarioReporte().getCveBimCalendario().equals(calendario.getCveBimCalendario())
						&& anioFinObra <= anioActual) {
					informacionIncidenciaDTO = new InformacionIncidenciaDTO();
					informacionIncidenciaDTO.setCveInformacionObra(cveInformacionObra);
					informacionIncidenciaDTO.setCalendarioReporteDTO(calendario.toConvertCalendarioReporteDTO());
					informacionIncidenciaDTO.setNumAnio(anioFinObra);
				}
			}
		} else {
			informacionIncidenciaDTO = new InformacionIncidenciaDTO();
			informacionIncidenciaDTO.setCveInformacionObra(cveInformacionObra);
			informacionIncidenciaDTO.setCalendarioReporteDTO(calendario.toConvertCalendarioReporteDTO());
			if(calendario.getCveBimCalendario() == 6){
				informacionIncidenciaDTO.setNumAnio(anioFinObra - 1);
			}else{
				informacionIncidenciaDTO.setNumAnio(anioFinObra);
			}
		}
		return informacionIncidenciaDTO;
	}

	private InformacionIncidenciaDTO obteneterIncidenciaSiFinDeObraEsIgualFechaActual(Long cveInformacionObra, InformacionObraDTO regActual){
		System.out.println("Entro al obteneterIncidenciaSiFinDeObraEsIgualFechaActual 2");
		InformacionIncidenciaDTO informacionIncidenciaDTO = null;
		Integer numAnio = null;
		Calendar calActual = Calendar.getInstance();
		calActual.setTime(new Date());
		int anioActual = calActual.get(Calendar.YEAR);
		int mesActual = calActual.get(Calendar.MONTH)+1;
		Calendar calFinObra = Calendar.getInstance();
		calFinObra.setTime(regActual.getFecFinObra());

		int anioReg = calFinObra.get(Calendar.YEAR);//regActual.getFecFinObra().getYear() + 1900;
		int anioFin = anioReg;
		int mesReg = calFinObra.get(Calendar.MONTH)+1;
		System.out.println("El mes de presentacion es " + mesActual);

		RocCalendarioReporte calendario = calendarioReporteDao.findMesPresentacion("" + mesActual);

		System.out.println("El bimestre a presentar es  " + calendario.getCveBimCalendario());
		//Si es el bimestre 6 al annio le restas uno correspondientes
		if(calendario.getCveBimCalendario() == 6){
			anioReg = anioReg - 1;
		}

		System.out.println("El anio es " + anioReg);
		numAnio = anioReg;

		RotInformacionIncidencia rotInformacionIncidencia = informacionIncidenciaDao.findLastReporteBimestralByCveInformacionObra(cveInformacionObra);

		if (rotInformacionIncidencia != null) {
			System.out.println("Existe ultimo reporte bimestrarl y su informacion es \n"
					+ "bimestre incidencia" + rotInformacionIncidencia.getRocCalendarioReporte().getCveBimCalendario() + " bimestre actual "+calendario.getCveBimCalendario()+"\n"
							+ "anio incidencia " + rotInformacionIncidencia.getNumAnio() + " anioReg " + anioReg + " aniio actual " + anioActual);
			if (!rotInformacionIncidencia.getRocCalendarioReporte().getCveBimCalendario().equals(calendario.getCveBimCalendario())
				&& (rotInformacionIncidencia.getNumAnio() == anioReg || rotInformacionIncidencia.getNumAnio() == anioActual)) {
				System.out.println("Entro al primer if cuando existe incidencia");
				informacionIncidenciaDTO = new InformacionIncidenciaDTO();
				informacionIncidenciaDTO.setCveInformacionObra(cveInformacionObra);
				informacionIncidenciaDTO.setCalendarioReporteDTO(calendario.toConvertCalendarioReporteDTO());
				informacionIncidenciaDTO.setNumAnio(numAnio);
			}else {
					if (rotInformacionIncidencia.getRocCalendarioReporte().getCveBimCalendario().equals(calendario.getCveBimCalendario())
							&& (rotInformacionIncidencia.getNumAnio() == anioReg || rotInformacionIncidencia.getNumAnio() == anioActual)) {
						System.out.println("Entro al segundo if cuando existe incidencia");
					} else {
						System.out.println("Entro al else cuando existe incidencia");
						informacionIncidenciaDTO = new InformacionIncidenciaDTO();
						informacionIncidenciaDTO.setCveInformacionObra(cveInformacionObra);
						informacionIncidenciaDTO.setCalendarioReporteDTO(calendario.toConvertCalendarioReporteDTO());
						informacionIncidenciaDTO.setNumAnio(anioReg);
					}

				}

		} else {
			System.out.println("No existe ultimo reporte bimestrarl ");
			cargarBimestresExtemporaneos(regActual.getFecIniObra(), regActual.getFecFinObra(), cveInformacionObra);
			RotInformacionIncidencia rotInformacionIncidencia2 = informacionIncidenciaDao
					.findLastReporteBimestralByCveInformacionObra(cveInformacionObra);
			if (rotInformacionIncidencia2 != null) {
				if (!rotInformacionIncidencia2.getRocCalendarioReporte().getCveBimCalendario().equals(calendario.getCveBimCalendario())
						&& rotInformacionIncidencia2.getNumAnio() == anioActual) {
					System.out.println("No existe ultimo reporte bimestrarl y anios y meses son iguales");
					informacionIncidenciaDTO = new InformacionIncidenciaDTO();
					informacionIncidenciaDTO.setCveInformacionObra(cveInformacionObra);
					informacionIncidenciaDTO.setCalendarioReporteDTO(calendario.toConvertCalendarioReporteDTO());
					informacionIncidenciaDTO.setNumAnio(anioActual);
				}
			} else {
				System.out.println("No existe ultimo reporte bimestrarl ultimo if 2 ");
				boolean noPresentarReporte = false;
				Calendar calInicioObra = Calendar.getInstance();
				calInicioObra.setTime(regActual.getFecIniObra());
				int anioIni = calInicioObra.get(Calendar.YEAR);
				Long bimActual = calendario.getCveBimCalendario();


				if(calInicioObra.after(calActual)) {
					System.out.println("Entro cuando la fecha de inicio de obra es mas alta que la fecha actual");
					noPresentarReporte = true;
				} else  {
					System.out.println("la fecha actual es mayor a la fecha de inicio");
					RocCalendarioReporte calendarioFin = calendarioReporteDao.findMesPresentacion("" + mesReg);

					Long bimFin = calendarioFin.getCveBimCalendario();
					RocCalendarioReporte calendarioInicio = calendarioReporteDao.findMesPresentacion("" + (calInicioObra.get(Calendar.MONTH)+1));
					Long bimIni = calendarioInicio.getCveBimCalendario();

					if (calFinObra.after(calActual)) {
						if(anioIni == anioActual && bimIni.equals(bimActual) ) {
							noPresentarReporte = true;
						}
					}

					Calendar fechaMaximoPeriodo = Calendar.getInstance();
					fechaMaximoPeriodo.setTime(calendarioFin.getFecFinPerDeclarado());
					fechaMaximoPeriodo.set(Calendar.YEAR, anioReg);

					System.out.println("Fecha fin de la obra  " + calFinObra);
					System.out.println("Fecha maxima del periodo  " + fechaMaximoPeriodo);

					if(anioIni == anioReg && bimIni.equals(bimFin) && !calFinObra.after(fechaMaximoPeriodo) ) {
						noPresentarReporte = true;
					}
				}

				if(!noPresentarReporte) {
					informacionIncidenciaDTO = new InformacionIncidenciaDTO();
					informacionIncidenciaDTO.setCveInformacionObra(cveInformacionObra);
					informacionIncidenciaDTO.setCalendarioReporteDTO(calendario.toConvertCalendarioReporteDTO());
					informacionIncidenciaDTO.setNumAnio(numAnio);
				}

			}
		}

		return informacionIncidenciaDTO;
	}


	@Override
	public InformacionIncidenciaDTO consultarUltimoReporteBimestralPorCveInformacionObra(Long cveInformacionObra)
			throws BusinessException {

		InformacionIncidenciaDTO informacionIncidenciaDTO = null;
		RotInformacionObra regActual = informacionObraDao.findByCveInformacionObra(cveInformacionObra);
		//Datos de la fecha actual
		Calendar calFecActual = Calendar.getInstance();
		calFecActual.setTime(new Date());
		int anioActual = calFecActual.get(Calendar.YEAR);
		int mesActual = calFecActual.get(Calendar.MONTH);
		//datos de la fecha fin de obra
		Calendar calFecFinObra = Calendar.getInstance();
		calFecFinObra.setTime(regActual.getFecFinObra());
		int anioFinObra = calFecFinObra.get(Calendar.YEAR);
		int mesFinObra = calFecFinObra.get(Calendar.MONTH);

		System.out.println("Entro a verificar para fechas fin iguales para la obra " + cveInformacionObra);
		log.debug("fecha actual es: " +mesActual + " - " + anioActual +  " el fin de obra es "+ mesFinObra+" - " + anioFinObra);
		System.out.println("El anio actual es: " + anioActual +  " el fin de obra es " + anioFinObra);

		if (anioFinObra > anioActual) {

			/*
			 * el anio de fin de obra es mayor a la fecha de hoy mi tope es el
			 * anio actual reviso el ultimo bimestre a presentar si es el 6 le
			 * quito un año al año actual
			 *
			 */
			log.debug("Entro al if de anio fin de obra mayor");
			informacionIncidenciaDTO = obteneterIncidenciaSiFinDeObraEsMayorAFechaActual(cveInformacionObra);

		} else {
			// Mismo Anno de fin de obra y fecha actual iguales
			// Mismo Mes
			if (anioFinObra == anioActual && mesFinObra >= mesActual) {
				System.out.println("El fin de obra es igual a la fecha actual para la obra " + cveInformacionObra);
				informacionIncidenciaDTO = obteneterIncidenciaSiFinDeObraEsIgualFechaActual(cveInformacionObra, regActual);

			} else {

				informacionIncidenciaDTO = obteneterIncidenciaSiFinDeObraEsMenorAFechaActual(cveInformacionObra, regActual);

			}
		}

		return informacionIncidenciaDTO;
	}


	public InformacionIncidenciaDTO obteneterIncidenciaSiFinDeObraEsMayorAFechaActual(Long cveInformacionObra){

		InformacionIncidenciaDTO informacionIncidenciaDTO = null;

		int anioReg = Calendar.getInstance().get(Calendar.YEAR);
		int anioActual = Calendar.getInstance().get(Calendar.YEAR);

		RocCalendarioReporte calendario = calendarioReporteDao.findMesPresentacion(String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1));
		RotInformacionIncidencia rotInformacionIncidencia = informacionIncidenciaDao.findLastReporteBimestralByCveInformacionObra(cveInformacionObra);
		log.debug("El bimestre es : " + calendario.getCveBimCalendario());

		if(calendario.getCveBimCalendario() == 6){
			anioReg--;
		}

		if (rotInformacionIncidencia != null) {
			log.debug("Si encontre resporte bimestral para la obra " + cveInformacionObra);
			if (!rotInformacionIncidencia.getRocCalendarioReporte().getCveBimCalendario().equals(calendario.getCveBimCalendario())
					&& (rotInformacionIncidencia.getNumAnio() == anioReg
							||  rotInformacionIncidencia.getNumAnio() == anioActual)) {
				informacionIncidenciaDTO = new InformacionIncidenciaDTO();
				informacionIncidenciaDTO.setCveInformacionObra(cveInformacionObra);
				informacionIncidenciaDTO.setCalendarioReporteDTO(calendario.toConvertCalendarioReporteDTO());

				if(calendario.getCveBimCalendario() == 6){
					informacionIncidenciaDTO.setNumAnio(anioActual - 1);
				}else{
					informacionIncidenciaDTO.setNumAnio(anioActual);
				}
			}else {

				if(rotInformacionIncidencia.getRocCalendarioReporte().getCveBimCalendario()
						.equals(calendario.getCveBimCalendario())
						&& (rotInformacionIncidencia.getNumAnio() == anioReg || rotInformacionIncidencia
								.getNumAnio() == anioActual)){

				}else{
					informacionIncidenciaDTO = new InformacionIncidenciaDTO();
					informacionIncidenciaDTO.setCveInformacionObra(cveInformacionObra);
					informacionIncidenciaDTO.setCalendarioReporteDTO(calendario.toConvertCalendarioReporteDTO());
					if(calendario.getCveBimCalendario() == 6){
                        informacionIncidenciaDTO.setNumAnio(anioActual - 1);
	                }else{
	                        informacionIncidenciaDTO.setNumAnio(anioActual);
	                }

//					informacionIncidenciaDTO.setNumAnio(anioActual);
				}

			}
		} else {
			log.debug("No encontre resporte bimestral para la obra " + cveInformacionObra);
			RotInformacionObra infoObraValida = informacionObraDao.findByCveInformacionObra(cveInformacionObra);
			cargarBimestresExtemporaneos(infoObraValida.getFecIniObra(), infoObraValida.getFecFinObra(), cveInformacionObra);
			RotInformacionIncidencia rotInformacionIncidencia2 = informacionIncidenciaDao.findLastReporteBimestralByCveInformacionObra(cveInformacionObra);
			if (rotInformacionIncidencia2 != null) {
				log.debug("encontre reporte despues de cargar bimestres extemporaneos");
				System.out.println("encontre reporte despues de cargar bimestres extemporaneos");
				if (!rotInformacionIncidencia2.getRocCalendarioReporte().getCveBimCalendario().equals(calendario.getCveBimCalendario())
					&& rotInformacionIncidencia2.getNumAnio() == anioActual) {

					informacionIncidenciaDTO = new InformacionIncidenciaDTO();
					informacionIncidenciaDTO.setCveInformacionObra(cveInformacionObra);
					informacionIncidenciaDTO.setCalendarioReporteDTO(calendario.toConvertCalendarioReporteDTO());
					informacionIncidenciaDTO.setNumAnio(anioActual);
				}
			} else {
				log.debug("No encontre reporte despues de cargar bimestres extemporaneos");
				System.out.println("No encontre reporte despues de cargar bimestres extemporaneos");
				informacionIncidenciaDTO = new InformacionIncidenciaDTO();
				informacionIncidenciaDTO.setCveInformacionObra(cveInformacionObra);
				informacionIncidenciaDTO.setCalendarioReporteDTO(calendario.toConvertCalendarioReporteDTO());

				if(calendario.getCveBimCalendario() == 6){
					informacionIncidenciaDTO.setNumAnio(anioActual - 1);
				}else{
					informacionIncidenciaDTO.setNumAnio(anioActual);
				}
			}
		}
		return informacionIncidenciaDTO;
	}


	public InformacionIncidenciaDTO obteneterIncidenciaSiFinDeObraEsIgualFechaActual(Long cveInformacionObra, RotInformacionObra regActual){
		System.out.println("Entro al metodo obteneterIncidenciaSiFinDeObraEsIgualFechaActual public con RotInformacionObra");
		InformacionIncidenciaDTO informacionIncidenciaDTO = null;
		Integer numAnio = null;
		Calendar calActual = Calendar.getInstance();
		calActual.setTime(new Date());
		int anioActual = calActual.get(Calendar.YEAR);
		int mesActual = calActual.get(Calendar.MONTH)+1;
		Calendar calFinObra = Calendar.getInstance();
		calFinObra.setTime(regActual.getFecFinObra());
		int anioReg = calFinObra.get(Calendar.YEAR);//regActual.getFecFinObra().getYear() + 1900;

		System.out.println("El mes de presentacion es " + mesActual);

		RocCalendarioReporte calendario = calendarioReporteDao.findMesPresentacion("" + mesActual);

		System.out.println("El bimestre a presentar es  " + calendario.getCveBimCalendario());
		//Si es el bimestre 6 al annio le restas uno correspondientes
		if(calendario.getCveBimCalendario() == 6){
			anioReg = anioReg - 1;
		}

		System.out.println("El anio es " + anioReg);
		numAnio = anioReg;

		RotInformacionIncidencia rotInformacionIncidencia = informacionIncidenciaDao.findLastReporteBimestralByCveInformacionObra(cveInformacionObra);

		if (rotInformacionIncidencia != null) {
			System.out.println("Existe ultimo reporte bimestrarl y su informacion es \n"
					+ "bimestre incidencia" + rotInformacionIncidencia.getRocCalendarioReporte().getCveBimCalendario() + " bimestre actual "+calendario.getCveBimCalendario()+"\n"
							+ "anio incidencia " + rotInformacionIncidencia.getNumAnio() + " anioReg " + anioReg + " aniio actual " + anioActual);
			if (!rotInformacionIncidencia.getRocCalendarioReporte().getCveBimCalendario().equals(calendario.getCveBimCalendario())
				&& (rotInformacionIncidencia.getNumAnio() == anioReg || rotInformacionIncidencia.getNumAnio() == anioActual)) {
				System.out.println("Entro al primer if cuando existe incidencia");
				informacionIncidenciaDTO = new InformacionIncidenciaDTO();
				informacionIncidenciaDTO.setCveInformacionObra(cveInformacionObra);
				informacionIncidenciaDTO.setCalendarioReporteDTO(calendario.toConvertCalendarioReporteDTO());
				informacionIncidenciaDTO.setNumAnio(numAnio);
			}else {
					if (rotInformacionIncidencia.getRocCalendarioReporte().getCveBimCalendario().equals(calendario.getCveBimCalendario())
							&& (rotInformacionIncidencia.getNumAnio() == anioReg || rotInformacionIncidencia.getNumAnio() == anioActual)) {
						System.out.println("Entro al segundo if cuando existe incidencia");
					} else {
						System.out.println("Entro al else cuando existe incidencia");
						informacionIncidenciaDTO = new InformacionIncidenciaDTO();
						informacionIncidenciaDTO.setCveInformacionObra(cveInformacionObra);
						informacionIncidenciaDTO.setCalendarioReporteDTO(calendario.toConvertCalendarioReporteDTO());
						informacionIncidenciaDTO.setNumAnio(anioReg);
					}

				}

		} else {
			System.out.println("No existe ultimo reporte bimestrarl ");
			RotInformacionObra infoObraValida = informacionObraDao.findByCveInformacionObra(cveInformacionObra);
			cargarBimestresExtemporaneos(infoObraValida.getFecIniObra(), infoObraValida.getFecFinObra(), cveInformacionObra);
			RotInformacionIncidencia rotInformacionIncidencia2 = informacionIncidenciaDao
					.findLastReporteBimestralByCveInformacionObra(cveInformacionObra);
			if (rotInformacionIncidencia2 != null) {
				if (!rotInformacionIncidencia2.getRocCalendarioReporte().getCveBimCalendario().equals(calendario.getCveBimCalendario())
						&& rotInformacionIncidencia2.getNumAnio() == anioActual) {
					informacionIncidenciaDTO = new InformacionIncidenciaDTO();
					informacionIncidenciaDTO.setCveInformacionObra(cveInformacionObra);
					informacionIncidenciaDTO.setCalendarioReporteDTO(calendario.toConvertCalendarioReporteDTO());
					informacionIncidenciaDTO.setNumAnio(anioActual);
				}
			} else {
				//TODO quitar anio cuando bimestre sea 6
				informacionIncidenciaDTO = new InformacionIncidenciaDTO();
				informacionIncidenciaDTO.setCveInformacionObra(cveInformacionObra);
				informacionIncidenciaDTO.setCalendarioReporteDTO(calendario.toConvertCalendarioReporteDTO());
				informacionIncidenciaDTO.setNumAnio(numAnio);

			}
		}

		return informacionIncidenciaDTO;
	}


	public InformacionIncidenciaDTO obteneterIncidenciaSiFinDeObraEsMenorAFechaActual(Long cveInformacionObra, RotInformacionObra regActual){
		InformacionIncidenciaDTO informacionIncidenciaDTO = null;

		int anioActual = new Date().getYear() + 1900;
		int anioReg = regActual.getFecFinObra().getYear() + 1900;

		RocCalendarioReporte calendario = calendarioReporteDao.findMesPresentacion(String.valueOf(regActual.getFecFinObra().getMonth() + 1));

		if(calendario.getCveBimCalendario() == 6){
			anioReg--;
		}

		RotInformacionIncidencia rotInformacionIncidencia = informacionIncidenciaDao
				.findLastReporteBimestralByCveInformacionObra(cveInformacionObra);

		if (rotInformacionIncidencia != null) {
			if (!rotInformacionIncidencia.getRocCalendarioReporte().getCveBimCalendario()
					.equals(calendario.getCveBimCalendario())
					&& (rotInformacionIncidencia.getNumAnio() == anioReg
							|| rotInformacionIncidencia.getNumAnio() == anioActual)) {
				informacionIncidenciaDTO = new InformacionIncidenciaDTO();
				informacionIncidenciaDTO.setCveInformacionObra(cveInformacionObra);
				informacionIncidenciaDTO.setCalendarioReporteDTO(calendario.toConvertCalendarioReporteDTO());
				if(calendario.getCveBimCalendario() == 6){
					informacionIncidenciaDTO.setNumAnio(regActual.getFecFinObra().getYear() + 1900 - 1);
				}else{
					informacionIncidenciaDTO.setNumAnio(regActual.getFecFinObra().getYear() + 1900);
				}
			}else{
				if (!rotInformacionIncidencia.getRocCalendarioReporte().getCveBimCalendario()
						.equals(calendario.getCveBimCalendario())
						&& regActual.getFecFinObra().getYear() <= new Date().getYear()) {
					informacionIncidenciaDTO = new InformacionIncidenciaDTO();
					informacionIncidenciaDTO.setCveInformacionObra(cveInformacionObra);
					informacionIncidenciaDTO.setCalendarioReporteDTO(calendario.toConvertCalendarioReporteDTO());
					informacionIncidenciaDTO.setNumAnio(regActual.getFecFinObra().getYear() + 1900);
				}
			}
		} else {
			informacionIncidenciaDTO = new InformacionIncidenciaDTO();
			informacionIncidenciaDTO.setCveInformacionObra(cveInformacionObra);
			informacionIncidenciaDTO.setCalendarioReporteDTO(calendario.toConvertCalendarioReporteDTO());
			if(calendario.getCveBimCalendario() == 6){
				informacionIncidenciaDTO.setNumAnio(regActual.getFecFinObra().getYear() + 1900 - 1);
			}else{
				informacionIncidenciaDTO.setNumAnio(regActual.getFecFinObra().getYear() + 1900);
			}
		}
		return informacionIncidenciaDTO;
	}


	@Override
	/**
	 * TODO cambiar este metodo para que sea mucho mas generico
	 *
	 * @param fecInicioObra
	 * @param fecFinObra
	 * @param fecRegistroObra
	 * @param cveInformacionObra
	 */
	@SuppressWarnings("deprecation")
	public void cargarBimestresExtemporaneos(Date fecInicio, Date fecFin, Long cveInformacionObra) {
		// XXX esto se hace para saber cuantos meses se estan tocando durante la
		// duracion de la obra
		Date fecActualSistema = new Date();
		int annos = 0;
		int bimestres = 0;
		int bimestresIni = 0;

		// Se obtiene el bimestre correspondiente a la fecha de inicio
		RocCalendarioReporte calenInicial = calendarioReporteDao
				.findMesDeclarar(String.valueOf(fecInicio.getMonth() + 1));
		// Se obtiene el bimestre correspondiente a la fecha de fin
		RocCalendarioReporte calenFinal = calendarioReporteDao.findMesDeclarar(String.valueOf(fecFin.getMonth() + 1));
		// Se obtiene el bimestre correspondiente a la fecha actual del sistema
		RocCalendarioReporte calenActual = calendarioReporteDao
				.findMesDeclarar(String.valueOf(fecActualSistema.getMonth() + 1));

		// Se obtiene el #de biemestres que se deben del anno donde esta ubicada
		bimestresIni = obtenerNumeroBimestresQueDeben(calenInicial.getCveBimCalendario().intValue());
//		// la fecha inicio
//		switch (calenInicial.getCveBimCalendario()) {
//		case 1:
//			bimestresIni = 6;
//			break;
//		case 2:
//			bimestresIni = 5;
//			break;
//		case 3:
//			bimestresIni = 4;
//			break;
//		case 4:
//			bimestresIni = 3;
//			break;
//		case 5:
//			bimestresIni = 2;
//			break;
//		case 6:
//			bimestresIni = 1;
//			break;
//
//		default:
//			break;
//		}

		// XXX obra viva
		if (fecFin.after(fecActualSistema)) {
			// anios de diferencia entre la fecha de actual con la fecha de
			// inicio
			// anio actual vs fecha inicio obra para conocer si esta en el mismo
			// anio de registro
			annos = (fecActualSistema.getYear() + 1900) - (fecInicio.getYear() + 1900);
			// si la duracion de la obra es mayor a un anio se tendra que hacer
			// el calculo de bimestres
			if (annos > 0) {
				// si la obra dura 1 o mas anios
				if (annos > 1) {
					// XXX DIFERENTES ANIOS
					// calculo de los bimetsres que toca la fecha de inicio con
					// respecto a los anios de
					// se resta el anio actual para calculas los bimestres que
					// toca
					annos--;
					// REVISAR LOS BIMESTRES QUE LA OBRA TOCA SIN TOMAR EN
					// CUENTA EL ULTIMO BIMESTRE POR QUE ES DE LA TERMINACION
					bimestres = (annos * 6) + bimestresIni;
					// se calculan los bimestres desde la fecha actual y se
					// suman a los calculados con al fecha de inicio
					// XXX se quita el ultimo bimestre por ser el de la
					// terminacion
					bimestres = bimestres +  calenActual.getCveBimCalendario().intValue() - 1;
					// si la duracion de la obra es menor a un anio se necesita
					// conocer la duracion en meses
				} else {
					// XXX DIFERENTES ANIOS
					bimestres = bimestresIni;
					// se quita el reporte bimestral final
					bimestres = bimestres + calenActual.getCveBimCalendario().intValue() - 1;
				}
			} else if(annos<0){
				return;
			}else {
				// XXX mismo ANIO
				bimestres = calenActual.getCveBimCalendario().intValue() - calenInicial.getCveBimCalendario().intValue();
			}
		} else {
			// XXX obra terminada
			annos = (fecFin.getYear() + 1900) - (fecInicio.getYear() + 1900);

			if (annos > 0) {
				if (annos > 1) {
					annos--;
					// REVISAR LOS BIMESTRES QUE LA OBRA TOCA SIN TOMAR EN
					// CUENTA EL ULTIMO BIMESTRE POR QUE ES DE LA TERMINACION
					bimestres = (annos * 6) + bimestresIni;
					bimestres = bimestres + calenFinal.getCveBimCalendario().intValue() - 1;
				} else {
					bimestres = bimestresIni;
					// se quita el reporte bimestral final
					bimestres = bimestres + calenFinal.getCveBimCalendario().intValue() - 1;
				}
			} else {
				bimestres = calenFinal.getCveBimCalendario().intValue() - calenInicial.getCveBimCalendario().intValue();
			}
		}
		// se quita 1 bimestre por que es elq ue se va a presentar
		bimestres = bimestres - 1;
		log.debug("bimestres: " + bimestres);

		List<RocCalendarioReporte> listBimestres = calendarioReporteDao.calendariosOrdenadosAsc();

		MotivoTipoIncidenciaDTO motivoTipoIncidenciaDTO = new MotivoTipoIncidenciaDTO();
		TipoIncidenciaDTO tipoIncidenciaDTO = new TipoIncidenciaDTO();
		MotivoDTO motivoDTO = new MotivoDTO();
		motivoDTO.setCveMotivo(13L);
		tipoIncidenciaDTO.setCveTipoIncidencia(new Long(6));
		motivoTipoIncidenciaDTO.setMotivoDTO(motivoDTO);
		motivoTipoIncidenciaDTO.setTipoIncidenciaDTO(tipoIncidenciaDTO);
		motivoTipoIncidenciaDTO.setCveMotivoTipoIncidencia(14L);
		TipoRegistroDTO tipoRegistroDTO = new TipoRegistroDTO();
		tipoRegistroDTO.setCveTipoRegistro(3L);// 3-No presentada

		// se resta 1 para poder inicar en 0 la iteracion de la lista
		int bimesteInicio = calenInicial.getCveBimCalendario().intValue() - 1;

		int annoBimestre = fecInicio.getYear() + 1900;

		for (int i = 0; i < bimestres; i++) {
			InformacionIncidenciaDTO informacionIncidenciaDTO = new InformacionIncidenciaDTO(0L,
					motivoTipoIncidenciaDTO, tipoRegistroDTO, null);
			RotInformacionIncidencia entity = ConvertUtil.toConvertInformacionIncidencia(informacionIncidenciaDTO);
			entity.getRocMotivoTipoIncidencia().setCveMotivoTipoIncidencia(14L);
			entity.setCveInformacionObra(cveInformacionObra);
			entity.setRocCalendarioReporte(listBimestres.get(bimesteInicio));
			entity.setNumAnio(annoBimestre);
			if (bimesteInicio == 5) {
				bimesteInicio = 0;
				annoBimestre++;
			} else {
				bimesteInicio++;
			}

			informacionIncidenciaDao.save(entity);
		}
	}


	public int obtenerNumeroBimestresQueDeben(int cveBimestre){
		int result = 0;
		switch (cveBimestre) {
			case 1:
				result = 6;
				break;
			case 2:
				result = 5;
				break;
			case 3:
				result = 4;
				break;
			case 4:
				result = 3;
				break;
			case 5:
				result = 2;
				break;
			case 6:
				result = 1;
				break;

			default:
				break;
		}
		return result;
	}

	/**
	 *
	 * @param beginningDate
	 * @param endingDate
	 * @return
	 */
	public Integer diferenciaEnMeses(Date beginningDate, Date endingDate) {
		if (beginningDate == null || endingDate == null) {
			return 0;
		}
		Calendar cal1 = new GregorianCalendar();
		cal1.setTime(beginningDate);
		Calendar cal2 = new GregorianCalendar();
		cal2.setTime(endingDate);
		return differenceInMonths(cal1, cal2);
	}

	/**
	 *
	 * @param beginningDate
	 * @param endingDate
	 * @return
	 */
	private Integer differenceInMonths(Calendar beginningDate, Calendar endingDate) {
		if (beginningDate == null || endingDate == null) {
			return 0;
		}
		int m1 = beginningDate.get(Calendar.YEAR) * 12 + beginningDate.get(Calendar.MONTH);
		int m2 = endingDate.get(Calendar.YEAR) * 12 + endingDate.get(Calendar.MONTH);
		return m2 - m1;
	}

	@Override
	public InformacionIncidenciaDTO consultarUltimoReporteBimestralPresentado(Long cveInformacionObra)
			throws BusinessException {
		InformacionIncidenciaDTO informacionIncidenciaDTO = null;
		RotInformacionIncidencia rotInformacionIncidencia = informacionIncidenciaDao
				.findLastReporteBimPresenByCveInfoObra(cveInformacionObra);
		if (rotInformacionIncidencia != null) {
			informacionIncidenciaDTO = rotInformacionIncidencia.toConvertInformacionIncidenciaDTO();
		}
		return informacionIncidenciaDTO;
	}

	@Override
	public String consultarDescripcionMotivoPorClaveMotivo(Long cveMotivo, Long cveTipoIn) {
		RocMotivoIncidencia rocMotivoIncidencia = motivoTipoIncidenciaDao
				.findMotivoIncidenciaByMotivoTipoIncidencia(cveMotivo, cveTipoIn);
		if (rocMotivoIncidencia != null) {
			return rocMotivoIncidencia.getRocMotivo().getDesMotivo();
		} else {
			return "";
		}
	}

	@Override
	public void eliminaReporteBimByCveInfoObra(Long cveInformacionObra, String fechaFinObra, int annio) {
		informacionIncidenciaDao.eliminaReporteBimByCveInfoObra(cveInformacionObra, fechaFinObra, annio);

	}

	@Override
	public void eliminaReporteBimByCveInfoObraDeclarado(Long cveInformacionObra, String fechaFinObra, int annio) {
		informacionIncidenciaDao.eliminaReporteBimByCveInfoObraDeclarado(cveInformacionObra, fechaFinObra, annio);

	}

	@Override
	public void eliminaReportesBimestralesNoPresentados(Long cveInformacionObra) {
		informacionIncidenciaDao.eliminaReportesBimestralesNoPresentados(cveInformacionObra);

	}

	@Override
	public CalendarioReporteDTO consultarBimestreCorrespondiente(String mes) throws BusinessException {
		RocCalendarioReporte calendarioReporte = calendarioReporteDao.findMesDeclarar(mes);

		CalendarioReporteDTO calendarioReporteDTO = calendarioReporte.toConvertCalendarioReporteDTO();
		return calendarioReporteDTO;
	}

	@Override
	public List<InformacionIncidenciaDTO> consultarIncidenciasPorCveInformacionObra(String cveInformacionObra) {
		List<InformacionIncidenciaDTO> listaInformacionIncidenciaDTO = new ArrayList<InformacionIncidenciaDTO>();
		List<RotInformacionIncidencia> listaInformacionIncidencia = null;
		listaInformacionIncidencia = informacionIncidenciaDao.findAllByCveInformacionObra(cveInformacionObra);
		if (listaInformacionIncidencia != null) {
			for (RotInformacionIncidencia rotInformacionIncidencia : listaInformacionIncidencia) {
				listaInformacionIncidenciaDTO.add(rotInformacionIncidencia.toConvertInformacionIncidenciaDTO());
			}
		}

		return listaInformacionIncidenciaDTO;
	}

}
