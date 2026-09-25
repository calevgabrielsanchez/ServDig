<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<html lang="sp">
 

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>


		
		<div id="cuerpo">
			
			
			<div class="menu_principal" style="height: 2em !important;"> 
		      <!--inicia menu principal-->
		      <div align="center">
		        <div class="centrado">
		          <ul style="height: 1em !important;">
		            <li><a> Descarga de Cedulas</a></li>
		          </ul>
		        </div>
		        <!--fin centrado--> 
		      </div>
		    </div>
		    
		    <div id="dgGasto"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
		<div id="wrapperDialogDescCedulaCorreccion" style="background-color: #f2fff2;">	
			<form:form modelAttribute="descargaCedula" action="descarga/archivo.do"
						method="post" id="descCedulaCorreccionForm">
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
											<td align="left" width="100px" colspan="1"><form:label
													id="folioCorreccionLabel" for="folioCorreccion" path="folioCorreccion" 
													cssErrorClass="error"> folioCorreccion: </form:label>
											</td>
											<td align="left" width="100px" colspan="1"><form:input
													path="folioCorreccion" value="" label="folio correccion" />
											</td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="100px" colspan="1"><form:label
													id="periodoLabel" for="periodo" path="periodo" 
													cssErrorClass="error"> periodo: </form:label>
											</td>
											<td align="left" width="100px" colspan="1"><form:input
													path="periodo" value="" label="periodo" />
											</td>
										</tr>
										
										<tr valign="top" class="par">
											<td align="left" width="100px" colspan="1"><form:label
													id="idArchivoDescargaLabel" for="idArchivoDescarga" path="idArchivoDescarga" 
													cssErrorClass="error"> Cedula: </form:label>
											</td>
											<td align="left" width="100px" colspan="1">
												<form:select path="idArchivoDescarga" label="Cedula" >
													<form:option value="1">Cedula A</form:option>
													<form:option value="2">Cedula G</form:option>
													<form:option value="3">Cedula H</form:option>
													<form:option value="4">Cedula I</form:option>
													<form:option value="5">Cedula O</form:option>
													<form:option value="6">Cedula Q</form:option>
												</form:select>
											</td>
										</tr>
										
										<tr align="center">
										  <td align="center" width="100px" colspan="4">
										  	<a href="#" onclick="document.forms[0].submit()"><span class="boton">Descargar</span></a>
										  </td>
										</tr>	
										<tr valign="top" class="impar">
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

</div>	    
	
