import axios from 'axios'

const adminAxios = axios.create({
	baseURL:'http://192.168.100.200:10010/admin',
	timeout: 2000                   
})

export default adminAxios
