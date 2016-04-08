desafioChat.controller('CreateGroupController', function($scope, $location, $importService, $mdDialog, $mdToast) {


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

$scope.insertChatGroup = function(){
  var userChatGroup;
   $scope.model.chatGroup.userGroupList = []
  angular.forEach($scope.model.usersSelected, function(user, key) {
    userChatGroup = new UserChatGroup();
    userChatGroup.user = user;
    $scope.model.chatGroup.userGroupList.push(userChatGroup);

  });

   chatGroupService.insertChatGroup($scope.model.chatGroup, {
     callbackHandler: function(result) {
       console.log("result", result);
         $scope.model.chatGroup = result;
         $scope.showSimpleToast('Group has been created successfully!');
         $location.path('/');
         $scope.$apply();
     },
     errorHandler: function(message, exception) {
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
