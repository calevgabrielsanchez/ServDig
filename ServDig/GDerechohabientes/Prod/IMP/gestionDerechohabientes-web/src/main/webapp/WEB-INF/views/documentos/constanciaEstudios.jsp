<%@ include file="../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/constanciaEstudios.js" htmlEscape="true" />"></script>
<div class="form-comment">
	<form id="formdocument">
		<fieldset>
			<legend><strong>Constancia de estudios</strong></legend>
			<center>
			<table>
				<tr >
					<td align="left">Nivel de estudios:</td>
					<td align="left">
						<select name="idTipoNivelEducativo" id="selectTipoNivelEducativo" onchange="getDetalleNivel();"></select>
					</td>
				</tr>
				<tr >
					<td align="left">Detalle del nivel de estudios:</td>
					<td align="left">
						<select name="idNivelEducativo" id="selectNivelEducativo"></select>
					</td>
				</tr>
				<tr >
					<td align="left">Nombre de la instituci&oacute;n educativa:</td>
					<td align="left">
						<input class="alfanumerico_espacios"  type="text" name="nombreEscuela" value="" style="width: 200px" maxlength="30"/>
					</td>
				</tr>
				
				<tr >
					<td align="left">Clave de la instituci&oacute;n educativa:</td>
					<td align="left">
						<input class="alfanumerico"  type="text"  name="claveEscuela" value="" style="width: 200px" maxlength="15"/>
					</td>
				</tr>
				
				<tr >
					<td align="left">N&uacute;mero de incorporaci&oacute;n a la SEP:</td>
					<td align="left">
						<input class="alfanumerico" type="text"  name="noIncorporacion" value="" style="width: 200px" maxlength="15"/>
					</td>
				</tr>
				<tr >
					<td align="left">Grado escolar:</td>
					<td align="left">
						<input class="alfanumerico" type="text"  name="gradoEscolar" value="" style="width: 200px" maxlength="15"/>
					</td>
				</tr>
				<tr >
					<td align="left">Fecha de expedici&oacute;n:</td>
					<td td align="left">
						<input type="text" id="fechaExpedicion" name="fechaExpedicionString" value="" style="width: 200px" readonly="readonly" onclick=" $( this ).datepicker();"/>	
					</td>
				</tr>
				<tr >
					<td align="left">Fecha de inicio del periodo escolar:</td>
					<td align="left">						
						<input type="text"  id="fechaInicioPeriodo" name="fechaInicioPeriodo" value="" style="width: 200px" onclick=" $( this ).datepicker()";/>
					</td>
				</tr>
				
				<tr >
					<td align="left">Fecha de t&eacute;rmino del periodo escolar :</td>
					<td align="left">
						<input type="text"  id="fechaFinPeriodo" name="fechaFinPeriodo" value="" style="width: 200px" readonly="readonly" onclick=" $( this ).datepicker();" />
					</td>
				</tr>
			</table>
			</center>
		</fieldset>
	</form>
</div>		