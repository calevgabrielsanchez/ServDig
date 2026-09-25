package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.reporte;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.Bien;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clase;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EquipoTransporte;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MateriaPrima;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Personal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Proceso;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Producto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoCombustible;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoMaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;

public class ReporteTest {

	public static List<SujetoObligado> sujetoObligadoReporte() {
		List<SujetoObligado> sujetos = new ArrayList<SujetoObligado>();
		SujetoObligado sujetoObligado = new SujetoObligado();
		sujetoObligado.setNumeroRegistroPatronal("A00000001");
		sujetoObligado.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
			Proceso proceso = new Proceso();
			proceso.setDesInicial("Descripcion proceso inicial");
			proceso.setDesFinal("Descripcion proceso final");
			proceso.setDesIntermedio("Descripcion proceso intermedio");
		sujetoObligado.setProceso(proceso);
		Clasificacion clasificacion = new Clasificacion();
		clasificacion.setGiro("GIRO DE LA EMPRESA");
		clasificacion.setIndPrestaServicioPersonal(1);
		clasificacion.setIndTransportePropio(1);
		clasificacion.setIndTransporteAjeno(0);
		clasificacion.setIndServiciosATerceros(1);
		clasificacion.setIndDistribuyeEntrega(0);
		Fraccion fraccion = new Fraccion();
		fraccion.setId(new Long(1));
			Grupo grupo = new Grupo();
			grupo.setDescripcion("Grupo");
				Division division = new Division();
				division.setId(new Long(1));
				division.setNumDivision("01");
				division.setDescripcion("Division");
			grupo.setDivision(division);
			grupo.setNumGrupo("01");
		grupo.setId(new Long(1));
		fraccion.setGrupo(grupo);
		Clase clase = new Clase();
		clase.setClave(new Long(1));
		clase.setDescripcion("VII");
		fraccion.setClase(clase );
		fraccion.setNumFraccion("FR1324LKM");
		fraccion.setDescripcion("Fraccion");
		fraccion.setPrimaSRT(new BigDecimal("1.19178"));
		clasificacion.setIndPrestaServicioPersonal(new Integer(1));
		clasificacion.setFraccion(fraccion);
		sujetoObligado.setClasificacion(clasificacion);
			List<Producto> productos = new ArrayList<Producto>();
			for(int i = 0; i<5; i++){
				Producto producto = new Producto();
				producto.setDescripcion("Producto #"+i);
				productos.add(producto);
			}
		sujetoObligado.setProductos(productos);
			List<MateriaPrima> materiaPrimaMateriales = new ArrayList<MateriaPrima>();
			for(int i = 0; i<5; i++){
				MateriaPrima materia = new MateriaPrima();
				materia.setDescripcion("Material #"+i);
				materiaPrimaMateriales.add(materia);
			}
		sujetoObligado.setMateriaPrimaMateriales(materiaPrimaMateriales);
		List<MaquinariaEquipo> equipos = new ArrayList<MaquinariaEquipo>();
		for(int i = 0; i<5; i++){
			MaquinariaEquipo equipo = new MaquinariaEquipo();
			equipo.setDesNombre("Equipo #"+i);
			equipo.setDesCapacidadPotencia("125 HP");
			equipo.setDesUso("se usa para algo");
			equipo.setNumUnidades(BigDecimal.TEN);
			TipoMaquinariaEquipo tipo = new TipoMaquinariaEquipo();
			tipo.setDescripcion("MOTORIZADO");
			equipo.setTipo(tipo);
			equipos.add(equipo);
		}
		sujetoObligado.setEquipos(equipos);
		List<EquipoTransporte> equiposTransporte = new ArrayList<EquipoTransporte>();
		for(int i = 0; i<5; i++){
			EquipoTransporte equipo = new EquipoTransporte();
			equipo.setDesNombre("Transporte #"+i);
			equipo.setDesCapacidadPotencia("300 HP");
			equipo.setDesUso("transporta gente y anmimales");
			equipo.setNumUnidades(BigDecimal.TEN);
			TipoCombustible tipo = new TipoCombustible();
			tipo.setDesTipoCombustible("Gasolina");
			equipo.setTipoCombustible(tipo);
			equiposTransporte.add(equipo);
		}
		sujetoObligado.setEquiposTransporte(equiposTransporte);
		List<Personal> personal = new ArrayList<Personal>();
		for(int i = 0; i<5; i++){
			Personal item = new Personal();
			item.setNumTrabajadores(BigDecimal.TEN);
			item.setOficioOcupacion("Ocupacion "+i);
			personal.add(item);
		}
		sujetoObligado.setPersonal(personal);
		List<Bien> bienes = new ArrayList<Bien>();
		for(int i = 1; i<6; i++){
			Bien bien = new Bien();
			bien.setNumCantidad(new BigDecimal(5));
			bien.setDesBienes("Edifico de "+i*10+" oficinas");
			bienes.add(bien);
		}
		sujetoObligado.setDesUsosBienes("Renta de oficinas");
		sujetoObligado.setDesAfectacion("Afectacion ");
		sujetoObligado.setBienes(bienes);
		sujetos.add(sujetoObligado);
		return sujetos;
	}
	
}
