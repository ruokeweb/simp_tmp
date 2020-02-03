<template>
    <view class="content">
        <view class="content_view">
            <view class="content_view_item">
                <text class="text_reg">活动主题</text>
                <input class="input_reg" style="width:50%" placeholder="写个吸引小伙伴的活动主题" placeholder-class="placeholderStyle"
                    v-model.trim="title" data-key="title" maxlength=30 />
            </view>
            <view class="content_view_item">
                <text class="text_reg">活动内容</text>
            </view>
            <textarea value="" placeholder="写个吸引小伙伴的活动内容" style="font-size: 30upx;" v-model.trim="content"
                placeholder-class="placeholderStyle" />
            <view class="content_view_item">
                <text class="text_reg">预计返校人数</text>
                <input class="input_reg" style="width:30%" placeholder="请输入预计返校人数" placeholder-class="placeholderStyle"
                    v-model.trim="num" data-key="num" maxlength=5 />
            </view>
			<view class="content_view_item">
			    <text class="text_reg">报名截止日期</text>
				<!-- <image src="/static/left.png" mode=""></image> -->
				<view class="input_reg">
					<view class="uni-list-cell-db"> 
						<picker mode="date" :value="endDate" :start="startDate"  @change="bindDateChange">
							<view class="uni-input">{{endDate}}</view>
						</picker> 
					</view>
				</view>
               
			</view>
			<view class="content_view_item">
			    <text class="text_reg">返校时间</text>
				<!-- <image src="/static/left.png" mode=""></image> -->
                <view class="input_reg">
					 <view class="uni-list-cell-db"> 
						 <picker mode="date" :value="backDate" :start="startDate" @change="backDateChange" >
							 <view class="uni-input">{{backDate}}</view>
						 </picker> 
					</view>
				</view>
			</view>
            <view class="content_view_item">
                <text class="text_reg">是否需要校友会接待</text>
                <switch class="switch" @change="receiveChange" :checked="isReception" />
            </view>
            
             <view v-show="isReception">
               <view class="uni-list">
                   <text class="text_reg">您拟服务需求清单</text>			   
                    <checkbox-group @change="checkboxChange">
                        <label class="uni-list-cell uni-list-cell-pd" v-for="item in items" :key="item.name">
                            <view>
                                <checkbox :value="item.label" :checked="item.checked" />
                            </view>
                            <view>{{item.label}}</view>
                        </label>
                   </checkbox-group>
                    <view class="content_view_item">
                       <text class="text_reg">联系人:</text>
                       <input class="input_reg" placeholder="联系人" v-model="conName" data-key="conName" maxlength=11 placeholder-class="placeholderStyle"/>
                   </view>
                   <view class="content_view_item">
                      <text class="text_reg">联系方式：</text>
                      <input class="input_reg" placeholder="手机号" v-model="conPhone" data-key="conPhone" maxlength=11 placeholder-class="placeholderStyle"/>
                   </view>
               </view>
            </view>          
        </view>  
        <form @submit="submit" report-submit="true">
            <view class="input-row">
                <button type="primary" class="primary btn_dark bottom-but"  :disabled="saveDisable" formType="submit">发起</button>
            </view>
        </form>
          
        
        
    </view>
</template>

<script>
    function getDate(type) {
    	const date = new Date();
    
    	let year = date.getFullYear();
    	let month = date.getMonth() + 1;
    	let day = date.getDate();
    
    	if (type === 'start') {
    		year = year - 60;
    	} else if (type === 'end') {
    		year = year + 2;
    	}
    	month = month > 9 ? month : '0' + month;;
    	day = day > 9 ? day : '0' + day;
    
    	return `${year}-${month}-${day}`;
    }
    import util from '@/common/util.js';
    import app from '@/common/app.js'; //公共变量
    import request from '@/common/request.js';
    import '@/static/css/index.css';
    import cache from '@/common/loadcache.js';
    import {
        mapState
    } from 'vuex';
    export default {
        components: {},
        data() {
            return {
               endDate: getDate({
               	format: true
               }),
               backDate: getDate({
                format: true   
               }),
               isReception:false,
               items: [],
               conName: '',
               conPhone: '',
               title:'',
               content:'',
               num:'',
               receptionService:'',
               request_data:{},
               formId:'',
               openid:'',
               startDate:getDate(),
               template_shjg:""
               
            }
        },
        computed: {
            ...mapState(['hasLogin', 'userInfo','configParams'])
        },
        onLoad() {
            this.items = cache.getDictsByTypeCode(app.DICT_TYPECODE.SELFORG_RECEPTION_SERVICE);
            this.loadMoreText = "加载更多",
            this.showLoadMore = false;
            uni.getProvider({
                service: 'oauth',
                success: (res) => {
                    console.log(res.provider)
                    if (~res.provider.indexOf('weixin')) {
                        uni.login({
                            provider: 'weixin',
                            success: (loginRes) => {
                                console.log(JSON.stringify(loginRes));
                                this.code = loginRes.code;
                                console.log(this.code)
                                //获取用户openid
                                request.http({
                                    url: "/app/pay/getInfo",
                                    data: {
                                        'code': this.code
                                    },
                                    header: 'application/x-www-form-urlencoded',
                                    success: (res) => {
                                        this.openid = res.data.data.openId;
                                    },
                                    fail: () => {
                                    }
                                })
            
                            }
                        });
                    }
                }
            })
        },
        onShow() {
            this.template_shjg = this.configParams.TEMPLATE_IDS.SHJG;
            this.conName =this.userInfo.name;
            this.conPhone = this.userInfo.sysUser.username;
           // this.getBackSchoolService();
        },
        methods: {
           /* getBackSchoolService: function(e) {
               request.ajax({
                    url: '/act/backSchoolService.json',
                    method: "GET",
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            res.data.data;
                        }
                    },
                    error: (res) => {
                        
                    }
                });
            }, */
            bindDateChange: function(e) {
            	this.endDate = e.target.value
            },
            backDateChange:function(e){
                this.backDate = e.target.value
            },
            receiveChange: function(e) {
            	this.isReception = e.target.value;
                if(!this.isReception){
                   this.receptionService=''; 
                   this.conName= '';
                   this.conPhone='';
                }
            },
            checkboxChange: function (e) {
            	 this.receptionService=e.target.value;
            },
            submit:function(e){
                console.log(e); 
                this.formId =e.detail.formId;
               if(this.title==''){
                    this.showpageToast("请输入主题名称");
                    return false;
                }
                if(this.content==''){
                    this.showpageToast("请输入活动内容");
                    return false;
                }
                if(this.num==''){
                    this.showpageToast("请输入返校人数");
                    return false;
                }
                var  objRegExp= /^[0-9]*[1-9][0-9]*$/;
                if(!objRegExp.test(this.num)){
                    this.showpageToast("请输入正确的返校人数");
                    return false;
                }  
                if(this.backDate<this.endDate){
                    
                    this.showpageToast("返校时间不能小于报名时间");
                    return false;
                }
                this.request_data={
                    name: this.title,
                    content: this.content,
                    limitNo: this.num,
                    endDate: this.endDate,
                    actDate: this.backDate,
                    touser: this.openid,
                    form_id:this.formId,
                    };
                if(this.isReception){
                    this.request_data["isReception"]=this.isReception;
                    this.request_data["receptionService"]=this.receptionService;
                    this.request_data["conName"] =this.conName;
                    this.request_data["conPhone"] =this.conPhone;
                }
                
                request.http({
                    url: '/app/actSelf/insert',
                    header : 'application/x-www-form-urlencoded',
                    data:  this.request_data,
                    success: (res) => {
                         if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                             //在此开始进行注册通知
                            let version = wx.getSystemInfoSync().SDKVersion;
                            if (util.compareVersion(version, '2.8.2') >= 0) {
                                 wx.requestSubscribeMessage({
                                   tmplIds: [this.template_shjg],
                                   success (res) {},
                                   error(res){}
                                 })
                            }
                         //默认将自己加到已报名校友中
                             uni.reLaunch({
                                 url: '/pages/act/backSchoolDetail?id=' + encodeURIComponent(JSON.stringify(res.data.data))
                             })
                             this.showpageToast(res.data.msg);
                         }
                     },
                     error: (res) => {
                
                     }
               });
                 
               
                 
                 
                 
            },
            showpageToast(msg) {
            	uni.showToast({
            		icon: 'none',
            		title: msg
            	});
            }
        }
    }
</script>


<style lang="scss">
/*    text {
        font-size: 35upx;
        color: #333;
    } */

    .action-row-center {
        color: #fff;
        background-color: rgba(0, 0, 0, 0.4);
        height: 98%;
        line-height: 60upx;
        display: flex;
        flex-direction: row;
        justify-content: center;
        font-size: 24upx;
    }

    .action-row-right {
        color: #007aff;
        padding-top: 20upx;
        display: flex;
        flex-direction: row;
        justify-content: flex-end;
    }

    .content_view {
        background-color: #FFFFFF;
        margin-top: 20upx;
        margin-bottom: 20upx;
        padding-left: 20upx;
        padding-right: 20upx;
    }

    .content_view_item {
        height: 80upx;
        line-height: 80upx;
        border-bottom: 1upx solid #e6e6e6;
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 10upx 20upx;
    }
    .text_reg {
        /* width: 15%; */
        font-size: $uni-font-size-base;
        display: inline-block;
        color: #000;
        /* padding-left: 20upx; */
        position: relative;
    }
	.content_view_item image{
		width: 12upx;
		height: 22upx;
		float: right;
		margin-right: 25upx;
		margin-top: 43upx;
	}
    .input_reg {
        width: 60% !important;
        float: right;
        text-align: right;
        // margin-right: 20upx;
    }

    .btn-verify {
        width: 174upx;
        height: 64upx;
        line-height: 64upx;
        background-color: #e2b352 !important;
        font-size: 26upx;
        color: #fff;
        padding: 0;
        display: inline-block;
        position: absolute;
        top: 18upx;
        right: 25upx;
        z-index: 1000;
    }

    .switch {
        float: right;
        padding-top: 17upx;
    }

    .primary {
        width: 90%;
        padding-bottom: 20upx;
    }

    .uni-input {
        text-align: right;
        // color: #cccccc;
        position: relative;
        // bottom: 10rpx;
        padding-right: 0;
    }

    .ring {
        width: 27upx;
        height: 32upx;
        position: relative;
        top: 12rpx;
        right: 16rpx;
    }
textarea{
	width: 99%;
	height: 185upx;
	background-color: #EEEEEE;
	font-size: $uni-font-size-base;
	padding-top: 25upx;
	padding-left: 1%;
}
    .primary btn_dark bottom-but {
        position: absolute;
        bottom: 2upx;
    }
	.input-row{
		padding-bottom: 30upx;
		margin-top: 100upx;
	}
	.uni-list:before{
		height: 0 !important;
	}
	.uni-list-cell {
		justify-content: flex-start;
	}
    .placeholderStyle{color: #ccc;}
</style>
