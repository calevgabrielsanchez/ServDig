(function($) {
	var cometd = $.cometd;

	$(document).ready(function() {
		
		function _connectionEstablished() {
		}

		function _connectionBroken() {
		}

		function _connectionClosed() {
		}

		var _connected = false;
		function _metaConnect(message) {
			if (cometd.isDisconnected()) {
				_connected = false;
				_connectionClosed();
				return;
			}

			var wasConnected = _connected;
			_connected = message.successful === true;

			if (!wasConnected && _connected) {
				_connectionEstablished();
			} else if (wasConnected && !_connected) {
				_connectionBroken();
			}
		}

		function _metaHandshake(handshake) {
			if (handshake.successful === true) {
				cometd.batch(function() {
					cometd.subscribe('/seguimiento/avisoConclusion', function(message) {
						var aviso = message.data.aviso;
						var exito = message.data.exito;

						$.unblockUI();

						if (exito) {
							fnSolicitudProcesada();
						} else {
							fnOnErrorEnSolicitud();
						}
					});

					var _idSolicitud = $('input#id').val();

					cometd.publish('/service/realizarSeguimiento', {
						idSolicitud : _idSolicitud
					});
				});
			}
		}

		$(window).unload(function() {
			cometd.disconnect(true);
		});

		cometd.configure({
			url : cometURL,
			logLevel : 'info'
		});

		cometd.addListener('/meta/handshake', _metaHandshake);
		cometd.addListener('/meta/connect', _metaConnect);

		cometd.handshake();
	});
})(jQuery);
