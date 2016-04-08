desafioChat.controller('EditGroupController', function($scope, $q, $timeout, $importService, $mdDialog, $mdToast, $routeParams) {
  $importService('userService');
  $importService('chatGroupService');
  /*-------------------------------------------------------------------
   * 		 				 	ATTRIBUTES
   *-------------------------------------------------------------------*/
  $scope.model = {
    userChatGroup: new UserChatGroup(),
    window:{
      name:'Edit - '
    },
    filterSelected: true,
    usersSelected: [],
    allContacts: null,
    selectedItem: null
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
  $scope.model.chatGroupId = $routeParams.id;

  chatGroupService.getChatGroupAndUsersList($scope.model.chatGroupId,{
    callbackHandler: function(result){
      $scope.model.chatGroup = result;
      $scope.model.window.name = 'Edit - ' + $scope.model.chatGroup.groupName;
      $scope.model.usersSelected = $scope.model.chatGroup.userList;
      console.log("EditChatGroup:" , $scope.model);
      $scope.$apply();
    },
    errorHandler: function(message, exception){
      $mdToast.showSimple(message);
      console.log('ERROR', message, exception);
    }
  });
};


$scope.loadAllUsers = function() {
  userService.listUsersByRole('USER', {
    callbackHandler: function(result) {
      console.log("result", result);
        $scope.model.usersList = result;
        $scope.$apply();
    },
    errorHandler: function(message, exception) {
      $mdToast.showSimple(message);
      console.log('ERROR', message, exception);
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
      // console.log("result", result);
        if(result != null){
            $scope.form.groupName.$setValidity(duplicateValue, false);
        }
    },
    errorHandler: function(message, exception) {
      $mdToast.showSimple(message);
      console.log('ERROR', message, exception);
    }
  });
};

$scope.editChatGroup = function(){
  // var userChatGroup;
  //  $scope.model.chatGroup.userGroupList = []
  // angular.forEach($scope.model.usersSelected, function(user, key) {
  //   userChatGroup = new UserChatGroup();
  //   userChatGroup.user = user;
  //   $scope.model.chatGroup.userGroupList.push(userChatGroup);
  //
  // });
  //
  //  chatGroupService.insertChatGroup($scope.model.chatGroup, {
  //    callbackHandler: function(result) {
  //      console.log("result", result);
  //        $scope.model.chatGroup = result;
  //        $scope.showSimpleToast('Group has been created successfully!');
  //        $location.path('/');
  //        $scope.$apply();
  //    },
  //    errorHandler: function(message, exception) {
  //      $mdToast.showSimple(message);
  //      console.log('ERROR', message, exception);
  //    }
  //  });

};

$scope.removeUserFromChatGroup = function(user){
  userChatGroupService.removeUserFromUserChatGroup(user.id, userChatGroup.id);
}

$scope.addUserToChatGroup = function(user){
  var userChatGroup = new UserChatGroup();
  userChatGroup.user = user;
  userChatGroup.chatGroup = $scope.model.chatGroup;

  userChatGroupService.addUserToChatGroup(userChatGroup, {
    callbackHandler: function(result){
      $scope.model.usersSelected.push(result);
    },
    errorHandler: function(message, exception){
      $mdToast.showSimple(message);
      console.log('ERROR', message, exception);
    }

  });

}



  $scope.showSimpleToast = function(content) {
    $mdToast.show(
      $mdToast.simple()
      .textContent(content)
      .position('top right')
      .hideDelay(5000)
    );
  };


});
