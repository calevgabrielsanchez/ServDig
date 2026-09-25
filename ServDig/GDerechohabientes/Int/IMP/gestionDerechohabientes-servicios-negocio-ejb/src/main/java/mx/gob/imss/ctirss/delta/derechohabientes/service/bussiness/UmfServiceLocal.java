package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.CodigoSinUmfException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Consultorio;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Turno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;

@Local
public interface UmfServiceLocal {
	Boolean mismaCircunscripcion(Long idUmfOrigen, Long idUmfDestino) throws DerechohabientesBusinessException;
	List<UnidadMedicaFamiliar> findUmfByCodigoPostal(String codigoPostal) throws DerechohabientesBusinessException,CodigoSinUmfException,Exception;
	List<UnidadMedicaFamiliar> findUmfByCodigoPostal(String codigoPostal, Integer notEqualIndUmfCfe) throws DerechohabientesBusinessException,CodigoSinUmfException,Exception;
	UnidadMedicaFamiliar getUnidadMedicaFamiliarById(Long idUmf) throws DerechohabientesBusinessException;
	List<Turno> getTurnosDisponiblesPorUmf(Long idUmf) throws DerechohabientesBusinessException;
	Consultorio getConsultorioMenorPoblacion(Long idUmf, Long idTurno, Boolean mostrarVirtuales) throws DerechohabientesBusinessException;
	
	
	List<UnidadMedicaFamiliar> findUmfsByAsentamiento(Asentamiento asentamiento, Boolean incrluirCFE) throws DerechohabientesBusinessException,CodigoSinUmfException,Exception;
}
