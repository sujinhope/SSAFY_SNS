import properties from './properties'
import axios from 'axios'
// const axios = require('axios')

axios.defaults.headers.common['Authorization'] = `Bearer ${localStorage.getItem('JWT')}`


const hosturl = properties.backendIP
const appname = '/post'

/* eslint-disable no-unused-vars */
// list 받아오기 
const getPostlist = (params,callback,errorCallback) => {
  //백앤드와 로그인 통신하는 부분
  axios.get(hosturl+appname+'/list', params)
  .then(callback)
  .catch(errorCallback)
}
//pno에 해당하는 포스트 하나 받아오기
const getPost = (params, callback, errorCallback)=>{
axios.defaults.headers.common['Authorization'] = `Bearer ${localStorage.getItem('JWT')}`
  axios.get(hosturl+appname+`/${params.pno}`)
  .then(callback)
  .catch(errorCallback)
}

// 해시태그에 해당하는 list 받아오기

const getTabPostlist = (params,callback,errorCallback) => {
  //백앤드와 로그인 통신하는 부분
axios.defaults.headers.common['Authorization'] = `Bearer ${localStorage.getItem('JWT')}`
  axios.get(hosturl+appname+`/tab/${params.hashtag}/${params.page}`, params)
  .then(callback)
  .catch(errorCallback)
}

const getHomePost = (params, callback, errorCallback) => {
axios.defaults.headers.common['Authorization'] = `Bearer ${localStorage.getItem('JWT')}`
  axios.get(hosturl+appname+`/newsfeed/${params.page}`)
  .then(callback)
  .catch(errorCallback)
}
// 게시글 작성하기
const requestPosting = (data,callback,errorCallback) => {
axios.post(hosturl+appname, data, {headers:{'Authorization': `Bearer ${localStorage.getItem('JWT')}`}})
  .then(callback)
  .catch(errorCallback)
}

// 댓글 작성하기 
const requestComment = (data,callback,errorCallback) => {
  axios.post(hosturl+appname+`/comment`, data)
  .then(callback)
  .catch(errorCallback)
}

// 댓글 불러오기 
const getComment = (params,callback,errorCallback) => {
  axios.get(hosturl+appname+`/comment/${params.pno}`)
  .then(callback)
  .catch(errorCallback)
}

// 게시글 수정
const updatePosting = (data,callback,errorCallback) =>{
  axios.put(hosturl+appname+`/${data.pno}`,data)
  .then(callback)
  .catch(errorCallback)
}

// 게시글 삭제 
const deletePosting = (data, callback, errorCallback) => {
  axios.delete(hosturl+appname+`/${data.pno}`,data)
  .then(callback)
  .catch(errorCallback)
}

// 댓글 삭제 
const deleteComment = (data, callback, errorCallback) => {
  axios.delete(hosturl+appname+`/comment/${data.no}`, data)
  .then(callback)
  .catch(errorCallback)
}

const like = (data, callback, errorCallback) =>{
  axios.post(hosturl+`/likes/${data.pno}`)
  .then(callback)
  .catch(errorCallback)
}

// 북마크 --------------------
const getBookMark = (callback, errorCallback) =>{
  axios.get(hosturl+`/bookmark`)
  .then(callback)
  .catch(errorCallback)
}


const updateBookMark = (data, callback, errorCallback) =>{
  axios.post(hosturl+`/bookmark/`,data)
  .then(callback)
  .catch(errorCallback)
}


const deleteBookMark = (callback, errorCallback) =>{
  axios.delete(hosturl+`/bookmark`)
  .then(callback)
  .catch(errorCallback)
}


const deleteOneBookMark = (data, callback, errorCallback) =>{
  axios.delete(hosturl+`/bookmark/post/${data.pno}`)
  .then(callback)
  .catch(errorCallback)
}

// 투표 등록 (추가)
const registerVote = (data, callback, errorCallback) => {
  axios.post(hosturl+appname+'/vote', data)
  .then(callback)
  .catch(errorCallback)
}

const PostApi = {
  getPostlist:(params,callback,errorCallback)=>getPostlist(params,callback,errorCallback),
  getPost:(params,callback,errorCallback)=>getPost(params,callback,errorCallback),
  getTabPostlist:(params,callback,errorCallback)=>getTabPostlist(params,callback,errorCallback),
  getHomePost:(params,callback,errorCallback)=>getHomePost(params,callback,errorCallback),
  requestPosting:(data,callback,errorCallback)=>requestPosting(data,callback,errorCallback),
  requestComment:(data,callback,errorCallback)=>requestComment(data,callback,errorCallback),
  getComment:(params,callback,errorCallback) => getComment(params,callback,errorCallback),
  updatePosting:(data,callback,errorCallback)=>updatePosting(data,callback,errorCallback),
  deletePosting:(data,callback,errorCallback)=>deletePosting(data,callback,errorCallback),
  deleteComment:(data,callback,errorCallback)=>deleteComment(data,callback,errorCallback),
  like:(data,callback,errorCallback)=>like(data,callback,errorCallback),
  getBookMark:(data,callback,errorCallback)=>getBookMark(data,callback,errorCallback),
  updateBookMark:(data,callback,errorCallback)=>updateBookMark(data,callback,errorCallback),
  deleteBookMark:(data,callback,errorCallback)=>deleteBookMark(data,callback,errorCallback),
  deleteOneBookMark:(data,callback,errorCallback)=>deleteOneBookMark(data,callback,errorCallback),
  registerVote:(data,callback,errorCallback)=>registerVote(data,callback,errorCallback),
}

export default PostApi
