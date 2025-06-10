<template>
  <div class="card h-100" v-if="product">
    <div class="embed-responsive embed-responsive-16by9">
      <img class="card-img-top embed-responsive-item" :src="product.imageUrl" alt="Product Image">
    </div>
    <div class="card-body">
      <router-link :to="{ name: 'ShowDetails', params: { id : product.id } }"><h5 class="card-title">{{product.name}}</h5></router-link>
      <p class="card-text"><sup>$</sup>{{product.price}}</p>
      <p class="card-text font-italic">{{product.description.substring(0,65)}}...</p>
      <div class="mt-auto d-flex justify-content-end">
        <router-link
          v-if="$route.name == 'AdminProduct'"
          :to="{ name: 'EditProduct', params: { id: product.id } }"
        >Edit</router-link>

        <router-link
          v-if="$route.name == 'WishList'"
          class="remove-link"
          :to="{ name: 'EditProduct', params: { id: product.id } }"
        >
        Remove
      </router-link>
        </div>
    </div>
  </div>
</template>

<script>
export default {
    name : "ProductBox",
    props : ["product"],
    methods : {
    }
}
</script>

<style scoped>

.embed-responsive .card-img-top {
  object-fit: cover;
}

.card {
  width: 20rem;
  height: 24rem;
  display: flex;
  flex-direction: column;
}

.card-body {
  display: flex;
  flex-direction: column;
}

.card-title {
  color: #484848;
  font-size: 1.1rem;
  font-weight: 400;
}

.card-title:hover {
  font-weight: bold;
}

.card-text {
  font-size: 0.9rem;
}

a {
  text-decoration: none;
}

#edit-product {
  float: right;
}

/* .remove-link{
  color: crimson;
} */
</style>