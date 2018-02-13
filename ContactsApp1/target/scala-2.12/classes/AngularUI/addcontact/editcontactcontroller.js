angular.module('ContactDetailsModule')


.controller('contactDetailsController', function($scope,ContactService,$routeParams,$location){

    $scope.contact = new ContactService();

    $scope.addPhone = function(phone){
                $scope.contact.phones.push({id:-1,contactId:$routeParams.id,phone:phone});
                $scope.phone="";
    };

    $scope.deletePhone = function(contactDetail){
                var index =$scope.contact.phones.indexOf(contactDetail);
                $scope.contact.phones.splice(index,1);
    };

    $scope.editHideFlag=false;

    $scope.loadContact = function() {$scope.contact = ContactService.get({ id: $routeParams.id });
    };

    $scope.loadContact();

    $scope.saveEditContact = function() {
            ContactService.update($scope.contact, function() {
            //data saved. do something here.
            alert("Successfully Saved")
            $location.path('/contacts-list'); // on success go back to contacts-list
            });

//            $scope.contact.$update({id: $routeParams.id});


});