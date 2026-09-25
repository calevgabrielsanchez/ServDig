<%@ include file="../general/taglibs.jsp"%>

<script>

var dialogo = null;
$(document).ready(function() {
	dialogo = $("#divDictamen").dialog({
		title : 'Detalle dictamen',
		autoOpen : false,
		width : 900,
		hight: 900,
		modal : true,
		resizable : true,
		overlay : {
			opacity : 0.5,
			background : "black"
		},
		position : {
			my : "top",
			at : "top",
			of : window,
			offset : "0 10"
		}
	});
	
	$("#verDictamen").click(function(){mostrarDictamen()});
});

function mostrarDictamen() {
	
	$("#divDictamen").html('<iframe id="frameDictamen" src="/gestionClasificacion-web/solicitud/dictamen/detalle/${dictamen.cveIdPatronDictamen}" width="100%" height="100%" frameborder="0"'
			+ '/>');
	dialogo.dialog('open');
}
	
</script>
<c:set var="domicilioFiscal" 			value="${patron.domicilioFiscal}"/>
<c:set var="centroTrabajo" 				value="${patron.cntroTrabajo}"/>
<div class="page_holder_no_height">
	<div class="post_entry_wide no-border">
		<!--Aquí pega tu código-->
		<div class="form-comment">
				<div class="marcointerno">	
				<div class="separadorseccion">
						<spring:message code="label.detalle.datos.generales.titulo" />
					</div>
				<fieldset>
					<legend>
						<strong>Datos generales</strong>
					</legend>
					
					<!-- *****************  Inicia Renglón DATOS GENERALES ********************** -->
					<div class="div-2secciones">
						<span class="etiqueta"><spring:message
								code="label.detalle.registro.patronal" />:</span> <span class="dato"
							id="RP_PATRON">${dictamen.registroPatronal}</span>
					</div>
					<c:if test="${patron.fisica != null}">
						<c:set var="rfcPatron" value="${patron.fisica.rfc}" />
					</c:if>
					<c:if test="${patron.moral != null}">
						<c:set var="rfcPatron" value="${patron.moral.rfc}" />
					</c:if>
					<div class="div-2secciones">
						<span class="etiqueta"><spring:message code="label.detalle.rfc" />:</span> <span class="dato" id="RFC_PATRON">${rfcPatron}</span>
					</div>
					<c:if test="${patron.moral != null}">
						<div class="div-1seccion">
							<span class="etiqueta"><spring:message code="label.detalle.razon.social" />:</span> 
							<span class="dato" id="RAZON_SOCIAL">${patron.moral.razonSocial}</span>
						</div>
						<div class="div-1seccion">
							<span class="etiqueta"><spring:message code="label.detalle.tipo.sociedad" />:</span> 
							<span class="dato" id="RAZON_SOCIAL">${patron.moral.tipoSociedad.descripcion}</span>
						</div>
					</c:if>
					<c:if test="${patron.fisica != null}">
						<div class="div-2secciones">
							<span class="etiqueta"><<spring:message code="label.detalle.nombre" />:
							</span> <span class="dato" id="RAZON_SOCIAL">${patron.fisica.nombreCompleto}</span>
						</div>
						<div class="div-2secciones">
							<span class="etiqueta"><spring:message code="label.detalle.curp" />:</span> 
							<span class="dato" id="CURP">${patron.fisica.curp}</span>
						</div>
					</c:if>

				</fieldset>
				<!-- *****************  Inicia Renglón  DOMICILIO FISCAL ********************** -->
						<div class="div-1seccion fielsetgris">
		 					<fieldset>
		 						<legend style="text-align:center">
		 							<b><spring:message code="label.detalle.domicilio.fiscal" /></b>
		 						</legend>

								<!-- *****************  Inicia Renglón  ********************** -->
		 						<div class="div-1seccion">
		 							<span class="etiqueta"><spring:message code="label.detalle.calle" />:</span>
		 							<span class="dato">${domicilioFiscal.vialidadPrimaria.nombre}</span>
		 							<span class="etiqueta"><spring:message code="label.detalle.numero.exterior" />:</span>
		 							<span class="dato">
		 								<c:if test="${domicilioFiscal.numExterior1 ne 0}">${domicilioFiscal.numExterior1}</c:if>
	 									<c:if test="${domicilioFiscal.numExteriorAlf ne 0}">&nbsp;${domicilioFiscal.numExteriorAlf}</c:if>
		 							</span>		 							
									<span class="etiqueta"><spring:message code="label.detalle.numero.interior" />:</span>
		 							<span class="dato">
		 								<c:if test="${domicilioFiscal.numInterior ne 0}">${domicilioFiscal.numInterior}</c:if>
	 									<c:if test="${domicilioFiscal.numInteriorAlf ne 0}">&nbsp;${domicilioFiscal.numInteriorAlf}</c:if>
		 							</span>
		 						</div>
 								<!-- *****************  Finaliza ********************** -->

 								<!-- *****************  Inicia Renglón  ********************** -->
		 						<div class="div-2secciones">
		 							<span class="etiqueta"><spring:message code="label.detalle.entre.calle" />:</span>
		 							<span class="dato">${domicilioFiscal.vialidadReferenciaPrimaria.nombre}</span>
		 						</div>
								<div class="div-2secciones">
		 							<span class="etiqueta"><spring:message code="label.detalle.y.calle" />:</span>
		 							<span class="dato">${domicilioFiscal.vialidadReferenciaSecundaria.nombre}</span>
		 						</div>
		 						<!-- *****************  Finaliza ********************** -->
		 	
		 						<!-- *****************  Inicia Renglón  ********************** -->
		 						<div class="div-2secciones">
		 							<span class="etiqueta"><spring:message code="label.detalle.colonia" />:</span>
		 							<span class="dato">${domicilioFiscal.asentamiento.nombre}</span>
		 						</div>
								<div class="div-2secciones">
		 							<span class="etiqueta"><spring:message code="label.detalle.localidad" />:</span>
		 							<span class="dato">${domicilioFiscal.asentamiento.localidad.nombre}</span>
		 						</div>
		 						<!-- *****************  Finaliza ********************** -->

								<!-- *****************  Inicia Renglón  ********************** -->
								<div class="div-2secciones">
		 							<span class="etiqueta"><spring:message code="label.detalle.municipio.delegacion" />:</span>
		 							<span class="dato">${domicilioFiscal.asentamiento.localidad.municipio.nombre}</span>
		 						</div>
								<div class="div-2secciones">
		 							<span class="etiqueta"><spring:message code="label.detalle.entidad.federativa" />:</span>
		 							<span class="dato">${domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre}</span>
		 						</div>
		 						<!-- *****************  Finaliza ********************** -->
		 	
		 						<!-- *****************  Inicia Renglón  ********************** -->
								<div class="div-2secciones">
		 							<span class="etiqueta"><spring:message code="label.detalle.codigo.postal" />:</span>
		 							<span class="dato">${domicilioFiscal.codigoPostal.codigoPostal}</span>
		 						</div>
		 						<!-- *****************  Finaliza ********************** -->

		 					</fieldset>
		 				</div>		

						<!-- *****************  Inicia Renglón  CENTRO DE TRABAJO ********************** -->
						<div class="div-1seccion">
		 					<fieldset>
		 						<legend style="text-align:center">
		 							<b><spring:message code="label.detalle.centro.trabajo" /></b>
		 						</legend>
		 						<!-- *****************  Inicia validación Centrro de Trabajo ********************** -->
		 						<c:if test="${centroTrabajo.vialidadPrimaria.nombre!=null}">
								<!-- *****************  Inicia Renglón  ********************** -->
		 						<div class="div-1seccion">
		 							<span class="etiqueta"><spring:message code="label.detalle.calle" />:</span>
		 							<span class="dato">${centroTrabajo.vialidadPrimaria.nombre}</span>
		 							<span class="etiqueta"><spring:message code="label.detalle.numero.exterior" />:</span>
	 								<span class="dato">
	 									<c:if test="${centroTrabajo.numExterior1 ne 0}">${centroTrabajo.numExterior1}</c:if>
	 									<c:if test="${centroTrabajo.numExteriorAlf ne 0}">&nbsp;${centroTrabajo.numExteriorAlf}</c:if>
	 								</span>
		 							
									<span class="etiqueta"><spring:message code="label.detalle.numero.interior" />:</span>
		 							<span class="dato">
	 									<c:if test="${centroTrabajo.numInterior ne 0}">${centroTrabajo.numInterior}</c:if>
	 									<c:if test="${centroTrabajo.numInteriorAlf ne 0}">&nbsp;${centroTrabajo.numInteriorAlf}</c:if>
		 							</span>
		 						</div>
 								<!-- *****************  Finaliza ********************** -->

 								<!-- *****************  Inicia Renglón  ********************** -->
		 						<div class="div-2secciones">
		 							<span class="etiqueta"><spring:message code="label.detalle.entre.calle" />:</span>
								 	<span class="dato">${centroTrabajo.vialidadReferenciaPrimaria.nombre}</span>
							 	</div>
								<div class="div-2secciones">
								 	<span class="etiqueta"><spring:message code="label.detalle.y.calle" />:</span>
								 	<span class="dato">${centroTrabajo.vialidadReferenciaSecundaria.nombre}</span>
							 	</div>
							 	<!-- *****************  Finaliza ********************** -->
		 	
							 	<!-- *****************  Inicia Renglón  ********************** -->
							 	<div class="div-2secciones">
								 	<span class="etiqueta"><spring:message code="label.detalle.colonia" />:</span>
								 	<span class="dato">${centroTrabajo.asentamiento.nombre}</span>
							 	</div>
								<div class="div-2secciones">
								 	<span class="etiqueta"><spring:message code="label.detalle.localidad" />:</span>
								 	<span class="dato">${centroTrabajo.asentamiento.localidad.nombre}</span>
							 	</div>
							 	<!-- *****************  Finaliza ********************** -->

								<!-- *****************  Inicia Renglón  ********************** -->
								<div class="div-2secciones">
								 	<span class="etiqueta"><spring:message code="label.detalle.municipio.delegacion" />:</span>
								 	<span class="dato">${centroTrabajo.asentamiento.localidad.municipio.nombre}</span>
							 	</div>
								<div class="div-2secciones">
								 	<span class="etiqueta"><spring:message code="label.detalle.entidad.federativa" />:</span>
								 	<span class="dato">${centroTrabajo.asentamiento.localidad.municipio.entidadFederativa.nombre}</span>
							 	</div>
							 	<!-- *****************  Finaliza ********************** -->

							 	<!-- *****************  Inicia Renglón  ********************** -->
								<div class="div-2secciones">
								 	<span class="etiqueta"><spring:message code="label.detalle.codigo.postal" />:</span>
								 	<span class="dato">${centroTrabajo.codigoPostal.codigoPostal}</span>
							 	</div>
							 	<!-- *****************  Finaliza ********************** -->
							 	</c:if>
							 	<c:if test="${centroTrabajo.vialidadPrimaria.nombre==null}">
							 		<div class="div-1seccion">
			 							<span class="etiqueta">Domicilio:</span>
			 							<span class="dato">${centroTrabajo.descripcion}</span>
			 						</div>
							 	</c:if>
							 	<!-- *****************  Finaliza validación Centrro de Trabajo ********************** -->
							 	
							 </fieldset>
		 				</div>		
		 				
		 				<div class="div-1seccion">
		 				<form>
		 					<fieldset>
		 						<legend style="text-align:center">
		 							Dictamen electr&oacute;nico
		 						</legend>
		 						<div class="div-1seccion">
		 					<button type="button" id="verDictamen" class="mboton">Ver detalle del dictamen</button>
		 					</div>
		 					</fieldset>
		 					</form>
		 				</div>
				</div>
		</div>
		
		<div id="divDictamen" style="display:none">
		</div>
		
	</div>
</div>