import axios from 'axios'

const adminAxios = axios.create({
	baseURL:'http://localhost:10010/admin',
	timeout: 2000                   
})

export default adminAxios
