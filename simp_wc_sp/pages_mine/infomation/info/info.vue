<template>
    <view>
        <uni-list>
            <uni-list-item :show-badge="true" title="基本信息" thumb="/static/img/mine/base.png" @click="onClickBase" />
            <uni-list-item :show-badge="true" title="联系方式" thumb="/static/img/mine/contact.png" @click="onClickContact" />
            <uni-list-item :show-badge="true" title="教育经历" thumb="/static/img/mine/edu.png" @click="onClickEdu" />
            <uni-list-item :show-badge="true" title="通讯地址" thumb="/static/img/mine/address.png" @click="onClickAddr" />
            <uni-list-item :show-badge="true" title="社会经历" thumb="/static/img/mine/cocial.png" @click="onClicksocial" />
            <uni-list-item :show-badge="true" title="职业经历" thumb="/static/img/mine/career.png" @click="onClickProfession" />
            <uni-list-item :show-badge="true" title="荣誉成果" thumb="/static/img/mine/honor.png" @click="onClickHonor" />
            <uni-list-item :show-badge="true" title="其他教育经历" thumb="/static/img/mine/address.png" @click="onClickHisEdu" />
        </uni-list>
    </view>
</template>

<script>
    import uniList from '@/components/uni-list/uni-list.vue'
    import uniListItem from '@/components/uni-list-item/uni-list-item.vue'
    export default {
        components: {
            uniList,
            uniListItem
        },
        data() {
            return {

            }
        },
        methods: {
            onClickBase() {
                uni.navigateTo({
                    url: "/pages_mine/infomation/base/base"
                })
            },
            onClickEdu() {
                uni.navigateTo({
                    url: "/pages_mine/infomation/edu/edu"
                })
            },
            onClickHisEdu(){
                uni.navigateTo({
                    url: "/pages_mine/infomation/hisEdu/hisEdu"
                })                
            },
            onClickAddr() {
                uni.navigateTo({
                    url: "/pages_mine/infomation/addr/addr"
                })
            },
            onClickContact() {
                uni.navigateTo({
                    url: "/pages_mine/infomation/contact/contact"
                })
            },
            onClicksocial() {
                uni.navigateTo({
                    url: "/pages_mine/infomation/social/social"
                })
            },
            onClickHonor() {
                uni.navigateTo({
                    url: "/pages_mine/infomation/honor/honor"
                })
            },
            onClickProfession(){
                uni.navigateTo({
                    url: "/pages_mine/infomation/profession/profession"
                })                
            }
        }
    }
</script>

<style>
	.uni-list-item__icon-img {
		height:36upx !important;
		width:36upx !important;
	}
	/* .uni-list:after {
		left: 28upx !important;
	} */
</style>
