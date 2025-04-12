import axios from 'axios'

const customerAxios = axios.create({
	baseURL:'http://192.168.100.200:10010/customer',
	timeout: 2000                   
})

export default customerAxios
