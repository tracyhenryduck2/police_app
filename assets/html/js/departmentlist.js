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


	app.controller('ProjectController',['$http','$window','$scope', function($http,$window,$scope){
        project=this;
		//url=getPath();
	//	url="111.1.3.197:88/police";
		project.url=url;
		//project.user=getUser();
		//project.user=d:1};
		project.page=0;
		model=project;
		request=$http;
	//	LoadData();      
	//client.saveNotGlobalInfo("id",null);


				$("#tuichu").on("tap",function(){
					project.exit();
				});

				$("#shuom").on("tap",function(){
					client.open("note.html",0);
				});

				$("#left").on("tap",function(){
					project.lastpage();
				});

				$("#right").on("tap",function(){
					project.nextpage();
				});
project.nextpage=function(){
				$scope.$apply(function(){
			project.page++;
			});
}

project.lastpage=function(){
				$scope.$apply(function(){
			project.page--;
			});
}

project.exit=function(){
	client.showDialog(1,"请输入密码","","method");
}

project.policelist=function(id){
	client.saveNotGlobalInfo("id",id);
	client.open("policelist.html",0);
}

        project.gotoResponse=function(id,name,position,image){
         client.open("huifu.html?id="+id+"&image="+image+"&name="+name+"&position="+position,0);
        }

        project.goprevious=function(){
        	goPrevious();
        }

		project.gotoedit=function(id,image,phone,name,position){
			client.open("policeedit.html?id="+id+"&image="+image+"&phone="+phone+"&name="+name+"&position="+position,0);
		}

		project.edit = function(code){
           progress("Show");
            $http({url:'http://'+url+'/app/Index!LoginOut.action',method:'post',data:{"code":code}}).success(function(data){
          if(data.result==1)
          {
            progress("Success","退出成功!","goPrevious();");
            client.logout();
          }
          else  
          {
            progress("Error","退出失败!");
          }
  
        }).error(function(data, status, headers, config){
         progress("Dismiss");
          if((status >= 200 && status < 300 ) || status === 304 || status === 1223 || status === 0)
          {
            progress("Error","网络访问出错!");
          }
        }) ;
		}


		project.getUnRead=function(list){
            if(project.list.length>0 && list!=null&&list.length>0)
            {
            	for(var i=0;i<project.list.length;i++)
            	{
            		for(var j=0;j<list.length;j++)
            		{
            			if(project.list[i].id==list[j].id)
            			{
            			  project.list[i].cnt=list[j].cnt;
            			  break;  	
            			}
            		}
            	}

            }
		}

	}]);
})();

function LoadData(){
		request.post('http://'+url+'/app/Index!departmentList.action').success(function(data){

			model.list=data; 

			if(model.list.length>0)
			setTimeout(function(){
				$(".dep").on("tap",function(){
					model.policelist(model.list[$('.dep').index($(this))].id);
				});

			},1);

 
		}).error(function(data, status, headers, config){
			if((status >= 200 && status < 300 ) || status === 304 || status === 1223 || status === 0)
			{
				$(".neirong").html('<div style="font-size: 3em;font-size: 3em;margin-top: 252px;">无网络链接</div><button onclick="reload();" style="width: 500px;height: 100px;margin-top: 26px;font-size: 3em;background-color: #F9F9F9;">点击刷新</button>');
			}
		});
}

function method(flag,str){
   model.edit(str);
}