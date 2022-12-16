import { NavLink } from 'react-router-dom';
import { hasAnyRole } from 'util/auth';

import './styles.css';

const Navbar = () => {
    return (
        <nav className="admin-navbar-container">
            <ul className="admin-navbar-items">
                <li>
                    <NavLink to="/admin/products" className="admin-navbar-item">
                        <p>Produtos</p>
                    </NavLink>
                </li>
                <li>
                    <NavLink to="/admin/categories" className="admin-navbar-item">
                        <p>Categorias</p>
                    </NavLink>
                </li>
                { hasAnyRole(['ROLE_ADMIN']) && (
                        <li>
                            <NavLink to="/admin/users" className="admin-navbar-item">
                                <p>Usuários</p>
                            </NavLink>
                        </li>
                    )
                }
            </ul>
        </nav>
    );
};

export default Navbar;
