import { _AsyncData } from "nuxt3/dist/app/composables/asyncData";
import { ElMessage } from "element-plus";

const baseUrl = "";
// 指定后端返回的基本数据类型
export interface ResponseConfig {
  code: number;
  status: number;
  data: any;
  msg: string;
}
export interface ValueConfig {
  value: any;
}

const fetch = async (url: string, options?: any): Promise<any> => {
  await nextTick(); //解决刷新页面useFetch无返回
  const reqUrl = baseUrl + url;
  return new Promise((resolve, reject) => {
    useFetch(reqUrl, { ...options })
      .then(({ data, error }: _AsyncData) => {
        if (error.value) {
          reject(error.value);
          return;
        }
        const value = data.value;
        if (!value) {
          console.log("执行错误了");
          // 这里处理错误回调
          // reject(value)
          // $router.replace('/reject/' + value.status)
        } else if (value.code === 102) {
          ElMessage({
            message: value.msg,
            type: "error",
          });
        } else {
          resolve(ref(value));
        }
      })
      .catch((err: any) => {
        reject(err);
      });
  });
};

export default new (class Http {
  get(url: string, params?: any): Promise<any> {
    return fetch(url, { method: "get", params });
  }

  post(url: string, body?: any): Promise<any> {
    return fetch(url, { method: "post", body });
  }

  put(url: string, body?: any): Promise<any> {
    return fetch(url, { method: "put", body });
  }

  delete(url: string, body?: any): Promise<any> {
    return fetch(url, { method: "delete", body });
  }
})();

