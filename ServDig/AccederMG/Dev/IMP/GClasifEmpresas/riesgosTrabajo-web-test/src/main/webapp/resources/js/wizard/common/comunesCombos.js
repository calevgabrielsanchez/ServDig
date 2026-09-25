/**
 * 
 */
var combosComunesCtrl = {
	existeDelegacion:false,
	existeSubdelegacion: false,
	delegacion: 0,
	subdelegacion: 0,
	context: '/${mvn.web.app.root}',
	optionDefault: '<option value=\"0\">--Selecciona por favor--</option>',
	init: function() {
		if($("#delegacion").length) {//si existe el combo de las delegaciones seteamos el evento y los datos
			combosComunesCtrl.existeDelegacion= true;
			combosComunesCtrl.cargarDelegaciones();
			$("#delegacion").on("change",{attr:"delegacion"},combosComunesCtrl.setValorCombo);
		} else {
			combosComunesCtrl.delegacion = $("#idDelegacion").val();
		}
		
		if($("#subdelegacion").length){
			combosComunesCtrl.existeSubdelegacion=true;
			$("#subdelegacion").on("change",{attr:"subdelegacion"},combosComunesCtrl.setValorCombo);
			if(!combosComunesCtrl.existeDelegacion) {
				combosComunesCtrl.delegacionChange();
			}
		} else {
			combosComunesCtrl.subdelegacion = $("#idSubdelegacion").val();
		}
	},
	limpiarCombos: function() {
		combosComunesCtrl.limpiarDelegacion();
		combosComunesCtrl.limpiarSubdelegacion();
	},
	setValorCombo: function(event) {
		var atributo = event.data.attr;
		combosComunesCtrl[atributo] = this.value;
		$("#contenedorRiegosTrabajoHistorial").html('');
		if(combosComunesCtrl[atributo+"Change"])combosComunesCtrl[atributo+"Change"]();
	},
	limpiarDelegacion: function() {
		if(combosComunesCtrl.existeDelegacion) {
			$("#delegacion").val(combosComunesCtrl.delegacion=0);
		}
	},
	limpiarSubdelegacion: function() {
		if(combosComunesCtrl.existeSubdelegacion) {
			$("#subdelegacion").val(combosComunesCtrl.subdelegacion=0);
			if(combosComunesCtrl.existeDelegacion) {
				$("#subdelegacion").html(combosComunesCtrl.optionDefault);
			}
		}
	},
	cargarDelegaciones: function() {
		combosComunesCtrl.cargarCombo("/getDelegaciones",function(data) {
			combosComunesCtrl.setCombos("delegacion","",data.delegaciones, "id", "clave,descripcion");
		});
	},
	delegacionChange: function() {
		//console.log("ejecuto function delegacion con la delegacion: " + combosComunesCtrl.delegacion);
		combosComunesCtrl.limpiarSubdelegacion();
		combosComunesCtrl.cargarCombo("/getSubdelegaciones/"+combosComunesCtrl.delegacion, function(data) {
			combosComunesCtrl.setCombos("subdelegacion","",data.subdelegaciones, "id", "clave,descripcion");
		});
	},
	cargarCombo: function(url, funcion) {
		$.ajax({
			url : combosComunesCtrl.context + "/historialRiesgoTrabajo/comunes"+url,
			data : null,
			cache: false,
			type: 'POST',
			beforeSend : $.blockUI,
			success : funcion,
			complete: $.unblockUI
		});
	},
	setCombos: function(id, selDefault, valores, campoclave, campodescripcion) {
		var options = combosComunesCtrl.optionDefault, esObjeto= campoclave != null;
		$.each(valores, function(index,value){
			var clave = esObjeto ? value[campoclave] : value, camposDesc = esObjeto ? campodescripcion.split(",") : [],
			descripcion= !esObjeto ? value : value[camposDesc[0]] + (camposDesc.length ? " - " + value[camposDesc[1]] : ""),
			selected = clave == selDefault ? ' selected=\"selected\" ': '';
			options +='<option value=\"'+ clave +'\"'+selected+'>'+descripcion+'</option>';
		});
		$("#"+id).html(options);
	}
};