/*
 * JS de la funcionalidad generica de los portlets
 */

var portlet = {

	init : function() {

		$('.portlet').each(function() {

			var oPortlet = $(this);
			var name = $(oPortlet).attr('portlet-name');

			var url = $(oPortlet).attr('portlet-url');
			
			if(typeof url !== 'undefined') {
			
				$.get(url, null, function(data) {
					$(oPortlet).html(data);
				}).done(function() {
					var urlInner = $('.contenedor', oPortlet).attr('portlet-url');
					$('.contenedor', oPortlet).load(urlInner, function() {
	
						var scriptInit = $('#initPortlet', this).text();
						eval(scriptInit);
						inicializarValidacionesCaracteresEspeciales();
					});
				});
			}
		});
	}
};

$(document).ready(function() {
	portlet.init();

	$('.contenedor-portlet .portlets').sortable({
		handle : ".handle",
		placeholder : "sortable-placeholder"
	});

});