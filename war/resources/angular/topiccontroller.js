//module declaration

var topicCtrls = angular.module('topicCtrls',['topicservices']);
var topicapp = angular.module('topicapp',['ngRoute','topicCtrls','ngResource','infinite-scroll','hm.readmore'])
        .config(function ($httpProvider) {
        	$httpProvider.defaults.headers.post['Content-Type'] =  'application/json';
            
        });
var topicservices = angular.module('topicservices', [ 'ngResource' ]);



topicservices.factory('topicservice', ['$http', '$q', function($http, $q){
	 
    return {
         
            getComments: function() {
            		var topicId=document.getElementById('topicId').value;
            		
                    return $http.get('http://localhost:8080/topicmanagementsystem/getComments.action?topicId='+topicId)
            	
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while fetching Comments');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
             
            saveComment: function(comment){
            		var data = { 'content': document.getElementById('comment').value,
            				'userId':document.getElementById('userId').value,
            				'topicId':document.getElementById('topicId').value};
            		
            		console.log(data);
            		
                    return $http.post('http://localhost:8080/topicmanagementsystem/saveComment.action',JSON.stringify(data))
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while Saving Comment');
                                        console.error(errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            
            updateScore: function(like,commentId){
        		var data = like==true?1:-1;
        		
        		console.log(data);
        		
                return $http.post('http://localhost:8080/topicmanagementsystem/updateScore.action?commentId='+commentId,JSON.stringify(data))
                        .then(
                                function(response){
                                    return response.data;
                                }, 
                                function(errResponse){
                                    console.error('Error while Saving Comment');
                                    console.error(errResponse);
                                    return $q.reject(errResponse);
                                }
                        );
            }
    };
 
}]);
topicCtrls.controller('topicCtrl',[ '$scope',
                     						'$http',
                     						'topicservice','$window','$compile',
                     						function($scope, $http, topicservice,$window,$compile) {
                     							
												$scope.topicId=document.getElementById('topicId').value;
												$scope.comments=[];
												$scope.leftOverComments=[]
												$scope.getComments = function() {
                     								
													topicservice.getComments().then(
                     		                               function(d) {
                     		                            	   console.log(d);
                     		                            	  $scope.comments=d;
                     		                                    
                     		                               },
                     		                                function(errResponse){
                     		                                    console.error('Error while getting Comments');
                     		                                }
                     		                       );
                     							};
                     							
                     							$scope.submitComment=function(){
                     								if(document.getElementById('comment').value=="" ||
                     										document.getElementById('comment').value==null){
                     										alert("Comment content cannot be empty");
                     										return false;
                     								}else{
	                     								topicservice.saveComment().then(
	                     		                               function(d) {
	                     		                            	   console.log(d);
	                     		                            	  $scope.comments=d;
	                     		                            	 document.getElementById('comment').value='';
	                     		                               },
	                     		                                function(errResponse){
	                     		                                    console.error('Error while saving comments');
	                     		                                }
	                     		                       );
                     								}
                     								
                     								
                     							}
                     							
                     							$scope.updateScore=function(like,commentId){
                     								var index;
                     								for(var i=0;i<$scope.comments.length;i++){
                     									if($scope.comments[i].commentId==commentId){
                     										index=i;
                     										break;
                     									}
                     								}
                     								$scope.newCommentsList=[];
                     								topicservice.updateScore(like,commentId).then(
                     		                               function(d) {
                     		                            	  if(d.commentId=="" || d.commentId==null || d.commentId==0){
                     		                            		 for(var i=0;i<$scope.comments.length;i++){
                                  									if(index!=i){
                                  										$scope.newCommentsList.push($scope.comments[i])
                                  									}
                                  								}
                     		                            		$scope.comments=$scope.newCommentsList; 
                     		                            	  }else{
                     		                            		 $scope.comments[index]=d;
                     		                            	  } 
                     		                            	  
                     		                               },
                     		                                function(errResponse){
                     		                                    console.error('Error while updating scores');
                     		                                }
                     		                       );
                     								
                     							};
                     							
                     							
                     							$scope.limit=10;
                     							
                     							$scope.loadMore = function() {
                    							    $scope.limit += 10;
                    							  };
                    							  
                    							  $scope.commentLength = 200;
                    							  $scope.lessText = "Read less";
                    							  $scope.moreText = "Read more";
                    							  $scope.dotsClass = "toggle-dots-grey";
                    							  $scope.linkClass = "toggle-link-yellow";  
                     							
                     							
                     						} ]);



