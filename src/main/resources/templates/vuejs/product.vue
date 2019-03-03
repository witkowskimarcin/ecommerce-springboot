<template>
  <div id="container">
    <top></top>

  <!-- <div v-if="json===null">
  		Nie można połączyć się z serwisem :(
  </div>
  	<div v-else> -->
<br>
<br>
<br>
<div class="container">

  <div class="row">

    <div class="col-sm-4">

      <!--- Image Slider -->

      <div id="slides" class="carousel slide" data-ride="carousel">
        <ul class="carousel-indicators">
          <li data-target="#slides" data-slide-to="0" class="active"></li>
          <!-- <label v-for="i in json.iteration">
            <li data-target="#slides" v-bind:data-slide-to="i">
          </li>
          </label> -->
        </ul>
        <div class="carousel-inner" height="250">

          <div class="carousel-item active">
          <a rel="lightbox[0]" v-bind:href="'data:image/jpeg;base64,'+product.images[0].image">
            <img v-bind:src="'data:image/jpeg;base64,'+product.images[0].image"
               class="img-circle mx-auto d-block"
               height="250" alt="obrazek">
          </a>
          </div>


            <!-- <div class="carousel-item" v-for="i in json.iteration">
            <a  v-bind:href="'data:image/jpeg;base64,'+json.product.images[i].image">
              <img v-bind:src="'data:image/jpeg;base64,'+json.product.images[i].image"
                 class="img-circle mx-auto d-block"
                 height="250" v-bind:alt="i"/>
                 </a>
            </div> -->

        </div>
      </div>

    </div>

    <div class="col-sm-4">
      <h3>{{product.name}}</h3>
      <h3>{{product.description}}</h3>
    </div>

    <div class="col-sm-4">

      <div class="card" style="max-width: 22rem; height: 24rem;">
        <div class="card-body">
          <h3 class="card-title text-center">{{product.price}} zł</h3>
          <h5 class="card-text">Odbiór osobisty: GRATIS</h5>
          <h5 class="card-text">Dostępnych sztuk: {{product.quantity}}</h5>
          <h5 class="card-text"><a href="#">Koszty dostawy</a></h5>
          <h5 class="card-text"><a href="#">Sprawdź dostępność w salonach</a></h5>
          <div v-if="product.quantity>0">
          <a class="btn btn-lg btn-success btn-block" style="color:#ffffff;" v-on:click="addToCart">Do koszyka</a>
          </div>
          <div v-else>
            <a class="btn btn-lg btn-danger btn-block" style="color:#ffffff;">Brak produktu</a>
          </div>
        </div>
      </div>

    </div>

  </div>

</div>

</div>

<!-- </div> -->

</template>

<script>

var top =  httpVueLoader('top.vue');
//console.log(top.cartQuantity);

  module.exports = {
  	name: 'product',
      data: function() {
          return {
              json: null,
              product: null,
  						loginAuth: null,
  						rememberMe: null,
  						email: null,
              password: null,
              error: false
          }
      },
      components: {
        'top':top
      },
  		  methods: {

          addToCart: function(){
            //axios.get('http://localhost:8080/rest/products/product/add?code='+this.$route.query.code, {withCredentials: true}).then(response => {
            axios.get('http://localhost:8080/rest/products/product/add?code='+this.$route.query.code).then(response => {
              if(response.status === 200){
                console.log(response.data);
                console.log("Dodano do koszyka");
                //this.$forceUpdate();
                //this.$forceUpdate();
                //console.log(cartQuantity);
                this.$router.go();

              }
            }).catch(error => console.log(error));
          },

          receiveData: function(){
              //axios.get('http://localhost:8080/rest/products/product?code='+this.$route.query.code, {withCredentials: true}).then(response => {
              axios.get('http://localhost:8080/rest/products/product?code='+this.$route.query.code).then(response => {
                if(response.status === 200){
                  //console.log(response.data[0]);
                  //console.log(response.headers);
                  //this.json = response.data[0];
                  this.product = response.data;
                  console.log(JSON.parse(JSON.stringify(this.product)));
                }
              }).catch(error => console.log(error));
          }
  		  },

  		created: function(){

  			console.log(this.$route.query.code);
  			this.receiveData();
  		}

  }

</script>
