var roundSet;
var round;
var userName="财鸟进阶学员";
var current;
window.onload = function() {
	//loadTables();
	checkUser();
	loadMenu();
}

//初始化表格
function loadTables(r) {
	$('[round='+r+'] .table1').load('table1.html');
	$('[round='+r+'] .table2').load('table2.html');
	$('[round='+r+'] .table3').load('table3.html');
}
//初始化登录信息
function checkUser(){
	var data = {
			serviceId:1,
			actionId:4,
			content:'{}'
		};
	$.post("/BaseAction",
			JSON.stringify(data),
			function(rsp){
	       		if(rsp.data==null) {
	       			$("#noName").show();
	       			userName = "请登录";
	       		} else {
	       			$("#userName").html(rsp.data.name);
	       			$("#hasName").show();
	       			userName = rsp.data.name == null?"请登录":rsp.data.name;
	       		}
	       		$("#userName1").html(userName);
	       		$('.grade').html(rsp.grade);
	    },"json");
}

//初始化菜单栏
function loadMenu() {
	var btnList = '<li class="selected" id="home"><a onclick="showIndex()">沙盘游戏</a></li>';
	for(var i=1;i<=11;i++) {
		var btn = '<li id="r'+i+'"><a onclick="checkRound('+i+')">第'+i+'轮</a></li>';
		btnList += btn;
	}
	$("#roundTab").html(btnList);
	var roundDivContent = "<div class=\"table1\"></div>" +
		"<div class=\"direct\"><img src=\"img/timg.jpeg\" /></div>"+
		"<div class=\"table3\"></div>" +
		"<div class=\"table2\"></div>";
	
	var roundContent = "";
	for(var i=1;i<=11;i++) {
		roundContent+="<div class=\"roundDiv\" round=\""+i+"\" style=\"display:none;\">" + roundDivContent + "</div>";
	}
	$("#roundContent").html(roundContent);
}
		
function showIndex() {
	$("#roundContent").hide();
	$('#myTabContent').show();
	$('.selected').removeClass("selected");
	$('#home').addClass("selected");
}

//轮次点击事件
function checkRound(r) {
	$('#myTabContent').hide();
	$("#roundContent").show();

	//原来的div
	var rr = $('.roundDiv[current=1]');
	//选中的div
	var roundDiv = $('[round='+r+']');
	
	$('#roundStep').val(r);
	
	var select = roundDiv.attr("select");
	if(select==1) {
		if(rr != null && rr != undefined) {
			rr.hide();
			rr.removeAttr('current');
		} 
		roundDiv.show();
		roundDiv.attr('current',1);
		//设置css样式
		$('.selected').removeClass("selected");
		$('#r'+r).addClass("selected");
		return;
	}
	
	//没有进入过就加载表格和请求数据
	loadTables(r);
	var table1 = roundDiv.children('.table1');
	var table2 = roundDiv.children('.table2');
	var table3 = roundDiv.children('.table3');
	var data = {
			serviceId:1,
			actionId:2,
			content:'{\"round\":'+r+'}'
		};
	$.post("/BaseAction",
			JSON.stringify(data),
			
			function(rsp){
				if(rsp.status==3) {
					alert(rsp.msg);
					roundDiv.removeAttr('current');//下次可以重新加载
					return;
				}
	       		if(rsp.data.currentRound >= r) {
	       			roundDiv.attr('select',1);
	       			$('#myTabContent').hide();
	       			//设置css样式
	       			$('.selected').removeClass("selected");
	       			$('#r'+r).addClass("selected");
	       			if(rr != null && rr != undefined) {
	       				rr.hide();
	       				rr.removeAttr('current');
	       			} 
	       			roundDiv.show();
	       			roundDiv.attr('current',1);
	       			
	       			//显示单价信息
	       			if(rsp.data.roundSet != null) {
	       				roundSet = rsp.data.roundSet;
	       				for(var p in roundSet) {
	       					if(typeof(roundSet[p]) != "function") {
	       						if(p.indexOf('PriceChange') > 0) {
	       							//显示涨幅
	       							var priceChange = roundSet[p];
	       							var color = '';
	       							if(priceChange>=0) {
	       								color = 'red';
	       							} else {
	       								color = 'green';
	       							}
	       							var changeFont = '<font color="'+color+'">' + (priceChange*100).toFixed(1) + '%</font>';
	       							table1.find('.'+p).html(changeFont);
	       						} else {
	       							table1.find('.'+p).html(roundSet[p]);
	       						}
	       						//显示利率
	       						table2.find('.'+p).html((roundSet[p]*100).toFixed(1)+'%');
	       						//显示单价
	       						table3.find('.'+p).html(roundSet[p]);
	       					}
	       				}
	       				table1.find('.housePrice').html(roundSet['housePrice']+' | '+roundSet['housePayPrice']);
	       				table1.find('.landPrice').html(roundSet['landPrice']+' | '+roundSet['landPayPrice']);
	       				table3.find('.housePrice').html(roundSet['housePrice']+' | '+roundSet['housePayPrice']);
	       				table3.find('.landPrice').html(roundSet['landPrice']+' | '+roundSet['landPayPrice']);
	       			}
	       			
	       			//显示期初数据
	       			if(rsp.data.roundStart != null) {
	       				roundStart = rsp.data.roundStart;
	       				for(var p in roundStart) {
	       					console.log(p);
	       					console.log(parseFloat(roundStart[p]).toString());
	       					if(typeof(roundStart[p]) != "function") {
	       						var value = 0;
	       						if(roundStart[p] != undefined && 
	       								parseFloat(roundStart[p]).toString() != "NaN" &&
	       								roundStart[p] != 0) {
	       							value = parseFloat(roundStart[p]).toFixed(2);
	       							
	       						}
	       						table1.find('.'+p).html(value);
	       					}
	       				}
	       				//显示期初现金
	       				table2.find('.cashStart').html(roundStart['cash']);
	       				table2.find('.surplus').html(roundStart['surplus']);
	       				var bankLimit = roundStart['netAsset']*0.1;
   						if(bankLimit > 100) {
   							bankLimit = 100;
   						}
   						console.log('banklimit:' + bankLimit);
   						table2.find('.bankLimit').html(bankLimit.toFixed(2));
	       				
   						table2.find('.rentStart').html(roundStart['rent'].toFixed(2));
   						table2.find('.deptToAsset').html((roundStart['deptToAsset']*100).toFixed(2) + '%');
	       			}
	       			//显示期末数据
	       			if(rsp.data.roundEnd != null) {
		       			var roundEnd = rsp.data.roundEnd;
		       			for(var p in roundEnd) {
	       					if(typeof(roundEnd[p]) != "function") {
	       						if(roundEnd.id>0) {
	       							table2.find('.'+p).val(roundEnd[p]);
	       						}
	       						table2.find('.'+p).html(roundEnd[p]);
	       						table2.find('.'+p).val(roundEnd[p]);
	       						table3.find('.'+p).html(roundEnd[p]);
//	       						if(p.indexOf('Interest') >= 0) {
//	       							console.log(p);
//	       							table1.find('.'+p).html(roundEnd[p]);
//	       						}
	       					}
	       				}
		       		//银行授信额度上限
   						var bankLimit = roundEnd['netAsset']*0.1;
   						if(bankLimit > 100) {
   							bankLimit = 100;
   						}
   						console.log('banklimit:' + bankLimit);
   						table2.find('.bankLimit').html(bankLimit);
		       			table2.find('.deptToAsset').html((roundEnd['deptToAsset']*100).toFixed(2) + '%');
		       			if(rsp.data.roundEnd.status==1) {
		       				$("#submitRound").hide();
			       			$('#unlock').show();
			       			table2.find('input[type="text"]').attr("disabled","disabled");
			       			table2.find('input').css('background-color','#E3E3E3');
		       			}
		       			
	       			} else {
	       				$("#submitRound").show();
		       			$('#unlock').hide();
	       			}
					$("#roundContent").show();
	       		} else {
	       			alert("本轮尚未开启，请等待主持人口令");
	       		}
	    },"json");
	
}

//用户登录
function submitName() {
	var name = $("#name").val();
	var data = {
			serviceId:1,
			actionId:3,
			content:'{name:\"'+name+'\"}'
		};
	$.post("/BaseAction",
			JSON.stringify(data),
			function(rsp){
	       		if(rsp.status==1) {
	       			location.reload();
	       		}
	    },"json");
}
	
//用户注销
function logout() {
	if(!window.confirm('确定退出吗？')) {
		return;
	}
	var data = {
			serviceId:1,
			actionId:5,
			content:'{}'
		};
	$.post("/BaseAction",
			JSON.stringify(data),
			function(rsp){
	       		if(rsp.status == 1) {
	       			alert("注销成功！");
	       			location.reload();
	       		}
	    },"json");
}

function isInteger(obj) {
	 return typeof obj === 'number' && obj%1 === 0
	}


function fromToJson(roundDiv,form) {  
    var result = {};  
    var fieldArray = roundDiv.find('.' + form).serializeArray();  
    for (var i = 0; i < fieldArray.length; i++) {  
        var field = fieldArray[i];  
        if (field.name in result) {  
            result[field.name] += ',' + field.value;  
        } else {  
        	if(field.value != null && field.value != '' && field.value != undefined) {
        		result[field.name] = field.value;  
        	}
        }  
    }  
    return result;  
}  

function view() {
//	var r = $('#roundStep').val();
	var roundDiv = $('.roundDiv[current=1]');
	var formData = fromToJson(roundDiv,"changeForm");
	var r = roundDiv.attr('round');
	var table1 = roundDiv.children('.table1');
	var table2 = roundDiv.children('.table2');
	var table3 = roundDiv.children('.table3');
	
	formData['round'] = r;
	formData['view']= 1;
//	var name = $("#name").val();
	var data = {
			serviceId:1,
			actionId:6,
			content:JSON.stringify(formData)
		};
	$.post("/BaseAction",
			JSON.stringify(data),
			function(rsp){
			if(rsp.status==2) {
				alert(rsp.msg);
				return;
			}
	       		if(rsp.status==1) {
	       			if(rsp.data != null) {
	       				var roundEnd = rsp.data;
	       				console.log(roundEnd);
	       				for(var p in roundEnd) {
	       					if(typeof(roundEnd[p]) != "function") {
	       						if(p=='bankLimit') {
	       							continue;
	       						}
	       						var value = 0;
	       						if(roundEnd[p] != undefined && 
	       								parseFloat(roundStart[p]).toString() != "NaN" &&
	       								roundEnd[p] != 0) {
	       							value = parseFloat(roundEnd[p]).toFixed(2);
	       							
	       						}
//			       				console.log(p + "--:" + roundEnd[p]);
	       						table3.find('.'+p).html(value);
	       						table2.find('span.'+p).html(value);
	       					}
	       				}
	       				//显示期初现金
	       				table2.find('.cash').html(roundEnd['cash'].toFixed(2));
	       				table2.find('.surplus').html(roundEnd['surplus']);
	       				table2.find('.deptToAsset').html((roundEnd['deptToAsset']*100).toFixed(2) + '%');
	       				table3.find('.netAsset').html(roundEnd['netAsset'].toFixed(2));
	       			}
	       		} 
	    },"json");
}

//提交数据
function submitRound() {
	if(!window.confirm('确定提交吗？')) {
		return;
	}
	
	var roundDiv = $('.roundDiv[current=1]');
	var formData = fromToJson(roundDiv,"changeForm");
	var r = roundDiv.attr('round');
	var table1 = roundDiv.children('.table1');
	var table2 = roundDiv.children('.table2');
	var table3 = roundDiv.children('.table3');
	
	formData['round'] = r;
//	var name = $("#name").val();
	var data = {
			serviceId:1,
			actionId:6,
			content:JSON.stringify(formData)
		};
	$.post("/BaseAction",
			JSON.stringify(data),
			function(rsp){
	       		alert(rsp.msg);
	       		if(rsp.status==1) {
	       			$("#submitRound").hide();
	       			$('#unlock').show();
	       			table2.find('.cashflow input[type="text"]').attr("disabled","disabled");
	       			table2.find('.cashflow input').css('background-color','#E3E3E3');
	       		}
	    },"json");
}

function showLock() {
	var lock = $('#unlock').attr('lock');
	if(lock=='1') {
		$('#unlock').val("收起");
		$('#unlockDiv').show();
		$('#unlock').attr('lock','0');
	} else {
		$('#unlock').val("解锁");
		$('#unlockDiv').hide();
		$('#unlock').attr('lock','1');
	}
}

function unlock() {
//	var r = $('#roundStep').val();
	var roundDiv = $('.roundDiv[current=1]');
	var r=roundDiv.attr('round');
	var table1 = roundDiv.children('.table1');
	var table2 = roundDiv.children('.table2');
	var table3 = roundDiv.children('.table3');
	
	var pwd = $('#lockField').val();
	var content = {};
	content['pwd']=pwd;
	content['round']=r;
	var data = {
			serviceId:1,
			actionId:7,
			content:JSON.stringify(content)
		};
	$.post("/BaseAction",
			JSON.stringify(data),
			function(rsp){
	       		alert(rsp.msg);
	       		if(rsp.status==1) {
	       			$('#unlock').hide();
	       			$('#unlockDiv').hide();
	       			$('#unlock').val("解锁");
	       			$("#submitRound").show();
	       			table2.find('.cashflow input').removeAttr("disabled");
	       			table2.find('.cashflow input').css('background-color','white');
	       		}
	    },"json");
}

function IsNum(e) {
    var k = window.event ? e.keyCode : e.which;
    if (((k >= 48) && (k <= 57)) || k == 8 || k == 0) {
    } else {
        if (window.event) {
            window.event.returnValue = false;
        }
        else {
            e.preventDefault(); //for firefox 
        }
    }
} 