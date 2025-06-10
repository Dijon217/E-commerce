<template>
    <tr>
      <td>
        <div class="d-flex align-items-center">
          <div class="rounded-circle bg-secondary me-3" style="width: 40px; height: 40px;"></div>
          <div>
            <div class="fw-bold">{{ user.fullName }}</div>
            <!-- <div class="text-muted small">{{ user.company }}</div> -->
          </div>
        </div>
      </td>
      <td>{{ user.email }}</td>
      <td>{{ user.role }}</td>
      <td>
        <button class="btn btn-sm btn-outline-primary">Edit</button>
      </td>
    </tr>
</template>
  
<script>
  export default {
    props: ['user']
  }
</script>

<style>
    .rounded-circle{
      margin-right: 16px;
    }
</style>
  