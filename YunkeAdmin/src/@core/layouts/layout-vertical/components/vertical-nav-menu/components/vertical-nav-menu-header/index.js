import { useUtils as useI18nUtils } from '@core/libs/i18n'

const { t } = useI18nUtils()

export default {
  props: {
    item: {
      type: Object,
      required: true,
    },
  },
  render(h) {
    const span = h('span', {}, t(this.item.header))
    const icon = h('feather-icon', { props: { icon: 'MoreHorizontalIcon', size: '18' } })
    if (true) {
      return h('li', { class: 'navigation-header text-truncate' }, [span, icon])
    }
  },
}
