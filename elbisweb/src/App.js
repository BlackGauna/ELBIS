import React from 'react';
import {BrowserRouter as Router, Route} from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";

//##########Component imports##########
import Navbar from "./components/navbar.component";
import ELBISweb from "./components/ELBISweb.component";
import userView from "./components/userView.component";
import moderationView from "./components/moderationView.component";
import administrationView from "./components/administrationView.component.js";
import loginView from "./components/loginView.component";

//##########App start##########
function App(){
  return (
      <Router>
          <Route path="/logout" component={loginView} />
          <Route path="/login" component={Navbar} />
          <br/>
          <Route path="/login/hauptseite" component={ELBISweb} />
          <Route path="/login/artikelverwaltung" component={userView} />
          <Route path="/login/moderation" component={moderationView} />
          <Route path="/login/administration" component={administrationView} />
      </Router>
  );
}

export default App;

/*
*
##########Default generated code##########
*
*/


// import logo from './logo.svg';


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