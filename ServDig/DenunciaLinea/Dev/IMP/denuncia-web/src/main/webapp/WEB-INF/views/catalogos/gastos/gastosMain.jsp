<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<html lang="sp">

 
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/catalogos/gastos/gastos.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>


		
		<div id="cuerpo">
			
			
			<div class="menu_principal" style="height: 2em !important;"> 
		      <!--inicia menu principal-->
		      <div align="center">
		        <div class="centrado">
		          <ul style="height: 1em !important;">
		            <li><a> Gastos</a></li>
		          </ul>
		        </div>
		        <!--fin centrado--> 
		      </div>
		    </div>
		    
		    <div id="dgGasto" title="Gastos" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
		<div id="wrapperDialogGasto" style="background-color: #f2fff2;">	
			<form:form modelAttribute="crcGastos" action="/catalogo/gastos/consultar.do"
						method="post" id="gastosForm">
				<fieldset>
					<table style="width: 900px" align="center">
						<tr valign="middle">
							<td align="center" width="900px">
	
								<table class="tablaverde2" style="width: 900px">
									<tbody>
										<tr valign="top" class="impar">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="120px" colspan="1">
												<label style="color: red;">* </label>
												<form:label id="folioCorreccionLabel" for="folioCorreccion" path="folioCorreccion"
													cssErrorClass="error"> Folio Correcci&oacute;n: </form:label>
											</td>
											<td align="left" width="780px" colspan="3">
											<form:input	path="folioCorreccion" value="" label="Folio Correcci�n" maxlength="20"  id="folioMain"
												onkeyup="validaCampo('noCaracteresEspeciales','folioMain','gastosForm');"
											/>
											<label id="labelFolioMain"></label>
											</td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="100px" colspan="1"><form:label
													id="txGastoLabel" for="txGasto" path="txGasto" 
													cssErrorClass="error"> Gasto: </form:label>
											</td>
											<td align="left" width="780px" colspan="3"><form:input
													path="txGasto" value="" label="Gasto" maxlength="50" id="txGastoMain"
													onkeyup="validaCampo('noCaracteresEspeciales','txGastoMain','gastosForm');"
													/>
											</td>
										</tr>
										<tr valign="top" class="impar">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>			
									</tbody>
								</table>
								<br>
								<table>
									<tbody>
										<tr align="center">
											<td align="center" width="100px" colspan="2">
										  		<a href="#" onclick="javascript:buscar();"><span class="boton">Buscar</span></a>
										  	</td>
											<td align="center" width="50px" colspan="2">
										  		<a href="#" onclick="javascript:limpiar();"><span class="boton">Limpiar</span></a>
										  	</td>
										  	<td align="center" width="100px" colspan="2">
												<a href="#" onclick="javascript:registra();"><span class="boton">Agregar</span></a>
											</td>
										  	
										</tr>	
										<tr valign="top" class="par">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>
									</tbody>
								</table>							
									
							</td>
						</tr>
					</table>
										
				</fieldset>
			</form:form>							    
	  	</div>
	</div>
			
			<div id="registroGasto">
				<jsp:include page="gastosRegistro.jsp" />
			</div>
		
			
			<div id="gastoData" style="overflow: auto; width:900px; height:320px;" align="center">
				<jsp:include page="gastosData.jsp" />
			</div>
			
			<div id="gastoBorrar" align="center">
				<jsp:include page="gastosBorrar.jsp" />
			</div>
			
			<div id="gastoButtons">
				<jsp:include page="gastosButtons.jsp" />
			</div>
			
			<br>	    	    
</div>	    
	
