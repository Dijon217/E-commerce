<template>
  <div class="container">
    <!--    sign in form-->
    <div class="row">
      <div class="col-12 justify-content-center d-flex flex-row pt-5">
        <div id="signin-div" class="flex-item border">
          <h2 class="pt-4 pl-4">Sign-In</h2>
          <form @submit="signin" class="pt-4 pl-4 pr-4">
            <div class="form-group">
              <label>Email</label>
              <input type="email" class="form-control" v-model="email" required>
            </div>
            <div class="form-group">
              <label>Password</label>
              <input type="password" class="form-control" v-model="password" required>
            </div>
            <small class="form-text text-muted">By continuing, you agree to Simplecoding's Conditions of Use and Privacy Notice.</small>
            <button type="submit" class="btn btn-primary mt-2 py-0">
              Continue
              <!--  loading bar will appear when we are making the API call and saving the token -->
              <div v-if="loading" class="spinner-border spinner-border-sm" role="status">
                <span class="sr-only">Loading...</span>
              </div>
            </button>
          </form>
          <hr>
          <small class="form-text text-muted pt-2 pl-4 text-center">New to Simplecoding?</small>
          <p class="text-center">
            <router-link :to="{name: 'Signup'}" class="btn btn-dark text-center mx-auto px-5 py-1 mb-2">Create Your Ecom-CO Account</router-link>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
const axios = require('axios')
import swal from 'sweetalert';
export default {
  name: 'UserSignin',
  props : [ "baseURL"],
  data() {
    return {
      email: null,
      password: null,
      loading: null
    }
  },
  methods : {
    async signin(e) {
      e.preventDefault();
      // set loading to true
      this.loading = true;

      // request body
      const user = {
        email: this.email,
        password: this.password

      }

      //API call
      await axios({
        method: 'post',
        url: this.baseURL + "user/signIn",
        data : JSON.stringify(user),
        headers: {
          'Content-Type': 'application/json'
        }
      })
      .then(res => {
        // login successful, we will get token in res.data object
        localStorage.setItem('token', res.data.token);
        localStorage.setItem('role', res.data.role);
        this.$router.replace({ path: '/' }).then(() => {
        location.reload();
        });
        swal({
          text: "Login successful. Please continue",
          icon: "success"
        });
      })
      .catch(err => {
        // error handling and showing sweet alert
        swal({
          text: "Unable to Log you in!",
          icon: "error",
          closeOnClickOutside: false,
        });
        console.log(err);
      })
      .finally(() => {
        // set loading false
        this.loading = false;
      })
    }
  },
  mounted() {
    this.loading = false;
  }
}
</script>

<style scoped>
.btn-dark {
  background-color: #e7e9ec;
  color: #000;
  font-size: smaller;
  border-radius: 0;
  border-color: #adb1b8 #a2a6ac #a2a6ac;
}

.btn-primary {
  background-color: #f0c14b;
  color: black;
  border-color: #a88734 #9c7e31 #846a29;
  border-radius: 0;
}

#logo {
  width: 150px;
}

@media only screen and (min-width: 992px) {
  #signin-div {
    width: 40%;
  }
}


</style>