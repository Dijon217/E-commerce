<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h4 class="pt-3">Add new Product</h4>
      </div>
    </div>

    <div class="row">
      <div class="col-3"></div>
      <div class="col-md-6 px-5 px-md-0">
        <form @submit.prevent="addProduct">
          <div class="form-group">
            <label>Category</label>
            <select class="form-control" v-model="categoryId" required>
              <option v-for="category of categories" :key="category.id" :value="category.id">
                {{ category.categoryName }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label>Name</label>
            <input type="text" class="form-control" v-model="name" required />
          </div>
          <div class="form-group">
            <label>Description</label>
            <input type="text" class="form-control" v-model="description" required />
          </div>
          <div class="form-group">
            <label>Image File</label>
            <input type="file" class="form-control" @change="handleFileChange" accept="image/*" required />
          </div>
          <div class="form-group">
            <label>Price</label>
            <input type="number" class="form-control" v-model="price" required />
          </div>
          <button type="submit" class="btn btn-primary">Submit</button>
        </form>
      </div>
      <div class="col-3"></div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import swal from "sweetalert";

export default {
  props: ["baseURL", "categories"],
  data() {
    return {
      categoryId: null,
      name: null,
      description: null,
      imageFile: null,
      price: null
    };
  },
  methods: {
    handleFileChange(event) {
      this.imageFile = event.target.files[0];
    },
    async addProduct() {
      const formData = new FormData();
      formData.append("categoryId", this.categoryId);
      formData.append("name", this.name);
      formData.append("description", this.description);
      formData.append("image", this.imageFile);
      formData.append("price", this.price);

      try {
        const response = await axios.post(
          "http://localhost:8080/product/add",
          formData,{
            headers: {
              "Content-Type": "multipart/form-data",
            },
          }
        );

        const successMessage =
          response.data && response.data.message
            ? response.data.message
            : "Product added successfully!";

        swal({
          text: successMessage,
          icon: "success",
          closeOnClickOutside: false,
        });

        window.location.href = "/admin/product";
      } catch (err) {
        const errorMessage =
          err.response && err.response.data && err.response.data.message
            ? err.response.data.message
            : "Failed to upload product";

        swal(errorMessage, {
          icon: "error",
        });
      }
    },
  },
};
</script>

<style>
h4 {
  font-family: "Roboto", sans-serif;
  color: #484848;
  font-weight: 700;
}
</style>
