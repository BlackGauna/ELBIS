import React from 'react';
import {BrowserRouter as Router, Route} from "react-router-dom";
// import logo from './logo.svg';
// import './App.css';
import "bootstrap/dist/css/bootstrap.min.css";
import Navbar from "./components/navbar.component";
import ShowUser from "./components/user-list.component";


// function App() {
//   return (
//     <div className="App">
//       <header className="App-header">
//         <img src={logo} className="App-logo" alt="logo" />
//         <p>
//           Edit <code>src/App.js</code> and save to reload.
//         </p>
//         <a
//           className="App-link"
//           href="https://reactjs.org"
//           target="_blank"
//           rel="noopener noreferrer"
//         >
//           Learn React
//         </a>
//       </header>
//     </div>
//   );
// }

function App(){
  return (
      <Router>
          <div className="container">
              <Navbar />
              <br/>
              <Route path="/user" component={ShowUser} />
          </div>
      </Router>
  );
}

export default App;
