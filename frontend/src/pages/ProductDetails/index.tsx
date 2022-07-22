import { ReactComponent as ArrowIcon } from 'assets/images/arrow.svg';
import ProductPrice from 'components/ProductPrice';
import { Link } from 'react-router-dom';

import './styles.css';

const ProductDetails = () => {
    return (
        <div className="product-details-container">
            <div className="base-card product-details-card">
                <Link to="/catalog">
                    <div className="back-container">
                        <ArrowIcon /> <h2>Voltar</h2>
                    </div>
                </Link>
                <div className="row">
                    <div className="col-xl-6">
                        <div className="img-container">
                            <img
                                src="https://m.media-amazon.com/images/I/81IdScEcX2S._AC_SX679_.jpg"
                                alt="Nome do Produto"
                            />
                        </div>
                        <div className="name-price-container">
                            <h1>Nome do produto</h1>
                            <ProductPrice price={2962.23} />
                        </div>
                    </div>

                    <div className="col-xl-6">
                        <div className="description-container">
                            <h2>Descrição do Produto</h2>
                            <p>
                                Lorem ipsum dolor sit amet consectetur,
                                adipisicing elit. Soluta, eaque!
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ProductDetails;
