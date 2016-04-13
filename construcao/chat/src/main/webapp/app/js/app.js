'use strict';

var desafioChat = angular.module('desafioChat', ['ngRoute', 'eits-ng', 'ngMessages','ngMaterial', 'ngMdIcons', 'md.data.table', 'luegg.directives']);

desafioChat.constant('appInfo', {
  name: 'DesafioChat',
  versionCode: '0.1',
  versionName: 'Silver-Titanium-Cat '
});

desafioChat.constant('roleTypes', {
  USER: 'User',
  ADMINISTRATOR: 'Administrator'
});

desafioChat.config(function($routeProvider, $httpProvider, $importServiceProvider){
  console.log('config');

  // para utilizar o eits-dwr-broker - chama-se o importService('nomeServico');
  $importServiceProvider.setBrokerURL('/chat/dwr/interface');

  $routeProvider
    .when('/', {
       templateUrl: 'app/views/main.html'
    })
    .when('/group/:id', {
      templateUrl: 'app/views/user-chat-group.html',
      controller: 'UserChatGroupController'
    })
    .when('/users-list',{
      templateUrl: 'app/views/users-list.html',
      controller: 'UsersListController'
    })

});

desafioChat.config(function($mdThemingProvider) {
  var customBlueMap = $mdThemingProvider.extendPalette('light-blue', {
    'contrastDefaultColor': 'light',
    'contrastDarkColors': ['50'],
    '50': 'ffffff'
  });
  $mdThemingProvider.definePalette('customBlue', customBlueMap);
  $mdThemingProvider.theme('default')
    .primaryPalette('customBlue', {
      'default': '500',
      'hue-1': '50'
    })
    .accentPalette('pink');
  $mdThemingProvider.theme('input', 'default')
    .primaryPalette('grey')
});


desafioChat.run(function($rootScope, $location,$mdSidenav, UserAuthenticatedService){
    $rootScope.user = {};

    $rootScope.showSideNav = true;

    $rootScope.toggleSideNav = function(){
       $mdSidenav('left').toggle();
       $rootScope.showSideNav = !$rootScope.showSideNav;
    };

  // Pegar o usuario Logado no Sistema
    UserAuthenticatedService.getAuthenticatedUser()
      .then(function(result){
        $rootScope.user = result;
        console.log("UserAuthenticatedService ROOTSCOPE:", $rootScope.user);
      },
      function(error){
        $location.path('/logout');
      }
  );

});
