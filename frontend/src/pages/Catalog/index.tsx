import Pagination from 'components/Pagination';
import ProductCard from 'components/ProductCard';
import { Link } from 'react-router-dom';
import { Product } from 'types/products';

import './styles.css';

const Catalog = () => {
    const prod: Product = {
        id: 2,
        name: 'Smart TV',
        description: 'Uma TV',
        price: 2193.0,
        imgUrl: 'https://m.media-amazon.com/images/I/81IdScEcX2S._AC_SX679_.jpg',
        date: '2020-07-14T10:00:00Z',
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
            <div className="container my-4 catalog-container">
                <div className="row catalog-title-container">
                    <h1>Catálogo de produtos</h1>
                </div>

                <div className="row">
                    <div className="col-sm-6 col-lg-4 col-xl-3">
                        <Link to="catalog/1">
                            <ProductCard product={prod} />
                        </Link>
                    </div>

                    <div className="col-sm-6 col-lg-4 col-xl-3">
                        <Link to="catalog/1">
                            <ProductCard product={prod} />
                        </Link>
                    </div>

                    <div className="col-sm-6 col-lg-4 col-xl-3">
                        <Link to="catalog/1">
                            <ProductCard product={prod} />
                        </Link>
                    </div>

                    <div className="col-sm-6 col-lg-4 col-xl-3">
                        <Link to="catalog/1">
                            <ProductCard product={prod} />
                        </Link>
                    </div>

                    <div className="col-sm-6 col-lg-4 col-xl-3">
                        <Link to="catalog/1">
                            <ProductCard product={prod} />
                        </Link>
                    </div>

                    <div className="col-sm-6 col-lg-4 col-xl-3">
                        <Link to="catalog/1">
                            <ProductCard product={prod} />
                        </Link>
                    </div>
                </div>

                <div className="row">
                    <Pagination />
                </div>
            </div>
        </>
    );
};

export default Catalog;
