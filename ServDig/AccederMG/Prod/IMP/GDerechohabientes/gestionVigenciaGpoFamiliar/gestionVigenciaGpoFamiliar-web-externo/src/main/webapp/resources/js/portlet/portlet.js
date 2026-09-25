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

					var resizeBtn = $('div.controles .widget-resize', oPortlet);
					resizeBtn.trigger('click');
					
					$('.contenido', oPortlet).load(urlInner, function() {
	
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
	
	$('.portlet a:has(.icono-refrescar)').live("click", function(event) {
		var _portlet = $(this).parents().filter('.portlet');
		_portlet.trigger('load-portlet');
	});
	
	$('.portlet .titulo span').live('click',function(event) {
		var resizeBtn = $('div.controles .widget-resize',$(this).parent());
		resizeBtn.trigger('click');
	});

});