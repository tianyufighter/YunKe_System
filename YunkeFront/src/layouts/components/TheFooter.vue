<!--
    页面底部说明信息
-->


<template>
    <footer class="the-footer flex-wrap justify-between" :class="classes">
        <span>COPYRIGHT @ {{ new Date().getFullYear() }} <a href="http://www.tianyufighter.cn" target="_blank" rel="nofollow">Titos</a>, 保留所有权利</span>
        <span class="md:flex hidden items-center">
            <span>自我提升 &amp; 分享平台</span>
            <feather-icon icon="HeartIcon" svgClasses="stroke-current text-danger w-6 h-6" class="ml-2" />
        </span>
    </footer>
</template>

<script>
export default {
    name: "the-footer",
    props: {
        classes: {
            type: String,
        },
    }
}
</script>
