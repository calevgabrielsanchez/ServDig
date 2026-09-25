



<%@ include file="../general/taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/tramite/tramite.js" htmlEscape="true" />"></script>
<div class="contenedor">

	<div class="row">
		<div class="cell" id="solicitud">
			
				<div id="persona" class="row">
				
				</div>
				
				<div id="domicilio" class="row">
				
					<!--  Datos del domicilio -->
					<div class="cell form-comment" style="padding: 15px;">
					
						<div id="wrapperIntsAnterior" class="ui-widget"
											style="width: 550px !important;" align="center">
											<div class="ui-state-highlight ui-corner-all"
												style="margin-top: 20px; padding: 0 .5em;">
												<p>
													<span class="ui-icon ui-icon-info"
														style="float: left; margin-right: .3em;"></span> <strong>Localize su domicilio:</strong> Registre los datos de su domicilio <strong><a
														href="javascript:fnOpenDomicilio();"> aqu&iacute;</a></strong>
												</p>
											</div>
										</div>
					
						<form id="form">
						<fieldset style="margin: 20px !important;">
							<legend>
								<strong>Domicilio:</strong>
							</legend>
							
							<fieldset class="fsInterno">
								<label class="">Domicilio localizado: </label> 
								<textarea id="domicilioLocalizado" name="domicilioLocalizado"   readonly="readonly" >
								</textarea>
							</fieldset>
							
						</fieldset>
					</form>
					
					</div>
				
				
				</div>
					
		</div>
	</div>



</div>