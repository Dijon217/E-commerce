<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <!-- Logo -->
    <a href="/" class="navbar-brand">
      <p id="logo">Ecom-CO</p>
    </a>  

    <!-- Burger Button -->
    <button
      class="navbar-toggler"
      type="button"
      data-toggle="collapse"
      data-target="#navbarSupportedContent"
      aria-controls="navbarSupportedContent"
      aria-expanded="false"
      aria-label="Toggle navigation"
    >
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <!-- Search Bar -->
      <form class="form-inline ml-auto mr-auto">
        <div class="input-group">
          <input
            size="100"
            type="text"
            class="form-control"
            placeholder="Search Items"
            aria-label="Search"
          />
          <div class="input-group-prepend">
            <span class="input-group-text" id="search-button-navbar">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="16"
                height="16"
                fill="currentColor"
                class="bi bi-search"
                viewBox="0 0 16 16"
              >
                <path
                  d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"
                />
              </svg>
            </span>
          </div>
        </div>
      </form>

      <!-- Dropdowns -->
      <ul class="navbar-nav ml-auto">
        <!-- Admin Dropdown -->
        <li class="nav-item dropdown" v-if="token && userRole === 'ADMIN'">
          <a
            class="nav-link text-light dropdown-toggle"
            href="#"
            id="navbarDropdownAdmin"
            role="button"
            data-toggle="dropdown"
            aria-haspopup="true"
            aria-expanded="false"
          >
            Admin
          </a>
          <div class="dropdown-menu" aria-labelledby="navbarDropdownAdmin">
            <router-link 
              class="dropdown-item" 
              :to="{ name: 'AdminCategory' }">
              Category</router-link>
            <router-link class="dropdown-item"  :to="{ name: 'AdminProduct' }">Products</router-link>
            <router-link class="dropdown-item"  :to="{ name: 'EditUser' }">Users</router-link>
          </div>
        </li>

        <!-- Account Dropdown -->
        <li class="nav-item dropdown">
          <a
            class="nav-link text-light dropdown-toggle"
            href="#"
            id="navbarDropdown"
            role="button"
            data-toggle="dropdown"
            aria-haspopup="true"
            aria-expanded="false"
          >
            Accounts
          </a>
          <div class="dropdown-menu" aria-labelledby="navbarDropdown">
            <router-link class="dropdown-item" v-if="token" :to="{ name: 'OrderHistory' }">Orders</router-link>
            <router-link class="dropdown-item" v-if="token" :to="{ name: 'WishList' }">WishList</router-link>
            <router-link class="dropdown-item" v-if="!token" :to="{ name: 'Signin' }">Log In</router-link>
            <router-link class="dropdown-item" v-if="!token" :to="{ name: 'Signup' }">Sign Up</router-link>
            <a class="dropdown-item" v-if="token" href="#" @click="signout">Sign Out</a>
          </div>
        </li>

        <!-- Cart -->
        <li class="nav-item">
          <router-link class="text-light" :to="{ name: 'Cart' }">
            <i class="fa fa-shopping-cart" style="font-size:28px"></i>
          </router-link>
        </li>
      </ul>
    </div>
  </nav>
</template>

<script>
import swal from "sweetalert";

export default {
  name: "AppNavbar",

  data() {
    return {
      token: null,
      userRole: null,
    };
  },

  methods: {
    signout() {
      localStorage.removeItem("token");
      localStorage.removeItem("role");
      swal({
        text: "Logged you out. Visit Again",
        icon: "success",
        closeOnClickOutside: false,
      })
      .then(() =>{
        window.location.href = "";
      }) 
    },
  },
  mounted() {
      this.token = localStorage.getItem("token");
      const role = localStorage.getItem("role");
      this.userRole = role ? role.toUpperCase() : null;
    },
};
</script>

<style scoped>
#logo {
  width: 150px;
  margin-left: 20px;
  margin-right: 20px;
  color: #d67fee;
}

.nav-link {
  color: rgba(255, 255, 255);
}

#search-button-navbar {
  background-color: #febd69;
  border-color: #febd69;
  border-top-right-radius: 2px;
  border-bottom-right-radius: 2px;
}
</style>
