/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Leticia Torres
 *  @Proyecto: delta
 *  @Archivo: ConfiguracionCeBusiness.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.analisis
 *  @Fecha: 18/10/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.analisis;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.analisis.AnalisisServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.ConfiguracionCeBusinessRemote;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.clasificacion.CodigoRolClasificacion;
import mx.gob.imss.ctirss.delta.model.clasificacion.ConfiguracionCe;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisEnum;

@Stateless(name = "configuracionCeBusiness", mappedName = "configuracionCeBusiness")
public class ConfiguracionCeBusiness extends AbstractServiceBusiness
		implements ConfiguracionCeBusinessRemote {
	
	@EJB
	private AnalisisServiceEntityLocal analisisEntity;

	/**
	 * {@inheritDoc}
	 * @see mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.ConfiguracionCeBusinessRemote#configurarOperaciones(long,
	 *      long, int)
	 */
	@Override
	public ConfiguracionCe configurarOperaciones(final long cveIdAnalisis,
			final String cveIdUsuario, final int cveIdRol) {
		
		final ConfiguracionCe configuracionCe = new ConfiguracionCe();
		
		AnalisisClasificacionEmpresas analisis = analisisEntity.consultaPorIdAnalisis(cveIdAnalisis);
		int estatusAnalisis = analisis.getCveIdEstatus().intValue();			
		
		if(cveIdRol == CodigoRolClasificacion.NORMATIVO_CENTRAL.getCodigo()	||
		   cveIdRol == CodigoRolClasificacion.NORMATIVO_DEL.getCodigo() ||
		   cveIdRol == CodigoRolClasificacion.NORMATIVO_SUBDEL.getCodigo() ||	
		   cveIdRol == CodigoRolClasificacion.JEFE_OFICINA_SUBDEL.getCodigo() || //por el momento el rol de jefe de oficina se considera en MAC como solo de lectura
		   cveIdRol == CodigoRolClasificacion.JEFE_OFICINA_DEL.getCodigo() ||
		   cveIdRol == CodigoRolClasificacion.DELEGADO_DEL.getCodigo() ||
		   cveIdRol == CodigoRolClasificacion.SUBDELEGADO_SUBDEL.getCodigo() ||
		   cveIdRol == CodigoRolClasificacion.JEFE_OFICINA_COBROS_SUBDEL.getCodigo()
		){
			
			if(estatusAnalisis == EstatusAnalisisEnum.RECTIFICADO_AUTORIZADO.getClave()){
				configuracionCe.setBoVerClem(true);
				if(analisis.getUrlClemFirma() != null){
					configuracionCe.setBoIndFirma(true);
					configuracionCe.setUrlClemFirma(analisis.getUrlClemFirma());
					configuracionCe.setBoVerClem(false);
				}
			}
			
			//regresamos respuesta en caso de estos ROL para que no se habiliten mas botones segun el estatus
			return configuracionCe;
		}
		
		if( estatusAnalisis == EstatusAnalisisEnum.PENDIENTE_DE_ANALISIS.getClave() ||
				estatusAnalisis == EstatusAnalisisEnum.RATIFICADO_RECHAZADO.getClave() ||
				estatusAnalisis == EstatusAnalisisEnum.RECTIFICADO_RECHAZADO.getClave() ||
				estatusAnalisis == EstatusAnalisisEnum.RATIFICADO_AUTORIZADO_Y_RECHAZADO.getClave() ||
				estatusAnalisis == EstatusAnalisisEnum.RECTIFICADO_AUTORIZADO_Y_RECHAZADO.getClave()){
			
			configuracionCe.setBoRatificar(true);
			configuracionCe.setBoRectificar(true);
			
			if(cveIdRol == CodigoRolClasificacion.JEFE_DEPTO_DEL.getCodigo()
					|| cveIdRol == CodigoRolClasificacion.JEFE_DEPTO_SUBDEL.getCodigo()
					|| cveIdRol == CodigoRolClasificacion.VENTANILLA_CLASIF_DEL.getCodigo()
					|| cveIdRol == CodigoRolClasificacion.VENTANILLA_CLASIF_SUBDEL.getCodigo()
			){
				configuracionCe.setBoDesechar(true);
			}
			
		}else if(estatusAnalisis == EstatusAnalisisEnum.RATIFICADO_PENDIENTE_AUTORIZACION.getClave()){
			
			if(cveIdRol == CodigoRolClasificacion.JEFE_DEPTO_SUBDEL.getCodigo() || cveIdRol == CodigoRolClasificacion.JEFE_DEPTO_DEL.getCodigo()){
				configuracionCe.setBoAutoRatificarN1(true);
				configuracionCe.setBoRechRatificarN1(true);
			}				

		}else if(estatusAnalisis == EstatusAnalisisEnum.RECTIFICADO_PENDIENTE_AUTORIZACION.getClave()){
			
			if(cveIdRol == CodigoRolClasificacion.JEFE_DEPTO_SUBDEL.getCodigo() || cveIdRol == CodigoRolClasificacion.JEFE_DEPTO_DEL.getCodigo()){
				configuracionCe.setBoAutoRectificarN1(true);
				configuracionCe.setBoRechRectificarN1(true);
			}				

		}else if(estatusAnalisis == EstatusAnalisisEnum.RATIFICADO_AUTORIZADO.getClave() || 
				estatusAnalisis == EstatusAnalisisEnum.RECTIFICADO_AUTORIZADO.getClave()){
			
			if(estatusAnalisis == EstatusAnalisisEnum.RECTIFICADO_AUTORIZADO.getClave()){
				configuracionCe.setBoVerClem(true);				
				if(cveIdRol == CodigoRolClasificacion.JEFE_DEPTO_SUBDEL.getCodigo() || cveIdRol == CodigoRolClasificacion.JEFE_DEPTO_DEL.getCodigo()){
					configuracionCe.setBoModificarClem(true);
					configuracionCe.setBoModificarAuto(true);
				}
				if(analisis.getUrlClemFirma() != null){
					configuracionCe.setBoIndFirma(true);
					configuracionCe.setUrlClemFirma(analisis.getUrlClemFirma());
					configuracionCe.setBoModificarClem(false);
					configuracionCe.setBoModificarAuto(false);
					configuracionCe.setBoVerClem(false);					
				}
			}
			if(estatusAnalisis == EstatusAnalisisEnum.RATIFICADO_AUTORIZADO.getClave()){
				if(cveIdRol == CodigoRolClasificacion.JEFE_DEPTO_SUBDEL.getCodigo() || cveIdRol == CodigoRolClasificacion.JEFE_DEPTO_DEL.getCodigo()){
					configuracionCe.setBoModificarAuto(true);
				}
//				configuracionCe.setBoIndFirma(false);
//				configuracionCe.setUrlClemFirma("");
//				configuracionCe.setBoModificarClem(false);
//				configuracionCe.setBoVerClem(false);
			}
			
		}
				
		return configuracionCe;
	}
		

}
