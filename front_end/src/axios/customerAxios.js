import axios from 'axios'

const customerAxios = axios.create({
	baseURL:'http://localhost:10010/customer',
	timeout: 2000                   
})

export default customerAxios
