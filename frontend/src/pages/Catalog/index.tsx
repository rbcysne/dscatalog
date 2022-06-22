import ProductCard from 'components/ProductCard';
import { Product } from 'types/products';

const Catalog = () => {

    const prod : Product = {
        "id": 2,
        "name": "Smart TV",
        "description": "Uma TV",
        "price": 2193.0,
        "imgUrl": "https://m.media-amazon.com/images/I/81IdScEcX2S._AC_SX679_.jpg",
        "date": "2020-07-14T10:00:00Z",
        "categories": [
            {
                "id": 1,
                "name": "Livros"
            },
            {
                "id": 3,
                "name": "Computadores"
            }
        ]

    }
    return (
        <>
            <div className="container my-4">
                <div className="row">
                    <div className="col-sm-6 col-lg-4 col-xl-3">
                        <ProductCard product={prod}/>
                    </div>

                    <div className="col-sm-6 col-lg-4 col-xl-3">
                        <ProductCard product={prod}/>
                    </div>

                    <div className="col-sm-6 col-lg-4 col-xl-3">
                        <ProductCard product={prod}/>
                    </div>

                    <div className="col-sm-6 col-lg-4 col-xl-3">
                        <ProductCard product={prod}/>
                    </div>

                    <div className="col-sm-6 col-lg-4 col-xl-3">
                        <ProductCard product={prod}/>
                    </div>

                    <div className="col-sm-6 col-lg-4 col-xl-3">
                        <ProductCard product={prod}/>
                    </div>
                </div>
            </div>
        </>
    );
};

export default Catalog;
