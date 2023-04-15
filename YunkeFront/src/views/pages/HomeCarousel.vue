<!-- 首页轮播图 -->
<template>
  <el-carousel :interval="4000" arrow="always" height="25rem">
    <el-carousel-item id="el-carousel-item1"></el-carousel-item>
    <el-carousel-item id="el-carousel-item2"></el-carousel-item>
    <el-carousel-item id="el-carousel-item3"></el-carousel-item>
    <el-carousel-item id="el-carousel-item4"></el-carousel-item>
    <el-carousel-item id="el-carousel-item5"></el-carousel-item>
  </el-carousel>
</template>

<script>

export default {

}
</script>

<style>
  /*.el-carousel__item {*/
  /*  !*-moz-background-size:100% 100%;*!*/
  /*  !*background-size:100% 100%;*!*/
  /*}*/
  #el-carousel-item1 {
    background-image: url('../../assets/images/slider/1.jpeg');
    -moz-background-size:100% 100%;
    background-size:100% 100%;
  }

  #el-carousel-item2 {
    background-image: url('../../assets/images/slider/2.jpeg');
    -moz-background-size:100% 100%;
    background-size:100% 100%;
  }

  #el-carousel-item3 {
    background-image: url('../../assets/images/slider/3.jpeg');
    -moz-background-size:100% 100%;
    background-size:100% 100%;
  }

  #el-carousel-item4 {
    background-image: url('../../assets/images/slider/4.jpeg');
    -moz-background-size:100% 100%;
    background-size:100% 100%;
  }

  #el-carousel-item5 {
    background-image: url('../../assets/images/slider/5.jpeg');
    -moz-background-size:100% 100%;
    background-size:100% 100%;
  }


  #el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  #el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
</style>
