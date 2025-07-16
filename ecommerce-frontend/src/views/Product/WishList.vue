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
            <template #default>
            <div class="mt-auto d-flex justify-content-end">
              <a href="#" @click.prevent="deleteItem(product.wishlistID)" class="text-danger">
                Remove
              </a>
            </div>
            </template>
          </ProductBox>
          
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import ProductBox from '../../components/Product/ProductBox';
  import swal from 'sweetalert';
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
          .then(response => {
            this.products = response.data;
            console.log(this.products); // now this will log the updated product list
          })
          .catch(err => console.log(err));
      },

      addToWishList(productId){
        axios.post(`${this.baseURL}wishlist/add?productId=${productId}&token=${this.token}`).then((response) => {
          if(response.status==201) {
            swal({
              text: "Added to WishList. Please continue",
              icon: "success"
              });
            }
          },(error) =>{
            console.log(error)
            swal({
              text: "Something wrong with add to wishlist",
                icon: "error",
                closeOnClickOutside: false,
            });
        });
      },

      deleteItem(wishlistProductID) {
        console.log(wishlistProductID)
      //  // 2. first delete the item by calling delete api
      axios.delete(`${this.baseURL}wishlist/delete?wishListProductId=${wishlistProductID}&token=${this.token}`)
        .then((response)=>{
          if(response.status == 200){
            // 3. refresh the data by calling listCartItems
            this.fetchWishlist();
          }
        },(error)=>{
          const msg = error.message
          swal({
              text: msg,
                icon: "info",
                closeOnClickOutside: false,
            });
        })
      },
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