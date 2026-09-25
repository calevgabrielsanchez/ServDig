<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<style>
	label.error { float: none; font-size:smaller; color: red; padding-left: .5em; vertical-align: top; }
	label  {  float: none; }
</style>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="mx.gob.imss.ctirss.correccion.web.controller.monitor.ConstantesCedulas" %>
<html lang="sp">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/cedulasConstruccion/monitoreo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/uploadFile/upclick.js"></script>
	
	
	
	
<style>
  .ui-progressbar {
    position: relative;
  }
  .progress-label {
    position: absolute;
    left: 50%;
    top: 4px;
    font-weight: bold;
    text-shadow: 1px 1px 0 #fff;
  }
</style>

<div id="cuerpo">
	<div class="menu_principal" style="height: 2em !important;">
		<!--inicia menu principal-->
  		   		<div align="center">
		        <div class="centrado">
		          <ul style="height: 1em !important;">
		            <li><a> Monitoreo de C&eacute;dulas</a></li>
		          </ul>
		        </div>
		        <!--fin centrado--> 
  		      </div>
     </div>
		    
 		    <div  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
		    	<div id="wrapperDialogDescCedulaCorreccion" style="background-color: #f2fff2;">
		       <input type="hidden" id="idContexto" value="<%=request.getContextPath()%>">
		       <input type="hidden" name="<%=ConstantesCedulas.FROM_PAGE_MONITOR%>" value="true"/>
		        
		    		<fieldset>
		    			<table style="width: 900px" align="center">
		    				<tr valign="middle">
		    					<td align="center" width="900px">
		    						<%-- <table  class="tablaverde2" style="width: 900px">
									       <tbody>
									     <tr>
									       <c:if test="${monitorCedula.cedulaA.crtEstatusFlujoCedula.cveEstatus==5}">	
							                     <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasA_sinOperacion.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaA.crtEstatusFlujoCedula.cveEstatus==1}">
							                    <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasA_sinOperacion.jpg"></td>
						                   </c:if>
						                   <c:if  test="${monitorCedula.cedulaA.crtEstatusFlujoCedula.cveEstatus==2}">				                     
						                   	  <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasA_sinOperacion.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaA.crtEstatusFlujoCedula.cveEstatus==6}">
						                       <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasA_proceso.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaA.crtEstatusFlujoCedula.cveEstatus==3}">
						                       <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasA_completado.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaA.crtEstatusFlujoCedula.cveEstatus==4}">
						                     <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasA_error.jpg"></td>
						                   </c:if>     
						                  
									       <c:if test="${monitorCedula.cedulaG.crtEstatusFlujoCedula.cveEstatus==5}">	
							                     <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasG_sinOperacion.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaG.crtEstatusFlujoCedula.cveEstatus==1}">
							                    <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasG_sinOperacion.jpg"></td>
						                   </c:if>
						                   <c:if  test="${monitorCedula.cedulaG.crtEstatusFlujoCedula.cveEstatus==2}">				                     
						                   	  <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasG_sinOperacion.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaG.crtEstatusFlujoCedula.cveEstatus==6}">
						                       <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasG_proceso.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaG.crtEstatusFlujoCedula.cveEstatus==3}">
						                       <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasG_completado.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaG.crtEstatusFlujoCedula.cveEstatus==4}">
						                     <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasG_error.jpg"></td>
						                   </c:if>     
						                   
									       <c:if test="${monitorCedula.cedulaH.crtEstatusFlujoCedula.cveEstatus==5}">	
							                     <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasH_sinOperacion.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaH.crtEstatusFlujoCedula.cveEstatus==1}">
							                    <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasH_sinOperacion.jpg"></td>
						                   </c:if>
						                   <c:if  test="${monitorCedula.cedulaH.crtEstatusFlujoCedula.cveEstatus==2}">				                     
						                   	  <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasH_sinOperacion.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaH.crtEstatusFlujoCedula.cveEstatus==6}">
						                       <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasH_proceso.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaH.crtEstatusFlujoCedula.cveEstatus==3}">
						                       <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasH_completado.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaH.crtEstatusFlujoCedula.cveEstatus==4}">
						                     <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasH_error.jpg"></td>
						                   </c:if>     
						                  
									       <c:if test="${monitorCedula.cedulaI.crtEstatusFlujoCedula.cveEstatus==5}">	
							                     <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasI_sinOperacion.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaI.crtEstatusFlujoCedula.cveEstatus==1}">
							                    <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasI_sinOperacion.jpg"></td>
						                   </c:if>
						                   <c:if  test="${monitorCedula.cedulaI.crtEstatusFlujoCedula.cveEstatus==2}">				                     
						                   	  <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasI_sinOperacion.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaI.crtEstatusFlujoCedula.cveEstatus==6}">
						                       <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasI_proceso.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaI.crtEstatusFlujoCedula.cveEstatus==3}">
						                       <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasI_completado.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaI.crtEstatusFlujoCedula.cveEstatus==4}">
						                     <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasI_error.jpg"></td>
						                   </c:if>     
						                     
						                   </tr>
						                   <tr>
						                       <td width="151" align="center">
						                       <c:out value="${monitorCedula.cedulaA.crtEstatusFlujoCedula.txDescripcion}"/>
						                       </td>
						                       <td width="151" align="center">
						                       <c:out value="${monitorCedula.cedulaG.crtEstatusFlujoCedula.txDescripcion}"/>
						                       </td>
						                       <td width="151" align="center">
						                        <c:out value="${monitorCedula.cedulaH.crtEstatusFlujoCedula.txDescripcion}"/>
						                       </td>
						                        <td width="151" align="center">
						                        <c:out value="${monitorCedula.cedulaI.crtEstatusFlujoCedula.txDescripcion}"/>
						                        </td>			                       					                        
						                   </tr>
						                   <tr>
						                          
						                   <c:if test="${monitorCedula.cedulaO.crtEstatusFlujoCedula.cveEstatus==5}">	
							                     <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasO_sinOperacion.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaO.crtEstatusFlujoCedula.cveEstatus==1}">
							                    <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasO_sinOperacion.jpg"></td>
						                   </c:if>
						                   <c:if  test="${monitorCedula.cedulaO.crtEstatusFlujoCedula.cveEstatus==2}">				                     
						                   	  <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasO_sinOperacion.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaO.crtEstatusFlujoCedula.cveEstatus==6}">
						                       <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasO_proceso.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaO.crtEstatusFlujoCedula.cveEstatus==3}">
						                       <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasO_completado.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaO.crtEstatusFlujoCedula.cveEstatus==4}">
						                     <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasO_error.jpg"></td>
						                   </c:if>     
						                 
									       <c:if test="${monitorCedula.cedulaQ.crtEstatusFlujoCedula.cveEstatus==5}">	
							                     <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasQ_sinOperacion.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaQ.crtEstatusFlujoCedula.cveEstatus==1}">
							                    <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasQ_sinOperacion.jpg"></td>
						                   </c:if>
						                   <c:if  test="${monitorCedula.cedulaQ.crtEstatusFlujoCedula.cveEstatus==2}">				                     
						                   	  <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasQ_sinOperacion.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaQ.crtEstatusFlujoCedula.cveEstatus==6}">
					                       <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasQ_proceso.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaQ.crtEstatusFlujoCedula.cveEstatus==3}">
						                       <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasQ_completado.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaQ.crtEstatusFlujoCedula.cveEstatus==4}">
						                     <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/cedulasQ_error.jpg"></td>
						                   </c:if>
						                          
									       <c:if test="${monitorCedula.cedulaTrabajadores.crtEstatusFlujoCedula.cveEstatus==5}">	
							                     <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/detalleTrab_sinOperacion.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaTrabajadores.crtEstatusFlujoCedula.cveEstatus==1}">
							                    <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/detalleTrab_sinOperacion.jpg"></td>
						                   </c:if>
						                   <c:if  test="${monitorCedula.cedulaTrabajadores.crtEstatusFlujoCedula.cveEstatus==2}">				                     
						                   	  <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/detalleTrab_sinOperacion.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaTrabajadores.crtEstatusFlujoCedula.cveEstatus==6}">
					                       <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/detalleTrab_proceso.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaTrabajadores.crtEstatusFlujoCedula.cveEstatus==3}">
						                       <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/detalleTrab_completado.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaTrabajadores.crtEstatusFlujoCedula.cveEstatus==4}">
						                     <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/detalleTrab_error.jpg"></td>
						                   </c:if>
						                   
						                   <c:if test="${monitorCedula.cedulaCopsPagadas.crtEstatusFlujoCedula.cveEstatus==5}">	
							                     <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/copsPagadas_sinOperacion.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaCopsPagadas.crtEstatusFlujoCedula.cveEstatus==1}">
							                    <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/copsPagadas_sinOperacion.jpg"></td>
						                   </c:if>
						                   <c:if  test="${monitorCedula.cedulaCopsPagadas.crtEstatusFlujoCedula.cveEstatus==2}">				                     
						                   	  <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/copsPagadas_sinOperacion.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaCopsPagadas.crtEstatusFlujoCedula.cveEstatus==6}">
					                       <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/copsPagadas_proceso.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaCopsPagadas.crtEstatusFlujoCedula.cveEstatus==3}">
						                       <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/copsPagadas_completado.jpg"></td>
						                   </c:if>
						                   <c:if test="${monitorCedula.cedulaCopsPagadas.crtEstatusFlujoCedula.cveEstatus==4}">
						                     <td width="151" align="left"><img src="<%=request.getContextPath()%>/resources/images/monitor/cedulas/copsPagadas_error.jpg"></td>
						                   </c:if>    
						                      
						                   </tr>
						                    <tr>
						                     <td width="151" align="center">
						                        <c:out value="${monitorCedula.cedulaO.crtEstatusFlujoCedula.txDescripcion}"/>
						                     </td>
						                     <td width="151" align="center">
						                        <c:out value="${monitorCedula.cedulaQ.crtEstatusFlujoCedula.txDescripcion}"/>
						                     </td>			
						                      <td width="151" align="center">
						                        <c:out value="${monitorCedula.cedulaTrabajadores.crtEstatusFlujoCedula.txDescripcion}"/>
						                      </td>	
						                       <td width="151" align="center">
						                        <c:out value="${monitorCedula.cedulaCopsPagadas.crtEstatusFlujoCedula.txDescripcion}"/>
						                      </td>
						                    </tr>
										  </tbody>
									 --%></table>
		    						
		    						<table class="tablaverde2" style="width: 900px">
		    							<tbody>
		    								<tr valign="top" class="impar">
												<td align="left" colspan="4">&nbsp;</td>
											</tr>
											<tr valign="top" class="par">
											<td align="left" width="130px" colspan="1" class="etiqueta2">
											<span class="etiquetaError">*</span>Folio Correcci&oacute;n:
											</td>
											<td align="left" width="130px" colspan="1">
											<input type="text" id="folioCorreccion" size="20" maxlength="18" onblur="this.value=(this.value).toUpperCase();"/><label for="folioCorreccion"></label>
											 
											</td>
											</tr>
<!-- 											<tr valign="top" class="par">
												<td align="left" width="130px" colspan="1" class="etiqueta2">
												<span class="etiquetaError">*</span>Ejercicio:
												<td align="left" width="130px" colspan="1">
													<select id="periodo" name="periodo" onchange="validaPeriodo()">
														<option id="" value="0">--Por favor seleccione--</option>
													</select>
													<label class="etiquetaError" id="periodoLabel"></label>
												</td>
											</tr> -->
<!-- 											<tr valign="top" class="par">
											<td align="left" width="130px" colspan="1" class="etiqueta2">
											<label>C&eacute;dula:</label>
											</td>
											<td align="left" width="130px" colspan="1">
											<select id="idArchivoCarga" name="idArchivoCarga">
											    <option value="-1">Seleccione</option>
  												<option value="1">Cedula A</option>
  												<option value="2">Cedula G</option>
  												<option value="3">Cedula H</option>
  												<option value="4">Cedula I</option>
  												<option value="5">Cedula O</option>
  												<option value="6">Cedula Q</option>
  												<option value="7">Detalle de Trabajadores</option>
  												<option value="8">COP Pagadas</option>
											</select> 
											</td>
											</tr> -->
									    <tr align="center">
										  <td align="center" width="130px" colspan="4">
										  &nbsp;
										  </td>
										</tr>
										<tr align="center">
										  <td align="center" width="130px" colspan="4">
										 
										  <a onclick="recuperaEstatusMonitor()"><span class="boton" id="botonMonitorCedulas">Presentar Monitor</span></a>	
										  </td>
										</tr>
										<tr align="center">
										  <td align="center" width="130px" colspan="4">
										  &nbsp;
										  </td>
										</tr>	
										
										
		    							</tbody>
		    							
		    							
		    							
		    							
		    							
		    						</table>
		    						<table id="tablaMonitorCedulas" class="tablaverde2" style="width: 900px">
		    				
		    									</table>
		    						<c:if test="${!empty LISTADO_ERRORES_ANEXOS}">
										<table  class="tablaverde2" style="width: 900px">
										  	  <thead>
									  	        <tr >
									  		   <td> Errores Presentados</td>
									  	       </tr>
									         </thead>
									         <tbody align="left">
									         <c:forEach var="item" items="${LISTADO_ERRORES_ANEXOS}" >
									         <tr>
									  		     <td style="color: red;"> ${item.txError}</td>
									  	       </tr>	
									         </c:forEach>
										  </tbody>
									</table>
								</c:if>
		    					
		    			
		    		
		    			
		    			
		    			
		    			
		    			
		    			
		    		</fieldset>
		    	
		    		<form id="formaDescargaCedula" action="descarga/descargaReturn.do" method="post" id="descCedulaCorreccionForm">	
		    				<input type="hidden" name="folioCorreccion" id="folioCorreccion"/>
		    				<input type="hidden" name="idArchivoDescarga" id="idArchivoDescarga"/>
		    				<input type="hidden" name="periodo" id="periodo"/>
		    			
		    			
		    			</form>
		    	
		    	</div>
		    </div>
		    
</div>	
	
</html>