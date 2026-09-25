function cancelar(){
	$("#razonRechazo").dialog({
		  resizable: false,
		  modal: true,
		  width: 480,				  
	      buttons : {
	        "Aceptar" : function() {	
	        	$("#resultado\\.doble").val("1");
	        	$("#resultado\\.idRazonResultado").val($("#idRazonResultado").val());	        	
	        	$("#frmConfirmacion").submit();
	        },
	        "Regresar" : function() {
	        	$(this).dialog("close");
	        }	
	      }
	  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
}	

function termina(){
	var mensaje = "Existe Concubina(rio) registrado. Es necesario realizar la baja de este integrante para registrar Esposo(a).";
	if($.trim($("div#motivo").text().toLowerCase()) != $.trim(mensaje.toLowerCase())) {	
	//Usuario Persona - Derechohabiente 	
		$("#frmConfirmacion").attr("action","/${mvn.web.app.root}/derechohabientes/registro/cita");			          
		$("#frmConfirmacion").submit();
	}
	
}
//victor camacho
function regresa(){
	$("#frmConfirmacion").attr("action","/${mvn.web.app.root}/derechohabientes/registro");			          
	$("#frmConfirmacion").submit();
//	location.href=context_path + '/derechohabientes/registro';
}
	
$(document).ready(function(){	
	
	 $('form:not(.formNotBlock)').submit(function(){
         $.blockUI();
	 });  
	 
	 
	var mensajeAprobado = '<div class="ui-widget">' +
	'<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> ' +
	'<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>' +
	'<strong>Aprobado</strong></p></div></div>';

	var mensajeRechazo = '<div class="ui-widget">' +
	'<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">'+
	'<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>' +
	'<strong>No aprobado</strong></p></div></div>';
	
	$(function(){		
		var actor = $('#perfil').val();
		var req = $("#requisito").val();
		var reqMvo = $("#reqMotivo").val();	
		var razon = $("#razonR").val();
		var parentesco = $("#idParentesco").val();
		
		if(actor == "1"){
			$('#cancela').show();
			$('#minimosDiv').show();
		}else{
			$('#minimosDiv').hide();
		}
		
		
		if(req == "1") {
			$('#resultado').html(mensajeAprobado);
			$('#aceptar').show();
		}else{
			$('#resultado').html(mensajeRechazo);
			if(actor == "1"){
				$('#aceptar').hide();
			}else{
				$('#aceptar').show();
			}	
			verificarBajaConcubina();
		}
		
		if(razon == "1"){
			$('#razonReg').hide();
			$('#tipoReg').hide();
		}else{
			if(razon == "8"){
				$('#tipoReg').show();
			}else{
				$('#tipoReg').hide();
			}
			$('#razonReg').show();
		}
		
		if(parentesco == "5" || parentesco == "6"){
			$("#parentAseg").show();
			$("#parentInteg").hide();
		}else{
			$("#parentAseg").hide();
			$("#parentInteg").show();
		}		
	});	
});

function verificarBajaConcubina() {
	var mensaje = "Existe Concubina(rio) registrado. Es necesario realizar la baja de este integrante para registrar Esposo(a).";
	if($.trim($("div#motivo").text().toLowerCase()) == $.trim(mensaje.toLowerCase())) {		
		$("button#aceptar").text("Baja de concubina(rio)");
		$("button#aceptar").click(goBajaConcubina_rio);
		$("button#aceptar").show();
	}
}
var goBajaConcubina_rio = function() {
	
	
	$dialog = $("<div></div>");
	$dialog.html("\u00BF Est\u00E1 seguro que desea registrar una solicitud de baja?");
	$dialog.dialog({
		autoOpen : false,
		resizable: false,
		modal: true,
		  width: 480,				  
	      buttons : {
	        "Si" : function() {	
	        	location.href = context_path + "/derechohabiente/baja/concubinato/home";
	        },
	        "No" : function() {
	        	$(this).dialog("close");
	        }	
	      }
	  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	$dialog.dialog('open');
}