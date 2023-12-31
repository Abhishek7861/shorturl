import instance from './axiosConfig';


export default function get(url, params) {
    return instance.get(url, {params})
      .then(response => {
        // console.log(response.data);
        return response;
      })
      .catch(error => {
        console.error(error);
        throw error; // Re-throw the error to handle it elsewhere
      });
  }