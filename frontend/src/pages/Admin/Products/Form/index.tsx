import { useForm } from 'react-hook-form';
import './styles.css';
import { ProductDTO } from 'types/ProductDTO';
import { AxiosRequestConfig } from 'axios';
import { requestBackend } from 'util/requests';

const Form = () => {

    const { register, handleSubmit, formState: {errors} } = useForm<ProductDTO>();

    const onSubmit = (productDto:ProductDTO) => {

        const cat = {...productDto, categories:[ {id: 1, name:""} ], imgUrl: "nada"};
        
        const config: AxiosRequestConfig = {
            method: 'POST',
            url: '/products',
            data: cat,
            withCredentials: true,
        }

        requestBackend(config)
            .then((response) => {
                console.log(response.data);
            });
    };

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
                                <input 
                                    
                                    type="text" 
                                    className="form-control base-input"
                                    placeholder="Categoria" />
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
                        <button className="btn btn-outline-danger btn-form-card">
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