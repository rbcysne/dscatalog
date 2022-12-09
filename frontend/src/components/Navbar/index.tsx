import { Link, NavLink } from 'react-router-dom';

import './styles.css';
import 'bootstrap/js/src/collapse';
import { useEffect, useState } from 'react';
import { AuthData } from 'types/AuthData';
import { getTokenData, isAuthenticated, removeAuthData } from 'util/requests';
import history from 'util/history';


function Navbar() {

  const [authData, setAuthData] = useState<AuthData>({authenticated: false});

  useEffect(() => {
    if(isAuthenticated()) {
      setAuthData({
        authenticated: true,
        tokenData: getTokenData()
      })
    } else {
      setAuthData({
        authenticated: false,
      })
    }
  }, []);

  const handleLogout = (event: React.MouseEvent<HTMLAnchorElement>) => {
    event.preventDefault();

    removeAuthData();

    setAuthData({
      authenticated: false,
    })

    history.replace("/");
  }

  return (
    <nav className="navbar navbar-expand-md bg-primary navbar-dark main-nav">
      <div className="container-fluid">
        <Link to="/" className="nav-logo-text">
          <h4>DS Catalog</h4>
        </Link>

        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#dscatalog-navbar"
          aria-controls="dscatalog-navbar"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="dscatalog-navbar">
          <ul className="navbar-nav offset-md-2 main-menu">
            <li>
              <NavLink to="/" activeClassName="active" exact>
                HOME
              </NavLink>
            </li>
            <li>
              <NavLink to="/catalog" activeClassName="active">
                CATÁLOGO
              </NavLink>
            </li>
            <li>
              <NavLink to="/admin" activeClassName="active">
                ADMIN
              </NavLink>
            </li>
          </ul>
        </div>

        <div>
          {
            authData.authenticated ? (
              <>
                <span>{authData.tokenData?.user_name} - </span>
                <a href="#logout" onClick={handleLogout}>LOGOUT</a>
              </>
            ) : (
              <Link to="/admin/auth">LOGIN</Link>
            )
          }
        </div>

      </div>
    </nav>
  );
}

export default Navbar;
