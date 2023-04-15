<template>
  <p class="clearfix mb-0">
    <span class="float-md-left d-block d-md-inline-block mt-25">
      COPYRIGHT  © {{ new Date().getFullYear() }}
      <b-link
        class="ml-25"
        href="https://1.envato.market/pixinvent_portfolio"
        target="_blank"
      >Titos</b-link>
      <span class="d-none d-sm-inline-block">, 保留所有权利</span>
    </span>

    <span class="float-md-right d-none d-md-block">自我提升 &amp; 分享平台
      <feather-icon
        icon="HeartIcon"
        size="21"
        class="text-danger stroke-current"
      />
    </span>
  </p>
</template>

<script>
import { BLink } from 'bootstrap-vue'

export default {
  components: {
    BLink,
  },
}
</script>
