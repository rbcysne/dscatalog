
import { BrowserRouter as Router, Switch, Route, Redirect } from "react-router-dom";

import Navbar from "components/Navbar";
import Home from "pages/Home";
import Catalog from "pages/Catalog";
import Admin from "pages/Admin";
import ProductDetails from "pages/ProductDetails";

const Routes = () => {
    return (
        <Router>
            <Navbar />
            <Switch>
                <Route component = { Home } path="/" exact />

                <Route component = { Catalog } path="/catalog" exact />

                <Route component = { ProductDetails } path="/catalog/:productId" exact />

                <Redirect from="/admin" to="/admin/products" exact />

                <Route component = { Admin } path="/admin" />
            </Switch>
        </Router>
    );
}

export default Routes;