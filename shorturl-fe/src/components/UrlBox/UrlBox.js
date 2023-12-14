import React, { useState } from 'react';
import './UrlBox.css';
import { Link } from "react-router-dom";

export default function UrlBox({ setLongUrl, shortUrl, setShortUrl, invalidMessage }) {

    const [inputValue, setInputValue] = useState('');

    const handleInputChange = (event) => {
        setShortUrl(null);
        setInputValue(event.target.value);
    };

    const handleButtonClick = () => {
        setLongUrl(inputValue);
    };


    return (
        <div className='input-box'>
            <p className='input-box-msg'>Submit the URL to Shorten</p>
            <input className='input-field' placeholder='Paste an url ex: https://www.yoururl.com/...' onChange={handleInputChange}></input>
            <button className='submit-btn' onClick={handleButtonClick}>Submit</button>
            {shortUrl && (
                <Link to={shortUrl} target="_blank">
                    <p className='input-box-url'>{shortUrl}</p>
                </Link>
            )}
            {invalidMessage && (
                <p className='error'>Invalid URL</p>
            )}
        </div>
    )
}
