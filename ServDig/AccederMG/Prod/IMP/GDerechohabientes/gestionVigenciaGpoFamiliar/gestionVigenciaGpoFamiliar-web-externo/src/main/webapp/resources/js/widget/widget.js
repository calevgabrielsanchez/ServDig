/*
 * JS Widget 
 */

var widget = {
	init : function() {
		
		$('.widget').each(function() {
			
			var _widget = $(this);
			var _url = _widget.attr('widget-url');
			
			if(typeof _url !== 'undefined') {
				
				$.get(_url, null, function(data) {
					
					$(_widget).html(data);
				}).done(function() {
					var urlInner = $('.contenedor', _widget).attr('widget-url');

					var resizeBtn = $('div.controles .widget-resize', _widget);
					resizeBtn.trigger('click');
					
					$('.contenido', _widget).load(urlInner, function() {
	
						/*var scriptInit = $('#initWidget', this).text();
						eval(scriptInit);
						inicializarValidacionesCaracteresEspeciales();
						
						*/
					});
				});
			}
		}	
		);
	}
		
};

$(document).ready(function() {
	
	//Configuracion de los efectos de presentacion
	$('.contenedor-widget').sortable({
		handle : ".handle",
		placeholder : "sortable-placeholder"
	});

	var options = {};
	
	$('.widget .titulo span').live('click',function(event) {
		var resizeBtn = $('div.controles .widget-resize',$(this).parent().parent());
		resizeBtn.trigger('click');
	});

	$('.widget a:has(.icon-refresh)').live('click', function(event) {
		var _widget = $(this).parents().filter('.widget');
		_widget.trigger('load-widget');
	});
});
