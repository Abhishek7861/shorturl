import React, { useState } from 'react';
import './ShortUrlHit.css';
import { Link } from "react-router-dom";

export default function ShortUrlHit({ urlHits, setShortUrl, longUrl, setUrlHits, setLongUrl, invalidMessage }) {

  const [inputValue, setInputValue] = useState('');

  const handleInputChange = (event) => {
    setUrlHits(null);
    setLongUrl(null);
    setInputValue(event.target.value);
  };

  const handleButtonClick = () => {
    setShortUrl(inputValue);
  };

  return (
    <div className='short-url'>
      <p className='short-url-hit-msg'>Short URL Hits </p>
      <input className='input-field' placeholder='Paste a short url' onChange={handleInputChange}></input>
      <button className='submit-btn' onClick={handleButtonClick}>Submit</button>
      <p className='input-box-url'> Hits: {urlHits}</p>
      {longUrl && (
        <Link to={longUrl} target="_blank">
          <p className='input-box-url'>{longUrl}</p>
        </Link>
      )}
      {invalidMessage && (
        <p className='error'>Invalid URL or URL Not Exists</p>
      )}
    </div>

  )
}
