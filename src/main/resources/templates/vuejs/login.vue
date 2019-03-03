<template>

 <div>
    <top></top>
    <br>
<br>
<br>
    	<div class="container">
		<div class="alert alert-danger" role="alert" v-if="error">
			Adres email lub hasło jest niepoprawne!</div>
		<form @submit.prevent="login">
			<h1 class="h3 mb-3 font-weight-normal">Logowanie</h1>
			<label for="inputEmail" class="sr-only">E-mail</label> 
            <input v-model="email"
				type="text" id="email" name="email" class="form-control"
				placeholder="E-mail" required autofocus> <label
				for="inputPassword" class="sr-only">Hasło</label> 
            <input v-model="password"
				type="password" id="password" name="password" class="form-control"
				placeholder="Hasło" required>
			<div class="checkbox mb-3">
            <br>
				<label><input type="checkbox" name="remember-me"/>Zapamiętaj mnie</label>
			</div>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Zaloguj się</button>
			<div class="margin-top20 text-center">Nie masz konta? <a v-bind:href="'index.html#/signup'">Zarejestruj się</a>
			</div>
		</form>
	</div>
 </div>
</template>

<script>

var top =  httpVueLoader('top.vue');

module.exports = {
	name: 'login',

    data: function() {
        return {
            email: null,
            password: null,
            error: false
        }
    },
      components: {
        'top':top
      },

	methods: {

                login: function() {

							axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
							address = 'http://localhost:8080/api/auth/signin';

              var _headers = {
								headers: {
										withCredentials: true,
										'Content-Type': 'application/x-www-form-urlencoded'
								}
							};
							
							var _form = 'username='+this.email+'&password='+this.password;

							axios.post(address, _form, _headers)
								.then(response => {
								if(response.status === 200){
										console.log(JSON.parse(JSON.stringify(response.data)));
										this.$cookies.set("access_token",response.data.accessToken);
										axios.defaults.headers.common['Authorization'] = "Bearer "+response.data.accessToken;
										//alert(axios.defaults.headers.common['Authorization']);

										this.receiveData();
								}
								
							}).catch(error => 
							{
								//if(response.status === 400)
								//{
									this.$router.push('/login?error=true');
									this.$router.go();
								//}
								console.log(error)
								});
            },

  			// 	login: function () {

        //     this.loginAuth = 'Basic ' + btoa(this.email + ':' + this.password);

        //     axios.defaults.headers.common['Authorization'] = this.loginAuth;

        //     address = 'http://localhost:8080/rest/login';

        //    axios.get(address, {withCredentials: true}).then(response => {
        //        if(response.status === 200){

        //          console.log(response.data);
        //          if(response.data.logged){
        //             console.log("Zalogowales sie");
        //             this.error = false;
        //             this.$router.push('/');
        //          }
        //          else{
        //              console.log("Zły email lub hasło"); 
        //              this.error = true;                    
        //          }
        //        } else {
        //            this.error = true;
        //        }
        //      }).catch(error => console.log(error));
        //         this.error = true;
  			// },

            receiveData: function() {
              address = 'http://localhost:8080/rest/logged';

              axios.get(address).then(response => {
                  if(response.status === 200){

                    console.log(response.data);
                    this.logged = response.data.logged;

                    if(this.logged)
                    {
                        console.log("Jestes zalogowany");
                        //this.$router.go('/');
                        this.$router.push('/');
                    }
                  }
                }).catch(error => console.log(error));
            }
    },
		
	created: function(){

        // if(this.$cookies.isKey("user_session")){
        //     loginAuth = this.$cookies.get("user_session");
        // }
        // else{

        // }
        if(this.$route.query.error==='true')
        this.error = this.$route.query.error;

        this.receiveData();
    }

}
</script>

<style>
</style>