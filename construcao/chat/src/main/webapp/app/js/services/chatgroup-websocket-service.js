desafioChat.service('ChatGroupWebsocketService', function($rootScope, $q, $timeout) {

    var service = {};
    var listener = $q.defer();
    $rootScope.groups_socket = {
      client: null,
      stomp: null
    };

    service.RECONNECT_TIMEOUT = 30000;
    service.SOCKET_URL = "/chat/websocket";
    service.CHAT_TOPIC = "/topic/new-group/";
    service.CHAT_BROKER = "/app/websocket/groups";

    service.receive = function() {
      return listener.promise;
    };

    service.send = function(chatGroup) {
      $rootScope.groups_socket.stomp.send(service.CHAT_BROKER, {
        priority: 9
      }, angular.toJson(chatGroup));
    };

    var reconnect = function() {
      $timeout(function() {
        initialize();
      }, this.RECONNECT_TIMEOUT);
    };

    var getMessage = function(data) {
      return data.body;
    };


    var startListener = function() {
      $rootScope.groups_socket.stomp.subscribe(service.CHAT_TOPIC, function(data) {
        ("LISTENER:", data);
        listener.notify(getMessage(data));
      });
    };

    service.initialize = function(id) {
      if($rootScope.groups_socket.stomp != null || $rootScope.groups_socket.client != null){
         $rootScope.groups_socket.stomp.disconnect();
      }

      $rootScope.groups_socket.client = new SockJS(service.SOCKET_URL);
      $rootScope.groups_socket.stomp = Stomp.over($rootScope.groups_socket.client);
      $rootScope.groups_socket.stomp.connect({}, startListener);
    };

    return service;
  });
