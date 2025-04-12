import axios from 'axios'

const deliverAxios = axios.create({
    baseURL:'http://192.168.100.200:10010/deliver',
    timeout: 2000
})

export default deliverAxios
