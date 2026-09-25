var conector;

$(function(){

	CometCtrl.prototype.addSubscriber = function(subscription) {
		this.listInitialSubscription.push(subscription);
	};

	setTimeout(function() {
		var user = $("#userCtrl").val();
		var timeout_subscriber = {
			channel : [ '/channels/server/session/', user ].join(''),
			action : function(message) {
				if (message.data.session_finished === true) {
					$('#dialogEndOfSession').dialog({
						resizable : false,
						height : 'auto',
						modal : true,
						close : closure_force_logout,
						buttons : {
							"ACEPTAR" : closure_force_logout
						}
					});
				}
			}
		};

		conector = new CometCtrl();
		conector.addSubscriber(timeout_subscriber);
		conector.init(false);
	}, 1100);
});

var closure_force_logout = function(event, ui) {
	document.location.href = '/portal-ventanilla-web/j_spring_security_logout';
};

