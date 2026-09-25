<%@ include file="../../../general/taglibs.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<script>
	var context="${contextpath}";
</script>

<script type="text/javascript" src=" <spring:url value="/static/resources/js/movPat/internet/common/login.js" htmlEscape="true" />"></script>	
<script src="https://www.google.com/recaptcha/api.js" async defer></script>


<div id="divContenidoCita" class="contenido-cita">
	<div class="separadorseccion text-center">
		<h4>
			Ingresar al sistema 
		</h4>
	</div>
	
<div>
<form class="form-horizontal" id="formRegistroPatronal" action="#" method="get">
	<div id="divCurp">
			<div class="form-group">
				<label class="control-label col-sm-3" for="curp"> CURP <span class="required">(*)</span>:
				</label>
				<div class="col-sm-5">
					<input type="text" class="form-control" name="curp"
						id="curp" value="" maxlength="18"
						placeholder="Ingresa la CURP" />
				</div>
			</div>
	</div>
	
	<div id="divEmail">
			<div class="form-group">
				<label class="control-label col-sm-3" for="email"> 
					Correo electr&oacute;nico <span class="required">(*)</span>:
				</label>
				<div class="col-sm-5">
					<input type="text" class="form-control" name="email"
						id="email" value="" maxlength="100"
						placeholder="Ingresa el correo electr&oacute;nico" />
				</div>
			</div>
	</div>
	<div id="divEmail2">
			<div class="form-group">
				<label class="control-label col-sm-3" for="confirmemail"> 
					Confirma tu correo electr&oacute;nico <span class="required">(*)</span>:
				</label>
				<div class="col-sm-5">
					<input type="text" class="form-control" name="confirmemail"
						id="confirmemail" value="" maxlength="100"
						placeholder="Confirma el correo electr&oacute;nico" />
				</div>
			</div>
	</div>
	
		<div id="divRp">
			<div class="form-group">
				<label class="control-label col-sm-3" for="registroPatronal"> 
					Registro patronal <span class="required">(*)</span>:
				</label>
				<div class="col-sm-5">
					<input type="text" class="form-control" name="registroPatronal"
						id="registroPatronal" value="" maxlength="10"
						placeholder="Ingresa el Registro Patronal" />
				</div>
			</div>
	</div>
	
	<div class="form-group">
	<div class="col-sm-8 top-8" style="text-align: right;">
	<label class="control-label col-sm-8" for="g-recaptcha"> 
				<span class="required"> Los campos marcados con (*) son obligatorios</span>
				</label>
	</div>
	</div>
	<div class="col-sm-12 top-12" > <br>  </div>
	
	<div id="divCaptcha" class="form-group">
	<div class="col-sm-3 top-3" style="text-align: right;">
	<label class="control-label col-sm-3" for="g-recaptcha"> 
				<span class="required">  </span>
				</label>
	</div>
			<div id="captchaRequired" class="col-sm-5 top-5" >
			<div id="g-recaptcha" class="g-recaptcha" style="text-align: right;" data-sitekey="6Ldc7TsoAAAAANw3sblPTvbcMVv4yslmEes1HpXI"></div>
			</div>
	</div>
	<div class="col-sm-12 top-12" > <br>  </div>

	<div id="divbutton" >
				<div class="col-sm-8 top-8" style="text-align: right;">
					<input type="button" id="btnIngresar"
					class="btn btn-default" 
					name="continuar"
					value="Continuar"
					style="width:150px"/>
					<input type="button" id="btnSalir" class="btn btn-primary" name="salir"
					value="Salir" onclick="salirLogin()" style="width:150px"/>
				</div>
	</div>
</form>
</div>

</div>



<div id="dialogoMensajes">
	<p>
		<span id="textoMensaje"></span>
	</p>
</div>

<div id="wizardModificacionClasificacionVentanilla"></div>
<div id="reporteFrame"></div>
<div id="domiciliosComponent"></div>

