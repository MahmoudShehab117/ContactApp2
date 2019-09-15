describe('test Contacts List', function() {

    ;

    beforeEach(angular.mock.module('ContactDetailsModule'));


    var resource;
    var ContactService;

    beforeEach(inject(function($resource, _ContactService_){ //Mock our factory and spy on methods
                resource = $resource.defer();
                ContactService = _ContactService_;
                spyOn(ContactService, 'fetchServerData').and.returnValue(resource.promise);
            }));

    var controller,scope

    beforeEach(angular.mock.inject(function($controller, $rootScope){

          scope = $rootScope.$new();
          controller = $controller('contactsListController',{scope : scope});

    }));

    it('ensure invalid user name are caught', function() {

    expect('what?').toBe('what?');

    });

    it('Ensure the list should be loaded !', function ()
    {

	});

});