<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
	<style>
		label.error { float: none; font-size:smaller; color: red; padding-left: .5em; vertical-align: top; }
		label  {  float: none; }
	</style>

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/delta/catalogos/deteccion/deteccionCarga.js"></script>
		
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
		
	<div id="cuerpo">
		<div class="menu_principal" style="height: 2em !important;">
			<div align="center">
				<div class="centrado">
					<ul style="height: 1em !important;">
						<li><a>Detecci&oacute;n</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div id="dialog-error" title="Mensaje de SICONET">
			<p>
				<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>
				Your files have downloaded successfully into the My Downloads folder.
			</p>
			<p>
				Currently using <b>36% of your storage space</b>.
			</p>
		</div>
		<div id="dialog-confirm" title="�Subir archivo EXCEL?">
			<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>El archivo se subira pero antes tiene que verificar si el archivo no esta abierto �?</p>
		</div>
		<form:form modelAttribute="deteccionCargaModel" method="POST" action="/deteccion/carga/cargarXls.do" enctype="multipart/form-data">
			<div id="headerDialog" title="Cargar / Descargar C&eacute;dula Promoci&oacute;n" style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity = 70) !important;">
			<input type="hidden" id="idContexto" value="<%=request.getContextPath()%>">
				<div id="wrapperHeaderDialog" style="background-color: #f2fff2;">
					<fieldset>
						<table style="width: 900px" align="center">
							<tr valign="middle">
								<td align="center" width="900px">
									
										<table class="tablaverde2" style="width: 900px">
											<tbody>
												<tr valign="top" class="impar">
													<td align="center" colspan="4"><b>Descargar Plantilla Detecci&oacute;n</b></td>
												</tr>
												<tr>
													<td align="center" width="50%" colspan="2">
														<label for="label1" id="label1">Descargar Plantilla Detecci&oacute;n: </label>
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
									<table class="tablaverde2" style="width: 900px">
										<tbody>
											<tr valign="top" class="impar">
												<td align="center" colspan="4"><b>Cargar Archivo - Detecci&oacute;n </b></td>
											</tr>
											<tr>
												<td colspan="4" width="100%">&nbsp;</td>
											</tr>
											<tr>
												<td align="left" width="20%">Archivo a cargar: </td>
												<td align="center" width="30%">
												  		<form:input type="file" path="archivo"/>
												</td>
												<td align="center" width="50%" colspan="2">
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