var url="";
var model;
var request;
var project;



(function(){
	var app = angular.module('project', [ ]);
	app.controller('ProjectController',['$http','$window', function($http,$window){
        project=this;
		url=getPath();
    //url="localhost:8080/police";
    project.url=url;
		model=project;
		request=$http;
    LoadData(); 
      project.image    = GetQueryString("image");
     project.name = GetQueryString("name");
    project.position = GetQueryString("position");

                    $(".anniu7").on("tap",function(){
                    project.goprevious();
                });

                $(".anniu6").on("tap",function(){
                    project.nextMessage();
                });
		project.goprevious=function(){
			 goPrevious();
		}
    project.nextMessage=function(){
        LoadData();
    }


	}]);
})();

function LoadData(){

    request.post('http://'+url+'/app/Index!unReadList.action?id='+GetQueryString("id")).success(function(data){

      model.response=data; 
 
    }).error(function(data, status, headers, config){
      if((status >= 200 && status < 300 ) || status === 304 || status === 1223 || status === 0)
      {
        //$("body").html("网络访问出错！");
      }
    });
}


