import { getRequest } from "./api"

export const initMenu = (router, store) => {
    // 如果store.state.routes已经存在菜单的存储数据，则不再请求初始化菜单
    if (store.state.routes.length > 0) {
        return;
    }
    // 如果用户可能按了f5刷新菜单等操作，将发送/system/config/menu路径的请求，并接受服务端返回的
    // 菜单数据
    getRequest("/system/config/menu").then(data => {
        if (data) {
            let fmtRoutes = formatRoutes(data);
            // 为initMenu的router参数添加格式化后的数据
            router.addRoutes(fmtRoutes);
            // 调用store文件夹中的index.js中的initRoutes,注入数据
            store.commit('initRoutes', fmtRoutes);
        }
    })
}

export const formatRoutes = (routes) => {
    let fmRoutes = [];
    // 遍历routes，对于其中的每一项创建一个router进行存储
    routes.forEach(router => {
        // 批量的变量定义，将以下每一项赋值给router，以格式化数据
        let {
            path,
            component,
            name,
            meta,
            iconCls,
            children
        } = router;
        // 判断是否有一级菜单的children并且该children还是一个非空的实例
        if (children && children instanceof Array) {
            // 使用递归调用children
            children = formatRoutes(children);
        }
        // 再次格式化一个router数据，将component转化为组件地址
        let fmRouter = {
            path: path,
            name: name,
            meta: meta,
            iconCls: iconCls,
            children: children,
            component(resolve) {
                // require为请求地址，下行代码为导入组件
                // require(['../views/' + component + '.vue'], resolve);
                // 由于组件在views文件下的不同子文件夹中，故通过不同的组件名开头来决定响应的请求路径。
                // 由于数据库menu表中还要/home路径，所以还得添加home判断
                if (component.startsWith("Home")) {
                    require(['../views/' + component + '.vue'], resolve);
                } else if (component.startsWith("Per")) {
                    require(['../views/per/' + component + '.vue'], resolve);
                } else if (component.startsWith("Sal")) {
                    require(['../views/sal/' + component + '.vue'], resolve);
                } else if (component.startsWith("Sta")) {
                    require(['../views/sta/' + component + '.vue'], resolve);
                } else if (component.startsWith("Sys")) {
                    require(['../views/sys/' + component + '.vue'], resolve);
                }
                // else if (component.startsWith("Emp")) {
                //     require(['../views/emp/' + component + '.vue'], resolve);
                // } 
            },
        }
        // 把最终格式化好的数据注入fmRoutes中
        fmRoutes.push(fmRouter)
    })
    return fmRoutes;
}
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/