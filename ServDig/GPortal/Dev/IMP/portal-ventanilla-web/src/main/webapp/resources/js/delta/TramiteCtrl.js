	dialogo = {};
	function init(_divContenedor) {
		dialogo = {};
		var d = $('#' + _divContenedor);
		this.dialogo = d.dialog({
			title : '',
			autoOpen : false,
			width : 1250,
			left  : '60px',
			modal : true,
			resizable : false,
			autoResize : true,
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

		this.dialogo.dialog({
			/*
			 * beforeClose : function(event, ui) {
			 * UbicarPersonaCtrl.callbacks.call(DomicilioCtrl.domicilio); },
			 */
			close : function(event, ui) {
				$(this).dialog('destroy').empty();
			}
		});
	}
	function cargarDialogoTramite(div,url) {
		init(div);
		this.dialogo.dialog('open');
		$('#' + div)
				.html('<iframe id="dialogoTramite" src="'
					+ url
					+ '" width="100%" height="100%" frameborder="0" onload="set_size(\'ubicarPersonaFrame\', 900)"/>');
		$('#'+div).append("<div style='text-align:right;'><button type='button' id='finalizarTramite' class='btn btn-primary' style='border-color: green; background: green; border-radius:3px;'>FINALIZAR TR&Aacute;MITE</button></div>");
		$('#'+div).prepend("<script text='javascrit'>$('#finalizarTramite').mouseout(function() {$('#finalizarTramite').css('background', 'green');$('#finalizarTramite').css('border-color', 'green');});;$('#finalizarTramite').mouseover(function() {$('#finalizarTramite').css('background', '#1a79a7');$('#finalizarTramite').css('border-color', '#1a79a7');});;$('button.ui-dialog-titlebar-close').click(function(){cerrar_manual();});$('button#finalizarTramite').click(function(){cerrar();});</script>");
	};

	function cerrar() {
		this.dialogo.dialog('close');
		parar();
		alert("DURACION DE TRAMITE: "+$('input#tiempoServicio').val());
}
	function cerrar_manual() {
		parar();
}