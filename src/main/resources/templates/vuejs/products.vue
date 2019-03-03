<template>
	<div id="container">
	<top></top>
<!-- <div v-if="json===null">
		Nie można połączyć się z serwisem :(
</div>
	<div v-else> -->

	<div class="container">
	<br><br><br>

			<nav aria-label="breadcrumb">
		  <ol class="breadcrumb">
		    <li class="breadcrumb-item">
		    <a v-bind:href="'index.html#/'">Strona główna</a>
		    </li>
		    <li class="breadcrumb-item">
		    <!-- <a v-bind:href="'index.html#/'">{{category}}</a> -->
		    </li>
		    <li class="breadcrumb-item active">
		    <!-- <a>{{json.subcategory}}</a> -->
		    </li>
		  </ol>
		</nav>

		<table class="table table-striped">
			<tr v-for="product in products">
				<td><img v-bind:src="'data:image/jpeg;base64,'+product.images[0].image" alt="miniaturka"/></td>
				<td><a v-bind:href="'index.html#/products/product?code='+product.id">{{product.name}}</a></td>
				<td>{{product.description}}</td>
				<td>{{product.price}} zł</td>
			</tr>
		</table>
	</div>

</div>
<!-- </div> -->
</template>

<script>

var top =  httpVueLoader('top.vue');

module.exports = {
	name: 'products',
    data: function() {
        return {
						json: null,
						products: null,
						loginAuth: null,
						rememberMe: null,
						email: null,
            password: null,
            error: false
        }
    },
		components: {
			'top' : top
		},
		  methods: {

				receiveData: function(){

						axios.get('http://localhost:8080/rest/products?subCatId='+this.$route.query.subCatId, {withCredentials: true}).then(response => {
							if(response.status === 200){
								this.products = response.data;
							}
						}).catch(error => console.log(error));
				}
		  },

		created: function(){

			console.log(this.$route.query.subCatId);

			this.receiveData();

		}

}
</script>

<style scoped>

img {
	max-height: 150px;
}

</style>
