import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)
const store = new Vuex.Store({
    state: {
        token:'',
        tokenTime: 0,
        isLogin:null
    },    
	mutations: {
        updateToken(state,token){
            state.token=token
        },
		updateTokenTime(state,tokenTime) {
		  // ±ä¸ü×´Ì¬
		  state.tokenTime = tokenTime
		},
        updateIsLogin(state,isLogin){
            state.isLogin=isLogin
        }
	}
})

export default store
