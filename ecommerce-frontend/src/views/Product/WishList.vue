<template>
    <div class="container">
      <div class="row">
        <div class="col-12 text-center">
          <h4 class="pt-3">Your WishList</h4>
        </div>
      </div>
  
      <div class="row">
        <div v-for="product of products" :key="product.id" class="col-md-6 col-xl-4 col-12 pt-3  justify-content-around d-flex">
          <ProductBox :product="product">
          </ProductBox>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import ProductBox from '../../components/Product/ProductBox';
  const axios = require('axios')
  export default {
    data() {
        return {
            products:null,
            token:null
        }
    },
    name: 'AppProduct',
    components : {ProductBox},
    props : [ "baseURL"],
    methods : {
      fetchWishlist() {
        // fetch products
        axios.get(`${this.baseURL}wishlist/${this.token}`)
          .then(data => this.products = data.data)
          .catch(err => console.log(err));
          console.log(this.products)
      },

      

      // addToWishList(productId){
      //       axios.post(`${this.baseURL}wishlist/add?productId=${productId}&token=${this.token}`).then((response) => {
      //           if(response.status==201) {
      //               swal({
      //                   text: "Added to WishList. Please continue",
      //                   icon: "success"
      //               });
      //           }
      //       },(error) =>{
      //           console.log(error)
      //           swal({
      //               text: "Something wrong with add to wishlist",
      //               icon: "error",
      //               closeOnClickOutside: false,
      //           });

      //       });
      //   },
    },
    mounted() {
          this.token = localStorage.getItem('token');
          this.fetchWishlist();
      }
  }
  </script>
  
  <style scoped>
  h4 {
    font-family: 'Roboto', sans-serif;
    color: #484848;
    font-weight: 700;
  }
  </style>