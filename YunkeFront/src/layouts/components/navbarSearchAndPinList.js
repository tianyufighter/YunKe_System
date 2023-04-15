/**
 * 页面顶部快捷方式的数据
 */
export default {
	actionIcon: 'StarIcon',
	highlightColor: 'warning',
	data: [
    	// Home
		{index: 0, label: '个人数据', url: '/home/personalData', labelIcon: 'ActivityIcon', highlightAction: false},
		{index: 1, label: '社区', url: '/home/community', labelIcon: 'ChromeIcon', highlightAction: false},
		{index: 2, label: '我的帖子', url: '/home/myPost', labelIcon: 'ClipboardIcon', highlightAction: false},
    	// 应用
		{index: 3, label: '聊天', url: '/apps/chat', labelIcon: 'MessageSquareIcon', highlightAction: true},
		{index: 4, label: '任务', url: '/apps/todo', labelIcon: 'CheckSquareIcon', highlightAction: true},
		{index: 5, label: '日历', url: '/apps/calendar/vue-fullcalendar', labelIcon: 'CalendarIcon', highlightAction: true},
    	{index: 6, label: '博客平台', url: '/apps/knowEverything/BlogPlatform', labelIcon: 'UploadCloudIcon', highlightAction: true},
    	{index: 7, label: '博客管理', url: '/apps/knowEverything/BlogManage', labelIcon: 'ShieldIcon', highlightAction: true},
    	{index: 8, label: '新闻', url: '/apps/news', labelIcon: 'FileTextIcon', highlightAction: false},
		{index: 9, label: '博客编辑器', url: '/apps/blog-editor', labelIcon: 'EditIcon', highlightAction: false},
		{index: 10, label: '个人中心', url: '/pages/profile', labelIcon: 'UserIcon', highlightAction: false},
		{index: 10, label: '关于', url: '/pages/About', labelIcon: 'ApertureIcon', highlightAction: false},
	]
}
