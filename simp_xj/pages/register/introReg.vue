<template>
	<view>
		<!--导航头 -->
		<cu-custom bgColor="bg-red" :isBack="true"><block slot="content">我要领卡</block></cu-custom>
		<!--动态内容区-->
		<view class="margin">
				<view class="text-xxl text-bold ">欢迎申领交大校友卡</view>
				<view class="text-xl margin-top-sm">
				    <view v-if="content!=''">
				        <uParse :content="content" />
				    </view>
				    <view v-else>
				        <view>长亭外，古道边，芳草碧连天。</view>
				        <view>问君此去几时来，来时莫徘徊。</view>
				        <view>天之涯，地之角，知交半零落。</view>
				        <view>人生难得是欢聚，惟有别离多。</view>
				    </view>
				</view>
		</view>
		<!--卡片区-->
		<view class="margin">
			<image src="../../static/img/register/demo_card1.png" mode="widthFix" />
		</view>
		<!--流程区-->
		<view class="padding">
			<view class="cu-steps">
				<view class="cu-item" :class="index>num?'':'text-red'" v-for="(item,index) in numList" :key="index">
					<text class="num" :class="index==2?'err':''" :data-index="index + 1"></text> {{item.name}}
				</view>
			</view>
		</view>
		<!--按钮区-->
		<view class="foot">
		    <view class="flex text-gray justify-center">
		    	点击手机授权，即代表您同意将数据提交至学校
		    </view>
		    <button class="cu-btn bg-red block round lg margin" open-type="getPhoneNumber" @getphonenumber="getPhoneNumber">
		    	手机授权
		    </button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				content:'',
				numList: [{
					name: '申请领卡'
				}, {
					name: '信息确认'
				}, {
					name: '审核认证'
				}, {
					name: '完成领卡'
				} ],
				num: 0,
			}
		},
		methods: {
			getPhoneNumber(e) {
			    if (e.detail.errMsg == 'getPhoneNumber:fail user deny') {
			        uni.showToast({title: '返回个人中心...',icon:'none',success:function(){
			            util.toPage("/pages/mine/mine");
			        }});
			    } else {
			        //缓存获取参数
			        var openId=uni.getStorageSync(app.openId);
			        var sessionKey = uni.getStorageSync(app.sessionKey);
			        //解密获取手机号
			        var encryptedData = e.detail.encryptedData;
			        var iv = e.detail.iv;
			        var pc = new WXBizDataCrypt(app.appId, sessionKey);
			        var phone = pc.decryptData(encryptedData, iv).phoneNumber;
			        // //注册
			        // request.http({
			        //     url: app.serverPath + '/app/register/register',
			        //     data: {
			        //         'openId': openId,
			        //         'phone': phone
			        //     },
			        //     header: 'application/x-www-form-urlencoded',
			        //     success: (res) => {
			        //         //更新用户信息
			        //         uni.setStorageSync(app.storageKey.userInfo,res.data.data.userInfo);
			        //         //更新token
			        //         store.commit('updateToken',res.data.data.token);
			        //         //更新token时间
			        //         store.commit('updateTokenTime',res.data.data.tokenTime);
			        //         //更新登录状态
			        //         store.commit('updateIsLogin',true);
			        //         util.toPage("/pages/login/loginStep2");
			        //     },
			        //     fail: (res) => {
			        //         console.log(res);
			        //     }
			        // })
			       
			    }
			},
			toPage(url){
				util.toPage(url);
			}
		}
	}
</script>
<style>
</style>