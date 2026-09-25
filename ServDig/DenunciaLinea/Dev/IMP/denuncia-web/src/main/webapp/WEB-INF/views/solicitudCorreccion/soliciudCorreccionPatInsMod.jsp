<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="dgPatInsModificar" title=" Modificar Elemento" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">

	<div id="wrapperDialogModif" style="background-color: #f2fff2;">
		<form  action="/solicitud/correcion/modificarInsc.do"	method="post" id="patInsFormModificar">
			<fieldset>
				<legend>Datos del patron modificar:</legend>
				<p class="impar">
					<label>Registro	Patronal a corregir: </label>
					<br />
					<input type="text" id="registroPatronal" size="20" maxlength="10" readonly="readonly"/>
				</p>				
				<p class="par">
					<label>N&uacute;mero de trabajadores: </label>
					<br />
					<input type="text" id="trabajadores" size="20" maxlength="3" onkeyup="validaCampo('PermiteSoloNumeros','trabajadores','patInsFormModificar');"/>
				</p>
				<p class="impar">
					<label>Clase: </label>
					<br />
					<input type="text" id="clase" size="20" maxlength="5" onkeyup="validaCampo('PermiteSoloNumerosYPunto','clase','patInsFormModificar')"/>
				</p>
				<p class="par">
					<label>Fracci&oacute;n: </label>
					<br />
					<input type="text" id="fraccion" size="20" maxlength="5" onkeyup="validaCampo('PermiteSoloNumerosYPunto','fraccion','patInsFormModificar')"/>
				</p>
				<p class="impar">
					<label>Prima: </label>
					<br />
					<input type="text" id="prima" size="20" maxlength="6" onkeyup="validaCampo('PermiteSoloNumerosYPunto','prima','patInsFormModificar')"/>
				</p>
				<p class="par">
					<label>Actividad: </label>
					<br />
					<input type="text" id="actividad" size="20" maxlength="60"/>
				</p>
			</fieldset>
		</form>
	</div>
</div>