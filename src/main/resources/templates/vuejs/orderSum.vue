<template>

<div id="wrapper">
  <div v-if="json===null">
    Json is null
  </div>
  <div v-else>
    <div v-if="json.logged===false">
      Unauthorizated
    </div>
    <div v-else>
<top></top>

<div class="container">

  <br><br><br>
<div class="container">
<h1>Zamówienie</h1>
<br>
<h3>Produkty</h3>
<table class="table table-striped">
  <thead>
    <tr>
      <th>Nazwa produktu</th>
      <th>Ilość</th>
      <th>Cena za sztuke</th>
      <th>Suma</th>
    </tr>
  </thead>
  <tbody>
    <tr v-for="product in json.cart">
      <td>{{product.key.name}}</td>
      <td><span>{{product.value}}</span></td>
      <td>{{product.key.price}} zł</td>
      <td>{{product.key.price*product.value}}</td>
    </tr>
  </tbody>
</table>

<form>
  <h3>Przesyłka</h3>
  <div class="input-group mb-3">
    <div class="input-group-prepend">
      <label class="input-group-text" for="shipment" >Wybierz rodzaj przesyłki</label>
    </div>
    <select class="custom-select" id="shipment" name="shipment" v-model="shipment">
      <optgroup label="Płatność z góry">
        <option value="1">Przesyłka kurierska - 20 zł</option>
        <option value="2">Paczkomaty Inpost - 10 zł</option>
        <option value="3">List polecony piorytetowy - 10 zł</option>
    </optgroup>
    <optgroup label="Płatność przy odbiorze">
      <option value="4">Przesyłka kurierska pobraniowa - 20 zł</option>
        <option value="5">Paczka pocztowa pobraniowa piorytetowa - 15 zł</option>
    </optgroup>
    </select>
  </div>
  <h3>Dane do wysyłki</h3>
  <div class="input-group mb-3">
    <div class="input-group-prepend">
      <label class="input-group-text" id="basic-addon3">Imię</label>
      </div>
    <input id="firstname" name="firstname" class="form-control" required autofocus v-model="firstname" >
  </div>
  <div class="input-group mb-3">
    <div class="input-group-prepend">
      <label class="input-group-text" id="basic-addon3">Nazwisko</label>
      </div>
      <input id="lastname" name="lastname" class="form-control" required autofocus v-model="lastname">
    </div>
    <div class="input-group mb-3">
    <div class="input-group-prepend">
      <label class="input-group-text" id="basic-addon3">Miejscowość</label>
      </div>
    <input id="locality" name="locality" class="form-control" required autofocus v-model="locality">
  </div>
  <div class="input-group mb-3">
    <div class="input-group-prepend">
      <label class="input-group-text" id="basic-addon3">Ulica i numer domu/mieszkania</label>
      </div>
    <input id="street" name="street" class="form-control" required autofocus v-model="street">
  </div>
  <div class="input-group mb-3">
    <div class="input-group-prepend">
      <label class="input-group-text" id="basic-addon3">Kod pocztowy</label>
      </div>
    <input id="zipCode" name="zipCode" class="form-control" required autofocus v-model="zipCode">
  </div>
  <div class="input-group mb-3">
    <div class="input-group-prepend">
      <label class="input-group-text" id="basic-addon3">Numer telefonu</label>
      </div>
    <input id="phone" name="phone" class="form-control" required autofocus v-model="phone">
  </div>
  <div class="input-group mb-3">
    <div class="input-group-prepend">
      <label class="input-group-text" id="basic-addon3">Uwagi do zamówienia</label>
      </div>
    <input id="description" name="description" class="form-control" v-model="description">
  </div>
  <br>
  Razem do zapłaty:<span>{{json.sum}} zł</span>
  <br>
  <button class="btn btn-lg btn-primary btn-block" v-on:click="makeOrder">Złóż zamówienie</button>
</form>

</div>

</div>

</div>

</div>

</div>

</template>

<script>

var top =  httpVueLoader('top.vue');

  module.exports = {
  	name: 'order',
      data: function() {
          return {
              json: null,
  						loginAuth: null,
  						rememberMe: null,
  						email: null,
              password: null,
              headers:null,
              error: false,
              shipment:null,
              firstname:null,
              lastname:null,
              locality:null,
              street:null,
              phone:null,
              zipCode:null,
              description:null
          }
      },
      components: {
        'top':top
      },
  		  methods: {

          receiveData: function(){

            address = 'http://localhost:8080/rest/order/';

              axios.post(address, {withCredentials: true}).then(response => {
                if(response.status === 200){
                  console.log(response.data);
                  console.log(response.headers);
                  this.json = response.data;

                  this.shipment = this.json.shipment;
                  this.firstname = this.json.firstname;
                  this.lastname = this.json.lastname;
                  this.locality = this.json.locality;
                  this.street = this.json.street;
                  this.phone = this.json.phone;
                  this.zipCode = this.json.zipCode;
                  this.description = this.json.description;
                }
              }).catch(error => console.log(error));
          },

          makeOrder: function() {
              if(phone!==null)
              {
                  address = 'http://localhost:8080/rest/order';

                  params = {
                    shipment : this.shipment,
                    firstname : this.firstname,
                    lastname : this.lastname,
                    locality : this.locality,
                    street : this.street,
                    phone : this.phone,
                    zipCode : this.zipCode,
                    description : this.description
                  }

                  axios.post(address, {withCredentials: true}, params).then(response => {
                    if(response.status === 200){
                      console.log(response.data);
                      console.log(response.headers);
                      this.json = response.data;
                    }
                  }).catch(error => console.log(error));
              }
          }

  		  },

  		created: function(){

  			this.receiveData();
  		}

  }

</script>
