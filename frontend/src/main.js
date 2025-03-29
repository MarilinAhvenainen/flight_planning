import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import axios from 'axios';
import router from './router';

createApp(App).use(router).mount('#app')
axios.defaults.baseURL = 'http://localhost:8080';
