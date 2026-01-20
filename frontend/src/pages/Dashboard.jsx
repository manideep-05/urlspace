import { useEffect, useState } from "react";
import { createShortUrl, getMyUrls } from "../api/urlApi";

export default function Dashboard() {
  const [longUrl, setLongUrl] = useState("");
  const [shortUrl, setShortUrl] = useState("");
  const [urls, setUrls] = useState([]);

  const handleShorten = async () => {
    try {
      const res = await createShortUrl(longUrl);
      setShortUrl(res);
      fetchUrls();
    } catch (err) {
      alert("Failed to shorten URL");
    }
  };

  const fetchUrls = async () => {
    try {
      const data = await getMyUrls();
      setUrls(data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchUrls();
  }, []);

  return (
    <div>
      <h2>User Dashboard</h2>

      <div>
        <input
          placeholder="Enter long URL"
          value={longUrl}
          onChange={e => setLongUrl(e.target.value)}
          style={{ width: "400px" }}
        />
        <button onClick={handleShorten}>Shorten</button>
      </div>

      {shortUrl && (
        <p>
          Short URL: <a href={shortUrl} target="_blank">{shortUrl}</a>
        </p>
      )}

      <h3>My URLs</h3>
      <ul>
        {urls.map((u, i) => (
          <li key={i}>
            <a href={u.shortUrl} target="_blank">{u.shortUrl}</a>
            {" → "}
            {u.longUrl}
          </li>
        ))}
      </ul>
    </div>
  );
}
