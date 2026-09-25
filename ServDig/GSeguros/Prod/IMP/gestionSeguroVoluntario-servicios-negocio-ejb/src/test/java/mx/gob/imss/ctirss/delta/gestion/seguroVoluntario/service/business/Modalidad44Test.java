/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.business;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CuotaServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ConsultaSeguroIvroServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.SolicitudSeguroIvroRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ValidaIncorporarRissIvroRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.test.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudServiciosExpuestosRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author brenda.renteral
 */
public class Modalidad44Test {
    
    private static final Logger LOGGER;
    
    private ServiciosPersonaBusinessRemote serviciosPersonaBusinessRemote;
    private SolicitudSeguroIvroRemote solicitudSeguroIvroRemote;
    private ValidaIncorporarRissIvroRemote validaIncorporarRissIvroRemote;
    private CuotaServiceRemote cuotaServiceRemote;
    private SolicitudServiciosExpuestosRemote solicitudServiciosExpuestosRemote;
    private ConsultaSeguroIvroServiceRemote consultaSeguroIvroServiceRemote;
    
    static {
        LOGGER = LoggerFactory.getLogger(ConsultaSeguroTest.class);
    
}
    @Before
    public void init() {
        consultaSeguroIvroServiceRemote = EjbLocator.getConsultaSeguroIvroServiceRemote();
    }
    
}