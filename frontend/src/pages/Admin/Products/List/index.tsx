import { Link } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { AxiosRequestConfig } from 'axios';
import { requestBackend } from 'util/requests';
import ProductCardCrud from 'pages/Admin/Products/ProductCardCrud';
import { SpringPage } from 'types/vendor/spring';
import { ProductDTO } from 'types/ProductDTO';

import './styles.css';

const List = () => {

    const [page, setPage] = useState<SpringPage<ProductDTO>>();

    const getProducts = () => {
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
    };

    useEffect(() => {
        getProducts();
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
                <div className="col-md-12">
                    {(
                        page?.content.map((product) => {
                            return(
                                <div key={product.id}>
                                    <ProductCardCrud product={product} 
                                        onDelete={() => getProducts()}/>
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
