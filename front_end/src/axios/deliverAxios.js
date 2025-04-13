import axios from 'axios'

const deliverAxios = axios.create({
    baseURL:'http://localhost:10010/deliver',
    timeout: 2000
})

export default deliverAxios
