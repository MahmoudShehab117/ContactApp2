angular.module('LoginModule')
.controller('LoginController',function ($scope, $location, AuthenticationService,SetCredentialServices){

         $scope.logout=false;
         SetCredentialServices.ClearCredentials($scope.userName,$scope.password);
         $scope.dataLoading = false;

         $scope.login = function() {
         AuthenticationService.loginUser($scope.userName,$scope.password).then(
                 function(loginResult){
                     $scope.dataLoading = true;
                     alert("Welcome Back" + " " + $scope.userName);
                     SetCredentialServices.SetCredentials($scope.userName, $scope.password);
                     $location.path('/contacts-list');
                 },
                 function(err){
                    $scope.error ="Username or Password are invalid !";
                 }
               )};
       });
