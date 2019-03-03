<template>


	<div class="container">
    <top></top>

<div v-if="json===null">
	<h1>Koszyk jest pusty</h1>
</div>

<div v-else>

<div v-if="json.cart.length<=0">
	<h1>Koszyk jest pusty</h1>
</div>
  
<div v-else>

	<h1>Koszyk</h1>
	<br>
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
				<td><a v-bind:href="'index.html#/products/product?code='+product.first.id">{{product.first.name}}</a></td>
				<td><a class="btn btn-danger" style="color:#ffffff;" v-on:click="minus(product.first.id)">-</a>
          <span> {{product.second}} </span>
          <a class="btn btn-success" style="color:#ffffff;" v-on:click="plus(product.first.id)">+</a>
        </td>
				<td>{{product.first.price}} zł</td>
				<td>{{product.first.price*product.second}} zł</td>
			</tr>
		</tbody>
		<tfoot>
			<tr>
				<td>Razem: {{json.sum}} zł</td>
			</tr>
		</tfoot>
	</table>

	<a class="btn btn-danger" style="color:#ffffff;" v-on:click="cleanCart">Opróżnij koszyk</a>
	<a class="btn btn-primary" v-bind:href="'index.html#/order'">Złóż zamówienie</a>
</div>

</div>
	</div>

<!-- </div> -->

</template>

<script>

var top =  httpVueLoader('top.vue');

  module.exports = {
  	name: 'cart',
      data: function() {
          return {
              json: null,
  						loginAuth: null,
  						rememberMe: null,
  						email: null,
              password: null,
              headers:null,
              error: false
          }
      },
      components: {
        'top' : top
      },
  		  methods: {

          receiveData: function(){

            address = 'http://localhost:8080/rest/cart';

              axios.get(address, {withCredentials: true}).then(response => {
                if(response.status === 200){
                  //console.log(response.data);
                  //console.log(response.headers);
                  this.json = response.data[0];
                  console.log(JSON.parse(JSON.stringify(this.json)));
                }
              }).catch(error => console.log(error));
          },

          cleanCart: function(){
            address = 'http://localhost:8080/rest/cart/removeAll';

              axios.get(address, {withCredentials: true}).then(response => {
                if(response.status === 200){
                  console.log(response.data);
                  console.log("Oprozniono koszyk");
                  this.$router.go();
                }
              }).catch(error => console.log(error));
          },

          plus: function(id){
            address = 'http://localhost:8080/rest/cart/plus?code='+id;

              axios.get(address, {withCredentials: true}).then(response => {
                if(response.status === 200){
                  console.log(response.data);
                  console.log("Plusik");
                  this.$router.go();
                }
              }).catch(error => console.log(error));
          },

          minus: function(id){
            address = 'http://localhost:8080/rest/cart/minus?code='+id;

              axios.get(address, {withCredentials: true}).then(response => {
                if(response.status === 200){
                  console.log(response.data);
                  console.log("Minusik");
                  this.$router.go();
                }
              }).catch(error => console.log(error));
          }

  		  },

  		created: function(){

  			this.receiveData();
  		}

  }

</script>
