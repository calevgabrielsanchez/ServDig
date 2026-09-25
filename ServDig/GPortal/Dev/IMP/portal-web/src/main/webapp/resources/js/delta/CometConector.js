/*
 * JS de soporte para la inclusion y llamado
 * de los componentes de CometD.
 */

/**
 * Objeto de control de llamadas al servidor de CometD, a traves de este objeto
 * de JS se iniciliaza, configura , invoca, publica y suscribe peticiones al
 * servidor CometD.
 */

var CometCtrl = function() {
	var _self = this;
	var _messageEnabled = false;
	var _connected = false;
	var _initialConfiguration = {
		url : '/portal-comet-web/cometd',
		logLevel : 'info'
	};
	var _deltaChannel = '/comet/delta';

	function _connectionEstablished() {
		
		_initialSubscribe();
		
		if (_messageEnabled) {
			$.jGrowl('Comet Connection Established', {
				header : 'Mensaje del sistema',
				life : 3000
			});
		}
	}

	function _connectionBroken() {
		if (_messageEnabled) {
			$.jGrowl('Comet Connection Broken', {
				header : 'Mensaje del sistema',
				sticky : true
			});
		}
		
		if (!$.cometd.isDisconnected()) {
			_self.disconnect(true);
		}
		
		_self.init(false);
	}

	function _connectionClosed() {
		if (_messageEnabled) {
			$.jGrowl('Comet Connection Closed', {
				header : 'Mensaje del sistema',
				sticky : true
			});
		}
	}

	function _metaConnect(message) {
		if ($.cometd.isDisconnected()) {
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
			if (_messageEnabled) {
				$.jGrowl('Handshake done', {
					header : 'Mensaje del sistema',
					sticky : true
				});
			}
		}
	}

	function _initialSubscribe() {
		if (_self.listInitialSubscription.length > 0) {
			
			$.each(_self.listInitialSubscription, function(){
				var subscription = this;
				
				var currentSubscription = $.cometd.subscribe(_deltaChannel + subscription.channel, subscription.action);
				subscription.fullChannel = currentSubscription[0];
				subscription.id = currentSubscription[1];
												
			});
		}		
	}

	this.listInitialSubscription = [];

	this.init = function(messageEnabled) {
		if (messageEnabled) {
			_messageEnabled = true;
		}

		$(window).unload(function() {
			$.cometd.disconnect(true);
		});
		
		$.cometd.websocketEnabled = false;	
		
		$.cometd.configure(_initialConfiguration);

		$.cometd.addListener('/meta/handshake', _metaHandshake);
		$.cometd.addListener('/meta/connect', _metaConnect);
		

		$.cometd.onListenerException = function(exception, subscriptionHandle,isListener, message) {
			alert(exception);
		};
		
		$.cometd.handshake();
	};

	this.disconnect = function() {
		$.cometd.disconnect(true);
	};

	this.receive = function(message) {
		var text = message.data.systemMessage;

		if (_messageEnabled) {
			$.jGrowl(text, {
				header : 'Mensaje del sistema',
				life : 3000
			});
		}
	};

	this.publish = function(channel, data) {
		$.cometd.publish(_deltaChannel + channel, data);
	};

	this.subscribe = function(channel, systemAction) {
		var cometSubscription = $.cometd.subscribe(_deltaChannel + channel, systemAction);
		
		var subscription = {
			channel : channel,
			action : systemAction,
			fullChannel : cometSubscription[0],
			id : cometSubscription[1]
		};
		
		_self.listInitialSubscription.push(subscription);
		
		return cometSubscription;
	};

	this.unsubscribe = function(subscription) {
		$.cometd.unsubscribe(subscription);
		
		$.each(_self.listInitialSubscription, function(idx){
			if (this.fullChannel == subscription[0]
					&& this.id == subscription[1]) {
				_self.listInitialSubscription.splice(idx, 1);
			} else if (this.fullChannel == subscription[0]) {
				_self.listInitialSubscription.splice(idx, 1);
				return false;
			}
		});
	};
};
