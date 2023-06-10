import './styles.css';

import ProductPrice from 'components/ProductPrice';
import { Product } from 'types/product';

type Props = {
    product: Product;
}

const ProductCardCrud = ( { product } : Props ) => {

    return (

        <div className="base-card product-card">
            <div className="card-top-container">
                <img src={product.imgUrl} alt="Nome do produto" />
            </div>

            <div className="card-bottom-container">
                <h6>{product.name}</h6>
                
                <ProductPrice price={product.price}/>

            </div>
        </div>
    );
}

export default ProductCardCrud;