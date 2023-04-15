<!--  -->


<template>
    <vx-card :noShadow="false">
        <ul class="centerx">
            <li>
                <vs-radio v-model="radios1" vs-value="noban">无限制</vs-radio>
            </li>
            <li>
                <vs-radio v-model="radios1" vs-value="week">近一周</vs-radio>
            </li>
            <li>
                <vs-radio v-model="radios1" vs-value="month">近一月</vs-radio>
            </li>
        </ul>
    </vx-card>
</template>

<script>
export default {
    data() {
        return {
            radios1: 'noban',
        }
    },
    watch: {
      radios1(newVal) {
        this.$emit("infoTimeEvent", newVal);
      }
    }
}
</script>
