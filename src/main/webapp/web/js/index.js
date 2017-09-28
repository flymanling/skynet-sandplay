var roundSet;
var round;
var userName="财鸟进阶学员";
var current;
window.onload = function() {
	loadTables();
	checkUser();
	loadMenu();
}
//初始化表格
function loadTables() {
	$('#table1').load('table1.html');
	$('#table2').load('table2.html');
	$('#table3').load('table3.html');
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
	for(var i=1;i<=10;i++) {
		var btn = '<li id="r'+i+'"><a onclick="checkRound('+i+')">第'+i+'轮</a></li>';
		btnList += btn;
	}
	$("#roundTab").html(btnList);
}
		
function showIndex() {
	$("#roundContent").hide();
	$('#myTabContent').show();
	$('.selected').removeClass("selected");
	$('#home').addClass("selected");
}

//轮次点击事件
function checkRound(r) {
	//$('#myTabContent').hide();
	//重置操作
	$('#table2 .cashflow input[type="text"]').val('');
	$('#table2 .cashflow input[type="text"]').css('background-color','white');
	$('#table2 .cashflow input').removeAttr("disabled");
	
	$('#table3 span').html('0');
//	$('#table3 td').css('background-color','#00BFFF');
	
	$('#roundStep').val(r);
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
					return;
				}
	       		if(rsp.data.currentRound >= r) {
	       			$('#myTabContent').hide();
	       			//设置css样式
	       			$('.selected').removeClass("selected");
	       			$('#r'+r).addClass("selected");
	       			
	       			//显示单价信息
	       			if(rsp.data.roundSet != null) {
	       				roundSet = rsp.data.roundSet;
	       				for(var p in roundSet) {
	       					if(typeof(roundSet[p]) != "function") {
	       						$('#table1 .'+p).html(roundSet[p]);
	       						//显示利率
	       						$('#table2 .'+p).html(roundSet[p]*100+'%');
	       						//显示单价
	       						$('#table3 .'+p).html(roundSet[p]);
	       					}
	       				}
   						$('#table1 .housePrice').html(roundSet['housePrice']+' | '+roundSet['housePayPrice']);
   						$('#table1 .landPrice').html(roundSet['landPrice']+' | '+roundSet['landPayPrice']);
   						$('#table3 .housePrice').html(roundSet['housePrice']+' | '+roundSet['housePayPrice']);
   						$('#table3 .landPrice').html(roundSet['landPrice']+' | '+roundSet['landPayPrice']);
	       			}
	       			
	       			//显示上一轮配置
	       			if(rsp.data.round != null) {
	       				round = rsp.data.round;
	       				for(var p in round) {
	       					if(typeof(round[p]) != "function") {
//			       				console.log(p + ":" + round[p]);
	       						$('#table1 .'+p).html(round[p]);
	       						$('#table2 .'+p).html(round[p]);
	       					}
	       				}
	       				//显示期初现金
	       				$('#table2 .old_cash').html(round['cashAfter']);
	       				$('#table2 .surplus').html(round['surplus']);
	       				//银行授信额度上限
   						var bankLimit = round['netAssetAfter']*0.1;
   						if(bankLimit > 100) {
   							bankLimit = 100;
   						}
   						$('#table2 .bankLimit').html(bankLimit);
   						$('#table2 .deptToAsset').html(round['deptToAsset']*100 + '%');
	       			}
	       			if(rsp.data.roundNow != null) {
		       			var roundNow = rsp.data.roundNow;
		       			for(var p in roundNow) {
	       					if(typeof(roundNow[p]) != "function") {
//			       				console.log(p + ":" + round[p]);
	       						$('#table2 .'+p).val(roundNow[p]);
	       						$('#table3 .'+p).html(roundNow[p]);
//	       						$('#table2 .'+p).attr("disabled","disabled");
//	       						$('#table2 .'+p).css('background-color','#E3E3E3');
	       					}
	       				}
		       			$('#table2 .deptToAsset').html(roundNow['deptToAsset']*100 + '%');
		       			if(rsp.data.roundNow.status==1) {
		       				$("#submitRound").hide();
			       			$('#unlock').show();
		       				$('#table2  input[type="text"]').attr("disabled","disabled");
		       				$('#table2  input').css('background-color','#E3E3E3');
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

//预览
function view1(fresh) {
	var r = $('#roundStep').val();
	if(!fresh) {
		$('#table2 .cashflow input').css('background-color','white');
	}
	$('#table3 td').css('background-color','#90EE90');
	var check = false;
	for(var p in round) {
		if(typeof(round[p]) != "function") {
			var item = p.replace('Change','');
//			console.log('item:' + item + ', p:' + p);
			var change = $('#table2 .'+p+'Change').val();
//			console.log(item + " value :" + change);
			if(change == undefined) {
				continue;
			}
			if(change == "" || change == null) {
				change = 0;
			}
			change = parseInt(change);
			
//			console.log(item + ' change:' + change);
			var amount = 0;
			if(item=='house' || item=='land') {
				var amountChange = change*roundSet[item+'Price'];
				amount = r==1?amountChange:round[item+''] + amountChange;
			//	console.log('amount:' + amount);
			} else {
				amount = r==1?change:round[item+''] + change;
			}
			var price = roundSet[item+'Price'];
			if(item=='house' || item=='land') {
				if(change>0) {
					price = roundSet[item+'PayPrice'];
				}
			//	console.log(item + ' price:' + price);
			}
//			if(price > 0) {
//				var temp = change%price;
//				if(change>0&&temp>0 && isInteger(price)) {
//					check = true;
//					$('#table2 .'+p+'Change').css('background-color','#FF6A6A');
//					$('#table3 .' + item + "Num").parent().css('background-color','#FF6A6A');
//					continue;
//				}
//			}
			var num = amount/price;
			if(item=='house'||item=='land') {
				num = amount/roundSet[item+'Price'];
			}
			$('#table3 .' + item + "Num").html(num);
			$('#table3 .'+item+'Amount').html(amount);
		}
	}
	if(check) {
		alert("数额必须为单价的整数倍！");
	}
	return check;
}

function fromToJson(form) {  
    var result = {};  
    var fieldArray = $('#' + form).serializeArray();  
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
	var formData = fromToJson("changeForm");
	var r = $('#roundStep').val();
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
	       				round = rsp.data;
	       				for(var p in round) {
	       					if(typeof(round[p]) != "function") {
//			       				console.log(p + ":" + round[p]);
	       						$('#table3 .'+p).html(round[p]);
	       						$('#table2  span.'+p).html(round[p]);
	       					}
	       				}
	       				$('#table2 .deptToAsset').html(round['deptToAsset']*100 + '%');
	       			}
	       		} 
	    },"json");
}

//提交数据
function submitRound() {
	if(!window.confirm('确定提交吗？')) {
		return;
	}
//	var check = view();
//	if(check) {
//		return;
//	}
	var formData = fromToJson("changeForm");
	var r = $('#roundStep').val();
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
	       			$('#table2 .cashflow input[type="text"]').attr("disabled","disabled");
	       			$('#table2 .cashflow input').css('background-color','#E3E3E3');
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
	var r = $('#roundStep').val();
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
	       			$('#table2 .cashflow input').removeAttr("disabled");
	       			$('#table2 .cashflow input').css('background-color','white');
	       		}
	    },"json");
}