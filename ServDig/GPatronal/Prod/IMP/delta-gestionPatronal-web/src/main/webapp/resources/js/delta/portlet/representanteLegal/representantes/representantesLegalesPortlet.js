/*
 * JS de control del Portlet de Representantes Legales.
 */

$(document).ready(function() {
	// representantesPortlet.init();
});

var representantesPortlet = {

	init : function() {
		$('.contenedor').each(function() {
			var url = $(this).attr('portlet-url');
			$(this).load(url);
		});
	},

	refresh : function() {

	}

};
