import { Link } from 'react-router-dom';
import { AxiosRequestConfig } from 'axios';

import ProductPrice from 'components/ProductPrice';
import { ProductDTO } from 'types/ProductDTO';
import CategoryBadge from '../CategoryBadge';
import { requestBackend } from 'util/requests';

import './styles.css';



type Props = {
    product: ProductDTO;
    onDelete: Function;
}

const ProductCardCrud = ( { product, onDelete } : Props ) => {

    const handleDelete = (productId : number) => {

        if(!window.confirm("Confirma exclusão do produto?")) {
            return;
        }

        const config: AxiosRequestConfig = {
            method: 'DELETE',
            url: `/products/${productId}`,
            withCredentials: true,
        };

        requestBackend(config).then(() => {
            onDelete();
        });
    };

    return (

        <div className="base-card product-card-crud">
            <div className="card-top-container-crud">
                <img src={product.imgUrl} alt={product.name} />
            </div>

            <div className="product-card-description-crud">
                <div className="card-bottom-container-crud">
                    <h6>{product.name}</h6>
                    
                    <ProductPrice price={product.price}/>
                </div>

                <div className="product-categories-container-crud">
                    {
                        product.categories.map(
                            (category) => (
                                <CategoryBadge categoryName={category.name} key={category.id} /> 
                            )
                        )
                    }
                </div>
            </div>

            <div className="product-card-buttons-container-crud">
                    <button onClick={() => handleDelete(product.id)}
                        className="btn btn-outline-danger product-card-btn-crud">
                        EXCLUIR
                    </button>
                    <Link to={`/admin/products/${product.id}`}>
                        <button className="btn btn-outline-secondary product-card-btn-crud">
                            EDITAR
                        </button>
                    </Link>
            </div>
        </div>
    );
}

export default ProductCardCrud;