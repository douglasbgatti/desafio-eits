desafioChat.controller('UserChatGroupController', function( $scope, $rootScope, $location, $importService, $routeParams, $mdToast, $mdDialog, ChatService, UserAuthenticatedService) {

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
    formEditGroup: null
  };

  $rootScope.showSideNav = true;


  $scope.loadUsersByChatGroup = function(){
    userChatGroupService.listUserChatGroupsByChatGroupId($scope.model.chatGroupId, {
      callbackHandler: function(result){
        $scope.model.userChatGroupList = result;
        $scope.$apply();
      },
      errorHandler: function(message, exception){
        $mdToast.simple(message, exception);
        console.log('ERROR', message, exception);
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

          ChatService.initialize($scope.model.chatGroupId);
          $scope.$apply();
        },
        errorHandler: function(message, exception){
          $mdToast.simple(message, exception);
          console.log('ERROR', message, exception);
        }
      });
    }
  };

  //INIT CHAT GROUPS AND MSG
  $scope.loadUserChatGroup();



  $scope.loadMessages = function(){
    if($rootScope.user.id !== null &&  $scope.model.chatGroupId != null){

      messageService.listAllMessagesByChatGroupId( $scope.model.chatGroupId, {
        callbackHandler: function(result){
          var msgs = result;

          //ao receber msg seta para visualizada quando não é a msg do proprio usuario
           angular.forEach(msgs, function(msg, key){
             if((msg.userChatGroup.user.id != $rootScope.user.id) && msg.visualized != true){
               $scope.setMessageStatusToVisualized(msg);
               msg.visualized = true;
             }
           });

          $scope.model.messages = msgs;
          $scope.$apply();
        },
        errorHandler: function(message, exception){
          $mdToast.simple(message, exception);
          console.log('ERROR', message, exception);
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

            ChatService.send($scope.model.message);

            $scope.model.message = new Message();
            $scope.$apply();
          },
          errorHandler: function(message, exception){
            $mdToast.simple(message);
            console.log('ERROR', exception);
          }
        });


    };



    // Receives message from websocket service
    ChatService.receive().then(null, null, function(message) {
      console.log('MESSAGES RECEIVED' );
      var msg = angular.fromJson(message);
      msg = angular.fromJson(msg.payload);
      msg.userChatGroup = angular.fromJson(msg.userChatGroup);

      if((msg.userChatGroup.user.id != $rootScope.user.id) && msg.visualized != true){
        $scope.setMessageStatusToVisualized(msg);
        msg.visualized = true;
      }else{
        msg.visualized = false;
      }

      $scope.model.messages.push(msg);
    });

    /**
    * Assim que o websocket entregar uma mensagem sera setado como visualizado;
    *
    **/
    $scope.setMessageStatusToVisualized = function(message){
      if(message.userChatGroup.user.id != $rootScope.user.id){
        messageService.setMessageStatusToVisualized(message.id, {
          callbackHandler: function(result){

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


      $scope.deleteMessage = function(message){
        if(message.userChatGroup.user.id == $rootScope.user.id){
          messageService.deleteMessage(message.id, {
            callbackHandler: function(result){
              var toast = $mdToast.simple()
                .content('SUCCESS: Message deleted!')
                .action('OK')
                .highlightAction(true)
                .position('top right');
              $mdToast.show(toast);
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
            $mdToast.showSimple("GROUP HAS BEEN DELETED");
              $location.path('/');
          },
          errorHandler: function(message, exception){
            $mdToast.showSimple(message);
            console.log('ERROR DELETING GROUP',exception);
            $location.path('/');
          }
        });

      }


      $scope.showManageUserToGroupHandler = function(event, chatGroupId){
          $mdDialog.show({
            controller: 'ChatGroupManageUsersModalController',
            templateUrl: 'app/views/chat-group-add-user-modal.html',
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
            console.log('ERROR', exception);
          }
        });
      }


      $scope.validateGroupName = function(){
        chatGroupService.verifyChatGroupNameIsUsed($scope.model.chatGroup.groupName, {
          callbackHandler: function(result) {
            // console.log("result", result);
              if(result != null){
                  $scope.model.formEditGroup.groupname.$setValidity('duplicateValue', false);
              }
          },
          errorHandler: function(message, exception) {
            $mdToast.showSimple(message);
            console.log('ERROR', message, exception);
          }
        });
      };

      $scope.cancelEditGroupName = function(){
        $scope.model.editChatGroupName = false;
      }

});
