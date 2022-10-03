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

function AddBook() {
  const [title, setTitle] = useState("");
  const [author, setAuthor] = useState("");

  async function handleSubmit(e) {
    e.preventDefault();
    await fetch("/api/books", {
      method: "post",
      body: JSON.stringify({ title, author }),
      headers: {
        "Content-Type": "application/json",
      },
    });
  }

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label>
          Title:{" "}
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />
        </label>
      </div>
      <div>
        <label>
          Author:{" "}
          <input
            type="text"
            value={author}
            onChange={(e) => setAuthor(e.target.value)}
          />
        </label>
      </div>
      <div>
        <button>Submit</button>
      </div>
    </form>
  );
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
      <AddBook />
    </div>
  );
}

export default App;
