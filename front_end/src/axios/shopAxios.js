import axios from 'axios'

const shopAxios = axios.create({
    baseURL:'http://192.168.100.200:10010/shop',
    timeout: 2000
})

export default shopAxios
