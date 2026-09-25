package mx.gob.imss.cit.cda.web.app.responsable.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractReadController;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.CorreccionDatos;
import mx.gob.imss.cit.cda.web.app.responsable.model.DatosAGuradarNSS;
import mx.gob.imss.cit.cda.web.app.responsable.model.DatosNSSBD;
import mx.gob.imss.cit.cda.web.app.responsable.model.RequestTramitesAsignadosPage;
import mx.gob.imss.cit.cda.web.app.responsable.model.TramitesAsignados;
import mx.gob.imss.cit.cda.web.support.model.Page;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;



@Controller
public class EditarDatosController extends AbstractReadController<RequestTramitesAsignadosPage, Page<TramitesAsignados>>{

	@Autowired
	private ServiceBusinessRemote serviceBusiness;
	
  @Autowired
	@Qualifier(BeansConstants.READ_TRAMITES_RESPONSABLE_HELPER)
  ReadHelper<RequestTramitesAsignadosPage, Page<TramitesAsignados>> service;
  
  @Override
  public ReadHelper<RequestTramitesAsignadosPage, Page<TramitesAsignados>> getHelper() {
    return service;
  }
  
  @RequestMapping(RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLEE )
	@ResponseBody	
  
  public List<CorreccionDatos>  datosNSSBD(@RequestBody DatosNSSBD input, HttpServletRequest request) throws IOException{	  
	  System.out.println("uno");
	  List<Fisica> personasFuenteNSS =serviceBusiness.getAseguradoByNSSLegadosyBDTU(input.getNss(),true);
	  System.out.println("dos");
	  List<CorreccionDatos> obtener = new ArrayList<CorreccionDatos>(); 
//	  CorreccionDatos correccion = new CorreccionDatos();
	  int i = 0;
	  for(Fisica f : personasFuenteNSS)
	  {		
		  CorreccionDatos correccion = new CorreccionDatos();
		  System.out.println("-----------------------------------------------------------------");
		  System.out.println("-----------------------------------------------------------------");
		  System.out.println("--------------------------Documentos-----------------------------");
		  System.out.println(" Datoscurp " +f.getCurp());
		  System.out.println(" AltaIMSS " +f.getIdentificadores().get(0).getIdentificadora());
		  System.out.println(" AltaIMSS " +f.getIdentificadores().get(0).getIdIdentificador());
		  System.out.println(" Sexo " + f.getSexo().getGenero());
//		  System.out.println(" Entidad: " + (f.getActaNacimiento().getIdEntidadFederativa()) != null?f.getActaNacimiento().getIdEntidadFederativa():"a");
//		  System.out.println(" Municipio: " + (f.getActaNacimiento().getMunicipio().getNombre())!=null?f.getActaNacimiento().getMunicipio().getNombre():"a");
//		  System.out.println(" Anio de Registro:" + (f.getActaNacimiento().getAnio())!=null?f.getActaNacimiento().getAnio(): "a ");
//		  System.out.println(" Tomo: " + (f.getActaNacimiento().getTomo())!=null?f.getActaNacimiento().getTomo(): " a");
//		  System.out.println(" Numero de Acta: " + (f.getActaNacimiento().getNoActa()) != null?f.getActaNacimiento().getNoActa(): "a ");
//		  System.out.println(" CRIPT " + (f.getActaNacimiento().getCrip())!= null?f.getActaNacimiento().getCrip():"a ");
//		  System.out.println(" Numero de libro " + (f.getActaNacimiento().getNoLibro())!=null?f.getActaNacimiento().getNoLibro():"a ");
//		  System.out.println(" Numero de Foja " + (f.getActaNacimiento().getNoFoja())!=null?f.getActaNacimiento().getNoFoja():" a");
//		  System.out.println(" Nacionalidad " + f.getCertificadoNacionalidadMexicana().getCifrado());
		  System.out.println("-----------------------------------------------------------------");
		  System.out.println("-----------------------------------------------------------------");
		  System.out.println("-----------------------------Inicia llenar datos----------------------------");
		  correccion.setCurp(f.getCurp());
		  correccion.setApellidoPaterno((f.getPrimerApellido()));
		  correccion.setApellidoMaterno((f.getSegundoApellido()));
		  correccion.setNombre(f.getNombre());
		  correccion.setSexo(f.getSexo().getGenero());
		  correccion.setFechaNacimiento(f.getFechaNacimientoFormateada());
		  correccion.setLugarNacimiento(f.getLugarNacimiento().getNombre());
		  correccion.setNacionalidad(f.getNombre());
		  correccion.setDatosDocumentoProbatorio(f.getCurp());
		  correccion.setPertenecebd(f.getIdentificadores().get(0).getIdentificadora());
		  System.out.println("-----------------------------termina llenar datos----------------------------");
		  System.out.println("-----------------------------llenar datos documentos-------------------------");
		  
//		  if((f.getActaNacimiento().getIdEntidadFederativa())!=null)
//		  {
//			  System.out.println("Entro if");
//			  correccion.setEntidad(f.getActaNacimiento().getIdEntidadFederativa());
//			  correccion.setMunicipio(f.getActaNacimiento().getMunicipio().getNombre());
//			  correccion.setAnioderegistro(String.valueOf(f.getActaNacimiento().getAnio()));
//			  correccion.setTomo(f.getActaNacimiento().getTomo());
//			  correccion.setNumerodeacta(f.getActaNacimiento().getNoActa());
//			  correccion.setCrip(f.getActaNacimiento().getCrip());
//			  correccion.setNumerodelibro(f.getActaNacimiento().getNoLibro());
//			  correccion.setNumerodefoja(f.getActaNacimiento().getNoFoja());
//		  }
//		  
		 
		  
		  System.out.println("-----------------------------termina llenar datos docuemto------------------");
	
		  obtener.add(correccion);		  
		 
		  i=i+1;
	  }
	  for(CorreccionDatos Co : obtener )
	  {
		  System.out.println(Co.getCurp());
		  System.out.println(Co.getPertenecebd());
	  }
	  return obtener;

	  
  }

  @RequestMapping(RequestMappingConstants.GUARDARDATOSNSSACTUALIZADOS)
	@ResponseBody	
//	public void guardarDatosNSS(@RequestBody ArrayList<DatosAGuradarNSS> input, HttpServletRequest request)datosNSSBD
	public void datosNSSBD(@RequestBody List<DatosAGuradarNSS>input, HttpServletRequest request)
  {
	  
	  System.out.println("--------------------------------------------------------------------------------");
	  System.out.println("--------------------------------A guardar---------------------------------------");
	  System.out.println("--------------------------------------------------------------------------------");
	  
				

	}
//
}
	

