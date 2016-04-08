desafioChat.controller('UsersController', function($scope, $rootScope, $location, $mdDialog, $injector, $importService, $mdToast) {

  /**
   * Serviços importados do DWR
   */
  $importService("userService");

  $scope.model = {
    selectedUser: new User(),
    usersList: [],
    filter: '',
    query: {
            order: 'name',
            limit: 5,
            page: 1
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
    },
    limitOptions : [5, 10, 15]

  };


  $rootScope.showSideNav = false;

  $scope.listUsersByFilterHandler = function() {
    console.log('listUsersByFilterHandler:', $scope.model);

    userService.listUsersByFilter($scope.model.filter, {
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

  $scope.listUsersByFilterHandler();

  $scope.editUserHandler = function(id){
    $location.path('/edit-user/' + id);
  }

  $scope.activateUser = function(id){
    userService.activateUser(id,{
      callbackHandler: function(result) {
          console.log("activateUser", result);
          $scope.showSimpleToast("User has been activated!");
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
        $scope.showSimpleToast("User has been deactivated!");
        $scope.listUsersByFilterHandler();
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

  $scope.openUserEditHandler = function($event, user) {
    $mdDialog.show({
      controller: 'EditUserModalController',
      templateUrl: 'app/views/user-modal-form.html',
      targetEvent: event,
      bindToController: false,
      locals:{
        userId: user.id
      }
    });
  }

  $scope.openCreateUserHandler = function($event) {
    $mdDialog.show({
      controller: 'CreateUserEditModalController',
      templateUrl: 'app/views/user-modal-form.html',
      targetEvent: event,
      bindToController: false
    });
  }


});
