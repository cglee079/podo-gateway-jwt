<template>
    <div>
        <router-link :to="{name : 'join'}">회원가입</router-link> &nbsp;
        <router-link :to="{name : 'login'}">로그인</router-link> &nbsp;
        <router-link :to="{name : 'request'}">요청테스트</router-link> &nbsp;
    </div>
</template>

<script>
    export default {
        name: "TheNav"
    }
</script>

<style scoped>

</style>
