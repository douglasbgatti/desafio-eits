'user strict';

desafioChat.controller('RootController',
    function( $scope, $rootScope, $injector, $location, UserAuthenticatedService, $mdBottomSheet, $mdSidenav, $mdDialog, $mdToast, $importService, ChatGroupWebsocketService) {

  /**
  * DWR IMPORT SERVICES
  */
  $importService('userChatGroupService');
  $importService('userService');
  /**
  * SETTING MESSAGES
  */
  $scope.REMOVE_MESSAGE = 'Remove Group';
  $scope.REMOVE_CONFIRM_CONTENT_MESSAGE = 'Are you sure you want to delete this group? This will remove all messages and users from the group.';
  $scope.REMOVE_ARIA_LABEL_MESSAGE = 'Confirm Delete Dialog';
  $scope.CONFIRM_OK_MESSAGE = 'Delete Group';
  $scope.CONFIRM_CANCEL_MESSAGE = 'Cancel';

  $scope.model ={
    chatGroupsList: [],
    user: new User(),
    messages : [],
    maxMessages: 100,
    message : new Message(),
    isFabMenuOpen: false,
    isSearchingChatGroup: false,
    searchText: ''

  };


$rootScope.showSideNav = true;

  UserAuthenticatedService.getAuthenticatedUser().then(function(result){
    $scope.model.user = result;
    console.log($scope.model.user);
  },
  function(error){
    $location.path('logout');
  }
);


  $scope.loadChatGroups = function(){
    $scope.model.user = null;
    userChatGroupService.listUserChatGroupsByUser( {
      callbackHandler: function(result){
        $scope.model.userChatGroupsList = result;
        console.log('CHATGROUPLIST:', $scope.model.userChatGroupsList  );
        $scope.$apply();
      },
      erroHandler: function(message, exception){
        $mdToast.simple(message, exception);
      }
    });
  };

  $scope.loadChatGroups();


//initialized the websocket connection to listen when a new group is created
  ChatGroupWebsocketService.initialize();

  // Receives message from websocket service
  ChatGroupWebsocketService.receive().then(null, null, function(chatGroup) {
      $scope.loadChatGroups();
      var group = angular.fromJson(chatGroup);
      group = angular.fromJson(group.payload);
      console.log(group);

      console.log($location.path());
      if(group.notificationType === 'DELETE_GROUP_NOTIFICATION'){
        $rootScope.showSimpleToast("Chat Group [" + group.groupName + "] has been deleted!" );

        var url = $location.path();
        var array = url.split('/');

        console.log(array);
        if(array.length > 0 &&  array[1] == 'group' && parseInt(array[2]) == group.id){
          $location.path('/');
        }
      }
  });



  $scope.openMenu = function($mdOpenMenu, ev) {
    originatorEv = ev;
    $mdOpenMenu(ev);
  };

  $scope.sideNavVisible = function(show) {
    $scope.showSideNav = show;
  };

  $scope.openChatGroupCreateHandler = function($event) {
      $mdDialog.show({
        controller: 'ChatGroupCreateModalController',
        templateUrl: 'app/views/chat-group-create-modal.html',
        targetEvent: event,
        bindToController: false,
    }).finally(function(){
        $scope.loadChatGroups();
    });
  };

  $scope.listChatGroupsByFilter = function(){
    userChatGroupService.listUserChatGroupsByUserAndFilter( $scope.model.searchText,  {
      callbackHandler: function(result){
        $scope.model.userChatGroupsList = result;
        console.log('GROUPS SEARCH:', $scope.model.userChatGroupsList  );
        $scope.$apply();
      },
      erroHandler: function(message, exception){
        $mdToast.simple(message, exception);
      }
    });
  };


  $scope.closeSearchInputAndClearText = function(){
    $scope.model.isSearchingChatGroup = false;
    $scope.model.searchText = '';

    $scope.listChatGroupsByFilter();
  }

  $scope.openSearchChatGroup = function(){
    $scope.model.isSearchingChatGroup = true;
  }

  $scope.closeSearchInput = function(){
    $scope.model.isSearchingChatGroup = false;
  }

  $scope.openUsersHandler = function() {
    $location.path('/users-list');
  }

  $scope.openChatHandler = function(groupId) {
    $location.path('/group/' + groupId);
  }

  $scope.openIndexHandler = function(){
    $rootScope.toggleSideNav();
    $location.path('/');
  }

});
