desafioChat.controller('CreateUserController', function($scope, $importService, $location, $mdToast) {

  /**
   * Serviços importados do DWR
   */
  $importService("userService");

  $scope.model = {
    user: new User(),
    retypedPassword: '',
    usersList: [],
    pageHeader: 'Create User'
  }

  function init(){
    $scope.model.user.enabled = true;
    $scope.model.user.role = 'USER';
  }

  init();


  $scope.insertUser = function() {
    userService.insertUser($scope.model.user, {
      callbackHandler: function(result) {
        console.log('SUCCESS:', result);

        $scope.showSimpleToast('The user has been created successfully and should receive an e-mail confirming the user created shortly!');
        $location.path('/users');
      },
      errorHandler: function(message, exception) {
        console.log('ERROR:', message);
      }
    });
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

  // $scope.getDummyUser();

  $scope.showSimpleToast = function(content) {
    $mdToast.show(
      $mdToast.simple()
      .textContent(content)
      .position('top right')
      .hideDelay(5000)
    );
  };

});
