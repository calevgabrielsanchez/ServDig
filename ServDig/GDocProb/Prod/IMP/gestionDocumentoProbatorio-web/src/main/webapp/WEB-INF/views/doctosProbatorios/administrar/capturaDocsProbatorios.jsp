<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.enums.DocumentosEnum"%>

<script type="text/javascript">
	$.fn.clearCustomForm = function() {
		
		/* Si la condición se cumple, entonces el id existe
		 * y se trata de una modificación y no es necesario
		 * implementar la limpieza del formulario
		 */
		if(!$('#indexDocProbatorio').length){
			var comboEstado = $('#actaNacimiento\\.municipio\\.entidadFederativa\\.clave');
			comboEstado[0].selectedIndex = 0;
	
			cmbcapturarDoctoProbatorioFormdgcatmunicipio.cargardep();
			var comboMunicipio = $('#actaNacimiento\\.municipio\\.clave');
			comboMunicipio[0].selectedIndex = 0;
	
		    return this.each(function() {
		        $('input,select,textarea', this).clearCustomFields();
		    });
		}
	};

	$.fn.clearCustomFields = $.fn.clearInputs = function() {
	    return this.each(function() {
	        var t = this.type, tag = this.tagName.toLowerCase();
	        if (t == 'text' || t == 'password' || tag == 'textarea')
	            this.value = '';
	        else if (t == 'checkbox' || t == 'radio')
	            this.checked = false;
	    });
	};

	$(document).ready(function(){
		$('input.numerico').keypress(function(event) {
			var charAt = String.fromCharCode(event.which);
			var characterReg = /\d{1}/;
			if (!characterReg.test(charAt)) {
				return event.preventDefault();
			}
		});

		$('#tipoDocumentoProbatorio\\.idTipoDocumentoProbatorio').change(function(){
			$('#capturarDoctoProbatorioForm').clearCustomForm();
			fnHideErrores('#capturarDoctoProbatorioForm');
			ocultarFormulariosAlta();
			
			if($(this).val() == <%=DocumentosEnum.ACTA_NACIMIENTO.getId().intValue()%>){
				$('#actaNacimientoDiv').show();
				$('div#actaNacimientoDiv :input').attr('disabled',false);
				$('#control').show();
				$('#tipoDocumentoProbatorio\\.descripcion').val($('#tipoDocumentoProbatorio\\.idTipoDocumentoProbatorio option:selected').text());
			}else if($(this).val() == <%=DocumentosEnum.DOCUMENTO_MIGRATORIO.getId().intValue()%>){
				$('#docMigratorioDiv').show();
				$('div#docMigratorioDiv :input').attr('disabled',false);
				$('#control').show();
				$('#tipoDocumentoProbatorio\\.descripcion').val($('#tipoDocumentoProbatorio\\.idTipoDocumentoProbatorio option:selected').text());
			}else if($(this).val() == <%=DocumentosEnum.CARTA_NATURALIZACION.getId().intValue()%>){
				$('#cartaNaturalizacionDiv').show();
				$('div#cartaNaturalizacionDiv :input').attr('disabled',false);
				$('#control').show();
				$('#tipoDocumentoProbatorio\\.descripcion').val($('#tipoDocumentoProbatorio\\.idTipoDocumentoProbatorio option:selected').text());
			}else if($(this).val() == <%=DocumentosEnum.NUMERO_UNICO_DE_EXTRANJERO.getId()
					.intValue()%>){
				$('#numUnicoExtranjeroDiv').show();
				$('div#numUnicoExtranjeroDiv :input').attr('disabled',false);
				$('#control').show();
				$('#tipoDocumentoProbatorio\\.descripcion').val($('#tipoDocumentoProbatorio\\.idTipoDocumentoProbatorio option:selected').text());
			}else if($(this).val() == <%=DocumentosEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA
					.getId().intValue()%>){
				$('#certifNacMexicanaDiv').show();
				$('div#certifNacMexicanaDiv :input').attr('disabled',false);
				$('#control').show();
				$('#tipoDocumentoProbatorio\\.descripcion').val($('#tipoDocumentoProbatorio\\.idTipoDocumentoProbatorio option:selected').text());
			}else if($(this).val() == <%=DocumentosEnum.OFICIO_SOLICITANTE_DE_REFUGIADO.getId()
					.intValue()%>){
				$('#oficioRefugiadoDiv').show();
				$('div#oficioRefugiadoDiv :input').attr('disabled',false);
				$('#control').show();
				$('#tipoDocumentoProbatorio\\.descripcion').val($('#tipoDocumentoProbatorio\\.idTipoDocumentoProbatorio option:selected').text());
			}else if($(this).val() == <%=DocumentosEnum.FORMA_MIGRATORIA_TURISTA.getId()
					.intValue()%>){
				$('#formaMigratoriaDiv').show();
				$('div#formaMigratoriaDiv :input').attr('disabled',false);
				$('#control').show();
				$('#tipoDocumentoProbatorio\\.descripcion').val($('#tipoDocumentoProbatorio\\.idTipoDocumentoProbatorio option:selected').text());
			}
			
			setSizeWithinIframe(document, 500);
			
		});
		
		$('#btnGuardarDocProbatorio').click(function(){
			fnGuardarModificarDocProbatorio();
		});
		
		if($('#indexDocProbatorio').val() >= 0){
			$('#tipoDocumentoProbatorio\\.idTipoDocumentoProbatorio').prop("disabled", true);
			$('#tipoDocumentoProbatorio\\.idTipoDocumentoProbatorio').trigger('change');
		}
	});
	
	// Funcio
	function ocultarFormulariosAlta(){
		$('#actaNacimientoDiv').hide();
		$('div#actaNacimientoDiv :input').attr('disabled',true);
		$('#docMigratorioDiv').hide();
		$('div#docMigratorioDiv :input').attr('disabled',true);
		$('#cartaNaturalizacionDiv').hide();
		$('div#cartaNaturalizacionDiv :input').attr('disabled',true);
		$('#numUnicoExtranjeroDiv').hide();
		$('div#numUnicoExtranjeroDiv :input').attr('disabled',true);
		$('#certifNacMexicanaDiv').hide();
		$('div#certifNacMexicanaDiv :input').attr('disabled',true);
		$('#oficioRefugiadoDiv').hide();
		$('div#oficioRefugiadoDiv :input').attr('disabled',true);
		$('#formaMigratoriaDiv').hide();
		$('div#formaMigratoriaDiv :input').attr('disabled',true);
		
		$('#control').hide();
	}
	
	var fnGuardarModificarDocProbatorio = function (){
		var url;
		
		if($('#indexDocProbatorio').val() >= 0){
			// Significa que se está editando
			url = "/gestionDocumentoProbatorio-web/documentos/probatorios/administrar/confirmar-modificacion/" + $('#indexDocProbatorio').val();
			$('#tipoDocumentoProbatorio\\.idTipoDocumentoProbatorio').prop("disabled", false);
		}else {
			url = $("form#capturarDoctoProbatorioForm").attr('action');
		}
		
		fnHideErrores("form#capturarDoctoProbatorioForm");
		
		$('#actaNacimiento\\.municipio\\.entidadFederativa\\.nombre').val($('#actaNacimiento\\.municipio\\.entidadFederativa\\.clave option:selected').text());
		
		var oForm = $("form#capturarDoctoProbatorioForm").toObject();
		
		$.postJSON(url, oForm, function(data) {
			parent.objCtrlDocsProbatorios.docsProbatorios = data;
			parent.objCtrlDocsProbatorios.cerrar();
		}).error(function(data){
			fnProcesarErrores(data, "form#capturarDoctoProbatorioForm");
		});
	};
</script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<div class="col-sm-12">
	<form:form action="${contextpath}/documentos/probatorios/administrar/agregar" method="POST"
		modelAttribute="documentoProbatorioFormWrapper" id="capturarDoctoProbatorioForm">

		<c:if test="${not empty indexDocProbatorio}">
			<input type="hidden" id="indexDocProbatorio" value="${indexDocProbatorio}" />
			<form:hidden path="actaNacimiento.idDocumentoProbatorio" />
			<form:hidden path="docProbRENAPO.idDocumentoProbatorio" />
		</c:if>

		<div class="form-group">
			<form:label path="tipoDocumentoProbatorio.idTipoDocumentoProbatorio">
				<span class="required m-r-xs">*</span>Tipo de Documento</form:label>
			<form:select path="tipoDocumentoProbatorio.idTipoDocumentoProbatorio"
				id="tipoDocumentoProbatorio.idTipoDocumentoProbatorio"
				cssClass="form-control">
				<form:option value="-1">--Por favor seleccione--</form:option>
				<form:option value="<%=DocumentosEnum.ACTA_NACIMIENTO.getId().intValue()%>">
					<%=DocumentosEnum.ACTA_NACIMIENTO.getDescripcion()%>
				</form:option>
				<form:option value="<%=DocumentosEnum.DOCUMENTO_MIGRATORIO.getId().intValue()%>">
					<%=DocumentosEnum.DOCUMENTO_MIGRATORIO.getDescripcion()%>
				</form:option>
				<form:option value="<%=DocumentosEnum.CARTA_NATURALIZACION.getId().intValue()%>">
					<%=DocumentosEnum.CARTA_NATURALIZACION.getDescripcion()%>
				</form:option>
				<form:option value="<%=DocumentosEnum.NUMERO_UNICO_DE_EXTRANJERO.getId().intValue()%>">
					<%=DocumentosEnum.NUMERO_UNICO_DE_EXTRANJERO.getDescripcion()%>
				</form:option>
				<form:option value="<%=DocumentosEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA.getId().intValue()%>">
					<%=DocumentosEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA.getDescripcion()%>
				</form:option>
				<form:option value="<%=DocumentosEnum.OFICIO_SOLICITANTE_DE_REFUGIADO.getId().intValue()%>">
					<%=DocumentosEnum.OFICIO_SOLICITANTE_DE_REFUGIADO.getDescripcion()%>
				</form:option>
				<form:option value="<%=DocumentosEnum.FORMA_MIGRATORIA_TURISTA.getId().intValue()%>">
					<%=DocumentosEnum.FORMA_MIGRATORIA_TURISTA.getDescripcion()%>
				</form:option>
			</form:select>
			<form:hidden path="tipoDocumentoProbatorio.descripcion" id="tipoDocumentoProbatorio.descripcion" />
		</div>

		<span id="errorNegocioLabel" class="error hiddenElement"></span>

		<div id="actaNacimientoDiv" style="display: none;">
			<fieldset>
				<legend>
					Acta de Nacimiento
					<span></span>
				</legend>

				<div class="form-group">
					<form:label path="actaNacimiento.anio">
						<span class="required m-r-xs">*</span>A&ntilde;o de Registro
					</form:label>
					<form:input path="actaNacimiento.anio" id="anio" maxlength="4" cssClass="numerico form-control" />
					<span id="actaNacimiento.anioError" class="error hiddenElement"></span>
				</div>

				<div class="form-group">
					<form:label path="actaNacimiento.tomo">Tomo</form:label>
					<form:input path="actaNacimiento.tomo" id="tomo" maxlength="3" cssClass="form-control"/>
					<span id="actaNacimiento.tomoError" class="error hiddenElement"></span>
				</div>

				<div class="form-group">
					<form:label path="actaNacimiento.crip">CRIP</form:label>
					<form:input path="actaNacimiento.crip" id="crip" cssClass="form-control" maxlength="15" />
					<span id="actaNacimiento.cripError" class="error hiddenElement"></span>
				</div>

				<div class="form-group">
					<form:label path="actaNacimiento.noFoja">Foja</form:label>
					<form:input path="actaNacimiento.noFoja" id="foja" cssClass="form-control" maxlength="5" />
					<span id="actaNacimiento.noFojaError" class="error hiddenElement"></span>
				</div>

				<div class="form-group">
					<form:label path="actaNacimiento.noLibro">
						<span class="required m-r-xs">*</span>Libro
					</form:label>
					<form:input path="actaNacimiento.noLibro" id="libro" cssClass="form-control" maxlength="4" />
					<span id="actaNacimiento.noLibroError" class="error hiddenElement"></span>
				</div>

				<div class="form-group">
					<form:label path="actaNacimiento.noActa">N&uacute;mero de Acta</form:label>
					<form:input path="actaNacimiento.noActa" id="numeroActa" cssClass="form-control" maxlength="5" />
					<span id="actaNacimiento.noActaError" class="error hiddenElement"></span>
				</div>

				<div class="form-group">
					<!-- Combo papa -->
					<form:label path="actaNacimiento.municipio.entidadFederativa.clave">
						<span class="required m-r-xs">*</span>Entidad de Registro
					</form:label>
					<combo:creaCombo idHtml="actaNacimiento.municipio.entidadFederativa.clave"
						idHtmlContenedor="capturarDoctoProbatorioForm" 
						entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado"
						idHtmlValor="${documentoProbatorioFormWrapper.actaNacimiento.municipio.entidadFederativa.clave}"
						mostrarSoloActivos="false" 
						cssClassname="form-control"/>
					<span id="actaNacimiento.municipio.entidadFederativa.claveError" class="error hiddenElement"></span>
					<form:hidden path="actaNacimiento.municipio.entidadFederativa.nombre"
						id="actaNacimiento.municipio.entidadFederativa.nombre" />
				</div>

				<div class="form-group">
					<!-- Combo hijo -->
					<form:label path="actaNacimiento.municipio.clave">
						<span class="required m-r-xs">*</span>Municipio de Registro
					</form:label>
					<combo:creaCombo entidad="mx.gob.imss.ctirss.delta.persistence.DgCatMunicipio"
						idHtml="actaNacimiento.municipio.clave" 
						entidadPadre="dgCatEstado.cveEnt"
						idHtmlPadre="actaNacimiento.municipio.entidadFederativa.clave" 
						idHtmlContenedor="capturarDoctoProbatorioForm"
						idHtmlValor="${documentoProbatorioFormWrapper.actaNacimiento.municipio.clave}" 
						mostrarSoloActivos="false"
						cssClassname="form-control" />
					<span id="actaNacimiento.municipio.claveError" class="error hiddenElement"></span>
				</div>
			</fieldset>
		</div>


		<div id="docMigratorioDiv" style="display: none;">
			<fieldset>
				<legend>Documento Migratorio</legend>
				<div class="form-group">
					<form:label path="docProbRENAPO.numFolioExtranjero">
						<span class="required m-r-xs">*</span>N&uacute;mero del Registro Nacional de Extranjeros
					</form:label>
					<form:input type="text" path="docProbRENAPO.numFolioExtranjero" maxlength="30" cssClass="form-control" />
					<span id="docProbRENAPO.numFolioExtranjeroError" class="error hiddenElement"></span>
				</div>
				<div class="form-group">
					<form:label path="docProbRENAPO.noActa">
						<span class="required m-r-xs">*</span>N&uacute;mero de Expediente del Documento Migratorios
					</form:label>
					<form:input type="text" path="docProbRENAPO.noActa" maxlength="30" cssClass="form-control"/>
					<span id="docProbRENAPO.noActaError" class="error hiddenElement"></span>
				</div>
			</fieldset>
		</div>

		<div id="cartaNaturalizacionDiv" style="display: none;">
			<fieldset>
				<legend>Carta de Naturalizaci&oacute;n</legend>

				<div class="form-group">
					<form:label path="docProbRENAPO.anioRegistro">
						<span class="required m-r-xs">*</span>A&ntilde;o de Registro
					</form:label>
					<form:input type="text" path="docProbRENAPO.anioRegistro" maxlength="30" cssClass="numerico form-control" />
					<span id="docProbRENAPO.anioRegistroError" class="error hiddenElement"></span>
				</div>
				<div class="form-group">
					<form:label path="docProbRENAPO.numFolioExtranjero">
						<span class="required m-r-xs">*</span>Folio de la Carta
					</form:label>
					<form:input type="text" path="docProbRENAPO.numFolioExtranjero" maxlength="30" cssClass="form-control" />
					<span id="docProbRENAPO.numFolioExtranjeroError" class="error hiddenElement"></span>
				</div>
			</fieldset>
		</div>

		<div id="numUnicoExtranjeroDiv" style="display: none;">
			<fieldset>
				<legend>N&uacute;mero &Uacute;nico de Extranjero</legend>
				
				<div class="form-group">
					<form:label path="docProbRENAPO.numFolioExtranjero">
						<span class="required m-r-xs">*</span>N&uacute;mero de folio
					</form:label>
					<form:input type="text" path="docProbRENAPO.numFolioExtranjero" maxlength="30" cssClass="form-control" />
					<span id="docProbRENAPO.numFolioExtranjeroError" class="error hiddenElement"></span>
				</div>
			</fieldset>
		</div>

		<div id="certifNacMexicanaDiv" style="display: none;">
			<fieldset>
				<legend>Certificado de Nacionalidad Mexicana</legend>

				<div class="form-group">
					<form:label path="docProbRENAPO.anioRegistro">
						<span class="required m-r-xs">*</span>A&ntilde;o de Registro
					</form:label>
					<form:input type="text" path="docProbRENAPO.anioRegistro" maxlength="30" cssClass="numerico form-control" />
					<span id="docProbRENAPO.anioRegistroError" class="error hiddenElement"></span>
				</div>

				<div class="form-group">
					<form:label path="docProbRENAPO.numFolioExtranjero">
						<span class="required m-r-xs">*</span>Folio de la Carta
					</form:label>
					<form:input type="text" path="docProbRENAPO.numFolioExtranjero" maxlength="30" cssClass="form-control" />
					<span id="docProbRENAPO.numFolioExtranjeroError" class="error hiddenElement"></span>
				</div>
			</fieldset>
		</div>

		<div id="oficioRefugiadoDiv" style="display: none;">
			<fieldset>
				<legend>Oficio Solicitante de Refugiado</legend>

				<div class="form-group">
					<form:label path="docProbRENAPO.numFolioExtranjero">
						<span class="required m-r-xs">*</span>N&uacute;mero del Folio
					</form:label>
					<form:input type="text" path="docProbRENAPO.numFolioExtranjero" maxlength="30" cssClass="form-control" />
					<span id="docProbRENAPO.numFolioExtranjeroError" class="error hiddenElement"></span>
				</div>
			</fieldset>
		</div>

		<div id="formaMigratoriaDiv" style="display: none;">
			<fieldset>
				<legend>Forma Migratoria Turista</legend>

				<div class="form-group">
					<form:label path="docProbRENAPO.numFolioExtranjero">
						<span class="required m-r-xs">*</span>N&uacute;mero del Folio
					</form:label>
					<form:input type="text" path="docProbRENAPO.numFolioExtranjero" maxlength="30" cssClass="form-control"/>
					<span id="docProbRENAPO.numFolioExtranjeroError" class="error hiddenElement"></span>
				</div>
			</fieldset>
		</div>

		<div id="control" style="display: none;" class="form-group text-right">
			<input type="button" value="Aceptar" class="btn btn-primary" id="btnGuardarDocProbatorio" />
		</div>
	</form:form>
</div>