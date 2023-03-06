<!-- =========================================================================================
    File Name: CheckboxArrayValue.vue
    Description: Create array using checked checkboxes
    ----------------------------------------------------------------------------------------
    Item Name: Vuesax Admin - VueJS Dashboard Admin Template
      Author: Pixinvent
    Author URL: http://www.themeforest.net/user/pixinvent
========================================================================================== -->


<template>
    <vx-card :noShadow="false">
        <ul class="demo-alignment">
            <li>
                <vs-radio v-model="checkBox1" vs-value="allType">全部类型</vs-radio>
            </li>
            <li>
                <vs-radio v-model="checkBox1" vs-value="goods">二手商品</vs-radio>
            </li>
            <li>
                <vs-radio v-model="checkBox1" vs-value="job">兼职信息</vs-radio>
            </li>
            <li>
                <vs-radio v-model="checkBox1" vs-value="lost">失物招领</vs-radio>
            </li>
        </ul>
    </vx-card>
</template>

<script>
export default {
    data() {
        return {
            checkBox1: 'allType'
        }
    },
    watch: {
      checkBox1(newVal) {
        this.$emit("infoTypeEvent", newVal);
      }
    }
}
</script>