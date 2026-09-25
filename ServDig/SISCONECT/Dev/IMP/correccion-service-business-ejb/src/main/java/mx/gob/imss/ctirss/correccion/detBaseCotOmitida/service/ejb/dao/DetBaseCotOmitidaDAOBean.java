package mx.gob.imss.ctirss.correccion.detBaseCotOmitida.service.ejb.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.management.Query;
import javax.persistence.PersistenceException;

import mx.gob.imss.correccion.commons.sbc.vo.ExcedentesTopadosVO;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcEjercicio;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcPercepciones;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.repository.AbstractRespository;
import mx.gob.imss.ctirss.correccion.model.CrtBalanzaComp;
import mx.gob.imss.ctirss.correccion.model.CrtCopPagadasAnual;
import mx.gob.imss.ctirss.correccion.model.CrtDetBaseCotOmitida;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@Stateless
public class DetBaseCotOmitidaDAOBean<T extends AbstractModel> extends AbstractRespository implements DetBaseCotOmitidaDAOLocal<T> {

	@SuppressWarnings("unchecked")
	public List<CrcEjercicio> buscaEjercicio(CrcEjercicio model) {
		List<CrcEjercicio> lst = new ArrayList<CrcEjercicio>();
		try {
			Criteria criteria = this.getSession().createCriteria(model.getClass()).add(Restrictions.eq("cveAcexoCorrPat", ((CrcEjercicio)model).getCveAcexoCorrPat()));
			lst = (List<CrcEjercicio>) criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}
	
	@Override
	public DatosSalidaPaginador<T> paginar(DatosEntradaPaginador<T> params) {
		
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();
		
		List<T> result = new ArrayList<T>();
		CrtDetBaseCotOmitida model = (CrtDetBaseCotOmitida) params.getModelo();
		
		Criteria criteria = this.getSession().createCriteria(model.getClass());
		if(model != null && model.getCveAnexoSolCorrPat() != null){
			criteria.add(Restrictions.eq("cveAnexoSolCorrPat", model.getCveAnexoSolCorrPat()));
		}
		if(model != null && model.getCveEjercicio() != null){
			criteria.add(Restrictions.eq("cveEjercicio", model.getCveEjercicio()));
		}			
			
		if (criteria.list().size() > 0)
			result =  criteria.list();
		
						

		int iTotalRecords = 0;
		/* Se debe de obtener el numero total de registros en la base de datos */
		if (result != null)
			iTotalRecords = result.size();

		/**
		 * Total records, after filtering (i.e. the total number of records
		 * after filtering has been applied - not just the number of records
		 * being returned in this result set)
		 */
		int iTotalDisplayRecords = 0;

		if (result != null)
			iTotalDisplayRecords = result.size();

		response.setAaData(result);
		response.setiTotalDisplayRecords(iTotalDisplayRecords);
		response.setiTotalRecords(iTotalRecords);
		

		return response;
	}

	@Override
	public List<T> buscaPercepciones(T model) {
		List<T> lst = new ArrayList<T>();
		List<T> lstPercepciones = new ArrayList<T>();
		CrtBalanzaComp balanza = (CrtBalanzaComp) model;
		try {
			Criteria criteria = this.getSession().createCriteria(model.getClass());
			criteria.setProjection(Projections.distinct(Projections.property("cvePercepciones")));
			if(balanza != null && balanza.getCveAnexosolcorrpat() != null){
				criteria.add(Restrictions.eq("cveAnexosolcorrpat",balanza.getCveAnexosolcorrpat()));
			}
			if(balanza != null && balanza.getCveEjercicio() != null){
				criteria.add(Restrictions.eq("cveEjercicio", balanza.getCveEjercicio()));
			}
			
			lst = criteria.list();
			
			Double importeRemuneracion=0.0;
			Double importeAuxiliarNom=0.0;
			String columna;
			//Se recupera las balanzas
			
			Criteria criteriaBalanza = this.getSession().createCriteria(CrtBalanzaComp.class);
			criteriaBalanza.add(Restrictions.eq("cveAnexosolcorrpat",balanza.getCveAnexosolcorrpat()));
			criteriaBalanza.add(Restrictions.eq("cveEjercicio", balanza.getCveEjercicio()));
			List<CrtBalanzaComp> balazas = criteriaBalanza.list();
			System.out.println("Balanzas "+balazas);
			
			//Valores recuperados query
			String sqlCop="SELECT CVE_PERCEPCION, SUM(IM_REMUNERACION) IM_REMUNERACION, IM_AUXILIARNOM from CRT_BALANZACOMP "
							  +" where CVE_ANEXOSOLCORRPAT ="+balanza.getCveAnexosolcorrpat()
							  +" and CVE_EJERCICIO ="+balanza.getCveEjercicio()
							  +" group by CVE_PERCEPCION,IM_AUXILIARNOM "
							  +" order by CVE_PERCEPCION ";
			
			List valor = this.getSession().createSQLQuery(sqlCop).list();
			Object[] va=null;
			Double impRemune=0.0;
			Double impAuxNomina=0.0;
			
			for(Object obj:valor){
				va=(Object[]) obj;
				impRemune=impRemune+Double.parseDouble(va[1].toString());
				impAuxNomina=impAuxNomina+Double.parseDouble(va[2].toString());
			}
			if(impRemune>impAuxNomina){
				columna="importeRemuneracion";
			}else{
				columna="importeAuxiliarNom";
			}
			
			
			//Valores recuperados query
			
			
			// Con los resultados obtenifos traemos las percepciones
			
			if(lst != null && lst.size() > 0){

				
				Criteria criteria2 = this.getSession().createCriteria(CrcPercepciones.class);
				criteria2.add(Restrictions.in("cvePercepcion", lst));				
				lstPercepciones = criteria2.list();
				// Suma de gastos por percepciones
				
				for (Iterator<?> iterator = lstPercepciones.iterator(); iterator
						.hasNext();) {
					CrcPercepciones t = (CrcPercepciones) iterator.next();
					Double result = this.buscaSumaGastos(t.getCvePercepcion(), (T) balanza,columna);
					if(result != null){
						t.setSumRemuneracion(result);
					}
				}
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lstPercepciones;
	}
	
	public Double buscaSumaGastos(Integer cvePercepcion , T model,String columna){
		
		System.out.println("La columna es "+columna);
		
		CrtBalanzaComp balanza = (CrtBalanzaComp) model;
		Double result = null;
		
		Criteria criteria = this.getSession().createCriteria(CrtBalanzaComp.class);
		
		criteria.add(Restrictions.eq("cvePercepciones", cvePercepcion));
		criteria.add(Restrictions.eq("cveAnexosolcorrpat",balanza.getCveAnexosolcorrpat()));
		criteria.add(Restrictions.eq("cveEjercicio", balanza.getCveEjercicio()));
		List<CrtBalanzaComp> lista=criteria.list();
		Double importeRemu=0.0;
		Double importeNom=0.0; 
		//Suma del Balnza de La percepcion //revisar que importeNom se mande solo una vez
		for(CrtBalanzaComp objeBal:lista){
			importeRemu=importeRemu+objeBal.getCveImRemuneracion();
			importeNom=objeBal.getCveImAuxiliarNom();
		}
		
		if(columna.equals("importeRemuneracion")){
			return importeRemu;
		}else{
			return importeNom;
		}
		//Se comenta por validacion de cedula R
//		if(balanza.getTpSeleccionBalanzaOAux().equals("remuneracion")){
//			criteria.setProjection(Projections.sum("cveImRemuneracion"));
//		
//			try{
//				result = (Double) criteria.uniqueResult();
//				}catch (ArithmeticException e) {
//					e.printStackTrace();
//				}
//			
//		}else{
//			List<CrtBalanzaComp> ls = criteria.list();
//			if(ls!=null && !ls.isEmpty()){
//				result = ls.get(0).getCveImAuxiliarNom();
//			}else{
//				result = 0.0;
//			}
//			
//		}
		
		
		//return result;
	}

	@Override
	public T calculaBalanza(T model) {
		
		CrtBalanzaComp balanza = (CrtBalanzaComp) model;
		Criteria criteria = this.getSession().createCriteria(model.getClass());
		criteria.setProjection(Projections.sum("cveImRemuneracion"));
		criteria.add(Restrictions.eq("inIntegraSalario", "S"));
		if(balanza != null && balanza.getCveAnexosolcorrpat() != null){
			criteria.add(Restrictions.eq("cveAnexosolcorrpat",balanza.getCveAnexosolcorrpat()));
		}
		if(balanza != null && balanza.getCveEjercicio() != null){
			criteria.add(Restrictions.eq("cveEjercicio", balanza.getCveEjercicio()));
		}
		Double totalRenum = (Double) criteria.uniqueResult();
		
		Criteria criteria2 = this.getSession().createCriteria(model.getClass());
		
		criteria2.setProjection(Projections.projectionList()
				.add(Projections.distinct(Projections.alias(Projections.sum("cveImAuxiliarNom"), "TOTAL")))
				.add(Projections.groupProperty("cveGastos"))
				);
		
		criteria2.add(Restrictions.eq("inIntegraSalario", "S"));
		
		
		if(balanza != null && balanza.getCveAnexosolcorrpat() != null){
			criteria2.add(Restrictions.eq("cveAnexosolcorrpat",balanza.getCveAnexosolcorrpat()));
		}
		if(balanza != null && balanza.getCveEjercicio() != null){
			criteria2.add(Restrictions.eq("cveEjercicio", balanza.getCveEjercicio()));
		}
		
		List<?> ls = criteria2.list(); 
		Double totalAux = 0.0;
		if(ls!=null && !ls.isEmpty())
			totalAux = Double.parseDouble(((Object[])ls.get(0))[0].toString());
		
		
		totalAux = totalAux == null ? 0 : totalAux;
		totalRenum = totalRenum == null ? 0 : totalRenum;
		if(totalRenum < totalAux){
			balanza.setImBalanzaOAux(totalAux.toString());
			balanza.setTpSeleccionBalanzaOAux("auxiliarNomina");
		}else{
			balanza.setImBalanzaOAux(totalRenum.toString());
			balanza.setTpSeleccionBalanzaOAux("remuneracion");
		}
		
		String sqlImp = "select case when sum(im_remuneracion) >= sum(im_auxiliarnom) then sum(im_remuneracion) " +
				"else sum(im_auxiliarnom) end valor from CRT_BALANZACOMP  where " +
				"CVE_ANEXOSOLCORRPAT=" + balanza.getCveAnexosolcorrpat() +
				" and CVE_EJERCICIO=" + balanza.getCveEjercicio();
				
		
		
		String sqlImporte="select "
						   +" case "
					       + "    when IM_REMUNERACION >=  IM_AUXILIARNOM then IM_REMUNERACION " 
					       + "    else IM_AUXILIARNOM  "
					       + " end valor "
							+" from "
					 		+" (SELECT SUM (IM_AUXILIARNOM) IM_AUXILIARNOM, SUM (IM_REMUNERACION) IM_REMUNERACION "
					    +" FROM (  SELECT B.CVE_EJERCICIO, "
					    +"              B.CVE_ANEXOSOLCORRPAT,"
					    +"             B.CVE_PERCEPCION, "
					    +"               B.IM_AUXILIARNOM, "
					    +"               SUM (B.IM_REMUNERACION) IM_REMUNERACION "
					    +"          FROM CRT_BALANZACOMP B "
					    +"         WHERE 1 = 1 "
					    +"      GROUP BY B.CVE_PERCEPCION, "
					    +"               B.CVE_EJERCICIO, "
					    +"               B.CVE_ANEXOSOLCORRPAT,"
					    +"               IM_AUXILIARNOM) "
					    +" WHERE 1 = 1 AND CVE_EJERCICIO = "+balanza.getCveEjercicio()+" AND CVE_ANEXOSOLCORRPAT = "+balanza.getCveAnexosolcorrpat()+" "
					    +" GROUP BY CVE_EJERCICIO) tabla";
		
		List valor = this.getSession().createSQLQuery(sqlImporte).list();
		if(valor.isEmpty()){
			balanza.setImBalanzaOAux("0.0");
		}else{
			balanza.setImBalanzaOAux(valor.get(0).toString());	
		}
		logger.info("qyery "+sqlImporte);
//		BigDecimal bd = null;
//		if (valor.get(0) != null) {
//			bd = new BigDecimal(valor.get(0));
//		}
		
		return (T) balanza;
	}

	@Override
	public T calculaMenos(T model) {
		
		CrtCopPagadasAnual obj = (CrtCopPagadasAnual) model;
		Criteria criteria = this.getSession().createCriteria(model.getClass());
		criteria.setProjection(Projections.sum("imTotCuotaGuardPrest"));
		if(obj != null && obj.getCveAnexoSolCorrPat() != null){
			criteria.add(Restrictions.eq("cveAnexoSolCorrPat",obj.getCveAnexoSolCorrPat()));
		}
		if(obj != null && obj.getCveEjercicio() != null){
			criteria.add(Restrictions.eq("cveEjercicio", obj.getCveEjercicio()));
		}
		Double totalRenum = (Double) criteria.uniqueResult();
		if(totalRenum==null){
			totalRenum=0.0;
		}
		totalRenum = totalRenum/0.01;
		
		obj.setSumaImpTotGuadPrest(totalRenum);
		
		return (T) obj;
	}

	@Override
	public T guardaDetBaseCotOm(T model) {
		
		CrtDetBaseCotOmitida crtDetBaseCotOmitida = (CrtDetBaseCotOmitida) model;
		
		try{
			this.getSession().saveOrUpdate(model);
			this.getSession().flush();			
			
			Criteria criteria = this.getSession().createCriteria(model.getClass());
			criteria.add(Restrictions.eq("cveAnexoSolCorrPat", crtDetBaseCotOmitida.getCveAnexoSolCorrPat()));
			criteria.add(Restrictions.eq("cveEjercicio", crtDetBaseCotOmitida.getCveEjercicio()));
			criteria.add(Restrictions.eq("impSueldoBalanzaComp", crtDetBaseCotOmitida.getImpSueldoBalanzaComp()));
			criteria.add(Restrictions.eq("impSueldoDelAnualISR", crtDetBaseCotOmitida.getImpSueldoDelAnualISR()));
			criteria.add(Restrictions.eq("impVarMasSextoBim", crtDetBaseCotOmitida.getImpVarMasSextoBim()));
			criteria.add(Restrictions.eq("impVarMenosSextoBim", crtDetBaseCotOmitida.getImpVarMenosSextoBim()));
			crtDetBaseCotOmitida = (CrtDetBaseCotOmitida) criteria.uniqueResult();
			
		}catch(RuntimeException re){
			
			System.out.println(".-.ERROR:"+re);
			re.printStackTrace();
			throw new PersistenceException();
			
		}catch( Exception e){
			e.printStackTrace();
			return null;
		}
		return (T) crtDetBaseCotOmitida;
	}

	@Override
	public T guardaDetBaseCotOmDet(T model) {
		
		try {
			this.getSession().saveOrUpdate(model);
			this.getSession().flush();		
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return model;
	}

	@Override
	public T validaDetBaseCotOm(T model) {
		
		CrtDetBaseCotOmitida determinacion = (CrtDetBaseCotOmitida) model;
		Criteria criteria = this.getSession().createCriteria(model.getClass());
		BigDecimal anexoSol=determinacion.getCveAnexoSolCorrPat();
		if(determinacion != null && determinacion.getCveAnexoSolCorrPat() != null){
			criteria.add(Restrictions.eq("cveAnexoSolCorrPat",determinacion.getCveAnexoSolCorrPat()));
		}
		if(determinacion != null && determinacion.getCveEjercicio() != null){
			criteria.add(Restrictions.eq("cveEjercicio", determinacion.getCveEjercicio()));
		}
		determinacion = (CrtDetBaseCotOmitida) criteria.uniqueResult();
		
		
		
		StringBuilder query=new StringBuilder();
		
		query.append("SELECT * FROM CRT_EXED_SAL_TOPADOS sal ,CRT_EJERTRABAJADOR tra where tra.CVE_EJERTRAB=sal.CVE_EJERTRAB and tra.CVE_ANEXOSOLCORRPAT="+anexoSol);
		List datos=this.getSession().createSQLQuery(query.toString()).list();
		if(datos.isEmpty()){
			if(determinacion==null)
				determinacion=new CrtDetBaseCotOmitida();
			determinacion.setCedulaIVacia(true);
		}
	
		return (T) determinacion;
		
	}
	
	@Override
	public ExcedentesTopadosVO calculaExcedenteTopado(ExcedentesTopadosVO excedentesTopadosVO){
			
//			List<?> ls = this.getSession().createSQLQuery(" select sum(IMP_EXCEDENTEYFINIQUITO) from CRT_EXEDSALTOP_SUMPORMES where cve_ejertrab = " +
//															excedentesTopadosVO.getCveEjercicio()).list();
			
//			excedentesTopadosVO.setCveEjercicio(2012);
//			excedentesTopadosVO.setCveAnexoSolCorrPat(605);
			StringBuffer quer=new StringBuffer();
			quer.append(" SELECT SUM(IMP_PERCEPEXCENTAVSMGDF),SUM(IMP_EXCEDENTEYFINIQUITO) FROM CRT_EJERTRABAJADOR trab, CRT_EXEDSALTOP_SUMPORMES sumares where"+
						" trab.CVE_EJERTRAB=sumares.CVE_EJERTRAB and "+
						" trab.CVE_EJERCICIO="+excedentesTopadosVO.getCveEjercicio()+" and "+
						" trab.CVE_ANEXOSOLCORRPAT="+excedentesTopadosVO.getCveAnexoSolCorrPat());
			System.out.println(quer.toString());
			List<?> ls  =this.getSession().createSQLQuery(quer.toString()).list();
			
			Double importeVSMGDF=0.0;
			Double importeExcedenteFiniqito=0.0;
			
			if(!ls.isEmpty()){
				Object[] resultados=(Object[]) ls.get(0);
				importeVSMGDF=Double.parseDouble(resultados[0]!=null?resultados[0].toString():"0.0");
				importeExcedenteFiniqito=Double.parseDouble(resultados[1]!=null ? resultados[1].toString():"0.0");
				
			}
			
			//Solicitud de Jhonatan siempre va el  ExcedenteFiniquito
//			if(importeVSMGDF.compareTo(importeExcedenteFiniqito)>0){
//				excedentesTopadosVO.setExcedenteTopado(importeVSMGDF);
//			}else{
//				excedentesTopadosVO.setExcedenteTopado(importeExcedenteFiniqito);
//			}
			excedentesTopadosVO.setExcedenteTopado(importeExcedenteFiniqito);
			
			
			
//			List<?> ls = this.getSession().createSQLQuery(" select sum(IMP_EXCEDENTEYFINIQUITO) from CRT_EXEDSALTOP_SUMPORMES where cve_ejertrab = " +
//					excedentesTopadosVO.getCveEjercicio()).list();
		
//			if(ls!=null && !ls.isEmpty()){
//				excedentesTopadosVO.setExcedenteTopado(Double.valueOf(ls.get(0).toString()));
//			}else{
//				excedentesTopadosVO.setExcedenteTopado(0.0);
//			}
//			
		
		return excedentesTopadosVO;
	}

}
