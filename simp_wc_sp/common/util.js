import CryptoJS from '@/common/crypto';
import app from '@/common/app.js'; //公共变量

function strcharacterDiscode(str) {
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

function formatTime(time) {
    if (typeof time !== 'number' || time < 0) {
        return time
    }

    var hour = parseInt(time / 3600)
    time = time % 3600
    var minute = parseInt(time / 60)
    time = time % 60
    var second = time

    return ([hour, minute, second]).map(function(n) {
        n = n.toString()
        return n[1] ? n : '0' + n
    }).join(':')
}

function compareVersion(v1, v2) {
    v1 = v1.split('.')
    v2 = v2.split('.')
    const len = Math.max(v1.length, v2.length)

    while (v1.length < len) {
        v1.push('0')
    }
    while (v2.length < len) {
        v2.push('0')
    }

    for (let i = 0; i < len; i++) {
        const num1 = parseInt(v1[i])
        const num2 = parseInt(v2[i])

        if (num1 > num2) {
            return 1
        } else if (num1 < num2) {
            return -1
        }
    }

    return 0
}


function formatLocation(longitude, latitude) {
    if (typeof longitude === 'string' && typeof latitude === 'string') {
        longitude = parseFloat(longitude)
        latitude = parseFloat(latitude)
    }

    longitude = longitude.toFixed(2)
    latitude = latitude.toFixed(2)

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
     * 获取年月日yyyy-mm-dd
     */
    getDate(date) {
        var year = date.getFullYear(); //年
        var month = date.getMonth() + 1; //月
        var day = date.getDate(); //日
        return year + "-" + month + "-" + day;
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
        return year + "年" + month + "月" + day + "日";
    }
};
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

function getBaiduToken(apiKey, secKey) {
    return new Promise((resolve, reject) => {
        const tokenUrl = 'https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=' +
            apiKey + '&client_secret=' + secKey;
        uni.request({
            url: tokenUrl,
            method: 'POST',
            dataType: "json",
            header: {
                'content-type': 'application/json; charset=UTF-8'
            },
            success: (res) => {
                resolve(res);
            },
            fail: (res) => {
                uni.hideLoading();
                uni.showToast({
                    title: '网络错误，请重试！',
                    icon: 'none',
                    duration: 2000
                })
                reject(res);
            },
            complete: (res) => {
                resolve(res);
            }
        })
    })
}

function getImgIdentify(tokenUrl, data) {
    return new Promise((resolve, reject) => {
        const detectUrl = `https://aip.baidubce.com/rest/2.0/face/v3/detect?access_token=${tokenUrl}`;
        uni.request({
            url: detectUrl,
            data: data,
            method: 'POST',
            dataType: "json",
            header: {
                'content-type': 'Content-Type:application/json; charset=UTF-8'
            },
            success: (res) => {
                resolve(res);
            },
            fail: (res) => {
                uni.hideLoading();
                uni.showToast({
                    title: '网络错误，请重试！',
                    icon: 'none',
                    duration: 2000
                })
                reject(res);
            },
            complete: (res) => {
                resolve(res);
            }
        })
    })
}

function getStatPoint(eventName, eventId) {

    // if(app.is_point){
    //     const statApp = getApp();
    //     statApp.stat.track(eventName, {EVENT_DESCRIBE_ID: eventId,_userId:uni.getStorageSync('userInfo').userId});
    // }

}

module.exports = {
    formatTime: formatTime,
    formatLocation: formatLocation,
    dateUtils: dateUtils,
    Encrypt: Encrypt,
    toFix: toFix,
    getBaiduToken: getBaiduToken,
    getImgIdentify: getImgIdentify,
    getStatPoint: getStatPoint,
    strcharacterDiscode: strcharacterDiscode,
    compareVersion: compareVersion
}
