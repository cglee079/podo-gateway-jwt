<template>
    <div id="app">
        <the-nav/>
        <router-view></router-view>
    </div>
</template>

<script>

    import TheNav from "./components/TheNav";

    export default {
        name: 'app',
        components: {
            TheNav
        }
    }
</script>

