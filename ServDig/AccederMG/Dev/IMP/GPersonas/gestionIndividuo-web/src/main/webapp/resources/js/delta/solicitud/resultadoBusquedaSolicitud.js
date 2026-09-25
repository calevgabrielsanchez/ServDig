$(document).ready(function(){
	$('input').click(function(){
		var indice = $(':checked').attr('indice')
		if($(':checked').val() == 'true'){
			deshabilitarComboRazonRechazo(indice);
		}else{
			habilitarComboRazonRechazo(indice);
		}
	});
});

function deshabilitarComboRazonRechazo(index) {
	var tramiteRazonResultado = '#tramite' + index + 'idRazonResultado';
	$(tramiteRazonResultado).fadeTo('fast', 0.5); // esto en necesario para que funcione en IE8, porque el disabled ya hace un fade pero si no se pone esta linea el IE8 hace pndjds
	$(tramiteRazonResultado).attr('disabled', 'disabled');
	$(tramiteRazonResultado).attr('value', '-1');
}

function habilitarComboRazonRechazo(index) {
	var tramiteRazonResultado = '#tramite' + index + 'idRazonResultado';
	$(tramiteRazonResultado).fadeTo('fast', 1.0); // esto en necesario para que funcione en IE8, porque el disabled ya hace un fade pero si no se pone esta linea el IE8 hace pndjds
	$(tramiteRazonResultado).removeAttr('disabled');
}
