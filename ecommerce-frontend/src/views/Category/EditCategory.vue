<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h4 class="pt-3">Edit Category</h4>
      </div>
    </div>

    <div class="row">
      <div class="col-3"></div>
      <div class="col-md-6 px-5 px-md-0">
        <form>
          <div class="form-group">
            <label>Category Name</label>
            <input type="text" class="form-control" v-model="categoryName" required>
          </div>
          <div class="form-group">
            <label>Description</label>
            <input type="text" class="form-control" v-model="description" required>
          </div>
          <div class="form-group">
            <label>ImageURL</label>
            <input type="url" class="form-control" v-model="imageUrl" required>
          </div>
          <button type="button" class="btn btn-primary" @click="editCategory">Update
          </button>
          <button type="button" class="btn btn-danger" @click="deleteCategory">Delete
          </button>
        </form>
      </div>
      <div class="col-3"></div>
    </div>
  </div>
</template>

<script>
const axios =  require('axios')
import swal from 'sweetalert';
export default {
  data(){
    return {
      id : null,
      categoryName : null,
      description : null,
      imageUrl : null,
      categoryIndex : null
    }
  },
  props : ["baseURL", "categories"],
  methods:{
    async editCategory() {
      const updatedCategory = {
        id: this.id,
        categoryName: this.categoryName,
        description: this.description,
        imageUrl: this.imageUrl,
      };

      await axios({
        method: 'post',
        url: this.baseURL + "category/update/" + this.id,
        data: JSON.stringify(updatedCategory),
        headers: {
          'Content-Type': 'application/json'
        }
      })
      .then(() => {
        this.$router.replace("/admin/category");
        swal({
          text: "Category Updated Successfully!",
          icon: "success",
          closeOnClickOutside: false,
        });
      })
      .catch(err => console.log(err));
    },
    
    async deleteCategory() {
    try {
      const response = await axios.post(this.baseURL + "category/delete/" + this.id);
      this.$router.replace("/admin/category");
      if (response.status === 200 && response.data.success) {
        swal({
          text: response.data.message,
          icon: "success",
          closeOnClickOutside: false,
        });
      } 

      } catch (err) {
      if (err.response) {
        // Server responded with a status code outside 2xx
        const status = err.response.status;
        const message = err.response?.data?.message;

        if (status === 400) {
          swal({
            text: message || "Bad Request: Please check the category ID or dependencies.",
            icon: "info",
            closeOnClickOutside: false,
          });
        } else if (status === 404) {
          swal({
            text: "Category not found.",
            icon: "error",
            closeOnClickOutside: false,
          });
        } else {
          swal({
            text: `Unexpected error: ${status}`,
            icon: "error",
            closeOnClickOutside: false,
          });
        }
        } else if (err.request) {
        // Request was made but no response received
        swal({
          text: "No response from server. Please try again later.",
          icon: "error",
          closeOnClickOutside: false,
        });
        } else {
        // Something else caused the error
        swal({
          text: "An error occurred: " + err.message,
          icon: "error",
          closeOnClickOutside: false,
        });
      }
      console.error("Delete category error:", err);
    }
  }
},
  mounted() {
    this.id = this.$route.params.id;
    this.category = this.categories.filter(category => category.id == this.id)[0];
    this.categoryName = this.category.categoryName;
    this.description = this.category.description;
    this.imageUrl = this.category.imageUrl;
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