<template>
    <view class="act_detail content">
        <view class="act_head">
            <view>
                <view class="list_1">
                    <view class="tu_mod">
                        <image v-if="assDetail.logo==null ||assDetail.logo==''" src="/static/img/as_default.png" mode="aspectFill"></image>
                        <image v-if="assDetail.logo!=null" :src="imagePath + assDetail.logo" mode="aspectFill"></image>
                    </view>
                    <view class="container">
                        <view class="title">{{assDetail.name}}</view>
                        <view class="bm" @click="asMebbers(e)">
                            <view class="xy">
                                <image src="/static/member.png" mode=""></image>
                            </view>
                            <text>{{assDetail.sum==null?0:assDetail.sum}}</text>
                        </view>
                        <view v-if="!assDetail.hasJoin">
                            <view class="join" @click="joinThisAs(e)">
                                加入
                            </view>
                        </view>
                        <view v-else="">
                            <view class="exit" @click="exitThisAs(e)">
                                退出
                            </view>
                        </view>
                    </view>
                </view>
                <view class="time">
                    <view class="sj sj_1">成立时间<text>{{assDetail.openDate==null?'':assDetail.openDate}}</text></view>
                    <view class="sj">校友会类型<text>{{assDetail.type}}</text></view>
                    <view class="sj sj_3">地域级别<text>{{assDetail.regionType==null?'':assDetail.regionType}}</text></view>
                    <view class="sj sj_4">所在地<text>{{assDetail.country==null?'':assDetail.country}}{{assDetail.province==null?'':assDetail.province}}{{assDetail.city==null?'':assDetail.city}}</text></view>
                    <view class="sj sj_5">详细地址<text>{{assDetail.address==null?'':assDetail.address}}</text></view>
                </view>
            </view>
            <view class="up" @click="btn" v-show="fold">
                
                    
                <text class="tu_1">展开</text>
            </view>
            <view class="zk" @click="btn" v-show="!fold">
                <view class="intro">
                    <view class="intro_jx">简介</view>
                 
                </view>
                <view v-if="assDetail.summary!=''&&assDetail.summary!=null" class="wb">
                    <uParse :content="assDetail.summary" @preview="preview" @navigate="navigate" />
                </view>
                <view v-if="assDetail.summary==''||assDetail.summary==null" class="wb">
                    无
                </view>
                <view class="intro">
                    <view class="intro_jx">校友会章程</view>
                </view>
                <view v-if="assDetail.constitution!=''&&assDetail.constitution!=null" class="wb">
                    <uParse :content="assDetail.constitution" @preview="preview" @navigate="navigate" />
                </view>
                <view v-if="assDetail.constitution==''||assDetail.constitution==null" class="wb">
                    无
                </view>
                <view class="up">
                    <text class="tu_2">
                        收起
                    </text>
                    
                </view>
            </view>
        </view>
        <view>
            <view class="list" id="list" v-for="(value, key) in actProList" :key="key" @click="goDetail(value)">
                <view class="activity">
                    <view class="sch_tu">
                        <image v-if="value.image!=null&&value.image!=''" :src="imagePath + value.image" mode="aspectFill"></image>
                        <image v-if="value.image==null||value.image==''" src="/static/img/act/act_default.png" mode="aspectFill"></image>
                        <view v-if="value.status=='DONE'" class="end">
                            已结束
                        </view>
                        <view v-if="value.status=='DOING'" class="doing">
                            进行中
                        </view>
                        <view v-if="value.status=='READAY'" class="readay">
                            预备中
                        </view>
                    </view>
                    <view class="sch_cont">
                        <view class="title">
                            {{ value.name }}
                        </view>
                        <view class="date">
                            {{value.createDate}}至{{value.endDate}}
                        </view>
                        <view class="bm">
                            <view class="xy">
                                · {{value.assoName}}
                            </view>
                            <view class="people">
                                <text>{{value.readyNo==null?0:value.readyNo}}</text>人已报名
                            </view>
                        </view>
                    </view>
                </view>
                <view class="activity_h"></view>
            </view>
            <uni-load-more v-if="hasShow" :status="status" :contentText="contentText" color="#999999" />
            <view class="foot">
                <view class="more" @click="goMore()">
                    查看更多
                </view>
            </view>
        </view>
    </view>
    </view>
    </view>
</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import request from '@/common/request.js';
    import loadcache from '@/common/loadcache.js';
    import '@/static/css/index.css';
    import segmentedControl from '@/components/uni-segmented-control/uni-segmented-control.vue';
    import uniLoadMore from "@/components/uni-load-more/uni-load-more.vue";
    import uParse from '@/components/uParse/src/wxParse.vue' //富文本框转义
    import util from '@/common/util.js'; //公共变量
    import {
        mapState
    } from 'vuex';
    export default {
        components: {
            segmentedControl,
            uniLoadMore,
            uParse
        },
        computed: {
            ...mapState(['hasLogin', 'userInfo'])
        },
        data() {
            return {
                id: '',
                fold: true,
                actProList: [],
                assDetail: {},
                imagePath: app.imagePath,
                contentText: {
                    contentdown: '加载更多',
                    contentrefresh: '加载中',
                    contentnomore: '没有更多'
                },
                status: "noMore",
                hasShow: false
            }
        },
        onLoad(event) {
            const payload = event.id || event.payload;
            try {
                this.id = JSON.parse(decodeURIComponent(payload));
            } catch (error) {
                this.id = JSON.parse(payload);
            };
        },
        onShow(event) {
            //判断当前是否在这个校友会
            if (this.actProList.length == 0) {
                this.getActProList();
            }
            this.getDetail();

        },
        methods: {
            btn: function() {
                this.fold = !this.fold;
            },

            goMore() {
                uni.reLaunch({
                    url: '/pages/tabbar/act/act'
                });
            },
            getDetail() {
                request.http({
                    url: '/app/as/getAssociationDetail',
                    method: "POST",
                    header: 'application/x-www-form-urlencoded',
                    data: {
                        id: this.id
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.assDetail = res.data.data;
                            this.assDetail.summary = util.strcharacterDiscode(res.data.data.summary);
                            this.assDetail.constitution = util.strcharacterDiscode(res.data.data.constitution);
                            this.assDetail["type"] = loadcache.getDictByvalue(res.data.data.type, app.DICT_TYPECODE
                                .ASSOCIATION_TYPE).label;
                            this.assDetail["regionType"] = loadcache.getDictByvalue(res.data.data.regionType,
                                app.DICT_TYPECODE.AREA_TYPE).label;
                            this.assDetail["country"] = loadcache.getObjFromStorageById(app.CACHE_NAME.AREABASE,
                                res.data.data.country);
                            this.assDetail["province"] = loadcache.getObjFromStorageById(app.CACHE_NAME.AREABASE,
                                res.data.data.province);
                            this.assDetail["city"] = loadcache.getObjFromStorageById(app.CACHE_NAME.AREABASE,
                                res.data.data.city);
                            console.log(this.assDetail.openDate);
                        }
                    },
                    error: (res) => {

                    }
                })
            },
            joinThisAs(id) {
                //请求后台（加入当前校友会）
                // ajax()
                // this.fold = false;
                if (this.userInfo.cardStatus != 'NORMAL') {
                    this.showpageToast("您的信息还未审核通过，不能加入校友会");
                    return false;
                }
                uni.showModal({
                    title: '',
                    content: '确定加入该校友会',
                    success: (res) => {
                        if (res.confirm) {
                            //需要去保存用户和校友会的关系，

                            request.http({
                                url: '/app/as/addSysAs',
                                method: "POST",
                                header: 'application/x-www-form-urlencoded',
                                data: {
                                    asId: this.id
                                },
                                success: (res) => {
                                    if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                                        this.showpageToast("恭喜你加入成功");
                                        this.assDetail["hasJoin"] = true;
                                        this.getDetail();
                                    }
                                },
                                error: (res) => {

                                }
                            })

                        }
                    }
                });

            },
            exitThisAs(id) {
                // this.fold = true;
                uni.showModal({
                    title: '',
                    content: '确定退出该校友会',
                    success: (res) => {
                        if (res.confirm) {
                            request.http({
                                url: '/app/as/deleteSysAs',
                                method: "POST",
                                header: 'application/x-www-form-urlencoded',
                                data: {
                                    asId: this.id
                                },
                                success: (res) => {
                                    if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                                        this.showpageToast("退出成功");
                                        this.assDetail["hasJoin"] = false;
                                        this.getDetail();
                                    }
                                },
                                error: (res) => {

                                }
                            })
                        }
                    }
                });

            },
            getActProList: function(e) {
                request.http({
                    url: '/app/act/ActList',
                    header: 'application/x-www-form-urlencoded',
                    method: "POST",
                    data: {
                        pageNo: 1,
                        pageSize: 1,
                        assoId: this.id
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            if (res.data.list == undefined || res.data.list.length == 0) {
                                this.status = "noMore";
                                this.hasShow = true;
                            }
                            var datas = res.data.list;
                            datas.forEach((d) => {
                                d.name = util.strcharacterDiscode(d.name);
                            });
                            this.actProList = datas;
                        }
                    },
                    error: (res) => {

                    }
                });
            },
            goDetail(e) {
                uni.navigateTo({
                    url: '/pages/act/actDetail?id=' + encodeURIComponent(JSON.stringify(e.id))
                });
            },
            asMebbers(e) {
                uni.navigateTo({
                    url: '/pages_mine/myAssociation/schoolMember?id=' + encodeURIComponent(JSON.stringify(this.id))
                });
            },
            showpageToast(msg) {
                uni.showToast({
                    icon: 'none',
                    title: msg
                });
            },
        },

    }
</script>

<style lang="scss">
    .act_detail .act_head {
        width: 690upx;
        box-shadow: 0px 0px 30px 0px rgba(116, 116, 116, 0.15);
        border-radius: 20upx;
        margin: 25upx auto;
    }

    .act_detail .list_1 {
        padding: 45upx 40upx;
        overflow: hidden;
    }

    .act_detail .list_1 .tu_mod {
        width: 210upx;
        height: 140upx;
        border-radius: 8upx;
        float: left;
    }

    .act_detail .list_1 .tu_mod image {
        width: 100%;
        height: 100%;
    }

    .act_detail .list_1 .container {
        float: left;
        margin-left: 30upx;
        width: 370rpx;
    }

    .act_detail .list_1 .container .title {
        margin-top: 10upx;
        font-size: $uni-font-size-lg;
        font-weight: bold;
        height: 60upx;
        overflow: hidden;
    }

    .act_detail .time {
        font-size: 28upx;
        padding: 0upx 50upx;
        letter-spacing: 1upx;
    }

    .act_detail .time text {
        margin-left: 40upx;
        color: #999999;
    }

    .act_detail .time .sj {
        line-height: 60upx;
    }

    .act_detail .time .sj_1 text {
        margin-left: 65upx;
    }

    .act_detail .time .sj_3 text {
        margin-left: 68upx;
    }

    .act_detail .time .sj_4 text {
        margin-left: 94upx;
    }

    .act_detail .time .sj_5 text {
        margin-left: 70upx;
    }

    .act_detail .up{line-height: 95upx;height: 95upx;text-align: center;width: 200upx;margin: auto;
    	text {
    				color: #c79f63;
    				font-size: 28upx;
    			
    			}
    		.tu_1 {display: inline-block;
    		   padding-left: 40rpx;text-align: center;
    	
    		    position: relative;
    			&:before{
    				position: absolute;content: "";width: 20upx;height: 1upx;background: #c79f63;transform: rotate(45deg);left: -6upx;
    				top: 0;bottom: 0;margin: auto;
    			}
    			&:after{
    				position: absolute;content: "";width: 20upx;height: 1upx;background: #c79f63;transform: rotate(-45deg);
    				left: 6upx;top: 0;bottom: 0;margin: auto;
    			}
    		}
    		
    		.tu_2 {display: inline-block;
    		   padding-left: 40rpx;text-align: center;
    	
    		    position: relative;
    			&:before{
    				position: absolute;content: "";width: 20upx;height: 1upx;background: #c79f63;transform: rotate(-45deg);left: -6upx;
    				top: 0;bottom: 0;margin: auto;
    			}
    			&:after{
    				position: absolute;content: "";width: 20upx;height: 1upx;background: #c79f63;transform: rotate(45deg);
    				left: 6upx;top: 0;bottom: 0;margin: auto;
    			}
    		}
    	
    }

  

    .act_detail .zk .intro_jx {position: relative;font-size: $uni-font-size-lg;color: #333333;font-weight: bold;padding-left: 20upx;
        		&:before{position: absolute;content:"";width: 9upx;height: 40upx;background-color: #deb051;
        		border-radius: 4upx;left:0;top:0;bottom:0;margin: auto 0;}
        }
    .act_detail .intro {
        margin-left: 50upx;
        margin-top: 40upx;
        overflow: hidden;
    }

    .act_detail .zk .intro text {
        float: left;
        font-size: 36upx;
        color: #333333;
        margin-top: -11upx;
        margin-left: 20upx;
    }

    .act_detail .wb {
        width: 598upx;
        font-size: 28upx;
        color: #999999;
        margin: 24upx 50upx;
        line-height: 1.5;
        letter-spacing: 1upx;
    }

    .act_detail .wb image {
        width: 100% !important;
    }

    .act_detail .act_head .container .xy {
        width: 31upx;
        height: 27upx;
        border-radius: 0upx;
        float: left;
        background-image: none;
    }

    .act_detail .xy image {
        width: 100%;
        height: 100%;
        border-radius: 0upx;

    }

    .act_detail .bm {
        margin-top: 19upx;
    }

    .act_detail .bm text {
        float: left;
        color: #c79f63;
        font-size: 30upx;
        margin-left: 9upx;
        margin-top: -11upx;
    }

    .join {
        float: right;
        width: 120upx;
        height: 50upx;
        background-color: #deb051;
        border-radius: 28upx;
        border: solid 1upx #f9c558;
        line-height: 50upx;
        font-size: 24upx;
        color: #FFFFFF;
        text-align: center;
        position: relative;
        top: -17upx;
        right: 6upx;
    }

    .exit {
        float: right;
        width: 120upx;
        height: 50upx;
        background-color: #FFFFFF;
        border-radius: 28upx;
        border: solid 1upx #f9c558;
        line-height: 50upx;
        font-size: 24upx;
        color: #deb051;
        text-align: center;
        position: relative;
        top: -17upx;
        right: 6upx;
    }

    .content .sch_cont .people {
        width: 126upx;
        font-size: 24upx;
        float: right;
        color: #999999;
    }

    .content .people text {
        color: #ed5849;
    }

    .line {
        top: 98upx !important;
        left: 10upx !important;
    }

    .act_detail .bm .people text {
        margin-top: -6upx;

    }

    .content .activity .sch_tu .doing {
        width: 230upx;
        height: 40upx;
        background-color: rgba(55, 151, 84, 0.5);
        border-radius: 5upx;
        position: absolute;
        right: 0upx;
        bottom: 0upx;
        color: white;
        line-height: 40upx;
        text-align: center;
    }

    .content .activity .sch_tu .readay {
        width: 230upx;
        height: 40upx;
        background-color: rgba(158, 117, 33, 0.5);
        border-radius: 5upx;
        position: absolute;
        right: 0upx;
        bottom: 0upx;
        color: white;
        line-height: 40upx;
        text-align: center;
    }

    .content .activity_2 .doing_2 {
        width: 93upx;
        height: 40upx;
        background-color: rgba(55, 151, 84, 0.5);
        border-radius: 5upx;
        color: white;
        line-height: 40upx;
        text-align: center;
        float: right;
    }

    .content .activity_2 .readay_2 {
        width: 93upx;
        height: 40upx;
        background-color: rgba(158, 117, 33, 0.5);
        border-radius: 5upx;
        color: white;
        line-height: 40upx;
        text-align: center;
        float: right;
    }

    .content .content_wb text {
        margin-top: -4upx;
    }

    .content .activity .sch_tu {
        width: 230upx;
        height: 160upx;
    }

    .content .sch_cont .bm {
        width: 432upx;
        top: 103upx;
    }

    .content .activity .sch_cont {
        width: 432upx;
        margin-top: 0;
    }

    .content .sch_cont .date {
        margin-top: 0upx;
    }

    .content .activity .sch_tu .end {
        width: 230upx;
        height: 40upx;
        background-color: rgba(0, 0, 0, 0.5);
        border-radius: 5upx;
        position: absolute;
        right: 0upx;
        bottom: 0upx;
        color: white;
        line-height: 40upx;
        text-align: center;
    }
</style>
