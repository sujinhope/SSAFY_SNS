const axios = require('axios').default

const hosturl = 'http://70.12.246.122:8080'
// const hosturl = 'http://i02a305.p.ssafy.io:8080'
const appname = '/userSNS'

const github = (params, callback, errorCallback) => {
  console.log('소셜 로그인 하는중')
  axios.get(hosturl+appname+`/githubLogin/${params.code}`)
  .then(callback)
  .catch(errorCallback)
}

const google = (params, callback, errorCallback) => {
  axios.get(hosturl+appname+`/googleLogin/${params.code}`)
  .then(callback)
  .catch(errorCallback)
}

const kakao = (params, callback, errorCallback) => {
  axios.get(hosturl+appname+`/kakaoLogin/${params.code}`)
  .then(callback)
  .catch(errorCallback)
}

const naver = (params, callback, errorCallback) => {
  axios.get(hosturl+appname+`/naverLogin/${params.code}`)
  .then(callback)
  .catch(errorCallback)
}

const SNSApi = {
  github:(params,callback,errorCallback)=>github(params,callback,errorCallback),
  google:(params, callback, errorCallback) =>google(params, callback, errorCallback),
  kakao:(params, callback, errorCallback) =>kakao(params, callback, errorCallback),
  naver:(params, callback, errorCallback) =>naver(params, callback, errorCallback)
}

export default SNSApi