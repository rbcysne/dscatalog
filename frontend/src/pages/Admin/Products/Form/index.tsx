import './styles.css';

const Form = () => {
    return (
        <div className="product-form-container">
            <div className="base-card product-form-card">
                <h1 className="product-form-card-title">Dados do Produto</h1>

                <form action="">
                    <div className="row">
                        <div className="col-lg-6">
                            <input 
                                type="text" 
                                className="form-control base-input" 
                                placeholder="Nome do Produto" />
                            <input type="text" className="form-control base-input" />
                            <input type="text" className="form-control base-input" />
                        </div>
                        <div className="col-lg-6">
                            <textarea name=""rows={10} className="form-control base-input"></textarea>

                        </div>
                    </div>
                    <div >
                        <button className="btn btn-outline-danger">
                            Cancelar
                        </button>
                        <button className="btn btn-primary">
                            Salvar
                        </button>

                    </div>
            </form>

            </div>
            
        </div>
    )
}

export default Form;