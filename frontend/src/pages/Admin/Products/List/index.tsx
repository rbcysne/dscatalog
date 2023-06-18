import { Link } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { AxiosRequestConfig } from 'axios';
import { requestBackend } from 'util/requests';
import ProductCardCrud from 'pages/Admin/Products/ProductCardCrud';
import { SpringPage } from 'types/vendor/spring';
import { Product } from 'types/product';

import './styles.css';

const List = () => {

    const [page, setPage] = useState<SpringPage<Product>>();

    useEffect(() => {
        const params: AxiosRequestConfig = {
            method: 'GET',
            url: "/products",
            params: {
                page: 0,
                size: 50,
            }
        };

        requestBackend(params)
            .then((response) => {
                setPage(response.data);
            });
    }, []);

    return (
        <div className="product-crud-container">
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
                    {(
                        page?.content.map((product) => {
                            return(
                                <div key={product.id} className="col-sm-6 col-md-12">
                                    <ProductCardCrud product={product} />
                                </div>
                            );
                        })
                    )}
                </div>
            </div>
        </div>
    );
};

export default List;
