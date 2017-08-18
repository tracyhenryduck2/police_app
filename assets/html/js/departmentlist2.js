var url="";
var model;
var request;
var project;
var first=false;
var result=false;

//var testdepartment =[{"id":2,"name":" 所领导","unit_id":1,"sort_id":1},{"id":6,"name":" 综合室","unit_id":1,"sort_id":2},{"id":1,"name":"社区中队","unit_id":1,"sort_id":3},{"id":3,"name":"刑侦中队","unit_id":1,"sort_id":4},{"id":4,"name":"巡控中队","unit_id":1,"sort_id":5},{"id":5,"name":"治安中队","unit_id":1,"sort_id":6}];
//var testallpolice = [{"id":13,"name":" 高昂","code":"12501","position":"治安副所长","phone":"13516880255","image":"1460452586117.jpg","dep_id":2,"sort_id":3,"password":"123456","unit_id":1},{"id":36,"name":"何春磊","code":"12501","position":"民警","phone":"13586737065","image":"1460452362491.jpg","dep_id":5,"sort_id":4,"password":"123456","unit_id":1},{"id":43,"name":"俞荣波","code":"12501","position":"中队长","phone":"13736190262","image":"1460452268454.jpg","dep_id":5,"sort_id":1,"password":"123456","unit_id":1},{"id":21,"name":"冯森磊","code":"12501","position":"民警","phone":"18892607838","image":"1460452514451.jpg","dep_id":3,"sort_id":5,"password":"123456","unit_id":1},{"id":42,"name":"叶俊","code":"12501","position":"民警","phone":"13567468026","image":"1460452280279.jpg","dep_id":5,"sort_id":9,"password":"123456","unit_id":1},{"id":11,"name":"叶荣亦","code":"12501","position":"中队长","phone":"13968328181","image":"1460452604073.jpg","dep_id":1,"sort_id":2,"password":"123456","unit_id":1},{"id":34,"name":"周新卫","code":"12501","position":"中队长","phone":"13777299298","image":"1460452384066.jpg","dep_id":4,"sort_id":1,"password":"123456","unit_id":1},{"id":41,"name":"姚盛通","code":"12501","position":"指导员","phone":"13968342178","image":"1460452294990.jpg","dep_id":5,"sort_id":2,"password":"123456","unit_id":1},{"id":33,"name":"应强","code":"12501","position":"民警","phone":"13968337777","image":"1460452393239.jpg","dep_id":4,"sort_id":3,"password":"123456","unit_id":1},{"id":10,"name":"徐国平","code":"12501","position":"民警","phone":"13906845988","image":"1460452611592.jpg","dep_id":1,"sort_id":10,"password":"123456","unit_id":1},{"id":30,"name":"徐磊","code":"12501","position":"民警","phone":"13968322397","image":"1460452424470.jpg","dep_id":3,"sort_id":12,"password":"123456","unit_id":1},{"id":35,"name":"戴锋泽","code":"12501","position":"民警","phone":"13867837100","image":"1460452371758.jpg","dep_id":5,"sort_id":3,"password":"123456","unit_id":1},{"id":6,"name":"李鸣江","code":"12501","position":"中队长","phone":"13989390519","image":"1460452656255.jpg","dep_id":1,"sort_id":1,"password":"123456","unit_id":1},{"id":31,"name":"杨建尧","code":"12501","position":"民警","phone":"13968356745","image":"1460452414392.jpg","dep_id":3,"sort_id":13,"password":"123456","unit_id":1},{"id":32,"name":"杨相良","code":"12501","position":"民警","phone":"13968359299","image":"1460452400399.jpg","dep_id":4,"sort_id":2,"password":"123456","unit_id":1},{"id":23,"name":"林俊帆","code":"12501","position":"民警","phone":"13586903937","image":"1460452491066.jpg","dep_id":3,"sort_id":6,"password":"123456","unit_id":1},{"id":24,"name":"林旭晓","code":"12501","position":"民警","phone":"13906603919","image":"1460452482674.jpg","dep_id":3,"sort_id":7,"password":"123456","unit_id":1},{"id":15,"name":"林珍","code":"12501","position":"教导员","phone":"13586660980","image":"1460452569238.jpg","dep_id":2,"sort_id":2,"password":"123456","unit_id":1},{"id":25,"name":"潘擎擎","code":"12501","position":"民警","phone":"15825599555","image":"1460452471130.jpg","dep_id":3,"sort_id":8,"password":"123456","unit_id":1},{"id":38,"name":"王俊","code":"12501","position":"民警","phone":"13646616421","image":"1460452325301.jpg","dep_id":5,"sort_id":6,"password":"123456","unit_id":1},{"id":18,"name":"王兆永","code":"12501","position":"巡控副所长","phone":"13605789777","image":"1460452541642.jpg","dep_id":2,"sort_id":6,"password":"123456","unit_id":1},{"id":26,"name":"王凯","code":"125012","position":"民警","phone":"15258319101","image":"1460452462222.jpg","dep_id":3,"sort_id":9,"password":"123456","unit_id":1},{"id":28,"name":"王宣澄","code":"12501","position":"民警","phone":"15058833777","image":"1460452444126.jpg","dep_id":3,"sort_id":11,"password":"123456","unit_id":1},{"id":27,"name":"王旭骁","code":"12501","position":"民警","phone":"13685892778","image":"1460452452800.jpg","dep_id":3,"sort_id":10,"password":"123456","unit_id":1},{"id":29,"name":"王毅","code":"12501","position":"中队长","phone":"13586666301","image":"1460452434142.jpg","dep_id":3,"sort_id":1,"password":"123456","unit_id":1},{"id":17,"name":"石守建","code":"12501","position":"社区副所长","phone":"13506882007","image":"1460452552437.jpg","dep_id":2,"sort_id":5,"password":"123456","unit_id":1},{"id":7,"name":"秦飞","code":"12501","position":"民警","phone":"15990502218","image":"1460452648439.jpg","dep_id":1,"sort_id":8,"password":"123456","unit_id":1},{"id":8,"name":"童伟盛","code":"12501","position":"民警","phone":"13586798956","image":"1460452630998.jpg","dep_id":1,"sort_id":9,"password":"123456","unit_id":1},{"id":4,"name":"董人杰","code":"12501","position":"民警","phone":"13906845804","image":"1460452674944.jpg","dep_id":1,"sort_id":6,"password":"123456","unit_id":1},{"id":5,"name":"蒋来建","code":"12501","position":"民警","phone":"15958236596","image":"1460452664258.jpg","dep_id":1,"sort_id":7,"password":"123456","unit_id":1},{"id":39,"name":"薛建宏","code":"33121075","position":"民警","phone":"13777011987","image":"1460452316939.jpg","dep_id":5,"sort_id":7,"password":"123456","unit_id":1},{"id":40,"name":"薛敬训","code":"12501","position":"民警","phone":"15058833059","image":"1460452308063.jpg","dep_id":5,"sort_id":8,"password":"123456","unit_id":1},{"id":12,"name":"詹皓峙","code":"12501","position":"民警","phone":"15888039158","image":"1460452596257.jpg","dep_id":1,"sort_id":11,"password":"123456","unit_id":1},{"id":22,"name":"金海涛","code":"12501","position":"指导员","phone":"13567462345","image":"1460452502501.jpg","dep_id":3,"sort_id":2,"password":"123456","unit_id":1},{"id":37,"name":"陆伟江","code":"12501","position":"民警","phone":"13968358690","image":"1460452332867.jpg","dep_id":5,"sort_id":5,"password":"123456","unit_id":1},{"id":44,"name":"陈云云","code":"12501","position":"民警","phone":"18758820123","image":"1460452238112.jpg","dep_id":6,"sort_id":1,"password":"123456","unit_id":1},{"id":19,"name":"陈家盛","code":"12501","position":"民警","phone":"18768155147","image":"1460452529380.jpg","dep_id":3,"sort_id":3,"password":"123456","unit_id":1},{"id":3,"name":"陈强","code":"12501","position":"民警","phone":"13805859933","image":"1460452682931.jpg","dep_id":1,"sort_id":5,"password":"123456","unit_id":1},{"id":2,"name":"陈律明","code":"12501","position":"民警","phone":"13805856050","image":"1460452692260.jpg","dep_id":1,"sort_id":4,"password":"123456","unit_id":1},{"id":20,"name":"陈恺","code":"12501","position":"民警","phone":"13732137661","image":"1460452521096.jpg","dep_id":3,"sort_id":4,"password":"123456","unit_id":1},{"id":1,"name":"陈根法","code":"12501","position":"民警","phone":"13805856091","image":"1460452700964.jpg","dep_id":1,"sort_id":3,"password":"123456","unit_id":1},{"id":14,"name":"顾建辉","code":"12501","position":"刑侦副所长","phone":"13968346001","image":"1460452577241.jpg","dep_id":2,"sort_id":4,"password":"123456","unit_id":1},{"id":16,"name":"骆昌定","code":"12501","position":"所长","phone":"13806656268","image":"1460452560502.jpg","dep_id":2,"sort_id":1,"password":"123456","unit_id":1}];
//var test =[{"id":16,"name":"骆昌定","code":"12501","position":"所长","phone":"13806656268","image":"1460452560502.jpg","dep_id":2,"sort_id":1,"password":"123456"},{"id":15,"name":"林珍","code":"12501","position":"教导员","phone":"13586660980","image":"1460452569238.jpg","dep_id":2,"sort_id":2,"password":"123456"},{"id":13,"name":" 高昂","code":"12501","position":"治安副所长","phone":"13516880255","image":"1460452586117.jpg","dep_id":2,"sort_id":3,"password":"123456"},{"id":14,"name":"顾建辉","code":"12501","position":"刑侦副所长","phone":"13968346001","image":"1460452577241.jpg","dep_id":2,"sort_id":4,"password":"123456"},{"id":17,"name":"石守建","code":"12501","position":"社区副所长","phone":"13506882007","image":"1460452552437.jpg","dep_id":2,"sort_id":5,"password":"123456"},{"id":18,"name":"王兆永","code":"12501","position":"巡控副所长","phone":"13605789777","image":"1460452541642.jpg","dep_id":2,"sort_id":6,"password":"123456"}];

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
		url=getPath();
		//url="zed1.cn:88/police";
		project.url=url.substring(0,url.indexOf("/police"));
		project.page=0;
    project.page_2=0;
    project.index = 0;
		model=project;
		request=$http;

		// LoadData();      
  //   LoadPoliceData();
 // var d =client.getAllDepartment();
 // var e  =client.getAllPolice();

        $("#tuichu").on("tap",function(){
          project.exit();
        });

        $("#shuom").on("tap",function(){
          client.open("note.html",0);
        });


        $("#up").on("tap",function(){
          project.lastpage();
        });

        $("#down").on("tap",function(){
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

project.nextpolicepage=function(){
        $scope.$apply(function(){
      project.page_2++;
      });
}

project.lastpolicepage=function(){
        $scope.$apply(function(){
      project.page_2--;
      });
}

project.exit=function(){
	client.showDialog(1,"请输入密码","","method");
}


project.LoadData=function(){
  
       var d =client.getAllDepartment();
    
    var data = eval("("+d+")");
   // 
   //

   //var data = testdepartment;
   $scope.$apply(function(){
                model.list=data; 
      if(model.list.length>0)
      {
              var obj = new Object();
              obj.id = -1;
              obj.name = "所有警员";
              obj.sort_id = -1;
              obj.unit_id =  1;
              model.list.splice(0,0,obj);

      }

      if(model.list.length>0)
      setTimeout(function(){
        $(".dep p").on("tap",function(){
          model.loadpolice(model.list[$('.dep p').index($(this))].id,$('.dep p').index($(this)));
        });

      },1);
   });


}


project.LoadPoliceData=function(){
           var d=  client.getAllPolice();
           var data = eval("("+d+")");
           //var data = testallpolice; 
           var str='';
           for(var i=0;i<data.length;i++)
           {
              if(i%8==0) str+='<li>';
             str+='<div class="zhao"><img class="avc" src="http://'+project.url+'/photo/'+data[i].image+'"  alt="" style="cursor:pointer;"/><div class="wenzi" style="cursor:pointer;">姓名：'+data[i].name+'<br/>职务：'+data[i].position+'<br/></div></div>';
              if(i%8==7 || i==(data.length-1)) str+='</li>';
           }
           $("#ceshi").html(str);
              DomReload();
           
          $scope.$apply(function(){
                        model.policelist=data; 

           setTimeout(function(){
                  $(".avc").on("tap",function(){
                    var map = model.policelist[$('.avc').index($(this))];
           model.gotoedit(map.id,map.image,map.phone,map.name,map.position);
        });
           },1);
          });

}


project.loadpolice=function(id,index){
       project.index = index;
       console.log(project.index);
       project.page_2 = 0;
       if(index!=0)
       {
           //var d= client.getDepartmentInfo(id);
           //var data = eval("("+d+")");
            var  data = test;
           var str='';


           for(var i=0;i<data.length;i++)
           {
             var str2=((index==1&&(i==0||i==1))?' style="padding:3px 16.5%" ':'');
              if(i%8==0) str+='<li>';
             str+='<div class="zhao"'+str2+'><img class="avc" src="http://'+project.url+'/photo/'+data[i].image+'"  alt="" style="cursor:pointer;"/><div class="wenzi" style="cursor:pointer;">姓名：'+data[i].name+'<br/>职务：'+data[i].position+'<br/></div></div>';
              if(i%8==7 || i==(data.length-1)) str+='</li>';
           }
           $("#ceshi").html(str);
              DomReload();

           $scope.$apply(function(){

            project.policelist=data; 



                 setTimeout(function(){
                        $(".avc").on("tap",function(){
                          var map = project.policelist[$('.avc').index($(this))];
                 project.gotoedit(map.id,map.image,map.phone,map.name,map.position);
              });
                 },1);


           });



       
       }
       else{

                model.LoadPoliceData(); 

  
       }

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


    var flag = client.readNotGlobalInfo("back");
    if(flag!=null){
 
    setTimeout(function(){
          project.LoadData();  
          project.LoadPoliceData();
    },100);   

      client.saveNotGlobalInfo("back",null);

    }
//initdata();
	}]);
})();

// function LoadData(){

//    var d =client.getAllDepartment();
//    var data = eval("("+d+")");
// 			model.list=data; 
//       if(model.list.length>0)
//       {
//               var obj = new Object();
//               obj.id = -1;
//               obj.name = "所有警员";
//               obj.sort_id = -1;
//               obj.unit_id =  1;
//               model.list.splice(0,0,obj);

//       }

// 			if(model.list.length>0)
// 			setTimeout(function(){
// 				$(".dep p").on("tap",function(){
// 					model.loadpolice(model.list[$('.dep p').index($(this))].id,$('.dep p').index($(this)));
// 				});

// 			},1);
// }

// function LoadPoliceData(){
//      var d=  client.getAllPolice();
//           var data = eval("("+d+")");
//             model.policelist=data; 
//             if(model.policelist.length>0){
//               project.totalpage = parseInt((model.policelist.length)/8 +1);
//             }
//            setTimeout(function(){
//                   $(".avc").on("tap",function(){
//                     var map = model.policelist[$('.avc').index($(this))];
//            model.gotoedit(map.id,map.image,map.phone,map.name,map.position);
//         });
//            },1);
 

// }


function initdata(){

      model.LoadData();      
      model.LoadPoliceData();
}


function method(flag,str){
   model.edit(str);
}
