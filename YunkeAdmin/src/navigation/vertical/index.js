/*

Array of object

Top level object can be:
1. Header
2. Group (Group can have navItems as children)
3. navItem

* Supported Options

/--- Header ---/

header

/--- nav Grp ---/

title
icon (if it's on top level)
tag
tagVariant
children

/--- nav Item ---/

icon (if it's on top level)
title
route: [route_obj/route_name] (I have to resolve name somehow from the route obj)
tag
tagVariant

*/
import systemManage from '@/navigation/vertical/system-manage'
import systemMonitor from '@/navigation/vertical/system-monitor'
import homePage from '@/navigation/vertical/home-page'
import dataManage from '@/navigation/vertical/data-manage'
// Array of sections
export default [...homePage, ...systemManage, ...systemMonitor, ...dataManage]
