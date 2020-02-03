<template>
    <view>
        
        <view class="apply">
            <view class="infro">
                <image src="/static/img/index/tz_2.png" mode=""></image>
                <text>如信息已自动获取，请确认信息是否正确</text>
            </view>
        </view>
        <view class="content_view content_view_one">
            <view class="content_view_item">
                <text class="text_reg">姓名</text>
                <text class="text_regr">{{name}}</text>
            </view>
            <view class="content_view_item">
                <text class="text_reg">联系方式</text>
                <text class="text_regr">{{phone}}</text>
            </view>
            <view class="content_view_item">
                <text class="text_reg sex">性别</text>
                <text class="text_regr">{{sex}}</text>
            </view>
        </view>
        <view class="kuang"></view>
		<view class="content_view">
			<block   class="" v-for="(value, key) in actFormList" :key="key">
				<view class="content_view_item" data-a="value.code" v-if="value.formType=='TEXT'">
				    <text class="text_reg entry">{{value.name}}</text>
				    <input class="input_reg input_ren" :id="value.code" :value="value.defaultVal" @input="formMethod"
				        placeholder="请输入" placeholder-class="placeholderStyle" />
				</view>
			    <view class="content_view_item item_checkbox"  v-if="value.formType=='CHECKBOX'">
			            <checkbox-group @change="formMethod" :id="value.code">
			                <view class="view_checkbox">{{value.name}}(多选)</view>
			                <label class="uni-list-cell uni-list-cell-pd check" v-for="item in value.defaultVal" :key="item">
			                    <view>
			                        <checkbox :value="item" />
			                    </view>
			                    <view class="wb">{{item}}</view>
			                </label>
			            </checkbox-group>
			    </view>
			    
			    <view v-if="value.formType=='SELECT'||value.formType=='RADIO'" class="content_view_item">
			        <text class="text_reg">{{value.name}}</text>
			        <picker class="input_reg" @change="selectMethod(value,$event)" :id="value.code" :value="selectindex"
			            :range="value.defaultVal">
			            <view class="date-uni-input">{{value.defaultVal[value.selectindex]}}</view>
			        </picker>
			    </view>
			    <view class="content_view_item" v-if="value.formType=='TEXTAREA'">
					<view class="infro_textarea">
						<text class="text_infro">{{value.name}}</text>
						<view class="uni-text">
							<textarea @input="formMethod" :id="value.code" :value="value.defaultVal" placeholder="请输入" placeholder-class="placeholderStyle"/>
						</view>
					</view>
			    </view>
			    <view v-if="value.formType=='DATE'" class="content_view_item">
			        <text class="text_reg">{{value.name}}</text>
			       	<view class="text_time"> 
			       		<picker mode="date" :id="value.code"  :value="value.date"  @change="dateMethod(value,$event)" class="input_reg">
			       			<view class="date-uni-input">{{value.date}}</view>
			       		</picker> 
			       	</view>
			    </view>
			
			
		</block  >
       </view>
        <!--    <button @click="regist" :disabled="regDisabled">提交认证</button> -->
         <form @submit="submit" report-submit="true">
            <view class="act_right" v-show="!hasEnroll">
                <button type="primary" class="primary btn_dark bottom-but" formType="submit" :disabled="saveDisable">提交</button>
            </view>
        </form>
    </view>
</template>

<script>
    function getDate(time,type) {
    	let date = new Date();
        if(time!=null&&time!=""){
            date = new Date(time);
        }
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
    import app from '@/common/app.js'; //公共变量
    import util from '@/common/util.js';
    import request from '@/common/request.js';
    // import '@/static/css/index.css';
    import cache from '@/common/loadcache.js';
    import {
        mapState
    } from 'vuex';
    export default {
        components: {},
        data() {
            return {
                id: '', //活动id
                userId: '', //用户id
                name: '',
                phone: '',
                sexArr: ['请选择'],
                sexindex: 0,
                sex: '',
                actFormList: [],
                current: 0,
                slide: 'none',
                show: false,
                request_date:{},  
                image:'',//活动首图 
                openid:'',
                actProName:'',
                formId:'',
                startDate:'',
                area:'',
                template_hdks: ''
            }
        },
        computed: {
            ...mapState(['hasLogin','userInfo','configParams'])
        },
        onLoad(event) {
            const payload = event.id || event.payload;
            try {
                this.id = JSON.parse(decodeURIComponent(payload));
            } catch (error) {
                this.id = JSON.parse(payload);
            };
            
            const image = event.image || event.payload;
            try {
                this.image = JSON.parse(decodeURIComponent(image));
            } catch (error) {
                this.image = JSON.parse(image);
            };
            const actProName = event.actProName || event.payload;
            try {
                this.actProName = JSON.parse(decodeURIComponent(actProName));
            } catch (error) {
                this.actProName = JSON.parse(actProName);
            };
            const startDate = event.startDate || event.payload;
            try {
                this.startDate = JSON.parse(decodeURIComponent(startDate));
            } catch (error) {
                this.startDate = JSON.parse(startDate);
            };
            const area = event.area || event.payload;
            try {
                this.area = JSON.parse(decodeURIComponent(area));
            } catch (error) {
                this.area = JSON.parse(area);
            };
            
            this.request_date["activityId"]=this.id;
        },
        onShow() {
            this.template_hdks = this.configParams.TEMPLATE_IDS.HDKS;
            this.name =this.userInfo.name;
            this.sex =cache.getDictByvalue(this.userInfo.sex,app.DICT_TYPECODE.SEX).label;
            this.phone = this.userInfo.sysUser.username;
           
            if(this.actFormList.length==0){
               this.getActFormList(); 
            }
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
            });

        },
        methods: {
            getActFormList: function(e) {
                var that = this;
                request.http({
                    url: '/app/act/getActForm',
                    header: 'application/x-www-form-urlencoded',
                    method: "POST",
                    data: {
                        id: this.id
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            var index_date=0;
                            
                            res.data.data.forEach(function(item,i){
                                
                             //   var _obj={};
                                item.defaultVal=item.defaultVal.split(",");
                                item.date=getDate();
                                item.selectindex=0;
                                that.actFormList.push(item);
                                if(item.formType=="SELECT"||item.formType=="RADIO"){
                                    that.request_date[item.code]=item.defaultVal[0];
                                }else if(item.formType=="DATE"){
                                    item.date=getDate(item.defaultVal);
                                    that.request_date[item.code]=item.date;
                                }else if(item.formType=="TEXT"||item.formType=="TEXTAREA"){
                                   that.request_date[item.code]=item.defaultVal; 
                                }else{
                                   that.request_date[item.code]=null; 
                                }
                                /* _obj["sort"]=item.sort;
                                that.request_date[item.code]=_obj; */
                            })
                           
                        }
                    },
                    error: (res) => {

                    }
                });
            },
            formMethod:function(e){
                this.request_date[e.target.id]=e.target.value;
            },
            selectMethod:function(value,e){
                value.selectindex=e.target.value;
                this.request_date[e.target.id]=value.defaultVal[e.target.value];
            },
            dateMethod:function(value,e){
                value.date = e.target.value;
                this.request_date[e.target.id]=e.target.value;
            },
            submit:function(e){
                //util.getStatPoint('click','EVENT_DESCRIBE_35');
                this.formId =e.detail.formId;
                var tag=true;
                /* 判断是否为空*/
                var that = this;
                this.actFormList.forEach(function(val, index) {
                   if(that.request_date[val.code] == null){
                      tag=false;
                      that.showpageToast("请将信息填写完成"); 
                   }
                   return false;
                });
               if(tag){
                 
                  request.http({
                      url: '/app/act/saveActContent',
                      method: "POST",
                      header: 'application/x-www-form-urlencoded',
                      data:  this.request_date,
                      success: (res) => {
                           if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                                let version = wx.getSystemInfoSync().SDKVersion;
                                if (util.compareVersion(version, '2.8.2') >= 0) {
                                   wx.requestSubscribeMessage({
                                     tmplIds: [this.template_hdks],
                                     success(res) {},error(res){}
                                   })
                                }                               
                               this.showpageToast(res.data.msg);
                               //默认将自己加到已报名校友中
                               uni.reLaunch({
                                   url: '/pages/act/actResult?id=' + encodeURIComponent(JSON.stringify(this.id))+'&image='+encodeURIComponent(JSON.stringify(this.image))+'&hasSuccess='+encodeURIComponent(JSON.stringify(res.data.data))
                               })
                               
                               
                           }
                       },
                       error: (res) => {
                  
                       }
                   });  
               } 
            },
            bindPickerChangeSex:function(e){
                this.sexindex=e.target.value;
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
    .action-row-center {
        color: #007aff;
        padding-top: 20upx;
        display: flex;
        flex-direction: row;
        justify-content: center;
    }

    .action-row-right {
        color: #007aff;
        padding-top: 20upx;
        display: flex;
        flex-direction: row;
        justify-content: flex-end;
    }

    .content {
        background-color: #F2F2F2;
    }

    .content_view {
		margin-left: 30upx;margin-right: 30upx;
		&.content_view_one{
			margin-top:60upx;
		}
    }

    .content_view_item {
        border-bottom: 1upx solid #D4D4D4;display: flex;align-items: center;justify-content: space-between;padding: 20rpx 0;

		.view_checkbox{
			border-bottom: 1upx solid #D4D4D4;padding: 0 0 20rpx
		}
    }
	.item_checkbox{padding-bottom: 0;}
	.text_reg{
		float: left;
        width:40%;
        overflow:hidden;
        -o-text-overflow:ellipsis;
        text-overflow:ellipsis;
        white-space:nowrap;      
	}
	.input_ren{text-align: right;}
	.text_regr{
		float: right;
		padding-right: 28upx;
	}
   
	.apply{
		width: 100%;
		height: 60upx;
		background-color: #999999;
		position: fixed;
		top: 0;
		z-index: 666;
	}
	.apply .infro{
		width: 480upx;
		margin: 0 auto;
	}
	.apply .infro image{
		width: 27upx;
		height: 32upx;
		float: left;
		margin-top: 15upx;
	}
	.apply .infro text{
		font-size: 24upx;
		color: #FFFFFF;
		float: right;
		line-height: 60upx;
	}
	.infro_textarea{width: 100%;
		
	}
	
	.uni-list-cell {
		justify-content: flex-start;padding: 20upx 0;
	}
	.uni-list-cell:after{
		background: #D4D4D4;transform: scaleY(1);
	}
	.uni-list-cell:last-child:after{
		height: 0;
	}
	.uni-list:after {		
         left: 30upx;
	}
	.uni-list:before {
        height: 0;
	}
	
	.uni-listl:after{
		height: 1upx;
		left: 30upx;
	}
	.input-row{
		margin: 50upx;
	}
	.uni-text{
		width: 100%;
		height: 185upx;
		background-color: #eeeeee;
	}
	.check .wb{
		padding-left: 10upx;
	}
	.kuang{
		height: 20upx;
		background-color: #f2f2f2;
	}
	.content_view_item:last-child{
		border-bottom: none;
	}
	.text_infro{margin-bottom: 20upx;
		display:block;
        overflow:hidden;
        -o-text-overflow:ellipsis;
        text-overflow:ellipsis;
        white-space:nowrap;

	}
	.left image{
		width: 16upx;
		height: 26upx;
		float: right;
		margin: 15upx 20upx 0 30upx;
	}
	.sex{
		float: left;
	}
	.input_sex{
		margin-left: 159upx;
		margin-top: 24upx;
	}
	.uni-text textarea{width: 100%;height: 100%;}
	
	.placeholderStyle{color: #ccc;}
</style>
