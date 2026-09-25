package mx.gob.imss.cit.cda.service.utility;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.asegurado.cda.BitacoraMovimientoSindoCDA;

@Local
public interface CorreosRespuestaSINDOUtilityLocal {
	
	String contenidoCorreoArchivoNoLocalizado();
	String contenidoCorreoDiferenteNumMovimientos();
	String contenidoCorreoMovimientoNoExitoso(String folio, String responsable, String asegurado, String curp, List<BitacoraMovimientoSindoCDA> bitacoraSINDO);
	String contenidoCorreoMovimientoExitoso(String folio, String responsable, String asegurado, String curp, List<BitacoraMovimientoSindoCDA> bitacoraSINDO);
	String contenidoCorreoSinRespuestaSINDO();
	
}
