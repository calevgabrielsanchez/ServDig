package mx.gob.imss.ctirss.correccion.deteccion.service.ejb.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtNroFolio;
import mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion;
import mx.gob.imss.ctirss.correccion.deteccion.service.ejb.DeteccionServiceRemote;
import mx.gob.imss.ctirss.correccion.deteccion.service.ejb.dao.DeteccionDAOLocal;
import mx.gob.imss.ctirss.correccion.folio.service.ejb.FoliadorServiceLocal;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.service.AbstractService;
import mx.gob.imss.ctirss.correccion.framework.utils.TipoCorreccion;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;

@Stateless(name="deteccionService", mappedName = "deteccionService")
public class DeteccionServiceBean<T extends AbstractModel> extends AbstractService implements DeteccionServiceRemote<T>{
	
	@EJB DeteccionDAOLocal<T> daoDeteccion;
	@EJB FoliadorServiceLocal foliador;
	
	public T agregar(T model) {
		daoDeteccion.agrega(setFieldsBeforeInsert(model));
		return model;
	}
	
	public void eliminar(T model){
		daoDeteccion.elimina(model);
	}
	
	public T modificar(T model) {
		daoDeteccion.modifica(setFieldsBeforeUpdate(model));
		return model;
	}
	
	public List<T> consultar(T filtro) {
		return daoDeteccion.consulta(filtro);
	}
	
	public T consultaPorClave(T filtro) {
		return daoDeteccion.consultaPorClave(filtro);
	}

	public DatosSalidaPaginador<T> pagina(DatosEntradaPaginador<T> params){
		return daoDeteccion.pagina(params);
	}
	
	public DatosSalidaPaginador<T> valida(DatosEntradaPaginador<T> params,List<DgDomicilioGeografico> domicilios){
		return daoDeteccion.valida(params,domicilios);
	}
	
	public String obtieneFolios(Long Del,Long SubDel, Date fecha){
		TipoCorreccion tipoCorreccion = null;
		tipoCorreccion = tipoCorreccion.CONTROL_DE_FUENTES_EXTERNAS_DE_INFORMACION_ORDINARIO;
		return this.foliador.recuperarSiguienteFolio(Del, SubDel, fecha, tipoCorreccion);
	}
	
	public DatosSalidaPaginador<T> validacionObraSatic(DatosEntradaPaginador<T> params){
		return daoDeteccion.validacionObraSatic(params);
	}

	@Override
	public T consultarXIdDom(T model) {
		return daoDeteccion.consultarXIdDom(model);
	}

	@Override
	public T validaNuReporte(T model) {
		CrtDeteccion deteccionExistente=null;
		
		List<CrtDeteccion> listaDeteccion=(List<CrtDeteccion>) daoDeteccion.validaNuReporte(model);		
		String fechaDeteccion = ((CrtDeteccion)model).getFechaDeteccion();
		String[] datosFechas = fechaDeteccion.split("-");
		String ejercicioActual=datosFechas[2];
		String ejerc;
	
		for(CrtDeteccion deteccion : listaDeteccion){
//				String[] datosFechaConsulta = new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(deteccion.getFecFechadeteccionFc()).split("/");
//				if(datosFechaConsulta[2].equals(datosFechas[2])){
//					deteccionExistente = deteccion;
//				}
			ejerc=deteccion.getNuFoliodeteccion().split("/")[2];
			
			if(ejercicioActual.equals(ejerc)){
				deteccionExistente=deteccion;
			}			
		}
		
		return (T) deteccionExistente;
	}

	@Override
	public T buscaUbicacion(T model) {
		return daoDeteccion.buscaUbicacion(model);
	}

	@Override
	public CrtNroFolio obtieneFolios(Long Del, Long SubDel, String fecha,
			Integer tipo) {
		// TODO Auto-generated method stub
		return null;
	}
}
