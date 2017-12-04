var app = angular.module('myApp', ["ngRoute"]);

app.config(function($routeProvider,$locationProvider) {
     $routeProvider.when('/', {
               templateUrl : 'home.html',
               controller: 'contactsCtrl'
     }).when('/editContact/:contact_index', {
               templateUrl : 'addContact.html',
               controller: 'editCtrl'
     }).when('/addContact', {
               templateUrl : 'addContact.html',
               controller: 'addCtrl'
                         }).otherwise({
         redirectTo: '/'
                         });
 });

app.controller('contactsCtrl', function($scope, ContactService, $http, $location, $window) {
  $scope.contacts = ContactService.getContacts(); //Yehia: Tabs Tabs
  $scope.goToHome =function(path) {
    $location.path(path);
  }

  $scope.goToEdit =function(contact_index) {
    if(contact_index =="new") {
      $location.url("/addContact");
    }
    else {
      $location.url("/editContact/"+contact_index);
    }
  }

  $scope.deleteContact = function(id) {
    var deleteUser = $window.confirm('Are you sure you want to delete the Ad?');
    if(deleteUser) {
      ContactService.deleteContact(id);
      alert("The Contact was deleted");
    }
    else  {
      alert("The Contact wasn't deleted yet");
    }
  }

});

app.controller('editCtrl', function($scope,ContactService,$routeParams,$location){
  $scope.edit=ContactService.edit;
  var index = $routeParams.contact_index;
  var contacts = ContactService.getContacts();
  $scope.currentContact = contacts[index];
    $scope.saveEditContact = function()
    {
    alert("Edit Successfully");
    $location.path('/home');
    }

  $scope.goToHome =function(path){
  $location.path(path);
  }
});

app.controller('addCtrl', function($scope,ContactService,$routeParams,$location){
$scope.edit = false;
$scope.contacts= ContactService.getContacts();
$scope.edit=true;
     $scope.addContact = function () {
       var contact = $scope.currentContact;
       contact.id = $scope.contacts.length;
       ContactService.addContact(contact);
       alert("Successfully Saved");
       $location.path('/home');
       }
     });

app.factory('ContactService',function ($http) {
     var factory = {};
     var edit=false;
     var contactList=[
     {
                         "id":0,
                            "fName" : "Alaa",
                            "lName" : "Maher",
                            "email" : "s@hotmail.com",
                      	  "phone" : 1110
                         },

                      	{
                      	  "id":1,
                            "fName" : "Marko",
                            "lName" : "Nazmy",
                            "email" : "n@hotmail.com",
                      	  "phone" : 1010111
                          },
                         {
                            "id":2,
                            "fName" : "Mahmoud",
                            "lName" : "ahmed",
                            "email" : "y@hotmail.com",
                      	  "phone" : 10100
                         },

                         {
                              "id":3,
                            "fName" : "Sara",
                            "lName" : "emad",
                            "email" : "k@hotmail.com",
                      	  "phone" : 412112
                         },
                         {
                            "id":4,
                            "fName" : "Ahmed",
                            "lName" : "Ragheb",
                            "email" : "h@hotmail.com",
                      	  "phone" : 42852
                         }];



     factory.getContacts = function () {
       return contactList;
     }

     factory.deleteContact =function (id)
     {

     var index = -1;
     for (var i = 0 ; i < contactList.length ; i++)
          {
          if(contactList[i].id == id)index = i;
          }
     contactList.splice(index, 1);

     }


    factory.addContact =function(contact){
     contactList.push(contact);
     }


     return factory;
   });
