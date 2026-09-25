package mx.gob.imss.ctirss.correccion.promocion.service.ejb.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtNroFolio;
import mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacion;
import mx.gob.imss.ctirss.correccion.model.CrtSelector;
import mx.gob.imss.ctirss.correccion.folio.service.ejb.FoliadorServiceLocal;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.service.AbstractService;
import mx.gob.imss.ctirss.correccion.framework.utils.TipoCorreccion;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.promocion.regularizacion.model.CrtRegulapagosdet;
import mx.gob.imss.ctirss.correccion.promocion.service.ejb.PromocionServiceRemote;
import mx.gob.imss.ctirss.correccion.promocion.service.ejb.dao.PromocionDAOLocal;
import mx.gob.imss.ctirss.correccion.session.UserSession;

@Stateless(name="promocionService", mappedName = "promocionService")
public class PromocionServiceBean<T extends AbstractModel> extends AbstractService implements PromocionServiceRemote<T> {
	
	@EJB PromocionDAOLocal<T> daoPromocion;
	@EJB FoliadorServiceLocal foliador;
	
	public T agregar(T model) {
		daoPromocion.agrega(model);
		return model;
	}
		
	public List<T> consultar(T filtro) {
		return daoPromocion.consulta(filtro);
	}
	
	public T consultaPorClave(T filtro) {
		return daoPromocion.consultaPorClave(filtro);
	}
	
	public CrtDeteccion obtieneDeteccionporClave(CrtDeteccion filtro) {
		return daoPromocion.obtieneDeteccionporClave(filtro);
	}
	
	public T modificar(T model) {
		daoPromocion.modificar(setFieldsBeforeUpdate(model));
		return model;
	}
	
	public void elimina(T model){
		daoPromocion.elimina(model);
	}
	
	public CrtDeteccion obtieneObraporNumeroRegistro(CrtDeteccion obra){
		return daoPromocion.obtieneObraporNumeroRegistro(obra);
	}
	
	public List<CrtDeteccion> obtieneObraporNumReg(CrtDeteccion obra){
		return daoPromocion.obtieneObraporNumReg(obra);
	}

	public DatosSalidaPaginador<T> paginaDeteccion(DatosEntradaPaginador<T> params){
		return daoPromocion.paginaDeteccion(params);
	}
	
	public DatosSalidaPaginador<T> paginaExConstruccion(DatosEntradaPaginador<T> params){
		return daoPromocion.paginaExConstruccion(params);
	}
	
	public DatosSalidaPaginador<T> consultaPromocion(DatosEntradaPaginador<T> params){
		return daoPromocion.consultaPromocion(params);
	}
	
	public DatosSalidaPaginador<T> paginaSaticB(DatosEntradaPaginador<T> params){
		return daoPromocion.paginaSaticB(params);
	}
	
	public DatosSalidaPaginador<T> paginaSaticBSinIncidencia(DatosEntradaPaginador<T> params){
		return daoPromocion.paginaSaticBSinIncidencia(params);
	}
	
	public List<CrtNroFolio> obtieneFolioPromocion(Long Del,Long SubDel, String cad, String fecha){
		return daoPromocion.obtieneFolioPromocion(Del, SubDel, cad, fecha);
	}
	
	public String obtieneFolioInvitacion(Long Del,Long SubDel, String cad, String fecha){
		return daoPromocion.obtieneFolioInvitacion(Del, SubDel, cad, fecha);
	}

	public CrtPromocion validaPromocionExistente(Long patronPK,Date periodoInicial,Date periodoFinal) {
		return daoPromocion.validaPromocionExistente(patronPK, periodoInicial,periodoFinal);
	}
	
	public DatosSalidaPaginador<T> paginaSelector(DatosEntradaPaginador<T> params){
		return daoPromocion.paginaSelector(params);
	}
	
     public List<T> consultarSelector(T model) {
		
		return (List<T>) daoPromocion.consultarSelector(model);
	}
     
     public CrtPromocion verificaDuplicidad(Long subDelegacion, Long idCriterioseleccion, Long patron) {
 		
 		return (CrtPromocion) daoPromocion.verificaDuplicidad(subDelegacion, idCriterioseleccion, patron);
 	}
     
     
     public void actualizaSelector(Long cveSelector, String usuario){
    	 CrtSelector selector = daoPromocion.obtieneSelectorporClave(cveSelector);
    	 if(selector != null){
    		 selector.setIdPromocionado("1");
    		 selector.setFecFecharegistro(new Date());
    		 selector.setCveUsuario(usuario);
    	 }
    	 daoPromocion.modificaSelector(selector);
    	 
     }

	@Override
	public T consultaPorClaveCS(T model) {
		return daoPromocion.consultaPorClaveCS(model);
	}
	
	public void replicaPromocion(T model){
		daoPromocion.replicaPromocion(model);
	}
     
	public void replicaPago(CrtRegulapagosdet regulaDet){
		daoPromocion.replicaPago(regulaDet);
	}
	
	public T consultaPagoReplicadoPorFolio(T filtro){
		return daoPromocion.consultaPagoReplicadoPorFolio(filtro);
	}
	
	public void replicaEliminaPago(CrtRegulapagosdet regulaDet){
		daoPromocion.replicaEliminaPago(regulaDet);
	}

	@Override
	public T consultaCriterioPorClave(T model) {
		return daoPromocion.consultaCriterioPorClave(model);
	}

	@Override
	public T consultaCorrPromInvita(T model) {
		return daoPromocion.consultaCorrPromInvita(model);
	}

	@Override
	public T consultaPromoInvita(T model) {
		return daoPromocion.consultaPromoInvita(model);
	}

	@Override
	public T consultaInvitacionPromocion(T model) {
		return daoPromocion.consultaInvitacionPromocion(model);
	}

	@Override
	public T guardar(T model) {
		CrtPromocion promocion = (CrtPromocion) model;
		UserSession user = promocion.getUsuarioFirmado();
		TipoCorreccion tipoCorreccion = null;	
		if(promocion.getCveTipocorr() == 3L){
			tipoCorreccion = tipoCorreccion.SATIC_A;
		}if(promocion.getCveTipocorr() == 4L){
			tipoCorreccion = tipoCorreccion.SATIC_B;
		}if(promocion.getCveTipocorr() == 5L){
			tipoCorreccion = tipoCorreccion.EXHORTO_DE_CONSTRUCCION;
		}if(promocion.getCveTipocorr() == 6L){
			tipoCorreccion = tipoCorreccion.EXHORTO_DE_LO_ORDINARIO;
		}if(promocion.getCveTipocorr() == 7L){
			tipoCorreccion = tipoCorreccion.SALARIO_BASE_DE_COTIZACION;
		}if(promocion.getCveTipocorr() == 8L){
			tipoCorreccion = tipoCorreccion.CONTROL_DE_FUENTES_EXTERNAS_DE_INFORMACION_ORDINARIO;
		}if(promocion.getCveTipocorr() == 9L){
			tipoCorreccion = tipoCorreccion.CONTROL_DETECCION_ORDINARIO;
		}
		String folio = this.foliador.recuperarSiguienteFolio(Long.valueOf(user.getCveCodigoDelegacion()), Long.valueOf(user.getCveCodigoSubDelegacion()), promocion.getFecFechaemisionpro(), tipoCorreccion);
		promocion.setNuFoliopromocion(folio);
		model = (T) promocion;
		return daoPromocion.guardar(model);
	}

	@Override
	public DatosSalidaPaginador<T> obtenerCriterioSelector(DatosEntradaPaginador<T> params, Long delegacion,Long subDelegacion, Long criterio) {
		return daoPromocion.obtenerCriterioSelector(params, delegacion, subDelegacion, criterio);
	}

	@Override
	public T consultaPorFolio(T model) {
		return daoPromocion.consultaPorFolio(model);
	}

	@Override
	public T obtieneObraporNumObra(T model) {
		return daoPromocion.obtieneObraporNumObra(model);
	}

	@Override
	public List<T> obtieneIncidenciasporCveObra(T model) {
		return daoPromocion.obtieneIncidenciasporCveObra(model);
	}

	@Override
	public List<T> obtieneRelTrabajadoresporCveObra(T model) {
		return daoPromocion.obtieneRelTrabajadoresporCveObra(model);
	}

	@Override
	public T consultaSelectorPorClave(T model) {
		return daoPromocion.consultaSelectorPorClave(model);
	}

	@Override
	public List<T> consultaNumeroFolio(String model) {
		return daoPromocion.consultaNumeroFolio(model);
	}
}
