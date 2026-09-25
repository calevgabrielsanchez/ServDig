<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<script type="text/javascript"
	src="https://maps.google.com/maps/api/js?sensor=false&region=MX"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/domicilios/domicilios.js" htmlEscape="true" />"></script>
	
<div class="page_holder">
	<div class=" contenedor" style="width: 100% !important">
	<div class="row">

		<div class="cell" >
			
			<div class="row" id="domicilio" style="width: 1000px;" >
				<div class="cell form-comment" id="captura" style=" float:right;  width:500px !important; height: 700px !important; ">
				
					<div id="wrapperIntsAnterior" class="ui-widget"
											style="width: 450px !important;" align="center">
											<div class="ui-state-highlight ui-corner-all"
												style="margin-top: 20px; padding: 0 .5em;">
												<p>
													<span class="ui-icon ui-icon-info"
														style="float: left; margin-right: .3em;"></span> <strong>Para iniciar:</strong> capture su Codigo Postal, Asentamiento y Calle 
												</p>
											</div>
										</div>
				
					<form id="form">
						<fieldset style="margin: 20px !important;">
							<legend>
								<strong>Datos de su domicilio</strong>
							</legend>
							
							<fieldset class="fsInterno">
								<label class="">Codigo postal: </label> 
								<input id="codigoPostal" name="codigoPostal"  type="text" />
							</fieldset>
							<fieldset class="fsInterno">
								<label class="">Asentamiento: </label> 
								<select id="nombreAsentamiento" name="nombreAsentamiento"  >
								<option>-- Seleccione --</option>
								</select>
							</fieldset>
							<fieldset class="fsInterno">
								<label class="">Calle: </label> 
								<input id="calle" name="calle"  type="text" />
							</fieldset>
							
							<fieldset class="fsInterno">
								<label class="">Tipo asentamiento: </label> 
								<combo:creaCombo idHtml="tipoAsentamiento" idHtmlContenedor="form" 
						entidad="mx.gob.imss.ctirss.delta.persistence.DgCatTipoAsen"
						mostrarSoloActivos = "false" />
							</fieldset>
							
							<fieldset class="fsInterno">
								<label class="">Tipo vialidad: </label> 
								<combo:creaCombo idHtml="tipoVialidad" idHtmlContenedor="form" 
						entidad="mx.gob.imss.ctirss.delta.persistence.DgCatVialidad"
						mostrarSoloActivos = "false" />
							</fieldset>
							
							
							<fieldset class="fsInterno">
								<label class=""># N&uacute;mero: </label> 
								<input id="numero" name="numero"  type="text" />
							</fieldset>
							
							<fieldset class="fsInterno">
								<label class="">Vialidad referencia primaria: </label> 
								<input id="vialidadRef1" name="vialidadRef1"  type="text" />
							</fieldset>
							<fieldset class="fsInterno">
								<label class="">Vialidad referencia secundaria: </label> 
								<input id="vialidadRef2" name="vialidadRef2"  type="text" />
							</fieldset>
							
							
							<fieldset class="fsInterno">
								<label class="">Localidad: </label> 
								<input id="nombreLocalidad" name="nombreLocalidad"  type="text" />
							</fieldset>
							
							<fieldset class="fsInterno">
								<label class="">Municipio: </label> 
								<input id="nombreMunicipio" name="nombreMunicipio"  type="text" />
							</fieldset>
							
							
							<fieldset class="fsInterno">
								<label class="">Entidad federativa: </label> 
								<input id="nombreEntidadFederativa" name="nombreEntidadFederativa"  type="text" />
							</fieldset>
							
							
						</fieldset>
					</form>
					
				</div>
			</div>
			
		

		</div>

		<div id="mapa" class="cell" style="width:500px !important; height: 700px !important; " >
			<div id="map_canvas" style="width: 100%; height: 100%">
				</div>
		</div>

		</div>

	</div>
	
	
</div>
<!-- 
style="float: left; width:500px !important; height: 700px !important; "
 style="width:500px !important; height: 700px !important; "-->

			