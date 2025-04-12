import { createRouter, createWebHistory } from 'vue-router'
import { customerStore } from '@/store/customerStore.js'
import { adminStore } from './store/adminStore';
import { shopStore } from "@/store/shopStore";
import { deliverStore } from "@/store/deliverStore";


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes : [
    {
      path: '/',
      redirect: '/index'
    },
    {
      path: '/index',
      component: () => import("@/views/IndexMenu.vue"),
      children: [
        {
            path: '/index',
            component: () => import("@/component/index/CarouselInterface.vue")
        },

        {
          path: '/customer/login',
          component: () => import("@/component/index/customer/CustomerLogin.vue")
        },
        {
          path: '/customer/register',
          component: () => import("@/component/index/customer/CustomerRegister.vue")
        },
        {
          path: '/customer/alterPassword',
          component: () => import("@/component/index/customer/CustomerAlterPassword.vue")
        },

        {
          path: '/admin/login',
          component: () => import("@/component/index/admin/AdminLogin.vue")
        },
        {
          path: '/admin/register',
          component: () => import("@/component/index/admin/AdminRegister.vue")
        },
        {
          path: '/admin/alterPassword',
          component: () => import("@/component/index/admin/AdminAlterPassword.vue")
        },

        {
          path: '/shop/login',
          component: () => import("@/component/index/shop/ShopLogin.vue")
        },
        {
          path: '/shop/register',
          component: () => import("@/component/index/shop/ShopRegister.vue")
        },
        {
          path: '/shop/alterPassword',
          component: () => import("@/component/index/shop/ShopAlterPassword.vue")
        },

        {
          path: '/deliver/login',
          component: () => import("@/component/index/deliver/DeliverLogin.vue")
        },
        {
          path: '/deliver/register',
          component: () => import("@/component/index/deliver/DeliverRegister.vue")
        },
        {
          path: '/deliver/alterPassword',
          component: () => import("@/component/index/deliver/DeliverAlterPassword.vue")
        },
      ]
    },
    {
      path: '/customer/menu',
      component: () => import("@/views/CustomerMenu.vue"),
      children:[
        {
          path: '/customer/menu',
          component: () => import("@/component/customer/WelcomeInterface.vue"),
        },
        {
          path: '/customer/menu/card',
          component: () => import("@/component/customer/CardInterface.vue")
        },
        {
          path: '/customer/menu/dish',
          component: () => import("@/component/customer/DishInterface.vue")
        },
        {
          path: '/customer/menu/order',
          component: () => import("@/component/customer/OrderInterface.vue")
        },
        {
          path: '/customer/menu/vip',
          component: () => import("@/component/customer/VIPInterface.vue")
        },
        {
          path: '/customer/menu/info',
          component: () => import("@/component/customer/InfoInterface.vue")
        }
      ]
    },
    {
      path: '/admin/menu',
      component: () => import("@/views/AdminMenu.vue"),
      children:[
        {
          path: '/admin/menu',
          component: () => import("@/component/admin/WelcomeInterface.vue"),
        },
        {
          path: '/admin/menu/dish',
          component: () => import("@/component/admin/DishInterface.vue")
        },
        {
          path: '/admin/menu/order',
          component: () => import("@/component/admin/OrderInterface.vue")
        },
        {
          path: '/admin/menu/backup',
          component: () => import("@/component/admin/BackupInterface.vue")
        }
      ]
    },
    {
      path: '/shop/menu',
      component: () => import("@/views/ShopMenu.vue"),
      children:[
        {
          path: '/shop/menu',
          component: () => import("@/component/shop/WelcomeInterface.vue"),
        },
        {
          path: '/shop/menu/card',
          component: () => import("@/component/shop/CardInterface.vue")
        },
        {
          path: '/shop/menu/dish',
          component: () => import("@/component/shop/DishInterface.vue")
        },
        {
          path: '/shop/menu/order',
          component: () => import("@/component/shop/OrderInterface.vue")
        },
        {
          path: '/shop/menu/info',
          component: () => import("@/component/shop/InfoInterface.vue")
        }
      ]
    },
    {
      path: '/deliver/menu',
      component: () => import("@/views/DeliverMenu.vue"),
      children:[
        {
          path: '/deliver/menu',
          component: () => import("@/component/deliver/WelcomeInterface.vue"),
        },
        {
          path: '/deliver/menu/order',
          component: () => import("@/component/deliver/OrderInterface.vue")
        },
        {
          path: '/deliver/menu/history',
          component: () => import("@/component/deliver/HistoryInterface.vue")
        },
        {
          path: '/deliver/menu/info',
          component: () => import("@/component/deliver/InfoInterface.vue")
        }
      ]
    }
  ]
})

const customerUrls= ['/customer/menu', '/customer/menu/card', '/customer/menu/dish', '/customer/menu/order','/customer/menu/vip','/customer/menu/info']
const adminUrls= ['/admin/menu', '/admin/menu/dish', '/admin/menu/order', '/admin/menu/backup']
const shopUrls= ['/shop/menu', '/shop/menu/card', '/shop/menu/dish', '/shop/menu/order','/shop/menu/info']
const deliverUrls= ['/deliver/menu', '/deliver/menu/order', '/deliver/menu/history','/deliver/menu/info']

router.beforeEach((to, from, next) => {
  const customer = customerStore()
  const admin = adminStore()
  const shop = shopStore()
  const deliver = deliverStore()
  if (customer.customerID === '' && customerUrls.includes(to.path)) {
    next('/customer/login')
    return
  }
  else if(admin.adminID === '' && adminUrls.includes(to.path)) {
    next('/admin/login')
    return
  }
  else if(shop.shopID === '' && shopUrls.includes(to.path)) {
    next('/shop/login')
    return
  }
  else if(deliver.deliverID === '' && deliverUrls.includes(to.path)) {
    next('/deliver/login')
    return
  }
  next()
})

export default router
