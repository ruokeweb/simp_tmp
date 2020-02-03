<template>
    <view class="team_cont">
        <view class="head">
            <view class="banner">
                <image src="/static/img/bgjuan.png" mode="aspectFill"></image>
                <text>共同献爱心</text>
            </view>
            <view class="act_head">
                <view class="list_1">
                    <image v-if="donProject.pic==null||donProject.pic==''" src="/static/img/don_default.png" class="uni-media-list-logo" mode="aspectFill"></image>
                    <image v-if="donProject.pic!=null && donProject.pic!=''" class="uni-media-list-logo" :src="imagePath + donProject.pic"
                        mode="aspectFill"></image>
                    {{donProject.name}}
                    <view class="container">
                        <view class="title"></view>
                        <view class="bm" v-if="null != donProject.startdate && null!= donProject.enddate">
                            时间：{{donProject.startdate}}至{{donProject.enddate}}
                        </view>
                    </view>
                </view>
                <view v-if="contentFlag  && donProject.summary!=''">
                    <uParse class="don-pro-summary" :content="donProject.summary" @preview="preview" @navigate="navigate" />
                </view>
              <!--  <text v_if="contentFlag">
                    <uParse :content="donProject.content" @preview="preview" @navigate="navigate" />
                </text> -->
            </view>
        </view>
        <view class="theme">
            主题
        </view>
        <input value="" v-model="theme" placeholder="写个吸引小伙伴的主题" class="input_fq"/>
        <view class="theme">
			发起口号
		</view>
		<textarea value="" v-model="slogan" placeholder="写个吸引小伙伴的口号" class="textarea_fq"/>
		<view class="theme">
			目标金额
		</view>
		<view class="targ">
			 <view class="money">
				  <view  v-for="(value, key) in defaultMoney" :key="key"  :class="cur==key? 'mon_1 active' : 'mon_1' " @click="howMuch(key)">
				     <text>{{value}}</text>元
				     <!-- <image v-show="cur==key" src="/static/xz.png" mode=""></image> -->
				 </view>
			</view>
			<view class="qt">
			    <text>其他金额</text>
			    <input @click="howMuch(-1)" v-model.trim="money1" type="digit" placeholder="请输入金额" maxlength="10" placeholder-class="placeholderStyle"></input>
			</view>
		</view>		
		<button type="primary" class="btn_dark" @click="goDon" style="z-index: 100;">发起共同献爱心</button>
    </view>
</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import util from '@/common/util.js';
    import request from '@/common/request.js';
	import uParse from '@/components/uParse/src/wxParse.vue'
    import segmentedControl from '@/components/uni-segmented-control/uni-segmented-control.vue';
    import uniLoadMore from "@/components/uni-load-more/uni-load-more.vue"
	  import '@/static/css/index.css';
	
    export default {
        components: {
            segmentedControl,
            uniLoadMore,
			uParse
        },
        data() {
            return {
                id: '',
                name: '',
                cur: 1,
				userInfo:{},
                donProject: {},
                money:5000,
                money1:'',
                theme:'',
                slogan:'',
               imagePath: app.imagePath,
			   contentFlag:false,
			   defaultMoney:[]
            }
        },
        onLoad(options) {
			this.donProject =JSON.parse(util.strcharacterDiscode(decodeURIComponent(options.donProject)));
			if(null != this.donProject.defaultMoney && ''!= this.donProject.defaultMoney){
				this.defaultMoney = this.donProject.defaultMoney.split(",");
				this.money = this.defaultMoney[0]
			}else{
				this.money = 1;
			}
            // this.donProject = util.strcharacterDiscode(this.donProject);
			this.contentFlag = true;
        },
        onShow(event) {
        },
        methods: {
            goDon(){
                //util.getStatPoint('click','EVENT_DESCRIBE_24');
                if(this.theme==''){
                    this.showpageToast("请输入主题");
                    return false;
                }
                if(this.slogan==''){
                    this.showpageToast("请输入口号");
                    return false;
                }
                if(this.money1==0){
                    
                     if(this.money<=0){
                        this.showpageToast("请输入正确的回馈金额");
                        return false;
                    }
                }else{
                    if(this.money1>0){
                        this.money=this.money1;
                    }else{
                        this.showpageToast("请输入正确的回馈金额");
                        return false;
                    }
                }
                var reg = /(^[1-9]([0-9]{1,6})?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
                
                if(!reg.test(this.money)){
                    this.showpageToast("请输入正确的回馈金额");
                    return false;
                }
                //保存一起捐，并跳转到捐赠页面
                 let url = '/pages/don/ImmediateDon?projectId=' + encodeURIComponent(JSON.stringify(this.donProject.id))
                    + '&title='+encodeURIComponent(JSON.stringify(this.theme))
                    + '&summary='+encodeURIComponent(JSON.stringify(this.slogan))
                    + '&targetMoney='+encodeURIComponent(JSON.stringify(this.money));
                 uni.reLaunch({
                     url: url
                 });                
					// request.http({
					// 	url: "/app/don/teamsave",
					// 	data: {
					// 		projectId: this.donProject.id,
					// 		name:this.theme,
					// 		summary:this.slogan,
					// 		targetMoney:this.money
					// 		
					// 	},
					// 	header: 'application/x-www-form-urlencoded',
					// 	success: (res) => {
					// 		 this.id = res.data.data.id;
					// 		 let url = '/pages/don/ImmediateDon?id=' + encodeURIComponent(JSON.stringify(this.id));
					// 		 uni.reLaunch({
					// 		     url: url
					// 		 });
					// 	},
     //                    fail: (data, code) => {
     //                        console.log(data);
     //                    }
					// });
            },
            howMuch(index){
                this.cur = index;
               if(index==-1){
                   this.money=0;
               }else{
				   this.money=this.defaultMoney[index];
				   this.money1=0;
			   } 
            },
            getDonProDetail(){
                uni.request({
                    url: app.serverPath + "/don/donProDetail.json",
                    method: "GET",
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.donProject = res.data.data.donProject;
                        }
                    },
                    fail: (data, code) => {
                        console.log(data);
                    }
                })
            },
            showpageToast(msg) {
                uni.showToast({
                    icon: 'none',
                    title: msg
                });
            }
        },
    }
</script>

<style lang="scss">
    
	.head{
		height: 590upx;
		position: relative;
		overflow: hidden;
	}
	.banner image{
		width: 100vw;
		height: 360upx;
		margin-top: 20upx;
	}
	.banner text{
		position: absolute;
		left: 140upx;
		top: 30upx;
		font-size: 46upx;
		color: #e7a41b;
		font-weight: bold;
	}
	.act_head {
		width: 690upx;
		box-shadow: 0px 0px 30px 0px rgba(116, 116, 116, 0.15);
		border-radius: 20upx;
		overflow: hidden;
		position: absolute;
		top: 170upx;
		left: 30upx;
		background-color: #FFFFFF;
	}
	.act_head text{
		display: block;
		margin:-18upx 50upx 30upx 50upx;
		overflow: hidden;
		text-overflow: ellipsis;
		display: -webkit-box;
		-webkit-line-clamp: 3;
		-webkit-box-orient: vertical;
		color: #999999;
		font-size: 28upx;
	}
	.input_fq{
		 height: 50upx; font-size:$uni-font-size-base; margin: 8upx 0; overflow: hidden;padding-left: 38upx;
	}
	.textarea_fq{height: 140upx; font-size:$uni-font-size-base; margin-top: 16upx;}
	.team_cont .list_1 {
		padding: 45upx 50upx;
		overflow: hidden;
		font-size:$uni-font-size-lg;
		font-weight:bold;
		line-height:1.45;
		text-align:justify;		
	}
	
	.team_cont .list_1 image {
		width: 210upx;
		height: 140upx;
		border-radius: 8upx;
		float: left;
	}
	
	.team_cont .list_1 .container {
		float: left;
		margin-left: 10upx;
		margin-top: 10upx;
	}
	
	.team_cont .list_1 .container .title {
		font-size: $uni-font-size-lg;
		font-weight: bold;
	}
	
	.team_cont .time {
		height: 170upx;
		font-size: 28upx;
		padding: 0upx 50upx;
		letter-spacing: 1upx;
	}
	
	.team_cont .time text {
		margin-left: 40upx;
		color: #999999;
	}
	.team_cont .bm {
		font-size: 24upx;
		color: #999999;
		margin-top: 16upx;
	}
	.team_cont .theme{
		height: 68upx;
		line-height: 68upx;
		background-color: #eaeaea;
		font-size: $uni-font-size-base;
		padding: 0 30upx;
	}
	textarea{
		padding: 0 30upx;
	}
	.money{
		padding: 35upx 30upx 20upx 30upx;
		overflow: hidden;
	}
	.mon_1{
        width: calc(25% - 20upx);
        height: 88upx;
        border: 1upx solid #eaeaea;
        float: left;
        text-align: center;
        line-height: 88upx;
        font-size: 28upx;
        color: #444444;
        font-weight: bold;
        position: relative;
        margin-bottom: 10upx;
        margin-left: 8upx;
        margin-right: 8upx;
		&.active{
			color: #E2B352;
			border: 1upx solid #E2B352;
			&:before {
				position: absolute;
				content: '';
				right: 0;
				bottom: 0;
				border-bottom: 40upx solid #E2B352;
				border-left: 55upx solid transparent;
				border-right: 0upx solid transparent;			
			}
			&:after {
				position: absolute;
				content: '\221A';
				right: 4upx;
				bottom: -28upx;
				color: #FFF;
				font-size: 24upx;			
			}
		}
	}
	.mon_2{
		margin-right: 17upx;
	}
	.money image{
		width: 45upx;
		height: 45upx;
		position: absolute;
		left: 112upx;
		bottom: 0upx;
	}
	.money .active{
		color: #E2B352;
		border: 1upx solid #E2B352;
	}
	.qt{
		width: 690upx;
		height: 88upx;
		border: solid 1px #eaeaea;
		margin: 0 auto;
	}
	.qt text:first-child{
		color: #444444;
		float: left;
		line-height: 88upx;
		padding-left: 30upx;
	}
	.qt text:last-child{
		color:#d1d1d1;
		float: right;
		padding-right: 40upx;
		line-height: 88upx;
	}
	.targ{
		padding-bottom: 145upx;
	}
	.btn_dark{
		width: 100%;
		height: 98upx;
		line-height: 98upx;
		position: fixed;
		bottom: 0;
		border-radius: 0;
		font-size: $uni-font-size-base;
	}
	.qt input {
        float:right;
        padding-right:40rpx;
        line-height:88rpx;
        text-align:right;
        margin-top:20rpx;

    }
    .don-pro-summary{
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 3;
        overflow: hidden;
        padding: 12rpx;
    }
	.placeholderStyle{color: #ccc;}
</style>
