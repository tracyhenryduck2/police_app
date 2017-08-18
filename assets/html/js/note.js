var url="";
var model;
var request;
var project;
var first=false;
var result=false;


(function(){
	var app = angular.module('project', [ ]);
	app.controller('ProjectController',['$http','$window','$scope', function($http,$window,$scope){
        project=this;
		//url=getPath();
		//url="192.168.1.110:8080/police";
		project.url=url;
		//project.user=getUser();
		//project.user={id:1};
		model=project;
		request=$http;

				$("#last").on("tap",function(){
					project.goprevious();
				});

        project.goprevious=function(){
        	client.saveNotGlobalInfo("back","1");
        	goPrevious();
        }


	}]);
})();
