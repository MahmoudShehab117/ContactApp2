angular.module('AddContactModule').controller('addContactController', function($scope,ContactService,$location){

        $scope.editHideFlag = true;
        $scope.contact = new ContactService();
        $scope.contact.id = Math.floor(Math.random()*999);
        $scope.addContact = function() { //create a new Contact.
            $scope.contact.$save(function() {
            alert("Successfully Saved")
            $location.path('/contacts-list'); // on success go back to contacts-list
            });

         };

     });
