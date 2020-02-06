<template>
    <div>
        <div>
            <input type="text" v-model="member.id"/>
            <input type="password" v-model="member.password"/>

            <button @click="submitJoin">회원가입</button>
        </div>
        <div>
            <vue-json-pretty :data="response"/>
        </div>
    </div>
</template>

<script>
    import VueJsonPretty from 'vue-json-pretty'
    import {join} from './../api';

    export default {
        name: "join",
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
            async submitJoin() {
                try {
                    this.response = await join(this.member);
                } catch (e) {
                    this.response = e;
                }
            }
        }
    }
</script>

<style scoped>

</style>
