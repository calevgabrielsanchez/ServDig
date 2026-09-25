package mx.gob.imss.ctirss.delta.gestion.patronal.test.mac;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;


public class ValidaClasificacionTest {
	
	
	private static final Logger log;

	static {
		log = LoggerFactory.getLogger(ValidaClasificacionTest.class);
	}

	
//	@Test
//	public void testModificarautorizacion(){
//
//		System.out.println("Antes de validar");
////		String regPatron = "A6540760100";
////		String idSol = "74958825";
//
//		//Produccion
//		String regPatron = "Y7315141106"; 
//		String idSol = "624211340";
//
//		ValidaClasificacionServiceBusinessRemote ejb = EJBLocator.getValidaClasificacionServiceBusiness();
//		ValidaClasificacionServiceBusinessRemote ejbCl = EJBLocator.getValidaClasificacionServiceBusiness();
//		
//		try {
//			
//			Clasificacion clasificacion = ejbCl.obtenerDetalleFraccionPorId(getClasificacion(cveIdDivision, cveIdGrupo, cveIdFraccion));
//			
//			Clasificacion clas = ejb.validaClasificacionGP(clasificacion,regPatron, new Long(idSol));
//			//Clasificacion clas = ejb.validaClasificacionGP(getClasificacionNum(),regPatron, new Long(idSol));
//			System.out.println(clas.toString());
//			
//		} catch (final GestionPatronalBusinessException e) {
//			//log.error(e.getMessage(), e);
//			if(e != null && e.getCodigo() != null) {
//				System.out.println("Trae codigo: " + e.getCodigo());
//				System.out.println(e.getMessage());
//			}
//			if(e != null && e.getCodigo() != null && e.getCodigo() == 800){
//				System.out.println("::: Error del EJB");
//				e.printStackTrace();
//				System.out.println("::Error que se regresa en Controller");
//				System.out.println("El registro patronal "+regPatron+" deber\u00E1 ser regularizado "
//						+ "por PAC, ya que el patr\u00F3n cuenta con el RP " + e.getMessage().substring(44, 55)
//						+ " con la misma clasificaci\u00F3n seleccionada"
//						+ ", por lo que se le asignar\u00E1 el estatus \"Por Regularizar\" ");
//				
//			}else{
//				System.out.println("::: Estoy en ELSE");
//				e.printStackTrace();
//			}			
//			
//			
//		} catch (Exception e) {
//			System.out.println("::: Errror: " + e.getMessage());
//			e.printStackTrace();
//		}			
//
//		System.out.println("Termine");
//
//	}	
	
	//obntiene la clasificacion por los numeros del catalogo ya que el metodo que obtiene la fraccion equivalente asi los necesita
	private Clasificacion getClasificacionNum() {		
		final Clasificacion clas = new Clasificacion();
		final Division division = new Division();
		division.setId(Long.valueOf("4"));
		final Grupo grupo = new Grupo();
		grupo.setId(Long.valueOf("2"));
		grupo.setDivision(division);
		final Fraccion fraccion = new Fraccion();
		fraccion.setId(Long.valueOf("4"));
		fraccion.setGrupo(grupo);
		clas.setFraccion(fraccion);
		return clas;
	}


	
}
