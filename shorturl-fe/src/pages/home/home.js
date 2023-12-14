import React, { useState } from 'react';
import TitleHeading from '../../components/TitleHeading/TitleHeading';
import UrlBox from '../../components/UrlBox/UrlBox';
import Footer from '../../components/Footer/Footer';
import ShortUrlHit from '../../components/ShortUrlHit/ShortUrlHit';

export default function Home() {
    const [longUrl, setLongUrl] = useState("");
    const [shortUrl, setShortUrl] = useState("");
    const [urlHits, setUrlHits] = useState(0);
    console.log(shortUrl);
    console.log(longUrl);


    return (
        <div>
            <TitleHeading></TitleHeading>
            <UrlBox shortUrl={shortUrl} setLongUrl={setLongUrl}></UrlBox>
            <ShortUrlHit urlHits = {urlHits} setShortUrl={setShortUrl}></ShortUrlHit>
            <Footer></Footer>
        </div>
    )
}
