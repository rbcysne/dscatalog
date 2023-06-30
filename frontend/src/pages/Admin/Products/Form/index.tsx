import { useForm } from 'react-hook-form';
import { AxiosRequestConfig } from 'axios';
import { requestBackend } from 'util/requests';
import { useHistory, useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import Select from 'react-select';

import { ProductDTO } from 'types/ProductDTO';
import './styles.css';
import { Category } from 'types/category';



type UrlParams = {
    productId: string;
}

const Form = () => {

    const {productId} = useParams<UrlParams>();
    const isEditing = (productId !== 'create');

    const { register, handleSubmit, formState: {errors}, setValue } = useForm<ProductDTO>();

    const history = useHistory();

    useEffect(() => {

        if(isEditing) {
            requestBackend({url:`/products/${productId}`})
                .then((response) => {
                    const product = response.data as ProductDTO;

                    setValue('name', product.name);
                    setValue('price', product.price);
                    setValue('description', product.description);
                    setValue('imgUrl', product.imgUrl);
                    setValue('categories', product.categories);
                });
        };

    }, [productId, isEditing, setValue]);

    const onSubmit = (productDto:ProductDTO) => {

        const cat = {...productDto, 
                    categories: isEditing ? productDto.categories : [ {id: 1, name:""} ], 
                    imgUrl: isEditing ? productDto.imgUrl : "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg"};
        
        const config: AxiosRequestConfig = {
            method: isEditing ? 'PUT' : 'POST',
            url: isEditing ? `/products/${productId}` : '/products',
            data: cat,
            withCredentials: true,
        }

        requestBackend(config)
            .then((response) => {
                history.push("/admin/products");
            });
    };

    const handleCancel = () => {
        history.push("/admin/products");
    };

    const [selectCategories, setSelectCategories] = useState<Category[]>([]);

    useEffect(() => {
        requestBackend({url: '/categories'})
            .then((response) => {
                setSelectCategories(response.data.content);
            });
    }, []);

    return (
        <div className="product-form-container">
            <div className="base-card product-form-card">
                <h1 className="product-form-card-title">Dados do Produto</h1>

                <form onSubmit={handleSubmit(onSubmit)}>
                    <div className="row product-form-card-info-container">
                        <div className="col-lg-6 product-form-card-details">
                            <div className="margin-bottom-30">
                                <input 
                                    {...register("name", {
                                            required: 'Campo obrigatório',
                                        })
                                    }
                                    type="text" 
                                    className={`form-control base-input ${errors.name ? 'is-invalid' : ''}`}
                                    placeholder="Nome do Produto"
                                    name="name" />
                                <div className="invalid-feedback d-block">{errors.name?.message}</div>
                            </div>

                            <div className="margin-bottom-30">
                                <Select options={selectCategories} 
                                    isMulti
                                    classNamePrefix="product-form-card-select" 
                                    getOptionLabel={(category: Category) => category.name}
                                    getOptionValue={(category: Category) => String(category.id)} />
                                    
                            </div>

                            <div className="">
                                <input 
                                    {...register("price", {
                                            required: 'Campo obrigatório',
                                        })
                                    }
                                    type="text" 
                                    className={`form-control base-input ${errors.price ? 'is-invalid' : ''}`}
                                    placeholder="Preço"
                                    name="price" />
                                <div className="invalid-feedback d-block">{errors.price?.message}</div>
                            </div>

                        </div>
                        <div className="col-lg-6 product-form-card-description">
                            <div>
                                <textarea 
                                    {...register("description", {
                                            required: 'Campo obrigatório',
                                        })
                                    }
                                    
                                    rows={10} 
                                    className={`form-control base-input h-auto ${errors.description ? 'is-invalid' : ''}`}
                                    placeholder="Descrição"
                                    name="description" />
                                <div className="invalid-feedback d-block">{errors.description?.message}</div>
                            </div>
                        </div>
                    </div>

                    <div className="product-form-card-buttons-container">
                        <button onClick={handleCancel}
                                className="btn btn-outline-danger btn-form-card">
                            Cancelar
                        </button>
                        <button className="btn btn-primary btn-form-card">
                            Salvar
                        </button>

                    </div>
            </form>

            </div>
            
        </div>
    )
}

export default Form;