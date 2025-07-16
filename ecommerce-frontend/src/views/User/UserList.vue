<template>
    <div class="container mt-4">
        <h4 class="mb-3 user-list-title">All Users</h4>
      <div class="table-responsive">
        <table class="table table-hover align-left">
          <thead class="table-light">
            <tr class="text-start">
              <th>Name</th>
              <th>Email</th>
              <th>Role</th>
              <th>Edit</th>
            </tr>
          </thead>
          <tbody>
            <UserRow
              v-for="user in users"
              :key="user.id"
              :user="user"
            />a
          </tbody>
        </table>
      </div>
    </div>
</template>
   
<script>
import UserRow from '../../components/User/UserRow'
var axios =  require('axios');  
export default {
  name: 'uList',
  components: { UserRow },
  data() {
  return {
        baseURL : "http://localhost:8080/",
        users: null,
      }
    },
    methods: {
      async getUsers(){
        await axios.get(this.baseURL + "user/admin/users")
        .then(res => this.users = res.data)
        .catch(err => console.log(err))
      }
    },
    mounted(){
      this.getUsers();
    }
}
</script>

<style>

  .user-list-title {
    text-align: left;
    margin-bottom: 2rem;
  }

  th, tbody td {
    text-align: left;
    padding: 12px 16px;      
    color: #333333;         
    background-color: #f8f9fa; 
  }   

    
</style>
  