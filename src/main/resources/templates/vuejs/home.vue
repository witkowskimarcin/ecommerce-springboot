<template>
	<div id="container">
		<top></top>
<!-- <div v-if="json===null">
		Nie można połączyć się z serwisem :(
	</div>
	<div v-else> -->

	<!-- Jumbotron -->

	<div class="jumbotron jumbotron-fluid">
		<div class="container">
			<h1 class="display-4">Sklep Komputerowy</h1>
			<p class="lead">Witaj w sklepie :)</p>
		</div>
	</div>

	<div class="container">
		<div class="row">
			<div class="col-2">
					<div v-for="category in categories" class="list-group dropright">
						<button type="button"
							class="list-group-item btn btn-outline-primary dropdown-toggle"
							data-toggle="dropdown">{{category.name}}</button>
						<div class="dropdown-menu">
							<a v-for="subcategory in category.subcategories"
								class="dropdown-item"
								v-bind:href="'index.html#/products?subCatId='+ subcategory.id">{{subcategory.name}}
							</a>
						</div>
					</div>
			</div>
			<div class="col-10">
				<div class="card">
					<div class="card-body">

						<!--- Image Slider -->

						<div id="slides" class="carousel slide" data-ride="carousel">
							<ul class="carousel-indicators">
								<li data-target="#slides" data-slide-to="0" class="active"></li>
								<li data-target="#slides" data-slide-to="1"></li>
								<li data-target="#slides" data-slide-to="2"></li>
							</ul>
							<div class="carousel-inner" height="250">
								<div class="carousel-item active">
									<img src="img/1.jpg"
										 class="img-circle mx-auto d-block"
										height="250" alt="tlo">
									<div class="carousel-caption">
										<h1 class="display-2">Polecamy</h1>
										<a class="btn btn-primary btn-lg">Sprawdź</a>
									</div>
								</div>
								<div class="carousel-item">
									<img src="img/2.jpg" class="img-circle mx-auto d-block"
										height="250" alt="tapeta2">
									<div class="carousel-caption">
										<h1 class="display-2">Polecamy</h1>
										<a class="btn btn-primary btn-lg">Sprawdź</a>
									</div>
								</div>
								<div class="carousel-item">
									<img src="img/3.jpg" class="img-circle mx-auto d-block"
										height="250" alt="tapeta3">
									<div class="carousel-caption">
										<h1 class="display-2">Polecamy</h1>
										<a class="btn btn-primary btn-lg">Sprawdź</a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<hr class="light row text-center">

	<!-- Promotion Cards -->

	<div class="container">
		<h1>Polecane produkty</h1>
		<br>

		<div class="row">

			<div class="col-sm-8">

				<div class="row">
					<div class="col-sm-4" v-for="pp in promotedproducts">
						<div class="card text-center" style="max-width: 22rem; height: 24rem;">
							<img class="card-img-top" v-bind:src="'data:image/jpeg;base64,'+pp.product.images[0].image" alt="Obrazek" style="max-height: 180px; width: auto;">
							<div class="card-body">
								<h5 class="card-title">{{pp.product.name}}</h5>
								<p class="card-text">{{pp.product.price}}</p>

								 <a class="btn btn-primary" v-bind:href="'index.html#/products/product?code='+ pp.product.id">Zobacz</a>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="col-sm-4" v-if="opportunity.product">
				<div class="card" style="max-width: 22rem; height: 49.5rem;">
					<h2>Złoty strzał</h2>
					<img class="card-img-top" v-bind:src="'data:image/jpeg;base64,'+opportunity.product.images[0].image" alt="Obrazek" style="max-width: 12rem;">
					<div class="card-body">
						<h5 class="card-title" th:text="${opportunity.product.name}">{{opportunity.product.name}}</h5>
						<p class="card-text"  th:text="${opportunity.product.description}">{{opportunity.product.description}}</p>
						<p class="card-text" th:text="${opportunity.product.price}+' zł'">{{opportunity.product.price}} zł</p>
						<h5 th:text="'Użyj kodu '+${opportunity.promotionCode}">Użyj kodu: {{opportunity.promotionCode}}</h5>
						<h6 th:text="'Zostało: '+${opportunity.quantity}">Zostało: {{opportunity.quantity}}</h6>
						<div class="progress">
							<div class="progress-bar" role="progressbar" style="width: 75%" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100"></div>
						</div>
						<br><a v-bind:href="'index.html#/products/product?code='+ opportunity.product.id" class="btn btn-primary">Do koszyka</a>
					</div>
				</div>
			</div>

		</div>
	</div>

	<hr class="light row text-center">

	<!-- Footer -->

	<div class="container">
		<footer>
			<div class="card container padding">
				<div class="card-body">

					<p>555-555-555</p>
					<p>email@gmail.com</p>
				</div>
			</div>
		</footer>
	</div>

  </div>

<!-- </div> -->
</template>

<script>

var top =  httpVueLoader('top.vue');

module.exports = {

	name: 'home',
    data: function() {
        return {
            json: null,
            opportunity: null,
            promotedproducts: null,
            categories: null,
						loginAuth: null,
						rememberMe: null,
						email: null,
            password: null,
            error: false
        }
    },
		components: {
			'top': top
		},

	methods: {
			receiveData: function(){

					//axios.get('http://localhost:8080/rest/home', {withCredentials: true}).then(response => {
					axios.get('http://localhost:8080/rest/home').then(response => {
						if(response.status === 200){
							this.json = response.data;
							this.categories = response.data.categories;
							this.promotedproducts = response.data.promotedproducts;
							this.opportunity = response.data.opportunity;

							console.log(JSON.parse(JSON.stringify(this.categories)));
							//console.log(JSON.parse(JSON.stringify(this.promotedproducts)));
						}
					}).catch(error => console.log(error));

					// axios.get('http://localhost:8080/rest/categories', {withCredentials: true}).then(response => {
					// 	if(response.status === 200){
					// 		//console.log(response.data);
					// 		//console.log(response.headers);
					// 		this.categories = response.data;
					// 	}
					// }).catch(error => console.log(error));

					// axios.get('http://localhost:8080/rest/promotedproducts', {withCredentials: true}).then(response => {
					// 	if(response.status === 200){
					// 		this.promotedproducts = response.data;
					// 	}
					// }).catch(error => console.log(error));

					// axios.get('http://localhost:8080/rest/opportunity', {withCredentials: true}).then(response => {
					// 	if(response.status === 200){
					// 		this.opportunity = response.data;
					// 	}
					// }).catch(error => console.log(error));
			}
		  },

		created: function(){

			// if(this.$cookies.login===null){
			// 	console.log("Brak cookie");
			// 	loginAuth = 'Basic '+ btoa('admin@admin.pl:qwerty');
			// 	this.$cookies.set("login", loginAuth, { expires: '1h' });
			// }
			// else{
			// 	loginAuth = this.$cookies.login;

			// }

			this.receiveData();
		}


}
</script>

<style>
.hello {
    background-color: #ffe;
}
</style>
