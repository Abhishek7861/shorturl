import React, { useState } from 'react';
import './UrlBox.css';

export default function UrlBox({ setLongUrl, shortUrl }) {

    const [inputValue, setInputValue] = useState('');

    const handleInputChange = (event) => {
        setInputValue(event.target.value);
    };

    const handleButtonClick = () => {
        setLongUrl(inputValue);
    };


    return (
        <div className='input-box'>
            <p className='input-box-msg'>Submit the URL to Shorten</p>
            <input className='input-field' placeholder='Paste an url' onChange={handleInputChange}></input>
            <button className='submit-btn' onClick={handleButtonClick}>Submit</button>
            <p className='input-box-url'> Short URL: {shortUrl}</p>
        </div>
    )
}
