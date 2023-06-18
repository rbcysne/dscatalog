import './styles.css';

import ProductPrice from 'components/ProductPrice';
import { ProductDTO } from 'types/ProductDTO';

type Props = {
    product: ProductDTO;
}

const ProductCard = ( { product } : Props ) => {

    return (

        <div className="base-card product-card">
            <div className="card-top-container">
                <img src={product.imgUrl} alt={product.name} />
            </div>

            <div className="card-bottom-container">
                <h6>{product.name}</h6>
                
                <ProductPrice price={product.price}/>

            </div>
        </div>
    );
}

export default ProductCard;