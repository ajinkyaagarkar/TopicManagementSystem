//module declaration
var homepageCtrls = angular.module('homepageCtrls',['homepageservices']);
var homepageapp = angular.module('homepageapp',['ngRoute','homepageCtrls','ngResource']);
        

var homepageservices = angular.module('homepageservices', [ 'ngResource' ]);



homepageservices.factory('homepageservice', ['$http', '$q', function($http, $q){
	 
    return {
         
            getTopics: function() {
                    return $http.get('http://localhost:8080/topicmanagementsystem/getTopics.action')
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while fetching topics');
                                        return $q.reject(errResponse);
                                    }
                            );
            }
       };
 
}]);
homepageCtrls.controller('homepageCtrl',[ '$scope',
                     						'$http',
                     						'homepageservice','$window','$compile',
                     						function($scope, $http, homepageservice,$window,$compile) {
                     							
												$scope.topicslist=[];
												$scope.showTopic=function(topic){
													
												}
                     							$scope.getTopics = function() {
                     								
                     								
                     								homepageservice.getTopics().then(
                     		                               function(d) {
                     		                            	  $scope.topicslist=d;
                     		                                    self.users = d;
                     		                               },
                     		                                function(errResponse){
                     		                                    console.error('Error while fetching topics');
                     		                                }
                     		                       );
                     		          };
                     							
                     							
                     						} ]);