'use strict';

var desafioChat = angular.module('desafioChat', ['ngRoute', 'eits-ng', 'ngMessages','ngMaterial', 'ngMdIcons', 'md.data.table']);

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
    .when('/add-group',{
      templateUrl: 'app/views/group.html',
      controller: 'CreateGroupController'
    })
    .when('/edit-group',{
      templateUrl: 'app/views/group.html',
      controller: 'EditGroupController'
    })
    .when('/users',{
      templateUrl: 'app/views/usersList.html',
      controller: 'UsersController'
    })
    .when('/create-user',{
      templateUrl: 'app/views/user.html',
      controller: 'CreateUserController'
    })
    .when('/edit-user/:id',{
      templateUrl: 'app/views/user.html',
      controller: 'EditUserController'
    })

});
