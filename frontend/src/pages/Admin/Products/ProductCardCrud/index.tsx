import './styles.css';

import ProductPrice from 'components/ProductPrice';
import { Product } from 'types/product';
import CategoryBadge from '../CategoryBadge';

type Props = {
    product: Product;
}

const ProductCardCrud = ( { product } : Props ) => {

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
                    <button className="btn btn-outline-danger product-card-btn-crud">
                        EXCLUIR
                    </button>
                    <button className="btn btn-outline-secondary product-card-btn-crud">
                        EDITAR
                    </button>
            </div>
        </div>
    );
}

export default ProductCardCrud;