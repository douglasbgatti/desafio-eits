desafioChat.controller('ChatGroupManageUsersModalController', function($scope, $location, $q, $timeout, $importService, $mdDialog, $mdToast, ChatGroupWebsocketService, chatGroupId) {
  $importService('userService');
  $importService('chatGroupService');
  /*-------------------------------------------------------------------
   * 		 				 	ATTRIBUTES
   *-------------------------------------------------------------------*/
  $scope.model = {
    userChatGroup: new UserChatGroup(),
    chatGroup: new ChatGroup(),
    window:{
      name:'Edit - '
    },
    filterSelected: true,
    usersSelected: [],
    allContacts: null,
    selectedItem: null,
    isDuplicate: false
  };


/**
 * Create filter function for a query string
 */
function createFilterFor(query) {
  var lowercaseQuery = angular.lowercase(query);

  return function filterFn(contact) {
    return (contact._lowername.indexOf(lowercaseQuery) != -1);;
  };
}

/**
 * Search for contacts.
 */
function querySearch(query) {
  var results = query ?
    $scope.model.usersList.filter(createFilterFor(query)) : [];
  return results;
}


$scope.loadChatGroup = function(){
  $scope.model.chatGroupId = chatGroupId;

  chatGroupService.getChatGroupAndUsersList($scope.model.chatGroupId,{
    callbackHandler: function(result){
      $scope.model.chatGroup = result;
      $scope.model.window.name = 'Edit - ' + $scope.model.chatGroup.groupName;
      $scope.model.usersSelected = $scope.model.chatGroup.userList;
      ("EditChatGroup:" , $scope.model);
      $scope.$apply();
    },
    errorHandler: function(message, exception){
      $mdToast.showSimple(message);
      ('ERROR', message, exception);
    }
  });
};


$scope.loadAllUsers = function() {
  userService.listUsersByRole('USER', {
    callbackHandler: function(result) {
      ("result", result);
        $scope.model.usersList = result;
        $scope.$apply();
    },
    errorHandler: function(message, exception) {
      $mdToast.showSimple(message);
      ('ERROR', message, exception);
    }
  });
};

  $scope.loadChatGroup();
  $scope.loadAllUsers();



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

$scope.validateGroupName = function(){
  chatGroupService.verifyChatGroupNameIsUsed($scope.model.chatGroup.groupName, {
    callbackHandler: function(result) {
      // ("result", result);
        if(result != null){
            $scope.form.groupName.$setValidity(duplicateValue, false);
        }
    },
    errorHandler: function(message, exception) {
      $mdToast.showSimple(message);
      ('ERROR', message, exception);
    }
  });
};

$scope.checkIsDuplicate = function(userAdded){
  userChatGroupService.getUserChatGroupByUserAndChatGroup(userAdded.id, $scope.model.chatGroup.id, {
    callbackHandler: function(result){
        if(result != null && (result.user.id == userAdded.id) ){
            $scope.model.isDuplicate = true;
            // removes user from array if it already exists in db
            angular.forEach($scope.model.usersSelected, function(user, key) {
                if(user === userAdded){
                    ("CHECK IS DUPE", user);
                    $scope.model.usersSelected.splice(key , 1);
                }
            });
        }else{
          $scope.model.isDuplicate = false;
        }
    },
    errorHandler: function(message, exception){
        $mdToast.simple(message, exception);
        ('ERROR', message, exception);
        $location.path('/');
      }
  });


}



$scope.addUserToChatGroup = function(user){
  $scope.checkIsDuplicate(user);
  if($scope.model.isDuplicate == false){
      var userChatGroup = new UserChatGroup();
      userChatGroup.user = user;
      userChatGroup.chatGroup = $scope.model.chatGroup;

      userChatGroupService.insertUserChatGroup(userChatGroup, {
        callbackHandler: function(result){
          //$scope.model.usersSelected.push(result);
          ChatGroupWebsocketService.send();

        },
        errorHandler: function(message, exception){
          $mdToast.showSimple(message);
          ('ERROR', message, exception);
        }

      });
    }
}


$scope.removeUserFromChatGroup = function(user){
  if(user.role == 'USER'){
      userChatGroupService.removeUserFromChatGroup(user.id, $scope.model.chatGroup.id, {
        callbackHandler: function(result){
          // $scope.model.usersSelected.push(result);
        },
        errorHandler: function(message, exception){
          $mdToast.showSimple(message);
          ('ERROR', message, exception);
        }

      });
  }else{
    $scope.loadChatGroup();
    $mdToast.showSimple('Cannot remove an ADMINISTRATOR from a chat group!');
  }
}


  /*
   * Fecha a dialog
   * */
  $scope.hide = function() {
    $mdDialog.hide();
  };


});
