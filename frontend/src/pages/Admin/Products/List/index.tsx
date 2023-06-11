import ProductCardCrud from 'pages/Admin/Products/ProductCardCrud';

import './styles.css';
import { Link } from 'react-router-dom';

const List = () => {
    const product = {
        id: 2,
        name: 'Smart TVa',
        description:
            'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
        price: 2190.0,
        imgUrl: 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/2-big.jpg',
        date: '2020-07-14T13:00:00Z',
        categories: [
            {
                id: 1,
                name: 'Livros',
            },
            {
                id: 3,
                name: 'Computadores',
            },
        ],
    };

    return (
        <>
            <div className="product-bar-crud-container">
                <Link to="/admin/products/create">
                    <button className="btn btn-primary btn-add-crud">
                        Adicionar
                    </button>
                </Link>
                <div className="base-card product-filter-container">
                    Search Bar
                </div>
            </div>

            <div className="row">
                <div className="col-sm-6 col-md-12">
                    <ProductCardCrud product={product} />
                </div>
                <div className="col-sm-6 col-md-12">
                    <ProductCardCrud product={product} />
                </div>
            </div>
        </>
    );
};

export default List;
