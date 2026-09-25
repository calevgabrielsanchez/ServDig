package mx.gob.imss.ctirss.delta.gestion.patronal.global.service.utility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.global.model.BienTO;
import mx.gob.imss.ctirss.delta.global.model.ClasificacionTO;
import mx.gob.imss.ctirss.delta.global.model.EquipoTransporteTO;
import mx.gob.imss.ctirss.delta.global.model.MaquinariaEquipoTO;
import mx.gob.imss.ctirss.delta.global.model.MateriaPrimaTO;
import mx.gob.imss.ctirss.delta.global.model.PersonalTO;
import mx.gob.imss.ctirss.delta.global.model.ProcesoTO;
import mx.gob.imss.ctirss.delta.global.model.ProductoTO;
import mx.gob.imss.ctirss.delta.global.model.RegistroPatronalTO;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Bien;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EquipoTransporte;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MateriaPrima;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Personal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Proceso;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Producto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

@Stateless
public class RegistroPatronalServiceUtility extends AbstractServiceUtility  
			implements RegistroPatronalServiceUtilityLocal {

	@Override
	public SujetoObligado convertirRegistroPatronalASujetoObligado(
			RegistroPatronalTO registroPatronal) {
		SujetoObligado sujetoTramite = new SujetoObligado();
		
		sujetoTramite.setNumeroRegistroPatronal(registroPatronal.getNumeroRegistro());
		sujetoTramite.setModalidad(registroPatronal.getModalidad());
		sujetoTramite.setDigVerificador(registroPatronal.getDigVerificador());
		
		//Transforma clasificacion
		sujetoTramite.setClasificacion(convertirClasificacionAModeloBase(registroPatronal.getClasificacion()));
		
		//Transforma actividad economica
		sujetoTramite = transformaActividadEconomicaAModeloBase(registroPatronal, sujetoTramite);
		
		return sujetoTramite;
	}
	
	public Clasificacion convertirClasificacionAModeloBase(ClasificacionTO clasificacionTO){
		System.out
				.println("::: Se valida que las marcas PSP, RPC no vengan NULL, en RegistroPatronalServiceUtility.convertirClasificacionAModeloBase");
		Clasificacion clasificacion = new Clasificacion();
		clasificacion.setId(clasificacionTO.getId());
		clasificacion.setCveIdFraccionClase(clasificacion.getCveIdFraccionClase());
		clasificacion.setFecEfecto(clasificacion.getFecEfecto());
		clasificacion.setFraccion(clasificacionTO.getFraccion());
		clasificacion.setGiro(clasificacionTO.getGiro());
		clasificacion.setIndDistribuyeEntrega(clasificacionTO.getIndDistribuyeEntrega() != null
				? clasificacionTO.getIndDistribuyeEntrega() : new Integer(0));
		clasificacion.setIndPrestaServicioPersonal(clasificacionTO.getIndPrestaServicioPersonal() != null
				? clasificacionTO.getIndPrestaServicioPersonal() : new Integer(0));
		clasificacion.setIndRegPatClase(clasificacionTO.getIndRegPatClase() != null
				? clasificacionTO.getIndRegPatClase() : new Integer(0));
		clasificacion.setIndServiciosATerceros(clasificacionTO.getIndServiciosATerceros() != null
				? clasificacionTO.getIndServiciosATerceros(): new Integer(0));
		clasificacion.setIndTransporteAjeno(clasificacion.getIndTransporteAjeno() != null
				? clasificacion.getIndTransporteAjeno() : new Integer(0));
		clasificacion.setIndTransportePropio(clasificacionTO.getIndTransportePropio() != null
				? clasificacionTO.getIndTransportePropio() : new Integer(0));
		clasificacion.setPrimaSRTActual(clasificacionTO.getPrimaSRTActual() != null
				? clasificacionTO.getPrimaSRTActual() : new BigDecimal(0));
		clasificacion.setSujetoObligado(new SujetoObligado());
		clasificacion.getSujetoObligado().setCveIdSujetoObligado(clasificacionTO.getRegistroPatronal().getIdRegistroPatronal());
		
		return clasificacion;
	}
	
	
	public List<Bien> convertirBienesAModelo(List<BienTO> bienes){
		List<Bien> lista = new ArrayList<Bien>();
		for(BienTO bien:bienes){
			lista.add(convertirBienAModeloBase(bien));
		}
		return lista;
	}
	
	public Bien convertirBienAModeloBase(BienTO to){
		Bien bien = new Bien();
		bien.setId(to.getId());
		bien.setDesBienes(to.getDesBienes());
		bien.setNumCantidad(to.getNumCantidad());
		bien.setSujetoObligado(new SujetoObligado());
		bien.getSujetoObligado().setCveIdSujetoObligado(to.getIdRegistroPatronal());
		return bien;
	}
	
	public List<MaquinariaEquipo> convertirEquiposAModelo(List<MaquinariaEquipoTO> equipos){
		List<MaquinariaEquipo> lista = new ArrayList<MaquinariaEquipo>();
		for(MaquinariaEquipoTO equipo:equipos){
			lista.add(convertirEquipoAModeloBase(equipo));
		}
		return lista;
	}
	
	public MaquinariaEquipo convertirEquipoAModeloBase(MaquinariaEquipoTO to){
		MaquinariaEquipo equipo = new MaquinariaEquipo();
		equipo.setId(to.getId());
		equipo.setDesCapacidadPotencia(to.getDesCapacidadPotencia());
		equipo.setDesNombre(to.getDesNombre());
		equipo.setDesUso(to.getDesUso());
		equipo.getSujetoObligado().setCveIdSujetoObligado(to.getIdRegistroPatronal());
		equipo.setTipo(to.getTipo());
		return equipo;
	}
	
	public List<EquipoTransporte> convertirTransportesAModelo(List<EquipoTransporteTO> transportes){
		List<EquipoTransporte> lista = new ArrayList<EquipoTransporte>();
		for(EquipoTransporteTO transporte:transportes){
			lista.add(convertirTransporteAModeloBase(transporte));
		}
		return lista;
	}
	
	public EquipoTransporte convertirTransporteAModeloBase(EquipoTransporteTO to){
		EquipoTransporte transporte = new EquipoTransporte();
		transporte.setId(to.getId());
		transporte.setDesCapacidadPotencia(to.getDesCapacidadPotencia());
		transporte.setDesNombre(to.getDesNombre());
		transporte.setDesUso(to.getDesUso());
		transporte.getSujetoObligado().setCveIdSujetoObligado(to.getIdRegistroPatronal());
		transporte.setTipoCombustible(to.getTipoCombustible());
		return transporte;
	}
	
	public List<MateriaPrima> convertirMateriasPrimasAModeloBase(List<MateriaPrimaTO> materias){
		List<MateriaPrima> lista = new ArrayList<MateriaPrima>();
		for(MateriaPrimaTO materia:materias){
			lista.add(convertirMateriaPrimaAModeloBase(materia));
		}
		
		return lista;
	}
	
	public MateriaPrima convertirMateriaPrimaAModeloBase(MateriaPrimaTO to){
		MateriaPrima materiaPrima = new MateriaPrima();
		materiaPrima.setId(to.getId());
		materiaPrima.setDescripcion(to.getDescripcion());
		materiaPrima.setSujetoObligado(new SujetoObligado());
		materiaPrima.getSujetoObligado().setCveIdSujetoObligado(to.getIdRegistroPatronal());
		
		return materiaPrima;
	}
	
	public List<Personal> convertirListaPersonalAModeloBase(List<PersonalTO> listaPersonal){
		List<Personal> lista = new ArrayList<Personal>();
		for(PersonalTO to:listaPersonal){
			lista.add(convertirPersonalAModeloBase(to));
		}
		
		return lista;
	}
	
	public Personal convertirPersonalAModeloBase(PersonalTO to){
		Personal personal = new Personal();
		personal.setClave(to.getClave());
		personal.setNumTrabajadores(to.getNumTrabajadores());
		personal.setOficioOcupacion(to.getOficioOcupacion());
		personal.setSujetoObligado(new SujetoObligado());
		personal.getSujetoObligado().setCveIdSujetoObligado(to.getIdRegistroPatronal());
		
		return personal;
	}
	
	public Proceso convertirProcesoAModeloBase(ProcesoTO to){
		Proceso proceso = new Proceso();
		proceso.setClave(to.getClave());
		proceso.setDesFinal(to.getDesFinal());
		proceso.setDesInicial(to.getDesInicial());
		proceso.setDesIntermedio(to.getDesIntermedio());
		proceso.setSujetoObligado(new SujetoObligado());
		proceso.getSujetoObligado().setCveIdSujetoObligado(to.getRegistroPatronal().getIdRegistroPatronal());
		return proceso;
	}
	
	public List<Producto> convertirProductosAModeloBase(List<ProductoTO> productos){
		List<Producto> lista = new ArrayList<Producto>();
		for(ProductoTO to:productos){
			lista.add(convertirProductoAModeloBase(to));
		}
		
		return lista;
	}
	
	public Producto convertirProductoAModeloBase(ProductoTO to){
		Producto producto = new Producto();
		producto.setId(to.getId());
		producto.setDescripcion(to.getDescripcion());
		producto.setSujetoObligado(new SujetoObligado());
		producto.getSujetoObligado().setCveIdSujetoObligado(to.getIdRegistroPatronal());
		
		return producto;
	}
	
	
	private SujetoObligado transformaActividadEconomicaAModeloBase(RegistroPatronalTO to, SujetoObligado modelo){
		
		modelo.setCveIdSujetoObligado(to.getIdRegistroPatronal());
		modelo.setBienes(convertirBienesAModelo(to.getBienes()));
		modelo.setCuentaConTransporte(to.getCuentaConTransporte());
		modelo.setDesAfectacion(to.getDesAfectacion());
		modelo.setDesUsosBienes(to.getDesUsosBienes());
		modelo.setEquipos(convertirEquiposAModelo(to.getEquipos()));
		modelo.setEquiposTransporte(convertirTransportesAModelo(to.getEquiposTransporte()));
		modelo.setMateriaPrimaMateriales(convertirMateriasPrimasAModeloBase(to.getMateriaPrimaMateriales()));
		modelo.setPersonal(convertirListaPersonalAModeloBase(to.getPersonal()));
		modelo.setProceso(convertirProcesoAModeloBase(to.getProceso()));
		modelo.setProductos(convertirProductosAModeloBase(to.getProductos()));
		
		return modelo;
	}

}
