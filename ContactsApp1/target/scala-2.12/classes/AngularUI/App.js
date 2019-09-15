
angular.module('AddContactModule', []);
angular.module('ContactDetailsModule', []);
angular.module('ContactListModule', []);
angular.module('LoginModule', []);
angular.module('Services', []);


var app = angular.module('myApp', ["ContactListModule",
                                    "ContactDetailsModule",
                                    "AddContactModule",
                                    "LoginModule",
                                    "Services",
                                    "ngRoute",
                                    "ngResource",
                                    "ngCookies"]);

app.config(function($routeProvider,$locationProvider) {
     $routeProvider.when('/contacts-list', {
               templateUrl : 'home/contacts-list.html',
               controller: 'contactsListController'
     }).when('/edit-contact/:id', {
               templateUrl : 'addcontact/add-contact.html',
               controller: 'contactDetailsController'
     }).when('/add-contact', {
               templateUrl : 'addcontact/add-contact.html',
               controller: 'addContactController'
     }).when('/login', {
               controller: 'LoginController',
               templateUrl: 'login/login.html'
     }).otherwise({
         redirectTo: '/login'
                         });
 });

app.run(['$rootScope', '$location', '$cookieStore', '$http',
       function ($rootScope, $location, $cookieStore, $http) {
           // keep user logged in after page refresh
           $rootScope.globals = $cookieStore.get('globals') || {};
           if ($rootScope.globals.currentUser) {
               $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata;
           }

           $rootScope.$on('$locationChangeStart', function (event, next, current) {
           // redirect to login page if not logged in
               if ($location.path() !== '/login' && !$rootScope.globals.currentUser) {
                   $location.path('/login');
               }
           });
       }]);
