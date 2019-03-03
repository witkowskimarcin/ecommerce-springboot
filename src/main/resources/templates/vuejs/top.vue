<template>

<!-- DIV TEMPLATE -->
<div id="wrapper">

  <div v-if="loginjson===null">

  </div>
  	<div v-else>
  	<!-- HEADER -->
    	<!-- Menu -->
  	<div>
  		<nav class="navbar navbar-expand-lg navbar-dark bg-primary fixed-top"
  			role="navigation">
  			<div class="container">
  				<a class="navbar-brand" v-bind:href="'index.html'">Sklep</a>
  				<button class="navbar-toggler border-0" type="button"
  					data-toggle="collapse" data-target="#exCollapsingNavbar">
  					&#9776;</button>
  				<div class="collapse navbar-collapse" id="exCollapsingNavbar">
  					<ul class="nav navbar-nav">
  						<li class="nav-item active"><a class="nav-link" v-bind:href="'index.html'">Strona główna</a></li>
  					</ul>

  					<form class="form-inline my-2 my-lg-0">
  						<input class="form-control mr-sm-4" type="search"
  							placeholder="Wpisz szukaną frazę" aria-label="Search">
  						<button class="btn btn-outline-light my-2 my-sm-0" type="submit">Szukaj</button>
  					</form>

  					<ul class="nav navbar-nav flex-row justify-content-between ml-auto">

  						<div v-if="loginjson.logged">
  							<li class="nav-item"><a class="nav-link">Zalogowany jako
  									<span>{{loginjson.username}}</span>
  							</a></li>
  						</div>

  						<li class="nav-item"><a href="#" class="nav-link"
  							title="settings" data-toggle="modal" data-target="#modalRegister">
  								<i class="fa fa-cog fa-fw fa-lg"></i>
  						</a></li>

  						<div v-if="loginjson.logged">
  							<li class="nav-item"><a type="button" class="btn btn-outline-light" v-on:click="logout" href="#">Wyloguj</a></li>
  						</div>

  						<div v-else>
  							<li class="nav-item">
  								<button type="button" id="dropdownMenu1" data-toggle="dropdown"
  									class="btn btn-outline-light dropdown-toggle">
  									Zaloguj się <span class="caret"></span>
  								</button> <!-- Login Form -->

  								<ul class="dropdown-menu dropdown-menu-right mt-2">
  									<li class="px-3 py-2">

  										<form @submit.prevent="login">
  											<h1 class="h3 mb-3 font-weight-normal">Logowanie</h1>
  											<label class="sr-only"></label>
  											<input v-model="email" class="form-control" placeholder="E-mail" required autofocus>
  											<label class="sr-only"></label>
  											<input v-model="password" type="password" class="form-control" placeholder="Hasło" required>
  											<div class="checkbox mb-3">
  												<label>
  												<input type="checkbox" v-model="rememberMe"/>Zapamietaj mnie
  												</label>
  											</div>
  											<button class="btn btn-lg btn-primary btn-block"
  												type="submit">Zaloguj</button>
  											<div class="margin-top20 text-center">
  												Nie masz konta? <a v-bind:href="'index.html#/signup'">Zarejestruj się</a>
  											</div>
  										</form>

  									</li>
  								</ul>
  							</li>
  						</div>
  						<!-- DIV IF  -->

  						<li class="nav-item">
  							<form>
  								<a v-if="loginjson.cartQuantity===0" type="button" class="btn btn-outline-light" v-bind:href="'index.html#/cart'">Koszyk (<span>0</span>)
  								</a>
  								<a v-else type="button" class="btn btn-danger" v-bind:href="'index.html#/cart'" >Koszyk (<span>{{cartQuantity}}</span>)
  								</a>
  							</form>
  						</li>
  					</ul>
  				</div>
  			</div>
  		</nav>
  	</div>
    <br><br><br>
  <!-- !HEADER -->
</div>

</div>
<!-- ! DIV TEMPLATE -->

</template>

<script>

  module.exports = {
  	name: 'top',
    cartQuantity: 0,
      data: function() {
          return {
              loginjson: null,
  						loginAuth: null,
  						rememberMe: null,
  						email: null,
              password: null,
              headers:null,
              xd: 'null',
							error: false,
							jsessionid: null
              //cartQuantity: 0
          }
      },
  		  methods: {

            login: function() {

							axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
							address = 'http://localhost:8080/api/auth/signin';

              var _body = {
								body:{
									email: this.email,
									password: this.password
								}
              };

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
								//}
								console.log(error)
								});
            },

  				// login: function () {

          // this.loginAuth = 'Basic ' + btoa(this.email + ':' + this.password);
          // //axios.defaults.headers.common['Authorization'] = this.loginAuth;

					// address = 'http://localhost:8080/rest/login';

          //  axios.get(address, {withCredentials: true}).then(response => {
          //      if(response.status === 200){

          //        console.log(response.data);
          //        //console.log(response.headers);
          //        //this.headers = axios.defaults.headers;
          //        this.loginjson = response.data;

					// 			 if(this.loginjson.logged === false){
					// 				 this.$router.push('/login?error=true');
					// 			 }
          //      } else {
					// 			 this.$router.push('/login?error=true');
					// 		 }
          //    }).catch(error => {
					// 		 console.log(error);
					// 		 this.$router.push('/login?error=true');
					// 		 });

  				// 	},

            logout: function(){

							

							//axios.defaults.headers.common['Authorization'] = '';
							//this.receiveData();

              
              //address = 'http://localhost:8080/logout';
              address = 'http://localhost:8080/api/auth/logout';
              axios.get(address, {withCredentials: true}).then(response => {
								console.log("Wylogowales sie");
								console.log(JSON.parse(JSON.stringify(response.data)));

							this.$cookies.set("access_token",'');
							axios.defaults.headers.common = {};
							//axios.defaults.headers.common['Authorization'] = '';
							this.receiveData();
							//this.$router.go();


							}).catch(error => console.log(error));


            },

            receiveData: function() {
              address = 'http://localhost:8080/rest/logged';

              var _body = {
                //'cos':'cos'
              }

              var _headers = {
                headers:{
                  //'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNTQ5OTEzMDAwLCJleHAiOjE1NDk5OTk0MDB9.YF42mo0ayyC_lwY3ower9oaqVIb7kHGlquJQ-_Xx-nLYOTz3G4WpHSsKtTExhSW839xcXPDeYOpQK-vtGuJhfQ'
                }
              };

              axios.get(address,_body,_headers).then(response => {
                  if(response.status === 200){

                    //console.log(response.data);
                    //console.log(response.headers);
                    //console.log(axios.defaults.headers);
                    //this.headers = axios.defaults.headers;
                    this.xd = response.headers;
                    this.loginjson = response.data;
										this.cartQuantity = this.loginjson.cartQuantity;
										console.log(JSON.parse(JSON.stringify(response.data)));
                  }
                }).catch(error => console.log(error));
            }
  		  },

  		created: function(){

				//axios.defaults.withCredentials = true;
        //axios.defaults.headers.common['Accept'] = 'application/json, text/plain, */*';
        //axios.defaults.headers.common['Accept-Language'] = 'pl';
				axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
//alert(this.$cookies.get("access_token"));

if(this.$cookies.get("access_token")!=null){
	if(this.$cookies.get("access_token")!=''){
				axios.defaults.headers.common['Authorization'] = "Bearer "+this.$cookies.get("access_token");
	}
}

// if(this.$cookies.get("JSESSIONID")==null){
// 					axios.get('http://localhost:8080/rest/getsessionid', {withCredentials: true}).then(response => {
// 						if(response.status === 200){
// 							console.log(JSON.parse(JSON.stringify(response.data)));
// 							console.log('set: '+JSON.parse(JSON.stringify(response.headers)));
// 							this.$cookies.set("JSESSIONID",response.data.JSESSIONID);
// 							this.jsessionid = response.data.JSESSIONID;
// 						}
// 					}).catch(error => console.log(error));
// }
// else
// {
// 	this.jsessionid = this.$cookies.get("JSESSIONID");
// }

//  if(this.$cookies.get("JSESSIONID")==null){
				
// 				axios.get('http://localhost:8080/rest/getsessionid', {withCredentials: true}).then(response => {
// 						if(response.status === 200){
// 							console.log(JSON.parse(JSON.stringify(response.data)));
// 							console.log('set: '+JSON.parse(JSON.stringify(response.headers)));
// 							this.$cookies.set("JSESSIONID",response.data.JSESSIONID);
// 							//axios.defaults.headers.common['Cookie'] = "JSESSIONID="+response.data.JSESSIONID;
// 						}
// 					}).catch(error => console.log(error));
//  }	
//  else
 //{
	 //axios.defaults.headers.common['Cookie'] = "JSESSIONID="+this.$cookies.get("JSESSIONID");
 //}	

//alert($('meta[name="Set-Cookie"]').attr('value'));

            this.receiveData();

            }
        }
</script>
