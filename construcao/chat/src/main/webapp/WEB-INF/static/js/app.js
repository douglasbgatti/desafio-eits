'use strict';

var desafioChat = angular.module('desafioChat', ['ngRoute', 'ngMessages','ngMaterial', 'ngMdIcons', 'md.data.table']);

desafioChat.constant('appInfo', {
  name: 'DesafioChat',
  versionCode: '0.1',
  versionName: 'Silver-Titanium-Cat '
});

desafioChat.constant('roleTypes', {
  USER: 'User',
  ADMINISTRATOR: 'Administrator'
});

desafioChat.config(function($routeProvider, $httpProvider){
  console.log('config');

  $routeProvider
    .when('/', {
      templateUrl: 'static/views/main.html'
    })
    .when('/add-group',{
      templateUrl: 'static/views/group.html',
      controller: 'AddGroupController'
    })
    .when('/edit-group',{
      templateUrl: 'static/views/group.html',
      controller: 'EditGroupController'
    })
    .when('/users',{
      templateUrl: 'static/views/usersList.html',
      controller: 'UsersController'
    })

});
