import { createRouter, createWebHistory } from 'vue-router';
import FlightView from '../views/FlightView.vue'; 
import MainPage from '../views/MainPage.vue'; 

const routes = [
  // routes
  {
    path: '/',  
    name: 'MainPage',   
    component: MainPage,
    props: true,
  },
  {
    path: '/flights/:id',  
    name: 'FlightView',   
    component: FlightView,
    props: true,
  },
];

const router = createRouter({
  history: createWebHistory(), 
  routes,
});

export default router;