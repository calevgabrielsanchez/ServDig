<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
	<style>
		label.error { float: none; font-size:smaller; color: red; padding-left: .5em; vertical-align: top; }
		label  {  float: none; }
	</style>

	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/promocion/promocionCritSeleccion.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
	<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/jquery/jquery.blockUI.1.33.js"></script>
	


	<div id="cuerpo">
		<div class="menu_principal" style="height: 2em !important;">
			<div align="center">
				<div class="centrado">
					<ul style="height: 1em !important;">
						<li><a>Selector</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div id="dialog-error" title="Mensaje de SISCONET">
			<p>
				<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>
				Your files have downloaded successfully into the My Downloads folder.
			</p>
			<p>
				Currently using <b>36% of your storage space</b>.
			</p>
		</div>
		<div id="dialog-confirm" title="¿Subir archivo EXCEL?">
			<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>El archivo se subira pero antes tiene que verificar si el archivo no esta abierto ¿?</p>
		</div>		
		<form:form modelAttribute="promocionCargaModel" method="POST" action="/promocion/carga/cargarXls.do" enctype="multipart/form-data">
			<div id="headerDialog"  style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity = 70) !important;">
			<input type="hidden" id="detalles" value="<%=request.getAttribute("detalles")%>">			
			<input type="hidden" id="idContexto" value="<%=request.getContextPath()%>">
			<input type="hidden" id="rutaCriterioSeleccion" value="<%=request.getContextPath()%>/promocion/carga/buscarCriteriosSeleccion.do">
				<div id="wrapperHeaderDialog" style="background-color: #f2fff2;">
					<fieldset>
						<table style="width: 900px" align="center">
							<tr valign="middle">
								<td align="center" width="900px">
									
										<table class="tablaverde2" style="width: 900px">
											<tbody>
												<tr valign="top" class="impar">
													<td align="center" colspan="4"><b>Descargar Plantilla Promoci&oacute;n</b></td>
												</tr>
												<tr>
													<td align="center" width="50%" colspan="2">
														<label for="subDelegacion" id="subDelegacionLabelID">Descargar Plantilla Promoci&oacute;n: </label>
													</td>
													<td align="center" width="50%" colspan="2">
												  		<a href="#" id="btnDescargar"><span class="boton">Descargar</span></a>
												  	</td>
												</tr>
											</tbody>
										</table>
								</td>
							</tr>
						</table>
					</fieldset>
				</div>
				<div id="cargaCedula" style="background-color: #f2fff2;" title="Cargar Promoci&oacute;n">
					<fieldset>
						<table style="width: 900px" align="center">
							<tr valign="middle">
								<td align="center" width="900px">
									<table class="tablaverde2" style="width: 900px" >
										<tbody>
											<tr valign="top" class="impar">
												<td align="center" colspan="4"><b>Cargar Archivo - Promoci&oacute;n </b></td>
											</tr>
											<tr>
												<td width="35%" align="left"><label for="tipoPromocionLbl" id="tipoPromocionLbl">Tipo Promoci&oacute;n: </label></td>
												<td width="65%" align="left">
													<form:select path="idTipo" cssStyle="width:400px">
														<form:option value="-1" label="<-----Seleccione un Tipo----->" />
														<form:options items="${promocionCargaModel.tiposPromocion}" itemValue="id" itemLabel="descripcion" />
													</form:select>
												</td>
												
											</tr>
											<tr>
											<td width="35%" align="left"><label for="origenLbl" id="origenLbl">Origen: </label></td>
												<td width="65%" align="left">
													<form:select path="idOrigen" cssStyle="width:400px">
														<form:option value="-1" label="<-----Seleccione un Origen----->" />
														<form:options items="${promocionCargaModel.origenes}" itemValue="id" itemLabel="descripcion" />
													</form:select>
												</td>
											</tr>
											<tr>
												<td width="35%" align="left"><label for="criterioSeleccionLbl" id="criterioSeleccionLbl">Criterio de Selecci&oacute;n: </label></td>
												<td width="65%" align="left" >
													<form:select path="idCriterio" cssStyle="width:400px">
														<form:option value="-1" label="<-Seleccione un Criterio de Selección->" />
													</form:select>
												</td>
											</tr>
											<tr>
												<td colspan="2" width="100%">&nbsp;</td>
											</tr>
										
											<tr>
												<td align="left" width="20%">Archivo a Cargar: </td>
												<td align="center" width="30%">
												  		<form:input type="file" path="archivo"/>
												</td>
												
											</tr>
											<tr>
												<td align="right" width="50%" colspan="2">
												  		<a href="#" id="btnCargar"><span class="boton">Cargar Archivo</span></a>
												</td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
						</table>
					</fieldset>
				</div>
				<div id="cargaArchivoStatus" style="background-color: #f2fff2;">
					<fieldset>
						<table style="width: 900px" align="center">
							<tr valign="middle">
								<td align="center" width="900px">
									<div id="iframe" style="width:0px; height:0px; visibility:none;"></div>
									<!-- div id="textarea"></div -->									
								</td>
							</tr>
						</table>
					</fieldset>
				</div>
			</div>
		</form:form>
	</div>
</html>

