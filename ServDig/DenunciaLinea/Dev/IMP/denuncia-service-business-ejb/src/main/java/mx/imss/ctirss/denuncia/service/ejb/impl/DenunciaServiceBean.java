package mx.imss.ctirss.denuncia.service.ejb.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;
import mx.imss.ctirss.base.model.AbstractDltFormapagoPK;
import mx.imss.ctirss.base.model.AbstractDltMotivodenunciaPK;
import mx.imss.ctirss.catalogos.model.DlcGrupo;
import mx.imss.ctirss.catalogos.model.DlcMotivodenuncia;
import mx.imss.ctirss.catalogos.model.DlcStatus;
import mx.imss.ctirss.catalogos.model.DlcSubdelegacion;
import mx.imss.ctirss.catalogos.model.DlcTipodenunciante;
import mx.imss.ctirss.catalogos.model.DlcTipodocumento;
import mx.imss.ctirss.catalogos.model.DlcUsuario;
import mx.imss.ctirss.catalogos.model.DlcUsuarioFuncionario;
import mx.imss.ctirss.denuncia.service.ejb.DenunciaServiceRemote;
import mx.imss.ctirss.denuncia.service.ejb.dao.DenunciaDAOBean;
import mx.imss.ctirss.denuncia.service.ejb.dao.DenunciaDAOLocal;
import mx.imss.ctirss.denuncia.vo.DatosCentroTrabajoVO;
import mx.imss.ctirss.denuncia.vo.DatosPatronVO;
import mx.imss.ctirss.denuncia.vo.DenunciaVO;
import mx.imss.ctirss.denuncia.vo.DenunciaVODT;
import mx.imss.ctirss.denuncia.vo.DenuncianteVO;
import mx.imss.ctirss.denuncia.vo.FormaPagoVO;
import mx.imss.ctirss.denuncia.vo.MotivoDenVO;
import mx.imss.ctirss.denuncia.vo.PatronSecundarioVO;
import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.framework.base.service.AbstractService;
import mx.imss.ctirss.framework.utils.ConstantesBusiness;
import mx.imss.ctirss.model.DltDatospatron;
import mx.imss.ctirss.model.DltDenuncia;
import mx.imss.ctirss.model.DltDerivaSub;
import mx.imss.ctirss.model.DltFormapago;
import mx.imss.ctirss.model.DltInfotrabajo;
import mx.imss.ctirss.model.DltMotivodenuncia;
import mx.imss.ctirss.model.DltNroFolio;
import mx.imss.ctirss.model.DltPersona;
import mx.imss.ctirss.model.DltUsuarioden;
import mx.imss.ctirss.service.ejb.dao.CatalogoDAOLocal;
import mx.imss.ctirss.session.UserSession;
import mx.imss.ctirss.utils.Functions;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;



@Stateless(name="denunciaServiceDL", mappedName = "denunciaServiceDL") 
public class DenunciaServiceBean <T extends AbstractModel> extends AbstractService  implements DenunciaServiceRemote<T>{

	/**
	 * Logger
	 */
	private final static Logger logger = Logger.getLogger(DenunciaServiceBean.class);
	
	@EJB DenunciaDAOLocal daoDenuncia;

	@EJB CatalogoDAOLocal<T> daoDelta;
	
	@EJB CatalogoDAOLocal daoService;
	
	public List<T> consultaLibrePorClave(Long claveConsultar, String query) {
		return daoDelta.consultaLibrePorClave(claveConsultar, query);
	}
	
	@Override
	public DltDenuncia saveDenuncia(DltDenuncia denuncia, UserSession usuarioDenuncia){
		logger.debug("actualizando denuncia" );	
		
        try{ 
		     
			if(usuarioDenuncia!=null){
				denuncia.setCveUsuarioden(usuarioDenuncia.getCveIdUsuario());
				//denuncia.setDltUsuarioden(usuarioDenuncia);
			}
			denuncia.setFecFechareg( new Date());
			denuncia.setFecRegistro( new Date());

			daoDenuncia.agrega(setFieldsBeforeInsert(denuncia));
			        
		 }catch(Exception e){        	
        	e.printStackTrace();        	
        }        
        denuncia = actualizaNumFolioDenuncia(denuncia);
                
		return denuncia;
	}
	
	
	
	
	public DltDenuncia actualizaNumFolioDenuncia(DltDenuncia dltDenuncia){
		try{			
			String str = dltDenuncia.getNumFoliodenuncia();
				
			if( str != null && !str.contains(ConstantesBusiness.PREFIJO_FOLIOS_SUB_DENUNCIA)){
				str = generaNumFolioDenuncia(dltDenuncia);
				dltDenuncia.setNumFoliodenuncia(str);				
		        dltDenuncia = (DltDenuncia)daoDenuncia.actualiza(setFieldsBeforeUpdate(dltDenuncia));	       
			}
		}catch(Exception e){
			logger.error(e.getMessage());
	        e.printStackTrace();
	     }
        
        
        return dltDenuncia;
	}
	
	@Override
	public DltDenuncia updateDenuncia(DltDenuncia denuncia, UserSession usuarioDenuncia){
		logger.debug("actualizando denuncia" );
		String date2 = ConstantesBusiness.dateToStringFormat(new Date(), ConstantesBusiness.dd_MM_yyyy_HH_mm_ss);

		 try{			
			 
			
			
				denuncia.setCveUsuarioden(usuarioDenuncia.getCveIdUsuario());												
				//denuncia.setDltUsuarioden(usuarioDenuncia);
			
			 
				 			 							
			 daoDenuncia.actualiza(setFieldsBeforeUpdate(denuncia));
			 
		 }catch(Exception e){
	        	e.printStackTrace();
	        }
		return denuncia;
	}
	
	
	//Formato de los folios DENddmmaahhmm99999}
	@Override
	public String generaNumFolioDenuncia(DltDenuncia dltDenuncia){
        StringBuffer sbFolio = new StringBuffer();        
        String date = ConstantesBusiness.dateToStringFormat(new Date(), ConstantesBusiness.DD_MM_YYYY);
        
        sbFolio.append(ConstantesBusiness.PREFIJO_FOLIOS_DENUNCIA);
        sbFolio.append(date.substring(0, 2));
        sbFolio.append(date.substring(3, 5));
        sbFolio.append(date.substring(6));
        
		ArrayList lista = (ArrayList)this.consultaLibrePorClave(0L,"from DltMotivodenuncia p where p.dltDenuncia.cveFoliodenuncia = " + dltDenuncia.getCveFoliodenuncia());
		
		if(lista != null && lista.size() > 0){										
			if(lista.size() == 1){
			    Iterator it = lista.iterator();		
			    
			    while(it.hasNext()){
				    DltMotivodenuncia mtv = (DltMotivodenuncia)it.next();
				    int i = new Long (mtv.getId().getCveMotivodenuncia()).intValue();
		        	switch (i) {
						case 1:
							sbFolio.append(ConstantesBusiness.MOTIVOS_DENUNCIA_NO_AFILIADO);
							break;
						case 2:
							sbFolio.append(ConstantesBusiness.MOTIVOS_DENUNCIA_AFILIADO_POST);
							break;
						case 3:
							sbFolio.append(ConstantesBusiness.MOTIVOS_DENUNCIA_AFILIADO_SAL_MENOR);
							break;
						case 4:
							sbFolio.append(ConstantesBusiness.MOTIVOS_DENUNCIA_SIN_AVISO_BAJA);
							break;									
						default:					
							break;
						}		    	
				    }
			    }else{	    		
			    	sbFolio.append(ConstantesBusiness.MOTIVOS_DENUNCIA_MULTIPLES);	
			    }	
		}
       
        sbFolio.append(dltDenuncia.getCveFoliodenuncia()==null?"":dltDenuncia.getCveFoliodenuncia());
		return sbFolio.toString();
	}
	
	public String generaSubFolioDenuncia(DltDenuncia dltDenuncia){
		StringBuffer sb = new StringBuffer();
		int i =0;
		try{
//			if(dltDenuncia != null && dltDenuncia.getCveSeqSubfolio() != null){								
//				sb.append(dltDenuncia.getNumFoliodenuncia());
//				sb.append(ConstantesBusiness.PREFIJO_FOLIOS_SUB_DENUNCIA);
//
//				if(dltDenuncia.getCveSeqSubfolio().intValue() == 0){
//					sb.append("01");
//					i++;
//					dltDenuncia.setCveSeqSubfolio( new BigDecimal(i));
//				}else{					
//					i = dltDenuncia.getCveSeqSubfolio().intValue();
//					i++;
//					dltDenuncia.setCveSeqSubfolio( new BigDecimal(i));
//					sb.append(dltDenuncia.getCveSeqSubfolio().toPlainString().length()==1?"0" + dltDenuncia.getCveSeqSubfolio().toPlainString():dltDenuncia.getCveSeqSubfolio().toPlainString());
//				}
//				
//				//para actualizar el seq de subfolios
//				this.updateDenuncia(dltDenuncia, null);
//				
//			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}		
		return sb.toString();
	}
	
	

	@Override
	public DltDenuncia generaDenunciaPatronComplemento(DltDenuncia denuncia, DltDatospatron dltDatospatron, UserSession usuario) {

		String subFolioDenuncia = this.generaSubFolioDenuncia(denuncia);
		
		DltDenuncia subDenuncia = new DltDenuncia();
		subDenuncia.setNumFoliodenuncia(subFolioDenuncia);
		
		
		
		
		ArrayList catalogoD = (ArrayList)this.consultaLibrePorClave(0L, "from DlcTipodenunciante dlcTipodenunciante where dlcTipodenunciante.cveTipodenunciante = '" 
		                                                                                                                      + denuncia.getCveTipodenunciante() +"'");
		if(catalogoD.size() == 1){
			subDenuncia.setDlcTipodenunciante((DlcTipodenunciante)catalogoD.get(0));
			subDenuncia.setCveTipodenunciante(denuncia.getCveTipodenunciante());
		}
		
		DlcStatus dlcStatus = new DlcStatus();
				
		ArrayList catStat = (ArrayList)this.consultaLibrePorClave(0L, "from DlcStatus dlcStatus where dlcStatus.idStatus = " + new Long(1) );	
		if(catStat.size() == 1){
			dlcStatus = (DlcStatus)catStat.get(0);
		}
		
		subDenuncia.setDlcStatus(dlcStatus);
		subDenuncia.setIdStatus(new Long(1));
		//subDenuncia.setCveSeqSubfolio(new BigDecimal(0));		
				
		subDenuncia = this.saveDenuncia(subDenuncia, usuario);
		
		return subDenuncia;
	}
	

	@Override
	public List<DltDenuncia> getDenuncias(DltUsuarioden dltUsuarioden) {	
		List<DltDenuncia> denunciasTotales = new ArrayList<DltDenuncia>();
		List<DltUsuarioden> dltUsuariodens = daoDenuncia.findUsrDenByMail(dltUsuarioden.getDesEmail());
		
		Iterator it = dltUsuariodens.iterator();
		
		while(it.hasNext()){
			
			DltUsuarioden usrDen = (DltUsuarioden)it.next();
			List<DltDenuncia> denuncias = daoDenuncia.findDenunciaByUsrDen(usrDen);
			if(denuncias.size()>=1){
				denunciasTotales.addAll(denuncias);
			}
		}
		
		return denunciasTotales;
	}
	
	
	@Override
	public DltDenuncia actualizaNumFolio(DltDenuncia dltDenuncia){

		StringBuffer sbNvoNumFolio = new StringBuffer();
		String strNumFolio = dltDenuncia.getNumFoliodenuncia();
		
		
		sbNvoNumFolio.append(strNumFolio.substring(0,11));	
		String idDenuncia = dltDenuncia.getCveFoliodenuncia().toString();
		ArrayList lista = (ArrayList)this.consultaLibrePorClave(0L,"from DltMotivodenuncia p where p.dltDenuncia.cveFoliodenuncia = "+ dltDenuncia.getCveFoliodenuncia());
		if(lista != null && lista.size() > 0){							
			if(lista.size() == 1){
			    Iterator it = lista.iterator();				    	
			    while(it.hasNext()){
				    DltMotivodenuncia mtv = (DltMotivodenuncia)it.next();
				    int i = new Long (mtv.getId().getCveMotivodenuncia()).intValue();
		        	switch (i) {
						case 1:
							sbNvoNumFolio.append(ConstantesBusiness.MOTIVOS_DENUNCIA_NO_AFILIADO);
							break;
						case 2:
							sbNvoNumFolio.append(ConstantesBusiness.MOTIVOS_DENUNCIA_AFILIADO_POST);
							break;
						case 3:
							sbNvoNumFolio.append(ConstantesBusiness.MOTIVOS_DENUNCIA_AFILIADO_SAL_MENOR);
							break;
						case 4:
							sbNvoNumFolio.append(ConstantesBusiness.MOTIVOS_DENUNCIA_SIN_AVISO_BAJA);
							break;									
						default:					
							break;
						}		    	
				    }
			    }else if(lista.size() > 1){	    		
			    	sbNvoNumFolio.append(ConstantesBusiness.MOTIVOS_DENUNCIA_MULTIPLES);	
			    }	    
		    
		    sbNvoNumFolio.append(idDenuncia);	
		    
			dltDenuncia.setNumFoliodenuncia(sbNvoNumFolio.toString());
		    dltDenuncia = (DltDenuncia)updateDenuncia(dltDenuncia, null);		
	    }
		
		return dltDenuncia;
	}
	
	
	@Override
	public DenunciaVO ratificaDenuncia(DenunciaVO denunciaVO,
			UserSession usuario) {
		// TODO Auto-generated method stub
		DltDenuncia de=null;
		if(denunciaVO.getCveDenuncia()!=null &&  denunciaVO.getCveEstatus().intValue()!=ConstantesBusiness.ESTATUS_RATIFICADA.intValue()){
			de=daoDenuncia.getDenunciaByClaveFolio(denunciaVO.getCveDenuncia());
			de.setDesAclaracion(denunciaVO.getAclaraciones());
			DlcStatus estatus=new DlcStatus();
			estatus.setIdStatus(Long.valueOf(ConstantesBusiness.ESTATUS_RATIFICADA.intValue()));
			de.setDlcStatus(estatus);
			denunciaVO.setCveEstatus(ConstantesBusiness.ESTATUS_RATIFICADA.intValue());
			daoService.actualiza(de);
		}
		return denunciaVO;
	}

	@Override
	public DenunciaVO guardarDenuncia(DenunciaVO denunciaVO,UserSession usuario) {
		// TODO Auto-generated method stub
		//se consulta si existe la denuncia
		//denunciaVO.s
		DltDenuncia de=null;
//		PatronSecundarioVO sec=new PatronSecundarioVO();
//		sec.setNombreRazonSocial("Razon sociual secundaria");
//		sec.setRfc("RFCSEc");
//		
//		denunciaVO.getDatosPatronVO().getPatronesSecundarios().add(sec);
//		
//		
//		PatronSecundarioVO seca=new PatronSecundarioVO();
//		seca.setNombreRazonSocial("Razon ss");
//		seca.setRfc("RCSEc");
//		//denunciaVO.getDatosPatronVO().getPatronesSecundarios().add(seca);
//		denunciaVO.setCveDenuncia(3311l);
		
		
		if(denunciaVO.getCveDenuncia()!=null){
			de=daoDenuncia.getDenunciaByClaveFolio(denunciaVO.getCveDenuncia());
		}
		if(de!=null){
			//Actualizacion
			guardarDatosTrabajador(denunciaVO,de,true,usuario);
			guardarDatosPatron(denunciaVO,de,true);
			guardarDatosCentroTrabajo(denunciaVO,de,true);
		}else{
			//Genera Nuevo Registros
			de=guardarDatosTrabajador(denunciaVO,de,false,usuario);
			guardarDatosPatron(denunciaVO,de,false);
			guardarDatosCentroTrabajo(denunciaVO,de,false);
		}
		

		if(denunciaVO.isFinalizado()){
			de=generaFolio(de,denunciaVO);
			denunciaVO.setFolioDenuncia(de.getNumFoliodenuncia());
		}
		denunciaVO.setCveDenuncia(de.getCveFoliodenuncia());
		return denunciaVO;
	}
	
	private DltDenuncia generaFolio(DltDenuncia dltDenuncia,DenunciaVO denunciaVO){
		
		
		int motivo;
        StringBuffer folio = new StringBuffer();  
        String fol=null;
        int numSub=0;
        SimpleDateFormat formatter = new SimpleDateFormat(ConstantesBusiness.FORMATO_FECHA_FOLIO);
		String stringDate = formatter.format(Calendar.getInstance().getTime());
		DecimalFormat formatFolio = new DecimalFormat("000000");
		DecimalFormat formatSubfolio = new DecimalFormat("00");
		
        folio.append(ConstantesBusiness.PREFIJO_FOLIOS_DENUNCIA);     	
		folio.append(stringDate);
		if(!denunciaVO.getDatosTrabajadorVO().getMotivosDenuncia().isEmpty() && denunciaVO.getDatosTrabajadorVO().getMotivosDenuncia().size()==1){
			folio.append(denunciaVO.getDatosTrabajadorVO().getMotivosDenuncia().get(0).getCveMotivoDenuncia());
			motivo=denunciaVO.getDatosTrabajadorVO().getMotivosDenuncia().get(0).getCveMotivoDenuncia();
		}else{
			folio.append(ConstantesBusiness.MOTIVOS_DENUNCIA_MULTIPLES);
			motivo=Integer.valueOf(ConstantesBusiness.MOTIVOS_DENUNCIA_MULTIPLES);
		}	
		
		folio.append(formatFolio.format(recuperaConsecutivo(Long.valueOf(motivo))));

		List<DltDenuncia> sb=daoDenuncia.getSubdenuncias(dltDenuncia);
		for(DltDenuncia subDen:sb){
			fol=folio.toString()+ConstantesBusiness.PREFIJO_FOLIOS_SUB_DENUNCIA+formatSubfolio.format(numSub);
			subDen.setNumFoliodenuncia(fol);
			numSub++;
			daoService.actualiza(subDen);
		}
		dltDenuncia.setNumFoliodenuncia(folio.toString());
		dltDenuncia.setFecFechaenv(Calendar.getInstance().getTime());
		DlcStatus esta=new DlcStatus();
		esta.setIdStatus(Long.valueOf(ConstantesBusiness.ENVIADA_PARA_RATIFICACION));
		
		dltDenuncia.setDlcStatus(esta);
		daoService.actualiza(dltDenuncia);
		return dltDenuncia;
	}
	
	private Long recuperaConsecutivo(Long motivoDenuncia){
		
		Calendar today = Calendar.getInstance();
		DltNroFolio sigFolio=daoDenuncia.recuperaSiguienteFolio(Long.valueOf(today.get(Calendar.YEAR)));
		DltNroFolio nroFolio=new DltNroFolio();
		if(sigFolio==null){
			nroFolio.setNumAnio(Long.valueOf(today.get(Calendar.YEAR)));
			nroFolio.setNumNumero(0L);
			nroFolio.setFecFechareg(Calendar.getInstance().getTime());
			nroFolio.setCveMotivoDenuncia(motivoDenuncia);
		}else{
			nroFolio.setNumNumero(sigFolio.getNumNumero().longValue()+1);
			nroFolio.setNumAnio(Long.valueOf(today.get(Calendar.YEAR)));
			nroFolio.setFecFechareg(Calendar.getInstance().getTime());
			nroFolio.setCveMotivoDenuncia(motivoDenuncia);
		}
		
		daoService.agrega(nroFolio);
		return nroFolio.getNumNumero();
	}
	
	private DltDenuncia guardarDatosTrabajador(DenunciaVO denunciaVO,DltDenuncia de,boolean update,UserSession userSession){
		
		//nueva denuncia
		if(!update){	//Alta
			return generaNuevaDenuncia(denunciaVO, de,userSession);
		}else{
			//actualizacion
			return actualizaDenuncia(denunciaVO, de);
		}
	}
	
	private DltDenuncia actualizaDenuncia(DenunciaVO denunciaVO,DltDenuncia de){
		//Actualizar Denuncia
		
		
		//Actualizar Trabajadores		
		DlcTipodenunciante tipo=new DlcTipodenunciante();
		tipo.setCveTipodenunciante(denunciaVO.getTipoDenunciante());
		de.setDlcTipodenunciante(tipo);		
		de.setDesObservaciones(denunciaVO.getDatosTrabajadorVO().getObservaciones());
		
		System.out.print("El sexo de dato tomado es "+denunciaVO.getDatosTrabajadorVO().getTrabajador().getSexoTrabajador());
		
		Set<DltPersona> personas=de.getDltPersonas();
		if(denunciaVO.getCveSubdelegacion()!=null){
			DlcSubdelegacion su=new DlcSubdelegacion();
			su.setCveSubdelegacion(new Long(denunciaVO.getCveSubdelegacion()));
			de.setDlcSubdelegacion(su);
			
			//de.nueva = denunciavo.datostrabajadorvo.sexo
			
			de.getPersona().setSexoTrabajador(denunciaVO.getDatosTrabajadorVO().getTrabajador().getSexoTrabajador());
			
			System.out.print("El sexo ingresado es "+de.getPersona().getSexoTrabajador());	
			
			daoService.actualiza(de);
		}
	
		Iterator<DltPersona> ite=null;
		logger.info("Valor de clave "+denunciaVO.getTipoDenunciante().intValue());
		switch (denunciaVO.getTipoDenunciante().intValue()) {
			case ConstantesBusiness.TRABAJADOR:
					ite=personas.iterator();
					while(ite.hasNext()){
						DltPersona per=ite.next();
						if(per.getCveTipodenunciante().intValue()==ConstantesBusiness.TRABAJADOR){
							//Se actualiza bd
							per=parserPersona(per, denunciaVO.getDatosTrabajadorVO().getTrabajador());
							daoService.actualiza(per);
						}else{
							//Se borra
							daoService.elimina(per);
						}
					}
				break;
			case ConstantesBusiness.BENEFICIARIO:
				ite=personas.iterator();
				boolean flagBene=false;
				while(ite.hasNext()){
					DltPersona per=ite.next();
					if(per.getCveTipodenunciante().intValue()==ConstantesBusiness.TRABAJADOR){
						//Se actualiza bd
						per=parserPersona(per, denunciaVO.getDatosTrabajadorVO().getTrabajador());
						daoService.actualiza(per);				
					}else if(per.getCveTipodenunciante().intValue()==ConstantesBusiness.BENEFICIARIO){
						per=parserPersona(per, denunciaVO.getDatosTrabajadorVO().getBeneficiario());
						flagBene=true;
						daoService.actualiza(per);
					}else{
						//Se borra
						daoService.elimina(per);
					}
				}
				if(!flagBene){					
					daoService.agrega(generaPersona(denunciaVO.getDatosTrabajadorVO().getBeneficiario(), de, ConstantesBusiness.BENEFICIARIO));
				}
				
				break;
			case ConstantesBusiness.REPRESENTANTE:
				ite=personas.iterator();
				boolean flagRepre=false;
				while(ite.hasNext()){
					DltPersona per=ite.next();
					if(per.getCveTipodenunciante().intValue()==ConstantesBusiness.TRABAJADOR){
						//Se actualiza bd
						per=parserPersona(per, denunciaVO.getDatosTrabajadorVO().getTrabajador());
						daoService.actualiza(per);					
					}else if(per.getCveTipodenunciante().intValue()==ConstantesBusiness.REPRESENTANTE){
						per=parserPersona(per, denunciaVO.getDatosTrabajadorVO().getRepresentanteLegal());
						flagRepre=true;
						daoService.actualiza(per);
					}else{
						//Se borra
						daoService.elimina(per);
					}
				}
				if(!flagRepre){
					daoService.agrega(generaPersona(denunciaVO.getDatosTrabajadorVO().getRepresentanteLegal(), de, ConstantesBusiness.REPRESENTANTE));
				}
				break;
		}
		
		//Actualizacion motivos denuncia
		logger.info("Total motivos denucnia "+de.getDltMotivodenuncias().size());		
		HashMap<Long, DltMotivodenuncia> motivosDenc=new HashMap<Long, DltMotivodenuncia>();		
		for(DltMotivodenuncia mtd:de.getDltMotivodenuncias()){
			motivosDenc.put(mtd.getId().getCveMotivodenuncia(),mtd);
		}		
		DltMotivodenuncia mtDenMD=null;
		logger.info("Motivos denuncia actualizar "+denunciaVO.getDatosTrabajadorVO().getMotivosDenuncia().size());
		for(MotivoDenVO mtDenc:denunciaVO.getDatosTrabajadorVO().getMotivosDenuncia()){
			mtDenMD=motivosDenc.get(Long.valueOf(mtDenc.getCveMotivoDenuncia()));
			if(mtDenMD!=null){
				//Se actualiza
				mtDenMD.setFecLabalDejolab(mtDenc.getFechaLabDejoLab()!=null ? Functions.stringToDateVal(mtDenc.getFechaLabDejoLab()):null);
				mtDenMD.setFecLabdelIngreso(mtDenc.getFechaLabDelIngreso()!=null ? Functions.stringToDateVal(mtDenc.getFechaLabDelIngreso()):null);
				mtDenMD.setImpSalarioReal(convierteBigDecimal(mtDenc.getImpoSalarioReal()));
				mtDenMD.setImpSalarioReg(convierteBigDecimal(mtDenc.getImpoSalarioReg()));				
				daoService.actualiza(mtDenMD);
				motivosDenc.remove(mtDenMD.getId().getCveMotivodenuncia());
				logger.info("motivosDenc size "+motivosDenc.size());
			}else{
				//Se crea uno nuevo
				generaMotivoDenuncia(mtDenc,de);
				
			}
		
		}
		
		//si sobran elmentos se borran	
		for (Entry<Long, DltMotivodenuncia> entry : motivosDenc.entrySet()) {
			daoService.elimina(entry.getValue());
		}	
		return de;
	}
	
	
	
	
	
	
	private DltPersona parserPersona(DltPersona persona,DenuncianteVO denunciante){	
		logger.info("Parser Persona "+persona.getCvePersona());
		persona.setCveCurp(denunciante.getCurp());
		if(denunciante.isTieneNss() && (denunciante.getNss().equals("") || denunciante.getNss()==null)){
			persona.setCveNss(" ");
		}else{
			persona.setCveNss(denunciante.getNss());	
		}
		
		System.out.println("oersiba "+persona.getCvePersona());
		persona.setCveRfc(denunciante.getRfc());
		persona.setDesEmail(denunciante.getEmail());
		persona.setDesMaterno(denunciante.getApellidoMaterno());
		persona.setDesNombre(denunciante.getNombre());
		persona.setDesPaterno(denunciante.getApellidoPaterno());
		persona.setNumCelular(denunciante.getTelefonoCelular());
		persona.setNumTelefono(denunciante.getTelefonoContacto());
		persona.setNumDocumento(denunciante.getNumeroDocumento());
		persona.setSexoTrabajador(denunciante.getSexoTrabajador());
		
		if(denunciante.getDocOficial()!=-1){
			DlcTipodocumento tipo=new DlcTipodocumento();
			tipo.setCveTipodocumento(new Long(denunciante.getDocOficial()));
			persona.setDlcTipodocumento(tipo);
		}
		
	
		
		if(denunciante.getIdDomicilio()!=null){
			DgDomicilioGeografico dom =  new DgDomicilioGeografico();
			dom.setDomicilioId(denunciante.getIdDomicilio());
			persona.setDgDomicilioGeografico(dom);	
		}else{
			persona.setDgDomicilioGeografico(null);	
		}
		
		return persona;
	}
	
	@SuppressWarnings("all")
	private DltDenuncia generaNuevaDenuncia(DenunciaVO denunciaVO,DltDenuncia de,UserSession userSess){

		DltDenuncia dltDenuncia =new DltDenuncia();	
		
		DlcTipodenunciante tipoDen=new DlcTipodenunciante();
		DlcStatus dlcStatus=null;
		tipoDen.setCveTipodenunciante(denunciaVO.getTipoDenunciante());				
		ArrayList catStat = (ArrayList)daoDelta.consultaLibrePorClave(0L, "from DlcStatus dlcStatus where dlcStatus.idStatus = " + new Long(ConstantesBusiness.ESTATUS_EN_ELABORACION));
		if(!catStat.isEmpty()){
			dlcStatus = (DlcStatus)catStat.get(0);
		}
		
		dltDenuncia.setDlcTipodenunciante(tipoDen);
		dltDenuncia.setDlcStatus(dlcStatus);
		dltDenuncia.setFecRegistro(Calendar.getInstance().getTime());
		dltDenuncia.setFecFechareg(Calendar.getInstance().getTime());
		dltDenuncia.setDesObservaciones(denunciaVO.getDatosTrabajadorVO().getObservaciones());	
		if(denunciaVO.getCveSubdelegacion()!=null){
			DlcSubdelegacion sub=new DlcSubdelegacion();
			sub.setCveSubdelegacion(new Long(denunciaVO.getCveSubdelegacion()));
			dltDenuncia.setDlcSubdelegacion(sub);
		}
	
	
		if(userSess.getIdTipoUsuario()==ConstantesBusiness.TIPO_USUARIO_DENUNCIA){
			dltDenuncia.setCveUsuarioden(userSess.getCveIdUsuario());
			DltUsuarioden userDen=new DltUsuarioden();
			userDen.setCveUsuarioden(userSess.getCveIdUsuario());
			dltDenuncia.setDltUsuarioden(userDen);
		}else if(userSess.getIdTipoUsuario()==ConstantesBusiness.TIPO_USUARIO_SUB){
			DlcUsuarioFuncionario dlcUsuarioFuncionario=new DlcUsuarioFuncionario();
			dlcUsuarioFuncionario.setCveIdUsuarioFuncionario(userSess.getCveIdUsuario());
			dltDenuncia.setDlcUsuarioFuncionario(dlcUsuarioFuncionario);
			dltDenuncia.setDesAclaracion(denunciaVO.getAclaraciones());
		}
		
		daoDenuncia.agrega(dltDenuncia);
		
		switch(denunciaVO.getTipoDenunciante().intValue()){
			case ConstantesBusiness.TRABAJADOR:
				daoService.agrega(generaPersona(denunciaVO.getDatosTrabajadorVO().getTrabajador(),dltDenuncia,ConstantesBusiness.TRABAJADOR));
				break;
			case ConstantesBusiness.BENEFICIARIO:
				daoService.agrega(generaPersona(denunciaVO.getDatosTrabajadorVO().getTrabajador(),dltDenuncia,ConstantesBusiness.TRABAJADOR));
				daoService.agrega(generaPersona(denunciaVO.getDatosTrabajadorVO().getBeneficiario(),dltDenuncia,ConstantesBusiness.BENEFICIARIO));
				break;
			case ConstantesBusiness.REPRESENTANTE:
				daoService.agrega(generaPersona(denunciaVO.getDatosTrabajadorVO().getTrabajador(),dltDenuncia,ConstantesBusiness.TRABAJADOR));
				daoService.agrega(generaPersona(denunciaVO.getDatosTrabajadorVO().getRepresentanteLegal(),dltDenuncia,ConstantesBusiness.REPRESENTANTE));
				break;
		}	
		
		List<MotivoDenVO> motivosDenuncia=denunciaVO.getDatosTrabajadorVO().getMotivosDenuncia();
		DltMotivodenuncia motDen=null;
		DlcMotivodenuncia catMotivoDen=new DlcMotivodenuncia();
		AbstractDltMotivodenunciaPK motivoDenPk=new AbstractDltMotivodenunciaPK();
		for(MotivoDenVO mot:motivosDenuncia){
			generaMotivoDenuncia(mot,dltDenuncia);
		}
		return dltDenuncia;
	}
	
	
	private void generaMotivoDenuncia(MotivoDenVO mot,DltDenuncia dltDenuncia){
		
		DltMotivodenuncia motDen=null;
		DlcMotivodenuncia catMotivoDen=new DlcMotivodenuncia();
		AbstractDltMotivodenunciaPK motivoDenPk=new AbstractDltMotivodenunciaPK();
		motDen=new DltMotivodenuncia();
		switch(mot.getCveMotivoDenuncia()){
			case ConstantesBusiness.SU_PATRON_NO_LO_AFILIO_AL_SEGURO_SOCIAL:
				motDen.setFecLabdelIngreso(Functions.stringToDateVal(mot.getFechaLabDelIngreso()));
				motDen.setFecLabalDejolab(Functions.stringToDateVal(mot.getFechaLabDejoLab()));
				catMotivoDen.setCveMotivodenuncia(Long.valueOf(ConstantesBusiness.SU_PATRON_NO_LO_AFILIO_AL_SEGURO_SOCIAL));
				motDen.setDlcMotivodenuncia(catMotivoDen);
				motivoDenPk.setCveMotivodenuncia(Long.valueOf(ConstantesBusiness.SU_PATRON_NO_LO_AFILIO_AL_SEGURO_SOCIAL));
				break;
			case ConstantesBusiness.SU_PATRON_LO_AFILIO_CON_FECHA_POSTERIOR_AL_INGRESO:
				motDen.setFecLabdelIngreso(Functions.stringToDateVal(mot.getFechaLabDelIngreso()));
				motDen.setFecLabalDejolab(Functions.stringToDateVal(mot.getFechaLabDejoLab()));
				catMotivoDen.setCveMotivodenuncia(Long.valueOf(ConstantesBusiness.SU_PATRON_LO_AFILIO_CON_FECHA_POSTERIOR_AL_INGRESO));
				motDen.setDlcMotivodenuncia(catMotivoDen);
				motivoDenPk.setCveMotivodenuncia(Long.valueOf(ConstantesBusiness.SU_PATRON_LO_AFILIO_CON_FECHA_POSTERIOR_AL_INGRESO));
				break;
			case ConstantesBusiness.SU_PATRON_LO_AFILIO_CON_UN_SALARIO_INFERIOR_AL_REA:
				motDen.setImpSalarioReal(convierteBigDecimal((mot.getImpoSalarioReal()!=null || mot.getImpoSalarioReal()!="")?mot.getImpoSalarioReal():"0.0"));
				motDen.setImpSalarioReg(convierteBigDecimal((mot.getImpoSalarioReg()!=null || mot.getImpoSalarioReg()!="")?mot.getImpoSalarioReg():"0.0"));
				catMotivoDen.setCveMotivodenuncia(Long.valueOf(ConstantesBusiness.SU_PATRON_LO_AFILIO_CON_UN_SALARIO_INFERIOR_AL_REA));
				motDen.setDlcMotivodenuncia(catMotivoDen);
				motivoDenPk.setCveMotivodenuncia(Long.valueOf(ConstantesBusiness.SU_PATRON_LO_AFILIO_CON_UN_SALARIO_INFERIOR_AL_REA));
				break;
			case ConstantesBusiness.EL_PATRON_SE_NIEGA_A_PRESENTAR_EL_AVISO_DE_BAJA_DE:					
				motDen.setFecLabalDejolab(Functions.stringToDateVal(mot.getFechaLabDejoLab()));
				catMotivoDen.setCveMotivodenuncia(Long.valueOf(ConstantesBusiness.EL_PATRON_SE_NIEGA_A_PRESENTAR_EL_AVISO_DE_BAJA_DE));
				motDen.setDlcMotivodenuncia(catMotivoDen);	
				motivoDenPk.setCveMotivodenuncia(Long.valueOf(ConstantesBusiness.EL_PATRON_SE_NIEGA_A_PRESENTAR_EL_AVISO_DE_BAJA_DE));
				break;
		}
		
		motivoDenPk.setCveFoliodenuncia(dltDenuncia.getCveFoliodenuncia());
		motDen.setId(motivoDenPk);
		motDen.setFecFechareg(Calendar.getInstance().getTime());
		motDen.setDltDenuncia(dltDenuncia);
		
		daoService.agrega(motDen);
	}
	
	
	private void guardarDatosPatron(DenunciaVO denunciaVO,DltDenuncia de,boolean update){
		DltDatospatron datosPatron=null;
		//Se actualiza
		if(update && !de.getDltInfotrabajos().isEmpty()){
			datosPatron=de.getDltDatospatrons().iterator().next();
			//Se crea
		}else{
			datosPatron=new DltDatospatron();		
		}
			DatosPatronVO infoPatron=denunciaVO.getDatosPatronVO();			
			datosPatron.setDltDenuncia(de);
			datosPatron.setCveFoliodenuncia(de.getCveFoliodenuncia());
			datosPatron.setCveStatusCorreccion(ConstantesBusiness.ESTATUS_EN_ELABORACION.intValue());
			datosPatron.setDesNomrazonsocial(infoPatron.getRazonSocial());
			datosPatron.setDesNomreplegal(infoPatron.getNombreRepresentanteLegal());
			datosPatron.setDesRfc(infoPatron.getRfc());
			datosPatron.setCveRegpat(infoPatron.getRegPat());
			if(infoPatron.getGiroPatron()!=null && !infoPatron.getGiroPatron().equals("-1") && !infoPatron.getGiroPatron().equals("") && infoPatron.getGiroPatron()!=null && !infoPatron.getGiroPatron().equals("null")){
				datosPatron.setCveActEconomica(Long.valueOf(infoPatron.getGiroPatron()));
			}
			if(infoPatron.getNumTrabajadores()!=null && !infoPatron.getNumTrabajadores().equals("")){
				logger.info("num trab='"+infoPatron.getNumTrabajadores()+"'");
				datosPatron.setNumTrabajadores(convierteBigDecimal(infoPatron.getNumTrabajadores()));	
			}
			
			datosPatron.setNumTelefono(infoPatron.getTelefonoEmpresa());
			datosPatron.setDesObservaciones(infoPatron.getObservaciones());
			datosPatron.setIndPatrondenunciado(new BigDecimal(infoPatron.getDenunciaExistente()));
			datosPatron.setIndRecibePagoTot(new BigDecimal(infoPatron.getRecibeTotalSueldoDeUnPatron()));
			datosPatron.setDomicilioId(infoPatron.getIdDomicilio());
			daoService.agrega(datosPatron);
			
			
			List<DltDenuncia> sb=daoDenuncia.getSubdenuncias(de);
			HashMap<Long, DltDenuncia> subdenuncias=new HashMap<Long, DltDenuncia>();
			for(DltDenuncia d:sb){
				subdenuncias.put(d.getCveFoliodenuncia(), d);
			}
			
			
			logger.info("Subdenuncias Asignadas"+ subdenuncias.size());
			Long cveDenuncia = null;
			logger.info("Patrones Secundarios Front "+denunciaVO.getDatosPatronVO().getPatronesSecundarios().size());
			HashMap<Long, PatronSecundarioVO> subdenunciaVista=new HashMap<Long, PatronSecundarioVO>();
			int i = 0;
			for(PatronSecundarioVO vo:denunciaVO.getDatosPatronVO().getPatronesSecundarios()){		
				 if(subdenuncias.get((long)vo.getCveDenuncia()) != null){
					 i++;
					 modificaPatronSecundario(vo);
				 }else if(vo.getCveDenuncia()==-1){
					 creaPatronSecundario(vo,de);
					 denunciaVO.getDatosPatronVO().getPatronesSecundarios().get(i).setCveDenuncia(vo.getCveDenuncia());
				 }
				 subdenunciaVista.put((long) vo.getCveDenuncia(), vo);
			}	 
			for (Entry<Long, DltDenuncia> entry : subdenuncias.entrySet()) {
				DltDatospatron patron=entry.getValue().getDltDatospatrons().iterator().next();
				if(subdenunciaVista.get(patron.getCveFoliodenuncia())==null){
					subdenuncias.remove(patron.getCveFoliodenuncia());
					daoService.elimina(entry.getValue().getDltDatospatrons().iterator().next());
					daoService.elimina(entry.getValue());
				}
			}
			
//			for (Entry<Long, DltDenuncia> entry : subdenuncias.entrySet()) {
//				daoService.elimina(entry.getValue().getDltDatospatrons().iterator().next());
//				daoService.elimina(entry.getValue());
//			}
			logger.info("Total de reistros a eliminar patrones secunadrioas "+subdenuncias.size());
		
		
	}
	
	private PatronSecundarioVO creaPatronSecundario(PatronSecundarioVO de,DltDenuncia denuncia){
			
		DltDenuncia subdenuncia=new DltDenuncia();
		subdenuncia.setCveOrigendenuncia(new BigDecimal(denuncia.getCveFoliodenuncia()));
		subdenuncia.setFecRegistro(Calendar.getInstance().getTime());
		subdenuncia.setFecFechareg(Calendar.getInstance().getTime());
		daoService.agrega(subdenuncia);
		
		DltDatospatron datospatron=new DltDatospatron();
		datospatron.setDltDenuncia(subdenuncia);
		datospatron.setDesNomrazonsocial(de.getNombreRazonSocial());
		datospatron.setDesRfc(de.getRfc());
		datospatron.setCveFoliodenuncia(subdenuncia.getCveFoliodenuncia());
		if(de.getCveDomicilio()!=null && !de.getCveDomicilio().equals("")){
			datospatron.setDomicilioId(Long.parseLong(de.getCveDomicilio()));
		}
		daoService.agrega(datospatron);
		
		de.setCveDenuncia(datospatron.getCveFoliodenuncia().intValue());
		return de;
	}
	
	private void modificaPatronSecundario(PatronSecundarioVO de){
		
		DltDatospatron datospatron=new DltDatospatron();
		datospatron.setDesNomrazonsocial(de.getNombreRazonSocial());
		datospatron.setDesRfc(de.getRfc());
		if(de.getCveDomicilio()!=null && !de.getCveDomicilio().equals("")){
			datospatron.setDomicilioId(Long.parseLong(de.getCveDomicilio()));
		}
		daoService.actualiza(datospatron);
	}
	
	private void guardarDatosCentroTrabajo(DenunciaVO denunciaVO,DltDenuncia de,boolean update){
		//Se crea nuevo registro
		DatosCentroTrabajoVO centroTrabajoVO=denunciaVO.getDatosCentroTrabajoVO();
		
		DltInfotrabajo infotrabajo=null;
	
		HashMap<Long, DltFormapago> formasPago=new HashMap<Long, DltFormapago>();
		
		if(update && !de.getDltInfotrabajos().isEmpty()){			
			infotrabajo=de.getDltInfotrabajos().iterator().next();
			//Se crea
		}else{
			infotrabajo=new DltInfotrabajo();		
		}
						
			infotrabajo.setCveFoliodenuncia(de.getCveFoliodenuncia());
			if(centroTrabajoVO.getFechaInicio()!=null){
				infotrabajo.setFecFechaInicio(Functions.stringToDateVal(centroTrabajoVO.getFechaInicio()));
			}
			if(centroTrabajoVO.getFechaTermino()!=null){
				infotrabajo.setFecFechaFin(Functions.stringToDateVal(centroTrabajoVO.getFechaTermino()));
			}
			infotrabajo.setDesLaboresdesemp(centroTrabajoVO.getActividadTrabajador());
			infotrabajo.setDesNomjefeinmediato(centroTrabajoVO.getNombreJefeInm());
			infotrabajo.setDesHorariolabores(centroTrabajoVO.getHorarioLabores());
			infotrabajo.setImpSalariopercibido(convierteBigDecimal(centroTrabajoVO.getSalario()));
			infotrabajo.setImpVacaciones(convierteBigDecimal(centroTrabajoVO.getVacaciones()));
			infotrabajo.setNumDiasvacaciones(convierteBigDecimal(centroTrabajoVO.getDiasVacaciones()));
			infotrabajo.setNumDiasAguinaldo(convierteBigDecimal(centroTrabajoVO.getDiasAguinaldo()));
			infotrabajo.setImpAguinaldo(convierteBigDecimal(centroTrabajoVO.getAguinaldoAnual()));
			infotrabajo.setImpComisionOtros(convierteBigDecimal(centroTrabajoVO.getGratificacion()));
			infotrabajo.setDesBaseComisionOtros(centroTrabajoVO.getComisiones());
			infotrabajo.setDesBaseOtorgamiento(centroTrabajoVO.getBaseComision());		
			infotrabajo.setDesObservaciones(centroTrabajoVO.getObservaciones());
			infotrabajo.setIdDomicilio(centroTrabajoVO.getIdDomicilio());
			if(centroTrabajoVO.getTuvoRiesgoTrabajo()==1){
				infotrabajo.setFecFechariesgotrab(centroTrabajoVO.getFechaRiesgoTrabajo()!=null? Functions.stringToDateVal(centroTrabajoVO.getFechaRiesgoTrabajo()):null);
			}
			
			if(centroTrabajoVO.getTieneContrato()==1){
				if(centroTrabajoVO.getNumeroContrato().equals("") || centroTrabajoVO.getNumeroContrato()==null){
					centroTrabajoVO.setNumeroContrato(" ");
				}
				infotrabajo.setDesNumcontrato(centroTrabajoVO.getNumeroContrato());
				infotrabajo.setIndContrato(new Long(centroTrabajoVO.getTieneContrato()));
			}
			daoService.agrega(infotrabajo);
			
			
			if(!update){
				//Se agregan periodos de pago
				if(centroTrabajoVO.getPagoPeriodoSal().getCveFormaPago()!=-1 && centroTrabajoVO.getPagoPeriodoSal().getCveFormaPago()!=0){				
					generaFormaPago(centroTrabajoVO.getPagoPeriodoSal(), ConstantesBusiness.TIPO_DE_PERIODO_PAGO, infotrabajo);		
				}
				
				//Se agregan tipo comprobante de pago
				if(centroTrabajoVO.getPagoComprobantePago().getCveFormaPago()!=-1 && centroTrabajoVO.getPagoComprobantePago().getCveFormaPago()!=0){				
					generaFormaPago(centroTrabajoVO.getPagoComprobantePago(),ConstantesBusiness.TIPO_DE_COMPROBANTE_PAGO, infotrabajo);
				}			
				//Se agregan formas de pago
				List<FormaPagoVO> listaForma=centroTrabajoVO.getFormasPago();
				for(FormaPagoVO vo:listaForma){
					generaFormaPago(vo,ConstantesBusiness.TIPO_DE_FORMA_PAGO, infotrabajo);
				}			
			}else{
				//estructura del modelo de datos actualizacion formas de pago
				
				DltFormapago periodoPago=null;
				DltFormapago comprobantePago=null;
				for(DltFormapago fp:infotrabajo.getDltFormapagos()){
					if(fp.getId().getCveConcepto().intValue()==ConstantesBusiness.TIPO_DE_FORMA_PAGO){
						formasPago.put(fp.getId().getCveFormapago(), fp);
					}else if(fp.getId().getCveConcepto().intValue()==ConstantesBusiness.TIPO_DE_PERIODO_PAGO){
						periodoPago=fp;
					}else if(fp.getId().getCveConcepto().intValue()==ConstantesBusiness.TIPO_DE_COMPROBANTE_PAGO){
						comprobantePago=fp;
					}
				}

				if((periodoPago!=null && periodoPago.getId().getCveFormapago().intValue()!=centroTrabajoVO.getPagoPeriodoSal().getCveFormaPago())|| periodoPago==null ){
					if(periodoPago!=null)
						daoService.elimina(periodoPago);
					generaFormaPago(centroTrabajoVO.getPagoPeriodoSal(), ConstantesBusiness.TIPO_DE_PERIODO_PAGO, infotrabajo);
				}
				
			
				
				if(comprobantePago!=null){
					if(comprobantePago.getId().getCveFormapago().intValue()!=centroTrabajoVO.getPagoComprobantePago().getCveFormaPago()){
						daoService.elimina(comprobantePago);
						generaFormaPago(centroTrabajoVO.getPagoComprobantePago(), ConstantesBusiness.TIPO_DE_COMPROBANTE_PAGO, infotrabajo);
					}
				}else{
					generaFormaPago(centroTrabajoVO.getPagoComprobantePago(), ConstantesBusiness.TIPO_DE_COMPROBANTE_PAGO, infotrabajo);
				}
				
				

				
				
				DltFormapago fp=null;
				//Estructura del front
				for(FormaPagoVO pagoVO:denunciaVO.getDatosCentroTrabajoVO().getFormasPago()){
					fp=formasPago.get(Long.valueOf(pagoVO.getCveFormaPago()));
					if(fp!=null){
					//Actualiza y se borra del mapa							
						fp.setDesEspecifique(pagoVO.getDescripcion());
						daoService.actualiza(fp);
						formasPago.remove(fp.getId().getCveFormapago());	
					}else{
					//Se genera uno nuevo
						generaFormaPago(pagoVO, ConstantesBusiness.TIPO_DE_FORMA_PAGO, infotrabajo);						
					}
				}

				for (Entry<Long, DltFormapago> entry : formasPago.entrySet()) {
					daoService.elimina(entry.getValue());
				}				
			}
		
	}
	
	private void actualizaFormaPago(FormaPagoVO vo,DltFormapago dlt){
		
	}
	
	private void generaFormaPago(FormaPagoVO vo,Long cveConcepto,DltInfotrabajo infotrabajo){
		if(vo.getCveFormaPago()<0){
			return;
		}
		AbstractDltFormapagoPK formPagoPk=new AbstractDltFormapagoPK();
		formPagoPk.setCveConcepto(cveConcepto);
		formPagoPk.setCveFormapago(Long.valueOf(vo.getCveFormaPago()));
		formPagoPk.setCveInfotrabajo(infotrabajo.getCveInfotrabajo());
		DltFormapago formapagoBD=new DltFormapago();
		formapagoBD.setId(formPagoPk);
		formapagoBD.setDesEspecifique(vo.getDescripcion());
		daoService.agrega(formapagoBD);
		
	}
	
	
	
	private DltPersona generaPersona(DenuncianteVO denunciante,DltDenuncia dltDenuncia,int tipoDenunciante){
		DltPersona persona= new DltPersona();
		DlcTipodenunciante tipoDen=new DlcTipodenunciante();
		tipoDen.setCveTipodenunciante(Long.valueOf(tipoDenunciante));			
		
		persona.setDesNombre(denunciante.getNombre());
		persona.setDesPaterno(denunciante.getApellidoPaterno());
		persona.setDesMaterno(denunciante.getApellidoMaterno());
		persona.setCveCurp(denunciante.getCurp());
		if(denunciante.isTieneNss() && (denunciante.getNss().equals("") ||denunciante.getNss()==null)){
			persona.setCveNss(" ");
		}else{
			persona.setCveNss(denunciante.getNss());	
		}
				
		persona.setCveRfc(denunciante.getRfc());
		persona.setDesEmail(denunciante.getEmail());
		persona.setNumTelefono(denunciante.getTelefonoContacto());
		persona.setNumCelular(denunciante.getTelefonoCelular());
		persona.setNumDocumento(denunciante.getNumeroDocumento());	
		persona.setDlcTipodenunciante(tipoDen);
		persona.setCveFoliodenuncia(dltDenuncia.getCveFoliodenuncia());
		persona.setFecFechareg(Calendar.getInstance().getTime());
		
		if(denunciante.getIdDomicilio()!=null){
			DgDomicilioGeografico dom =  new DgDomicilioGeografico();
			dom.setDomicilioId(denunciante.getIdDomicilio());
			persona.setDgDomicilioGeografico(dom);	
		}		
		return persona;
	}
	
	

	private BigDecimal convierteBigDecimal(String valor){		
		if(valor!=null && !valor.equals("")){
			return new BigDecimal(valor);
		}else{
			return BigDecimal.ZERO;
		}
	}

	@Override
	public List<DenunciaVODT> recuperaDenunciasConsulta(UserSession user, boolean consultaPorUsuario, DenunciaVO den) {
		// TODO Auto-generated method stub
		
		List<DenunciaVODT> listaDen=new ArrayList<DenunciaVODT>();
		DenunciaVODT denVo=null;
		StringBuffer query=new StringBuffer();
		DltDenuncia denunciaD=new DltDenuncia();
		DltDenuncia d=null;
		DltPersona p=null;
		if(consultaPorUsuario){
			denunciaD.setCveUsuarioden(user.getCveIdUsuario());
			query.append("FROM DltDenuncia where CVE_USUARIODEN= "+user.getCveIdUsuario()+" order by FEC_REGISTRO desc ");
		}else{
			if(user.getCveRol() == 1 && !consultaPorUsuario){
				String idDenuncias = obtenerDerivaciones(user.getIdSubDelegacion());
				query.append("FROM DltDenuncia where NUM_FOLIODENUNCIA is not null and CVE_FOLIODENUNCIA in (" + idDenuncias + ")");
				query.append(obtenerConsultaConFiltro(den,false));
				
			}else if(user.getCveRol()==2 && !consultaPorUsuario){
				query.append("FROM DltDenuncia where NUM_FOLIODENUNCIA is not null ");
				query.append(obtenerConsultaConFiltro(den,true));
			}
		}
		
		
		List<AbstractModel> denuncia=daoService.consultaLibrePorClave(0L, query.toString());
		DltDerivaSub deriva;
		for(AbstractModel mod:denuncia){
			d=(DltDenuncia) mod;
			if(d.getCveTipodenunciante()==null || d.getCveOrigendenuncia()!=null || (!consultaPorUsuario && d.getFecFechaenv()==null)){
				continue;
			}
			denVo=new DenunciaVODT();
			denVo.setNumFolio(d.getNumFoliodenuncia());
			denVo.setFechaRegistro(Functions.dateToString3(d.getFecRegistro()));
			denVo.setNumClaveFolio(d.getCveFoliodenuncia().toString());
			if(!d.getDltDatospatrons().isEmpty()){
				denVo.setNombrePatronDenunciado(d.getDltDatospatrons().iterator().next().getDesNomrazonsocial());	
			}
				
			denVo.setEstatus(d.getDlcStatus().getDesStatus());
			denVo.setIdEstatus(d.getDlcStatus().getIdStatus());
			
			p=determinaPersona(d, d.getCveTipodenunciante().intValue());
			denVo.setNombreDenunciante(p.getNombreCompleto());
			// && d.getDlcStatus().getIdStatus()
			if (d.getFecFechaenv()!=null && validaDiasFecha(d.getFecFechaenv(), 14)
					&& d.getDlcStatus().getIdStatus().intValue() == ConstantesBusiness.ENVIADA_PARA_RATIFICACION.intValue()) {
				d=actualizaEstatusDenuncia(ConstantesBusiness.NO_RATIFICADA, d);
				denVo.setEstatus(d.getDlcStatus().getDesStatus());
			} else if (validaDiasFecha(d.getFecRegistro(), 10)
					&& d.getDlcStatus().getIdStatus().intValue() == ConstantesBusiness.ESTATUS_EN_ELABORACION.intValue()) {
				d=actualizaEstatusDenuncia(ConstantesBusiness.EXPIRADA_POR_NO_ENVIO, d);
				denVo.setEstatus(d.getDlcStatus().getDesStatus());
			}
			denVo.setFechaPresentacion(d.getFecFechaenv());
			
			query = new StringBuffer();
			query.append("FROM DltDerivaSub where CVE_FOLIODENUNCIA = " + d.getCveFoliodenuncia() + " order by CVE_DERIVASUB DESC");
			List<AbstractModel> derivada = (List<AbstractModel>) daoService.consultaLibrePorClave(0L, query.toString());
			
			if(derivada!=null && derivada.size()>0){
				deriva = (DltDerivaSub)derivada.get(0);
				denVo.setDesSubdelegacion(deriva.getDlcSubdelegacion().getNomNombre());
				denVo.setFechaDerivacion(deriva.getFecFechaDeriva());
				denVo.setInstrucciones(deriva.getDesInstrucciones());
			}
			listaDen.add(denVo);
			denVo=null;
		}
		logger.info("Tam "+denuncia.size());
		
		return listaDen;
	}
	
	private String obtenerConsultaConFiltro(DenunciaVO den, boolean nivelCentral){
		StringBuffer query = new StringBuffer();
		if(den!=null){
			boolean ban=false;
			if(den.getCveEstatus()!=null && den.getCveEstatus()>0){
//				if(nivelCentral){
//					query.append(" and ");
//				}else{
//					query.append(" where ");
//				}
				query.append(" and ID_STATUS=  " + den.getCveEstatus());
				ban=true;
			}
			if(den.getCveSubdelegacion()!=null && den.getCveSubdelegacion()>0 && nivelCentral){
//				if(ban||nivelCentral){
//					query.append(" and ");
//				}else{
//					query.append(" where ");
//				}
				String idDenuncias = obtenerDerivaciones(den.getCveSubdelegacion());
				query.append(" and CVE_FOLIODENUNCIA in (" + idDenuncias + ")");
				ban=true;
			}
			if(den.getFechaPresentacion()!=null && !den.getFechaPresentacion().equalsIgnoreCase("DD/MM/AAAA")){
//				if(ban || nivelCentral){
//					query.append(" and ");
//				}else{
//					query.append(" where ");
//				}
				query.append(" and FEC_FECHAENV ='"+den.getFechaPresentacion()+"'");
			}
		}
		query.append(" order by FEC_FECHAENV desc ");
		return query.toString();
	}
	
	private String obtenerDerivaciones(long idSubdelegacion){
		DltDerivaSub deriva;
		StringBuffer query2=new StringBuffer();
		List<Long> listaDerivaciones = new ArrayList<Long>();
		query2.append("FROM DltDerivaSub where CVE_SUBDELEGACION =" + idSubdelegacion);
		List<AbstractModel> derivaciones = daoService.consultaLibrePorClave(0L, query2.toString());
		for(AbstractModel der:derivaciones){
			deriva=(DltDerivaSub) der;
			listaDerivaciones.add(deriva.getCveFolioDenuncia());
		}
		String idDenuncias =  listaDerivaciones.toString();
		idDenuncias = idDenuncias.replace("[", "").replace("]", "");
		if(idDenuncias.equals("")){
			idDenuncias="0";
		}
		return idDenuncias;
	}
	
	private boolean validaDiasFecha(Date fecha,int dias){		
		Date fechaSistema = Calendar.getInstance().getTime(); 
		long diferencia = ( fechaSistema.getTime() - fecha.getTime() )/ConstantesBusiness.MILLSECS_PER_DAY; 
		if(diferencia>=dias){
			return true;
		}else{
			return false;
		}
	}
	
	private DltDenuncia actualizaEstatusDenuncia(Long estatus,DltDenuncia denuncia){
		DlcStatus esta=daoDenuncia.getStatusByClve(estatus);		
		denuncia.setDlcStatus(esta);
		return (DltDenuncia) daoService.actualiza(denuncia);
	}
	private DltPersona determinaPersona(DltDenuncia de,int tipoDenunciate){
		DltPersona pr = null;
		for(DltPersona pe:de.getDltPersonas()){
			if(pe.getCveTipodenunciante().intValue()==tipoDenunciate){
				pr = pe;
			}
		} 
		return pr;
	}

	@Override
	public DenunciaVO consultaDenuncia(DltDenuncia denuncia) {
		// TODO Auto-generated method stub
		DltDenuncia denun=daoDenuncia.getDenunciaByClaveFolio(denuncia.getCveFoliodenuncia());
		DenunciaVO d=new DenunciaVO();
		
		d.setCveDenuncia(denun.getCveFoliodenuncia());
		d.setTipoDenunciante(denun.getCveTipodenunciante());
		d.setFolioDenuncia(denun.getNumFoliodenuncia());
		d.setCveEstatus(denun.getDlcStatus().getIdStatus().intValue());
		d.getDatosTrabajadorVO().setObservaciones(denun.getDesObservaciones());
		if(denun.getCveSubdelegRatifica()!=null){
			d.setCveSubdelegacion(denun.getCveSubdelegRatifica().intValue());
		}
		
		for(DltPersona pe:denun.getDltPersonas()){
				switch(pe.getCveTipodenunciante().intValue()){
				case ConstantesBusiness.TRABAJADOR:
					d.getDatosTrabajadorVO().setTrabajador(parserPersona(pe));
					break;
				case ConstantesBusiness.BENEFICIARIO:
					d.getDatosTrabajadorVO().setBeneficiario(parserPersona(pe));
					break;
				case ConstantesBusiness.REPRESENTANTE:
					d.getDatosTrabajadorVO().setRepresentanteLegal(parserPersona(pe));
					break;
				}
		}
		
		MotivoDenVO mv=null;
		for(DltMotivodenuncia mvDen:denun.getDltMotivodenuncias()){
			mv=new MotivoDenVO();
			mv.setCveMotivoDenuncia(mvDen.getId().getCveMotivodenuncia().intValue());
			mv.setFechaLabDejoLab(mvDen.getFecLabalDejolab()!=null ? Functions.dateToString3(mvDen.getFecLabalDejolab()):"");
			mv.setFechaLabDelIngreso(mvDen.getFecLabdelIngreso()!=null ? Functions.dateToString3(mvDen.getFecLabdelIngreso()):"");
			mv.setImpoSalarioReal(mvDen.getImpSalarioReal()!=null ? (mvDen.getImpSalarioReal()).toString():"");
			mv.setImpoSalarioReg(mvDen.getImpSalarioReg()!=null ? mvDen.getImpSalarioReg().toString():"");
			d.getDatosTrabajadorVO().getMotivosDenuncia().add(mv);			
		}
		
		
		//Datos del patron		
		if(!denun.getDltDatospatrons().isEmpty()){
			DltDatospatron patron=denun.getDltDatospatrons().iterator().next();
			d.getDatosPatronVO().setDenunciaExistente(patron.getIndPatrondenunciado().intValue());
			d.getDatosPatronVO().setNombreRepresentanteLegal(patron.getDesNomreplegal());
			d.getDatosPatronVO().setNumTrabajadores(patron.getNumTrabajadores()!=null ?patron.getNumTrabajadores().toString():"");
			d.getDatosPatronVO().setObservaciones(patron.getDesObservaciones());
			d.getDatosPatronVO().setRazonSocial(patron.getDesNomrazonsocial());
			if(patron.getIndRecibePagoTot()!=null)
				d.getDatosPatronVO().setRecibeTotalSueldoDeUnPatron(patron.getIndRecibePagoTot().intValue());
			d.getDatosPatronVO().setRegPat(patron.getCveRegpat());
			d.getDatosPatronVO().setRfc(patron.getDesRfc());
			d.getDatosPatronVO().setTelefonoEmpresa(patron.getNumTelefono());
			d.getDatosPatronVO().setGiroPatron(String.valueOf(patron.getCveActEconomica()));
			if(patron.getCveActEconomica()!=null){
				DlcGrupo gru=daoDenuncia.getGrupoByActv(patron.getCveActEconomica());
				d.getDatosPatronVO().setSector(String.valueOf(gru.getCveGrupo()));
			}
			
			if(patron.getDomicilioId()!=null && patron.getDomicilioId().longValue()>0){
				d.getDatosPatronVO().setIdDomicilio(patron.getDomicilioId()); 
				d.getDatosPatronVO().setDesDomicilio(daoDenuncia.obtenerDomicilioPorId(patron.getDomicilioId().longValue()));			
			}
		}
		
		List<DltDenuncia> sb=daoDenuncia.getSubdenuncias(denun);
		int s=0;
		for(DltDenuncia de:sb){
			if(de.getDltDatospatrons().iterator().hasNext()){
				DltDatospatron patro=de.getDltDatospatrons().iterator().next();
				PatronSecundarioVO vo=new PatronSecundarioVO();
				vo.setCveDenuncia(de.getCveFoliodenuncia().intValue());
				vo.setIdRow(s++);
				vo.setNombreRazonSocial(patro.getDesNomrazonsocial());
				vo.setRfc(patro.getDesRfc());
				if(patro.getDomicilioId()!=null && patro.getDomicilioId().longValue()>0){
					vo.setDescDomicilio(daoDenuncia.obtenerDomicilioPorId(patro.getDomicilioId()));
					vo.setCveDomicilio(String.valueOf((patro.getDomicilioId())));
				}
				d.getDatosPatronVO().getPatronesSecundarios().add(vo);
			}			
			
		}
		//Datos del centro de trabajo
		if(!denun.getDltInfotrabajos().isEmpty()){
			DltInfotrabajo infotrabajo=denun.getDltInfotrabajos().iterator().next();
			DatosCentroTrabajoVO trabajoVO=d.getDatosCentroTrabajoVO();
			trabajoVO.setActividadTrabajador(infotrabajo.getDesLaboresdesemp());
			trabajoVO.setAguinaldoAnual(infotrabajo.getImpAguinaldo()!=null ? infotrabajo.getImpAguinaldo().toString():"0.0");
			trabajoVO.setBaseComision(infotrabajo.getDesBaseComisionOtros());
			trabajoVO.setComisiones(infotrabajo.getDesBaseOtorgamiento());
			trabajoVO.setNombreJefeInm(infotrabajo.getDesNomjefeinmediato());
			trabajoVO.setSalario(infotrabajo.getImpSalariopercibido().toString());
			trabajoVO.setVacaciones(infotrabajo.getImpVacaciones().toString());
			if(!infotrabajo.getDesNumcontrato().equals("") || infotrabajo.getDesNumcontrato()==null){
				trabajoVO.setTieneContrato(1);
			}
			trabajoVO.setNumeroContrato(infotrabajo.getDesNumcontrato());
			trabajoVO.setTieneContrato(infotrabajo.getIndContrato()!=null ?infotrabajo.getIndContrato().intValue():0);
			trabajoVO.setDiasAguinaldo(String.valueOf(infotrabajo.getNumDiasAguinaldo()!=null ?infotrabajo.getNumDiasAguinaldo():"0"));
			trabajoVO.setDiasVacaciones(infotrabajo.getNumDiasvacaciones()!=null ? infotrabajo.getNumDiasvacaciones().toString():"0");
			trabajoVO.setFechaInicio(infotrabajo.getFecFechaInicio()!=null ? Functions.dateToString3(infotrabajo.getFecFechaInicio()):null);
			trabajoVO.setFechaTermino(infotrabajo.getFecFechaFin()!=null ? Functions.dateToString3(infotrabajo.getFecFechaFin()):null);
			trabajoVO.setFechaRiesgoTrabajo(infotrabajo.getFecFechariesgotrab()!=null ? Functions.dateToString3(infotrabajo.getFecFechariesgotrab()):null);
			trabajoVO.setHorarioLabores(infotrabajo.getDesHorariolabores());
			trabajoVO.setObservaciones(infotrabajo.getDesObservaciones());
			trabajoVO.setGratificacion(String.valueOf(infotrabajo.getImpComisionOtros()));
			
			if(infotrabajo.getIdDomicilio()!=null && infotrabajo.getIdDomicilio().longValue()>0){
				trabajoVO.setIdDomicilio(infotrabajo.getIdDomicilio());
				trabajoVO.setDesDomicilio(daoDenuncia.obtenerDomicilioPorId(trabajoVO.getIdDomicilio().longValue()));
			}

			for(DltFormapago fp:denun.getDltInfotrabajos().iterator().next().getDltFormapagos()){
				switch(fp.getId().getCveConcepto().intValue()){				
					case ConstantesBusiness.CONCEPTO_TIPO_DE_PERIODO_PAGO:
						trabajoVO.setPagoPeriodoSal(generaFormaPagoVO(fp));
						break;
					case ConstantesBusiness.CONCEPTO_TIPO_DE_COMPROBANTE_PAGO:
						trabajoVO.setPagoComprobantePago(generaFormaPagoVO(fp));
						break;
					case ConstantesBusiness.CONCEPTO_TIPO_DE_FORMA_PAGO:
						trabajoVO.getFormasPago().add(generaFormaPagoVO(fp));
						break;
				}							
			}
		
		}
		return d;
	}
	
	
	private FormaPagoVO generaFormaPagoVO(DltFormapago dltFormapago){
		FormaPagoVO voFormaPago=new FormaPagoVO();
		voFormaPago.setCveFormaPago(dltFormapago.getId().getCveFormapago().intValue());
		voFormaPago.setDescripcion(dltFormapago.getDesEspecifique());
		voFormaPago.setNombreArchivo(dltFormapago.getNomDocumento());
		return voFormaPago;
	}
	
	private  DenuncianteVO parserPersona(DltPersona persona){
			DenuncianteVO vo=new DenuncianteVO();
			vo.setApellidoMaterno(persona.getDesMaterno());
			vo.setApellidoPaterno(persona.getDesPaterno());
			vo.setCurp(persona.getCveCurp());
			vo.setEmail(persona.getDesEmail());
			vo.setNombre(persona.getDesNombre());
			vo.setSexoTrabajador(persona.getSexoTrabajador());
			vo.setNss(persona.getCveNss());
			if(persona.getCveNss()==null){
				vo.setTieneNss(false);
			}else{
				vo.setTieneNss(true);
			}
			vo.setRfc(persona.getCveRfc());
			vo.setTelefonoCelular(persona.getNumCelular());
			vo.setTelefonoContacto(persona.getNumTelefono());
			vo.setNumeroDocumento(persona.getNumDocumento());
			vo.setNombreDocumento(persona.getNomDocumento());
			if(persona.getDlcTipodocumento()!=null){
				vo.setDocOficial(persona.getDlcTipodocumento().getCveTipodocumento().intValue());	
			}
			
			if(persona.getDgDomicilioGeografico()!=null){
				vo.setIdDomicilio(persona.getDgDomicilioGeografico().getDomicilioId());
				vo.setDesDomicilio(daoDenuncia.obtenerDomicilioPorId(vo.getIdDomicilio()));			
			}
			
		return vo;
	}
	

	
	@Override
	public boolean guardaDocumentoPersona(DltDenuncia dltDenuncia, byte[] archivo, Long tipoDocumento, String nombreArchivo) {
		// TODO Auto-generated method stub
		boolean flag=false;
		DltDenuncia denun=daoDenuncia.getDenunciaByClaveFolio(dltDenuncia.getCveFoliodenuncia());	
		DlcTipodocumento doc=new DlcTipodocumento();
		doc.setCveTipodocumento(tipoDocumento);
		for(DltPersona per:denun.getDltPersonas()){
			if(per.getCveTipodenunciante().intValue()==dltDenuncia.getCveTipodenunciante().intValue()){
				per.setRefDocumento(archivo);
				per.setDlcTipodocumento(doc);
				per.setNomDocumento(nombreArchivo);
				daoService.actualiza(per);
				flag=true;
			}
		}
		return flag;
	}

	@Override
	public boolean guardaFormaPago(DltDenuncia dltDenuncia, byte[] archivo,
			Long cveFormaPago, String nombreArchivo) {
		// TODO Auto-generated method stub
		DltDenuncia denun=daoDenuncia.getDenunciaByClaveFolio(dltDenuncia.getCveFoliodenuncia());	
		boolean flag=false;
		if(!denun.getDltInfotrabajos().isEmpty()){
			DltInfotrabajo traba=denun.getDltInfotrabajos().iterator().next();
			for(DltFormapago formaPago:traba.getDltFormapagos()){
				if(formaPago.getId().getCveFormapago().equals(cveFormaPago)){									
					formaPago.setRefDocumento(archivo);
					formaPago.setNomDocumento(nombreArchivo);
					daoService.actualiza(formaPago);
					flag=true;
				}
			}
		}else{
			flag=false;
		}		
		return flag;
	}

	@Override
	public DltDenuncia getDenunciaByNumFolio(String numFolio) {
		// TODO Auto-generated method stub
		System.out.println("El num de folio es "+numFolio);
		return daoDenuncia.getDenunciaByNumFolio(numFolio);

	}

	@Override
	public void eliminarDomicilio(Long idDomicilio) {
		// TODO Auto-generated method stub
		ArrayList<DgDomicilioGeografico> domi = (ArrayList)daoService.consultaLibrePorClave(0L, "from DgDomicilioGeografico d where d.domicilioId = "+ idDomicilio);
	
		if(!domi.isEmpty()){
			daoService.elimina(domi.get(0));
		}
		
	}


	public void saveDerivaSub(DltDerivaSub deriva){
		daoService.agrega(deriva);
		StringBuffer query = new StringBuffer();
		query.append("FROM DltDenuncia where CVE_FOLIODENUNCIA = " + deriva.getCveFolioDenuncia());
		List<AbstractModel> den = (List<AbstractModel>) daoService.consultaLibrePorClave(0L, query.toString());
		DltDenuncia denuncia = (DltDenuncia) den.get(0);
		DlcStatus status=new DlcStatus();
		status.setIdStatus(deriva.getDlcStatus().getIdStatus());
		denuncia.setDlcStatus(status);
		daoService.actualiza(denuncia);
	}
	
	public void guardarSeguimiento(DltDerivaSub deriva){
		StringBuffer query = new StringBuffer();
		DlcStatus status=new DlcStatus();
		status.setIdStatus(deriva.getDlcStatus().getIdStatus());
		
		query.append("FROM DltDerivaSub where CVE_FOLIODENUNCIA = " + deriva.getCveFolioDenuncia() +" order by CVE_DERIVASUB desc ");
		List<AbstractModel> der = (List<AbstractModel>) daoService.consultaLibrePorClave(0L, query.toString());
		DltDerivaSub derivar = (DltDerivaSub) der.get(0);
		derivar.setDlcStatus(status);
		derivar.setFechFechaConclusion(deriva.getFechFechaConclusion());
		derivar.setDesSeguimiento(deriva.getDesSeguimiento());
		daoService.actualiza(derivar);
		
		query = new StringBuffer();
		query.append("FROM DltDenuncia where CVE_FOLIODENUNCIA = " + deriva.getCveFolioDenuncia());
		List<AbstractModel> den = (List<AbstractModel>) daoService.consultaLibrePorClave(0L, query.toString());
		DltDenuncia denuncia = (DltDenuncia) den.get(0);
		denuncia.setDlcStatus(status);
		daoService.actualiza(denuncia);
	}
}
