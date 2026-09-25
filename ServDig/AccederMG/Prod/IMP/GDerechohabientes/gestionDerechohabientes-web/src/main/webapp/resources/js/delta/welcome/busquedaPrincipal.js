var folioRequerido = true;
var nssRequerido = false;
var  tb = '';

$(document).ready(function() {
	
	//$.postJSON("/gestionVigenciaGpoFamiliar-web-externo/derechohabientesImg/getFotografiaAsegurado/1/11111111111", null, function(data){});
	
	validateForm.allowOnlyRegularExpression( $('.entero_11'),regularExpression.entero_11);	

	if(tb == 'nss' || tb == ''){
		
		//document.getElementById('folioTable').style.display='none';
		//document.getElementById('bFolio').style.display='none';
		//document.getElementById('sBFolio').style.display='none';
		document.getElementById( 'tipoBusqueda' ).value = "nss";
	}else{
		
	//	document.getElementById('folioTable').style.display='block';
	//	document.getElementById('bFolio').style.display='block';
	//	document.getElementById('sBFolio').style.display='block';
	//	document.getElementById( 'tipoBusqueda' ).value = "folio";
	}	
	
		$("#busquedaPrincipalForm").validate({ 
			rules: { 
				nss: {
					required:true,
					maxlength: 11,
					minlength: 10
				}, 
				folio: {
					required:true,
					maxlength: 18
						
				}
			}, 
		errorLabelContainer: "#warning", 
		messages: { 
			nss:   {required:"Obligatorio", 
					maxlength:"Debe ser de 11 d\u00edgitos como m\u00e1ximo", 
					minlength:"La longitud del nss debe ser al menos de 10 d\u00edgitos.",
					numeric:"Debe ser num�rico"
					},
			folio: {required:"Obligatorio", maxlength:"Debe ser de 18 d\u00edgitos como m\u00e1ximo" 
					}
		} 
	}); 
		
		$("#busquedaPrincipalForm").submit(function(event) {
			
			var valido = $("#busquedaPrincipalForm").valid();
			if(valido){
				$.blockUI();
				return true;
			}
			
			return false;
			
		});
		
		$("#aceptarVal").click(function() {
			var valido = $("#busquedaPrincipalForm").valid();
			if(valido){
				
				$("#busquedaPrincipalForm").submit();
			}
		});
		
		$("#cancelar").click(
				function() {
					cancelarBusqueda();
				}
		);	
		
		$("#nss").focus();
}); 
	

function cancelarBusqueda() {
	
	$decision = $('<div></div');
	
	$decision.dialog({
		autoOpen : false,
		resizable: false,
		height: 140,
		title: 'Selecciona una opci\u00F3n',
		modal: true,
		buttons: {
			"Si": function() {
				location.href = "" + context_path + "/welcome/uno/busqueda";
				cierraDialogo($(this));
			},
			"No": function() {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$decision.text('\u00BF Est\u00E1 seguro que desea cancelar la b\u00FAsqueda del resumen del grupo familiar?');
	$decision.dialog('open');
}

function cierraDialogo($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}
/**
 * muestra los campos del folio cuando se selecciona 
 * la liga para capturar el folio de la solicitud
 */

/*
	function mostrarFolio() {
		$('#contError').hide();
//		folioRequerido = true;
//		nssRequerido = false;
		                  
		document.getElementById( 'tipoBusqueda' ).value = "folio"; 
		document.getElementById( 'nss' ).value = "";
		
		//Mostrar el folio
		$('#folioTable').show();
		$('#sBFolio').show();
		$('#bFolio').show();
		
		$('#nssTable').hide();
		$('#bNSS').hide();
		$('#sBNSS').hide();
		
		$("#nss input:text").attr('disabled', 'disabled');
		$("#folio input:text").attr('disabled', false);
//	    document.getElementById('folioTable').style.display='block';
//	    document.getElementById('sBFolio').style.display='block';
//	    document.getElementById('bFolio').style.display='block';
//		
//	    //Ocultar nss
//	    document.getElementById('nssTable').style.display='none';
//	    document.getElementById('bNSS').style.display='none';
//	    document.getElementById('sBNSS').style.display='none';
	        
	} 
	
	*/
	
	
	/**
	 * Oculta los campos del folio cuando se selecciona 
	 * la liga para capturar el folio de la solicitud
	 */
/*

		function ocultarFolio() {
			
			$('#contError').hide();			
			folioRequerido = false;
			nssRequerido = true;
			
			document.getElementById( 'tipoBusqueda' ).value = "nss";
			document.getElementById( 'folio' ).value = "";
			//Ocultar el folio
			$('#folioTable').hide();
			$('#sBFolio').hide();
			$('#bFolio').hide();
			//Mostrar nss			
			$('#nssTable').show();
			$('#bNSS').show();
			$('#sBNSS').show();
			
			$("#folio input:text").attr('disabled', 'disabled');
			$("#nss input:text").attr('disabled', false);
			
			
			//Ocultar el folio
//		    document.getElementById('folioTable').style.display='none';
//		    document.getElementById('sBFolio').style.display='none';
//		    document.getElementById('bFolio').style.display='none';
//			//Mostrar nss
//		    document.getElementById('nssTable').style.display='block';
//		    document.getElementById('bNSS').style.display='block';
//		    document.getElementById('sBNSS').style.display='block';

		    
		} 
		*/
		
	