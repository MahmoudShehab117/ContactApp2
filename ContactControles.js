angular.module('myApp', []).controller('contactsCtrl', function($scope,$http) {
$scope.fName = '';
$scope.lName = '';
$scope.email = '';
$scope.phone = '';

var url="Contacts.txt";
$http.get(url).then( function(response) {
 $scope.Contacts = response.data;
  });


$scope.edit = true;
$scope.showList = true;
$scope.error = false;
$scope.incomplete = false; 

$scope.showLists =function(){
$scope.showList = true;
}

$scope.editContact = function(id) {
  $scope.showList = false;
  if (id == 'new') {
    $scope.edit = true;
    $scope.incomplete = true;
    $scope.fName = '';
    $scope.lName = '';
	$scope.email = '';
	$scope.phone = '';
    } else 
	{
    $scope.edit = false;
    $scope.fName = $scope.Contacts[id-1].fName;
    $scope.lName = $scope.Contacts[id-1].lName; 
	$scope.email = $scope.Contacts[id-1].email;
    $scope.phone = $scope.Contacts[id-1].phone;
  }
};

$scope.saveEditContact =function(id) {
     $scope.Contacts[id-1].fName = $scope.fName;
     $scope.Contacts[id-1].lName = $scope.lName; 
	 $scope.Contacts[id-1].email = $scope.email ;
     $scope.Contacts[id-1].phone = $scope.phone;	
	
}

$scope.deleteUser = function(id) {
$scope.Contacts.splice(id-1, 1);
}

$scope.saveNewContact = function(id){

	$scope.max = Math.max.apply(Math,$scope.Contacts.map(function(Contacts){return Contacts.id;}));
	$scope.Contacts.push({"id":$scope.max+1, "fName": $scope.fName ,"lName": $scope.lName ,"email": $scope.email ,"phone": $scope.phone});

}

$scope.$watch('fName', function() {$scope.test();});

$scope.$watch('lName', function() {$scope.test();});

$scope.test = function() {
  $scope.incomplete = false;
  if ($scope.edit && (!$scope.fName.length ||
  !$scope.lName.length ||
  !$scope.passw1.length || !$scope.passw2.length || !$scope.fName.length )) {
     $scope.incomplete = true;
  }
};

});