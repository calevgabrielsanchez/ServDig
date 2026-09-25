/*
 * JS Widget 
 */

var widget = {

	init : function() {

		$('.widget').each(
				function() {

					var oWidget = $(this);

					var url = $(oWidget).attr('widget-url');

					$.get(url, null, function(data) {
						$(oWidget).html(data);
					}).done(
							function() {

								var urlInner = $('.contenedor', oWidget).attr(
										'widget-url');
								var idNameWidget = $('.contenedor', oWidget)
										.attr('widget-name');

								$('.contenedor', oWidget).load(urlInner);
							});

				});
	},

	refresh : function(message) {

		var idWidgetEjecucion = message.data.idWidget;

		$('.widget').each(function() {
			var oWidget = $(this);
			var idNameWidget = $('.contenedor', oWidget).attr('widget-name');
			if (idNameWidget == idWidgetEjecucion) {
				var urlInner = $('.contenedor', oWidget).attr('widget-url');
				$('.contenedor', oWidget).load(urlInner);
			}

		});
	}

};

$(document).ready(
		function() {
			widget.init();

			// Configuracion de los efectos de presentacion

			$('.contenedor-widget').sortable({
				handle : ".handle",
				placeholder : "sortable-placeholder"
			});
			var options = {};

			$('.widget-resize').live(
					'click',
					function(event) {

						var icon = $('i', this);
						var css_present = $(icon).attr('class');

						var iconresizefull = "icon-resize-full";
						var iconresizesmall = "icon-resize-small";

						if (css_present == iconresizesmall) {
							$(icon).removeClass(css_present).addClass(
									iconresizefull);
						} else {
							$(icon).removeClass(css_present).addClass(
									iconresizesmall);
						}

						$('.contenido', $(this).parent().parent().parent())
								.toggle('drop', options, 500);
					});

		});
