<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h4 class="pt-3">Edit Product</h4>
      </div>
    </div>

    <div class="row">
      <div class="col-3"></div>
      <div class="col-md-6 px-5 px-md-0">
        <form>
          <div class="form-group">
            <label>Category</label>
            <select class="form-control" v-model="categoryId" required>
              <option v-for="category of categories" :key="category.id" :value="category.id">{{category.categoryName}}</option>
            </select>
          </div>
          <div class="form-group">
            <label>Name</label>
            <input type="text" class="form-control" v-model="name" required>
          </div>
          <div class="form-group">
            <label>Description</label>
            <input type="text" class="form-control" v-model="description" required>
          </div>
          <div class="form-group">
            <label>ImageURL</label>
            <input type="url" class="form-control" v-model="imageURL" required>
          </div>
          <div class="form-group">
            <label>Price</label>
            <input type="number" class="form-control" v-model="price" required>
          </div>
          <div class="form-group">
            <label>Quantity</label>
            <input type="number" class="form-control" v-model="quantity" required>
          </div>
          <button type="button" class="btn btn-primary" @click="editProduct">Update</button>
          <button type="button" class="btn btn-danger" @click="deleteProduct">Delete</button>
        </form>
      </div>
      <div class="col-3"></div>
    </div>
  </div>
</template>

<script>
// var axios =  require('axios');
import axios from 'axios';
import swal from 'sweetalert';
export default {
  data(){
    return {
      id : null,
      categoryId : 0,
      name : null,
      description : null,
      imageURL : null,
      price : 0,
      productIndex : null,
      quantity: 0
    }
  },
  props : ["baseURL", "products", "categories"],
  methods : {
    async editProduct() {
      const updatedProduct = {
        id : this.id,
        categoryId : this.categoryId,
        name : this.name,
        description : this.description,
        imageURL : this.imageURL,
        price : this.price,
        quantity : this.quantity
      }

      await axios({
        method: 'post',
        url: this.baseURL+"product/update/"+this.id,
        data : JSON.stringify(updatedProduct),
        headers: {
          'Content-Type': 'application/json'
        }
      })
      .then(() => {
        //sending the event to parent to handle
        this.$emit("fetchData");
        this.$router.push({name : 'AdminProduct'});
        swal({
          text: "Product Updated Successfully!",
          icon: "success",
          closeOnClickOutside: false,
        });
      })
      .catch(err => console.log("Hello", err));
    },

    async deleteProduct(){
      try {
        const response = await axios.post(this.baseURL + "product/delete/" + this.id);
        const successMessage = response.data && response.data.message 
          ? response.data.message 
          : "Product deleted successfully";

          swal({
          text: successMessage,
          icon: "success",
          closeOnClickOutside: false,
        });
        window.location.href = "/admin/product";
      } catch (err) {
        swal("Failed to delete product", {
          icon: "error"
        });
        console.error(err);
      }
    }
  },
  mounted() {
    console.log(this.products)
    this.id = this.$route.params.id;
    this.productIndex = this.products.findIndex(product => product.id == this.id);
    console.log(this.productIndex)
    //input fields
    this.categoryId = this.products[this.productIndex].categoryId
    console.log(this.products[this.productIndex].categoryId)
    this.name = this.products[this.productIndex].name;
    this.description = this.products[this.productIndex].description;
    this.imageURL = this.products[this.productIndex].imageUrl;
    this.price = this.products[this.productIndex].price;
    this.quantity = this.products[this.productIndex].quantity
  }
}
</script>

<style scoped>
h4 {
  font-family: 'Roboto', sans-serif;
  color: #484848;
  font-weight: 700;
}

.btn{
  margin-right: 16px;

}
</style>