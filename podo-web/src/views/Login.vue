<template>
    <div>
        <div>
            <input type="text" v-model="member.id"/>
            <input type="password" v-model="member.password"/>

            <button @click="submitLogin">로그인</button>
        </div>
        <div>
            <vue-json-pretty :data="response"/>
        </div>
    </div>
</template>

<script>
    import VueJsonPretty from 'vue-json-pretty';
    import {mapActions} from 'vuex';
    import {login} from './../api';

    export default {
        name: "login",
        components: {
            VueJsonPretty
        },
        data() {
            return {
                member: {
                    id: 'test',
                    password: 'user'
                },
                response: {}
            }
        },
        methods: {
            ...mapActions(["setAuth"]),

            async submitLogin() {
                try {
                    this.response = await login(this.member);
                    const auth = {};

                    auth.accessToken = this.response.data.accessToken;
                    auth.refreshToken = this.response.data.refreshToken;

                    this.setAuth(auth);

                } catch (e) {
                    this.response = e;
                }
            }
        }
    }
</script>

<style scoped>

</style>
