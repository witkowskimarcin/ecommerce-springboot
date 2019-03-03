<template>

<div id="wrapper">
  <div v-if="json===null">
    Json is null
  </div>
  <div v-else-if="json.cartQuantity===0 && accept_flag===false">
    <top></top>
    <h2>Brak produktów w koszyku</h2>
  </div>
  <div v-else>
    <div v-if="json.logged===false">
      Unauthorizated
    </div>
    <div v-else>
<top></top>

<div class="container">
<br>
<div class="container">
  <div v-if="form_flag">
<h1>Zamówienie</h1>
  </div><div v-if="sum_flag"><h1>Podsumowanie zamówienia</h1></div>
<br>
<div v-if="accept_flag===false">
<h4>Produkty</h4>
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
      <td>{{product.first.name}}</td>
      <td><span>{{product.second}}</span></td>
      <td>{{product.first.price}} zł</td>
      <td>{{product.first.price*product.second}} zł</td>
    </tr>
  </tbody>
</table>
</div>

<div v-if="form_flag">

  <h4>Przesyłka</h4>
  <div class="input-group mb-3">
    <div class="input-group-prepend">
      <label class="input-group-text" for="shipment" >Wybierz rodzaj przesyłki</label>
    </div>
    <select class="custom-select" v-model="shipment" required>
      <optgroup label="Płatność z góry">
        <option value="1" >Przesyłka kurierska - 20 zł</option>
        <option value="2">Paczkomaty Inpost - 10 zł</option>
        <option value="3">List polecony piorytetowy - 10 zł</option>
    </optgroup>
    <optgroup label="Płatność przy odbiorze">
      <option value="4">Przesyłka kurierska pobraniowa - 20 zł</option>
        <option value="5">Paczka pocztowa pobraniowa piorytetowa - 15 zł</option>
    </optgroup>
    </select>
  </div>
  <h4>Dane do wysyłki</h4>
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

    <label><input type="checkbox" id="checkbox" v-model="savedata"> Zapamiętaj dane dla następnych zamówień</label>
  <br>
  Razem do zapłaty: <span>{{json.sum}} zł</span>
  <br>
  <button class="btn btn-lg btn-primary btn-block" v-on:click="makeOrder">Przejdź do podsumowania</button>


</div>

<div v-if="sum_flag">
<br><br>
<h4>Dane zamówienia</h4>
  <table class="table table-striped">
    <tr>
      <th>Imię</th>
      <th>{{firstname}}</th>
    </tr>
    <tr>
      <th>Nazwisko</th>
      <th>{{lastname}}</th>
    </tr>
    <tr>
      <th>Miejscowość</th>
      <th>{{locality}}</th>
    </tr>
    <tr>
      <th>Ulica i numer ulicy</th>
      <th>{{street}}</th>
    </tr>
    <tr>
      <th>Telefon</th>
      <th>{{phone}}</th>
    </tr>
    <tr>
      <th>Kod pocztowy</th>
      <th>{{zipCode}}</th>
    </tr>
    <tr>
      <th>Przesyłka</th>
      <th>{{shipment}}</th>
    </tr>
    <tr>
      <th>Uwagi do zamówienia</th>
      <th v-if="description==='undefined'">Brak</th>
      <th v-else>{{description}}</th>
    </tr>
  </table>
  <button class="btn btn-lg btn-primary btn-block" v-on:click="acceptOrder">Złóż zamówienie</button>
</div>

<div v-if="accept_flag">
  <div v-if="fine">
<h2>Złożyłeś zamówienie, sprawdź e-mail</h2>
  </div>
    <div v-else>
<h2>Nie udało się złożyć zamówienia, przepraszamy</h2>
  </div>
</div>

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
              description:null,
              form_flag:true,
              sum_flag: false,
              accept_flag:false,
              savedata:true,
              fine:false
          }
      },
      components: {
        'top':top
      },
  		  methods: {

          receiveData: function(){

            address = 'http://localhost:8080/rest/order';

              axios.get(address, {withCredentials: true}).then(response => {
                if(response.status === 200){
                  console.log(response.data);
                  console.log(response.headers);
                  this.json = response.data;

                  if(!this.json.logged){
                      this.$router.push('/login');
                  }

                  this.shipment = 1;
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
              if(this.firstname !== null && 
              this.shipment !== null && 
              this.phone!==null  &&
              this.lastname!==null &&
              this.locality!==null &&
              this.street!==null &&
              this.zipCode!==null &&
              this.description!==null)
              {
                  //address = 'http://localhost:8080/rest/order?save='+this.savedata;
                  address = 'http://localhost:8080/rest/order';

                  const body = new URLSearchParams();
                  body.append('firstname',this.firstname);
                  body.append('lastname',this.lastname);
                  body.append('locality',this.locality);
                  body.append('street',this.street);
                  body.append('phone',this.phone);
                  body.append('zipCode',this.zipCode);
                  body.append('shipment',this.shipment);
                  body.append('description',this.description);
                  //body.append('save',this.savedata);

                  // const parameters = {
                  //   save : this.savedata
                  // }

                  const config = {
                    headers: {
                      'Content-Type': 'application/x-www-form-urlencoded'
                    }
                  }

                  axios.post(address,body,config).then(response => {
                    if(response.status === 200){
                      console.log(response.data);
                      console.log("Zlozyles zamowienie");
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
                      this.form_flag=false;
                      this.sum_flag=true;
                      console.log("XDDD = "+this.phone);
                    }
                  }).catch(error => console.log(error));
              }
          },

          acceptOrder: function() {
              if(this.firstname !== null && 
              this.shipment !== null && 
              this.phone!==null  &&
              this.lastname!==null &&
              this.locality!==null &&
              this.street!==null &&
              this.zipCode!==null &&
              this.description!==null)
              {
                  address = 'http://localhost:8080/rest/order/accept?save='+this.savedata;

                  axios.get(address, {withCredentials: true}).then(response => {
                    if(response.status === 200){
                      //console.log(response.data);
                      console.log("Akceptowales zamowienie");
                      //console.log(response.headers);
                      this.json = response.data[0];
                      this.form_flag=false;
                      this.sum_flag=false;
                      this.accept_flag=true;
                      console.log(JSON.parse(JSON.stringify(this.json)));
                      this.fine = this.json.fine;
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
