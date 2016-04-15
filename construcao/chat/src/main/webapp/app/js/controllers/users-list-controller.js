desafioChat.controller( 'UsersListController' , function($scope, $rootScope, $location, $mdDialog, $injector, $importService, $mdToast) {

  /**
   * Serviços importados do DWR
   */
  $importService("userService");

  $scope.model = {
    selectedUser: new User(),
    usersList: [],
    filter: "",
    query: {
            order: 'name',
            limit: 5,
            page: 1,
            request: {}
    },
    options: {
            rowSelection: true,
            multiSelect: false,
            autoSelect: true,
            decapitate: false,
            largeEditDialog: false,
            boundaryLinks: false,
            limitSelect: true,
            pageSelect: true
          }

  };


  $rootScope.showSideNav = false;

  $scope.listUsersByFilterHandler = function() {
    console.log('listUsersByFilterHandler:', $scope.model);

    userService.listUsersByFilter($scope.model.filter, {
      callbackHandler: function(result) {
        console.log("LIST USERS BY FILTER", result);
          $scope.model.usersList = result;
          $scope.$apply();

      },
      errorHandler: function(message, exception) {
        $mdToast.showSimple(message);
        console.log('ERROR',  exception);
      }

    });
  };

  $scope.listUsersByFilterHandler();

  $scope.editUserHandler = function(id){
    $location.path('/edit-user/' + id);
  }

  $scope.activateUser = function(id){
    userService.activateUser(id,{
      callbackHandler: function(result) {
          console.log("activateUser", result);
          $rootScope.showSimpleToast("User has been activated!");
          $scope.listUsersByFilterHandler();
      },
      errorHandler: function(message, exception) {
        $mdToast.showSimple(message);
        console.log('ERROR', message, exception);
      }
    });
  }

  $scope.deactivateUser = function(id){
    userService.deactivateUser(id,{
      callbackHandler: function(result) {
        console.log("deactivateUser", result);
        $rootScope.showSimpleToast("User has been deactivated!");
        $scope.listUsersByFilterHandler();
      },
      errorHandler: function(message, exception) {
        $mdToast.showSimple(message);
        console.log('ERROR', message, exception);
      }
    });
  }

  $scope.openUserEditHandler = function($event, user) {
    $mdDialog.show({
      controller: 'EditUserModalController',
      templateUrl: 'app/views/user-modal-form.html',
      targetEvent: event,
      bindToController: false,
      locals:{
        userId: user.id
      }
    }).finally(function(){
        $scope.listUsersByFilterHandler();
    });
  }

  $scope.openCreateUserHandler = function($event) {
    $mdDialog.show({
      controller: 'CreateUserEditModalController',
      templateUrl: 'app/views/user-modal-form.html',
      targetEvent: event,
      bindToController: false
    }).finally(function(){
        $scope.listUsersByFilterHandler();
    });
  }


});
