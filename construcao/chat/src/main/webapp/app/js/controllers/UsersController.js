desafioChat.controller('UsersController', function($scope) {

  $scope.model = {
    users: [],
    selectedUser: [],
    filter : ''
  };

  $scope.query = {
    order: 'name',
    limit: 5,
    page: 1
  };

  $scope.options = {
  autoSelect: true,
  boundaryLinks: false,
  largeEditDialog: false,
  pageSelector: false,
  rowSelection: true
};

  $scope.model.users = [{
    "name": "Lukas",
    "email": "email@email.com",
    "role": "ADMIN"
  },
  {
    "name": "Douglas",
    "email": "email@email.com",
    "role": "ADMIN"
  }, {
    "name": "Tiago",
    "email": "email@email.com",
    "role": "ADMIN"
  }, {
    "name": "Maria",
    "email": "email@email.com",
    "role": "ADMIN"
  }, {
    "name": "Joao",
    "email": "email@email.com",
    "role": "ADMIN"
  }, {
    "name": "Jose",
    "email": "email@email.com",
    "role": "ADMIN"
  }
];


  $scope.query = {
    order: 'name',
    limit: 5,
    page: 1
  };

  function getUsers(query) {
    // $scope.promise = $model.users.get(query, success).$promise;
  }

  function success(desserts) {
    // $scope.desserts = desserts;
  }

  $scope.onPaginate = function(page, limit) {
    // getUsers(angular.extend({}, $scope.query, {page: page, limit: limit}));
  };

  $scope.onReorder = function(order) {
    // getDesserts(angular.extend({}, $scope.query, {order: order}));
  };



  $scope.onPaginate = function(page, limit) {
    // $scope.$broadcast('md.table.deselect');
    console.log('Scope Page: ' + $scope.query.page + ' Scope Limit: ' + $scope.query.limit);
    console.log('Page: ' + page + ' Limit: ' + limit);

    $scope.promise = $timeout(function() {

    }, 2000);

    $scope.deselect = function (item) {
  console.log(item.name, 'was deselected');
};

$scope.log = function (item) {
  console.log(item.name, 'was selected');
};

$scope.loadStuff = function () {
  $scope.promise = $timeout(function () {

  }, 2000);
};

$scope.onReorder = function(order) {

  console.log('Scope Order: ' + $scope.query.order);
  console.log('Order: ' + order);

  $scope.promise = $timeout(function () {

  }, 2000);
};
  };

});
