import axios from 'axios';
import { Message } from 'element-ui';
import router from '../router'
import { mymessage } from '@/utils/mymessage';
// 使用axios的方法对status=200（登录成功）和status=500（登录失败）的服务器响应信息进行处理
// 提示：data处理的是HTTP的status=200的信息，并不是是登录成功后服务器返回的status=200的信息！
// 比如：Spring Security配置文件中对登录失败
// 请求的服务器响应配置，服务器对登录失败请求返回了status=500的信息，但是Postman下方区域的右上
// 角显示接受到了HTTP的响应为Status=200响应信息。
// 【if(success.status&&success.status==200&&success.data.status==200)】
// 如果响应信息的status存在，且HTTP响应信息的status为200，且响应信息中的data属性的status值也为
// 200则进行响应成功的处理。如果success.data.status==500则是业务上的错误。

axios.interceptors.response.use(success => {
    if (success.status && success.status == 200 && success.data.status == 500) {
        // 使用ElementUI提供的Message功能，对success.data.msg信息进行处理。完成后，返回。
        Message.error({ message: success.data.msg })
        return;
    }
    // 添加对响应成功消息的显示
    if (success.data.msg) {
        Message.success({ message: success.data.msg })
    }
    return success.data;
}, error => {
    // 如果error的响应信息为响应失败【504】或者error的响应信息为找不到页面【404】
    if (error.response.status == 504 || error.response.status == 404) {
        Message.error({ message: '服务器出错，请稍后再试！' })
    } else if (error.response.status == 403) {
        Message.error({ message: '权限不足，请联系管理员！' })
    } else if (error.response.status == 401) {
        mymessage.error({ message: error.response.data.msg ? error.response.data.msg : '尚未登录，请登录' });
        // 当后端重启后，前端session过期，应该主动跳转至登录页
        router.replace("/");
    } else {
        // 抓住服务器响应信息的错误
        if (error.response.data.msg) {
            Message.error({ message: error.response.data.msg })
        } else {
            Message.error({ message: '未知错误！' })
        }
    }
    return;
})

let base = '';

export const postKeyValueRequest = (url, params) => {
    return axios({
        method: 'post',
        // 注意：此处为变量，所以非引号，而是tab键上方的键
        url: `${base}${url}`,
        data: params,
        transformRequest: [function (data) {
            let ret = '';
            for (let i in data) {
                ret += encodeURIComponent(i) + '=' + encodeURIComponent(data[i]) + '&'
            }
            return ret;
        }],
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    })
}

export const postRequest = (url, params) => {
    return axios({
        method: 'post',
        url: `${base}${url}`,
        data: params
    })
}

export const putRequest = (url, params) => {
    return axios({
        method: 'put',
        url: `${base}${url}`,
        data: params
    })
}

export const getRequest = (url, params) => {
    return axios({
        method: 'get',
        url: `${base}${url}`,
        data: params
    })
}

export const deleteRequest = (url, params) => {
    return axios({
        method: 'delete',
        url: `${base}${url}`,
        data: params
    })
}

/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/