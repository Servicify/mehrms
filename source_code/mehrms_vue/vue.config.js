// let proxyObj = {};

// proxyObj['/'] = {
//   ws: false,
//   target: 'http://127.0.0.1:8081',
//   changeOrigin: true,
//   pathRewrite: {
//     '^/': ''
//   }
// }
// module.exports = {
//   devServer: {
//     host: 'localhost',
//     port: 8080,
//     proxy: proxyObj
//   },
// }
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
let proxyObj = {};
const CompressionPlugin = require("compression-webpack-plugin");
// 拦截所有 HTTP 请求
proxyObj['/'] = {
  ws: false,
  target: 'http://localhost:8081',
  changeOrigin: true,
  pathRewrite: {
    '^/': ''
  }
}
module.exports = {
  devServer: {
    host: 'localhost',
    port: 8080,
    proxy: proxyObj
  },
  publicPath: process.env.NODE_ENV === "production" ? "./" : "/",
  // publicPath: './',
  // publicPath: '/mehrms/',
  lintOnSave: false,
  outputDir: "dist",
  //禁止使用assetsDir: "static"，此项会造成将打包的文件放入Springboot项目的静态资源后
  //，由于多出的/ static / 路径造成路劲引用错误
  // webpack打包后的压缩配置
  configureWebpack: config => {
    if (process.env.NODE_ENV === 'production') {
      return {
        plugins: [
          new CompressionPlugin({
            test: /\.js$|\.html$|\.css/,
            threshold: 1024,
            deleteOriginalAssets: false
          })
        ]
      }
    }
  }
}