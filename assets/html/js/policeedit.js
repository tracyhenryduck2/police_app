var url="";
var model;
var request;
var project;
var first=false;
var result=false;


(function(){
	var app = angular.module('project', [ ]);
      app.config(function($httpProvider) {
        $httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
        $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
        
    // Override $http service's default transformRequest
    $httpProvider.defaults.transformRequest = [function(data) {
        /**
         * The workhorse; converts an object to x-www-form-urlencoded serialization.
         * @param {Object} obj
         * @return {String}
         */
         var param = function(obj) {
            var query = '';
            var name, value, fullSubName, subName, subValue, innerObj, i;
            
            for (name in obj) {
                value = obj[name];
                
                if (value instanceof Array) {
                    for (i = 0; i < value.length; ++i) {
                        subValue = value[i];
                        fullSubName = name + '[' + i + ']';
                        innerObj = {};
                        innerObj[fullSubName] = subValue;
                        query += param(innerObj) + '&';
                    }
                } else if (value instanceof Object) {
                    for (subName in value) {
                        subValue = value[subName];
                        fullSubName = name + '[' + subName + ']';
                        innerObj = {};
                        innerObj[fullSubName] = subValue;
                        query += param(innerObj) + '&';
                    }
                } else if (value !== undefined && value !== null) {
                    query += encodeURIComponent(name) + '='
                    + encodeURIComponent(value) + '&';
                }
            }
            
            return query.length ? query.substr(0, query.length - 1) : query;
        };
        
        return angular.isObject(data) && String(data) !== '[object File]'
        ? param(data)
        : data;
    }];
});


	app.controller('ProjectController',['$http','$window', function($http,$window){
        project=this;
        creatProceed1();
		url=getPath();
		//url="111.1.3.197:88/police";
        project.url=url.substring(0,url.indexOf("/police"));
		model=project;
		request=$http;
		project.image    = GetQueryString("image");

		project.name     = GetQueryString("name");
		project.position = GetQueryString("position");


                $(".anniu6").on("tap",function(){
                    project.goprevious();
                });

                $(".anniu7").on("tap",function(){
                    project.edit();
                });
		project.goprevious=function(){
             client.saveNotGlobalInfo("back","1");
			 goPrevious();
		}


		project.edit = function(){
         if(project.editName==null ||project.editName==''){
         	progress("Error","请填写姓名!");
         	return;
         }
         if(!(/^[\u4e00-\u9fa5]+$/).test(project.editName)){
            progress("Error","姓名必须为中文!");
            return;
         }
         if(project.editName.length>4){
            progress("Error","姓名必须小于四个字!");
            return;
         }


          if(project.editPhone==null ||project.editPhone==''){
         	progress("Error","请填写电话号码!");
         	return;
         }

         if(!(/^1[3|4|5|8][0-9]\d{4,8}$/).test(project.editPhone.toString()) || project.editPhone.toString().length!=11){

            progress("Error","手机号码格式错误!");
            return;
         }

         if(project.content==null ||project.content==''){
         	progress("Error","请填写留言内容!");
         	return;
         }
           

           progress("Show");
            $http({url:'http://'+url+'/app/Index!messageSubmit.action',method:'post',data:{"id":GetQueryString("id"),"name":project.editName,"phone":project.editPhone,"content":project.content,"phoneMin":GetQueryString("phone")}}).success(function(data){
          if(data.result==1)
          {
                client.saveNotGlobalInfo("back","1");
            progress("Success","发送成功!","goPrevious();");
          }
          else  
          {
            progress("Error","发送失败!");
          }
  
        }).error(function(data, status, headers, config){
         progress("Dismiss");
          if((status >= 200 && status < 300 ) || status === 304 || status === 1223 || status === 0)
          {
            progress("Error","网络访问出错!");
          }
        }) ;
		}

	}]);
})();




