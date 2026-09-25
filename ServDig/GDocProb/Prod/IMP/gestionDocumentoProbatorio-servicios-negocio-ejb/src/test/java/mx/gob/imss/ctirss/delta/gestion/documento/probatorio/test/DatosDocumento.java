package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.test;

import java.util.Date;

import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CURP;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorioRenapoEnum;

public class DatosDocumento {
	
	public static CURP getCurpRenapo(){
		CURP curp = new CURP();
        curp.setAnioRegistro(1974L);       
        curp.setNoTomo("0");
        curp.setCrip("0");
        curp.setNoFoja("0");
        curp.setNoLibro("2");
        curp.setNoActa("562");
        curp.setCurp("NEAG680629HVZRVD00");
                                
        //MUNICIPIO - ENTIDAD DEL ACTA DE NACIMIENTO
        EntidadFederativa entidadFederativa = new EntidadFederativa();
        entidadFederativa.setNombre("VERACRUZ");
        entidadFederativa.setClave("30");           
        Municipio municipio = new Municipio();
        municipio.setNombre("CERRO AZUL");
        municipio.setClave("34");
        municipio.setEntidadFederativa(entidadFederativa);
        curp.setMunicipio(municipio);
        curp.setNumTipoDocumento(TipoDocumentoProbatorioRenapoEnum.ACTA_NACIMIENTO.getValor().longValue());
        curp.setDescripcionTipoDocumento(TipoDocumentoProbatorioRenapoEnum.ACTA_NACIMIENTO.getDescripcion());
       
        return curp;
	}
	
	public static Nacimiento getActaNacimiento(){
		Nacimiento nacimiento = new Nacimiento();
		nacimiento.setAnio(1986);
		nacimiento.setCrip("12345678DAF");
		nacimiento.setNoJuzgado("12345");
		nacimiento.setTomo("986hdi");
		nacimiento.setNoActa("1243");
		nacimiento.setNoFoja("adfad");
		nacimiento.setNoLibro("93839");
		nacimiento.setFechaExpedicion(new Date());		
		nacimiento.setFechaSuceso(new Date());
		Municipio municipio = new Municipio();
		municipio.setClave("1");
		EntidadFederativa entidadFederativa = new EntidadFederativa();
		entidadFederativa.setClave("1");
		municipio.setEntidadFederativa(entidadFederativa);
		nacimiento.setMunicipio(municipio);
		return nacimiento;		
	}
}
