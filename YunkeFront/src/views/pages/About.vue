<!--关于界面-->
<template>
    <div id="invoice-page">
        <vx-card>
            <div class="vx-row leading-loose p-base">
                <div class="vx-col w-full md:w-1/2 mt-base">
                    <img src="../../assets/images/logo/logo.png" alt="vuesax-logo">
                </div>
                <div class="vx-col w-full md:w-1/2 text-right">
                    <h1>云客在线服务系统</h1>
                    <div class="invoice__invoice-detail mt-6">
                        <h6>开发时间: 2023年03月01日</h6>
                    </div>
                </div>
                <div class="vx-col w-full md:w-1/2 mt-12">
                    <h5>开发者</h5>
                    <div class="invoice__recipient-info my-4">
                        <p>tianyufighter</p>
                    </div>
                    <div class="invoice__recipient-contact ">
                        <p class="flex items-center">
                            <feather-icon icon="MailIcon" svgClasses="h-4 w-4"></feather-icon>
                            <span class="ml-2">634522023@qq.com</span>
                        </p>
                        <p class="flex items-center">
                            <feather-icon icon="PhoneIcon" svgClasses="h-4 w-4"></feather-icon>
                            <span class="ml-2">18482079914</span>
                        </p>
                    </div>
                </div>
                <div class="vx-col w-full md:w-1/2 mt-base text-right mt-12">
                    <h5>技术架构</h5>
                    <div class="invoice__company-info my-4">
                        <p>前端: Vue</p>
                        <p>后台: SpringCloud微服务</p>
                    </div>
                </div>
            </div>

            <!-- INVOICE FOOTER -->
            <div class="invoice__footer text-right p-base">
                <p class="mb-4">欢迎使用云客在线服务系统，有什么问题时您可以通过邮箱的方式与我们取得联系。</p>
            </div>
        </vx-card>
    </div>
</template>

<script>

export default{
    data() {
        return {
        }
    },
    computed: {

    },
    methods: {},
    components: {}
}
</script>
