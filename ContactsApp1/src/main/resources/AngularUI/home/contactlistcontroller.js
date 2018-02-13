angular.module('ContactListModule')
.controller('contactsListController', function($scope,ContactService,ReportService,$window,$location) {

    $scope.logout=true;
    $scope.contacts = new ContactService();
    $scope.contacts = ContactService.query();

    $scope.deleteContact = function(contact)
    {
      var deleteContact = $window.confirm('Are you sure you want to delete this contact?');
      if(deleteContact){
             ContactService.delete(contact);
              $location.path('/contacts-list'); // on success go back to contacts-list
//             $scope.contacts.$delete({id : contact.id});
                }
      else alert("The Contact wasn't deleted yet");
    }
    $scope.report = ReportService.get();
});
