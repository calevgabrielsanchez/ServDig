package mx.gob.imss.ctirss.idse.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import mx.gob.imss.ctirss.idse.model.RequerimientoIETCBean;
import mx.gob.imss.ctirss.idse.persistencia.IfrCartasIdseFiel;
import mx.gob.imss.ctirss.idse.persistencia.IfrCartasIdseFielPK;
import mx.gob.imss.ctirss.idse.persistencia.IfrCertificadoReq;
import mx.gob.imss.ctirss.idse.persistencia.IfrCertificadoReqPK;
import mx.gob.imss.ctirss.idse.persistencia.IftDatosCertificado;
import mx.gob.imss.ctirss.idse.persistencia.IftRegistrosPatronale;
import mx.gob.imss.ctirss.idse.persistencia.IftRegistrosPatronalePK;
import mx.gob.imss.ctirss.idse.utility.IETCUtilityLocal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(mappedName="ietcEntity", name="ietcEntity")
public class IETCServiceEntity extends IETCAbstractEntity implements IETCServiceEntityLocal {
	private static final Logger LOG;
	static {
		LOG = LoggerFactory.getLogger(IETCServiceEntity.class);
	}
	@EJB
	IETCUtilityLocal ietcUtility;
	
	@Override
	public IftDatosCertificado consultarCertificado(String rfc) {
		Query query = this.em.createQuery("select certificado from IftDatosCertificado certificado where certificado.numEstatus !=2 and certificado.refRfcAsociado=:rfc");
		query.setParameter("rfc", rfc.toUpperCase());
		try{
			IftDatosCertificado certificado = (IftDatosCertificado)query.getSingleResult();
			return certificado;
		}catch(NoResultException nre){
			return null;
		}
	}

	@Override
	public void insertaCertificado(RequerimientoIETCBean certificado) {
		//Se obtiene el id de requerimiento
		Long seqCveReq =obtenerNuevaClaveDeRequerimiento();
				
		//Se inserta el certificado
		IftDatosCertificado entity = ietcUtility.convertirPkcs7AIftDatosCertificado(certificado,seqCveReq);
		
		IftDatosCertificado certificadoActual = consultarCertificado(certificado.getPkcs7Bean().getRfcAsociado());
		System.err.println("insertando cert");
		if(certificadoActual==null){
			System.err.println("caso uno");
			LOG.debug("No existia el certificado");
			this.em.persist(entity);//inserto certificado
			insertarNuevoRegistroPatronal(entity);
			LOG.debug("Se inserto certificado");
		}else if(!certificadoActual.getCveSerial().equalsIgnoreCase(entity.getCveSerial())){
			System.err.println("caso dos");
			LOG.debug("Se encontró que el certificado asociado al RFC ["+certificado.getPkcs7Bean().getRfcAsociado()+"] ha sido actualizado");
			LOG.debug("Serial actual almacenado ["+certificadoActual.getCveSerial()+"] serial nuevo ["+entity.getCveSerial()+"]");
			LOG.debug("Se inserta el nuevo certificado");
			this.em.persist(entity);//Se inserta el nuevo certificado
			LOG.debug("Se inserta el nuevo registro patronal asociado al certificado");
			certificadoActual.setNumEstatus(new BigDecimal(2));
			certificadoActual.setCveSerialAnterior(entity.getCveSerial());
			this.em.merge(certificadoActual);
			insertarNuevoRegistroPatronal(entity);
			
			List<String> registrosPatronalesAOmitir = new ArrayList<String>();
			registrosPatronalesAOmitir.add(certificado.getRegPatronBean().getRegistroPatronal());
			asociarRegistroPatronalesPrevios(entity.getRefRfcAsociado(), entity.getCveSerial(),registrosPatronalesAOmitir);
			
		}else{
			System.err.println("caso tres");
			entity=ietcUtility.combinarInfoCertificado(certificadoActual, entity);
			LOG.debug("El certificado ya estaba en IDSE, se copian los datos de la base al objeto certificado solo se conserva la información del nuvo requerimiento");
			LOG.debug(certificadoActual.toString());
			insertarNuevoRegistroPatronal(entity);
		}
		
		
		
	}
	
	private Long obtenerNuevaClaveDeRequerimiento(){
		Query query = this.em.createNativeQuery("SELECT MAX(CVE_REQUERIMIENTO) FROM IFR_CERTIFICADO_REQ");
		
		Object result = query.getSingleResult();
		BigDecimal seqCveReqBd = (BigDecimal)result;
		return seqCveReqBd.longValue()+1;
	}
	
	/**
	 * Inserta 
	 * @param entity
	 */
	private void insertarNuevoRegistroPatronal(IftDatosCertificado entity){
		
		
		IfrCertificadoReq requerimiento = entity.getIfrCertificadoReqs().get(0);
		
		IfrCartasIdseFiel cartasIdseFiel = requerimiento.getIfrCartasIdseFiel();
		
		IftRegistrosPatronale registroPatronal = requerimiento.getIftRegistrosPatronales().get(0);
		
		if(existeRegistroPatronal(registroPatronal.getId().getRefRegistroPatronal(), registroPatronal.getId().getCveSerial())){
			LOG.debug("El registro patronal ["+registroPatronal.getId().getRefRegistroPatronal()+"] ya existe asociado al serial ["+registroPatronal.getId().getCveSerial()+"]");
			return;
		}else{
			LOG.debug("Se insertara requerimiento");
			this.em.persist(requerimiento);//inserto requerimiento
			LOG.debug("Se insertara carta fiel idse");
			this.em.persist(cartasIdseFiel);//inserto cartas idse
			LOG.debug("Se insertara registro patronal");
			this.em.persist(registroPatronal);//inserto registro patronal
		}
	}
	
	
	private boolean existeRegistroPatronal(String nrp, String cveSerial){
		Query query = this.em.createQuery("select regPatron from IftRegistrosPatronale regPatron "
				+ "where regPatron.id.refRegistroPatronal=:nrp and regPatron.id.cveSerial=:cveSerial "
				+ "and regPatron.cveEstatus != 4");
		query.setParameter("nrp", nrp);
		query.setParameter("cveSerial", cveSerial);
		try{
			IftRegistrosPatronale rp = (IftRegistrosPatronale)query.getSingleResult();
			if(rp!=null)
				return true;
		}catch(NoResultException nre){
			return false;
		}catch(NonUniqueResultException nure){
			return true;
		}
		
		
		return false;
	}
	
	private void asociarRegistroPatronalesPrevios(String rfc, String cveSerialNuevo, List<String> regPatronalesAExcluir){
		Query query = this.em.createQuery("select regPatron from IftRegistrosPatronale regPatron "
				+ "where regPatron.refRfcRegistroPatronal=:rfc "
				+ "and regPatron.cveEstatus != 4");
		query.setParameter("rfc", rfc);
		
		
		List<IftRegistrosPatronale> registrosPatronales = query.getResultList();
		
		for(IftRegistrosPatronale rp:registrosPatronales){
			boolean omitirRp = omitirRegistroPatronal(rp.getId().getRefRegistroPatronal(), regPatronalesAExcluir);
			if(omitirRp){
				LOG.debug("No se clonara el rp");
			}else{
				IftDatosCertificado datosCert = obtenerNuevaInstanciaIftRegistrosPatronale(rp, cveSerialNuevo);
				insertarNuevoRegistroPatronal(datosCert);
				rp.setCveEstatus(new BigDecimal(4));
				this.em.merge(rp);
			}
		}
		
		
	}
	
	private boolean omitirRegistroPatronal(String rp, List<String> listaRegistrosOmitir){
		
		for(String rpOmitir:listaRegistrosOmitir){
			if(rpOmitir.equalsIgnoreCase(rp))
				return true;
		}
		return false;
	}
	
	private IftDatosCertificado obtenerNuevaInstanciaIftRegistrosPatronale(IftRegistrosPatronale persistentObj, String cveSerialNuevo){
		Long cveRequerimiento = obtenerNuevaClaveDeRequerimiento();
		
		IftRegistrosPatronale noPersistentObject = new IftRegistrosPatronale();
		IftRegistrosPatronalePK noPersistentObjectPK = new IftRegistrosPatronalePK();
		
		noPersistentObjectPK.setCveRequerimiento(cveRequerimiento);
		noPersistentObjectPK.setCveSerial(cveSerialNuevo);
		noPersistentObjectPK.setRefRegistroPatronal(persistentObj.getId().getRefRegistroPatronal());
		
		noPersistentObject.setId(noPersistentObjectPK);
		noPersistentObject.setCveEstatus(new BigDecimal(2));
		noPersistentObject.setCveTipoPersona(persistentObj.getCveTipoPersona());
		noPersistentObject.setRefRazonSocial(persistentObj.getRefRazonSocial());
		noPersistentObject.setRefRfcRegistroPatronal(persistentObj.getRefRfcRegistroPatronal());
		noPersistentObject.setRefUsuarioSubdelAutoriza(persistentObj.getRefUsuarioSubdelAutoriza());
		noPersistentObject.setStpFechaActivacion(persistentObj.getStpFechaActivacion());
		noPersistentObject.setStpFechaRecepcion(persistentObj.getStpFechaRecepcion());
		
		
		IfrCertificadoReq ifrCertReq = new IfrCertificadoReq();
		
		IfrCertificadoReqPK ifrCertReqPK= new IfrCertificadoReqPK();
		ifrCertReqPK.setCveRequerimiento(cveRequerimiento);
		ifrCertReqPK.setCveSerial(cveSerialNuevo);
		
		IfrCartasIdseFiel cartasIdse = new IfrCartasIdseFiel();
		
		IfrCartasIdseFielPK cartasIdsePK = new IfrCartasIdseFielPK();
		cartasIdsePK.setCveRequerimiento(cveRequerimiento);
		cartasIdsePK.setCveSerial(cveSerialNuevo);
		
		
		cartasIdse.setId(cartasIdsePK);
		
		List<IftRegistrosPatronale> regPatronales = new ArrayList<IftRegistrosPatronale>();
		regPatronales.add(noPersistentObject);
		
		
		
		
		ifrCertReq.setPk(ifrCertReqPK);
		ifrCertReq.setIfrCartasIdseFiel(cartasIdse);
		ifrCertReq.setIftRegistrosPatronales(regPatronales);
		
		
		List<IfrCertificadoReq> datosReq=new ArrayList<IfrCertificadoReq>();
		datosReq.add(ifrCertReq);
		
		
		IftDatosCertificado datosNuevocert = new IftDatosCertificado();
		datosNuevocert.setIfrCertificadoReqs(datosReq);
		return datosNuevocert;
	}
	
	
}
