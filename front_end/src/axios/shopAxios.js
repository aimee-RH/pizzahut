import axios from 'axios'

const shopAxios = axios.create({
    baseURL:'http://localhost:10010/shop',
    timeout: 2000
})

export default shopAxios
