var app = angular.module('myApp', ["ngRoute"]);

app.config(function($routeProvider,$locationProvider) {
     $routeProvider.when('/', {
               templateUrl : 'contacts-list.html',
               controller: 'contactsListController'
     }).when('/edit-contact/:contact_index', {
               templateUrl : 'add-contact.html',
               controller: 'contactDetailsController'
     }).when('/add-contact', {
               templateUrl : 'add-contact.html',
               controller: 'addContactController'
                         }).otherwise({
         redirectTo: '/'
                         });
 });

app.controller('contactsListController', function($scope,ContactService,$location,$window) {

    $scope.contacts = ContactService.getContacts();
    $scope.deleteContact = function(id) {


        var deleteUser = $window.confirm('Are you sure you want to delete this contact?');
             if(deleteUser){
              ContactService.deleteContact(id);
              alert("The Contact was deleted");
             }
        else  alert("The Contact wasn't deleted yet");
    }

});

app.controller('contactDetailsController', function($scope,ContactService,$routeParams,$location){
    $scope.editHideFlag=false;
    var index = $routeParams.contact_index;
    var contacts = ContactService.getContacts();
    $scope.contact = contacts[index];
    $scope.saveEditContact = function()
    {
    alert("Edit Successfully");
    $location.path('/contacts-list');
    }

    $scope.goToHome =function(path){
    $location.path(path);
  }
});

app.controller('addContactController', function($scope,ContactService,$location){
    $scope.editHideFlag = true;
    $scope.contacts= ContactService.getContacts();
    $scope.addContact = function () {
        var contact = $scope.contact;
        contact.id = $scope.contacts.length;
        ContactService.addContact(contact);
        alert("Successfully Saved");
        $location.path('/contacts-list');
       }
     });

app.factory('ContactService',function ($http) {
     var factory = {};
     var contactList=[
                       {
                         "id":0,
                         "firstName" : "Alaa",
                         "lastName" : "Maher",
                         "email" : "s@hotmail.com",
                         "phone" : 1110
                       },

                       {
                         "id":1,
                         "firstName" : "Marko",
                         "lastName" : "Nazmy",
                         "email" : "n@hotmail.com",
                         "phone" : 1010111
                       },
                       {
                         "id":2,
                         "firstName" : "Mahmoud",
                         "lastName" : "ahmed",
                         "email" : "y@hotmail.com",
                         "phone" : 10100
                       },

                       {
                         "id":3,
                         "firstName" : "Sara",
                         "lastName" : "emad",
                         "email" : "k@hotmail.com",
                         "phone" : 412112
                       },
                       {
                         "id":4,
                         "firstName" : "Ahmed",
                         "lastName" : "Ragheb",
                         "email" : "h@hotmail.com",
                         "phone" : 42852
                       }
                     ];
     factory.deleteContact =function (id){

     var index = -1;
     for (var i = 0 ; i < contactList.length ; i++)
          {
          if(contactList[i].id == id)index = i;
          }
     contactList.splice(index, 1);

     }
     factory.getContacts = function(){

     return contactList;
     }
     factory.addContact =function(contact){

     contactList.push(contact);
     }
     return factory;
   });
