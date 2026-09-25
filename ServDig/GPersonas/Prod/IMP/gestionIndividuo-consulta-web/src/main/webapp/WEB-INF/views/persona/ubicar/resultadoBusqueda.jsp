<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>

<style>
	div.dataTables_wrapper {
	    display: inline-block;
	    margin-top: 8px;
	    width: 100%;
	}
</style>

<script>
	$(function(){
		$('button#continuar').click(function(e){
					var valor = $('input#curp').val();
	    			//SUBMIT DE REGISTRO MANUAL
	    			$("input").each(function() {
	    			   var elemento = $(this);
	    			   $(this).val(elemento.val().toUpperCase());
	    			});
	    			$sexo = $("[id='sexo.idSexo']");
					$lugarNacimiento = $("[id='lugarNacimiento.clave']");
					$sexo.removeAttr('disabled');
					$lugarNacimiento.removeAttr('disabled');
	    			window.parent.$('div#ubicarContainer').css('margin-bottom','');
	    			var _container = $('div#busquedaFormContainerPM');
	    			 $.ajax({
						    type: "POST",
						    url: context_path+'/ubicar/persona/buscar/fisica/'+valor,
						    beforeSend: function() {
						    	_container.html('<div class="loading" style="text-align: center; margin-top: 110px;"><img class="loading"></div>');
			                 },
						    success: function(data) {
							    	 folio = data.split(";")[1];
						    	     resultado=data.split(";")[0];
						    	     mostrarFolioDeRegistroPorDatosBasicos(folio,resultado);
						    }
					      });
		});
		
		$('button#registroManual').click(function(e){
			$sexo = $("[id='sexo.idSexo']");
			$lugarNacimiento = $("[id='lugarNacimiento.clave']");
			$sexo.removeAttr('disabled');
			$lugarNacimiento.removeAttr('disabled');
	        var _container = $('div#busquedaFormContainerPM');
	        $.ajax({
	              type: 'POST',
	              url: context_path+'/ubicar/persona/agregar',
	              data: $('form#altaPersonaFisica').serialize(),
	              beforeSend: function() {
				    	_container.html('<div class="loading" style="text-align: center; margin-top: 140px;"><img class="loading"></div>');
	                 },
	              success: function (data) {
	            	  folio = data.split(";")[1];
			    	  resultado=data.split(";")[0];
			    	  mostrarFolioDeRegistroPorDatosBasicos(folio,resultado);
	              }
	        });
		});
		
		$('button#confirmarSat').click(function(e){
			  var valor = $('input#radioPersona').filter(":checked").val();
				  if(valor == undefined){
					  //alert ("Primero selecciona un opción");
					  muestraMsgError();
					  return false;
				  }else{
					  valor=valor.split("_")[0];
					  var _containerSat = $('div#busquedaFormContainerPM');
					  $.ajax({
						    type: "POST",
						    url : context_path+'/ubicar/persona/buscar/moral/'+valor,
						    beforeSend: function() {
						    	_containerSat.html('<div class="loading" style="text-align: center; margin-top: 160px;"><img class="loading"></div>');
			                 },
						    success: function(data) {
						    	     folio = data.split(";")[1];
						    	     resultado=data.split(";")[0];
						    	     //$('input#radioPersona').filter(":checked").val(resultado);
						    	     mostrarFolioDeRegistroPorDatosBasicos(folio,resultado);
						    }
					      });
				  }
		});
		
		
		$('button#confirmarSATFisica').click(function(e){
			var valor = $('input#radioPersona').filter(":checked").val();
			  if(valor == undefined){
				  //alert ("Primero selecciona un opción");
				  muestraMsgError();
				  return false;
			  }else{
				  $('button#confirmarSATFisica').attr("disabled", true);
				  var _containerSATFisica = $('div#busquedaFormContainerPM');
				  $.ajax({
					    type: "POST",
					    url: context_path+'/ubicar/persona/buscar/fisica/'+valor,
					    beforeSend: function() {
					    	_containerSATFisica.html('<div class="loading" style="text-align: center; margin-top: 160px;"><img class="loading"></div>');
		                 },
					    success: function(data) {
						    	 folio = data.split(";")[1];
					    	     resultado=data.split(";")[0];
					    	     //$('input#radioPersona').filter(":checked").val(resultado);
					    	     mostrarFolioDeRegistroPorDatosBasicos(folio,resultado);
					    }
				      });
			  }
		});
		
		$('button#confirmarRenapo').click(function(e){
			var valor = $('input#radioPersona').filter(":checked").val();
			  if(valor == undefined){
				  //alert ("Primero selecciona un opción");
				  muestraMsgError();
				  return false;
			  }else{
				  $('button#confirmarRenapo').attr("disabled", true);
				  var _containerRenapo = $('div#busquedaFormContainerPM');
				  $.ajax({
					    type: "POST",
					    url: context_path+'/ubicar/persona/buscar/fisica/'+valor,
					    beforeSend: function() {
					    	_containerRenapo.html('<div class="loading" style="text-align: center; margin-top: 160px;"><img class="loading"></div>');
		                 },
					    success: function(data) {
						    	 folio = data.split(";")[1];
					    	     resultado=data.split(";")[0];
					    	     //$('input#radioPersona').filter(":checked").val(resultado);
					    	     mostrarFolioDeRegistroPorDatosBasicos(folio,resultado);
					    }
				      });
			  }
		});
		
		if ($('#resultadoDatosFisica').length > 0 || $('#resultadoDatosMoral').length > 0){
			
			var _container = null;
			var _nonVisibleTargets = null;
			
			if ($('#resultadoDatosFisica').length > 0) {
				_container = $('#resultadoDatosFisica');
				_nonVisibleTargets = [1, 11];
			} else {
				_container = $('#resultadoDatosMoral');
				_nonVisibleTargets = [1];
			}
						
			var oTable = _container.dataTable({
				"sPaginationType" : "bootstrap-full",
				"oLanguage": {
					"sZeroRecords": "<center><strong style=\"font-size: small;\">Sin información que mostrar</strong></center>"
				},
				"bLengthChange" : false,
				"aoColumnDefs": [{"bSortable": false, "aTargets": [0]}, 
					{"bVisible": false, "aTargets": _nonVisibleTargets}],
				"fnDrawCallback": function( oSettings ) {
					$('div.dataTables_wrapper').css('overflow','auto');
				}
			});		
		}
				
		var host = window.location.href;
		$('div#pie').hide();
		$('button#nuevaBusqueda').click(function(e){
			var url = host.split("/ubicar/")[0]+"/ubicar/persona/iniciar";
			window.open(url,"_self");
			return false;
		});	
		    
		$('button#iniciarTramite').click(function(e){
			  obtenerDatos(null);
			});

		function obtenerDatos(folio){
			var valor = $('input#radioPersona').filter(":checked").val();
			  if(valor == undefined){
				  //alert ("Primero selecciona un opción");
				  muestraMsgError();
				  return false;
			  }
			  $('button#iniciarTramite').attr("disabled", true);
			  $.ajax({
				    type: "POST",
				    url	: context_path+'/ubicar/persona/buscar/'+valor,
				    beforeSend : function() {
				    	$.blockUI();
				    },
				    success: function(data) {
			    		$.unblockUI();
			    		if(folio !=="" && folio !== null){
							 parent.dialogoBuscar.persona({folioRecibido : folio});
			    			 parent.dialogoBuscar.persona({ubicarPersona : data});
			    			 parent.dialogoBuscar.persona('mostrar');
			    			
			    		}else{
			    			 $('button#iniciarTramite').attr("disabled", false);
				    	     parent.dialogoBuscar.persona({ubicarPersona : data});
							 parent.dialogoBuscar.persona('cerrar');
			    		} 
			    	},
			    	error : function() {
			    		$.unblockUI();
			    	}
		      }); 
		}
		
		function mostrarFolioDeRegistroPorDatosBasicos(folio,resultado){
			  $.ajax({
				    type: "POST",
				    url	: context_path+'/ubicar/persona/buscar/'+resultado,
				    success: function(data) {
				    		if(folio !=="" && folio !== null){
								 parent.dialogoBuscar.persona({folioRecibido : folio});
				    			 parent.dialogoBuscar.persona({ubicarPersona : data});
				    			 parent.dialogoBuscar.persona('mostrar');
				    			
				    		}else{
				    			 $('button#iniciarTramite').attr("disabled", false);
					    	     parent.dialogoBuscar.persona({ubicarPersona : data});
								 parent.dialogoBuscar.persona('cerrar');
				    		} 
				    	}
			      }); 
		}
		
		function muestraMsgError(){
			$('#dialogoMsgSeleccion').html('<div class="ui-dialog-content ui-widget-content">'
			+'<span style="float: left; margin: 0 7px 20px 0;" class="ui-icon ui-icon-alert"></span>'
			+'<span style="color: black;">Para continuar con el trámite, selecciona una opción.</span></div>');
			  $('#dialogoMsgSeleccion').dialog({
				  title : 'Mensaje',
				  dialogClass: "no-close",
			      modal: true,
			      resizable : false,
			      buttons: {
			        Aceptar: function() {
			          $( this ).dialog( "close" );
			          $('#dialogoMsgSeleccion').html('');
			        }
			      }
			});
		}
		
		$('button#regresarAtrasPersonaMoralImss').click(function(e){
			parent.dialogoBuscar.persona('backPersonaMoral');
		});
		$('button#regresarAtrasPersonaMoralSAT').click(function(e){
			parent.dialogoBuscar.persona('backPersonaMoral');
		});
		$('button#regresarPersonaMoralNoencontrada').click(function(e){
			parent.dialogoBuscar.persona('backPersonaMoral');
		});
		$('button#cerrarPersonaMoralNoencontrada').click(function(e){
			parent.dialogoBuscar.persona('cerrarPersonaNoEncontrada');
		});
		
		$('button#atrasPersonaFisicaRegistro').click(function(e){
			parent.dialogoBuscar.persona('backPersonaFisica');
		});
		
		$('button#atrasPersonaFisicaNoEncontrada').click(function(e){
			parent.dialogoBuscar.persona('backPersonaFisica');
		});
		
		$('button#cerrarPersonaFisicaNoEncontrada').click(function(e){
			parent.dialogoBuscar.persona('cerrarPersonaNoEncontrada');
		});
		
		$('button#atrasPersonaFisicaEncontradaIMSS').click(function(e){
			parent.dialogoBuscar.persona('backPersonaFisica');
		});
		$contain = $('div#busquedaFormContainerPM').find('div.dataTables_filter > label > input');
		$contain.focus();
		if($contain.html()==null){		
			$('#busquedaFormContainerPM button').each(function() {
				   if($(this).html()!=null){
					   $(this).focus();
					   return false;
				    }
				});
		}
});
</script>

<div class="container-fluid" id="busquedaFormContainerPM">
	<c:choose>
		<c:when test="${encontradoMoral == 'SAT'}">
				<div class="alert alert-info"><strong>Paso 2: </strong>Resultado de la b&uacute;squeda. La persona no fue localizada en el IMSS, fue localizada en el SAT.
				Confirme si los datos de la persona son correctos para su registro.</div>	
		</c:when>
		<c:otherwise>
	
			<c:if test="${encontradoMoral == 'IMSS'}">
				<div class="alert alert-info"><strong>Paso 2: </strong>Resultado de la b&uacute;squeda, la persona fue localizada en el IMSS y se encontraron
					<c:out value="${totalPersonas}"></c:out>
					registro(s) que coinciden con la informaci&oacute;n proporcionada.</div>	
			</c:if>
	
			<c:if test="${encontradoMoral == ''}">
				<div class="col-sm-12" style="">
			    	<div class="empty-state" style="background-color: #F5F5F5;margin-top: 25px">
						<div class="imagen" style="margin-bottom:1em;"><i class="glyphicon glyphicon-remove-sign"></i></div>
						<div class="alert alert-danger" style="margin-right: 95px;margin-left: 95px;text-align: center;">
						El dato proporcionado no fue localizado en IMSS y en SAT: <strong><c:out value="${rfc}"></c:out></strong>
						</div>
					</div>
				</div>		
			</c:if>
	
			<c:if test="${encontradoFisica == 'IMSS'}">
				<div class="alert alert-info"><strong>Paso 2: </strong>Resultado de la b&uacute;squeda, la persona fue localizada en el IMSS y se encontraron
					<c:out value="${totalPersonas}"></c:out>
					registro(s) que coinciden con la informaci&oacute;n proporcionada.</div>	
			</c:if>
	
			<c:if test="${encontradoFisica == 'REGISTRO'}">
				<div class="alert alert-info"><strong>Paso 2: </strong>Los datos no fueron localizados en IMSS, se encontraron en RENAPO, para dar de alta a la persona presione registrar.</div>	
			</c:if>
			
			<c:if test="${encontradoFisica == 'REGISTRO_MANUAL'}">
				<div class="alert alert-info"><strong>Paso 2: </strong>Los datos no fueron localizados en IMSS, ni en RENAPO, para dar de alta a la persona manualmente presione registrar.</div>	
			</c:if>
	
	
			<c:if test="${encontradoFisica == ''}">
				<div class="col-sm-12" style="">
			    	<div class="empty-state" style="background-color: #F5F5F5;margin-top: 25px">
						<div class="imagen" style="margin-bottom:1em;"><i class="glyphicon glyphicon-remove-sign"></i></div>
						<div class="alert alert-danger" style="margin-right: 95px;margin-left: 95px;text-align: center;">
						<c:if test="${curp == ''}">
							Los datos proporcionados no fueron localizados en IMSS y RENAPO.
						</c:if>
						<c:if test="${curp != ''}">
							La curp proporcionada no fue localizada en IMSS y en RENAPO: <strong><c:out value="${curp}"></c:out></strong>
						</c:if>
						</div>
					</div>
				</div>		
			</c:if>
	
			<c:if test="${encontradoFisica == 'RFC_PF_NO_ENCONTRADO'}">
				<div class="col-sm-12" style="">
			    	<div class="empty-state" style="background-color: #F5F5F5;margin-top: 25px">
						<div class="imagen" style="margin-bottom:1em;"><i class="glyphicon glyphicon-remove-sign"></i></div>
						<div class="alert alert-danger" style="margin-right: 95px;margin-left: 95px;text-align: center;">
						El dato proporcionado no fue localizado en IMSS y en SAT: <strong><c:out value="${curp}"></c:out></strong>
						</div>
					</div>
				</div>		
			</c:if>
			
			<c:if test="${encontradoFisica == 'RFC_PF_RENAPO_NO_ENCONTRADO'}">
				<div class="col-sm-12" style="">
			    	<div class="empty-state" style="background-color: #F5F5F5;margin-top: 25px">
						<div class="imagen" style="margin-bottom:1em;"><i class="glyphicon glyphicon-remove-sign"></i></div>
						<div class="alert alert-danger" style="margin-right: 95px;margin-left: 95px;text-align: center;">
						El dato proporcionado no fue localizado en IMSS y en RENAPO: <strong><c:out value="${curp}"></c:out></strong>
						</div>
					</div>
				</div>		
			</c:if>
			
			<c:if test="${encontradoFisica == 'RENAPO'}">
					<div class="alert alert-info"><strong>Paso 2: </strong>Resultado de la b&uacute;squeda. La persona no fue localizada en el IMSS, fue localizada en el
					RENAPO. Confirme si los datos de la persona son correctos para su registro.</div>	
			</c:if>
			
			<c:if test="${encontradoFisica == 'SAT'}">
					<div class="alert alert-info"><strong>Paso 2: </strong>Resultado de la b&uacute;squeda. La persona no fue localizada en el IMSS, fue localizada en el
					SAT. Confirme si los datos de la persona son correctos para su registro.</div>	
			</c:if>
	
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="${vista == 'personaFisica'}">
			<%@ include file="./personaFisica.jsp"%>
		</c:when>
		<c:otherwise>
			<c:if test="${vista == 'altaPersonaFisica'}">
				<%@ include file="./altaPersonaFisica.jsp"%>
			</c:if>
			<c:if test="${vista == 'personaMoral'}">
				<%@ include file="./personaMoral.jsp"%>
			</c:if>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${encontradoMoral == 'SAT'}">
			<div style="text-align: right; float: right; margin-right: 15px; margin-top: 15px; margin-bottom: 5px">
				<button
							type="button"
							id="regresarAtrasPersonaMoralSAT"
							class="btn btn-default"
							style="margin-right: 10px;">REGRESAR</button>
				<button
					type="button"
					id="confirmarSat"
					class="btn btn-primary">CONFIRMAR</button>
			</div>
		</c:when>
		<c:otherwise>

			<c:if test="${encontradoMoral == 'IMSS'}">
				<div style="text-align: right; float: right; margin-right: 15px; margin-top: 15px; margin-bottom: 5px">

					<c:if test="${totalPersonas > 0}">
						<button
							type="button"
							id="regresarAtrasPersonaMoralImss"
							class="btn btn-default"
							style="margin-right: 10px;">REGRESAR</button>
						<button
							type="button"
							id="iniciarTramite"
							class="btn btn-primary"
							style="margin-right: -15px;">INICIAR TR&Aacute;MITE</button>
					</c:if>
				</div>
			</c:if>
			
			
			<c:if test="${encontradoMoral == ''}">
				<div style="text-align: right; float: right; margin-right: 30px; margin-top: 15px; margin-bottom: 5px">
						<button
							type="button"
							id="regresarPersonaMoralNoencontrada"
							class="btn btn-default"
							style="margin-right: 10px;">REGRESAR</button>
						<button
							type="button"
							id="cerrarPersonaMoralNoencontrada"
							class="btn btn-default"
							style="margin-right: -15px;">CERRAR</button>
				</div>
			</c:if>
			
			
			<c:if test="${ encontradoFisica== ''}">
				<div style="text-align: right; float: right; margin-right: 30px; margin-top: 15px; margin-bottom: 5px">
						<%--<button
							type="button"
							id="atrasPersonaFisicaNoEncontrada"
							class="btn btn-default"
							style="margin-right: 10px;">REGRESAR</button>--%>
						<button
							type="button"
							id="cerrarPersonaFisicaNoEncontrada"
							class="btn btn-default"
							style="margin-right: -15px;">CERRAR</button>
				</div>
			</c:if>
			
			
			<c:if test="${encontradoFisica == 'RFC_PF_NO_ENCONTRADO'}">
				<div style="text-align: right; float: right; margin-right: 30px; margin-top: 15px; margin-bottom: 5px">
						<%--<button
							type="button"
							id="atrasPersonaFisicaNoEncontrada"
							class="btn btn-default"
							style="margin-right: 10px;">REGRESAR</button>--%>
						<button
							type="button"
							id="cerrarPersonaFisicaNoEncontrada"
							class="btn btn-default"
							style="margin-right: -15px;">CERRAR</button>
				</div>
			</c:if>
			
			<c:if test="${encontradoFisica == 'RFC_PF_RENAPO_NO_ENCONTRADO'}">
				<div style="text-align: right; float: right; margin-right: 30px; margin-top: 15px; margin-bottom: 5px">
						<%--<button
							type="button"
							id="atrasPersonaFisicaNoEncontrada"
							class="btn btn-default"
							style="margin-right: 10px;">REGRESAR</button>--%>
						<button
							type="button"
							id="cerrarPersonaFisicaNoEncontrada"
							class="btn btn-default"
							style="margin-right: -15px;">CERRAR</button>
				</div>
			</c:if>

			<c:if test="${encontradoFisica == 'IMSS'}">
				<div style="text-align: right; float: right; margin-right: 15px; margin-top: 15px; margin-bottom: 5px">
					<c:if test="${totalPersonas > 0}">
						<%--<button
							type="button"
							id="atrasPersonaFisicaEncontradaIMSS"
							class="btn btn-default"
							style="margin-right: 10px;">REGRESAR</button>--%>
						<button
							type="button"
							id="iniciarTramite"
							class="btn btn-primary"
							style="margin-right: -15px;">INICIAR TR&Aacute;MITE</button>
					</c:if>
				</div>
			</c:if>

			<c:if test="${encontradoFisica == 'RENAPO'}">
				<div style="text-align: right; float: right; margin-top: 15px; margin-bottom: 5px">
					<%--<button
							type="button"
							id="atrasPersonaFisicaEncontradaIMSS"
							class="btn btn-default"
							style="margin-right: 10px;">REGRESAR</button>--%>
					<button
						type="button"
						id="confirmarRenapo"
						class="btn btn-primary">CONFIRMAR</button>
				</div>
			</c:if>
			
			<c:if test="${encontradoFisica == 'SAT'}">
				<div style="text-align: right; float: right; margin-top: 15px; margin-bottom: 5px">
					<%--<button
							type="button"
							id="atrasPersonaFisicaEncontradaIMSS"
							class="btn btn-default"
							style="margin-right: 10px;">REGRESAR</button>--%>
					<button
						type="button"
						id="confirmarSATFisica"
						class="btn btn-primary">CONFIRMAR</button>
				</div>
			</c:if>
			
		</c:otherwise>
	</c:choose>
	<div id="dialogoMsgSeleccion"></div>
</div>