angular.module('AddContactModule').controller('addContactController', function($scope,ContactService,$location){

        $scope.editHideFlag = true;

        $scope.contact = new ContactService();

        $scope.phones = [];


        $scope.contact = {
                contact: {id: 0},
                phones: $scope.phones

            }

        $scope.addPhone = function(phone){
                    $scope.contact.phones.push({id:0,contactID:0,phone:phone});
                    $scope.phone="";
        };

        $scope.deletePhone = function(contactDetail){
                    var index =$scope.contact.phones.indexOf(contactDetail);
                    $scope.contact.phones.splice(index,1);
                };

        $scope.addContact = function() { //create a new Contact.

        ContactService.save($scope.contact, function() {
        //data saved. do something here.
        alert("Successfully Saved")
        $location.path('/contacts-list'); // on success go back to contacts-list
        });
//
//            $scope.contact.$save(function() {
//            alert("Successfully Saved")
//            $location.path('/contacts-list'); // on success go back to contacts-list
//            });

        };

     });
