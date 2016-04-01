desafioChat.controller('EditUserController', function($scope, $routeParams, $importService, $location, $mdToast) {

  /**
   * Serviços importados do DWR
   */
  $importService("userService");

  $scope.model = {
    user: new User(),
    retypedPassword: '',
    userId: null,
    pageHeader: 'Edit User'
  }


  $scope.getUser = function() {
    $scope.model.userId = $routeParams.id;

    if ($scope.model.userId != null) {
      userService.getUser($scope.model.userId, {
        callbackHandler: function(result) {
          $scope.model.user = result;
          $scope.model.showForm = true;
          console.log('user:', $scope.model);
          $scope.$apply();
        },
        errorHandler: function(message, exception) {
          console.log('ERROR:', message);
        }
      });
    } else {
      $scope.showSimpleToast('User Id doesnt exist!');
    }
  }

  $scope.getUser();


  $scope.editUser = function() {
    console.log('USER:', $scope.model.user);
    if ($scope.model.userId != null) {
      userService.alterUser($scope.model.user, {
        callbackHandler: function(result) {
          console.log('SUCCESS:', result);

          $scope.showSimpleToast('User has been updated successfully!');
          $location.path('/users');
        },
        errorHandler: function(message, exception) {
          console.log('ERROR:', message, exception);
          $scope.showSimpleToast(message + ' ' + exception);
        }
      });
    }
  };

  $scope.getDummyUser = function() {
    userService.getDummyUser({
      callbackHandler: function(result) {
        console.log('SUCCESS:', result);
      },
      errorHandler: function(message, exception) {
        console.log('ERROR:', message);
      }
    });
  };

  $scope.showSimpleToast = function(content) {
    $mdToast.show(
      $mdToast.simple()
      .textContent(content)
      .position('top right')
      .hideDelay(5000)
    );
  }

});
