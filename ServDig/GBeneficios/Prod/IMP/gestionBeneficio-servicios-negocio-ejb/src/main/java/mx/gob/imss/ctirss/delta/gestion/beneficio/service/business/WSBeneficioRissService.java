package mx.gob.imss.ctirss.delta.gestion.beneficio.service.business;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import mx.gob.imss.ctirss.delta.exception.beneficio.BeneficioRissException;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.BeneficioRissServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.beneficio.Beneficio;
import mx.gob.imss.ctirss.delta.model.beneficio.BeneficioSchema;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.springframework.util.CollectionUtils;

@Stateless
@WebService(name="wsBeneficioRissService",
        portName="wsBeneficioRissServicePort",
        serviceName="wsBeneficioRissService",
        targetNamespace="http://mx.gob.imss.ctirss.delta.beneficio/beneficioriss")
public class WSBeneficioRissService {

    @EJB
    private BeneficioRissServiceBusinessRemote beneficioRissServiceBusinessRemote;

    @WebMethod
    @WebResult(name="beneficio",
            targetNamespace="http://mx.gob.imss.ctirss.delta.beneficio/beneficioriss")
	public BeneficioSchema obtenerBeneficioPorNRP(@WebParam(name="nrp") String nrp,
            @WebParam(name="fechaInicio") Date fechaInicio,
            @WebParam(name="fechaFin") Date fechaFin)
        throws BeneficioRissException {
        Beneficio beneficio = beneficioRissServiceBusinessRemote.obtenerBeneficioPorNRP(nrp,
                fechaInicio, fechaFin);
        return beneficioToBeneficioSchema(beneficio);
    }

    @WebMethod
    @WebResult(name="beneficio",
            targetNamespace="http://mx.gob.imss.ctirss.delta.beneficio/beneficioriss")
	public BeneficioSchema obtenerBeneficioPorNSS(@WebParam(name="nss") String nss,
            @WebParam(name="fechaInicio") Date fechaInicio,
            @WebParam(name="fechaFin") Date fechaFin) 
			throws BeneficioRissException {
        Beneficio beneficio = beneficioRissServiceBusinessRemote.obtenerBeneficioPorNSS(nss,
                fechaInicio, fechaFin);
		return beneficioToBeneficioSchema(beneficio);
	}

    private BeneficioSchema beneficioToBeneficioSchema(Beneficio beneficio) {
        BeneficioSchema beneficioSchema = new BeneficioSchema();
        beneficioSchema.setInicioVigencia(beneficio.getInicioVigencia());
        beneficioSchema.setFinVigencia(beneficio.getFinVigencia());
        beneficioSchema.setFechaBaja(beneficio.getFechaBaja());
        beneficioSchema.setEstadoBeneficio(beneficio.getEstadoBeneficio());
        beneficioSchema.setTipoBeneficio(beneficio.getTipoBeneficio());
        beneficioSchema.setMotivoCancelacion(beneficio.getMotivoCancelacion());
        beneficioSchema.setListaDescuentosBeneficio(beneficio.getListaDescuentosBeneficio());        
        beneficioSchema.setIndicadorApartadoC(beneficio.isIndicadorApartadoC());
        beneficioSchema.setListaNRPsMod10y13(beneficio.getListaNRPsMod10y13());
        Fisica fisica = null;
        if(!CollectionUtils.isEmpty(beneficio.getListaSujetosObligados())){
        	fisica = beneficio.getListaSujetosObligados().get(0).getFisica();
        }else if (beneficio.getFisica()!=null){
        	fisica = beneficio.getFisica();
        }        
        if(fisica!=null){
            beneficioSchema.setRfc(fisica.getRfc());
            beneficioSchema.setCurp(fisica.getCurp());	
            beneficioSchema.setNombre(fisica.getNombre());
            beneficioSchema.setPrimerApellido(fisica.getPrimerApellido());
            beneficioSchema.setSegundoApellido(fisica.getSegundoApellido());
        }
        return beneficioSchema;
    }
}
