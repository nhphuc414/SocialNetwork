import axios from "axios";
import cookie from "react-cookies";

const SERVER_CONTEXT = "/AdminWeb";
const SERVER = "http://localhost:8080";

export const endpoints = {
    "login": `${SERVER_CONTEXT}/api/login/`,
    "current-user": `${SERVER_CONTEXT}/api/current-user/`,
    "posts": `${SERVER_CONTEXT}/api/posts/`,
    "addpost": `${SERVER_CONTEXT}/api/post/`,
    "userPost": (userId) => `${SERVER_CONTEXT}/api/posts/${userId}`,
    "register": `${SERVER_CONTEXT}/api/users/`,
}
export const authApi = () => {
    return axios.create({
        baseURL: SERVER,
        headers: {
            "Authorization": cookie.load("token"),
        }
    })
}
export default axios.create({
    baseURL: SERVER
})