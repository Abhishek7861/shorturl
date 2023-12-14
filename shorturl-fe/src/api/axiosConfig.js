import axios from 'axios';

const instance = axios.create({
  baseURL: 'http://localhost:8080', // Replace with your API's base URL
  headers: {
    'Content-Type': 'application/json',
    // Add any other default headers here
  },
});

export default instance;