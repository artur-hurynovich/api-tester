<template>
    <div>
            <table>
                <tr>
                    <td>
                        <span>e-mail</span>
                    </td>

                    <td>
                        <input id="emailInput" type="email" autocomplete="username" />
                    </td>
                </tr>

                <tr>
                    <td>
                        <span>password</span>
                    </td>

                    <td>
                        <input id="passwordInput" type="password" autocomplete="current-password"/>
                    </td>
                </tr>

                <tr>
                    <td>
                        
                    </td>

                    <td>
                        <button v-on:click="sendLoginRequest()">Login</button>
                    </td>
                </tr>
            </table>
    </div>
</template>

<script>
export default {
    methods: {
        sendLoginRequest() {
            var successLogin = false;

			var xhttp = new XMLHttpRequest();

            xhttp.onreadystatechange = function() {
                if (this.readyState == 4) {
                    var jsonResponse = JSON.parse(this.responseText);

                    if (this.status == 200) {
                        sessionStorage.token = jsonResponse.token;

                        successLogin = true;
                    } else {
                        alert(jsonResponse.validationResult.descriptions);
                    }
                }
            };

            xhttp.open("POST", "http://localhost:8008/api-tester/api/authentication/login", true);

            xhttp.setRequestHeader("Content-type", "application/json");

            var email = document.getElementById("emailInput").value;
            var password = document.getElementById("passwordInput").value;

            xhttp.send(JSON.stringify({'email':email,'password':password}));

            if (successLogin) {
                this.$router.push("/");
            }
        }
    }
}
</script>
