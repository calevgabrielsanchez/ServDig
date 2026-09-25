package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.web.controller;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.util.DateUtils;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.web.utils.Constants;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Acta;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.ActaTerminoUnionCivil;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.ActaUnionCivil;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Acuerdo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Adimss;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CURP;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CartillaMilitar;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CedulaProfesional;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CertificadoNacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CertificadoSituacionCritica;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.ComprobanteDomicilio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.ConstanciaEstudio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DetalleNivelEducativo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DictamenIntegranteIncapacitado;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.FileUploadVB;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Ife;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Obstetrico;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Pasaporte;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoNivelEducativo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.VigenciaTemporal;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto.ActaComunDTO;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto.ActaNacimientoDTO;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto.ActaTerminoUnionCivilDTO;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto.ActaUnionCivilDTO;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto.AdimssDto;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto.CartillaMilitarDTO;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto.CedulaProfesionalDTO;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto.CertificadoNacimientoDTO;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto.CertificadoSitCriticaDTO;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto.CompDomDto;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto.ConstanciaEstudiosDTO;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto.CurpDTO;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto.DictamenIntegranteIncapacitadoDTO;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto.IfeDto;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto.ObstetricoDTO;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto.PasaporteDTO;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.AutoridadEmisora;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author CEGA
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 10/04/2012
 */
@Controller
@RequestMapping(value = "/documentos/*")
public class CapturaDatosDocsController extends AbstractController {

	public static final int UMF_NOMBRE_TAMANO = 150;
	private static final String RESP_OK = "Se ha guardado correctamente la plantilla";
	@Autowired
	DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;

	/**
	 * envia al formulario correspondiente de acuerdo al id documento
	 * 
	 * @param idDocumento
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/seleccionFormulario", method = RequestMethod.POST)
	public String seleccionFormulario(@RequestParam(value = "idDocumento") Integer idDocumento, @RequestParam(value = "idUmf") Integer idUmf, Model model,
			HttpServletRequest request, HttpSession session) {
		
		String fordward = null;
		String tituloComp="";
		switch (idDocumento) {
		case Constants.TIPO_DOC_CEDULA_PROFESIONAL:
			fordward = Constants.FORDWARD_CEDULA_PROFESIONAL;			
			break;			
		case Constants.TIPO_DOC_ACTA_NACIMIENTO:
			fordward = Constants.FORDWARD_ACTA_NACIMIENTO;
			break;
		case Constants.TIPO_DOC_CARTILLA_MILITAR:
			fordward = Constants.FORDWARD_CARTILLA_MILITAR;
			break;		
		case Constants.TIPO_DOC_CERTIFICADO_NACIMIENTO:
			fordward = Constants.FORDWARD_CERTIFICADO_NACIMIENTO;
			break;	
		case Constants.TIPO_DOC_CONSTANCIA_ESTUDIOS:
			fordward = Constants.FORDWARD_CONSTANCIA_ESTUDIOS;
			break;
		case Constants.TIPO_DOC_CREDENCIAL_ELECTOR:
			fordward = Constants.FORDWARD_CREDENCIAL_ELECTOR;
			break;	
		case Constants.TIPO_DOC_CURP:
			fordward = Constants.FORDWARD_CURP;		
			break;			
		case Constants.TIPO_DOC_PASAPORTE:
			fordward = Constants.FORDWARD_PASAPORTE;	
			break;		
		case Constants.TIPO_CERTIFICADO_SIT_CRITICA:
			fordward = Constants.FORDWARD_CERTIFICADO_SIT_CRITICA;
			break;	
		case Constants.TIPO_DOC_ACUERDO:
			fordward = Constants.FORDWARD_ACUERDO;
			tituloComp=Constants.TITLE_ACUERDO;
			break;
		case Constants.TIPO_DOC_LAUDO:
			fordward = Constants.FORDWARD_ACUERDO;
			tituloComp=Constants.TITLE_LAUDO;
			break;
		case Constants.TIPO_DOC_DICTAMEN_INCAPACITADO:
			fordward = Constants.FORDWARD_DICTAMEN_INCAPACITADO;
			break;	
		case Constants.TIPO_DOC_OBSTETRICO:
			fordward = Constants.FORDWARD_OBSTETRICO;
			break;	
		case Constants.TIPO_DOC_VIGENCIA_TEMPORAL:
		case Constants.TIPO_DOC_PENSION:
			fordward = Constants.FORDWARD_VIGENCIA_TEMPORAL;
			break;
		case Constants.TIPO_DOC_ACTA_MATRIMONIO:
			tituloComp=Constants.TITLE_ACTA_MATRIMONIO;
			fordward = "actaComun";
			break;	
		case Constants.TIPO_DOC_ACTA_DIVORCIO:
			tituloComp=Constants.TITLE_ACTA_DIVORCIO;
			fordward= Constants.FORDWARD_ACTA_COMUN;
			break;
		case Constants.TIPO_DOC_ACTA_ADOPCION:
			tituloComp=Constants.TITLE_ACTA_ADOPCION;
			fordward= Constants.FORDWARD_ACTA_COMUN;
			break;	
		case Constants.TIPO_DOC_ACTA_DEFUNCION:
			tituloComp=Constants.TITLE_ACTA_DEFUNCION;
			fordward= Constants.FORDWARD_ACTA_COMUN;
			break;		
		case Constants.TIPO_DOC_ACTA_MATRIMONIO_DICTAMEN_DIS:
			tituloComp=Constants.TITLE_ACTA_MATRIMONIO_DIC_DIS;
			fordward= Constants.FORDWARD_ACTA_COMUN;
			break;	
		case Constants.TIPO_DOC_ACTA_RECONOCIMIENTO:
			tituloComp=Constants.TITLE_ACTA_RECONOCIMIENTO;
			fordward= Constants.FORDWARD_ACTA_COMUN;
			break;
		case Constants.TIPO_DOC_ESTADO_CUENTA_BANCARIO:
			tituloComp="Estado de cuenta bancario";
			fordward= Constants.FORDWARD_COMPBANTE_DOMICILIO;
			break;
		case Constants.TIPO_DOC_CONTRATO_ARRENDAMIENTO:
			tituloComp="Contrato de arrendamiento";
			fordward= Constants.FORDWARD_COMPBANTE_DOMICILIO;
			break;
		case Constants.TIPO_DOC_RECIBO_TELEVISION:
			tituloComp="Recibo de television";
			fordward= Constants.FORDWARD_COMPBANTE_DOMICILIO;
			break;
		case Constants.TIPO_DOC_PAGO_TENENCIA_VEHICULAR:
			tituloComp="Tenencia vehicular";
			fordward= Constants.FORDWARD_COMPBANTE_DOMICILIO;
			break;
		case Constants.TIPO_DOC_RECIBO_GAS:
			tituloComp="Recibo de gas";
			fordward= Constants.FORDWARD_COMPBANTE_DOMICILIO;
			break;
		case Constants.TIPO_DOC_RECIBO_LUZ:
			tituloComp="Recibo de luz";
			fordward= Constants.FORDWARD_COMPBANTE_DOMICILIO;
			break;
		case Constants.TIPO_DOC_RECIBO_TELEFONO:
			tituloComp="Recibo de telefono";
			fordward= Constants.FORDWARD_COMPBANTE_DOMICILIO;
			break;
		case Constants.TIPO_DOC_ESCRITURA_PROPIEDAD_INMOBILIARIA:
			tituloComp="Escritura de propiedad inmobiliaria";
			fordward= Constants.FORDWARD_COMPBANTE_DOMICILIO;
			break;
		case Constants.TIPO_DOC_RECIBO_AGUA:
			tituloComp="Recibo de agua";
			fordward= Constants.FORDWARD_COMPBANTE_DOMICILIO;
			break;
		case Constants.TIPO_DOC_PAGO_PREDIAL:
			tituloComp="Recibo de pago de predial";
			fordward= Constants.FORDWARD_COMPBANTE_DOMICILIO;
			break;
		case Constants.TIPO_DOC_ADIMSS:
			tituloComp="Credencial ADIMSS";
			fordward= Constants.FORDWARD_ADIMSS;
			break;
		case Constants.TIPO_DOC_ACTA_PACTO_CIVIL:
			tituloComp= "Acta de pacto civil de solidaridad";
			fordward = Constants.FORDWARD_ACTA_PACTO_CIVIL;
			break;
		case Constants.TIPO_DOC_ACTA_UNION_CIVIL:
			tituloComp= "Acta de Uni&oacute;n civil";
			fordward = Constants.FORDWARD_ACTA_UNION_CIVIL;
			break;
		case Constants.TIPO_DOC_ACTA_TERMINO_UNION_CIVIL:
			tituloComp= "Acta de T&eacute;rmino de Uni&oacute;n civil";
			fordward = Constants.FORDWARD_ACTA_TERMINO_UNION_CIVIL;
			break;
		}
		model.addAttribute("tituloComp",tituloComp);
		model.addAttribute("idUmf", idUmf);
		return fordward;
	}

	@RequestMapping(value = "/guardarCedulaProfesional", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> guardarCedulaProfesional(
			@RequestBody CedulaProfesionalDTO cedula, HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> respuesta = new HashMap<String, Object>();
		CedulaProfesional cedulaProfesional=cedula;
		//FECHA DE EXPEDICION
		cedulaProfesional.setFechaExpedicion(DateUtils.dateToDateConFormato(
				cedula.getFechaExpedicionString(), 
		"dd/MM/yyyy"));
		guardarObjetoSession(session, cedulaProfesional);

		respuesta.put("modelo", RESP_OK);
		return respuesta;
	}

	@RequestMapping(value = "/guardarAdimss", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> guardarAdimss(
			@RequestBody AdimssDto adimss, HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> respuesta = new HashMap<String, Object>();

		Adimss adimssO = new Adimss();
		adimssO.setFolio(adimss.getFolio());
		adimssO.setFechaExpedicion(DateUtils.dateToDateConFormato(adimss.getFechaExpedicion(), "dd/MM/yyyy"));
		guardarObjetoSession(session, adimssO);

		respuesta.put("modelo", RESP_OK);

		return respuesta;
	}
	
	@RequestMapping(value = "/guardarActaUnionCivil", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> guardarActaUnionCivil(
			@RequestBody ActaUnionCivilDTO actaUnionCivil, HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
		ActaUnionCivil actaUC =new ActaUnionCivil();
		actaUC.setLugarEmision(actaUnionCivil.getLugarEmision());
		actaUC.setFechaEmision(DateUtils.dateToDateConFormato(actaUnionCivil.getFechaEmision(), "dd/MM/yyyy"));
		
		AutoridadEmisora aut = new AutoridadEmisora ();
		aut.setCveIdAutoridadEmisora(actaUnionCivil.getAutoridadEmisora());
		aut.setDesAutoridadEmisora(actaUnionCivil.getNombreAutoridadEmisora());
		actaUC.setAutoridadEmisora(aut);
		
		EntidadFederativa ent = new EntidadFederativa ();
		ent.setClave(actaUnionCivil.getEntidad());
		ent.setNombre(actaUnionCivil.getNombreEntidad());
		actaUC.setEntidadFederativa(ent);
		
		actaUC.setNoReferencia(actaUnionCivil.getNoReferencia());
		
		guardarObjetoSession(session, actaUC);
		respuesta.put("modelo", RESP_OK);
		return respuesta;
	}
	
	@RequestMapping(value = "/guardarActaTerminoUnionCivil", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> guardarActaTerminoUnionCivil(
			@RequestBody ActaTerminoUnionCivilDTO actaTerminoUnionCivil, HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
		ActaTerminoUnionCivil actaTUC = new ActaTerminoUnionCivil();
		actaTUC.setLugarEmision(actaTerminoUnionCivil.getLugarEmision());
		actaTUC.setFechaEmision(DateUtils.dateToDateConFormato(actaTerminoUnionCivil.getFechaEmision(), "dd/MM/yyyy"));
		
		AutoridadEmisora aut = new AutoridadEmisora ();
		aut.setCveIdAutoridadEmisora(actaTerminoUnionCivil.getAutoridadEmisora());
		aut.setDesAutoridadEmisora(actaTerminoUnionCivil.getNombreAutoridadEmisora());
		actaTUC.setAutoridadEmisora(aut);
		
		EntidadFederativa ent = new EntidadFederativa ();
		ent.setClave(actaTerminoUnionCivil.getEntidad());
		ent.setNombre(actaTerminoUnionCivil.getNombreEntidad());
		actaTUC.setEntidadFederativa(ent);
		
		actaTUC.setNoReferencia(actaTerminoUnionCivil.getNoReferencia());
		
		guardarObjetoSession(session, actaTUC);
		respuesta.put("modelo", RESP_OK);
		log.debug("acta termino union civil prueba");
		return respuesta;
	}

	@RequestMapping(value = "/guardarCartillaMilitar", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> guardarCartillaMilitar(
			@RequestBody CartillaMilitarDTO cartilla, HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> respuesta = new HashMap<String, Object>();

		CartillaMilitar cartillaMilitar=cartilla;
		cartillaMilitar.setFechaExpedicion(DateUtils.dateToDateConFormato(
				cartilla.getFechaExpedicionString(), 
				"dd/MM/yyyy"));
		log.debug("Se guarda la cartilla Militar");
		guardarObjetoSession(session, cartillaMilitar);

		respuesta.put("modelo", RESP_OK);

		return respuesta;
	}


	@RequestMapping(value = "/guardarConstanciaEstudios", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> guardarConstanciaEstudios(
			@RequestBody ConstanciaEstudiosDTO constanciaEstudio,
			HttpServletRequest request, HttpSession session) {
		DetalleNivelEducativo detalleNivelEducativo=null;
		Map<String, Object> respuesta = new HashMap<String, Object>();

		ConstanciaEstudio ce =  new ConstanciaEstudio();
		ce.setClaveEscuela(constanciaEstudio.getClaveEscuela().toUpperCase());
		ce.setGradoEscolar(constanciaEstudio.getGradoEscolar().toUpperCase());
		ce.setNombreEscuela(constanciaEstudio.getNombreEscuela().toUpperCase());
		ce.setNoIncorporacion(constanciaEstudio.getNoIncorporacion().toUpperCase());
		ce.setFechaInicioPeriodo(DateUtils.dateToDateConFormato(
				constanciaEstudio.getFechaInicioPeriodo(), 
		"dd/MM/yyyy"));
		ce.setFechaFinPeriodo(DateUtils.dateToDateConFormato(
				constanciaEstudio.getFechaFinPeriodo(), 
		"dd/MM/yyyy"));
		ce.setFechaExpedicion(DateUtils.dateToDateConFormato(
				constanciaEstudio.getFechaExpedicionString(), 
		"dd/MM/yyyy"));
		//obtiene el id de detalle

		try {
			detalleNivelEducativo=this.documentoProbatorioServiceBusinessRemote.getDetalleNivelEducativo(new Long(constanciaEstudio.getIdTipoNivelEducativo())
			,new Long( constanciaEstudio.getIdNivelEducativo()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DerechohabientesBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		ce.setDetalleNivelEducativo(detalleNivelEducativo);
		guardarObjetoSession(session, ce);

		respuesta.put("modelo", RESP_OK);

		return respuesta;
	}

	@RequestMapping(value = "/guardarCertificadoNacimiento", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> guardarCertificadoNacimiento(
			@RequestBody CertificadoNacimientoDTO certificadoNacimiento,
			HttpServletRequest request, HttpSession session) {
		Map<String, Object> respuesta = new HashMap<String, Object>();

		String fecha = certificadoNacimiento.getFechaAlumbramiento();

		CertificadoNacimiento cn =  new CertificadoNacimiento();
		cn.setFechaAlumbramiento(DateUtils.dateToDateConFormato(fecha,"dd/MM/yyyy"));
		cn.setFechaExpedicion(DateUtils.dateToDateConFormato(certificadoNacimiento.getFechaExpedicion(),"dd/MM/yyyy"));
		cn.setNoFolio(certificadoNacimiento.getNoFolio().toUpperCase());
		cn.setSexo(new Sexo(Integer.valueOf(certificadoNacimiento.getIdSexo())));
		cn.setDesLugarAlumbramiento(certificadoNacimiento.getDesLugarAlumbramiento().toUpperCase());

		guardarObjetoSession(session, cn);

		respuesta.put("modelo", RESP_OK);

		return respuesta;
	}
	@RequestMapping(value = "/guardarPasaporte", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> guardarPasaporte(@RequestBody PasaporteDTO pasaporte,
			HttpServletRequest request, HttpSession session) {
		Map<String, Object> respuesta = new HashMap<String, Object>();

		Pasaporte p =  new Pasaporte();
		p.setNoPasaporte(pasaporte.getNoPasaporte().toUpperCase());
		p.setFechaCaducidad(DateUtils.dateToDateConFormato(
				pasaporte.getFechaCaducidad(),
		"dd/MM/yyyy"));
		p.setFechaExpedicion(DateUtils.dateToDateConFormato(
				pasaporte.getFechaExpedicion(),
		"dd/MM/yyyy"));

		guardarObjetoSession(session, p);

		respuesta.put("modelo", RESP_OK);
		return respuesta;
	}


	@RequestMapping(value = "/guardarActaComun", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> guardarActaMatrimonio(
			@RequestBody ActaComunDTO amDTO, HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> respuesta = new HashMap<String, Object>();

		Acta acta =  new Acta();





		acta.setFechaSuceso(
				DateUtils.dateToDateConFormato(
						amDTO.getFechaSuceso(), 
						"dd/MM/yyyy"));
		acta.setFechaExpedicion(
				DateUtils.dateToDateConFormato(
						amDTO.getFechaExpedicionString(), 
				"dd/MM/yyyy"));
		Municipio m =  new Municipio();
		m.setClave(amDTO.getMunicipio());
		m.setNombre(amDTO.getNombreMunicipio().toUpperCase());
		acta.setMunicipio(m);
		EntidadFederativa entidad = new EntidadFederativa();
		entidad.setClave(amDTO.getEntidad());
		entidad.setNombre(amDTO.getNombreEntidad().toUpperCase());

		acta.getMunicipio().setEntidadFederativa(entidad);

		acta.setNoActa(amDTO.getNoActa().toUpperCase());
		acta.setNoFoja(amDTO.getNoFoja().toUpperCase());
		acta.setNoLibro(amDTO.getNoLibro().toUpperCase());
		acta.setTomo(amDTO.getTomo());
		acta.setNoJuzgado(amDTO.getNoJuzgado().toUpperCase());


		guardarObjetoSession(session, acta);

		respuesta.put("modelo", RESP_OK);

		return respuesta;
	}
	
	
	@RequestMapping(value = "/guardarActaPacto", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> guardarActaPacto(
			@RequestBody ActaComunDTO amDTO, HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> respuesta = new HashMap<String, Object>();

		Acta acta =  new Acta();
		log.debug("se guardaran metadatos" + amDTO);
		
		acta.setFechaSuceso(DateUtils.dateToDateConFormato(amDTO.getFechaSuceso(), "dd/MM/yyyy"));
		acta.setNoActa(amDTO.getNoActa().toUpperCase());
		acta.setNoFoja(amDTO.getNoFoja().toUpperCase());
		acta.setNoLibro(amDTO.getNoLibro().toUpperCase());
		acta.setTomo(amDTO.getTomo());

		guardarObjetoSession(session, acta);

		respuesta.put("modelo", RESP_OK);

		return respuesta;
	}
	

	@RequestMapping(value = "/guardarActaNacimiento", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> guardarActaNacimiento(
			@RequestBody ActaNacimientoDTO anDTO, HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> respuesta = new HashMap<String, Object>();

		Nacimiento actaNacimiento =  new Nacimiento();
		actaNacimiento.setAnio(new Integer(anDTO.getAnio()));
		actaNacimiento.setCrip(anDTO.getCrip().toUpperCase());

		actaNacimiento.setFechaExpedicion(
				DateUtils.dateToDateConFormato(
						anDTO.getFechaSuceso(), 
				"dd/MM/yyyy"));
		Municipio m =  new Municipio();
		m.setClave(anDTO.getMunicipio());
		actaNacimiento.setMunicipio(m);
		EntidadFederativa entidad = new EntidadFederativa();
		entidad.setClave(anDTO.getEntidad());
		actaNacimiento.getMunicipio().setEntidadFederativa(entidad);
		actaNacimiento.setNoActa(anDTO.getNoActa().toUpperCase());
		actaNacimiento.setNoFoja(anDTO.getNoFoja().toUpperCase());
		actaNacimiento.setNoJuzgado(anDTO.getNoJuzgado().toUpperCase());
		actaNacimiento.setNoLibro(anDTO.getNoLibro().toUpperCase());
		actaNacimiento.setTomo(anDTO.getTomo().toUpperCase());


		guardarObjetoSession(session, actaNacimiento);

		respuesta.put("modelo", RESP_OK);

		return respuesta;
	}


	@RequestMapping(value = "/guardarCurp", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> guardarCurp(@RequestBody CurpDTO curp,
			HttpServletRequest request, HttpSession session) {
		Map<String, Object> respuesta = new HashMap<String, Object>();

		CURP c =  new CURP();
		try{
			c.setAnioRegistro(new Long(curp.getAnioRegistro()));
		}catch(NumberFormatException e){
			log.error(e);
		}

		c.setCrip(curp.getCrip().toUpperCase());
		c.setCurp(curp.getCurp().toUpperCase());
		c.setRefFolio(curp.getFolio().toUpperCase());
		c.setNoActa(curp.getNoActa().toUpperCase());
		c.setNoFoja(curp.getNoFoja().toUpperCase());
		c.setNoLibro(curp.getNoLibro().toUpperCase());
		c.setNoTomo(curp.getNoTomo().toUpperCase());

		c.setFechaInscripcion(DateUtils.dateToDateConFormato(
				curp.getFechaInscripcion(), 
				"dd/MM/yyyy"));
		EntidadFederativa entidad = new EntidadFederativa();
		entidad.setClave(curp.getEntidadFederativa());
		Municipio municipio=new Municipio();
		municipio.setClave(curp.getMunicipio());
		municipio.setEntidadFederativa(entidad);

		c.setMunicipio(municipio);

		guardarObjetoSession(session, c);

		respuesta.put("modelo", RESP_OK);

		return respuesta;
	}


	@RequestMapping(value = "/guardarCredencialElector", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> guardarCredencialElector(
			@RequestBody IfeDto credencialElector, HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> respuesta = new HashMap<String, Object>();
		Ife ife = new Ife();
		
		ife.setTipoCredencial(credencialElector.getTipoCredencial());
		ife.setAnioRegistro(credencialElector.getAnioRegistro());
		ife.setClaveElector(credencialElector.getClaveElector().toUpperCase());
		ife.setCodigoSeguridad(credencialElector.getCodigoSeguridad().toUpperCase());
		ife.setEmision(credencialElector.getEmision());

		guardarObjetoSession(session, ife);

		respuesta.put("modelo", RESP_OK);

		return respuesta;
	}


	@RequestMapping(value = "/guardarDictamenIncapacitado", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> guardarDictamenIncapacitado(
			@RequestBody DictamenIntegranteIncapacitadoDTO dii, HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> respuesta = new HashMap<String, Object>();

		DictamenIntegranteIncapacitado dictamen =  new DictamenIntegranteIncapacitado();
		dictamen.setDiagnosticoPadecimiento(dii.getDiagnosticoPadecimiento());

		if(!dii.getIdMedicoEspecialidad().equals("-1")) {
			MedicoFamiliar mf = new MedicoFamiliar();
			mf.setIdMedicoFamiliar(new Long (dii.getMedicoFamiliar()));
			mf.setIdMedicoEspecialidad(new Long (dii.getIdMedicoEspecialidad()));
			mf.setNoMatricula(dii.getMatriculaMed().toUpperCase());
			mf.setNombre(dii.getNombreMed().toUpperCase());
			mf.setPrimerApellido(dii.getPrimerApeidoMed().toUpperCase());
			mf.setSegundoApellido(dii.getSegundoApeidoMed().toUpperCase());
			dictamen.setMedicoFamiliar(mf);
		}
		UnidadMedicaFamiliar umf =  new UnidadMedicaFamiliar();
		umf.setIdUMF(new Long(dii.getUnidadMedicaFamiliar()));
		umf.setNombreCorto(dii.getNombreUMF());
		dictamen.setUnidadMedicaFamiliar(umf);

		dictamen.setFechaInicioEnfermedad(
				DateUtils.dateToDateConFormato(
						dii.getFechaInicioEnfermedad(), 
						"dd/MM/yyyy"));
		dictamen.setFechaExpedicion(
				DateUtils.dateToDateConFormato(
						dii.getFechaExpedicionString(), 
				"dd/MM/yyyy"));

		dictamen.setGradoIncapacidad(dii.getGradoIncapacidad().toUpperCase());
		dictamen.setExisteEstadoIncapacidad(dii.getExisteEstadoIncapacidad());

		dictamen.setDelegacion(new Delegacion());
		dictamen.getDelegacion().setId(new Long(dii.getDelegacion()));
		dictamen.getDelegacion().setDescripcion(dii.getNombreDelegacion().toUpperCase());

		guardarObjetoSession(session, dictamen);

		respuesta.put("modelo", RESP_OK);

		return respuesta;
	}

	@RequestMapping(value = "/guardarObstetrico", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> guardarObstetrico(
			@RequestBody ObstetricoDTO obstetricoDTO, HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> respuesta = new HashMap<String, Object>();

		Obstetrico obstetrico =  new Obstetrico();
		obstetrico.setFechaParto(DateUtils.dateToDateConFormato(
				obstetricoDTO.getFechaParto(), 
		"dd/MM/yyyy"));
		obstetrico.setFechaCertificacionMedico(
				DateUtils.dateToDateConFormato(
						obstetricoDTO.getFechaCertificacionMedico(), 
				"dd/MM/yyyy"));
		obstetrico.setFechaExpedicion(DateUtils.dateToDateConFormato(
				obstetricoDTO.getFechaExpedicion(), 
		"dd/MM/yyyy"));
		obstetrico.setFechaProbableConcepcion(DateUtils.dateToDateConFormato(
				obstetricoDTO.getFechaConcepcion(), 
		"dd/MM/yyyy"));

		if(!obstetricoDTO.getIdMedicoEspecialidad().equals("-1")) {
			MedicoFamiliar mf = new MedicoFamiliar();
			mf.setIdMedicoFamiliar(new Long (obstetricoDTO.getMedicoFamiliar()));
			System.out.println("*****************  getIdMedicoEspecialidad "+obstetricoDTO.getIdMedicoEspecialidad());
			mf.setIdMedicoEspecialidad(new Long (obstetricoDTO.getIdMedicoEspecialidad()));
			mf.setNoMatricula(obstetricoDTO.getMatriculaMed().toUpperCase());
			mf.setNombre(obstetricoDTO.getNombreMed().toUpperCase());
			mf.setPrimerApellido(obstetricoDTO.getPrimerApeidoMed().toUpperCase());
			mf.setSegundoApellido(obstetricoDTO.getSegundoApeidoMed().toUpperCase());
			obstetrico.setMedicoFamiliar(mf);
		}

		guardarObjetoSession(session, obstetrico);

		respuesta.put("modelo", RESP_OK);

		return respuesta;
	}

	@RequestMapping(value = "/guardarCertificadoSitCritica", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> guardarCertificadoSitCritica(
			@RequestBody CertificadoSitCriticaDTO cscDTO, HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> respuesta = new HashMap<String, Object>();

		CertificadoSituacionCritica certificadoSituacionCritica =  new CertificadoSituacionCritica();
		certificadoSituacionCritica.setFechaTerminoIncapacidad(
				DateUtils.dateToDateConFormato(
						cscDTO.getFechaTerminoIncapacidad(), 
				"dd/MM/yyyy"));
		certificadoSituacionCritica.setFechaExpedicion(
				DateUtils.dateToDateConFormato(
						cscDTO.getFechaExpedicionString(), 
				"dd/MM/yyyy"));
		certificadoSituacionCritica.setFechaProbableInicio(
				DateUtils.dateToDateConFormato(
						cscDTO.getFechaProbableInicio(), 
				"dd/MM/yyyy"));
		
		if(!cscDTO.getIdMedicoEspecialidad().equals("-1")) {
			MedicoFamiliar mf = new MedicoFamiliar();
			mf.setIdMedicoFamiliar(new Long (cscDTO.getMedicoFamiliar()));
			mf.setNombre(cscDTO.getNombreMed().toUpperCase());
			mf.setIdMedicoEspecialidad(new Long (cscDTO.getIdMedicoEspecialidad()));
			mf.setPrimerApellido(cscDTO.getPrimerApeidoMed().toUpperCase());
			mf.setSegundoApellido(cscDTO.getSegundoApeidoMed().toUpperCase());
			mf.setNoMatricula(cscDTO.getMatriculaMed().toUpperCase());
			certificadoSituacionCritica.setMedicoFamiliar(mf);
		}

		certificadoSituacionCritica.setEnfermedadPadecida(cscDTO.getEnfermedadPadecida());

		guardarObjetoSession(session, certificadoSituacionCritica);

		respuesta.put("modelo", RESP_OK);

		return respuesta;
	}

	@RequestMapping(value = "/guardarAcuerdo", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> guardarAcuerdo(
			@RequestBody Acuerdo acuerdo, HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> respuesta = new HashMap<String, Object>();

		acuerdo.setFechaExpedicion(DateUtils.dateToDateConFormato(acuerdo.getFechaExpedicionCadena(),"dd/MM/yyyy"));

		guardarObjetoSession(session, acuerdo);

		respuesta.put("modelo", RESP_OK);

		return respuesta;
	}


	@RequestMapping(value = "/guardarVigenciaTemporal", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> guardarVigenciaTemporal(
			@RequestBody VigenciaTemporal vigenciaTemporal, HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> respuesta = new HashMap<String, Object>();
		vigenciaTemporal.setFechaExpedicion(DateUtils.dateToDateConFormato(vigenciaTemporal.getFechaExpedicionCadena(),"dd/MM/yyyy"));
		guardarObjetoSession(session, vigenciaTemporal);

		respuesta.put("modelo", RESP_OK);

		return respuesta;
	}

	@RequestMapping(value = "/guardarCompDom", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> guardaCompDom(
			@RequestBody CompDomDto compDomDto, HttpServletRequest request,
			HttpSession session) {

		ComprobanteDomicilio comprobanteDomicilio=new ComprobanteDomicilio();

		Map<String, Object> respuesta = new HashMap<String, Object>();
		comprobanteDomicilio.setFechaExpedicion(
				DateUtils.dateToDateConFormato(
						compDomDto.getFechaExpedicionString(), 
				"dd/MM/yyyy"));
		comprobanteDomicilio.setFolio(compDomDto.getFolio().toUpperCase());

		guardarObjetoSession(session, comprobanteDomicilio);
		respuesta.put("modelo", RESP_OK);

		return respuesta;
	}
	@RequestMapping("/getMedicos") 
	public @ResponseBody List<MedicoFamiliar> getMedicos() { 
		List<MedicoFamiliar> medicos=null;
		try {
			medicos= documentoProbatorioServiceBusinessRemote.getAllMedicos();
		} catch (DerechohabientesBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		 
		return medicos;
	} 	 


	@RequestMapping("/getMedicosByUmf/") 
	public @ResponseBody List<MedicoFamiliar> getMedicosByUmf(
			@RequestParam(value = "idUmf") Long idUmf) { 
		List<MedicoEnTurno> medicos=null;
		List<MedicoFamiliar> medicoFamiliarList=null;
		try {

			medicos= documentoProbatorioServiceBusinessRemote.medicoEnTurnos(idUmf);
			if(medicos != null && medicos.size() > 0 ){

				medicoFamiliarList = new ArrayList<MedicoFamiliar>();

				for(MedicoEnTurno m : medicos){
					
					// ----------------------------------------------------------------
					// Devolv�a valores nulos y el combo de medicos no se renderizaba
					// ----------------------------------------------------------------
					if( (m != null) && (m.getMedicoFamiliar() != null) )
						medicoFamiliarList.add(m.getMedicoFamiliar());
				}
			}


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		 
		return medicoFamiliarList;
	} 	 


	@RequestMapping("/getNivelEducativo") 
	public @ResponseBody List<TipoNivelEducativo> getNivelEducativo() { 
		List<TipoNivelEducativo> salida=null; 
		try {
			salida= documentoProbatorioServiceBusinessRemote.getAllTipoNivelEducativos();
		} catch (DerechohabientesBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return salida;
	} 



	private void guardarObjetoSession(HttpSession session, Object objeto){
		FileUploadVB fu =
			(FileUploadVB)session.getAttribute(FileUploadVB.SES_NAME);
		fu.setCaptura(objeto);
		fu.setLoadedDocform(true);
	}

}