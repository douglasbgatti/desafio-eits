'user strict';

desafioChat.controller('RootController',
    function( $scope, $rootScope, $injector, $location, UserAuthenticatedService, $mdBottomSheet, $mdSidenav, $mdDialog, $mdToast, $importService) {

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
  /**
 * Injeta os m�todos, atributos e seus estados herdados de AbstractCRUDController.
 * @see AbstractCRUDController
 */
// $injector.invoke('AbstractCrudController', this, {
//   $scope: $scope
// });

//FROM AbstractCRUDController
// $scope.initUser();

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

  $scope.openSearchChatGroup = function(){
    $scope.model.isSearchingChatGroup = true;
  }

  $scope.closeSearchInput = function(){
    $scope.model.isSearchingChatGroup = false;
  }

  $scope.openUsersHandler = function() {
    $location.path('/users');
  }

  $scope.openChatHandler = function(groupId) {
    $location.path('/group/' + groupId);
  }

  $scope.openIndexHandler = function(){
    $rootScope.showSideNav = true;
    $rootScope.toggleSideNav();
    $location.path('/');
  }

});
