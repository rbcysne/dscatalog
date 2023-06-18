import './styles.css';

const Form = () => {
    return (
        <div className="product-form-container">
            <div className="base-card product-form-card">
                <h1 className="product-form-card-title">Dados do Produto</h1>

                <form action="">
                    <div className="row product-form-card-info-container">
                        <div className="col-lg-6 product-form-card-details">
                            <div className="margin-bottom-30">
                                <input 
                                    type="text" 
                                    className="form-control base-input" 
                                    placeholder="Nome do Produto" />
                            </div>

                            <div className="margin-bottom-30">
                                <input 
                                    type="text" 
                                    className="form-control base-input"
                                    placeholder="Categoria" />
                            </div>

                            <div className="">
                                <input 
                                    type="text" 
                                    className="form-control base-input"
                                    placeholder="Preço" />
                            </div>

                        </div>
                        <div className="col-lg-6 product-form-card-description">
                            <div>
                                <textarea 
                                    name="" 
                                    rows={10} 
                                    className="form-control base-input h-auto"
                                    placeholder="Descrição">
                                </textarea>
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