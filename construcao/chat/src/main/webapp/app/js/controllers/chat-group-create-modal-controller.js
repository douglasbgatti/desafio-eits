desafioChat.controller('ChatGroupCreateModalController', function($scope, $rootScope, $location, $importService, $mdDialog, $mdToast, ChatGroupWebsocketService) {


$importService('userService');
$importService('chatGroupService');

  /*-------------------------------------------------------------------
   * 		 				 	ATTRIBUTES
   *-------------------------------------------------------------------*/
  $scope.model = {
    chatGroup: new ChatGroup(),
    window:{
      name:'Add New Group'
    },
    filterSelected: true,
    usersSelected: [],
    usersList: [],
    selectedUser: null
  };


// $scope.model.allContacts = $scope.loadContacts();


/**
 * Search for contacts.
 */
function querySearch(query) {
  var results = query ?
    $scope.model.usersList.filter(createFilterFor(query)) : [];
  return results;
}

/**
 * Create filter function for a query stringchatGroupService.
 */
function createFilterFor(query) {
  var lowercaseQuery = angular.lowercase(query);

  return function filterFn(user) {
    return (user.name.indexOf(lowercaseQuery) != -1);;
  };
}

/**
* lists the users in the db - lists only USER Role because ADMINISTRATOS have access to every group
*/
$scope.loadAllUsers = function() {
  userService.listUsersByRole('USER', {
    callbackHandler: function(result) {
      // ("result", result);
        $scope.model.usersList = result;
        $scope.$apply();
    },
    errorHandler: function(message, exception) {
      $mdToast.showSimple(message);
      // ('ERROR', message, exception);
    }
  });
};

$scope.loadAllUsers();

/**
* User info modal for selected user
*/
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

  $scope.removeDuplicateItemsFromUsersSelected = function(){
    var newArr = [];
    var origLen = $scope.model.usersSelected.length;
    var found, x, y;

      for (x = 0; x < origLen; x++) {
          found = undefined;
          for (y = 0; y < newArr.length; y++) {
              if ($scope.model.usersSelected[x] === newArr[y]) {
                  found = true;
                  break;
              }
          }
          if (!found) {
              newArr.push($scope.model.usersSelected[x]);
          }
      }
      $scope.model.usersSelected = newArr;
  }

$scope.insertChatGroup = function(){
  $scope.removeDuplicateItemsFromUsersSelected();
  var userChatGroup;
   $scope.model.chatGroup.userGroupList = []
  angular.forEach($scope.model.usersSelected, function(user, key) {
    userChatGroup = new UserChatGroup();
    userChatGroup.user = user;
    $scope.model.chatGroup.userGroupList.push(userChatGroup);

  });
     chatGroupService.insertChatGroup($scope.model.chatGroup, {
       callbackHandler: function(result) {
         ("result", result);
           $scope.model.chatGroup = result;
           var group = {};
           group.id = $scope.model.chatGroup.id;
           group.groupName = $scope.model.chatGroup.groupName;

           //Sends a notification to every user that a group has been created
           group.notificationType = 'NEW_GROUP_NOTIFICATION';
           ChatGroupWebsocketService.send(group);

           $rootScope.showSimpleToast('Group has been created successfully!');
           $scope.hide();
       },
       errorHandler: function(message, exception) {
         $mdToast.showSimple(message);
         ('ERROR', message, exception);
       }
     });

}

  /*
   * Fecha a dialog
   * */
  $scope.hide = function() {
    $mdDialog.hide();
  };




});
