import "./App.css";
import { useEffect, useState } from "react";

function BookList() {
  const [loading, setLoading] = useState(true);
  const [books, setBooks] = useState([]);

  useEffect(() => {
    (async () => {
      const res = await fetch("/api/books");
      setBooks(await res.json());
      setLoading(false);
    })();
  }, [])

  if (loading) {
    return <div>Loading...</div>
  }

  return <ul>{books.map(b => <div>{b.title}</div>)}</ul>;
}

function App() {

  return (
    <div className="App">
      <h1>Library</h1>

      <BookList />
    </div>
  )
}

export default App
