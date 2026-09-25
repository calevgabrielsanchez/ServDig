package mx.gob.imss.ctirss.delta.gestion.patronal.global.service.business;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.model.AttachmentContent;
import mx.gob.imss.ctirss.delta.framework.base.model.EmailPayloadType;
import mx.gob.imss.ctirss.delta.gestion.patronal.global.service.interfaces.ClasificacionServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.solicitud.SolicitudServiceEntityTest;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.test.EJBLocator;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration.EMailProducer;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.fecha.FechaDto;
import mx.gob.imss.ctirss.delta.model.persona.hlda.HldaVO;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClasificacionServiceTest {


	@Test
	public void getDiasFestivos(){
		System.out.println("::: Inicia ");
		ClasificacionServiceRemote solEJB = EjbLocator.getClasificacionServiceBusiness();	
		List<FechaDto> lista = solEJB.diasFestivos("NOV","25"); 
		
		for (FechatDto fecha: lista) {
			System.out.println("fecha: " );
		}
		System.out.println("::: Termine");
	}
}
