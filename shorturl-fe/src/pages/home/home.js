import React, { useState, useEffect } from 'react';
import instance from '../../api/axiosConfig';
import TitleHeading from '../../components/TitleHeading/TitleHeading';
import UrlBox from '../../components/UrlBox/UrlBox';
import Footer from '../../components/Footer/Footer';
import ShortUrlHit from '../../components/ShortUrlHit/ShortUrlHit';
import get from '../../api/get';
import post from '../../api/post';

export default function Home() {
    const [longUrl_1, setLongUrl_1] = useState(null);
    const [shortUrl_1, setShortUrl_1] = useState(null);
    const [invalidMessage1, setInvalidMessage1] = useState(false);
    const [invalidMessage2, setInvalidMessage2] = useState(false);
    const [longUrl_2, setLongUrl_2] = useState(null);
    const [shortUrl_2, setShortUrl_2] = useState(null);
    const [urlHits, setUrlHits] = useState(0);
    const baseURL = instance.defaults.baseURL;

    function isValidUrl(url) {
        const urlPattern = /^http:\/\/localhost:8080\/[a-fA-F0-9]{10}$/;
        return urlPattern.test(url);
    }

    function isValidHttpsUrl(url) {
        const urlPattern = /^(https?:\/\/)?([\w-]+(\.[\w-]+)+\/?)?(:\d+)?(\/\S*)?$/;
        return urlPattern.test(url);
    }


    useEffect(() => {
        setInvalidMessage2(false);
        if (isValidUrl(shortUrl_2)) {
            let url = new URL(shortUrl_2);
            const firstPathSegment = url.pathname.split('/')[1];
            let nextUrl = "/" + firstPathSegment + "/info";
            get(nextUrl)
                .then(response => {
                    setUrlHits(response.data.urlHit);
                    setLongUrl_2(response.data.longUrl);
                })
                .catch(error => {
                    console.error(error);
                });
        }
        else if(shortUrl_2!=null){
            setInvalidMessage2(true);
            console.log("Invalid URL");
        }
    }, [shortUrl_2]);

    useEffect(() => {
        setInvalidMessage1(false);
        if (isValidHttpsUrl(longUrl_1)) {
            const params = { longUrl: longUrl_1 };
            post("/", params)
                .then(response => {
                    console.log(response);
                    let shortUrl = baseURL + "/" + response.data.shortUrl
                    setShortUrl_1(shortUrl);
                })
                .catch(error => {
                    console.error(error);
                });
        }
        else if(longUrl_1!=null){
            setInvalidMessage1(true);
            console.log("Invalid URL");
        }
    }, [longUrl_1, baseURL]);


    return (
        <div>
            <TitleHeading></TitleHeading>
            <UrlBox shortUrl={shortUrl_1} setLongUrl={setLongUrl_1} setShortUrl={setShortUrl_1} invalidMessage={invalidMessage1}></UrlBox>
            <ShortUrlHit urlHits={urlHits} setShortUrl={setShortUrl_2} longUrl={longUrl_2} setUrlHits={setUrlHits} setLongUrl={setLongUrl_2} invalidMessage={invalidMessage2}></ShortUrlHit>
            <Footer></Footer>
        </div>
    )
}
