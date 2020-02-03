<template>
    <view class="search" :style="{ backgroundColor: backgroundColor }">
        <view class="content" :style="{ 'border-radius': radius + 'px', border: border }">
            <view class="content-box" :class="{ center: mode === 2 }">
                
                <input class="input" :class="{ center: !active && mode === 2 }" :focus="isFocus" :placeholder="placeholder"
                    v-model="inputVal" placeholder-style="color:#cacaca;" />
					<image src="/static/img/act/search.png" mode="aspectFill" @click="click"></image>
            </view>
        </view>
    </view>
</template>

<script>
    export default {
        props: {
            mode: {
                type: Number,
                default: 1
            },
            button: {
                type: String,
                default: 'outside'
            },
            show: {
                type: Boolean,
                default: true
            },
            radius: {
                type: String,
                default: '60'
            },
            placeholder: {
                type: String,
                default: '请输入搜索关键字'
            },
            backgroundColor: {
                type: String,
                default: '#fff'
            },
            // border: { type: String, default: '1px #f5f5f5 solid' }
            // 
        },
        data() {
            return {
                active: false,
                inputVal: '',
                searchName: '取消',
                isDelShow: false,
                isFocus: false
            };
        },
        methods: {
            focus() {
                this.active = true;
            },
            blur() {
                this.isFocus = false;
                if (!this.inputVal) {
                    this.active = false;
                }
            },
            clear() {
                this.inputVal = '';
                this.active = false;
                //this.$emit('search', '');
            },
            getFocus() {
                this.isFocus = true;
            },
            click() {
                this.search()
            },
            search() {
                this.$emit('search', this.inputVal);
            }
        },
        watch: {
            inputVal(newVal) {
                if (newVal) {
                    this.searchName = '搜索';
                    this.isDelShow = true;
                } else {
                    this.searchName = '取消';
                    this.isDelShow = false;
                }
            }
        }
    };
</script>

<style lang="scss" scoped>
    .search {
        width: 690upx;
        height: 65upx;
        background-color: rgba(255, 255, 255, 0.4);
        border-radius: 34upx;
        font-size: 28upx;
        margin: 0 auto;
        margin-top: 40upx;

        .content {
            display: flex;
            align-items: center;
            width: 100%;
            height: 65upx;
            // border: 1px #ccc solid;
            background-color: #fff;
            overflow: hidden;
            transition: all 0.2s linear;
            border-radius: 30px;

            .content-box {
                width: 94%;
                display: flex;
                align-items: center;
                position: relative;
                left: 44upx;

                &.center {
                    justify-content: center;
                }

                .input {
                    width: 86%;

                    line-height: 60upx;
                    height: 60upx;
                    transition: all 0.2s linear;

                    &.center {
                        width: 200upx;
                    }

                    &.sub {
                        width: auto;
                        color: grey;
                    }
                }
            }

            .searchBtn {
                height: 100%;
                flex-shrink: 0;
                padding: 0 30upx;
                background: $uni-color-success;
                line-height: 60upx;
                color: #fff;
                border-left: 1px #ccc solid;
                transition: all 0.3s;
            }
        }

        .button {
            display: flex;
            align-items: center;
            justify-content: center;
            position: relative;
            flex-shrink: 0;
            width: 0;
            transition: all 0.2s linear;
            white-space: nowrap;
            overflow: hidden;

            &.active {
                padding-left: 15upx;
                width: 100upx;
            }
        }
    }

    .search .content-box image {
        position: absolute;
        right: 0;
        top: -1;
        width: 65upx;
        height: 65upx;
        border-radius: 50%;
    }
</style>
