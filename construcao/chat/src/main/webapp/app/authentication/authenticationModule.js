'use strict';

var authentication = angular.module('authentication', ['ngRoute', 'ngMessages','ngMaterial', 'ngMdIcons']);

// authentication.config(function($routeProvider, $httpProvider){
//   $routeProvider
//     .when('/', {
//       templateUrl: 'app/authentication/views/login.html',
//       controller: 'LoginController'
//     });

  //   //fancy random token, losely after https://gist.github.com/jed/982883
  // function b(a){return a?(a^Math.random()*16>>a/4).toString(16):([1e16]+1e16).replace(/[01]/g,b)};
  //
  // $httpProvider.interceptors.push(function() {
  //     return {
  //         'request': function(response) {
  //             // put a new random secret into our CSRF-TOKEN Cookie before each request
  //             document.cookie = 'CSRF-TOKEN=' + b();
  //             return response;
  //         }
  //     };
  // });
//});
