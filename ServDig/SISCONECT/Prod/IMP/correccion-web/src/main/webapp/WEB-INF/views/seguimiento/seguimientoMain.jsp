<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/tabControler.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/estudioCorreccion/controlSeguimiento.js"></script>

<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/resources/estilos/styleTabs.css">

<div id="cuerpo">

	<div class="menu_principal" style="height: 2em !important;">
		<!--inicia menu principal-->
		<div align="center">
			<div class="centrado">
				<ul style="height: 1em !important;">
					<li><a> Seguimiento</a></li>
				</ul>
			</div>
			<!--fin centrado-->
		</div>
	</div>

	<div id="dgSeguimiento" 
		style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity =   70) !important;">
		<table style="width: 900px" align="center">
			<tr valign="middle">
				<td align="left" width="900px">

					<table width="100%" align="left" border="0">
						<tr>
							<td>
								<ul class="tabs">
									<li class="etiqueta4"><a href="#seguimiento">Seguimiento</a></li>
									<li class="etiqueta4"><a href="#devSubDelegacion">Derivación a subdelegación</a></li>
									<li class="etiqueta4"><a href="#reasignarRevisor">Reasignar	revisor</a></li>
									<li class="etiqueta4"><a href="#cedulaRevision">Cédula revisión</a></li>
									<li class="etiqueta4"><a href="#reqDocumentacion">Requerimiento	documentación</a></li>
									<li class="etiqueta4"><a href="#cedulaValidacion">Cédula validación</a></li>
									<li class="etiqueta4"><a href="#ofDiferencias">Oficio diferencias</a></li>
									<li class="etiqueta4"><a href="#derivacionFiscalizacion">Derivación	a fiscalización</a></li>
									<li class="etiqueta4"><a href="#derivacionDictamen">Derivación a dictamen</a></li>
									<li class="etiqueta4"><a href="#cancelacion">Cancelación</a></li>
									<li class="etiqueta4"><a href="#conclusion">Conclusión</a></li>
								</ul>
							</td>
						</tr>
						<tr>
							<td height="30px"></td>
						</tr>
						<tr>
							<td>
								<div id="seguimiento" class="tab_content">A</div>
								<div id="devSubDelegacion" class="tab_content">B</div>
								<div id="reasignarRevisor" class="tab_content">C</div>
								<div id="cedulaRevision" class="tab_content">D</div>
								<div id="reqDocumentacion" class="tab_content">F</div>
								<div id="cedulaValidacion" class="tab_content">G</div>
								<div id="ofDiferencias" class="tab_content">H</div>
								<div id="derivacionFiscalizacion" class="tab_content">I</div>
								<div id="derivacionDictamen" class="tab_content">J</div>
								<div id="cancelacion" class="tab_content">K</div>
								<div id="conclusion" class="tab_content">L</div>

							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>


	</div>
</div>