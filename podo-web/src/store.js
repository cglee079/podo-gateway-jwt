import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        accessToken: '',
        refreshToken: '',
    },
    mutations:{
      setAuth(state, auth){
          state.accessToken = auth.accessToken;
          state.refreshToken = auth.refreshToken;
      }
    },
    actions:{
        setAuth({commit}, auth){
            commit('setAuth', auth)
        }
    }
});
