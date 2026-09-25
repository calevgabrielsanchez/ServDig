<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
		
<style>
	label.error { float: none; font-size:smaller; color: red; padding-left: .5em; vertical-align: top; }
	label  {  float: none; }
</style>

<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/delta/solicitud/autorizacion/autorizacion.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/jquery/jquery.blockUI.1.33.js"></script>


<br />
<div class="menu_principal" style="height: 2em !important;">
	<!--inicia menu principal-->
	<div align="center">
		<div class="centrado">
			<ul style="height: 1em !important;">
				<li><a> Autorizaci&oacute;n de Solicitud de Correcci&oacute;n</a>
				</li>
			</ul>
		</div>
		<!--fin centrado-->
	</div>
</div>
<br />


<div id="dgsolicitudCorreccionPendientes" style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity =     70) !important;">
	<div id="wrapperDialogSolCorr" style="background-color: #f2fff2;">
		<fieldset>
			<form action="autorizacion/muestraDetalle.do" method="post" id="autorizacionForm">
			</form>
			<table id="dtPatronesInscritos" style="width: 900px">
				<thead>
				</thead>
				<tbody>
				</tbody>
			</table>
		</fieldset>
	</div>
</div>

<br />

<div id="dgAutorizaSolicitudCorreccion"  style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity =     70) !important;">
	<div id="wrapperDialogSolCorr2" style="background-color: #f2fff2;">
		<fieldset>
			<table class="tablaverde2" style="width: 900px" align="center">
				<tr valign="middle">
					<td align="center" width="900px">
						<table style="width: 900px" border="0">
							<tbody>
								<tr>
									<td align="center">
										<a href="#" id="btnAutoriza"><span class="boton">Continuar</span> </a>
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
		</fieldset>
	</div>
</div>
