desafioChat.controller('EditGroupController', function($scope, $q, $timeout, appInfo) {
  /*-------------------------------------------------------------------
   * 		 				 	ATTRIBUTES
   *-------------------------------------------------------------------*/
  $scope.model = {
    group: {
      name: 'Group Development',
      users: []
    },
    window:{
      name:'Alter < Group Development >'
    },
    filterSelected: true,
    usersSelected: [],
    allContacts: null,
    selectedItem: null
  };


var pendingSearch, cancelSearch = angular.noop;
var cachedQuery, lastSearch;


/**
 * Create filter function for a query string
 */
function createFilterFor(query) {
  var lowercaseQuery = angular.lowercase(query);

  return function filterFn(contact) {
    return (contact._lowername.indexOf(lowercaseQuery) != -1);;
  };

}

$scope.loadContacts = function() {
  var contacts = [
    'Marina Augustine',
    'Oddr Sarno',
    'Nick Giannopoulos',
    'Narayana Garner',
    'Anita Gros',
    'Megan Smith',
    'Tsvetko Metzger',
    'Hector Simek',
    'Some-guy withalongalastaname'
  ];

  return contacts.map(function(c, index) {
    var cParts = c.split(' ');
    var contact = {
      name: c,
      email: cParts[0][0].toLowerCase() + '.' + cParts[1].toLowerCase() + '@example.com'
    };
    contact._lowername = contact.name.toLowerCase();
    return contact;
  });
}

$scope.model.allContacts = $scope.loadContacts();
$scope.model.usersSelected = $scope.model.allContacts;
$scope.model.group.name = 'Group Development';


/**
 * Search for contacts.
 */
function querySearch(query) {
  var results = query ?
    self.allContacts.filter(createFilterFor(query)) : [];
  return results;
}

/**
 * Create filter function for a query string
 */
function createFilterFor(query) {
  var lowercaseQuery = angular.lowercase(query);

  return function filterFn(contact) {
    return (contact._lowername.indexOf(lowercaseQuery) != -1);;
  };
}

$scope.showUserInfo = function(chip) {
  console.log('Selected User:', chip);
}



});
