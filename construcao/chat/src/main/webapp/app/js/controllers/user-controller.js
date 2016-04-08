desafioChat.controller('UserController', function($scope, $importService) {

  /**
   * Serviços importados do DWR
   */
  $importService("userService");

$scope.model = {
  user: new User(),
  retypedPassword:'',
  usersList:[]
  }



 $scope.insertUser = function(){

   userService.insertUser( $scope.model.user,{
   callbackHandler: function(result){
     console.log('SUCCESS:', result);
   },
   errorHandler: function(message, exception){
     console.log('ERROR:', message);
   }
 });
 };
 
 $scope.getDummyUser = function(){
   userService.getDummyUser({
     callbackHandler: function(result){
       console.log('SUCCESS:', result);
     },
     errorHandler: function(message, exception){
       console.log('ERROR:', message);
     }
   });
 };

 $scope.getDummyUser();
});
