$(document).ready(function() {

	// var fecha= getFecha();
	var index = 0;
	$("#fechaInicio").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : '-112:+0'
	});
	$("#fechaFin").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : '-112:+0'
	});
	$("#fechaAcuerdo").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : '-112:+0'
	});
	$("#prorroga\\.caracter\\.idCaracter").change(function() {
		index = $(this)[0].selectedIndex;
		if (index == 2) {
			$("#fechaFin").datepicker('disable')
		} else {
			$("#fechaFin").datepicker('enable')
		}
	});

	$("#frmRegistroProrroga").validate({
		rules : {
			noAcuerdo : {
				required : true
			},
			fechaFin: {
				required : true
			},
			fechaInicio:{
				required : true
			},
			fechaAcuerdo:{
				required : true
			},
			observaciones:{
				required : true
			}
		},
		messages : {
			noAcuerdo : {
				required : "Obligatorio"
			},
			fechaFin: {
				required : "Obligatorio"
			},
			fechaInicio: {
				required : "Obligatorio"
			},
			fechaAcuerdo:{
				required : "Obligatorio"
			},
			observaciones: {
				required : "Obligatorio"
			}
		}
	});
	
	$('#cancelarRegistro').click(
			function(){
				cancelar();
			}
	);
	
	
});


function cancelar() {
	$decision = $('<div></div');
	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 140,
		title : 'Selecciona una opcion',
		modal : true,
		buttons : {
			"Si" : function() {
				location.href = "" + context_path + "/prorroga/acuerdos";
			},
			"No" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('\u00BF Est\u00E1 seguro que desea cancelar el registro de prórroga?');
	$decision.dialog('open');
}
