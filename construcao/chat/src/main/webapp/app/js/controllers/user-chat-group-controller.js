desafioChat.controller( 'UserChatGroupController' , function( $scope, $rootScope, $location, $importService, $routeParams, $mdToast, $mdDialog, ChatWebsocketService, ChatGroupWebsocketService, UserAuthenticatedService, $notification) {

  $importService('userChatGroupService');
  $importService('chatGroupService');
  $importService('messageService');

  $scope.model = {
    chatGroupId: null,
    userChatGroup: new UserChatGroup(),
    messages: [],
    message: new Message(),
    userChatGroupList: [],
    editChatGroupName: false,
    chatGroup: new ChatGroup(),
    formEditGroup: null,
    pageRequest: {
      page: 0,
      size: 100
    }
  };

  $rootScope.showSideNav = true;


  $scope.loadUsersByChatGroup = function(){
    userChatGroupService.listUserChatGroupsByChatGroupId($scope.model.chatGroupId,  {
      callbackHandler: function(result){
          $scope.model.userChatGroupList = result;
          $scope.$apply();
      },
      errorHandler: function(message, exception){
        $mdToast.simple(message, exception);
        ('ERROR', message, exception);
      }
    });
  };

  $scope.loadUserChatGroup = function(){
    if($rootScope.user.id !== null){
      $scope.model.chatGroupId = $routeParams.id;
      userChatGroupService.getUserChatGroupByUserAndChatGroup($rootScope.user.id, $scope.model.chatGroupId, {
        callbackHandler: function(result){
          $scope.model.userChatGroup = result;
          $scope.model.message.userChatGroup = $scope.model.userChatGroup;
          $scope.model.chatGroup = $scope.model.userChatGroup.chatGroup;
          $scope.loadMessages();
          $scope.loadUsersByChatGroup();
          //inicializa uma conexão websocket com o servidor se subscrevendo para escutar ao topico do grupo atual;
          ChatWebsocketService.initialize($scope.model.chatGroupId);
          $scope.$apply();
        },
        errorHandler: function(message, exception){
          $mdToast.simple(message, exception);
          ('ERROR', message, exception);
        }
      });
    }
  };

  //INIT CHAT GROUPS AND MSG
  $scope.loadUserChatGroup();



  $scope.loadMessages = function(){
    if($rootScope.user.id !== null &&  $scope.model.chatGroupId != null){

      messageService.listAllMessagesByChatGroupId( $scope.model.chatGroupId,  {
        callbackHandler: function(result){
          var msgs = result;
          //ao receber msg seta para visualizada quando não é a msg do proprio usuario
           angular.forEach(msgs, function(msg, key){
             if((msg.userChatGroup.user.id != $rootScope.user.id) && msg.visualized == false){
               $scope.setMessageStatusToVisualized(msg);
               msg.visualized = true;
               //SETs the message notificationtype - when the websocket broker delivers the messages to the destinations the listener method will deal with each type differently;
                msg.notificationType = 'MESSAGE_VISUALIZED_NOTIFICATION';
               //send the message to websocket broker /topic/message/{chat_group_id}
               ChatWebsocketService.send(msg);
             }
           });
          $scope.model.messages = msgs;
          $scope.$apply();
        },
        errorHandler: function(message, exception){
          $mdToast.simple(message, exception);
          ('ERROR',exception);
          $rootScope.showSimpleToast(message);
        }
      });
    }
  };


    // sends Message through chatservice websocket
    $scope.addMessage = function(){
        $scope.model.message.userChatGroup = $scope.model.userChatGroup;
        $scope.model.message.visualized = false;
        messageService.insertMessage($scope.model.message, {
          callbackHandler: function(result){
            $scope.model.message = result;
            $scope.model.message.userChatGroup.sentMessages = [];

            //SETs the message notificationtype - when the websocket broker delivers the messages to the destinations the listener method will deal with each type differently;
            $scope.model.message.notificationType = 'NEW_MESSAGE_NOTIFICATION';
            //send the message to websocket broker /topic/message/{chat_group_id}
            ChatWebsocketService.send($scope.model.message);

            $scope.model.message = new Message();
            $scope.$apply();
          },
          errorHandler: function(message, exception){
            $mdToast.simple(message);
            ('ERROR', exception);
          }
        });


    };



    // Receives message from websocket service
    ChatWebsocketService.receive().then(null, null, function(message) {
      var msg = angular.fromJson(message);
      msg = angular.fromJson(msg.payload);
      msg.userChatGroup = angular.fromJson(msg.userChatGroup);

      if(msg.notificationType === 'NEW_MESSAGE_NOTIFICATION' ){
          $scope.notifyUser(msg.message);
          if((msg.userChatGroup.user.id != $rootScope.user.id) && msg.visualized != true){
            $scope.setMessageStatusToVisualized(msg);
            msg.visualized = true;
          }else{
            msg.visualized = false;
          }
          $scope.model.messages.push(msg);
          return;
      }

        if(msg.notificationType === 'DELETED_MESSAGE_NOTIFICATION' ){
          angular.forEach($scope.model.messages, function(message, key){
            if(message.id == msg.id){
              $scope.model.messages.splice(key, 1);
              return;
            }
          });
        }

        if(msg.notificationType === 'MESSAGE_VISUALIZED_NOTIFICATION' ){
          angular.forEach($scope.model.messages, function(message, key){
            if(message.id == msg.id){
              message.visualized = true;
              return;
            }
          });
        }
    });


    /**
    *
    */
    $scope.notifyUser = function(message){
    	$notification.requestPermission()
        .then(
        function success(value) {
            ("ASKED FOR PERMISSION:", value);
            new Notification('Desafio Chat', {
              body: message,
              dir: 'auto',
              lang: 'en',
              tag: 'my-tag',
              icon: 'app/icons/chat_icon.png',
              delay: 1000, // in ms
              focusWindowOnClick: true // focus the window on click
            });
        }, function error() {
            ("Can't request for notification");
        })
    };

    /**
    * Assim que o websocket entregar uma mensagem sera setado como visualizado;
    *
    **/
    $scope.setMessageStatusToVisualized = function(message){
      if(message.userChatGroup.user.id != $rootScope.user.id){
        messageService.setMessageStatusToVisualized(message.id, {
          callbackHandler: function(result){
            //SETs the message notificationtype - when the websocket broker delivers the messages to the destinations the listener method will deal with each type differently;
            message.notificationType = 'MESSAGE_VISUALIZED_NOTIFICATION';
            //sends the message to the websocket broker
            ChatWebsocketService.send(message);

          },
          errorHandler: function(message, exception){
            var toast = $mdToast.simple()
              .content('ERROR: something went wrong while receiving message!')
              .action('OK')
              .highlightAction(true)
              .position('top right');
            $mdToast.show(toast);
          }
        });
      }
    }


    $scope.showEditChatGroupNameInput = function(){
      $scope.model.editChatGroupName = true;
    }

    /**
    * Messages for mdDialog
    *
    **/
      $scope.REMOVE_MESSAGE = 'Delete Message';
      $scope.REMOVE_CONFIRM_CONTENT_MESSAGE = 'Are you sure you want to delete this message?';
      $scope.REMOVE_ARIA_LABEL_MESSAGE = 'Confirm Delete Dialog';
      $scope.CONFIRM_OK_MESSAGE = 'Delete Message';
      $scope.CONFIRM_CANCEL_MESSAGE = 'Cancel';


      $scope.deleteMessage = function(messageDeleted){
        var msg = messageDeleted;
        if(msg.userChatGroup.user.id == $rootScope.user.id){
          messageService.deleteMessage(msg.id, {
            callbackHandler: function(result){
              $rootScope.showSimpleToast('Message deleted successfully!');

              //SETs the message notificationtype - when the websocket broker delivers the messages to the destinations the listener method will deal with each type differently;
              msg.notificationType = 'DELETED_MESSAGE_NOTIFICATION';
              ("MESSAGE DELETED!: " , msg);
              //sends the message to the websocket broker
              ChatWebsocketService.send(msg);
            },
            errorHandler: function(message, exception){
              var toast = $mdToast.simple()
                .content('ERROR: Cannot delete message!')
                .action('OK')
                .highlightAction(true)
                .position('top right');
              $mdToast.show(toast);
            }
          });
        }
      };

      // confirm delete the user´s messages
      $scope.showConfirmDeleteMessage = function(ev, message) {
        // Appending dialog to document.body to cover sidenav in docs app
        var confirm = $mdDialog.confirm()
          .title($scope.REMOVE_MESSAGE)
          .content($scope.REMOVE_CONFIRM_CONTENT_MESSAGE)
          .ariaLabel($scope.REMOVE_ARIA_LABEL_MESSAGE)
          .targetEvent(ev)
          .ok($scope.CONFIRM_OK_MESSAGE)
          .cancel($scope.CONFIRM_CANCEL_MESSAGE);
        $mdDialog.show(confirm).then(function() {
            $scope.deleteMessage(message);
        });
      };


      // confirm delete the user´s messages
      $scope.showConfirmDeleteChatGroup = function(ev) {
        // Appending dialog to document.body to cover sidenav in docs app

        if($scope.model.chatGroup != null && $scope.model.chatGroup.id != null){
            var confirm = $mdDialog.confirm()
              .title('Delete Group')
              .content('Are you sure you want to dele this Chat Group?')
              .ariaLabel('Delete Group')
              .targetEvent(ev)
              .ok('Delete Group')
              .cancel('Cancel');
            $mdDialog.show(confirm).then(function() {
                $scope.deleteChatGroup();
            });
          }
      };

      $scope.deleteChatGroup = function(){
        chatGroupService.deleteChatGroup($scope.model.chatGroup.id, {
          callbackHandler: function(result){
            //TODO MSG
            $rootScope.showSimpleToast('GROUP HAS BEEN DELETED SUCCESSFULLY!');

            //Notifies eveery user that this group has been deleted
            $scope.model.chatGroup.notificationType = 'DELETE_GROUP_NOTIFICATION';
            ChatGroupWebsocketService.send($scope.model.chatGroup);

            $location.path('/');
          },
          errorHandler: function(message, exception){
            $mdToast.showSimple(message);
            $rootScope.showSimpleToast('ERROR DELETING GROUP!');

            $location.path('/');
          }
        });

      }


      $scope.showManageUserToGroupHandler = function(event, chatGroupId){
          $mdDialog.show({
            controller: 'ChatGroupManageUsersModalController',
            templateUrl: 'app/views/chat-group-manage-user-modal.html',
            targetEvent: event,
            bindToController: false,
            locals: {
                chatGroupId: chatGroupId
            }
        }).finally(function(){
          $scope.loadUserChatGroup();
        });
      }

      $scope.editChatGroupName = function(){
        chatGroupService.editChatGroup($scope.model.chatGroup, {
          callbackHandler:function(result){
            $scope.model.chatGroup = result;
            $scope.model.editChatGroupName = false;
            $scope.$apply();
          },
          errorHandler: function(message, exception){
            $mdToast.simple(message);
            ('ERROR', exception);
          }
        });
      }


      $scope.validateGroupName = function(){
        chatGroupService.verifyChatGroupNameIsUsed($scope.model.chatGroup.groupName, {
          callbackHandler: function(result) {
            // ("result", result);
              if(result != null){
                  $scope.model.formEditGroup.groupname.$setValidity('duplicateValue', false);
              }
          },
          errorHandler: function(message, exception) {
            $mdToast.showSimple(message);
            ('ERROR', message, exception);
          }
        });
      };

      $scope.cancelEditGroupName = function(){
        $scope.model.editChatGroupName = false;
      }


      $scope.showUserInfo = function(event, userSelected) {
        $mdDialog.show({
          controller: 'UserInfoModalController',
          templateUrl: 'app/views/user-info-modal.html',
          targetEvent: event,
          bindToController: false,
          locals: {
              user: userSelected
          }
      })
      };

});
