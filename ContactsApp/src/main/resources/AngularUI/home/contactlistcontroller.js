angular.module('ContactListModule')
.controller('contactsListController', function($scope,ContactService,$location,$window) {

    $scope.logout=true;
    $scope.contacts = ContactService.query();
    $scope.deleteContact = function(contact) {
      var deleteContact = $window.confirm('Are you sure you want to delete this contact?');
      if(deleteContact){
            contact.$delete({id : contact.id});
            }
       else alert("The Contact wasn't deleted yet");
    }

});
