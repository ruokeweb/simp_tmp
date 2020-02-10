import CryptoJS from '@/common/crypto';
import app from '@/common/app.js';
import store from '@/store/index.js';

function formatTime(time) {
	if (typeof time !== 'number' || time < 0) {
		return time;
	}
	var hour = parseInt(time / 3600);
	time = time % 3600;
	var minute = parseInt(time / 60);
	time = time % 60;
	var second = time;
	return ([hour, minute, second]).map(function(n) {
		n = n.toString();
		return n[1] ? n : '0' + n;
	}).join(':');
}

function formatLocation(longitude, latitude) {
	if (typeof longitude === 'string' && typeof latitude === 'string') {
		longitude = parseFloat(longitude);
		latitude = parseFloat(latitude);
	}

	longitude = longitude.toFixed(2);
	latitude = latitude.toFixed(2);

	return {
		longitude: longitude.toString().split('.'),
		latitude: latitude.toString().split('.')
	}
}

function toFix(value) {
	return parseFloat(value.toFixed(1)); //此处1为保留两位小数
}

var dateUtils = {
	UNITS: {
		'年': 31557600000,
		'月': 2629800000,
		'天': 86400000,
		'小时': 3600000,
		'分钟': 60000,
		'秒': 1000
	},
	humanize: function(milliseconds) {
		var humanize = '';
		for (var key in this.UNITS) {
			if (milliseconds >= this.UNITS[key]) {
				humanize = Math.floor(milliseconds / this.UNITS[key]) + key + '前';
				break;
			}
		}
		return humanize || '刚刚';
	},
	format: function(dateStr) {
		var date = this.parse(dateStr)
		var diff = Date.now() - date.getTime();
		if (diff < this.UNITS['天']) {
			return this.humanize(diff);
		}
		var _format = function(number) {
			return (number < 10 ? ('0' + number) : number);
		};
		return date.getFullYear() + '/' + _format(date.getMonth() + 1) + '/' + _format(date.getDay()) + '-' +
			_format(date.getHours()) + ':' + _format(date.getMinutes());
	},
	parse: function(str) { //将"yyyy-mm-dd HH:MM:ss"格式的字符串，转化为一个Date对象
		var a = str.split(/[^0-9]/);
		return new Date(a[0], a[1] - 1, a[2], a[3], a[4], a[5]);
	},
	getYearByDate: function(date) {
		return date.split("-")[0];
	},
	ConverStringToDate: (time, separator) => {
		if (!separator) {
			separator = "-";
		}
		let dateArr = time.split(separator);
		let chinese = ['零', '一', '二', '三', '四', '五', '六', '七', '八', '九'];
		let y = dateArr[0];
		let m = dateArr[1];
		let d = dateArr[2];
		let result = "";
		for (let i = 0; i < y.length; i++) {
			result += chinese[y.charAt(i)];
		}
		result += "年";
		if (m.length == 2) {
			if (m.charAt(0) == "1" && m.charAt(1) != "0") {
				result += ("十" + chinese[m.charAt(1)] + "月");
			} else if (m.charAt(0) == "1" && m.charAt(1) == "0") {
				result += "十月";
			} else {
				result += (chinese[m.charAt(1)] + "月");
			}
		} else {
			result += (chinese[m.charAt(0)] + "月");
		}
		if (d.length == 2) {
			if (d.charAt(0) != "0") {
				result += (chinese[d.charAt(0)] + "十" + chinese[d.charAt(1)] + "日");
			} else if (d.charAt(1) == "0") {
				result += (chinese[d.charAt(0)] + "十日");
			} else {
				result += (chinese[d.charAt(1)] + "日");
			}
		} else {
			result += (chinese[d.charAt(0)] + "日");
		}
		return result;
	},
	/**
	 * 计算n天后的日期
	 * initDate：开始日期，默认为当天日期， 格式：yyyymmdd/yyyy-mm-dd
	 * days:天数
	 * flag：返回值， 年与日之间的分隔符， 默认为xxxx年xx月xx日格式
	 */
	getDateAfter_n: (initDate, days, flag) => {

		if (!days) {
			return initDate;
		}
		initDate = initDate.replace(/-/g, '');
		var date;
		// 是否设置了起始日期
		if (!initDate) { // 没有设置初始化日期，就默认为当前日期
			date = new Date();
		} else {
			var year = initDate.substring(0, 4);
			var month = initDate.substring(4, 6);
			var day = initDate.substring(6, 8);
			date = new Date(year, month - 1, day); // 月份是从0开始的
		}
		date.setDate(date.getDate() + days);

		var yearStr = date.getFullYear();
		var monthStr = ("0" + (date.getMonth() + 1)).slice(-2, 8); // 拼接2位数月份
		var dayStr = ("0" + date.getDate()).slice(-2, 8); // 拼接2位数日期
		var result = "";
		if (!flag) {
			result = yearStr + "年" + monthStr + "月" + dayStr + "日";
		} else {
			result = yearStr + flag + monthStr + flag + dayStr + flag;
		}
		return result;
	},
	/**
	 * date yyyy-MM-dd
	 */
	format_date: (initDate) => {
		var date = initDate.split("-");
		var year = date[0];
		var month = date[1];
        var day = date[2];
        var dayTime=date[2].split(" ");
        var time="";
        if(date[2].split(" ").length>1){
            day=dayTime[0];
            time=dayTime[1];
        }
		return year + "年" + month + "月" + day + "日" +" " + time;
	}
};

function dateFormat(fmt, date) {
    let ret;
    let opt = {
        "Y+": date.getFullYear().toString(),        // 年
        "m+": (date.getMonth() + 1).toString(),     // 月
        "d+": date.getDate().toString(),            // 日
        "H+": date.getHours().toString(),           // 时
        "M+": date.getMinutes().toString(),         // 分
        "S+": date.getSeconds().toString()          // 秒
        // 有其他格式化字符需求可以继续添加，必须转化成字符串
    };
    for (let k in opt) {
        ret = new RegExp("(" + k + ")").exec(fmt);
        if (ret) {
            fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
        };
    };
    return fmt;
}


//返回ECB加密方式
function getECBMode() {
	var ECB = CryptoJS.lib.BlockCipherMode.extend();
	ECB.Encryptor = ECB.extend({
		processBlock: function(words, offset) {
			this._cipher.encryptBlock(words, offset);
		}
	});
	ECB.Decryptor = ECB.extend({
		processBlock: function(words, offset) {
			this._cipher.decryptBlock(words, offset);
		}
	});
	return ECB;
}
//加密方法
function Encrypt(key, value) {
	key = CryptoJS.enc.Utf8.parse(key);
	value = CryptoJS.enc.Utf8.parse(value);
	var encrypted = CryptoJS.AES.encrypt(value, key, {
		mode: getECBMode(),
		padding: CryptoJS.pad.Pkcs7
	});
	return encrypted.toString();
}

//跳转任意页
function toNavigatePage(url){
    uni.hideLoading();
    if(!url){
        uni.showToast({title: '暂未开通，敬请期待...',icon:"none"});
        return;
    }
    uni.navigateTo({
        url:url
    })
}

//跳转到菜单页
function toPage(url){
    if(url==""||url=="undefined"){
        //uni.showToast({title: '暂未开通，前往首页中...',icon:"none"});
        url=app.MENU_PAGE.INDEX;
    }
    if(url==app.MENU_PAGE.INDEX ||url==app.MENU_PAGE.MALL|| url==app.MENU_PAGE.COURSE||url==app.MENU_PAGE.MINE){
        uni.switchTab({
            url: url
        });
    }else{
        uni.navigateTo({
            animationType: 'pop-in',
            url:url
        })
    }
}

function isLogin(){
    var loginStatus=false;
    //判断并获取用户登录信息
    var token=store.state.token;
    var tokenTime=store.state.tokenTime;
    var userInfo=uni.getStorageSync(app.storageKey.userInfo);
    
    //token 过期时间判断
    var nowTokenTime=parseInt(tokenTime)+(parseInt(app.storageKey.tokenDate)*24*60*1000);
    var nowTime=(new Date()).valueOf();
    var isUsed=nowTime-nowTokenTime-(parseInt(app.storageKey.tokenCycle)*24*60*1000);
    
    //判断登录状态
    if(userInfo!=""&&token!=""&&isUsed>0){
         loginStatus=true;
         store.commit('updateIsLogin',loginStatus);
    }
    if(isUsed<=0){
        store.commit('updateIsLogin',loginStatus);
    }
    return loginStatus;
}

function strCharacterDiscode(str) {
  // 加入常用解析
  str = str.replace(/&nbsp;/g, ' ');
  str = str.replace(/&quot;/g, "'");
  str = str.replace(/&amp;/g, '&');
  str = str.replace(/&lt;/g, '<');
  str = str.replace(/&gt;/g, '>');
  str = str.replace(/&#8226;/g, '•');
  str = str.replace(/&ldquo;/g, '“');
  str = str.replace(/&rdquo;/g, '”');
  return str;
}

/*通过typeCode 获取下面的dict对象集合(字典表)*/
function getDictsByTypeCode(typeCode){
    return uni.getStorageSync(app.CACHE_NAME.DICT)[typeCode];
}
    
/*通过 value 获取 该字典对象((字典表))*/
function getDictByValue(value, typeCode) {
    if (value.length == 0 || typeCode.length == 0) {
        return "";
    } else {
        return uni.getStorageSync(app.CACHE_NAME.DICTKV)[typeCode+value];
    }
}

function getCourseTypeIcon(type){
	var icon="icon-jewelry";
	if(type=="PIANO"){
		icon="icon-aoli-gangqin";
	}else if(type=="GUITAR"){
		icon="icon-jita";
	}else if(type=="GUZHENG"){
		icon="icon-icon-test-copy";
	}else if(type=="ASSESS"){
		icon="icon-pinggu1";
	}else if(type=="PSYCHOLOGY"){
		icon="icon-xinli";
	}
	return icon;
}

function getWeekName(num){
    var name="星期";
    if(num==1){
        name="星期一";
    }else if(num==2){
        name="星期二";
    }else if(num==3){
        name="星期三";
    }else if(num==4){
        name="星期四";
    }else if(num==5){
        name="星期五";
    }else if(num==6){
        name="星期六";
    }else if(num==7){
        name="星期日";
    }
    return name;
}

function getDayToWeek(str){
    var dates = new Date(str);
    var week="";
    if(dates.getDay()==0) week="星期日"
    if(dates.getDay()==1) week="星期一"
    if(dates.getDay()==2) week="星期二"
    if(dates.getDay()==3) week="星期三"
    if(dates.getDay()==4) week="星期四"
    if(dates.getDay()==5) week="星期五"
    if(dates.getDay()==6) week="星期六"
    return week;
}

//输出
module.exports = {
	formatTime: formatTime,
	formatLocation: formatLocation,
	dateUtils: dateUtils,
	Encrypt: Encrypt,
	toFix: toFix,
    toPage: toPage,
    strCharacterDiscode:strCharacterDiscode,
    isLogin:isLogin,
    getDictsByTypeCode:getDictsByTypeCode,
    getDictByValue:getDictByValue,
	getCourseTypeIcon:getCourseTypeIcon,
    getWeekName:getWeekName,
    getDayToWeek:getDayToWeek,
    dateFormat:dateFormat
}
