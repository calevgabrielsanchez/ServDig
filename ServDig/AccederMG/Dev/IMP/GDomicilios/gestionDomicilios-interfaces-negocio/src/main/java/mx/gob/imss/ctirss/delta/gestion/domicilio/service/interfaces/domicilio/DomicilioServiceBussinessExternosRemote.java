package mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio;

import javax.ejb.Remote;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.LocalizarUmfException;
import mx.gob.imss.ctirss.delta.exception.domicilio.MunicipioImssNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.VialidadesNoLocalizadasException;
import mx.gob.imss.ctirss.delta.global.model.UnidadMedicaFamiliarTO;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;

@Remote
public interface DomicilioServiceBussinessExternosRemote {

    /**
     * Metodo que recupera una lista de UMF asociadas a un codigo postal a 5
     * digitos
     *
     * @param codigoPostal
     * @return
     * @throws
     * mx.gob.imss.ctirss.delta.exception.domicilio.LocalizarUmfException
     */
    UnidadMedicaFamiliarTO[] consultarUMFPorCP(String codigoPostal)
            throws LocalizarUmfException;

    Vialidad[] obtenerVialidadesAutocompletar(Localidad localidad, int periodo,
            String nomVialidad) throws VialidadesNoLocalizadasException;
    
    mx.gob.imss.digital.modelo.domicilio.Domicilio consultarUltimoDomicilioParticilar(Long idPersona) throws DomicilioNoLocalizadoException,MunicipioImssNoLocalizadoException;
    
}
