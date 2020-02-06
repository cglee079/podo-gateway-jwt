<template>
    <div>
        <div>
            <button @click="requestTest">요청</button>
        </div>
        <div>
            <vue-json-pretty :data="response"/>
        </div>
    </div>
</template>

<script>
    import VueJsonPretty from 'vue-json-pretty';
    import {mapState} from 'vuex';
    import {test} from './../api';

    export default {
        name: "request",
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
        computed: {
            ...mapState({
                accessToken: state => state.accessToken,
                refreshToken: state => state.refreshToken,
            })
        },
        methods: {
            requestTest() {
                const headers = {};
                headers.Authorization = `Bearer ${this.accessToken}`;
                test(headers)
                    .then(res => this.response = res)
                    .catch(error => {
                        const response = error.response
                        console.log(response.data.errors)
                    })

            }
        }
    }
</script>

<style scoped>

</style>
