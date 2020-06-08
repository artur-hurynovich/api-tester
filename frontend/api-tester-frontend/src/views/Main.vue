<template>
    <div>
        MAIN PAGE
    </div>    
</template>

<script>
export default {
    mounted() {
        if (sessionStorage.getItem("token") == null) {
            this.$router.push("/access-denied");
        }
    }
}
</script>
