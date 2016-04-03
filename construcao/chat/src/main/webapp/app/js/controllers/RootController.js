'user strict';

desafioChat.controller('RootController', 
    function($scope, $location, ChatService, $mdBottomSheet, $mdSidenav, $mdDialog, $mdToast, $importService) {

  /**
  * DWR IMPORT SERVICES
  */
  $importService('chatGroupService');
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
    showSideNav: true,
    messages : [],
    maxMessages: 100,
    message : new Message()

  };



  $scope.loadChatGroups = function(){
    $scope.model.user = null;
    chatGroupService.findChatGroups($scope.model.user, {
      callbackHandler: function(result){
        $scope.model.chatGroupsList = result;
        console.log('CHATGROUPLIST:', $scope.model.chatGroupsList  );
        $scope.$apply();
      },
      erroHandler: function(message, exception){
        $mdToast.simple(message, exception);
      }
    });
  };

  $scope.loadChatGroups();


  // sends Message through chatservice websocket
  $scope.addMessage = function(){
    console.log('ADDMESSAGE!', $scope.model.message);
      ChatService.send($scope.model.message);

      $scope.model.message = new Message();
  }



  // Receives message from websocket service
  ChatService.receive().then(null, null, function(message) {
    console.log('MESSAGES RECEIVED', message )
    $scope.model.messages.push(message);
    console.log('MESSAGES RECEIVED FROM BROKER', $scope.model.messages )
  });

  $scope.openMenu = function($mdOpenMenu, ev) {
    originatorEv = ev;
    $mdOpenMenu(ev);
  };

  $scope.sideNavVisible = function(show) {
    $scope.showSideNav = show;
  };

  $scope.openAddUserToGroupHandler = function() {
    $location.path('/add-group');
  }

  $scope.openEditGroupHandler = function() {
    $location.path('/edit-group');
  }

  $scope.openDeleteGroupHandler = function() {
    $location.path('/delete-group');
  }

  $scope.openUsersHandler = function() {
    $scope.sideNavVisible(false);
    $location.path('/users');
  }

  $scope.openChatHanlder = function() {
    $scope.sideNavVisible(true);
    $location.path('/');
  }





  $scope.showConfirmDelete = function(ev) {
    // Appending dialog to document.body to cover sidenav in docs app
    var confirm = $mdDialog.confirm()
      .title($scope.REMOVE_MESSAGE)
      .content($scope.REMOVE_CONFIRM_CONTENT_MESSAGE)
      .ariaLabel($scope.REMOVE_ARIA_LABEL_MESSAGE)
      .targetEvent(ev)
      .ok($scope.CONFIRM_OK_MESSAGE)
      .cancel($scope.CONFIRM_CANCEL_MESSAGE);
    $mdDialog.show(confirm).then(function() {
      var toast = $mdToast.simple()
        .content('SUCCESS: Group deleted!')
        .action('OK')
        .highlightAction(true)
        .position('top right');
      $mdToast.show(toast);
    });
  };




});


desafioChat.config(function($mdIconProvider) {
  $mdIconProvider
  // linking to https://github.com/google/material-design-icons/tree/master/sprites/svg-sprite
  //
    .iconSet('action', 'https://raw.githubusercontent.com/google/material-design-icons/master/sprites/svg-sprite/svg-sprite-action.svg', 24)
    .iconSet('alert', 'https://raw.githubusercontent.com/google/material-design-icons/master/sprites/svg-sprite/svg-sprite-alert.svg', 24)
    .iconSet('av', 'https://raw.githubusercontent.com/google/material-design-icons/master/sprites/svg-sprite/svg-sprite-av.svg', 24)
    .iconSet('communication', 'https://raw.githubusercontent.com/google/material-design-icons/master/sprites/svg-sprite/svg-sprite-communication.svg', 24)
    .iconSet('content', 'https://raw.githubusercontent.com/google/material-design-icons/master/sprites/svg-sprite/svg-sprite-content.svg', 24)
    .iconSet('device', 'https://raw.githubusercontent.com/google/material-design-icons/master/sprites/svg-sprite/svg-sprite-device.svg', 24)
    .iconSet('editor', 'https://raw.githubusercontent.com/google/material-design-icons/master/sprites/svg-sprite/svg-sprite-editor.svg', 24)
    .iconSet('file', 'https://raw.githubusercontent.com/google/material-design-icons/master/sprites/svg-sprite/svg-sprite-file.svg', 24)
    .iconSet('hardware', 'https://raw.githubusercontent.com/google/material-design-icons/master/sprites/svg-sprite/svg-sprite-hardware.svg', 24)
    .iconSet('image', 'https://raw.githubusercontent.com/google/material-design-icons/master/sprites/svg-sprite/svg-sprite-image.svg', 24)
    .iconSet('maps', 'https://raw.githubusercontent.com/google/material-design-icons/master/sprites/svg-sprite/svg-sprite-maps.svg', 24)
    .iconSet('navigation', 'https://raw.githubusercontent.com/google/material-design-icons/master/sprites/svg-sprite/svg-sprite-navigation.svg', 24)
    .iconSet('notification', 'https://raw.githubusercontent.com/google/material-design-icons/master/sprites/svg-sprite/svg-sprite-notification.svg', 24)
    .iconSet('social', 'https://raw.githubusercontent.com/google/material-design-icons/master/sprites/svg-sprite/svg-sprite-social.svg', 24)
    .iconSet('toggle', 'https://raw.githubusercontent.com/google/material-design-icons/master/sprites/svg-sprite/svg-sprite-toggle.svg', 24)

  // Illustrated user icons used in the docs https://material.angularjs.org/latest/#/demo/material.components.gridList
  .iconSet('avatars', 'https://raw.githubusercontent.com/angular/material/master/docs/app/icons/avatar-icons.svg', 24)
    .defaultIconSet('https://raw.githubusercontent.com/google/material-design-icons/master/sprites/svg-sprite/svg-sprite-action.svg', 24);
});
