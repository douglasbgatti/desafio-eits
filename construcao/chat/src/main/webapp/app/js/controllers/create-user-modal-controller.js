desafioChat.controller('CreateUserEditModalController', function($scope, $rootScope, $importService, $location, $mdDialog, $mdToast) {

  /**
   * Serviços importados do DWR
   */
  $importService("userService");


  $scope.model = {
    user: new User(),
    retypedPassword: '',
    usersList: [],
    pageHeader: 'Create User',
    creatingUser: false,
    form: null

  }

  function init(){
    $scope.model.user.enabled = true;
    $scope.model.creatingUser= false;
    $scope.model.user.role = 'USER';
    $rootScope.showSideNav = false;
  };

  init();


  $scope.insertUser = function() {
      $scope.model.creatingUser= true;

      userService.insertUser($scope.model.user, {
        callbackHandler: function(result) {
          console.log('SUCCESS:', result);

          $scope.hide();
          $scope.showSimpleToast('The user has been created successfully and should receive an e-mail confirming the user created shortly!');

          // $location.path('/users');
        },
        errorHandler: function(message, exception) {
          console.log('ERROR:', message);
          $mdToast.simple(exception);
          $location.path('/');
        }
      });
  };

  $scope.validateEmail = function(){
    userService.findUserDetailsByEmail($scope.model.user.email, {
      callbackHandler: function(result) {
        // console.log("result", result);
          if(result != null){
              $scope.model.form.email.$setValidity('duplicateEmail', false);
          }else{
            $scope.model.form.email.$setValidity('duplicateEmail', true);
          }
      },
      errorHandler: function(message, exception) {
        $mdToast.showSimple(message);
        console.log('ERROR', message, exception);
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
  };

  /*
   * Fecha a dialog
   * */
  $scope.hide = function() {
    $mdDialog.hide();
  };


});
