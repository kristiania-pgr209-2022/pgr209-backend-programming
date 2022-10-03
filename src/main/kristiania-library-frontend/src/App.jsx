import reactLogo from "./assets/react.svg";
import "./App.css";
import { useEffect, useState } from "react";

function BookList() {
  const [loading, setLoading] = useState(true);
  const [books, setBooks] = useState([]);
  useEffect(() => {
    (async () => {
      const res = await fetch("/api/books");
      if (res.ok) {
        setBooks(await res.json());
        setLoading(false);
      }
    })();
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

  return books.map((b) => <div>{b.title}</div>);
}

function App() {
  return (
    <div className="App">
      <div>
        <a href="https://vitejs.dev" target="_blank">
          <img src="./vite.svg" className="logo" alt="Vite logo" />
        </a>
        <a href="https://reactjs.org" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>Kristiania Library</h1>
      <BookList />
    </div>
  );
}

export default App;
