<!--
  日历界面
-->
<template>
  <div id="calendar-app">

    <div class="vx-card app-fixed-height">
        <VuePerfectScrollbar class="scroll-area" :settings="settings">
          <full-calendar v-if="fresh" class="w-full select-none" :events="calendarEvents" locale="zh-cn"  @dayClick ="openAddNewEvent" @eventClick="openEditEvent">
              <!-- 左上角标签 -->
              <div slot="fc-header-left" class="flex flex-wrap sm:justify-start justify-center">
                  <div v-for="(label, index) in calendarLabels" :key="index" class="flex items-center mr-4 mb-2">
                      <div class="h-3 w-3 inline-block rounded-full mr-2" :class="'bg-' + label.color"></div>
                      <span>{{ label.text }}</span>
                  </div>
              </div>
              <!-- 右上角添加按钮 -->
              <div slot="fc-header-right" class="flex justify-end">
                  <vs-button icon-pack="feather" icon="icon-plus" @click="promptAddNewEvent(new Date())">添加</vs-button>
              </div>
          </full-calendar>
        </VuePerfectScrollbar>
        <!-- 添加事件 -->
        <vs-prompt
            class="calendar-event-dialog"
            vs-title="添加事件"
            vs-accept-text= "添加事件"
            vsCancelText="取消"
            @vs-accept="addEvent"
            :vs-is-valid="validForm"
            :vs-active.sync="activePromptAddEvent">

            <div class="calendar__label-container flex">

                <vs-chip v-if="addLabelLocal != ''" class="text-white" :class="'bg-' + labelColor(addLabelLocal)">{{ addLabelLocal }}</vs-chip>

                <vs-dropdown vs-custom-content vs-trigger-click class="ml-auto my-2 cursor-pointer">

                    <feather-icon icon="TagIcon" svgClasses="h-5 w-5" class="cursor-pointer" @click.prevent></feather-icon>

                    <vs-dropdown-menu style="z-index: 200001">
                            <vs-dropdown-item v-for="(label, index) in calendarLabels" :key="index" @click="addLabelLocal = label.value">
                                <div class="h-3 w-3 inline-block rounded-full mr-2" :class="'bg-' + label.color"></div>
                                <span>{{ label.text }}</span>
                            </vs-dropdown-item>
                    </vs-dropdown-menu>
                </vs-dropdown>

            </div>

            <vs-input name="event-name" v-validate="'required'" class="w-full" label-placeholder="事件标题" v-model="addTitleLocal"></vs-input>
                <div class="my-4">
                    <small class="date-label">开始日期</small>
                    <datepicker format="yyyy MM dd" name="start-date" v-model="addStartLocal" :disabled="disabledFrom"></datepicker>
                </div>
                <div class="my-4">
                    <small class="date-label">结束日期</small>
                    <datepicker format="yyyy MM dd" :disabledDates="disabledDatesToAdd" name="end-date" v-model="addEndLocal"></datepicker>
                </div>
            <vs-textarea rows="5" label="添加描述" v-model="addDescLocal" />

        </vs-prompt>

        <!-- 编辑事件 -->
        <vs-prompt
            class="calendar-event-dialog"
            vs-title="Edit Event"
            vs-accept-text= "Submit"
            vs-cancel-text = "Remove"
            vs-button-cancel = "border"
            @vs-cancel="removeEvent"
            @vs-accept="editEvent"
            :vs-is-valid="validForm2"
            :vs-active.sync="activePromptEditEvent">

            <div class="calendar__label-container flex">

                <vs-chip v-if="editLabelLocal != ''" class="text-white" :class="'bg-' + labelColor(editLabelLocal)">{{ editLabelLocal }}</vs-chip>

                <vs-dropdown vs-custom-content class="ml-auto my-2 cursor-pointer">

                    <feather-icon icon="TagIcon" svgClasses="h-5 w-5" @click.prevent></feather-icon>

                    <vs-dropdown-menu style="z-index: 200001">
                            <vs-dropdown-item v-for="(label, index) in calendarLabels" :key="index" @click="editLabelLocal = label.value">
                                <div class="h-3 w-3 inline-block rounded-full mr-2" :class="'bg-' + label.color"></div>
                                <span>{{ label.text }}</span>
                            </vs-dropdown-item>
                    </vs-dropdown-menu>
                </vs-dropdown>

            </div>

            <vs-input name="event-name" v-validate="'required'" class="w-full" label-placeholder="事件标题" v-model="editTitleLocal"></vs-input>
                <div class="my-4">
                    <small class="date-label">开始日期</small>
                    <datepicker :disabledDates="disabledDatesFromEdit" format="yyyy-MM-dd" name="start-date" v-model="editStartLocal"></datepicker>
                </div>
                <div class="my-4">
                    <small class="date-label">结束日期</small>
                    <datepicker :disabledDates="disabledDatesToEdit" format="yyyy-MM-dd" name="end-date" v-model="editEndLocal"></datepicker>
                </div>
            <vs-textarea rows="5" label="描述" v-model="editDescLocal" />

        </vs-prompt>
    </div>



  </div>
</template>

<script>
import Datepicker from 'vuejs-datepicker';
import VuePerfectScrollbar from 'vue-perfect-scrollbar';
import {doDeleteCalendarEvent, doUpdateCalendarEvent, getAllCalendarEvent} from "../../../network";

export default {
  data() {
      return {
        editEventId: null,
        editLabelLocal: '',
        editTitleLocal: '',
        editStartLocal: '',
        editEndLocal: '',
        editDescLocal: '',

        addEventId: null,
        addLabelLocal: '',
        addTitleLocal: '',
        addStartLocal: '',
        addEndLocal: '',
        addDescLocal: '',

        disabledFrom: false,
        settings: {
            maxScrollbarLength: 60,
            wheelSpeed: 0.30,
        },
        activePromptAddEvent: false,
        activePromptEditEvent: false,
        calendarLabels:  [
          { text: '学习' ,value : '学习', color: 'success' },
          { text: '生活', value: '生活', color: 'warning'},
          { text: '工作', value: '工作', color: 'danger'},
          { text: '其它', value: '其它', color: 'primary'},
        ],
        calendarEvents: [],
        fresh: true
      }
  },
  computed: {
      // calendarEvents() {
      //     return this.$store.state.calendar.calendarEvents
      // },
      validForm() {
          return this.addTitleLocal != '' && this.addStartLocal != '' && this.addEndLocal != '' && (this.addEndLocal - this.addStartLocal) >= 0;
      },
      validForm2() {
        return this.editTitleLocal != '' && this.editStartLocal != '' && this.editEndLocal != '' && (this.editEndLocal - this.editStartLocal) >= 0;
      },
      disabledDatesToEdit() {
          return { to: this.editStartLocal }
      },
      disabledDatesFromEdit() {
          return { from: this.editEndLocal}
      },
      disabledDatesToAdd() {
        return { to: this.editStartLocal }
      },
      disabledDatesFromAdd() {
        return { from: this.editEndLocal}
      },
      labelColor() {
          return (label) => {
              if (label === "学习") return "success"
              else if (label === "生活") return "warning"
              else if (label === "工作") return "danger"
              else if (label === "其它") return "primary"
          }
      },
  },
  methods: {
      // 点击添加事件按钮执行的操作
      addEvent() {
          // const obj = { title: this.title, start: this.start, end: this.end, label: this.labelLocal, desc: this.desc }
          // obj.cssClass = "event-" + this.labelColor(this.labelLocal)
          // this.$store.dispatch('calendar/addEventToCalendar', obj);
        let obj = {
          calendarTitle: this.addTitleLocal,
          calendarDesc: this.addDescLocal,
          tagName: this.addLabelLocal,
          startTime: this.dateFormat(this.addStartLocal),
          endTime: this.dateFormat(this.addEndLocal)
        }
        doUpdateCalendarEvent(obj).then(res => {
          if (res.data.code == 200) {
            getAllCalendarEvent().then(res => {
              if (res.data.code == 200) {
                this.calendarEvents = [];
                for (let i = 0; i < res.data.data.length; i++) {
                  let obj = {};
                  obj.id = res.data.data[i].id;
                  obj.title = res.data.data[i].calendarTitle;
                  obj.start = new Date(res.data.data[i].startTime.replace(/-/g,"/"));
                  obj.end = new Date(res.data.data[i].endTime.replace(/-/g,"/"));
                  obj.desc = res.data.data[i].calendarDesc;
                  obj.label = res.data.data[i].tagName;
                  obj.cssClass = 'event-' + this.labelColor(obj.label);
                  this.calendarEvents.push(obj);
                }
                this.$vs.notify({
                  title:'提示',
                  text: '事件添加成功',
                  color:'primary',
                  position:'top-center'})
              }
            }).catch(err => {
              console.error(err)
            }).then(() => {

            })
          }
        }).catch(err => {
          console.error(err)
        })
      },
      clearFields() {
          this.addEventId = null;
          this.addLabelLocal = "其它";
          this.addTitleLocal = "";
          this.addStartLocal = "";
          this.addEndLocal = "";
          this.addDescLocal = "";
          this.title = this.end = this.desc = "";
          this.id = 0;
          this.labelLocal = "其它";
      },
      addNewEventDialog(date) {
          this.clearFields();
          this.addStartLocal = date;
          this.addEndLocal = date;
          this.activePromptAddEvent = true;
      },
      openAddNewEvent(date) {
          this.disabledFrom = true;
          this.addNewEventDialog(date);
      },
      promptAddNewEvent(date) {
          this.disabledFrom = false;
          this.addNewEventDialog(date);
      },
      //时间格式化函数，此处仅针对yyyy-MM-dd hh:mm:ss 的格式进行格式化
      dateFormat(time) {
        let date=new Date(time);
        let year=date.getFullYear();
        /* 在日期格式中，月份是从0开始的，因此要加0
         * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
         * */
        let month= date.getMonth()+1<10 ? "0"+(date.getMonth()+1) : date.getMonth()+1;
        let day=date.getDate()<10 ? "0"+date.getDate() : date.getDate();
        let hours=date.getHours()<10 ? "0"+date.getHours() : date.getHours();
        let minutes=date.getMinutes()<10 ? "0"+date.getMinutes() : date.getMinutes();
        let seconds=date.getSeconds()<10 ? "0"+date.getSeconds() : date.getSeconds();
        // 拼接
        return year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
      },
      openEditEvent(event) {
        this.editEventId = event.id;
        this.editLabelLocal= event.label;
        this.editTitleLocal= event.title;
        this.editStartLocal= event.start
        this.editEndLocal= event.end;
        this.editDescLocal= event.desc;
        this.activePromptEditEvent = true;
      },
      editEvent() {
          // const obj = { id: this.id, title: this.title, start: this.start, end: this.end, label: this.labelLocal, desc: this.desc }
          // obj.cssClass = "event-" + this.labelColor(this.labelLocal)
          // this.$store.dispatch('calendar/ediCalendarEvent', obj)
        let obj = {
          id: this.editEventId,
          calendarTitle: this.editTitleLocal,
          calendarDesc: this.editDescLocal,
          tagName: this.editLabelLocal,
          startTime: this.dateFormat(this.editStartLocal),
          endTime: this.dateFormat(this.editEndLocal)
        }
        doUpdateCalendarEvent(obj).then(res => {
          if (res.data.code == 200) {
            for (let i = 0; i < this.calendarEvents.length; i++) {
              if (this.calendarEvents[i].id == this.editEventId) {
                this.calendarEvents[i].title = this.editTitleLocal;
                this.calendarEvents[i].start = this.editStartLocal;
                this.calendarEvents[i].end = this.editEndLocal;
                this.calendarEvents[i].desc = this.editDescLocal;
                this.calendarEvents[i].label = this.editLabelLocal;
                this.calendarEvents[i].cssClass = 'event-' + this.labelColor(this.editLabelLocal);
                break;
              }
            }
            this.$vs.notify({
              title:'提示',
              text: '事件修改成功',
              color:'primary',
              position:'top-center'})
          }
        }).catch(err => {
          console.log("err = ", err)
        })
      },
      removeEvent() {
          // this.$store.dispatch('calendar/removeCalendarEvent', this.id)
        doDeleteCalendarEvent({
          idList: [this.editEventId]
        }).then(res => {
          if (res.data.code == 200) {
            this.calendarEvents = this.calendarEvents.filter((faq) => {
              if (faq.id == this.editEventId) {
                return false;
              } else {
                return true;
              }
            })
            this.fresh = false;
            this.$nextTick(() => {
              this.fresh = true;
            })
            this.$vs.notify({
              title:'提示',
              text: '事件删除成功',
              color:'primary',
              position:'top-center'})
          }
        }).catch(err => {
          console.log(err)
        })
      },
  },
  components: {
      'full-calendar': require('vue-fullcalendar'),
      Datepicker,
      VuePerfectScrollbar
  },
  mounted() {
    getAllCalendarEvent().then(res => {
      if (res.data.code == 200) {
        this.calendarEvents = [];
        for (let i = 0; i < res.data.data.length; i++) {
          let obj = {};
          obj.id = res.data.data[i].id;
          obj.title = res.data.data[i].calendarTitle;
          obj.start = new Date(res.data.data[i].startTime.replace(/-/g,"/"));
          obj.end = new Date(res.data.data[i].endTime.replace(/-/g,"/"));
          obj.desc = res.data.data[i].calendarDesc;
          obj.label = res.data.data[i].tagName;
          obj.cssClass = 'event-' + this.labelColor(obj.label);
          this.calendarEvents.push(obj);
        }
      }
    }).catch(err => {
      console.error(err)
    })
  }
}
</script>

<style lang="scss">
@import "@/assets/scss/vuesax/apps/calendar.scss";
</style>
