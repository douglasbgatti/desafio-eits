desafioChat.service('ChatService', function($rootScope, $q, $timeout) {

    var service = {};
    var listener = $q.defer();
    $rootScope.socket = {
      client: null,
      stomp: null
    };
    var chatGroupId = '';

    service.RECONNECT_TIMEOUT = 30000;
    service.SOCKET_URL = "/chat/websocket";
    service.CHAT_TOPIC = "/topic/message/";
    service.CHAT_BROKER = "/app/websocket/";

    service.receive = function() {
      return listener.promise;
    };

    service.send = function(message) {
      $rootScope.socket.stomp.send(service.CHAT_BROKER, {
        priority: 9
      }, angular.toJson(message));
    };

    var reconnect = function() {
      $timeout(function() {
        initialize(chatGroupId);
      }, this.RECONNECT_TIMEOUT);
    };

    var getMessage = function(data) {
      return data.body;
    };


    var startListener = function() {
      $rootScope.socket.stomp.subscribe(service.CHAT_TOPIC, function(data) {
        console.log("LISTENER:", data);
        listener.notify(getMessage(data));
      });
    };

    service.initialize = function(id) {
      chatGroupId = id;

      if($rootScope.socket.stomp != null || $rootScope.socket.client != null){
         $rootScope.socket.stomp.disconnect();
        // console.log("STOMP AND CLIENT", $rootScope.socket.stomp, $rootScope.socket.client);
      }

      service.CHAT_TOPIC = "/topic/message/" + chatGroupId;
      service.CHAT_BROKER = "/app/websocket/" + chatGroupId;

      $rootScope.socket.client = new SockJS(service.SOCKET_URL);
      $rootScope.socket.stomp = Stomp.over($rootScope.socket.client);
      $rootScope.socket.stomp.connect({}, startListener);
      //socket.stomp.onclose = reconnect;
    };

    // initialize();
    return service;
  });
