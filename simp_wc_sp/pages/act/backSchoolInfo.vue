<template>
    <view class="content">
        <image src="/static/img/act/bg.png" mode=""></image>
        <view class="scroll">

            <view v-if="content!=''">
                <uParse :content="content" />
            </view>
            <view v-else="">
                <text>亲爱的校友：
                    弹指间岁月变迁，风雨兼程的您是否还记得曾经的青青校园？是否还惦念旧时朝夕相处的的恩师同窗？是否总有一段马家花园的记忆拨动您的心弦？秀园、五谷园的景色是否常悄然进入您的梦乡？您可曾梦想再次踏入校园，留下美好的身影和健硕的步伐？母校这方土地蕴育过您的梦想，挥洒过您的汗水，记载着您那段青葱岁月里的友情、爱情，喜怒哀乐，相聚、别离。不论身在何方，您永远是母校的牵挂。近两年，每年暑期都有近千名校友回到校园逐梦青春，也请您邀约班级、年级同学一同“回家”吧！
                </text>
            </view>


        </view>
        <view class="input-row">
            <button type="primary" class="primary btn_dark" @tap="backSchool()" :disabled="disabled">发起值年返校</button>
        </view>
    </view>
</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import util from '@/common/util.js';
    import request from '@/common/request.js';
    import uParse from '@/components/uParse/src/wxParse.vue' //富文本框转义
    import {
        mapMutations
    } from 'vuex';
    export default {
        components: {
            uParse
        },
        data() {
            return {
                content: ''
            }
        },
        onShow() {
            uni.request({
                url: app.serverPath + '/app/wx/getContentByPage',
                method: 'POST',
                header: {
                    'content-type': 'application/x-www-form-urlencoded'
                },
                data: {
                    code: 'backSchoolInfo',
                },
                success: (res) => {
                    if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                        this.content = util.strcharacterDiscode(res.data.data);
                    }
                },
                fail: (res) => {

                }
            })
        },
        methods: {
            backSchool(e) {
                //去发起
                let url = '/pages/act/launchBackSchool';
                uni.navigateTo({
                    url: url
                });
            },
        },
    }
</script>

<style>
    .content {
        width: 100vw;
        height: 100vh;
        position: relative;
        overflow: hidden;
    }

    .content image {
        width: 100vw;
        height: 100vh;
    }

    .scroll {
        width: 480upx;
        height: 726upx;
        overflow-y: scroll;
        position: absolute;
        font-size: 26upx;
        top: 245upx;
        left: 135upx;
    }

    .content text {
        letter-spacing: 1upx;
        line-height: 44upx;
    }

    .input-row {
        width: 92%;
        height: 88upx;
        position: absolute;
        bottom: 25upx;
        left: 35upx;
    }
</style>
