import React from 'react';
import {BrowserRouter as Router, Route} from "react-router-dom";
import { Redirect } from "react-router-dom";
//import "bootstrap/dist/css/bootstrap.min.css";
import'./ELBISWeb.css';

//##########Component imports##########
import Navbar from "./components/navbar.component";
import ELBISweb from "./components/ELBISweb.component";
import userView from "./components/user/userView.component";
import moderationView from "./components/moderation/moderationView.component";
import createUser from "./components/moderation/moderation_createUser.component";
import administrationView from "./components/administration/administrationView.component.js";
import loginView from "./components/loginView.component";

//##########App start##########
//TODO check which type of user is logged in before redirecting to moderation or administration
function App(){
  return (
      <Router>
          <Route exact path="/" component={loginView} />
          <Route path="/login" component={Navbar} />
          <br/>
          <Route exact path="/login/hauptseite" component={ELBISweb} />
          <Route exact path="/login/artikelverwaltung" component={userView} />
          <Route exact path="/login/moderation" component={moderationView} />
          <Route exact path="/login/nutzerErstellen" component={createUser} />
          <Route exact path="/login/administration" component={administrationView} />
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