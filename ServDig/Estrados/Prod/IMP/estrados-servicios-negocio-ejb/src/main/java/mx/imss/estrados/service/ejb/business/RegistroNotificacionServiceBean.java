package mx.imss.estrados.service.ejb.business;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.FirmaElectronicaSegPortType;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.FirmaElectronicaSegService;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.ObjectFactory;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.RegistroSeguimientoRequestType;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.RegistroSeguimientoResponseType;
import mx.com.metatrust.doctrust.wsfirmaelectronicaseg.ResultadoType;
import mx.gob.imss.ctirss.delta.model.firma.Archivo;
import mx.gob.imss.ctirss.delta.model.firma.PeticionGuardadoArchivosFirma;
import mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoSimple;
import mx.gob.imss.ctirss.delta.model.firma.RespuestaGuardadoArchivosFirma;
import mx.imss.estrados.commons.Constantes;
import mx.imss.estrados.dto.AreaRespNotifDTO;
import mx.imss.estrados.dto.AreanormativaDTO;
import mx.imss.estrados.dto.DiasInhabilDTO;
import mx.imss.estrados.dto.DocumentosAdjuntosDTO;
import mx.imss.estrados.dto.NotificacionesDTO;
import mx.imss.estrados.dto.ProcesoDTO;
import mx.imss.estrados.dto.SsoVwUsuarioDTO;
import mx.imss.estrados.dto.TipodocumentoDTO;
import mx.imss.estrados.entity.NeeCatAreaRespNotif;
import mx.imss.estrados.entity.NeeCatDelegacion;
import mx.imss.estrados.entity.NeeCatDiasInhabil;
import mx.imss.estrados.entity.NeeCatProceso;
import mx.imss.estrados.entity.NeeCatStatus;
import mx.imss.estrados.entity.NeeCatSubdelegacion;
import mx.imss.estrados.entity.NeeCatSujetoANotificar;
import mx.imss.estrados.entity.NeeCatTipoAdjunto;
import mx.imss.estrados.entity.NeeCatTipodocumento;
import mx.imss.estrados.entity.NeeDocumentosAdjuntos;
import mx.imss.estrados.entity.NeeNotificaciones;
import mx.imss.estrados.entity.SsoVwUsuario;
import mx.imss.estrados.service.ejb.dao.GenericDAO;
import mx.imss.estrados.service.ejb.tramiteDigital.TramiteDigitalServiceLocal;
import mx.imss.estrados.service.interfaces.RegistroNotificacionServiceRemote;
import mx.imss.estrados.service.interfaces.ReporteNotificacionServiceRemote;
import mx.imss.estrados.utils.EstradosStringUtils;
import mx.imss.estrados.utils.UtileriaFechas;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import vo.InfoPatronEntrada;
import vo.InfoPatronSalida;
import ws.WSConsultaPatronServiceProxy;

@Stateless(name = "registroNotificacionServiceB", mappedName = "registroNotificacionServiceB")
public class RegistroNotificacionServiceBean implements RegistroNotificacionServiceRemote {
	
	/**
	 * Logger
	 */
	private final static Logger logger = Logger.getLogger(RegistroNotificacionServiceBean.class);
	
	@EJB
	GenericDAO<NeeCatDiasInhabil> diasInhabilDAO;
	
	@EJB
	GenericDAO<NeeCatAreaRespNotif> areaResponNotDAO;
	
	@EJB
	GenericDAO<NeeCatTipodocumento> catTipoDocumentoDAO;
	
	@EJB
	GenericDAO<NeeNotificaciones> notificacionDAO;
	
	@EJB
	GenericDAO<NeeDocumentosAdjuntos> documentoAdjunto;
	
	@EJB
	TramiteDigitalServiceLocal digitalServiceLocal;
	
	@EJB
	ReporteNotificacionServiceRemote notificacionServiceRemote;
	
	@EJB
	GenericDAO<NeeCatProceso> catProcesoDAO;
	
	
	@EJB
	GenericDAO<SsoVwUsuario> vistaUsuario;
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<List> consultaDiasInhabiles() {
		List<NeeCatDiasInhabil> listNeeCatDiasInhabil;
		List<DiasInhabilDTO> listDiasInhabilDTOs = new ArrayList<DiasInhabilDTO>();
		List<List> listDeList = null;
		List<Integer> list;
		
		listNeeCatDiasInhabil = (List<NeeCatDiasInhabil>) diasInhabilDAO.getAll(NeeCatDiasInhabil.class);
		
		if (!listNeeCatDiasInhabil.isEmpty()) {
			for (NeeCatDiasInhabil neeCatDiasInhabil : listNeeCatDiasInhabil) {
				DiasInhabilDTO diasInhabilDTO = new DiasInhabilDTO();
				diasInhabilDTO.setCveDiaInhabil(neeCatDiasInhabil.getCveDiaInhabil());
				diasInhabilDTO.setDesDiaInhabil(neeCatDiasInhabil.getDesDiaInhabil());
				diasInhabilDTO.setFecFechainhabil(neeCatDiasInhabil.getFecFechainhabil());
				listDiasInhabilDTOs.add(diasInhabilDTO);
			}
			
			listDeList = new ArrayList<List>();
			
			for (DiasInhabilDTO diasInhabilDTO : listDiasInhabilDTOs) {
				list = new ArrayList<Integer>();
				if (obtenerAnio(diasInhabilDTO.getFecFechainhabil()).equals(obtenerAnio(new Date()))) {
					list.add(obtenerMes(diasInhabilDTO.getFecFechainhabil()));
					list.add(obtenerDia(diasInhabilDTO.getFecFechainhabil()));
					listDeList.add(list);
				}
			}
		}
		return listDeList;
	}
	
	public Integer obtenerAnio(Date date) {
		Integer anio = 0;
		if (date != null) {
			String formatoAño = "yyyy";
			SimpleDateFormat dateFormat = new SimpleDateFormat(formatoAño);
			anio = Integer.parseInt(dateFormat.format(date));
		}
		return anio;
	}
	
	public Integer obtenerMes(Date date) {
		Integer mes = 0;
		if (date != null) {
			String formatoMes = "MM";
			SimpleDateFormat dateFormat = new SimpleDateFormat(formatoMes);
			mes = Integer.parseInt(dateFormat.format(date));
		}
		return mes;
	}
	
	public Integer obtenerDia(Date date) {
		Integer dia = 0;
		if (date != null) {
			String formatoDia = "dd";
			SimpleDateFormat dateFormat = new SimpleDateFormat(formatoDia);
			dia = Integer.parseInt(dateFormat.format(date));
		}
		return dia;
	}

	@Override
	public List<AreaRespNotifDTO> recuperaAreasResponsables(String curp) throws RuntimeException {
		List<AreaRespNotifDTO> listAreaRespNotifDTOs = new ArrayList<AreaRespNotifDTO>();
		AreaRespNotifDTO areaRespNotifDTO = null;
		ProcesoDTO procesoDTO = null;
		AreanormativaDTO areanormativaDTO = new AreanormativaDTO();
		try {
			List<SsoVwUsuario> listaCurps = vistaUsuario.getByQuery("from SsoVwUsuario user where user.desUsrCURP='"+curp+"'");
			SsoVwUsuario soUsuario = listaCurps.get(0);
			
			if (soUsuario != null) {
				List<NeeCatAreaRespNotif> listAreaRespNotifs = areaResponNotDAO.getByQuery("from NeeCatAreaRespNotif area where area.neeCatAreanormativa.cveAreanorma="
						  +soUsuario.getCveSsoAreaNorma()+" and area.cveSSODepto="+soUsuario.getCveSSODepto());
				
				if (listAreaRespNotifs != null && !listAreaRespNotifs.isEmpty()) {
					for(NeeCatAreaRespNotif neeCatAreaRespNotif : listAreaRespNotifs) {
						areaRespNotifDTO = new AreaRespNotifDTO();
						procesoDTO = new ProcesoDTO();
						
						areaRespNotifDTO.setCveAreaRespNotif(neeCatAreaRespNotif.getCveAreaRespNotif());
						procesoDTO.setCveProceso(neeCatAreaRespNotif.getNeeCatProceso().getCveProceso());
						procesoDTO.setDesProceso(neeCatAreaRespNotif.getNeeCatProceso().getDesProceso());
						
						areaRespNotifDTO.setProcesoDTO(procesoDTO);
						areanormativaDTO.setCveAreanorma(soUsuario.getCveSsoAreaNorma());
						areaRespNotifDTO.setAreanormativaDTO(areanormativaDTO);
						listAreaRespNotifDTOs.add(areaRespNotifDTO);
					}
				}
			}
		} catch (Exception ex) {
			logger.warn("ERROR: Al tratar de obtener la lista de Areas Responsables.", ex);
			throw new RuntimeException("ERROR: En el servicio de RegistroNotificacionServiceBean, al tratar de obtener la lista de Areas Responsables.", ex);
		}
		return listAreaRespNotifDTOs;
	}

	@Override
	public InfoPatronSalida validaRegistroPatronal(String registroPatronal) {
		// TODO Auto-generated method stub
		
		InfoPatronSalida salida = null;
		try {
			
			WSConsultaPatronServiceProxy vo = new WSConsultaPatronServiceProxy();
			InfoPatronEntrada patIn = new InfoPatronEntrada();
			patIn.setRegistroPatronal(registroPatronal.substring(0,8));
			patIn.setModalidad(registroPatronal.substring(8,10));
//			patIn.setDigitoVerificador(String.valueOf(generaDigitoVerificador(registroPatronal)));			
			patIn.setDigitoVerificador(registroPatronal.substring(10,11));
			if(!(registroPatronal.substring(10,11)).equals(String.valueOf(generaDigitoVerificador(registroPatronal.substring(0,10))))){
				//Digito verificador no coincide				
				salida=new InfoPatronSalida();
				salida.setCodigo(-1);
				salida.setDescripcion("El d\u00edgito verificador es incorrecto");
				return salida;
			}
			
			try {
				salida = vo.getInformacionPatron(patIn);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return salida;
	}
	
	   public int generaDigitoVerificador(String nrp) {
		   System.out.println("generando digito verificador");
	        int factorDeConversion = 10;
	        int digitoVerificador = 0;
	        int paso3 = 0;
	        boolean bandera = true;
	        String alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	        String clave = "";
	        int primeraLetra = alfabeto.indexOf(nrp.toUpperCase().charAt(0));
	        if (primeraLetra != -1) {
	            clave = (primeraLetra + factorDeConversion) + nrp.substring(1, nrp.length());
	        } else {
	            clave = nrp;
	        }
	        int i = clave.length() - 1;
	        while (i >= 0) {
	            if (bandera) {
	                int porDos = Integer.parseInt("" + clave.charAt(i)) * 2;
	                if (porDos > 9)// si el resultado es un numero de dos cifras, es necesario tratar
	                               // estas por separado.
	                {
	                    paso3 += (porDos % 10) + (porDos / 10);
	                } else {
	                    paso3 += porDos;
	                }
	                bandera = false;
	            } else {
	                paso3 += Integer.parseInt("" + clave.charAt(i));
	                bandera = true;
	            }
	            i--;
	        }
	        digitoVerificador = 10 - (paso3 % 10);
	        if (digitoVerificador > 9) {
	            digitoVerificador = 0;
	        }
	      
	   return digitoVerificador;
	   }
	@Override
	public List<TipodocumentoDTO> recuperaTiposDocumento(int cveProceso) {
		// TODO Auto-generated method stub
		List<NeeCatTipodocumento> lista=catTipoDocumentoDAO.getByQuery("from NeeCatTipodocumento tipoDoc where tipoDoc.neeCatProceso.cveProceso="+cveProceso);
		List<TipodocumentoDTO> listaCombo=new ArrayList<TipodocumentoDTO>();
		TipodocumentoDTO documentoDTO=null;
		for(NeeCatTipodocumento tipo:lista){
			documentoDTO=new TipodocumentoDTO();
			documentoDTO.setCveTipodocto(tipo.getCveTipodocto());
			documentoDTO.setDesTipodocumento(tipo.getDesTipodocumento());			
			listaCombo.add(documentoDTO);
		}
		return listaCombo;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public NotificacionesDTO guardaParcialNotificacion(NotificacionesDTO notificacionDTO) {
		NeeCatDelegacion neeCatDelegacion = null;
		NeeCatSubdelegacion neeCatSubdelegacion = null;
		NeeCatTipodocumento tipoDocumento = new NeeCatTipodocumento();
		NeeNotificaciones neeNotificaciones = new NeeNotificaciones();
		NeeCatProceso neeCatProceso = new NeeCatProceso();
		NeeCatAreaRespNotif neeCatAreaRespNotif = new NeeCatAreaRespNotif();
		NeeCatSujetoANotificar neeCatSujetoANotificar = new NeeCatSujetoANotificar();
		NeeCatStatus neeCatStatus = new NeeCatStatus();
		List<NeeDocumentosAdjuntos> listNeeDocumentosAdjuntos = null;
		boolean existeAcuerdo = false;
		boolean existeDocumento = false;
		String numeroOficio = "";
		RespuestaFirmadoSimple respuestaFirmadoSimple = null;
		
		// Delegacion
		if (notificacionDTO.getSsoVwUsuarioDTO().getIdDelegacion() != null) {
			neeCatDelegacion = new NeeCatDelegacion();
			neeCatDelegacion.setCveIdDelegacion(notificacionDTO.getSsoVwUsuarioDTO().getIdDelegacion());
		}
		
		// Subdelegacion
		if (notificacionDTO.getSsoVwUsuarioDTO().getIdSubdelegacion()!=null) {
			neeCatSubdelegacion = new NeeCatSubdelegacion();
			neeCatSubdelegacion.setCveIdSubdelegacion(notificacionDTO.getSsoVwUsuarioDTO().getIdSubdelegacion());
		}
		
		//Tipo documento
		tipoDocumento.setCveTipodocto(notificacionDTO.getTipodocumentoDTO().getCveTipodocto());
		
		//Departamento
		if (notificacionDTO.getSsoVwUsuarioDTO().getCveSSODepto() != null) {
			neeNotificaciones.setCveDepto(notificacionDTO.getSsoVwUsuarioDTO().getCveSSODepto());
		}
		
		//Proceso
		neeCatProceso.setCveProceso(notificacionDTO.getAreaRespNotifDTO().getProcesoDTO().getCveProceso());
		
		//Area Responsable
		neeCatAreaRespNotif.setCveAreaRespNotif(notificacionDTO.getAreaRespNotifDTO().getCveAreaRespNotif());
		neeCatAreaRespNotif.setNeeCatProceso(neeCatProceso);
		
		//Sujeto A notificar
		neeCatSujetoANotificar.setCveSujetoANotificar(notificacionDTO.getSujetoANotificarDTO().getCveSujetoANotificar());
			
		//Departamento
//		String query=" FROM NeeCatDepartamento depa where depa.neeCatProceso.cveProceso="+catProceso.getCveProceso()+" and depa.neeCatAreanormativa.cveAreanorma="+notificacionDTO.getAreanormativaDTO().getCveAreanorma().intValue();
//		List<NeeCatDepartamento> lis=catTipoDepartamento.getByQuery(query);
//
//		NeeCatDepartamento de=lis.get(0);
//		departamento.setCveDepto(de.getCveDepto());
//		departamento.setNeeCatAreanormativa(areaNorma);
//		departamento.setNeeCatProceso(catProceso);
			
		//Estatus
		if (!notificacionDTO.isAutorizarNotificacion()) {
			neeCatStatus.setCveStatus(Constantes.ESTATUS.INCOMPLETO.getStatus());
		} else {
			//Se recupera el sello digital
			String con = "FROM NeeDocumentosAdjuntos adj where adj.neeNotificaciones.cveNotificaciones="+notificacionDTO.getCveNotificaciones();
			listNeeDocumentosAdjuntos = documentoAdjunto.getByQuery(con);
			
			if (!listNeeDocumentosAdjuntos.isEmpty()) {
				for (NeeDocumentosAdjuntos neeDocumentosAdjuntos : listNeeDocumentosAdjuntos) {
					if (neeDocumentosAdjuntos.getNeeCatTipoAdjunto().getCveTipoAdjunto().intValue() == Constantes.TIPO_DOCTO_ADJUNTO.ACUERDO.getDocto()) {
						numeroOficio = neeDocumentosAdjuntos.getDesNumOficio();
						existeAcuerdo = true;
					}
					if (neeDocumentosAdjuntos.getNeeCatTipoAdjunto().getCveTipoAdjunto().intValue() == Constantes.TIPO_DOCTO_ADJUNTO.DOCUMENTO.getDocto()) {
						existeDocumento = true;
					}
				}
			} else {
				notificacionDTO.setAutorizacionFinalizada(false);
				return notificacionDTO;
			}
			
//			DocumentosAdjuntosDTO des = new DocumentosAdjuntosDTO();
//			notificacionDTO.setListDocumentosAdjuntosDTOs(new ArrayList<DocumentosAdjuntosDTO>());
//			for (NeeDocumentosAdjuntos adjunto : listNeeDocumentosAdjuntos) {					
//				if (adjunto.getNeeCatTipoAdjunto().getCveTipoAdjunto().intValue() == Constantes.TIPO_DOCTO_ADJUNTO.ACUERDO.getDocto()) {
//					des.setDesNumOficio(adjunto.getDesNumOficio());
//					notificacionDTO.getListDocumentosAdjuntosDTOs().add(des);
//				}
//			}
			
			if (existeAcuerdo && existeDocumento) {
				respuestaFirmadoSimple = digitalServiceLocal.getSelloDigital(generaCadenaOriginal(notificacionDTO, numeroOficio), null, null);
				
				neeNotificaciones.setDesRefAcuse(respuestaFirmadoSimple.getTramite());
				notificacionDTO.setDesRefAcuse(respuestaFirmadoSimple.getTramite());
				neeCatStatus.setCveStatus(Constantes.ESTATUS.REGISTRADA.getStatus());
			} else {
				notificacionDTO.setAutorizacionFinalizada(false);
				return notificacionDTO;
			}
		}
		
		if (notificacionDTO.getCveNotificaciones() != 0) {
			neeNotificaciones.setCveNotificaciones(notificacionDTO.getCveNotificaciones());
		}
		
		neeNotificaciones.setCveUsuario(notificacionDTO.getSsoVwUsuarioDTO().getDesUsrCURP());
		neeNotificaciones.setCorreoUsuario(notificacionDTO.getSsoVwUsuarioDTO().getRefCorreoElectronico());
		neeNotificaciones.setNeeCatAreaRespNotif(neeCatAreaRespNotif);
		neeNotificaciones.setNeeCatSujetoANotificar(neeCatSujetoANotificar);
		neeNotificaciones.setRazonSocial(notificacionDTO.getRazonSocial());
		neeNotificaciones.setDesDomicilio(notificacionDTO.getDesDomicilio());
		neeNotificaciones.setRegistroPatronal(notificacionDTO.getRegistroPatronal());
		neeNotificaciones.setDesNumRegCpa(notificacionDTO.getDesNumRegCpa());
		neeNotificaciones.setNeeCatStatus(neeCatStatus);
		//neeNotificaciones.setNeeCatDepartamento(departamento);
		
		if (neeCatDelegacion != null) {
			neeNotificaciones.setNeeCatDelegacion(neeCatDelegacion);
		}
		
		if (neeCatSubdelegacion != null) {
			neeNotificaciones.setNeeCatSubdelegacion(neeCatSubdelegacion);
		}
		
		neeNotificaciones.setFecPublicacion(getFechaObjeto(notificacionDTO.getFecPublicacionCadena()));
		neeNotificaciones.setFecInicioPublicacion(getFechaObjeto(notificacionDTO.getFecInicioPublicacionCadena()));
		neeNotificaciones.setFecFinPublicacion(getFechaObjeto(notificacionDTO.getFecFinPublicacionCadena()));
		neeNotificaciones.setFecRegistro(Calendar.getInstance().getTime());
		neeNotificaciones.setFecRetiroPublicacion(getFechaObjeto(notificacionDTO.getFecRetiroPublicacionCadena()));
		
		if (tipoDocumento.getCveTipodocto() != null) {
			neeNotificaciones.setNeeCatTipodocumento(tipoDocumento);
		}
		
		neeNotificaciones = notificacionDAO.saveOrUpdate(neeNotificaciones);
		notificacionDTO.setCveNotificaciones(neeNotificaciones.getCveNotificaciones());
			
		if (notificacionDTO.isAutorizarNotificacion() && !listNeeDocumentosAdjuntos.isEmpty()) {
			//Generacion del Reporte
			Map<String,Object> parametrosReporte=new HashMap<String,Object>();
			
			//Datos session
			StringBuilder nom=new StringBuilder();
			SsoVwUsuarioDTO user=notificacionDTO.getSsoVwUsuarioDTO();
			nom.append(user.getNomNombre()+" "+user.getNomPaterno()+" "+user.getNomMaterno()+" ");
			parametrosReporte.put("nombreUsuario", nom.toString());
			parametrosReporte.put("cargoUsuario", user.getDesPuesto());
			parametrosReporte.put("desDelegacion", user.getDesDelegacion()!=null ? user.getDesDelegacion():"");
			parametrosReporte.put("desSubdelegacion", user.getDesSubdelegacion()!=null ? user.getDesSubdelegacion() :"");
			parametrosReporte.put("correo", user.getRefCorreoElectronico());
			if(user.getDesDelegacion() == null) {
				parametrosReporte.put("areaNormativa", user.getDesAreaNorma() !=null ? user.getDesAreaNorma() : "");
				parametrosReporte.put("etiquetaAreaRespNotif","Materia del documento a notificar");
			}else{
				parametrosReporte.put("areaNormativa", "");
				parametrosReporte.put("etiquetaAreaRespNotif","Área responsable de la notificación");
			}
			parametrosReporte.put("fechaActual", UtileriaFechas.parseDateToString(new Date(), "dd/MM/yyyy"));
			parametrosReporte.put("tramite", respuestaFirmadoSimple.getTramite());
			parametrosReporte.put("fecPublicacion", notificacionDTO.getFecPublicacionCadena().replaceAll("-", "/"));
			parametrosReporte.put("fecRetiroPublicacion", notificacionDTO.getFecRetiroPublicacionCadena().replaceAll("-", "/"));
			parametrosReporte.put("sello",respuestaFirmadoSimple.getSello() );
			
			if (!notificacionDTO.getDesNumRegCpa().isEmpty()) {
				parametrosReporte.put("etiquetaRazonSocial","Nombre: ");
			} else {
				parametrosReporte.put("etiquetaRazonSocial","Nombre, denominación o razón social: ");
			}
			parametrosReporte.put("razonSocial", notificacionDTO.getRazonSocial().trim());
			
			if (!notificacionDTO.getRegistroPatronal().isEmpty()) {
				parametrosReporte.put("registroPatronal",notificacionDTO.getRegistroPatronal() != null ? notificacionDTO.getRegistroPatronal() : "");
			} else {
				parametrosReporte.put("registroPatronal",notificacionDTO.getDesNumRegCpa() != null ? notificacionDTO.getDesNumRegCpa() : "");
			}
			
			parametrosReporte.put("fecInicioPublicacion", notificacionDTO.getFecInicioPublicacionCadena().replaceAll("-", "/"));
			parametrosReporte.put("fecFinPublicacion", notificacionDTO.getFecFinPublicacionCadena().replaceAll("-", "/"));

			for(NeeDocumentosAdjuntos adjunto:listNeeDocumentosAdjuntos){					
				if(adjunto.getNeeCatTipoAdjunto().getCveTipoAdjunto().intValue()==Constantes.TIPO_DOCTO_ADJUNTO.ACUERDO.getDocto()){
					numeroOficio=adjunto.getDesNumOficio();
					parametrosReporte.put("numOficioAcuerdo",adjunto.getDesNumOficio());
				}else if(adjunto.getNeeCatTipoAdjunto().getCveTipoAdjunto().intValue()==Constantes.TIPO_DOCTO_ADJUNTO.DOCUMENTO.getDocto()){
					parametrosReporte.put("numOficioDocumento",adjunto.getDesNumOficio());
				}
			}
			parametrosReporte.put("cadenaOriginalAcuse", generaCadenaOriginal(notificacionDTO,numeroOficio));		
			List<NeeCatTipodocumento> listaTipDoc=null;
			
			if(neeNotificaciones.getNeeCatTipodocumento().getCveTipodocto()!=null){
				listaTipDoc=catTipoDocumentoDAO.getByQuery("FROM NeeCatTipodocumento tipo where tipo.cveTipodocto="+neeNotificaciones.getNeeCatTipodocumento().getCveTipodocto().intValue());
			}
			
			if(!listaTipDoc.isEmpty()){
 				parametrosReporte.put("tipoDocumento",listaTipDoc.get(0).getDesTipodocumento());	
 			}
			
			List<NeeCatProceso>listaProceso=catProcesoDAO.getByQuery("FROM NeeCatProceso  proceso where proceso.cveProceso="+notificacionDTO.getAreaRespNotifDTO().getProcesoDTO().getCveProceso());
			if(!listaProceso.isEmpty()){
				parametrosReporte.put("areaRespNotif", listaProceso.get(0).getDesProceso());
			}
			
			parametrosReporte.put("subject", generarAsuntoRegistro(parametrosReporte));
			parametrosReporte.put("cuerpo", generarCuerpoRegistro(parametrosReporte));
			
			notificacionServiceRemote.generarReportePDF(parametrosReporte, Constantes.TIPO_REPORTE_REGISTRO);
			
			notificacionDTO.setAutorizacionFinalizada(true);
			
		} else {
			notificacionDTO.setAutorizacionFinalizada(false);
		}
		
		return notificacionDTO;
	}
	
	private String generaCadenaOriginal(NotificacionesDTO notificacionDTO,String numeroOficio){
		
		StringBuilder cad=new StringBuilder();
		cad.append("||");
		
//		cad.append("Domicilio|"+notificacionDTO.getDesDomicilio()+"|");
//		cad.append("Fecha Fin Publicacion|"+notificacionDTO.getFecPublicacionCadena()+"|");
//		cad.append("Fecha Inicio Publicacion|"+notificacionDTO.getFecInicioPublicacionCadena()+"|");
		cad.append("Fecha Publicacion|"+notificacionDTO.getFecPublicacionCadena()+"|");
//		cad.append("Fecha Retiro Publicacion|"+notificacionDTO.getFecRetiroPublicacionCadena()+"|");
//		cad.append("Razon Social|"+notificacionDTO.getRazonSocial()+"|");
//		cad.append("Registro Patronal|"+notificacionDTO.getRegistroPatronal()+"|");
//		cad.append("Area Responsable|"+notificacionDTO.getAreaRespNotifDTO().getCveAreaRespNotif());
		cad.append("Nombre Sujeto a Notificar|"+notificacionDTO.getRazonSocial().trim()+"|");
		cad.append("Numero Oficio Acuerdo|"+numeroOficio+"");
		cad.append("||");		
		
		
		
		return cad.toString();
	}
	
	private String generarAsuntoRegistro(Map<String, Object> parametrosReporte) {
		StringBuilder query = new StringBuilder();
		query.append("Registro de notificación de documento "+parametrosReporte.get("numOficioDocumento"));
		return query.toString();
	}
	
	private String generarCuerpoRegistro(Map<String, Object> parametrosReporte) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<html>");
		stringBuilder.append("<body>");
		stringBuilder.append("<div style='width: 95%; background-color: lightgray; padding: 40px;'>");
		stringBuilder.append("<div style='background-color: white; margin: 0px auto; height: auto;'>");
		stringBuilder.append("<table align='center' style='width: 80%'>");
		stringBuilder.append("<tbody>");
		stringBuilder.append("<tr>");
		stringBuilder.append("<td align='center'>");
		stringBuilder.append("<h2 style='font-size:30px;'>Instituto Mexicano del Seguro Social</h2>");
		stringBuilder.append("<br/>");
		stringBuilder.append("</td>");
		stringBuilder.append("</tr>");
		stringBuilder.append("<tr>");
		stringBuilder.append("<td>");
		stringBuilder.append("<p style='font-size: 16px !important; text-align: justify;'>");
		stringBuilder.append("<br/>");
		
		stringBuilder.append(remplazarAcentosHTML((String) parametrosReporte.get("nombreUsuario")));
		stringBuilder.append("<br/>"+remplazarAcentosHTML((String) parametrosReporte.get("cargoUsuario")));
		if (!EstradosStringUtils.isReallyEmptyOrNull((String) parametrosReporte.get("desDelegacion"))) {
			stringBuilder.append("<br/>"+remplazarAcentosHTML((String) parametrosReporte.get("desDelegacion")));
			if (!EstradosStringUtils.isReallyEmptyOrNull((String) parametrosReporte.get("desSubdelegacion"))) {
				stringBuilder.append("<br/>"+remplazarAcentosHTML((String) parametrosReporte.get("desSubdelegacion")));
			}
		} else {
			stringBuilder.append("<br/>"+remplazarAcentosHTML((String) parametrosReporte.get("areaNormativa")));
		}
		stringBuilder.append("<br/><br/>Se informa que su solicitud de publicaci&oacute;n por estrado electr&oacute;nico dirigida a "+remplazarAcentosHTML((String) parametrosReporte.get("razonSocial")));
		stringBuilder.append(", ha sido registrada en el Sistema de Notificaciones por Estrados Electr&oacute;nicos, para publicarse en la p&aacute;gina www.imss.gob.mx");
		stringBuilder.append("<br/><br/>Se adjunta acuse con n&uacute;mero de folio "+remplazarAcentosHTML((String) parametrosReporte.get("tramite"))+", que deber&aacute; imprimir e integrar en el");
		stringBuilder.append(" expediente respectivo conjuntamente con las constancias que se integren para dar cumplimiento");
		stringBuilder.append("a lo establecido en el art&iacute;culo 139 del C&oacute;digo Fiscal de la Federaci&oacute;n.");
		stringBuilder.append("<br/>");
		stringBuilder.append("<br/>");
		
		stringBuilder.append("</p>");
		stringBuilder.append("<br/>");
		stringBuilder.append("</td>");
		stringBuilder.append("</tr>");
		stringBuilder.append("</tbody>");
		stringBuilder.append("</table>");
		stringBuilder.append("</div>");
		stringBuilder.append("</div>");
		stringBuilder.append("</body>");
		stringBuilder.append("</html>");
		return stringBuilder.toString();
	}
	
	public String remplazarAcentosHTML(String stringAcentos) {
		if (!EstradosStringUtils.isReallyEmptyOrNull(stringAcentos)) {
			if (stringAcentos.contains("á")) {
				stringAcentos = stringAcentos.replace("á", "&#225;");
			}
			if (stringAcentos.contains("é")) {
				stringAcentos = stringAcentos.replace("é", "&#233;");
			}
			if (stringAcentos.contains("í")) {
				stringAcentos = stringAcentos.replace("í", "&#237;");
			}
			if (stringAcentos.contains("ó")) {
				stringAcentos = stringAcentos.replace("ó", "&#243;");
			}
			if (stringAcentos.contains("ú")) {
				stringAcentos = stringAcentos.replace("ú", "&#250;");
			}
			if (stringAcentos.contains("ñ")) {
				stringAcentos = stringAcentos.replace("ñ", "&#241;");
			}
			if (stringAcentos.contains("Á")) {
				stringAcentos = stringAcentos.replace("Á", "&#193;");
			}
			if (stringAcentos.contains("É")) {
				stringAcentos = stringAcentos.replace("É", "&#201;");
			}
			if (stringAcentos.contains("Í")) {
				stringAcentos = stringAcentos.replace("Í", "&#205;");
			}
			if (stringAcentos.contains("Ó")) {
				stringAcentos = stringAcentos.replace("Ó", "&#211;");
			}
			if (stringAcentos.contains("Ú")) {
				stringAcentos = stringAcentos.replace("Ú", "&#218;");
			}
			if (stringAcentos.contains("Ñ")) {
				stringAcentos = stringAcentos.replace("Ñ", "&#209;");
			}
		}
		return stringAcentos;
	}
	
	private byte[] tempGeneraArchivo(){
		ByteArrayOutputStream byteArrayOutputStream;
        byteArrayOutputStream = new ByteArrayOutputStream();
		try{
			File file = null;
			InputStream inputStream = null;        
	 
	        file = new File("C:/Users/NOVUTECK1/Documents/testArchivo.txt");
	        inputStream = new FileInputStream(file);
	            
	         byte[] byteArray = new byte[(int) file.length()];
	            byteArrayOutputStream = new ByteArrayOutputStream();
	            
	            int bytesRead;
	            while ((bytesRead = inputStream.read(byteArray)) != -1) {
	            	byteArrayOutputStream.write(byteArray, 0, bytesRead);
	            }
	            
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return byteArrayOutputStream.toByteArray();
	}
	
	public Archivo generaArchivo(byte[] bytes,String nombreArchivo){
			String acusePdf = org.apache.soap.encoding.soapenc.Base64.encode(bytes);
			Archivo archivo=new Archivo();
			archivo.setNombre(nombreArchivo);
			archivo.setBuffer(acusePdf);
			return archivo;
	}
	
	public void guardarArchivoFirmado(String secuenciaNotaria, Archivo archivo) {
		// TODO Auto-generated method stub
		   String jsonParams= "";
           ObjectMapper mapper = new ObjectMapper();
           PeticionGuardadoArchivosFirma peticion = new PeticionGuardadoArchivosFirma();
          
           List<Archivo> archivos = new ArrayList<Archivo>();
           archivos.add(archivo);
          
           peticion.setTramite(secuenciaNotaria);
           peticion.setArchivos(archivos);
          
           try {
                   jsonParams = mapper.writeValueAsString(peticion);
           } catch (JsonGenerationException e) {
        	   logger.error("Error en el parseo a JSON", e);
                   return;
           } catch (JsonMappingException e) {
        	   logger.error("Error en el parseo a JSON", e);
                   return;
           } catch (IOException e) {
        	   logger.error("Error en el parseo a JSON", e);
                   return;
           }
          
           logger.warn("Asi quedo el archivo objeto JSON: " + jsonParams);
          
           ObjectFactory of = new ObjectFactory();
           RegistroSeguimientoRequestType peticionSeguimiento = of.createRegistroSeguimientoRequestType();
           peticionSeguimiento.setJsonParms(jsonParams);
          
           FirmaElectronicaSegService firmaElectronicaSegService = new FirmaElectronicaSegService();
           FirmaElectronicaSegPortType firmaElectronicaSegPortType = firmaElectronicaSegService.getFirmaElectronicaSegPortTypePort();

           RegistroSeguimientoResponseType respuestaPeticion = firmaElectronicaSegPortType.registroSeguimiento(peticionSeguimiento);

           ResultadoType resultado = respuestaPeticion.getResultado();
           
           logger.info("Resultado "+resultado.getCodigo());
           logger.info("Codigo "+resultado.getTexto());
           logger.info("Json "+respuestaPeticion.getJsonSalida());
           logger.info("Salida");
           if(resultado.getCodigo() == 0) {
                   try {
                           RespuestaGuardadoArchivosFirma respuestaGuardado = mapper.readValue(respuestaPeticion.getJsonSalida(), RespuestaGuardadoArchivosFirma.class);
                           logger.info("Archivo Enviado exitosamente");
                           logger.info("Id del documento: "+ respuestaGuardado.getArchivos().get(0).getId());
                           logger.info("Nombre del archivo: " + respuestaGuardado.getArchivos().get(0).getNombre());
                   } catch (JsonParseException e) {
                	   e.printStackTrace();
                	   logger.error("Error en el parseo a JSON", e);
                   } catch (JsonMappingException e) {
                	   e.printStackTrace();
                	   logger.error("Error en el parseo a JSON", e);
                   } catch (IOException e) {
                	   e.printStackTrace();
                	   logger.error("Error en el parseo a JSON", e);
                   }
                   logger.warn("El archivo ha sido guardado correctamente");
           } else {
        	   logger.error("Codigo: " + resultado.getCodigo());
        	   logger.error("Descripcion: " + resultado.getTexto());
           } 
	} 
	
	
	private Date getFechaObjeto(String fecha){
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date fechaDate=null;
		if(fecha!=null && !fecha.equals("")){
			try {
				fechaDate=formatter.parse(fecha);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fechaDate;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public DocumentosAdjuntosDTO guardarArchivo(DocumentosAdjuntosDTO archivo) {
		StringBuilder nombreArchivo = new StringBuilder();
		SsoVwUsuarioDTO ssoVwUsuarioDTO = archivo.getDtSsoVwUsuarioDTO();
		
		String clavePresupuestal = ssoVwUsuarioDTO.getDesClavePresupuestal();
		if (clavePresupuestal != null && clavePresupuestal.length() > 4) {
			clavePresupuestal = clavePresupuestal.substring(clavePresupuestal.length() -4, clavePresupuestal.length());
		} else {
			clavePresupuestal = "0000";
		}
		
		nombreArchivo.append(clavePresupuestal);
		nombreArchivo.append(String.format("%02d", Integer.parseInt(ssoVwUsuarioDTO.getCveDelegacion() != null ? ssoVwUsuarioDTO.getCveDelegacion() : "00")));
		nombreArchivo.append(String.format("%02d", Integer.parseInt(ssoVwUsuarioDTO.getCveSubdelegacion() != null ? ssoVwUsuarioDTO.getCveSubdelegacion() : "00")));
		nombreArchivo.append(archivo.getTipoAdjuntoDTO().getCveTipoAdjunto());
		
		if (archivo.getNotificacionesDTO().getCveNotificaciones() != 0) {
			nombreArchivo.append(archivo.getNotificacionesDTO().getCveNotificaciones());
		} else {
			return null;
		}
		
		nombreArchivo.append("_");
		
		String formatoFecha = "yyyyMMddHHmm";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatoFecha);
		nombreArchivo.append(simpleDateFormat.format(Calendar.getInstance().getTime()) + Constantes.EXTENSION);
		System.out.println(nombreArchivo.toString());
		
		FileOutputStream fos;
		String ruta = "";
		try {
			ruta = Constantes.rutaArchivos+"//"+nombreArchivo.toString();
			fos = new FileOutputStream(ruta);
			fos.write(archivo.getArchivo());
			fos.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			return archivo;
		} catch (IOException ex) {
			ex.printStackTrace();
			return archivo;
		}
		
		//Se elimina registro de la base de datos si es que el usuario ya habia guardado un archivo para esa notificacion.
		if (archivo.getTipoAdjuntoDTO().getCveTipoAdjunto().intValue() != Constantes.TIPO_DOCTO_ADJUNTO.OTROS.getDocto()) {
			String query = "FROM NeeDocumentosAdjuntos adj where adj.neeNotificaciones.cveNotificaciones = "
							+ archivo.getNotificacionesDTO().getCveNotificaciones()+" and " 
					        + " adj.neeCatTipoAdjunto.cveTipoAdjunto = "+archivo.getTipoAdjuntoDTO().getCveTipoAdjunto();
			List<NeeDocumentosAdjuntos> listNeeDocumentosAdjuntos = documentoAdjunto.getByQuery(query);
			if (listNeeDocumentosAdjuntos.size() > 0) {
				documentoAdjunto.eliminar(listNeeDocumentosAdjuntos.get(0));
			}
		}
		
		NeeNotificaciones neeNotificaciones = new NeeNotificaciones();
		neeNotificaciones.setCveNotificaciones(archivo.getNotificacionesDTO().getCveNotificaciones());
		
		NeeCatTipoAdjunto tipoAdjunto = new NeeCatTipoAdjunto();
		tipoAdjunto.setCveTipoAdjunto((archivo.getTipoAdjuntoDTO().getCveTipoAdjunto()));
		
		NeeDocumentosAdjuntos neeDocumentosAdjuntos = new NeeDocumentosAdjuntos();
		neeDocumentosAdjuntos.setDesNombreArchivo(archivo.getDesNombreArchivo());
		neeDocumentosAdjuntos.setDesNumOficio(archivo.getDesNumOficio());
		neeDocumentosAdjuntos.setNeeNotificaciones(neeNotificaciones);
		neeDocumentosAdjuntos.setNeeCatTipoAdjunto(tipoAdjunto);//Viene desde la pantalla 1 = acuerdo, 2 = Documento, 3 = otros
		neeDocumentosAdjuntos.setDesRefFilesystem(Constantes.rutaArchivos+nombreArchivo.toString());
		
		neeDocumentosAdjuntos = documentoAdjunto.saveOrUpdate(neeDocumentosAdjuntos);
		
		archivo.setCveDoctoAdjunto(neeDocumentosAdjuntos.getCveDoctoAdjunto());
		
		return archivo;
	}

	private String generaNombreArchivo(DocumentosAdjuntosDTO archivo){
		StringBuilder nombre=new StringBuilder();
		nombre.append("4003");//valores delegacion y subdelegacion de la session
		return "";
	}
	
//	@Override
//	public List<DocumentosAdjuntosDTO> recuperaListaDocOtrosAdjuntos(
//			NotificacionesDTO notificacion) {
//		// TODO Auto-generated method stub
//		
//		String query="FROM NeeDocumentosAdjuntos adj where adj.neeNotificaciones.cveNotificaciones="+notificacion.getCveNotificaciones()+" and "+" adj.neeCatTipoAdjunto.cveTipoAdjunto="+Constantes.TIPO_DOCTO_ADJUNTO.OTROS.getDocto();
//		List<NeeDocumentosAdjuntos> listaDocs=documentoAdjunto.getByQuery(query);
//		List<DocumentosAdjuntosDTO> lista=new ArrayList<DocumentosAdjuntosDTO>();
//		DocumentosAdjuntosDTO docuDTO=null;
//		for(NeeDocumentosAdjuntos doc:listaDocs){
//			docuDTO=new DocumentosAdjuntosDTO();
//			docuDTO.setCveDoctoAdjunto(doc.getCveDoctoAdjunto());
//			docuDTO.setDesNombreArchivo(doc.getDesNombreArchivo());
////			docuDTO.getNotificacionesDTO().setCveNotificaciones(doc.getNeeNotificaciones().getCveNotificaciones());
//			lista.add(docuDTO);
//		}
//		return lista;
//	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public DocumentosAdjuntosDTO eliminarArchivAdjunto(
			DocumentosAdjuntosDTO documento) {
		// TODO Auto-generated method stub
		String query="FROM NeeDocumentosAdjuntos adj where adj.cveDoctoAdjunto="+documento.getCveDoctoAdjunto()+" and adj.neeNotificaciones.cveNotificaciones="+documento.getNotificacionesDTO().getCveNotificaciones();
		List<NeeDocumentosAdjuntos> lista=documentoAdjunto.getByQuery(query);
		if(lista.size()>0){
			documentoAdjunto.eliminar(lista.get(0));	
		}	
		return documento;
	}

	@Override
	public Date agregaDias(Date fecha,int dias) {
	
		// TODO Auto-generated method stub                   
		  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	      Calendar cal = Calendar.getInstance();
	      List<NeeCatDiasInhabil> diasInhabiles=diasInhabilDAO.getByQuery("FROM NeeCatDiasInhabil dia order by dia.fecFechainhabil ");
		  try {
	       
			cal.setTime(fecha);
			int diasAgregados=0;			
			while(diasAgregados<dias){
				cal.add(Calendar.DATE, 1);
				if(!esDiaInhabil(cal.getTime(),diasInhabiles) && !(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) && !(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)){
					diasAgregados++;
				}
			}
		  } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
		return cal.getTime();
	}
	
	private boolean esDiaInhabil(Date fecha,List<NeeCatDiasInhabil> diasInhabiles){
		
		for(NeeCatDiasInhabil di:diasInhabiles){
			if(di.getFecFechainhabil().compareTo(fecha)==0){
				System.out.println(fecha+" es inhabil ");
				return true;
			}
		}		
		return false;
	}

	@Override
	public SsoVwUsuarioDTO recuperaHeader(String curp) {
		// TODO Auto-generated method stub
		SimpleDateFormat formatoDeFecha = new SimpleDateFormat(Constantes.MASCARA_FECHA);
		
//		curp = "LEGM711002MASCNR01";
		System.out.println("Entra 0 CURP:"+curp);
		List<SsoVwUsuario> listaCurps=vistaUsuario.getByQuery("from SsoVwUsuario user where user.desUsrCURP='"+curp+"'");
		SsoVwUsuario soUsuario=listaCurps.get(0);
		SsoVwUsuarioDTO svDTO=new SsoVwUsuarioDTO();
		
		svDTO.setDesDelegacion(soUsuario.getDesDelegacion());
		svDTO.setDesSubdelegacion(soUsuario.getDesSubdelegacion());
		svDTO.setDesUsrCURP(soUsuario.getDesUsrCURP());	
		svDTO.setCveDelegacion(soUsuario.getCveDelegacion());
		svDTO.setCveSubdelegacion(soUsuario.getCveSubdelegacion());
		svDTO.setFechaSistema(formatoDeFecha.format(Calendar.getInstance().getTime()));
		svDTO.setDesPuesto(soUsuario.getDesPuesto());
		svDTO.setRefCorreoElectronico(soUsuario.getRefCorreoElectronico());
		svDTO.setNomMaterno(soUsuario.getNomMaterno());
		svDTO.setNomPaterno(soUsuario.getNomPaterno());
		svDTO.setNomNombre(soUsuario.getNomNombre());
		svDTO.setRefCorreoElectronico(soUsuario.getRefCorreoElectronico());
		svDTO.setCveSSODepto(soUsuario.getCveSSODepto());
		svDTO.setIdDelegacion(soUsuario.getCveIdDelegacion());
		svDTO.setIdSubdelegacion(soUsuario.getCveIdSubdelegacion());
		svDTO.setCveSsoAreaNorma(soUsuario.getCveSsoAreaNorma());
		svDTO.setDesClavePresupuestal(soUsuario.getDesClavePresupuestal());
		svDTO.setDesAreaNorma(soUsuario.getDesAreaNorma());
		svDTO.setDesDepartamento(soUsuario.getDesDepartamento());
		return svDTO;
	}
	
	/**
	 * Metodo que elimina una notificacion de la base de datos
	 * @param cveNotificaciones
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminarNotificacion(long cveNotificaciones, SsoVwUsuarioDTO ssoVwUsuarioDTO) {
		NeeNotificaciones neeNotificaciones = new NeeNotificaciones();
		neeNotificaciones.setCveNotificaciones(cveNotificaciones);
		String query = "FROM NeeDocumentosAdjuntos adj where adj.neeNotificaciones.cveNotificaciones="+cveNotificaciones;
		List<NeeDocumentosAdjuntos> listNeeDocumentosAdjuntos = documentoAdjunto.getByQuery(query);
		for (NeeDocumentosAdjuntos neeDocumentosAdjuntos : listNeeDocumentosAdjuntos) {
			documentoAdjunto.eliminar(neeDocumentosAdjuntos);
		}
		String queryNotificacion = "FROM NeeNotificaciones not where not.cveNotificaciones = " +cveNotificaciones;
		List<NeeNotificaciones> listNeeNotificaciones = notificacionDAO.getByQuery(queryNotificacion);
		String rasonSocial="";
		if(listNeeNotificaciones.size() > 0) {
			notificacionDAO.eliminar(listNeeNotificaciones.get(0));
			rasonSocial=listNeeNotificaciones.get(0).getRazonSocial();
		}
		String subjectCorreo = "Solicitud de eliminar registro de notificación";
		String cuerpoCorreo = generarCuerpoEliminacion(rasonSocial);
		notificacionServiceRemote.enviarMailEliminacion(ssoVwUsuarioDTO.getRefCorreoElectronico(), subjectCorreo, cuerpoCorreo);
	}
	
	private String generarCuerpoEliminacion(String rasonSocial) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("En atenci&oacute;n a su solicitud de eliminar el registro de notificaci&oacute;n: ");
		stringBuilder.append("<br/>");
		stringBuilder.append("Status(Incompleto/Registrado)");
		stringBuilder.append("<br/>");
		stringBuilder.append("Nombre,denominaci&oacute;n o raz&oacute;n social: "+remplazarAcentosHTML(rasonSocial));
		stringBuilder.append("<br/>");
		stringBuilder.append("Se le informa que ha sido eliminado satisfactoriamente.");
		return stringBuilder.toString();
	}

	@Override
	public DocumentosAdjuntosDTO validaNumeroOficio(DocumentosAdjuntosDTO adjuntosDTO) {		
		String query = "SELECT adj  FROM NeeDocumentosAdjuntos adj, NeeNotificaciones noti "
				+ "where adj.neeNotificaciones.cveNotificaciones = noti.cveNotificaciones and "
				+ "adj.desNumOficio='"+adjuntosDTO.getDesNumOficio()+"' and "
				+ "noti.neeCatSubdelegacion.cveIdSubdelegacion="+adjuntosDTO.getDtSsoVwUsuarioDTO().getIdSubdelegacion();
		List<NeeDocumentosAdjuntos> listNeeDocumentosAdjuntos = documentoAdjunto.getByQuery(query);
		if(!listNeeDocumentosAdjuntos.isEmpty()){
			adjuntosDTO.setCveDoctoAdjunto(listNeeDocumentosAdjuntos.get(0).getCveDoctoAdjunto());
		}else{
			adjuntosDTO.setCveDoctoAdjunto(-1);
		}
		return adjuntosDTO;
		
	}


}
