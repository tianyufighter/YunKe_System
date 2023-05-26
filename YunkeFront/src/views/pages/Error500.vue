<template>
    <div class="h-screen flex w-full bg-img">
        <div class="vx-col flex items-center justify-center flex-col sm:w-1/2 md:w-3/5 lg:w-3/4 xl:w-1/2 mx-auto text-center">
            <img src="../../assets/images/pages/500.png" alt="graphic-500" class="mx-auto mb-4">
            <h1 class="sm:mx-0 mx-4 mb-4 sm:mb-12 text-5xl d-theme-heading-color">服务器内部错误!</h1>
            <vs-button size="large" @click="$router.push('/pages/login')">返回上一页</vs-button>
        </div>
    </div>
</template>
