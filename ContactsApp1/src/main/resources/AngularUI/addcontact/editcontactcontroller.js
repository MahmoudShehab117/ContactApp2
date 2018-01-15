angular.module('ContactDetailsModule')
.controller('contactDetailsController', function($scope,ContactService,$routeParams,$location){
    $scope.editHideFlag=false;

    $scope.contact = new ContactService();

    $scope.loadContact = function() {
        $scope.contact = ContactService.get({ id: $routeParams.id });
        };

    $scope.loadContact();

    $scope.saveEditContact = function() {
            $scope.contact.$update({id: $routeParams.id});
            alert("Saved Successfully");
            $location.path('/contacts-list');
            }


});