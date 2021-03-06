desafioChat.controller('EditUserModalController', function($scope, $rootScope, $routeParams, $importService, $location,  $mdDialog, $mdToast, userId) {

  /**
   * Serviços importados do DWR
   */
  $importService("userService");

  $scope.model = {
    user: new User(),
    originalEmail: '',
    retypedPassword: '',
    userId: null,
    window:{
      name: 'Edit User'
    },
    userId: '',
    creatingUser: false,
    form: null
  }


  $scope.getUser = function() {
    $scope.model.userId = userId;
    $scope.model.creatingUser = true;
    ("GETUSER - ID:", $scope.model.userId);
    if ($scope.model.userId != null) {
      userService.getUser($scope.model.userId, {
        callbackHandler: function(result) {
          $scope.model.user = result;
          $scope.model.originalEmail = $scope.model.user.email;
          $scope.model.showForm = true;
          ('user:', $scope.model);
          $scope.model.creatingUser = false;
          $scope.$apply();
        },
        errorHandler: function(message, exception) {
          ('ERROR:', message);
        }
      });
    } else {
      $rootScope.showSimpleToast('User Id doesnt exist!');
    }
  }

  $scope.getUser();


  $scope.editUser = function() {
    $scope.model.creatingUser = true;
    if($scope.model.user.email != '' && $scope.model.user.email != null){
      $scope.model.form.$valid = true;
        if ($scope.model.userId != null) {
          userService.alterUser($scope.model.user, {
            callbackHandler: function(result) {
              ('SUCCESS:', result);

              $rootScope.showSimpleToast('User has been updated successfully!');
              $scope.hide();
              $location.path('/users-list');
            },
            errorHandler: function(message, exception) {
              ('ERROR:', message, exception);
              $rootScope.showSimpleToast(message + ' ' + exception);
            }
          });
        }
    }
  };


  /*
   * Fecha a dialog
   * */
  $scope.hide = function() {
    $mdDialog.hide();
  };


  $scope.validateEmail = function(){
    userService.findUserByEmail($scope.model.user.email, {
      callbackHandler: function(result) {
          if(result != null){
              if(result.email != $scope.model.originalEmail)
                $scope.model.form.email.$setValidity('duplicateEmail', false);
              else
                $scope.model.form.email.$setValidity('duplicateEmail', true);
          }else{
            $scope.model.form.email.$setValidity('duplicateEmail', true);
          }
      },
      errorHandler: function(message, exception) {
        $mdToast.showSimple(message);
        ('ERROR', message, exception);
      }
    });
  };

});
