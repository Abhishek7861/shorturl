import React, { useState } from 'react';
import './ShortUrlHit.css';

export default function ShortUrlHit({ urlHits, setShortUrl }) {

  const [inputValue, setInputValue] = useState('');

  const handleInputChange = (event) => {
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
    </div>

  )
}
