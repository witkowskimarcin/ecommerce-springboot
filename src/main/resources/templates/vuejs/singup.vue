<template>
<div>
        <top></top>
<br>
<br>
<div class="container">

     <h2>Rejestracja</h2>
     <hr>
     <form @submit.prevent="signup">
      <div class="row">
            <div class="alert alert-success" role="alert" v-if="success">
			Zarejestrowałeś się!</div>

            <div class="alert alert-danger" role="alert" v-if="error1">
			Hasła się nie zgadzają!</div>
         
            <div class="alert alert-danger" role="alert" v-if="error2">
			Taka nazwa użytkownika już istnieje!</div>
            </div>
        <div class="row">
             <div class="col-md-3 field-label-responsive">
                 <label for="username">Nazwa użytkownika</label>
             </div>
             <div class="col-md-6">
                 <div class="form-group">
                     <div class="input-group mb-2 mr-sm-2 mb-sm-0">
                         <div class="input-group-addon" style="width: 2.6rem"><i class="fa fa-at"></i></div>
                         <input v-model="username" type="username" class="form-control" id="username" name="username"
                                placeholder="username" required autofocus>
                     </div>
                 </div>
             </div>
         </div>
         <div class="row">
             <div class="col-md-3 field-label-responsive">
                 <label for="email">E-mail</label>
             </div>
             <div class="col-md-6">
                 <div class="form-group">
                     <div class="input-group mb-2 mr-sm-2 mb-sm-0">
                         <div class="input-group-addon" style="width: 2.6rem"><i class="fa fa-at"></i></div>
                         <input v-model="email" type="email" class="form-control" id="email" name="email"
                                placeholder="email@email.pl" required autofocus>
                     </div>
                 </div>
             </div>
         </div>
                  <div class="row">
             <div class="col-md-3 field-label-responsive">
                 <label for="name">Imię</label>
             </div>
             <div class="col-md-6">
                 <div class="form-group">
                     <div class="input-group mb-2 mr-sm-2 mb-sm-0">
                         <div class="input-group-addon" style="width: 2.6rem"><i class="fa fa-user"></i></div>
                         <input v-model="firstname" type="text"  class="form-control" id="firstname" name="firstname"
                                placeholder="Imię" required autofocus>
                     </div>
                 </div>
             </div>
         </div>
         <div class="row">
             <div class="col-md-3 field-label-responsive">
                 <label for="name">Nazwisko</label>
             </div>
             <div class="col-md-6">
                 <div class="form-group">
                     <div class="input-group mb-2 mr-sm-2 mb-sm-0">
                         <div class="input-group-addon" style="width: 2.6rem"><i class="fa fa-user"></i></div>
                         <input v-model="lastname" type="text" class="form-control" id="lastname" name="lastname"
                                placeholder="Nazwisko" required autofocus>
                     </div>
                 </div>
             </div>
         </div>
         <div class="row">
             <div class="col-md-3 field-label-responsive">
                 <label for="password">Hasło</label>
             </div>
             <div class="col-md-6">
                 <div class="form-group has-danger">
                     <div class="input-group mb-2 mr-sm-2 mb-sm-0">
                         <div class="input-group-addon" style="width: 2.6rem"><i class="fa fa-key"></i></div>
                         <input v-model="password" type="password" class="form-control" id="password" name="password"
                                placeholder="Hasło" required>
                     </div>
                 </div>
             </div>
         </div>
         <div class="row">
             <div class="col-md-3 field-label-responsive">
                 <label for="password">Powtórz hasło</label>
             </div>
             <div class="col-md-6">
                 <div class="form-group has-danger">
                     <div class="input-group mb-2 mr-sm-2 mb-sm-0">
                         <div class="input-group-addon" style="width: 2.6rem"><i class="fa fa-key"></i></div>
                         <input v-model="confirm_password" type="password" class="form-control" id="confirm_password" name="confirm_password"
                                placeholder="Powtórz hasło" required>
                     </div>
                 </div>
             </div>
         </div>
         <div class="row">
             <div class="col-md-3"></div>
             <div class="col-md-6">
                 <button type="submit" class="btn btn-primary"><i class="fa fa-user-plus"></i> Zapisz się</button>
             </div>
         </div>
     </form>
 </div>
</div>
 </template>

 <script>

 var top =  httpVueLoader('top.vue');

  module.exports = {
  	name: 'signup',
    cartQuantity: 0,
      data: function() {
          return {
  			email: null,
  			firstname: null,
  			lastname: null,
  			username: null,
  			confirm_password: null,
            password: null,
            success: false,
            error1: false,
            error2: false,
            error3: false,
            error4: false
          }
      },
      components: {
        'top':top
      },
  		  methods: {

            signup: function() {

                                if(this.password !== this.confirm_password){
                    this.error1=true;
                    return;
                }

							axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
                            address = 'http://localhost:8080/api/auth/signup';
                            
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
							
                            var _form = 'username='+this.username
                            +'&password='+this.password
                            +'&firstname='+this.firstname
                            +'&lastname='+this.lastname
                            +'&email='+this.email;

							axios.post(address, _form, _headers)
								.then(response => {
								if(response.status === 200){
										console.log(JSON.parse(JSON.stringify(response.data)));
										this.$cookies.set("access_token",response.data.accessToken);
                                        axios.defaults.headers.common['Authorization'] = "Bearer "+response.data.accessToken;
                                        this.success=true;
                                        this.error1=false;
                                        this.error2=false;
                                        this.error3=false;
                                        this.error4=false;
										//alert(axios.defaults.headers.common['Authorization']);

										//this.receiveData();
								}
							}).catch(error => console.log(error));
            },

  		created: function(){

            }
        }
  }
</script>