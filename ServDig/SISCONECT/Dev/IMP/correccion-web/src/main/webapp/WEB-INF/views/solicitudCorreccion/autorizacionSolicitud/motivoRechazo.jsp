<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<div id="dgSolRechazar"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">

	<div id="wrapperDialogModif" style="background-color: #f2fff2;">
		<form  action="/solicitud/correcion/modificarInsc.do"	method="post" id="patInsFormMotivo">
			<fieldset>
				<legend>Datos del patron modificar:</legend>
				<p class="par">
					<label>Referencia: </label>
					<br />
					<input type="text" maxlength="30" id="txRefRechazo"></input>
				</p>	
				<p class="impar">
					<label>Causa del rechazo: </label>
					<br />
					<textarea rows="5" cols="80" maxlength="300" id="motivoRechazo"></textarea>
				</p>				
				<p class="par">
					<label>Motivo: </label>
					<br />
					<combo:creaCombo 
						entidad="mx.gob.imss.ctirss.correccion.model.CgcCatMotivoRechazo"
						idHtml="idMotivoRechazo"
						idHtmlContenedor="patInsFormMotivo"	  														
					/>
				</p>
			</fieldset>
		</form>
	</div>
</div>