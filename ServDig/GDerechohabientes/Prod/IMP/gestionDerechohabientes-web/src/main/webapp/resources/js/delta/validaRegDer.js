var objDatable;
$(document).ready(function(){
	var parentesco = $("#parentesco").val();
	var dom = $("#domDif").val();
	
	$('form:not(.formNotBlock)').submit(function(){
        $.blockUI();
	 });
	
	if(dom == "igual" || parentesco == "4"){		
		$("#comboTurno").hide();
		$("#medicoEnTurno\\.turno\\.descripcion").show();
		$("#medicoEnTurno\\.turno\\.descripcion").attr('readonly','readonly');
		$("#consultorio").hide();
		$("#medicoEnTurno\\.consultorio\\.descripcion").show();
		$("#medicoEnTurno\\.consultorio\\.descripcion").attr('readonly','readonly');
		$("#medico").hide();
		$("#medicoEnTurno\\.medicoFamiliar\\.nombre").show();		
		$("#medicoEnTurno\\.medicoFamiliar\\.nombre").attr('readonly','readonly');
	}else{		
		$("#comboTurno").show();
		$("#medicoEnTurno\\.turno\\.descripcion").hide();
		$("#consultorio").show();
		$("#medicoEnTurno\\.consultorio\\.descripcion").hide();
		$("#medico").show();
		$("#medicoEnTurno\\.medicoFamiliar\\.nombre").hide();
	}
	
	$("#turno").change(function() {
		var idUmf = $('#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF').val();
		var idTurno = $('#turno').val();
		$('#medicoEnTurno\\.turno\\.idTurno').val(idTurno);
		
		$("#consultorio")[0].selectedIndex = 0;	
		$("#medico")[0].selectedIndex = 0;	
		setConsultorios(idUmf,idTurno);
	});		
	
	$("#consultorio").change(function() {				
		var idUmf = $('#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF').val();
		var idTurno = $('#medicoEnTurno\\.turno\\.idTurno').val();
		var idConsultorio = $('#consultorio').val();
		$('#medicoEnTurno\\.consultorio\\.idConsultorio').val(idConsultorio);
				
		setMedico(idUmf,idTurno,idConsultorio);
		
	});
	
	$("#medico").change(function(){
		$("#medicoEnTurno\\.idMedicoContultorioTurno").val($("#medico").val());
		
//		$("#msgConfirmaMedico").dialog({
//			modal: true,
//		      buttons : {
//		        "Si" : function() {
//		        	$(this).dialog("close");
//		        },
//		        "No" : function() {
//		        	$("#turno")[0].selectedIndex = 0;
//		        	$("#consultorio")[0].selectedIndex = 0;	
//		        	$("#medico")[0].selectedIndex = 0;		        	
//		        	$(this).dialog("close");
//		        }
//		      }
//		  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	});
	
	$('#razonRegistro').attr('readonly','readonly');
	$('#tipoRegistro\\.descripcion').attr('readonly','readonly');
	$('#medicoEnTurno\\.unidadMedicaFamiliar\\.nombreCorto').attr('readonly','readonly');
	asignartextAreaLimites("observaciones",{styles: {}});
});
	  

function setMedico(idUmf,idTurno,idConsultorio) {
	var url = context_path + "/umf/getMedicosUmfTurnoMedicoEsp";
	var parametros = {
		'unidadMedicaFamiliar': {
			'idUMF': idUmf
		},
		'turno': {
			'idTurno': idTurno
		},
		'consultorio': {
			'idConsultorio': idConsultorio
		}
	};
	
	if(idUmf != null && idUmf != undefined && idTurno != null && idTurno != undefined && $.trim(idTurno).length != 0 && idConsultorio != null && idConsultorio != undefined && $.trim(idConsultorio).length != 0) {
		$.postJSON(url, parametros, function(result) {
			var options ="";
			result.sort();
			for(var i = 0 ; i < result.length ; i++){				
					options += "<option value='" + result[i].idMedicoContultorioTurno + "'>" + result[i].medicoFamiliar.nombre+" "+result[i].medicoFamiliar.primerApellido+" "+result[i].medicoFamiliar.segundoApellido + "</option>";
					$("#medicoEnTurno\\.idMedicoContultorioTurno").val(result[i].idMedicoContultorioTurno);
			}			
			$("#medico").html(options);
		});
	}
}

function setConsultorios(idUmf , idTurno, idConsultorio) {
	
	var url = context_path + "/umf/getConsultoriosEsp";
	var parametros = {
		'unidadMedicaFamiliar': {
			'idUMF': idUmf
		},
		'turno': {
			'idTurno': idTurno
		}
	};				
	
	if(idUmf != null && idUmf != undefined && idTurno != null && idTurno != undefined && $.trim(idTurno).length != 0) {
		$.postJSON(url, parametros, function(result) {
			var options = "<option value=''> -- Por favor seleccione -- </option>";
			
			for(var i = 0 ; i < result.length ; i++){
				if(idConsultorio != null || idConsultorio != undefined) {
					if(idConsultorio == result[i].idConsultorio)
						options += "<option value='" + result[i].idConsultorio + "' selected='selected'>" + result[i].descripcion + "</option>";
					else
						options += "<option value='" + result[i].idConsultorio + "'>" + result[i].descripcion + "</option>";
				}
				else
					options += "<option value='" + result[i].idConsultorio + "'>" + result[i].descripcion + "</option>";	
			}
			
			$("#consultorio").html(options);
		});
	}
}

function rechazo(){

	$("#razonRechazo").dialog({
		modal: true,
		  width: 480,				  
	      buttons : {
	        "Si" : function() {	
	        	var idSolicitud = $("#idSolicitud").val();
	        	var tipoTramite = 1;
	        	var razonRechazo = $("#idRazon").val();	   
	        	fnAbrirMensajeEsperePorFavor();
	        	location.href = context_path +"/derechohabientes/registro/rechazar2/"+idSolicitud+"/"+tipoTramite+"/"+razonRechazo;
	        },
	        "No" : function() {
	        	$(this).dialog("close");
	        }	
	      }
	  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

}	

function cancela(){
	$("#msg03").dialog({
		modal: true,
	      buttons : {
	        "Si" : function() {
	          $("#frmRegDerechohabiente").attr("action","/${mvn.web.app.root}/inicio/grupoFamiliar");			          
			  $("#frmRegDerechohabiente").submit();	
	        },
	        "No" : function() {
	          $(this).dialog("close");
	        }
	      }
	  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
}

function registraSolicitud(){
	var idTramite = $('#idTramite').val();		  	
	  $("#frmValidacion").attr("action","/${mvn.web.app.root}/derechohabientes/registro/actualizar");		
	  $("#frmValidacion").submit();	
}

function valDatos(){
	var correcto = true;
	var patron = $("#patron").val();	
	var requiereDocs = $("#requiereDocs").val() == 1;
	
	if(requiereDocs && !fileUploadFinish){
		$("#msgDocumentosProb").dialog({	
			modal: true,
		      buttons : {
		        "Aceptar" : function() {
		        	$(this).dialog("close");
		        }
		      }
		  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();			
		correcto = false;
		return 0;
	}
	
	/*
	if(patron == "0"){
		if(($('#tipoTramite').val() =="46" || $('#tipoTramite').val() =="49") && $('#capturado').val() != "1"){
			$("#msgCuestionario").dialog({	
				modal: true,
			      buttons : {
			        "Aceptar" : function() {
			        	$(this).dialog("close");
			        }
			      }
			  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();			
			correcto = false;
			return 0;
		}
	}else if($('#tipoTramite').val() =="46" && $('#capturado').val() != "1"){
		$("#msgCuestionario").dialog({	
			modal: true,
		      buttons : {
		        "Aceptar" : function() {
		        	$(this).dialog("close");
		        }
		      }
		  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();			
		correcto = false;
		return 0;
	}*/
	
	if($('#tipoTramite').val() =="44"  ||  $('#tipoTramite').val() =="45" || $("#domDif").val() == "diferente"){
		if (document.getElementById('medicoEnTurno.idMedicoContultorioTurno').selectedIndex==0){
			$("#msgMedico").dialog({	
				modal: true,
			      buttons : {
			        "Aceptar" : function() { 
			        	$(this).dialog("close");
			        }
			      }
			  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();			
			correcto = false;
			return 0;
		}
		if (document.getElementById('turno').selectedIndex==0){
			$("#msgTurno").dialog({	
				modal: true,
			      buttons : {
			        "Aceptar" : function() {
			        	$(this).dialog("close");
			        }
			      }
			  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
			correcto = false;
			return 0;
		}
		if (document.getElementById('medicoEnTurno.consultorio.idConsultorio').selectedIndex==0){
			$("#msgConsultorio").dialog({		
				modal: true,
			      buttons : {
			        "Aceptar" : function() {
			        	$(this).dialog("close");
			        }
			      }
			  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
			correcto = false;
			return 0;
		}
	}
	if(correcto){
		registraSolicitud();
	}
}