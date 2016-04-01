desafioChat.controller('UserInfoModalController', function($scope, $importService, $mdDialog, user) {



  $scope.model = {
    user: {}
  }

  $scope.model.user = user;

  /*
   * Fecha a dialog
   * */
  $scope.hide = function() {
    $mdDialog.hide();
  };

});
