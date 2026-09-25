<%@ include file="../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/additional-methods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/constanciaEstudios.js" htmlEscape="true" />"></script>
<div class="form-comment">
	<form id="formdocument">
		<fieldset>
			<table class="table table-striped table-bordered">
				<tr >
					<td align="left">
						<label class="control-label" for="selectTipoNivelEducativo">Nivel de estudios<span class="required">*</span>:</label>
					</td>
					<td align="left">
						<select class="form-control" name="idTipoNivelEducativo" id="selectTipoNivelEducativo" onchange="getDetalleNivel();"></select>
					</td>
				</tr>
				<tr >
					<td align="left">
						<label class="control-label" for="selectNivelEducativo">Detalle del nivel de estudios<span class="required">*</span>:</label>
					</td>
					<td align="left">
						<select class="form-control" name="idNivelEducativo" id="selectNivelEducativo"></select>
					</td>
				</tr>
				<tr >
					<td align="left">
						<label class="control-label" for="nombreEscuela">Nombre de la instituci&oacute;n educativa<span class="required">*</span>:</label>
					</td>
					<td align="left">
						<input class="alfanumerico_espacios form-control"  type="text" name="nombreEscuela" value="" maxlength="30"/>
					</td>
				</tr>
				
				<tr >
					<td align="left">
						<label class="control-label" for="claveEscuela">Clave de la instituci&oacute;n educativa<span class="required">*</span>:</label>
					</td>
					<td align="left">
						<input class="alfanumerico form-control"  type="text"  name="claveEscuela" value="" maxlength="15"/>
					</td>
				</tr>
				
				<tr >
					<td align="left">
						<label class="control-label" for="noIncorporacion">N&uacute;mero de incorporaci&oacute;n a la SEP<span class="required">*</span>:</label>
					</td>
					<td align="left">
						<input class="alfanumerico form-control" type="text"  name="noIncorporacion" value="" maxlength="15"/>
					</td>
				</tr>
				<tr >
					<td align="left">
						<label class="control-label" for="gradoEscolar">Grado escolar<span class="required">*</span>:</label>
					</td>
					<td align="left">
						<input class="alfanumerico form-control" type="text"  name="gradoEscolar" value="" maxlength="15"/>
					</td>
				</tr>
				<tr >
					<td align="left">
						<label class="control-label" for="fechaExpedicion">Fecha de expedici&oacute;n<span class="required">*</span>:</label>
					</td>
					<td align="left">
						<input type="text" class="form-control" id="fechaExpedicion" name="fechaExpedicionString" value="" readonly="readonly"/>	
					</td>
				</tr>
				<tr >
					<td align="left">
						<label class="control-label" for="fechaInicioPeriodo">Fecha de inicio del periodo escolar<span class="required">*</span>:</label>
					</td>
					<td align="left">						
						<input type="text" class="form-control" id="fechaInicioPeriodo" name="fechaInicioPeriodo" value="" readonly="readonly"/>
					</td>
				</tr>
				
				<tr >
					<td align="left">
						<label class="control-label" for="fechaFinPeriodo">Fecha de t&eacute;rmino del periodo escolar<span class="required">*</span>:</label>
					</td>
					<td align="left">
						<input type="text" class="form-control" id="fechaFinPeriodo" name="fechaFinPeriodo" value="" readonly="readonly"/>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>		