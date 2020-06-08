<template>
    <div>
        <div id="loginRequiredDiv" style="display: none">
            <table>
                <tr>
                    <td colspan="2">
                        <h3>You are not authorized</h3>
                    </td>
                </tr>

                <tr>
                    <td>
                        <router-link to="/registration">Registration</router-link>
                    </td>

                    <td>
                        <router-link to="/login">Login</router-link>
                    </td>
                </tr>
            </table>
        </div>

        <div id="noPermissionDiv" style="display: none">
            <h3>You have no permission to perform the action</h3>
        </div>
    </div>
</template>

<script>
export default {
    mounted() {
        if (sessionStorage.getItem("token") == null) {
            document.getElementById("loginRequiredDiv").style.display="block";
            document.getElementById("noPermissionDiv").style.display="none";
        } else {
            document.getElementById("loginRequiredDiv").style.display="none";
            document.getElementById("noPermissionDiv").style.display="block";
        }
    }
}
</script>
