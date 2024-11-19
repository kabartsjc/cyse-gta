import axios from 'axios'
import { parseJwt } from './Helpers'

export const gtaApi = {
  authenticate,
  signup,
  numberOfUsers,
  numberOfOrders,
  getUsers,
  deleteUser,
  getOrders,
  deleteOrder,
  createOrder,
  getUserMe,
  submitApplication,
  renewToken,
  loadGTAApplicationInfo,
  updateGTAHistoryCourses
}

const netaddr =`http://129.174.95.42:8080`

function authenticate(username, password) {
  return instance.post(`/auth/authenticate`, { username, password }, {
    headers: { 'Content-type': 'application/json' }
  })
}

async function renewToken() {
  const refreshToken = localStorage.getItem("authToken");
  console.log(refreshToken)
  const response = await instance.post(`/auth/refresh`, null,
    {   params: {
      refreshToken: refreshToken
    } });
  const newAccessToken = response.data.accessToken;
  localStorage.setItem("authToken", newAccessToken);
}



function signup(user) {
  return instance.post(`/auth/signup`, user, {
    headers: { 'Content-type': 'application/json' }
  })
}

async function submitApplication(user, config, formData) {
  try {

    const response = await instance.post(`/gta/application`, formData, config);

    return response;

  } catch (error) {
    console.error('Error submitting application:', error);
    throw error;
  }
}

function loadGTAApplicationInfo(user, config) {
  try {
    const username = user.data.email;
    console.log(username);

    // Assuming you're sending 'username' as a query parameter
    const response = instance.get(`/app/gtainfo`, {
      params: {
        gta_param_name: username  // Send username as a query parameter
      },
      ...config  // Spread the config to include Authorization headers and any other settings
    });

    return response;
  } catch (error) {
    console.error('Error fetching data:', error);
    throw error;
  }
}



async function updateGTAHistoryCourses(user, updatedCourses, config) {
  try {
    const username = user.data.email;
    console.log(username)
    let courses = updatedCourses;
    await gtaApi.renewToken(); // Call your token renewal function here
    config.headers["Authorization"] = `Bearer ${localStorage.getItem("authToken")}`;
    console.log('config',config)

    // Assuming the endpoint for updating course history is /gta/courseHistory
    const response = await instance.put(`/gta/courseHistory?gta_param_name=${username}`, courses,{
      
      ...config  // Include Authorization headers and any other config settings
    });


    return response;
  } catch (error) {
    console.error("Error updating GTA history courses:", error);
    throw error;
  }
}


function numberOfUsers() {
  return instance.get(`/public/numberOfUsers`)
}

function numberOfOrders() {
  return instance.get(`/public/numberOfOrders`)
}

function getUsers(user, username) {
  const url = username ? `/api/users/${username}` : `/api/users`
  return instance.get(url, {
    headers: { 'Authorization': bearerAuth(user) }
  })
}

function deleteUser(user, username) {
  return instance.delete(`/api/users/${username}`, {
    headers: { 'Authorization': bearerAuth(user) }
  })
}

function getOrders(user, text) {
  const url = text ? `/api/orders?text=${text}` : '/api/orders'
  return instance.get(url, {
    headers: { 'Authorization': bearerAuth(user) }
  })
}

function deleteOrder(user, orderId) {
  return instance.delete(`/api/orders/${orderId}`, {
    headers: { 'Authorization': bearerAuth(user) }
  })
}

function createOrder(user, order) {
  return instance.post(`/api/orders`, order, {
    headers: {
      'Content-type': 'application/json',
      'Authorization': bearerAuth(user)
    }
  })
}

function getUserMe(user) {
  return instance.get(`/api/users/me`, {
    headers: { 'Authorization': bearerAuth(user) }
  })
}

// -- Axios

const instance = axios.create({
  baseURL: netaddr
})

instance.interceptors.request.use(function (config) {
  // If token is expired, redirect user to login
  if (config.headers.Authorization) {
    const token = config.headers.Authorization.split(' ')[1]
    const data = parseJwt(token)
    if (Date.now() > data.exp * 1000) {
      window.location.href = "/login"
    }
  }
  return config
}, function (error) {
  return Promise.reject(error)
})

// -- Helper functions

function bearerAuth(user) {
  return `Bearer ${user.accessToken}`
}
