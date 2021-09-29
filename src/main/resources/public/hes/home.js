const app = new Vue({
    el: '#app',
    data() {
        return {

        }
    },
    created() {

    },
    methods: {
        handleOpen(key, keyPath) {
            console.log(key, keyPath);
        },
        handleClose(key, keyPath) {
            console.log(key, keyPath);
        }

    },
    watch: {


    }
})
