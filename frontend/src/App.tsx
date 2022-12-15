import React, { useState } from 'react';
import './assets/styles/custom.scss';
import './App.css';
import Routes from './Routes';
import { AuthContextData } from 'types/AuthContextData';
import { AuthContext } from 'AuthContext';

function App() {

  const [authContextData, setAuthContextData] = useState<AuthContextData> ({
    authenticated: false
  });

  return (
    <AuthContext.Provider value={{authContextData, setAuthContextData}}>
      <Routes />
    </AuthContext.Provider>
  );
}

export default App;
