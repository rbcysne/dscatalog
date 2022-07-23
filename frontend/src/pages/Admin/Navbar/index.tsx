import './styles.css';

const Navbar = () => {
    return (
        <nav className="admin-navbar-container">
            <ul className="admin-navbar-items">
                <li>
                    <a href="link" className="admin-navbar-item active">
                        <p>Produtos</p>
                    </a>
                </li>
                <li>
                    <a href="link" className="admin-navbar-item">
                        <p>Categorias</p>
                    </a>
                </li>
                <li>
                    <a href="link" className="admin-navbar-item">
                        <p>Usuários</p>
                    </a>
                </li>
            </ul>
        </nav>
    );
};

export default Navbar;
