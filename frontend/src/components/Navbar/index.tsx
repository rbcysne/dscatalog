import { Link, NavLink } from 'react-router-dom';

import './styles.css';
import 'bootstrap/js/src/collapse';
import { useContext, useEffect } from 'react';
import history from 'util/history';
import { AuthContext } from 'AuthContext';
import { getTokenData, isAuthenticated } from 'util/auth';
import { removeAuthData } from 'util/storage';


function Navbar() {

  const {authContextData, setAuthContextData} = useContext(AuthContext);

  // const [authData, setAuthData] = useState<AuthContextData>({authenticated: false});

  useEffect(() => {
    if(isAuthenticated()) {
      setAuthContextData({
        authenticated: true,
        tokenData: getTokenData()
      })
    } else {
      setAuthContextData({
        authenticated: false,
      })
    }
  }, [setAuthContextData]);

  const handleLogout = (event: React.MouseEvent<HTMLAnchorElement>) => {
    event.preventDefault();

    removeAuthData();

    setAuthContextData({
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
            <li>

            </li>
          </ul>

        </div>
        <div className="navbar-login-logout">
            {
              authContextData.authenticated ? (
                <>
                  <span className="navbar-username">{authContextData.tokenData?.user_name}</span>
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
