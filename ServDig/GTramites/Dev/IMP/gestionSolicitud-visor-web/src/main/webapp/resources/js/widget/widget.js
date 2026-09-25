/*
 * JS Widget 
 */

var widget = {
	waitingDiv : '',
	
	init : function() {
		$('.widget').bind('init-widget', function() {
			var _widget = $(this);
			var _url = _widget.attr('widget-url');
			
			// Se limpia el contenido del widget
			$(_widget).children('.contenedor').empty();
			
			$.get(_url, null, function(data) {
				//carga de una sola vez
				_widget.html(data);
			}).done(function() {
				if (widget.waitingDiv === '') {
					widget.waitingDiv = $('div.contenido', _widget).html();
				}

			});
		}).bind('load-widget', function() {
			var contenedor = $('.contenedor', this);
			var contenidoWrapper = $('.contenido', contenedor);
			
			contenidoWrapper.html(widget.waitingDiv);
			
			setTimeout(function() {
				var urlInner = contenedor.attr('widget-url');
				contenidoWrapper.load(urlInner);
			}, 300);
		}).trigger('init-widget');
	},
	
	refresh : function(message) {
		var idWidgetEjecucion = message.data.idWidget;

		$('.widget').each(function() {
			var oWidget = $(this);
			var idNameWidget = $('.contenedor', oWidget).attr('widget-name');
			if (idNameWidget == idWidgetEjecucion) {
				$(this).trigger('load-widget');
			}
		});
	}
};

$(document).ready(function() {
	
	widget.init();
	
	//Configuracion de los efectos de presentacion
	$('.contenedor-widget').sortable({
		handle : ".handle",
		placeholder : "sortable-placeholder"
	});

	var options = {};

	$('.widget-resize').live('click',function(event) {
		var icon = $('i', this);
		var css_present = $(icon).attr('class');
		var iconRefresh = $('.widget-refresh', $(this).parent());
		var iconresizefull = "icon-plus";
		var iconresizesmall = "icon-minus";

		if (css_present == iconresizesmall) {
			$(icon).removeClass(css_present).addClass(
					iconresizefull);
		} else {
			$(icon).removeClass(css_present).addClass(
					iconresizesmall);
		}
		
		iconRefresh.toggle();
		
		$('.contenido', $(this).parent().parent().parent())
			.toggle('drop', options, 500, function (){
				
				if (css_present == iconresizefull) {
					var loaded = $(this).attr('already-loaded');
					
					if (loaded == 'false') {
						$(this).attr('already-loaded','true');
						if ($(this).parents().filter('.widget').length > 0) {
							$(this).parents().filter('.widget').trigger('load-widget');
						} else {
							$(this).parents().filter('.portlet').trigger('load-portlet');
						}
					}
				} 
			});
	});
	
	$('.widget .titulo span').live('click',function(event) {
		var resizeBtn = $('div.controles .widget-resize',$(this).parent().parent());
		resizeBtn.trigger('click');
	});

	$('.widget a:has(.icon-refresh)').live('click', function(event) {
		var _widget = $(this).parents().filter('.widget');
		_widget.trigger('load-widget');
	});
});
