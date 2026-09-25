
import gob.imss.webservice.renapo.curp.implementacion.ClienteWebserviceCurp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;

import org.junit.Test;

public class ConsultaCurpTest {
	
	
	@Test
	public void getPersonaPorCurp() throws ClienteWebserviceRenapoCurpException{
		System.out.println("getPersonaPorCurp. Inicio. " + new Date());
		//		personaFisica.setCurp("YAYO820119HPLDRM03");
		//        // personaBusiness.buscarPersonaFisicaPorCurpEnRenapo("GUPA840605HDFZRN06");
        //List<Fisica> listaPersonasFisicas = servicios.localizarPersonaFisica("MOMM770108HDFRNR02", "EUJA700727", "MAURICIO ALBERTO", "MORENO", "MANRIQUEZ", formatoFecha.parse("08/01/1977"), lugarNacimiento, sexo);
        
        //List<Fisica> listaPersonasFisicas = servicios.localizarPersonaFisica("YADM000712HNE22N07", null, null, null, null, null, null, null);
        //List<Fisica> listaPersonasFisicas = servicios.localizarPersonaFisica("AAGA040223HNELR909", null, null, null, null, null, null, null);
        //List<Fisica> listaPersonasFisicas = servicios.localizarPersonaFisica("ROGS820119HPLDRM03", null, null, null, null, null, null, null);
		

        ClienteWebserviceCurp cliente = new ClienteWebserviceCurp();
        Fisica fisica = cliente.buscarPersonaFisicaPorCurpEnRenapo("OEGA960108XNTLNL06");
        System.out.println("getPersonaPorCurp. Persona fisica recuperada:\n " + fisica);
        System.out.println("getPersonaPorCurp. Lugar nacimiento:\n " + fisica.getLugarNacimiento());
        System.out.println("getPersonaPorCurp. Acta nacimiento. Entidad:\n " + fisica.getActaNacimiento().getEntidadFederativa());
        System.out.println("getPersonaPorCurp. Acta nacimiento. getMunicipio:\n " + fisica.getActaNacimiento().getMunicipio());
        System.out.println("getPersonaPorCurp. Final. " + new Date());
	}
	
	@Test
	public void getPersonaPorDatosBasicos() throws ClienteWebserviceRenapoCurpException {
		System.out.println("getPersonaPorDatosBasicos. Inicio. " + new Date());
        //List<Fisica> listaPersonasFisicas = servicios.localizarPersonaFisica("MOMM770108HDFRNR02", "EUJA700727", "MAURICIO ALBERTO", "MORENO", "MANRIQUEZ", formatoFecha.parse("08/01/1977"), lugarNacimiento, sexo);        
        //List<Fisica> listaPersonasFisicas = servicios.localizarPersonaFisica(null, null, "SAMUEL", "RODRIGUEZ", "GRAJEDA", formatoFecha.parse("19/01/1982"), lugarNacimiento, sexo);

        final DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "mx"));
        Date fecha = null;
        
        try{
        	fecha = formatoFecha.parse("19/01/1982");
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
        final EntidadFederativa lugarNacimiento = new EntidadFederativa();
        final Sexo sexo = new Sexo();
        lugarNacimiento.setClave("21");
        sexo.setIdSexo(1);
		
        ClienteWebserviceCurp cliente = new ClienteWebserviceCurp();
        Fisica fisica = cliente.buscarPersonaFisicaPorDatosBasicosEnRenapo("SAMUEL", "RODRIGUEZ", "GRAJEDA", 1, fecha, 21);
        System.out.println("getPersonaPorCurp. Persona fisica recuperada:\n " + fisica);
        System.out.println("getPersonaPorDatosBasicos. Final. " + new Date());
	}	
	
}
