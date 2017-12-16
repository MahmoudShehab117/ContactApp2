angular.module('ContactDetailsModule')
.controller('contactDetailsController', function($scope,ContactService,$routeParams,$location){
    $scope.editHideFlag=false;

    $scope.contact = new ContactService();
    $scope.saveEditContact = function() { //Update the edited movie. Issues a PUT to /api/movies/:id
            $scope.contact.$update({id: $routeParams.id});
            alert("Saved Successfully");
            $location.path('/contacts-list');
            }

    $scope.loadContact = function() { //Issues a GET request to /api/movies/:id to get a movie to update
        $scope.contact = ContactService.get({ id: $routeParams.id });
        };

    $scope.loadContact();
});