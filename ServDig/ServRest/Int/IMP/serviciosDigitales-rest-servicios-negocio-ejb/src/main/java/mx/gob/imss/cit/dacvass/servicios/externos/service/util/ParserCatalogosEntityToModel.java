package mx.gob.imss.cit.dacvass.servicios.externos.service.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.ErrorResponseBean;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.MunicipioImss;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.MunicipioInegi;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.EntidadFederativa;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.EstadoCivil;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Pais;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Parentesco;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Sexo;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Turno;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.AccNivelAtencion;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DgCatEstado;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DgCatMunicipio;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicCalidadParentesco;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicClase;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicClavePresupuestal;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicDelegacion;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicDiasFestivo;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicDivision;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicEstadoCivil;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicFraccion;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicFraccionClase;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicGrupo;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicModalidad;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicMunicipioImss;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicPai;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicSexo;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicSubdelegacion;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicTipoPersona;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicTipoUmf;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicTurno;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.cache.catalogo.DicUmf;
import mx.gob.imss.ctirss.delta.model.derechohabiente.ClavePresupuestal;
import mx.gob.imss.ctirss.delta.model.derechohabiente.NivelAtencion;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoUMF;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clase;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.digital.modelo.domicilio.Delegacion;
import mx.gob.imss.digital.modelo.domicilio.Subdelegacion;

@Resource
public class ParserCatalogosEntityToModel {

	private static final Logger LOG = LoggerFactory
            .getLogger(ParserCatalogosEntityToModel.class);
	

	public static Sexo parserSexoRest (DicSexo dicSexo) throws ServiciosRestException {
		validaObjetoNulo(dicSexo);
		
		Sexo sexoRest = new Sexo();
		try {
			sexoRest.setDescripcion(dicSexo.getDesSexo());
			sexoRest.setIdSexo(dicSexo.getCveIdSexo());
			return  sexoRest;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto sexo" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto Sexo", e.getMessage()));
		}
	}
	
	public static List<Sexo> parserSexoListRestList (List<DicSexo> sexoImssDigitalList) throws ServiciosRestException {
		validaListaNulaVacia(sexoImssDigitalList);
		List<Sexo> sexoRestList = new ArrayList<Sexo>();
		try {
			for (DicSexo sexoIt : sexoImssDigitalList) {
				sexoRestList.add(parserSexoRest(sexoIt));
			   }	
		return  sexoRestList;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear la lista de sexo" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear la lista de Sexo", e.getMessage()));
		}
		
	}
	
	
	public static Pais parserPaisRest (DicPai paisImssDigital) throws ServiciosRestException {
		validaObjetoNulo(paisImssDigital);
		
		Pais paisRest = new Pais();
		try {
			paisRest.setDescripcion(paisImssDigital.getDesPais());
			paisRest.setIdPais((int)paisImssDigital.getCveIdPais());
			paisRest.setNacionalidad(paisImssDigital.getDesNacionalidad());
			return  paisRest;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto Pais" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto Pais", e.getMessage()));
		}
	}
	
	public static List<Pais> parserPaisListRest (List<DicPai> paisImssDigitalList) throws ServiciosRestException {
		validaListaNulaVacia(paisImssDigitalList);
		
		List<Pais> paisRestList = new ArrayList<Pais>();
		try {
			for (DicPai paisIt : paisImssDigitalList) {
				paisRestList.add(parserPaisRest(paisIt));
			   }	
		return  paisRestList;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto Pais" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto Pais list", e.getMessage()));
		}
		
	}
	
	public static Parentesco parserParentescoRest (DicCalidadParentesco parentescoImssDigital) throws ServiciosRestException {
		validaObjetoNulo(parentescoImssDigital);
		
		Parentesco parentescoRest = new Parentesco();
		try {
			parentescoRest=new Parentesco();
			parentescoRest.setIdParentesco(parentescoImssDigital.getCveIdCalidadParentesco());
			parentescoRest.setDescripcion(parentescoImssDigital.getDesParentesco());
			parentescoRest.setDescripcionFemenina(parentescoImssDigital.getDesTipificacionFemenina());
			parentescoRest.setDescripcionMasculina(parentescoImssDigital.getDesTipificacionMasculina());
			return  parentescoRest;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto Parentesco" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto Parentesco", e.getMessage()));
		}
	}
	
	public static List<Parentesco> parserParentescoListRest (List<DicCalidadParentesco> parentescoImssDigitalList) throws ServiciosRestException {
		validaListaNulaVacia(parentescoImssDigitalList);
		
		List<Parentesco> parentescoRestList = new ArrayList<Parentesco>();
		try {
			for (DicCalidadParentesco parentescoIt : parentescoImssDigitalList) {
				parentescoRestList.add(parserParentescoRest(parentescoIt));
		   }	
		return  parentescoRestList;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto Parentesco list" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto Parentesco list", e.getMessage()));
		}
		
	}
	
	
	public static EntidadFederativa parserEntidadFederativaRest (DgCatEstado entidadFederativaImssDigital) throws ServiciosRestException {
		validaObjetoNulo(entidadFederativaImssDigital);
		
		EntidadFederativa entidadFederativaRest = new EntidadFederativa();
		try {
			entidadFederativaRest.setClave(entidadFederativaImssDigital.getCveEnt());
			entidadFederativaRest.setNombre(entidadFederativaImssDigital.getNomEnt());
			return  entidadFederativaRest;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto Entidad Federativa" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto Entidad Federativa",  e.getMessage()));
		}
	}
	
	public  static List<EntidadFederativa> parserEntidadFederativaListRest (List<DgCatEstado> entidadFedImssDigitalList) throws ServiciosRestException {
		validaListaNulaVacia(entidadFedImssDigitalList);
		
		List<EntidadFederativa> entidadFederativaRestList = new ArrayList<EntidadFederativa>();
		try {
			for (DgCatEstado entidadFedIt : entidadFedImssDigitalList) {
				entidadFederativaRestList.add(parserEntidadFederativaRest(entidadFedIt));
			   }	
		return  entidadFederativaRestList;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto Entidad Federativa list" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto Entidad Federativa list",  e.getMessage()));
		}
		
	}
	
	public static EstadoCivil parserEdoCivilRest (DicEstadoCivil estadoCivilImssDigital) throws ServiciosRestException {
		validaObjetoNulo(estadoCivilImssDigital);
		
		EstadoCivil edoCivilRest = new EstadoCivil();
		try {
			edoCivilRest.setDescripcion(estadoCivilImssDigital.getDesEstadoCivil());
			edoCivilRest.setIdEstadoCivil(estadoCivilImssDigital.getCveIdEstadoCivil().intValue());
			return  edoCivilRest;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto Entidad Estado civil" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto  Estado civil", e.getMessage()));
		}
	}
	
	public static List<EstadoCivil> parserEdoCivilListRest (List<DicEstadoCivil> parentescoImssDigitalList) throws ServiciosRestException {
		validaListaNulaVacia(parentescoImssDigitalList);
		
		List<EstadoCivil> edoCivilRestList = new ArrayList<EstadoCivil>();
		try {
			for (DicEstadoCivil edoCivilIt : parentescoImssDigitalList) {
				edoCivilRestList.add(parserEdoCivilRest(edoCivilIt));
			   }	
		return  edoCivilRestList;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto  edoCivilRest list" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto  Estado civil list", e.getMessage()));
		}
		
	}
	
	public static Turno  parserTurnoRest (DicTurno turnoImssDigital) throws ServiciosRestException {
		validaObjetoNulo(turnoImssDigital);
		
		Turno turnoRest = new Turno();
		try {
			turnoRest.setIdTurno(turnoImssDigital.getCveIdTurno());
			turnoRest.setDescripcion(turnoImssDigital.getDesDescripcion());
			return  turnoRest;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto turno" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto turno",  e.getMessage()));
		}
	}
	
	public static List<Turno> parserTurnoListRest (List<DicTurno> turnoImssDigitalList) throws ServiciosRestException {
		validaListaNulaVacia(turnoImssDigitalList);
		
		List<Turno> trunoRestList = new ArrayList<Turno>();
		try {
			for (DicTurno turnoIt : turnoImssDigitalList) {
				trunoRestList.add(parserTurnoRest(turnoIt));
			   }	
		return  trunoRestList;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto  truno tList" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto turno tList",  e.getMessage()));
		}
		
	}
	
	
	public static Delegacion  parserDelegacionRest (DicDelegacion delegacionImssDigital) throws ServiciosRestException {
		validaObjetoNulo(delegacionImssDigital);
		
		Delegacion delegacionRest = new Delegacion();
		try {
			delegacionRest.setCiz(delegacionImssDigital.getCveCiz());
			delegacionRest.setClave(delegacionImssDigital.getClaveDelegacion());
			delegacionRest.setDescripcion(delegacionImssDigital.getDesDeleg());
			delegacionRest.setId(delegacionImssDigital.getCveIdDelegacion());
			return  delegacionRest;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto De�egacion" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto Delegacion", e.getMessage()));
		}
	}
	
	public static List<Delegacion> parserDelegacionListRest (List<DicDelegacion> delegacionDeltaList) throws ServiciosRestException {
		validaListaNulaVacia(delegacionDeltaList);
		
		List<Delegacion> delegacionRestList = new ArrayList<Delegacion>();
		try {
			for (DicDelegacion delegacionIt : delegacionDeltaList) {
				delegacionRestList.add(parserDelegacionRest(delegacionIt));
			   }	
			return delegacionRestList;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto  delegacion List" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto Delegacion lsit", e.getMessage()));
		}
		
	}
	
	public static Subdelegacion  parserSubDelegacionRest (DicSubdelegacion subDelegacionImssDigital) throws ServiciosRestException {
		validaObjetoNulo(subDelegacionImssDigital);
		
		Subdelegacion subDelegacionRest = new Subdelegacion();
		Delegacion delegacionRest = null;
		try {
			delegacionRest = parserDelegacionRest(subDelegacionImssDigital.getDicDelegacion());
			subDelegacionRest.setDelegacion(delegacionRest);
			subDelegacionRest.setClave(subDelegacionImssDigital.getClaveSubdelegacion());
			subDelegacionRest.setDescripcion(subDelegacionImssDigital.getDesSubdelegacion());
			subDelegacionRest.setId(subDelegacionImssDigital.getCveIdSubdelegacion());
			return  subDelegacionRest;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto SubDelegacion" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto SubDelegacion", e.getMessage()));
		}
	}
	
	public static List<Subdelegacion> parserSubDelegacionListRest (List<DicSubdelegacion> subDelegacionDeltaList) throws ServiciosRestException {
		validaListaNulaVacia(subDelegacionDeltaList);
		
		List<Subdelegacion> subDelegacionRestList = new ArrayList<Subdelegacion>();
		try {
			for (DicSubdelegacion subDelegacionIt : subDelegacionDeltaList) {
				subDelegacionRestList.add(parserSubDelegacionRest(subDelegacionIt));
			   }	
			return  subDelegacionRestList;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto  subdelegacion list" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto subdelegacion list", e.getMessage()));
		}
		
	}
	
	public static UnidadMedicaFamiliar parserUmfEnityToModel(DicUmf dicUmf) throws ServiciosRestException{
		validaObjetoNulo(dicUmf);
		UnidadMedicaFamiliar salida =new UnidadMedicaFamiliar();		
			try {
				String direccionUmf = "";
				salida=new UnidadMedicaFamiliar();
				salida.setIdUMF(dicUmf.getCveIdUmf());
				salida.setDescripcion(dicUmf.getNomUnidad());
				salida.setNombreCorto(dicUmf.getNomCorto());
				salida.setGeneracionCita(dicUmf.getIndGeneracionCita());
				salida.setNoConsultorio(dicUmf.getNumConsultorio());
				salida.setNoEconomico(dicUmf.getNumEconom());
				Subdelegacion subdelegacion = parserSubDelegacionRest(dicUmf.getDicSubdelegacion());
				mx.gob.imss.ctirss.delta.model.domicilio.Delegacion delegacionSD = new mx.gob.imss.ctirss.delta.model.domicilio.Delegacion();
				mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion subDelegacionSD = new mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion();
				BeanUtils.copyProperties(subdelegacion.getDelegacion(),delegacionSD);
				BeanUtils.copyProperties(subdelegacion, subDelegacionSD, new String[]{"delegacion"});
				subDelegacionSD.setDelegacion(delegacionSD);
				salida.setSubdelegacion(subDelegacionSD);
				salida.setClavePresupuestal(clavePresupuestaEntityToModel(dicUmf.getDicClavePresupuestal()));
				salida.setNivelAtencion(nivelAtencionEntityToModel(dicUmf.getAccNivelAtencion()));
				salida.setTipoUMF(tipoUmfEntityToModel(dicUmf.getDicTipoUmf()));	
				salida.setIndUmfCfe(dicUmf.getIndUmfCfe());
				direccionUmf = StringUtils.isNotBlank(dicUmf.getDomCalle()) ? dicUmf.getDomCalle() : "";
				salida.setDesDireccion(StringUtils.isBlank(direccionUmf)? "Direccion no disponible" : direccionUmf);
				salida.setLatitud(dicUmf.getLatitud());
				salida.setLongitud(dicUmf.getLongitud());
				return salida;
			} catch (Exception e) {
				LOG.error("ocurio un erro al parsear el objeto umf " , e);
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
						"Ocurrio un error la parsear el objeto umf ", e.getMessage()));
			}
	}
	
	public static List<UnidadMedicaFamiliar> parserUmfEnityToModelList(List<DicUmf> entrada) throws ServiciosRestException{
		validaListaNulaVacia(entrada);
		List<UnidadMedicaFamiliar> umfList = new ArrayList<UnidadMedicaFamiliar>();
		try {
			for (DicUmf dicUmf : entrada) {
				umfList.add(parserUmfEnityToModel(dicUmf));
			   }	
			return  umfList;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto umf list" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto umf list", e.getMessage()));
		}
		
	}
	
	
	
	
	public static void validaObjetoNulo(Object obj) throws ServiciosRestException {
		if( obj == null) {
		
				LOG.error("no se encontro el registro en el catalgo");
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
						"El valor del ID no se encuentra en el catalogo", "El valor del ID no se encuentra en el catalogo"), new Exception("El valor del ID no se encuentra en el catalogo"));
		}
		
	}
		
	@SuppressWarnings("rawtypes")
	public static void validaListaNulaVacia(List objLista) throws ServiciosRestException {
		if( objLista == null || objLista.isEmpty()) {
				LOG.error("la lista del catalogo llego nula");
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
						"No se encontro informaci�n para el catalogo", "No se encontro informaci�n para el catalogo"), new Exception("No se encontor informaci�n para el catalogo"));
		}
		
	}
	
	public static ClavePresupuestal clavePresupuestaEntityToModel(DicClavePresupuestal entrada) throws ServiciosRestException{
		ClavePresupuestal salida=null;
		if(entrada!=null){
			try {
				salida=new ClavePresupuestal();
				salida.setIdClavePresupuestal(entrada.getCveIdClavePresupuestal());
				salida.setClavePresupuestal(entrada.getCvePresupuestal());
				salida.setDescripcion(entrada.getDesClavePresupuestal());
			} catch (Exception e) {
				LOG.error("ocurio un erro al parsear el objeto clavePresupuesta " , e);
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
						"Ocurrio un error la parsear el objeto clavePresupuesta", e.getMessage()));
			}
			
		}
		return salida;
	}
	
	public static NivelAtencion nivelAtencionEntityToModel(AccNivelAtencion entrada) throws ServiciosRestException{
		NivelAtencion salida=null;
		if(entrada!=null){
			try {
				salida=new NivelAtencion();
				salida.setIdNivelAtencion(new Long(entrada.getCveNivelAtencion()));
			} catch (Exception e) {
				LOG.error("ocurio un erro al parsear el objeto NivelAtencion " , e);
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
						"Ocurrio un error la parsear el objeto NivelAtencion", e.getMessage()));
			}
			
		}
		return salida;
	}
	
	public static TipoUMF tipoUmfEntityToModel(DicTipoUmf entrada) throws ServiciosRestException {
		TipoUMF salida=null;
		if(entrada!=null){
			try {
				salida=new TipoUMF();
				salida.setIdTipoUMF(new BigInteger(String.valueOf(entrada.getCveIdTipoUmf())));
				salida.setDescripcion(entrada.getDesTipoUmf());
			} catch (Exception e) {
				LOG.error("ocurio un erro al parsear el objeto  TipoUmf " , e);
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
						"Ocurrio un error la parsear el objeto TipoUmf", e.getMessage()));
			}
			
		}
		return salida;
	}
	
	public static Modalidad parserModalidadEntityToModel(DicModalidad entity)throws ServiciosRestException{
		validaObjetoNulo(entity);
		try{
			Modalidad model = new Modalidad();
			model.setIdModalidad(entity.getCveIdModalidad());
			model.setNumModalidad(entity.getNumModalidad());
			model.setDescripcion(entity.getDesModalidad());
			model.setSiglaAgregadoMedico(entity.getSiglaAgregadoMedico());
			model.setDesCorta(entity.getDesNomModalidadCorto());
			return model;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto modalidad" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto modalidad", e.getMessage()));
		}
	}
	
	public static List<Modalidad> parserModalidadEntityToModelList(List<DicModalidad> entradaList) throws ServiciosRestException{
		validaListaNulaVacia(entradaList);
		List<Modalidad> salidaList = new ArrayList<Modalidad>();
		try {
			for (DicModalidad entrada : entradaList) {
				salidaList.add(parserModalidadEntityToModel(entrada));
			   }	
			return  salidaList;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto modalidad list" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto modalidad list", e.getMessage()));
		}
		
	}
	
	public static TipoPersona parserTipoPersonaEntityToModel(DicTipoPersona entity)throws ServiciosRestException{
		validaObjetoNulo(entity);
		try{
			TipoPersona model = new TipoPersona();
			model.setIdTipoPersona(entity.getCveIdTipoPersona());
			model.setDescripcion(entity.getDesTipoPersona());
			return model;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto TipoPersona" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto TipoPersona", e.getMessage()));
		}
	}
	
	public static List<TipoPersona> parserTipoPersonaEntityToModelList(List<DicTipoPersona> entradaList) throws ServiciosRestException{
		validaListaNulaVacia(entradaList);
		List<TipoPersona> salidaList = new ArrayList<TipoPersona>();
		try {
			for (DicTipoPersona entrada : entradaList) {
				salidaList.add(parserTipoPersonaEntityToModel(entrada));
			   }	
			return  salidaList;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto TipoPersona list" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto TipoPersona list", e.getMessage()));
		}	
	}
	
	public static Clase parserClaseEntityToModel(DicClase entity)throws ServiciosRestException{
		validaObjetoNulo(entity);
		try{
		    Clase model = new Clase();
			model.setClave(entity.getCveIdClase());
			model.setDescripcion(entity.getDesClase().toUpperCase());
			return model;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto Clase" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto Clase", e.getMessage()));
		}
	}
	
	public static List<Clase> parserClaseEntityToModelList(List<DicClase> entradaList) throws ServiciosRestException{
		validaListaNulaVacia(entradaList);
		List<Clase> salidaList = new ArrayList<Clase>();
		try {
			for (DicClase entrada : entradaList) {
				salidaList.add(parserClaseEntityToModel(entrada));
			   }	
			return  salidaList;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto Clase list" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto Clase list", e.getMessage()));
		}	
	}
	
	public static Fraccion parserFraccionEntityToModel(DicFraccion entity)throws ServiciosRestException{
		validaObjetoNulo(entity);
		try{
			Fraccion model = new Fraccion();
			DicClase dicClase = entity.getDicFraccionClases().get(0).getDicClase();
			model.setClase(parserClaseEntityToModel(dicClase));
			model.setPrimaSRT(dicClase.getNumPrimaMedia());
			model.setId(entity.getCveIdFraccion());
			model.setDescripcion(entity.getDesFraccion().toUpperCase());
			model.setDescripcionDetallada(entity.getDesActividad().toUpperCase());
			model.setGrupo(parserGrupoEntityToModel(entity.getDicGrupo()));
			model.setNumFraccion(entity.getNumFraccion());
			return model;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto Clase" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto Clase", e.getMessage()));
		}
	}
	
	/**
	 * Metodo que setea la informa�i�n 
	 * @param entity
	 * @return
	 * @throws ServiciosRestException
	 */
	public static Fraccion parserFraccionClaseEntityToModel(DicFraccionClase dicFraccionClase)throws ServiciosRestException{
		validaObjetoNulo(dicFraccionClase);
		try{
			Fraccion model = new Fraccion();
			DicClase dicClase = dicFraccionClase.getDicClase();
			DicFraccion entity = dicFraccionClase.getDicFraccion();
			model.setClase(parserClaseEntityToModel(dicClase));
			model.setPrimaSRT(dicClase.getNumPrimaMedia());
			model.setId(entity.getCveIdFraccion());
			model.setDescripcion(entity.getDesFraccion().toUpperCase());
			model.setDescripcionDetallada(entity.getDesActividad().toUpperCase());
			model.setGrupo(parserGrupoEntityToModel(entity.getDicGrupo()));
			model.setNumFraccion(entity.getNumFraccion());
			return model;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto FraccionClase" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto FraccionClase", e.getMessage()));
		}
	}
	
	public static List<Fraccion> parserFraccionEntityToModelList(List<DicFraccion> entradaList) throws ServiciosRestException{
		validaListaNulaVacia(entradaList);
		List<Fraccion> salidaList = new ArrayList<Fraccion>();
		try {
			for (DicFraccion entrada : entradaList) {
				salidaList.add(parserFraccionEntityToModel(entrada));
			   }	
			return  salidaList;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto fraccion list" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto fraccion list", e.getMessage()));
		}	
	}
	
	/**
	 * Metodo para setear la informaci�n de la clase activa que no tiene fecha de fin 
	 * @param entradaList
	 * @return
	 * @throws ServiciosRestException
	 */
	public static List<Fraccion> parserFraccionClaseEntityToModelList(List<DicFraccionClase> dicFraccionClaseList) throws ServiciosRestException{
		validaListaNulaVacia(dicFraccionClaseList);
		List<Fraccion> salidaList = new ArrayList<Fraccion>();
		try {
			for (DicFraccionClase entrada : dicFraccionClaseList) {
				salidaList.add(parserFraccionClaseEntityToModel(entrada));
			   }	
			return  salidaList;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto fraccionClase list" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto fraccionClase list", e.getMessage()));
		}	
	}
	

    public static Grupo parserGrupoEntityToModel(DicGrupo dicGrupo) throws ServiciosRestException {
    	validaObjetoNulo(dicGrupo);
    	try{
         Grupo grupo = new Grupo();
        grupo.setId(dicGrupo.getCveIdGrupo());
        grupo.setDescripcion(dicGrupo.getDesGrupo().toUpperCase());
        grupo.setDivision(parserDivisionEntityToModel(dicGrupo.getDicDivision()));
        grupo.setNumGrupo(dicGrupo.getNumGrupo());
        return grupo;
    	}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto grupo" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto grupo", e.getMessage()));
		}
    }
    
    public static List<Grupo> parserGrupoEntityToModelList(List<DicGrupo> entradaList) throws ServiciosRestException{
		validaListaNulaVacia(entradaList);
		List<Grupo> salidaList = new ArrayList<Grupo>();
		try {
			for (DicGrupo entrada : entradaList) {
				salidaList.add(parserGrupoEntityToModel(entrada));
			   }	
			return  salidaList;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto grupo list" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto grupo list", e.getMessage()));
		}	
	}
	
    
   	public static  Division parserDivisionEntityToModel ( DicDivision dicDivision) throws ServiciosRestException{
   		validaObjetoNulo(dicDivision);
   		try{
   		 Division division = new Division();
   		division.setId(dicDivision.getCveIdDivision());
   		division.setDescripcion(dicDivision.getDesDivision().toUpperCase());
   		division.setNumDivision(dicDivision.getNumDivision());
   		return division;
   		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto division" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto division ", e.getMessage()));
		}
   	}
   	
    public static List<Division> parserDivisionEntityToModelList(List<DicDivision> entradaList) throws ServiciosRestException{
		validaListaNulaVacia(entradaList);
		List<Division> salidaList = new ArrayList<Division>();
		try {
			for (DicDivision entrada : entradaList) {
				salidaList.add(parserDivisionEntityToModel(entrada));
			   }	
			return  salidaList;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto division list" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto division list", e.getMessage()));
		}	
	}
    
 	public static  MunicipioInegi parserMunicipioInegiEntityToModel ( DgCatMunicipio entity) throws ServiciosRestException{
   		validaObjetoNulo(entity);
   		try{
   		MunicipioInegi model = new MunicipioInegi();
   		model.setCveMunicipio(entity.getId().getCveMun());
   		model.setCveEntidadFed(entity.getDgCatEstado().getCveEnt());
   		model.setNomMunicipio(entity.getNomMun());
   		return model;
   		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto municipio inegi" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto municipio inegi ", e.getMessage()));
		}
   	}
 	
 	 public static List<MunicipioInegi> parserMunicipioInegiEntityToModelList(List<DgCatMunicipio> entradaList) throws ServiciosRestException{
 		validaListaNulaVacia(entradaList);
 		List<MunicipioInegi> salidaList = new ArrayList<MunicipioInegi>();
 		try {
 			for (DgCatMunicipio entrada : entradaList) {
 				salidaList.add(parserMunicipioInegiEntityToModel(entrada));
 			   }	
 			return  salidaList;
 		}catch (Exception e ) {
 			LOG.error("ocurio un erro al parsear el objeto municipio list" , e);
 			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
 					"Ocurrio un error la parsear el objeto municipio list", e.getMessage()));
 		}	
 	}
 	 

 	public static List<Date> parserDiaFestivoEntityToModelList(List<DicDiasFestivo> entradaList) throws ServiciosRestException{
 		validaListaNulaVacia(entradaList);
 		List<Date> salidaList= new ArrayList<Date>();
 			try {
 				for(DicDiasFestivo salida:entradaList){
 					salidaList.add(salida.getFecDiaFestivo());
 				}
 			}catch (Exception e ) {
 	 			LOG.error("ocurio un erro al parsear el objeto Dia festivo list" , e);
 	 			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
 	 					"Ocurrio un error la parsear el objeto Dia festivo list", e.getMessage()));
 	 		}			
 		return salidaList;
 	}
    
	public static MunicipioImss parserMunicipioImssEntityToModel (DicMunicipioImss entity) throws ServiciosRestException {
		
		ValidacionesComunesUtil.validaObjetoRespuestaNulo(entity, "La consulta de municipio IMSS no econtro registros");
		try {
			MunicipioImss model = new MunicipioImss();
			EntidadFederativa estado = new EntidadFederativa();
	   		model.setIdMunicipioImss(entity.getCveIdMunicipioImss());
	   		model.setCveMunicipioImss(entity.getCveMunicipio());
	   		model.setNomMunicipioImss(entity.getNomMunicipioImss());
	   		estado.setClave(entity.getDgCatEstado().getCveEnt());
	   		estado.setNombre(entity.getDgCatEstado().getNomEnt());
	   		model.setEntidadFederativa(estado);
			return model;
	 	}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el municipio IMSS" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error al parsear el municipio IMSS", e.getMessage()));
		}
		
	}
	
	public static List<MunicipioImss> parserMunicipioImssEntityToModelList (List<DicMunicipioImss> entradaList) throws ServiciosRestException {
		validaListaNulaVacia(entradaList);
		try {
			List<MunicipioImss> modelList = new ArrayList<MunicipioImss>();
			for(DicMunicipioImss muniBD: entradaList) {
				modelList.add(parserMunicipioImssEntityToModel(muniBD));
			}
			return modelList;
	 	}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el municipio IMSS" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error al parsear el municipio IMSS", e.getMessage()));
		}
		
	}
 	
 	
}
