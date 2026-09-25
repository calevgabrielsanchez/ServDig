package mx.gob.imss.cit.dacvass.servicios.externos.service.util;



import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.ErrorResponseBean;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.EntidadFederativa;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.EstadoCivil;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Pais;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Parentesco;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Sexo;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Turno;
import mx.gob.imss.digital.modelo.domicilio.Delegacion;
import mx.gob.imss.digital.modelo.domicilio.Subdelegacion;

@Resource
public class ParserCatalogosServiciosToRest {
	
	private static final Logger LOG = LoggerFactory
            .getLogger(ParserCatalogosServiciosToRest.class);
	
	
	public static Sexo parserSexoRest (mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo sexoImssDigital) throws ServiciosRestException {
		validaObjetoNulo(sexoImssDigital);
		
		Sexo sexoRest = new Sexo();
		try {
			BeanUtils.copyProperties(sexoRest, sexoImssDigital);
			return  sexoRest;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto sexo" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto Sexo", e.getMessage()));
		}
	}
	
	public static List<Sexo> parserSexoListRest (List<mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo> sexoImssDigitalList) throws ServiciosRestException {
		validaListaNulaVacia(sexoImssDigitalList);
		
		List<Sexo> sexoRestList = new ArrayList<Sexo>();
		try {
			for (mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo sexoIt : sexoImssDigitalList) {
				sexoRestList.add(parserSexoRest(sexoIt));
			   }	
		return  sexoRestList;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto sexo" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto Sexo", e.getMessage()));
		}
		
	}
	
	
	public static Pais parserPaisRest (mx.gob.imss.ctirss.delta.model.domicilio.Pais paisImssDigital) throws ServiciosRestException {
		validaObjetoNulo(paisImssDigital);
		
		Pais paisRest = new Pais();
		try {
			BeanUtils.copyProperties(paisRest, paisImssDigital);
			return  paisRest;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto Pais" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto Pais", e.getMessage()));
		}
	}
	
	public static List<Pais> parserPaisListRest (List<mx.gob.imss.ctirss.delta.model.domicilio.Pais> paisImssDigitalList) throws ServiciosRestException {
		validaListaNulaVacia(paisImssDigitalList);
		
		List<Pais> paisRestList = new ArrayList<Pais>();
		try {
			for (mx.gob.imss.ctirss.delta.model.domicilio.Pais paisIt : paisImssDigitalList) {
				paisRestList.add(parserPaisRest(paisIt));
			   }	
		return  paisRestList;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto Pais" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto Pais", e.getMessage()));
		}
		
	}
	
	public static Parentesco parserParentescoRest (mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco parentescoImssDigital) throws ServiciosRestException {
		validaObjetoNulo(parentescoImssDigital);
		
		Parentesco parentescoRest = new Parentesco();
		try {
			BeanUtils.copyProperties(parentescoRest, parentescoImssDigital);
			return  parentescoRest;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto Parentesco" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto Parentesco", e.getMessage()));
		}
	}
	
	public static List<Parentesco> parserParentescoListRest (List<mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco> parentescoImssDigitalList) throws ServiciosRestException {
		validaListaNulaVacia(parentescoImssDigitalList);
		
		List<Parentesco> parentescoRestList = new ArrayList<Parentesco>();
		try {
			for (mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco parentescoIt : parentescoImssDigitalList) {
				parentescoRestList.add(parserParentescoRest(parentescoIt));
			   }	
		return  parentescoRestList;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto Parentesco" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto Parentesco", e.getMessage()));
		}
		
	}
	
	
	public static EntidadFederativa parserEntidadFederativaRest (mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa entidadFederativaImssDigital) throws ServiciosRestException {
		validaObjetoNulo(entidadFederativaImssDigital);
		
		EntidadFederativa entidadFederativaRest = new EntidadFederativa();
		try {
			BeanUtils.copyProperties(entidadFederativaRest, entidadFederativaImssDigital);
			return  entidadFederativaRest;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto Entidad Federativa" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto Entidad Federativa",  e.getMessage()));
		}
	}
	
	public  static List<EntidadFederativa> parserEntidadFederativaListRest (List<mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa> entidadFedImssDigitalList) throws ServiciosRestException {
		validaListaNulaVacia(entidadFedImssDigitalList);
		
		List<EntidadFederativa> entidadFederativaRestList = new ArrayList<EntidadFederativa>();
		try {
			for (mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa entidadFedIt : entidadFedImssDigitalList) {
				entidadFederativaRestList.add(parserEntidadFederativaRest(entidadFedIt));
			   }	
		return  entidadFederativaRestList;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto Entidad Federativa" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto Entidad Federativa",  e.getMessage()));
		}
		
	}
	
	public static EstadoCivil parserEdoCivilRest (mx.gob.imss.ctirss.delta.model.gestion.individuo.EstadoCivil entidadFederativaImssDigital) throws ServiciosRestException {
		validaObjetoNulo(entidadFederativaImssDigital);
		
		EstadoCivil edoCivilRest = new EstadoCivil();
		try {
			BeanUtils.copyProperties(edoCivilRest, entidadFederativaImssDigital);
			return  edoCivilRest;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto Entidad Estado civil" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto  Estado civil", e.getMessage()));
		}
	}
	
	public static List<EstadoCivil> parserEdoCivilListRest (List<mx.gob.imss.ctirss.delta.model.gestion.individuo.EstadoCivil> parentescoImssDigitalList) throws ServiciosRestException {
		validaListaNulaVacia(parentescoImssDigitalList);
		
		List<EstadoCivil> edoCivilRestList = new ArrayList<EstadoCivil>();
		try {
			for (mx.gob.imss.ctirss.delta.model.gestion.individuo.EstadoCivil edoCivilIt : parentescoImssDigitalList) {
				edoCivilRestList.add(parserEdoCivilRest(edoCivilIt));
			   }	
		return  edoCivilRestList;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto  edoCivilRest" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto  Estado civil", e.getMessage()));
		}
		
	}
	
	public static Turno  parserTurnoRest (mx.gob.imss.ctirss.delta.model.derechohabiente.Turno turnoImssDigital) throws ServiciosRestException {
		validaObjetoNulo(turnoImssDigital);
		
		Turno turnoRest = new Turno();
		try {
			BeanUtils.copyProperties(turnoRest, turnoImssDigital);
			return  turnoRest;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto Entidad trunoRestList" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto turnoRest",  e.getMessage()));
		}
	}
	
	public static List<Turno> parserTurnoListRest (List<mx.gob.imss.ctirss.delta.model.derechohabiente.Turno> turnoImssDigitalList) throws ServiciosRestException {
		validaListaNulaVacia(turnoImssDigitalList);
		
		List<Turno> trunoRestList = new ArrayList<Turno>();
		try {
			for (mx.gob.imss.ctirss.delta.model.derechohabiente.Turno turnoIt : turnoImssDigitalList) {
				trunoRestList.add(parserTurnoRest(turnoIt));
			   }	
		return  trunoRestList;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto  trunoRestList" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto turnoRest",  e.getMessage()));
		}
		
	}
	
	
	public static Delegacion  parserDelegacionRest (mx.gob.imss.ctirss.delta.model.domicilio.Delegacion delegacionImssDigital) throws ServiciosRestException {
		validaObjetoNulo(delegacionImssDigital);
		
		Delegacion delegacionRest = new Delegacion();
		try {
			BeanUtils.copyProperties(delegacionRest, delegacionImssDigital);
			return  delegacionRest;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto Deñegacion" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto Delegacion", e.getMessage()));
		}
	}
	
	public static List<Delegacion> parserDelegacionListRest (List<mx.gob.imss.ctirss.delta.model.domicilio.Delegacion> delegacionDeltaList) throws ServiciosRestException {
		validaListaNulaVacia(delegacionDeltaList);
		
		List<Delegacion> delegacionRestList = new ArrayList<Delegacion>();
		try {
			for (mx.gob.imss.ctirss.delta.model.domicilio.Delegacion delegacionIt : delegacionDeltaList) {
				delegacionRestList.add(parserDelegacionRest(delegacionIt));
			   }	
			return delegacionRestList;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto  trunoRestList" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto Delegacion", e.getMessage()));
		}
		
	}
	
	public static Subdelegacion  parserSubDelegacionRest (mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion subDelegacionImssDigital) throws ServiciosRestException {
		validaObjetoNulo(subDelegacionImssDigital);
		
		Subdelegacion subDelegacionRest = new Subdelegacion();
		Delegacion delegacionRest = null;
		try {
			delegacionRest = parserDelegacionRest(subDelegacionImssDigital.getDelegacion());
			subDelegacionImssDigital.setDelegacion(null);
			BeanUtils.copyProperties(subDelegacionRest, subDelegacionImssDigital);
			subDelegacionRest.setDelegacion(delegacionRest);
			return  subDelegacionRest;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto SubDelegacion" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto SubDelegacion", e.getMessage()));
		}
	}
	
	public static List<Subdelegacion> parserSubDelegacionListRest (List<mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion> subDelegacionDeltaList) throws ServiciosRestException {
		validaListaNulaVacia(subDelegacionDeltaList);
		
		List<Subdelegacion> subDelegacionRestList = new ArrayList<Subdelegacion>();
		try {
			for (mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion subDelegacionIt : subDelegacionDeltaList) {
				subDelegacionRestList.add(parserSubDelegacionRest(subDelegacionIt));
			   }	
			return  subDelegacionRestList;
		}catch (Exception e ) {
			LOG.error("ocurio un erro al parsear el objeto  trunoRestList" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto SubDelegacion", e.getMessage()));
		}
		
	}
	
	
	public static void validaObjetoNulo(Object obj) throws ServiciosRestException {
		if( obj == null) {
		
				LOG.error("no se encontro el registro en el catalgo");
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
						"El valor del ID no se encuentra en el catalogo", "El valor del ID no se encuentra en el catalogo"), new Exception("El valor del ID no se encuentra en el catalogo"));
		}
		
	}
	
	public static void validaListaNulaVacia(List objLista) throws ServiciosRestException {
		if( objLista == null || objLista.isEmpty()) {
				LOG.error("la lista del catalogo llego nula");
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
						"No se encontro información para el catalogo", "No se encontro información para el catalogo"), new Exception("No se encontor información para el catalogo"));
		}
		
	}
	
}
