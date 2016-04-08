desafioChat.service('UserAuthenticatedService', function( $q, $timeout, $importService, $mdToast, $location) {
  $importService('userService');
  var self = this;
  var deferred = $q.defer();

    self.getAuthenticatedUser = function(){
      userService.getAuthenticatedUser({
      callbackHandler: function(result){
          result.password = null;
          deferred.resolve(result);
        },
        errorHandler: function(message, exception) {
            $mdToast.showSimple(message);
            deferred.reject(message);
        }
      });

      return deferred.promise;
    };

});
