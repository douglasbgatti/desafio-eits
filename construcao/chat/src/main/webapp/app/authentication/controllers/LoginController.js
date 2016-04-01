authentication.controller('LoginController', function($scope, $http, $mdToast, $window) {

  /*-------------------------------------------------------------------
   * 		 				 	ATTRIBUTES
   *-------------------------------------------------------------------*/
  /**
   *
   */
  $scope.model = {
    credentials: {
      username: '',
      password: ''
    }
  };

  /*-------------------------------------------------------------------
   * 		 				 	  HANDLERS
   *-------------------------------------------------------------------*/
  /**
   *
   */
  $scope.loginHandler = function() {

      var config = {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        }
      };
      // $http.defaults.xsrfHeaderName = 'X-CSRF-TOKEN';
      // $http.defaults.xsrfCookieName = 'CSRF-TOKEN';


      $http.post("./authenticate", $.param($scope.model.credentials), config)
        .success(function(data, status, headers, config) {
          console.log('authentication data', data , status, headers, config);
          $window.location.href = "./";
        })
        .error(function(data, status, headers, config) {
          // $mdToast.showSimple((data && data.message) ? data.message : data);
           $scope.form.$setValidity("incorrectCredentials", false);
           $scope.model.credentials.username = '';
           $scope.model.credentials.password = '';

        });

  }


  //   var authenticate = function(credentials, callback) {
  //
  //     var headers = credentials ? {
  //       authorization: "Basic " + btoa(credentials.username + ":" + credentials.password)
  //     } : {};
  //
  //     $http.get('user', {
  //       headers: headers
  //     }).success(function(data) {
  //       if (data.name) {
  //         $rootScope.authenticated = true;
  //       } else {
  //         $rootScope.authenticated = false;
  //       }
  //       callback && callback();
  //     }).error(function() {
  //       $rootScope.authenticated = false;
  //       callback && callback();
  //     });
  //
  //   }
  //
  //   authenticate();
  //
  //
  //   $scope.login = function() {
  //     authenticate($scope.credentials, function() {
  //       if ($rootScope.authenticated) {
  //         $location.path("/");
  //         $scope.error = false;
  //       } else {
  //         $location.path("/login");
  //         $scope.error = true;
  //       }
  //     });
  // };

});
