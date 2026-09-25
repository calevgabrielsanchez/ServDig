<%@ include file="../general/taglibs.jsp"%>
<%@taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>


<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
	<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
	
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/additional-methods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/credencialElector.js" htmlEscape="true" />"></script>
<div class="form-comment">
<form id="formdocument">
	<fieldset>
		<legend><strong>Credencial elector</strong></legend>
		<div id="divErrorIfe" style="display:none">
			<div class="ui-widget" style="width:100%">
				<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
					<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
					<span id="mensajeErrorIfe"></span></p>
				</div>
			</div>
		</div>
		<div id="divTipoCredencial">
			<table class="table table-striped table-bordered" style="width: 100%">
				<tr >
					<td style="width: 200px">
						Tipo credencial:
					</td>
					<td>
						<select class = "form-control" id="tipoCredencial" name = "tipoCredencial" style="width: 300px">
							<option value="">Elige por favor</option>
							<option value="A">A</option>
							<option value="B">B</option>
							<option value="C">C</option>
							<option value="D">D</option>
							<option value="E">E</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<div class="ui-widget" style="width: 95%">
							<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em; width: 100%;">
								<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
									<strong>Si no conoces el tipo de credencial que tienes  
									<a href="http://listanominal.ife.org.mx/consulta_permanente_ln.htm" target="_blank"
									style="color: blue">verif&iacute;calo aqu&iacute;</a>
									</strong>.
								</p>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="camposAnioNumero" style="display:none">
			<table class="table table-striped table-bordered"  style="width: 100%">
				<tr>
					<td style="width: 200px">
						<label for="anioRegistro">A&ntilde;o de expedici&oacute;n:</label>
					</td>
					<td>
						<input type="text"  class="entero_4 form-control" name="anioRegistro" id = "anioRegistro" style="width: 300px" maxlength="4"/>
					</td>
				</tr>
				<tr>
					<td>
						<label id="labelCodigo" for="codigoSeguridad">N&uacute;mero(OCR) :</label>
					</td>
					<td align="left">
						<input type="text"  class="alfanumerico_15 form-control" name="codigoSeguridad" id = "codigoSeguridad" value="" style="width: 300px" maxlength="13"/>
					</td>
				</tr>
			</table>
		</div>		
		<div id="camposComplementarios" style="display:none">
			<table class="table table-striped table-bordered"  style="width: 100%">
				<tr>
					<td style="width: 200px">
						<label for="emision">N&uacute;mero de emisi&oacute;n:</label>
					</td>
					<td>
						<input type="text" name="emision" id="emision" value="" style="width: 300px" maxlength="2"/>
					</td>
				</tr>
				<tr >
					<td align="left">
						<label for="claveElector">Clave de elector:</label>
					</td>
					<td align="left">
						<input type="text" class="alfanumerico_15 form-control" name="claveElector" id="claveElector" value="" style="width: 300px" maxlength="18"/>
					</td>
				</tr>			
			</table>
		</div>
	</fieldset>
	
</form>


</div>		