<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h3 class="pt-3">Add new Category</h3>
      </div>
    </div>

    <div class="row">
      <div class="col-3"></div>
      <div class="col-md-6 px-5 px-md-0">
        <form @submit.prevent="addCategory">
          <div class="form-group">
            <label>Category Name</label>
            <input
              type="text"
              class="form-control"
              v-model="categoryName"
              required
              placeholder="Enter category name"
            />
          </div>

          <div class="form-group">
            <label>Description</label>
            <input
              type="text"
              class="form-control"
              v-model="description"
              required
              placeholder="Enter description"
            />
          </div>

          <div class="form-group">
            <label>Image File</label>
            <input
              type="file"
              class="form-control"
              @change="handleFileChange"
              accept="image/*"
              required
            />
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
  data() {
    return {
      categoryName: null,
      description: null,
      imageFile: null,
    };
  },
  methods: {
    handleFileChange(event) {
      this.imageFile = event.target.files[0];
    },

    async addCategory() {
      if (!this.categoryName || !this.description || !this.imageFile) {
        swal("All fields including the image are required!", {
          icon: "error",
        });
        return;
      }

      const formData = new FormData();
      formData.append("categoryName", this.categoryName);
      formData.append("description", this.description);
      formData.append("image", this.imageFile); // Key name should match backend parameter

      try {
        const baseURL = "http://localhost:8080/";
        const response = await axios.post(baseURL + "category/create", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        });

      const successMessage = response.data && response.data.message
        ? response.data.message
        : "Category Added Successfully!";

        swal({
          text: successMessage,
          icon: "success",
          closeOnClickOutside: false,
        });

        // Reset form fields
        this.categoryName = null;
        this.description = null;
        this.imageFile = null;

        window.location.href = "/admin/category";  

        // Clear file input manually
        const fileInput = this.$el.querySelector('input[type="file"]');
        if (fileInput) fileInput.value = null;
      } catch (err) {
        // console.error("Upload error:", err);
        const errorMessage = err.message && err.response.data && err.response.data.message
          ? err.response.data.message
          : "Failed to upload category";

        swal(errorMessage, {
          icon: "error",
        });
      }
    },
  },
};
</script>

<style scoped>
h4 {
  font-family: 'Roboto', sans-serif;
  color: #484848;
  font-weight: 700;
}

.btn {
  margin-right: 16px;
}
</style>
