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
		url=getPath();
		//url="111.1.3.197:88/police";
		project.url=url;
		//project.user=getUser();
		//project.user={id:1};
		project.page=0;
		model=project;
		request=$http;
	    
        setInterval("LoadUnreadList();",10000);
        project.depid = client.readNotGlobalInfo("id");
        //project.depid = 2;
	   LoadData();  

				$("#last").on("tap",function(){
					project.goprevious();
				});

				$("#left").on("tap",function(){
					project.lastpage();
				});

				$("#right").on("tap",function(){
					project.nextpage();
				});
				$("#shuom").on("tap",function(){
					client.open("note.html",0);
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

        project.gotoResponse=function(id,name,position,image){

         client.open("huifu.html?id="+id+"&image="+image+"&name="+name+"&position="+position,0);
        }

        project.goprevious=function(){
        	goPrevious();
        }

		project.gotoedit=function(id,image,phone,name,position){
			client.open("policeedit.html?id="+id+"&image="+image+"&phone="+phone+"&name="+name+"&position="+position,0);
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

		request.post('http://'+url+'/app/Index!policeList.action?depId='+model.depid).success(function(data){
            console.log(data);
			model.list=data; 
            if(model.depid==2 && model.list.length>2){
            	model.list[0].style = {"padding": "3px 19.5%"};
            	model.list[1].style = {"padding": "3px 19.5%"};
            }
		       setTimeout(function(){
                  $(".avc").on("tap",function(){
                   	var map = model.list[$('.avc').index($(this))];
					 model.gotoedit(map.id,map.image,map.phone,map.name,map.position);
				});

		       },1);
 
		}).error(function(data, status, headers, config){
			if((status >= 200 && status < 300 ) || status === 304 || status === 1223 || status === 0)
			{
				$(".neirong").html('<div style="font-size: 3em;font-size: 3em;margin-top: 252px;">无网络链接</div><button onclick="reload();" style="width: 500px;height: 100px;margin-top: 26px;font-size: 3em;background-color: #F9F9F9;">点击刷新</button>');
			}
		});
}

function LoadUnreadList(){

		request.post('http://'+url+'/app/Index!getResult.action?depId='+model.depid).success(function(data){
          
			if(model.list!=null&&data!=null&&data.length>0){

		       project.getUnRead(data);
		       setTimeout(function(){
                  $(".unread").on("tap",function(){
                  	var map = model.list[$('.unread').index($(this))];
					model.gotoResponse(map.id,map.name,map.position,map.image);
				});

		       },1);
			}
 
		}).error(function(data, status, headers, config){
			if((status >= 200 && status < 300 ) || status === 304 || status === 1223 || status === 0)
			{
				//$("body").html("网络访问出错！");
			}
		});
}